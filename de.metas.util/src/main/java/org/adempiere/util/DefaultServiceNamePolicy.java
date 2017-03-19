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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Default implementation to be used at metasfresh runtime.<br>
 * For a given {@link IService} class, it returns a service implementation class name that is similar to the interface's class name, but sits in an <code>impl</code> package.<br>
 * Check out the code for details
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DefaultServiceNamePolicy implements IServiceNameAutoDetectPolicy
{

	@Override
	public List<String> getServiceImplementationClassNames(final Class<? extends IService> interfaceClass)
	{
		final String serviceClassName;
		if (interfaceClass.getSimpleName().startsWith("I"))
		{
			serviceClassName = interfaceClass.getSimpleName().substring(1);
		}
		else
		{
			serviceClassName = interfaceClass.getSimpleName();
		}

		final String interfacePackageName = interfaceClass.getPackage().getName();

		return ImmutableList.of(
				interfacePackageName + ".impl." + serviceClassName // impl package: de.metas.package.IServiceBL -> de.metas.package.impl.ServiceBL
				, interfacePackageName + "." + serviceClassName // same package: de.metas.package.IServiceBL -> de.metas.package.ServiceBL
		);
	}

}
