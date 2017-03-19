/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_WF_Activity
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_WF_Activity extends org.compiere.model.PO implements I_AD_WF_Activity, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1284750267L;

    /** Standard Constructor */
    public X_AD_WF_Activity (Properties ctx, int AD_WF_Activity_ID, String trxName)
    {
      super (ctx, AD_WF_Activity_ID, trxName);
      /** if (AD_WF_Activity_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_WF_Activity_ID (0);
			setAD_WF_Node_ID (0);
			setAD_WF_Process_ID (0);
			setAD_Workflow_ID (0);
			setProcessed (false);
			setRecord_ID (0);
			setWFState (null);
        } */
    }

    /** Load Constructor */
    public X_AD_WF_Activity (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Message getAD_Message() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class);
	}

	@Override
	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message)
	{
		set_ValueFromPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class, AD_Message);
	}

	/** Set Meldung.
		@param AD_Message_ID 
		System Message
	  */
	@Override
	public void setAD_Message_ID (int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, Integer.valueOf(AD_Message_ID));
	}

	/** Get Meldung.
		@return System Message
	  */
	@Override
	public int getAD_Message_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Message_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Workflow-Aktivität.
		@param AD_WF_Activity_ID 
		Workflow Activity
	  */
	@Override
	public void setAD_WF_Activity_ID (int AD_WF_Activity_ID)
	{
		if (AD_WF_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, Integer.valueOf(AD_WF_Activity_ID));
	}

	/** Get Workflow-Aktivität.
		@return Workflow Activity
	  */
	@Override
	public int getAD_WF_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Node getAD_WF_Node() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class);
	}

	@Override
	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class, AD_WF_Node);
	}

	/** Set Knoten.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
	}

	/** Get Knoten.
		@return Workflow Node (activity), step or process
	  */
	@Override
	public int getAD_WF_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Process getAD_WF_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Process_ID, org.compiere.model.I_AD_WF_Process.class);
	}

	@Override
	public void setAD_WF_Process(org.compiere.model.I_AD_WF_Process AD_WF_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Process_ID, org.compiere.model.I_AD_WF_Process.class, AD_WF_Process);
	}

	/** Set Workflow-Prozess.
		@param AD_WF_Process_ID 
		Actual Workflow Process Instance
	  */
	@Override
	public void setAD_WF_Process_ID (int AD_WF_Process_ID)
	{
		if (AD_WF_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, Integer.valueOf(AD_WF_Process_ID));
	}

	/** Get Workflow-Prozess.
		@return Actual Workflow Process Instance
	  */
	@Override
	public int getAD_WF_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class);
	}

	@Override
	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class, AD_WF_Responsible);
	}

	/** Set Workflow - Verantwortlicher.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow - Verantwortlicher.
		@return Responsible for Workflow Execution
	  */
	@Override
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	@Override
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Letzte Benachrichtigung.
		@param DateLastAlert 
		Date when last alert were sent
	  */
	@Override
	public void setDateLastAlert (java.sql.Timestamp DateLastAlert)
	{
		set_Value (COLUMNNAME_DateLastAlert, DateLastAlert);
	}

	/** Get Letzte Benachrichtigung.
		@return Date when last alert were sent
	  */
	@Override
	public java.sql.Timestamp getDateLastAlert () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastAlert);
	}

	/** Set Dyn Priority Start.
		@param DynPriorityStart 
		Starting priority before changed dynamically
	  */
	@Override
	public void setDynPriorityStart (int DynPriorityStart)
	{
		set_Value (COLUMNNAME_DynPriorityStart, Integer.valueOf(DynPriorityStart));
	}

	/** Get Dyn Priority Start.
		@return Starting priority before changed dynamically
	  */
	@Override
	public int getDynPriorityStart () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DynPriorityStart);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set End Wait.
		@param EndWaitTime 
		End of sleep time
	  */
	@Override
	public void setEndWaitTime (java.sql.Timestamp EndWaitTime)
	{
		set_Value (COLUMNNAME_EndWaitTime, EndWaitTime);
	}

	/** Get End Wait.
		@return End of sleep time
	  */
	@Override
	public java.sql.Timestamp getEndWaitTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndWaitTime);
	}

	/** Set Priorität.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public void setPriority (int Priority)
	{
		set_Value (COLUMNNAME_Priority, Integer.valueOf(Priority));
	}

	/** Get Priorität.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public int getPriority () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Priority);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mitteilung.
		@param TextMsg 
		Text Message
	  */
	@Override
	public void setTextMsg (java.lang.String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Mitteilung.
		@return Text Message
	  */
	@Override
	public java.lang.String getTextMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TextMsg);
	}

	/** 
	 * WFState AD_Reference_ID=305
	 * Reference name: WF_Instance State
	 */
	public static final int WFSTATE_AD_Reference_ID=305;
	/** NotStarted = ON */
	public static final String WFSTATE_NotStarted = "ON";
	/** Running = OR */
	public static final String WFSTATE_Running = "OR";
	/** Suspended = OS */
	public static final String WFSTATE_Suspended = "OS";
	/** Completed = CC */
	public static final String WFSTATE_Completed = "CC";
	/** Aborted = CA */
	public static final String WFSTATE_Aborted = "CA";
	/** Terminated = CT */
	public static final String WFSTATE_Terminated = "CT";
	/** Set Workflow State.
		@param WFState 
		State of the execution of the workflow
	  */
	@Override
	public void setWFState (java.lang.String WFState)
	{

		set_Value (COLUMNNAME_WFState, WFState);
	}

	/** Get Workflow State.
		@return State of the execution of the workflow
	  */
	@Override
	public java.lang.String getWFState () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WFState);
	}
}