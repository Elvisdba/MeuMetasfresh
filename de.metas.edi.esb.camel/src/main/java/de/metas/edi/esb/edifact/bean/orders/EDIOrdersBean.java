package de.metas.edi.esb.edifact.bean.orders;

import static de.metas.edi.esb.commons.Util.resolveGenericLookup;
import static de.metas.edi.esb.commons.ValidationHelper.validateBigIntegerString;
import static de.metas.edi.esb.commons.ValidationHelper.validateCalendarString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.RuntimeCamelException;
import org.milyn.edi.unedifact.d96a.ORDERS.Orders;
import org.milyn.edi.unedifact.d96a.ORDERS.SegmentGroup1;
import org.milyn.edi.unedifact.d96a.ORDERS.SegmentGroup2;
import org.milyn.edi.unedifact.d96a.ORDERS.SegmentGroup25;
import org.milyn.edi.unedifact.d96a.common.DTMDateTimePeriod;
import org.milyn.edi.unedifact.d96a.common.NADNameAndAddress;
import org.milyn.edi.unedifact.d96a.common.RFFReference;
import org.milyn.edi.unedifact.d96a.common.field.C082PartyIdentificationDetails;
import org.milyn.edi.unedifact.d96a.common.field.C506Reference;
import org.milyn.edi.unedifact.d96a.common.field.C507DateTimePeriod;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.compudata.pojo.desadv.H000;
import de.metas.edi.esb.compudata.route.imports.EDIOrderRoute;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.EDIImpCOLCandType;
import de.metas.edi.esb.jaxb.ObjectFactory;

public class EDIOrdersBean
{
	public static final String METHOD_createMetasfreshData = "createMetasfreshData";

	protected static final String voidString = "";

	private static final ObjectFactory factory = Constants.JAXB_ObjectFactory;

