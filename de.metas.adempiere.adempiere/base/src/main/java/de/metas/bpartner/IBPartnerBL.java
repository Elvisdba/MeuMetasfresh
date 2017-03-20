package de.metas.bpartner;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;

import de.metas.bpartner.model.BPartner;

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
	 * Compute and set {@link I_C_BPartner_Location#COLUMNNAME_Address} field.
	 *
	 * @param bpLocation
	 */
	void setAddress(I_C_BPartner_Location bpLocation);

	/**
	 * @param partner the partner to check for. Internally working with {@link org.compiere.model.I_C_BPartner}.
	 * @param isSOTrx
	 * @return true if InOut consolidation is allowed for given partner
	 */
	boolean isAllowConsolidateInOutEffective(BPartner partner, boolean isSOTrx);

	/**
	 * Creates BPartner, Location and contact from given template.
	 * 
	 * @param template
	 * @return created bpartner
	 * @task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	I_C_BPartner createFromTemplate(I_C_BPartner_QuickInput template);
	
	/**
	 * @param bpartner
	 * @return new bpartner location (not saved)
	 */
	I_C_BPartner_Location createBPLocation(I_C_BPartner bpartner);

	/**
	 * @param bpartner
	 * @return new bpartner contact (not saved)
	 */
	I_AD_User createContact(I_C_BPartner bpartner);

	/**
	 * Set BP Group and update bpartner fields from group.
	 * 
	 * @param group
	 *            group
	 */
	void setBPGroup(I_C_BPartner bpartner, I_C_BP_Group group);

}
