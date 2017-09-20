package org.adempiere.model;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.POZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class RelationTypeZoomProvider implements IZoomProvider
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(RelationTypeZoomProvider.class);

	private final boolean directed;
	private final String zoomInfoId;
	private final String internalName;
	private final int adRelationTypeId;
	private final boolean isReferenceTarget;

	private final ZoomProviderDestination source;
	private final ZoomProviderDestination target;

	private final ReferenceTargetZoomProviderDestination referenceTarget;

	private RelationTypeZoomProvider(final Builder builder)
	{
		super();

		directed = builder.isDirected();
		zoomInfoId = builder.getZoomInfoId();
		internalName = builder.getInternalName();
		adRelationTypeId = builder.getAD_RelationType_ID();

		isReferenceTarget = builder.isReferenceTarget();

		if (isReferenceTarget)
		{
			target = null;
			source = null;

			referenceTarget = new ReferenceTargetZoomProviderDestination(
					builder.getTarget_Reference_ID(),
					builder.getTargetTableRefInfoOrNull(),
					builder.getTargetRoleDisplayName());

		}

		else

		{
			source = new ZoomProviderDestination(
					builder.getSource_Reference_ID(),
					builder.getSourceTableRefInfoOrNull(),
					builder.getSourceRoleDisplayName());

			target = new ZoomProviderDestination(
					builder.getTarget_Reference_ID(),
					builder.getTargetTableRefInfoOrNull(),
					builder.getTargetRoleDisplayName());

			referenceTarget = null;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("zoomInfoId", zoomInfoId)
				.add("internalName", internalName)
				.add("directed", directed)
				.add("source", source)
				.add("target", target)
				.add("isReferenceTarget", isReferenceTarget)
				.toString();
	}

	@Override
	public List<ZoomInfo> retrieveZoomInfos(final IZoomSource zoomSource, final int targetAD_Window_ID, final boolean checkRecordsCount)
	{
		final int adWindowId;
		final ITranslatableString display;

		// #2340 Reference Target relation type: There is no source, only a target that contains the table and
		// the reference target column to be linked with the zoomSource
		if (isReferenceTarget)
		{
			final ReferenceTargetZoomProviderDestination referenceTarget = getReferenceTarget();

			adWindowId = referenceTarget.getAD_Window_ID(zoomSource.isSOTrx());

			display = referenceTarget.getRoleDisplayName(adWindowId);
		}

		else
		{

			final IPair<ZoomProviderDestination, ZoomProviderDestination> sourceAndTarget = findSourceAndTargetEffective(zoomSource);

			final ZoomProviderDestination source = sourceAndTarget.getLeft();
			final ZoomProviderDestination target = sourceAndTarget.getRight();

			if (!source.matchesAsSource(zoomSource))
			{
				logger.trace("Skip {} because {} is not matching source={}", this, zoomSource, source);
				return ImmutableList.of();
			}

			adWindowId = target.getAD_Window_ID(zoomSource.isSOTrx());

			display = target.getRoleDisplayName(adWindowId);
		}

		if (targetAD_Window_ID > 0 && targetAD_Window_ID != adWindowId)
		{
			return ImmutableList.of();
		}

		final MQuery query = mkQuery(zoomSource, isReferenceTarget);
		
		if (checkRecordsCount)
		{
			updateRecordsCountAndZoomValue(query);
		}

		final String zoomInfoId = getZoomInfoId();
		return ImmutableList.of(ZoomInfo.of(zoomInfoId, adWindowId, query, display));
	}

	public boolean isDirected()
	{
		return directed;
	}

	private String getZoomInfoId()
	{
		return zoomInfoId;
	}

	String getInternalName()
	{
		return internalName;
	}

	public int getAD_RelationType_ID()
	{
		return adRelationTypeId;
	}

	private ZoomProviderDestination getTarget()
	{
		return target;
	}

	private ZoomProviderDestination getSource()
	{
		return source;
	}

	private ReferenceTargetZoomProviderDestination getReferenceTarget()
	{
		return referenceTarget;
	}

	public String getTargetTableName()
	{
		return target.getTableName();
	}

	public String getSourceTableName()
	{
		return source.getTableName();
	}

	/**
	 * @return effective source and target pair
	 */
	private IPair<ZoomProviderDestination, ZoomProviderDestination> findSourceAndTargetEffective(final IZoomSource zoomSource)
	{
		final ZoomProviderDestination target = getTarget();
		final ZoomProviderDestination source = getSource();

		if (isDirected())
		{
			// the type is directed, so our destination is always the *target* reference
			return ImmutablePair.of(source, target);
		}
		else if (source.getTableName().equals(target.getTableName()))
		{
			// this relation type is from one table to the same table
			// use the window-id to distinguish
			final boolean isSOTrx = zoomSource.isSOTrx();
			if (zoomSource.getAD_Window_ID() == source.getAD_Window_ID(isSOTrx))
			{
				return ImmutablePair.of(source, target);
			}
			else
			{
				return ImmutablePair.of(target, source);
			}
		}
		else
		{
			if (zoomSource.getTableName().equals(source.getTableName()))
			{
				return ImmutablePair.of(source, target);
			}
			else
			{
				return ImmutablePair.of(target, source);
			}
		}

	}

	/**
	 * @return a query which will find all zoomSource references in given target
	 */
	private MQuery mkQuery(final IZoomSource zoomSource, boolean isReferenceTarget)
	{
		final StringBuilder queryWhereClause = new StringBuilder();

		final ITableRefInfo refTable = isReferenceTarget ? getReferenceTarget().getTableRefInfo() : getTarget().getTableRefInfo();

		final String tableName = refTable.getTableName();
		final String columnName = refTable.getDisplayColumn();

		if (isReferenceTarget)
		{

			final I_AD_Column columnReference = InterfaceWrapperHelper.create(zoomSource.getCtx(), refTable.getReferenceTargetColumnID(), I_AD_Column.class, zoomSource.getTrxName());

			Check.assumeNotNull(columnReference, "The reference table {} does not have a Column ReferenceTarget defined", refTable);

			queryWhereClause
					.append(zoomSource.getAD_Table_ID())
					.append(" = ")
					.append(tableName)
					.append(".")
					.append("AD_Table_ID")
					.append(" AND ")
					.append(zoomSource.getRecord_ID())
					.append(" = ")
					.append(tableName)
					.append(".")
					.append(columnReference.getColumnName());

		}
		else
		{

			final String refTableWhereClause = refTable.getWhereClause();

			if (!Check.isEmpty(refTableWhereClause))
			{
				queryWhereClause.append(parseWhereClause(zoomSource, refTableWhereClause, true));
			}
			else
			{
				throw new AdempiereException("RefTable " + refTable + " has no whereClause, so RelationType " + this + " needs to be explicit");
			}
		}

		final MQuery query = new MQuery();
		query.addRestriction(queryWhereClause.toString());
		query.setZoomTableName(tableName);
		query.setZoomColumnName(columnName);

		return query;
	}

	/**
	 * Parses given <code>where</code>
	 *
	 * @param source zoom source
	 * @param where where clause to be parsed
	 * @param throwEx true if an exception shall be thrown in case the parsing failed.
	 * @return parsed where clause or empty string in case parsing failed and throwEx is <code>false</code>
	 */
	private static String parseWhereClause(final IZoomSource source, final String where, final boolean throwEx)
	{
		final IStringExpression whereExpr = IStringExpression.compileOrDefault(where, IStringExpression.NULL);
		if (whereExpr.isNullExpression())
		{
			return "";
		}

		final Evaluatee evalCtx = source.createEvaluationContext();
		final OnVariableNotFound onVariableNotFound = throwEx ? OnVariableNotFound.Fail : OnVariableNotFound.ReturnNoResult;
		final String whereParsed = whereExpr.evaluate(evalCtx, onVariableNotFound);
		if (whereExpr.isNoResult(whereParsed))
		{
			// NOTE: logging as debug instead of warning because this might be a standard use case,
			// i.e. we are checking if a given where clause has some results, so the method was called with throwEx=false
			// and if we reached this point it means one of the context variables were not present and it has no default value.
			// This is perfectly normal, a default value is really not needed because we don't want to execute an SQL command
			// which would return no result. It's much more efficient to stop here.
			logger.debug("Could not parse where clause:\n{} \n EvalCtx: {} \n ZoomSource: {}", where, evalCtx, source);
			return "";
		}

		return whereParsed;
	}

	private static void updateRecordsCountAndZoomValue(final MQuery query)
	{
		final String sqlCommon = " FROM " + query.getZoomTableName() + " WHERE " + query.getWhereClause(false);

		final String sqlCount = "SELECT COUNT(*) " + sqlCommon;
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlCount);
		query.setRecordCount(count);

		if (count > 0)
		{
			final String sqlFirstKey = "SELECT " + query.getZoomColumnName() + sqlCommon;

			final int firstKey = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlFirstKey);
			query.setZoomValue(firstKey);
		}
	}

	/**
	 * 
	 * Retrieve destinations for the source given as parameter.
	 * NOTE: This is not suitable for REferenceTarget relation types, only for the normal kind!
	 * 
	 * @param ctx
	 * @param sourcePO
	 * @param clazz
	 * @param trxName
	 * @return
	 */
	public <T> List<T> retrieveDestinations(final Properties ctx, final PO sourcePO, final Class<T> clazz, final String trxName)
	{
		final IZoomSource zoomSource = POZoomSource.of(sourcePO, -1);

		// Ignore reference target possibility, which is not suitable here. The destination must have a source.
		final MQuery query = mkQuery(zoomSource, false);

		return new Query(ctx, query.getZoomTableName(), query.getWhereClause(false), trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(query.getZoomColumnName())
				.list(clazz);
	}

	private static final class ReferenceTargetZoomProviderDestination
	{

		private final int AD_Reference_ID;
		private final ITableRefInfo tableRefInfo;
		private final ITranslatableString roleDisplayName;

		private ReferenceTargetZoomProviderDestination(final int AD_Reference_ID, @NonNull final ITableRefInfo tableRefInfo, @Nullable final ITranslatableString roleDisplayName)
		{
			super();
			Preconditions.checkArgument(AD_Reference_ID > 0, "AD_Reference_ID > 0");
			this.AD_Reference_ID = AD_Reference_ID;
			this.tableRefInfo = tableRefInfo;
			this.roleDisplayName = roleDisplayName;

		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Reference_ID", AD_Reference_ID)
					.add("roleDisplayName", roleDisplayName)
					.add("tableRefInfo", tableRefInfo)
					.toString();

		}

		public ITableRefInfo getTableRefInfo()
		{
			return tableRefInfo;
		}

		public ITranslatableString getRoleDisplayName(final int fallbackAD_Window_ID)
		{
			if (roleDisplayName != null)
			{
				return roleDisplayName;
			}

			// Fallback to window name
			final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(fallbackAD_Window_ID);
			Check.errorIf(windowName == null, "Found no display string for, destination={}, AD_Window_ID={}", this, fallbackAD_Window_ID);
			return windowName;
		}

		/**
		 * @return the <code>AD_Window_ID</code>
		 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
		 */
		public int getAD_Window_ID(final boolean isSOTrx)
		{
			int windowId = tableRefInfo.getZoomAD_Window_ID_Override();
			if (windowId > 0)
			{
				return windowId;
			}

			if (isSOTrx)
			{
				windowId = tableRefInfo.getZoomSO_Window_ID();
			}
			else
			{
				windowId = tableRefInfo.getZoomPO_Window_ID();
			}
			if (windowId <= 0)
			{
				throw PORelationException.throwMissingWindowId(tableRefInfo.getName(), tableRefInfo.getTableName(), isSOTrx);
			}

			return windowId;
		}

	}

	private static final class ZoomProviderDestination
	{
		private final int AD_Reference_ID;
		private final ITableRefInfo tableRefInfo;
		private final ITranslatableString roleDisplayName;

		private ZoomProviderDestination(final int AD_Reference_ID, @NonNull final ITableRefInfo tableRefInfo, @Nullable final ITranslatableString roleDisplayName)
		{
			super();
			Preconditions.checkArgument(AD_Reference_ID > 0, "AD_Reference_ID > 0");
			this.AD_Reference_ID = AD_Reference_ID;
			this.tableRefInfo = tableRefInfo;
			this.roleDisplayName = roleDisplayName;

		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Reference_ID", AD_Reference_ID)
					.add("roleDisplayName", roleDisplayName)
					.add("tableRefInfo", tableRefInfo)
					.toString();
		}

		public String getTableName()
		{
			return tableRefInfo.getTableName();
		}

		public ITableRefInfo getTableRefInfo()
		{
			return tableRefInfo;
		}

		public ITranslatableString getRoleDisplayName(final int fallbackAD_Window_ID)
		{
			if (roleDisplayName != null)
			{
				return roleDisplayName;
			}

			// Fallback to window name
			final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(fallbackAD_Window_ID);
			Check.errorIf(windowName == null, "Found no display string for, destination={}, AD_Window_ID={}", this, fallbackAD_Window_ID);
			return windowName;
		}

		/**
		 * @return the <code>AD_Window_ID</code>
		 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
		 */
		public int getAD_Window_ID(final boolean isSOTrx)
		{
			int windowId = tableRefInfo.getZoomAD_Window_ID_Override();
			if (windowId > 0)
			{
				return windowId;
			}

			if (isSOTrx)
			{
				windowId = tableRefInfo.getZoomSO_Window_ID();
			}
			else
			{
				windowId = tableRefInfo.getZoomPO_Window_ID();
			}
			if (windowId <= 0)
			{
				throw PORelationException.throwMissingWindowId(tableRefInfo.getName(), tableRefInfo.getTableName(), isSOTrx);
			}

			return windowId;
		}

		public boolean matchesAsSource(final IZoomSource zoomSource)
		{
			// if (isReferenceTarget())
			// {
			// // the source always matches if the target is ReferenceTarget
			// return true;
			// }

			final String whereClause = tableRefInfo.getWhereClause();
			if (Check.isEmpty(whereClause, true))
			{
				logger.debug("whereClause is empty. Returning true (matching)");
				return true;
			}

			final String parsedWhere = parseWhereClause(zoomSource, whereClause, false);
			if (Check.isEmpty(parsedWhere))
			{
				return false;
			}

			final String keyColumnName = zoomSource.getKeyColumnName();
			Check.assumeNotEmpty(keyColumnName, "keyColumn is not empty for {}", zoomSource);

			final StringBuilder whereClauseEffective = new StringBuilder();
			whereClauseEffective.append(parsedWhere);
			whereClauseEffective.append(" AND ( ").append(keyColumnName).append("=").append(zoomSource.getRecord_ID()).append(" )");

			final boolean match = new Query(zoomSource.getCtx(), zoomSource.getTableName(), whereClauseEffective.toString(), zoomSource.getTrxName())
					.match();

			logger.debug("whereClause='{}' matches source='{}': {}", parsedWhere, zoomSource, match);
			return match;
		}
	}

	@ToString(exclude = "lookupDAO")
	public static final class Builder
	{
		private final transient ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		private Boolean directed;
		private String internalName;
		private int adRelationTypeId;
		private boolean isReferenceTarget;

		private int sourceReferenceId = -1;
		private ITranslatableString sourceRoleDisplayName;
		private ITableRefInfo sourceTableRefInfo = null; // lazy

		private int targetReferenceId = -1;
		private ITranslatableString targetRoleDisplayName;
		private ITableRefInfo targetTableRefInfo = null; // lazy

		private Builder()
		{
			super();
		}

		public RelationTypeZoomProvider buildOrNull()
		{
			if (!isReferenceTarget() && getSourceTableRefInfoOrNull() == null)
			{

				logger.info("Skip building {} because source tableRefInfo is null", this);
				return null;

			}
			if (getTargetTableRefInfoOrNull() == null)
			{
				logger.info("Skip building {} because target tableRefInfo is null", this);
				return null;
			}

			return new RelationTypeZoomProvider(this);
		}

		public Builder setAD_RelationType_ID(final int adRelationTypeId)
		{
			this.adRelationTypeId = adRelationTypeId;
			return this;
		}

		private int getAD_RelationType_ID()
		{
			Check.assume(adRelationTypeId > 0, "adRelationTypeId > 0");
			return adRelationTypeId;
		}

		private String getZoomInfoId()
		{
			return "relationType-" + getAD_RelationType_ID();
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		private String getInternalName()
		{
			if (Check.isEmpty(internalName, true))
			{
				return getZoomInfoId();
			}
			return internalName;
		}

		public Builder setDirected(final boolean directed)
		{
			this.directed = directed;
			return this;
		}

		private boolean isDirected()
		{
			Check.assumeNotNull(directed, "Parameter directed is not null");
			return directed;
		}

		public Builder setSource_Reference_AD(final int sourceReferenceId)
		{
			this.sourceReferenceId = sourceReferenceId;
			sourceTableRefInfo = null; // reset
			return this;
		}

		private int getSource_Reference_ID()
		{
			Check.assume(sourceReferenceId > 0, "sourceReferenceId > 0");
			return sourceReferenceId;
		}

		private ITableRefInfo getSourceTableRefInfoOrNull()
		{
			if (sourceTableRefInfo == null)
			{
				sourceTableRefInfo = lookupDAO.retrieveTableRefInfo(getSource_Reference_ID());
			}
			return sourceTableRefInfo;
		}

		public Builder setSourceRoleDisplayName(final ITranslatableString sourceRoleDisplayName)
		{
			this.sourceRoleDisplayName = sourceRoleDisplayName;
			return this;
		}

		private ITranslatableString getSourceRoleDisplayName()
		{
			return sourceRoleDisplayName;
		}

		public Builder setTarget_Reference_AD(final int targetReferenceId)
		{
			this.targetReferenceId = targetReferenceId;
			targetTableRefInfo = null; // lazy
			return this;
		}

		private int getTarget_Reference_ID()
		{
			Check.assume(targetReferenceId > 0, "targetReferenceId > 0");
			return targetReferenceId;
		}

		private ITableRefInfo getTargetTableRefInfoOrNull()
		{
			if (targetTableRefInfo == null)
			{
				targetTableRefInfo = lookupDAO.retrieveTableRefInfo(getTarget_Reference_ID());
			}
			return targetTableRefInfo;
		}

		public Builder setTargetRoleDisplayName(final ITranslatableString targetRoleDisplayName)
		{
			this.targetRoleDisplayName = targetRoleDisplayName;
			return this;
		}

		public ITranslatableString getTargetRoleDisplayName()
		{
			return targetRoleDisplayName;
		}

		public Builder setIsReferenceTarget(boolean isReferenceTarget)
		{
			this.isReferenceTarget = isReferenceTarget;
			return this;
		}

		private boolean isReferenceTarget()
		{
			return isReferenceTarget;
		}
	}

}
