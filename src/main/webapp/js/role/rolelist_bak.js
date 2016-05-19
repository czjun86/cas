$(function() {
	var verfier = new SJ.Component['JVerfier']();
	var popup = null;
	var addCount = 0; 
	var params = {
		title: '角色信息',
		width: 400,
		height: 175,
		button: {
			sure: {
				text: '确定', isClose: false, fn: function(){
					var result = verfier.verfier();
					if(result && result.length > 0){
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
							    success: function(data) {
							      if(data.msg == 1){
							    	  $.messager.alert("提示","操作成功！","",function(){	
								    	  window.location.href =  window.location.href;
								    	  popup.close();
							    	  });
							      }else if(data.msg == -100){
							    	  //请不要重复提交！
							    	  window.location.href =  window.location.href;
							      }else if(data.msg == -1){
							    	  $.messager.alert("提示","角色名称已经存在！","warning");
							      }else{
							    	  $.messager.alert("提示","操作失败！","error",function(){	
								    	  window.location.href =  window.location.href;
							    	  });
							      }
						    	  addCount = 0;
								}
							});
							$("#dataForm").submit();
						}
					}
				}
			}
		}
	};
	popup = new SJ.Component['JPopUps'](params);
	
	var popup2 = null;
	var params = {
		title: '权限选择',
		width: 500,
		height: 380
	};
	popup2 = new SJ.Component['JPopUps'](params);
	
	$('.add').click(function(){
		popup.load( contextPath + '/role/addRole', null, function(){
			verfier.init();
		});
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
							$.messager.alert("提示","删除成功！","",function(){								
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
	    				popup.load( contextPath + '/role/updateRole?roleid='+$(this).attr("id"), null, function(){
	    					verfier.init();
	    				});
	    			});
	    			$(".del").click(function(){
	    				var id = $(this).attr("id");
	    				deleteRole(id);
	    			});
	    			$(".access").click(function(){
	    				var id= $(this).attr("id");
	    				src = contextPath + "/role/assignAccess/"+id;
	    				popup2.open({
	    					html:'<iframe src='+src+' id="assign" style="width:100%;height:100%;" frameborder="0"></iframe>',
	    					button: {
	    						sure: { text: '确定', isClose: true, fn: function(){
	    								$($('#assign')[0].contentWindow.document).find('#sure').click();
    									var v = $("#msg").val();
    									$.ajax({
    										type:"post",
    										url: contextPath + "/role/updateAccess",
    										data:{ids:v,roleid:id},
    										success:function(data){
    											if(data.msg == 1){
    												$.messager.alert("提示","权限分配成功！","",function(){		    		
    		    										top.parent.location.href =  top.parent.location.href;
    		    									});
    											}else{
    												$.messager.alert("提示","权限分配失败！","error");
    											}
    										}
    									});
	    							}
	    						}
	    					}
	    				});
	    				$(".pop_up_center").css("padding-bottom","0px");
	    				$(".pop_up_center").css("padding-top","0px");
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