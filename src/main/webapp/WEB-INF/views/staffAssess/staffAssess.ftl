<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="内部考核" description="内部考核">
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
	  <form action="${application.getContextPath()}/staffAssess/staffAssess" id="searchForm" method="post">
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
	    
	    <!--判断用户查询权限-->
	    <samp id = "search" style="margin-left:20px;">查询</samp>
	    <!--判断用户导出去权限-->
	    <samp id = "leadExcel" style="margin-left:15px;">导出</samp>
		<samp onclick = "showError()" id="errorBtn" style="display:none;"></samp>
	    <iframe id="download" src="#" style="display:none;">&nbsp;</iframe>
	    <input type="hidden" id="msg" value="${msg!0}"/>
	  </form>
	  </div>
	  <div class="wlf" style="float:right;margin-right:1%;">
	  	<span class="wlfspan" style="margin-left:px;">累计起始时间：</span>
		<div class="search_input" style="width:148px;">
			 <h6></h6>
	      <h5><input name= "reportdate" value="${reportdate!}" oldVal = "${reportdate!}" id="reportdate" type="text" class="Wdate"  style="width:138px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy.MM.dd HH:mm:ss'})" />
	   		 </h5><h4></h4> 
	    </div>
	    <samp id="setBtn" style="margin-left:15px;">设置</samp>
	  </div>
	</div>
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="all_list_top1"><span></span><samp></samp></div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yybb_top"><span>中心内部考核</span>
      </div>
      <!-- -->
      <div class="yybb_left">
      </div>
		<!--小组加减分设置开始 -->
		<#if groupScore?? && groupScore?size &gt;0>
      		<div class="zxnbkh" style="margin-left: 1px;">
				<ul class="zxnbkh-ts">
				<li>大组</li>
				<li>小组</li>
				<li class="zxnbkh-height">小组其它加减分</li>
				</ul>
			</div>
      	<div id="cliwidth" style="overflow-x: auto; float: left;">
			<div class="zxnbkh" id="zxnbkh" style="width:1340px;float:left;">
			      	<#list groupScore as bg>
						<ul style="width:${bg.score*120!}px;">
							<li style="width:100%;">${bg.groupName!''}</li>
							<#if bg.list?? && bg.list?size &gt;0>
			      				<#list bg.list as sg>
									<li >${sg.groupName!''}</li>
								</#list>
							</#if>
							<#if bg.list?? && bg.list?size &gt;0>
			      				<#list bg.list as sg>
									<li class="zxnbkh-height" >
										<input type="hidden" name="groupid" value="${sg.groupId!''}"/>
										<input name="score" type="text" value="${sg.score!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/>
									</li>
								</#list>
							</#if>
						</ul>
					</#list>
			</div>
		</div>
		<input type="hidden" id ="size" value="${size!0}">
			<div style="margin:14px 35px 14px 0;margin-bottom:16px; float:right;" class="bottom_btn">
			      <a class="l-btn" style="margin-left: 10px;" id="saveScore" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a> 
			    </div>
		
		</#if>
      <!--小组加减分设置结束 -->
      <div class="yybb_right wtjjl_right tstj_right" style="overflow:hidden;">
        <div class="yybb_right_top" style="border-bottom:none;">
          <ul class="wtjjl_right_ts tstj_right_ts">
            <li style="line-height:40px; width:13%">大组</li>
            <li style="line-height:40px;width:13%">小组</li>
            <li style="width:5%">累计实测<br />率</li>
            <li style="width:5%"><#if t_type?? && t_type == '2'>月</#if>测试及<br />时率</li>
            <li style="width:5%">累计解决<br />率</li>
            <li style="width:5%">累计工单<br />滞留率</li>
            <li style="width:5%">累计工单<br />驳回率</li>
            <li style="width:5%">累计工单<br />超时率</li>
            <li style="width:6%">累计工单<br />重派率</li>
            <li>累计重复<br />投诉率</li>
            <li>累计工单<br />升级率</li>
            <li><#if t_type?? && t_type == '2'>月</#if>网络投<br />诉工单量</li>
            <li>小组KPI<br />考核得分</li>
            <li>小组其它<br />加减分</li>
            <li>小组综合<br />得分</li>
            <li style="line-height:20px;width:3%">小组排名</li>
            <li style="border-right:none;">其它加减<br />分原因</li>
          </ul>
          <#if staff?? && staff?size &gt;0>
          	<#list staff as sf>
	      		<ul>
		            <li style="line-height:40px; width:13%">${sf.group_big_name!"-"}</li>
		            <li style="line-height:40px;width:13%">${sf.group_small_name!"-"}</li>
		            <li style="width:5%">${sf.total_test_rate!'-'}</li>
		            <li style="width:5%">${sf.curr_test_timely!'-'}</li>
		            <li style="width:5%">${sf.total_solve_rate!'-'}</li>
		            <li style="width:5%">${sf.total_delay_rate!'-'}</li>
		            <li style="width:5%">${sf.total_reject_rate!'-'}</li>
		            <li style="width:5%">${sf.total_over_rate!'-'}</li>
		            <li style="width:6%">${sf.total_send_rate!'-'}</li>
		            <li>${sf.total_complaint_rate!'-'}</li>
		            <li>${sf.total_upgrade_rate!'-'}</li>
		            <li>${sf.complaint!'-'}</li>
		            <li>${sf.group_kpi_score!'-'}</li>
		            <li>${sf.group_plusMinus_score!'0'}</li>
		            <li>${sf.group_synthesis_score!'-'}</li>
		            <li style="width:3%">${sf.group_rank!'-'}</li>
		            <li style="border-right:none;">${sf.group_plusMinus_cause!''}</li>
	      		</ul>
	      	</#list>
          </#if>
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
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/commUtils.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/staffAssess.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = '${application.getContextPath()}';
	</script>
</@script>
