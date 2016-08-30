package de.metas.edi.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;

import de.metas.adempiere.util.CacheModel;
import de.metas.edi.api.IEDIBPartnerService;
import de.metas.esb.edi.model.I_EDI_BPartner_Config;

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

public class EDIBPartnerService implements IEDIBPartnerService
{

	@Override
	public boolean isEDIRecipient(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config);
	}

	private boolean isEdiRecipient(final I_EDI_BPartner_Config config)
	{
		return config != null && config.isEdiRecipient();
	}

	@Override
	public BigDecimal getEdiDESADVDefaultItemCapacity(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config) ? config.getEDI_DefaultItemCapacity() : BigDecimal.ZERO;
	}

	@Override
	public String getEdiPartnerIdentification(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config) ? config.getEdiPartnerIdentification() : "";
	}

	@Override
	public boolean isDesadvRecipient(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config) && config.isDesadvRecipient();
	}

	@Override
	public boolean isOrdrspRecipient(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config) && config.isOrdrspRecipient();
	}

	@Override
	public boolean isInvoicRecipient(final I_C_BPartner bpartner, final Timestamp date)
	{
		final I_EDI_BPartner_Config config = retrieve(bpartner, date);
		return isEdiRecipient(config) && config.isInvoicRecipient();
	}

	@Cached(cacheName = I_EDI_BPartner_Config.Table_Name + "#by#" + I_EDI_BPartner_Config.COLUMNNAME_ValidFrom)
	/* package */ I_EDI_BPartner_Config retrieve(@CacheModel final I_C_BPartner bpartner, final Timestamp date)
	{
		final de.metas.edi.model.I_C_BPartner bpartnerExt = InterfaceWrapperHelper.create(bpartner, de.metas.edi.model.I_C_BPartner.class);
		if (!bpartnerExt.getHasEdiConfig())
		{
			return null;
		}
		final I_EDI_BPartner_Config config = Services.get(IQueryBL.class).createQueryBuilder(I_EDI_BPartner_Config.class, bpartner)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_BPartner_Config.COLUMN_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.addCompareFilter(I_EDI_BPartner_Config.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.orderBy()
				.addColumn(I_EDI_BPartner_Config.COLUMN_ValidFrom, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first();

		return config;
	}
}
