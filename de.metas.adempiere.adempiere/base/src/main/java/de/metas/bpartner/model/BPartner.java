package de.metas.bpartner.model;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.bpartner.BPartnerContacts;
import de.metas.bpartner.BPartnerLocations;
import de.metas.bpartner.IBPartnerDAO;
import de.metas.logging.LogManager;
import lombok.ToString;

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

/**
 * BPartner (aggregate)
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public class BPartner
{
	/**
	 * Creates a BPartner which consists only by given bpartnerName.
	 * Used mainly for testing.
	 */
	public static BPartner strict(final I_C_BPartner bpartnerData)
	{
		Supplier<BPartnerLocations> bpartnerLocations = () -> new BPartnerLocations(bpartnerData.getC_BPartner_ID(), ImmutableList.of());
		Supplier<BPartnerContacts> contacts = () -> new BPartnerContacts(bpartnerData.getC_BPartner_ID(), ImmutableList.of());
		return new BPartner(bpartnerData, bpartnerLocations, contacts);
	}

	private static final transient Logger logger = LogManager.getLogger(BPartner.class);

	private final I_C_BPartner bpartnerData;
	private final Supplier<BPartnerLocations> locations;
	private final Supplier<BPartnerContacts> contacts;

	public BPartner(final I_C_BPartner bpartnerData //
			, final Supplier<BPartnerLocations> bpartnerLocations //
			, final Supplier<BPartnerContacts> contacts //
	)
	{
		this.bpartnerData = bpartnerData;
		locations = Suppliers.memoize(bpartnerLocations);
		this.contacts = Suppliers.memoize(contacts);
	}

	public BPartnerAndLocationAndContact toBPartnerAndLocationAndContact(final int bpLocationId, final int contactId)
	{
		return new BPartnerAndLocationAndContact(this, bpLocationId, contactId);
	}

	public int getBPartnerId()
	{
		return bpartnerData.getC_BPartner_ID();
	}
	
	public String getName()
	{
		return getBPartnerData().getName();
	}
	
	public String getAD_Language()
	{
		return getBPartnerData().getAD_Language();
	}

	public Language getLanguage()
	{
		final String lang = getAD_Language();
		if (!Check.isEmpty(lang, true))
		{
			return Language.getLanguage(lang);
		}

		return null;
	}

	/** @return readonly BPartner data object */
	public I_C_BPartner getBPartnerData()
	{
		return bpartnerData;
	}

	public BPartnerLocations getBPartnerLocations()
	{
		return locations.get();
	}

	public void edit(final Consumer<I_C_BPartner> consumer)
	{
		consumer.accept(bpartnerData);
	}

	public void editLocationById(final int bpartnerLocationId, final Consumer<I_C_BPartner_Location> consumer)
	{
		getBPartnerLocations().editById(bpartnerLocationId, consumer);
	}

	public BPartnerContacts getContacts()
	{
		return contacts.get();
	}

	public int getSalesRep_ID()
	{
		return getBPartnerData().getSalesRep_ID();
	}

	/**
	 * @return contact assigned to default/first ship to address. If no contact found, the first default contact will be returned.
	 */
	public Optional<I_AD_User> getShipContactData()
	{
		final I_C_BPartner_Location bpLocationData = getBPartnerLocations().getShipToOrFirst();
		final int bpLocationId = bpLocationData == null ? -1 : bpLocationData.getC_BPartner_Location_ID();
		return getContacts().getByLocationIdOrDefault(bpLocationId);
	}

	/**
	 * @return contact assigned to default/first billTo to address. If no contact found, the first default contact will be returned.
	 */
	public Optional<I_AD_User> getBillContactData()
	{
		final I_C_BPartner_Location bpLocationData = getBPartnerLocations().getBillTo(false); // alsoTryBilltoRelation=false
		final int bpLocationId = bpLocationData == null ? -1 : bpLocationData.getC_BPartner_Location_ID();
		return getContacts().getByLocationIdOrDefault(bpLocationId);
	}

	public void editContactById(final int contactId, final Consumer<I_AD_User> consumer)
	{
		getContacts().editById(contactId, consumer);
	}

	public int getM_PricingSystem_ID(final boolean soTrx)
	{
		final I_C_BPartner bpartnerData = getBPartnerData();
		// try to set the pricing system from BPartner

		// metas: The method always retrieved SO-PricingSys. This caused errors in PO-Documents.
		final Integer bpPricingSysId;

		if (soTrx)
		{
			bpPricingSysId = bpartnerData.getM_PricingSystem_ID();
		}
		else
		{
			bpPricingSysId = bpartnerData.getPO_PricingSystem_ID();
		}
		// metas: end
		if (bpPricingSysId != null && bpPricingSysId > 0)
		{
			logger.debug("Got M_PricingSystem_ID={} from bPartner={}", bpPricingSysId, bpartnerData);
			return bpPricingSysId;
		}

		final I_C_BP_Group bpGroup = bpartnerData.getC_BP_Group();
		if (bpGroup != null)
		{
			final Integer bpGroupPricingSysId;

			// metas: Same problem as above: The method always retrieved SO-PricingSys. This caused errors in
			// PO-Documents.
			if (soTrx)
			{
				bpGroupPricingSysId = bpGroup.getM_PricingSystem_ID();
			}
			else
			{
				bpGroupPricingSysId = bpGroup.getPO_PricingSystem_ID();
			}
			// metas: end
			if (bpGroupPricingSysId != null && bpGroupPricingSysId > 0)
			{
				logger.debug("Got M_PricingSystem_ID={} from bpGroup={}", bpGroupPricingSysId, bpGroup);
				return bpGroupPricingSysId;
			}
		}

		final int adOrgId = bpartnerData.getAD_Org_ID();
		if (adOrgId > 0 && soTrx)
		{
			// TODO: avoid calling DAO here
			final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(Services.get(IOrgDAO.class).retrieveOrgInfo(Env.getCtx(), adOrgId, ITrx.TRXNAME_None), I_AD_OrgInfo.class);
			if (orgInfo.getM_PricingSystem_ID() > 0)
			{
				return orgInfo.getM_PricingSystem_ID();
			}
		}

		logger.warn("bPartner={} has no pricing system id (soTrx={}); returning 0", bpartnerData, soTrx);
		return 0;
	}

	public int getM_PriceList_ID(final boolean isSOTrx)
	{
		final I_C_BPartner bpartnerData = getBPartnerData();
		final int priceListId = isSOTrx ? bpartnerData.getM_PriceList_ID() : bpartnerData.getPO_PriceList_ID();
		if (priceListId > 0)
		{
			return priceListId;
		}

		final I_C_BP_Group bpGroup = bpartnerData.getC_BP_Group();
		return isSOTrx ? bpGroup.getM_PriceList_ID() : bpGroup.getPO_PriceList_ID();
	}

	public I_M_DiscountSchema getM_DiscountSchema(final boolean soTrx)
	{
		final I_C_BPartner bpartnerData = getBPartnerData();

		{
			final I_M_DiscountSchema discountSchema;
			if (soTrx)
			{
				discountSchema = bpartnerData.getM_DiscountSchema();
			}
			else
			{
				discountSchema = bpartnerData.getPO_DiscountSchema();
			}
			if (discountSchema != null && discountSchema.getM_DiscountSchema_ID() > 0)
			{
				return discountSchema; // we are done
			}
		}

		// didn't get the schema yet; now we try to get the discount schema from the C_BPartner's C_BP_Group
		final I_C_BP_Group bpGroup = bpartnerData.getC_BP_Group();
		if (bpGroup != null && bpGroup.getC_BP_Group_ID() > 0)
		{
			final I_M_DiscountSchema groupDiscountSchema;
			if (soTrx)
			{
				groupDiscountSchema = bpGroup.getM_DiscountSchema();
			}
			else
			{
				groupDiscountSchema = bpGroup.getPO_DiscountSchema();
			}
			if (groupDiscountSchema != null && groupDiscountSchema.getM_DiscountSchema_ID() > 0)
			{
				return groupDiscountSchema; // we are done
			}
		}

		return null;
	}

	public I_M_Shipper getShipperOrDefault()
	{
		final I_C_BPartner bpartnerData = getBPartnerData();

		final I_M_Shipper shipper = bpartnerData.getM_Shipper();
		if (shipper != null && shipper.getM_Shipper_ID() > 0)
		{
			return shipper;
		}

		// TODO: avoid calling DAO from here
		return Services.get(IBPartnerDAO.class).retrieveDefaultShipper();
	}

	public int getC_PaymentTerm_ID(final boolean isSOTrx)
	{
		if (isSOTrx)
		{
			return getBPartnerData().getC_PaymentTerm_ID();
		}
		else
		{
			return getBPartnerData().getPO_PaymentTerm_ID();
		}
	}

	public String getDeliveryRule()
	{
		return getBPartnerData().getDeliveryRule();
	}

	public String getDeliveryViaRule(final boolean isSOTrx)
	{
		if (isSOTrx)
		{
			return getBPartnerData().getDeliveryViaRule();
		}
		else
		{
			return getBPartnerData().getPO_DeliveryViaRule();
		}
	}

	public String getPaymentRule()
	{
		return getBPartnerData().getPaymentRule();
	}

	public String getInvoiceRule()
	{
		return getBPartnerData().getInvoiceRule();
	}

	public BigDecimal getPostageFreeAmt()
	{
		return getBPartnerData().getPostageFreeAmt();
	}

}
