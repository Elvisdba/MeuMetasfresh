
--
-- rename the AD_Element etc
--

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='EdiPartnerIdentification', Description='Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.', Name='EDI-GLN des Partners ', PrintName='EDI-GLN des Partners ',Updated=TO_TIMESTAMP('2016-08-19 08:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542001
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EdiPartnerIdentification', Name='EDI-GLN des Partners ', Description='Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.', Help=NULL WHERE AD_Element_ID=542001
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiPartnerIdentification', Name='EDI-GLN des Partners ', Description='Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.', Help=NULL, AD_Element_ID=542001 WHERE UPPER(ColumnName)='EDIPARTNERIDENTIFICATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiPartnerIdentification', Name='EDI-GLN des Partners ', Description='Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.', Help=NULL WHERE AD_Element_ID=542001 AND IsCentrallyMaintained='Y'
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_Field SET Name='EDI-GLN des Partners ', Description='Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542001) AND IsCentrallyMaintained='Y'
;

-- 19.08.2016 08:15
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='EDI-GLN des Partners ', Name='EDI-GLN des Partners ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542001)
;

-- 22.08.2016 13:58
-- URL zum Konzept
UPDATE AD_Tab SET DisplayLogic='@IsEdiRecipient@=''Y''', ReadOnlyLogic=NULL,Updated=TO_TIMESTAMP('2016-08-22 13:58:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540756
;



--
-- remove EDIRecipientGLN and a desadv-related fields from C_BPArtner window and clean up export formats
--
-- 19.08.2016 08:17
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553178
;

-- 19.08.2016 08:18
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=553178
;

-- 19.08.2016 08:18
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=556622
;

-- 19.08.2016 08:18
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=556622
;

-- 19.08.2016 08:26
-- URL zum Konzept
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549330
;

-- the EDI_DefaultItemCapacity AD_Column
-- 22.08.2016 13:53
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=553173
;
-- 22.08.2016 13:53
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=553173
;


-- delete an unused format to look up C_BPartner's via their EDI RecepientGLN (because we drop that column)
DELETE FROM EXP_FormatLine WHERE EXP_Format_ID=540392;
DELETE FROM EXP_Format WHERE EXP_Format_ID=540392;

--
-- delete the field also from cockpit
--
-- 19.08.2016 10:22
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551573
;

-- 19.08.2016 10:22
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=551573
;
-- now delete the column
-- 19.08.2016 10:22
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548483
;

-- 19.08.2016 10:22
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=548483
;

-- note: before we can also drop the column, we need to migrate


-- 19.08.2016 11:05
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,ReadOnlyLogic,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,554910,0,540756,540778,123,TO_TIMESTAMP('2016-08-19 11:05:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'EDI Konfig','N','@IsEdiRecipient@=''Y''',150,1,TO_TIMESTAMP('2016-08-19 11:05:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:05
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540756 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='EDI_BPartner_Config.ValidFrom',Updated=TO_TIMESTAMP('2016-08-19 11:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540756
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554902,557182,0,540756,TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.esb.edi','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557182 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554903,557183,0,540756,TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.esb.edi','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557183 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554906,557184,0,540756,TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.esb.edi','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2016-08-19 11:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557184 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554909,557185,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','Y','N','N','N','N','N','N','N','EDI_BPartner_Config',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557185 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554910,557186,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.esb.edi','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557186 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554911,557187,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege',1,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','Erhält EDI-Belege',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557187 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554912,557188,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'Wird je nach Kontext als Absender-GLN oder als Empfänger-GLN benutzt.',13,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','EDI-GLN des Partners ',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557188 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554913,557189,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','Erhält ORDRSP',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557189 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554914,557190,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','Erhält DESADV',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557190 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554915,557191,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','Erhält INVOIC',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557191 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554917,557192,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',7,'de.metas.esb.edi','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','Y','Y','N','N','N','N','N','Gültig ab',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557192 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554918,557193,0,540756,TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',14,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','"CU pro TU" bei unbestimmter Verpackungskapazität',TO_TIMESTAMP('2016-08-19 11:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.08.2016 11:06
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557193 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557182
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557183
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557186
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557184
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557192
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557188
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557187
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557189
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557190
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557191
;

-- 19.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2016-08-19 11:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557193
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DefaultItemCapacity', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',Updated=TO_TIMESTAMP('2016-08-19 11:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542978
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542978
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL, AD_Element_ID=542978 WHERE UPPER(ColumnName)='DEFAULTITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978 AND IsCentrallyMaintained='Y'
;

-- 19.08.2016 11:09
-- URL zum Konzept
UPDATE AD_Field SET Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542978) AND IsCentrallyMaintained='Y'
;


-- 19.08.2016 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-19 11:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557184
;

-- 19.08.2016 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-19 11:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557188
;

-- 19.08.2016 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-19 11:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557189
;

-- 19.08.2016 11:12
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=''Y''',Updated=TO_TIMESTAMP('2016-08-19 11:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557189
;

-- 19.08.2016 11:12
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=''Y''',Updated=TO_TIMESTAMP('2016-08-19 11:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557190
;

-- 19.08.2016 11:13
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=''Y''', IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-19 11:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557191
;

-- 19.08.2016 11:13
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=''Y''',Updated=TO_TIMESTAMP('2016-08-19 11:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557193
;

-- 19.08.2016 11:14
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=''Y''', IsSameLine='N', SeqNo=55,Updated=TO_TIMESTAMP('2016-08-19 11:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557188
;

-- 19.08.2016 11:14
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-19 11:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557187
;

-- 19.08.2016 11:20
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='EDI_DefaultItemCapacity',Updated=TO_TIMESTAMP('2016-08-19 11:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542978
;

-- 19.08.2016 11:20
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EDI_DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978
;

-- 19.08.2016 11:20
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EDI_DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL, AD_Element_ID=542978 WHERE UPPER(ColumnName)='EDI_DEFAULTITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.08.2016 11:20
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EDI_DefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem ausgehenden EDI-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978 AND IsCentrallyMaintained='Y'
;

