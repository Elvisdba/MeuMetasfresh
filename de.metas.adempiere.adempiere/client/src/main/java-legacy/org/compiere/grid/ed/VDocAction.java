/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_Order;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.wf.MWFActivity;
import org.slf4j.Logger;

import com.google.common.base.Objects;

import de.metas.adempiere.form.IClientUI;
import de.metas.document.engine.DefaultDocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;

/**
 * Displays valid Document Action Options based on context
 *
 * @author Jorg Janke
 * @version $Id: VDocAction.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VDocAction extends CDialog
		implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7800828832602048052L;

	/**
	 * Constructor.
	 * 
	 * Please note, in case there is a failure, a popup will be displayed but this constructor will never fail.
	 * To check if this dialog is valid, use {@link #getNumberOfOptions()}.
	 * 
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param Record_ID record id
	 */
	public VDocAction(final int WindowNo, final GridTab mTab, final int Record_ID)
	{
		super(Env.getWindow(WindowNo), Services.get(IMsgBL.class).translate(Env.getCtx(), "DocAction"), true);
		log.info("");
		m_WindowNo = WindowNo;
		m_mTab = mTab;
		m_AD_Table_ID = mTab.getAD_Table_ID();

		//
		try
		{
			jbInit();

			//
			dynInit(Record_ID);
			//
			AEnv.positionCenterWindow(Env.getWindow(WindowNo), this);
		}
		catch (Exception ex)
		{
			Services.get(IClientUI.class).error(WindowNo, ex);
		}
	}	// VDocAction
	
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(VDocAction.class);

	//
	private final int m_WindowNo;
	private final int m_AD_Table_ID;
	private final GridTab m_mTab;

	private boolean m_OKpressed = false;
	private Map<String, DocActionItem> docActionItemsByValue = null; // lazy

	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel northPanel = new CPanel();
	private CComboBox<DocActionItem> actionCombo = new CComboBox<>();
	private JLabel actionLabel = new JLabel();
	private JScrollPane centerPane = new JScrollPane();
	private JTextArea message = new JTextArea();
	private FlowLayout northLayout = new FlowLayout();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception
	{
		mainPanel.setLayout(mainLayout);
		actionLabel.setText(Services.get(IMsgBL.class).translate(Env.getCtx(), "DocAction"));
		actionCombo.addActionListener(this);
		// task 09797 The ComboBox for DocAction shall not have Autocomplete
		actionCombo.disableAutoCompletion();
		message.setLineWrap(true);
		message.setPreferredSize(new Dimension(350, 35));
		message.setWrapStyleWord(true);
		message.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		message.setEditable(false);
		northPanel.setLayout(northLayout);
		northLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(mainPanel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(actionLabel, null);
		northPanel.add(actionCombo, null);
		mainPanel.add(centerPane, BorderLayout.CENTER);
		centerPane.getViewport().add(message, null);
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	// jbInit

	/**
	 * Dynamic Init - determine valid DocActions based on DocStatus for the different documents.
	 * 
	 * <pre>
	 *  DocStatus (131)
	 * 			??                         Unknown
	 * 			AP *                       Approved
	 * 			CH                         Changed
	 * 			CL *                       Closed
	 * 			CO *                       Completed
	 * 			DR                         Drafted
	 * 			IN                         Inactive
	 * 			NA                         Not Approved
	 * 			PE                         Posting Error
	 * 			PO *                       Posted
	 * 			PR *                       Printed
	 * 			RE                         Reversed
	 * 			TE                         Transfer Error
	 * 			TR *                       Transferred
	 * 			VO *                       Voided
	 * 			XX                         Being Processed
	 * 
	 *   DocAction (135)
	 * 			--                         <None>
	 * 			AP *                       Approve
	 * 			CL *                       Close
	 * 			CO *                       Complete
	 * 			PO *                       Post
	 * 			PR *                       Print
	 * 			RA                         Reverse - Accrual
	 * 			RC                         Reverse - Correction
	 * 			RE                         RE-activate
	 * 			RJ                         Reject
	 * 			TR *                       Transfer
	 * 			VO *                       Void
	 * 			XL                         Unlock
	 * </pre>
	 * 
	 * @param Record_ID id
	 */
	private void dynInit(int Record_ID)
	{
		String DocStatus = (String)m_mTab.getValue("DocStatus");
		String DocAction = (String)m_mTab.getValue("DocAction");
		//
		final boolean Processing = DisplayType.toBoolean(m_mTab.getValue("Processing"));
		final String OrderType = Env.getContext(Env.getCtx(), m_WindowNo, "OrderType");
		final boolean IsSOTrx = DisplayType.toBoolean(Env.getContext(Env.getCtx(), m_WindowNo, "IsSOTrx"));

		if (DocStatus == null)
		{
			message.setText("*** ERROR ***");
			return;
		}

		log.debug("DocStatus=" + DocStatus
				+ ", DocAction=" + DocAction + ", OrderType=" + OrderType
				+ ", IsSOTrx=" + IsSOTrx + ", Processing=" + Processing
				+ ", AD_Table_ID=" + m_AD_Table_ID + ", Record_ID=" + Record_ID);

		/**
		 * Check Existence of Workflow Activities
		 */
		final String wfStatus = MWFActivity.getActiveInfo(Env.getCtx(), m_AD_Table_ID, Record_ID);
		if (wfStatus != null)
		{
			ADialog.error(m_WindowNo, this, "WFActiveForRecord", wfStatus);
			return;
		}

		// Status Change
		if (!checkStatus(m_mTab.getTableName(), Record_ID, DocStatus))
		{
			ADialog.error(m_WindowNo, this, "DocumentStatusChanged");
			return;
		}

		/*******************
		 * General Actions
		 */
		final Set<String> docActions;
		{
			Integer docTypeId = (Integer)m_mTab.getValue("C_DocType_ID");
			if (docTypeId == null || docTypeId.intValue() <= 0)
			{
				docTypeId = (Integer)m_mTab.getValue("C_DocTypeTarget_ID");
			}
			log.debug("get doctype: " + docTypeId);

			final Properties ctx = Env.getCtx();
			final IDocActionOptionsContext optionsCtx = DefaultDocActionOptionsContext.builder(ctx)
					.setTableName(m_mTab.getTableName())
					.setDocStatus(DocStatus)
					.setC_DocType_ID(docTypeId)
					.setProcessing(Processing)
					.setOrderType(OrderType)
					.setIsSOTrx(IsSOTrx)
					.build();
			Services.get(IDocActionOptionsBL.class).updateDocActions(optionsCtx);
			docActions = optionsCtx.getDocActions();

			// metas
			// DocAction = optionsCtx.getDocActionToUse();
		}

		/**
		 * Fill actionCombo
		 */
		final Map<String, DocActionItem> docActionItems = getDocActionItemsIndexedByValue();
		for (final String docAction : docActions)
		{
			final DocActionItem docActionItem = docActionItems.get(docAction);
			if (docActionItem == null)
			{
				// shall not happen
				continue;
			}

			actionCombo.addItem(docActionItem);
		}

		// setDefault
		if (DocAction.equals("--")) // If None, suggest closing
		{
			DocAction = org.compiere.process.DocAction.ACTION_Close;
		}

		final DocActionItem defaultDocActionItem = docActionItems.get(DocAction);
		if (defaultDocActionItem != null)
		{
			actionCombo.setSelectedItem(defaultDocActionItem);
		}
	}	// dynInit

	/**
	 * Check Status Change
	 *
	 * @param TableName table name
	 * @param Record_ID record
	 * @param DocStatus current doc status
	 * @return true if status not changed
	 */
	private boolean checkStatus(String TableName, int Record_ID, String DocStatus)
	{
		String sql = "SELECT 2 FROM " + TableName
				+ " WHERE " + TableName + "_ID=" + Record_ID
				+ " AND DocStatus='" + DocStatus + "'";
		int result = DB.getSQLValue(null, sql);
		return result == 2;
	}	// checkStatusChange

	/**
	 * Number of options available (to decide to display it)
	 * 
	 * @return item count
	 */
	public int getNumberOfOptions()
	{
		return actionCombo.getItemCount();
	}	// getNumberOfOptions

	/**
	 * Should the process be started?
	 * 
	 * @return OK pressed
	 */
	public boolean isStartProcess()
	{
		return m_OKpressed;
	}	// isStartProcess

	/**
	 * Fill Vector with DocAction Ref_List(135) values
	 */
	private Map<String, DocActionItem> getDocActionItemsIndexedByValue()
	{
		if(docActionItemsByValue == null)
		{
			final IADReferenceDAO referenceDAO = Services.get(IADReferenceDAO.class);
			final Properties ctx = Env.getCtx();
			final String adLanguage = Env.getAD_Language(ctx);
	
			docActionItemsByValue = referenceDAO.retrieveListItems(X_C_Order.DOCACTION_AD_Reference_ID) // 135
					.stream()
					.map(adRefListItem -> new DocActionItem(adRefListItem, adLanguage))
					.sorted(Comparator.comparing(DocActionItem::toString))
					.collect(GuavaCollectors.toImmutableMapByKey(DocActionItem::getValue));
		}
		
		return docActionItemsByValue;
	}

	/**
	 * ActionListener
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (save())
			{
				dispose();
				m_OKpressed = true;
				return;
			}
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}
		//
		// ActionCombo: display the description for the selection
		else if (e.getSource() == actionCombo)
		{
			final DocActionItem selectedDocAction = actionCombo.getSelectedItem();
			// Display description
			if (selectedDocAction != null)
			{
				message.setText(selectedDocAction.getDescription());
			}
		}
	}	// actionPerformed

	/**
	 * Save to Database
	 * 
	 * @return true if saved to Tab
	 */
	private boolean save()
	{
		final DocActionItem selectedDocAction = actionCombo.getSelectedItem();
		if(selectedDocAction == null)
		{
			return false;
		}

		// Save Selection
		log.info("DocAction={}", selectedDocAction);
		m_mTab.setValue("DocAction", selectedDocAction.getValue());
		return true;
	}	// save

	private static final class DocActionItem
	{
		private final String value;
		private final String caption;
		private String description;

		private DocActionItem(final ADRefListItem adRefListItem, final String adLanguage)
		{
			this.value = adRefListItem.getValue();
			this.caption = adRefListItem.getName().translate(adLanguage);
			this.description = adRefListItem.getDescription().translate(adLanguage);
		}

		@Override
		public String toString()
		{
			// IMPORTANT: this is how it will be displayed to user
			return caption;
		}

		@Override
		public int hashCode()
		{
			return Objects.hashCode(value);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj instanceof DocActionItem)
			{
				final DocActionItem other = (DocActionItem)obj;
				return Objects.equal(value, other.value);
			}
			else
			{
				return false;
			}
		}

		public String getValue()
		{
			return value;
		}

		public String getDescription()
		{
			return description;
		}
	}
}	// VDocAction
