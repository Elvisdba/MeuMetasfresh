package org.eevolution.mrp.spi.impl.ddorder;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_DD_Order;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;
import org.springframework.stereotype.Service;

import de.metas.document.IDocTypeDAO;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.ddorder.DDOrderUtil;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-mrp
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
@Service
public class DDOrderProducer
{

	public I_DD_Order createDDOrder(
			@NonNull final DDOrder pojo,
			@NonNull final Date dateOrdered)
	{
		return createDDOrder(pojo, dateOrdered, null);
	}

	public I_DD_Order createDDOrder(
			@NonNull final DDOrder pojo,
			@NonNull final IMRPCreateSupplyRequest request)
	{
		return createDDOrder(pojo,
				request.getMrpContext().getDate(),
				request);
	}

	/**
	 * 
	 * @param pojo
	 * @param request may be {@code null}. If not-null, then the method also does some {@link I_PP_MRP} related stuff that is likely to become obsolete soon.
	 * @return
	 */
	private I_DD_Order createDDOrder(
			@NonNull final DDOrder pojo,
			@NonNull final Date dateOrdered,
			final IMRPCreateSupplyRequest request)
	{
		final I_PP_Product_Planning productPlanning = InterfaceWrapperHelper.create(Env.getCtx(), pojo.getProductPlanningId(), I_PP_Product_Planning.class, ITrx.TRXNAME_ThreadInherited);

		final int orgBPartnerId = DDOrderUtil.retrieveOrgBPartnerId(Env.getCtx(), pojo.getOrgId());
		final int orgBPartnerLocationId = DDOrderUtil.retrieveOrgBPartnerLocationId(Env.getCtx(), pojo.getOrgId());

		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(pojo.getOrgId());
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setAD_Org_ID(pojo.getOrgId());
		ddOrder.setPP_Plant_ID(pojo.getPlantId());
		ddOrder.setC_BPartner_ID(orgBPartnerId);
		ddOrder.setC_BPartner_Location_ID(orgBPartnerLocationId);
		ddOrder.setAD_User_ID(productPlanning.getPlanner_ID()); // FIXME: improve performances/cache and retrive Primary BP's User
		ddOrder.setSalesRep_ID(productPlanning.getPlanner_ID());

		ddOrder.setC_DocType_ID(getC_DocType_ID(pojo.getOrgId()));

		final int inTransitWarehouseId = DDOrderUtil.retrieveInTransitWarehouseId(Env.getCtx(), pojo.getOrgId());
		ddOrder.setM_Warehouse_ID(inTransitWarehouseId);

		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrder.setDateOrdered(new Timestamp(dateOrdered.getTime()));
		ddOrder.setDatePromised(new Timestamp(pojo.getDatePromised().getTime()));
		ddOrder.setM_Shipper_ID(pojo.getShipperId());
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);

		ddOrder.setPP_Product_Planning_ID(productPlanning.getPP_Product_Planning_ID());

		InterfaceWrapperHelper.save(ddOrder);

		for (final DDOrderLine linePojo : pojo.getLines())
		{
			//
			// Create DD Order Line
			final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
			ddOrderline.setAD_Org_ID(pojo.getOrgId());
			ddOrderline.setDD_Order(ddOrder);
			ddOrderline.setC_OrderLineSO_ID(linePojo.getSalesOrderLineId());
			if (linePojo.getSalesOrderLineId() > 0)
			{
				ddOrderline.setC_BPartner_ID(ddOrderline.getC_OrderLineSO().getC_BPartner_ID());
			}

			final I_DD_NetworkDistributionLine networkDistributionLine = InterfaceWrapperHelper.create(Env.getCtx(), linePojo.getNetworkDistributionLineId(), I_DD_NetworkDistributionLine.class, ITrx.TRXNAME_ThreadInherited);

			ddOrderline.setDD_NetworkDistributionLine_ID(networkDistributionLine.getDD_NetworkDistributionLine_ID());

			// get supply source warehouse and locator
			final I_M_Warehouse warehouseFrom = networkDistributionLine.getM_WarehouseSource();
			final I_M_Locator locatorFrom = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseFrom);

