package org.adempiere.server.rpl.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.server.rpl.api.IExpFormatDAO;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.adempiere.util.Services;
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

public class ExpFormatDAO implements IExpFormatDAO
{

	@Override
	public int deleteAllLines(I_EXP_Format format)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_EXP_FormatLine.class, format)
				.addEqualsFilter(I_EXP_FormatLine.COLUMNNAME_EXP_Format_ID, format.getEXP_Format_ID())
				.create()
				.delete();
	}
}
