package de.metas.edi.api;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * "Sendability" state of a document that shall be send via EDI.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum ValidationState
{
	/**
	 * The document can't be send, e.g. because a GLN is missing in the underlying master data.
	 */
	INVALID,

	/**
	 * The document was already valid (could be send) and it didn't change.
	 */
	ALREADY_VALID,

	/**
	 * The document was invalid, but now it was found valid and can therefore now be send.
	 */
	UPDATED_VALID
}
