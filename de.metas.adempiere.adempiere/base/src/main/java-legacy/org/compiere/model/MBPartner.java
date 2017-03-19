package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.bpartner.IBPartnerBL;
import de.metas.bpartner.IBPartnerDAO;

/**
 * BPartner model
 * @author metas-dev <dev@metasfresh.com>
 * @author based on original version created by Jorg Janke, Teo Sarca, Armen Rizal
 */
@SuppressWarnings("serial")
public class MBPartner extends X_C_BPartner
{
	public MBPartner(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	} // MBPartner

	public MBPartner(Properties ctx, int C_BPartner_ID, String trxName)
	{
		super(ctx, C_BPartner_ID, trxName);
		if (C_BPartner_ID == 0)
		{
			// setValue ("");
			// setName ("");
			// setName2 (null);
			// setDUNS("");
			//
			setIsCustomer(true);
			setIsProspect(true);
			//
			setSendEMail(false);
			setIsOneTime(false);
			setIsVendor(false);
			setIsSummary(false);
			setIsEmployee(false);
			setIsSalesRep(false);
			setIsTaxExempt(false);
			setIsPOTaxExempt(false);
			setIsDiscountPrinted(false);
			//
			setSO_CreditLimit(BigDecimal.ZERO);

			//
			setFirstSale(null);

			setPotentialLifeTimeValue(BigDecimal.ZERO);
			setAcqusitionCost(BigDecimal.ZERO);
			setShareOfCustomer(0);
			setSalesVolume(0);
		}
		// ts: doesn't work with table level caching, when this instance is created by PO.copy().
		// Reason: at this stage we don't yet have a POInfo, but the toString() method calls getValue which requires a POInfo
		// log.debug(toString());
	} // MBPartner
	
	@Override
	public String toString()
	{
		// NOTE: don't print the stats, because that will involve database access which is quite expensive for a stupid toString method!
		// final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		// final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(partner);

		final StringBuilder sb = new StringBuilder("MBPartner[ID=").append(get_ID())
				.append(",Value=").append(getValue())
				.append(",Name=").append(getName())
				// .append(",Open=").append(stats.getTotalOpenBalance())
				.append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Linked Organization. (is Button) The Business Partner is another
	 * Organization for explicit Inter-Org transactions
	 * 
	 * @return AD_Org_ID if BP
	 */
	public int getAD_OrgBP_ID_Int()
	{
		return super.getAD_OrgBP_ID();
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord || is_ValueChanged(COLUMNNAME_C_BP_Group_ID))
		{
			I_C_BP_Group bpGroup = getC_BP_Group();
			if(bpGroup == null)
			{
				bpGroup = Services.get(IBPartnerDAO.class).retrieveDefaultBPGroup(getCtx());
			}
			if (bpGroup == null)
			{
				throw new AdempiereException("@NotFound@:  @C_BP_Group_ID@");
			}
			Services.get(IBPartnerBL.class).setBPGroup(this, bpGroup);
		}
		return true;
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (newRecord && success)
		{
			// Accounting
			insert_Accounting(I_C_BP_Customer_Acct.Table_Name, I_C_BP_Group_Acct.Table_Name,
					"p.C_BP_Group_ID=" + getC_BP_Group_ID());
			insert_Accounting(I_C_BP_Vendor_Acct.Table_Name, I_C_BP_Group_Acct.Table_Name,
					"p.C_BP_Group_ID=" + getC_BP_Group_ID());
			insert_Accounting(I_C_BP_Employee_Acct.Table_Name, I_C_AcctSchema_Default.Table_Name,
					null);
		}

		// Value/Name change
		if (success && !newRecord && (is_ValueChanged(COLUMNNAME_Value) || is_ValueChanged(COLUMNNAME_Name)))
		{
			MAccount.updateValueDescription(getCtx(), "C_BPartner_ID=" + getC_BPartner_ID(), get_TrxName());
		}

		return success;
	}

	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting(I_C_BP_Customer_Acct.Table_Name)
				&& delete_Accounting(I_C_BP_Vendor_Acct.Table_Name)
				&& delete_Accounting(I_C_BP_Employee_Acct.Table_Name);
	}
}
