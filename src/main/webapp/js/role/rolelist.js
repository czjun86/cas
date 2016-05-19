$(function() {
	var addCount = 0;
	$('.add').click(function(){
		$("#dlg").dialog({
			href:contextPath + '/role/addRole',
			height: 130,width: 435,title: "角色信息",
			modal: true,closed:false
		});
		$('#dlg').dialog('open');
		$.parser.parse('#dlg');
	});
	$(".saveedit").click(function(){
		if(addCount == 0){
			addCount++;
			var type = $("#type").val();
			var url = "";
			if(type == 0){
				url = contextPath + "/role/addRole";
			}else{
				url = contextPath + "/role/updateRole";
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
			      }else if(data.msg == -100){
			    	  //请不要重复提交！
			    	  $('#dlg').dialog('close');
			    	  window.location.href =  window.location.href;
			      }else if(data.msg == -1){
			    	  $.messager.alert("提示","角色名称已经存在！","warning");
			      }else{
			    	  $.messager.alert("提示","操作失败！","error",function(){	
			    		  $('#dlg').dialog('close');
				    	  window.location.href =  window.location.href;
			    	  });
			      }
		    	  addCount = 0;
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
			deleteRole(str);
		}else{
			$.messager.alert("提示","请选择你要删除的角色！","warning");
		}
	});
	function deleteRole(ids){
		$.messager.confirm('提示', '是否删除你选择的角色？', function(r){
			if (r){
				$.ajax({
					type:"post",
					url:contextPath + "/role/deleteRole",
					data:({ids : ids}),
					success:function(data){
						if(data.msg == 1){
							$.messager.alert("提示","删除成功！","success",function(){								
								location.href= contextPath + "/role/rolelist";
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
	    		url: contextPath + "/role/rolelist/template",
	    		data:({rolename : $("#hiddenval").val(),pageIndex: page_index+1}),
	    		success:function(data){
	    			$('#content').html(data);
	    			$('.edit').click(function(){
	    				$("#dlg").dialog({
	    					href:contextPath + '/role/updateRole?roleid='+$(this).attr("id"),
	    					height: 130,width: 435,title: "角色信息",
	    					modal: true,closed:false
	    				});
	    				$('#dlg').dialog('open');
	    				$.parser.parse('#dlg');
	    			});
	    			$(".del").click(function(){
	    				var id = $(this).attr("id");
	    				deleteRole(id);
	    			});
	    			$(".access").click(function(){
	    				var id= $(this).attr("id");
	    				var src = contextPath + "/role/assignAccess/"+id;
	    				$("#treedlg").dialog({
	    					href:src,
	    					height: 430,width: 435,title: "权限分配",
	    					modal: true,closed:false
	    				});
	    				$('#treedlg').dialog('open');
	    				$.parser.parse('#treedlg');
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
});
function search(){
	var nameval = $.trim($("#name").val());
	$("#hiddenval").val(nameval);
	$("#name").val(nameval);
	$("#searchForm").submit();	
}