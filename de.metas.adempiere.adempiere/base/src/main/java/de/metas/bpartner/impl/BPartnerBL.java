package de.metas.bpartner.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.util.Env;
import org.compiere.util.Language;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import de.metas.adempiere.service.ILocationBL;
import de.metas.adempiere.service.impl.AddressBuilder;
import de.metas.bpartner.IBPartnerAware;
import de.metas.bpartner.IBPartnerBL;
import de.metas.bpartner.IBPartnerDAO;

public class BPartnerBL implements IBPartnerBL
{
	/* package */static final String SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override = "C_BPartner.SOTrx_AllowConsolidateInOut_Override";

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public String mkFullAddress(
			final I_C_BPartner bPartner,
			final I_C_BPartner_Location location,
			final I_AD_User user,
			final String trxName)
	{
		return new AddressBuilder(bPartner.getAD_Org())
				.setLanguage(bPartner.getAD_Language())
				.buildBPartnerFullAddressString(bPartner, location, user, trxName);
	}

	@Override
	public I_AD_User retrieveShipContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final I_C_BPartner_Location loc = bPartnerDAO.retrieveShipToLocation(ctx, bPartnerId, trxName);
		final int bPartnerLocationId = loc == null ? -1 : loc.getC_BPartner_Location_ID();
		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Override
	public I_AD_User retrieveShipContact(final org.compiere.model.I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int bPartnerId = bpartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final I_AD_User user = retrieveShipContact(ctx, bPartnerId, trxName);
		return user;
	}

	@Override
	public I_AD_User retrieveBillContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final org.compiere.model.I_C_BPartner_Location loc = bPartnerDAO.retrieveBillToLocation(ctx, bPartnerId, false, trxName);
		final int bPartnerLocationId = loc == null ? -1 : loc.getC_BPartner_Location_ID();
		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Override
	public I_AD_User retrieveUserForLoc(final org.compiere.model.I_C_BPartner_Location loc)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(loc);
		final int bPartnerId = loc.getC_BPartner_ID();
		final int bPartnerLocationId = loc.getC_BPartner_Location_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(loc);

		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	private I_AD_User retrieveUserForLoc(final Properties ctx, final int bPartnerId, final int bPartnerLocationId, final String trxName)
	{
		final List<I_AD_User> users = bPartnerDAO.retrieveContacts(ctx, bPartnerId, trxName);

		if (bPartnerLocationId > 0)
		{
			for (final I_AD_User user : users)
			{
				if (user.getC_BPartner_Location_ID() == bPartnerLocationId)
				{
					return user;
				}
			}
		}

		return getDefaultBPContact(users);
	}

	/**
	 * Selects the default contact from a list of BPartner users. Returns first user with IsDefaultContact=Y found or first contact.
	 *
	 * @param users
	 * @return default user/contact.
	 */
	private I_AD_User getDefaultBPContact(final List<I_AD_User> users)
	{
		if (users == null || users.isEmpty())
		{
			return null;
		}

		for (final I_AD_User user : users)
		{
			if (user.isDefaultContact())
			{
				return user;
			}
		}
		return users.get(0);
	}

	@Override
	public void setAddress(final I_C_BPartner_Location bpLocation)
	{
		final String address = Services.get(ILocationBL.class).mkAddress(bpLocation.getC_Location(),
				bpLocation.getC_BPartner(),
				"",  // bPartnerBlock
				"" // userBlock
		);

		bpLocation.setAddress(address);

	}

	@Override
	public boolean isAllowConsolidateInOutEffective(final org.compiere.model.I_C_BPartner partner, final boolean isSOTrx)
	{
		Check.assumeNotNull(partner, "partner not null");

		final boolean partnerAllowConsolidateInOut = partner.isAllowConsolidateInOut();
		if (partnerAllowConsolidateInOut)
		{
			return true;
		}

		//
		// 07973: Attempt to override SO shipment consolidation if configured
		if (isSOTrx)
		{
			final boolean allowConsolidateInOutOverrideDefault = false; // default=false (preserve existing logic)
			final boolean allowConsolidateInOutOverride = Services.get(ISysConfigBL.class).getBooleanValue(
					SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override,
					allowConsolidateInOutOverrideDefault
					);
			return allowConsolidateInOutOverride;
		}
		return false;
	}

