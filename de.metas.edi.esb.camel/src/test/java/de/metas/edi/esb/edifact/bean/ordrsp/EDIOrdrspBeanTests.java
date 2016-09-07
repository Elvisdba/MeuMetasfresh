package de.metas.edi.esb.edifact.bean.ordrsp;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.milyn.edi.unedifact.d96a.D96AInterchangeFactory;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;

import de.metas.edi.esb.commons.FixedTimeSource;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDIExpCCurrencyType;
import de.metas.edi.esb.jaxb.EDIExpCTaxType;
import de.metas.edi.esb.jaxb.EDIExpOrdrspLineType;
import de.metas.edi.esb.jaxb.EDIExpOrdrspType;
import de.metas.edi.esb.jaxb.QuantityQualifierEnum;

/*
 * #%L
 * de.metas.edi.esb.camel
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

public class EDIOrdrspBeanTests
{
	/**
	 * A test with the segments UNB up to CUX. I.e. the test does not include lines and neither includes the trailer.
	 * @throws Exception
	 */
	@Test
	public void testUpperPart() throws Exception
	{
		D96AInterchangeFactory factory = D96AInterchangeFactory.getInstance();

		final int monthSeptember = 8;
		SystemTime.setTimeSource(new FixedTimeSource().setTime(2016, monthSeptember, 6, 10, 19, 23));

		final EDIExpOrdrspType xmlOrdrsp = new EDIExpOrdrspType();
		xmlOrdrsp.setSequenceNoAttr(BigInteger.ONE);
		xmlOrdrsp.setEDISenderIdentification("<Sender-GLN>");
		xmlOrdrsp.setEDIReceiverIdentification("<Receiver-GLN>");
		xmlOrdrsp.setPOReference("testPOReference");
		xmlOrdrsp.setSupplierGLN("<Supplier-GLN>");
		xmlOrdrsp.setDeliveryGLN("<Delivery-GLN>");

		final EDIExpCCurrencyType ediExpCCurrencyType = new EDIExpCCurrencyType();
		ediExpCCurrencyType.setISOCode("EUR");
		xmlOrdrsp.setCCurrencyID(ediExpCCurrencyType);

		final EDIExpOrdrspLineType xmlOrdrspLine0 = new EDIExpOrdrspLineType();
		xmlOrdrspLine0.setLine(new BigInteger("10"));

		final EDIExpCTaxType tax = new EDIExpCTaxType();
		tax.setRate(new BigDecimal("19"));
		xmlOrdrspLine0.setCTaxID(tax);

		xmlOrdrspLine0.setShipDate(Util.createCalendarDate("2016-09-05 10:11:12.123"));
		xmlOrdrspLine0.setDeliveryDate(Util.createCalendarDate("2016-09-06 10:11:12.123"));

		xmlOrdrspLine0.setUPC("<Product0-EAN>");
		xmlOrdrspLine0.setPriceActual(new BigDecimal("12.13"));
		xmlOrdrspLine0.setQuantityQualifier(QuantityQualifierEnum.ItemAccepted);
		xmlOrdrspLine0.setQtyEntered(new BigDecimal("15"));
		xmlOrdrspLine0.setConfirmedQty(new BigDecimal("12"));

		xmlOrdrsp.getEDIExpOrdrspLine().add(xmlOrdrspLine0);

		final UNEdifactInterchange interchange = new EDIOrdrspBean().createInterchange(xmlOrdrsp);

		final StringWriter writer = new StringWriter();
		factory.toUNEdifact(interchange, writer);

		// System.out.println(writer.toString().replace("'", "'\n")); // linebreaks for better readability

		assertThat(writer.toString(),
				is("UNB+UNOC:2+<Sender-GLN>:14+<Receiver-GLN>:14+160906:1019+1+++++EANCOM'UNH+1+ORDRSP:D:96D:U:EAN005'BGM+231+1+29'DTM+137:20160906:102'RFF+ON:testPOReference'NAD+SU+<Supplier-GLN>::9'NAD+DP+<Delivery-GLN>::9'CUX+2:EUR:9'LIN+10.0+3+<Product0-EAN>:EN'QTY+12:12.0'DTM+11:20160905:102'DTM+67:20160906:102'PRI+AAA:12.13:CT:NTP'TAX+7+VAT+++:::19'UNS+S'CNT+2:1.0'UNT+16+1'UNZ+1+1'"));
	}
}
