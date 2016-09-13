package de.metas.edi.esb.edifact.bean.desadv;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.commons.lang.mutable.MutableInt;
import org.milyn.edi.unedifact.d96a.DESADV.Desadv;
import org.milyn.payload.JavaSource;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;

import com.google.common.collect.ImmutableList;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.compudata.route.exports.EDIDesadvRoute;
import de.metas.edi.esb.edifact.bean.CommonCode;
import de.metas.edi.esb.jaxb.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class EdifactDesadvExportBean
{
	public final void createUNEdifactIntechange(final Exchange exchange)
	{
		final EDIExpDesadvType xmlDesadv = validateExchange(exchange); // throw exceptions if mandatory fields are missing
		sortLines(xmlDesadv);

		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String testMode = exchange.getProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST, String.class);

		final UNEdifactInterchange interchange = createInterchange(xmlDesadv);

		final JavaSource source = new JavaSource(interchange);
		exchange.getIn().setBody(source, UNEdifactInterchange.class);
	}

	private EDIExpDesadvType validateExchange(Exchange exchange)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: see if we want to migrate to java-8 and use lambda instead
	private void sortLines(final EDIExpDesadvType xmlDesadv)
	{
		// sort M_InOutLines
		final List<EDIExpDesadvLineType> ordrspLines = xmlDesadv.getEDIExpDesadvLine();
		Collections.sort(ordrspLines, new Comparator<EDIExpDesadvLineType>()
		{
			@Override
			public int compare(final EDIExpDesadvLineType iol1, final EDIExpDesadvLineType iol2)
			{
				final BigInteger line1 = iol1.getLine();
				final BigInteger line2 = iol2.getLine();
				return line1.compareTo(line2);
			}
		});
	}

	private UNEdifactInterchange createInterchange(EDIExpDesadvType xmlDesadv)
	{
		final CommonCode commonCode = new CommonCode();

		final UNEdifactInterchange41 interchange = commonCode.createEmptyInterchange(
				xmlDesadv.getEDISenderIdentification(),
				xmlDesadv.getEDIReceiverIdentification(),
				xmlDesadv.getSequenceNoAttr().toString());

		final MutableInt segmentCounter = new MutableInt(0);
		final UNEdifactMessage41 messageEnvelope = commonCode.createEmptyMessage("DESADV", segmentCounter);

		final Desadv message = createEDIDesadvFromXMLBean(xmlDesadv, segmentCounter);
		messageEnvelope.setMessage(message);
		messageEnvelope.getMessageTrailer().setSegmentCount(segmentCounter.intValue());

		interchange.setMessages(ImmutableList.<UNEdifactMessage41> of(messageEnvelope));

		return interchange;
	}

	private Desadv createEDIDesadvFromXMLBean(EDIExpDesadvType xmlDesadv, MutableInt segmentCounter)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
