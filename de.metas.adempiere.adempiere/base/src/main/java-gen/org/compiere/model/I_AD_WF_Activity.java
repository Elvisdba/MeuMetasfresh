package org.compiere.model;


/** Generated Interface for AD_WF_Activity
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_WF_Activity 
{

    /** TableName=AD_WF_Activity */
    public static final String Table_Name = "AD_WF_Activity";

    /** AD_Table_ID=644 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Client>(I_AD_WF_Activity.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Meldung.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Meldung.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Message_ID();

	public org.compiere.model.I_AD_Message getAD_Message();

	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message);

    /** Column definition for AD_Message_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Message> COLUMN_AD_Message_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Message>(I_AD_WF_Activity.class, "AD_Message_ID", org.compiere.model.I_AD_Message.class);
    /** Column name AD_Message_ID */
    public static final String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Org>(I_AD_WF_Activity.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Table>(I_AD_WF_Activity.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User>(I_AD_WF_Activity.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Activity_ID (int AD_WF_Activity_ID);

	/**
	 * Get Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Activity_ID();

    /** Column definition for AD_WF_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_AD_WF_Activity_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "AD_WF_Activity_ID", null);
    /** Column name AD_WF_Activity_ID */
    public static final String COLUMNNAME_AD_WF_Activity_ID = "AD_WF_Activity_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node();

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Node>(I_AD_WF_Activity.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Process_ID (int AD_WF_Process_ID);

	/**
	 * Get Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Process_ID();

	public org.compiere.model.I_AD_WF_Process getAD_WF_Process();

	public void setAD_WF_Process(org.compiere.model.I_AD_WF_Process AD_WF_Process);

    /** Column definition for AD_WF_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Process> COLUMN_AD_WF_Process_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Process>(I_AD_WF_Activity.class, "AD_WF_Process_ID", org.compiere.model.I_AD_WF_Process.class);
    /** Column name AD_WF_Process_ID */
    public static final String COLUMNNAME_AD_WF_Process_ID = "AD_WF_Process_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible();

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column definition for AD_WF_Responsible_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_WF_Responsible>(I_AD_WF_Activity.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_Workflow>(I_AD_WF_Activity.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User>(I_AD_WF_Activity.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Letzte Benachrichtigung.
	 * Date when last alert were sent
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastAlert (java.sql.Timestamp DateLastAlert);

	/**
	 * Get Letzte Benachrichtigung.
	 * Date when last alert were sent
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastAlert();

    /** Column definition for DateLastAlert */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_DateLastAlert = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "DateLastAlert", null);
    /** Column name DateLastAlert */
    public static final String COLUMNNAME_DateLastAlert = "DateLastAlert";

	/**
	 * Set Dyn Priority Start.
	 * Starting priority before changed dynamically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDynPriorityStart (int DynPriorityStart);

	/**
	 * Get Dyn Priority Start.
	 * Starting priority before changed dynamically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDynPriorityStart();

    /** Column definition for DynPriorityStart */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_DynPriorityStart = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "DynPriorityStart", null);
    /** Column name DynPriorityStart */
    public static final String COLUMNNAME_DynPriorityStart = "DynPriorityStart";

	/**
	 * Set End Wait.
	 * End of sleep time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEndWaitTime (java.sql.Timestamp EndWaitTime);

	/**
	 * Get End Wait.
	 * End of sleep time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEndWaitTime();

    /** Column definition for EndWaitTime */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_EndWaitTime = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "EndWaitTime", null);
    /** Column name EndWaitTime */
    public static final String COLUMNNAME_EndWaitTime = "EndWaitTime";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriority (int Priority);

	/**
	 * Get Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTextMsg (java.lang.String TextMsg);

	/**
	 * Get Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTextMsg();

    /** Column definition for TextMsg */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_TextMsg = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "TextMsg", null);
    /** Column name TextMsg */
    public static final String COLUMNNAME_TextMsg = "TextMsg";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, org.compiere.model.I_AD_User>(I_AD_WF_Activity.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Workflow State.
	 * State of the execution of the workflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWFState (java.lang.String WFState);

	/**
	 * Get Workflow State.
	 * State of the execution of the workflow
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWFState();

    /** Column definition for WFState */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object> COLUMN_WFState = new org.adempiere.model.ModelColumn<I_AD_WF_Activity, Object>(I_AD_WF_Activity.class, "WFState", null);
    /** Column name WFState */
    public static final String COLUMNNAME_WFState = "WFState";
}
