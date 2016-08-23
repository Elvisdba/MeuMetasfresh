package de.metas.edi.api;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

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

public interface IEDIBPartnerService extends ISingletonService
{
	boolean isEDIRecipient(I_C_BPartner bpartner, Timestamp date);

	/**
	 *
	 * @param bpartner
	 * @param date
	 * @return return zero if there is no bpartner config or the partner is not a desadv receiver at the given date.
	 */
	BigDecimal getEdiDESADVDefaultItemCapacity(I_C_BPartner bpartner, Timestamp date);

	String getEdiPartnerIdentification(I_C_BPartner bpartner, Timestamp date);

	boolean isDesadvRecipient(I_C_BPartner bpartner, Timestamp date);

	boolean isOrdrspRecipient(I_C_BPartner bpartner, Timestamp date);

	boolean isInvoicRecipient(I_C_BPartner bpartner, Timestamp date);

}