			// get supply target warehouse and locator
			final I_M_Warehouse warehouseTo = networkDistributionLine.getM_Warehouse();
			final I_M_Locator locatorTo = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseTo);

			//
			// Locator From/To
			ddOrderline.setM_Locator_ID(locatorFrom.getM_Locator_ID());
			ddOrderline.setM_LocatorTo_ID(locatorTo.getM_Locator_ID());

			//
			// Product, UOM, Qty
			// NOTE: we assume qtyToMove is in "mrpContext.getC_UOM()" which shall be the Product's stocking UOM
			final ProductDescriptor productDescriptor = linePojo.getProductDescriptor();
			final I_M_Product product = load(productDescriptor.getProductId(), I_M_Product.class);

			final I_M_AttributeSetInstance asi = load(productDescriptor.getAttributeSetInstanceId(), I_M_AttributeSetInstance.class);
			final ASICopy asiCopy = ASICopy.newInstance(asi);

			ddOrderline.setM_Product_ID(product.getM_Product_ID());
			ddOrderline.setC_UOM_ID(product.getC_UOM_ID());
			ddOrderline.setQtyEntered(linePojo.getQty());
			ddOrderline.setQtyOrdered(linePojo.getQty());
			ddOrderline.setTargetQty(linePojo.getQty());
			ddOrderline.setM_AttributeSetInstance(asiCopy.copy());
			ddOrderline.setM_AttributeSetInstanceTo(asiCopy.copy());

			//
			// Dates
			ddOrderline.setDateOrdered(ddOrder.getDateOrdered());
			ddOrderline.setDatePromised(ddOrder.getDatePromised());

			//
			// Other flags
			ddOrderline.setIsInvoiced(false);
			ddOrderline.setDD_AllowPush(networkDistributionLine.isDD_AllowPush());
			ddOrderline.setIsKeepTargetPlant(networkDistributionLine.isKeepTargetPlant());

			//
			// Save DD Order Line
			InterfaceWrapperHelper.save(ddOrderline);

			final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

			if (request != null)
			{
				//
				// Create DD_OrderLine_Alternatives
				// NOTE: demand MRP line is available only in lot-for-lot order policy
				// TODO: i think we shall get all MRP demands, retrieve their alternatives, aggregate them and create DD_OrderLine_Alternatives
				final I_PP_MRP parentMRPDemand = request.getMRPDemandRecordOrNull();
				if (parentMRPDemand != null)
				{
					final List<I_PP_MRP_Alternative> mrpAlternatives = mrpDAO.retrieveMRPAlternativesQuery(parentMRPDemand).create().list();
					for (final I_PP_MRP_Alternative mrpAlternative : mrpAlternatives)
					{
						createDD_OrderLine_Alternative(ddOrderline, mrpAlternative);
					}
				}
				final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());

				//
				// Set Correct Planning Dates
				final Timestamp supplyDateStartSchedule = TimeUtil.addDays(supplyDateFinishSchedule, 0 - linePojo.getDurationDays());

				final List<I_PP_MRP> mrpList = mrpDAO.retrieveMRPRecords(ddOrderline);
				for (final I_PP_MRP mrp : mrpList)
				{
					mrp.setDateStartSchedule(supplyDateStartSchedule);
					mrp.setDateFinishSchedule(supplyDateFinishSchedule);
					InterfaceWrapperHelper.save(mrp);
				}
			}
		}

		return ddOrder;
	}

	private void createDD_OrderLine_Alternative(
			final I_DD_OrderLine ddOrderLine,
			final I_PP_MRP_Alternative mrpAlternative)
	{
		final IMRPBL mrpBL = Services.get(IMRPBL.class);

		final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_Alternative.class, ddOrderLine);
		ddOrderLineAlt.setAD_Org_ID(mrpAlternative.getAD_Org_ID());
		ddOrderLineAlt.setDD_OrderLine(ddOrderLine);

		final I_M_Product product = mrpAlternative.getM_Product();
		final I_C_UOM uom = mrpBL.getC_UOM(mrpAlternative);
		final BigDecimal qty = mrpAlternative.getQty();

		ddOrderLineAlt.setM_Product(product);
		ddOrderLineAlt.setM_AttributeSetInstance(ddOrderLine.getM_AttributeSetInstance());
		ddOrderLineAlt.setC_UOM(uom);
		ddOrderLineAlt.setQtyOrdered(qty);
		ddOrderLineAlt.setQtyDelivered(BigDecimal.ZERO);
		ddOrderLineAlt.setQtyInTransit(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(ddOrderLineAlt);
	}

	private int getC_DocType_ID(final int orgId)
	{
		final Properties ctx = Env.getCtx();

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final I_AD_Org org = orgDAO.retrieveOrg(ctx, orgId);

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		return docTypeDAO.getDocTypeId(ctx, X_C_DocType.DOCBASETYPE_ManufacturingOrder, org.getAD_Client_ID(), orgId, ITrx.TRXNAME_None);
	}
}
