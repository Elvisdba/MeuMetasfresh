<#-- H000 -->
<#-- StartPosition=0001 -->${"H000"?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0005 -->${h000.sender?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0040 -->${"14"?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0044 -->${h000.receiver?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0079 -->${"14"?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0083 -->${h000.reference?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0118 -->${"INHEDI"?right_pad(6)?substring(0, 6)}<#lt><#t>
<#-- StartPosition=0124 -->${"DESADV"?right_pad(25)?substring(0, 25)}<#lt><#t>
<#-- StartPosition=0149 -->${"D96A"?right_pad(25)?substring(0, 25)}<#lt><#t>
<#-- StartPosition=0174 -->${"1"?left_pad(4)?substring(0, 4)}<#lt><#t><#-- msgCount=1 -->
<#-- StartPosition=0178 -->${"UNOC"?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0182 -->${"3"?left_pad(1)?substring(0, 1)}<#lt><#t>
<#-- StartPosition=0183 -->${h000.messageDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
<#-- StartPosition=0191 -->${h000.messageDate?string("mmHH")?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0195 -->${"DESADV0702"?right_pad(14)?substring(0, 14)}<#lt><#t>
<#-- StartPosition=0209 -->${"EANCOM761702700000"}<#lt><#t>
<#-- StartPosition=0209 -->${h000.sender?left_pad(14)?substring(0, 14)}<#lt><#t>
<#-- StartPosition=0209 -->${"900"}<#lt><#t>
<#-- StartPosition=0244 -->${h000.testFlag?right_pad(1)?substring(0, 1)}<#lt><#t>

