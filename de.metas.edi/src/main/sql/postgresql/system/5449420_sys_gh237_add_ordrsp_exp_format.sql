-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,554358,540776,'N',TO_TIMESTAMP('2016-08-23 14:41:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,'Y','N','EDI_Ordrsp','N','N','N',TO_TIMESTAMP('2016-08-23 14:41:29','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Ordrsp','*')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554858,0,TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540412,550150,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554859,0,TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540412,550151,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554871,0,TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','de.metas.esb.edi',540412,550152,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','N','Y','Währung',30,'R',TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'C_Currency_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554860,0,TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540412,550153,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',40,'E',TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554861,0,TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540412,550154,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',50,'R',TO_TIMESTAMP('2016-08-23 14:41:36','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554872,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem die Lieferung den Lieferempfänger erreicht.','de.metas.esb.edi',540412,550155,'N','N','Lieferdatum',60,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'DeliveryDate')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554868,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Übergabeort (EANCOM: NAD+DP)','de.metas.esb.edi',540412,550156,'N','Y','Lieferort-GLN',70,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'DeliveryGLN')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554875,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','de.metas.esb.edi',540412,550157,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','Y','Beleg Nr.',80,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'DocumentNo')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554876,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550158,'N','N','EDI Fehlermeldung',90,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDIErrorMsg')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554877,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550159,'N','Y','EDI Status Exportieren',100,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI_ExportStatus')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554865,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550160,'N','Y','ORDRSP',110,'R',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Ordrsp_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554880,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Mindestprozentsatz der beauftragten Gesamtmenge, zu der die Bestätigung eine Aussage treffen muss.','de.metas.esb.edi',540412,550161,'N','N','Bestätigung % Minimum',120,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI_ORDRSP_MinimumSumPercentage')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554881,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550162,'N','N','Bestätigt %',130,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI_ORDRSP_SumPercentage')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555012,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI-GLN des Empfängers','de.metas.esb.edi',540412,550163,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','N','Y','Empfänger-GLN',140,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDIReceiverIdentification')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,555011,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDI-GLN des Absenders','de.metas.esb.edi',540412,550164,'Der Wert ist in erster Linie Identifkator einer EDI-Partei und nicht GLN eines Ortes.','N','Y','Absender-GLN',150,'E',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'EDISenderIdentification')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554870,0,TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550165,'N','Y','Übergabeadresse',160,'R',TO_TIMESTAMP('2016-08-23 14:41:37','YYYY-MM-DD HH24:MI:SS'),100,'HandOver_Location_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554869,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550166,'N','Y','Übergabe an',170,'R',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'HandOver_Partner_ID')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554862,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540412,550167,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',180,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554866,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','de.metas.esb.edi',540412,550168,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','N','Y','Referenz',190,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'POReference')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554879,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','de.metas.esb.edi',540412,550169,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','N','Y','Verarbeitet',200,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Processed')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554878,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540412,550170,'N','Y','Process Now',210,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Processing')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554873,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem die Lieferung das Lager des Lieferanten verlässt','de.metas.esb.edi',540412,550171,'N','N','Versanddatum',220,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'ShipDate')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554867,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Im Fall einer Verkaufstransaktion is die Lieferanten-GLN eine der eigenen GLNs (EANCOM: NAD+SU)','de.metas.esb.edi',540412,550172,'N','Y','Lieferanten-GLN',230,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'SupplierGLN')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554863,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540412,550173,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',240,'E',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 23.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554864,0,TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540412,550174,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',250,'R',TO_TIMESTAMP('2016-08-23 14:41:38','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540389, IsActive='Y', Name='C_Currency_ID',Updated=TO_TIMESTAMP('2016-08-23 14:42:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550152
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:42:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550155
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550156
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550163
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550164
;

-- 23.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550168
;

-- 23.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, IsActive='Y', Name='HandOver_Location_ID',Updated=TO_TIMESTAMP('2016-08-23 14:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550165
;

-- 23.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550171
;

-- 23.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550172
;

-- 23.08.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_Format SET Name='EDI_Exp_Ordrsp', Value='EDI_Exp_Ordrsp',Updated=TO_TIMESTAMP('2016-08-23 14:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540412
;

-- 23.08.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-23 14:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550054
;

-- 23.08.2016 14:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550160
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,540777,'N',TO_TIMESTAMP('2016-08-23 14:47:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540413,'Y','N','EDI_Exp_OrdrspLine','N','N','N',TO_TIMESTAMP('2016-08-23 14:47:14','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_OrdrspLine','*')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554882,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540413,550175,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554883,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540413,550176,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554894,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Bestätigung einer erhaltenen Menge','de.metas.esb.edi',540413,550177,'Bestätigung einer erhaltenen Menge','N','Y','Bestätigte Menge',30,'E',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'ConfirmedQty')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554884,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540413,550178,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',40,'E',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554885,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540413,550179,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',50,'R',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554900,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz','de.metas.esb.edi',540413,550180,'Steuer bezeichnet den in dieser Dokumentenzeile verwendeten Steuersatz.','N','N','Steuer',60,'R',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'C_Tax_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554895,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','de.metas.esb.edi',540413,550181,'Eine eindeutige (nicht monetäre) Maßeinheit','N','Y','Maßeinheit',70,'R',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'C_UOM_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554897,0,TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem die Lieferung den Lieferempfänger erreicht.','de.metas.esb.edi',540413,550182,'N','N','Lieferdatum',80,'E',TO_TIMESTAMP('2016-08-23 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'DeliveryDate')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554890,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540413,550183,'N','Y','ORDRSP',90,'R',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Ordrsp_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554889,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540413,550184,'N','Y','EDI_OrdrspLine',100,'R',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_OrdrspLine_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554886,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540413,550185,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',110,'E',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554891,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','de.metas.esb.edi',540413,550186,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','N','Y','Zeile Nr.',120,'E',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Line')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554892,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.esb.edi',540413,550187,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','N','Y','Produkt',130,'R',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554901,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540413,550188,'N','N','Lieferdisposition',140,'R',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipmentSchedule_ID')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554899,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','de.metas.esb.edi',540413,550189,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','N','Y','Einzelpreis',150,'E',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'PriceActual')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554939,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','de.metas.esb.edi',540413,550190,'Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','N','N','Menge',160,'E',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'QtyEntered')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554896,0,TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob die jeweilige Menge zugesagt ist, nicht verfügbar ist, nachbestellt wird usw.','de.metas.esb.edi',540413,550191,'N','Y','Mengenkennzeichner',170,'E',TO_TIMESTAMP('2016-08-23 14:47:27','YYYY-MM-DD HH24:MI:SS'),100,'QuantityQualifier')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554898,0,TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem die Lieferung das Lager des Lieferanten verlässt','de.metas.esb.edi',540413,550192,'N','N','Versanddatum',180,'E',TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'ShipDate')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554893,0,TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)','de.metas.esb.edi',540413,550193,'Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.','N','Y','UPC/EAN',190,'E',TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'UPC')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554887,0,TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540413,550194,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',200,'E',TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554888,0,TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540413,550195,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',210,'R',TO_TIMESTAMP('2016-08-23 14:47:28','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 23.08.2016 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550177
;

