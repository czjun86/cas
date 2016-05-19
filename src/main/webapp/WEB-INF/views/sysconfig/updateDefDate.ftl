<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="修改报表起始时间" description="修改报表起始时间">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<form action="${application.getContextPath()}/sysconfig/updateDefDate"  method="post" id="dataForm" class="form">
	<div class="right">
	  <div class="forms">
	    <div class="forms_nav">
	      <ul>
	        <li style="cursor:default;"></li>
	      </ul>
	      <samp></samp> 
	    </div>
	    <div class="border_left">
		    <div style="background:url('');margin-top:20px;" >
			   <span class="wlfspan" style="margin-left:15px;margin-bottom:20px;">累计起始时间：</span>
				<div class="search_input" style="width:148px;">
					 <h6></h6>
			      <h5><input name= "defDate" value="${defDate!}" id="defDate" type="text" class="Wdate"  style="width:138px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			   		 </h5><h4></h4> 
			    </div>
			    <div class="bottom_btn" style="margin-left:8px;margin-top:2px;">
			      <a href="javascript:void(0)"  id="defDateOk" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a>
			    </div>
			</div>
	        <div class="clear"></div>
	    </div>
	    <div class="all_list_bottom"><span></span><samp></samp></div>
	  </div>
	</div>
</form>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/sysConfig/sysConfig.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	</script>
</@script>
