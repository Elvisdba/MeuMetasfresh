/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Dashboard_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Dashboard_Access extends org.compiere.model.PO implements I_WEBUI_Dashboard_Access, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -30160032L;

    /** Standard Constructor */
    public X_WEBUI_Dashboard_Access (Properties ctx, int WEBUI_Dashboard_Access_ID, String trxName)
    {
      super (ctx, WEBUI_Dashboard_Access_ID, trxName);
      /** if (WEBUI_Dashboard_Access_ID == 0)
        {
			setIsAllUsers (false); // N
			setIsReadWrite (false); // N
			setWEBUI_Dashboard_Access_ID (0);
			setWEBUI_Dashboard_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Dashboard_Access (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set All users.
		@param IsAllUsers All users	  */
	@Override
	public void setIsAllUsers (boolean IsAllUsers)
	{
		set_ValueNoCheck (COLUMNNAME_IsAllUsers, Boolean.valueOf(IsAllUsers));
	}

	/** Get All users.
		@return All users	  */
	@Override
	public boolean isAllUsers () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllUsers);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lesen und Schreiben.
		@param IsReadWrite 
		Feld / Eintrag / Bereich kann gelesen und verändert werden
	  */
	@Override
	public void setIsReadWrite (boolean IsReadWrite)
	{
		set_Value (COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
	}

	/** Get Lesen und Schreiben.
		@return Feld / Eintrag / Bereich kann gelesen und verändert werden
	  */
	@Override
	public boolean isReadWrite () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadWrite);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Dashboard access.
		@param WEBUI_Dashboard_Access_ID Dashboard access	  */
	@Override
	public void setWEBUI_Dashboard_Access_ID (int WEBUI_Dashboard_Access_ID)
	{
		if (WEBUI_Dashboard_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_Access_ID, Integer.valueOf(WEBUI_Dashboard_Access_ID));
	}

	/** Get Dashboard access.
		@return Dashboard access	  */
	@Override
	public int getWEBUI_Dashboard_Access_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Dashboard_Access_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Dashboard getWEBUI_Dashboard() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Dashboard_ID, de.metas.ui.web.base.model.I_WEBUI_Dashboard.class);
	}

	@Override
	public void setWEBUI_Dashboard(de.metas.ui.web.base.model.I_WEBUI_Dashboard WEBUI_Dashboard)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Dashboard_ID, de.metas.ui.web.base.model.I_WEBUI_Dashboard.class, WEBUI_Dashboard);
	}

	/** Set Dashboard.
		@param WEBUI_Dashboard_ID Dashboard	  */
	@Override
	public void setWEBUI_Dashboard_ID (int WEBUI_Dashboard_ID)
	{
		if (WEBUI_Dashboard_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, Integer.valueOf(WEBUI_Dashboard_ID));
	}

	/** Get Dashboard.
		@return Dashboard	  */
	@Override
	public int getWEBUI_Dashboard_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Dashboard_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}