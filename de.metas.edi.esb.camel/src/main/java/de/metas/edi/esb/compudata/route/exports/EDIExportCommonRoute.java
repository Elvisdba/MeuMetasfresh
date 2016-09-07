package de.metas.edi.esb.compudata.route.exports;

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


import java.text.DecimalFormat;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spi.DataFormat;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.compudata.route.AbstractEDIRoute;
import de.metas.edi.esb.jaxb.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;

public class EDIExportCommonRoute extends AbstractEDIRoute
{
	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		from(Constants.EP_JMS_FROM_AD)
				.routeId("XML-To-EDI-Common")

				.log(LoggingLevel.INFO, "EDI: Processing XML body:\r\n" + body())

				.setProperty(AbstractEDIRoute.IS_CREATE_XML_FEEDBACK, constant(true))
				.setProperty(Constants.DECIMAL_FORMAT, constant(decimalFormat))

				.log(LoggingLevel.INFO, "EDI: Unmarshalling XML...")
				.unmarshal(jaxb)

				.setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, body())

				// @formatter:off
				.choice()
					.when(body().isInstanceOf(EDICctopInvoicVType.class))
						.to(EDIInvoiceRoute.EP_EDI_INVOICE_CONSUMER)
					//
					// DESADV (exported EDI_Desadv record)
					.when(body().isInstanceOf(EDIExpDesadvType.class))
						.to(EDIDesadvRoute.EP_EDI_DESADV_CONSUMER)
				.end();
				// @formatter:on
	}
}
