package de.metas.bpartner;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;

import de.metas.bpartner.exceptions.OrgHasNoBPartnerLinkException;
import de.metas.bpartner.model.BPartner;

public interface IBPartnerDAO extends ISingletonService
{
	BPartner retrieveBPartnerAgg(Properties ctx, int bpartnerId);

	/** Extracts BPartner from given dynamic {@link IBPartnerAware} model */
	BPartner retrieveBPartnerAggForModel(Object model);

	default BPartner retrieveBPartnerAgg(final int bpartnerId)
	{
		return retrieveBPartnerAgg(Env.getCtx(), bpartnerId);
	}


	/** Saves BPartner aggregate */
	void save(BPartner bpartner);

	/**
	 * Retrieves BPartner if it's AD_Client_ID is context's AD_Client_ID.
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @return bpartner or null
	 */
	I_C_BPartner retrieveBPartner(final Properties ctx, final int bpartnerId);

	/**
	 * Retrieve default BPartner to be used on "anonymous" cash transactions
	 *
	 * @param ctx
	 * @param adClientId
	 * @return bpartner or null
	 */
	I_C_BPartner retrieveBPartnerForCacheTrx(Properties ctx, int adClientId);

	/**
	 * Retrieve {@link I_C_BPartner} assigned to given organization
	 *
	 * @param ctx
	 * @param orgId
	 * @param clazz
	 * @param trxName
	 * @return {@link I_C_BPartner}; never return null
	 * @throws OrgHasNoBPartnerLinkException if no partner was found
	 */
	// TODO: move it to de.metas.adempiere.service.IBPartnerOrgBL
	<T extends I_C_BPartner> T retrieveOrgBPartner(Properties ctx, int orgId, Class<T> clazz, String trxName);

	BPartnerLocations retrieveLocations(I_C_BPartner bpartner);

	BPartnerLocations retrieveLocations(Properties ctx, int bpartnerId, String trxName);

	default BPartnerLocations retrieveLocations(final Properties ctx, final int bpartnerId)
	{
		return retrieveLocations(ctx, bpartnerId, ITrx.TRXNAME_None);
	}
	
	default BPartnerLocations retrieveLocations(final int bpartnerId)
	{
		return retrieveLocations(Env.getCtx(), bpartnerId, ITrx.TRXNAME_None);
	}


	/**
	 * Contacts of the partner, ordered by ad_user_ID, ascending
	 *
	 * @param ctx
	 * @param partnerId
	 * @param trxName
	 * @return
	 */
	List<I_AD_User> retrieveContacts(Properties ctx, int partnerId, String trxName);

	/**
	 * Contacts of the partner, ordered by ad_user_ID, ascending
	 *
	 * @param bpartner
	 * @return
	 */
	List<I_AD_User> retrieveContacts(I_C_BPartner bpartner);

	/**
	 * Returns the <code>M_PricingSystem_ID</code> to use for a given bPartner.
	 *
	 *
	 * @param ctx
	 * @param bPartnerId the ID of the BPartner for which we need the pricing system id
	 * @param soTrx
	 *            <ul>
	 *            <li>if <code>true</code>, then the method first checks <code>C_BPartner.M_PricingSystem_ID</code> , then (if the BPartner has a C_BP_Group_ID) in
	 *            <code>C_BP_Group.M_PricingSystem_ID</code> and finally (if the C_BPArtner has a AD_Org_ID>0) in <code>AD_OrgInfo.M_PricingSystem_ID</code></li>
	 *            <li>if <code>false</code></li>, then the method first checks <code>C_BPartner.PO_PricingSystem_ID</code>, then (if the BPartner has a C_BP_Group_ID!) in
	 *            <code>C_BP_Group.PO_PricingSystem_ID</code>. Note that <code>AD_OrgInfo</code> has currently no <code>PO_PricingSystem_ID</code> column.
	 *            </ul>
	 * @param trxName
	 * @return M_PricingSystem_ID or 0
	 */
	int retrievePricingSystemId(Properties ctx, int bPartnerId, boolean soTrx, String trxName);

	/**
	 * Retrieves the discount schema for the given BParnter. If the BPartner has none, it falls back to the partner's C_BP_Group. If the partner has no group or that group hasn't a discount schema
	 * either, it returns <code>null</code>.
	 *
	 * @param partner
	 * @param soTrx if <code>true</code>, the sales discount schema is returned, otherwise the purchase discount schema is returned.
	 */
	I_M_DiscountSchema retrieveDiscountSchemaOrNull(I_C_BPartner partner, boolean soTrx);

	I_M_Shipper retrieveShipper(int bPartnerId);

	I_M_Shipper retrieveDefaultShipper();

	/**
	 * Search after the BPartner when the value is given
	 *
	 * @param ctx
	 * @param value
	 * @return C_BPartner object or null
	 */
	I_C_BPartner retrieveBPartnerByValue(Properties ctx, String value);

	<T extends I_AD_User> T retrieveDefaultContactOrNull(I_C_BPartner bPartner, Class<T> clazz);

	I_AD_User retrieveDefaultContactOrNull(int bpartnerId);

	I_AD_User retrieveDefaultContactOrFirstOrNull(int bpartnerId);

	/**
	 * Search the {@link I_C_BP_Relation}s for matching partner and location (note that the link without location is acceptable too)
	 *
	 * @param bpartnerId
	 * @param bpartnerLocationId optional bpartner location ID
	 * @return {@link I_C_BP_Relation} first encountered which is used for billing
	 */
	I_C_BP_Relation retrieveBillBPartnerRelationFirstEncountered(final int bpartnerId, final int bpartnerLocationId);

	/**
	 * Get the fit contact for the given partner and isSOTrx. In case of SOTrx, the salesContacts will have priority. Same for POTrx and PurcanseCOntacts In case of 2 entries with equal values in the
	 * fields above, the Default contact will have priority
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @param isSOTrx
	 * @param trxName
	 * @return
	 */
	I_AD_User retrieveContact(Properties ctx, int bpartnerId, boolean isSOTrx, String trxName);

	I_C_BP_Group retrieveDefaultBPGroup(final Properties ctx);

	I_C_BP_Relation retrieveBillToRelation(int bpartnerId);
}