<#-- H100 -->
<#list h000.h100Lines! as h100>
	${"H100"?right_pad(4)?substring(0, 4)}<#lt><#t>
	${h100.partner?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.messageNo?right_pad(14)?substring(0, 14)}<#lt><#t>
	${"DESADV"?right_pad(6)?substring(0, 6)}<#lt><#t>
	${"D"?right_pad(3)?substring(0, 3)}<#lt><#t>
	${"01B"?right_pad(3)?substring(0, 3)}<#lt><#t>
	${"UN"?right_pad(2)?substring(0, 2)}<#lt><#t>
	${"EAN010"?right_pad(6)?substring(0, 6)}<#lt><#t>
	${"351"?right_pad(3)?substring(0, 3)}<#lt><#t>
	${h100.documentNo?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.messageDate?string("yyyyMMddHHmmss")?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.deliveryDate?string("yyyyMMddHHmmss")?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.buyerID?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h000.sender?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.deliveryID?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.storeNumber?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.invoiceID?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.orderNumber?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.orderDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
	${h100.despatchNoteNumber?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.referenceCode?right_pad(1)?substring(0, 1)}<#lt><#t>
	${h100.prodGrpCode?right_pad(10)?substring(0, 10)}<#lt><#t>
	${h100.supplierNo?right_pad(10)?substring(0, 10)}<#lt><#t>
	${"30"?right_pad(3)?substring(0, 3)}<#lt><#t>
	${h100.transportReference?right_pad(17)?substring(0, 17)}<#lt><#t>
	${h100.transportCode?right_pad(3)?substring(0, 3)}<#lt><#t>
	${h100.transportName?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.rampeID?right_pad(14)?substring(0, 14)}<#lt><#t>
	${h100.deliveryName?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.storeName?right_pad(35)?substring(0, 35)}<#lt><#t>

	<#-- P050 -->
	${"P050"?right_pad(4)?substring(0, 4)}<#lt><#t>
	${h100.p050.partner?right_pad(35)?substring(0, 35)}<#lt><#t>
	${h100.p050.messageNo?right_pad(14)?substring(0, 14)}<#lt><#t>
	${"1"?right_pad(1)?substring(0, 1)}<#lt><#t>
	${(h100.p050.packageQty)?left_pad(8)?substring(0, 8)}<#lt><#t>
	${h100.p050.packageType?right_pad(3)?substring(0, 3)}<#lt><#t>

	<#list h100.p050.p102Lines! as p102>
		<#-- P102 -->
		${"P102"?right_pad(4)?substring(0, 4)}<#lt><#t>
		${p102.partner?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.messageNo?right_pad(14)?substring(0, 14)}<#lt><#t>
		${p102.positionNo?left_pad(6)?substring(0, 6)}<#lt><#t>
		${p102.eanArtNo?right_pad(14)?substring(0, 14)}<#lt><#t>
		${p102.buyerArtNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.supplierArtNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.artDescription?right_pad(70)?substring(0, 70)}<#lt><#t>
		${p102.deliverQual?right_pad(3)?substring(0, 3)}<#lt><#t>
		${p102.deliverQTY?left_pad(15)?substring(0, 15)}<#lt><#t>
		${p102.deliverUnit?right_pad(3)?substring(0, 3)}<#lt><#t>
		${p102.orderNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.orderPosNo?right_pad(6)?substring(0, 6)}<#lt><#t>
		${p102.cUperTU?left_pad(15)?substring(0, 15)}<#lt><#t>
		${p102.currency?right_pad(3)?substring(0, 3)}<#lt><#t>
		${p102.detailPrice?left_pad(15)?substring(0, 15)}<#lt><#t>
		${p102.deliveryDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
		${""?right_pad(8)?substring(0, 8)}<#lt><#t><#-- p102.bestBeforeDate?string("yyyyMMdd") -->
		${p102.chargenNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.articleClass?right_pad(35)?substring(0, 35)}<#lt><#t>
		${p102.differenceQTY?left_pad(3)?substring(0, 3)}<#lt><#t>
		${p102.discrepancyCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		${""?right_pad(8)?substring(0, 8)}<#lt><#t><#-- p102.diffDeliveryDate?string("yyyyMMdd") -->
		${p102.eanTU?right_pad(14)?substring(0, 14)}<#lt><#t>
		${p102.storeNumber?right_pad(14)?substring(0, 14)}<#lt><#t>
		${"CU"?right_pad(3)?substring(0, 3)}<#lt><#t>

	</#list>
	<#list h100.p050.joinP060P100Lines! as join>
		<#-- P060 -->
		${"P060"?right_pad(4)?substring(0, 4)}<#lt><#t>
		${join.p060.partner?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p060.messageNo?right_pad(14)?substring(0, 14)}<#lt><#t>
		${join.p060.cPScounter?left_pad(6)?substring(0, 6)}<#lt><#t>
		${"1"?left_pad(6)?substring(0, 6)}<#lt><#t>
		${""?left_pad(8)?substring(0, 8)}<#lt><#t><#-- join.p060.palettQTY -->
		${join.p060.innerOuterCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		${join.p060.palettTyp?right_pad(3)?substring(0, 3)}<#lt><#t>
		${"0"?left_pad(6)?substring(0, 6)}<#lt><#t>
		${"0"?left_pad(6)?substring(0, 6)}<#lt><#t>
		${"1"?left_pad(6)?substring(0, 6)}<#lt><#t>
		${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- join.p060.volumen -->
		${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- join.p060.bruttogewicht -->
		${(join.p060.hierachSSCC!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		${(join.p060.normalSSCC!"")?right_pad(35)?substring(0, 35)}<#lt><#t>

		<#-- P100 -->
		${"P100"?right_pad(4)?substring(0, 4)}<#lt><#t>
		${join.p100.partner?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.messageNo?right_pad(14)?substring(0, 14)}<#lt><#t>
		${join.p100.positionNo?left_pad(6)?substring(0, 6)}<#lt><#t>
		${join.p100.eanArtNo?right_pad(14)?substring(0, 14)}<#lt><#t>
		${join.p100.buyerArtNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.supplierArtNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.artDescription?right_pad(70)?substring(0, 70)}<#lt><#t>
		${join.p100.deliverQual?right_pad(3)?substring(0, 3)}<#lt><#t>
		${join.p100.deliverQTY?left_pad(15)?substring(0, 15)}<#lt><#t>
		${join.p100.deliverUnit?right_pad(3)?substring(0, 3)}<#lt><#t>
		${join.p100.orderNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.orderPosNo?right_pad(6)?substring(0, 6)}<#lt><#t>
		${join.p100.cUperTU?left_pad(15)?substring(0, 15)}<#lt><#t>
		${join.p100.currency?right_pad(3)?substring(0, 3)}<#lt><#t>
		${join.p100.detailPrice?left_pad(15)?substring(0, 15)}<#lt><#t>
		${join.p100.deliveryDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
		${""?right_pad(8)?substring(0, 8)}<#lt><#t><#-- join.p100.bestBeforeDate?string("yyyyMMdd") -->
		${join.p100.chargenNo?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.articleClass?right_pad(35)?substring(0, 35)}<#lt><#t>
		${join.p100.differenceQTY?left_pad(3)?substring(0, 3)}<#lt><#t>
		${join.p100.discrepancyCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		${""?right_pad(8)?substring(0, 8)}<#lt><#t><#-- join.p100.diffDeliveryDate?string("yyyyMMdd") -->
		${join.p100.eanTU?right_pad(14)?substring(0, 14)}<#lt><#t>
		${join.p100.storeNumber?right_pad(14)?substring(0, 14)}<#lt><#t>
		${"CU"?right_pad(3)?substring(0, 3)}<#lt><#t>
		${""?right_pad(14)?substring(0, 14)}<#lt><#t><#-- join.p100.sellBeforeDate?string("yyyyMMddHHmmss") -->
		${""?right_pad(14)?substring(0, 14)}<#lt><#t><#-- join.p100.productionDate?string("yyyyMMddHHmmss") -->
		${join.p100.discrepancyText?right_pad(35)?substring(0, 35)}<#lt><#t>

	</#list>
</#list>