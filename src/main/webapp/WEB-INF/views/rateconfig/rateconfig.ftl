<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
<style>
#saveButton{
	position:fixed;
	bottom:20px;
	right:20px;
	}
.wrongRed{
	background:red;
}
</style>
<div class="right"> 
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="forms_nav">
      <ul>
        <li class="forms_nav_now">评价阈值</li>
      </ul>
      <samp></samp> </div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="pjyz"> 
        <form method="post" id="saveAllForm">
        <!--========评价阈值中间颜色选择开始========= -->
        <div class="pjyz_color" style=" height:53px; width:98%; margin-left:1%;  margin-top:25px;}"> 
        
        <samp>颜色:</samp>
          <ul id="colorUL">
          	<#if colors?? && colors?size &gt; 0>
          		<#list  colors as color>
          			<li>
          			  	<input class="color {valueElement:'myValue'+${(color.rank_code)!},pickerFaceColor:'#00ccff'}" value="<#if color.rank_code=1>优</#if><#if color.rank_code=2>良</#if><#if color.rank_code=3>中</#if><#if color.rank_code=4>差</#if>" readonly="readonly" style="background:'#${(color.rank_color)!}';width:65px; padding:5px 0px; float:left; margin-right:20px; cursor:pointer;border:none;text-align:center;color:#fff"/>
	         			<input id='myValue${(color.rank_code)!}' name="colors" type="hidden" size="6px" value="${(color.rank_color)!}" oldval="${(color.rank_color)!}" onChange="isChangeVal(this)" isc="0">
          			</li>
          		</#list>
          	</#if>
          </ul>
          <div id="saveButton" class="bottom_btn yfpz_btn" style="float:right; margin-right:10px;">
      <a href="javascript:void(0)"  id="saveAll" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确  定</span></span></a>
      <input id="colorChangein" name="colorChangein" type="hidden" value="0" />
    </div>
    <div class="clean"></div>
        </div>
        <div class="shiwai titleName">指标评价</div>
        <!--========评价阈值室外========= -->
        <div class="shinw"> <span class="shiwai"  style="border:none;" >室外</span>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt">2G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb"  title="优:RxLev≥-75dBm的采样点比例≥90%为优