	@Override
	public Language getLanguage(final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId > 0)
		{
			final I_C_BPartner bp = Services.get(IBPartnerDAO.class).retrieveBPartner(ctx, bpartnerId);
			if (null != bp)
			{
				final String lang = bp.getAD_Language();
				if (!Check.isEmpty(lang, true))
				{
					return Language.getLanguage(lang);
				}
			}
			return null;
		}
		return null;
	}
	
	@Override
	public I_C_BPartner getBPartnerForModel(final Object model)
	{
		// 09527 get the most suitable language:
		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IBPartnerAware.class);
		if (bpartnerAware == null)
		{
			return null;
		}
		if (bpartnerAware.getC_BPartner_ID() <= 0)
		{
			return null;
		}
		return bpartnerAware.getC_BPartner();
	}


	@Override
	public Language getLanguageForModel(final Object model)
	{
		// 09527 get the most suitable language:
		final I_C_BPartner bpartner = getBPartnerForModel(model);
		if (bpartner == null)
		{
			return null;
		}
		final String lang = bpartner.getAD_Language();
		if (Check.isEmpty(lang, true))
		{
			return null;
		}

		return Language.getLanguage(lang);
	}

	@Override
	public I_C_BPartner createFromTemplate(final I_C_BPartner_QuickInput template)
	{
		Check.assumeNotNull(template, "Parameter template is not null");
		Check.assume(!template.isProcessed(), "{} not already processed", template);
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		
		//
		// BPartner
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		bpartner.setAD_Org_ID(template.getAD_Org_ID());
		//bpartner.setValue(Value);
		bpartner.setName(extractName(template));
		bpartner.setName2(template.getName2());
		bpartner.setIsCompany(template.isCompany());
		bpartner.setCompanyName(template.getCompanyname());
		bpartner.setC_BP_Group_ID(template.getC_BP_Group_ID());
		bpartner.setAD_Language(template.getAD_Language());
		// Customer
		bpartner.setIsCustomer(template.isCustomer());
		bpartner.setC_PaymentTerm_ID(template.getC_PaymentTerm_ID());
		bpartner.setM_PricingSystem_ID(template.getM_PricingSystem_ID());
		// Vendor
		bpartner.setIsVendor(template.isVendor());
		bpartner.setPO_PaymentTerm_ID(template.getPO_PaymentTerm_ID());
		bpartner.setPO_PricingSystem_ID(template.getPO_PricingSystem_ID());
		//
		InterfaceWrapperHelper.save(bpartner);
		
		//
		// BPartner location
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpLocation.setC_BPartner(bpartner);
		bpLocation.setC_Location_ID(template.getC_Location_ID());
		bpLocation.setIsBillTo(true);
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsShipTo(true);
		bpLocation.setIsShipToDefault(true);
		InterfaceWrapperHelper.save(bpLocation);
		
		//
		// BPartner contact
		final I_AD_User bpContact = InterfaceWrapperHelper.newInstance(I_AD_User.class, bpartner);
		bpContact.setC_BPartner(bpartner);
		bpContact.setFirstname(template.getFirstname());
		bpContact.setLastname(template.getLastname());
		bpContact.setPhone(template.getPhone());
		bpContact.setEMail(template.getEMail());
		InterfaceWrapperHelper.save(bpContact);
		

		//
		// Update the template
		template.setC_BPartner(bpartner);
		template.setC_BPartner_Location(bpLocation);
		template.setAD_User(bpContact);
		template.setProcessed(true);
		InterfaceWrapperHelper.save(template);
		
		return bpartner;
	}
	
	private final String extractName(final I_C_BPartner_QuickInput template)
	{
		if(template.isCompany())
		{
			return template.getCompanyname();
		}
		else
		{
			final String firstname = Strings.emptyToNull(template.getFirstname());
			final String lastname = Strings.emptyToNull(template.getLastname());
			return Joiner.on(" ")
					.skipNulls()
					.join(firstname, lastname);
		}
	}
	
	@Override
	public int getM_PriceList_ID(final I_C_BPartner bpartner, final boolean isSOTrx)
	{
		final int priceListId = isSOTrx ? bpartner.getM_PriceList_ID() : bpartner.getPO_PriceList_ID();
		if(priceListId > 0)
		{
			return priceListId;
		}
		
		final I_C_BP_Group bpGroup = bpartner.getC_BP_Group();
		return isSOTrx ? bpGroup.getM_PriceList_ID() : bpGroup.getPO_PriceList_ID();
	}
}
