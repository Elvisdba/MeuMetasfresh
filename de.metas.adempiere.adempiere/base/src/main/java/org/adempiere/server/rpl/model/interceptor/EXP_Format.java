package org.adempiere.server.rpl.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.server.rpl.api.IExpFormatDAO;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

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
@Interceptor(I_EXP_Format.class)
public class EXP_Format
{
	public static final EXP_Format INSTANCE = new EXP_Format();

	private EXP_Format()
	{
		super();
	}

	/**
	 * Deletes the given <code>format</code>'s lines.
	 *
	 * @param ordrsp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onExpFormatDelete(final I_EXP_Format format)
	{
		Services.get(IExpFormatDAO.class).deleteAllLines(format);
	}
}
