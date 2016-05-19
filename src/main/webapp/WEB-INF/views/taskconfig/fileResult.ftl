<div>
<input type="hidden" id="leadResult" value="${msg!}" />
</div>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
<script>
$(function(){
	var value = $("#leadResult").val();
	window.parent.lead(value);
});
</script>