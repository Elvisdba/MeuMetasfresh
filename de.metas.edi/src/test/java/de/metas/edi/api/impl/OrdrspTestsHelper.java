package de.metas.edi.api.impl;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;

/*
 * #%L
 * de.metas.edi
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

public class OrdrspTestsHelper
{
	public static final BigDecimal QTY_ENTERED = new BigDecimal("10");

	public static final int LINE = 10;

	private I_EDI_Ordrsp ordrsp;

	public OrdrspTestsHelper()
	{
		ordrsp = InterfaceWrapperHelper.newInstance(I_EDI_Ordrsp.class);
		InterfaceWrapperHelper.save(ordrsp);
	}

	public I_EDI_OrdrspLine createLine(final String qualifier, final BigDecimal confirmedQty)
	{
		final I_EDI_OrdrspLine line = InterfaceWrapperHelper.newInstance(I_EDI_OrdrspLine.class);
		line.setEDI_Ordrsp(ordrsp);
		line.setLine(LINE);
		line.setQtyEntered(QTY_ENTERED);

		line.setQuantityQualifier(qualifier);
		line.setConfirmedQty(confirmedQty);

		InterfaceWrapperHelper.save(line);
		return line;
	}
}
