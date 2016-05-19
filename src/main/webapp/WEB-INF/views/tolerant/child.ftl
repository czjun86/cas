<#if list?? && list?size &gt; 0>
	<#list list as wo>
		<ul <#if  wo_index%2 == 1>class="all_list_background"</#if>>
			<li style="width:20%;">${(wo.serialno)!}</li>
			<li style="width:12%;">${(wo.areaname)!}</li>
			<li style="width:22%;">${(wo.submitDatetime?string("yyyy-MM-dd HH:mm:ss"))!''}</li>
			<li style="width:22%;">${(wo.testtime?string("yyyy-MM-dd HH:mm:ss"))!}</li>
			<li style="width:12%;"><#if wo.toleType?? && wo.toleType!=-1>已标记</#if></li>
			<li style="width:12%" class="all_list_riborder"><input type="button" class="compare_test" val ="${(wo.areaId)!}" serialno="${(wo.serialno)!}" title='容错修改'/></li>
		</ul>
	</#list>
<#else>
	<ul class="all_list_background">
		<li style="width:100%;">
			没有符合条件的数据
		</li>
	</ul>
</#if>