

CREATE OR REPLACE FUNCTION "de.metas.edi".get_edi_gln(
    p_CBPartner_ID numeric,
    P_date date)
  RETURNS character varying AS
$BODY$

SELECT c.EDIRecipientGLN
FROM EDI_BPartner_Config c
	-- anti-left-join an older config that is still after p_date
	LEFT JOIN EDI_BPartner_Config c2 ON c2.C_BPartner_ID=c.C_BPartner_ID AND c2.IsActive='Y' AND c2.IsEdiRecipient='Y' AND c2.ValidFrom<=$2 AND c2.ValidFrom<c.ValidFrom 
WHERE true
	AND c.C_BPartner_ID=$1 
	AND c.IsActive='Y' 
	AND c.IsEdiRecipient='Y' 
	AND c.ValidFrom<=$2
	AND c2.EDI_BPartner_Config_ID IS NULL

$BODY$
	LANGUAGE sql STABLE;
COMMENT ON FUNCTION "de.metas.edi".get_edi_gln(numeric, date) IS 'Selects the EDI particpant identifier to a given party and date.
see https://github.com/metasfresh/metasfresh/issues/307';

DROP VIEW IF EXISTS edi_cctop_000_v;
CREATE OR REPLACE VIEW edi_cctop_000_v AS 
SELECT 
	i.C_Invoice_ID,
	i.C_Invoice_ID AS edi_cctop_000_v_id, 
	"de.metas.edi".get_edi_gln(i.C_BPartner_ID, i.DateOrdered::date) AS EdiReceiverIdentification, 
	"de.metas.edi".get_edi_gln(bp_sender.C_BPartner_ID, i.DateOrdered::date) AS EdiSenderIdentification, 
	i.AD_Client_ID, 
	i.AD_Org_ID, 
	i.Created, 
	i.CreatedBy, 
	i.Updated, 
	i.Updatedby, 
	i.IsActive
 FROM C_Invoice i
	INNER JOIN C_BPartner bp_sender ON bp_sender.AD_OrgBP_ID=i.AD_Org_ID
;
COMMENT ON VIEW edi_cctop_000_v IS 'The view calls "de.metas.edi".get_edi_gln to return the EdiPartnerIdentification (EDI GLN Identifier) of both the invoic''s recipient and sender.
The returned GLNs are the ones which are valid right now().
As of task https://github.com/metasfresh/metasfresh/issues/307 this view is not based on C_BPartner_Location andmove, but directly on C_Invoice.';

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;
CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT
	i.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID
	, i.C_Invoice_ID
	, i.C_Order_ID
	, i.DocumentNo AS Invoice_DocumentNo
	, i.DateInvoiced
	, (CASE
		WHEN i.POReference::TEXT <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
			THEN i.POReference
		ELSE NULL::CHARACTER VARYING
	END) AS POReference
	, (CASE
		WHEN i.DateOrdered IS NOT NULL /* task 09182: if there is an orderDate, then export it */
			THEN i.DateOrdered
		ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
	END) AS DateOrdered
	, (CASE dt.DocBaseType
		WHEN 'ARI'::BPChar THEN (CASE
			WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='' THEN '380'::TEXT
			WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AQ' THEN '383'::TEXT
			WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AP' THEN '84'::TEXT
			ELSE 'ERROR EAN_DocType'::TEXT
		END)
		WHEN 'ARC'::BPChar THEN (CASE
		
			/* CQ => "GS - Lieferdifferenz"; CS => "GS - Retoure" */
			WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ','CS') THEN '381'
			WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='CR' THEN '83'::TEXT
			ELSE 'ERROR EAN_DocType'::TEXT
		END)
		ELSE 'ERROR EAN_DocType'::TEXT
	END) AS EANCOM_DocType
	, i.GrandTotal
	, i.TotalLines
	/* IF docSubType is CS, the we don't reference the original shipment*/
	, CASE WHEN dt.DocSubType='CS' THEN NULL ELSE s.MovementDate END AS MovementDate
	, CASE WHEN dt.DocSubType='CS' THEN NULL ELSE s.DocumentNo END AS Shipment_DocumentNo
	, t.TotalVAT
	, t.TotalTaxBaseAmt
	, sp.VATaxId
	, c.ISO_Code
	, i.CreditMemoReason
	, (
		SELECT
			Name
		FROM AD_Ref_List
		WHERE AD_Reference_ID=540014 -- C_CreditMemo_Reason
		AND Value=i.CreditMemoReason
	) AS CreditMemoReasonText
	, cc.CountryCode
	, cc.CountryCode_3Digit
	, cc.CountryCode as AD_Language
	, i.AD_Client_ID , i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
FROM C_Invoice i
LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
LEFT JOIN C_Order o ON o.C_Order_ID=i.C_Order_ID
LEFT JOIN EDI_Desadv s ON s.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we don't use the M_InOut, but the desadv. there might be multiple InOuts, all with the same POReference and the same desadv_id
LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
LEFT JOIN (
	SELECT
		C_InvoiceTax.C_Invoice_ID
		, SUM(C_InvoiceTax.TaxAmt) AS TotalVAT
		, SUM(C_InvoiceTax.TaxBaseAmt) AS TotalTaxBaseAmt
	FROM C_InvoiceTax
	GROUP BY C_InvoiceTax.C_Invoice_ID
) t ON t.C_Invoice_ID = i.C_Invoice_ID
LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
;
