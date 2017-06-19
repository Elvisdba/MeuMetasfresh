package de.metas.process.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;
import org.compiere.swing.CButton;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.IProcessExecutionListener;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

class ProcessPanel implements ProcessDialog, ActionListener, IProcessExecutionListener
{
	// services
	private static final Logger log = LogManager.getLogger(ProcessPanel.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private ProcessPanelWindow _window;
	private final Properties ctx;
	private final I_AD_Process _adProcessTrl;
	private final int m_WindowNo;
	private final int m_TabNo;
	private final String whereClause;
	private final int adTableId;
	private final int recordId;
	private final boolean m_IsReport;
	private final boolean printPreview;
	private final IProcessExecutionListener processExecutionListener;

	/** Determine if a Help Process Window is shown */
	private final String m_ShowHelp;
	private final boolean _allowProcessReRun;
	private final boolean skipResultsPanel;

	@SuppressWarnings("unused")
	private boolean m_isLocked = false;
	private boolean _disposed = false;

	/**
	 * Dialog to start Process
	 *
	 * @param AD_Process_ID process
	 * @param isSOTrx is sales trx
	 */
	ProcessPanel(final ProcessDialogBuilder builder)
	{
		super();
		this.ctx = Env.getCtx();

		//
		// Load process definition from database
		// m_AD_Process_ID = builder.getAD_Process_ID();
		final I_AD_Process process = InterfaceWrapperHelper.create(ctx, builder.getAD_Process_ID(), I_AD_Process.class, ITrx.TRXNAME_None);
		if (process == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Process_ID@=" + builder.getAD_Process_ID());
		}
		this._adProcessTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class, Env.getAD_Language(ctx));

		this.m_WindowNo = builder.getWindowNo();
		this.m_TabNo = builder.getTabNo();

		Env.setContext(ctx, m_WindowNo, "IsSOTrx", builder.isSOTrx());
		whereClause = builder.getWhereClause();
		adTableId = builder.getAD_Table_ID();
		recordId = builder.getRecord_ID();

		skipResultsPanel = builder.isSkipResultsPanel();
		printPreview = builder.isPrintPreview();
		processExecutionListener = builder.getProcessExecutionListener();

		//
		// Load process definition from database
		m_IsReport = _adProcessTrl.isReport();

		m_ShowHelp = builder.getShowHelp(_adProcessTrl::getShowHelp);
		_allowProcessReRun = builder.isAllowProcessReRun(_adProcessTrl::isAllowProcessReRun);

		try
		{
			jbInit();
			init();
		}
		catch (final Exception ex)
		{
			log.error("", ex);
			dispose();
			return;
		}
	}	// ProcessDialog

	private Properties getCtx()
	{
		return ctx;
	}

	/** The main panel */
	@SuppressWarnings("serial")
	private final CPanel mainPanel = new CPanel()
	{
		@Override
		public Dimension getPreferredSize()
		{
			final Dimension d = super.getPreferredSize();
			final Dimension m = getMinimumSize();
			if (d.height < m.height || d.width < m.width)
			{
				final Dimension d1 = new Dimension();
				d1.height = Math.max(d.height, m.height);
				d1.width = Math.max(d.width, m.width);
				return d1;
			}
			else
			{
				return d;
			}
		}
	};

	private final MessagePanel messageTop = new MessagePanel();

	private final CPanel _centerPanel = new CPanel();
	private final CardLayout _centerPanelLayout = new CardLayout();
	private String _currentCardName = null;
	//
	private static final String CARDNAME_ProcessParameters = "ProcessParameters";
	private final CPanel parametersContainer = new CPanel();
	private ProcessParametersPanel parameterPanel = null;
	//
	private static final String CARDNAME_ProcessResult = "ProcessResult";
	private final CPanel resultContainer = new CPanel();
	private final MessagePanel resultMessagePane = new MessagePanel();

	private final CButton bOK = ConfirmPanel.createOKButton(true);
	private final CButton bBack = ConfirmPanel.createBackButton(true);

	/**
	 * #782: Focus on the first parameter of the process when the process panel is opened, so it can be directly changed from keyboard
	 */
	private final WindowListener onWindowOpenFocusFirstParameter = new WindowAdapter()
	{
		@Override
		public void windowOpened(final WindowEvent e)
		{

			requestFocusInWindow();

		}
	};

