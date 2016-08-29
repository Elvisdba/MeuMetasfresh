package org.adempiere.server.rpl.api;

import java.util.List;

import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_EXP_FormatLine;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface IExpFormatDAO extends ISingletonService
{

	int deleteAllLines(I_EXP_Format format);

	/**
	 *
	 * @param exportFormat
	 * @return active lines of the given export format, ordered by {@link I_EXP_FormatLine#COLUMNNAME_Position}, {@link I_EXP_FormatLine#COLUMNNAME_EXP_FormatLine_ID}.
	 *
	 */
	List<I_EXP_FormatLine> retrieveLines(org.compiere.model.I_EXP_Format exportFormat);

}
