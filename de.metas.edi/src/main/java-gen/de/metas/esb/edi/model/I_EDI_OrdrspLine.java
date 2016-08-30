package de.metas.esb.edi.model;


/** Generated Interface for EDI_OrdrspLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_OrdrspLine 
{

    /** TableName=EDI_OrdrspLine */
    public static final String Table_Name = "EDI_OrdrspLine";

    /** AD_Table_ID=540777 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_Client>(I_EDI_OrdrspLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_Org>(I_EDI_OrdrspLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bestätigte Menge.
	 * Bestätigung einer erhaltenen Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfirmedQty (java.math.BigDecimal ConfirmedQty);

	/**
	 * Get Bestätigte Menge.
	 * Bestätigung einer erhaltenen Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getConfirmedQty();

    /** Column definition for ConfirmedQty */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_ConfirmedQty = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "ConfirmedQty", null);
    /** Column name ConfirmedQty */
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_User>(I_EDI_OrdrspLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Steuer.
	 * Steuersatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Steuersatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax();

	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax);

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_C_Tax>(I_EDI_OrdrspLine.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_C_UOM>(I_EDI_OrdrspLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Lieferdatum.
	 * Datum, an dem die Lieferung den Lieferempfänger erreicht.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Lieferdatum.
	 * Datum, an dem die Lieferung den Lieferempfänger erreicht.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set ORDRSP.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Ordrsp_ID (int EDI_Ordrsp_ID);

	/**
	 * Get ORDRSP.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Ordrsp_ID();

	public de.metas.esb.edi.model.I_EDI_Ordrsp getEDI_Ordrsp();

	public void setEDI_Ordrsp(de.metas.esb.edi.model.I_EDI_Ordrsp EDI_Ordrsp);

    /** Column definition for EDI_Ordrsp_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, de.metas.esb.edi.model.I_EDI_Ordrsp> COLUMN_EDI_Ordrsp_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, de.metas.esb.edi.model.I_EDI_Ordrsp>(I_EDI_OrdrspLine.class, "EDI_Ordrsp_ID", de.metas.esb.edi.model.I_EDI_Ordrsp.class);
    /** Column name EDI_Ordrsp_ID */
    public static final String COLUMNNAME_EDI_Ordrsp_ID = "EDI_Ordrsp_ID";

	/**
	 * Set EDI_OrdrspLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_OrdrspLine_ID (int EDI_OrdrspLine_ID);

	/**
	 * Get EDI_OrdrspLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_OrdrspLine_ID();

    /** Column definition for EDI_OrdrspLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_EDI_OrdrspLine_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "EDI_OrdrspLine_ID", null);
    /** Column name EDI_OrdrspLine_ID */
    public static final String COLUMNNAME_EDI_OrdrspLine_ID = "EDI_OrdrspLine_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_M_Product>(I_EDI_OrdrspLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, de.metas.inoutcandidate.model.I_M_ShipmentSchedule>(I_EDI_OrdrspLine.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Mengenkennzeichner.
	 * Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQuantityQualifier (java.lang.String QuantityQualifier);

	/**
	 * Get Mengenkennzeichner.
	 * Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQuantityQualifier();

    /** Column definition for QuantityQualifier */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_QuantityQualifier = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "QuantityQualifier", null);
    /** Column name QuantityQualifier */
    public static final String COLUMNNAME_QuantityQualifier = "QuantityQualifier";

	/**
	 * Set Versanddatum.
	 * Datum, an dem die Lieferung das Lager des Lieferanten verlässt
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShipDate (java.sql.Timestamp ShipDate);

	/**
	 * Get Versanddatum.
	 * Datum, an dem die Lieferung das Lager des Lieferanten verlässt
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getShipDate();

    /** Column definition for ShipDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_ShipDate = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "ShipDate", null);
    /** Column name ShipDate */
    public static final String COLUMNNAME_ShipDate = "ShipDate";

	/**
	 * Set UPC/EAN.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, Object>(I_EDI_OrdrspLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_EDI_OrdrspLine, org.compiere.model.I_AD_User>(I_EDI_OrdrspLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
