
-- 18.08.2016 17:13
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='The minimum sum percentage (QtyDeliveredInUOM/QtyEntered) above which the system shall allow a user to send an EDI DESADV.', Name='de.metas.edi.DESADV.MinimumPercentage',Updated=TO_TIMESTAMP('2016-08-18 17:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540892
;

-- 18.08.2016 17:15
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541006,'C',TO_TIMESTAMP('2016-08-18 17:15:03','YYYY-MM-DD HH24:MI:SS'),100,'The minimum sum percentage(ConfirmedQty/QtyEntered) above which the system shall allow a user to send an EDI ORDRSP.','de.metas.esb.edi','Y','de.metas.edi.ORDRSP.MinimumPercentage',TO_TIMESTAMP('2016-08-18 17:15:03','YYYY-MM-DD HH24:MI:SS'),100,'10')
;

