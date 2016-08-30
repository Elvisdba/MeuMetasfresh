package de.metas.edi.process;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.GridTab;
import org.compiere.process.SvrProcess;

import de.metas.edi.api.IOrdrspDAO;
import de.metas.esb.edi.model.I_EDI_OrdrspLine;
import de.metas.esb.edi.model.X_EDI_OrdrspLine;

/*
 * #%L
 * de.metas.edi
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

public class EDI_OrdrspLine_CreateManualLine
		extends SvrProcess
		implements ISvrProcessPrecondition
{
	private final IOrdrspDAO ordrspDAO = Services.get(IOrdrspDAO.class);

	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final I_EDI_OrdrspLine ordrspLine = getRecord(I_EDI_OrdrspLine.class);
		final I_EDI_OrdrspLine newOrdrspLine = InterfaceWrapperHelper.newInstance(I_EDI_OrdrspLine.class, this);

		final IPair<String, BigDecimal> pair = getNextAvailablequalifier(ordrspLine);
		if (Check.isEmpty(pair.getLeft()))
		{
			return "@Error@";
		}

		InterfaceWrapperHelper.copyValues(ordrspLine, newOrdrspLine, true);

		newOrdrspLine.setQuantityQualifier(pair.getLeft());
		newOrdrspLine.setConfirmedQty(pair.getRight());
		InterfaceWrapperHelper.save(newOrdrspLine);

		return "OK";
	}

	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		final I_EDI_OrdrspLine ordrspLine = InterfaceWrapperHelper.create(gridTab, I_EDI_OrdrspLine.class);

		final IPair<String, BigDecimal> pair = getNextAvailablequalifier(ordrspLine);

		if (Check.isEmpty(pair.getLeft()))
		{
			return false; // we already have ordrspLines for each of the available QuantityQualifiers
		}

		if (pair.getRight().signum() <= 0)
		{
			return false; // there is no unconfirmed quantity
		}

		return true;
	}

	private IPair<String, BigDecimal> getNextAvailablequalifier(final I_EDI_OrdrspLine ordrspLine)
	{
		final Set<String> availableQualifiers = retrieveQualifierNames();
		availableQualifiers.remove(ordrspLine.getQuantityQualifier());

		BigDecimal sumConfirmedQty = ordrspLine.getConfirmedQty();

		final List<I_EDI_OrdrspLine> manualSiblings = ordrspDAO.retrieveManualSiblings(ordrspLine);

		for (final I_EDI_OrdrspLine sibling : manualSiblings)
		{
			availableQualifiers.remove(sibling.getQuantityQualifier());
			sumConfirmedQty = sumConfirmedQty.add(sibling.getConfirmedQty());
		}

		final Optional<String> quantityQualifier = availableQualifiers.stream().min(Comparator.naturalOrder());
		final BigDecimal yetUnconfirmedQty = ordrspLine.getQtyEntered().subtract(sumConfirmedQty).max(BigDecimal.ZERO);

		return ImmutablePair.<String, BigDecimal> of(quantityQualifier.orElse(""), yetUnconfirmedQty);
	}

	private Set<String> retrieveQualifierNames()
	{
		final Set<String> availableQualifiers = adReferenceDAO.retrieveListItems(getCtx(), X_EDI_OrdrspLine.QUANTITYQUALIFIER_AD_Reference_ID)
				.stream()
				.map(listItem -> listItem.getValue())
				.collect(Collectors.toSet());

		return availableQualifiers;
	}

}
