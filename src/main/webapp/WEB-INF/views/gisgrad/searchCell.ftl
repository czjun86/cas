

<div class="gis-xqjz">

<div id="cell_indoor" class="gis-xqjz-div">
<span>室内/外：</span>
<ul>
<li>
<input name="" type="checkbox" value="1" <#if cellVo?? && cellVo.indoor??><#if (cellVo.indoor?index_of('1')) &gt;= 0>checked="checked"</#if></#if>/>室内
</li>
<li>
<input name="" type="checkbox" value="0" <#if cellVo?? && cellVo.indoor??><#if (cellVo.indoor?index_of('0')) &gt;= 0>checked="checked"</#if></#if>/>室外
</li>
</ul>
</div>
<div id="cell_nettype" class="gis-xqjz-div">
<span>网络类型：</span>
<ul>
<li>
<input  type="checkbox" value="0" <#if cellVo?? && cellVo.modetypes??><#if (cellVo.modetypes?index_of('0')) &gt;= 0>checked="checked"</#if></#if>/>WCDMA
</li>
<li>
<input id="cell_gsm" type="checkbox" value="1" <#if cellVo?? && cellVo.modetypes??><#if (cellVo.modetypes?index_of('1')) &gt;= 0>checked="checked"</#if></#if> />GSM
</li>
</ul>
</div>
<div  id="cell_bands" class="gis-xqjz-div" style="display:none;">
<span>频段：</span>
<ul>
<li>
<input  type="checkbox" value="900" <#if cellVo?? && cellVo.cellBands??><#if (cellVo.cellBands?index_of('900')) &gt;= 0>checked="checked"</#if></#if> />900
</li>
<li>
<input  type="checkbox" value="1800" <#if cellVo??&& cellVo.cellBands??><#if (cellVo.cellBands?index_of('1800')) &gt;= 0>checked="checked"</#if></#if>/>1800
</li>
</ul>
</div>
<div class="clear"></div>

<!-- -->
<div class="gis-xqjz-qy" >
<span>区域：</span>
<div style="float:left;" id="cell_areas">
<#if cellVo?? && cellVo.type==1>
	<ul>
			<li><input type="checkbox"  id="cell_area_all" />全选</li>
		</ul>
			<div class="clear"></div>
</#if>
<#if areas?? &&  areas?size &gt; 0>
	<#list areas as area>
		<#if area_index  == 0 >
			<ul>
		</#if>
		<li><input type="checkbox" value="${area.areaid!}" <#if cellVo??><#if cellVo.type==0><#if (cellVo.areas?index_of(''+area.areaid)) &gt;= 0>checked="checked"</#if></#if></#if> />${area.areaname!}</li>
		<#if (area_index +1)%7 == 0>
			</ul>
				<div class="clear"></div>
		</#if>
		<#if (area_index +1)%7 == 0 && area_index != (areas?size - 1)>
			<ul>
		</#if>
	</#list>
</#if>	
</div>
</div>
<input type="hidden" id="cell_btn_type"  value="<#if cellVo??>${cellVo.type}</#if>"/>
<div class="clear"></div>
</div>
<script>
	$(function(){
		if($("#cell_gsm").is(":checked")){
			 $("#cell_bands").show();
	    }else{
		   $("#cell_bands").hide();
	    }
	});
</script>