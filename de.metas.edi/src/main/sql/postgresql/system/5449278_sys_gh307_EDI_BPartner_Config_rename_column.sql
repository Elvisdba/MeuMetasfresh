
--
-- Rename C_BPartner IsEdiRecipient to HasEdiConfig. We want to use it as IsDisplayed criterion in the EDI_BPartner_Config AD_Tab.
-- the name needs to be different EDI_BPartner_Config from EDI_BPartner_Config.IsEdiRecipient for that to work.
--

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='HasEdiConfig', Description='Partner mit EDI Konfiguration  können für EDI eingerichtet werden. Z.B. kann EDI für soche Partner auch zeitweise ein- oder ausgeschaltet werden.', Name='Hat EDI Konfiguration', PrintName='Hat EDI Konfiguration',Updated=TO_TIMESTAMP('2016-08-22 14:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='HasEdiConfig', Name='Hat EDI Konfiguration', Description='Partner mit EDI Konfiguration  können für EDI eingerichtet werden. Z.B. kann EDI für soche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='HasEdiConfig', Name='Hat EDI Konfiguration', Description='Partner mit EDI Konfiguration  können für EDI eingerichtet werden. Z.B. kann EDI für soche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL, AD_Element_ID=542000 WHERE UPPER(ColumnName)='HASEDICONFIG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='HasEdiConfig', Name='Hat EDI Konfiguration', Description='Partner mit EDI Konfiguration  können für EDI eingerichtet werden. Z.B. kann EDI für soche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL WHERE AD_Element_ID=542000 AND IsCentrallyMaintained='Y'
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Hat EDI Konfiguration', Description='Partner mit EDI Konfiguration  können für EDI eingerichtet werden. Z.B. kann EDI für soche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542000) AND IsCentrallyMaintained='Y'
;

-- 22.08.2016 14:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Hat EDI Konfiguration', Name='Hat EDI Konfiguration' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542000)
;

-- 22.08.2016 14:17
-- URL zum Konzept
UPDATE AD_Tab SET DisplayLogic='@HasEdiConfig@=''Y''',Updated=TO_TIMESTAMP('2016-08-22 14:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540756
;


-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='IsEdiRecipient', Description=NULL, Name='Erhält EDI-Belege', PrintName='Erhält EDI-Belege',Updated=TO_TIMESTAMP('2016-08-22 14:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsEdiRecipient', Name='Erhält EDI-Belege', Description=NULL, Help=NULL WHERE AD_Element_ID=542000
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEdiRecipient', Name='Erhält EDI-Belege', Description=NULL, Help=NULL, AD_Element_ID=542000 WHERE UPPER(ColumnName)='ISEDIRECIPIENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEdiRecipient', Name='Erhält EDI-Belege', Description=NULL, Help=NULL WHERE AD_Element_ID=542000 AND IsCentrallyMaintained='Y'
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Field SET Name='Erhält EDI-Belege', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542000) AND IsCentrallyMaintained='Y'
;

-- 22.08.2016 14:52
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Erhält EDI-Belege', Name='Erhält EDI-Belege' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542000)
;

-- 22.08.2016 14:53
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543161,0,'HasEdiConfig',TO_TIMESTAMP('2016-08-22 14:53:49','YYYY-MM-DD HH24:MI:SS'),100,'Partner mit EDI Konfiguration können für EDI eingerichtet werden. Z.B. kann EDI für solche Partner auch zeitweise ein- oder ausgeschaltet werden.','de.metas.esb.edi','Y','Hat EDI Konfiguration','Hat EDI Konfiguration',TO_TIMESTAMP('2016-08-22 14:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.08.2016 14:53
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543161 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 22.08.2016 14:56
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=543161, ColumnName='HasEdiConfig', Description='Partner mit EDI Konfiguration können für EDI eingerichtet werden. Z.B. kann EDI für solche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL, Name='Hat EDI Konfiguration',Updated=TO_TIMESTAMP('2016-08-22 14:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548482
;

-- 22.08.2016 14:56
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=548482
;

-- 22.08.2016 14:56
-- URL zum Konzept
UPDATE AD_Field SET Name='Hat EDI Konfiguration', Description='Partner mit EDI Konfiguration können für EDI eingerichtet werden. Z.B. kann EDI für solche Partner auch zeitweise ein- oder ausgeschaltet werden.', Help=NULL WHERE AD_Column_ID=548482 AND IsCentrallyMaintained='Y'
;



--
-- DDL
--
COMMIT;
ALTER TABLE C_BPartner RENAME IsEdiRecipient TO HasEdiConfig;
