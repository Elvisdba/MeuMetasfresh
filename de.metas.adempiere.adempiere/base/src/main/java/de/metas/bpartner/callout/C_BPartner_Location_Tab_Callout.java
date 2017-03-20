package de.metas.bpartner.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.bpartner.BPartnerLocations;
import de.metas.bpartner.IBPartnerDAO;

// FIXME: i think we shall delete this callout because the only place where it's used is in Cockpit->Addressen which is actually no longer used.
public class C_BPartner_Location_Tab_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_BPartner_Location bpLocation = calloutRecord.getModel(I_C_BPartner_Location.class);
		
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final BPartnerLocations bpLocations = bpartnerDAO.retrieveLocations(bpLocation.getC_BPartner_ID());

		if (!bpLocations.hasShipToDefault())
		{
			bpLocation.setIsShipTo(true);
			bpLocation.setIsShipToDefault(true);
		}
		else
		{
			bpLocation.setIsShipTo(false);
			bpLocation.setIsShipToDefault(false);
		}

		if (!bpLocations.hasBillToDefault())
		{
			bpLocation.setIsBillTo(true);
			bpLocation.setIsBillToDefault(true);
		}
		else
		{
			bpLocation.setIsBillTo(false);
			bpLocation.setIsBillToDefault(false);
		}
		if (!bpLocations.hasHandOver())
		{
			bpLocation.setIsHandOverLocation(true);
		}
		else
		{
			bpLocation.setIsHandOverLocation(false);
		}
		// TODO: needs to be moved into de.metas.contracts project
		// if (!bPartnerPA.existsDefaultAddressInTable(address, null, I_C_BPartner_Location.COLUMNNAME_IsSubscriptionToDefault))
		// {
		// address.setIsSubscriptionTo(true);
		// address.setIsSubscriptionToDefault(true);
		// }
		// else
		// {
		// address.setIsSubscriptionTo(false);
		// address.setIsSubscriptionToDefault(false);
		// }
	}
}
