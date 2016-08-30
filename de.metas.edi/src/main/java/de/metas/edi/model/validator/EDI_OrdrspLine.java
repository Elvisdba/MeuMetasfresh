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
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IOrdrspBL;
import de.metas.edi.api.IOrdrspDAO;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.esb.edi.model.X_EDI_OrdrspLine;

@Interceptor(I_EDI_OrdrspLine.class)
public class EDI_OrdrspLine
{
	public static final Object INSTANCE = new EDI_OrdrspLine();

	private EDI_OrdrspLine()
	{
		super();
	}

	/**
	 * Unsets the given <code>ordrspLine</code>'s <code>C_OrderLine.EDI_OrdrspLine_ID</code> references.
	 *
	 * @param ordrsp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onOrdrspLineDelete(final I_EDI_OrdrspLine ordrspLine)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);

		final List<I_C_OrderLine> allLines = ordrspDAO.retrieveAllOrderLines(ordrspLine);
		for (final I_C_OrderLine line : allLines)
		{
			line.setEDI_OrdrspLine_ID(0);
			InterfaceWrapperHelper.save(line);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_EDI_OrdrspLine.COLUMNNAME_ConfirmedQty })
	public void onOrdrspLineChange(final I_EDI_OrdrspLine ordrspLine)
	{
		if (X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted.equals(ordrspLine.getQuantityQualifier()))
		{
			final IOrdrspBL ordrspBL = Services.get(IOrdrspBL.class);
			ordrspBL.updateManualLinesFromCalculatedLine(ordrspLine);
		}
	}
}
