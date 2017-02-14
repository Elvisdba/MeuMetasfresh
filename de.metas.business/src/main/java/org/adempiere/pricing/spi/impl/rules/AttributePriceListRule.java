package org.adempiere.pricing.spi.impl.rules;

import java.util.Optional;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IAttributePricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.ProductPriceQuery.ASIProductPriceAttributesFilter;
import org.adempiere.pricing.spi.impl.rules.PriceList.IProductPriceRule;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.swat.base
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

public class AttributePriceListRule implements IProductPriceRule
{
	private static final Logger logger = LogManager.getLogger(AttributePriceListRule.class);

	@Override
	public boolean matches(IPricingContext pricingCtx, IPricingResult result, I_M_ProductPrice productPrice)
	{
		if (!productPrice.isAttributeDependant())
		{
			return false;
		}

		final I_M_AttributeSetInstance attributeSetInstance = getM_AttributeSetInstance(pricingCtx); // might be null
		if (attributeSetInstance == null)
		{
			return false;
		}

		final boolean asiMatching = ASIProductPriceAttributesFilter.of(attributeSetInstance).accept(productPrice);
		if (!asiMatching)
		{
			logger.debug("ASI '{}' does not match {}", attributeSetInstance, productPrice);
			return false;
		}

		return true;
	}

	@Override
	public void updateResult(IPricingContext pricingCtx, IPricingResult result, I_M_ProductPrice productPrice)
	{
		result.setTaxIncluded(false);

		// 08803: store the information about the price relevant attributes
		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		result.addPricingAttributes(attributePricingBL.extractPricingAttributes(productPrice));
	}

	/**
	 * Extracts the ASI from pricing context.
	 * 
	 * @param pricingCtx
	 * @return
	 *         <ul>
	 *         <li>ASI
	 *         <li><code>null</code> if the given <code>pricingCtx</code> has no <code>ReferencedObject</code> or if the referenced object can't be converted in an {@link IAttributeSetInstanceAware}.
	 *         </ul>
	 */
	public final static I_M_AttributeSetInstance getM_AttributeSetInstance(final IPricingContext pricingCtx)
	{
		final IAttributeSetInstanceAware asiAware = getAttributeSetInstanceAware(pricingCtx).orElse(null);
		if (asiAware == null)
		{
			return null;
		}

		//
		// Get M_AttributeSetInstance_ID and return it.
		// NOTE: to respect the method contract, ALWAYS return ZERO if it's not set, no matter if the getter returned -1.
		final int attributeSetInstanceId = asiAware.getM_AttributeSetInstance_ID();
		if (attributeSetInstanceId <= 0)
		{
			return null;
		}

		final I_M_AttributeSetInstance attributeSetInstance = InterfaceWrapperHelper.create(pricingCtx.getCtx(), attributeSetInstanceId, I_M_AttributeSetInstance.class, pricingCtx.getTrxName());
		return attributeSetInstance;
	}

	static final Optional<IAttributeSetInstanceAware> getAttributeSetInstanceAware(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();
		if (null == referencedObj)
		{
			return Optional.empty();
		}

		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(referencedObj);
		return Optional.ofNullable(asiAware);
	}
}
