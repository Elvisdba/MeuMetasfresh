-- 2017-07-20T13:34:15.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2017-07-20 13:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541195
;

-- 2017-07-20T13:38:40.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542492,0,540807,541197,29,'QtyCU',TO_TIMESTAMP('2017-07-20 13:38:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Menge CU',20,TO_TIMESTAMP('2017-07-20 13:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T13:38:40.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541197 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-07-20T14:55:07.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2017-07-20 14:55:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541195
;

-- 2017-07-20T22:46:40.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540361,'(select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=@M_HU_PI_Version_ID@)=''LU''',TO_TIMESTAMP('2017-07-20 22:46:40','YYYY-MM-DD HH24:MI:SS'),100,'validation rule to filter for M_HUs that are LUs','de.metas.handlingunits','Y','M_HU_LU','S',TO_TIMESTAMP('2017-07-20 22:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T22:48:06.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540361,Updated=TO_TIMESTAMP('2017-07-20 22:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 2017-07-20T22:50:52.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=M_HU.M_HU_PI_Version_ID)=''LU''',Updated=TO_TIMESTAMP('2017-07-20 22:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540361
;

-- 2017-07-20T22:53:53.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540362,'(select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=M_HU.M_HU_PI_Version_ID)=''TU''',TO_TIMESTAMP('2017-07-20 22:53:53','YYYY-MM-DD HH24:MI:SS'),100,'validation rule to filter for M_HUs that are TUs','de.metas.handlingunits','Y','M_HU_TU','S',TO_TIMESTAMP('2017-07-20 22:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T22:54:12.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540361,Updated=TO_TIMESTAMP('2017-07-20 22:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 2017-07-20T22:55:32.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540362,Updated=TO_TIMESTAMP('2017-07-20 22:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 2017-07-20T22:58:29.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2017-07-20 22:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 2017-07-20T22:58:42.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2017-07-20 22:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

