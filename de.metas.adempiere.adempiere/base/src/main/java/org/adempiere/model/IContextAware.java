package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

/**
 * Simulates context objects. You can use e.g.
 * <ul>
 * <li>{@link org.adempiere.model.PlainContextAware}</li>
 * <li>{@link InterfaceWrapperHelper#getContextAware(Object)}</li>
 * </ul>
 * to get yours. Also, a number of important classes like {@link org.compiere.process.SvrProcess} already implement this interface.
 */
public interface IContextAware
{
	/**
	 * @return ctx
	 */
	Properties getCtx();

	/**
	 * @return trxName
	 */
	String getTrxName();
}
