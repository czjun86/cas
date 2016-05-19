<input type="hidden" id="serialno_info" value="${(serialno)!}">
<input type="hidden" id="areaId_info" value="${(areaId)!}">
<div style="float:left;margin:15px 50px;">
	<input name="tolerantval" type="checkbox" value="1" <#if list??&&list?size &gt; 0><#list list as val><#if val == 1>checked="checked"</#if></#list></#if>>
		<span class="" style="margin-right:56px;">需实测工单量</span>
    <input name="tolerantval" type="checkbox" value="2" <#if list??&&list?size &gt; 0><#list list as val><#if val == 2>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:92px;">实测量</span>
    <input name="tolerantval" type="checkbox" value="3" <#if list??&&list?size &gt; 0><#list list as val><#if val == 3>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:20px;">网络投诉工单量</span>
    <input name="tolerantval" type="checkbox" value="4" <#if list??&&list?size &gt; 0><#list list as val><#if val == 4>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:43px;">优化真正解决量</span>
</div>
<div style="float:left;margin:15px 50px;">
	<input name="tolerantval" type="checkbox" value="5" <#if list??&&list?size &gt; 0><#list list as val><#if val == 5>checked="checked"</#if></#list></#if>>
		<span class="" style="margin-right:44px;">建设真正解决量</span>
    <input name="tolerantval" type="checkbox" value="6" <#if list??&&list?size &gt; 0><#list list as val><#if val == 6>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:44px;">维护真正解决量</span>
    <input name="tolerantval" type="checkbox" value="7" <#if list??&&list?size &gt; 0><#list list as val><#if val == 7>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:20px;">其它真正解决量</span>
    <input name="tolerantval" type="checkbox" value="8" <#if list??&&list?size &gt; 0><#list list as val><#if val == 8>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:20px;">累计优化工单滞留量</span>
</div>
<div style="float:left;margin:15px 50px;">
	<input name="tolerantval" type="checkbox" value="9" <#if list??&&list?size &gt; 0><#list list as val><#if val == 9>checked="checked"</#if></#list></#if>>
		<span class="" style="margin-right:20px;">累计建设工单滞留量</span>
	<input name="tolerantval" type="checkbox" value="10" <#if list??&&list?size &gt; 0><#list list as val><#if val == 10>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:20px;">累计维护工单滞留量</span>
    <input name="tolerantval" type="checkbox" value="11" <#if list??&&list?size &gt; 0><#list list as val><#if val == 11>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:20px;">优化工单驳回量</span>
    <input name="tolerantval" type="checkbox" value="12" <#if list??&&list?size &gt; 0><#list list as val><#if val == 12>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:43px;">建设工单驳回量</span>
</div>
<div style="float:left;margin:15px 50px;">
    <input name="tolerantval" type="checkbox" value="13" <#if list??&&list?size &gt; 0><#list list as val><#if val == 13>checked="checked"</#if></#list></#if>>
		<span class="" style="margin-right:44px;">维护工单驳回量</span>
	<input name="tolerantval" type="checkbox" value="14" <#if list??&&list?size &gt; 0><#list list as val><#if val == 14>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:68px;">工单超时量</span>
    <input name="tolerantval" type="checkbox" value="15" <#if list??&&list?size &gt; 0><#list list as val><#if val == 15>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:44px;">工单重派量</span>
    <input name="tolerantval" type="checkbox" value="16" <#if list??&&list?size &gt; 0><#list list as val><#if val == 16>checked="checked"</#if></#list></#if>>
    	<span class="" style="margin-left:10px;margin-right:43px;">工单重复投诉量</span>
</div>
<div style="float:left;margin:15px 50px;">
	<input name="tolerantval" type="checkbox" value="17" <#if list??&&list?size &gt; 0><#list list as val><#if val == 17>checked="checked"</#if></#list></#if>>
		<span class="" style="margin-right:20px;">工单升级量</span>
</div>