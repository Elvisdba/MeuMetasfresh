package de.metas.edi.esb.edifact.bean.orders;

import static de.metas.edi.esb.commons.Util.resolveGenericLookup;
import static de.metas.edi.esb.commons.ValidationHelper.validateBigIntegerString;
import static de.metas.edi.esb.commons.ValidationHelper.validateCalendarString;

import java.math.BigDecimal;
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
import org.milyn.edi.unedifact.d96a.ORDERS.SegmentGroup7;
import org.milyn.edi.unedifact.d96a.common.CUXCurrencies;
import org.milyn.edi.unedifact.d96a.common.DTMDateTimePeriod;
import org.milyn.edi.unedifact.d96a.common.LINLineItem;
import org.milyn.edi.unedifact.d96a.common.NADNameAndAddress;
import org.milyn.edi.unedifact.d96a.common.RFFReference;
import org.milyn.edi.unedifact.d96a.common.field.C082PartyIdentificationDetails;
import org.milyn.edi.unedifact.d96a.common.field.C212ItemNumberIdentification;
import org.milyn.edi.unedifact.d96a.common.field.C5041CurrencyDetails;
import org.milyn.edi.unedifact.d96a.common.field.C506Reference;
import org.milyn.edi.unedifact.d96a.common.field.C507DateTimePeriod;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.orders.OLCandUtils;
import de.metas.edi.esb.compudata.pojo.desadv.H000;
import de.metas.edi.esb.compudata.route.imports.EDIOrderRoute;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.EDIImpCOLCandType;
import de.metas.edi.esb.jaxb.EDIMProductLookupUPCVType;
import de.metas.edi.esb.jaxb.ObjectFactory;

