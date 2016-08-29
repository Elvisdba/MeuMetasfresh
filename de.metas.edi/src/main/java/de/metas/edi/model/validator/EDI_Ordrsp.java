package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IOrdrspBL;
import de.metas.edi.api.IOrdrspDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_EDI_Document;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;

@Interceptor(I_EDI_Ordrsp.class)
public class EDI_Ordrsp
{
	public static final Object INSTANCE = new EDI_Ordrsp();

	private EDI_Ordrsp()
	{
		super();
	}

	/**
	 * Deletes the given <code>ordrsp</code>'s lines and unsets its <code>C_Order.EDI_Ordrsp_ID</code> references.
	 * @param ordrsp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onOrdrspDelete(final I_EDI_Ordrsp ordrsp)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);

		final List<I_EDI_OrdrspLine> allLines = ordrspDAO.retrieveAllOrdrspLines(ordrsp);
		for (final I_EDI_OrdrspLine line : allLines)
		{
			InterfaceWrapperHelper.delete(line);
		}

		final List<I_C_Order> allIOrders = Services.get(IOrdrspDAO.class).retrieveAllOrders(ordrsp);
		for (final I_C_Order order : allIOrders)
		{
			order.setEDI_Ordrsp_ID(0);
			InterfaceWrapperHelper.save(order);
		}
	}

	/**
	 * Updates the ORDRSP's processing and processed flags.
	 *
	 * @param ordrsp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus })
	public void onOrdrspStatusChanged(final I_EDI_Ordrsp ordrsp)
	{
		final String exportStatus = ordrsp.getEDI_ExportStatus();

		final boolean processing = I_EDI_Document.EDI_EXPORTSTATUS_Enqueued.equals(exportStatus) || I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted.equals(exportStatus);
		ordrsp.setProcessing(processing);

		final boolean processed = I_EDI_Document.EDI_EXPORTSTATUS_Sent.equals(exportStatus);
		ordrsp.setProcessed(processed);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_EDI_Desadv.COLUMNNAME_EDIErrorMsg })
	public void translateErrorMessage(final I_EDI_Ordrsp ordrsp)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String errorMsgTrl = msgBL.parseTranslation(InterfaceWrapperHelper.getCtx(ordrsp), ordrsp.getEDIErrorMsg());
		ordrsp.setEDIErrorMsg(errorMsgTrl);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setMinimumSumPercentage(final I_EDI_Ordrsp ordrsp)
	{
		// set the minimum sum percentage on each new ordrsp.
		// Even if the percentage will be changed via sys config, for this ordrsp it won't change
		Services.get(IOrdrspBL.class).setMinimumPercentage(ordrsp);
	}
}
