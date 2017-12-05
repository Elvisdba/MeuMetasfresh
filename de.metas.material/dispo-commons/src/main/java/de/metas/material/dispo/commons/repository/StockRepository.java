package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Functions;
import org.adempiere.util.Functions.MemoizingFunction;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.StockResult.AddToResultGroupRequest;
import de.metas.material.dispo.commons.repository.StockResult.ResultGroup;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.NonNull;
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

@Service
public class StockRepository
{
	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final StockMultiQuery multiQuery)
	{
		return retrieveAvailableStock(multiQuery)
				.getResultGroups()
				.stream().map(ResultGroup::getQty).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public StockResult retrieveAvailableStock(@NonNull StockMultiQuery multiQuery)
	{
		final StockResult result = multiQuery.isAddToPredefinedBuckets()
				? StockResult.createEmptyWithPredefinedBuckets(multiQuery)
				: StockResult.createEmpty();

		final IQuery<I_MD_Candidate_Stock_v> dbQuery = createDBQueryForMaterialQueryOrNull(multiQuery);
		if (dbQuery == null)
		{
			return result;
		}

		final List<AddToResultGroupRequest> addRequests = dbQuery
				.stream()
				.map(stockRecord -> createAddToResultGroupRequest(stockRecord))
				.collect(ImmutableList.toImmutableList());

		if (multiQuery.isAddToPredefinedBuckets())
		{
			addRequests.forEach(result::addQtyToAllMatchingGroups);
		}
		else
		{
			addRequests.forEach(result::addGroup);
		}
		return result;
	}

	public StockResult retrieveAvailableStock(@NonNull StockQuery query)
	{
		return retrieveAvailableStock(StockMultiQuery.of(query));
	}

	private IQuery<I_MD_Candidate_Stock_v> createDBQueryForMaterialQueryOrNull(@NonNull final StockMultiQuery multiQuery)
	{
		final MemoizingFunction<Date, Timestamp> maxDateLessOrEqualFunction //
				= Functions.memoizing(date -> retrieveMaxDateLessOrEqual(date));

		return multiQuery.getQueries()
				.stream()
				.map(stockQuery -> {
					final Timestamp latestDateOrNull = maxDateLessOrEqualFunction.apply(stockQuery.getDate());
					if (latestDateOrNull == null)
					{
						return null;
					}
					return stockQuery.withDate(latestDateOrNull);
				})
				.filter(Predicates.notNull())
				.map(stockQuery -> StockRepositorySqlHelper.createDBQueryForStockQuery(stockQuery)
						.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
						.create())
				.reduce((previousDBQuery, dbQuery) -> {
					if (previousDBQuery == null)
					{
						return dbQuery;
					}
					else
					{
						previousDBQuery.addUnion(dbQuery, true);
						return previousDBQuery;
					}
				})
				.orElse(null);
	}

	private Timestamp retrieveMaxDateLessOrEqual(@NonNull final Date date)
	{
		return Services.get(IQueryBL.class)

				// select from MD_Candidate, because the performance is much worse with MD_Candidate_Stock_v
				// also note that this method is supported by the DB index md_candidate_stock_latest_date_perf
				.createQueryBuilder(I_MD_Candidate.class)

				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))

				.orderBy()
				.addColumn(I_MD_Candidate.COLUMN_DateProjected, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last)
				.endOrderBy()

				.create()
				.first(I_MD_Candidate.COLUMNNAME_DateProjected, Timestamp.class);
	}

	@VisibleForTesting
	static AddToResultGroupRequest createAddToResultGroupRequest(final I_MD_Candidate_Stock_v stockRecord)
	{
		final int bPpartnerIdForRequest = stockRecord.getC_BPartner_ID() > 0
				? stockRecord.getC_BPartner_ID()
				: StockQuery.BPARTNER_ID_NONE;

		return AddToResultGroupRequest.builder()
				.productId(stockRecord.getM_Product_ID())
				.bpartnerId(bPpartnerIdForRequest)
				.warehouseId(stockRecord.getM_Warehouse_ID())
				.storageAttributesKey(StorageAttributesKey.ofString(stockRecord.getStorageAttributesKey()))
				.qty(stockRecord.getQty())
				.build();
	}

	@Value
	private static class ProductAndAttributeKey
	{
		int productId;
		String attributeKey;
	}
}
