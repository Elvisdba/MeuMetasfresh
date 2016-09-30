package de.metas.edi.esb.edifact.bean.desadv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.commons.lang.mutable.MutableInt;
import org.milyn.edi.unedifact.d96a.DESADV.Desadv;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup1;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup10;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup11;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup13;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup14;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup15;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup16;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup2;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup20;
import org.milyn.edi.unedifact.d96a.DESADV.SegmentGroup21;
import org.milyn.edi.unedifact.d96a.common.BGMBeginningOfMessage;
import org.milyn.edi.unedifact.d96a.common.CPSConsignmentPackingSequence;
import org.milyn.edi.unedifact.d96a.common.DTMDateTimePeriod;
import org.milyn.edi.unedifact.d96a.common.GINGoodsIdentityNumber;
import org.milyn.edi.unedifact.d96a.common.LINLineItem;
import org.milyn.edi.unedifact.d96a.common.NADNameAndAddress;
import org.milyn.edi.unedifact.d96a.common.PACPackage;
import org.milyn.edi.unedifact.d96a.common.PCIPackageIdentification;
import org.milyn.edi.unedifact.d96a.common.QTYQuantity;
import org.milyn.edi.unedifact.d96a.common.RFFReference;
import org.milyn.edi.unedifact.d96a.common.field.C002DocumentMessageName;
import org.milyn.edi.unedifact.d96a.common.field.C082PartyIdentificationDetails;
import org.milyn.edi.unedifact.d96a.common.field.C186QuantityDetails;
import org.milyn.edi.unedifact.d96a.common.field.C202PackageType;
import org.milyn.edi.unedifact.d96a.common.field.C2081IdentityNumberRange;
import org.milyn.edi.unedifact.d96a.common.field.C212ItemNumberIdentification;
import org.milyn.edi.unedifact.d96a.common.field.C506Reference;
import org.milyn.edi.unedifact.d96a.common.field.C507DateTimePeriod;
import org.milyn.payload.JavaSource;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;

