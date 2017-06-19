-- 2017-06-19T00:26:15.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540358,'EXISTS ( select 1 from WEBUI_Dashboard_Access a where a.WEBUI_Dashboard_ID=WEBUI_Dashboard.WEBUI_Dashboard_ID and a.IsActive=''Y'' and (a.IsAllUsers=''Y'' or a.AD_User_ID=@AD_User_ID/-1@) )',TO_TIMESTAMP('2017-06-19 00:26:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','WEBUI_Dashboard_ForUser','S',TO_TIMESTAMP('2017-06-19 00:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-19T00:26:44.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,CacheInvalidateParent,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsLocalPK,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556970,543217,0,19,114,540358,'N','N','WEBUI_Dashboard_ID',TO_TIMESTAMP('2017-06-19 00:26:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ui.web',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Dashboard',0,TO_TIMESTAMP('2017-06-19 00:26:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-06-19T00:26:44.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556970 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-06-19T00:27:00.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ad_user','ALTER TABLE public.AD_User ADD COLUMN WEBUI_Dashboard_ID NUMERIC(10)')
;

-- 2017-06-19T00:27:01.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_User ADD CONSTRAINT WEBUIDashboard_ADUser FOREIGN KEY (WEBUI_Dashboard_ID) REFERENCES public.WEBUI_Dashboard DEFERRABLE INITIALLY DEFERRED
;

-- 2017-06-19T00:27:17.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-06-19 00:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556970
;

