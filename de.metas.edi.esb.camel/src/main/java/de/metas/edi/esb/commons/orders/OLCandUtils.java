package de.metas.edi.esb.commons.orders;

import static de.metas.edi.esb.commons.Util.resolveGenericLookup;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDICBPartnerLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.EDIImpCBPartnerLocationLookupGLNType;
import de.metas.edi.esb.jaxb.EDIImpCOLCandType;

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

public class OLCandUtils
{

	/**
	 * Creates the lookups for
	 * <ul>
	 * <li>C_BPartner and C_BPartner_Location
	 * <li>HandOver_BPartner and HandOver_Location
	 * <li>DropShip_BPartner and DropShip_Location
	 * <li>Bill_BPartner and Bill_Location
	 * </ul>
	 *
	 * @param olcand
	 * @param buyerGLN party qualifier BY; used to lookup C_BPartner*
	 * @param deliveryGLN party qualifier DP; used to lookup HandOver* and (only if <code>storeNumberGLN</code> is empty!) DropShip*
	 * @param invoiceGLN party qualifier IV; used to lookup Bill*
	 * @param storeNumberGLN optional, can be empty; if not empty, then it is
	 *            <ul>
	 *            <li>used as <code>StoreGLN</code> lookup value for each of the four BPartners, see the view and Exp_Format for <code>EDI_C_BPartner_Lookup_BPL_GLN_V</code> for details.
	 *            <li>also used to lookup DropShip*
	 *            </ul>
	 */
	public static void setBartnerAndLocationFields(final EDIImpCOLCandType olcand,
			final String buyerGLN,
			final String deliveryGLN,
			final String invoiceGLN,
			final String storeNumberGLN)
	{
		// use this as the shipto-Address of the buyer
		final String lookupStoreGLN = Util.coalesce(storeNumberGLN); // this might be null

		// BuyerID - BY = Main GLN of C_BPartner and C_BPartner_Location
		// use buyerGLN and shiptoGLN to identify the partner
		{
			final EDICBPartnerLookupBPLGLNVType buyerBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(buyerGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(lookupStoreGLN));
			olcand.setCBPartnerID(buyerBPartner);

			final EDIImpCBPartnerLocationLookupGLNType buyerLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(buyerGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(buyerBPartner));
			olcand.setCBPartnerLocationID(buyerLocation);
		}

		// handover location - use deliveryGLN
		{
			final EDICBPartnerLookupBPLGLNVType handOverBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(deliveryGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(lookupStoreGLN));
			olcand.setHandOverPartnerID(handOverBPartner);

			final EDIImpCBPartnerLocationLookupGLNType deliveryLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(deliveryGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(handOverBPartner));
			olcand.setHandOverLocationID(deliveryLocation);
		}

		// DropShip_BPartner and DropShip_Location
		// use storeNumberGLN if we have one, or deliveryGLN else
		final String shipToLocation = Util.coalesce(storeNumberGLN, deliveryGLN);
		{
			final EDICBPartnerLookupBPLGLNVType shipToBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(shipToLocation),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createMandatoryValueLookup(shipToLocation));
			olcand.setDropShipBPartnerID(shipToBPartner);

			final EDIImpCBPartnerLocationLookupGLNType storeLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(shipToLocation),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(shipToBPartner));
			olcand.setDropShipLocationID(storeLocation);
		}

		// InvoiceID - IV = GLN of Bill_BPartner and Bill_Location
		{
			final EDICBPartnerLookupBPLGLNVType invoiceBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(invoiceGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(lookupStoreGLN));
			olcand.setBillBPartnerID(invoiceBPartner);

			final EDIImpCBPartnerLocationLookupGLNType invoiceLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(invoiceGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(invoiceBPartner));
			olcand.setBillLocationID(invoiceLocation);
		}
	}

}
