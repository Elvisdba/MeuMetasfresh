package de.metas.edi.esb.edifact.bean;

import java.util.Date;

import org.apache.commons.lang.mutable.MutableInt;
import org.milyn.smooks.edi.unedifact.model.r41.UNB41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org.milyn.smooks.edi.unedifact.model.r41.UNH41;
import org.milyn.smooks.edi.unedifact.model.r41.UNT41;
import org.milyn.smooks.edi.unedifact.model.r41.UNZ41;
import org.milyn.smooks.edi.unedifact.model.r41.types.DateTime;
import org.milyn.smooks.edi.unedifact.model.r41.types.MessageIdentifier;
import org.milyn.smooks.edi.unedifact.model.r41.types.Party;
import org.milyn.smooks.edi.unedifact.model.r41.types.SyntaxIdentifier;

import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;

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

public class CommonCode
{
	public UNEdifactInterchange41 createEmptyInterchange(final String senderId,
			final String receiverId,
			final String interchangeReference)
	{
		final UNEdifactInterchange41 unEdifactInterchange41 = new UNEdifactInterchange41();

		final UNB41 unb41 = new UNB41();

		final SyntaxIdentifier syntaxIdentifier = new SyntaxIdentifier();
		syntaxIdentifier.setId("UNOC"); // UNB010-010 0001 Syntax identifier; UNOC = UN/ECE level C; Description: As defined in ISO 8859-1 : Information processing - Part 1: Latin alphabet No. 1.
		syntaxIdentifier.setVersionNum("2"); // 2 = Version 2; Description: ISO 9735:1990.
		unb41.setSyntaxIdentifier(syntaxIdentifier);

		final Party interchangeSender = new Party();
		interchangeSender.setId(senderId);
		interchangeSender.setCodeQualifier("14"); // 14 = EAN (European Article Numbering Association); Description: Partner identification code assigned by the European Article Numbering Association
		unb41.setSender(interchangeSender);

		final Party interchangeRecipient = new Party();
		interchangeRecipient.setId(receiverId);
		interchangeRecipient.setCodeQualifier("14"); // 14 = EAN
		unb41.setRecipient(interchangeRecipient);

		final Date now = SystemTime.asDate();
		DateTime dateTimeOfPreparation = new DateTime();
		dateTimeOfPreparation.setDate(Util.formatDate(now, "yyMMdd"));
		dateTimeOfPreparation.setTime(Util.formatDate(now, "hhmm"));
		unb41.setDate(dateTimeOfPreparation);

		unb41.setControlRef(interchangeReference);
		unb41.setAgreementId("EANCOM");
		// unb41.setTestIndicator(testIndicator); // not mentioned in the spec
		unEdifactInterchange41.setInterchangeHeader(unb41);

		final UNZ41 unz41 = new UNZ41();
		unz41.setControlCount(1); // UNZ010 0036 Interchange control count; Description: Count either of the number of messages or, if used, of the number of functional groups in an interchange.
		unz41.setControlRef(interchangeReference); // UNZ020 0020 Interchange control reference
		unEdifactInterchange41.setInterchangeTrailer(unz41);

		return unEdifactInterchange41;
	}

	public UNEdifactMessage41 createEmptyMessage(final String messageTypeIdentifier, final MutableInt segmentCounter)
	{
		final UNEdifactMessage41 message = new UNEdifactMessage41();

		final UNH41 unh41 = new UNH41();
		unh41.setMessageRefNum("1"); // UNH010 0062 Message reference number; ! has to be unique within one EDIFact interchange !

		final MessageIdentifier messageIdentifier = new MessageIdentifier(); // UNH020 S009 MESSAGE IDENTIFIER
		messageIdentifier.setId(messageTypeIdentifier); // UNH020-010 0065 Message type identifier
		messageIdentifier.setVersionNum("D"); // UNH020-020 0052 Message type version number; the "D" is taken from the example
		messageIdentifier.setReleaseNum("96D"); // UNH020-030 0054 Message type release number; the "96A" is taken from the example
		messageIdentifier.setControllingAgencyCode("U"); // UNH020-040 0051 Controlling agency; UN/ECE/TRADE/WP.4, United Nations Standard Messages (UNSM)
		messageIdentifier.setAssociationAssignedCode("EAN005"); // UNH020-050 0057 Association assigned code; the "EAN005" is taken from the example
		unh41.setMessageIdentifier(messageIdentifier);

		message.setMessageHeader(unh41);
		segmentCounter.increment();

		final UNT41 unt41 = new UNT41();
		// unt41.setSegmentCount(segmentCounter); // UNT010 0074 Number of segments in a message. We will update this value at the end.
		unt41.setMessageRefNum("1"); // UNT020 0062 Message reference number i guess this can/shall be the same as in the message header

		message.setMessageTrailer(unt41);
		segmentCounter.increment();

		return message;
	}
}
