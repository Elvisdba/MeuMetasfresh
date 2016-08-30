package de.metas.edi.api.impl;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.esb.edi.model.X_EDI_OrdrspLine;

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

public class OrdrspDAOTests
{
	private OrdrspTestsHelper helper;

	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new OrdrspTestsHelper();
	}

	@Test
	public void retrieveManualSiblings_0()
	{
		final I_EDI_OrdrspLine line3 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemCancelled, new BigDecimal("2"));
		final I_EDI_OrdrspLine line0 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted, new BigDecimal("4"));
		final I_EDI_OrdrspLine line2 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemRejected, new BigDecimal("2"));
		final I_EDI_OrdrspLine line1 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemBackordered, new BigDecimal("4"));

		final List<I_EDI_OrdrspLine> result = new OrdrspDAO().retrieveManualSiblings(line0);

		assertThat(result, contains(line1, line2, line3));
	}
}
