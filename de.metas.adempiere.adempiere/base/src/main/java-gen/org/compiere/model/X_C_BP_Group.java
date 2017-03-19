/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_BP_Group
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BP_Group extends org.compiere.model.PO implements I_C_BP_Group, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1636313315L;

    /** Standard Constructor */
    public X_C_BP_Group (Properties ctx, int C_BP_Group_ID, String trxName)
    {
      super (ctx, C_BP_Group_ID, trxName);
      /** if (C_BP_Group_ID == 0)
        {
			setC_BP_Group_ID (0);
			setIsConfidentialInfo (false);
// N
			setIsCustomer (false);
// N
			setIsDefault (false);
			setM_PricingSystem_ID (0);
			setName (null);
			setPO_PricingSystem_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_BP_Group (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_PrintColor getAD_PrintColor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class);
	}

	@Override
	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class, AD_PrintColor);
	}

	/** Set Druck - Farbe.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	@Override
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Druck - Farbe.
		@return Color used for printing and display
	  */
	@Override
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Business Partner Group
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	/** Set Mahnung.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	@Override
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Mahnung.
		@return Dunning Rules for overdue invoices
	  */
	@Override
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit Watch %.
		@param CreditWatchPercent 
		Credit Watch - Percent of Credit Limit when OK switches to Watch
	  */
	@Override
	public void setCreditWatchPercent (java.math.BigDecimal CreditWatchPercent)
	{
		set_Value (COLUMNNAME_CreditWatchPercent, CreditWatchPercent);
	}

	/** Get Credit Watch %.
		@return Credit Watch - Percent of Credit Limit when OK switches to Watch
	  */
	@Override
	public java.math.BigDecimal getCreditWatchPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditWatchPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Confidential Info.
		@param IsConfidentialInfo 
		Can enter confidential information
	  */
	@Override
	public void setIsConfidentialInfo (boolean IsConfidentialInfo)
	{
		set_Value (COLUMNNAME_IsConfidentialInfo, Boolean.valueOf(IsConfidentialInfo));
	}

	/** Get Confidential Info.
		@return Can enter confidential information
	  */
	@Override
	public boolean isConfidentialInfo () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfidentialInfo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kunde.
		@param IsCustomer 
		Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public void setIsCustomer (boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Kunde.
		@return Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public boolean isCustomer () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	/** Set Rabatt-Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt-Schema.
		@return Schema to calculate the trade discount percentage
	  */
	@Override
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.adempiere.model.I_M_FreightCost getM_FreightCost() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCost_ID, org.adempiere.model.I_M_FreightCost.class);
	}

	@Override
	public void setM_FreightCost(org.adempiere.model.I_M_FreightCost M_FreightCost)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCost_ID, org.adempiere.model.I_M_FreightCost.class, M_FreightCost);
	}

	/** Set Frachtkostenpauschale.
		@param M_FreightCost_ID Frachtkostenpauschale	  */
	@Override
	public void setM_FreightCost_ID (int M_FreightCost_ID)
	{
		if (M_FreightCost_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_ID, Integer.valueOf(M_FreightCost_ID));
	}

	/** Get Frachtkostenpauschale.
		@return Frachtkostenpauschale	  */
	@Override
	public int getM_FreightCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	/** Set Exclude from MRP.
		@param MRP_Exclude 
		Exclude from MRP calculation
	  */
	@Override
	public void setMRP_Exclude (java.lang.String MRP_Exclude)
	{

		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	/** Get Exclude from MRP.
		@return Exclude from MRP calculation
	  */
	@Override
	public java.lang.String getMRP_Exclude () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MRP_Exclude);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRule 
		Wie die Rechnung bezahlt wird
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return Wie die Rechnung bezahlt wird
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setPO_DiscountSchema(org.compiere.model.I_M_DiscountSchema PO_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, PO_DiscountSchema);
	}

	/** Set PO Discount Schema.
		@param PO_DiscountSchema_ID 
		Schema to calculate the purchase trade discount percentage
	  */
	@Override
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID)
	{
		if (PO_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, Integer.valueOf(PO_DiscountSchema_ID));
	}

	/** Get PO Discount Schema.
		@return Schema to calculate the purchase trade discount percentage
	  */
	@Override
	public int getPO_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getPO_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setPO_PriceList(org.compiere.model.I_M_PriceList PO_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_PO_PriceList_ID, org.compiere.model.I_M_PriceList.class, PO_PriceList);
	}

	/** Set Einkaufspreisliste.
		@param PO_PriceList_ID 
		Price List used by this Business Partner
	  */
	@Override
	public void setPO_PriceList_ID (int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, Integer.valueOf(PO_PriceList_ID));
	}

	/** Get Einkaufspreisliste.
		@return Price List used by this Business Partner
	  */
	@Override
	public int getPO_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, PO_PricingSystem);
	}

	/** Set Einkaufspreissystem.
		@param PO_PricingSystem_ID Einkaufspreissystem	  */
	@Override
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID)
	{
		if (PO_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, Integer.valueOf(PO_PricingSystem_ID));
	}

	/** Get Einkaufspreissystem.
		@return Einkaufspreissystem	  */
	@Override
	public int getPO_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Price Match Tolerance.
		@param PriceMatchTolerance 
		PO-Invoice Match Price Tolerance in percent of the purchase price
	  */
	@Override
	public void setPriceMatchTolerance (java.math.BigDecimal PriceMatchTolerance)
	{
		set_Value (COLUMNNAME_PriceMatchTolerance, PriceMatchTolerance);
	}

	/** Get Price Match Tolerance.
		@return PO-Invoice Match Price Tolerance in percent of the purchase price
	  */
	@Override
	public java.math.BigDecimal getPriceMatchTolerance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceMatchTolerance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * PriorityBase AD_Reference_ID=350
	 * Reference name: C_BP_Group PriorityBase
	 */
	public static final int PRIORITYBASE_AD_Reference_ID=350;
	/** Same = S */
	public static final String PRIORITYBASE_Same = "S";
	/** Lower = L */
	public static final String PRIORITYBASE_Lower = "L";
	/** Higher = H */
	public static final String PRIORITYBASE_Higher = "H";
	/** Set Priority Base.
		@param PriorityBase 
		Base of Priority
	  */
	@Override
	public void setPriorityBase (java.lang.String PriorityBase)
	{

		set_Value (COLUMNNAME_PriorityBase, PriorityBase);
	}

	/** Get Priority Base.
		@return Base of Priority
	  */
	@Override
	public java.lang.String getPriorityBase () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityBase);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}