-- 30.08.2016 11:54
-- URL zum Konzept
INSERT INTO t_alter_column values('edi_ordrspline','QuantityQualifier','VARCHAR(5)',null,'10_IA')
;

-- 30.08.2016 11:54
-- URL zum Konzept
UPDATE EDI_OrdrspLine SET QuantityQualifier='10_IA' WHERE QuantityQualifier IS NULL
;

-- 30.08.2016 12:34
-- URL zum Konzept
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2016-08-30 12:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554901
;

-- 30.08.2016 12:48
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2016-08-30 12:48:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540749
;

-- 30.08.2016 12:48
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2016-08-30 12:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540750
;

-- 30.08.2016 13:10
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540720,'Y','N',TO_TIMESTAMP('2016-08-30 13:10:54','YYYY-MM-DD HH24:MI:SS'),100,'Erstellt zu einer gegebenen zusagbaren Position deren zusagbare Menge geringer als die bestellte Menge ist eine weitere Position.','de.metas.esb.edi','Y','N','N','N','N','N','N','Y',0,'Manuelle Bestätigungszeile erstellen','N','Y',0,0,'Java',TO_TIMESTAMP('2016-08-30 13:10:54','YYYY-MM-DD HH24:MI:SS'),100,'EDI_OrdrspLine_CreateManualLine')
;

-- 30.08.2016 13:10
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540720 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 30.08.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543144,0,540720,541011,17,540663,540345,'QuantityQualifier',TO_TIMESTAMP('2016-08-30 13:12:40','YYYY-MM-DD HH24:MI:SS'),100,'20_IB','Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.','de.metas.esb.edi',0,'Y','N','Y','N','Y','N','Mengenkennzeichner',10,TO_TIMESTAMP('2016-08-30 13:12:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.08.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541011 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 30.08.2016 13:12
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.edi.process.EDI_OrdrspLine_CreateManualLine',Updated=TO_TIMESTAMP('2016-08-30 13:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540720
;

-- 30.08.2016 13:13
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540720,540777,TO_TIMESTAMP('2016-08-30 13:13:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2016-08-30 13:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.08.2016 13:46
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541011
;

-- 30.08.2016 13:46
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541011
;

-- 30.08.2016 13:48
-- URL zum Konzept
UPDATE AD_Process SET Description='Erstellt  eine weitere Position zu einer gegebenen zusagbaren Position, deren zusagbare Menge geringer als die bestellte Menge ist.', Help='Die neu erstellte Position kann editiert werden, um festzulegen, ob und wann die noch fehlenden Mengen nachgeliefert werden.',Updated=TO_TIMESTAMP('2016-08-30 13:48:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540720
;

-- 30.08.2016 13:48
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540720
;

-- 30.08.2016 13:53
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-08-30 13:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540720
;

-- 30.08.2016 13:53
-- URL zum Konzept
UPDATE AD_Process SET IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2016-08-30 13:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540720
;

-- 30.08.2016 13:54
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-08-30 13:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557130
;

-- 30.08.2016 14:11
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540345,Updated=TO_TIMESTAMP('2016-08-30 14:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554896
;

-- 30.08.2016 14:19
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(@IsManual@=''N'' AND AD_Ref_List.Value=''10_IA'') OR (@IsManual@=''Y'' AND AD_Ref_List.Value!=''10_IA'')
AND NOT EXISTS (
	select 1 from EDI_OrdrspLine l 
	where l.EDI_Ordrsp_ID=@EDI_Ordrsp_ID@ and l.Line=@Line@ and l.EDI_OrdrspLine_ID<@EDI_OrdrspLine_ID@ and l.QuantityQualifier=AD_Ref_List.Value
)', Description='Allows only 10_IA if IsManual=N and only the other items'' values if IsManual=''Y''. Also does not allow qualifiers that are already used by siblings.', Type='S',Updated=TO_TIMESTAMP('2016-08-30 14:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 30.08.2016 14:22
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(''@IsManual@''=''N'' AND AD_Ref_List.Value=''10_IA'') OR (''@IsManual@''=''Y'' AND AD_Ref_List.Value!=''10_IA'')
AND NOT EXISTS (
	select 1 from EDI_OrdrspLine l 
	where l.EDI_Ordrsp_ID=@EDI_Ordrsp_ID@ and l.Line=@Line@ and l.EDI_OrdrspLine_ID<@EDI_OrdrspLine_ID@ and l.QuantityQualifier=AD_Ref_List.Value
)',Updated=TO_TIMESTAMP('2016-08-30 14:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 30.08.2016 14:24
-- URL zum Konzept
UPDATE AD_Val_Rule SET Description='Allows only ''10_IA'' if IsManual=N and only the other items'' values if IsManual=Y. Also, don''t not allow qualifiers that are already used by siblings.',Updated=TO_TIMESTAMP('2016-08-30 14:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 30.08.2016 14:27
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(''@IsManual@''=''N'' AND AD_Ref_List.Value=''10_IA'') OR (''@IsManual@''=''Y'' AND AD_Ref_List.Value!=''10_IA'')
AND NOT EXISTS (
	select 1 from EDI_OrdrspLine l 
	where l.EDI_Ordrsp_ID=@EDI_Ordrsp_ID@ and l.Line=@Line@ and l.EDI_OrdrspLine_ID!=@EDI_OrdrspLine_ID@ and l.QuantityQualifier=AD_Ref_List.Value
)',Updated=TO_TIMESTAMP('2016-08-30 14:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 30.08.2016 14:30
-- URL zum Konzept
UPDATE EXP_Format SET Description='Exports EDI_OrderSpLines with ConfirmedQty > 0', WhereClause='EDI_OrdrspLine.ConfirmedQty > 0',Updated=TO_TIMESTAMP('2016-08-30 14:30:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540413
;

-- 30.08.2016 15:07
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select Percent from "de.metas.edi".EDI_Ordrsp_QtyConfirmed_Percentage_V v where v.EDI_Ordrsp_ID=EDI_Ordrsp.EDI_Ordrsp_ID)',Updated=TO_TIMESTAMP('2016-08-30 15:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554881
;

