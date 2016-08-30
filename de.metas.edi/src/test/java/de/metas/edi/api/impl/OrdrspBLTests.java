package de.metas.edi.api.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
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

/**
 * Hint: if a test in this class fails, first check if all is well with {@link OrdrspDAOTests}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrdrspBLTests
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

	/**
	 * We have three lines, and their summed confirmed qty is 10, just like qtyEntered. So, nothing has to change
	 */
	@Test
	public void updateManualLinesFromCalculatedLine_0()
	{
		assertThat(OrdrspTestsHelper.QTY_ENTERED, comparesEqualTo(new BigDecimal("10"))); // guard

		final I_EDI_OrdrspLine line0 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted, new BigDecimal("4"));
		final I_EDI_OrdrspLine line1 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemBackordered, new BigDecimal("4"));
		final I_EDI_OrdrspLine line2 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemRejected, new BigDecimal("2"));

		new OrdrspBL().updateManualLinesFromCalculatedLine(line0);

		InterfaceWrapperHelper.refresh(line1);
		InterfaceWrapperHelper.refresh(line2);

		assertThat(line1.getConfirmedQty(), comparesEqualTo(new BigDecimal("4"))); // unchanged
		assertThat(line2.getConfirmedQty(), comparesEqualTo(new BigDecimal("2"))); // unchanged
	}

	/**
	 * We have three lines, and their summed confirmed qty is 12.
	 * QtyEntered is 10, so the manual line's sum needs to be decreased by 2.
	 * Those two shall be subtracted from the "Item-Backordered" line.
	 */
	@Test
	public void updateManualLinesFromCalculatedLine_1()
	{
		assertThat(OrdrspTestsHelper.QTY_ENTERED, comparesEqualTo(new BigDecimal("10"))); // guard

		final I_EDI_OrdrspLine line0 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted, new BigDecimal("6"));
		final I_EDI_OrdrspLine line1 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemBackordered, new BigDecimal("4"));
		final I_EDI_OrdrspLine line2 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemRejected, new BigDecimal("2"));

		new OrdrspBL().updateManualLinesFromCalculatedLine(line0);

		InterfaceWrapperHelper.refresh(line1);
		InterfaceWrapperHelper.refresh(line2);

		assertThat(line1.getConfirmedQty(), comparesEqualTo(new BigDecimal("2")));
		assertThat(line1.isActive(), is(true));
		assertThat(line2.getConfirmedQty(), comparesEqualTo(new BigDecimal("2"))); // unchanged
	}

	/**
	 * We have four lines, and their confirmed qty is 17.
	 * QtyEntered is 10, so the manual line's sum needs to be decreased by 7.
	 * Those two shall be subtracted from the "item-backordered" line, then the "item-rejected" line and finally the "item-canceled" line.
	 */
	@Test
	public void updateManualLinesFromCalculatedLine_2()
	{
		assertThat(OrdrspTestsHelper.QTY_ENTERED, comparesEqualTo(new BigDecimal("10"))); // guard

		final I_EDI_OrdrspLine line0 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemAccepted, new BigDecimal("9"));
		final I_EDI_OrdrspLine line1 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemBackordered, new BigDecimal("4"));
		final I_EDI_OrdrspLine line2 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemRejected, new BigDecimal("2"));
		final I_EDI_OrdrspLine line3 = helper.createLine(X_EDI_OrdrspLine.QUANTITYQUALIFIER_ItemCancelled, new BigDecimal("2"));

		new OrdrspBL().updateManualLinesFromCalculatedLine(line0);

		InterfaceWrapperHelper.refresh(line1);
		InterfaceWrapperHelper.refresh(line2);
		InterfaceWrapperHelper.refresh(line3);

		assertThat(line1.getConfirmedQty(), comparesEqualTo(new BigDecimal("0")));
		assertThat(line1.isActive(), is(false));
		assertThat(line2.getConfirmedQty(), comparesEqualTo(new BigDecimal("0")));
		assertThat(line2.isActive(), is(false));
		assertThat(line3.getConfirmedQty(), comparesEqualTo(new BigDecimal("1")));
		assertThat(line3.isActive(), is(true));
	}

}
