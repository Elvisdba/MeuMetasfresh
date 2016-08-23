-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EDISenderIdentification', Description='EDI-GLN des Absenders', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.', Name='Absender-GLN', PrintName='Absender-GLN',Updated=TO_TIMESTAMP('2016-08-23 11:37:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541984
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541984
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDISenderIdentification', Name='Absender-GLN', Description='EDI-GLN des Absenders', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Element_ID=541984
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDISenderIdentification', Name='Absender-GLN', Description='EDI-GLN des Absenders', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.', AD_Element_ID=541984 WHERE UPPER(ColumnName)='EDISENDERIDENTIFICATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDISenderIdentification', Name='Absender-GLN', Description='EDI-GLN des Absenders', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Element_ID=541984 AND IsCentrallyMaintained='Y'
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Absender-GLN', Description='EDI-GLN des Absenders', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541984) AND IsCentrallyMaintained='Y'
;

-- 23.08.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Absender-GLN', Name='Absender-GLN' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541984)
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EDIReceiverIdentification', Description='EDI-GLN des Empfängers', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.', Name='Empfänger-GLN', PrintName='Empfänger-GLN',Updated=TO_TIMESTAMP('2016-08-23 11:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541983
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541983
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDIReceiverIdentification', Name='Empfänger-GLN', Description='EDI-GLN des Empfängers', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Element_ID=541983
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDIReceiverIdentification', Name='Empfänger-GLN', Description='EDI-GLN des Empfängers', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.', AD_Element_ID=541983 WHERE UPPER(ColumnName)='EDIRECEIVERIDENTIFICATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDIReceiverIdentification', Name='Empfänger-GLN', Description='EDI-GLN des Empfängers', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Element_ID=541983 AND IsCentrallyMaintained='Y'
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger-GLN', Description='EDI-GLN des Empfängers', Help='Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541983) AND IsCentrallyMaintained='Y'
;

-- 23.08.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Empfänger-GLN', Name='Empfänger-GLN' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541983)
;

-- 23.08.2016 11:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555009,541984,0,10,540644,'N','EDISenderIdentification',TO_TIMESTAMP('2016-08-23 11:39:42','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Absenders','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Absender-GLN',0,TO_TIMESTAMP('2016-08-23 11:39:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 23.08.2016 11:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555009 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 23.08.2016 11:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-08-23 11:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555009
;

-- 23.08.2016 11:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555010,541983,0,10,540644,'N','EDIReceiverIdentification',TO_TIMESTAMP('2016-08-23 11:40:03','YYYY-MM-DD HH24:MI:SS'),100,'N','EDI-GLN des Empfängers','de.metas.esb.edi',14,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Empfänger-GLN',0,TO_TIMESTAMP('2016-08-23 11:40:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 23.08.2016 11:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555010 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 23.08.2016 11:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-08-23 11:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555010
;

--
-- fields
--
-- 23.08.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555009,557196,0,540662,0,TO_TIMESTAMP('2016-08-23 11:51:34','YYYY-MM-DD HH24:MI:SS'),100,'EDI-GLN des Absenders',0,'de.metas.esb.edi','Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.',0,'Y','Y','Y','Y','N','N','N','Y','N','Absender-GLN',33,43,0,1,1,TO_TIMESTAMP('2016-08-23 11:51:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.08.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557196 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 23.08.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555010,557197,0,540662,0,TO_TIMESTAMP('2016-08-23 11:51:56','YYYY-MM-DD HH24:MI:SS'),100,'EDI-GLN des Empfängers',0,'de.metas.esb.edi','Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Empfänger-GLN',35,45,0,1,1,TO_TIMESTAMP('2016-08-23 11:51:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.08.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557197 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;




--
-- DDL
--
COMMIT;

-- 23.08.2016 11:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Desadv ADD EDISenderIdentification VARCHAR(14) DEFAULT NULL 
;

-- 23.08.2016 11:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_Desadv ADD EDIReceiverIdentification VARCHAR(14) DEFAULT NULL 
;

