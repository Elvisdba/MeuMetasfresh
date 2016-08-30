-- 29.08.2016 08:42
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555020,543143,0,30,260,'N','EDI_OrdrspLine_ID',TO_TIMESTAMP('2016-08-29 08:42:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','EDI_OrdrspLine',0,TO_TIMESTAMP('2016-08-29 08:42:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.08.2016 08:42
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555020 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.08.2016 12:16
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555020,557205,0,187,0,TO_TIMESTAMP('2016-08-29 12:16:37','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.esb.edi',0,'Y','Y','N','N','N','N','N','N','N','EDI_OrdrspLine',220,210,0,1,1,TO_TIMESTAMP('2016-08-29 12:16:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 12:16
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557205 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.08.2016 13:10
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-29 13:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555020
;

--
-- C_Order.EDI_Ordrsp_ID
-- 29.08.2016 13:13
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-29 13:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554940
;



COMMIT;
--
--DDL
--
-- 29.08.2016 08:42
-- URL zum Konzept
ALTER TABLE C_OrderLine ADD EDI_OrdrspLine_ID NUMERIC(10) DEFAULT NULL 
;

--
-- bonus: add indeces and FK contraints for C_Order and C_OrderLine
--

CREATE INDEX c_orderline_edi_ordrspline
  ON c_orderline
  USING btree
  (edi_ordrspline_ID);

ALTER TABLE C_OrderLine DROP CONSTRAINT IF EXISTS EDIOrdrspLine_COrderLine;
ALTER TABLE C_Order DROP CONSTRAINT IF EXISTS EDIOrdrsp_COrder;
ALTER TABLE C_OrderLine ADD CONSTRAINT EDIOrdrspLine_COrderLine FOREIGN KEY (EDI_OrdrspLine_ID) REFERENCES EDI_OrdrspLine DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE C_Order ADD CONSTRAINT EDIOrdrsp_COrder FOREIGN KEY (EDI_Ordrsp_ID) REFERENCES EDI_Ordrsp DEFERRABLE INITIALLY DEFERRED;
