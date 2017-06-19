/**
 *
 */
package de.metas.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import javax.sql.DataSource;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ITableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.appdict.validation.model.validator.ApplicationDictionary;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.impl.AsyncBPartnerStatisticsUpdater;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.impl.AbstractInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.impl.BPartnerTreeSupport;
import org.adempiere.model.tree.spi.impl.CampainTreeSupport;
import org.adempiere.model.tree.spi.impl.MElementValueTreeSupport;
import org.adempiere.model.tree.spi.impl.MenuTreeSupport;
import org.adempiere.model.tree.spi.impl.OrgTreeSupport;
import org.adempiere.model.tree.spi.impl.ProductTreeSupport;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.scheduler.housekeeping.spi.impl.ResetSchedulerState;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.warehouse.validationrule.FilterWarehouseByDocTypeValidationRule;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.report.IJasperServiceRegistry;
import org.compiere.report.IJasperServiceRegistry.ServiceType;
import org.compiere.report.impl.JasperService;
import org.compiere.util.CCache.CacheMapType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import de.metas.adempiere.callout.C_OrderFastInputTabCallout;
import de.metas.adempiere.engine.MViewModelValidator;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.modelvalidator.AD_User;
import de.metas.adempiere.modelvalidator.C_CountryArea_Assign;
import de.metas.adempiere.modelvalidator.M_Inventory;
import de.metas.adempiere.modelvalidator.Order;
import de.metas.adempiere.modelvalidator.OrderLine;
import de.metas.adempiere.modelvalidator.OrgInfo;
import de.metas.adempiere.modelvalidator.Payment;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.document.ICounterDocBL;
import de.metas.freighcost.modelvalidator.FreightCostValidator;
import de.metas.i18n.IADMessageDAO;
import de.metas.i18n.IMsgBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.validator.M_InOut;
import de.metas.inout.model.validator.M_QualityNote;
import de.metas.inoutcandidate.modelvalidator.InOutCandidateValidator;
import de.metas.inoutcandidate.modelvalidator.ReceiptScheduleValidator;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.callout.C_InvoiceLine_TabCallout;
import de.metas.invoice.model.validator.C_Invoice;
import de.metas.invoice.model.validator.C_InvoiceLine;
import de.metas.invoice.model.validator.M_MatchInv;
import de.metas.logging.LogManager;
import de.metas.order.document.counterDoc.C_Order_CounterDocHandler;
import de.metas.request.model.validator.R_Request;
import de.metas.request.service.IRequestCreator;
import de.metas.request.service.impl.AsyncRequestCreator;
import de.metas.shipping.model.validator.M_ShipperTransportation;

/**
 * Model Validator for SWAT general features
 *
 * @author tsa
 *
 */
public class SwatValidator implements ModelValidator
{
	private static final String MSG_ORG_ADEMPIERE_UTIL_CHECK_EXCEPTION_HEADER_MESSAGE = "org.adempiere.util.Check.ExceptionHeaderMessage";

	private static final String SYSCONFIG_ORG_ADEMPIERE_UTIL_CHECK_THROW_EXCEPTION = "org.adempiere.util.Check.ThrowException";
	private static final String SYSCONFIG_C3P0 = "com.mchange.v2.c3p0.ComboPooledDataSource.";
	private static final String SYSCONFIG_C3P0_Server = "com.mchange.v2.c3p0.ComboPooledDataSource.server.";
	private static final String SYSCONFIG_C3P0_Client = "com.mchange.v2.c3p0.ComboPooledDataSource.client.";
	private static final String SYSCONFIG_C3P0_UnreturnedConnectionTimeout = SYSCONFIG_C3P0 + "UnreturnedConnectionTimeout";
	private static final String SYSCONFIG_C3P0_DebugUnreturnedConnectionStackTraces = SYSCONFIG_C3P0 + "DebugUnreturnedConnectionStackTraces";
	private static final String SYSCONFIG_C3P0_Server_MaxStatements = SYSCONFIG_C3P0_Server + "MaxStatements";
	private static final String SYSCONFIG_C3P0_Client_MaxStatements = SYSCONFIG_C3P0_Client + "MaxStatements";

	/**
	 * Default SalesRep_ID
	 *
	 * @see http://dewiki908/mediawiki/index.php/US315:_Im_Mahntext_die_neuen_Textbausteine_verwenden_k%C3%B6nnen_%282010070510000495%29#SalesRep_issue_.28Teo_09:24.2C_26._Okt._2011_.28CEST.29.29
	 */
	private static final String SYSCONFIG_DEFAULT_SalesRep_ID = "DEFAULT_SalesRep_ID";

