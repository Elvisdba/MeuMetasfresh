package de.metas.edi.process.export.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.esb.edi.model.I_EDI_Ordrsp;

public class EDI_ORDRSPExport extends AbstractExport<I_EDI_Document>
{
	/**
	 * EXP_Format.Value for exporting InOut EDI documents
	 */
	private static final String CST_ORDRSP_EXP_FORMAT = "EDI_Exp_Ordrsp";

	public EDI_ORDRSPExport(final I_EDI_Ordrsp ordrsp, final String tableIdentifier, final int clientId)
	{
		super(InterfaceWrapperHelper.create(ordrsp, I_EDI_Document.class), tableIdentifier, clientId);
	}

	@Override
	public List<Exception> createExport()
	{
		final I_EDI_Document document = getDocument();

		// note: we don't do any validation, because what we did for validation was actually master-data validation.
		// however, with ORDRSP, we have it all in the edi-records and don't get it from the master data on the fly.

		// Mark the ordrsp as "EDI starting"
		document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted);
		InterfaceWrapperHelper.save(document);

		try
		{
			exportEDI(I_EDI_Ordrsp.class, EDI_ORDRSPExport.CST_ORDRSP_EXP_FORMAT, I_EDI_Ordrsp.Table_Name, getTableIdentifier());
		}
		catch (final Exception e)
		{
			document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error);

			final String errmsg = e.getLocalizedMessage();
			document.setEDIErrorMsg(errmsg);
			InterfaceWrapperHelper.save(document); // a model interceptor will attempt to translate the message

			throw new AdempiereException(e);
		}

		return Collections.emptyList();
	}
}
