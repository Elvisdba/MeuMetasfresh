package de.metas.esb.edi.model;


/** Generated Interface for EDI_BPartner_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_BPartner_Config 
{

    /** TableName=EDI_BPartner_Config */
    public static final String Table_Name = "EDI_BPartner_Config";

    /** AD_Table_ID=540778 */
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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_Client>(I_EDI_BPartner_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_Org>(I_EDI_BPartner_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_C_BPartner>(I_EDI_BPartner_Config.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_User>(I_EDI_BPartner_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set EDI_BPartner_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_BPartner_Config_ID (int EDI_BPartner_Config_ID);

	/**
	 * Get EDI_BPartner_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_BPartner_Config_ID();

    /** Column definition for EDI_BPartner_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_EDI_BPartner_Config_ID = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "EDI_BPartner_Config_ID", null);
    /** Column name EDI_BPartner_Config_ID */
    public static final String COLUMNNAME_EDI_BPartner_Config_ID = "EDI_BPartner_Config_ID";

	/**
	 * Set "CU pro TU" bei unbestimmter Verpackungskapazität.
	 * "CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DefaultItemCapacity (java.math.BigDecimal EDI_DefaultItemCapacity);

	/**
	 * Get "CU pro TU" bei unbestimmter Verpackungskapazität.
	 * "CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEDI_DefaultItemCapacity();

    /** Column definition for EDI_DefaultItemCapacity */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_EDI_DefaultItemCapacity = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "EDI_DefaultItemCapacity", null);
    /** Column name EDI_DefaultItemCapacity */
    public static final String COLUMNNAME_EDI_DefaultItemCapacity = "EDI_DefaultItemCapacity";

	/**
	 * Set EDI-GLN des Partners .
	 * Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEdiPartnerIdentification (java.lang.String EdiPartnerIdentification);

	/**
	 * Get EDI-GLN des Partners .
	 * Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEdiPartnerIdentification();

    /** Column definition for EdiPartnerIdentification */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_EdiPartnerIdentification = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "EdiPartnerIdentification", null);
    /** Column name EdiPartnerIdentification */
    public static final String COLUMNNAME_EdiPartnerIdentification = "EdiPartnerIdentification";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Erhält DESADV.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDesadvRecipient (boolean IsDesadvRecipient);

	/**
	 * Get Erhält DESADV.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDesadvRecipient();

    /** Column definition for IsDesadvRecipient */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_IsDesadvRecipient = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "IsDesadvRecipient", null);
    /** Column name IsDesadvRecipient */
    public static final String COLUMNNAME_IsDesadvRecipient = "IsDesadvRecipient";

	/**
	 * Set Erhält EDI-Belege.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEdiRecipient (boolean IsEdiRecipient);

	/**
	 * Get Erhält EDI-Belege.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEdiRecipient();

    /** Column definition for IsEdiRecipient */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_IsEdiRecipient = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "IsEdiRecipient", null);
    /** Column name IsEdiRecipient */
    public static final String COLUMNNAME_IsEdiRecipient = "IsEdiRecipient";

	/**
	 * Set Erhält INVOIC.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvoicRecipient (boolean IsInvoicRecipient);

	/**
	 * Get Erhält INVOIC.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvoicRecipient();

    /** Column definition for IsInvoicRecipient */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_IsInvoicRecipient = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "IsInvoicRecipient", null);
    /** Column name IsInvoicRecipient */
    public static final String COLUMNNAME_IsInvoicRecipient = "IsInvoicRecipient";

	/**
	 * Set Erhält ORDRSP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOrdrspRecipient (boolean IsOrdrspRecipient);

	/**
	 * Get Erhält ORDRSP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOrdrspRecipient();

    /** Column definition for IsOrdrspRecipient */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_IsOrdrspRecipient = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "IsOrdrspRecipient", null);
    /** Column name IsOrdrspRecipient */
    public static final String COLUMNNAME_IsOrdrspRecipient = "IsOrdrspRecipient";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, org.compiere.model.I_AD_User>(I_EDI_BPartner_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_EDI_BPartner_Config, Object>(I_EDI_BPartner_Config.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
