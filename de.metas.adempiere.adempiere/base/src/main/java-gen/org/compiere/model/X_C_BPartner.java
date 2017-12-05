/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner extends org.compiere.model.PO implements I_C_BPartner, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1310693204L;

    /** Standard Constructor */
    public X_C_BPartner (Properties ctx, int C_BPartner_ID, String trxName)
    {
      super (ctx, C_BPartner_ID, trxName);
      /** if (C_BPartner_ID == 0)
        {
			setAllowConsolidateInOut (true); // Y
			setC_BP_Group_ID (0);
			setC_BPartner_ID (0);
			setIsCreateDefaultPOReference (false); // N
			setIsCustomer (false);
			setIsEmployee (false);
			setIsOneTime (false);
			setIsPOTaxExempt (false); // N
			setIsProspect (false);
			setIsSalesRep (false);
			setIsSEPASigned (false); // N
			setIsSummary (false);
			setIsVendor (false);
			setName (null);
			setPaymentRule (null); // P
			setPaymentRulePO (null); // P
			setSendEMail (false);
			setSO_CreditLimit (BigDecimal.ZERO);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_BPartner (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Akquisitionskosten.
		@param AcqusitionCost 
		The cost of gaining the prospect as a customer
	  */
	@Override
	public void setAcqusitionCost (java.math.BigDecimal AcqusitionCost)
	{
		set_Value (COLUMNNAME_AcqusitionCost, AcqusitionCost);
	}

	/** Get Akquisitionskosten.
		@return The cost of gaining the prospect as a customer
	  */
	@Override
	public java.math.BigDecimal getAcqusitionCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AcqusitionCost);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgBP() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgBP_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgBP(org.compiere.model.I_AD_Org AD_OrgBP)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgBP_ID, org.compiere.model.I_AD_Org.class, AD_OrgBP);
	}

	/** Set Linked Organization.
		@param AD_OrgBP_ID 
		The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	@Override
	public void setAD_OrgBP_ID (int AD_OrgBP_ID)
	{
		if (AD_OrgBP_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgBP_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgBP_ID, Integer.valueOf(AD_OrgBP_ID));
	}

	/** Get Linked Organization.
		@return The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	@Override
	public int getAD_OrgBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Straße und Nr..
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		throw new IllegalArgumentException ("Address1 is virtual column");	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Sammel-Lieferscheine erlaubt.
		@param AllowConsolidateInOut Sammel-Lieferscheine erlaubt	  */
	@Override
	public void setAllowConsolidateInOut (boolean AllowConsolidateInOut)
	{
		set_Value (COLUMNNAME_AllowConsolidateInOut, Boolean.valueOf(AllowConsolidateInOut));
	}

	/** Get Sammel-Lieferscheine erlaubt.
		@return Sammel-Lieferscheine erlaubt	  */
	@Override
	public boolean isAllowConsolidateInOut () 
	{
		Object oo = get_Value(COLUMNNAME_AllowConsolidateInOut);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Partner Parent.
		@param BPartner_Parent_ID 
		Business Partner Parent
	  */
	@Override
	public void setBPartner_Parent_ID (int BPartner_Parent_ID)
	{
		if (BPartner_Parent_ID < 1) 
			set_Value (COLUMNNAME_BPartner_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_BPartner_Parent_ID, Integer.valueOf(BPartner_Parent_ID));
	}

	/** Get Partner Parent.
		@return Business Partner Parent
	  */
	@Override
	public int getBPartner_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BPartner_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Business Partner Group
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	/** Set Mahnung.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	@Override
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Mahnung.
		@return Dunning Rules for overdue invoices
	  */
	@Override
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class);
	}

	@Override
	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting)
	{
		set_ValueFromPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class, C_Greeting);
	}

	/** Set Anrede.
		@param C_Greeting_ID 
		Greeting to print on correspondence
	  */
	@Override
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Anrede.
		@return Greeting to print on correspondence
	  */
	@Override
	public int getC_Greeting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	/** Set Terminplan Rechnung.
		@param C_InvoiceSchedule_ID 
		Schedule for generating Invoices
	  */
	@Override
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Terminplan Rechnung.
		@return Schedule for generating Invoices
	  */
	@Override
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxGroup_ID, org.eevolution.model.I_C_TaxGroup.class);
	}

	@Override
	public void setC_TaxGroup(org.eevolution.model.I_C_TaxGroup C_TaxGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxGroup_ID, org.eevolution.model.I_C_TaxGroup.class, C_TaxGroup);
	}

	/** Set Tax Group.
		@param C_TaxGroup_ID Tax Group	  */
	@Override
	public void setC_TaxGroup_ID (int C_TaxGroup_ID)
	{
		if (C_TaxGroup_ID < 1) 
			set_Value (COLUMNNAME_C_TaxGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxGroup_ID, Integer.valueOf(C_TaxGroup_ID));
	}

	/** Get Tax Group.
		@return Tax Group	  */
	@Override
	public int getC_TaxGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ort.
		@param City 
		Name des Ortes
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		throw new IllegalArgumentException ("City is virtual column");	}

	/** Get Ort.
		@return Name des Ortes
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set Firmenname.
		@param CompanyName Firmenname	  */
	@Override
	public void setCompanyName (java.lang.String CompanyName)
	{
		set_Value (COLUMNNAME_CompanyName, CompanyName);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CompanyName);
	}

	/** Set Auftrag anlegen.
		@param CreateSO Auftrag anlegen	  */
	@Override
	public void setCreateSO (java.lang.String CreateSO)
	{
		set_Value (COLUMNNAME_CreateSO, CreateSO);
	}

	/** Get Auftrag anlegen.
		@return Auftrag anlegen	  */
	@Override
	public java.lang.String getCreateSO () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateSO);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** Mit nächster Abolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	/** Set Lieferart.
		@param DeliveryRule 
		Defines the timing of Delivery
	  */
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Lieferart.
		@return Defines the timing of Delivery
	  */
	@Override
	public java.lang.String getDeliveryRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Kopien.
		@param DocumentCopies 
		Number of copies to be printed
	  */
	@Override
	public void setDocumentCopies (int DocumentCopies)
	{
		set_Value (COLUMNNAME_DocumentCopies, Integer.valueOf(DocumentCopies));
	}

	/** Get Kopien.
		@return Number of copies to be printed
	  */
	@Override
	public int getDocumentCopies () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentCopies);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Grace Date.
		@param DunningGrace Dunning Grace Date	  */
	@Override
	public void setDunningGrace (java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace Date.
		@return Dunning Grace Date	  */
	@Override
	public java.sql.Timestamp getDunningGrace () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningGrace);
	}

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	@Override
	public void setDUNS (java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	@Override
	public java.lang.String getDUNS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DUNS);
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		throw new IllegalArgumentException ("EMail is virtual column");	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Vorname.
		@param Firstname 
		Vorname
	  */
	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	/** Get Vorname.
		@return Vorname
	  */
	@Override
	public java.lang.String getFirstname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	/** Set Erster Verkauf.
		@param FirstSale 
		Date of First Sale
	  */
	@Override
	public void setFirstSale (java.sql.Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	/** Get Erster Verkauf.
		@return Date of First Sale
	  */
	@Override
	public java.sql.Timestamp getFirstSale () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_FirstSale);
	}

	/** Set Fester Rabatt %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	@Override
	public void setFlatDiscount (java.math.BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Fester Rabatt %.
		@return Flat discount percentage 
	  */
	@Override
	public java.math.BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	/** Set Frachtkostenberechnung.
		@param FreightCostRule 
		Method for charging Freight
	  */
	@Override
	public void setFreightCostRule (java.lang.String FreightCostRule)
	{

		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	/** Get Frachtkostenberechnung.
		@return Method for charging Freight
	  */
	@Override
	public java.lang.String getFreightCostRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FreightCostRule);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Invoice_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setInvoice_PrintFormat(org.compiere.model.I_AD_PrintFormat Invoice_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_Invoice_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, Invoice_PrintFormat);
	}

	/** Set Druckformat Rechnung.
		@param Invoice_PrintFormat_ID 
		Print Format for printing Invoices
	  */
	@Override
	public void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID)
	{
		if (Invoice_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, Integer.valueOf(Invoice_PrintFormat_ID));
	}

	/** Get Druckformat Rechnung.
		@return Print Format for printing Invoices
	  */
	@Override
	public int getInvoice_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Invoice_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** Nach Lieferung Auftrag = O */
	public static final String INVOICERULE_NachLieferungAuftrag = "O";
	/** Nach Lieferung = D */
	public static final String INVOICERULE_NachLieferung = "D";
	/** Kundenintervall (nach Lieferung) = S */
	public static final String INVOICERULE_KundenintervallNachLieferung = "S";
	/** Sofort = I */
	public static final String INVOICERULE_Sofort = "I";
	/** Set Rechnungsstellung.
		@param InvoiceRule 
		"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public void setInvoiceRule (java.lang.String InvoiceRule)
	{

		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	/** Get Rechnungsstellung.
		@return "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public java.lang.String getInvoiceRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRule);
	}

	/** Set Firma.
		@param IsCompany Firma	  */
	@Override
	public void setIsCompany (boolean IsCompany)
	{
		set_Value (COLUMNNAME_IsCompany, Boolean.valueOf(IsCompany));
	}

	/** Get Firma.
		@return Firma	  */
	@Override
	public boolean isCompany () 
	{
		Object oo = get_Value(COLUMNNAME_IsCompany);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Autom. Referenz.
		@param IsCreateDefaultPOReference 
		Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	  */
	@Override
	public void setIsCreateDefaultPOReference (boolean IsCreateDefaultPOReference)
	{
		set_Value (COLUMNNAME_IsCreateDefaultPOReference, Boolean.valueOf(IsCreateDefaultPOReference));
	}

	/** Get Autom. Referenz.
		@return Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	  */
	@Override
	public boolean isCreateDefaultPOReference () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreateDefaultPOReference);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kunde.
		@param IsCustomer 
		Indicates if this Business Partner is a Customer
	  */
	@Override
	public void setIsCustomer (boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Kunde.
		@return Indicates if this Business Partner is a Customer
	  */
	@Override
	public boolean isCustomer () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rabatte drucken.
		@param IsDiscountPrinted 
		Print Discount on Invoice and Order
	  */
	@Override
	public void setIsDiscountPrinted (boolean IsDiscountPrinted)
	{
		set_Value (COLUMNNAME_IsDiscountPrinted, Boolean.valueOf(IsDiscountPrinted));
	}

	/** Get Rabatte drucken.
		@return Print Discount on Invoice and Order
	  */
	@Override
	public boolean isDiscountPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mitarbeiter.
		@param IsEmployee 
		Indicates if  this Business Partner is an employee
	  */
	@Override
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Mitarbeiter.
		@return Indicates if  this Business Partner is an employee
	  */
	@Override
	public boolean isEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set One time transaction.
		@param IsOneTime One time transaction	  */
	@Override
	public void setIsOneTime (boolean IsOneTime)
	{
		set_Value (COLUMNNAME_IsOneTime, Boolean.valueOf(IsOneTime));
	}

	/** Get One time transaction.
		@return One time transaction	  */
	@Override
	public boolean isOneTime () 
	{
		Object oo = get_Value(COLUMNNAME_IsOneTime);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PO Tax exempt.
		@param IsPOTaxExempt 
		Business partner is exempt from tax on purchases
	  */
	@Override
	public void setIsPOTaxExempt (boolean IsPOTaxExempt)
	{
		set_Value (COLUMNNAME_IsPOTaxExempt, Boolean.valueOf(IsPOTaxExempt));
	}

	/** Get PO Tax exempt.
		@return Business partner is exempt from tax on purchases
	  */
	@Override
	public boolean isPOTaxExempt () 
	{
		Object oo = get_Value(COLUMNNAME_IsPOTaxExempt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Aktiver Interessent/Kunde.
		@param IsProspect 
		Indicates this is a Prospect
	  */
	@Override
	public void setIsProspect (boolean IsProspect)
	{
		set_Value (COLUMNNAME_IsProspect, Boolean.valueOf(IsProspect));
	}

	/** Get Aktiver Interessent/Kunde.
		@return Indicates this is a Prospect
	  */
	@Override
	public boolean isProspect () 
	{
		Object oo = get_Value(COLUMNNAME_IsProspect);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vertriebsbeauftragter.
		@param IsSalesRep 
		Indicates if  the business partner is a sales representative or company agent
	  */
	@Override
	public void setIsSalesRep (boolean IsSalesRep)
	{
		set_Value (COLUMNNAME_IsSalesRep, Boolean.valueOf(IsSalesRep));
	}

	/** Get Vertriebsbeauftragter.
		@return Indicates if  the business partner is a sales representative or company agent
	  */
	@Override
	public boolean isSalesRep () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesRep);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SEPA Signed.
		@param IsSEPASigned SEPA Signed	  */
	@Override
	public void setIsSEPASigned (boolean IsSEPASigned)
	{
		set_Value (COLUMNNAME_IsSEPASigned, Boolean.valueOf(IsSEPASigned));
	}

	/** Get SEPA Signed.
		@return SEPA Signed	  */
	@Override
	public boolean isSEPASigned () 
	{
		Object oo = get_Value(COLUMNNAME_IsSEPASigned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Shipping Notification Email.
		@param IsShippingNotificationEmail Shipping Notification Email	  */
	@Override
	public void setIsShippingNotificationEmail (boolean IsShippingNotificationEmail)
	{
		set_Value (COLUMNNAME_IsShippingNotificationEmail, Boolean.valueOf(IsShippingNotificationEmail));
	}

	/** Get Shipping Notification Email.
		@return Shipping Notification Email	  */
	@Override
	public boolean isShippingNotificationEmail () 
	{
		Object oo = get_Value(COLUMNNAME_IsShippingNotificationEmail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zusammenfassungseintrag.
		@param IsSummary 
		This is a summary entity
	  */
	@Override
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Zusammenfassungseintrag.
		@return This is a summary entity
	  */
	@Override
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Steuerbefreit.
		@param IsTaxExempt 
		Steuersatz steuerbefreit
	  */
	@Override
	public void setIsTaxExempt (boolean IsTaxExempt)
	{
		set_Value (COLUMNNAME_IsTaxExempt, Boolean.valueOf(IsTaxExempt));
	}

	/** Get Steuerbefreit.
		@return Steuersatz steuerbefreit
	  */
	@Override
	public boolean isTaxExempt () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxExempt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferant.
		@param IsVendor 
		Indicates if this Business Partner is a Vendor
	  */
	@Override
	public void setIsVendor (boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/** Get Lieferant.
		@return Indicates if this Business Partner is a Vendor
	  */
	@Override
	public boolean isVendor () 
	{
		Object oo = get_Value(COLUMNNAME_IsVendor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kundencockpit_includedTab1.
		@param Kundencockpit_includedTab1 Kundencockpit_includedTab1	  */
	@Override
	public void setKundencockpit_includedTab1 (java.lang.String Kundencockpit_includedTab1)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab1, Kundencockpit_includedTab1);
	}

	/** Get Kundencockpit_includedTab1.
		@return Kundencockpit_includedTab1	  */
	@Override
	public java.lang.String getKundencockpit_includedTab1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Kundencockpit_includedTab1);
	}

	/** Set Kundencockpit_includedTab2.
		@param Kundencockpit_includedTab2 Kundencockpit_includedTab2	  */
	@Override
	public void setKundencockpit_includedTab2 (java.lang.String Kundencockpit_includedTab2)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab2, Kundencockpit_includedTab2);
	}

	/** Get Kundencockpit_includedTab2.
		@return Kundencockpit_includedTab2	  */
	@Override
	public java.lang.String getKundencockpit_includedTab2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Kundencockpit_includedTab2);
	}

	/** Set Kundencockpit_includedTab3.
		@param Kundencockpit_includedTab3 Kundencockpit_includedTab3	  */
	@Override
	public void setKundencockpit_includedTab3 (java.lang.String Kundencockpit_includedTab3)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab3, Kundencockpit_includedTab3);
	}

	/** Get Kundencockpit_includedTab3.
		@return Kundencockpit_includedTab3	  */
	@Override
	public java.lang.String getKundencockpit_includedTab3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Kundencockpit_includedTab3);
	}

	/** Set Nachname.
		@param Lastname Nachname	  */
	@Override
	public void setLastname (java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	/** Get Nachname.
		@return Nachname	  */
	@Override
	public java.lang.String getLastname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	/** Set Logo.
		@param Logo_ID Logo	  */
	@Override
	public void setLogo_ID (int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Integer.valueOf(Logo_ID));
	}

	/** Get Logo.
		@return Logo	  */
	@Override
	public int getLogo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Logo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	/** Set Rabatt Schema.
		@param M_DiscountSchema_ID 
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt Schema.
		@return Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Frachtkostenpauschale.
		@param M_FreightCost_ID Frachtkostenpauschale	  */
	@Override
	public void setM_FreightCost_ID (int M_FreightCost_ID)
	{
		if (M_FreightCost_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_ID, Integer.valueOf(M_FreightCost_ID));
	}

	/** Get Frachtkostenpauschale.
		@return Frachtkostenpauschale	  */
	@Override
	public int getM_FreightCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	/** Set MRP ausschliessen.
		@param MRP_Exclude MRP ausschliessen	  */
	@Override
	public void setMRP_Exclude (java.lang.String MRP_Exclude)
	{

		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	/** Get MRP ausschliessen.
		@return MRP ausschliessen	  */
	@Override
	public java.lang.String getMRP_Exclude () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MRP_Exclude);
	}

	/** Set NAICS/SIC.
		@param NAICS 
		Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public void setNAICS (java.lang.String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	/** Get NAICS/SIC.
		@return Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public java.lang.String getNAICS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NAICS);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name Zusatz.
		@param Name2 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name Zusatz.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Name3.
		@param Name3 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName3 (java.lang.String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	/** Get Name3.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name3);
	}

	/** Set Anzahl Beschäftigte.
		@param NumberEmployees 
		Anzahl der Mitarbeiter
	  */
	@Override
	public void setNumberEmployees (int NumberEmployees)
	{
		set_Value (COLUMNNAME_NumberEmployees, Integer.valueOf(NumberEmployees));
	}

	/** Get Anzahl Beschäftigte.
		@return Anzahl der Mitarbeiter
	  */
	@Override
	public int getNumberEmployees () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberEmployees);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRule 
		How you pay the invoice
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return How you pay the invoice
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	/** 
	 * PaymentRulePO AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULEPO_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRulePO 
		Purchase payment option
	  */
	@Override
	public void setPaymentRulePO (java.lang.String PaymentRulePO)
	{

		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	/** Get Zahlungsweise.
		@return Purchase payment option
	  */
	@Override
	public java.lang.String getPaymentRulePO () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRulePO);
	}

	/** 
	 * PO_DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int PO_DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String PO_DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String PO_DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String PO_DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param PO_DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setPO_DeliveryViaRule (java.lang.String PO_DeliveryViaRule)
	{

		set_Value (COLUMNNAME_PO_DeliveryViaRule, PO_DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getPO_DeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_DeliveryViaRule);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setPO_DiscountSchema(org.compiere.model.I_M_DiscountSchema PO_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, PO_DiscountSchema);
	}

	/** Set Einkauf Rabatt Schema.
		@param PO_DiscountSchema_ID 
		Schema to calculate the purchase trade discount percentage
	  */
	@Override
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID)
	{
		if (PO_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, Integer.valueOf(PO_DiscountSchema_ID));
	}

	/** Get Einkauf Rabatt Schema.
		@return Schema to calculate the purchase trade discount percentage
	  */
	@Override
	public int getPO_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setPO_PaymentTerm(org.compiere.model.I_C_PaymentTerm PO_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, PO_PaymentTerm);
	}

	/** Set Zahlungskondition.
		@param PO_PaymentTerm_ID 
		Payment rules for a purchase order
	  */
	@Override
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, Integer.valueOf(PO_PaymentTerm_ID));
	}

	/** Get Zahlungskondition.
		@return Payment rules for a purchase order
	  */
	@Override
	public int getPO_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getPO_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setPO_PriceList(org.compiere.model.I_M_PriceList PO_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_PO_PriceList_ID, org.compiere.model.I_M_PriceList.class, PO_PriceList);
	}

	/** Set Einkaufspreisliste.
		@param PO_PriceList_ID 
		Price List used by this Business Partner
	  */
	@Override
	public void setPO_PriceList_ID (int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, Integer.valueOf(PO_PriceList_ID));
	}

	/** Get Einkaufspreisliste.
		@return Price List used by this Business Partner
	  */
	@Override
	public int getPO_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, PO_PricingSystem);
	}

	/** Set Einkaufspreissystem.
		@param PO_PricingSystem_ID Einkaufspreissystem	  */
	@Override
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID)
	{
		if (PO_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, Integer.valueOf(PO_PricingSystem_ID));
	}

	/** Get Einkaufspreissystem.
		@return Einkaufspreissystem	  */
	@Override
	public int getPO_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Referenz Vorgabe.
		@param POReferencePattern 
		Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	  */
	@Override
	public void setPOReferencePattern (java.lang.String POReferencePattern)
	{
		set_Value (COLUMNNAME_POReferencePattern, POReferencePattern);
	}

	/** Get Referenz Vorgabe.
		@return Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	  */
	@Override
	public java.lang.String getPOReferencePattern () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReferencePattern);
	}

	/** Set PLZ.
		@param Postal 
		Postleitzahl
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		throw new IllegalArgumentException ("Postal is virtual column");	}

	/** Get PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Möglicher Gesamtertrag.
		@param PotentialLifeTimeValue 
		Total Revenue expected
	  */
	@Override
	public void setPotentialLifeTimeValue (java.math.BigDecimal PotentialLifeTimeValue)
	{
		set_Value (COLUMNNAME_PotentialLifeTimeValue, PotentialLifeTimeValue);
	}

	/** Get Möglicher Gesamtertrag.
		@return Total Revenue expected
	  */
	@Override
	public java.math.BigDecimal getPotentialLifeTimeValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PotentialLifeTimeValue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Rating.
		@param Rating 
		Classification or Importance
	  */
	@Override
	public void setRating (java.lang.String Rating)
	{
		set_Value (COLUMNNAME_Rating, Rating);
	}

	/** Get Rating.
		@return Classification or Importance
	  */
	@Override
	public java.lang.String getRating () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Rating);
	}

	/** Set Referenznummer.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Referenznummer.
		@return Your customer or vendor number at the Business Partner's site
	  */
	@Override
	public java.lang.String getReferenceNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set Wiedervorlage Datum Aussen.
		@param ReminderDateExtern Wiedervorlage Datum Aussen	  */
	@Override
	public void setReminderDateExtern (java.sql.Timestamp ReminderDateExtern)
	{
		set_Value (COLUMNNAME_ReminderDateExtern, ReminderDateExtern);
	}

	/** Get Wiedervorlage Datum Aussen.
		@return Wiedervorlage Datum Aussen	  */
	@Override
	public java.sql.Timestamp getReminderDateExtern () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ReminderDateExtern);
	}

	/** Set Wiedervorlage Datum Innen.
		@param ReminderDateIntern Wiedervorlage Datum Innen	  */
	@Override
	public void setReminderDateIntern (java.sql.Timestamp ReminderDateIntern)
	{
		set_Value (COLUMNNAME_ReminderDateIntern, ReminderDateIntern);
	}

	/** Get Wiedervorlage Datum Innen.
		@return Wiedervorlage Datum Innen	  */
	@Override
	public java.sql.Timestamp getReminderDateIntern () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ReminderDateIntern);
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/** Set Aussendienst.
		@param SalesRep_ID Aussendienst	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Aussendienst.
		@return Aussendienst	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRepIntern() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRepIntern_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRepIntern(org.compiere.model.I_AD_User SalesRepIntern)
	{
		set_ValueFromPO(COLUMNNAME_SalesRepIntern_ID, org.compiere.model.I_AD_User.class, SalesRepIntern);
	}

	/** Set Sales Responsible.
		@param SalesRepIntern_ID 
		Sales Responsible Internal
	  */
	@Override
	public void setSalesRepIntern_ID (int SalesRepIntern_ID)
	{
		if (SalesRepIntern_ID < 1) 
			set_Value (COLUMNNAME_SalesRepIntern_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRepIntern_ID, Integer.valueOf(SalesRepIntern_ID));
	}

	/** Get Sales Responsible.
		@return Sales Responsible Internal
	  */
	@Override
	public int getSalesRepIntern_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRepIntern_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verkaufsvolumen in 1.000.
		@param SalesVolume 
		Total Volume of Sales in Thousands of Currency
	  */
	@Override
	public void setSalesVolume (int SalesVolume)
	{
		set_Value (COLUMNNAME_SalesVolume, Integer.valueOf(SalesVolume));
	}

	/** Get Verkaufsvolumen in 1.000.
		@return Total Volume of Sales in Thousands of Currency
	  */
	@Override
	public int getSalesVolume () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesVolume);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set E-Mail senden.
		@param SendEMail 
		Enable sending Document EMail
	  */
	@Override
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get E-Mail senden.
		@return Enable sending Document EMail
	  */
	@Override
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Anteil.
		@param ShareOfCustomer 
		Share of Customer's business as a percentage
	  */
	@Override
	public void setShareOfCustomer (int ShareOfCustomer)
	{
		set_Value (COLUMNNAME_ShareOfCustomer, Integer.valueOf(ShareOfCustomer));
	}

	/** Get Anteil.
		@return Share of Customer's business as a percentage
	  */
	@Override
	public int getShareOfCustomer () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShareOfCustomer);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mindesthaltbarkeit %.
		@param ShelfLifeMinPct 
		Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	@Override
	public void setShelfLifeMinPct (int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, Integer.valueOf(ShelfLifeMinPct));
	}

	/** Get Mindesthaltbarkeit %.
		@return Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	@Override
	public int getShelfLifeMinPct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinPct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Short Description.
		@param ShortDescription Short Description	  */
	@Override
	public void setShortDescription (java.lang.String ShortDescription)
	{
		set_Value (COLUMNNAME_ShortDescription, ShortDescription);
	}

	/** Get Short Description.
		@return Short Description	  */
	@Override
	public java.lang.String getShortDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShortDescription);
	}

	/** Set Kreditlimit.
		@param SO_CreditLimit 
		Total outstanding invoice amounts allowed
	  */
	@Override
	public void setSO_CreditLimit (java.math.BigDecimal SO_CreditLimit)
	{
		set_Value (COLUMNNAME_SO_CreditLimit, SO_CreditLimit);
	}

	/** Get Kreditlimit.
		@return Total outstanding invoice amounts allowed
	  */
	@Override
	public java.math.BigDecimal getSO_CreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SO_CreditLimit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Beschreibung Auftrag.
		@param SO_Description 
		Description to be used on orders
	  */
	@Override
	public void setSO_Description (java.lang.String SO_Description)
	{
		set_Value (COLUMNNAME_SO_Description, SO_Description);
	}

	/** Get Beschreibung Auftrag.
		@return Description to be used on orders
	  */
	@Override
	public java.lang.String getSO_Description () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SO_Description);
	}

	@Override
	public org.compiere.model.I_C_DocType getSO_DocTypeTarget() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SO_DocTypeTarget_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setSO_DocTypeTarget(org.compiere.model.I_C_DocType SO_DocTypeTarget)
	{
		set_ValueFromPO(COLUMNNAME_SO_DocTypeTarget_ID, org.compiere.model.I_C_DocType.class, SO_DocTypeTarget);
	}

	/** Set Zielbelegart.
		@param SO_DocTypeTarget_ID 
		Zielbelegart für die Umwandlung von Dokumenten
	  */
	@Override
	public void setSO_DocTypeTarget_ID (int SO_DocTypeTarget_ID)
	{
		if (SO_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_SO_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_SO_DocTypeTarget_ID, Integer.valueOf(SO_DocTypeTarget_ID));
	}

	/** Get Zielbelegart.
		@return Zielbelegart für die Umwandlung von Dokumenten
	  */
	@Override
	public int getSO_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SO_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz Auftragsart.
		@param SO_TargetDocTypeReason Notiz Auftragsart	  */
	@Override
	public void setSO_TargetDocTypeReason (java.lang.String SO_TargetDocTypeReason)
	{
		set_Value (COLUMNNAME_SO_TargetDocTypeReason, SO_TargetDocTypeReason);
	}

	/** Get Notiz Auftragsart.
		@return Notiz Auftragsart	  */
	@Override
	public java.lang.String getSO_TargetDocTypeReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SO_TargetDocTypeReason);
	}

	/** Set Steuer-ID.
		@param TaxID 
		Tax Identification
	  */
	@Override
	public void setTaxID (java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Steuer-ID.
		@return Tax Identification
	  */
	@Override
	public java.lang.String getTaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public void setURL (java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public java.lang.String getURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_URL);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Umsatzsteuer-ID.
		@param VATaxID Umsatzsteuer-ID	  */
	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	/** Get Umsatzsteuer-ID.
		@return Umsatzsteuer-ID	  */
	@Override
	public java.lang.String getVATaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}