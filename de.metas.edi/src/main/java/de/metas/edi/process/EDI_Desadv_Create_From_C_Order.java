package de.metas.edi.process;

import java.util.List;
import java.util.ListIterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.GridTab;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;

import de.metas.document.engine.IDocActionBL;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;

/**
 * Allows a user to create a DESADV for a C_Order in case someone forgot to set the IsEDIEnabled flag or if the DESADV is missing for any other reason.
 * <p>
 * IMPORTANT: see {@link #isPreconditionApplicable(GridTab)} for the current scope of this process.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/358
 *
 */
public class EDI_Desadv_Create_From_C_Order
		extends SvrProcess
		implements ISvrProcessPrecondition
{
	@Override
	protected final String doIt() throws Exception
	{

		final I_C_Order order = getRecord(I_C_Order.class);
		final ListIterator<I_C_Order> orders = IteratorUtils.singletonListIterator(order);

		final ITrxItemProcessor<I_C_Order, Integer> processor = mkProcessor();

		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(getCtx(), ITrx.TRX_None);

		final Integer count = executorService.createExecutor(processorCtx, processor)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.execute(orders);

		return "@Added@: " + count + " @C_Order_ID@";
	}

	private ITrxItemProcessor<I_C_Order, Integer> mkProcessor()
	{
		return new TrxItemProcessorAdapter<I_C_Order, Integer>()
		{
			private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);
			private int counter = 0;

			@Override
			public void process(final I_C_Order order) throws Exception
			{
				final I_EDI_Desadv desadv = desadvBL.addToDesadvCreateForOrderIfNotExist(order);
				if (desadv == null)
				{
					ILoggable.THREADLOCAL.getLoggable().addLog("Could not create desadv for C_Order_ID=" + order.getDocumentNo());
				}
				else
				{
					InterfaceWrapperHelper.save(order);
					ILoggable.THREADLOCAL.getLoggable().addLog("@Added@: @C_Order_ID@ " + order.getDocumentNo());
					counter++;
				}

				//
				// also check if we can add existing M_InOuts
				final List<I_M_InOut> inOuts = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOut.class, order)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_M_InOut.COLUMNNAME_IsEdiEnabled, true)
						.addEqualsFilter(I_M_InOut.COLUMNNAME_EDI_Desadv_ID, null)
						.addEqualsFilter(I_M_InOut.COLUMNNAME_POReference, order.getPOReference())
						.addEqualsFilter(I_M_InOut.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
						.addInArrayFilter(de.metas.inout.model.I_M_InOut.COLUMNNAME_DocStatus, DocAction.STATUS_Completed,  DocAction.STATUS_Closed)
						.create()
						.list();
				for (final I_M_InOut inOut : inOuts)
				{
					final I_EDI_Desadv desadvforInOut = desadvBL.addToDesadvCreateForInOutIfNotExist(inOut);
					if (desadvforInOut == null)
					{
						ILoggable.THREADLOCAL.getLoggable().addLog("Could not add M_InOut_ID=" + inOut.getDocumentNo() + " to the desadv");
					}
					else
					{
						InterfaceWrapperHelper.save(order);
						ILoggable.THREADLOCAL.getLoggable().addLog("@Added@: @M_InOut_ID@ " + inOut.getDocumentNo());
					}
				}

			}

			@Override
			public Integer getResult()
			{
				return counter;
			}
		};
	}

	@Override
	public boolean isPreconditionApplicable(GridTab gridTab)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);

		return order.isSOTrx()
				&& order.isEdiEnabled()
				&& order.getEDI_Desadv_ID() <= 0
				&& Services.get(IDocActionBL.class).isStatusCompletedOrClosed(order);
	}
}
