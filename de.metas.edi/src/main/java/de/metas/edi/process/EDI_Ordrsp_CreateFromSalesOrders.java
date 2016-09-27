package de.metas.edi.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.GridTab;
import org.compiere.model.IQuery;
import org.compiere.process.SvrProcess;

import de.metas.edi.api.IOrdrspBL;
import de.metas.edi.model.I_C_Order;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.process.Param;

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
/**
 * Iterates {@link I_C_Order}s and creates {@link I_EDI_Ordrsp} for them, if they don't have, but should have one.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EDI_Ordrsp_CreateFromSalesOrders
		extends SvrProcess
		implements ISvrProcessPrecondition
{

	/**
	 * If false, then the current selection is processed.
	 *
	 * By default, the value is <code>true</code> to make it harder to accidentally create a large number of records if that is not wanted.
	 */
	@Param(parameterName = "IsOnlyCurrentRecord")
	private boolean onlyCurrentRecord = true;

	private final transient IOrdrspBL ordrspBL = Services.get(IOrdrspBL.class);

	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final transient ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Order> orders = createIterator();

		final Integer countCreated = trxItemProcessorExecutorService.<I_C_Order, Integer> createExecutor()
				.setContext(getCtx(), getTrxName())
				.setItemsPerBatch(1) // this is probably not needed because we don't have a chunk processor
				.setUseTrxSavepoints(false) // optimization: don't use trx savepoints because they are expensive and do not help us here
				.setExceptionHandler(LoggableTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_C_Order, Integer>()
				{
					private int counter = 0;

					@Override
					public void process(I_C_Order order) throws Exception
					{
						final I_EDI_Ordrsp ordrsp = ordrspBL.addToOrdrspCreateIfNotExistForOrder(order);
						if (ordrsp != null)
						{
							InterfaceWrapperHelper.save(order);
							ILoggable.THREADLOCAL.getLoggable().addLog("Created EDI_Ordrsp {} for C_Order {}", ordrsp, order);
							counter++;
						}
						else
						{
							ILoggable.THREADLOCAL.getLoggable().addLog("Created *no* EDI_Ordrsp for C_Order {}", order);
						}
					}

					@Override
					public Integer getResult()
					{
						return counter;
					}
				})
				.process(orders);

		return "@Created@ " + countCreated + " @EDI_Ordrsp@";
	}

	private Iterator<I_C_Order> createIterator()
	{
		if (onlyCurrentRecord)
		{
			final I_C_Order order = getRecord(I_C_Order.class);
			return IteratorUtils.singletonIterator(order);
		}

		final Iterator<I_C_Order> orders = queryBL.createQueryBuilder(I_C_Order.class, this)
				// general filters
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_EDI_Ordrsp_ID, null)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, true)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsEdiEnabled, true)

		// only process what was selected
				.filter(getProcessInfo().getQueryFilter())

		// order them to make it all more predictable
				.orderBy().addColumn(I_C_Order.COLUMNNAME_C_Order_ID).endOrderBy()
				.create()

		.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_Order.class);

		return orders;
	}

	/**
	 * @return <code>true</code> if the given <code>gridTab</code> is about a sales order.
	 */
	@Override
	public boolean isPreconditionApplicable(GridTab gridTab)
	{
		if (!I_C_Order.Table_Name.equals(gridTab.getTableName()))
		{
			return false;
		}
		return gridTab.getGridWindow().isSOTrx();
	}
}
