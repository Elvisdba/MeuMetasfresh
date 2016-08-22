--
-- https://github.com/metasfresh/metasfresh/issues/308
-- delete EDI_Exp_M_InOut
DELETE FROM EXP_FormatLine WHERE EXP_Format_ID=540383;
DELETE FROM EXP_Format WHERE EXP_Format_ID=540383;
-- delete EDI_Exp_M_InOutLine
DELETE FROM EXP_FormatLine WHERE EXP_Format_ID=540384;
DELETE FROM EXP_Format WHERE EXP_Format_ID=540384;
-- delete EDI_InOut_Feedback
DELETE FROM EXP_FormatLine WHERE EXP_Format_ID=540391;
DELETE FROM EXP_Format WHERE EXP_Format_ID=540391;

--
-- deleting M_InOut EDIErrorMsg and EDI_ExportStatus
--

-- 19.08.2016 10:31
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553215
;

-- 19.08.2016 10:31
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=553215
;

-- 19.08.2016 10:34
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553214
;

-- 19.08.2016 10:34
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=553214
;

-- 19.08.2016 10:38
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553217
;

-- 19.08.2016 10:38
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=553217
;

-- 19.08.2016 10:38
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553218
;

-- 19.08.2016 10:38
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=553218
;

-- 19.08.2016 10:39
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=549873
;

-- 19.08.2016 10:39
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=549873
;

-- 19.08.2016 10:39
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=549871
;

-- 19.08.2016 10:39
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=549871
;

--
-- EDI_M_InOut_Overdelivery_C_OrderLine_v
--

-- 19.08.2016 10:46
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=50505
;

-- 19.08.2016 10:46
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=50506
;

-- 19.08.2016 10:46
-- URL zum Konzept
DELETE FROM EXP_Format WHERE EXP_Format_ID=50026
;

-- 19.08.2016 10:47
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=53741
;

-- 19.08.2016 10:47
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=53741
;

-- further cleanup: EDI_Exp_M_IOL_HU_IPA_SSCC18_v
-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549949
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549950
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549951
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549952
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM EXP_Format WHERE EXP_Format_ID=540397
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540541
;

-- 19.08.2016 10:52
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=540541
;

DROP VIEW EDI_M_InOutLine_HU_IPA_SSCC18_v;

--
-- DDL
--
DROP view edi_m_inout_overdelivery_c_orderline_v;

-- now we can drop the columns too
ALTER TABLE M_InOut DROP COLUMN EDIErrorMsg;
ALTER TABLE M_InOut DROP COLUMN EDI_ExportStatus;
