package de.metas.handlingunits.pricing.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.impl.rules.PriceList.IProductPriceRule;
import org.slf4j.Logger;

import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUPriceListRule implements IProductPriceRule
{
	private static final Logger logger = LogManager.getLogger(HUPriceListRule.class);

	@Override
	public boolean matches(final IPricingContext pricingCtx, final IPricingResult result, final org.compiere.model.I_M_ProductPrice productPrice)
	{
		final I_M_ProductPrice huProductPrice = InterfaceWrapperHelper.create(productPrice, I_M_ProductPrice.class);
		final int productPrice_HUPIItemProductId = huProductPrice.getM_HU_PI_Item_Product_ID();
		if (productPrice_HUPIItemProductId <= 0)
		{
			return false;
		}

		final int huPIItemProductId = getM_HU_PI_Item_Product_ID(pricingCtx);
		if (huPIItemProductId <= 0)
		{
			logger.debug("No M_HU_PI_Item_Product_ID found: {}", pricingCtx);
			return false;
		}

		if (huPIItemProductId != productPrice_HUPIItemProductId)
		{
			return false;
		}

		return true;
	}

	@Override
	public void updateResult(final IPricingContext pricingCtx, final IPricingResult result, final org.compiere.model.I_M_ProductPrice productPrice)
	{
		result.setTaxIncluded(false);
	}

	private static final int getM_HU_PI_Item_Product_ID(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();

		if (null == referencedObj)
		{
			return -1;
		}

		//
		// check if we have an piip-aware
		if (referencedObj instanceof I_M_HU_PI_Item_Product_Aware)
		{
			return ((I_M_HU_PI_Item_Product_Aware)referencedObj).getM_HU_PI_Item_Product_ID();
		}

		//
		// check if there is a M_HU_PI_Item_Product_ID(_Override) column.
		if (InterfaceWrapperHelper.hasModelColumnName(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			final Integer valueOverrideOrValue = InterfaceWrapperHelper.getValueOverrideOrValue(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID);
			return valueOverrideOrValue == null ? 0 : valueOverrideOrValue.intValue();
		}

		return -1;
	}

}
