$(function() {
	var addCount = 0;
	$('.add').click(function(){
		$("#dlg").dialog({
			href:contextPath + '/user/addUser',
			height: 330,width: 435,title: "用户信息",
			modal: true,closed:false
		});
		$('#dlg').dialog('open');
		$.parser.parse('#dlg');
		$("#valiSelect").val("0");
	});
	$(".saveedit").click(function(){
		if(addCount == 0){
			addCount ++;
			var type = $("#type").val();
			var url = "";
			if(type == 0){
				url = contextPath + "/user/addUser";
			}else{
				url = contextPath + "/user/updateUser";
			}
			$('#dataForm').ajaxForm({
				url: url,
				beforeSubmit:function(){ 
		            if(!$('#dataForm').form('validate')){
		            	addCount = 0;
		            	return false;
		            }else{
		            	return true;
		            }
		        },  
				success: function(data) {
					if(data.msg == 1){
						$.messager.alert("提示","操作成功！","success",function(){		
							$('#dlg').dialog('close');
							window.location.href =  window.location.href;
						});
					}else if (data.msg == -1){
						$.messager.alert("提示","该登录名已经存在！","warning");
					}else if(data.msg == -100){
						//重复提交
						$('#dlg').dialog('close');
						window.location.href =  window.location.href;
					}else{
						$.messager.alert("提示","操作失败！","error",function(){			
							$('#dlg').dialog('close');
							window.location.href =  window.location.href;
						});
					}
				}
			});
			$("#dataForm").submit();
		}
	});
	$("#parent").attr('checked',false);
	$("#parent").click(function () {
		if($("#parent").attr('checked') == undefined){			
			$('input[name="ids"]').attr('checked',false);
		}else{			
			$('input[name="ids"]').attr('checked',true);
		}
    }); 
	$("#delall").click(function(){
		var str="";   
		$("input[name='ids']:checked").each(function(){   
			str+=$(this).val()+",";   
		});   
		if(str != "" && str.length > 1){
			str = str.substring(0, str.length-1);
			deleteUser(str);
		}else{
			$.messager.alert("提示","请选择你要删除的用户！","warning");
		}
	});
	function deleteUser(ids){
		$.messager.confirm('提示', '是否删除你选择的用户？', function(r){
			if (r){
				$.ajax({
					type:"post",
					url:contextPath +"/user/deleteUser",
					data:({ids : ids}),
					success:function(data){
						if(data.msg == 1){
							$.messager.alert("提示","删除成功！","success",function(){								
								location.href= contextPath + "/user/userlist";
							});
						}else{
							$.messager.alert("提示","删除失败！","error");
						}
					}
				});	
				}
			});
	}
	 var optInit = {callback: 
	    	function(page_index,jq){
	        $.ajax({
	        	type:"post",
	    		url: contextPath + "/user/userlist/template",
	    		data:({name : $("#hiddenval").val(),pageIndex: page_index+1}),
	    		success:function(data){
	    			$('#content').html(data);
	    			$('.edit').click(function(){
	    				$("#dlg").dialog({
	    					href:contextPath + '/user/updateUser?id='+$(this).attr("id")+"&timestamp1=" + new Date().getTime(),
	    					height: 330,width: 435,title: "用户信息",
	    					modal: true,closed:false
	    				});
	    				$('#dlg').dialog('open');
	    				$.parser.parse('#dlg');
	    				$("#valiSelect").val("1");
	    			});
	    			$(".del").click(function(){
	    				var id = $(this).attr("id");
	    				deleteUser(id);
	    			});
	    			$(".resetpsw").click(function(){
	    				var id = $(this).attr("id");
	    				$.messager.confirm('提示', '是否重置该用户的密码？', function(r){
	    					if (r){
	    						$.ajax({
		    						type:"post",
		    						url:contextPath +"/user/resetpsw",
		    						data:({id : id}),
		    						success:function(data){
		    							if(data.msg == 1){
		    								$.messager.alert("提示","密码重置成功！","success");
		    							}else{
		    								$.messager.alert("提示","重置失败！","error");
		    							}
		    						}
		    					});
	    					}
	    				});
	    			});
	    			$("input[name='ids']").each(function(){   
	    				$(this).attr('checked',false);
	    			}); 
	    		}
	        });
	        return false;
	    }
	};
	$("#pager").pagination(pageTotal, optInit);
	

	
	/**
	 * 分配区域
	 */
	$(".access2").live('click' ,function(){
		var userid = $(this).attr("id");
		var src = contextPath + '/user/arealist?userid='+userid+"&type=0";
		//打开区域选择框
		$("#areadlg").dialog({
			href:src,
			height: 400,width: 380,title: "选择区域",
			modal: true,closed:false
		});
		$('#areadlg').dialog('open');
		$.parser.parse('#areadlg');
		
		//获取区域
		$(".sel_area").unbind('click').click(function(){
			var nodes = $('#areadlg').tree('getChecked');
			var	strids = "";
			if(nodes.length > 0){
				for(var i = 0; i < nodes.length; i++){
					if(nodes[i].id!= -1){
						strids += nodes[i].id + ",";
					}
				}
				if(strids !=null && strids != ""){
					strids = strids.substring(0,strids.length-1);
				}
			}
			//关闭弹出层
			$('#areadlg').dialog('close');
			//保存区域
			$.ajax({
				type:"post",
	    		url: contextPath + "/user/saveArea",
	    		data:({userid: userid,areas: strids}),
	    		success: function(data){
	    			if(data == 1){
	    				$.messager.alert("提示","区域分配成功！","success");
	    			}else{
	    				$.messager.alert("警告","区域分配失败！","warning");
	    			}
	    		}
			});
		});
		
	});
});
function search(){
	var nameval = $.trim($("#name").val());
	$("#hiddenval").val(nameval);
	$("#name").val(nameval);
	$("#searchForm").submit();	
}