import com.google.common.collect.ImmutableList;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	private EDIExpDesadvType validateExchange(final Exchange exchange)
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

	private UNEdifactInterchange createInterchange(final EDIExpDesadvType xmlDesadv)
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

		interchange.setMessages(ImmutableList.of(messageEnvelope));
		return interchange;
	}

	/**
	 * Note about updating/replacing desadvs (segment "BGM030 1225 Message function"):
	 *
	 * <pre>
	 * Unique ASN ID (do not repeat within 365 days).
	 * In order to edit a previously submitted EDI ASN, a second EDI ASN can be sent. The ASN ID (BGM+351) and
	 * Supplier Identification (NAD+SU) need to be identical for both transmissions, so that our system can
	 * successfully overwrite the previous version of the EDI ASN.
	 * Please consult the "EU EDI ASN Business Requirements" in the EDI package from Vendor Central Resource
	 * Center for further information.
	 * </pre>
	 * <p>
	 * Note about the Bill of Lading (BOL) number (segment "RFF010-010 1153 Reference qualifier" = BM):
	 *
	 * <pre>
	 * External Information: The Bill of Lading (BOL) reference number is the unique number
	 * assigned by the shipper in creating the Bill of Lading. This is a number that identifies a unique
	 * shipment.
	 * The BOL sent in the DESADV ideally matches the paper BOL provided with the shipment, but
	 * that is no must. The BOL is the reference that is used for appointment booking.
	 * You can either send this shipment id via RFF+BM or RFF+DQ.
	 * If neither is available, the DESADV is non-compliant and cannot be used.
	 * Formatting Notes: If possible, Amazon suggests to add a prefix to the BOL reference to make
	 * it unique per vendor, e.g. 'ABCD' in RFF+BM:ABCD12332'
	 * </pre>
	 *
	 * @param xmlDesadv
	 * @param segmentCounter
	 * @return
	 */
	private Desadv createEDIDesadvFromXMLBean(final EDIExpDesadvType xmlDesadv, final MutableInt segmentCounter)
	{
		final Desadv desadv = new Desadv();

		// BGM
		//
		desadv.setBGMBeginningOfMessage(
				new BGMBeginningOfMessage() // BGM BEGINNING OF MESSAGE
						.setC002DocumentMessageName(
								new C002DocumentMessageName() // BGM010 C002 DOCUMENT/MESSAGE NAME; Description: Identification of a type of document/message by code or name. Code preferred.
										.setE1001DocumentMessageNameCoded("351") // BGM010-010 1001 Document/message name, coded; 351 = Despatch advice
										.setE3055CodeListResponsibleAgencyCoded("9")) // BGM010-030 3055 Code list responsible agency, coded; 9 = EAN; Description: Code identifying the agency responsible for a code list.
						.setE1004DocumentMessageNumber(xmlDesadv.getDocumentNo()) // BGM020 1004 Document/message number; Description: Reference number assigned to the document/message by the issuer.
						.setE1225MessageFunctionCoded("9") // BGM030 1225 Message function, coded; 5=Replace, 9=Original; Description: Code indicating the function of the message.
		); // end of BGM
		segmentCounter.increment();

		// DTM
		//
		desadv.setDTMDateTimePeriod(ImmutableList.of(
				new DTMDateTimePeriod()  // DTM+11; Date on which the shipment leaves the vendor’s warehouse
						.setC507DateTimePeriod(
								new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
										.setE2005DateTimePeriodQualifier("11") // DTM010-010 2005 Date/time/period qualifier; 11 = Despatch date and or time; Description: Code giving specific meaning to a date, time or period.
										.setE2380DateTimePeriod(Util.formatDate(xmlDesadv.getMovementDate().toGregorianCalendar().getTime(), "yyyyMMdd")) // DTM010-020 2380 Date/time/period
										.setE2379DateTimePeriodFormatQualifier("102") // DTM010-030 2379 Date/time/period format qualifier; 102=CCYYMMDD; Description: Specification of the representation of a date, a date and time or of a period.
		),                                                                                              // end of DTM+11
				new DTMDateTimePeriod()  // DTM+17; Date on which the shipment is expected to reach the recipient
						.setC507DateTimePeriod(
								new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
										// TODO: get delivery time from desadv!! It might or might not be the MovementDate
										// note: i'm not clear about the difference between delivery and arrival time, but i think it's not critical
										.setE2005DateTimePeriodQualifier("17") // DTM010-010 2005 Date/time/period qualifier; 17=Delivery date/time, estimated, 132=Arrival date/time, estimated;
										.setE2380DateTimePeriod(Util.formatDate(xmlDesadv.getMovementDate().toGregorianCalendar().getTime(), "yyyyMMdd")) // DTM010-020 2380 Date/time/period
										.setE2379DateTimePeriodFormatQualifier("102") // DTM010-030 2379 Date/time/period format qualifier; 102=CCYYMMDD; Description: Specification of the representation of a date, a date and time or of a period.
		),                                                                                             // end of DTM+17
				new DTMDateTimePeriod()  // DTM+137;
						.setC507DateTimePeriod(
								new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
										// TODO: get document time from desadv!! It might or might not be the MovementDate...or maybe DateAcct
										// note: i'm not clear about the difference between delivery and arrival time, but i think it's not critical
										.setE2005DateTimePeriodQualifier("137") // DTM010-010 2005 Date/time/period qualifier; 137=Document/message date/time;
										.setE2380DateTimePeriod(Util.formatDate(xmlDesadv.getMovementDate().toGregorianCalendar().getTime(), "yyyyMMdd")) // DTM010-020 2380 Date/time/period
										.setE2379DateTimePeriodFormatQualifier("102") // DTM010-030 2379 Date/time/period format qualifier; 102=CCYYMMDD; Description: Specification of the representation of a date, a date and time or of a period.
		) // end of DTM+137
		)); // end of setDTMDateTimePeriod()
		segmentCounter.add(desadv.getDTMDateTimePeriod().size());

		// SegmentGroup1
		//
		desadv.setSegmentGroup1(ImmutableList.of(
				new SegmentGroup1() // RFF+BM; Bill of lading number
						.setRFFReference(
								new RFFReference() // RFF REFERENCE
										.setC506Reference(
												new C506Reference() // RFF010 C506 REFERENCE; Description: Identification of a reference.
														.setE1153ReferenceQualifier("BM") // RFF010-010 1153 Reference qualifier; BM=Bill of lading number, DQ=Delivery note number; Description: Code giving specific meaning to a reference segment or a reference number.
														.setE1154ReferenceNumber(xmlDesadv.getDocumentNo()) // RFF010-020 1154 Reference number
		)),                                                                                                              // end of RFF+BM
				new SegmentGroup1() // RFF+ON; Order number (purchase)
						.setRFFReference(
								new RFFReference() // RFF REFERENCE
										.setC506Reference(
												new C506Reference() // RFF010 C506 REFERENCE; Description: Identification of a reference.
														.setE1153ReferenceQualifier("ON") // RFF010-010 1153 Reference qualifier; ON=Order number (purchase); Description: Code giving specific meaning to a reference segment or a reference number.
														.setE1154ReferenceNumber(xmlDesadv.getPOReference()) // RFF010-020 1154 Reference number
		)) // end of RFF+ON

		// Note: skipping because it's not yet clear that we need it
		// RFF+CN; Carrier's reference number
		// RFF+ACD; Additional reference number
		)); // end of Segment Group 1
		segmentCounter.add(desadv.getSegmentGroup1().size());

		// SegmentGroup2
		//
		desadv.setSegmentGroup2(ImmutableList.of(
				new SegmentGroup2() // NAD+CA; Carrier
						.setNADNameAndAddress(
								new NADNameAndAddress() // NAD NAME AND ADDRESS
										.setE3035PartyQualifier("CA") // NAD010 3035 Party qualifier; CA=Carrier
										.setC082PartyIdentificationDetails(
												new C082PartyIdentificationDetails() // NAD020 C082 PARTY IDENTIFICATION DETAILS
														// TODO: we are asked to provide the SCAC here. maybe we need to add M_Shipper to EDI_Desadv and then store the SCAC there? Btw, SCAC = Standard Carrier Alpha Code
														.setE3039PartyIdIdentification("") // NAD020-010 3039 Party id. identification
		)),                                                                                                                          // end of NAD+CA
				new SegmentGroup2() // NAD+DP; Delivery party
						.setNADNameAndAddress(
								new NADNameAndAddress() // NAD NAME AND ADDRESS
										.setE3035PartyQualifier("DP") // NAD010 3035 Party qualifier; DP=Delivery Party
										.setC082PartyIdentificationDetails(
												new C082PartyIdentificationDetails() // NAD020 C082 PARTY IDENTIFICATION DETAILS
														// TODO: add DeliveryGLN to EDI_Desadv
														// .setE3039PartyIdIdentification(xmlDesadv.getDeliveryGLN()) // NAD020-010 3039 Party id. identification
														.setE3055CodeListResponsibleAgencyCoded("9")) // NAD020-030 3055 Code list responsible agency, coded; 9=EAN
										// TODO: we are missing the DP's country code. note that this is only "recommended"
										.setE3207CountryCoded("") // NAD090 3207 Country, coded;
		),                                                                                                                        // end of NAD+DP
				new SegmentGroup2() // NAD+SU; Supplier
						.setNADNameAndAddress(
								new NADNameAndAddress() // NAD NAME AND ADDRESS
										.setE3035PartyQualifier("SU") // NAD010 3035 Party qualifier
										.setC082PartyIdentificationDetails(
												new C082PartyIdentificationDetails() // NAD020 C082 PARTY IDENTIFICATION DETAILS
														// TODO: add DeliveryGLN to EDI_Desadv
														// .setE3039PartyIdIdentification(xmlDesadv.getSupplierGLN()) // NAD020-010 3039 Party id. identification
														.setE3055CodeListResponsibleAgencyCoded("9") // NAD020-030 3055 Code list responsible agency, coded; 9=EAN
		)),                                                                                                                     // end of NAD+SU
				new SegmentGroup2() // NAD+SF; Ship From (warehouse)
						.setNADNameAndAddress(
								new NADNameAndAddress() // NAD NAME AND ADDRESS
										.setE3035PartyQualifier("SF") // NAD010 3035 Party qualifier
										.setC082PartyIdentificationDetails(
												new C082PartyIdentificationDetails() // NAD020 C082 PARTY IDENTIFICATION DETAILS
														// TODO: add ShipFromGLN to EDI_Desadv
														// .setE3039PartyIdIdentification(xmlDesadv.getShipFromGLN()) // NAD020-010 3039 Party id. identification
														.setE3055CodeListResponsibleAgencyCoded("9")) // NAD020-030 3055 Code list responsible agency, coded; 9=EAN
										// TODO add zip code and 2-digit country code for NAD+SF; note that here they are a must.
										.setE3251PostcodeIdentification("") // NAD080 3251 Postcode identification
										.setE3207CountryCoded("") // NAD090 3207 Country, coded
		) // end of NAD+SF
		));		// end of Segment Group 2
		segmentCounter.add(desadv.getSegmentGroup2().size());

		// SegmentGroup6
		//
		// for now we skip "Segment Group 6" with "TDT - DETAILS OF TRANSPORT". It's apparently not a must
		// TDT is about e.g. "by train", "by aircraft", etc

		// Segmentgroup10
		//
		final ArrayList<SegmentGroup10> segmentgroup10s = new ArrayList<SegmentGroup10>();
		desadv.setSegmentGroup10(segmentgroup10s);
		segmentgroup10s.add(

		// adding the top level group of the packaging hierarchy
				new SegmentGroup10()
						.setCPSConsignmentPackingSequence(
								new CPSConsignmentPackingSequence() // CPS CONSIGNMENT PACKING SEQUENCE
										.setE7164HierarchicalIdNumber("1")) // CPS010 7164 Hierarchical id. number; 1=root of the package tree; Description: A unique number assigned by the sender to identify a level within a hierarchical structure.
						.setSegmentGroup11(ImmutableList.of(
								new SegmentGroup11() // PAC+[# of pallets]; Number of pallets present in the shipment.
										.setPACPackage(
												new PACPackage()
														// TODO: compute the number of pallets if possible, and/or extend EDI_Desadv as needed
														.setE7224NumberOfPackages(BigDecimal.ZERO) // PAC010 7224 Number of packages; Description: Number of individual parts of a shipment either unpacked, or packed in such a way that they cannot be divided without first undoing the packing.
														.setC202PackageType(
																new C202PackageType() // PAC030 C202 PACKAGE TYPE; Description: Type of package by name or by code from a specified source.
																		.setE7065TypeOfPackagesIdentification("201") // PAC030-010 7065 Type of packages identification; 201=ISO EURO pallet, 202=UK industrial size pallets (might only be applicable for UK);
		)),                                                                             // end of PAC+[# of pallets]
								new SegmentGroup11() // PAC+[# of cartons]; Number of pallets present in the shipment.
										// Number of cartons present in the shipment. Units that are stacked on the pallet without outer carton are counted as 1 unit = 1 carton.
										.setPACPackage(
												new PACPackage()
														// TODO: compute the number of boxes if possible, and/or extend EDI_Desadv as needed
														.setE7224NumberOfPackages(BigDecimal.ZERO) // PAC010 7224 Number of packages;
														.setC202PackageType(
																new C202PackageType() // PAC030 C202 PACKAGE TYPE
																		.setE7065TypeOfPackagesIdentification("PK") // PAC030-010 7065 Type of packages identification; PK=carton
		)) // end of PAC+[# of cartons]
		)) // end of segment group 11
		); // end of the top level segment group 10
		segmentCounter.add(1 + 1 + 1); // CPS + PAC-pallets + PAC+cartons

		final int counter = 0;

		for (final EDIExpDesadvLineType xmlDesadvLine : xmlDesadv.getEDIExpDesadvLine())
		{ // look over the different packages we have around

			final String e7065TypeOfPackagesIdentification = "PK"; // TODO might be PK, 201 or 202

			segmentgroup10s.add(
					new SegmentGroup10()
							.setCPSConsignmentPackingSequence(
									new CPSConsignmentPackingSequence() // CPS CONSIGNMENT PACKING SEQUENCE
											.setE7164HierarchicalIdNumber(Integer.toString(counter)) // CPS010 7164 Hierarchical id. number
											.setE7166HierarchicalParentId("1") // CPS020 7166 Hierarchical parent id.; 1=the package tree's root; Description: Identification number of the next higher hierarchical data segment in a hierarchical structure.
			)
							.setSegmentGroup11(ImmutableList.<SegmentGroup11> of(
									new SegmentGroup11()
											.setPACPackage(
													new PACPackage()
															.setE7224NumberOfPackages(BigDecimal.ONE) // PAC010 7224 Number of packages; always 1 because we now iterate each package
															// Note that we skip the following, since they are optional as of now
															// PAC020 C531 PACKAGING DETAILS; Description: Packaging level and details, terms and conditions.
															// PAC020-020 7233 Packaging related information, coded; 52=Package barcoded UCC or EAN-128; Description: Code giving packaging, handling and marking related information.
															.setC202PackageType(
																	new C202PackageType() // PAC030 C202 PACKAGE TYPE
																			.setE7065TypeOfPackagesIdentification(e7065TypeOfPackagesIdentification)) // PAC030-010 7065 Type of package identification

			) // end of PACPackage
 // Note that we skip the following physical dimensions:
 // MEA+PD+LN length
 // MEA+PD+WD width
 // MEA+PD+HT height
 // MEA+PD+AAB unit gross weight
 // For now we also skip Segment Group 12 "HAN - HANDLING INSTRUCTIONS" (e.g. "EAT"=food or "HWC"=handle with care)
											.setSegmentGroup13(ImmutableList.of(
													new SegmentGroup13()
															.setPCIPackageIdentification(
																	new PCIPackageIdentification() // PCI PACKAGE IDENTIFICATION (SSCC)
																			.setE4233MarkingInstructionsCoded("33E")) // PCI010 4233 Marking instructions, coded; 33E=Serial Shipping Container Code (SSCC); Description: Code indicating instructions on how specified packages or physical units should be marked.
															.setSegmentGroup14(ImmutableList.<SegmentGroup14> of(
																	new SegmentGroup14()
																			.setGINGoodsIdentityNumber( // 1280 GIN GOODS IDENTITY NUMBER (SSCC)
																					new GINGoodsIdentityNumber()
																							.setE7405IdentityNumberQualifier("BJ") // GIN010 7405 Identity number qualifier; BJ=Serial shipping container code; Description: Code specifying the type/source of identity number.
																							.setC2081IdentityNumberRange(new C2081IdentityNumberRange() // GIN020 C208 IDENTITY NUMBER RANGE
																									.setE74022IdentityNumber(xmlDesadvLine.getIPASSCC18()) // GIN020-010 7402 Identity number
			)))) // end of Segment group 14
			)))) // end of segment group 11
							.setSegmentGroup15(ImmutableList.of(
									new SegmentGroup15()
											.setLINLineItem(
													new LINLineItem() // 1310 LIN LINE ITEM
															.setE1082LineItemNumber(new BigDecimal(xmlDesadvLine.getLine())) // LIN010 1082 Line item number
															.setC212ItemNumberIdentification(
																	new C212ItemNumberIdentification() // LIN030 C212 ITEM NUMBER IDENTIFICATION
																			.setE7140ItemNumber(xmlDesadvLine.getUPC()) // LIN030-010 7140 Item number
																			.setE7143ItemNumberTypeCoded("EN") // LIN030-020 7143 Item number type, coded; EN=EAN, UP=UPC, SRV=GTIN
			)) // end of LIN - LINE ITEM
 // Note that we skip the "PIA - ADDITIONAL PRODUCT ID" segment. I think it's OK since we provided the EAN
											.setQTYQuantity(ImmutableList.of(
													new QTYQuantity() // QTY QUANTITY
															.setC186QuantityDetails(
																	new C186QuantityDetails() // QTY010 C186 QUANTITY DETAILS; Description: Quantity information in a transaction, qualified when relevant.
																			.setE6063QuantityQualifier("12") // QTY010-010 6063 Quantity qualifier; 12=Despatch quantity; Description: Code giving specific meaning to a quantity.
																			.setE6060Quantity(xmlDesadvLine.getQtyDeliveredInUOM())) // QTY010-020 6060 Quantity

			)) // end of QTY - QUANTITY
											.setSegmentGroup16(ImmutableList.of(
													new SegmentGroup16()
															.setRFFReference(
																	new RFFReference() // 1440 RFF REFERENCE
																			.setC506Reference(
																					new C506Reference() // RFF010 C506 REFERENCE
																							.setE1153ReferenceQualifier("ON") // RFF010-010 1153 Reference qualifier; ON=Order number (purchase)
																							.setE1154ReferenceNumber(xmlDesadv.getPOReference())) // RFF010-020 1154 Reference number
			))) // end of segment group 16
 // TODO i think we need Segment Group 20. It's optional, but it's about expiry date which is sth we need.
											.setSegmentGroup20(
													false /* !xmlDesadv.hasExpiryDate() */ ? null : ImmutableList.of(
															new SegmentGroup20()
																	.setPCIPackageIdentification(
																			new PCIPackageIdentification() // 1590 PCI PACKAGE IDENTIFICATION
																					// TODO the "38E" is just a guess. we might need to get this value from EDI_DesadvLine; possible values are
																					// 10=Mark batch number
																					// 13=Mark date of production
																					// 14=Mark expiry date
																					// 17=Seller's instructions
																					// 36E=Marked with batch number (EAN Code)
																					// 37E=Marked with production/manufacturing date (EAN Code)
																					// 38E=Marked with expiry date (EAN Code)
																					// 39E=Marked with best before date (EAN Code)
																					.setE4233MarkingInstructionsCoded("38E")) // PCI010 4233 Marking instructions, coded; Description: Code indicating instructions on how specified packages or physical units should be marked.
																	.setDTMDateTimePeriod(ImmutableList.of(
																			new DTMDateTimePeriod() // 1600 DTM DATE/TIME/PERIOD
																					.setC507DateTimePeriod(
																							new C507DateTimePeriod() // DTM010 C507 DATE/TIME/PERIOD
																									// TODO: as with setE4233MarkingInstructionsCoded(), this is just a guess for now
																									.setE2005DateTimePeriodQualifier("36") // DTM010-010 2005 Date/time/period qualifier; 36=Expiry date, 94=Production/manufacture date, 361=Best before date;Description: Code giving specific meaning to a date, time or period.
																									// TODO: get the date from EDI_DesadvLine
																									.setE2380DateTimePeriod(null) // DTM010-020 2380 Date/time/period
																									.setE2379DateTimePeriodFormatQualifier("102")) // DTM010-030 2379 Date/time/period format qualifier; 102=CCYYMMDD
			)) // end of setDTMDateTimePeriod()
																	.setSegmentGroup21(ImmutableList.of(
																			new SegmentGroup21()
																					.setGINGoodsIdentityNumber(
																							new GINGoodsIdentityNumber() // 1640 GIN GOODS IDENTITY NUMBER
																									.setE7405IdentityNumberQualifier("BX") // GIN010 7405 Identity number qualifier; BX=Batch number; Description: Code specifying the type/source of identity number.
																									.setC2081IdentityNumberRange(
																											new C2081IdentityNumberRange() // GIN020 C208 IDENTITY NUMBER RANGE; Description: Goods item identification numbers, start and end of consecutively numbered range.
																													// TODO: get a batch number from EDI_DesadvLine
																													// Note from specs:
																													// The batch or lot number associates an item with information the manufacturer considers relevant for traceability
																													// of the trade item to which the Element String is applied. The data may refer to the trade item itself or to items contained.
																													.setE74021IdentityNumber(null))) // GIN020-010 7402 Identity number
			)) // end of setSegmentGroup21()
			)) // end of setSegmentGroup20()
			)) // end of setSegmentGroup15()
			); // end of segmentgroup10s.add()

			segmentCounter.add(1 /*CPS*/ + 1 /*PAC*/ + 1 /*PCI*/ + 1 /*GIN-BJ*/ + 1 /*LIN*/ + 1 /*QTY*/ + 1 /*RFF*/);

			if (false /* !xmlDesadv.hasExpiryDate() */)
			{
				segmentCounter.add(1 /*PCI*/ + 1 /*DTM*/ + 1 /*GIN-BX*/);
			}
		}

		return desadv;
	}
}