	private final Logger log = LogManager.getLogger(getClass());

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		Check.errorUnless(Services.isAutodetectServices(), "Autodetect services is not true!");

		setupTableCacheConfig();

		configDatabase();

		//
		// Services

		// task FRESH-152: BPartner Stats Updater
		Services.registerService(IBPartnerStatisticsUpdater.class, new AsyncBPartnerStatisticsUpdater());

		// task FRESH-636: Request Creator
		Services.registerService(IRequestCreator.class, new AsyncRequestCreator());

		engine.addModelChange(I_C_InvoiceLine.Table_Name, this);
		engine.addModelChange(I_M_InOutLine.Table_Name, this);

		// registering child validators
		engine.addModelValidator(new ApplicationDictionary(), client);
		engine.addModelValidator(new FreightCostValidator(), client);

		engine.addModelValidator(new Order(), client);
		engine.addModelValidator(new OrderLine(), client);
		engine.addModelValidator(new C_Invoice(), client); // 03771
		engine.addModelValidator(new M_InOut(), client); // 03771
		engine.addModelValidator(new OrgInfo(), client);
		engine.addModelValidator(new Payment(), client);
		engine.addModelValidator(new C_InvoiceLine(), client);
		// 04359 this MV cripples the processing performance of Sales Orders
		// the MV has been added to AD_ModelValidator, so that it can be enabled for certain customers *if* required.
		// engine.addModelValidator(new PurchaseModelValidator(), client);

		engine.addModelValidator(new AD_User(), client);
		engine.addModelValidator(new MViewModelValidator(), client);
		engine.addModelValidator(new CLocationValidator(), client); // us786
		engine.addModelValidator(new C_CountryArea_Assign(), client);

		engine.addModelValidator(new InOutCandidateValidator(), client);
		engine.addModelValidator(ReceiptScheduleValidator.instance, client);
		engine.addModelValidator(new M_Warehouse(), client); // 03084
		engine.addModelValidator(new C_BPartner_Location(), client); // 02618

		engine.addModelValidator(new de.metas.allocation.modelvalidator.C_Invoice(), client); // 04193
		engine.addModelValidator(new de.metas.allocation.modelvalidator.C_Payment(), client); // 04193

		engine.addModelValidator(new M_AttributeInstance(), client); // 05839

		engine.addModelValidator(new de.metas.activity.model.validator.M_InOutLine(), client); // 06788
		engine.addModelValidator(new de.metas.activity.model.validator.C_OrderLine(), client); // 06788
		engine.addModelValidator(new de.metas.activity.model.validator.C_InvoiceLine(), client); // 06788

		engine.addModelValidator(new de.metas.picking.modelvalidator.M_PickingSlot(), client); // 06178

		engine.addModelValidator(new M_ShipperTransportation(), client); // 06899
		
		//task #1064
		engine.addModelValidator(new M_Inventory(), client);

		// task 09700
		final IModelInterceptor counterDocHandlerInterceptor = Services.get(ICounterDocBL.class).registerHandler(C_Order_CounterDocHandler.instance, I_C_Order.Table_Name);
		engine.addModelValidator(counterDocHandlerInterceptor, null);

		// pricing
		{
			engine.addModelValidator(new de.metas.pricing.modelvalidator.M_ProductPrice(), client); // 06931
		}

		// #361: Request
		engine.addModelValidator(new R_Request(), client);

		// #548: QualityNote
		engine.addModelValidator(new M_QualityNote(), client);

		// AD_Tree UI support
		{
			final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
			treeSupportFactory.register(I_C_BPartner.Table_Name, BPartnerTreeSupport.class);
			treeSupportFactory.register(I_C_Campaign.Table_Name, CampainTreeSupport.class);
			treeSupportFactory.register(I_C_ElementValue.Table_Name, MElementValueTreeSupport.class);
			treeSupportFactory.register(I_AD_Menu.Table_Name, MenuTreeSupport.class);
			treeSupportFactory.register(I_AD_Org.Table_Name, OrgTreeSupport.class);
			treeSupportFactory.register(I_M_Product.Table_Name, ProductTreeSupport.class);
		}

		// Note: de.metas.adempiere.modelvalidator.InvoiceLine is currently deactivated, so we leave it in
		// AD_ModelValidator until its status is clear.

		new de.metas.order.model.validator.ConfigValidator().initialize(engine, client);

		new de.metas.invoicecandidate.modelvalidator.ConfigValidator().initialize(engine, client);

		JRClient.get(); // make sure Jasper client is loaded and initialized

