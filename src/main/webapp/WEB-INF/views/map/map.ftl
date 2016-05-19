<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="地图类型配置" description="地图类型配置">
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
			  <form action="${application.getContextPath()}/map/update" method="post" id="dataForm">
			  	<div style="padding:20px 0px 150px;">
			  		<div class="shiwai titleName">地图类型配置</div>
					<div class="clear"></div>
				    <div style="margin-top:10px;float:left;margin-left:30px;">
				    	<span style="width:100px;float:left;">
				    	<input type="radio" name="mapType" <#if mapType?? && mapType=='google'>checked='true'</#if> value="google">
				    	google地图
				    	</span>
				    	<span style="width:100px;float:left;margin-left:80px;">
				    	<input type="radio" name="mapType" <#if !(mapType??) || (mapType?? && mapType=='baidu')>checked='true'</#if> value="baidu">
				    	百度地图
				    	</span>
				    </div>
			    </div>
			    <div class="bottom_btn" style="margin-right:30px;margin-bottom:16px;float:right;">
			      <a href="javascript:void(0)"  id="save" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">保存</span></span></a>
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
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript" src="${application.getContextPath()}/js/map/map.js"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
		
	</script>
</@script>
