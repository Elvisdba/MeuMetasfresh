-- 05.09.2016 12:35
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540721,'Y','de.metas.edi.process.EDI_Desadv_Create_From_C_Order','N',TO_TIMESTAMP('2016-09-05 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','N','N','N','N','N','N','Y',0,'DESADV zum Auftrag erstellen','N','Y',0,0,'Java',TO_TIMESTAMP('2016-09-05 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Create_From_C_Order')
;

-- 05.09.2016 12:35
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540721 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 05.09.2016 12:35
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N', IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2016-09-05 12:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540721
;

-- 05.09.2016 12:35
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540721,259,TO_TIMESTAMP('2016-09-05 12:35:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2016-09-05 12:35:50','YYYY-MM-DD HH24:MI:SS'),100)
;

