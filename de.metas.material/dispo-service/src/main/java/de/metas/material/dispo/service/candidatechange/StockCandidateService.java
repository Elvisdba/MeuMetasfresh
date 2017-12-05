package de.metas.material.dispo.service.candidatechange;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidatesQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
public class StockCandidateService
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	public StockCandidateService(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands)
	{
		this.candidateRepositoryRetrieval = candidateRepository;
		this.candidateRepositoryWriteService = candidateRepositoryCommands;
	}

	/**
	 * Creates and returns <b>but does not store</b> a new stock candidate
	 * whose quantity is the quantity of the given {@code candidate} plus the quantity from
	 * the next-younger (not same-age!) stock candidate that has the same product, storage attributes key and warehouse.
	 *
	 * If there is no such next-younger stock candidate (i.e. if this is the very first stock candidate to be created for the given product and locator), then a quantity of zero is taken.
	 *
	 * @param candidate
	 * @return a candidate with
	 *         <ul>
	 *         <li>type = {@link CandidateType#STOCK}</li>
	 *         <li>qty = qty of the given {@code candidate} plus the next younger candidate's quantity
	 *         <li>groupId of the next younger-candidate (or null if there is none)
	 *         </ul>
	 */
	public Candidate createStockCandidate(@NonNull final Candidate candidate)
	{
		final Candidate previousStockOrNull;
		{
			final CandidatesQuery stockQuery = createStockQueryBuilderWithDateOperator(candidate, DateOperator.BEFORE_OR_AT);
			previousStockOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(stockQuery);
		}
		// TODO i know from the unit tests this kindof works, but i need to better understand it
		final BigDecimal newQty;
		if (previousStockOrNull == null)
		{
			newQty = candidate.getQuantity();
		}
		else if (previousStockOrNull.getDate().before(candidate.getDate()))
		{
			final CandidatesQuery stockQuery = createStockQueryBuilderWithDateOperator(previousStockOrNull, DateOperator.AT);
			final BigDecimal previousQuantity = candidateRepositoryRetrieval
					.retrieveOrderedByDateAndSeqNo(stockQuery).stream().map(Candidate::getQuantity)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			newQty = previousQuantity.add(candidate.getQuantity());
		}
		else
		{
			// previousStockOrNull has the same date as the given "candidate"
			newQty = candidate.getQuantity();
		}

		final MaterialDescriptor materialDescriptor = candidate
				.getMaterialDescriptor()
				.withQuantity(newQty);

		final Integer groupId = previousStockOrNull != null
				? previousStockOrNull.getGroupId()
				: 0;

		return Candidate.builder()
				.type(CandidateType.STOCK)
				.orgId(candidate.getOrgId())
				.clientId(candidate.getClientId())
				.materialDescriptor(materialDescriptor)
				.parentId(candidate.getParentId())
				.seqNo(candidate.getSeqNo())
				.groupId(groupId)
				.build();
	}

	private CandidatesQuery createStockQueryBuilderWithDateOperator(
			@NonNull final Candidate candidate,
			@NonNull final DateOperator dateOperator)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery
				.forDescriptor(candidate.getMaterialDescriptor(), dateOperator);

		return CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidatesQuery.UNSPECIFIED_PARENT_ID)
				.build();
	}

	/**
	 * Updates the qty of the given candidate.
	 * Differs from {@link #addOrUpdateOverwriteStoredSeqNo(Candidate)} in that
	 * only the ID of the given {@code candidateToUpdate} is used, and if there is no existing persisted record, then an exception is thrown.
	 * Also it just updates the underlying persisted record of the given {@code candidateToUpdate} and nothing else.
	 *
	 *
	 * @param candidateToUpdate the candidate to update. Needs to have {@link Candidate#getId()} > 0.
	 *
	 * @return a copy of the given {@code candidateToUpdate} with the quantity being a delta, similar to the return value of {@link #addOrUpdate(Candidate, boolean)}.
	 */
	public Candidate updateQty(@NonNull final Candidate candidateToUpdate)
	{
		Preconditions.checkState(candidateToUpdate.getId() > 0,
				"Parameter 'candidateToUpdate' needs to have Id > 0; candidateToUpdate=%s",
				candidateToUpdate);

		final I_MD_Candidate candidateRecord = load(candidateToUpdate.getId(), I_MD_Candidate.class);
		final BigDecimal oldQty = candidateRecord.getQty();

		candidateRecord.setQty(candidateToUpdate.getQuantity());
		save(candidateRecord);

		final BigDecimal qtyDelta = candidateToUpdate.getQuantity().subtract(oldQty);

		return candidateToUpdate.withQuantity(qtyDelta);
	}

	/**
	 * Selects all stock candidates which have the same product and locator but a later timestamp than the one from the given {@code segment}.
	 * Iterate them and add the given {@code delta} to their quantity.
	 * <p>
	 * That's it for now :-). Don't alter those stock candidates' children or parents.
	 *
	 * @param productDescriptor the product to match against
	 * @param warehouseId the warehouse ID to match against
	 * @param date the date to match against (i.e. everything after this date shall be a match)
	 * @param groupId the groupId to set to every stock record that we matched
	 * @param delta the quantity (positive or negative) to add to every stock record that we matched
	 */
	public void applyDeltaToMatchingLaterStockCandidates(
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final Integer groupId,
			@NonNull final BigDecimal delta)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery
				.forDescriptor(materialDescriptor, DateOperator.AFTER);

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptorQuery(materialDescriptorQuery)
				.parentId(CandidatesQuery.UNSPECIFIED_PARENT_ID)
				.matchExactStorageAttributesKey(true)
				.build();

		final List<Candidate> candidatesToUpdate = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		for (final Candidate candidate : candidatesToUpdate)
		{
			final BigDecimal newQty = candidate.getQuantity().add(delta);
			candidateRepositoryWriteService.updateCandidate(candidate
					.withQuantity(newQty)
					.withGroupId(groupId));
		}
	}
}
