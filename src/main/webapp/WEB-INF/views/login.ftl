<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<style type="text/css">
body{background:url(${application.getContextPath()}/images/login_bdy_bj.png) repeat-x; width:100%;}
body{background-color:#F5F5F5;}
</style>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js"></script>
<script src="${application.getContextPath()}/js/md5-min.js"></script>
<script src="${application.getContextPath()}/js/jquery.form.js"></script>
<script src="${application.getContextPath()}/js/login.js"></script>
<script>
	if (window.self != window.top) {
		window.top.location = window.self.location;
	}
	var contextPath = '${application.getContextPath()}';
</script>
<title>登陆</title>
</head>
<body background="#ffffff">
<form action="${application.getContextPath()}/doLogin" id="myform" method="post">
<div class="login">
<div class="login_middle">
<samp id="errorMsg"></samp>
	<ul>
		<li><span>用户名：</span><input id="username" name="username" type="text" value=""/></li>
		<li><span>密  码：</span><input id="password" name="password" type="password" value=""/></li>
		<li><span>&nbsp;</span><p id="submit"></p></li>
	</ul>
</div>
</div>
</form>
<a href="${application.getContextPath()}/faq/faq" style="position:fixed;position:absoult;z-index:999;right:20px;top:10px;text-align: left; text-indent: 20px; width: 110px; background: url('images/faq.png')left no-repeat;">帮助文档下载</a>
<script>
var contextPath = '${application.getContextPath()}';
</script>
</body>
</html>