package org.compiere.model;

import java.util.Properties;

@SuppressWarnings("serial")
public class MBPGroup extends X_C_BP_Group
{
	public MBPGroup(final Properties ctx, final int C_BP_Group_ID, final String trxName)
	{
		super(ctx, C_BP_Group_ID, trxName);
		if (C_BP_Group_ID == 0)
		{
			// setValue (null);
			// setName (null);
			setIsConfidentialInfo(false);	// N
			setIsDefault(false);
			setPriorityBase(PRIORITYBASE_Same);
		}
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (newRecord && success)
		{
			return insert_Accounting(I_C_BP_Group_Acct.Table_Name, I_C_AcctSchema_Default.Table_Name, null);
		}
		return success;
	}

	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting(I_C_BP_Group_Acct.Table_Name);
	}
}
