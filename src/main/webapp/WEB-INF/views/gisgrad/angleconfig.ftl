<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="GIS角度配置" description="GIS角度配置">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<div class="right" >
	  <div class="forms">
	    <div class="forms_nav">
	      <ul>
	        <li style="cursor:default;"></li>
	      </ul>
	      <samp></samp> </div>
	    <div class="border_left">
	    <div  style="background:url('');">
			  <div class="sys_possword">
			  <form action="${application.getContextPath()}/gisgrad/saveangle" method="post" id="dataForm" class="addef">
			  	<div style="padding:20px 0px 150px;">
			  		<div class="shiwai titleName">方向角配置</div>
					<div class="clear"></div>
				    <div style="margin-top:10px;float:left;margin-left:30px;">
				    	<span style="width:80px;float:left;">方向角类型：</span><p style="float:left;">
				      		<select name="angletype" id="angletype" class="easyui-validatebox easyui-combobox"  data-options="required:true,editable:false,panelHeight: 'auto'" style="height:24px">
				      			<option value = "0" <#if typeangle?? && typeangle =='0'>selected</#if>>修正方位角</option>
				      			<option value = "1" <#if typeangle?? && typeangle =='1'>selected</#if>>自身方位角</option>
				      		</select>
				      </p>
				    </div>
				    <ul style="float:left;">
				      <li id="angleli" style="display:none;">
				      		<span style="width:80px;">一扇区角度：</span><p>
				      		<input  type="text"  name ="angle" id="angle" value="${angle!}" size=13 style="height:22px"/>
				      </p></li>
				    </ul>
			    </div>
			    <div class="bottom_btn" style="margin-right:30px;margin-bottom:16px;float:right;">
			      <a href="javascript:void(0)"  id="saveangle" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">保存</span></span></a>
				</div>
			  </form>
			  </div>
			</div>
	      <div class="clear"></div>
	    </div>
	    <div class="all_list_bottom"><span></span><samp></samp></div>
	  </div>
	</div>
</@body>
<@script>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/sj.base.library.1.0.1.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/report/highcharts1.0.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jQueryRotate.js"></script>
    <script type="text/javascript" src="${application.getContextPath()}/js/report/ReportForms.js"></script>
	<script src="${application.getContextPath()}/js/gisgrad/angleconfig.js" type="text/javascript"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
	</script>
</@script>
