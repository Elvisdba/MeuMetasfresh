package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.inout.util.CachedObjects;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.model.BPartner;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IUpdatableSchedulesCollector;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderPA;
import de.metas.product.IProductBL;

/**
 * This collector is currently disabled, see the interface's javadoc.
 *
 */
public class UpdatableSchedulesCollector implements IUpdatableSchedulesCollector
{
	/**
	 * Does nothing, just returns <code>seed</code>.
	 */
	@Override
	public List<OlAndSched> collectUpdatableLines(
			final Properties ctx,
			final List<OlAndSched> seed,
			final CachedObjects co,
			final String trxName)
	{
		return seed;
	}

	final List<OlAndSched> inspectSingleOl(
			final Properties ctx,
			final I_C_OrderLine ol,
			final Set<Integer> productsSeen,
			final Set<Integer> bPartnersSeen,
			final CachedObjects co,
			final String trxName)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		final List<OlAndSched> newOlAndScheds = new ArrayList<OlAndSched>();

		final boolean hasPostageFreeAmt = bPartnerHasPostageFreeAmt(co, ol);

		final boolean nonItemProduct = isNonItemProduct(ctx, co, ol, trxName);

		if (hasPostageFreeAmt || nonItemProduct)
		{
			final int bPartnerId = ol.getC_BPartner_ID();

			if (bPartnersSeen.add(bPartnerId))
			{
				addForBPartnerAndProducts(ctx, bPartnerId, newOlAndScheds, productsSeen, trxName);
			}
		}

		//
		// Check if there is a non-item-product in the same order. If so, it needs to be updated, too.
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		for (final I_C_OrderLine lineOfOrder : Services.get(IOrderPA.class).retrieveOrderLines(ctx, ol.getC_Order_ID(), I_C_OrderLine.class, trxName))
		{
			if (productsSeen.add(lineOfOrder.getM_Product_ID()))
			{
				if (isNonItemProduct(ctx, co, InterfaceWrapperHelper.create(lineOfOrder, I_C_OrderLine.class), trxName))
				{
					final I_M_ShipmentSchedule sched = shipmentSchedulePA.retrieveForOrderLine(ctx, lineOfOrder.getC_OrderLine_ID(), trxName);
					if (sched != null)
					{
						// note: if sched is null, it is no problem, because the updater will create (and update!) any
						// sched that is not yet there
						newOlAndScheds.add(new OlAndSched(InterfaceWrapperHelper.create(lineOfOrder, I_C_OrderLine.class), sched));
					}
				}
			}
		}

		return newOlAndScheds;
	}

	private boolean isNonItemProduct(final Properties ctx, final CachedObjects co, final I_C_OrderLine ol, final String trxName)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		if (ol.getM_Product_ID() <= 0)
		{
			return false;
		}

		I_M_Product product = co.getProductCache().get(ol.getM_Product_ID());
		if (product == null)
		{
			product = InterfaceWrapperHelper.create(ol.getM_Product(), I_M_Product.class);
			Check.assume(product != null, "C_OrderLine.M_Product_ID has an FK-constraint on M_Product and therefore 'product' is not null");

			co.getProductCache().put(ol.getM_Product_ID(), product);
		}

		return !Services.get(IProductBL.class).isItem(product);
	}

	private boolean bPartnerHasPostageFreeAmt(final CachedObjects co, final I_C_OrderLine ol)
	{
		Check.assume(ol != null, "Param 'ol' is not null");

		final BPartner bpartner = co.retrieveAndCacheBPartner(ol);

		final BigDecimal postageFreeAmt = bpartner.getPostageFreeAmt();

		final boolean hasPostageFreeAmt = postageFreeAmt != null && postageFreeAmt.signum() > 0;
		return hasPostageFreeAmt;
	}

	/**
	 * Adds those shipment schedules (A) that have the given bPartnerId. For each of theses scheds (A), it then adds all
	 * further shipment schedules (B) that have the same M_Product_ID as (A).
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param newOlAndScheds
	 * @param productsSeen
	 * @param trxName
	 */
	private void addForBPartnerAndProducts(
			final Properties ctx,
			final int bPartnerId,
			final List<OlAndSched> newOlAndScheds,
			final Set<Integer> productsSeen,
			final String trxName)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final List<OlAndSched> allForBPartner = shipmentSchedulePA.retrieveOlAndSchedsForBPartner(bPartnerId, false, trxName);

		for (final OlAndSched currentForBPartner : allForBPartner)
		{
			final int currentProductId = currentForBPartner.getOl().getM_Product_ID();

			if (!productsSeen.contains(currentProductId))
			{
				productsSeen.add(currentProductId);

				final List<OlAndSched> allForProduct =
						shipmentSchedulePA.retrieveOlAndSchedsForProduct(ctx, currentProductId, trxName);

				newOlAndScheds.addAll(allForProduct);
			}
		}
	}
}
