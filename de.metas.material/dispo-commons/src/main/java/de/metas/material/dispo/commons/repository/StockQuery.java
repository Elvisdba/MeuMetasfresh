package de.metas.material.dispo.commons.repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@Value
public class StockQuery
{
	public static StockQuery forMaterialDescriptor(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return StockQuery.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.date(materialDescriptor.getDate())
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.bpartnerId(materialDescriptor.getBPartnerId())
				.build();
	}

	private final ImmutableSet<Integer> warehouseIds;
	private final Date date;
	private final ImmutableList<Integer> productIds;
	private final ImmutableList<StorageAttributesKey> storageAttributesKeys;

	public static final int BPARTNER_ID_ANY = -1;
	public static final int BPARTNER_ID_NONE = -2;
	private final int bpartnerId;

	@Builder(toBuilder = true)
	private StockQuery(
			@Singular final Set<Integer> warehouseIds,
			final Date date,
			@Singular final List<Integer> productIds,
			@Singular final List<StorageAttributesKey> storageAttributesKeys,
			final int bpartnerId)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.warehouseIds = warehouseIds == null || warehouseIds.isEmpty() ? ImmutableSet.of() : ImmutableSet.copyOf(warehouseIds);
		this.date = date != null ? (Date)date.clone() : SystemTime.asDate();
		this.productIds = ImmutableList.copyOf(productIds);
		this.storageAttributesKeys = ImmutableList.copyOf(storageAttributesKeys);

		final boolean bPartnerIdIsSpecified = bpartnerId == BPARTNER_ID_ANY
				|| bpartnerId == BPARTNER_ID_NONE
				|| bpartnerId > 0;
		if (bPartnerIdIsSpecified)
		{
			this.bpartnerId = bpartnerId;
		}
		else // default, including 0; bpartnerId was not specified on build time
		{
			this.bpartnerId = BPARTNER_ID_ANY;
		}
	}

	public StockQuery withDate(@NonNull final Date newDate)
	{
		if (Objects.equals(this.date, newDate))
		{
			return this;
		}
		return toBuilder().date(newDate).build();
	}

	public Date getDate()
	{
		return (Date)date.clone();
	}

	public boolean isBPartnerMatching(final int bpartnerIdToMatch)
	{
		return isBPartnerMatching(bpartnerId, bpartnerIdToMatch);
	}

	public static boolean isBPartnerMatching(final int bpartnerId, final int bpartnerIdToMatch)
	{
		return bpartnerId == StockQuery.BPARTNER_ID_ANY
				|| (bpartnerId == StockQuery.BPARTNER_ID_NONE && bpartnerIdToMatch <= 0)
				|| (bpartnerId == bpartnerIdToMatch);
	}

}
