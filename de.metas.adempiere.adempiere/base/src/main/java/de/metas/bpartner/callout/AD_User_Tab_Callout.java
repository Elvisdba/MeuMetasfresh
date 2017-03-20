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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;

import de.metas.bpartner.IBPartnerDAO;

// FIXME: it seems this tab callout it's not used. Consider deleting it.
public class AD_User_Tab_Callout extends TabCalloutAdapter
{

	public static final String MSG_NoDefaultContactError = "NoDefaultContactError";

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_AD_User user = calloutRecord.getModel(I_AD_User.class);

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final boolean alreadyHasDefaultContact = bpartnerDAO.retrieveBPartnerAgg(Env.getCtx(), user.getAD_User_ID())
				.getContacts()
				.hasDefault();

		user.setIsDefaultContact(!alreadyHasDefaultContact);
	}
}
