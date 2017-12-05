package de.metas.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.adempiere.model.I_C_Invoice;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ProcessExecutionResultTest
{
	private ObjectMapper jsonMapper = null;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		jsonMapper = new ObjectMapper();
	}

	@Test
	public void testJsonSerializeDeserialize() throws Exception
	{
		ProcessExecutionResult result = new ProcessExecutionResult();
		result.setAD_PInstance_ID(12345);
		result.setRecordToSelectAfterExecution(createDummyTableRecordReference());
		result.markAsError("error summary1");
		result.setReportData(new byte[] { 1, 2, 3 }, "report.pdf", "application/pdf");
		//
		result.setRecordsToOpen(createDummyTableRecordReferenceList(3), 1234);
		//
		result.setWebuiIncludedViewIdToOpen("123-dummyIncludedViewId");
		result.setWebuiViewProfileId("dummyProfile");
		//
		System.out.println("result: " + result);

		final String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		System.out.println("json: \n" + json);

		final ProcessExecutionResult resultFromJson = jsonMapper.readValue(json, ProcessExecutionResult.class);
		System.out.println("result (from json): " + resultFromJson);

		// Compare
		Assert.assertEquals(result.getAD_PInstance_ID(), resultFromJson.getAD_PInstance_ID());
		Assert.assertEquals(result.getRecordToSelectAfterExecution(), resultFromJson.getRecordToSelectAfterExecution());
		Assert.assertEquals(result.getSummary(), resultFromJson.getSummary());
		Assert.assertEquals(result.isError(), resultFromJson.isError());
		Assert.assertEquals(result.isErrorWasReportedToUser(), resultFromJson.isErrorWasReportedToUser());
		Assert.assertEquals(result.isShowProcessLogs(), resultFromJson.isShowProcessLogs());
		Assert.assertEquals(result.isRefreshAllAfterExecution(), resultFromJson.isRefreshAllAfterExecution());
		//
		Assert.assertArrayEquals(result.getReportData(), resultFromJson.getReportData());
		Assert.assertEquals(result.getReportFilename(), resultFromJson.getReportFilename());
		Assert.assertEquals(result.getReportContentType(), resultFromJson.getReportContentType());
		//
		Assert.assertEquals(result.getRecordToSelectAfterExecution(), resultFromJson.getRecordToSelectAfterExecution());
		//
		Assert.assertEquals(result.getRecordsToOpen(), resultFromJson.getRecordsToOpen());
		//
		Assert.assertEquals(result.getWebuiIncludedViewIdToOpen(), resultFromJson.getWebuiIncludedViewIdToOpen());
		Assert.assertEquals(result.getWebuiViewProfileId(), resultFromJson.getWebuiViewProfileId());
		//
		// Assert.assertEquals(result.get, resultFromJson.get);
	}

	private static final TableRecordReference createDummyTableRecordReference()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(invoice);
		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);
		return invoiceRef;
	}

	private static final List<TableRecordReference> createDummyTableRecordReferenceList(final int size)
	{
		final List<TableRecordReference> list = new ArrayList<>();
		for (int i = 0; i < size; i++)
		{
			list.add(createDummyTableRecordReference());
		}
		return list;
	}

}
