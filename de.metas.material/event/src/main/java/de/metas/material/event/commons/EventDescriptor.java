package de.metas.material.event.commons;

import java.time.Instant;
import java.util.UUID;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IClientOrgAware;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-event
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
@AllArgsConstructor
public class EventDescriptor
{

	public EventDescriptor(final int clientId, final int orgId)
	{
		this(Instant.now(), UUID.randomUUID(), clientId, orgId);
	}

	@NonNull
	private final Instant when;

	@NonNull
	private final UUID uuid;

	@NonNull
	private final Integer clientId;

	@NonNull
	private final Integer orgId;

	public EventDescriptor createNew()
	{
		return new EventDescriptor(clientId, orgId);
	}

	/**
	 * 
	 * @param clientOrgAware model which can be made into a {@link IClientOrgAware} via {@link InterfaceWrapperHelper#asColumnReferenceAwareOrNull(Object, Class)}.
	 * @return
	 */
	public static EventDescriptor createNew(final Object clientOrgAware)
	{
		final IClientOrgAware clientOrgAwareToUse = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(clientOrgAware, IClientOrgAware.class);

		return new EventDescriptor(
				clientOrgAwareToUse.getAD_Client_ID(),
				clientOrgAwareToUse.getAD_Org_ID());
	}
}
