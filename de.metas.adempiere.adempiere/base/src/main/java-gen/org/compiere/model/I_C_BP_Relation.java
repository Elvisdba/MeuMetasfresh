package org.compiere.model;


/** Generated Interface for C_BP_Relation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BP_Relation 
{

    /** TableName=C_BP_Relation */
    public static final String Table_Name = "C_BP_Relation";

    /** AD_Table_ID=678 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_Client>(I_C_BP_Relation.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_Org>(I_C_BP_Relation.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Beziehungen Geschäftspartner.
	 * Business Partner Relation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Relation_ID (int C_BP_Relation_ID);

	/**
	 * Get Beziehungen Geschäftspartner.
	 * Business Partner Relation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Relation_ID();

    /** Column definition for C_BP_Relation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_C_BP_Relation_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "C_BP_Relation_ID", null);
    /** Column name C_BP_Relation_ID */
    public static final String COLUMNNAME_C_BP_Relation_ID = "C_BP_Relation_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner>(I_C_BP_Relation.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner_Location>(I_C_BP_Relation.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Zugehöriger Geschäftspartner.
	 * Related Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID);

	/**
	 * Get Zugehöriger Geschäftspartner.
	 * Related Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartnerRelation_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerRelation();

	public void setC_BPartnerRelation(org.compiere.model.I_C_BPartner C_BPartnerRelation);

    /** Column definition for C_BPartnerRelation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner> COLUMN_C_BPartnerRelation_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner>(I_C_BP_Relation.class, "C_BPartnerRelation_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartnerRelation_ID */
    public static final String COLUMNNAME_C_BPartnerRelation_ID = "C_BPartnerRelation_ID";

	/**
	 * Set Zugehöriger Standort.
	 * Location of the related Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartnerRelation_Location_ID (int C_BPartnerRelation_Location_ID);

	/**
	 * Get Zugehöriger Standort.
	 * Location of the related Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartnerRelation_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartnerRelation_Location();

	public void setC_BPartnerRelation_Location(org.compiere.model.I_C_BPartner_Location C_BPartnerRelation_Location);

    /** Column definition for C_BPartnerRelation_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartnerRelation_Location_ID = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_C_BPartner_Location>(I_C_BP_Relation.class, "C_BPartnerRelation_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartnerRelation_Location_ID */
    public static final String COLUMNNAME_C_BPartnerRelation_Location_ID = "C_BPartnerRelation_Location_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_User>(I_C_BP_Relation.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillTo();

    /** Column definition for IsBillTo */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsBillTo = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsBillTo", null);
    /** Column name IsBillTo */
    public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Abladeort.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsHandOverLocation (boolean IsHandOverLocation);

	/**
	 * Get Abladeort.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHandOverLocation();

    /** Column definition for IsHandOverLocation */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsHandOverLocation = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsHandOverLocation", null);
    /** Column name IsHandOverLocation */
    public static final String COLUMNNAME_IsHandOverLocation = "IsHandOverLocation";

	/**
	 * Set Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPayFrom (boolean IsPayFrom);

	/**
	 * Get Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPayFrom();

    /** Column definition for IsPayFrom */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsPayFrom = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsPayFrom", null);
    /** Column name IsPayFrom */
    public static final String COLUMNNAME_IsPayFrom = "IsPayFrom";

	/**
	 * Set Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRemitTo (boolean IsRemitTo);

	/**
	 * Get Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRemitTo();

    /** Column definition for IsRemitTo */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsRemitTo = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsRemitTo", null);
    /** Column name IsRemitTo */
    public static final String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/**
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipTo();

    /** Column definition for IsShipTo */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_IsShipTo = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "IsShipTo", null);
    /** Column name IsShipTo */
    public static final String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BP_Relation, Object>(I_C_BP_Relation.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BP_Relation, org.compiere.model.I_AD_User>(I_C_BP_Relation.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
