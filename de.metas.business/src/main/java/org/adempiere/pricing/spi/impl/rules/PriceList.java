package org.adempiere.pricing.spi.impl.rules;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MPriceList;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PriceList extends PricingRuleAdapter
{
	private static final Logger logger = LogManager.getLogger(PriceList.class);

	public static interface IProductPriceRule
	{
		boolean matches(IPricingContext pricingCtx, IPricingResult result, I_M_ProductPrice productPrice);

		void updateResult(IPricingContext pricingCtx, IPricingResult result, I_M_ProductPrice productPrice);
	}

	private static final CopyOnWriteArrayList<IProductPriceRule> priceListRules = new CopyOnWriteArrayList<>();

	public static final void registerRule(final IProductPriceRule rule)
	{
		Check.assumeNotNull(rule, "Parameter rule is not null");
		priceListRules.addIfAbsent(rule);
	}

	private List<IProductPriceRule> getPriceListRules()
	{
		return priceListRules;
	}

	private List<IProductPriceRule> getPriceListRulesThatMatches(final IPricingContext pricingCtx, final IPricingResult result, final I_M_ProductPrice productPrice)
	{
		final ImmutableList.Builder<IProductPriceRule> matchingRules = ImmutableList.builder();
		for (final IProductPriceRule rule : getPriceListRules())
		{
			if (!rule.matches(pricingCtx, result, productPrice))
			{
				continue;
			}

			matchingRules.add(rule);
		}

		return matchingRules.build();
	}

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getM_Product_ID() <= 0)
		{
			logger.debug("Not applying because there is no M_Product_ID specified in context");
			return false;
		}

		if (pricingCtx.getM_PriceList_ID() <= 0)
		{
			final String msg = "pricingCtx {} contains no priceList";
			Loggables.get().addLog(msg, pricingCtx);
			logger.error(msg, pricingCtx);
			Trace.printStack();
			return false; // false;
		}

		if (pricingCtx.getM_PriceList_ID() == MPriceList.M_PriceList_ID_None)
		{
			logger.info("Not applying because PriceList is NoPriceList ({})", pricingCtx);
			return false;
		}

		if (pricingCtx.getM_PriceList_Version_ID() <= 0)
		{
			logger.info("Not applying because M_PriceList_Version_ID is not set ({})", pricingCtx);
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final int productId = pricingCtx.getM_Product_ID();

		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null || !plv.isActive())
		{
			return;
		}

		final List<I_M_ProductPrice> productPrices = Services.get(IPriceListDAO.class).retrieveProductPrices(plv, productId);
		if (productPrices.isEmpty())
		{
			logger.trace("No product prices found for M_Product_ID={} in {}", productId, plv);
			return;
		}

		//
		// TODO: check the explicit product price

		//
		// Check the price list rules
		for (final I_M_ProductPrice productPrice : productPrices)
		{
			final List<IProductPriceRule> rules = getPriceListRulesThatMatches(pricingCtx, result, productPrice);

			// If no matching rules found then skip this product price
			if (rules.isEmpty())
			{
				continue;
			}

			//
			// Some rules are matching.
			// => Update the pricing result
			result.setCalculated(true);
			updateResult(pricingCtx, result, productPrice);
			for (final IProductPriceRule rule : rules)
			{
				rule.updateResult(pricingCtx, result, productPrice);
			}
			// Stop here, we have a result
			return;
		}
	}

	public static void updateResult(final IPricingContext pricingCtx, final IPricingResult result, final I_M_ProductPrice productPrice)
	{
		Check.assumeNotNull(productPrice, "Parameter productPrice is not null");
		
		final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();
		final I_M_PriceList priceList = priceListVersion.getM_PriceList();
		final I_M_Product product = productPrice.getM_Product();

		result.setPriceStd(productPrice.getPriceStd());
		result.setPriceList(productPrice.getPriceList());
		result.setPriceLimit(productPrice.getPriceLimit());
		result.setC_Currency_ID(priceList.getC_Currency_ID());
		result.setM_Product_Category_ID(product.getM_Product_Category_ID());
		result.setEnforcePriceLimit(priceList.isEnforcePriceLimit());
		result.setTaxIncluded(priceList.isTaxIncluded());
		result.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
		result.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID()); // make sure that the result doesn't lack this important info, even if it was already known from the context!

		// 06942 : use product price uom all the time
		if (productPrice.getC_UOM_ID() <= 0)
		{
			result.setPrice_UOM_ID(product.getC_UOM_ID());
		}
		else
		{
			result.setPrice_UOM_ID(productPrice.getC_UOM_ID());
		}

		result.setCalculated(true);
	}
}
