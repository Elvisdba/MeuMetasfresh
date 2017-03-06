package org.adempiere.ad.dao.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Query filter for validation rules
 *
 * @author al
 *
 * @param <T>
 */
public class ValidationRuleQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private static final Logger logger = LogManager.getLogger(ValidationRuleQueryFilter.class);

	private final Evaluatee context;
	private final int adValRuleId;
	private final String contextTableName;

	public ValidationRuleQueryFilter(final Evaluatee context, final String contextTableName, final int adValRuleId)
	{
		super();

		Check.assumeNotNull(context, "Parameter context is not null");
		Check.assumeNotEmpty(contextTableName, "contextTableName is not empty");
		Check.assume(adValRuleId > 0, "Given AD_Val_Rule exists");

		// this.model = model;
		this.context = context;
		this.contextTableName = contextTableName;
		this.adValRuleId = adValRuleId;
	}

	@Override
	public boolean accept(final T model)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getSql()
	{
		final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
		final IValidationContext evalCtx = validationRuleFactory.createValidationContext(context);
		final IValidationRule valRule = validationRuleFactory.create(contextTableName, adValRuleId);
		
		final IStringExpression prefilterWhereClauseExpr = valRule.getPrefilterWhereClause();
		final String prefilterWhereClause = prefilterWhereClauseExpr.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);
		if(prefilterWhereClauseExpr.isNoResult(prefilterWhereClause))
		{
			final String prefilterWhereClauseDefault = "1=0";
			logger.warn("Cannot evaluate {} using {}. Returing {}.", prefilterWhereClauseExpr, evalCtx, prefilterWhereClauseDefault);
			return prefilterWhereClauseDefault;
		}
		
		return prefilterWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return ImmutableList.of();
	}
}
