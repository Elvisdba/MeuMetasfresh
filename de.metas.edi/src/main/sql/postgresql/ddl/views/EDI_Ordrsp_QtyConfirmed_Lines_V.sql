
CREATE OR REPLACE VIEW "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Lines_V AS
SELECT 
	EDI_Ordrsp_ID, 
	Line, 
	COALESCE(max(QtyEntered),0) AS QtyEntered, 
	COALESCE(sum(ConfirmedQty),0) AS ConfirmedQty
FROM EDI_OrdrspLine
WHERE IsActive='Y'
GROUP BY EDI_Ordrsp_ID, Line;
COMMENT ON VIEW "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Lines_V IS '
This view is used in the view EDI_Ordrsp_QtyConfirmed_Percentage_V and also for the color logic of the column EDI_Ordrsp.DocumentNo.
Note that all EDI_OrdrspLines with the same EDI_Ordrsp_ID and line have the same QtyEntered, because they belong to the same C_OrderLine.
See issue https://github.com/metasfresh/metasfresh/issues/237 for the bigger picture';
