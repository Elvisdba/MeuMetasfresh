package de.metas.bpartner;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.ILocationBL;

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
 * Business Partner Locations (Aggregate)
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPartnerLocations implements Iterable<I_C_BPartner_Location>
{
	private final int bpartnerId;
	private final List<I_C_BPartner_Location> bpartnerLocations;

	public BPartnerLocations(final int bpartnerId, final List<I_C_BPartner_Location> bpartnerLocations)
	{
		this.bpartnerId = bpartnerId;
		this.bpartnerLocations = bpartnerLocations;

		// TODO: mark all of them as readonly, prevent changing from outside
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("bpartnerId", bpartnerId)
				.addValue(bpartnerLocations)
				.toString();
	}

	public int size()
	{
		return bpartnerLocations.size();
	}

	public Stream<I_C_BPartner_Location> stream()
	{
		return bpartnerLocations.stream();
	}

	public List<I_C_BPartner_Location> toList()
	{
		return ImmutableList.copyOf(bpartnerLocations);
	}

	/** @return immutable iterator */
	@Override
	public Iterator<I_C_BPartner_Location> iterator()
	{
		return toList().iterator();
	}

	public I_C_BPartner_Location first()
	{
		return bpartnerLocations.get(0);
	}

	public boolean isEmpty()
	{
		return bpartnerLocations.isEmpty();
	}

	public Optional<I_C_BPartner_Location> getByIdIfExists(final int bpartnerLocationId)
	{
		if (bpartnerLocationId <= 0)
		{
			return Optional.empty();
		}
		return stream()
				.filter(bpl -> bpl.getC_BPartner_Location_ID() == bpartnerLocationId)
				.findFirst();
	}

	public I_C_BPartner_Location getById(final int bpartnerLocationId)
	{
		return getByIdIfExists(bpartnerLocationId)
				.orElseThrow(() -> new AdempiereException("No BPartner location found for " + bpartnerLocationId + " in " + this));
	}

	public List<I_C_BPartner_Location> getShipToLocations()
	{
		final Comparator<I_C_BPartner_Location> ordering = Comparator.<I_C_BPartner_Location, Integer> //
				comparing(bpl -> bpl.isShipToDefault() ? 0 : 1) // default ship to shall be first
				.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());

		return stream()
				.filter(bpl -> bpl.isShipTo())
				.sorted(ordering)
				.collect(ImmutableList.toImmutableList());
	}

	/** @return best matching ship to location, or first location or null */
	public I_C_BPartner_Location getShipToOrFirst()
	{
		return getShipToOrFirstIfExists().orElse(null);
	}
	
	public Optional<I_C_BPartner_Location> getShipToOrFirstIfExists()
	{
		final Comparator<I_C_BPartner_Location> ordering = Comparator.<I_C_BPartner_Location, Integer> comparing(bpl -> bpl.isShipToDefault() ? 0 : 1)
				.thenComparing(bpl -> bpl.isShipTo() ? 0 : 1)
				.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());
		return stream()
				.sorted(ordering)
				.findFirst();
	}

	/** @return default/first BillTo(if sales) or PayFrom(if purchase) location. */
	public I_C_BPartner_Location getBillToBySOTrx(final boolean isSOTrx)
	{
		final Comparator<I_C_BPartner_Location> ordering;
		if (isSOTrx)
		{
			ordering = Comparator.<I_C_BPartner_Location, Integer> comparing(bpl -> bpl.isBillToDefault() ? 0 : 1)
					.thenComparing(bpl -> bpl.isBillTo() ? 0 : 1)
					.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());
		}
		else
		{
			ordering = Comparator.<I_C_BPartner_Location, Integer> comparing(bpl -> bpl.isPayFrom() ? 0 : 1)
					.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());
		}
		return stream()
				.sorted(ordering)
				.findFirst()
				.orElse(null);
	}
	
	public I_C_BPartner_Location getBillToOrFirstOrNull()
	{
		final Comparator<I_C_BPartner_Location> ordering = Comparator.<I_C_BPartner_Location, Integer> comparing(bpl -> bpl.isBillToDefault() ? 0 : 1)
				.thenComparing(bpl -> bpl.isBillTo() ? 0 : 1)
				.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());
		return stream()
				.sorted(ordering)
				.findFirst()
				.orElse(null);
	}


	/**
	 * Gets default/first bill to location.
	 *
	 * @param alsoTryBilltoRelation if <code>true</code> and the given partner has no billTo location, then the method also checks if there is a billTo-<code>C_BP_Relation</code> and if so, returns
	 *            that relation's bPartner location.
	 * @return bill to location or null
	 */
	public I_C_BPartner_Location getBillTo(final boolean alsoTryBilltoRelation)
	{
		final I_C_BPartner_Location ownBillToLocation = stream()
				.filter(bpl -> bpl.isBillTo())
				.sorted(Comparator.comparing(bpl -> bpl.isBillToDefault() ? 0 : 1)) // default BillTo shall be first
				.findFirst()
				.orElse(null);

		if (!alsoTryBilltoRelation || ownBillToLocation != null)
		{
			// !alsoTryBilltoRelation => we return whatever we got here (null or not)
			// ownBillToLocation != null => we return the not-null location we found
			return ownBillToLocation;
		}

		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class); // TODO: avoid calling the repository from here
		final I_C_BP_Relation billToRelation = partnerDAO.retrieveBillToRelation(bpartnerId);
		if (billToRelation == null)
		{
			return null;
		}

		final int relatedBPartnerId = billToRelation.getC_BPartnerRelation_ID();
		final int relatedBPartnerLocationId = billToRelation.getC_BPartnerRelation_Location_ID();

		return partnerDAO.retrieveLocations(relatedBPartnerId)
				.getByIdIfExists(relatedBPartnerLocationId)
				.orElse(null);
	}

	public boolean anyMatch(final Predicate<I_C_BPartner_Location> predicate)
	{
		return stream().anyMatch(predicate);
	}

	public boolean hasShipToDefault()
	{
		return anyMatch(I_C_BPartner_Location::isShipToDefault);
	}

	public boolean hasBillToDefault()
	{
		return anyMatch(I_C_BPartner_Location::isBillToDefault);
	}

	public boolean hasHandOver()
	{
		return anyMatch(I_C_BPartner_Location::isHandOverLocation);
	}

	public void editById(final int bpartnerLocationId, final Consumer<I_C_BPartner_Location> consumer)
	{
		final I_C_BPartner_Location bpLocationData = getById(bpartnerLocationId);
		consumer.accept(bpLocationData);
	}

	public Optional<I_C_Location> getAddressByIdIfExists(final int bpartnerLocationId)
	{
		return getByIdIfExists(bpartnerLocationId)
				.map(bpLocationData -> bpLocationData.getC_Location())
				.map(address -> Services.get(ILocationBL.class).duplicate(address)); // NOTE: C_Location shall be handled as a value object!
	}
}
