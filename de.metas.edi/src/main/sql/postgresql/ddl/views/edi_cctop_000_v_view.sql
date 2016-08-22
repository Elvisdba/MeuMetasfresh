--DROP VIEW IF EXISTS edi_cctop_000_v;
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
