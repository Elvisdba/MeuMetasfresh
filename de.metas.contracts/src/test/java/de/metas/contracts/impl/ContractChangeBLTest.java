package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.impl.ContractsTestBase.FixedTimeSource;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import lombok.NonNull;

public class ContractChangeBLTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	final private IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static Timestamp cancelDate = TimeUtil.parseTimestamp("2017-12-10");
	final private static String terminationMemo = "note: cancelContract_test";
	final private static FixedTimeSource today = new FixedTimeSource(2017, 11, 10);
	final private static ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
			.changeDate(cancelDate)
			.isCloseInvoiceCandidate(true)
			.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
			.terminationMemo(terminationMemo)
			.build();

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
		SystemTime.setTimeSource(today); 
	}

	@Test
	public void cancelContract_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true, startDate);
		contractChangeBL.cancelContract(contract, contractChangeParameters);
		assertFlatrateTerm(contract, cancelDate);
		assertSubscriptionProgress(contract, 1);
	}

	@Test
	public void cancel_a_Contract_which_was_extended_using_a_date_from_initial_contract()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true, startDate);
		Services.get(IFlatrateBL.class).extendContract(contract, true, true, null, null);
		save(contract);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		contractChangeBL.cancelContract(contract, contractChangeParameters);

		assertFlatrateTerm(contract, cancelDate);
		assertSubscriptionProgress(contract, 1);
		assertThat(contract.getMasterEndDate()).isEqualTo(cancelDate);
		assertFlatrateTerm(extendedContract, cancelDate);
		assertSubscriptionProgress(extendedContract, 0);
	}

	@Test
	public void cancel_a_Contract_which_was_extended_using_a_date_from_extended_contract()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true, startDate);
		Services.get(IFlatrateBL.class).extendContract(contract, true, true, null, null);
		save(contract);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();

		contractChangeBL.cancelContract(extendedContract, changeParameters);

		InterfaceWrapperHelper.refresh(contract);
		InterfaceWrapperHelper.refresh(extendedContract);

		assertFlatrateTerm(contract, cancellingDate);
		assertSubscriptionProgress(contract, 12);
		assertThat(contract.getMasterEndDate()).isEqualTo(cancellingDate);
		assertFlatrateTerm(extendedContract, cancellingDate);
		assertSubscriptionProgress(extendedContract, 3);
		assertThat(contract.getMasterEndDate()).isEqualTo(extendedContract.getMasterEndDate());
	}
	
	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term flatrateTerm, final Timestamp cancelinglDate)
	{
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertThat(flatrateTerm.isAutoRenew()).isFalse();
		assertThat(flatrateTerm.getMasterStartDate()).isNotNull();
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(cancelinglDate);
		assertThat(flatrateTerm.getTerminationMemo()).isEqualTo(terminationMemo);
		assertThat(flatrateTerm.getTerminationReason()).isEqualTo(X_C_Flatrate_Term.TERMINATIONREASON_General);
	}

	private void assertSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm, final int expected)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);
		assertThat(subscriptionProgress).hasSize(expected);

		subscriptionProgress.stream()
				.filter(progress -> progress.getEventDate().before(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Quit))
				.filter(progress -> progress.getEventDate().after(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running));
	}
}
