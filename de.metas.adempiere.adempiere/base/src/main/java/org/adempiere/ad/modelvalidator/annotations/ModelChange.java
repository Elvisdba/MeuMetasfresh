package org.adempiere.ad.modelvalidator.annotations;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adempiere.ad.modelvalidator.ModelChangeType;

/**
 * Indicates that the annotated method shall be triggered on a particular model change validator event.
 *
 * Your annotated method can have following formats:
 * <ul>
 * <li>public void myMethod(final MyModelClass model)
 * <li>public void myMethod(final MyModelClass model, final int timing)
 * </ul>
 *
 * @author tsa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ModelChange
{

	/**
	 * On which model change events shall we call the annotated methods.
	 *
	 * For more information about events, please check {@link ModelChangeType#getChangeType()} values.
	 *
	 * At least one event shall be specified.
	 */
	int[] timings() default {};

	/**
	 * Indicate that the method shall be called only if one of the given fields were changed.
	 * <p>
	 * This is optional and can be overridden by {@link #ignoreColumnsChanged()}.
	 */
	String[] ifColumnsChanged() default {};

	/**
	 * Specify the columns that shall be excluded from "column value changed" checking.
	 * <p>
	 * This is optional and overrides possible {@link #ifColumnsChanged()} settings. Example:
	 *
	 * <pre>
	 * ifColumnsChanged={IsPaid, C_BBartner_ID}, ignoreColumnsChanged={IsPaid}
	 * </pre>
	 *
	 * => in this case, the system will only check for <code>C_BBartner_ID</code>.
	 * <p>
	 * If the annotation is specifying only ignore columns but no {@link #ifColumnsChanged()}-columns then all columns excluding the ignore columns will be checked for changes.
	 */
	String[] ignoreColumnsChanged() default {};

	/**
	 * If true, this event shall be triggered only if there was an UI/user action (i.e. user changed the record manually from a window)
	 *
	 * @return
	 */
	boolean ifUIAction() default false;

	/**
	 * <code>true</code> if this method will be scheduled to be executed after transaction commit.
	 * <p>
	 * WARNINGs:<br>
	 * * if you want do store things to DB, you need to do so in your own local transaction<br>
	 * * any failure will be just logged and will not prevent execution<br>
	 */
	boolean afterCommit() default false;
}
