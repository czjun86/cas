<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/sj.base.library.1.0.1.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/Component/JVerfier.js"></script>
<script src="${application.getContextPath()}/js/user/update.js" type="text/javascript"></script>
<title>修改用户基本信息</title>
</head>
<body style="background:url(../images/bj.png) repeat;">
<form id="dataForm" class="form">
<div class="right">
  <div class="sys_possword">
    <ul>
      <input name="userid" type="hidden" value="${(user.userid)!}" />
      <li><span>登录名：</span><p>${(user.userName)!}</p></li>
      <li><span>用户组：</span><p>${(user.rolename)!}</p></li>
      <li><span>姓&nbsp;&nbsp;名：</span><p><input name="name" type="text" value="${(user.name)!}" id="name" class="required V002" maxlength="20" minlength="4" style="height:24px"/></p></li>
      <li><span>电&nbsp;&nbsp;话：</span><p><input name="phone" type="text" value="${(user.phone)!}" id="phone" class="required telPhone" style="height:24px"/></p></li>
      <li><span>邮&nbsp;&nbsp;箱：</span><p><input name="email" type="text" value="${(user.email)!}" id="email" class="email" style="height:24px"/></p></li>
     
    </ul>
     <div class="bottom_btn" style="margin-left:220px;">
      <div class="btn"><a id="update">确  定</a></div>
    </div>
  </div>
</div>
</form>
<script>
var contextPath = '${application.getContextPath()}';
</script>
</body>
</html>
