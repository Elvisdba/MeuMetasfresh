
CREATE OR REPLACE VIEW "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Percentage_V AS
SELECT 
	l.EDI_Ordrsp_ID, 
	SUM (l.ConfirmedQty) AS ConfirmedQty_Sum,
	SUM (l.QtyEntered) AS QtyEntered_Sum,
	CASE WHEN SUM (l.ConfirmedQty) = 0 THEN NULL ELSE round ((SUM (l.ConfirmedQty)/SUM (l.QtyEntered) ) * 100, 2) END AS percent
FROM "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Lines_V l
GROUP BY EDI_Ordrsp_ID;
COMMENT ON VIEW "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Percentage_V IS '
This view is used for the SQL-column EDI_Ordrsp.SumPercentage for the color logic of the column EDI_Ordrsp.DocumentNo.
See issue https://github.com/metasfresh/metasfresh/issues/237 for the bigger picture';
