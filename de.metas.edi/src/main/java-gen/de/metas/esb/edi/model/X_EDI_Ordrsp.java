/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for EDI_Ordrsp
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_Ordrsp extends org.compiere.model.PO implements I_EDI_Ordrsp, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 840760758L;

    /** Standard Constructor */
    public X_EDI_Ordrsp (Properties ctx, int EDI_Ordrsp_ID, String trxName)
    {
      super (ctx, EDI_Ordrsp_ID, trxName);
      /** if (EDI_Ordrsp_ID == 0)
        {
			setC_Currency_ID (0);
			setDeliveryGLN (null);
			setEDI_ExportStatus (null);
// P
			setEDI_Ordrsp_ID (0);
			setEDIReceiverIdentification (null);
			setEDISenderIdentification (null);
			setHandOver_Location_ID (0);
			setHandOver_Partner_ID (0);
			setPOReference (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setSupplierGLN (null);
        } */
    }

    /** Load Constructor */
    public X_EDI_Ordrsp (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdatum.
		@param DeliveryDate 
		Datum, an dem die Lieferung den Lieferempfänger erreicht.
	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Datum, an dem die Lieferung den Lieferempfänger erreicht.
	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	/** Set Lieferort-GLN.
		@param DeliveryGLN 
		Übergabeort (EANCOM: NAD+DP)
	  */
	@Override
	public void setDeliveryGLN (java.lang.String DeliveryGLN)
	{
		set_Value (COLUMNNAME_DeliveryGLN, DeliveryGLN);
	}

	/** Get Lieferort-GLN.
		@return Übergabeort (EANCOM: NAD+DP)
	  */
	@Override
	public java.lang.String getDeliveryGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryGLN);
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EDI Fehlermeldung.
		@param EDIErrorMsg EDI Fehlermeldung	  */
	@Override
	public void setEDIErrorMsg (java.lang.String EDIErrorMsg)
	{
		set_Value (COLUMNNAME_EDIErrorMsg, EDIErrorMsg);
	}

	/** Get EDI Fehlermeldung.
		@return EDI Fehlermeldung	  */
	@Override
	public java.lang.String getEDIErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDIErrorMsg);
	}

	/** 
	 * EDI_ExportStatus AD_Reference_ID=540381
	 * Reference name: EDI_ExportStatus
	 */
	public static final int EDI_EXPORTSTATUS_AD_Reference_ID=540381;
	/** Invalid = I */
	public static final String EDI_EXPORTSTATUS_Invalid = "I";
	/** Pending = P */
	public static final String EDI_EXPORTSTATUS_Pending = "P";
	/** Sent = S */
	public static final String EDI_EXPORTSTATUS_Sent = "S";
	/** SendingStarted = D */
	public static final String EDI_EXPORTSTATUS_SendingStarted = "D";
	/** Error = E */
	public static final String EDI_EXPORTSTATUS_Error = "E";
	/** Enqueued = U */
	public static final String EDI_EXPORTSTATUS_Enqueued = "U";
	/** DontSend = N */
	public static final String EDI_EXPORTSTATUS_DontSend = "N";
	/** Set EDI Export Status.
		@param EDI_ExportStatus EDI Export Status	  */
	@Override
	public void setEDI_ExportStatus (java.lang.String EDI_ExportStatus)
	{

		set_Value (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	/** Get EDI Export Status.
		@return EDI Export Status	  */
	@Override
	public java.lang.String getEDI_ExportStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDI_ExportStatus);
	}

	/** Set ORDRSP.
		@param EDI_Ordrsp_ID ORDRSP	  */
	@Override
	public void setEDI_Ordrsp_ID (int EDI_Ordrsp_ID)
	{
		if (EDI_Ordrsp_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Ordrsp_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Ordrsp_ID, Integer.valueOf(EDI_Ordrsp_ID));
	}

	/** Get ORDRSP.
		@return ORDRSP	  */
	@Override
	public int getEDI_Ordrsp_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Ordrsp_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestätigung % Minimum.
		@param EDI_ORDRSP_MinimumSumPercentage 
		Mindestprozentsatz der beauftragten Gesamtmenge, zu der die Bestätigung eine Aussage treffen muss.
	  */
	@Override
	public void setEDI_ORDRSP_MinimumSumPercentage (java.math.BigDecimal EDI_ORDRSP_MinimumSumPercentage)
	{
		set_Value (COLUMNNAME_EDI_ORDRSP_MinimumSumPercentage, EDI_ORDRSP_MinimumSumPercentage);
	}

	/** Get Bestätigung % Minimum.
		@return Mindestprozentsatz der beauftragten Gesamtmenge, zu der die Bestätigung eine Aussage treffen muss.
	  */
	@Override
	public java.math.BigDecimal getEDI_ORDRSP_MinimumSumPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EDI_ORDRSP_MinimumSumPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestätigt %.
		@param EDI_ORDRSP_SumPercentage Bestätigt %	  */
	@Override
	public void setEDI_ORDRSP_SumPercentage (java.math.BigDecimal EDI_ORDRSP_SumPercentage)
	{
		throw new IllegalArgumentException ("EDI_ORDRSP_SumPercentage is virtual column");	}

	/** Get Bestätigt %.
		@return Bestätigt %	  */
	@Override
	public java.math.BigDecimal getEDI_ORDRSP_SumPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EDI_ORDRSP_SumPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Empfänger-GLN.
		@param EDIReceiverIdentification 
		EDI-GLN des Empfängers
	  */
	@Override
	public void setEDIReceiverIdentification (java.lang.String EDIReceiverIdentification)
	{
		set_Value (COLUMNNAME_EDIReceiverIdentification, EDIReceiverIdentification);
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
		set_Value (COLUMNNAME_EDISenderIdentification, EDISenderIdentification);
	}

	/** Get Absender-GLN.
		@return EDI-GLN des Absenders
	  */
	@Override
	public java.lang.String getEDISenderIdentification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDISenderIdentification);
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getHandOver_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setHandOver_Location(org.compiere.model.I_C_BPartner_Location HandOver_Location)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Location_ID, org.compiere.model.I_C_BPartner_Location.class, HandOver_Location);
	}

	/** Set Übergabeadresse.
		@param HandOver_Location_ID Übergabeadresse	  */
	@Override
	public void setHandOver_Location_ID (int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, Integer.valueOf(HandOver_Location_ID));
	}

	/** Get Übergabeadresse.
		@return Übergabeadresse	  */
	@Override
	public int getHandOver_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getHandOver_Partner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Partner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setHandOver_Partner(org.compiere.model.I_C_BPartner HandOver_Partner)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Partner_ID, org.compiere.model.I_C_BPartner.class, HandOver_Partner);
	}

	/** Set Übergabe an.
		@param HandOver_Partner_ID Übergabe an	  */
	@Override
	public void setHandOver_Partner_ID (int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, Integer.valueOf(HandOver_Partner_ID));
	}

	/** Get Übergabe an.
		@return Übergabe an	  */
	@Override
	public int getHandOver_Partner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Partner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Versanddatum.
		@param ShipDate 
		Datum, an dem die Lieferung das Lager des Lieferanten verlässt
	  */
	@Override
	public void setShipDate (java.sql.Timestamp ShipDate)
	{
		set_Value (COLUMNNAME_ShipDate, ShipDate);
	}

	/** Get Versanddatum.
		@return Datum, an dem die Lieferung das Lager des Lieferanten verlässt
	  */
	@Override
	public java.sql.Timestamp getShipDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ShipDate);
	}

	/** Set Lieferanten-GLN.
		@param SupplierGLN 
		Im Fall einer Verkaufstransaktion is die Lieferanten-GLN eine der eigenen GLNs (EANCOM: NAD+SU)
	  */
	@Override
	public void setSupplierGLN (java.lang.String SupplierGLN)
	{
		set_Value (COLUMNNAME_SupplierGLN, SupplierGLN);
	}

	/** Get Lieferanten-GLN.
		@return Im Fall einer Verkaufstransaktion is die Lieferanten-GLN eine der eigenen GLNs (EANCOM: NAD+SU)
	  */
	@Override
	public java.lang.String getSupplierGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SupplierGLN);
	}
}