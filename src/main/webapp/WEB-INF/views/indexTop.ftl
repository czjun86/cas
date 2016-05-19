<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${application.getContextPath()}/js/topMenu.js" type="text/javascript"></script>
</head>
<body>
<div class="mainmenu">
<div class="mainmenu_left"></div>
<!--======= -->
<div class="mainmenu_center">
<span></span>
<ul id="topMenu">
<#assign flag = false> 
<#if resources?? && resources?size &gt; 0>
	<#list resources as resource>
		<#if resource.parentid == 0>
			<li url="${application.getContextPath()}${(resource.url)!}">${(resource.resourcename)!}</li>
		</#if>
		<#if resource.resourceid == 13>
			<#assign flag = true> 
		</#if>
	</#list>
</#if>
</ul>
</div>
<div class="clear">&nbsp;</div>
<div class="mainmenu_right">
<div class="mainmenu_right_right">
<p id="curTime"></p>
<div class="clear">&nbsp;</div>
<samp><h3><a id="logout" contextPath="${application.getContextPath()}">退出系统</a></h3>${(Session.user.name)!} 【<#if flag == true><span id = "toInfo" url="${application.getContextPath()}/user/updateInfo" style="cursor:pointer;">${(Session.user.username)!}</span><#else>${(Session.user.username)!}</#if>】欢迎您!</samp>
<div class="clear">&nbsp;</div>
</div>
</div>
<div class="clear">&nbsp;</div>
</div>
</body>
</html>
