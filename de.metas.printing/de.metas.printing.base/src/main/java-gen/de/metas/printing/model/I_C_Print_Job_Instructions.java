package de.metas.printing.model;


/** Generated Interface for C_Print_Job_Instructions
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Print_Job_Instructions 
{

    /** TableName=C_Print_Job_Instructions */
    public static final String Table_Name = "C_Print_Job_Instructions";

    /** AD_Table_ID=540473 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_Client>(I_C_Print_Job_Instructions.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_Org>(I_C_Print_Job_Instructions.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_ID();

	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

    /** Column definition for AD_PrinterHW_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_AD_PrinterHW>(I_C_Print_Job_Instructions.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
    /** Column name AD_PrinterHW_ID */
    public static final String COLUMNNAME_AD_PrinterHW_ID = "AD_PrinterHW_ID";

	/**
	 * Set Ausdruck für.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID);

	/**
	 * Get Ausdruck für.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ToPrint_ID();

	public org.compiere.model.I_AD_User getAD_User_ToPrint();

	public void setAD_User_ToPrint(org.compiere.model.I_AD_User AD_User_ToPrint);

    /** Column definition for AD_User_ToPrint_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User> COLUMN_AD_User_ToPrint_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User>(I_C_Print_Job_Instructions.class, "AD_User_ToPrint_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ToPrint_ID */
    public static final String COLUMNNAME_AD_User_ToPrint_ID = "AD_User_ToPrint_ID";

	/**
	 * Set Druck-Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_ID (int C_Print_Job_ID);

	/**
	 * Get Druck-Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_ID();

	public de.metas.printing.model.I_C_Print_Job getC_Print_Job();

	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job);

    /** Column definition for C_Print_Job_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job> COLUMN_C_Print_Job_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job>(I_C_Print_Job_Instructions.class, "C_Print_Job_ID", de.metas.printing.model.I_C_Print_Job.class);
    /** Column name C_Print_Job_ID */
    public static final String COLUMNNAME_C_Print_Job_ID = "C_Print_Job_ID";

	/**
	 * Set Druck-Job Anweisung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID);

	/**
	 * Get Druck-Job Anweisung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Instructions_ID();

    /** Column definition for C_Print_Job_Instructions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_C_Print_Job_Instructions_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "C_Print_Job_Instructions_ID", null);
    /** Column name C_Print_Job_Instructions_ID */
    public static final String COLUMNNAME_C_Print_Job_Instructions_ID = "C_Print_Job_Instructions_ID";

	/**
	 * Set Print job line from.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PrintJob_Line_From_ID (int C_PrintJob_Line_From_ID);

	/**
	 * Get Print job line from.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PrintJob_Line_From_ID();

	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_From();

	public void setC_PrintJob_Line_From(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_From);

    /** Column definition for C_PrintJob_Line_From_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job_Line> COLUMN_C_PrintJob_Line_From_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job_Line>(I_C_Print_Job_Instructions.class, "C_PrintJob_Line_From_ID", de.metas.printing.model.I_C_Print_Job_Line.class);
    /** Column name C_PrintJob_Line_From_ID */
    public static final String COLUMNNAME_C_PrintJob_Line_From_ID = "C_PrintJob_Line_From_ID";

	/**
	 * Set Print job line to.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PrintJob_Line_To_ID (int C_PrintJob_Line_To_ID);

	/**
	 * Get Print job line to.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PrintJob_Line_To_ID();

	public de.metas.printing.model.I_C_Print_Job_Line getC_PrintJob_Line_To();

	public void setC_PrintJob_Line_To(de.metas.printing.model.I_C_Print_Job_Line C_PrintJob_Line_To);

    /** Column definition for C_PrintJob_Line_To_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job_Line> COLUMN_C_PrintJob_Line_To_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, de.metas.printing.model.I_C_Print_Job_Line>(I_C_Print_Job_Instructions.class, "C_PrintJob_Line_To_ID", de.metas.printing.model.I_C_Print_Job_Line.class);
    /** Column name C_PrintJob_Line_To_ID */
    public static final String COLUMNNAME_C_PrintJob_Line_To_ID = "C_PrintJob_Line_To_ID";

	/**
	 * Set Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCopies (int Copies);

	/**
	 * Get Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCopies();

    /** Column definition for Copies */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_Copies = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "Copies", null);
    /** Column name Copies */
    public static final String COLUMNNAME_Copies = "Copies";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User>(I_C_Print_Job_Instructions.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHostKey (java.lang.String HostKey);

	/**
	 * Get Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHostKey();

    /** Column definition for HostKey */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_HostKey = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "HostKey", null);
    /** Column name HostKey */
    public static final String COLUMNNAME_HostKey = "HostKey";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, org.compiere.model.I_AD_User>(I_C_Print_Job_Instructions.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set UserOK.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserOK (boolean UserOK);

	/**
	 * Get UserOK.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUserOK();

    /** Column definition for UserOK */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object> COLUMN_UserOK = new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions, Object>(I_C_Print_Job_Instructions.class, "UserOK", null);
    /** Column name UserOK */
    public static final String COLUMNNAME_UserOK = "UserOK";
}
