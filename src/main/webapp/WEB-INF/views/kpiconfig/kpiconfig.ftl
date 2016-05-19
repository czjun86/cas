<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
	
</@head>
<@body>
<div class="right">
  <!--========阈值配置开始========= -->
  <div class="forms">
    <!--========阈值配置头部========= -->
    <div class="forms_nav">
      <ul>
        <li class="forms_nav_now">阈值配置</li>
      </ul>
      <samp></samp> </div>
    <!--========阈值配置头部结束========= -->
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yfpz">
        <!--========阈值配置中间颜色选择开始========= -->
        <div class="yfpz_color"> 
	        <samp>颜色：</samp> 
	        <form  method="post" id="colorForm">
	        <ul class="colorul">
	        <#if colors?? && colors?size &gt; 0>
	        	<#list colors as color>
	        		<input class="color" value="${(color.colourcode)!}" readonly="readonly" name="color" style="border:0px;background:#${(color.colourcode)!};width:65px;height:26px;line-height:26px;">
		       	</#list>
	        </#if>
	        </ul>
	         <div class="bottom_btn">
	      	<!--<div class="btn" id="savecolor">确  定</div>-->
	        
	        <a href="javascript:void(0)"  id="savecolor" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a>
	        </div>
	        </form>
        </div>
        <!--========阈值配置中间颜色选择结束========= -->
        <!--==室外信息开始==-->
        <div class="scene_title">室外</div>

        <!--室外的信息 -->
        <form  method="post" id="dataForm" class="form">
        <div class="yfpz_2g" >
          <h3>2G ：</h3>
          <ul>
            <!--<li>
              <h4>TxPower(dBm ):</h4>
              <#if (kpiOutside.txPowerfor2g)?? && (kpiOutside.txPowerfor2g.kpiValues)?? && (kpiOutside.txPowerfor2g.kpiValues)?size &gt; 0>
              <#list kpiOutside.txPowerfor2g.kpiValues as txPowerfor2g>
              <div style="float:left"><input name="txPowerfor2g" type="text" class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(txPowerfor2g.kpiValue)!}" maxlength="7"  oldval="${(txPowerfor2g.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if txPowerfor2g_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>-->  
             <li>
              <h4>RXlev(dBm ): </h4>
             <#if (kpiOutside.rxlev)?? && (kpiOutside.rxlev.kpiValues)?? && (kpiOutside.rxlev.kpiValues)?size &gt; 0>
              <#list kpiOutside.rxlev.kpiValues as rxlev>
               <div style="float:left"><input name="rxlev" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rxlev.kpiValue)!}"  maxlength="7"  oldval="${(rxlev.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if rxlev_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>
            <li>
              <h4>RxQual: </h4>
              <#if (kpiOutside.rxQual)?? && (kpiOutside.rxQual.kpiValues)?? && (kpiOutside.rxQual.kpiValues)?size &gt; 0>
              <#list kpiOutside.rxQual.kpiValues as rxQual>
               <div style="float:left"><input name="rxQual" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rxQual.kpiValue)!}"  maxlength="7"   oldval="${(rxQual.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if rxQual_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>
            <li>
              <h4>C/I(dB): </h4>
	           <#if (kpiOutside.ci)?? && (kpiOutside.ci.kpiValues)?? && (kpiOutside.ci.kpiValues)?size &gt; 0>
	          <#list kpiOutside.ci.kpiValues as ci>
	           <div style="float:left"><input name="ci" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ci.kpiValue)!}"   maxlength="7"   oldval="${(ci.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ci_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
            <!--<li>
              <h4>MOS: </h4>
	           <#if (kpiOutside.mos)?? && (kpiOutside.mos.kpiValues)?? && (kpiOutside.mos.kpiValues)?size &gt; 0>
	          <#list kpiOutside.mos.kpiValues as mos>
	           <div style="float:left"><input name="mos" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(mos.kpiValue)!}"   maxlength="7"   oldval="${(mos.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if mos_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>-->                   
          </ul>
        </div>
        <div class="clear"></div>
        <div class="yfpz_2g yfpz_2g_ts">
          <h3>3G ：</h3>
	          <ul style="margin-bottom:40px;">
	            <li>
	              <h4>RSCP(dBm ):</h4>
	              <#if (kpiOutside.rscp)?? && (kpiOutside.rscp.kpiValues)?? && (kpiOutside.rscp.kpiValues)?size &gt; 0>
		          <#list kpiOutside.rscp.kpiValues as rscp>
		          <div style="float:left"><input name="rscp" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rscp.kpiValue)!}"   maxlength="7"   oldval="${(rscp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if rscp_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>  
	             <li>
	              <h4>Ec/No(dB): </h4>
	              <#if (kpiOutside.ecno)?? && (kpiOutside.ecno.kpiValues)?? && (kpiOutside.ecno.kpiValues)?size &gt; 0>
		          <#list kpiOutside.ecno.kpiValues as ecno>
		          <div style="float:left"><input name="ecno" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ecno.kpiValue)!}"   maxlength="7"   oldval="${(ecno.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if ecno_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>
	            <li>
	              <h4>TxPower(dBm): </h4>
	              <#if (kpiOutside.txPowerfor3g)?? && (kpiOutside.txPowerfor3g.kpiValues)?? && (kpiOutside.txPowerfor3g.kpiValues)?size &gt; 0>
		          <#list kpiOutside.txPowerfor3g.kpiValues as txPowerfor3g>
		          <div style="float:left"><input name="txPowerfor3g" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(txPowerfor3g.kpiValue)!}"   maxlength="7"   oldval="${(txPowerfor3g.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if txPowerfor3g_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>     
	          </ul>
			<div style="width: 100%; display: block; float: left; height: 10px;"></div>
          	<h5 style="left:-5px;">3G数据： </h5>
            <ul style="position:relative;top:-35px;">
            <li>
              <h4>FTP上行(Kbps): </h4>
	           <#if (kpiOutside.ftpUp)?? && (kpiOutside.ftpUp.kpiValues)?? && (kpiOutside.ftpUp.kpiValues)?size &gt; 0>
	          <#list kpiOutside.ftpUp.kpiValues as ftpUp>
	          <div style="float:left"><input name="ftpUp" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpUp.kpiValue)!}"   maxlength="7"   oldval="${(ftpUp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpUp_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
             <li>
              <h4>FTP下行(Kbps):</h4>
              <#if (kpiOutside.ftpDown)?? && (kpiOutside.ftpDown.kpiValues)?? && (kpiOutside.ftpDown.kpiValues)?size &gt; 0>
	          <#list kpiOutside.ftpDown.kpiValues as ftpDown>
	          <div style="float:left"><input name="ftpDown" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpDown.kpiValue)!}"   maxlength="7"   oldval="${(ftpDown.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpDown_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
          <div class="clear"></div>
          <!--=阈值配置数据开始=== -->
          <div class="yfpz_sj">
           <ul style="position:relative;top:-35px;">
            <li><h4>PING丢包率(%): </h4>
            <#if (kpiOutside.pingLoss)?? && (kpiOutside.pingLoss.kpiValues)?? && (kpiOutside.pingLoss.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingLoss.kpiValues as pingLoss>
	          <div style="float:left"><input name="pingLoss" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingLoss.kpiValue)!}"   maxlength="7"   oldval="${(pingLoss.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingLoss_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING最大延迟(ms): </h4>
            <#if (kpiOutside.pingMaxDelay)?? && (kpiOutside.pingMaxDelay.kpiValues)?? && (kpiOutside.pingMaxDelay.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingMaxDelay.kpiValues as pingMaxDelay>
	         <div style="float:left"> <input name="pingMaxDelay" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMaxDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingMaxDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMaxDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>PING最小延迟(ms): </h4>
            <#if (kpiOutside.pingMinDelay)?? && (kpiOutside.pingMinDelay.kpiValues)?? && (kpiOutside.pingMinDelay.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingMinDelay.kpiValues as pingMinDelay>
	          <div style="float:left"><input name="pingMinDelay" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMinDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingMinDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMinDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING平均延迟(ms): </h4>
             <#if (kpiOutside.pingAvgDelay)?? && (kpiOutside.pingAvgDelay.kpiValues)?? && (kpiOutside.pingAvgDelay.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingAvgDelay.kpiValues as pingAvgDelay>
	          <div style="float:left"><input name="pingAvgDelay" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingAvgDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingAvgDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingAvgDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最大响应时间(ms): </h4>
            <#if (kpiOutside.httpMaxResponseTime)?? && (kpiOutside.httpMaxResponseTime.kpiValues)?? && (kpiOutside.httpMaxResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMaxResponseTime.kpiValues as httpMaxResponseTime>
	          <div style="float:left"><input name="httpMaxResponseTime" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最小响应时间(ms):  </h4>
	         <#if (kpiOutside.httpMinResponseTime)?? && (kpiOutside.httpMinResponseTime.kpiValues)?? && (kpiOutside.httpMinResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMinResponseTime.kpiValues as httpMinResponseTime>
	          <div style="float:left"><input name="httpMinResponseTime" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpMinResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
             <ul style="position:relative;top:-35px;">
            <li><h4>HTTP平均响应时间(ms): </h4>
            <#if (kpiOutside.httpAvgResponseTime)?? && (kpiOutside.httpAvgResponseTime.kpiValues)?? && (kpiOutside.httpAvgResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpAvgResponseTime.kpiValues as httpAvgResponseTime>
	          <div style="float:left"><input name="httpAvgResponseTime" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最大下载速度(Kbps): </h4>
            <#if (kpiOutside.httpMaxDownloadSpeed)?? && (kpiOutside.httpMaxDownloadSpeed.kpiValues)?? && (kpiOutside.httpMaxDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMaxDownloadSpeed.kpiValues as httpMaxDownloadSpeed>
	          <div style="float:left"><input name="httpMaxDownloadSpeed" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
            <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最小下载速度(Kbps): </h4>
            <#if (kpiOutside.httpMinDownloadSpeed)?? && (kpiOutside.httpMinDownloadSpeed.kpiValues)?? && (kpiOutside.httpMinDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMinDownloadSpeed.kpiValues as httpMinDownloadSpeed>
	          <div style="float:left"><input name="httpMinDownloadSpeed" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpMinDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP平均下载速度(Kbps): </h4>
            <#if (kpiOutside.httpAvgDownloadSpeed)?? && (kpiOutside.httpAvgDownloadSpeed.kpiValues)?? && (kpiOutside.httpAvgDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpAvgDownloadSpeed.kpiValues as httpAvgDownloadSpeed>
	         <div style="float:left"><input name="httpAvgDownloadSpeed" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
         </div>
          </div>
          <div class="clear"></div>
          <div class="yfpz_2g yfpz_2g_ts">
          	<h3>4G： </h3>
	          <ul style="margin-bottom:40px;">
	            <li>
	              <h4>RSRP(dBm ):</h4>
	              <#if (kpiOutside.rsrp)?? && (kpiOutside.rsrp.kpiValues)?? && (kpiOutside.rsrp.kpiValues)?size &gt; 0>
		          <#list kpiOutside.rsrp.kpiValues as rsrp>
		          <div style="float:left"><input name="rsrp" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rsrp.kpiValue)!}"   maxlength="7"   oldval="${(rsrp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if rsrp_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>  
	             <li>
	              <h4>RSRQ(dBm): </h4>
	              <#if (kpiOutside.rsrq)?? && (kpiOutside.rsrq.kpiValues)?? && (kpiOutside.rsrq.kpiValues)?size &gt; 0>
		          <#list kpiOutside.rsrq.kpiValues as rsrq>
		          <div style="float:left"><input name="rsrq" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rsrq.kpiValue)!}"   maxlength="7"   oldval="${(rsrq.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if rsrq_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>
	            <li>
	              <h4>SINR(dBm): </h4>
	              <#if (kpiOutside.sinr)?? && (kpiOutside.sinr.kpiValues)?? && (kpiOutside.sinr.kpiValues)?size &gt; 0>
		          <#list kpiOutside.sinr.kpiValues as sinr>
		          <div style="float:left"><input name="sinr" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(sinr.kpiValue)!}"   maxlength="7"   oldval="${(sinr.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if sinr_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>     
	          </ul>
			<div style="width: 100%; display: block; float: left; height: 10px;"></div>
          	<h5 style="left:-5px;">4G数据： </h5>
            <ul style="position:relative;top:-35px;">
            <li>
              <h4>FTP上行(Kbps): </h4>
	           <#if (kpiOutside.ftpUp4G)?? && (kpiOutside.ftpUp4G.kpiValues)?? && (kpiOutside.ftpUp4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.ftpUp4G.kpiValues as ftpUp4G>
	          <div style="float:left"><input name="ftpUp4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpUp4G.kpiValue)!}"   maxlength="7"   oldval="${(ftpUp4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpUp4G_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
             <li>
              <h4>FTP下行(Kbps):</h4>
              <#if (kpiOutside.ftpDown4G)?? && (kpiOutside.ftpDown4G.kpiValues)?? && (kpiOutside.ftpDown4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.ftpDown4G.kpiValues as ftpDown4G>
	          <div style="float:left"><input name="ftpDown4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpDown4G.kpiValue)!}"   maxlength="7"   oldval="${(ftpDown4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpDown4G_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
          <div class="clear"></div>
          <!--=阈值配置数据开始=== -->
          <div class="yfpz_sj">
           <ul style="position:relative;top:-35px;">
            <li><h4>PING丢包率(%): </h4>
            <#if (kpiOutside.pingLoss4G)?? && (kpiOutside.pingLoss4G.kpiValues)?? && (kpiOutside.pingLoss4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingLoss4G.kpiValues as pingLoss4G>
	          <div style="float:left"><input name="pingLoss4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingLoss4G.kpiValue)!}"   maxlength="7"   oldval="${(pingLoss4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingLoss4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING最大延迟(ms): </h4>
            <#if (kpiOutside.pingMaxDelay4G)?? && (kpiOutside.pingMaxDelay4G.kpiValues)?? && (kpiOutside.pingMaxDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingMaxDelay4G.kpiValues as pingMaxDelay4G>
	         <div style="float:left"> <input name="pingMaxDelay4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMaxDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingMaxDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMaxDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>PING最小延迟(ms): </h4>
            <#if (kpiOutside.pingMinDelay4G)?? && (kpiOutside.pingMinDelay4G.kpiValues)?? && (kpiOutside.pingMinDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingMinDelay4G.kpiValues as pingMinDelay4G>
	          <div style="float:left"><input name="pingMinDelay4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMinDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingMinDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMinDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING平均延迟(ms): </h4>
             <#if (kpiOutside.pingAvgDelay4G)?? && (kpiOutside.pingAvgDelay4G.kpiValues)?? && (kpiOutside.pingAvgDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.pingAvgDelay4G.kpiValues as pingAvgDelay4G>
	          <div style="float:left"><input name="pingAvgDelay4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingAvgDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingAvgDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingAvgDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最大响应时间(ms): </h4>
            <#if (kpiOutside.httpMaxResponseTime4G)?? && (kpiOutside.httpMaxResponseTime4G.kpiValues)?? && (kpiOutside.httpMaxResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMaxResponseTime4G.kpiValues as httpMaxResponseTime4G>
	          <div style="float:left"><input name="httpMaxResponseTime4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最小响应时间(ms):  </h4>
	         <#if (kpiOutside.httpMinResponseTime4G)?? && (kpiOutside.httpMinResponseTime4G.kpiValues)?? && (kpiOutside.httpMinResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMinResponseTime4G.kpiValues as httpMinResponseTime4G>
	          <div style="float:left"><input name="httpMinResponseTime4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMinResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
             <ul style="position:relative;top:-35px;">
            <li><h4>HTTP平均响应时间(ms): </h4>
            <#if (kpiOutside.httpAvgResponseTime4G)?? && (kpiOutside.httpAvgResponseTime4G.kpiValues)?? && (kpiOutside.httpAvgResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpAvgResponseTime4G.kpiValues as httpAvgResponseTime4G>
	          <div style="float:left"><input name="httpAvgResponseTime4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最大下载速度(Kbps): </h4>
            <#if (kpiOutside.httpMaxDownloadSpeed4G)?? && (kpiOutside.httpMaxDownloadSpeed4G.kpiValues)?? && (kpiOutside.httpMaxDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMaxDownloadSpeed4G.kpiValues as httpMaxDownloadSpeed4G>
	          <div style="float:left"><input name="httpMaxDownloadSpeed4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
            <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最小下载速度(Kbps): </h4>
            <#if (kpiOutside.httpMinDownloadSpeed4G)?? && (kpiOutside.httpMinDownloadSpeed4G.kpiValues)?? && (kpiOutside.httpMinDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpMinDownloadSpeed4G.kpiValues as httpMinDownloadSpeed4G>
	          <div style="float:left"><input name="httpMinDownloadSpeed4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMinDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP平均下载速度(Kbps): </h4>
            <#if (kpiOutside.httpAvgDownloadSpeed4G)?? && (kpiOutside.httpAvgDownloadSpeed4G.kpiValues)?? && (kpiOutside.httpAvgDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiOutside.httpAvgDownloadSpeed4G.kpiValues as httpAvgDownloadSpeed4G>
	         <div style="float:left"><input name="httpAvgDownloadSpeed4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
          </div>
        </div>
      <!--==室外信息结束==-->
      
      <!--===室内外信息划分线===-->
      
      <!--====室内信息开始===-->
      <div class="clear"></div>
      <div class="scene_title_inside">室内</div>

        <!--室内的信息 -->
        <div class="yfpz_2g" style="padding-top:10px;" >
          <h3>2G ：</h3>
          <ul>
            <!--<li>
              <h4>TxPower(dBm ):</h4>
              <#if (kpiInside.txPowerfor2g)?? && (kpiInside.txPowerfor2g.kpiValues)?? && (kpiInside.txPowerfor2g.kpiValues)?size &gt; 0>
              <#list kpiInside.txPowerfor2g.kpiValues as txPowerfor2g>
              <div style="float:left"><input name="txPowerfor2gInside" type="text" class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(txPowerfor2g.kpiValue)!}" maxlength="7"   oldval="${(txPowerfor2g.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if txPowerfor2g_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>-->  
             <li>
              <h4>RXlev(dBm ): </h4>
             <#if (kpiInside.rxlev)?? && (kpiInside.rxlev.kpiValues)?? && (kpiInside.rxlev.kpiValues)?size &gt; 0>
              <#list kpiInside.rxlev.kpiValues as rxlev>
               <div style="float:left"><input name="rxlevInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rxlev.kpiValue)!}"  maxlength="7"   oldval="${(rxlev.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if rxlev_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>
            <li>
              <h4>RxQual: </h4>
              <#if (kpiInside.rxQual)?? && (kpiInside.rxQual.kpiValues)?? && (kpiInside.rxQual.kpiValues)?size &gt; 0>
              <#list kpiInside.rxQual.kpiValues as rxQual>
               <div style="float:left"><input name="rxQualInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rxQual.kpiValue)!}"  maxlength="7"   oldval="${(rxQual.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if rxQual_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
              </#if>
            </li>
            <li>
              <h4>C/I(dB): </h4>
	           <#if (kpiInside.ci)?? && (kpiInside.ci.kpiValues)?? && (kpiInside.ci.kpiValues)?size &gt; 0>
	          <#list kpiInside.ci.kpiValues as ci>
	           <div style="float:left"><input name="ciInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ci.kpiValue)!}"   maxlength="7"   oldval="${(ci.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ci_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
            <!--<li>
              <h4>MOS: </h4>
	           <#if (kpiInside.mos)?? && (kpiInside.mos.kpiValues)?? && (kpiInside.mos.kpiValues)?size &gt; 0>
	          <#list kpiInside.mos.kpiValues as mos>
	           <div style="float:left"><input name="mosInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(mos.kpiValue)!}"   maxlength="7"   oldval="${(mos.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if mos_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>-->                   
          </ul>
        </div>
        <div class="clear"></div>
        <div class="yfpz_2g yfpz_2g_ts">
          <h3>3G ：</h3>
          <ul style="margin-bottom:40px;">
            <li>
              <h4>RSCP(dBm ):</h4>
              <#if (kpiInside.rscp)?? && (kpiInside.rscp.kpiValues)?? && (kpiInside.rscp.kpiValues)?size &gt; 0>
	          <#list kpiInside.rscp.kpiValues as rscp>
	          <div style="float:left"><input name="rscpInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rscp.kpiValue)!}"   maxlength="7"   oldval="${(rscp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if rscp_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
             <li>
              <h4>Ec/No(dB): </h4>
              <#if (kpiInside.ecno)?? && (kpiInside.ecno.kpiValues)?? && (kpiInside.ecno.kpiValues)?size &gt; 0>
	          <#list kpiInside.ecno.kpiValues as ecno>
	          <div style="float:left"><input name="ecnoInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ecno.kpiValue)!}"   maxlength="7"   oldval="${(ecno.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ecno_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li>
              <h4>TxPower(dBm): </h4>
              <#if (kpiInside.txPowerfor3g)?? && (kpiInside.txPowerfor3g.kpiValues)?? && (kpiInside.txPowerfor3g.kpiValues)?size &gt; 0>
	          <#list kpiInside.txPowerfor3g.kpiValues as txPowerfor3g>
	          <div style="float:left"><input name="txPowerfor3gInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(txPowerfor3g.kpiValue)!}"   maxlength="7"   oldval="${(txPowerfor3g.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if txPowerfor3g_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>     
          </ul>
			<div style="width: 100%; display: block; float: left; height: 10px;"></div>
          	<h5 style="left:-5px;">3G数据： </h5>
            <ul style="position:relative;top:-35px;">
            <li>
              <h4>FTP上行(Kbps): </h4>
	           <#if (kpiInside.ftpUp)?? && (kpiInside.ftpUp.kpiValues)?? && (kpiInside.ftpUp.kpiValues)?size &gt; 0>
	          <#list kpiInside.ftpUp.kpiValues as ftpUp>
	          <div style="float:left"><input name="ftpUpInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpUp.kpiValue)!}"   maxlength="7"   oldval="${(ftpUp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpUp_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
             <li>
              <h4>FTP下行(Kbps):</h4>
              <#if (kpiInside.ftpDown)?? && (kpiInside.ftpDown.kpiValues)?? && (kpiInside.ftpDown.kpiValues)?size &gt; 0>
	          <#list kpiInside.ftpDown.kpiValues as ftpDown>
	          <div style="float:left"><input name="ftpDownInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpDown.kpiValue)!}"   maxlength="7"   oldval="${(ftpDown.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpDown_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
          <div class="clear"></div>
          <!--=阈值配置数据开始=== -->
          <div class="yfpz_sj">
           <ul style="position:relative;top:-35px;">
            <li><h4>PING丢包率(%): </h4>
            <#if (kpiInside.pingLoss)?? && (kpiInside.pingLoss.kpiValues)?? && (kpiInside.pingLoss.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingLoss.kpiValues as pingLoss>
	          <div style="float:left"><input name="pingLossInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingLoss.kpiValue)!}"   maxlength="7"   oldval="${(pingLoss.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingLoss_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING最大延迟(ms): </h4>
            <#if (kpiInside.pingMaxDelay)?? && (kpiInside.pingMaxDelay.kpiValues)?? && (kpiInside.pingMaxDelay.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingMaxDelay.kpiValues as pingMaxDelay>
	         <div style="float:left"> <input name="pingMaxDelayInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMaxDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingMaxDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMaxDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>PING最小延迟(ms): </h4>
            <#if (kpiInside.pingMinDelay)?? && (kpiInside.pingMinDelay.kpiValues)?? && (kpiInside.pingMinDelay.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingMinDelay.kpiValues as pingMinDelay>
	          <div style="float:left"><input name="pingMinDelayInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMinDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingMinDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMinDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING平均延迟(ms): </h4>
             <#if (kpiInside.pingAvgDelay)?? && (kpiInside.pingAvgDelay.kpiValues)?? && (kpiInside.pingAvgDelay.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingAvgDelay.kpiValues as pingAvgDelay>
	          <div style="float:left"><input name="pingAvgDelayInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingAvgDelay.kpiValue)!}"   maxlength="7"   oldval="${(pingAvgDelay.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingAvgDelay_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最大响应时间(ms): </h4>
            <#if (kpiInside.httpMaxResponseTime)?? && (kpiInside.httpMaxResponseTime.kpiValues)?? && (kpiInside.httpMaxResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMaxResponseTime.kpiValues as httpMaxResponseTime>
	          <div style="float:left"><input name="httpMaxResponseTimeInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最小响应时间(ms):  </h4>
	         <#if (kpiInside.httpMinResponseTime)?? && (kpiInside.httpMinResponseTime.kpiValues)?? && (kpiInside.httpMinResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMinResponseTime.kpiValues as httpMinResponseTime>
	          <div style="float:left"><input name="httpMinResponseTimeInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpMinResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
             <ul style="position:relative;top:-35px;">
            <li><h4>HTTP平均响应时间(ms): </h4>
            <#if (kpiInside.httpAvgResponseTime)?? && (kpiInside.httpAvgResponseTime.kpiValues)?? && (kpiInside.httpAvgResponseTime.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpAvgResponseTime.kpiValues as httpAvgResponseTime>
	          <div style="float:left"><input name="httpAvgResponseTimeInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgResponseTime.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgResponseTime.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgResponseTime_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最大下载速度(Kbps): </h4>
            <#if (kpiInside.httpMaxDownloadSpeed)?? && (kpiInside.httpMaxDownloadSpeed.kpiValues)?? && (kpiInside.httpMaxDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMaxDownloadSpeed.kpiValues as httpMaxDownloadSpeed>
	          <div style="float:left"><input name="httpMaxDownloadSpeedInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
            <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最小下载速度(Kbps): </h4>
            <#if (kpiInside.httpMinDownloadSpeed)?? && (kpiInside.httpMinDownloadSpeed.kpiValues)?? && (kpiInside.httpMinDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMinDownloadSpeed.kpiValues as httpMinDownloadSpeed>
	          <div style="float:left"><input name="httpMinDownloadSpeedInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpMinDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP平均下载速度(Kbps): </h4>
            <#if (kpiInside.httpAvgDownloadSpeed)?? && (kpiInside.httpAvgDownloadSpeed.kpiValues)?? && (kpiInside.httpAvgDownloadSpeed.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpAvgDownloadSpeed.kpiValues as httpAvgDownloadSpeed>
	         <div style="float:left"><input name="httpAvgDownloadSpeedInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgDownloadSpeed.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgDownloadSpeed.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgDownloadSpeed_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
          </div>
          </div>
          <div class="clear"></div>
           <div class="yfpz_2g yfpz_2g_ts">
	          <h3>4G ：</h3>
	          <ul style="margin-bottom:40px;">
	            <li>
	              <h4>RSRP(dBm ):</h4>
	              <#if (kpiInside.rsrp)?? && (kpiInside.rsrp.kpiValues)?? && (kpiInside.rsrp.kpiValues)?size &gt; 0>
		          <#list kpiInside.rsrp.kpiValues as rsrp>
		          <div style="float:left"><input name="rsrpInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rsrp.kpiValue)!}"   maxlength="7"   oldval="${(rsrp.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if rsrp_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>  
	             <li>
	              <h4>RSRQ(dBm ): </h4>
	              <#if (kpiInside.rsrq)?? && (kpiInside.rsrq.kpiValues)?? && (kpiInside.rsrq.kpiValues)?size &gt; 0>
		          <#list kpiInside.rsrq.kpiValues as rsrq>
		          <div style="float:left"><input name="rsrqInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(rsrq.kpiValue)!}"   maxlength="7"   oldval="${(rsrq.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if rsrq_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>
	            <li>
	              <h4>SINR(dBm): </h4>
	              <#if (kpiInside.sinr)?? && (kpiInside.sinr.kpiValues)?? && (kpiInside.sinr.kpiValues)?size &gt; 0>
		          <#list kpiInside.sinr.kpiValues as sinr>
		          <div style="float:left"><input name="sinrInside" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(sinr.kpiValue)!}"   maxlength="7"   oldval="${(sinr.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
		          <#if sinr_index &lt; 4>
		         	 <span>～</span>
		          </#if>
		          </#list>
		          </#if>
	            </li>     
      		</ul>
			<div style="width: 100%; display: block; float: left; height: 10px;"></div>
          	<h5 style="left:-5px;">4G数据： </h5>
            <ul style="position:relative;top:-35px;">
            <li>
              <h4>FTP上行(Kbps): </h4>
	           <#if (kpiInside.ftpUp4G)?? && (kpiInside.ftpUp4G.kpiValues)?? && (kpiInside.ftpUp4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.ftpUp4G.kpiValues as ftpUp4G>
	          <div style="float:left"><input name="ftpUpInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpUp4G.kpiValue)!}"   maxlength="7"   oldval="${(ftpUp4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpUp4G_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
             <li>
              <h4>FTP下行(Kbps):</h4>
              <#if (kpiInside.ftpDown4G)?? && (kpiInside.ftpDown4G.kpiValues)?? && (kpiInside.ftpDown4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.ftpDown4G.kpiValues as ftpDown4G>
	          <div style="float:left"><input name="ftpDownInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(ftpDown4G.kpiValue)!}"   maxlength="7"   oldval="${(ftpDown4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if ftpDown4G_index &lt; 4>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
          <div class="clear"></div>
          <!--=阈值配置数据开始=== -->
          <div class="yfpz_sj">
           <ul style="position:relative;top:-35px;">
            <li><h4>PING丢包率(%): </h4>
            <#if (kpiInside.pingLoss4G)?? && (kpiInside.pingLoss4G.kpiValues)?? && (kpiInside.pingLoss4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingLoss4G.kpiValues as pingLoss4G>
	          <div style="float:left"><input name="pingLossInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingLoss4G.kpiValue)!}"   maxlength="7"   oldval="${(pingLoss4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingLoss4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING最大延迟(ms): </h4>
            <#if (kpiInside.pingMaxDelay4G)?? && (kpiInside.pingMaxDelay4G.kpiValues)?? && (kpiInside.pingMaxDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingMaxDelay4G.kpiValues as pingMaxDelay4G>
	         <div style="float:left"> <input name="pingMaxDelayInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMaxDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingMaxDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMaxDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li> 
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>PING最小延迟(ms): </h4>
            <#if (kpiInside.pingMinDelay4G)?? && (kpiInside.pingMinDelay4G.kpiValues)?? && (kpiInside.pingMinDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingMinDelay4G.kpiValues as pingMinDelay4G>
	          <div style="float:left"><input name="pingMinDelayInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingMinDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingMinDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingMinDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>PING平均延迟(ms): </h4>
             <#if (kpiInside.pingAvgDelay4G)?? && (kpiInside.pingAvgDelay4G.kpiValues)?? && (kpiInside.pingAvgDelay4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.pingAvgDelay4G.kpiValues as pingAvgDelay4G>
	          <div style="float:left"><input name="pingAvgDelayInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(pingAvgDelay4G.kpiValue)!}"   maxlength="7"   oldval="${(pingAvgDelay4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if pingAvgDelay4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
           <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最大响应时间(ms): </h4>
            <#if (kpiInside.httpMaxResponseTime4G)?? && (kpiInside.httpMaxResponseTime4G.kpiValues)?? && (kpiInside.httpMaxResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMaxResponseTime4G.kpiValues as httpMaxResponseTime4G>
	          <div style="float:left"><input name="httpMaxResponseTimeInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最小响应时间(ms):  </h4>
	         <#if (kpiInside.httpMinResponseTime4G)?? && (kpiInside.httpMinResponseTime4G.kpiValues)?? && (kpiInside.httpMinResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMinResponseTime4G.kpiValues as httpMinResponseTime4G>
	          <div style="float:left"><input name="httpMinResponseTimeInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMinResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
             <ul style="position:relative;top:-35px;">
            <li><h4>HTTP平均响应时间(ms): </h4>
            <#if (kpiInside.httpAvgResponseTime4G)?? && (kpiInside.httpAvgResponseTime4G.kpiValues)?? && (kpiInside.httpAvgResponseTime4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpAvgResponseTime4G.kpiValues as httpAvgResponseTime4G>
	          <div style="float:left"><input name="httpAvgResponseTimeInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgResponseTime4G.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgResponseTime4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgResponseTime4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP最大下载速度(Kbps): </h4>
            <#if (kpiInside.httpMaxDownloadSpeed4G)?? && (kpiInside.httpMaxDownloadSpeed4G.kpiValues)?? && (kpiInside.httpMaxDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMaxDownloadSpeed4G.kpiValues as httpMaxDownloadSpeed4G>
	          <div style="float:left"><input name="httpMaxDownloadSpeedInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMaxDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMaxDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMaxDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
            <ul style="position:relative;top:-35px;">
            <li><h4>HTTP最小下载速度(Kbps): </h4>
            <#if (kpiInside.httpMinDownloadSpeed4G)?? && (kpiInside.httpMinDownloadSpeed4G.kpiValues)?? && (kpiInside.httpMinDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpMinDownloadSpeed4G.kpiValues as httpMinDownloadSpeed4G>
	          <div style="float:left"><input name="httpMinDownloadSpeedInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpMinDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpMinDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpMinDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>
            <li class="yfpz_sj_ts"><h4>HTTP平均下载速度(Kbps): </h4>
            <#if (kpiInside.httpAvgDownloadSpeed4G)?? && (kpiInside.httpAvgDownloadSpeed4G.kpiValues)?? && (kpiInside.httpAvgDownloadSpeed4G.kpiValues)?size &gt; 0>
	          <#list kpiInside.httpAvgDownloadSpeed4G.kpiValues as httpAvgDownloadSpeed4G>
	         <div style="float:left"><input name="httpAvgDownloadSpeedInside4G" type="text"  class="easyui-validatebox" data-options="required:true,validType:['float']"  value="${(httpAvgDownloadSpeed4G.kpiValue)!}"   maxlength="7"   oldval="${(httpAvgDownloadSpeed4G.kpiValue)!}" onChange="isChangeVal(this)" isc="0"/></div>
	          <#if httpAvgDownloadSpeed4G_index &lt; 0>
	         	 <span>～</span>
	          </#if>
	          </#list>
	          </#if>
            </li>  
          </ul>
          </div>
          </form>
           <!--=阈值配置数据结束=== -->
        </div>
 		<!--==保存按钮开始==-->
           <div class="clear"></div>
		    <div class="bottom_btn yfpz_btn">
		  <!-- <div class="btn"><a>重 置</a></div> -->
		  <a href="javascript:void(0)"  id="setKpi" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a>
		  <!--<div class="btn" id="setKpi">确  定</div>-->
		</div>
      <!--==保存按钮结束==-->
      <!--===结束室内信息===-->
      <div class="clear"></div>
      <!--========阈值配置中间结束========= -->
    </div>
    <div class="all_list_bottom"><span></span><samp></samp></div>
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
	<script src="${application.getContextPath()}/js/workorder/kpi.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jscolor/jscolor.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var isChange = 0;
	</script>
</@script>

