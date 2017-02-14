package org.adempiere.pricing.spi.impl.rules;

import java.util.Optional;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.IProductPriceAware;
import org.adempiere.pricing.ProductPriceAware;
import org.adempiere.pricing.api.IAttributePricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

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

public class ExplicitProductPrice extends PricingRuleAdapter
{
	private static final Logger logger = LogManager.getLogger(ExplicitProductPrice.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final I_M_ProductPrice productPrice = getProductPriceIfMatches(pricingCtx);
		if (productPrice == null)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final I_M_ProductPrice productPrice = getProductPriceIfMatches(pricingCtx);
		if (productPrice == null)
		{
			// shall not happen
			return;
		}

		PriceList.updateResult(pricingCtx, result, productPrice);
	}

	private static final I_M_ProductPrice getProductPriceIfMatches(final IPricingContext pricingCtx)
	{
		final IProductPriceAware priceAttributeAware = getProductPriceAttributeAware(pricingCtx).orElse(null);
		if (priceAttributeAware == null)
		{
			return null;
		}
		if (!priceAttributeAware.isExplicitProductPriceAttribute())
		{
			return null;
		}

		final int productPriceId = priceAttributeAware.getM_ProductPrice_ID();
		if (productPriceId <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_ID is not set: {}", pricingCtx);
			return null;
		}

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(pricingCtx.getCtx(), productPriceId, I_M_ProductPrice.class, pricingCtx.getTrxName());
		if (productPrice == null || productPrice.getM_ProductPrice_ID() <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_ID={} was not found", productPriceId);
			return null;
		}

		// Make sure the product price attribute is still active.
		if (!productPrice.isActive())
		{
			logger.debug("Returning null because M_ProductPrice is not active: {}", productPrice);
			return null;
		}

		// Make sure if the product price matches our pricing context
		if (productPrice.getM_Product_ID() != pricingCtx.getM_Product_ID())
		{
			logger.debug("Returning null because M_ProductPrice.M_Product_ID is not matching pricing context product: {}", productPrice);
			return null;
		}

		return productPrice;
	}

	private static final Optional<IProductPriceAware> getProductPriceAttributeAware(final IPricingContext pricingCtx)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();

		// 1st try the direct way, using the referenced object itself as IProductPriceAttributeAware
		final Optional<IProductPriceAware> directProductPriceAware = ProductPriceAware.ofModel(referencedObject);
		if (directProductPriceAware.isPresent())
		{
			return directProductPriceAware;
		}

		// 2nd fall back to viewing the referenced object as ASI and then check if someone attached a IProductPriceAware to it as dynamic attribute
		final Optional<IAttributeSetInstanceAware> attributeSetInstanceAware = AttributePriceListRule.getAttributeSetInstanceAware(pricingCtx);
		if (attributeSetInstanceAware.isPresent())
		{
			final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

			final Optional<IProductPriceAware> explicitProductPriceAware = attributePricingBL.getDynAttrProductPriceAttributeAware(attributeSetInstanceAware.get());
			return explicitProductPriceAware;
		}
		return Optional.empty();
	}

}
