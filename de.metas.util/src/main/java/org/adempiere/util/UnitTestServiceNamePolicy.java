package org.adempiere.util;

import java.util.List;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.util
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
 * Implementation to load "plain" (i.e. DB decoupled) service implementations. Mainly used for unit and module testing.
 * 
 * 
 */
public class UnitTestServiceNamePolicy extends DefaultServiceNamePolicy implements IServiceNameAutoDetectPolicy
{

	@Override
	public List<String> getServiceImplementationClassNames(final Class<? extends IService> interfaceClass)
	{
		final String servicePackageName = interfaceClass.getPackage().getName() + ".impl.";

		String serviceClassName;
		if (interfaceClass.getSimpleName().startsWith("I"))
			serviceClassName = interfaceClass.getSimpleName().substring(1);
		else
			serviceClassName = interfaceClass.getSimpleName();

		final String plainServiceClassNameFQ = servicePackageName + "Plain" + serviceClassName;
		try
		{
			Thread.currentThread().getContextClassLoader().loadClass(plainServiceClassNameFQ);
			return ImmutableList.of(plainServiceClassNameFQ);
		}
		catch (ClassNotFoundException e)
		{
			// logger.debug("No Plain/Unit service found for "+clazz+". Tried "+plainServiceClassNameFQ);
			// Plain Service not found, use original one

			// TODO also try to load
			return super.getServiceImplementationClassNames(interfaceClass);
		}
	}

}
