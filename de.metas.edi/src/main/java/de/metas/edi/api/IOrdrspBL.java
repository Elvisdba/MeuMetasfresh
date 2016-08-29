package de.metas.edi.api;

import org.adempiere.util.ISingletonService;

import de.metas.edi.model.I_C_Order;
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

public interface IOrdrspBL extends ISingletonService
{
	/**
	 * Create a new ordrsp for the given <code>order</code>'s <code>POReference</code>, or retrieve and update an existing one.<br>
	 * If a new one is created, then take the values from this order.<br>
	 * Also iterate the order's lines and create {@link I_EDI_OrdrspLine}s for their respective line numbers unless such ordrsp lines already exist.
	 * <p>
	 * Notes:
	 * <ul>
	 * <li>modify the order and its lines (their referencing/FK columns are set), but only save the lines! This is because we call this method from a C_Order model interceptor.
	 * <li>assume that the given order has a non-empty <code>POReference</code>.
	 * </ul>
	 *
	 * @param inOut
	 * @return
	 */
	I_EDI_Ordrsp addToOrdrspCreateIfNotExistForOrder(I_C_Order order);

	/**
	 * Removes the given <code>order</code> from its {@link I_EDI_Ordrsp} record.
	 *
	 * @param order
	 */
	void removeOrderFromOrdrsp(I_C_Order order);

	void setMinimumPercentage(I_EDI_Ordrsp ordrsp);
}