	/**
	 * <ul>
	 * <li>IN: {@link EDIExpDesadvType}</li>
	 * <li>OUT: {@link H000}</li>
	 * </ul>
	 *
	 * @param exchange
	 */
	public final List<EDIImpCOLCandType> createMetasfreshData(final Orders orders,
			@ExchangeProperty(value = Exchange.FILE_NAME) final String CamelFileName,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_ADClientValue) final String ADClientValue,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName) final String ADInputDataDestination_InternalName,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_ADInputDataSourceID) final BigInteger ADInputDataSourceID,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_ADUserEnteredByID) final BigInteger ADUserEnteredByID,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_DELIVERY_RULE) final String DeliveryRule,
			@ExchangeProperty(value = EDIOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE) final String DeliveryViaRule)
	{
		final List<EDIImpCOLCandType> result = new ArrayList<EDIImpCOLCandType>();

		for (final SegmentGroup25 line : orders.getSegmentGroup25())
		{
			final EDIImpCOLCandType olCand = factory.createEDIImpCOLCandType();

			// set the exchange properties
			olCand.setADClientValueAttr(ADClientValue);

			final EDIImpADInputDataSourceLookupINType dataDestinationLookup = resolveGenericLookup(EDIImpADInputDataSourceLookupINType.class,
					Constants.LOOKUP_TEMPLATE_InternalName.createMandatoryValueLookup(ADInputDataDestination_InternalName));
			olCand.setADDataDestinationID(dataDestinationLookup);

			olCand.setADInputDataSourceID(ADInputDataSourceID);
			olCand.setADUserEnteredByID(ADUserEnteredByID);

			// TODO: decide if deliveryRule and deliveryViaRule shall be deducted from the ORDERS instance.

			setHeaderData(orders, olCand);

			final String e7140ItemNumber = line.getLINLineItem().getC212ItemNumberIdentification().getE7140ItemNumber();
			validateBigIntegerString(e7140ItemNumber, "Unable to parse element 'LIN030-010 7140 Item number' from string " + e7140ItemNumber);

			olCand.setLine(new BigInteger(e7140ItemNumber));

			result.add(olCand);
		}

		return result;
	}

	/**
	 * Sets all the fields of the given <code>olCand</code> that are line detail/line specific
	 *
	 * @param orders
	 * @param olCand
	 */
	private void setHeaderData(Orders orders, EDIImpCOLCandType olCand)
	{
		{
			final String e1004DocumentMessageNumber = orders.getBGMBeginningOfMessage().getE1004DocumentMessageNumber(); // BGM020 1004 Document/message number => POreference
			olCand.setPOReference(e1004DocumentMessageNumber);
		}                // BGM - BEGINNING OF MESSAGE

		for (final DTMDateTimePeriod dtm : orders.getDTMDateTimePeriod())
		{
			final C507DateTimePeriod c507DateTimePeriod = dtm.getC507DateTimePeriod(); // DTM010 C507 DATE/TIME/PERIOD
			final String e2005DateTimePeriodQualifier = c507DateTimePeriod.getE2005DateTimePeriodQualifier(); // DTM010-010 2005 Date/time/period qualifier

			final String e2380DateTimePeriod = c507DateTimePeriod.getE2380DateTimePeriod();

			// according to the spec, this is always 102 = CCYYMMDD; Description: Calendar date: C = Century ; Y = Year ; M = Month ; D = Day.
			// we just get it for diag purposes
			final String e2379DateTimePeriodFormatQualifier = c507DateTimePeriod.getE2379DateTimePeriodFormatQualifier(); // DTM010-030 2379 Date/time/period format qualifier

			final XMLGregorianCalendar date = validateCalendarString(e2380DateTimePeriod, "yyyyMMdd", "Unable to parse element 'DTM010-020 2380 Date/time/period' from string " + e2380DateTimePeriod + " with 'DTM010-030 2379 Date/time/period format qualifier'=" + e2379DateTimePeriodFormatQualifier);

			// TODO implement https://github.com/metasfresh/metasfresh/issues/364
			if ("63".equals(e2005DateTimePeriodQualifier))                     // 63 = Delivery date/time, latest; Description: Date identifying a point of time after which goods shall not or will not be delivered.
			{
				// olCand.setDatePromisedTo(date)
			}
			else if ("64".equals(e2005DateTimePeriodQualifier))                     // 64 = Delivery date/time, earliest; Description: Date identifying a point in time before which the goods shall not be delivered.
			{
				// olCand.setDatePromisedFrom(date)
			}
		}                  // DTM - DATE/TIME/PERIOD

		for (final SegmentGroup1 segmentGroup1 : orders.getSegmentGroup1())
		{
			final RFFReference rffReference = segmentGroup1.getRFFReference(); // RFF010 C506 REFERENCE
			final C506Reference c506Reference = rffReference.getC506Reference(); // RFF010 C506 REFERENCE
			final String e1153ReferenceQualifier = c506Reference.getE1153ReferenceQualifier(); // RFF010-010 1153 Reference qualifier
			final String e1154ReferenceNumber = c506Reference.getE1154ReferenceNumber(); // RFF010-020 1154 Reference number

			if ("ADE".equals(e1153ReferenceQualifier))              // ADE Account number; #363: carries the backorder rule
			{
				// TODO: implement https://github.com/metasfresh/metasfresh/issues/363

				if ("firstOrder".equalsIgnoreCase(e1154ReferenceNumber))
				{
					// olCand.setBackOrderRule("firstOrder");
				}
				else if ("backorder".equalsIgnoreCase(e1154ReferenceNumber))
				{
					// olCand.setBackOrderRule("backorder");
				}
				else if ("novelty".equalsIgnoreCase(e1154ReferenceNumber))
				{
					// olCand.setBackOrderRule("novelty");
				}
			}
			else if ("PD".equals(e1153ReferenceQualifier))           // PD = Promotion deal number; Description: Number assigned by a vendor to a special promotion activity.
			{
				// TODO: find out what it means and if we need to implement it in this round
			}
			else if ("CR".equals(e1153ReferenceQualifier))           // CR Customer reference number; Description: Reference number assigned by the customer to a transaction.
			{
				// TODO: find out what it means and if we need to implement it in this round
			}
		}        // RFF - REFERENCE

		final Map<String, NADNameAndAddress> qualifier2gln = new HashMap<>();

		for (final SegmentGroup2 segmentGroup2 : orders.getSegmentGroup2())
		{
			final NADNameAndAddress nadNameAndAddress = segmentGroup2.getNADNameAndAddress();

			final String e3035PartyQualifier = nadNameAndAddress.getE3035PartyQualifier(); // NAD010 3035 Party qualifier; Description: Code giving specific meaning to a party.
			final C082PartyIdentificationDetails c082PartyIdentificationDetails = nadNameAndAddress.getC082PartyIdentificationDetails(); // NAD020 C082 PARTY IDENTIFICATION DETAILS; Description: Identification of a transaction party by code.
			final String e3055CodeListResponsibleAgencyCoded = c082PartyIdentificationDetails.getE3055CodeListResponsibleAgencyCoded(); // NAD020-030 3055 Code list responsible agency, coded; Description: Code identifying the agency responsible for a code list.
			assumeValue(e3055CodeListResponsibleAgencyCoded, "9", "NAD020-030 3055 Code list responsible agency, coded"); // 9 = EAN (International Article Numbering association)

			qualifier2gln.put(e3035PartyQualifier, nadNameAndAddress);
		}
		final NADNameAndAddress buyerNAD = qualifier2gln.get("BY");   // BY = Buyer; Description: (3002) Party to which merchandise is sold.
		final NADNameAndAddress supplierNAD = qualifier2gln.get("SU");   // SU = Supplier; Description: (3280) Party which manufactures or otherwise has possession of goods,and consigns or makes them available in trade.
		final NADNameAndAddress deliveryNAD = qualifier2gln.get("DP");   // DP = Delivery party; Description: (3144) Party to which goods should be delivered, if not identical with
		final NADNameAndAddress invoiceeNAD = qualifier2gln.get("IV");   // IV = Invoicee; Description: (3006) Party to whom an invoice is issued.

		// TODO: create and set values to olcand (have a look at the compudata bean, maybe extract the code into a common method)

		// NAD - NAME AND ADDRESS
	}

	private void assumeValue(String actual, String expected, String fieldIdentifier)
	{
		if (!expected.equals(actual))
		{
			throw new RuntimeCamelException("Value of field '" + fieldIdentifier + "' should be '" + expected + "' but is '" + actual + "'");
		}
	}

	private void assumeNotEmpty(String actual, String fieldIdentifier)
	{
		if (Util.isEmpty(actual))
		{
			throw new RuntimeCamelException("Value of field '" + fieldIdentifier + "' is empty or null");
		}
	}

}
