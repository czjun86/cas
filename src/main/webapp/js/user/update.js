
$(function(){
	var oknum = 0;
	$.parser.parse('.sys_possword');
	$("#ok").click(function(){
		if(oknum == 0){
			oknum ++;
			$('#dataForm').ajaxForm({
			  	url: contextPath + "/user/updatePsw",
			  	beforeSubmit:function(){ 
		            if(!$('#dataForm').form('validate')){
		            	oknum = 0;
		            	return false;
		            }else{
		            	return true;
		            }
		        },  
			    success: function(data) {
			    	oknum = 0;
			      if(data.msg == 1){
			    	  $.messager.alert("提示","密码修改成功！","success",function(){				    		  
			    		  init();
			    	  });
			      }else if (data.msg == -1){
			    	  $.messager.alert("提示","旧密码输入错误！","error",function(){				    		  
				    	  $("#oldpsw").val("");
				    	  $("#oldpsw").focus();
			    	  });
			      }else if (data.msg == -2){
			    	  $.messager.alert("提示","两次密码输入不一致！","error",function(){				    		  
			    		  $("#password").focus();
				    	  $("#password").val("");
				    	  $("#repsw").val("");
			    	  });
			      }else{
			    	  $.messager.alert("提示","密码修改失败！","error",function(){				    		  
			    		  init();
			    	  });
			      }
				}
			});
			$("#dataForm").submit();
		}
		
	});
	$("#reset").click(function(){
		init();
	});
	var updatenum = 0;
	$("#update").click(function(){
		if(updatenum == 0){
			updatenum ++;
			$('#dataForm').ajaxForm({
				type: "POST",
			  	url: contextPath + "/user/updateInfo",
			  	beforeSubmit:function(){ 
		            if(!$('#dataForm').form('validate')){
		            	updatenum = 0;
		            	return false;
		            }else{
		            	return true;
		            }
		        },  
			    success: function(data) {
			      updatenum = 0; 
			      if(data.msg == 1){
			    	  $.messager.alert("提示","修改成功！","success");
			      }else{
			    	  $.messager.alert("提示","修改失败！","error");
			      }
				}
			});
			$("#dataForm").submit();
		}
		
	});
	function init(){
		$("#oldpsw").val("").attr('class','easyui-validatebox');  
		$("#password").val("").attr('class','easyui-validatebox');
		$("#repsw").val("").attr('class','easyui-validatebox');
	}
});

