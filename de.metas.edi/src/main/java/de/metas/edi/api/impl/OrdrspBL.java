package de.metas.edi.api.impl;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.service.IOrderBL;
import de.metas.edi.api.IEDIBPartnerService;
import de.metas.edi.api.IOrdrspBL;
import de.metas.edi.api.IOrdrspDAO;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_BPartner_Location;
import de.metas.edi.model.I_C_Order;
import de.metas.esb.edi.model.I_EDI_Ordrsp;


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

public class OrdrspBL implements IOrdrspBL
{

	@Override
	public I_EDI_Ordrsp addToOrdrspCreateIfNotExistForOrder(I_C_Order order)
	{
		Check.assumeNotEmpty(order.getPOReference(), "C_Order {} has a not-empty POReference", order);

		final I_EDI_Ordrsp ordrsp = retrieveOrCreateDesadv(order);
		return ordrsp;
	}

	private I_EDI_Ordrsp retrieveOrCreateDesadv(final I_C_Order order)
	{
		final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		// what's the handover partner and the handover-location?

		final I_C_BPartner handoverPartner = InterfaceWrapperHelper.create(orderBL.getHandoverPartner(order), I_C_BPartner.class);
		I_EDI_Ordrsp ordrsp = ordrspDAO.retrieveMatchingOrdrspOrNull(handoverPartner, order.getPOReference(), InterfaceWrapperHelper.getContextAware(order));

		if (ordrsp == null)
		{
			ordrsp = InterfaceWrapperHelper.newInstance(I_EDI_Ordrsp.class, order);

			ordrsp.setPOReference(order.getPOReference());
			ordrsp.setC_Currency_ID(order.getC_Currency_ID());

			// TODO: sort out those two!
			ordrsp.setDeliveryDate(order.getDatePromised());
			ordrsp.setShipDate(order.getDatePromised());

			ordrsp.setHandOver_Partner(handoverPartner);

			final I_C_BPartner_Location handoverLocation = InterfaceWrapperHelper.create(orderBL.getHandoverLocation(order), I_C_BPartner_Location.class);
			ordrsp.setHandOver_Location_ID(handoverLocation.getC_BPartner_Location_ID());
			ordrsp.setDeliveryGLN(handoverLocation.getGLN());

			// https://github.com/metasfresh/metasfresh/issues/307
			final I_C_BPartner orgBPartner = InterfaceWrapperHelper.create(Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(order.getAD_Org()),I_C_BPartner.class);
			final String supplierGLN = Services.get(IEDIBPartnerService.class).getEdiPartnerIdentification(orgBPartner, order.getDatePromised());
			ordrsp.setSupplierGLN(supplierGLN);

			InterfaceWrapperHelper.save(ordrsp);
		}
		return ordrsp;
	}

	@Override
	public void removeOrderFromOrdrsp(I_C_Order order)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMinimumPercentage(I_EDI_Ordrsp ordrsp)
	{
		final BigDecimal minimumPercentageAccepted = Services.get(IOrdrspDAO.class).retrieveMinimumSumPercentage();
		ordrsp.setEDI_ORDRSP_MinimumSumPercentage(minimumPercentageAccepted);
	}

}
