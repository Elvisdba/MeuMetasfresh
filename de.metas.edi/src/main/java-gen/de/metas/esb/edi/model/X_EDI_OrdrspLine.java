/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for EDI_OrdrspLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_OrdrspLine extends org.compiere.model.PO implements I_EDI_OrdrspLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1229160321L;

    /** Standard Constructor */
    public X_EDI_OrdrspLine (Properties ctx, int EDI_OrdrspLine_ID, String trxName)
    {
      super (ctx, EDI_OrdrspLine_ID, trxName);
      /** if (EDI_OrdrspLine_ID == 0)
        {
			setConfirmedQty (Env.ZERO);
			setC_UOM_ID (0);
			setEDI_Ordrsp_ID (0);
			setEDI_OrdrspLine_ID (0);
			setIsManual (true);
// Y
			setLine (0);
			setM_Product_ID (0);
			setPriceActual (Env.ZERO);
			setQuantityQualifier (null);
// 10_IA
			setUPC (null);
        } */
    }

    /** Load Constructor */
    public X_EDI_OrdrspLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Bestätigte Menge.
		@param ConfirmedQty 
		Bestätigung einer erhaltenen Menge
	  */
	@Override
	public void setConfirmedQty (java.math.BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	/** Get Bestätigte Menge.
		@return Bestätigung einer erhaltenen Menge
	  */
	@Override
	public java.math.BigDecimal getConfirmedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax)
	{
		set_ValueFromPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class, C_Tax);
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Steuersatz
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Steuersatz
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	@Override
	public de.metas.esb.edi.model.I_EDI_Ordrsp getEDI_Ordrsp() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Ordrsp_ID, de.metas.esb.edi.model.I_EDI_Ordrsp.class);
	}

	@Override
	public void setEDI_Ordrsp(de.metas.esb.edi.model.I_EDI_Ordrsp EDI_Ordrsp)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Ordrsp_ID, de.metas.esb.edi.model.I_EDI_Ordrsp.class, EDI_Ordrsp);
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

	/** Set EDI_OrdrspLine.
		@param EDI_OrdrspLine_ID EDI_OrdrspLine	  */
	@Override
	public void setEDI_OrdrspLine_ID (int EDI_OrdrspLine_ID)
	{
		if (EDI_OrdrspLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_OrdrspLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_OrdrspLine_ID, Integer.valueOf(EDI_OrdrspLine_ID));
	}

	/** Get EDI_OrdrspLine.
		@return EDI_OrdrspLine	  */
	@Override
	public int getEDI_OrdrspLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_OrdrspLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * QuantityQualifier AD_Reference_ID=540663
	 * Reference name: QuantityQualifier
	 */
	public static final int QUANTITYQUALIFIER_AD_Reference_ID=540663;
	/** ItemAccepted = 10_IA */
	public static final String QUANTITYQUALIFIER_ItemAccepted = "10_IA";
	/** ItemBackordered = 20_IB */
	public static final String QUANTITYQUALIFIER_ItemBackordered = "20_IB";
	/** ItemCancelled = 40_CK */
	public static final String QUANTITYQUALIFIER_ItemCancelled = "40_CK";
	/** ItemRejected = 30_IR */
	public static final String QUANTITYQUALIFIER_ItemRejected = "30_IR";
	/** Set Mengenkennzeichner.
		@param QuantityQualifier 
		Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.
	  */
	@Override
	public void setQuantityQualifier (java.lang.String QuantityQualifier)
	{

		set_Value (COLUMNNAME_QuantityQualifier, QuantityQualifier);
	}

	/** Get Mengenkennzeichner.
		@return Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.
	  */
	@Override
	public java.lang.String getQuantityQualifier () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QuantityQualifier);
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

	/** Set UPC/EAN.
		@param UPC 
		Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	  */
	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	  */
	@Override
	public java.lang.String getUPC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}
}