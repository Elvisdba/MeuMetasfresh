package de.metas.product;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Collection;
import java.util.Properties;

import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MAttributeSetInstance;

import de.metas.adempiere.model.I_M_Product;

public interface IProductPA extends ISingletonService
{
	I_M_Product retrieveProduct(Properties ctx, String value, boolean throwEx, String trxName);

	I_M_Product retrieveProduct(Properties ctx, int productId, boolean throwEx, String trxName);

	Collection<MAttributeSetInstance> retrieveSerno(int productId, String text, String trxName);

	/**
	 * Loads the lines of completed {@link I_M_Requisition}s for a product and warehouse.
	 * 
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return the requisition lines for the given product and warehouse.
	 */
	Collection<I_M_RequisitionLine> retrieveRequisitionlines(int productId, int warehouseId, String trxName);

	/**
	 * 
	 * @param ctx
	 * @param pricingSystemId
	 * @param locationId
	 * @param isSOPriceList
	 * @param trxName
	 * @return the price list for the given pricing system and location or <code>null</code>.
	 */
	I_M_PriceList retrievePriceListByPricingSyst(Properties ctx, int pricingSystemId, int bPartnerLocationId, boolean isSOPriceList, String trxName);

	/**
	 * 
	 * @param productId
	 * @param bPartnerId
	 * @param priceListId
	 * @param qty
	 * @param soTrx
	 * @return
	 * @throws ProductNotOnPriceListException
	 */
	@Deprecated
	BigDecimal retrievePriceStd(int productId, int bPartnerId, int priceListId, BigDecimal qty, boolean soTrx);

	/**
	 * @deprecated please use {@link IProductBL#getStockingUOM(org.compiere.model.I_M_Product)}
	 */
	@Deprecated
	I_C_UOM retrieveProductUOM(Properties ctx, int productId);

}
