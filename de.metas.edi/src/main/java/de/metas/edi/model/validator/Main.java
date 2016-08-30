package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_AD_Client;
import org.compiere.util.CacheMgt;

import de.metas.document.ICopyHandlerBL;
import de.metas.edi.spi.impl.EdiInvoiceCandidateListener;
import de.metas.edi.spi.impl.EdiInvoiceCopyHandler;
import de.metas.esb.edi.model.I_EDI_BPartner_Config;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;

public class Main extends AbstractModuleInterceptor
{

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new C_Invoice(), client);
		engine.addModelValidator(new C_BPartner(), client);
		engine.addModelValidator(C_Order.INSTANCE, client);
		engine.addModelValidator(C_OrderLine.INSTANCE, client);
		engine.addModelValidator(new C_OLCand(), client);

		engine.addModelValidator(EDI_Ordrsp.INSTANCE, client);
		engine.addModelValidator(EDI_OrdrspLine.INSTANCE, client);

		engine.addModelValidator(EDI_Desadv.INSTANCE, client);
		engine.addModelValidator(EDI_DesadvLine.INSTANCE, client);

		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(M_InOutLine.INSTANCE, client);

		engine.addModelValidator(M_ShipmentSchedule.INSTANCE, client);

		engine.addModelValidator(new C_Invoice_Candidate(), client);
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		final CacheMgt cacheMgt = CacheMgt.get();

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_EDI_BPartner_Config.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_EDI_Ordrsp.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_EDI_OrdrspLine.Table_Name);
	}

	@Override
	public void onAfterInit()
	{
		// task 08926
		// invoice candidate listener
		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(EdiInvoiceCandidateListener.instance);

		// task 08926
		// invoice copy handler
		Services.get(ICopyHandlerBL.class).registerCopyHandler(
				org.compiere.model.I_C_Invoice.class,
				new IQueryFilter<IPair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice>>()
				{
					@Override
					public boolean accept(IPair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice> model)
					{
						return true;
					}
				},
				new EdiInvoiceCopyHandler());
	}
}