	/* package */void installTo(final ProcessPanelWindow window)
	{
		Check.assumeNotNull(window, "Parameter window is not null");
		Check.assumeNull(_window, "window was not already configured for {}", this);

		window.enableWindowEvents(AWTEvent.WINDOW_EVENT_MASK);
		window.setTitle(_adProcessTrl.getName());
		window.setIconImage(Images.getImage2("mProcess"));
		window.getContentPane().add(mainPanel);
		window.getRootPane().setDefaultButton(bOK);

		window.addWindowListener(onWindowOpenFocusFirstParameter);
		this._window = window;

		//
		// Automatically execute the process (if possible)
		{
			boolean autostartNow = false;
			if (autostartNow)
			{
				//
			}
			else if (!parameterPanel.hasFields()
					&& (X_AD_Process.SHOWHELP_DonTShowHelp.equals(m_ShowHelp) || m_IsReport))
			{
				autostartNow = true;    // don't ask first click, anyway show resulting window
			}
			// Check if the process is a silent one
			else if (X_AD_Process.SHOWHELP_RunSilently_TakeDefaults.equals(m_ShowHelp))
			{
				autostartNow = true;
			}

			if (autostartNow)
			{
				bOK.doClick();
			}
			else
			{
				window.showCenterScreen();
			}
		}

		mainPanel.revalidate();
	}

	/**
	 * Request focus in the first parameter possible, if any is available.
	 */
	protected void requestFocusInWindow()
	{
		if (parameterPanel == null)
		{
			// nothing to do
			return;
		}

		if (CARDNAME_ProcessParameters.equals(getCurrentCardName()))
		{
			parameterPanel.focusFirstParameter();
		}

	}

	private ProcessPanelWindow getWindow()
	{
		return _window;
	}

	/**
	 * Static Layout
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		//
		// Main panel:
		{
			final BorderLayout mainLayout = new BorderLayout();
			mainLayout.setVgap(2);
			//
			mainPanel.setLayout(mainLayout);
			mainPanel.setMinimumSize(new Dimension(500, 200));
		}

		//
		// NorthPanel: Process message
		{
			mainPanel.add(messageTop, BorderLayout.NORTH);
			messageTop.setMaximumSize(new Dimension(600, 300));
		}

		//
		// Center panel: process parameters / process result
		{
			_centerPanel.setLayout(_centerPanelLayout);
			_centerPanel.setBorder(BorderFactory.createEmptyBorder());
			mainPanel.add(_centerPanel, BorderLayout.CENTER);

			// Parameters panel:
			{
				parametersContainer.setLayout(new BorderLayout());
				_centerPanel.add(parametersContainer, CARDNAME_ProcessParameters);
			}

			// Result panel:
			{
				resultContainer.setLayout(new BorderLayout());
				resultContainer.add(resultMessagePane, BorderLayout.CENTER);
				_centerPanel.add(resultContainer, CARDNAME_ProcessResult);
			}

			showCard(CARDNAME_ProcessParameters); // initially process parameters (first card) shall be displayed
		}

		//
		// South panel: confirm panel buttons
		{
			bOK.setText(msgBL.getMsg(getCtx(), "Start")); // make sure the text is set
			bOK.addActionListener(this);

			bBack.setText(msgBL.getMsg(getCtx(), "Back")); // make sure the text is set
			bBack.addActionListener(this);

			final FlowLayout southLayout = new FlowLayout();
			final CPanel southPanel = new CPanel();
			southPanel.setLayout(southLayout);
			southLayout.setAlignment(FlowLayout.RIGHT);
			mainPanel.add(southPanel, BorderLayout.SOUTH);
			southPanel.add(bBack, null);
			southPanel.add(bOK, null);
		}
	}	// jbInit

	private final void showCard(final String cardName)
	{
		_centerPanelLayout.show(_centerPanel, cardName);
		_currentCardName = cardName;

		updateUIStatus();
	}

	private final String getCurrentCardName()
	{
		return _currentCardName;
	}

	private final void updateUIStatus()
	{

		final String okButtonMessage;
		final boolean enableBackButton;

		final String cardName = getCurrentCardName();
		if (CARDNAME_ProcessParameters.equals(cardName))
		{
			okButtonMessage = "Start";
			enableBackButton = false;
		}
		else if (CARDNAME_ProcessResult.equals(cardName))
		{
			okButtonMessage = "Close";
			enableBackButton = true;
		}
		else
		{
			throw new IllegalStateException("Unknown card: " + cardName);
		}

		//
		// Update UI
		bOK.setText(msgBL.getMsg(getCtx(), okButtonMessage));
		bBack.setVisible(_allowProcessReRun);
		bBack.setEnabled(_allowProcessReRun && enableBackButton);
	}

	/**
	 * Set Visible (set focus to OK if visible)
	 *
	 * @param visible true if visible
	 */
	public void setVisible(final boolean visible)
	{
		if (visible && _disposed)
		{
			log.warn("Marking visible an already disposed panel: {}", this, new Exception("trace"));
		}

		if (visible)
		{
			bOK.requestFocus();
		}
	}	// setVisible

