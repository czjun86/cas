<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<script type="text/javascript" src="${application.getContextPath()}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/leftMenu.js"></script>
<title>菜单</title>
</head>
<body style="background:url(../images/left_bj.png) repeat-y;margin:0;">
<div class="menu_left">
    <ul class="SJ-JTreeMenu-Main">
    <input type="hidden" id="linkid" value="${linkid!}"/>
    <#if resources?? && resources?size &gt; 0 && parentid??>
    	<#list resources as res1>
    		<#if res1.parentid == parentid && res1.type == 0>
        	<li class="JTreeMenu-Node menu_left_add">
        		<span class="JTreeMenu-PNode" url="${application.getContextPath()}${(res1.url)!}" ys="${(res1.css)!}"> <samp class="${(res1.css)!}">&nbsp;</samp>${(res1.resourcename)!}</span>
	            <ul class="JTreeMenu-CNode menu_left_erji" style="display:none">
	            <#list resources as res2>
	            	<#if res1.resourceid == res2.parentid && res2.type == 0>
	                	<li class="jtm-node" url="${application.getContextPath()}${res2.url}"><samp class="JTreeMenu-CNode-Item">${res2.resourcename}</samp></li>
	                </#if>
	            </#list>
	            </ul>
	        </li>
            </#if>
        </#list>
    </#if>
    </ul>
<div class="clear"></div>
</div>
</body>
</html>
