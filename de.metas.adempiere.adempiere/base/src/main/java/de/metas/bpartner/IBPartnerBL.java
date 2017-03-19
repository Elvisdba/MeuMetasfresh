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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.util.Language;

public interface IBPartnerBL extends ISingletonService
{
	/**
	 * make full address
	 *
	 * @param bPartner
	 * @param location
	 * @param user
	 * @param trxName
	 * @return
	 */
	String mkFullAddress(I_C_BPartner bPartner, I_C_BPartner_Location location, I_AD_User user, String trxName);

	/**
	 * Retrieve user/contact assigned to default/first ship to address. If no user/contact found, the first default user contact will be returned.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param trxName
	 * @return user/contact or null
	 */
	I_AD_User retrieveShipContact(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve user/contact assigned to default/first bill to address. If no user/contact found, the first default user contact will be returned.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param trxName
	 * @return user/contact or null
	 */
	I_AD_User retrieveBillContact(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve user/contact that is assigned to given location. If no assigned contact found then the default BPartner contact will be returned.<br>
	 * If there is no contact for given BPartner null will be returned.
	 *
	 * @param loc
	 * @return user/contact or null
	 */
	I_AD_User retrieveUserForLoc(org.compiere.model.I_C_BPartner_Location loc);

	/**
	 * Compute and set {@link I_C_BPartner_Location#COLUMNNAME_Address} field.
	 *
	 * @param bpLocation
	 */
	void setAddress(I_C_BPartner_Location bpLocation);

	I_AD_User retrieveShipContact(org.compiere.model.I_C_BPartner bpartner);

	/**
	 * @param partner the partner to check for. Internally working with {@link org.compiere.model.I_C_BPartner}.
	 * @param isSOTrx
	 * @return true if InOut consolidation is allowed for given partner
	 */
	boolean isAllowConsolidateInOutEffective(org.compiere.model.I_C_BPartner partner, boolean isSOTrx);

	/**
	 * Use {@link IBPartnerAware} to get BPartner from given model.
	 * 
	 * @param model
	 * @return bpartner or <code>null</code>
	 */
	I_C_BPartner getBPartnerForModel(Object model);

	/**
	 * Gets BPartner's Language
	 * 
	 * @param ctx
	 * @param bpartnerId
	 * @return {@link Language} or <code>null</code>
	 */
	Language getLanguage(Properties ctx, int bpartnerId);

	/**
	 * Get the language of the given model's C_BPartner, if it has a <code>C_BPartner_ID</code> column and if the BPartner is set.
	 * 
	 * @param model
	 * @return the language, if found, <code>null</code> otherwise.
	 */
	Language getLanguageForModel(Object model);

	/**
	 * Creates BPartner, Location and contact from given template.
	 * 
	 * @param template
	 * @return created bpartner
	 * @task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	I_C_BPartner createFromTemplate(I_C_BPartner_QuickInput template);
	
	int getM_PriceList_ID(final I_C_BPartner bpartner, final boolean isSOTrx);
}
