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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IOrdrspDAO extends ISingletonService
{

	List<I_EDI_OrdrspLine> retrieveAllOrdrspLines(I_EDI_Ordrsp ordrsp);

	List<I_C_Order> retrieveAllOrders(I_EDI_Ordrsp ordrsp);

	I_EDI_Ordrsp retrieveMatchingOrdrspOrNull(I_C_BPartner c_BPartner, String poReference, IContextAware ctxAware);

	BigDecimal retrieveMinimumSumPercentage();

	boolean hasOrders(I_EDI_Ordrsp ordrsp);

	boolean hasOrdrspLines(I_EDI_Ordrsp ordrsp);

	List<I_C_OrderLine> retrieveAllOrderLines(I_EDI_OrdrspLine ordrspLine);

}
