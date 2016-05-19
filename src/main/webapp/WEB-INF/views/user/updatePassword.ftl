<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="修改密码" description="修改密码">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<form action="${application.getContextPath()}/user/updatePsw"  method="post" id="dataForm" class="form">
	<div class="right">
	  <div class="forms">
	    <div class="forms_nav">
	      <ul>
	        <li style="cursor:default;"></li>
	      </ul>
	      <samp></samp> 
	    </div>
	    <div class="border_left">
		    <div   style="background:url('')">
			   <div class="sys_possword">
			    <ul>
			      <li><span>旧密码：</span><p><input  type="password" name="oldpsw" id="oldpsw" class="easyui-validatebox"  data-options="required:true,validType:['password','length[6,20]']" style="height:24px"/></p></li>
			      <li><span>新密码：</span><p><input  type="password"  name ="password" id="password" class="easyui-validatebox"  data-options="required:true,validType:['password','length[6,20]']" style="height:24px"/></p></li>
			      <li><span>确认密码：</span><p><input  type="password" name="repsw" id="repsw" class="easyui-validatebox"  data-options="required:true,validType:['same[\'password\']','password','length[6,20]']" style="height:24px"/></p></li>
			    </ul>
			    <div class="bottom_btn" style="margin-left:85px;margin-bottom:16px;">
			      <a href="javascript:void(0)"  id="reset" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">重 置</span></span></a>
			      <a href="javascript:void(0)"  id="ok" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a>
			      <!--<div class="btn" id="reset" style="padding-right:0px;">重 置</div>
			      <div class="btn" id="ok">确 定</div>-->
			    </div>
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
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/user/update.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	</script>
</@script>
