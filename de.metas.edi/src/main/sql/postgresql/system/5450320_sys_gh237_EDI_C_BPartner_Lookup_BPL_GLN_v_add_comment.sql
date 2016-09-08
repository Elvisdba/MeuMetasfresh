-- View: EDI_C_BPartner_Lookup_BPL_GLN_v

DROP VIEW IF EXISTS EDI_C_BPartner_Lookup_BPL_GLN_v;

CREATE OR REPLACE VIEW EDI_C_BPartner_Lookup_BPL_GLN_v AS
SELECT 
	*
FROM
(
	-- support the lookup with ("<Main-GLN>", "<StoreNumber-GLN>") by selecting all locations' GLNs of a BPArtner and left-joining all shipTo-locations' GLNs of the same BPartner
	SELECT
		bp.C_BPartner_ID,
		bp.IsActive,
		--
		-- Note: The GLN is unique per BPL.
		-- We're just filtering by two locations here and will add them to the whereclause in the EXP_Format_Line filter.
		--
		l_main.GLN AS GLN, -- The Selector's GLN
		l_store.GLN AS StoreGLN -- The Store's GLN
	FROM C_BPartner bp
		-- Many-to-many
		LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID=bp.C_BPartner_ID 
						AND l_main.IsActive='Y'
		LEFT JOIN C_BPartner_Location l_store ON l_store.C_BPartner_ID=bp.C_BPartner_ID
						AND l_store.IsActive='Y'
						AND l_store.GLN IS NOT NULL 
						AND TRIM(BOTH ' ' FROM l_store.GLN) != ''
						AND l_store.IsShipTo='Y' --- without this, the case of two partners sharing the same location-GLN still wouldn't work
	
	-- support the lookup with ("<Main-GLN>", null) by also selecting jsut the "main" GLN with null in the second column
	UNION 
	SELECT
		bp.C_BPartner_ID,
		bp.IsActive,
		l_main.GLN AS GLN, -- The Selector's GLN
		NULL AS StoreGLN -- The Store's GLN
	FROM C_BPartner bp
		-- Many-to-many with NULL StoreGLN
		LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID=bp.C_BPartner_ID
						AND l_main.IsActive='Y'
) master
WHERE 
	master.GLN IS NOT NULL 
	AND TRIM(BOTH ' ' FROM master.GLN) != ''
GROUP BY 
	C_BPartner_ID, IsActive, GLN, StoreGLN;
COMMENT ON VIEW EDI_C_BPartner_Lookup_BPL_GLN_V IS '
This view supports the lookup of a BPartner via its C_BPArtner_Locations'' GLNs. the lookup is supposed to take place with a 2-tuple:
* either with ("<Main-GLN>", "<StoreNumber-GLN>") by selecting all locations'' GLNs of a BPartner and left-joining all ShipTo-locations'' GLNs of the same BPartner (cartesian product)
* or with ("<Main-GLN>", NULL) by also selecting jsut the "main" GLN with NULL in the second column

The view is this complicated because we have the case of multiple BPartners which share one headquarter and each one has at least one individual store.
Those different BPartners all have BillTo-C_BPartnerLocation with same GLN, but for each store, each BPartner also all has a Shipto-C_BPartnerLocation with an individual GLN.
In order to look up each individual BPartner we can use the tuple of ("<Main-GLN>", "<StoreNumber-GLN>").
';
