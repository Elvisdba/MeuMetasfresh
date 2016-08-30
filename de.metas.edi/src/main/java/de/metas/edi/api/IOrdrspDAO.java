package de.metas.edi.api;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.esb.edi.model.I_EDI_Ordrsp;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.esb.edi.model.X_EDI_OrdrspLine;
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

public interface IOrdrspDAO extends ISingletonService
{

	/**
	 * System configuration to tell the minimum sum percentage (ConfirmedQty/QtyEntered) that is accepted for a ordrsp entry.
	 *
	 * @see #retrieveMinimumSumPercentage()
	 */
	String SYS_CONFIG_MinimumPercentage = "de.metas.edi.ORDRSP.MinimumPercentage";
	String SYS_CONFIG_DefaultMinimumPercentage_DEFAULT = "50";

	List<I_EDI_OrdrspLine> retrieveAllOrdrspLines(I_EDI_Ordrsp ordrsp);

	List<I_C_Order> retrieveAllOrders(I_EDI_Ordrsp ordrsp);

	I_EDI_Ordrsp retrieveMatchingOrdrspOrNull(I_C_BPartner c_BPartner, String poReference, IContextAware ctxAware);

	/**
	 * Get the value of the minimum sum percentage from the sysconfig {@value #SYS_CONFIG_MinimumPercentage}.
	 *
	 * @return
	 */
	BigDecimal retrieveMinimumSumPercentage();

	boolean hasOrders(I_EDI_Ordrsp ordrsp);

	boolean hasOrdrspLines(I_EDI_Ordrsp ordrsp);

	List<I_C_OrderLine> retrieveAllOrderLines(I_EDI_OrdrspLine ordrspLine);

	/**
	 * Retrieve the {@link I_EDI_OrdrspLine} whose parent record is not yet send,
	 * which references the given <code>shipmentSchedule</code>
	 * and which has <code>QuantityQualifier = ItemAccepted</code> (meaning that it also has <code>IsManual=false</code>).
	 *
	 * @param shipmentSchedule
	 * @return
	 */
	I_EDI_OrdrspLine retrieveUnsentOrdrspLine(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Retrieve the <b>first</b> I_EDI_OrdrspLine which has the same {@link I_EDI_Ordrsp} and <code>Line</code> (thus comes from the same ORDERS line).
	 * <p>
	 * Before selecting the "first" record, the result is ordered by <code>QuantityQualifier</code>, so the order of preference is
	 * <ul>
	 * <li>{@link X_EDI_OrdrspLine#QUANTITYQUALIFIER_ItemAccepted}</li>
	 * <li>{@link X_EDI_OrdrspLine#QUANTITYQUALIFIER_ItemBackordered}</li>
	 * <li>{@link X_EDI_OrdrspLine#QUANTITYQUALIFIER_ItemRejected}</li>
	 * <li>{@link X_EDI_OrdrspLine#QUANTITYQUALIFIER_ItemCancelled}</li>
	 * </ul>
	 *
	 * @param ordrspLine
	 * @return
	 */
	List<I_EDI_OrdrspLine> retrieveManualSiblings(I_EDI_OrdrspLine ordrspLine);
}
