package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.StorageAttributesKey;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
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

@Data
public class StockResult
{
	public static StockResult createEmpty()
	{
		return new StockResult(new ArrayList<>());
	}

	@NonNull
	public static StockResult createEmptyWithPredefinedBuckets(@NonNull final StockMultiQuery multiQuery)
	{
		final ImmutableList.Builder<ResultGroup> resultBuilder = ImmutableList.builder();

		for (final StockQuery query : multiQuery.getQueries())
		{
			List<StorageAttributesKey> storageAttributesKeys = query.getStorageAttributesKeys();
			if (storageAttributesKeys.isEmpty())
			{
				storageAttributesKeys = ImmutableList.of(StorageAttributesKey.NONE); // TODO: tobi: StorageAttributesKey.ANY
			}

			Set<Integer> warehouseIds = query.getWarehouseIds();
			if (warehouseIds.isEmpty())
			{
				warehouseIds = ImmutableSet.of(ResultGroup.WAREHOUSE_ID_ANY);
			}

			final int bpartnerId = query.getBpartnerId();
			final List<Integer> productIds = query.getProductIds();

			for (final int warehouseId : warehouseIds)
			{
				for (final int productId : productIds)
				{
					for (final StorageAttributesKey storageAttributesKey : storageAttributesKeys)
					{
						resultBuilder.add(ResultGroup.builder()
								.productId(productId)
								.storageAttributesKey(storageAttributesKey)
								.warehouseId(warehouseId)
								.bpartnerId(bpartnerId)
								.build());
					}
				}
			}
		}

		return new StockResult(resultBuilder.build());
	}

	private final List<ResultGroup> resultGroups;

	@ToString
	@EqualsAndHashCode
	@Getter
	public static final class ResultGroup
	{
		private static final int WAREHOUSE_ID_ANY = -1;

		private final int warehouseId;
		private final int productId;
		private final StorageAttributesKey storageAttributesKey;
		private final int bpartnerId;
		private BigDecimal qty;

		@Builder
		public ResultGroup(
				final int warehouseId,
				final int productId,
				@NonNull final StorageAttributesKey storageAttributesKey,
				final int bpartnerId,
				@Nullable final BigDecimal qty)
		{
			Check.assume(productId > 0, "productId > 0");

			this.warehouseId = warehouseId > 0 ? warehouseId : WAREHOUSE_ID_ANY;
			this.productId = productId;
			this.storageAttributesKey = storageAttributesKey;
			this.qty = qty == null ? BigDecimal.ZERO : qty;

			if (bpartnerId == StockQuery.BPARTNER_ID_ANY
					|| bpartnerId == StockQuery.BPARTNER_ID_NONE
					|| bpartnerId > 0)
			{
				this.bpartnerId = bpartnerId;
			}
			else
			{
				throw new AdempiereException("Invalid bpartnerId: " + bpartnerId);
			}
		}

		@VisibleForTesting
		boolean matches(final AddToResultGroupRequest request)
		{
			if (productId != request.getProductId())
			{
				return false;
			}

			if (!isWarehouseMatching(request.getWarehouseId()))
			{
				return false;
			}

			if (!isBPartnerMatching(request.getBpartnerId()))
			{
				// OK
			}

			final StorageAttributesKey storageAttributesKeyToMatch = request.getStorageAttributesKey();
			if (!storageAttributesKeyToMatch.contains(storageAttributesKey))
			{
				return false;
			}

			return true;
		}

		private void addQty(final BigDecimal qtyToAdd)
		{
			qty = qty.add(qtyToAdd);
		}

		private boolean isWarehouseMatching(final int warehouseIdToMatch)
		{
			return this.warehouseId == WAREHOUSE_ID_ANY
					|| this.warehouseId == warehouseIdToMatch;
		}

		private boolean isBPartnerMatching(final int bpartnerIdToMatch)
		{
			return StockQuery.isBPartnerMatching(bpartnerId, bpartnerIdToMatch);
		}
	}

	@Value
	public static final class AddToResultGroupRequest
	{
		private final int warehouseId;
		private final int productId;
		private final StorageAttributesKey storageAttributesKey;
		private final int bpartnerId;
		private BigDecimal qty;

		@Builder
		public AddToResultGroupRequest(
				final int warehouseId,
				final int productId,
				@NonNull final StorageAttributesKey storageAttributesKey,
				final int bpartnerId,
				@NonNull final BigDecimal qty)
		{
			Check.assume(productId > 0, "productId > 0");
			Check.assume(warehouseId > 0, "warehouseId > 0");

			this.warehouseId = warehouseId;
			this.productId = productId;
			this.storageAttributesKey = storageAttributesKey;
			if (bpartnerId > 0 || bpartnerId == StockQuery.BPARTNER_ID_NONE)
			{
				this.bpartnerId = bpartnerId;
			}
			else
			{
				// i.e. BPARTNER_ID_ANY shall not be accepted,
				// because this is actual data and not grouping/aggregation data
				throw new AdempiereException("Invalid bpartnerId: " + bpartnerId);
			}
			this.qty = qty;
		}
	}

	public void addQtyToAllMatchingGroups(@NonNull final AddToResultGroupRequest request)
	{
		boolean added = false;
		for (final ResultGroup group : resultGroups)
		{
			final boolean matchers = group.matches(request);
			if (matchers)
			{
				group.addQty(request.getQty());
				added = true;
			}
		}

		if (!added)
		{
			throw new AdempiereException("No matching group found for " + request + " in " + this);
		}
	}

	public void addGroup(@NonNull final AddToResultGroupRequest request)
	{
		final ResultGroup group = ResultGroup.builder()
				.productId(request.getProductId())
				.storageAttributesKey(request.getStorageAttributesKey())
				.warehouseId(request.getWarehouseId())
				.bpartnerId(request.getBpartnerId())
				.qty(request.getQty())
				.build();

		resultGroups.add(group);
	}
}
