<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="${application.getContextPath()}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/report/raphael.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/scatter.js"></script>
</head>
<body>
<div id="parentContainer" style="width:400px;height:400px;overflow:hidden;position:absolute;">
	<div id="container" style="width:800px;height:800px;background-color:black;position:absolute">
		
	</div>
</div>
<div style="float:right">
	<a href="javascript:location.href='/cas/scatter?timeStamp=' + new Date().getTime()">刷新</a>
	<a id="large" style="cursor:pointer">容器画布放大</a><br/>
	<a id="reset" style="cursor:pointer">容器画布还原</a><br/>
	<a id="expand" style="cursor:pointer">画布放大</a>
	<a id="narrow" style="cursor:pointer">画布缩小</a>
</div>
</body>
</html>
