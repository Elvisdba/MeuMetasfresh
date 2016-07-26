

--
-- making EDI_Desadv.Processed mandatory
--
-- 26.07.2016 10:12
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-07-26 10:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551752
;


-- 26.07.2016 10:12
-- URL zum Konzept
UPDATE EDI_Desadv SET Processed='N' WHERE Processed IS NULL
;

--
-- EDI_Desadv.EDI_ExportStatus
--
-- 26.07.2016 10:08
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-07-26 10:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551732
;

--
--
-- DDL
--
--

-- 26.07.2016 10:12
-- URL zum Konzept
INSERT INTO t_alter_column values('edi_desadv','Processed','CHAR(1)',null,'N')
;

-- 26.07.2016 10:12
-- URL zum Konzept
INSERT INTO t_alter_column values('edi_desadv','Processed',null,'NOT NULL',null)
;
