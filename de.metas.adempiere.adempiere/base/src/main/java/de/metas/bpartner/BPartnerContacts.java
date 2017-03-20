package de.metas.bpartner;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

public class BPartnerContacts implements Iterable<I_AD_User>
{
	private final int bpartnerId;
	private final List<I_AD_User> contacts;

	public BPartnerContacts(final int bpartnerId, final List<I_AD_User> contacts)
	{
		this.bpartnerId = bpartnerId;
		this.contacts = contacts;

		// TODO: mark all of them as readonly, prevent changing from outside
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("bpartnerId", bpartnerId)
				.addValue(contacts)
				.toString();
	}

	public Stream<I_AD_User> stream()
	{
		return contacts.stream();
	}

	public List<I_AD_User> toList()
	{
		return ImmutableList.copyOf(contacts);
	}

	/** @return immutable iterator */
	@Override
	public Iterator<I_AD_User> iterator()
	{
		return toList().iterator();
	}
	
	public I_AD_User first()
	{
		return contacts.get(0);
	}
	
	public boolean isEmpty()
	{
		return contacts.isEmpty();
	}

	public Optional<I_AD_User> getByIdIfExists(final int contactId)
	{
		if (contactId <= 0)
		{
			return Optional.empty();
		}
		return stream()
				.filter(contact -> contact.getAD_User_ID() == contactId)
				.findFirst();
	}

	public I_AD_User getById(final int contactId)
	{
		return getByIdIfExists(contactId)
				.orElseThrow(() -> new AdempiereException("No BPartner contact found for " + contactId + " in " + this));
	}

	public <T extends I_AD_User> Optional<T> getDefaultIfExists(final Class<T> clazz)
	{
		return getDefaultIfExists()
				.map(contact -> InterfaceWrapperHelper.create(contact, clazz));
	}

	public Optional<I_AD_User> getDefaultIfExists()
	{
		return stream()
				.filter(contact -> contact.isActive())
				.filter(contact -> contact.isDefaultContact())
				.findFirst();
	}

	public Optional<I_AD_User> getDefaultOrFirst()
	{
		final List<I_AD_User> contacts = this.contacts;
		if (contacts.isEmpty())
		{
			return null;
		}

		final Optional<I_AD_User> defaultContact = contacts.stream()
				.filter(contact -> contact.isActive())
				.filter(contact -> contact.isDefaultContact())
				.findFirst();
		if (defaultContact.isPresent())
		{
			return defaultContact;
		}

		// Get first active contact
		return contacts.stream()
				.filter(contact -> contact.isActive())
				.findFirst();
	}

	public Optional<I_AD_User> getDefaultSalesOrPurchase(final boolean isSOTrx)
	{
		// #928: DefaultContact is no longer relevant in contact retrieval. The Sales and Purchase defaults are used instead
		// .addColumn(I_AD_User.COLUMNNAME_IsDefaultContact, Direction.Descending, Nulls.Last)
		final Comparator<I_AD_User> ordering = Comparator.comparing(contact -> contact.getAD_User_ID());

		// #928
		// Only retrieve users that are default for sales or purchase (depending on the isSOTrx)
		// Sales
		if (isSOTrx)
		{
			return stream()
					.filter(contact -> contact.isSalesContact() && contact.isSalesContact_Default())
					.sorted(ordering)
					.findFirst();
		}
		// Purchase
		else
		{
			return stream()
					.filter(contact -> contact.isPurchaseContact() && contact.isPurchaseContact_Default())
					.sorted(ordering)
					.findFirst();
		}
	}

	public Optional<I_AD_User> getByLocationIdOrDefault(final int bpartnerLocationId)
	{
		if (bpartnerLocationId > 0)
		{
			Optional<I_AD_User> contact = stream()
					.filter(contactData -> contactData.isActive())
					.filter(contactData -> contactData.getC_BPartner_Location_ID() == bpartnerLocationId)
					.findFirst();
			if (contact.isPresent())
			{
				return contact;
			}
		}

		return getDefaultIfExists();
	}

	public boolean anyMatch(final Predicate<I_AD_User> predicate)
	{
		return stream().anyMatch(predicate);
	}

	public boolean hasDefault()
	{
		return anyMatch(contact -> contact.isDefaultContact());
	}

	public void editById(final int contactId, final Consumer<I_AD_User> consumer)
	{
		final I_AD_User contactData = getById(contactId);
		consumer.accept(contactData);
	}

}
