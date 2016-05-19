<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
<title>404</title>
<script type="text/javascript" src="${application.getContextPath()}/js/sj.base.library.1.0.1.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
SJ.ready(function(){
	var resize = function(){
		var exception = SJ('.exception')[0];
		var client = SJ.clientWH();
		var exce = exception.style;
		exce.position = 'relative';
		exce.top = ((client.height/2) - (exception.offsetHeight/2))+'px';
	}
	window.onresize = function(){ resize(); };
	resize();
	$('#backHome').click(function(){
		top.location.href="${application.getContextPath()}/login";
	});
});
</script>
</head>
<body bgcolor="#e0e0e0">
<div class="exception">
<ul>
<li><a id="/logout" onfocus="this.blur()"><&nbsp;返回首页</a></li>
<li><a href="javascript:history.back()" onfocus="this.blur()"><&nbsp;返回上一页</a></li>
</ul>
</div>
</body>
</html>
