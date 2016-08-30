package de.metas.edi.api;

import org.adempiere.util.ISingletonService;

import de.metas.edi.model.I_C_Order;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
	 * <li>modify the order and its lines (their referencing/FK columns are set), but only save the lines! This is because we usually call this method from a C_Order model interceptor.
	 * <li>assume that the given order has a non-empty <code>POReference</code>.
	 * </ul>
	 *
	 * @param inOut
	 * @return
	 */
	I_EDI_Ordrsp addToOrdrspCreateIfNotExistForOrder(I_C_Order order);

	/**
	 * Remove the given <code>order</code> from its {@link I_EDI_Ordrsp} record. This means:
	 * <ul>
	 * <li>delete all {@link I_EDI_OrdrspLine}s that reference one of the given <code>order</code>'s lines</li>
	 * <li>unset the given <code>order</code>'s reference to its {@link I_EDI_Ordrsp} and also save that change.</li>
	 * <li>if the given <code>order</code>'s former {@link I_EDI_Ordrsp} does not have any referencing {@link I_C_Order} and no {@link I_EDI_OrdrspLine} left afterwards, then delete it.
	 * </ul>
	 *
	 * @param order
	 */
	void removeOrderFromOrdrsp(I_C_Order order);

	void setMinimumPercentage(I_EDI_Ordrsp ordrsp);

	/**
	 * Retrieve the given <code>shipmentSchedule</code>'s {@link I_EDI_OrdrspLine} and updates its
	 * <ul>
	 * <li>{@link I_EDI_OrdrspLine#COLUMN_ConfirmedQty}</li>
	 * <li>{@link I_EDI_OrdrspLine#COLUMN_ShipDate}</li>
	 * <li>{@link I_EDI_OrdrspLine#COLUMN_DeliveryDate}</li>
	 * </ul>
	 * values. If there is no {@link I_EDI_OrdrspLine}, then do nothing. If <code>ConfirmedQty <= 0</code> as a result of the update, then also set the ordrspLine's <code>IsActive=N</code>.
	 *
	 */
	void updateLineFromShipmentSchedule(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * If the system updates the given (not-manual) "item-accepted" <code>ordrspLine</code>,
	 * then this method shall retrieve its manual siblings see ({@link IOrdrspDAO#retrieveManualSiblings(I_EDI_OrdrspLine)}) and decrease the siblings' <code>ConfirmedQty</code> value.
	 * <p>
	 * In case the not-manual <code>ordrspLine</code>'s <code>ConfirmedQty</code> decreases,
	 * then <b>don't</b> increase the <code>ConfirmedQty</code> in the manual ordrspLines!
	 * Instead, leave it as it is. That means that if the accepted quantity is decreased, then ORDRSPs SumPercentage also decreases and a user needs to decide what to do.
	 * <p>
	 * In case the not-manual <code>ordrspLine</code>'s <code>ConfirmedQty</code> increases and the summed <code>QtyConfirmed</code> of it and its siblings is now greater than <code>QtyEntered</code>,
	 * then decrease the manual siblings in the order returned by {@link IOrdrspDAO#retrieveManualSiblings(I_EDI_OrdrspLine)}.
	 * <p>
	 * Hint: check out the unit tests for examples and details.
	 *
	 * @param ordrspLine
	 */
	void updateManualLinesFromCalculatedLine(I_EDI_OrdrspLine ordrspLine);
}
