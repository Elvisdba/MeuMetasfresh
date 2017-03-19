update AD_Tab_Callout set Classname='de.metas.bpartner.callout.C_BPartner_Location_Tab_Callout' where Classname='org.adempiere.bpartner.callout.C_BPartner_Location_Tab_Callout'
;

/* query to check AD_Tab_Callout(s)
select w.Name, tt.Name, t.TableName, tc.*
from AD_Tab_Callout tc
inner join AD_Tab tt on (tt.AD_Tab_ID=tc.AD_Tab_ID)
inner join AD_Table t on (t.AD_Table_ID=tt.AD_Table_ID)
inner join AD_Window w on (w.AD_Window_ID=tt.AD_Window_ID)
-- where tc.Classname like '%AD_User_Tab_Callout%'
order by w.Name, tt.Name
*/



update C_Queue_PackageProcessor set Classname='de.metas.bpartner.async.C_BPartner_UpdateStatsFromBPartner' where Classname='org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner'
;

/* query to check C_Queue_PackageProcessor
select *
from C_Queue_PackageProcessor
where classname like '%BPartner%'
;
*/

