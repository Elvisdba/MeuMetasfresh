
-- 23.08.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555011,541984,0,10,540776,'N','EDISenderIdentification',TO_TIMESTAMP('2016-08-23 11:53:14','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Absenders','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Absender-GLN',0,TO_TIMESTAMP('2016-08-23 11:53:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 23.08.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555011 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 23.08.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555012,541983,0,10,540776,'N','EDIReceiverIdentification',TO_TIMESTAMP('2016-08-23 11:53:59','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Empfängers','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Empfänger-GLN',0,TO_TIMESTAMP('2016-08-23 11:53:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 23.08.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555012 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

--
-- DDL
--
COMMIT;
-- 23.08.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Ordrsp ADD EDISenderIdentification VARCHAR(14) NOT NULL
;

-- 23.08.2016 11:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Ordrsp ADD EDIReceiverIdentification VARCHAR(14) NOT NULL
;