		Services.get(IValidationRuleFactory.class).registerTableValidationRule(I_M_Warehouse.Table_Name, FilterWarehouseByDocTypeValidationRule.instance);

		// task 06295: those two are implemented in de.metas.adempiere.adempiere, but we don't have such a nice central MV in there.
		Services.get(IHouseKeepingBL.class).registerStartupHouseKeepingTask(new ResetSchedulerState());
		// not registering this one for because is might lead to problems if a swing-client is running while the server is starting up.
		// Services.get(IHouseKeepingBL.class).registerStartupHouseKeepingTask(new ClearTemporaryTables());

		//
		// Configure tables which are skipped when we record migration scripts
		{
			final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
			migrationLogger.addTableToIgnoreList(I_EXP_ReplicationTrx.Table_Name);
			migrationLogger.addTableToIgnoreList(I_EXP_ReplicationTrxLine.Table_Name);
		}

		//
		engine.addModelValidator(new de.metas.tourplanning.model.validator.TourPlanningModuleActivator(), client);

		// de.metas.invoice submodule
		{
			engine.addModelValidator(new M_MatchInv(), client);
		}

		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_C_InvoiceLine.Table_Name, C_InvoiceLine_TabCallout.class);

		// register the default copy handler
		// task 07286: required because we need to introduce copy handlers that replace some former jboss-aop aspects in de.metas.commission. We do that be adding copy handlers there and to allow
		// this, we had to extend the API and register the default handlers whose invocation used to be hardcoded in the IInvoiceBL impl.
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		invoiceBL.registerCopyHandler(
				new IQueryFilter<Pair<I_C_Invoice, I_C_Invoice>>()
				{
					@Override
					public boolean accept(Pair<I_C_Invoice, I_C_Invoice> model)
					{
						return true;
					}
				},
				AbstractInvoiceBL.defaultDocCopyHandler);
		invoiceBL.registerLineCopyHandler(
				new IQueryFilter<Pair<org.compiere.model.I_C_InvoiceLine, org.compiere.model.I_C_InvoiceLine>>()
				{
					@Override
					public boolean accept(Pair<org.compiere.model.I_C_InvoiceLine, org.compiere.model.I_C_InvoiceLine> model)
					{
						return true;
					}
				},
				AbstractInvoiceBL.defaultDocCopyHandler.getDocLineCopyHandler());

		// 07466: adding an option to just log failed Checks, and to add a message which puts them into context for the user
		{
			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final IADMessageDAO msgDAO = Services.get(IADMessageDAO.class);

			final boolean throwException = sysConfigBL.getBooleanValue(SYSCONFIG_ORG_ADEMPIERE_UTIL_CHECK_THROW_EXCEPTION, true);
			Check.setThrowException(throwException);
			if (!throwException)
			{
				Check.setLogger(LogManager.getLogger(Check.class));
			}
			else if (msgDAO.isMessageExists(MSG_ORG_ADEMPIERE_UTIL_CHECK_EXCEPTION_HEADER_MESSAGE))
			{
				Check.setExceptionHeaderMessage(msgBL.getMsg(Env.getCtx(), MSG_ORG_ADEMPIERE_UTIL_CHECK_EXCEPTION_HEADER_MESSAGE));
			}
		}

		// 08284: register our default old-school service for all service types.
		// we expect the printing module to register the real MASS_PRINT_FRAMEWORK service and replace JasperService for that service type
		{
			final IJasperServiceRegistry jasperServiceRegistry = Services.get(IJasperServiceRegistry.class);
			jasperServiceRegistry.registerJasperService(ServiceType.DIRECT_PRINT_FRAMEWORK, new JasperService());
			if (!jasperServiceRegistry.isRegisteredServiceFor(ServiceType.MASS_PRINT_FRAMEWORK))
			{
				// fallback
				jasperServiceRegistry.registerJasperService(ServiceType.MASS_PRINT_FRAMEWORK, new JasperService());
			}
		}

		// task 09232
		{
			Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_C_Order.Table_Name, C_OrderFastInputTabCallout.class);
		}
	}

	private void setupTableCacheConfig()
	{
		final IModelCacheService cachingService = Services.get(IModelCacheService.class);

		cachingService.addTableCacheConfigIfAbsent(I_M_Attribute.class);
		cachingService.addTableCacheConfigIfAbsent(I_M_Product.class);
		cachingService.addTableCacheConfigIfAbsent(I_C_UOM.class);
		cachingService.addTableCacheConfigIfAbsent(I_M_Warehouse.class);
		cachingService.addTableCacheConfigIfAbsent(I_M_Locator.class);

		// Cache C_BPartner
		// NOTE: because we have a lot of them, we will cache only latest 50, using LRU map,
		// InTransactionOnly, because don't have a distributed cache invalidation..
		cachingService.createTableCacheConfigBuilder(I_C_BPartner.Table_Name)
				.setEnabled(true)
				.setTrxLevel(TrxLevel.InTransactionOnly)
				.setCacheMapType(CacheMapType.LRU)
				.setExpireMinutes(ITableCacheConfig.EXPIREMINUTES_Never)
				.setInitialCapacity(50)
				.setMaxCapacity(50)
				.register();
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		if(Ini.isClient())
		{
			configDatabase(); // run it again here because ModelValidator.initialize is run only once
		}

		final Properties ctx = Env.getCtx();

		//
		// us315 - set default SalesRep_ID
		int defaultSalesRepId = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DEFAULT_SalesRep_ID, -1, Env.getAD_Client_ID(ctx), AD_Org_ID);
		if (defaultSalesRepId > 0)
		{
			log.info("Set " + Env.CTXNAME_SalesRep_ID + "=" + defaultSalesRepId + " from " + SYSCONFIG_DEFAULT_SalesRep_ID);
			Env.setContext(ctx, Env.CTXNAME_SalesRep_ID, defaultSalesRepId);
		}

		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (I_C_InvoiceLine.Table_Name.equals(po.get_TableName()) && TYPE_BEFORE_NEW == type)
		{
			I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);
			if (invoiceLine.getC_OrderLine_ID() > 0)
			{
				I_C_OrderLine orderLine = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), I_C_OrderLine.class);
				invoiceLine.setProductDescription(orderLine.getProductDescription());
			}
		}
		if (I_M_InOutLine.Table_Name.equals(po.get_TableName()) && TYPE_BEFORE_NEW == type)
		{
			I_M_InOutLine ioLine = InterfaceWrapperHelper.create(po, I_M_InOutLine.class);
			if (ioLine.getC_OrderLine_ID() > 0)
			{
				I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ioLine.getC_OrderLine(), I_C_OrderLine.class);
				ioLine.setProductDescription(orderLine.getProductDescription());
			}
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

	private void configDatabase()
	{
		//
		// Get configuration from SysConfigs
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int unreturnedConnectionTimeout = sysConfigBL.getIntValue(SYSCONFIG_C3P0_UnreturnedConnectionTimeout, 0);
		final boolean debugUnreturnedConnectionStackTraces = sysConfigBL.getBooleanValue(SYSCONFIG_C3P0_DebugUnreturnedConnectionStackTraces, false);

		final String maxStatementsSysConfig;
		if (Ini.isClient())
		{
			maxStatementsSysConfig = SYSCONFIG_C3P0_Client_MaxStatements;
		}
		else
		{
			maxStatementsSysConfig = SYSCONFIG_C3P0_Server_MaxStatements;
		}

		final int maxStatementsValue = sysConfigBL.getIntValue(maxStatementsSysConfig, 0);

		final CConnection cc = CConnection.get();
		DataSource ds = cc.getDataSource();

		//
		// On server side, the cc.getDataSource() always return null,
		// so we need to take the DataSource from it's original place
		if (ds == null)
		{
			ds = cc.getDatabase().getDataSource(cc);
		}

		if (ds instanceof ComboPooledDataSource)
		{
			ComboPooledDataSource cpds = (ComboPooledDataSource)ds;

			if (unreturnedConnectionTimeout > 0)
			{
				final int old = cpds.getUnreturnedConnectionTimeout();
				cpds.setUnreturnedConnectionTimeout(unreturnedConnectionTimeout);
				log.info("Config " + SYSCONFIG_C3P0_UnreturnedConnectionTimeout + "=" + unreturnedConnectionTimeout + " (Old: " + old + ")");
			}

			{
				final boolean old = cpds.isDebugUnreturnedConnectionStackTraces();
				cpds.setDebugUnreturnedConnectionStackTraces(debugUnreturnedConnectionStackTraces);
				log.info("Config " + SYSCONFIG_C3P0_DebugUnreturnedConnectionStackTraces + "=" + debugUnreturnedConnectionStackTraces + " (Old: " + old + ")");
			}

			{
				final int old = cpds.getMaxStatements();
				cpds.setMaxStatements(maxStatementsValue);
				log.info("Config " + maxStatementsSysConfig + "=" + maxStatementsValue + " (Old: " + old + ")");
			}
		}
		else
		{
			log.warn("Can not configure datasource because is not an instance of ComboPooledDataSource: " + ds);
		}
	}
}
