package de.metas.edi.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.IContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

import de.metas.edi.api.IOrdrspDAO;
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

public class OrdrspDAO implements IOrdrspDAO
{

	/**
	 * System configuration to tell the minimum sum percentage (ConfirmedQty/QtyEntered) that is accepted for a ordrsp entry.
	 */
	private static final String SYS_CONFIG_MinimumPercentage = "de.metas.edi.ORDRSP.MinimumPercentage";
	private static final String SYS_CONFIG_DefaultMinimumPercentage_DEFAULT = "50";

	@Override
	public List<I_EDI_OrdrspLine> retrieveAllOrdrspLines(final I_EDI_Ordrsp ordrsp)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_OrdrspLine.class, ordrsp)
				.addEqualsFilter(I_EDI_OrdrspLine.COLUMNNAME_EDI_Ordrsp_ID, ordrsp.getEDI_Ordrsp_ID())
				.create()
				.list();
	}

	@Override
	public List<I_C_Order> retrieveAllOrders(final I_EDI_Ordrsp ordrsp)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, ordrsp)
				.addEqualsFilter(I_C_Order.COLUMNNAME_EDI_Ordrsp_ID, ordrsp.getEDI_Ordrsp_ID())
				.create()
				.list();
	}

	@Override
	public I_EDI_Ordrsp retrieveMatchingOrdrspOrNull(
			final I_C_BPartner handOver_Partner,
			final String poReference,
			final IContextAware ctxAware)
	{
		Check.assumeNotNull(ctxAware, "Param 'ctxAware' is not null");
		Check.assumeNotEmpty(poReference, "Param 'poReference' is not emtpy");

		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_Ordrsp.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Ordrsp.COLUMN_POReference, poReference)
				.addEqualsFilter(I_EDI_Ordrsp.COLUMN_HandOver_Partner_ID, handOver_Partner.getC_BPartner_ID())
				.addEqualsFilter(I_EDI_Ordrsp.COLUMN_Processed, false)
				.addEqualsFilter(I_EDI_Ordrsp.COLUMN_Processing, false)
				.create()
				.firstOnly(I_EDI_Ordrsp.class);
	}

	@Override
	public BigDecimal retrieveMinimumSumPercentage()
	{
		final String minimumPercentageAccepted_Value = Services.get(ISysConfigBL.class).getValue(
				SYS_CONFIG_MinimumPercentage, SYS_CONFIG_DefaultMinimumPercentage_DEFAULT);
		try
		{
			return new BigDecimal(minimumPercentageAccepted_Value);
		}
		catch (final NumberFormatException e)
		{
			Check.errorIf(true, "AD_SysConfig {} = {} can't be parsed as a number", SYS_CONFIG_MinimumPercentage, minimumPercentageAccepted_Value);
			return null; // shall not be reached
		}
	}
}
