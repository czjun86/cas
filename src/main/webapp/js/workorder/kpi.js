$(function() {
	$.parser.parse('.yfpz');
	var num = 0;
	var colornum = 0;
	$("#setKpi").click(function(){
		if(num == 0){		
			num++;
			$('#dataForm').ajaxForm({
				url: contextPath + "/kpi/kipconfig?ischange="+ isChange,
				beforeSubmit:function(){ 
		            if(!$('#dataForm').form('validate')){
		            	num = 0;
		            	return false;
		            }else{
		            	return true;
		            }
		        }, 
				success: function(data) {
					num = 0;
					$.messager.alert("提示",data.msg,"success",function(){
						 window.location.href =  window.location.href;
					});
				}
			});
			$("#dataForm").submit();
		}
	});
	$("#savecolor").click(function(){
		if(colornum == 0){
			colornum ++;
			$('#colorForm').ajaxForm({
			  	url: contextPath + "/kpi/colorconfig",
			    success: function(data) {
			    	colornum = 0;
			    	if(data.msg == -2){
			    		$.messager.alert("提示","请将颜色值填写完整！","warning");
			    	}else if(data.msg == -1){
			    		$.messager.alert("提示","请不要选择相同的颜色值！","warning");
			    	}else if(data.msg == 0){
			    		$.messager.alert("提示","操作失败！","error");
			    	}else{
			    		$.messager.alert("提示","操作成功！","success");
			    	}
				}
			});
			$("#colorForm").submit();
		}
		
	});
});
function changecolor(obj){
	$(obj).css('background', $(obj).val());
}
function isChangeVal(arg){
	var oldval = $(arg).attr("oldval");
	var newval = $(arg).val();
	var isc = $(arg).attr("isc");
	if(oldval != newval && isc ==0){
		isChange ++;
		$(arg).attr("isc",1);
	}else{
		isChange --;
		$(arg).attr("isc",0);
	}
}