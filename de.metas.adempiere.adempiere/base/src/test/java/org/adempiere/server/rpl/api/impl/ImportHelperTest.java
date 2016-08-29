package org.adempiere.server.rpl.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.adempiere.server.rpl.api.impl.ReplicationHelper.setReplicationCtx;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.Env;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ImportHelperTest
{
	@BeforeClass
	public static void initBeforeClass()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_setReplicationCtx() throws Exception
	{
		final Properties initialCtx = Env.newTemporaryCtx();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = Env.deriveCtx(initialCtx);
		assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 12345);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, true); // overwrite = true, but same value
		assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		// Test remove
		// We expect that AD_Client_ID to be set to ZERO
		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, null, true); // overwrite = true
		assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 0);
	}

	@Test(expected = ReplicationException.class)
	public void test_setReplicationCtx_overwrite_fails() throws Exception
	{
		final Properties initialCtx = new Properties();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = new Properties(initialCtx);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 2, false); // overwrite = false, not same value, shall throw exception
	}

	/**
	 * Tests {@link ImportHelper#FORMAT_LINE_COMPARATOR}. Check its javadoc for the expected behavior.
	 */
	@Test
	public void testLineComparatorType()
	{
		//
		// note: the variable names reflect the expected ordering, and the order they are created in reflects the initial/unsorted ordering
		//

		final I_EXP_FormatLine line1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);

		final I_EXP_FormatLine line0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0.setType(X_EXP_FormatLine.TYPE_XMLElement);

		final ArrayList<I_EXP_FormatLine> list = Lists.newArrayList(line1, line0);
		list.sort(ImportHelper.FORMAT_LINE_COMPARATOR);

		assertThat("Wrong ordering of list", list, contains(line0, line1));
	}

	/**
	 * Tests {@link ImportHelper#FORMAT_LINE_COMPARATOR}. Check its javadoc for the expected behavior.
	 */
	@Test
	public void testLineComparatorTypeAndMandatory()
	{
		//
		// note: the variable names reflect the expected ordering, and the order they are created in reflects the initial/unsorted ordering
		//

		final I_EXP_FormatLine line1_1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1_1.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);
		line1_1.setIsMandatory(false);

		final I_EXP_FormatLine line1_0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1_0.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);
		line1_0.setIsMandatory(true);

		final I_EXP_FormatLine line0_0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0_0.setType(X_EXP_FormatLine.TYPE_XMLElement);
		line0_0.setIsMandatory(true);

		final I_EXP_FormatLine line0_1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0_1.setType(X_EXP_FormatLine.TYPE_XMLElement);
		line0_1.setIsMandatory(false);

		final ArrayList<I_EXP_FormatLine> list = Lists.newArrayList(line1_1, line1_0, line0_0, line0_1);

		list.sort(ImportHelper.FORMAT_LINE_COMPARATOR);
		assertThat("Wrong ordering of list", list, contains(line0_0, line0_1, line1_0, line1_1));
	}

	/**
	 * Tests {@link ImportHelper#FORMAT_LINE_COMPARATOR}. Check its javadoc for the expected behavior.
	 */
	@Test
	public void testLineComparatorTypeAndMandatoryAndPos()
	{
		//
		// note: the variable names reflect the expected ordering, and the order they are created in reflects the initial/unsorted ordering
		//

		final I_EXP_FormatLine line1_1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1_1.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);
		line1_1.setIsMandatory(false);
		line1_1.setPosition(10);

		final I_EXP_FormatLine line1_0_1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1_0_1.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);
		line1_0_1.setIsMandatory(true);
		line1_0_1.setPosition(20);

		final I_EXP_FormatLine line1_0_0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line1_0_0.setType(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat);
		line1_0_0.setIsMandatory(true);
		line1_0_0.setPosition(10);

		final I_EXP_FormatLine line0_0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0_0.setType(X_EXP_FormatLine.TYPE_XMLElement);
		line0_0.setIsMandatory(true);

		final I_EXP_FormatLine line0_1_1 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0_1_1.setType(X_EXP_FormatLine.TYPE_XMLElement);
		line0_1_1.setIsMandatory(false);
		line0_1_1.setPosition(50);

		final I_EXP_FormatLine line0_1_0 = InterfaceWrapperHelper.newInstance(I_EXP_FormatLine.class);
		line0_1_0.setType(X_EXP_FormatLine.TYPE_XMLElement);
		line0_1_0.setIsMandatory(false);
		line0_1_0.setPosition(40);

		final ArrayList<I_EXP_FormatLine> list = Lists.newArrayList(line1_1, line1_0_1, line1_0_0, line0_0, line0_1_1, line0_1_0);

		list.sort(ImportHelper.FORMAT_LINE_COMPARATOR);
		assertThat("Wrong ordering of list", list, contains(line0_0, line0_1_0, line0_1_1, line1_0_0, line1_0_1, line1_1));
	}
}