public class EdifactOrdersImportBean
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

			setLineData(line, olCand);

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
		} // DTM - DATE/TIME/PERIOD

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

		final Map<String, NADNameAndAddress> qualifier2nad = new HashMap<>();
		final Map<String, String> qualifier2gln = new HashMap<>();

		for (final SegmentGroup2 segmentGroup2 : orders.getSegmentGroup2())
		{
			final NADNameAndAddress nadNameAndAddress = segmentGroup2.getNADNameAndAddress();

			final String e3035PartyQualifier = nadNameAndAddress.getE3035PartyQualifier(); // NAD010 3035 Party qualifier; Description: Code giving specific meaning to a party.
			final C082PartyIdentificationDetails c082PartyIdentificationDetails = nadNameAndAddress.getC082PartyIdentificationDetails(); // NAD020 C082 PARTY IDENTIFICATION DETAILS; Description: Identification of a transaction party by code.

			final String e3039PartyIdIdentification = c082PartyIdentificationDetails.getE3039PartyIdIdentification(); // NAD020-010 3039 Party id. identification
			final String e3055CodeListResponsibleAgencyCoded = c082PartyIdentificationDetails.getE3055CodeListResponsibleAgencyCoded(); // NAD020-030 3055 Code list responsible agency, coded; Description: Code identifying the agency responsible for a code list.
			assumeValue(e3055CodeListResponsibleAgencyCoded, "9", "NAD020-030 3055 Code list responsible agency, coded"); // 9 = EAN (International Article Numbering association)

			qualifier2nad.put(e3035PartyQualifier, nadNameAndAddress);
			qualifier2gln.put(e3035PartyQualifier, e3039PartyIdIdentification);
		}

		// TODO: see "Q5" at https://metasfresh.atlassian.net/wiki/display/FRESHteam/le87+-+LETS+GmbH+Projektbericht#le87-LETSGmbHProjektbericht-OffeneFragenzuEDI
		final NADNameAndAddress buyerNAD = qualifier2nad.get("BY");   // BY = Buyer; Description: (3002) Party to which merchandise is sold.
		final NADNameAndAddress supplierNAD = qualifier2nad.get("SU");   // SU = Supplier; Description: (3280) Party which manufactures or otherwise has possession of goods,and consigns or makes them available in trade.
		final NADNameAndAddress deliveryNAD = qualifier2nad.get("DP");   // DP = Delivery party; Description: (3144) Party to which goods should be delivered, if not identical with
		final NADNameAndAddress invoiceeNAD = qualifier2nad.get("IV");   // IV = Invoicee; Description: (3006) Party to whom an invoice is issued.

		OLCandUtils.setBartnerAndLocationFields(olCand,
				qualifier2gln.get("BY"),
				qualifier2gln.get("DP"),
				qualifier2gln.get("IV"),
				Util.coalesce(qualifier2gln.get("SN"), qualifier2gln.get("UC"))); // the amazon spec mentions neither SN nor UC, but compudata specifies "StoreNumber" field which is identified with "EAN Verkaufstelle (SN/UC)".
		// NAD - NAME AND ADDRESS

		// about
		// RFF - REFERENCE (VAT-ID): see Q6 at https://metasfresh.atlassian.net/wiki/display/FRESHteam/le87+-+LETS+GmbH+Projektbericht#le87-LETSGmbHProjektbericht-OffeneFragenzuEDI
		// note that the RFF is probably contained in segmentGroup2.getSegmentGroup3().get(0).getRFFReference();

		final List<SegmentGroup7> segmentGroup7 = orders.getSegmentGroup7();
		if(segmentGroup7.size() != 1)
		{
			throw new RuntimeCamelException("We expect SegmentGroup7 to be contained only once");
		}
		final CUXCurrencies cuxCurrencies = segmentGroup7.get(0).getCUXCurrencies(); // CUX CURRENCIES
		final C5041CurrencyDetails c5041CurrencyDetails = cuxCurrencies.getC5041CurrencyDetails(); // CUX010 C504 CURRENCY DETAILS
		final String e6347CurrencyDetailsQualifier = c5041CurrencyDetails.getE6347CurrencyDetailsQualifier(); // CUX010-010 6347 Currency details qualifier
		assumeValue(e6347CurrencyDetailsQualifier, "2", "CUX010-010 6347 Currency details qualifier"); // 2 = Reference currency; Description: The currency applicable to amounts stated. It may have to be converted.
		final String e6345CurrencyCoded = c5041CurrencyDetails.getE6345CurrencyCoded(); // CUX010-020 6345 Currency, coded;
		final String e6343CurrencyQualifier = c5041CurrencyDetails.getE6343CurrencyQualifier(); // CUX010-030 6343 Currency qualifier; Description: Code giving specific meaning to data element 6345 Currency.
		assumeValue(e6343CurrencyQualifier, "9", "CUX010-030 6343 Currency qualifier"); // 9 = Order currency; Description: The name or symbol of the monetary unit used in an order.

		final EDIImpCCurrencyLookupISOCodeType currencyLookup = resolveGenericLookup(EDIImpCCurrencyLookupISOCodeType.class,
				Constants.LOOKUP_TEMPLATE_ISOCode.createMandatoryValueLookup(e6345CurrencyCoded));
		olCand.setCCurrencyID(currencyLookup);
		// CUX - CURRENCIES

		// now comes "Segment Group 25" with the individual order lines.

		// orders.getUNSSectionControl();
		// about
		// UNS - SECTION CONTROL: there is nothing for us to import

		// orders.getCNTControlTotal();
		// about
		// CNT - CONTROL TOTAL: there is nothing to import. maybe we should still validate it to check the ORDERS's integrety
	}

	private void setLineData(SegmentGroup25 segmentGroup25, EDIImpCOLCandType olCand)
	{
		final LINLineItem linLineItem = segmentGroup25.getLINLineItem();
		final BigDecimal e1082LineItemNumber = linLineItem.getE1082LineItemNumber(); // LIN010 1082 Line item number
		olCand.setLine(e1082LineItemNumber.toBigInteger());

		final C212ItemNumberIdentification c212ItemNumberIdentification = linLineItem.getC212ItemNumberIdentification(); // LIN030 C212 ITEM NUMBER IDENTIFICATION
		final String e7140ItemNumber = c212ItemNumberIdentification.getE7140ItemNumber(); // LIN030-010 7140 Item number
		final EDIMProductLookupUPCVType productLookupUPCVType = factory.createEDIMProductLookupUPCVType();
		productLookupUPCVType.setGLN(olCand.getCBPartnerID().getGLN()); // need the bpartner's GLN for a save lookup!!
		productLookupUPCVType.setUPC(e7140ItemNumber);
		olCand.setMProductID(productLookupUPCVType);

		// next LIN030-020 7143 Item number type, coded

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
