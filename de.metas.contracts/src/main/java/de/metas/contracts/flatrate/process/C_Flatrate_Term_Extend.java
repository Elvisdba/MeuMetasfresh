package de.metas.contracts.flatrate.process;

import java.sql.Timestamp;
import java.util.Arrays;

/*
 * #%L
 * de.metas.contracts
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

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.Query;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

public class C_Flatrate_Term_Extend
		extends JavaProcess
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);
	final private IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Param(parameterName = I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm, mandatory = false)
	private String p_forceComplete;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate, mandatory = false)
	private Timestamp p_startDate;

	@Override
	protected String doIt() throws Exception
	{
		final Boolean forceComplete = StringUtils.toBooleanOrNull(p_forceComplete);

		if (I_C_Flatrate_Term.Table_Name.equals(getTableName()))
		{
			final I_C_Flatrate_Term termToExtend = getRecord(I_C_Flatrate_Term.class);

			// we are called from a given term => extend the term
			flatrateBL.extendContract(termToExtend,
					true,   // forceExtend
					forceComplete,
					p_startDate,
					null); // ol
			termToExtend.setAD_PInstance_EndOfTerm_ID(getAD_PInstance_ID());

			addLog("@Processed@: @C_Flatrate_Term_ID@ " + termToExtend.getC_Flatrate_Term_ID());
			InterfaceWrapperHelper.save(termToExtend);

			getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(termToExtend));
		}
		else
		{
			final Iterator<I_C_Flatrate_Term> termsToExtend = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
					.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_AD_PInstance_EndOfTerm_ID, 0, null)
					.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
					.addCompareFilter(I_C_Flatrate_Term.COLUMN_NoticeDate, Operator.LESS, SystemTime.asTimestamp())
					.addNotInArrayFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, Arrays.asList(X_C_Flatrate_Term.CONTRACTSTATUS_Quit, X_C_Flatrate_Term.CONTRACTSTATUS_Voided))
					.orderBy().addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID).endOrderBy()
					.create()
					.setClient_ID()
					.setOnlyActiveRecords(true)
					.setOption(Query.OPTION_GuaranteedIteratorRequired, true) // guaranteed = true, because the term extension changes AD_PInstance_EndOfTerm_ID
					.iterate(I_C_Flatrate_Term.class);

			int counter = 0;
			while (termsToExtend.hasNext())
			{
				final I_C_Flatrate_Term termToExtend = termsToExtend.next();
				flatrateBL.extendContract(termToExtend,
						false,   // forceExtend
						forceComplete,   //
						p_startDate,
						null); // ol
				termToExtend.setAD_PInstance_EndOfTerm_ID(getAD_PInstance_ID());
				if (termToExtend.getC_FlatrateTerm_Next_ID() > 0)
				{
					counter++;
				}
				InterfaceWrapperHelper.save(termToExtend);
			}
			addLog("Extended " + counter + " terms");
		}
		return "@Success@";
	}
}