良:RxLev≥-75dBm的采样点比例[85%，90%）为良
中:其余情况为中
差:RxLev≥-75dBm的采样点比例≤80%为差">RXlev(dBm): </div>
              <ul>
	            <#if rateOutside.rxlev.rateValues?? && rateOutside.rxlev.rateValues?size &gt; 0 >
	            <input type='hidden' name='id' value='${(rateOutside.rxlev.id)!}_0_rxlev'/>
	            <#list rateOutside.rxlev.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                <#if rateValues.rank_code!=3>
                  <select id="rxlev_arithmetic_outside${(rateValues.rank_code)!}" name="rxlev_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rxlev_value_outside${(rateValues.rank_code)!}" name="rxlev_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rxLevFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rxlev_ratio" id="rxlevOutside${(rateValues.rank_code)!}" type="text" class="easyui-validatebox" value="${(rateValues.rank_ratio)!}" / style="width:60px;" 
                  data-options="required:true,validType:['normalValue']"
                  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0">
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
	           </#list>
	           </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RxQual≤3的采样点比例≥90%为优
良:RxQual≤3的采样点比例[85%，90%）为良
中:其余情况为中
差:RxQual≤3的采样点比例≤80%为差">RxQual: </div>
              <ul>
              	<#if rateOutside.rxqual.rateValues?? && rateOutside.rxqual.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.rxqual.id)!}_0_rxqual'/>
	            <#list rateOutside.rxqual.rateValues as rateValues>
                <li>
                <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                </span>
	            <#if rateValues.rank_code!=3>
                  <select id="rxqual_arithmetic_outside${(rateValues.rank_code)!}" name="rxqual_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rxqual_value_outside${(rateValues.rank_code)!}" name="rxqual_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;"  data-options="required:true,validType:['rxQualFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rxqual_ratio" type="text" class="easyui-validatebox" id="rxqualOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
				  data-options="required:true,validType:['normalValue']"
                   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
               
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:C/I≥12dBm的采样点比例≥98%为优
良:C/I≥12dBm的采样点比例[96%，98%）为良
中:其余情况为中
差:C/I≥12dBm的采样点比例≤93%为差">C/I: </div>
              <ul>
              	<#if rateOutside.ci.rateValues?? && rateOutside.ci.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ci.id)!}_0_ci'/>
	            <#list rateOutside.ci.rateValues as rateValues>
                <li>
                <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                </span>
	            <#if rateValues.rank_code!=3>
                  <select id="ci_arithmetic_outside${(rateValues.rank_code)!}" name="ci_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ci_value_outside${(rateValues.rank_code)!}" name="ci_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;"  data-options="required:true,validType:['ciFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ci_ratio" type="text" class="easyui-validatebox" id="ciOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
				  data-options="required:true,validType:['normalValue']"
                   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
               
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt">3G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSCP≥-80dBm的采样点比例≥94%为优
良:RSCP≥-80dBm的采样点比例[90%,94%)为良
中:其余情况为中
差:RSCP≥-85dBm的采样点比例≤90%为差">RSCP(dBm): </div>
              <ul>
              	<#if rateOutside.rscp.rateValues?? && rateOutside.rscp.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.rscp.id)!}_0_rscp'/>
	            <#list rateOutside.rscp.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="rscp_arithmetic_outside${(rateValues.rank_code)!}" name="rscp_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rscp_value_outside${(rateValues.rank_code)!}" name="rscp_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rscpFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rscp_ratio" type="text"  class="easyui-validatebox" id="rscpOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
					data-options="required:true,validType:['normalValue']"
                 	oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
					>
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
               	</#list>
               	</#if>
              </ul>
              <div class="clear"></div>
            </div>
            <!-- -->
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:Ec/No≥-10dB的采样点比例≥95%
良:Ec/No≥-10dB的采样点比例[90%,95%)
中:其余情况为中
差:Ec/No≥-10dB的采样点比例≤85%">Ec/No (dB): </div>
              <ul>
              	<#if rateOutside.ecno.rateValues?? && rateOutside.ecno.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ecno.id)!}_0_ecno'/>
	            <#list rateOutside.ecno.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				<#if rateValues.rank_code!=3>
                  <select id="ecno_arithmetic_outside${(rateValues.rank_code)!}"  name="ecno_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ecno_value_outside${(rateValues.rank_code)!}" name="ecno_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ecnoFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ecno_ratio" type="text" class="easyui-validatebox" id="ecnoOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  data-options="required:true,validType:['normalValue']"
                  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:TxPower≤-15dBm的采样点比例≥93%为优
良:TxPower≤-15dBm的采样点比例[85%，93%）为良
中:其余情况为中
差:TxPower≤-15dBm的采样点比例≤75%为差">TxPower(dBm): </div>
              <ul>
              	<#if rateOutside.txpower.rateValues?? && rateOutside.txpower.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.txpower.id)!}_0_txpower'/>
	            <#list rateOutside.txpower.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="txpower_arithmetic_outside${(rateValues.rank_code)!}" name="txpower_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"   oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                     <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="txpower_value_outside${(rateValues.rank_code)!}" name="txpower_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['txpowerFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="txpower_ratio" type="text" class="easyui-validatebox" id="txpowerOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </li>
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
                    <div class="shiwai_2g">
            <div class="shinwai_2g_bt" style="font-size:12px;">3G数据:</div>
            <div class="clear"></div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP上传平均速率≥2048;且FTP上传速率分布≤5%
良:FTP上传平均速率[1536,2048);且FTP上传速率分布(5%,10%]
中:其余情况为中
差:FTP上传平均速率≤700Kbps或者FTP上传速率分布≥20%">FTP上行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateOutside.ftpup.rateValues?? && rateOutside.ftpup.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ftpup.id)!}_0_ftpup'/>
	            <#list rateOutside.ftpup.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
				  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpup_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpupAvgOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
                  	   oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpup_arithmetic_outside${(rateValues.rank_code)!}" name="ftpup_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"   oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpup_value_outside${(rateValues.rank_code)!}" name="ftpup_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpupFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpup_ratio" type="text" class="easyui-validatebox" id="ftpupValueOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP下载平均速率≥3584;且FTP下载速率分布≤4%
良:FTP下载平均速率[2560,3584);且FTP下载速率分布(4%,8%]
中:其余情况为中
差:FTP下载平均速率≤1536或者FTP下载速率分布≥15%">FTP下行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateOutside.ftpdown.rateValues?? && rateOutside.ftpdown.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ftpdown.id)!}_0_ftpdown'/>
	            <#list rateOutside.ftpdown.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpdown_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpdownAvgOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
                  	  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpdown_arithmetic_outside${(rateValues.rank_code)!}" name="ftpdown_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpdown_value_outside${(rateValues.rank_code)!}" name="ftpdown_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpdownFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpdown_ratio" type="text" class="easyui-validatebox" id="ftpdownValueOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
 	 	</div>
 	 	
 	 	
 	 	<!--4G 室外数据 开始 -->
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt">4G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSRP≥-80dBm的采样点比例≥94%为优
良:RSRP≥-80dBm的采样点比例[90%,94%)为良
中:其余情况为中
差:RSRP≥-85dBm的采样点比例≤90%为差">RSRP(dBm): </div>
              <ul>
              	<#if rateOutside.rsrp?? && rateOutside.rsrp.rateValues?? && rateOutside.rsrp.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.rsrp.id)!}_0_rsrp'/>
	            <#list rateOutside.rsrp.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="rsrp_arithmetic_outside${(rateValues.rank_code)!}" name="rsrp_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rsrp_value_outside${(rateValues.rank_code)!}" name="rsrp_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rsrpFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rsrp_ratio" type="text"  class="easyui-validatebox" id="rsrpOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
					data-options="required:true,validType:['normalValue']"
                 	oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
					>
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
               	</#list>
               	</#if>
              </ul>
              <div class="clear"></div>
            </div>
            <!-- -->
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSRQ≥-10dB的采样点比例≥95%
良:RSRQ≥-10dB的采样点比例[90%,95%)
中:其余情况为中
差:RSRQ≥-10dB的采样点比例≤85%">RSRQ (dB): </div>
              <ul>
              	<#if rateOutside.rsrq?? && rateOutside.rsrq.rateValues?? && rateOutside.rsrq.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.rsrq.id)!}_0_rsrq'/>
	            <#list rateOutside.rsrq.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				<#if rateValues.rank_code!=3>
                  <select id="rsrq_arithmetic_outside${(rateValues.rank_code)!}"  name="rsrq_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rsrq_value_outside${(rateValues.rank_code)!}" name="rsrq_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rsrqFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rsrq_ratio" type="text" class="easyui-validatebox" id="rsrqOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  data-options="required:true,validType:['normalValue']"
                  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:SINR≥20dBm的采样点比例≥93%为优
良:SINR≥20dBm的采样点比例[85%，93%）为良
中:其余情况为中
差:SINR≥20dBm的采样点比例≤75%为差">SINR(dBm): </div>
              <ul>
              	<#if rateOutside.sinr?? && rateOutside.sinr.rateValues?? && rateOutside.sinr.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.sinr.id)!}_0_sinr'/>
	            <#list rateOutside.sinr.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="sinr_arithmetic_outside${(rateValues.rank_code)!}" name="sinr_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"   oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                     <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="sinr_value_outside${(rateValues.rank_code)!}" name="sinr_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['sinrFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="sinr_ratio" type="text" class="easyui-validatebox" id="sinrOutside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </li>
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt" style="font-size:12px;">4G数据:</div>
            <div class="clear"></div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP上传平均速率≥2048;且FTP上传速率分布≤5%
良:FTP上传平均速率[1536,2048);且FTP上传速率分布(5%,10%]
中:其余情况为中
差:FTP上传平均速率≤700Kbps或者FTP上传速率分布≥20%">FTP上行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateOutside.ftpup4G.rateValues?? && rateOutside.ftpup4G.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ftpup4G.id)!}_0_ftpup4G'/>
	            <#list rateOutside.ftpup4G.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
				  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpup4G_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpupAvgOutside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
                  	   oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpup4G_arithmetic_outside${(rateValues.rank_code)!}" name="ftpup4G_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"   oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpup4G_value_outside${(rateValues.rank_code)!}" name="ftpup4G_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpupLteFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpup4G_ratio" type="text" class="easyui-validatebox" id="ftpupValueOutside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP下载平均速率≥3584;且FTP下载速率分布≤4%
良:FTP下载平均速率[2560,3584);且FTP下载速率分布(4%,8%]
中:其余情况为中
差:FTP下载平均速率≤1536或者FTP下载速率分布≥15%">FTP下行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateOutside.ftpdown4G.rateValues?? && rateOutside.ftpdown4G.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateOutside.ftpdown4G.id)!}_0_ftpdown4G'/>
	            <#list rateOutside.ftpdown4G.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpdown4G_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpdownAvgOutside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
                  	  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpdown4G_arithmetic_outside${(rateValues.rank_code)!}" name="ftpdown4G_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpdown4G_value_outside${(rateValues.rank_code)!}" name="ftpdown4G_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpdownLteFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpdown4G_ratio" type="text" class="easyui-validatebox" id="ftpdownValueOutside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <!--4G 室外数据 结束 -->
          
          
          
        </div>
        <div class="clear"></div>
        <div class="shinw"> <span class="shiwai">室内</span>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt">2G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RxLev≥-75dBm的采样点比例≥90%为优
良:RxLev≥-75dBm的采样点比例[85%，90%）为良
中:其余情况为中
差:RxLev≥-75dBm的采样点比例≤80%为差">RXlev(dBm): </div>
              <ul>
              	<#if rateInside.rxlev.rateValues?? && rateInside.rxlev.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.rxlev.id)!}_1_rxlev'/>
	            <#list rateInside.rxlev.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="rxlev_arithmetic_inside${(rateValues.rank_code)!}" name="rxlev_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rxlev_value_inside${(rateValues.rank_code)!}" name="rxlev_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rxLevFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rxlev_ratio" type="text" class="easyui-validatebox" id="rxlevInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
	                   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RxQual≤3的采样点比例≥90%为优
良:RxQual≤3的采样点比例[85%，90%）为良
中:其余情况为中
差:RxQual≤3的采样点比例≤80%为差">RxQual: </div>
              <ul>
              	<#if rateInside.rxqual.rateValues?? && rateInside.rxqual.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.rxqual.id)!}_1_rxqual'/>
	            <#list rateInside.rxqual.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="rxqual_arithmetic_inside${(rateValues.rank_code)!}" name="rxqual_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rxqual_value_inside${(rateValues.rank_code)!}" name="rxqual_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rxQualFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rxqual_ratio" type="text" class="easyui-validatebox" id="rxqualInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                %
                </#if>
                <#if rateValues.rank_code==3>
                  	<div class="ylz" >优良差都不符合则为中</div>
                  </#if>
                </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:C/I≥12dBm的采样点比例≥98%为优
良:C/I≥12dBm的采样点比例[96%，98%）为良
中:其余情况为中
差:C/I≥12dBm的采样点比例≤93%为差">C/I: </div>
              <ul>
              	<#if rateInside.ci.rateValues?? && rateInside.ci.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.ci.id)!}_1_ci'/>
	            <#list rateInside.ci.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="ci_arithmetic_inside${(rateValues.rank_code)!}" name="ci_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ci_value_inside${(rateValues.rank_code)!}" name="ci_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ciFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ci_ratio" type="text" class="easyui-validatebox" id="ciInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                %
                </#if>
                <#if rateValues.rank_code==3>
                  	<div class="ylz" >优良差都不符合则为中</div>
                  </#if>
                </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
        <div class="shiwai_2g">
            <div class="shinwai_2g_bt">3G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSCP≥-80dBm的采样点比例≥94%为优
良:RSCP≥-80dBm的采样点比例[90%,94%)为良
中:其余情况为中
差:RSCP≥-85dBm的采样点比例≤90%为差">RSCP(dBm): </div>
              <ul>
              	<#if rateInside.rscp.rateValues?? && rateInside.rscp.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.rscp.id)!}_1_rscp'/>
	            <#list rateInside.rscp.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				  <#if rateValues.rank_code!=3>
                  <select id="rscp_arithmetic_inside${(rateValues.rank_code)!}" name="rscp_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rscp_value_inside${(rateValues.rank_code)!}" name="rscp_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rscpFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rscp_ratio" type="text" class="easyui-validatebox" id="rscpInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <!-- -->
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:Ec/No≥-10dB的采样点比例≥95%
良:Ec/No≥-10dB的采样点比例[90%,95%)
中:其余情况为中
差:Ec/No≥-10dB的采样点比例≤85%">Ec/No (dB): </div>
              <ul>
              	<#if rateInside.ecno.rateValues?? && rateInside.ecno.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.ecno.id)!}_1_ecno'/>
	            <#list rateInside.ecno.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="ecno_arithmetic_inside${(rateValues.rank_code)!}" name="ecno_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ecno_value_inside${(rateValues.rank_code)!}" name="ecno_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ecnoFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ecno_ratio" type="text" class="easyui-validatebox" id="ecnoInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:TxPower≤-15dBm的采样点比例≥93%为优
良:TxPower≤-15dBm的采样点比例[85%，93%）为良
中:其余情况为中
差:TxPower≤-15dBm的采样点比例≤75%为差">TxPower(dBm): </div>
              <ul>
              	<#if rateInside.txpower.rateValues?? && rateInside.txpower.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.txpower.id)!}_1_txpower'/>
	            <#list rateInside.txpower.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="txpower_arithmetic_inside${(rateValues.rank_code)!}" name="txpower_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="txpower_value_inside${(rateValues.rank_code)!}" name="txpower_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['txpowerFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="txpower_ratio" type="text" class="easyui-validatebox" id="txpowerInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt" style="font-size:12px;">3G数据:</div>
            <div class="clear"></div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP上传平均速率≥2048;且FTP上传速率分布≤5%
良:FTP上传平均速率[1536,2048);且FTP上传速率分布(5%,10%]
中:其余情况为中
差:FTP上传平均速率≤700Kbps或者FTP上传速率分布≥20%">FTP上行(Kbps): </div>
              <ul style="width:910px;">
             	<#if rateInside.ftpup.rateValues?? && rateInside.ftpup.rateValues?size &gt; 0 >
             	<input type='hidden' name='id' value='${(rateInside.ftpup.id)!}_1_ftpup'/>
	            <#list rateInside.ftpup.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  	<#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpup_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpupAvgInside${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;" 
                  	  data-options="required:true,validType:['ftpMax']"
                  	  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpup_arithmetic_inside${(rateValues.rank_code)!}" name="ftpup_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpup_value_inside${(rateValues.rank_code)!}" name="ftpup_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpupFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpup_ratio" type="text"  class="easyui-validatebox" id="ftpupValueInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP下载平均速率≥3584;且FTP下载速率分布≤4%
良:FTP下载平均速率[2560,3584);且FTP下载速率分布(4%,8%]
中:其余情况为中
差:FTP下载平均速率≤1536或者FTP下载速率分布≥15%">FTP下行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateInside.ftpdown.rateValues?? && rateInside.ftpdown.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.ftpdown.id)!}_1_ftpdown'/>
	            <#list rateInside.ftpdown.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpdown_avg" type="text"  class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpdownAvgInside${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
	                  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpdown_arithmetic_inside${(rateValues.rank_code)!}" name="ftpdown_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpdown_value_inside${(rateValues.rank_code)!}" name="ftpdown_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpdownFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpdown_ratio" type="text"  class="easyui-validatebox" id="ftpdownValueInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
	                  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
  		</div>
		          <div class="shiwai_2g">
            <div class="shinwai_2g_bt">4G</div>
            <div class="clear"></div>
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSRP≥-80dBm的采样点比例≥94%为优
良:RSRP≥-80dBm的采样点比例[90%,94%)为良
中:其余情况为中
差:RSRP≥-85dBm的采样点比例≤90%为差">RSRP(dBm): </div>
              <ul>
              	<#if rateInside.rsrp?? && rateInside.rsrp.rateValues?? && rateInside.rsrp.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.rsrp.id)!}_1_rsrp'/>
	            <#list rateInside.rsrp.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
				  </span>
				  <#if rateValues.rank_code!=3>
                  <select id="rsrp_arithmetic_inside${(rateValues.rank_code)!}" name="rsrp_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rsrp_value_inside${(rateValues.rank_code)!}" name="rsrp_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rsrpFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rsrp_ratio" type="text" class="easyui-validatebox" id="rsrpInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
        
            <!--室内 4G数据 开始-->
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:RSRQ≥-10dB的采样点比例≥95%
良:RSRQ≥-10dB的采样点比例[90%,95%)
中:其余情况为中
差:RSRQ≥-10dB的采样点比例≤85%">RSRQ (dB): </div>
              <ul>
              	<#if rateInside.rsrq.rateValues?? && rateInside.rsrq.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.rsrq.id)!}_1_rsrq'/>
	            <#list rateInside.rsrq.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
              	<#if rateValues.rank_code!=3>
                  <select id="rsrq_arithmetic_inside${(rateValues.rank_code)!}" name="rsrq_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}" isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="rsrq_value_inside${(rateValues.rank_code)!}" name="rsrq_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['rsrqFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="rsrq_ratio" type="text" class="easyui-validatebox" id="rsrqInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            
            <div class="shinw_div">
              <div class="shiwai_zb" title="优:SINR≥20dBm的采样点比例≥93%为优
良:SINR≥20dBm的采样点比例[85%，93%）为良
中:其余情况为中
差:SINR≥20dBm的采样点比例≤75%为差">SINR(dBm): </div>
              <ul>
              	<#if rateInside.sinr.rateValues?? && rateInside.sinr.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.sinr.id)!}_1_sinr'/>
	            <#list rateInside.sinr.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3>
                  <select id="sinr_arithmetic_inside${(rateValues.rank_code)!}" name="sinr_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="sinr_value_inside${(rateValues.rank_code)!}" name="sinr_value" type="text" class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['sinrFigure']"  oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="sinr_ratio" type="text" class="easyui-validatebox" id="sinrInside${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="shiwai_2g">
            <div class="shinwai_2g_bt" style="font-size:12px;">4G数据:</div>
            <div class="clear"></div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP上传平均速率≥2048;且FTP上传速率分布≤5%
良:FTP上传平均速率[1536,2048);且FTP上传速率分布(5%,10%]
中:其余情况为中
差:FTP上传平均速率≤700Kbps或者FTP上传速率分布≥20%">FTP上行(Kbps): </div>
              <ul style="width:910px;">
             	<#if rateInside.ftpup4G.rateValues?? && rateInside.ftpup4G.rateValues?size &gt; 0 >
             	<input type='hidden' name='id' value='${(rateInside.ftpup4G.id)!}_1_ftpup4G'/>
	            <#list rateInside.ftpup4G.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  	<#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpup4G_avg" type="text" class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpupAvgInside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;" 
                  	  data-options="required:true,validType:['ftpMax']"
                  	  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpup_arithmetic_inside4G${(rateValues.rank_code)!}" name="ftpup4G_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'" oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpup4G_value_inside${(rateValues.rank_code)!}" name="ftpup4G_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpupLteFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpup4G_ratio" type="text"  class="easyui-validatebox" id="ftpupValueInside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
                  	   oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <div class="shinw_div" style="width:1030px;">
              <div class="shiwai_zb" title="优:FTP下载平均速率≥3584;且FTP下载速率分布≤4%
良:FTP下载平均速率[2560,3584);且FTP下载速率分布(4%,8%]
中:其余情况为中
差:FTP下载平均速率≤1536或者FTP下载速率分布≥15%">FTP下行(Kbps): </div>
              <ul style="width:910px;">
              	<#if rateInside.ftpdown4G.rateValues?? && rateInside.ftpdown4G.rateValues?size &gt; 0 >
              	<input type='hidden' name='id' value='${(rateInside.ftpdown4G.id)!}_1_ftpdown4G'/>
	            <#list rateInside.ftpdown4G.rateValues as rateValues>
                <li>
                  <span style="background:${(rateValues.rank_color)!};width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-right:3px; margin-top:3px;">
                  	<#if rateValues.rank_code==1>优</#if>
                  	<#if rateValues.rank_code==2>良</#if>
                  	<#if rateValues.rank_code==3>中</#if>
                  	<#if rateValues.rank_code==4>差</#if>
                  </span>
                  <#if rateValues.rank_code!=3><div style="float:left;margin-top:1px;">平均速率</div><input name="ftpdown4G_avg" type="text"  class="easyui-validatebox <#if rateValues.rank_code==1>avgMax</#if>" id="ftpdownAvgInside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_avg)!}" / style="width:86px; margin-right:5px;float:left;margin-top:3px;"
                  	  data-options="required:true,validType:['ftpMax']"
	                  oldval="${(rateValues.rank_avg)!}" onChange="isChangeVal(this)" isc="0"
                  ><#if rateValues.rank_code==1||rateValues.rank_code==2><div class="pjyz_wz">且</div></#if><#if rateValues.rank_code==4><div class="pjyz_wz">或</div></#if><div style="float:left;margin-top:1px;">速率</div><select id="ftpdown4G_arithmetic_inside${(rateValues.rank_code)!}" name="ftpdown4G_arithmetic" class="easyui-combobox" style="width:50px;" data-options="editable:false,panelHeight: 'auto'"  oldval="${(rateValues.rank_arithmetic)!}"  isc="0">
                  	 <option value="1" <#if rateValues.rank_arithmetic==1>selected="selected"</#if>>></option>
                  	 <option value="2" <#if rateValues.rank_arithmetic==2>selected="selected"</#if>>≥</option>
                  	 <option value="3" <#if rateValues.rank_arithmetic==3>selected="selected"</#if>><</option>
                  	 <option value="4" <#if rateValues.rank_arithmetic==4>selected="selected"</#if>>≤</option>
                  </select>
                  <input id="ftpdown4G_value_inside${(rateValues.rank_code)!}" name="ftpdown4G_value" type="text"  class="easyui-validatebox" value="${(rateValues.rank_value)!}" / style="width:50px; margin-right:8px;" data-options="required:true,validType:['ftpdownLteFigure']" oldval="${(rateValues.rank_value)!}" onChange="isChangeVal(this)" isc="0">占比<input name="ftpdown4G_ratio" type="text"  class="easyui-validatebox" id="ftpdownValueInside4G${(rateValues.rank_code)!}" value="${(rateValues.rank_ratio)!}" / style="width:60px;"
                  	  data-options="required:true,validType:['normalValue']"
	                  oldval="${(rateValues.rank_ratio)!}" onChange="isChangeVal(this)" isc="0"
                  >
                  %
                  </#if>
                  <#if rateValues.rank_code==3>
                  	<div class="ylz" style="width:388px;">优良差都不符合则为中</div>
                  </#if>
                  </li>
                </#list>
                </#if>
              </ul>
              <div class="clear"></div>
            </div>
            <!--室内  4G数据  结束-->
            
            
            <div class="clear"></div>
      	</div>
          <br/>
        </div>
        
    <div class="clear"></div>
    <!--========计分规则========= -->
    <div class="shiwai titleName">综合评价</div>
        <div class="pjyz_jfgz" style="border:none;">
          <div class="pjyz_jfgz_left" style="margin-right:100px;"> <span><samp>计分规则:</samp>（示例:若指标级别为优，计2分，良计5分，中计8分，差计11分）</span>
            <ul>
            <#if scoreRule??&&scoreRule?size &gt; 0>
              <li>
              <#list scoreRule as score>
              	<span style="background:<#if score.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if score.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if score.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if score.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px; display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if score.rank_code == 1>优</#if>
              	<#if score.rank_code == 2>良</#if>
              	<#if score.rank_code == 3>中</#if>
              	<#if score.rank_code == 4>差</#if>
              	</span>
                <input id="${'rank_code'+(score.rank_code)!}" name="rank_score" class="easyui-validatebox" data-options="required:true,validType:['integration']" type="text" value="${(score.rank_score)!}" oldval="${(score.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </li>
            </#if>
            </ul>
          </div>
          <!-- -->
          <div class="pjyz_jfgz_left"> <span><samp>评分规则:</samp>（该项目所有指标分值的平均值向上整取为该项目的分值）</span>
            <ul>
            <#if evaluateRule?? &&evaluateRule?size &gt; 0>
              <li>
              <#if evaluateRule.A?? &&evaluateRule.A?size &gt; 0>
              <#list evaluateRule.A as evaluate>
              <span style = " background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>; width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优+</#if>
              	<#if evaluate.rank_code==2>良+</#if>
              	<#if evaluate.rank_code==3>中+</#if>
              	<#if evaluate.rank_code==4>差+</#if>
              	</span>
                <input id="evaluate_scoreA<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreA" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
              <li>
              <#if evaluateRule.B?? &&evaluateRule.B?size &gt; 0>
              <#list evaluateRule.B as evaluate>
                <span style = "background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优</#if>
              	<#if evaluate.rank_code==2>良</#if>
              	<#if evaluate.rank_code==3>中</#if>
              	<#if evaluate.rank_code==4>差</#if>
              	</span>
                <input id="evaluate_scoreB<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreB" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
              <li>
              <#if evaluateRule.C?? &&evaluateRule.C?size &gt; 0>
              <#list evaluateRule.C as evaluate>
              <span style = " background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优-</#if>
              	<#if evaluate.rank_code==2>良-</#if>
              	<#if evaluate.rank_code==3>中-</#if>
              	<#if evaluate.rank_code==4>差-</#if>
              	</span>
                <input id="evaluate_scoreC<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreC" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
            </#if>
            </ul>
          </div>
          <div class="clear"></div>
        </div>
<div class="pjyz_xzgz" style="margin-bottom:30px;">
<samp>修正规则:</samp>
<div>
<span>项目修正:</span>
<ul>
<#if revisRule??&&revisRule?size &gt;0>
	<#list revisRule as revis>
	<#if revis.revis_type==1>
		<li>若项目等级为<#if revis.id==1>优</#if><#if revis.id==2>良</#if>（包含+/-）,但项目中有任一指标为差，该项目在原等级基础上降<input name="revis_left" type="hidden" value="${(revis.left_rate)!}" /><input name="revis_right" type="hidden" value="${(revis.right_rate)!}"/><input name="revis_level" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.revis_level)!}" oldval="${(revis.revis_level)!}" onChange="isChangeVal(this)" isc="0"/>档</li>
	</#if>
	</#list>
</#if>
</ul>
</div>
<div class="clear"></div>
<div>
<span>自由模式修正:</span>
<ul>
<#if revisRule??&&revisRule?size &gt;0>
	<#list revisRule as revis>
	<#if revis.revis_type==2>
		<#if revis_index != 11>
		<li>如果3G网络测试点所占比例在<input id="${'left'+(revis.id)!}" name="revis_left" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.left_rate)!}"  oldval="${(revis.left_rate)!}" onChange="isChangeVal(this)" isc="0"/>%　～<input id="${'right'+(revis.id)!}" name="revis_right" type="text" class="easyui-validatebox" <#if revis.id!=3>data-options="required:true,validType:['integration']"</#if><#if revis.id==3>data-options="required:true,validType:['absolutely']"</#if> value="${(revis.right_rate)!}" oldval="${(revis.right_rate)!}" onChange="isChangeVal(this)" isc="0"/>%  该项目在原等级基础上降<input id="${(revis.id)!}" name="revis_level" class="easyui-validatebox" data-options="required:true,validType:['integration']" type="text"  value="${(revis.revis_level)!}"  oldval="${(revis.revis_level)!}" onChange="isChangeVal(this)" isc="0"/>档</li>
		</#if>
		<#if revis_index == 11>
		<li>如果3G网络测试点所占比例在<input id="${'left'+(revis.id)!}" name="revis_left" type="hidden" value="${(revis.left_rate)!}"/><input id="right${(revis.id)!}" name="revis_right" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.right_rate)!}"  oldval="${(revis.right_rate)!}" onChange="isChangeVal(this)" isc="0"/>% <input name="revis_level" type="hidden" value="${(revis.revis_level)!}"/>以下，该项目定性为差-</li>
		</#if>
	</#if>
	</#list>
</#if>
</ul>
</div>
<div class="clear"></div>
</div>
       
      </div>
      <div class="clear"></div>
      </div>
      <!--========阈值配置中间结束========= --> 
      </form>
    <div class="all_list_bottom"><span></span><samp></samp></div>
    </div>
  </div>
  <!--========阈值配置结束========= --> 
</div>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/workorder/rate.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jscolor/jscolor.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/workorder/saveAll.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/workorder/integration.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var isChange = 0;
	</script>
</@script>