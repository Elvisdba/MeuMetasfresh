package org.compiere.grid.ed.menu;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ui.AbstractContextMenuAction;
import org.compiere.grid.ed.VBPartner;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Lookup;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;

/**
 * @deprecated Scheduled to be removed because it's no longer used
 */
@Deprecated
abstract class BPartnerNewUpdateContextEditorAction extends AbstractContextMenuAction
{
	private final boolean createNew;

	public BPartnerNewUpdateContextEditorAction(boolean createNew)
	{
		super();
		this.createNew = createNew;
	}

	@Override
	public String getIcon()
	{
		return "InfoBPartner16";
	}

	@Override
	public boolean isAvailable()
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		if (!MSysConfig.getBooleanValue("UI_EnableBPartnerContextMenu", true, adClientId))
		{
			return false;
		}

		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();
		if (gridField == null)
		{
			return false;
		}
		
		if (!gridField.isLookup())
		{
			return false;
		}
		
		final Lookup lookup = gridField.getLookup();
		if (lookup == null)
		{
			// No Lookup???
			log.warn("No lookup found for " + gridField + " even if is marked as Lookup");
			return false;
		}
		
		final String tableName = lookup.getTableName();
		if (!I_C_BPartner.Table_Name.equals(tableName))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isRunnable()
	{
		final VEditor editor = getEditor();
		return editor.isReadWrite();
	}

	@Override
	public void run()
	{
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();
		final Lookup lookup = gridField.getLookup();
		final int windowNo = gridField.getWindowNo();

		final VBPartner vbp = new VBPartner(Env.getWindow(windowNo), windowNo);
		int BPartner_ID = 0;
		// if update, get current value
		if (!createNew)
		{
			final Object value = editor.getValue();
			if (value instanceof Integer)
				BPartner_ID = ((Integer)value).intValue();
			else if (value != null)
				BPartner_ID = Integer.parseInt(value.toString());
		}

		vbp.loadBPartner(BPartner_ID);
		vbp.setVisible(true);
		// get result
		int result = vbp.getC_BPartner_ID();
		if (result == 0 // 0 = not saved
				&& result == BPartner_ID) // the same
			return;
		// Maybe new BPartner - put in cache
		lookup.getDirect(IValidationContext.NULL, new Integer(result), false, true);

		// actionCombo (new Integer(result)); // data binding
		gridField.getGridTab().setValue(gridField, result);
	} // actionBPartner
}
