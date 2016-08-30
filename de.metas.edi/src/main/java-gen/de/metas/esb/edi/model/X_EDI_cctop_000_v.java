/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_000_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -312471007L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (Properties ctx, int EDI_cctop_000_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_000_v_ID, trxName);
      /** if (EDI_cctop_000_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_000_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI_cctop_000_v.
		@param EDI_cctop_000_v_ID EDI_cctop_000_v	  */
	@Override
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID)
	{
		if (EDI_cctop_000_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, Integer.valueOf(EDI_cctop_000_v_ID));
	}

	/** Get EDI_cctop_000_v.
		@return EDI_cctop_000_v	  */
	@Override
	public int getEDI_cctop_000_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_000_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Empfänger-GLN.
		@param EDIReceiverIdentification 
		EDI-GLN des Empfängers
	  */
	@Override
	public void setEDIReceiverIdentification (java.lang.String EDIReceiverIdentification)
	{
		set_ValueNoCheck (COLUMNNAME_EDIReceiverIdentification, EDIReceiverIdentification);
	}

	/** Get Empfänger-GLN.
		@return EDI-GLN des Empfängers
	  */
	@Override
	public java.lang.String getEDIReceiverIdentification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDIReceiverIdentification);
	}

	/** Set Absender-GLN.
		@param EDISenderIdentification 
		EDI-GLN des Absenders
	  */
	@Override
	public void setEDISenderIdentification (java.lang.String EDISenderIdentification)
	{
		set_ValueNoCheck (COLUMNNAME_EDISenderIdentification, EDISenderIdentification);
	}

	/** Get Absender-GLN.
		@return EDI-GLN des Absenders
	  */
	@Override
	public java.lang.String getEDISenderIdentification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDISenderIdentification);
	}
}