	public boolean isDisposed()
	{
		return _disposed;
	}

	/**
	 * Dispose
	 */
	public void dispose()
	{
		if (_disposed)
		{
			log.warn("Calling dispose again for {}", this, new Exception("trace"));
		}
		_disposed = true;

		if (parameterPanel != null)
		{
			parameterPanel.dispose();
			parameterPanel = null;

		}
	}

	/**
	 * Dynamic Init
	 *
	 * @return true, if there is something to process (start from menu)
	 */
	private void init()
	{
		//
		// Update UI
		messageTop.setText(buildProcessMessageText());

		//
		// Create process parameters panel
		final ProcessInfo processInfo = createProcessInfo(); // NOTE: used only for loading
		parameterPanel = new ProcessParametersPanel(processInfo);
		parametersContainer.removeAll();
		if (parameterPanel.hasFields())
		{
			final JScrollPane parametersPanelScroll = new JScrollPane(parameterPanel);
			parametersPanelScroll.setBorder(BorderFactory.createEmptyBorder());

			parametersContainer.add(parametersPanelScroll, BorderLayout.CENTER);

			parameterPanel.setFocusable(true);

		}
	}

	/**
	 * Build the top message (Process description and help).
	 */
	private String buildProcessMessageText()
	{
		final StringBuilder messageText = new StringBuilder();

		//
		// Description
		final String processDescription = _adProcessTrl.getDescription();
		messageText.append("<b>");
		if (Check.isEmpty(processDescription))
		{
			messageText.append(msgBL.getMsg(getCtx(), "StartProcess?"));
		}
		else
		{
			messageText.append(processDescription);
		}
		messageText.append("</b>");

		//
		// Help
		final String processHelp = _adProcessTrl.getHelp();
		if (!Check.isEmpty(processHelp))
		{
			messageText.append("<p>").append(processHelp).append("</p>");
		}

		return messageText.toString();
	}

	private final ProcessInfo createProcessInfo()
	{
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process(_adProcessTrl)
				.setWhereClause(whereClause)
				.setRecord(adTableId, recordId)
				.setWindowNo(m_WindowNo).setTabNo(m_TabNo)
				.addParameters(parameterPanel == null ? ImmutableList.of() : parameterPanel.createParameters())
				.setPrintPreview(printPreview)
				.build();
		return pi;
	}

	private final void windowDispose()
	{
		final ProcessPanelWindow window = getWindow();
		window.dispose();
	}

	private final void setWindowEnabled(final boolean enabled)
	{
		final ProcessPanelWindow window = getWindow();
		window.setEnabled(enabled);
	}

