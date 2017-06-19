package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IInOutCandHandlerBL;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IInOutCandHandler;
import de.metas.inoutcandidate.spi.IInOutCandHandlerListener;
import de.metas.inoutcandidate.spi.IInOutCandHandlerListener.OnMissingCandidate;

public class InOutCandHandlerBL implements IInOutCandHandlerBL
{
	private static final String MSG_RECORDS_CREATED_1P = "de.metas.inoutCandidate.RECORDS_CREATED";
	private static final String MSG_RECORD_CREATION_VETOED_1P = "de.metas.inoutCandidate.RECORD_CREATION_VETOED";

	private final static Logger logger = LogManager.getLogger(InOutCandHandlerBL.class);

	private final Map<String, IInOutCandHandler> tableName2Handler = new HashMap<String, IInOutCandHandler>();

	private final Map<String, List<IInOutCandHandlerListener>> tableName2Listeners = new HashMap<String, List<IInOutCandHandlerListener>>();

	@Override
	public void registerHandler(final Properties ctx, final IInOutCandHandler handler)
	{
		// do the actual registering
		final IInOutCandHandler oldImpl = tableName2Handler.put(handler.getSourceTable(), handler);
		Check.assume(oldImpl == null, "There is only one attempt to register a handler for table '" + handler.getSourceTable() + "'");

		// make sure that there is also a list of listeners (albeit empty) for the handler's source table
		if (!tableName2Listeners.containsKey(handler.getSourceTable()))
		{
			tableName2Listeners.put(handler.getSourceTable(), new ArrayList<IInOutCandHandlerListener>());
		}

		// make sure that there is an M_IolCandHandler record for the handler
		final I_M_IolCandHandler existingRecord = retrieveHandlerRecordOrNull(ctx, handler.getClass().getName(), null);
		if (existingRecord != null)
		{
			return; // nothing to do
		}

		// create a handler record
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final I_M_IolCandHandler handlerRecord = InterfaceWrapperHelper.create(ctx, I_M_IolCandHandler.class, localTrxName);
				handlerRecord.setClassname(handler.getClass().getName());
				handlerRecord.setAD_Org_ID(0);
				handlerRecord.setTableName(handler.getSourceTable());

				InterfaceWrapperHelper.save(handlerRecord);

			}
		});
	}

	@Override
	@Cached
	public I_M_IolCandHandler retrieveHandlerRecordOrNull(
			final @CacheCtx Properties ctx,
			final String className,
			final @CacheTrx String trxName)
	{
		final String wc = I_M_IolCandHandler.COLUMNNAME_Classname + "=?";

		return new Query(ctx, I_M_IolCandHandler.Table_Name, wc, trxName)
				.setParameters(className)
				.setOnlyActiveRecords(true)
				.setApplyAccessFilter(true)
				.firstOnly(I_M_IolCandHandler.class);
	}

	@Override
	public void registerListener(final IInOutCandHandlerListener l, final String tableName)
	{
		List<IInOutCandHandlerListener> listeners = tableName2Listeners.get(tableName);
		if (listeners == null)
		{
			listeners = new ArrayList<IInOutCandHandlerListener>();
			tableName2Listeners.put(tableName, listeners);
		}
		listeners.add(l);
	}

	@Override
	public List<I_M_ShipmentSchedule> createMissingCandidates(final Properties ctx, final String trxName)
	{
		Check.errorIf(Check.isEmpty(trxName), "Param 'trxName' may not be empty");
		Check.errorIf(trxName.startsWith("POSave"), "Param 'trxName'={} may not start with 'POSave'", trxName);

		final List<I_M_ShipmentSchedule> result = new ArrayList<I_M_ShipmentSchedule>();

		for (final String tableName : tableName2Handler.keySet())
		{
			final IInOutCandHandler handler = tableName2Handler.get(tableName);
			final I_M_IolCandHandler handlerRecord = retrieveHandlerRecordOrNull(ctx, handler.getClass().getName(), trxName);

			final List<Object> missingCandidateModels = handler.retrieveModelsWithMissingCandidates(ctx, trxName);

			for (final Object model : missingCandidateModels)
			{
				final List<IInOutCandHandlerListener> vetoListeners = new ArrayList<IInOutCandHandlerListener>();

				for (final IInOutCandHandlerListener l : tableName2Listeners.get(tableName))
				{
					final OnMissingCandidate listenerResult = l.foundModelWithoutInOutCandidate(model, handler);
					if (listenerResult == OnMissingCandidate.SKIP_CREATION)
					{
						logger.debug("IInOutCandHandlerListener " + l + " doesn't want us to create a shipment schedule");
						vetoListeners.add(l);
					}
				}

				final IMsgBL msgBL = Services.get(IMsgBL.class);
				if (vetoListeners.isEmpty())
				{
					// There was no listener forbidding us to create one or more I_M_ShipmentSchedules for 'model'
					final List<I_M_ShipmentSchedule> candidatesForModel = handler.createCandidatesFor(model);

					for (final I_M_ShipmentSchedule newSched : candidatesForModel)
					{
						newSched.setM_IolCandHandler_ID(handlerRecord.getM_IolCandHandler_ID());
						InterfaceWrapperHelper.save(newSched);
					}

					result.addAll(candidatesForModel);

					saveHandlerLog(handlerRecord, model,
							msgBL.getMsg(ctx, MSG_RECORDS_CREATED_1P,
									new Object[] {
											candidatesForModel.size(),
											msgBL.translate(ctx, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID) }));
				}
				else
				{
					final StringBuilder vetoNames = new StringBuilder();
					for (final IInOutCandHandlerListener vetoListener : vetoListeners)
					{
						if (vetoNames.length() > 0)
						{
							vetoNames.append(", ");
						}
						vetoNames.append(vetoListener.getClass().getName());
					}

					saveHandlerLog(handlerRecord, model,
							msgBL.getMsg(ctx, MSG_RECORD_CREATION_VETOED_1P,
									new Object[] {
											msgBL.translate(ctx, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID),
											vetoNames }));
				}
			}
		}
		return result;
	}

	@Override
	public void invalidateCandidatesFor(final Object model, final String tableName)
	{
		final IInOutCandHandler handler = tableName2Handler.get(tableName);
		if (handler == null)
		{
			return;
		}

		handler.invalidateCandidatesFor(model);
	}

	private void saveHandlerLog(final I_M_IolCandHandler handlerRecord, final Object referencedModel, final String status)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(handlerRecord);
		final String trxName = InterfaceWrapperHelper.getTrxName(handlerRecord);

		final I_M_IolCandHandler_Log logRecord = InterfaceWrapperHelper.create(ctx, I_M_IolCandHandler_Log.class, trxName);

		logRecord.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(handlerRecord.getTableName()));
		logRecord.setRecord_ID(InterfaceWrapperHelper.getId(referencedModel));
		logRecord.setM_IolCandHandler_ID(handlerRecord.getM_IolCandHandler_ID());
		logRecord.setStatus(status);

		InterfaceWrapperHelper.save(logRecord);
	}

	@Override
	public IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final String tableName = adTableDAO.retrieveTableName(sched.getAD_Table_ID());

		final IInOutCandHandler inOutCandHandler = tableName2Handler.get(tableName);
		Check.assumeNotNull(inOutCandHandler, "IInOutCandHandler for {} with table name {} is not null", sched, tableName);

		return inOutCandHandler.createDeliverRequest(sched);
	}
}
