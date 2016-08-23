

UPDATE EDI_Desadv d_outer
SET Updated=now(), UpdatedBy=99,
	EdiReceiverIdentification=d.EdiReceiverIdentification,
	EdiSenderIdentification=d.EdiSenderIdentification
FROM (
SELECT
	d.EDI_Desadv_ID,
	d.MovementDate,
	d.C_BPartner_ID,
	"de.metas.edi".get_edi_gln(d.C_BPartner_ID, d.MovementDate::date) AS EdiReceiverIdentification, 
	bp_sender.C_BPartner_ID,
	"de.metas.edi".get_edi_gln(bp_sender.C_BPartner_ID, d.MovementDate::date) AS EdiSenderIdentification
FROM
	EDI_Desadv d
	INNER JOIN C_BPartner bp_sender ON bp_sender.AD_OrgBP_ID=d.AD_Org_ID
) d
WHERE d.EDI_Desadv_ID=d_outer.EDI_Desadv_ID AND (d_outer.EdiReceiverIdentification IS NULL OR d_outer.EdiSenderIdentification IS NULL)
;


UPDATE EDI_Desadv d
SET 
	EdiReceiverIdentification='<NOT SET>',
	Updated=now(),
	UpdatedBy=99
WHERE
	EdiReceiverIdentification IS NULL;

UPDATE EDI_Desadv d
SET 
	EdiSenderIdentification='<NOT SET>',
	Updated=now(),
	UpdatedBy=99
WHERE
	EdiSenderIdentification IS NULL;

-- DDL: Now we can chagne the columns to not-null
COMMIT;
	
ALTER TABLE edi_desadv ALTER COLUMN EdiSenderIdentification SET NOT NULL;
ALTER TABLE edi_desadv ALTER COLUMN EdiReceiverIdentification SET NOT NULL;
