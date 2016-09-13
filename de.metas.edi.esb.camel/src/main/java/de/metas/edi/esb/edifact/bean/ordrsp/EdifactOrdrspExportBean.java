package de.metas.edi.esb.edifact.bean.ordrsp;

import static de.metas.edi.esb.commons.ValidationHelper.validateNumber;
import static de.metas.edi.esb.commons.ValidationHelper.validateObject;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.commons.lang.mutable.MutableInt;
import org.milyn.edi.unedifact.d96a.ORDRSP.Ordrsp;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup1;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup26;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup3;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup30;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup36;
import org.milyn.edi.unedifact.d96a.ORDRSP.SegmentGroup8;
import org.milyn.edi.unedifact.d96a.common.BGMBeginningOfMessage;
import org.milyn.edi.unedifact.d96a.common.CNTControlTotal;
import org.milyn.edi.unedifact.d96a.common.CUXCurrencies;
import org.milyn.edi.unedifact.d96a.common.DTMDateTimePeriod;
import org.milyn.edi.unedifact.d96a.common.LINLineItem;
import org.milyn.edi.unedifact.d96a.common.NADNameAndAddress;
import org.milyn.edi.unedifact.d96a.common.PRIPriceDetails;
import org.milyn.edi.unedifact.d96a.common.QTYQuantity;
import org.milyn.edi.unedifact.d96a.common.RFFReference;
import org.milyn.edi.unedifact.d96a.common.TAXDutyTaxFeeDetails;
import org.milyn.edi.unedifact.d96a.common.Uns;
import org.milyn.edi.unedifact.d96a.common.field.C002DocumentMessageName;
import org.milyn.edi.unedifact.d96a.common.field.C082PartyIdentificationDetails;
import org.milyn.edi.unedifact.d96a.common.field.C186QuantityDetails;
import org.milyn.edi.unedifact.d96a.common.field.C212ItemNumberIdentification;
import org.milyn.edi.unedifact.d96a.common.field.C241DutyTaxFeeType;
import org.milyn.edi.unedifact.d96a.common.field.C243DutyTaxFeeDetail;
import org.milyn.edi.unedifact.d96a.common.field.C270Control;
import org.milyn.edi.unedifact.d96a.common.field.C5041CurrencyDetails;
import org.milyn.edi.unedifact.d96a.common.field.C506Reference;
import org.milyn.edi.unedifact.d96a.common.field.C507DateTimePeriod;
import org.milyn.edi.unedifact.d96a.common.field.C509PriceInformation;
import org.milyn.payload.JavaSource;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.compudata.pojo.desadv.H000;
import de.metas.edi.esb.compudata.route.exports.EDIDesadvRoute;
import de.metas.edi.esb.edifact.bean.CommonCode;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.EDIExpOrdrspLineType;
import de.metas.edi.esb.jaxb.EDIExpOrdrspType;
import de.metas.edi.esb.jaxb.QuantityQualifierEnum;

public class EdifactOrdrspExportBean
{
	public static final String METHOD_createEDIData = "createUNEdifactIntechange";

	protected static final String voidString = "";

	/**
	 * <ul>
	 * <li>IN: {@link EDIExpDesadvType}</li>
	 * <li>OUT: {@link H000}</li>
	 * </ul>
	 *
	 * @param exchange
	 */
	public final void createUNEdifactIntechange(final Exchange exchange)
	{
		final EDIExpOrdrspType xmlOrdrsp = validateExchange(exchange); // throw exceptions if mandatory fields are missing
		sortLines(xmlOrdrsp);

		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String testMode = exchange.getProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST, String.class);

		final UNEdifactInterchange interchange = createInterchange(xmlOrdrsp);

