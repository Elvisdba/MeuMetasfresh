-- 29.08.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540719,'Y','de.metas.edi.process.EDI_Ordrsp_CreateFromSalesOrders','N',TO_TIMESTAMP('2016-08-29 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,'Erstellt zu den aktuell selektierten Aufträgen ORDRSP Datensätze, sofern keine existieren, aber nach aktuellem Stand existieren müssten.','de.metas.esb.edi','Y','N','N','N','N','N','N','Y',0,'ORDRSP aus Aufträgen erstellen','N','Y',0,0,'Java',TO_TIMESTAMP('2016-08-29 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Ordrsp_CreateFromSalesOrders')
;

-- 29.08.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540719 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 29.08.2016 14:16
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540719,541010,20,'IsOnlyCurrentRecord',TO_TIMESTAMP('2016-08-29 14:16:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','Wenn angehakt, dann versucht das System nicht zur derzeitigen Selektion ORDRSP Datensätze zu erstellen, sondern nur zum gerade ausgewählten Auftrag','de.metas.esb.edi',1,'Y','N','Y','N','Y','N','Nur aktueller Datensatz',10,TO_TIMESTAMP('2016-08-29 14:16:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 14:16
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541010 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 29.08.2016 14:17
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540719,259,TO_TIMESTAMP('2016-08-29 14:17:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2016-08-29 14:17:14','YYYY-MM-DD HH24:MI:SS'),100)
;

--
-- deleting legacy sql-column C_Order.IsEDIRecipient
--
-- 29.08.2016 14:27
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552603
;
-- 29.08.2016 14:27
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=552603
;

-- 29.08.2016 14:43
-- URL zum Konzept
UPDATE AD_Tab SET SeqNo=151,Updated=TO_TIMESTAMP('2016-08-29 14:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540739
;

-- 29.08.2016 14:43
-- URL zum Konzept
UPDATE AD_Tab SET SeqNo=140,Updated=TO_TIMESTAMP('2016-08-29 14:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540756
;

--
-- fixing the readonly logic field of the C_BPArtner.EDI_BPartnerConfig tab
-- 29.08.2016 14:44
-- URL zum Konzept
UPDATE AD_Tab SET ReadOnlyLogic=NULL,Updated=TO_TIMESTAMP('2016-08-29 14:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540756
;
-- 29.08.2016 14:52
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-08-29 14:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554875
;

-- 29.08.2016 14:52
-- URL zum Konzept
INSERT INTO t_alter_column values('edi_ordrsp','DocumentNo','VARCHAR(30)',null,'NULL')
;
-- 29.08.2016 14:52
-- URL zum Konzept
INSERT INTO t_alter_column values('edi_ordrsp','DocumentNo',null,'NULL',null)
;

--
-- deleting legacy sql-column M_ShipmentSchedule.IsEDIRecipient
--
-- 29.08.2016 15:02
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=555941
;
-- 29.08.2016 15:02
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=555941
;
-- 29.08.2016 15:02
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552463
;
-- 29.08.2016 15:02
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=552463
;

ALTER TABLE EDI_OrdrspLine DROP COLUMN Qty;

--
-- fixing column-SQL of EDI_Ordrsp.
--
-- 29.08.2016 15:15
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select CASE WHEN SUM (l.ConfirmedQty) = 0 THEN NULL ELSE round ((SUM (l.ConfirmedQty) ), 2) END from EDI_OrdrspLine l where l.EDI_Ordrsp_ID = EDI_Ordrsp.EDI_Ordrsp_ID)',Updated=TO_TIMESTAMP('2016-08-29 15:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554881
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554881
;

--
-- setting all M_Shipmentschedule records to lazyloading, because we don't want to use them in the code anyways
--
-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544786
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544787
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552467
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551099
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544789
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544784
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-08-29 15:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546477
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552469
;

-- 29.08.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549796
;

--
-- setting all C_Invoice_candidate records to lazyloading, because we don't want to use them in the code anyways
--
-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546607
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551339
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551953
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546600
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552287
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546639
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551796
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544924
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551792
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552289
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552023
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552288
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549870
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551284
;

-- 29.08.2016 15:19
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-08-29 15:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551763
;

--
-- tweak the EDI_Ordrsp table
--
-- 29.08.2016 15:27
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-08-29 15:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540719
;

-- 29.08.2016 15:32
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2016-08-29 15:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554875
;

-- 29.08.2016 15:32
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsSelectionColumn='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-08-29 15:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554866
;

-- 29.08.2016 15:32
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsSelectionColumn='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-08-29 15:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554872
;

--
-- add ORDRSP window to the menu tree
--
-- 29.08.2016 15:36
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540724,0,540300,TO_TIMESTAMP('2016-08-29 15:36:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','EDI_Ordrsp','Y','N','Y','N','EDI Auftragsbestätigung (ORDRSP)',TO_TIMESTAMP('2016-08-29 15:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 15:36
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540724 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 29.08.2016 15:36
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540724, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540724)
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=272 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=457 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540643 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=459 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=458 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=100 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=166, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540724 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=52001 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540240 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540241 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540311 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540205 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=460 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=301 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=129 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=543 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=195 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53223 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=407 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=406 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=335 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=436 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=507 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=448 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=449 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=492 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53269 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=491 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=419 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540724 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540583 AND AD_Tree_ID=10
;

-- 29.08.2016 15:36
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540652 AND AD_Tree_ID=10
;

--
-- indices for EDI_Ordrsp* tables
--
CREATE INDEX edi_ordrsp_poreference
  ON edi_desadv
  (poreference);
  
CREATE INDEX edi_ordrspline_edi_ordrsp_id
  ON edi_ordrspline
  (edi_ordrsp_id);
CREATE INDEX edi_ordrspline_m_shipmentschedule_id
  ON edi_ordrspline
  (m_shipmentschedule_id);
  -- 29.08.2016 15:51
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-29 15:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554875
;

-- 29.08.2016 15:51
-- URL zum Konzept
UPDATE AD_Column SET IsUseDocSequence='Y',Updated=TO_TIMESTAMP('2016-08-29 15:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554875
;

-- 29.08.2016 15:52
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-29 15:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551728
;

-- 29.08.2016 15:56
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-08-29 15:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557117
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Element SET Name='EDI Export Status', PrintName='EDI Export Status',Updated=TO_TIMESTAMP('2016-08-29 15:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541997
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541997
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EDI_ExportStatus', Name='EDI Export Status', Description=NULL, Help=NULL WHERE AD_Element_ID=541997
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EDI_ExportStatus', Name='EDI Export Status', Description=NULL, Help=NULL, AD_Element_ID=541997 WHERE UPPER(ColumnName)='EDI_EXPORTSTATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EDI_ExportStatus', Name='EDI Export Status', Description=NULL, Help=NULL WHERE AD_Element_ID=541997 AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_Field SET Name='EDI Export Status', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541997) AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 15:57
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='EDI Export Status', Name='EDI Export Status' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541997)
;

-- 29.08.2016 15:58
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554367,TO_TIMESTAMP('2016-08-29 15:58:59','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'DocumentNo/Value for Table EDI_Ordrsp',1,'Y','N','Y','N','DocumentNo_EDI_Ordrsp','N',1000000,TO_TIMESTAMP('2016-08-29 15:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 16:06
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsSelectionColumn='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2016-08-29 16:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554890
;

-- 29.08.2016 16:06
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-08-29 16:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554891
;

-- 29.08.2016 18:04
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540300,Updated=TO_TIMESTAMP('2016-08-29 18:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540777
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557126
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557127
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557128
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557129
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557138
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557130
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557132
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557131
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557136
;

-- 29.08.2016 18:05
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557135
;

-- 29.08.2016 18:06
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-08-29 18:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557137
;

-- 29.08.2016 18:06
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-08-29 18:06:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557132
;

-- 29.08.2016 18:10
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select CASE WHEN SUM (l.ConfirmedQty) = 0 THEN NULL ELSE round ((SUM (l.ConfirmedQty)/SUM (l.QtyEntered) ) * 100, 2) END from EDI_OrdrspLine l where l.EDI_Ordrsp_ID = EDI_Ordrsp.EDI_Ordrsp_ID)',Updated=TO_TIMESTAMP('2016-08-29 18:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554881
;

-- 29.08.2016 18:12
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-08-29 18:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540777
;

-- 29.08.2016 18:12
-- URL zum Konzept
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2016-08-29 18:12:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540776
;

-- 29.08.2016 18:13
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555021,1474,0,20,540777,'N','IsManual',TO_TIMESTAMP('2016-08-29 18:13:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Dies ist ein manueller Vorgang','de.metas.esb.edi',1,'Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorang manuell durchgeführt wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Manuell',0,TO_TIMESTAMP('2016-08-29 18:13:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.08.2016 18:13
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555021 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Element SET Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt btw der Datensatz manuell gepflegt wird.',Updated=TO_TIMESTAMP('2016-08-29 18:14:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt btw der Datensatz manuell gepflegt wird.' WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt btw der Datensatz manuell gepflegt wird.', AD_Element_ID=1474 WHERE UPPER(ColumnName)='ISMANUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt btw der Datensatz manuell gepflegt wird.' WHERE AD_Element_ID=1474 AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Field SET Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt btw der Datensatz manuell gepflegt wird.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1474) AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 18:14
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsManual@=N',Updated=TO_TIMESTAMP('2016-08-29 18:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554896
;

-- 29.08.2016 18:15
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsManual@=N',Updated=TO_TIMESTAMP('2016-08-29 18:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554897
;

-- 29.08.2016 18:15
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsManual@=N',Updated=TO_TIMESTAMP('2016-08-29 18:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554894
;

-- 29.08.2016 18:15
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsManual@=N',Updated=TO_TIMESTAMP('2016-08-29 18:15:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554898
;

-- 29.08.2016 18:15
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-29 18:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555021
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Element SET Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.',Updated=TO_TIMESTAMP('2016-08-29 18:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.' WHERE AD_Element_ID=1474
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.', AD_Element_ID=1474 WHERE UPPER(ColumnName)='ISMANUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManual', Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.' WHERE AD_Element_ID=1474 AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 18:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Manuell', Description='Dies ist ein manueller Vorgang', Help='Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1474) AND IsCentrallyMaintained='Y'
;

-- 29.08.2016 18:18
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540345,TO_TIMESTAMP('2016-08-29 18:18:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','QuantityQualifier','J',TO_TIMESTAMP('2016-08-29 18:18:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 18:33
-- URL zum Konzept
UPDATE AD_Val_Rule SET Classname='de.metas.edi.validationRule.EDI_Ordrssp_QuantityQualifierValidationRule',Updated=TO_TIMESTAMP('2016-08-29 18:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 29.08.2016 18:37
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555021,557206,0,540750,0,TO_TIMESTAMP('2016-08-29 18:37:46','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein manueller Vorgang',0,'de.metas.esb.edi','Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorgang manuell durchgeführt bzw der Datensatz manuell gepflegt wird.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Manuell',150,160,0,1,1,TO_TIMESTAMP('2016-08-29 18:37:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.08.2016 18:37
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557206 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557137
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557206
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557127
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557128
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557129
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557138
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557130
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557132
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557131
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557136
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557135
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557133
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2016-08-29 18:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557134
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-08-29 18:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557137
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-08-29 18:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557133
;

-- 29.08.2016 18:38
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-08-29 18:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557134
;

-- 30.08.2016 08:49
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-08-30 08:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554901
;

-- 30.08.2016 09:15
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='EDI_OrdrspLine.Line, EDI_OrdrspLine.QuantityQualifier',Updated=TO_TIMESTAMP('2016-08-30 09:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540750
;

-- 30.08.2016 11:07
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='10_IA',Updated=TO_TIMESTAMP('2016-08-30 11:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541202
;

-- 30.08.2016 11:07
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='20_IB',Updated=TO_TIMESTAMP('2016-08-30 11:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541203
;

-- 30.08.2016 11:07
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='30_IR',Updated=TO_TIMESTAMP('2016-08-30 11:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541205
;

-- 30.08.2016 11:07
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='40_CK',Updated=TO_TIMESTAMP('2016-08-30 11:07:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541204
;

-- 30.08.2016 11:08
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='10_IA', FieldLength=5,Updated=TO_TIMESTAMP('2016-08-30 11:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554896
;

-- 30.08.2016 11:11
-- URL zum Konzept
UPDATE AD_Val_Rule SET Description='Allows only 10_IA for IsManual=N and only the other items'' values for IsManual=''Y',Updated=TO_TIMESTAMP('2016-08-30 11:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;

-- 30.08.2016 11:12
-- URL zum Konzept
UPDATE AD_Reference SET Help='Developer note: we prepended numbers (10, 20, ..) because the code relies on the items to be in this particular order.', IsOrderByValue='Y',Updated=TO_TIMESTAMP('2016-08-30 11:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540663
;

-- 30.08.2016 11:12
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540663
;

-- getting rid of the "java validation rule again. it will be replaced by an SQL one.
-- URL zum Konzept
UPDATE AD_Val_Rule SET Classname=null,Updated=TO_TIMESTAMP('2016-08-29 18:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540345
;
