package org.adempiere.bpartner.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_BPartner;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_C_BPartner.class)
@Callout(I_C_BPartner.class)
public class C_BPartner
{
	@Init
	public void init()
	{
		Services.get(ITabCalloutFactory.class)
				.registerTabCalloutForTable(I_C_BPartner.Table_Name, org.adempiere.bpartner.callout.C_BPartner_TabCallout.class);

		Services.get(IProgramaticCalloutProvider.class)
				.registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsCompany, I_C_BPartner.COLUMNNAME_CompanyName })
	public void updateNameFromCompanyName(final I_C_BPartner bpartner)
	{
		final boolean failIfNotValid = true;
		updateNameFromCompanyName(bpartner, failIfNotValid);
	}

	@CalloutMethod(columnNames = { I_C_BPartner.COLUMNNAME_IsCompany, I_C_BPartner.COLUMNNAME_CompanyName })
	public void onCompanyChanged(final I_C_BPartner bpartner)
	{
		final boolean failIfNotValid = false;
		updateNameFromCompanyName(bpartner, failIfNotValid);
	}

	private void updateNameFromCompanyName(final I_C_BPartner bpartner, final boolean failIfNotValid)
	{
		if (bpartner.isCompany())
		{
			String companyName = bpartner.getCompanyName();
			if (Check.isEmpty(companyName, true))
			{
				if (!failIfNotValid)
				{
					return;
				}
				throw new FillMandatoryException(I_C_BPartner.COLUMNNAME_CompanyName);
			}

			bpartner.setName(companyName);
		}
		else
		{
			// c.ghita@metas.ro : start : US321
			final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bpartner, I_AD_User.class);
			if (defaultContact != null)
			{
				bpartner.setName(defaultContact.getName());
			}
		}

	}
}
