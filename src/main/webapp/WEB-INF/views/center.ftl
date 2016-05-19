<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<style type="text/css">
body { margin:0 auto; padding:0;}
</style>
<style> 
.navPoint { 
color:white; cursor:hand; font:12px "宋体";} 
</style>
<script>
	$(document).ready(function(){
		
		$('#switcher').click(function(){
		
			$('#leftSide').toggle('fast',function(){
				var _target = $('#switcher');
				if(_target.attr('src') == '${application.getContextPath()}/images/sssj_fx.png'){
					_target.attr('src','${application.getContextPath()}/images/sssj.png');
				}else{
					_target.attr('src','${application.getContextPath()}/images/sssj_fx.png');
				}
			});
		});
		
	});
</script>
</head>
<body style="overflow:hidden">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
  <tr>
    <td width="198" id="leftSide" align="center" valign="top">
      <iframe name="I1" id="leftMenu" height="100%" width="198" src="${application.getContextPath()}/user/test" border="0" frameborder="0" scrolling="no"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
    </td>
    <td width="5" style="width:4px;background:url('${application.getContextPath()}/images/sssj_bj.png') repeat scroll 0 0 transparent" valign="middle">
    	<span class="navPoint" id="switchPoint" title="打开/关闭左栏"><img src="${application.getContextPath()}/images/sssj_fx.png" name="img1" width="5" height="40" id="switcher"></span>
    </td>
    <td align="center" valign="top">
      <iframe id="iframeContent" name="I2" height="100%" width="100%" frameborder="0" src=""> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
    </td>
  </tr>
</table>
</body>
</html>
