package de.metas.edi.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.edi.api.IEDIBPartnerService;
import de.metas.edi.api.IOrdrspBL;
import de.metas.edi.api.IOrdrspDAO;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_BPartner_Location;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.esb.edi.model.X_EDI_OrdrspLine;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.purchasing.api.IBPartnerProductDAO;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrdrspBL implements IOrdrspBL
{

	@Override
	public I_EDI_Ordrsp addToOrdrspCreateIfNotExistForOrder(final I_C_Order order)
	{
		final IEDIBPartnerService ediBPartnerService = Services.get(IEDIBPartnerService.class);

		if (!ediBPartnerService.isOrdrspRecipient(order.getC_BPartner(), order.getDateOrdered()))
		{
			return null;
		}

		Check.assumeNotEmpty(order.getPOReference(), "C_Order {} has a not-empty POReference", order);

		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final I_EDI_Ordrsp ordrsp = retrieveOrCreateOrdrsp(order);
		order.setEDI_Ordrsp(ordrsp);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			if (orderLine.getEDI_OrdrspLine_ID() > 0)
			{
				continue; // is already assigned to an ordrsp line
			}
			if (orderLine.isPackagingMaterial())
			{
				continue; // packing materials from the OL don't belong into the ordrsp document
			}

			final I_EDI_OrdrspLine ordrspLine = retrieveOrCreateOrdrspLine(order, ordrsp, orderLine);
			Check.errorIf(
					ordrspLine.getM_Product_ID() != orderLine.getM_Product_ID(),
					"EDI_OrdrspLine {} of EDI_Ordrsp {} has M_Product_ID {} and C_OrderLine {} of C_Order {} has M_Product_ID {}, but both have POReference {} and Line {} ",
					ordrspLine, ordrsp, ordrspLine.getM_Product_ID(),
					orderLine, order, orderLine.getM_Product_ID(),
					order.getPOReference(), orderLine.getLine());

			orderLine.setEDI_OrdrspLine(ordrspLine);
			InterfaceWrapperHelper.save(orderLine);
		}

		return ordrsp;
	}

	private I_EDI_Ordrsp retrieveOrCreateOrdrsp(final I_C_Order order)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final IEDIBPartnerService ediBPartnerService = Services.get(IEDIBPartnerService.class);
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);

		final I_C_BPartner handoverPartner = InterfaceWrapperHelper.create(orderBL.getHandoverPartner(order), I_C_BPartner.class);

		I_EDI_Ordrsp ordrsp = ordrspDAO.retrieveMatchingOrdrspOrNull(handoverPartner, order.getPOReference(), InterfaceWrapperHelper.getContextAware(order));

		if (ordrsp == null)
		{
			ordrsp = InterfaceWrapperHelper.newInstance(I_EDI_Ordrsp.class, order);

			ordrsp.setPOReference(order.getPOReference());
			ordrsp.setC_Currency_ID(order.getC_Currency_ID());

			ordrsp.setDeliveryDate(order.getPreparationDate());
			ordrsp.setShipDate(order.getDatePromised());

			ordrsp.setHandOver_Partner(handoverPartner);

			final I_C_BPartner_Location handoverLocation = InterfaceWrapperHelper.create(orderBL.getHandoverLocation(order), I_C_BPartner_Location.class);
			ordrsp.setHandOver_Location_ID(handoverLocation.getC_BPartner_Location_ID());
			ordrsp.setDeliveryGLN(handoverLocation.getGLN());

			final String receiverIdentification = ediBPartnerService.getEdiPartnerIdentification(handoverPartner, order.getDateOrdered());
			ordrsp.setEDIReceiverIdentification(receiverIdentification);

			// https://github.com/metasfresh/metasfresh/issues/307
			final I_C_BPartner orgBPartner = InterfaceWrapperHelper.create(bpartnerOrgBL.retrieveLinkedBPartner(order.getAD_Org()), I_C_BPartner.class);

			final String supplierGLN = ediBPartnerService.getEdiPartnerIdentification(orgBPartner, order.getDateOrdered());
			ordrsp.setSupplierGLN(supplierGLN);
			ordrsp.setEDISenderIdentification(supplierGLN);

			InterfaceWrapperHelper.save(ordrsp);
		}
		return ordrsp;
	}

	private I_EDI_OrdrspLine retrieveOrCreateOrdrspLine(final I_C_Order order, final I_EDI_Ordrsp ordrsp, final I_C_OrderLine orderLine)
	{
		if (orderLine.getEDI_OrdrspLine_ID() > 0)
		{
			// if the given orderLine references an ORDRSP line, that that's "the" line.
			// if not, then there is no ORDRSP line yet. So, unlike M_InOutLines and desadv line, we don't have to assign >1 inoutLine for one desadvLine, because
			// one ORDERS line => one C_OLCand => one C_OrderLine
			return orderLine.getEDI_OrdrspLine();
		}

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);

		final I_EDI_OrdrspLine ordrspLine = InterfaceWrapperHelper.newInstance(I_EDI_OrdrspLine.class, ordrsp);
		ordrspLine.setEDI_Ordrsp(ordrsp);
		ordrspLine.setIsManual(false); // this one is maintained by the system
		ordrspLine.setQuantityQualifier(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted);

		ordrspLine.setC_Tax_ID(orderLine.getC_Tax_ID());
		ordrspLine.setLine(orderLine.getLine());

		ordrspLine.setM_Product_ID(orderLine.getM_Product_ID());
		ordrspLine.setPriceActual(orderLine.getPriceActual());
		ordrspLine.setQtyEntered(orderLine.getQtyEntered());
		ordrspLine.setC_UOM_ID(orderLine.getC_UOM_ID());

		final I_M_ShipmentSchedule shipmentSched = shipmentSchedulePA.retrieveForOrderLine(orderLine);
		ordrspLine.setM_ShipmentSchedule(shipmentSched);

		updateOrdrspLineFromShipmentSchedule(ordrspLine, shipmentSched);

		final I_C_BPartner_Product bPartnerProduct = InterfaceWrapperHelper.create(
				bPartnerProductDAO.retrieveBPartnerProductAssociation(order.getC_BPartner(), orderLine.getM_Product(), orderLine.getM_Product().getAD_Org_ID()),
				I_C_BPartner_Product.class);

		if (bPartnerProduct != null)
		{
			ordrspLine.setUPC(bPartnerProduct.getUPC());
		}

		InterfaceWrapperHelper.save(ordrspLine);
		return ordrspLine;
	}

	@Override
	public void removeOrderFromOrdrsp(final I_C_Order order)
	{
		if (order == null || order.getEDI_Ordrsp_ID() <= 0)
		{
			return; // nothing to do
		}

		final I_EDI_Ordrsp ordrsp = order.getEDI_Ordrsp();

		order.setEDI_Ordrsp_ID(0);
		InterfaceWrapperHelper.save(order);

		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			if (orderLine.getEDI_OrdrspLine_ID() > 0)
			{
				InterfaceWrapperHelper.delete(orderLine.getEDI_OrdrspLine());
			}
		}

		if (!ordrspDAO.hasOrdrspLines(ordrsp)
				&& !ordrspDAO.hasOrders(ordrsp))
		{
			InterfaceWrapperHelper.delete(ordrsp);
		}
	}

	@Override
	public void setMinimumPercentage(final I_EDI_Ordrsp ordrsp)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final BigDecimal minimumPercentageAccepted = ordrspDAO.retrieveMinimumSumPercentage();

		ordrsp.setEDI_ORDRSP_MinimumSumPercentage(minimumPercentageAccepted);
	}

	@Override
	public void updateLineFromShipmentSchedule(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final I_EDI_OrdrspLine ordrspLine = ordrspDAO.retrieveUnsentOrdrspLine(shipmentSchedule);
		if (ordrspLine == null)
		{
			return;
		}
		updateOrdrspLineFromShipmentSchedule(ordrspLine, shipmentSchedule);
		InterfaceWrapperHelper.save(ordrspLine);
	}

	/**
	 * See {@link IOrdrspBL#updateLineFromShipmentSchedule(I_M_ShipmentSchedule)}.
	 *
	 * @param ordrspLine
	 * @param shipmentSched
	 */
	private void updateOrdrspLineFromShipmentSchedule(final I_EDI_OrdrspLine ordrspLine, final I_M_ShipmentSchedule shipmentSched)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final BigDecimal confirmableQty = shipmentSched.getQtyToDeliver()
				.add(shipmentSched.getQtyPickList())
				.add(shipmentSched.getQtyDelivered());

		final BigDecimal confirmedQty = uomConversionBL.convertFromProductUOM(InterfaceWrapperHelper.getCtx(shipmentSched), ordrspLine.getM_Product(), ordrspLine.getC_UOM(), confirmableQty);

		ordrspLine.setConfirmedQty(confirmedQty);
		ordrspLine.setShipDate(shipmentScheduleEffectiveBL.getPreparationDate(shipmentSched));
		ordrspLine.setDeliveryDate(shipmentScheduleEffectiveBL.getDeliveryDate(shipmentSched));
	}

	@Override
	public void updateManualLinesFromCalculatedLine(final I_EDI_OrdrspLine ordrspLine)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final List<I_EDI_OrdrspLine> manualSiblings = ordrspDAO.retrieveManualSiblings(ordrspLine);
		if (manualSiblings.isEmpty())
		{
			return; // nothing to update
		}

		BigDecimal sumConfirmedQty = ordrspLine.getConfirmedQty();
		for (final I_EDI_OrdrspLine sibling : manualSiblings)
		{
			sumConfirmedQty = sumConfirmedQty.add(sibling.getConfirmedQty());
		}

		BigDecimal overhead = sumConfirmedQty.subtract(ordrspLine.getQtyEntered());
		if (overhead.signum() <= 0)
		{
			return; // we *never* raise the Qty in the manual ordrspLines! Instead, we leave it as it is. That means that if the accepted quantity is decreased, then ORDRSPs SumPercentage also decreases and a user needs to decide what to do.
		}

		for (final I_EDI_OrdrspLine sibling : manualSiblings)
		{
			if (overhead.signum() <= 0)
			{
				break; // this loop's work is done
			}
			if (overhead.compareTo(sibling.getConfirmedQty()) >= 0)
			{
				// example 1: overhead = 8, confirmedQty = 3
				// => new confirmedQty = 0, new overhead = 5

				// example 2: overhead = 8, confirmedQty = 8
				// => new confirmedQty = 0, new overhead = 0
				overhead = overhead.subtract(sibling.getConfirmedQty()).max(BigDecimal.ZERO);
				sibling.setConfirmedQty(BigDecimal.ZERO);
			}
			else
			{
				// example overhead = 5, confirmedQty = 8
				// => new confirmedQty = 3, new overhead = 0
				sibling.setConfirmedQty(sibling.getConfirmedQty().subtract(overhead));
				overhead = BigDecimal.ZERO;
			}

			InterfaceWrapperHelper.save(sibling);
		}
	}
}
