<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#macro head title keywords description>
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>${title!}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="${keywords!}" />
	<meta name="description" content="${description!}" />
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/DefaultSkin.css">
	<#nested>
	</head>
</#macro>
<#macro body>
<body style="background:url(${application.getContextPath()}/images/bj.png) repeat;">
<div class="mainmenu">
<div class="mainmenu_left"></div>
<!--======= -->
<#assign flag = false> 
<div class="mainmenu_center">
<span></span>

<div class="outerbox">
	<div class="innerbox clearfixmenu">
		<ul id="topMenu" class="menuitem">
		<#assign flag = false> 
		<#if Session.resources?? && Session.resources?size &gt; 0>
			<#list Session.resources as resource1>
				<#if resource1.parentid == 0>
				<li class="spritemenu" parentid="${resource1.resourceid!}"  url="${application.getContextPath()}/user/mainMenu">
					<h3>
						<a class="xiala1" href="javascript:void(0);"><div>${(resource1.resourcename)!}</div></a>
					</h3>
					<ul class='children clearfixmenu ej' style='display: none;'>
					<#list Session.resources as resource2>
					<#if resource2.parentid == resource1.resourceid && resource2.type == 0>
					 <li class="noborder second" url="${application.getContextPath()}${resource2.url!}">
					 	<h3 class="two">
					 		<a class="spritemenu" href="javascript:void(0);"><div>${resource2.resourcename!}</div><div class="arrows"></div></a>
					 	</h3>
						<#assign count = 0> 
							<#list Session.resources as resource3>
								<#if resource3.parentid == resource2.resourceid && resource3.type == 0>
									<#if count==0>
										<ul class="children clearfixmenu three" style="<#if resource2.resourcename?? && resource2.resourcename =='月报'>width:165px</#if>;position:absolute;top:-2px;left:125px;display: none;border-top:2px solid #c50000;padding:5px 0;">
										<#assign count = 1> 
									</#if>
									<li class="threeli" <#if resource2.resourcename?? && resource2.resourcename =='月报'>style="width:163px;"</#if> url="${application.getContextPath()}${resource3.url!}">${resource3.resourcename!}</li>										
								</#if>
							</#list>
							<#if count == 1>
								</ul>
								<#assign count = 0>
							</#if>
						</li>	
						</#if>
					</#list>
					</ul>
				</li>
				</#if>
				<#if resource1.resourceid == 13>
					<#assign flag = true> 
				</#if>
			</#list>
		</#if>			
		</ul>
	</div>
</div>
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
<div class="mainmenu_caid" >
<div class="menu_erji" style="display:none;">

</div>
<!-- -->
<div class="menu_sanji" style="display:none;">

</div>
<!-- -->
</div>

</div>
	<#nested>
</body>
</#macro>
<#macro script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/topMenu.js" type="text/javascript"></script>
	<#nested>
</html>
</#macro>