	private final void windowShow()
	{
		final ProcessPanelWindow window = getWindow();
		window.showCenterScreen();
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			Services.get(IClientUI.class).error(m_WindowNo, ex);
		}
	}

	private void actionPerformed0(final ActionEvent e)
	{
		if (e.getSource() == bOK)
		{
			final String currentCardName = getCurrentCardName();
			if (CARDNAME_ProcessParameters.equals(currentCardName))
			{
				final ProcessInfo pi = createProcessInfo();
				ProcessExecutor.builder(pi)
						.setListener(SwingProcessExecutionListener.of(this))
						.executeASync();

				if (skipResultsPanel && !_allowProcessReRun)
				{
					windowDispose();
				}
			}
			else if (CARDNAME_ProcessResult.equals(currentCardName))
			{
				windowDispose();
			}
			else
			{
				log.warn("Unknown card: {}", currentCardName);
			}
		}
		else if (e.getSource() == bBack)
		{
			showCard(CARDNAME_ProcessParameters);
		}
	}	// actionPerformed

	/**
	 * Lock User Interface Called from the Worker before processing
	 *
	 * @param pi process info
	 */
	@Override
	public void lockUI(final ProcessInfo pi)
	{
		bOK.setEnabled(false);
		setWindowEnabled(false);
		m_isLocked = true;

		if (processExecutionListener != null)
		{
			processExecutionListener.lockUI(pi);
		}
	}   // lockUI

	/**
	 * Unlock User Interface. Called from the Worker when processing is done
	 *
	 * @param pi process info
	 */
	@Override
	public void unlockUI(final ProcessInfo pi)
	{
		//
		// Enable OK button, flag as not locked anymore.
		bOK.setEnabled(true);
		setWindowEnabled(true);
		m_isLocked = false;

		//
		// Check if we shall close the window automatically
		boolean closeWindow = false;
		{
			// If it was a report which finished successfully and we don't allow process re-run
			if (m_IsReport && !pi.getResult().isError())
			{
				closeWindow = true;
			}

			// If the process is a silent one and no errors occurred, close the dialog
			if (X_AD_Process.SHOWHELP_RunSilently_TakeDefaults.equals(m_ShowHelp))
			{
				closeWindow = true;
			}
			
			// #863
			// Do not show result window for any process that doesn't allow rerun
			if(!_allowProcessReRun)
			{
				closeWindow = true;
			}
			
		}
		//
		// Close the window
		if (closeWindow)
		{
			windowDispose();
		}
		else
		{
			loadResultsAndShow(pi.getResult());
		}

		if (processExecutionListener != null)
		{
			processExecutionListener.unlockUI(pi);
		}
	}   // unlockUI

	private final void loadResultsAndShow(final ProcessExecutionResult result)
	{
		final StringBuilder messageText = new StringBuilder();
		// Show process logs if any
		if (result.isShowProcessLogs())
		{
			//
			// Update message
			messageText.append("<p><font color=\"").append(result.isError() ? "#FF0000" : "#0000FF").append("\">** ")
					.append(result.getSummary())
					.append("</font></p>");
			messageText.append(result.getLogInfo(true));
		}
		resultMessagePane.setText(messageText.toString());
		resultMessagePane.moveCaretToEnd(); // scroll down

		result.setErrorWasReportedToUser();

		//
		// Show the results panel
		showCard(CARDNAME_ProcessResult);

		//
		// Update UI
		windowShow();
	}

	/**
	 * Message panel component
	 *
	 * @author tsa
	 *
	 */
	private static final class MessagePanel extends JScrollPane
	{
		private static final long serialVersionUID = 1L;

		private final StringBuffer messageBuf = new StringBuffer();
		private final JEditorPane message = new JEditorPane()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize()
			{
				Dimension d = super.getPreferredSize();
				Dimension m = getMaximumSize();
				if (d.height > m.height || d.width > m.width)
				{
					Dimension d1 = new Dimension();
					d1.height = Math.min(d.height, m.height);
					d1.width = Math.min(d.width, m.width);
					return d1;
				}
				else
					return d;
			}
		};

		public MessagePanel()
		{
			super();

			setBorder(null);
			setViewportView(message);

			message.setContentType("text/html");
			message.setEditable(false);
			message.setBackground(Color.white);
			message.setFocusable(true);

		}

		public void moveCaretToEnd()
		{
			message.setCaretPosition(message.getDocument().getLength());	// scroll down
		}

		public final void setText(final String text)
		{
			messageBuf.setLength(0);
			appendText(text);
		}

		public final void appendText(final String text)
		{
			messageBuf.append(text);
			message.setText(messageBuf.toString());
		}

		@Override
		public Dimension getPreferredSize()
		{
			final Dimension d = super.getPreferredSize();
			final Dimension m = getMaximumSize();
			if (d.height > m.height || d.width > m.width)
			{
				final Dimension d1 = new Dimension();
				d1.height = Math.min(d.height, m.height);
				d1.width = Math.min(d.width, m.width);
				return d1;
			}
			else
			{
				return d;
			}
		}
	}

}
