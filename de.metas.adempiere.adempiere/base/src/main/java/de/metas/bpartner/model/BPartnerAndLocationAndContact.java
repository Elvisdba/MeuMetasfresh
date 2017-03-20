package de.metas.bpartner.model;

import java.util.Optional;
import java.util.function.Consumer;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * BPartner/one Location/one Contact structure.
 *
 * Mainly this is more a convenient accessor for {@link BPartner} aggregate.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@lombok.Value
public class BPartnerAndLocationAndContact
{
	private final BPartner bpartner;
	private final int bpartnerLocationId;
	private final int contactId;

	public I_C_BPartner getBPartnerData()
	{
		return bpartner.getBPartnerData();
	}

	public void edit(final Consumer<I_C_BPartner> consumer)
	{
		bpartner.edit(consumer);
	}
	
	public boolean isLocationSet()
	{
		return bpartnerLocationId > 0;
	}

	public I_C_BPartner_Location getLocationData()
	{
		return bpartner.getBPartnerLocations().getById(bpartnerLocationId);
	}

	public Optional<I_C_Location> getAddressByIdIfExists()
	{
		return bpartner.getBPartnerLocations().getAddressByIdIfExists(bpartnerLocationId);
	}

	public void editLocation(final Consumer<I_C_BPartner_Location> consumer)
	{
		bpartner.getBPartnerLocations()
				.editById(bpartnerLocationId, consumer);
	}

	public I_AD_User getContactData()
	{
		return bpartner.getContacts().getById(contactId);
	}
	
	public Optional<I_AD_User> getLocationContactData()
	{
		return bpartner.getContacts().getByLocationIdOrDefault(bpartnerLocationId);
	}

	public void editContact(final Consumer<I_AD_User> consumer)
	{
		bpartner.getContacts()
				.editById(contactId, consumer);
	}
}