-- 23.08.2016 14:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540399, Name='C_Tax_ID',Updated=TO_TIMESTAMP('2016-08-23 14:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550180
;

-- 23.08.2016 14:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540390, IsActive='Y', Name='C_UOM_ID',Updated=TO_TIMESTAMP('2016-08-23 14:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550181
;

-- 23.08.2016 14:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550182
;

-- 23.08.2016 14:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550184
;

-- 23.08.2016 14:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550186
;

-- 23.08.2016 14:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540386, IsActive='Y', Name='M_Product_ID',Updated=TO_TIMESTAMP('2016-08-23 14:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550187
;

-- 23.08.2016 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550189
;

-- 23.08.2016 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550190
;

-- 23.08.2016 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550191
;

-- 23.08.2016 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550192
;

-- 23.08.2016 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-23 14:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550193
;

-- 23.08.2016 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2016-08-23 14:55:15','YYYY-MM-DD HH24:MI:SS'),100,'U',540412,540412,550196,'Y','N','N','EDI_Exp_Ordrsp',260,'M',TO_TIMESTAMP('2016-08-23 14:55:15','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_Ordrsp')
;

-- 23.08.2016 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_Format SET Name='EDI_Imp_Desadv_Feedback', Value='EDI_Imp_Desadv_Feedback',Updated=TO_TIMESTAMP('2016-08-23 16:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540407
;

-- 23.08.2016 16:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,RplImportMode,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,540776,'N',TO_TIMESTAMP('2016-08-23 16:04:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540414,'Y','N','EDI_Imp_Ordrsp_Feedback','N','L','N','N',TO_TIMESTAMP('2016-08-23 16:04:59','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Imp_Ordrsp_Feedback','*')
;

-- 23.08.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554865,0,TO_TIMESTAMP('2016-08-23 16:06:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540414,550197,'Y','Y','Y','EDI_Ordrsp_ID',10,'E',TO_TIMESTAMP('2016-08-23 16:06:32','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Ordrsp_ID')
;

-- 23.08.2016 16:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554877,0,TO_TIMESTAMP('2016-08-23 16:07:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540414,550198,'Y','Y','N','EDI_ExportStatus',20,'E',TO_TIMESTAMP('2016-08-23 16:07:31','YYYY-MM-DD HH24:MI:SS'),100,'EDI_ExportStatus')
;

-- 23.08.2016 16:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554876,0,TO_TIMESTAMP('2016-08-23 16:08:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540414,550199,'Y','N','N','EDIErrorMsg',30,'E',TO_TIMESTAMP('2016-08-23 16:08:33','YYYY-MM-DD HH24:MI:SS'),100,'EDIErrorMsg')
;

-- 23.08.2016 16:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Name='EDI_Ordrsp_ID', Type='E',Updated=TO_TIMESTAMP('2016-08-23 16:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550160
;

-- 23.08.2016 16:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Name='EDI_OrdrspLine_ID', Type='E',Updated=TO_TIMESTAMP('2016-08-23 16:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550184
;

-- 23.08.2016 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,540776,'N',TO_TIMESTAMP('2016-08-23 17:04:05','YYYY-MM-DD HH24:MI:SS'),100,'U',540415,'Y','N','EDI_Imp_EDI_Ordrsp_Feedback','N','N','N',TO_TIMESTAMP('2016-08-23 17:04:05','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Imp_EDI_Ordrsp_Feedback','*')
;

