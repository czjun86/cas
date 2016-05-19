<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="万人日均投诉" description="万人日均投诉">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
	
</@head>
<@body>
 <div class="right1">
	<div id="alertBackground" class="alertBackground"></div>
<div id="dialogBackground" class="dialogBackground"></div>
<div id='background' class='background'></div>
<div id="bar_id">
<img src="../images/jbar.gif" /></span>
</div>
<div class="right"> 
      <!-----搜索框 开始 ------->
  <div class="wls">
  <div class="wlf">
  <form action="${application.getContextPath()}/complain/complain" id="searchForm" method="post">
  	<span class="wlfspan" style="margin-left:20px;">报表类型：</span>
    <div class="search_input" style="width:70px;height:28px;">
	      <select id="cc" class="easyui-combobox" name="type" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
	        <option <#if t_type?? && t_type == '2'>selected="selected"</#if> value="2">月报表</option>
	        <option <#if t_type?? && t_type == '1'>selected="selected"</#if> value="1">周报表</option>
	     </select> 
	</div>
    <input type="hidden" name ="t_type" value=${t_type!2} id="t_type"/>
    <span class="wlfspan" id= "m_timetitle" style="margin-left:15px;<#if t_type?? && t_type == '1'>display:none;</#if>">月份：</span>
	<div class="search_input" id = "m_timediv" style="width:88px;<#if t_type?? && t_type == '1'>display:none;</#if>">
		<h6></h6>
      		<h5><input name= "m_time" value="${m_time!''}" id="m_time" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})" /></h5>
      	<h4></h4> 
    </div>
    
    <span class="wlfspan" id= "s_timetitle" style="margin-left:15px;<#if t_type?? && t_type == '2'>display:none;</#if>">开始日期：</span>
	<div class="search_input" id = "s_timediv" style="width:88px;<#if t_type?? && t_type == '2'>display:none;</#if>">
		<h6></h6>
      		<h5><input name= "s_time" value="${s_time!''}" id="s_time" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'e_time\',{d:-1})}'})" /></h5>
      	<h4></h4> 
    </div>
    
    <span class="wlfspan" id= "e_timetitle" style="margin-left:15px;<#if t_type?? && t_type == '2'>display:none;</#if>">结束日期：</span>
	<div class="search_input" id = "e_timediv" style="width:88px;<#if t_type?? && t_type == '2'>display:none;</#if>">
		<h6></h6>
      		<h5><input name= "e_time" value="${e_time!''}" id="e_time" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'s_time\',{d:+1})}',maxDate:'%y-%M-%d'})" /></h5>
      	<h4></h4> 
    </div>
    
	<span class="wlfspan" style="margin-left:15px;">区域：</span>
    <div class="" style="float:left;margin-right:10px;">
      	<h6></h6>
      	<h5>
      	<select id="areas" class="easyui-combobox areas"  style="width:80px;height:26px;" data-options="editable:false,panelHeight: '0px'">
      		<option><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
      	</select>
      	 <input type="hidden" name="areatext" value="${areatext!'全部'}" id="areatext"/>
      	 <input type="hidden" name="area_id" value="${area_id!-1}" id="area_id"/>
      	 <input type="hidden" name="queryids" value="${queryids!-1}" id="queryids"/>
      	</h5><h4></h4>
    </div>
    <!--判断用户查询权限-->
    <samp id = "search" style="margin-left:20px;">查询</samp>
    <!--判断用户导出去权限-->
    <samp id = "leadExcel" style="margin-left:15px;">导出</samp>
	<samp onclick = "showError()" id="errorBtn" style="display:none;"></samp>
    <iframe id="download" src="#" style="display:none;">&nbsp;</iframe>
    <input type="hidden" id="msg" value="${msg!0}"/>
  </form>
  </div>
</div>
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="all_list_top1"><span></span><samp></samp></div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    	<div class="border_left">
      		<div class="yybb_top"><span>日均万人投诉率与投诉话务比统计</span>
		    </div>
		    <!-- -->
		    <div class="yybb_left">
		    	<!-- -->
		    </div>
			<!--标题开始-->
	        <div class="yybb_right wtjjl_right tstj_right tstj_right_wr">
		        <div class="yybb_right_top" style="border-bottom:none;">
		          	<ul class="wtjjl_right_ts tstj_right_ts">
		  				<li style="line-height:40px;">区域</li>
		  				<li style="line-height:40px;">分公司类别</li>
			        	<li style="line-height:40px;">2g投诉量</li>
			        	<li style="line-height:40px;">3g投诉量</li>
			        	<li>2g语音投<br />诉量</li>
			        	<li>3g语音投<br />诉量</li>
			        	<li>2g室内无<br />覆盖</li>
			        	<li>2g室外无<br />覆盖</li>
			        	<li>3g室内无<br />覆盖</li>
			        	<li>3g室外无<br />覆盖</li>
			        	<li>2g室内无<br />覆盖占比</li>
			        	<li>2g室外无<br />覆盖占比</li>
			        	<li style="width:8%;">3g室内无<br />覆盖占比</li>
			        	<li style="border-right:none;width:8%;">3g室外无<br />覆盖占比</li>
	        		</ul>
	     		<!--标题结束-->
	     		<!--内容开始-->
			      	<#if complain?? && complain?size &gt;0>
			      		<#list complain as cp>
					        <ul>
						        <li >${cp.areaname!''}</li>
						        <li >${cp.groupname!''}</li>
					        	<li >${cp.gsm_complaint!''}</li>
					        	<li >${cp.wcdma_complaint!''}</li>
					        	<li >${cp.gsm_speech_complaint!''}</li>
					        	<li >${cp.wcdma_speech_complaint!''}</li>
					        	<li >${cp.gsm_inside_uncovered!''}</li>
					        	<li >${cp.gsm_outside_uncovered!''}</li>
					        	<li >${cp.wcdma_inside_uncovered!''}</li>
					        	<li >${cp.wcdma_outside_uncovered!''}</li>
					        	<li >${cp.gsm_inside_ratio!0.00}%</li>
					        	<li >${cp.gsm_outside_ratio!0.00}%</li>
					        	<li style="width:8%;">${cp.wcdma_inside_ratio!0.00}%</li>
					        	<li style="width:8%;border-right:none;">${cp.wcdma_outside_ratio!0.00}%</li>
					        </ul>
				        </#list>
			        </#if>
			     <!--内容结束-->
     			 </div>
      		<div class="clear"></div>
      	</div>
      <!-- -->
      <div class="clear"></div>
      <!--========阈值配置中间结束========= --> 
    </div>
    <div class="all_list_bottom"><span></span><samp></samp></div>
  </div>
  <!--========阈值配置结束========= --> 
</div>
<div id="areadlg" class="easyui-dialog" title="选择区域" style="padding:10px"
		data-options="
			buttons: '#select-area',
			modal:true ,
			closed:true
		">
</div>
<div id="select-area" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_area">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#areadlg').dialog('close')">取消</a>
</div>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/commUtils.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/complain.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = '${application.getContextPath()}';
	</script>
</@script>