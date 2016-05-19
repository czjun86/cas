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
<title>修改密码</title>
</head>
<body style="background:url(../images/bj.png) repeat;">
<form action="${application.getContextPath()}/user/updatePsw"  method="post" id="dataForm" class="form">
<div class="right">
  <div class="sys_possword">
    <ul>
      <li><span>旧密码：</span><p><input  type="password" name="oldpsw" id="oldpsw" class="required V004" style="height:24px"/></p></li>
      <li><span>新密码：</span><p><input  type="password"  name ="password" id="password" class="required V004" style="height:24px" maxlength="20" minlength="4" /></p></li>
      <li><span>确认密码：</span><p><input  type="password" name="repsw" id="repsw" class="required V004" style="height:24px" equalto="#password" maxlength="20" minlength="4"/></p></li>
    </ul>
     <div class="bottom_btn" style="margin-left:162px;">
      <div class="btn"><a id="reset">重 置</a></div>
      <div class="btn"><a id="ok">确 定</a></div>
    </div>
  </div>
</div>
</form>
<script>
var contextPath = '${application.getContextPath()}';
</script>
</body>
</html>
