package de.metas.ui.web.base.model;


/** Generated Interface for WEBUI_Dashboard_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_WEBUI_Dashboard_Access 
{

    /** TableName=WEBUI_Dashboard_Access */
    public static final String Table_Name = "WEBUI_Dashboard_Access";

    /** AD_Table_ID=540830 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_Client>(I_WEBUI_Dashboard_Access.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_Org>(I_WEBUI_Dashboard_Access.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User>(I_WEBUI_Dashboard_Access.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User>(I_WEBUI_Dashboard_Access.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set All users.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllUsers (boolean IsAllUsers);

	/**
	 * Get All users.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllUsers();

    /** Column definition for IsAllUsers */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_IsAllUsers = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "IsAllUsers", null);
    /** Column name IsAllUsers */
    public static final String COLUMNNAME_IsAllUsers = "IsAllUsers";

	/**
	 * Set Lesen und Schreiben.
	 * Feld / Eintrag / Bereich kann gelesen und verändert werden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadWrite (boolean IsReadWrite);

	/**
	 * Get Lesen und Schreiben.
	 * Feld / Eintrag / Bereich kann gelesen und verändert werden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadWrite();

    /** Column definition for IsReadWrite */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_IsReadWrite = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "IsReadWrite", null);
    /** Column name IsReadWrite */
    public static final String COLUMNNAME_IsReadWrite = "IsReadWrite";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, org.compiere.model.I_AD_User>(I_WEBUI_Dashboard_Access.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Dashboard access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Dashboard_Access_ID (int WEBUI_Dashboard_Access_ID);

	/**
	 * Get Dashboard access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Dashboard_Access_ID();

    /** Column definition for WEBUI_Dashboard_Access_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object> COLUMN_WEBUI_Dashboard_Access_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, Object>(I_WEBUI_Dashboard_Access.class, "WEBUI_Dashboard_Access_ID", null);
    /** Column name WEBUI_Dashboard_Access_ID */
    public static final String COLUMNNAME_WEBUI_Dashboard_Access_ID = "WEBUI_Dashboard_Access_ID";

	/**
	 * Set Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Dashboard_ID (int WEBUI_Dashboard_ID);

	/**
	 * Get Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Dashboard_ID();

	public de.metas.ui.web.base.model.I_WEBUI_Dashboard getWEBUI_Dashboard();

	public void setWEBUI_Dashboard(de.metas.ui.web.base.model.I_WEBUI_Dashboard WEBUI_Dashboard);

    /** Column definition for WEBUI_Dashboard_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, de.metas.ui.web.base.model.I_WEBUI_Dashboard> COLUMN_WEBUI_Dashboard_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Dashboard_Access, de.metas.ui.web.base.model.I_WEBUI_Dashboard>(I_WEBUI_Dashboard_Access.class, "WEBUI_Dashboard_ID", de.metas.ui.web.base.model.I_WEBUI_Dashboard.class);
    /** Column name WEBUI_Dashboard_ID */
    public static final String COLUMNNAME_WEBUI_Dashboard_ID = "WEBUI_Dashboard_ID";
}
