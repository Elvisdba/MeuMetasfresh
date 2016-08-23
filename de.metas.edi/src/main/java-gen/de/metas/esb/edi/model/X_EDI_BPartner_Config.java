/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for EDI_BPartner_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_BPartner_Config extends org.compiere.model.PO implements I_EDI_BPartner_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1698587468L;

    /** Standard Constructor */
    public X_EDI_BPartner_Config (Properties ctx, int EDI_BPartner_Config_ID, String trxName)
    {
      super (ctx, EDI_BPartner_Config_ID, trxName);
      /** if (EDI_BPartner_Config_ID == 0)
        {
			setC_BPartner_ID (0);
			setEDI_BPartner_Config_ID (0);
			setEDI_DefaultItemCapacity (Env.ZERO);
// 1
			setIsDesadvRecipient (false);
// N
			setIsEdiRecipient (false);
// N
			setIsInvoicRecipient (false);
// N
			setIsOrdrspRecipient (false);
// N
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_EDI_BPartner_Config (Properties ctx, ResultSet rs, String trxName)
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
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI_BPartner_Config.
		@param EDI_BPartner_Config_ID EDI_BPartner_Config	  */
	@Override
	public void setEDI_BPartner_Config_ID (int EDI_BPartner_Config_ID)
	{
		if (EDI_BPartner_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_BPartner_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_BPartner_Config_ID, Integer.valueOf(EDI_BPartner_Config_ID));
	}

	/** Get EDI_BPartner_Config.
		@return EDI_BPartner_Config	  */
	@Override
	public int getEDI_BPartner_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_BPartner_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set "CU pro TU" bei unbestimmter Verpackungskapazität.
		@param EDI_DefaultItemCapacity 
		"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	  */
	@Override
	public void setEDI_DefaultItemCapacity (java.math.BigDecimal EDI_DefaultItemCapacity)
	{
		set_Value (COLUMNNAME_EDI_DefaultItemCapacity, EDI_DefaultItemCapacity);
	}

	/** Get "CU pro TU" bei unbestimmter Verpackungskapazität.
		@return "CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	  */
	@Override
	public java.math.BigDecimal getEDI_DefaultItemCapacity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EDI_DefaultItemCapacity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EDI-GLN des Partners .
		@param EdiPartnerIdentification 
		Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.
	  */
	@Override
	public void setEdiPartnerIdentification (java.lang.String EdiPartnerIdentification)
	{
		set_Value (COLUMNNAME_EdiPartnerIdentification, EdiPartnerIdentification);
	}

	/** Get EDI-GLN des Partners .
		@return Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.
	  */
	@Override
	public java.lang.String getEdiPartnerIdentification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EdiPartnerIdentification);
	}

	/** Set Erhält DESADV.
		@param IsDesadvRecipient Erhält DESADV	  */
	@Override
	public void setIsDesadvRecipient (boolean IsDesadvRecipient)
	{
		set_Value (COLUMNNAME_IsDesadvRecipient, Boolean.valueOf(IsDesadvRecipient));
	}

	/** Get Erhält DESADV.
		@return Erhält DESADV	  */
	@Override
	public boolean isDesadvRecipient () 
	{
		Object oo = get_Value(COLUMNNAME_IsDesadvRecipient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Erhält EDI-Belege.
		@param IsEdiRecipient Erhält EDI-Belege	  */
	@Override
	public void setIsEdiRecipient (boolean IsEdiRecipient)
	{
		set_Value (COLUMNNAME_IsEdiRecipient, Boolean.valueOf(IsEdiRecipient));
	}

	/** Get Erhält EDI-Belege.
		@return Erhält EDI-Belege	  */
	@Override
	public boolean isEdiRecipient () 
	{
		Object oo = get_Value(COLUMNNAME_IsEdiRecipient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Erhält INVOIC.
		@param IsInvoicRecipient Erhält INVOIC	  */
	@Override
	public void setIsInvoicRecipient (boolean IsInvoicRecipient)
	{
		set_Value (COLUMNNAME_IsInvoicRecipient, Boolean.valueOf(IsInvoicRecipient));
	}

	/** Get Erhält INVOIC.
		@return Erhält INVOIC	  */
	@Override
	public boolean isInvoicRecipient () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoicRecipient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Erhält ORDRSP.
		@param IsOrdrspRecipient Erhält ORDRSP	  */
	@Override
	public void setIsOrdrspRecipient (boolean IsOrdrspRecipient)
	{
		set_Value (COLUMNNAME_IsOrdrspRecipient, Boolean.valueOf(IsOrdrspRecipient));
	}

	/** Get Erhält ORDRSP.
		@return Erhält ORDRSP	  */
	@Override
	public boolean isOrdrspRecipient () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrdrspRecipient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}