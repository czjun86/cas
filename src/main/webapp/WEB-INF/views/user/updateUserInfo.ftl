<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="修改用户基本信息" description="修改用户基本信息">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<form id="dataForm" class="form">
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
			    <ul>
			      <input name="userid" type="hidden" value="${(user.userid)!}" />
			      <li><span>登录名：</span><p>${(user.userName)!}</p></li>
			      <li><span>用户组：</span><p>${(user.rolename)!}</p></li>
			      <li><span>姓　名：</span><p><input name="name" type="text" value="${(user.name)!}" id="name"  class="easyui-validatebox"  data-options="required:true,validType:['vname','lengthb[4,20]']" /></p></li>
			      <li><span>电　话：</span><p><input name="phone" type="text" value="${(user.phone)!}" id="phone" class="easyui-validatebox" data-options="required:true,validType:'telPhone'" /></p></li>
			      <li><span>邮　箱：</span><p><input name="email" type="text" value="${(user.email)!}" id="email" class="easyui-validatebox" data-options="required:false,validType:['email','maxLength[50]']" /></p></li>
			      
			    </ul>
			     <div class="bottom_btn" style="margin-left:167px;margin-bottom:15px;">
			      <!--<div class="btn" id="update">确  定</div>-->
			      <a href="javascript:void(0)"  id="update" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确  定</span></span></a>
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