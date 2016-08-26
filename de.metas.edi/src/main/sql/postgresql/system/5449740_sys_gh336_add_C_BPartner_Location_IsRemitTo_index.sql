
-- Aug 26, 2016 5:23 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540381,0,293,'IsRemitTo=''N''','SQLS','Möchten sie wirklich die Erstattungsaddresse ändern?',TO_TIMESTAMP('2016-08-26 17:23:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Es darf nur eine Erstattungsaddresse aktiviert sein. Bei Änderung wird bei der vorherigen Erstattungsaddresse automatisch der Haken entfernt.','Y','Y','IsRemitTo','N',TO_TIMESTAMP('2016-08-26 17:23:20','YYYY-MM-DD HH24:MI:SS'),100,'IsBillToDefault=''Y'' and isActive=''Y''')
;

-- Aug 26, 2016 5:23 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540381 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- Aug 26, 2016 5:23 PM
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsRemitTo=''Y'' and isActive=''Y''',Updated=TO_TIMESTAMP('2016-08-26 17:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540381
;

-- Aug 26, 2016 5:26 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2958,540761,540381,0,TO_TIMESTAMP('2016-08-26 17:26:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',10,TO_TIMESTAMP('2016-08-26 17:26:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Aug 26, 2016 5:27 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,3093,540762,540381,0,TO_TIMESTAMP('2016-08-26 17:27:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',20,TO_TIMESTAMP('2016-08-26 17:27:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Aug 26, 2016 5:28 PM
-- URL zum Konzept
UPDATE AD_Index_Table SET Description='Zur Zeit ist es beim Senden von EDI-Rechnungen (INVOIC) nötig, dass der Geschäftspartner der jeweiligen Organisation nur eine Erstattungsaddresse hat.',Updated=TO_TIMESTAMP('2016-08-26 17:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540381
;

-- Aug 26, 2016 6:11 PM
-- URL zum Konzept
CREATE UNIQUE INDEX IsRemitTo ON C_BPartner_Location (C_BPartner_ID,IsRemitTo) WHERE IsRemitTo='Y' and isActive='Y'
;

-- Aug 26, 2016 6:11 PM
-- URL zum Konzept
CREATE OR REPLACE FUNCTION IsRemitTo_tgfn()
 RETURNS TRIGGER AS $IsRemitTo_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE C_BPartner_Location SET IsRemitTo='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsRemitTo=NEW.IsRemitTo AND C_BPartner_Location_ID<>NEW.C_BPartner_Location_ID AND IsRemitTo='Y' and isActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsRemitTo<>NEW.IsRemitTo THEN
UPDATE C_BPartner_Location SET IsRemitTo='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsRemitTo=NEW.IsRemitTo AND C_BPartner_Location_ID<>NEW.C_BPartner_Location_ID AND IsRemitTo='Y' and isActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $IsRemitTo_tg$ LANGUAGE plpgsql;
;

-- Aug 26, 2016 6:11 PM
-- URL zum Konzept
DROP TRIGGER IF EXISTS IsRemitTo_tg ON C_BPartner_Location
;

-- Aug 26, 2016 6:11 PM
-- URL zum Konzept
CREATE TRIGGER IsRemitTo_tg BEFORE INSERT OR UPDATE  ON C_BPartner_Location FOR EACH ROW EXECUTE PROCEDURE IsRemitTo_tgfn()
;

