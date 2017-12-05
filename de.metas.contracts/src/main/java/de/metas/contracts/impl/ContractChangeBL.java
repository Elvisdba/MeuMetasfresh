package de.metas.contracts.impl;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.slf4j.Logger;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeDAO;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ContractChangeBL implements IContractChangeBL
{
	private static final Logger logger = LogManager.getLogger(ContractChangeBL.class);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	public static final String MSG_IS_NOT_ALLOWED_TO_TERMINATE_CURRENT_CONTRACT = "de.metas.contracts.isNotAllowedToTerminateCurrentContract";

	@Override
	public void cancelContract(@NonNull final I_C_Flatrate_Term currentTerm,
			final @NonNull ContractChangeParameters contractChangeParameters)
	{
		final I_C_Flatrate_Term initialContract = Services.get(IFlatrateBL.class).getInitialFlatrateTerm(currentTerm);
		if (initialContract == null || contractChangeParameters.isVoidSingleContract())
		{
			cancelContractIfNotCanceledAlready(currentTerm, contractChangeParameters);
			unlinkContractIfNeeded(currentTerm, contractChangeParameters);
		}
		else
		{
			// start canceling with first ancestor
			cancelContractIfNotCanceledAlready(initialContract, contractChangeParameters);
		}
	}

	private void unlinkContractIfNeeded(@NonNull final I_C_Flatrate_Term currentTerm,
			final @NonNull ContractChangeParameters contractChangeParameters)
	{
		if (contractChangeParameters.isVoidSingleContract())
		{
			final I_C_Flatrate_Term ancestor = flatrateDAO.retrieveAncestorFlatrateTerm(currentTerm);
			if (ancestor != null)
			{
				ancestor.setC_FlatrateTerm_Next(null);
				ancestor.setAD_PInstance_EndOfTerm(null);
				setAncestorMasterEndDateWhenUnlinkContract(ancestor);
				InterfaceWrapperHelper.save(ancestor);
			}
		}
	}

	private void setAncestorMasterEndDateWhenUnlinkContract(@NonNull final I_C_Flatrate_Term ancestor)
	{
		if (ancestor.isAutoRenew())
		{
			ancestor.setMasterEndDate(null);
		}
		else
		{
			ancestor.setMasterEndDate(ancestor.getEndDate());
		}
	}

	@Builder
	@Getter
	public static class ContextForCompesationOrder
	{
		@NonNull
		private final Timestamp changeDate;

		@NonNull
		private final List<I_C_SubscriptionProgress> subscriptionProgress;

		@NonNull
		private final I_C_Flatrate_Term currentTerm;

		@Default
		@Setter
		private boolean isOrderCreated = false;
	}

	private void cancelContractIfNotCanceledAlready(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		if (isCanceledContract(currentTerm))
		{
			return;
		}

		if (isNotAllowedToTerminateCurrentContract(currentTerm, contractChangeParameters))
		{
			throw new AdempiereException(MSG_IS_NOT_ALLOWED_TO_TERMINATE_CURRENT_CONTRACT,
					new Object[] { currentTerm });
		}

		createCompesationOrderAndDeleteDeliveriesIfNeeded(currentTerm, contractChangeParameters);
		setTerminatioReasonAndMemo(currentTerm, contractChangeParameters);
		setMasterDates(currentTerm, contractChangeParameters);
		currentTerm.setIsCloseInvoiceCandidate(contractChangeParameters.isCloseInvoiceCandidate());
		currentTerm.setIsAutoRenew(false);
		setContractStatus(currentTerm, contractChangeParameters);
		setClosedDocStatusIfNeeded(currentTerm, contractChangeParameters);
		InterfaceWrapperHelper.save(currentTerm);

		cancelNextContractIfNeeded(currentTerm, contractChangeParameters);
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(currentTerm);
	}

	@Override
	public boolean isCanceledContract(@NonNull final I_C_Flatrate_Term currentTerm)
	{
		return X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(currentTerm.getContractStatus())
				|| X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(currentTerm.getContractStatus());
	}

	private boolean isNotAllowedToTerminateCurrentContract(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		return contractChangeParameters.isVoidSingleContract() && currentTerm.getC_FlatrateTerm_Next_ID() > 0;

	}

	private void createCompesationOrderAndDeleteDeliveriesIfNeeded(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		final Timestamp changeDate = contractChangeParameters.getChangeDate();
		if (changeDate.before(currentTerm.getEndDate()))
		{
			final List<I_C_SubscriptionProgress> subscriptionProgress = Services.get(IContractsDAO.class).getSubscriptionProgress(currentTerm);

			final ContextForCompesationOrder compensationOrderContext = ContextForCompesationOrder.builder()
					.changeDate(changeDate)
					.currentTerm(currentTerm)
					.subscriptionProgress(subscriptionProgress)
					.build();
			createCompesationOrderIfNeeded(compensationOrderContext);
			deleteDeliveriesAdjustOrderLine(compensationOrderContext);
			currentTerm.setEndDate(computeEndDate(currentTerm, changeDate));
		}
	}

	private void setMasterDates(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		if (contractChangeParameters.isVoidSingleContract())
		{
			currentTerm.setMasterStartDate(null);
			currentTerm.setMasterEndDate(null);
		}
		else
		{
			currentTerm.setMasterEndDate(computeMasterEndDate(currentTerm, contractChangeParameters.getChangeDate()));
		}
	}

	private void setContractStatus(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		if (contractChangeParameters.isVoidSingleContract())
		{
			currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Voided);
		}
		else
		{
			currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		}
	}

	private void setClosedDocStatusIfNeeded(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		if (contractChangeParameters.isVoidSingleContract())
		{
			currentTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Closed);
		}
	}

	private void cancelNextContractIfNeeded(@NonNull final I_C_Flatrate_Term currentTerm,
			@NonNull final ContractChangeParameters contractChangeParameters)
	{
		if (currentTerm.getC_FlatrateTerm_Next_ID() > 0)
		{
			cancelContractIfNotCanceledAlready(currentTerm.getC_FlatrateTerm_Next(), contractChangeParameters);
		}
	}

	private void createCompesationOrderIfNeeded(@NonNull final ContextForCompesationOrder compensationOrderContext)
	{
		final I_C_Flatrate_Term currentTerm = compensationOrderContext.getCurrentTerm();
		final Timestamp changeDate = compensationOrderContext.getChangeDate();

		final I_C_Contract_Change changeConditions = Services.get(IContractChangeDAO.class).retrieveChangeConditions(currentTerm, X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt, changeDate);
		Check.assume(changeConditions != null, "");

		if (currentTerm.getC_OrderLine_Term_ID() > 0 && changeConditions.getM_Product_ID() > 0)
		{
			final I_C_Order termChangeOrder = createCompesationOrder(currentTerm, changeConditions, changeDate);
			final BigDecimal difference = computePriceDifference(compensationOrderContext, changeConditions);
			final I_C_OrderLine termChangeOL = createChargeOrderLine(termChangeOrder, changeConditions, difference);
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			docActionBL.processEx(termChangeOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			currentTerm.setC_OrderLine_TermChange_ID(termChangeOL.getC_OrderLine_ID());
			compensationOrderContext.setOrderCreated(true);
		}
	}

	private final I_C_Order createCompesationOrder(@NonNull final I_C_Flatrate_Term currentTerm, @NonNull final I_C_Contract_Change changeConditions, final Timestamp changeDate)
	{
		final I_C_OrderLine currentTermOl = currentTerm.getC_OrderLine_Term();
		final I_C_Order currentTermOrder = currentTermOl.getC_Order();
		final IOrderPA orderPA = Services.get(IOrderPA.class);
		final String trxName = InterfaceWrapperHelper.getTrxName(currentTerm);
		final I_C_Order termChangeOrder = orderPA.copyOrder(currentTermOrder, false, trxName);
		final int pricingSystemId = getPricingSystemId(currentTerm, changeConditions);
		termChangeOrder.setM_PricingSystem_ID(pricingSystemId);

		termChangeOrder.setDateOrdered(SystemTime.asDayTimestamp());
		termChangeOrder.setDatePromised(changeDate);
		termChangeOrder.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.save(termChangeOrder);

		return termChangeOrder;
	}

	private Timestamp computeEndDate(@NonNull final I_C_Flatrate_Term currentTerm, final Timestamp changeDate)
	{
		if (changeDate.before(currentTerm.getStartDate()))
		{
			return currentTerm.getStartDate();
		}

		return new Timestamp(changeDate.getTime());
	}

	private Timestamp computeMasterEndDate(@NonNull final I_C_Flatrate_Term contract, final Timestamp changeDate)
	{
		final I_C_Flatrate_Term initialContract = Services.get(IFlatrateBL.class).getInitialFlatrateTerm(contract);
		if (initialContract == null && !changeDate.after(contract.getEndDate()))
		{
			return contract.getEndDate();
		}

		if (initialContract != null && changeDate.before(initialContract.getStartDate()))
		{
			return initialContract.getStartDate();
		}

		return new Timestamp(changeDate.getTime());
	}

	private void setTerminatioReasonAndMemo(@NonNull final I_C_Flatrate_Term currentTerm, final @NonNull ContractChangeParameters contractChangeParameters)
	{
		final String terminationMemo = contractChangeParameters.getTerminationMemo();
		final String terminationReason = contractChangeParameters.getTerminationReason();
		if (!Check.isEmpty(terminationReason, true))
		{
			currentTerm.setTerminationReason(terminationReason);
		}

		if (!Check.isEmpty(terminationMemo, true))
		{
			currentTerm.setTerminationMemo(terminationMemo);
		}
	}

	private void deleteDeliveriesAdjustOrderLine(final ContextForCompesationOrder compensationOrderContext)
	{
		final List<I_C_SubscriptionProgress> sps = compensationOrderContext.getSubscriptionProgress();
		final Timestamp changeDate = compensationOrderContext.getChangeDate();
		final I_C_OrderLine oldOl = compensationOrderContext.isOrderCreated() ? compensationOrderContext.getCurrentTerm().getC_OrderLine_Term() : null;
		final I_C_Order oldOrder = compensationOrderContext.isOrderCreated() ? oldOl.getC_Order() : null;

		BigDecimal surplusQty = BigDecimal.ZERO;
		for (final I_C_SubscriptionProgress currentSP : sps)
		{
			if (changeDate.after(currentSP.getEventDate()))
			{
				setQuitContractStatus(currentSP);
			}
			else
			{
				surplusQty = deleteSubscriptionProgressAndComputeSurplusQty(currentSP, surplusQty);
			}
		}

		if (oldOl != null && surplusQty.signum() != 0)
		{
			logger.info("Adjusting QtyOrdered of order " + oldOrder.getDocumentNo() + ", line " + oldOl.getLine());
			oldOl.setQtyOrdered(oldOl.getQtyOrdered().subtract(surplusQty));
			final IOrderPA orderPA = Services.get(IOrderPA.class);
			orderPA.reserveStock(oldOrder, oldOl);
		}
	}

	private BigDecimal deleteSubscriptionProgressAndComputeSurplusQty(@NonNull final I_C_SubscriptionProgress currentSP, BigDecimal surplusQty)
	{
		final String evtType = currentSP.getEventType();
		final String status = currentSP.getStatus();

		if (X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(evtType)
				&& (X_C_SubscriptionProgress.STATUS_Planned.equals(status) || X_C_SubscriptionProgress.STATUS_Open.equals(status)))
		{
			surplusQty = surplusQty.add(currentSP.getQty());
			InterfaceWrapperHelper.delete(currentSP);
		}
		else if (X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause.equals(evtType) || X_C_SubscriptionProgress.EVENTTYPE_EndOfPause.equals(evtType))
		{
			InterfaceWrapperHelper.delete(currentSP);
		}

		return surplusQty;
	}

	private void setQuitContractStatus(@NonNull final I_C_SubscriptionProgress progress)
	{
		final Timestamp today = SystemTime.asDayTimestamp();
		if (today.after(progress.getEventDate()))
		{
			progress.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Quit);
			InterfaceWrapperHelper.save(progress);
		}
	}

	private I_C_OrderLine createChargeOrderLine(final I_C_Order newOrder, final I_C_Contract_Change changeConditions, final BigDecimal additionalCharge)
	{
		final MOrderLine chargeOlPO = new MOrderLine((MOrder)InterfaceWrapperHelper.getPO(newOrder));
		final de.metas.interfaces.I_C_OrderLine chargeOl = InterfaceWrapperHelper.create(chargeOlPO, de.metas.interfaces.I_C_OrderLine.class);

		chargeOlPO.setM_Product_ID(changeConditions.getM_Product_ID());
		chargeOlPO.setQtyOrdered(BigDecimal.ONE);
		chargeOlPO.setQtyEntered(BigDecimal.ONE);

		chargeOlPO.setPrice();

		chargeOl.setIsManualPrice(true);
		chargeOlPO.setPriceActual(additionalCharge.add(chargeOlPO.getPriceActual()));
		chargeOlPO.setPriceEntered(additionalCharge.add(chargeOlPO.getPriceEntered()));

		InterfaceWrapperHelper.save(chargeOl);

		logger.debug("created new order line " + chargeOlPO);
		return chargeOl;
	}

	private BigDecimal computePriceDifference(@NonNull final ContextForCompesationOrder compensationOrderContext, @NonNull final I_C_Contract_Change contractChange)
	{
		final List<I_C_SubscriptionProgress> sps = compensationOrderContext.getSubscriptionProgress();
		final Timestamp changeDate = compensationOrderContext.getChangeDate();

		final List<I_C_SubscriptionProgress> deliveries = sps.stream()
				.filter(currentSP -> changeDate.after(currentSP.getEventDate())
						&& X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(currentSP.getEventType()))
				.collect(Collectors.toList());

		if (deliveries.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
		final I_C_Flatrate_Term currentTerm = compensationOrderContext.getCurrentTerm();
		final Properties ctx = InterfaceWrapperHelper.getCtx(currentTerm);
		final String trxName = InterfaceWrapperHelper.getTrxName(currentTerm);
		final int pricingSystemId = getPricingSystemId(currentTerm, contractChange);

		// compute the difference (see javaDoc of computePriceDifference for details)
		final BigDecimal difference = subscriptionBL.computePriceDifference(ctx, pricingSystemId, deliveries, trxName);
		logger.debug("The price difference to be applied on deliveries before the change is " + difference);
		return difference;
	}

	private int getPricingSystemId(final I_C_Flatrate_Term currentTerm, final I_C_Contract_Change changeConditions)
	{
		final int pricingSystemId;
		if (changeConditions.getM_PricingSystem_ID() > 0)
		{
			pricingSystemId = changeConditions.getM_PricingSystem_ID();
		}
		else
		{
			pricingSystemId = currentTerm.getC_Flatrate_Conditions().getM_PricingSystem_ID();
		}
		return pricingSystemId;
	}

	@Override
	public void endContract(I_C_Flatrate_Term currentTerm)
	{
		Check.assumeNotNull(currentTerm, "Param 'currentTerm' not null");
		currentTerm.setIsAutoRenew(false);
		currentTerm.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract);
		InterfaceWrapperHelper.save(currentTerm);
	}
}
