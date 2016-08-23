
--
-- migrate from C_BPArtner to EDI_BPartner_Config
--
INSERT INTO edi_bpartner_config (
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  c_bpartner_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  edi_bpartner_config_id, -- numeric(10,0) NOT NULL,
  EdiPartnerIdentification, -- character varying(13) DEFAULT NULL::character varying,
  isactive, -- character(1) NOT NULL,
  isdesadvrecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  isedirecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  isinvoicrecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  isordrsprecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  updated, -- timestamp with time zone NOT NULL,
  updatedby, -- numeric(10,0) NOT NULL,
  validfrom, -- timestamp without time zone NOT NULL,
  edi_defaultitemcapacity -- numeric NOT NULL DEFAULT 1,
)
SELECT
  bp.ad_client_id, -- numeric(10,0) NOT NULL,
  bp.ad_org_id, -- numeric(10,0) NOT NULL,
  bp.c_bpartner_id, -- numeric(10,0) NOT NULL,
  now() AS created, -- timestamp with time zone NOT NULL,
  99 AS createdby, -- numeric(10,0) NOT NULL,
  nextval('edi_bpartner_config_seq') AS edi_bpartner_config_id, -- numeric(10,0) NOT NULL,
  bp.edirecipientgln, -- character varying(13) DEFAULT NULL::character varying,
  bp.isactive, -- character(1) NOT NULL,
  bp.HasEdiConfig AS isdesadvrecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  bp.HasEdiConfig AS isedirecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  bp.HasEdiConfig AS isinvoicrecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  'N' AS isordrsprecipient, -- character(1) NOT NULL DEFAULT 'N'::bpchar,
  now() AS updated, -- timestamp with time zone NOT NULL,
  99 AS updatedby, -- numeric(10,0) NOT NULL,
  '2010-01-01' AS validfrom, -- timestamp without time zone NOT NULL,
  bp.EdiDESADVDefaultItemCapacity AS edi_defaultitemcapacity -- numeric NOT NULL DEFAULT 1,
FROM C_BPartner bp
WHERE bp.HasEdiConfig='Y'
AND NOT EXISTS (select 1 from edi_bpartner_config c where c.c_bpartner_id=bp.c_bpartner_id)
;

COMMIT;

--
-- DDL - now drop the old columns
--
ALTER TABLE C_BPartner DROP COLUMN EdiRecipientGLN;
ALTER TABLE C_BPartner DROP COLUMN EdiDESADVDefaultItemCapacity;
