package de.metas.bpartner.impl;

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

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import de.metas.adempiere.service.ILocationBL;
import de.metas.adempiere.service.impl.AddressBuilder;
import de.metas.bpartner.IBPartnerBL;
import de.metas.bpartner.model.BPartner;

public class BPartnerBL implements IBPartnerBL
{
	/* package */static final String SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override = "C_BPartner.SOTrx_AllowConsolidateInOut_Override";

	@Override
	public String mkFullAddress(final I_C_BPartner bPartner, final I_C_BPartner_Location location, final I_AD_User user, final String trxName)
	{
		return new AddressBuilder(bPartner.getAD_Org())
				.setLanguage(bPartner.getAD_Language())
				.buildBPartnerFullAddressString(bPartner, location, user, trxName);
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
	public boolean isAllowConsolidateInOutEffective(final BPartner partner, final boolean isSOTrx)
	{
		Check.assumeNotNull(partner, "partner not null");

		final boolean partnerAllowConsolidateInOut = partner.getBPartnerData().isAllowConsolidateInOut();
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
					allowConsolidateInOutOverrideDefault);
			return allowConsolidateInOutOverride;
		}
		return false;
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
		// bpartner.setValue(Value);
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
		if (template.isCompany())
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
	public I_C_BPartner_Location createBPLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpLocation.setAD_Org_ID(bpartner.getAD_Org_ID());
		bpLocation.setC_BPartner(bpartner);
		return bpLocation;
	}

	@Override
	public I_AD_User createContact(final I_C_BPartner bpartner)
	{
		final I_AD_User contact = InterfaceWrapperHelper.newInstance(I_AD_User.class, bpartner, true);
		contact.setAD_Org_ID(bpartner.getAD_Org_ID());
		contact.setC_BPartner(bpartner);
		contact.setName(bpartner.getName());

		return contact;
	}

	@Override
	public void setBPGroup(final I_C_BPartner bpartner, final I_C_BP_Group group)
	{
		bpartner.setC_BP_Group(group);

		if (group.getC_Dunning_ID() != 0)
			bpartner.setC_Dunning_ID(group.getC_Dunning_ID());
		if (group.getM_PriceList_ID() != 0)
			bpartner.setM_PriceList_ID(group.getM_PriceList_ID());
		if (group.getPO_PriceList_ID() != 0)
			bpartner.setPO_PriceList_ID(group.getPO_PriceList_ID());
		if (group.getM_DiscountSchema_ID() != 0)
			bpartner.setM_DiscountSchema_ID(group.getM_DiscountSchema_ID());
		if (group.getPO_DiscountSchema_ID() != 0)
			bpartner.setPO_DiscountSchema_ID(group.getPO_DiscountSchema_ID());
	}

}
