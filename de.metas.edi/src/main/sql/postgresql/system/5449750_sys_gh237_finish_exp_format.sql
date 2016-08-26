
-- 26.08.2016 18:44
-- URL zum Konzept
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540413, Name='EDI_Exp_OrdrspLine', Value='EDI_Exp_OrdrspLine',Updated=TO_TIMESTAMP('2016-08-26 18:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550196
;

-- 26.08.2016 18:52
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555010,0,TO_TIMESTAMP('2016-08-26 18:52:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550200,'Y','Y','N','EDIReceiverIdentification',260,'E',TO_TIMESTAMP('2016-08-26 18:52:04','YYYY-MM-DD HH24:MI:SS'),100,'EDIReceiverIdentification')
;

-- 26.08.2016 18:52
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555009,0,TO_TIMESTAMP('2016-08-26 18:52:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550201,'Y','Y','N','EDISenderIdentification',270,'E',TO_TIMESTAMP('2016-08-26 18:52:21','YYYY-MM-DD HH24:MI:SS'),100,'EDISenderIdentification')
;

-- 26.08.2016 18:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555017,541984,0,10,540464,'N','EDISenderIdentification',TO_TIMESTAMP('2016-08-26 18:57:46','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Absenders','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Absender-GLN',0,TO_TIMESTAMP('2016-08-26 18:57:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 26.08.2016 18:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555017 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.08.2016 18:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555018,541983,0,10,540464,'N','EDIReceiverIdentification',TO_TIMESTAMP('2016-08-26 18:57:56','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Empfängers','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Empfänger-GLN',0,TO_TIMESTAMP('2016-08-26 18:57:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 26.08.2016 18:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555018 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.08.2016 18:58
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=545134
;

-- 26.08.2016 18:58
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555017,0,TO_TIMESTAMP('2016-08-26 18:58:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540222,550202,'Y','N','N','EDISenderIdentification',30,'E',TO_TIMESTAMP('2016-08-26 18:58:32','YYYY-MM-DD HH24:MI:SS'),100,'EDISenderIdentification')
;

-- 26.08.2016 18:58
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555018,0,TO_TIMESTAMP('2016-08-26 18:58:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540222,550203,'Y','N','N','EDIReceiverIdentification',40,'E',TO_TIMESTAMP('2016-08-26 18:58:44','YYYY-MM-DD HH24:MI:SS'),100,'EDIReceiverIdentification')
;

-- 26.08.2016 18:58
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548360
;

-- 26.08.2016 18:58
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=548360
;

-- 26.08.2016 19:02
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=545132
;

-- 26.08.2016 19:02
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548359
;

-- 26.08.2016 19:02
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=548359
;

-- 26.08.2016 19:03
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555019,1008,0,30,540464,'N','C_Invoice_ID',TO_TIMESTAMP('2016-08-26 19:03:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','de.metas.esb.edi',10,'The Invoice Document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Rechnung',0,TO_TIMESTAMP('2016-08-26 19:03:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 26.08.2016 19:03
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555019 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.08.2016 19:04
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555019,0,TO_TIMESTAMP('2016-08-26 19:04:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540222,540222,550204,'Y','N','N','C_Invoice_ID',50,'E',TO_TIMESTAMP('2016-08-26 19:04:34','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ID')
;

-- 26.08.2016 19:05
-- URL zum Konzept
UPDATE EXP_FormatLine SET AD_Column_ID=548325,Updated=TO_TIMESTAMP('2016-08-26 19:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545181
;

-- 26.08.2016 19:05
-- URL zum Konzept
UPDATE EXP_FormatLine SET Description='EDI_cctop_000_v via C_Invoice_ID',Updated=TO_TIMESTAMP('2016-08-26 19:05:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545181
;

-- 26.08.2016 19:06
-- URL zum Konzept
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-08-26 19:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545181
;

