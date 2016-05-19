$(function(){
	var num1 = 0;
	$("#defDateOk").click(function(){
		if(num1 == 0){
			var defDate = $("#defDate").val() ;
			if(defDate != null && defDate != ""){
				num1 ++;
				$('#dataForm').ajaxForm({
					type: "POST",
				  	url: contextPath + "/sysconfig/defDate",
				    success: function(data) {
				    	num1 = 0; 
				      if(data.msg == 1){
				    	  $.messager.alert("提示","修改成功！","success");
				      }else{
				    	  $.messager.alert("提示","修改失败！","error");
				      }
					}
				});
				$("#dataForm").submit();
			}else{
				$.messager.alert("提示","请选择时间！","warning");
			}
		}
	});
	var num2 = 0;
	$("#timelyrateOk").click(function(){
		if(num2 == 0){
			var timelyrate = $("#timelyrate").val();
			if(timelyrate != null && timelyrate != "" && /^[1-9]\d{0,2}$/.exec(timelyrate)){
				num2 ++;
				$('#dataForm').ajaxForm({
					type: "POST",
				  	url: contextPath + "/sysconfig/timelyrate",
				    success: function(data) {
				    	num2 = 0; 
				      if(data.msg == 1){
				    	  $.messager.alert("提示","修改成功！","success");
				      }else{
				    	  $.messager.alert("提示","修改失败！","error");
				      }
					}
				});
				$("#dataForm").submit();
			}else{
				$.messager.alert("提示","及时率默认时间只能是在1~999之间!","warning");
			}
		}
	});
});

