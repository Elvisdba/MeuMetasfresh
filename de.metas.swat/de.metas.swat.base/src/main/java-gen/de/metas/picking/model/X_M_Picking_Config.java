/** Generated Model - DO NOT CHANGE */
package de.metas.picking.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Picking_Config extends org.compiere.model.PO implements I_M_Picking_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1469592117L;

    /** Standard Constructor */
    public X_M_Picking_Config (Properties ctx, int M_Picking_Config_ID, String trxName)
    {
      super (ctx, M_Picking_Config_ID, trxName);
      /** if (M_Picking_Config_ID == 0)
        {
			setM_Picking_Config_ID (0);
			setWEBUI_PickingTerminal_ViewProfile (null);
        } */
    }

    /** Load Constructor */
    public X_M_Picking_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Picking configuration.
		@param M_Picking_Config_ID Picking configuration	  */
	@Override
	public void setM_Picking_Config_ID (int M_Picking_Config_ID)
	{
		if (M_Picking_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_ID, Integer.valueOf(M_Picking_Config_ID));
	}

	/** Get Picking configuration.
		@return Picking configuration	  */
	@Override
	public int getM_Picking_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Picking_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * WEBUI_PickingTerminal_ViewProfile AD_Reference_ID=540772
	 * Reference name: WEBUI_PickingTerminal_ViewProfile
	 */
	public static final int WEBUI_PICKINGTERMINAL_VIEWPROFILE_AD_Reference_ID=540772;
	/** groupByProduct = groupByProduct */
	public static final String WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByProduct = "groupByProduct";
	/** Group by Order = groupByOrder */
	public static final String WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByOrder = "groupByOrder";
	/** Set Picking terminal view profile.
		@param WEBUI_PickingTerminal_ViewProfile Picking terminal view profile	  */
	@Override
	public void setWEBUI_PickingTerminal_ViewProfile (java.lang.String WEBUI_PickingTerminal_ViewProfile)
	{

		set_Value (COLUMNNAME_WEBUI_PickingTerminal_ViewProfile, WEBUI_PickingTerminal_ViewProfile);
	}

	/** Get Picking terminal view profile.
		@return Picking terminal view profile	  */
	@Override
	public java.lang.String getWEBUI_PickingTerminal_ViewProfile () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_PickingTerminal_ViewProfile);
	}
}