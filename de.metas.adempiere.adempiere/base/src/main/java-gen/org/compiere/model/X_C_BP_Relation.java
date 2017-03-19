/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_Relation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BP_Relation extends org.compiere.model.PO implements I_C_BP_Relation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -203186097L;

    /** Standard Constructor */
    public X_C_BP_Relation (Properties ctx, int C_BP_Relation_ID, String trxName)
    {
      super (ctx, C_BP_Relation_ID, trxName);
      /** if (C_BP_Relation_ID == 0)
        {
			setC_BP_Relation_ID (0);
			setC_BPartner_ID (0);
			setC_BPartnerRelation_ID (0);
			setC_BPartnerRelation_Location_ID (0);
			setIsBillTo (false);
			setIsHandOverLocation (false);
// N
			setIsPayFrom (false);
			setIsRemitTo (false);
			setIsShipTo (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_BP_Relation (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Beziehungen Geschäftspartner.
		@param C_BP_Relation_ID 
		Business Partner Relation
	  */
	@Override
	public void setC_BP_Relation_ID (int C_BP_Relation_ID)
	{
		if (C_BP_Relation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Relation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Relation_ID, Integer.valueOf(C_BP_Relation_ID));
	}

	/** Get Beziehungen Geschäftspartner.
		@return Business Partner Relation
	  */
	@Override
	public int getC_BP_Relation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Relation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartnerRelation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartnerRelation_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartnerRelation(org.compiere.model.I_C_BPartner C_BPartnerRelation)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartnerRelation_ID, org.compiere.model.I_C_BPartner.class, C_BPartnerRelation);
	}

	/** Set Zugehöriger Geschäftspartner.
		@param C_BPartnerRelation_ID 
		Related Business Partner
	  */
	@Override
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID)
	{
		if (C_BPartnerRelation_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, Integer.valueOf(C_BPartnerRelation_ID));
	}

	/** Get Zugehöriger Geschäftspartner.
		@return Related Business Partner
	  */
	@Override
	public int getC_BPartnerRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartnerRelation_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartnerRelation_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartnerRelation_Location(org.compiere.model.I_C_BPartner_Location C_BPartnerRelation_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartnerRelation_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartnerRelation_Location);
	}

	/** Set Zugehöriger Standort.
		@param C_BPartnerRelation_Location_ID 
		Location of the related Business Partner
	  */
	@Override
	public void setC_BPartnerRelation_Location_ID (int C_BPartnerRelation_Location_ID)
	{
		if (C_BPartnerRelation_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, Integer.valueOf(C_BPartnerRelation_Location_ID));
	}

	/** Get Zugehöriger Standort.
		@return Location of the related Business Partner
	  */
	@Override
	public int getC_BPartnerRelation_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Vorbelegung Rechnung.
		@param IsBillTo 
		Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Vorbelegung Rechnung.
		@return Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abladeort.
		@param IsHandOverLocation Abladeort	  */
	@Override
	public void setIsHandOverLocation (boolean IsHandOverLocation)
	{
		set_Value (COLUMNNAME_IsHandOverLocation, Boolean.valueOf(IsHandOverLocation));
	}

	/** Get Abladeort.
		@return Abladeort	  */
	@Override
	public boolean isHandOverLocation () 
	{
		Object oo = get_Value(COLUMNNAME_IsHandOverLocation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zahlungs-Adresse.
		@param IsPayFrom 
		Business Partner pays from that address and we'll send dunning letters there
	  */
	@Override
	public void setIsPayFrom (boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, Boolean.valueOf(IsPayFrom));
	}

	/** Get Zahlungs-Adresse.
		@return Business Partner pays from that address and we'll send dunning letters there
	  */
	@Override
	public boolean isPayFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Erstattungs-Adresse.
		@param IsRemitTo 
		Business Partner payment address
	  */
	@Override
	public void setIsRemitTo (boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, Boolean.valueOf(IsRemitTo));
	}

	/** Get Erstattungs-Adresse.
		@return Business Partner payment address
	  */
	@Override
	public boolean isRemitTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemitTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferstandard.
		@param IsShipTo 
		Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Lieferstandard.
		@return Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
}