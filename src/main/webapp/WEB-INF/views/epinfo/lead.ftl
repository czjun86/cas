<div>
<input type="hidden" id="leadResult" value="${result!}" />
<input type="hidden" id="leadadd" value="${add!}" />
<input type="hidden" id="leadupdate" value="${update!}" />
<input type="hidden" id="leadunchange" value="${unchange!}" />
<input type="hidden" id="errorname" value="${errorname!}" />
</div>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/epinfo/epinfolistValidate.js"></script>
<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
<script>
$(function(){
	var value1 = $("#leadResult").val();
	var value2 = $("#leadadd").val();
	var value3 = $("#leadupdate").val();
	var value4 = $("#leadunchange").val();
	var value5 = $("#errorname").val();
	window.parent.lead(value1,parseInt(value2),parseInt(value3),parseInt(value4),value5);
});
</script>