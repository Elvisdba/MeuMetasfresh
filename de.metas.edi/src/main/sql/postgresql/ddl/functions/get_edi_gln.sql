
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
