package de.metas.esb.edi.model;


/** Generated Interface for EDI_Ordrsp
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_Ordrsp 
{

    /** TableName=EDI_Ordrsp */
    public static final String Table_Name = "EDI_Ordrsp";

    /** AD_Table_ID=540776 */
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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_Client>(I_EDI_Ordrsp.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_Org>(I_EDI_Ordrsp.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_Currency>(I_EDI_Ordrsp.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_User>(I_EDI_Ordrsp.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Lieferort-GLN.
	 * Übergabeort (EANCOM: NAD+DP)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryGLN (java.lang.String DeliveryGLN);

	/**
	 * Get Lieferort-GLN.
	 * Übergabeort (EANCOM: NAD+DP)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryGLN();

    /** Column definition for DeliveryGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_DeliveryGLN = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "DeliveryGLN", null);
    /** Column name DeliveryGLN */
    public static final String COLUMNNAME_DeliveryGLN = "DeliveryGLN";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set EDI Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDIErrorMsg (java.lang.String EDIErrorMsg);

	/**
	 * Get EDI Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDIErrorMsg();

    /** Column definition for EDIErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDIErrorMsg = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDIErrorMsg", null);
    /** Column name EDIErrorMsg */
    public static final String COLUMNNAME_EDIErrorMsg = "EDIErrorMsg";

	/**
	 * Set EDI Export Status.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_ExportStatus (java.lang.String EDI_ExportStatus);

	/**
	 * Get EDI Export Status.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDI_ExportStatus();

    /** Column definition for EDI_ExportStatus */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDI_ExportStatus = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDI_ExportStatus", null);
    /** Column name EDI_ExportStatus */
    public static final String COLUMNNAME_EDI_ExportStatus = "EDI_ExportStatus";

	/**
	 * Set ORDRSP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Ordrsp_ID (int EDI_Ordrsp_ID);

	/**
	 * Get ORDRSP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Ordrsp_ID();

    /** Column definition for EDI_Ordrsp_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDI_Ordrsp_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDI_Ordrsp_ID", null);
    /** Column name EDI_Ordrsp_ID */
    public static final String COLUMNNAME_EDI_Ordrsp_ID = "EDI_Ordrsp_ID";

	/**
	 * Set Bestätigung % Minimum.
	 * Mindestprozentsatz der beauftragten Gesamtmenge, zu der die Bestätigung eine Aussage treffen muss.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_ORDRSP_MinimumSumPercentage (java.math.BigDecimal EDI_ORDRSP_MinimumSumPercentage);

	/**
	 * Get Bestätigung % Minimum.
	 * Mindestprozentsatz der beauftragten Gesamtmenge, zu der die Bestätigung eine Aussage treffen muss.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEDI_ORDRSP_MinimumSumPercentage();

    /** Column definition for EDI_ORDRSP_MinimumSumPercentage */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDI_ORDRSP_MinimumSumPercentage = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDI_ORDRSP_MinimumSumPercentage", null);
    /** Column name EDI_ORDRSP_MinimumSumPercentage */
    public static final String COLUMNNAME_EDI_ORDRSP_MinimumSumPercentage = "EDI_ORDRSP_MinimumSumPercentage";

	/**
	 * Set Bestätigt %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setEDI_ORDRSP_SumPercentage (java.math.BigDecimal EDI_ORDRSP_SumPercentage);

	/**
	 * Get Bestätigt %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getEDI_ORDRSP_SumPercentage();

    /** Column definition for EDI_ORDRSP_SumPercentage */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDI_ORDRSP_SumPercentage = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDI_ORDRSP_SumPercentage", null);
    /** Column name EDI_ORDRSP_SumPercentage */
    public static final String COLUMNNAME_EDI_ORDRSP_SumPercentage = "EDI_ORDRSP_SumPercentage";

	/**
	 * Set Empfänger-GLN.
	 * EDI-GLN des Empfängers
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDIReceiverIdentification (java.lang.String EDIReceiverIdentification);

	/**
	 * Get Empfänger-GLN.
	 * EDI-GLN des Empfängers
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDIReceiverIdentification();

    /** Column definition for EDIReceiverIdentification */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDIReceiverIdentification = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDIReceiverIdentification", null);
    /** Column name EDIReceiverIdentification */
    public static final String COLUMNNAME_EDIReceiverIdentification = "EDIReceiverIdentification";

	/**
	 * Set Absender-GLN.
	 * EDI-GLN des Absenders
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDISenderIdentification (java.lang.String EDISenderIdentification);

	/**
	 * Get Absender-GLN.
	 * EDI-GLN des Absenders
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEDISenderIdentification();

    /** Column definition for EDISenderIdentification */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_EDISenderIdentification = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "EDISenderIdentification", null);
    /** Column name EDISenderIdentification */
    public static final String COLUMNNAME_EDISenderIdentification = "EDISenderIdentification";

	/**
	 * Set Übergabeadresse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Location_ID (int HandOver_Location_ID);

	/**
	 * Get Übergabeadresse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getHandOver_Location();

	public void setHandOver_Location(org.compiere.model.I_C_BPartner_Location HandOver_Location);

    /** Column definition for HandOver_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_BPartner_Location> COLUMN_HandOver_Location_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_BPartner_Location>(I_EDI_Ordrsp.class, "HandOver_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name HandOver_Location_ID */
    public static final String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";

	/**
	 * Set Übergabe an.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Partner_ID (int HandOver_Partner_ID);

	/**
	 * Get Übergabe an.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Partner_ID();

	public org.compiere.model.I_C_BPartner getHandOver_Partner();

	public void setHandOver_Partner(org.compiere.model.I_C_BPartner HandOver_Partner);

    /** Column definition for HandOver_Partner_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_BPartner> COLUMN_HandOver_Partner_ID = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_C_BPartner>(I_EDI_Ordrsp.class, "HandOver_Partner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name HandOver_Partner_ID */
    public static final String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_ShipDate = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "ShipDate", null);
    /** Column name ShipDate */
    public static final String COLUMNNAME_ShipDate = "ShipDate";

	/**
	 * Set Lieferanten-GLN.
	 * Im Fall einer Verkaufstransaktion is die Lieferanten-GLN eine der eigenen GLNs (EANCOM: NAD+SU)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSupplierGLN (java.lang.String SupplierGLN);

	/**
	 * Get Lieferanten-GLN.
	 * Im Fall einer Verkaufstransaktion is die Lieferanten-GLN eine der eigenen GLNs (EANCOM: NAD+SU)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSupplierGLN();

    /** Column definition for SupplierGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_SupplierGLN = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "SupplierGLN", null);
    /** Column name SupplierGLN */
    public static final String COLUMNNAME_SupplierGLN = "SupplierGLN";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, Object>(I_EDI_Ordrsp.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_EDI_Ordrsp, org.compiere.model.I_AD_User>(I_EDI_Ordrsp.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
