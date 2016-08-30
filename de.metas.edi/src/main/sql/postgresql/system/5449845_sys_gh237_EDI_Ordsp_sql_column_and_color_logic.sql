

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



-- 30.08.2016 15:38
-- URL zum Konzept
UPDATE AD_Field SET ColorLogic='SELECT   ( 		
 	CASE 
 		WHEN x.QtyEntered_Sum = x.ConfirmedQty_Sum AND x.Qty0 = 0 AND ''@EDI_ExportStatus@'' != ''S''
 		THEN  (select ad_color_id from ad_color where name = ''Gruen'')
 		WHEN x.ConfirmedQty_Sum = 0 AND ''@EDI_ExportStatus@'' != ''S''
 		THEN (select ad_color_id from ad_color where name = ''Rot'')  		
 		WHEN ''@EDI_ExportStatus@'' != ''S'' 			
 		THEN (select ad_color_id from ad_color where name = ''Orange'')  		
 		ELSE -1  		
 	END 	) as ad_color_id 
 FROM  ( 		 
 	SELECT 
		e.ConfirmedQty_Sum, 
		e.QtyEntered_Sum,
		(select count(l.Line) from "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Lines_V l where COALESCE(ConfirmedQty,0)=0 and l.EDI_Ordrsp_ID=e.EDI_Ordrsp_ID) as Qty0
 	FROM "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Percentage_V e
 	WHERE e.EDI_Ordrsp_ID = @EDI_Ordrsp_ID@
 ) x',Updated=TO_TIMESTAMP('2016-08-30 15:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557115
;