		final JavaSource source = new JavaSource(interchange);
		exchange.getIn().setBody(source, UNEdifactInterchange.class);
	}

	// TODO: see if we want to migrate to java-8 and use lambda instead
	private void sortLines(final EDIExpOrdrspType ordrsp)
	{
		// sort M_InOutLines
		final List<EDIExpOrdrspLineType> ordrspLines = ordrsp.getEDIExpOrdrspLine();
		Collections.sort(ordrspLines, new Comparator<EDIExpOrdrspLineType>()
		{
			@Override
			public int compare(final EDIExpOrdrspLineType iol1, final EDIExpOrdrspLineType iol2)
			{
				final BigInteger line1 = iol1.getLine();
				final BigInteger line2 = iol2.getLine();
				return line1.compareTo(line2);
			}
		});
	}

	@VisibleForTesting
	/* package */ UNEdifactInterchange createInterchange(final EDIExpOrdrspType xmlOrdrsp)
	{
		final CommonCode commonCode = new CommonCode();

		final UNEdifactInterchange41 interchange = commonCode.createEmptyInterchange(
				xmlOrdrsp.getEDISenderIdentification(),
				xmlOrdrsp.getEDIReceiverIdentification(),
				xmlOrdrsp.getSequenceNoAttr().toString());

		final MutableInt segmentCounter = new MutableInt(0);
		final UNEdifactMessage41 messageEnvelope = commonCode.createEmptyMessage("ORDRSP", segmentCounter);

		final Ordrsp message = createEDIOrdrspFromXMLBean(xmlOrdrsp, segmentCounter);
		messageEnvelope.setMessage(message);
		messageEnvelope.getMessageTrailer().setSegmentCount(segmentCounter.intValue());

		interchange.setMessages(ImmutableList.<UNEdifactMessage41> of(messageEnvelope));

		return interchange;
	}

	@VisibleForTesting
	/* package */ Ordrsp createEDIOrdrspFromXMLBean(final EDIExpOrdrspType xmlOrdrsp,
			final MutableInt segmentCounter)
	{
		final Ordrsp ordrsp = new Ordrsp();

		ordrsp.setBGMBeginningOfMessage(new BGMBeginningOfMessage()
				.setC002DocumentMessageName(new C002DocumentMessageName()
						.setE1001DocumentMessageNameCoded("231")) // BGM010-010 1001 Document/message name, coded; 231 = Purchase order response
				.setE1004DocumentMessageNumber(xmlOrdrsp.getSequenceNoAttr().toString(10)) // BGM020 1004 Document/message number; Description: Reference number assigned to the document/message by the issuer.

		// i don't yet understand if we should return "29" or "4". The example in my document returns "29"
				.setE1225MessageFunctionCoded("29") // 29 = Accepted without amendment; Description: Referenced message is entirely accepted.
		// .setE1225MessageFunctionCoded("4") // 4 = Change; Description: Message containing items (e.g. line items, goods items, Customs items, equipment items) to be changed in a previously sent message.
		); // BGM - Beginning Of Message
		segmentCounter.increment();

		ordrsp.setDTMDateTimePeriod(ImmutableList.<DTMDateTimePeriod> of(new DTMDateTimePeriod()
				.setC507DateTimePeriod(new C507DateTimePeriod()
						.setE2005DateTimePeriodQualifier("137") // 137 = Document/message date/time; Description: (2006) Date/time when a document/message is issued. This may include authentication
						.setE2380DateTimePeriod(Util.formatDate(SystemTime.asDate(), "yyyyMMdd"))
						.setE2379DateTimePeriodFormatQualifier("102"))) // 102 = CCYYMMDD Description: Calendar date: C = Century ; Y = Year ; M = Month ; D = Day.
		); // DTM - DATE/TIME/PERIOD
		segmentCounter.increment();

		ordrsp.setSegmentGroup1(ImmutableList.<SegmentGroup1> of(new SegmentGroup1()
				.setRFFReference(new RFFReference()
						.setC506Reference(new C506Reference()
								.setE1153ReferenceQualifier("ON") // RFF010-010 1153 Reference qualifier; ON = Order number (purchase); Description: [1022] Reference number assigned by the buyer to an order.
								.setE1154ReferenceNumber(xmlOrdrsp.getPOReference())))) // RFF010-020 1154 Reference number
		); // RFF - REFERENCE
		segmentCounter.increment();

		// now the document specifies an optional DTM segment with qualifier = 171 Reference date/time; Description: Date/time on which the reference was issued.
		// we skip it because it seems to be optional and is not included in the example which we got

		ordrsp.setSegmentGroup3(ImmutableList.<SegmentGroup3> of(
				new SegmentGroup3().setNADNameAndAddress(new NADNameAndAddress()
						.setE3035PartyQualifier("SU") // SU = Supplier; Description: (3280) Party which manufactures or otherwise has possession of goods,and consigns or makes them available in trade.
						.setC082PartyIdentificationDetails(new C082PartyIdentificationDetails()
								.setE3039PartyIdIdentification(xmlOrdrsp.getSupplierGLN())
								.setE3055CodeListResponsibleAgencyCoded("9"))),                                                       // 9 = EAN (International Article Numbering association); Description: Self explanatory.
				new SegmentGroup3().setNADNameAndAddress(new NADNameAndAddress()
						.setE3035PartyQualifier("DP") // DP = Delivery party; Description: (3144) Party to which goods should be delivered, if not identical with consignee.
						.setC082PartyIdentificationDetails(new C082PartyIdentificationDetails()
								.setE3039PartyIdIdentification(xmlOrdrsp.getDeliveryGLN())
								.setE3055CodeListResponsibleAgencyCoded("9")) // 9 = EAN
		// .setE3207CountryCoded(e3207CountryCoded) // TODO: we are missing the DP's country code. note that this is only "recommended"
		))); // NAD NAME AND ADDRESS
		segmentCounter.add(2); // +2 because we added two NAD segments

		ordrsp.setSegmentGroup8(ImmutableList.<SegmentGroup8> of(new SegmentGroup8()
				.setCUXCurrencies(new CUXCurrencies()
						.setC5041CurrencyDetails(new C5041CurrencyDetails()
								.setE6347CurrencyDetailsQualifier("2") // 2 = Reference currency; Description: The currency applicable to amounts stated. It may have to be converted.
								.setE6345CurrencyCoded(xmlOrdrsp.getCCurrencyID().getISOCode())
								.setE6343CurrencyQualifier("9") // 9 = Order currency; Description: The name or symbol of the monetary unit used in an order.
		)))); // SegmentGroup8 (CUX)
		segmentCounter.increment();

		ordrsp.setSegmentGroup26(createLines(xmlOrdrsp, segmentCounter))

		.setUNSSectionControl(new Uns()
				.setE0081("S") // UNS010 0081 Section identification; S = Detail/summary section separation; Description: To qualify the segment UNS, when separating the detail from the summary section of a message.
		); // UNS - SECTION CONTROL
		segmentCounter.increment();

		ordrsp.setCNTControlTotal(ImmutableList.<CNTControlTotal> of(new CNTControlTotal()
				.setC270Control(new C270Control() // CNT010 C270 CONTROL; Description: Control total for checking integrity of a message or part of a message.
						.setE6069ControlQualifier("2") // CNT010-010 6069 Control qualifier; 2 = Number of line items in message
						.setE6066ControlValue(new BigDecimal(xmlOrdrsp.getEDIExpOrdrspLine().size())) // CNT010-020 6066 Control value; Description: Value obtained from summing the values specified by the Control Qualifier throughout the message (Hash total).
		))); // CNT - CONTROL TOTAL
		segmentCounter.increment();

		return ordrsp;
	}

	List<SegmentGroup26> createLines(final EDIExpOrdrspType xmlOrdrsp,
			final MutableInt segmentsCounter)
	{
		final ArrayList<SegmentGroup26> result = new ArrayList<SegmentGroup26>();

		for (final EDIExpOrdrspLineType xmlLine : xmlOrdrsp.getEDIExpOrdrspLine())
		{
			// get the value for
			// LIN020 1229 Action request/notification, coded
			final String actionRequestNotification =                                  // the value depends on whether we deliver everything that was ordered (-> 5) or not (-> 3).
			xmlLine.getQtyEntered().compareTo(xmlLine.getConfirmedQty()) == 0
					&& QuantityQualifierEnum.ItemAccepted == xmlLine.getQuantityQualifier()
							? "5" // 5 = Accepted without amendment
							: "3"; // 3 = Changed;

			final String quantityQualifier = validateAndGetQuantityQualifier(xmlOrdrsp, xmlLine);

			final SegmentGroup26 segmentGroup26 = new SegmentGroup26();

			segmentGroup26.setLINLineItem(new LINLineItem()
					.setE1082LineItemNumber(new BigDecimal(xmlLine.getLine())) // LIN010 1082 Line item number
					.setE1229ActionRequestNotificationCoded(actionRequestNotification) // LIN020 1229 Action request/notification, coded
					.setC212ItemNumberIdentification(new C212ItemNumberIdentification() // LIN030 C212 ITEM NUMBER IDENTIFICATION
							.setE7140ItemNumber(xmlLine.getUPC()) // LIN030-010 7140 Item number
							.setE7143ItemNumberTypeCoded("EN")) // LIN030-020 7143 Item number type, coded; EN = International Article Numbering Association (EAN)
			); // LIN - LINE ITEM
			segmentsCounter.increment();

			// TODO OPEN: HOW TO GROUP THEM - assumption: one line = one SegmentGroup26

			// note: we want to exchange our article numbers as EANs, so we can skip the "PIA ADDITIONAL PRODUCT ID" segment that would otherwise follow now.

			segmentGroup26.setQTYQuantity(ImmutableList.<QTYQuantity> of(
					new QTYQuantity().setC186QuantityDetails(new C186QuantityDetails() // QTY010 C186 QUANTITY DETAILS
							.setE6063QuantityQualifier(quantityQualifier) // QTY010-010 6063 Quantity qualifier
							.setE6060Quantity(xmlLine.getConfirmedQty()) // QTY010-020 6060 Quantity
			))); // QTY - QUANTITY
			segmentsCounter.increment();

			if (QuantityQualifierEnum.ItemAccepted.equals(xmlLine.getQuantityQualifier())
					|| QuantityQualifierEnum.ItemBackordered.equals(xmlLine.getQuantityQualifier()))
			{
				final List<DTMDateTimePeriod> dtmDateTimePeriods = new ArrayList<DTMDateTimePeriod>();

				// one for despatch time, one for delivery time, *if* the respective field is not null
				if (xmlLine.getShipDate() != null)
				{
					dtmDateTimePeriods.add(new DTMDateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
							.setC507DateTimePeriod(new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
									.setE2005DateTimePeriodQualifier("11") // DTM010-010 2005 Date/time/period qualifier; 11 = Despatch date and or time, estimated; Description: (2170) Date/time on which the goods are or are expected to be despatched or shipped.
									.setE2380DateTimePeriod(Util.formatDate(xmlLine.getShipDate().toGregorianCalendar().getTime(), "yyyyMMdd")) // DTM010-020 2380 Date/time/period
									.setE2379DateTimePeriodFormatQualifier("102") // DTM010-030 2379 Date/time/period format qualifier; 102 = CCYYMMDD; Description: Calendar date: C = Century ; Y = Year ; M = Month ; D = Day.
					));
					segmentsCounter.increment();
				}
				if (xmlLine.getDeliveryDate() != null)
				{
					dtmDateTimePeriods.add(new DTMDateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
							.setC507DateTimePeriod(new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
									.setE2005DateTimePeriodQualifier("67") // DTM010-010 2005 Date/time/period qualifier; 67 = Delivery date/time, current schedule; Description: Delivery Date deriving from actual schedule.
									.setE2380DateTimePeriod(Util.formatDate(xmlLine.getDeliveryDate().toGregorianCalendar().getTime(), "yyyyMMdd")) // DTM010-020 2380 Date/time/period
									.setE2379DateTimePeriodFormatQualifier("102") // DTM010-030 2379 Date/time/period format qualifier; 102 = CCYYMMDD; Description: Calendar date: C = Century ; Y = Year ; M = Month ; D = Day.
					));
					segmentsCounter.increment();
				}
				if (!dtmDateTimePeriods.isEmpty())
				{
					segmentGroup26.setDTMDateTimePeriod(dtmDateTimePeriods);
				}
			}                    // DTM - DATE/TIME/PERIOD

			segmentGroup26.setSegmentGroup30(ImmutableList.<SegmentGroup30> of(new SegmentGroup30()
					.setPRIPriceDetails(new PRIPriceDetails()
							.setC509PriceInformation(new C509PriceInformation() // PRI010 C509 PRICE INFORMATION; Description: Identification of price type, price and related details.
									.setE5125PriceQualifier("AAA") // PRI010-010 5125 Price qualifier; AAA = Calculation net; Description: The price stated is the net price including allowances/ charges. Allowances/charges may be stated for information only.
									.setE5118Price(xmlLine.getPriceActual()) // PRI010-020 5118 Price
									.setE5375PriceTypeCoded("CT") // PRI010-030 5375 Price type, coded; CT = CT Contract; Description: Self explanatory.
									.setE5387PriceTypeQualifier("NTP") // PRI010-040 5387 Price type qualifier; NTP = Net unit price; Description: Unit price to which no allowances and charges apply.
			)))); // PRI - PRICE DETAILS
			segmentsCounter.increment();

			segmentGroup26.setSegmentGroup36(ImmutableList.<SegmentGroup36> of(new SegmentGroup36()
					.setTAXDutyTaxFeeDetails(new TAXDutyTaxFeeDetails() // TAX010 5283 Duty/tax/fee function qualifier
							.setE5283DutyTaxFeeFunctionQualifier("7") // TAX010 5283 Duty/tax/fee function qualifier; 7 = Tax; Description: Contribution levied by an authority
							.setC241DutyTaxFeeType(new C241DutyTaxFeeType() // TAX020 C241 DUTY/TAX/FEE TYPE
									.setE5153DutyTaxFeeTypeCoded("VAT")) // TAX020-010 5153 Duty/tax/fee type, coded; VAT = Value added tax
							.setC243DutyTaxFeeDetail(new C243DutyTaxFeeDetail() // TAX050 C243 DUTY/TAX/FEE DETAIL
									.setE5278DutyTaxFeeRate(xmlLine.getCTaxID().getRate().toString()) // TAX050-040 5278 Duty/tax/fee rate
			)))); // TAX - DUTY/TAX/FEE DETAILS
			segmentsCounter.increment();

			result.add(segmentGroup26);
		}
		return result;
	}

	private String validateAndGetQuantityQualifier(final EDIExpOrdrspType xmlOrdrsp, final EDIExpOrdrspLineType xmlLine)
	{
		final String quantityQualifier; // QTY010-010 6063 Quantity qualifier
		switch (xmlLine.getQuantityQualifier())
		{
			case ItemAccepted:
				quantityQualifier = "12"; // Despatch quantity; Description: Quantity despatched by the seller
				break;
			case ItemBackordered:
				quantityQualifier = "83"; // Backorder quantity; Description: Items on backorder.
				break;
			case ItemCancelled:
				quantityQualifier = "182"; // Cancelled quantity; Description: Article no longer listed. This code will affect future ordering. Amazon will no longer order this article id, when you send 2 order responses for the article ID using this QTY qualifier.
				break;
			case ItemRejected:
				quantityQualifier = "185"; // Rejected quantity; Description: The quantity of received goods rejected for quantity reasons. The article id is still active for ordering and the article ID might show up in the next order to you.
				break;
			// note: we currently don't support:
			// 192 Free goods quantity; Description: Quantity of goods which are free of charge, but will be shipped.
			default:
				throw new RuntimeCamelException("@EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@= " + xmlLine.getLine() + " has invalid @QuantityQualifier@=" + xmlLine.getQuantityQualifier());
		}
		return quantityQualifier;
	}

	/**
	 * Validate document, and throw {@link RuntimeCamelException} if mandatory fields or properties are missing or empty.
	 *
	 * @param exchange
	 *
	 * @return {@link EDIExpDesadvType} DESADV(aggregated M_InOuts) JAXB object
	 */
	private EDIExpOrdrspType validateExchange(final Exchange exchange)
	{
		// validate mandatory exchange properties
		validateString(exchange.getProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST, String.class), "exchange property IsTest cannot be null or empty");

		final EDIExpOrdrspType xmlOrdrsp = exchange.getIn().getBody(EDIExpOrdrspType.class);
		validateObject(xmlOrdrsp, "@EDI.DESADV.XmlNotNull@"); // DESADV body shall not be null

		validateNumber(xmlOrdrsp.getSequenceNoAttr(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @SequenceNoAttr@");
		validateString(xmlOrdrsp.getPOReference(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @POReference@");
		validateString(xmlOrdrsp.getSupplierGLN(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @SupplierGLN@");
		validateString(xmlOrdrsp.getDeliveryGLN(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @DeliveryGLN@");

		validateObject(xmlOrdrsp.getCCurrencyID(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @C_Currency_ID@");
		validateString(xmlOrdrsp.getCCurrencyID().getISOCode(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @C_Currency_ID@.@ISOCode@");

		// validate strings (not null or empty)
		validateString(xmlOrdrsp.getEDIReceiverIdentification(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @EdiReceiverIdentification@");
		validateString(xmlOrdrsp.getEDISenderIdentification(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @EdiSenderIdentification@");

		// Evaluate EDI_DesadvLines
		final List<EDIExpOrdrspLineType> ediExpOrdrspLines = xmlOrdrsp.getEDIExpOrdrspLine();
		if (ediExpOrdrspLines.isEmpty())
		{
			throw new RuntimeCamelException("@EDI.DESADV.ContainsDesadvLines@");
		}

		for (final EDIExpOrdrspLineType xmlLine : ediExpOrdrspLines)
		{
			// validate mandatory types (not null)
			validateObject(xmlLine.getLine(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " @EDI_OrdrspLine_ID@ @Line@");

			validateAndGetQuantityQualifier(xmlOrdrsp, xmlLine);

			validateNumber(xmlLine.getPriceActual(), "@EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@=" + xmlLine.getLine() + " @PriceActual@ > 0");

			validateObject(xmlLine.getCTaxID(), "@EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@=" + xmlLine.getLine() + " @C_Tax_ID@");
			validateNumber(xmlLine.getCTaxID().getRate(), "@EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@=" + xmlLine.getLine() + " @C_Tax_ID@ @Rate@");

			validateNumber(xmlLine.getQtyEntered(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@=" + xmlLine.getLine() + " @QtyEntered@"); // needed to figure out if we are delivering all of it
			validateNumber(xmlLine.getConfirmedQty(), "@FillMandatory@ @EDI_Ordrsp_ID@=" + xmlOrdrsp.getDocumentNo() + " - @EDI_OrdrspLine_ID@=" + xmlLine.getLine() + " @ConfirmedQty@");

			validateString(xmlLine.getUPC(), "@FillMandatory@ UPC in @EDI_OrdrspLine_ID@ " + xmlLine.getLine());
		}

		return xmlOrdrsp;
	}
}
