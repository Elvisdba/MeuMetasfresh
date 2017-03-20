package de.metas.fresh.setup.process;

import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MPriceList;
import org.compiere.model.X_C_BP_BankAccount;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.util.cache.CacheInterceptor;
import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.bpartner.IBPartnerBL;
import de.metas.bpartner.IBPartnerDAO;
import de.metas.bpartner.model.BPartnerAndLocationAndContact;
import de.metas.payment.esr.ESRConstants;

/*
 * #%L
 * de.metas.fresh.base
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

/**
 * AD_Client setup adapter which will provide following functionalities:
 * <ul>
 * <li>when created it will load all related database records
 * <li>when a setter is called it will set the value to all needed database records (but won't save)
 * <li>save everything: {@link #save()}
 * <li>when a getter is called, it will fetch the value directly from the loaded database record
 * </ul>
 *
 * This shall be a short living object.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class ClientSetup
{
	public static final ClientSetup newInstance(final Properties ctx)
	{
		return new ClientSetup(ctx);
	}

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final transient IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final transient IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IBankingBPBankAccountDAO bankAccountDAO = Services.get(IBankingBPBankAccountDAO.class);

	private static final int AD_Org_ID_Main = 1000000;

	// Parameters
	private final Properties _ctx;
	private final I_AD_Client adClient;
	private final I_AD_ClientInfo adClientInfo;
	private final I_AD_Org adOrg;
	private final I_AD_OrgInfo adOrgInfo;
	private final BPartnerAndLocationAndContact orgBPartner;
	private final I_C_BP_BankAccount orgBankAccount;
	private final I_C_AcctSchema acctSchema;
	private final I_M_PriceList priceList_None;

	private ClientSetup(final Properties ctx)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		_ctx = ctx;

		//
		// Load
		try (IAutoCloseable cacheFlagRestorer = CacheInterceptor.temporaryDisableCaching())
		{
			final int adClientId = Env.getAD_Client_ID(getCtx());
			adClient = clientDAO.retriveClient(getCtx(), adClientId);
			InterfaceWrapperHelper.setTrxName(adClient, ITrx.TRXNAME_ThreadInherited);
			//
			adClientInfo = clientDAO.retrieveClientInfo(getCtx(), adClient.getAD_Client_ID());
			InterfaceWrapperHelper.setTrxName(adClientInfo, ITrx.TRXNAME_ThreadInherited);
			//
			adOrg = orgDAO.retrieveOrg(getCtx(), AD_Org_ID_Main);
			InterfaceWrapperHelper.setTrxName(adOrg, ITrx.TRXNAME_ThreadInherited);
			//
			adOrgInfo = orgDAO.retrieveOrgInfo(getCtx(), adOrg.getAD_Org_ID(), ITrx.TRXNAME_ThreadInherited);
			//
			orgBPartner = partnerOrgBL.retrieveLinkedBPartnerAndAddressAndUserInCharge(adOrg);

			orgBankAccount = InterfaceWrapperHelper.create(bankAccountDAO.retrieveDefaultBankAccount(orgBPartner.getBPartnerData()), I_C_BP_BankAccount.class);
			Check.assumeNotNull(orgBankAccount, "orgBankAccount not null"); // TODO create one if does not exists
			//
			acctSchema = adClientInfo.getC_AcctSchema1();
			priceList_None = InterfaceWrapperHelper.create(getCtx(), MPriceList.M_PriceList_ID_None, I_M_PriceList.class, ITrx.TRXNAME_ThreadInherited);
		}
	}

	public void save()
	{
		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		trxManager.run(trxName, new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				saveInTrx();
			}
		});

	}

	private final void saveInTrx()
	{
		setOtherDefaults();

		InterfaceWrapperHelper.save(adClient, ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.save(adClientInfo, ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.save(adOrg, ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.save(adOrgInfo, ITrx.TRXNAME_ThreadInherited);

		bpartnerDAO.save(orgBPartner.getBpartner());

		InterfaceWrapperHelper.save(orgBankAccount, ITrx.TRXNAME_ThreadInherited);

		InterfaceWrapperHelper.save(acctSchema, ITrx.TRXNAME_ThreadInherited);

		InterfaceWrapperHelper.save(priceList_None, ITrx.TRXNAME_ThreadInherited);
	}

	private void setOtherDefaults()
	{
		//
		// AD_Org
		adOrg.setIsSummary(false);

		//
		// AD_Org Linked BPartner:
		orgBPartner.edit(bpartnerData -> {
			bpartnerData.setIsCustomer(false);
			bpartnerData.setIsVendor(false);
			bpartnerData.setIsEmployee(false);
		});

		//
		// AD_Org Linked BPartner Location:
		orgBPartner.editLocation(bpLocationData -> {
			// C_BPartner_Location - EDI
			final de.metas.edi.model.I_C_BPartner_Location ediBPartnerLocation = InterfaceWrapperHelper.create(bpLocationData, de.metas.edi.model.I_C_BPartner_Location.class);
			ediBPartnerLocation.setGLN(null); // TODO: how to set the GLN?!?
		});

		//
		// AD_Org Linked Bank Account:
		{
			orgBankAccount.setA_Name("-"); // FIXME: shall we add here the contact name?!
			orgBankAccount.setIsDefault(true);
			orgBankAccount.setBPBankAcctUse(X_C_BP_BankAccount.BPBANKACCTUSE_Both);
			orgBankAccount.setBankAccountType(X_C_BP_BankAccount.BANKACCOUNTTYPE_Girokonto);
		}

		//
		// ESR
		ESRConstants.setEnabled(getCtx(), false);

		// task FRESH-129
		// Make sure the org contact is both sales and purchase contact
		orgBPartner.editContact(contactData -> {
			contactData.setIsSalesContact(true);
			contactData.setIsPurchaseContact(true);
		});
	}

	private Properties getCtx()
	{
		return _ctx;
	}

	public ClientSetup setCompanyName(final String companyName)
	{
		if (Check.isEmpty(companyName, true))
		{
			return this;
		}

		final String companyNameNorm = companyName.trim();

		adClient.setValue(companyNameNorm);
		adClient.setName(companyNameNorm);

		adOrg.setValue(companyNameNorm);
		adOrg.setName(companyNameNorm);

		orgBPartner.edit(bpartnerData -> {
			bpartnerData.setValue(companyNameNorm);
			bpartnerData.setName(companyNameNorm);
			bpartnerData.setCompanyName(companyNameNorm);
			bpartnerData.setIsCompany(true);
		});

		return this;
	}

	public String getCompanyName()
	{
		return orgBPartner.getBPartnerData()
				.getCompanyName();
	}

	public ClientSetup setC_Currency_ID(final int currencyId)
	{
		if (currencyId <= 0)
		{
			return this;
		}

		orgBankAccount.setC_Currency_ID(currencyId);

		acctSchema.setC_Currency_ID(currencyId);
		acctSchema.setName(acctSchema.getGAAP() + " / " + acctSchema.getC_Currency().getISO_Code());

		priceList_None.setC_Currency_ID(currencyId);

		return this;
	}

	public int getC_Currency_ID()
	{
		return acctSchema.getC_Currency_ID();
	}

	public ClientSetup setAD_Language(final String adLanguage)
	{
		if (Check.isEmpty(adLanguage, true))
		{
			return this;
		}

		adClient.setAD_Language(adLanguage);

		orgBPartner.edit(bpartnerData -> bpartnerData.setAD_Language(adLanguage)); // i.e. Org Language

		return this;
	}

	public String getAD_Language()
	{
		return orgBPartner.getBPartnerData()
				.getAD_Language();
	}

	public final I_C_Location getCompanyAddress()
	{
		return getCompanyAddressIfExists().orElse(null);
	}

	private final Optional<I_C_Location> getCompanyAddressIfExists()
	{
		return orgBPartner.getAddressByIdIfExists();
	}

	public final int getCompanyAddressLocationId()
	{
		return getCompanyAddressIfExists()
				.map(address -> address.getC_Location_ID())
				.orElse(0);
	}

	public ClientSetup setCompanyAddress(final I_C_Location companyAddress)
	{
		if (companyAddress == null)
		{
			return this;
		}

		orgBPartner.editLocation(bpLocationData -> {
			// C_Location
			final I_C_Location orgLocation = bpLocationData.getC_Location();
			InterfaceWrapperHelper.copyValues(companyAddress, orgLocation);
			InterfaceWrapperHelper.save(orgLocation);

			// C_BPartner_Location
			bpLocationData.setName(orgLocation.getCity()); // To be updated on save...
			bpartnerBL.setAddress(bpLocationData); // update Address string
		});

		return this;
	}

	public ClientSetup setCompanyAddressByLocationId(final int locationId)
	{
		if (locationId <= 0)
		{
			return this;
		}

		final I_C_Location companyAddress = InterfaceWrapperHelper.create(getCtx(), locationId, I_C_Location.class, ITrx.TRXNAME_ThreadInherited);
		return setCompanyAddress(companyAddress);
	}

	public ClientSetup setCompanyLogo(final I_AD_Image companyLogo)
	{
		if (companyLogo == null || companyLogo.getAD_Image_ID() <= 0)
		{
			return this;
		}

		adClientInfo.setLogo_ID(companyLogo.getAD_Image_ID());
		adClientInfo.setLogoReport_ID(companyLogo.getAD_Image_ID());
		adClientInfo.setLogoWeb_ID(companyLogo.getAD_Image_ID());

		adOrgInfo.setLogo_ID(companyLogo.getAD_Image_ID());

		orgBPartner.edit(bpData -> bpData.setLogo_ID(companyLogo.getAD_Image_ID()));

		return this;
	}

	public ClientSetup setCompanyLogoByImageId(final int adImageId)
	{
		if (adImageId <= 0)
		{
			return this;
		}

		final I_AD_Image companyLogo = InterfaceWrapperHelper.create(getCtx(), adImageId, I_AD_Image.class, ITrx.TRXNAME_ThreadInherited);
		return setCompanyLogo(companyLogo);
	}

	public final I_AD_Image getCompanyLogo()
	{
		final I_AD_Image logo = orgBPartner.getBPartnerData().getLogo();
		if (logo == null || logo.getAD_Image_ID() <= 0)
		{
			return null;
		}

		// NOTE: even if the AD_Image is a value object, it's safe to return it as-is,
		// because the VImageDialog knows to create a new record instead of changing an existing image

		return logo;
	}

	public int getCompanyLogoImageId()
	{
		final I_AD_Image logo = getCompanyLogo();
		if (logo == null || logo.getAD_Image_ID() <= 0)
		{
			return -1;
		}
		return logo.getAD_Image_ID();
	}

	public ClientSetup setCompanyTaxID(final String companyTaxID)
	{
		if (Check.isEmpty(companyTaxID, true))
		{
			return this;
		}

		final String companyTaxID_Norm = companyTaxID.trim();
		orgBPartner.edit(bpData -> bpData.setVATaxID(companyTaxID_Norm));

		return this;
	}

	public final String getCompanyTaxID()
	{
		return orgBPartner.getBPartnerData().getVATaxID();
	}

	public String getContactFirstName()
	{
		return orgBPartner.getContactData().getFirstname();
	}

	public ClientSetup setContactFirstName(final String contactFirstName)
	{
		if (!Check.isEmpty(contactFirstName, true))
		{
			orgBPartner.editContact(contactData -> contactData.setFirstname(contactFirstName.trim()));
		}
		return this;
	}

	public String getContactLastName()
	{
		return orgBPartner.getContactData().getLastname();
	}

	public ClientSetup setContactLastName(final String contactLastName)
	{
		if (!Check.isEmpty(contactLastName, true))
		{
			orgBPartner.editContact(contactData -> contactData.setLastname(contactLastName.trim()));
		}
		return this;
	}

	public ClientSetup setAccountNo(final String accountNo)
	{
		if (!Check.isEmpty(accountNo, true))
		{
			orgBankAccount.setAccountNo(accountNo.trim());
		}
		return this;
	}

	public final String getAccountNo()
	{
		return orgBankAccount.getAccountNo();
	}

	public String getIBAN()
	{
		return orgBankAccount.getIBAN();
	}

	public ClientSetup setIBAN(final String iban)
	{
		if (!Check.isEmpty(iban, true))
		{
			orgBankAccount.setIBAN(iban.trim());
		}
		return this;
	}

	public ClientSetup setC_Bank_ID(final int bankId)
	{
		if (bankId > 0)
		{
			orgBankAccount.setC_Bank_ID(bankId);
		}
		return this;
	}

	public final int getC_Bank_ID()
	{
		return orgBankAccount.getC_Bank_ID();
	}

	public ClientSetup setPhone(final String phone)
	{
		if (!Check.isEmpty(phone, true))
		{
			// NOTE: we are not setting the Phone, Fax, EMail on C_BPartner_Location because those fields are hidden in BPartner window
			orgBPartner.editContact(contactData -> contactData.setPhone(phone.trim()));
		}

		return this;
	}

	public final String getPhone()
	{
		return orgBPartner.getContactData().getPhone();
	}

	public ClientSetup setFax(final String fax)
	{
		if (!Check.isEmpty(fax, true))
		{
			// NOTE: we are not setting the Phone, Fax, EMail on C_BPartner_Location because those fields are hidden in BPartner window
			orgBPartner.editContact(contactData -> contactData.setFax(fax.trim()));
		}
		return this;
	}

	public final String getFax()
	{
		return orgBPartner.getContactData().getFax();
	}

	public ClientSetup setEMail(final String email)
	{
		if (!Check.isEmpty(email, true))
		{
			// NOTE: we are not setting the Phone, Fax, EMail on C_BPartner_Location because those fields are hidden in BPartner window
			orgBPartner.editContact(contactData -> contactData.setEMail(email.trim()));
		}
		return this;
	}

	public final String getEMail()
	{
		return orgBPartner.getContactData().getEMail();
	}

	public ClientSetup setBPartnerDescription(final String bpartnerDescription)
	{
		if (Check.isEmpty(bpartnerDescription, true))
		{
			return this;
		}

		orgBPartner.edit(bpData -> bpData.setDescription(bpartnerDescription.trim()));

		return this;
	}

	public String getBPartnerDescription()
	{
		return orgBPartner.getBPartnerData().getDescription();
	}
}
