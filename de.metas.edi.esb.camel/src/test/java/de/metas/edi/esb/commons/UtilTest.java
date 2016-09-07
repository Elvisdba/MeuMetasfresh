package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
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


import static de.metas.edi.esb.commons.Util.normalize;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class UtilTest
{
	@Test
	public void testNormalize()
	{
		final String input = "~!@#$%^&*()_+{}:\"|<>?|`-=[];'\\,./ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
		final String actualResult = normalize(input);

		final String expectedResult = "~!@#$%^&*()_+{}:\"|<>?|`-=[];'\\,./AAAAAAAECEEEEIIIIDNOOOOOxOUUUUYthssaaaaaaaceeeeiiiionooooo÷ouuuuythy";

		assertEquals(input + " must be converted to " + actualResult, expectedResult, actualResult);
	}

	@Test
	public void testFormatDate()
	{
		final int monthSeptember = 8;
		final Date date = new Date(new FixedTimeSource().setTime(2016, monthSeptember, 6, 10, 19, 23).millis());
		assertEquals("20160906", Util.formatDate(date, "yyyyMMdd"));
	}
}
