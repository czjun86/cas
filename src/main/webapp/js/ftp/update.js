$(function(){
	$("#parent").attr('checked',false);
	$("#parent").click(function () {
		if($("#parent").attr('checked') == undefined){			
			$('input[name="ids"]').attr('checked',false);
		}else{			
			$('input[name="ids"]').attr('checked',true);
		}
    }); 
	var count =0;
	//增加页面打开
	$('.add').click(function(){
		$("#dlg").dialog({
			href:contextPath + '/ftp/addftp',
			height: 510,width: 435,title: "新增FTP配置",
			modal: true,closed:false
		});
		$('#dlg').dialog('open');
		$.parser.parse('#dlg');
		$("#valiSelect").val("0");
	});
	//修改和新增保存
	$('.saveedit').click(function(){
		if(count==0){
			count++;
			var operationType =  $("#type").val();
			if(parseInt(operationType)==0){
				//0为新增
				$('#dataForm').ajaxForm({
				  	url: contextPath + "/ftp/addftp",
				  	beforeSubmit:function(){
			            if(!$('#dataForm').form('validate')){
			            	count=0;
			            	return false;
			            }else{
			            	$(".right1").show();
			            	return true;
			            }
			        },success:function(data) {
			        $(".right1").hide();
						count=0;
				      addOrEdit(data.msg,data.statuNull);
					}
				});
				$("#dataForm").submit();
			}else if(parseInt(operationType)==1){
				//1为修改
				$('#dataForm').ajaxForm({
				  	url: contextPath + "/ftp/editftp",
				  	beforeSubmit:function(){
			            if(!$('#dataForm').form('validate')){
			            	count=0;
			            	return false;
			            }else{
			            	$(".right1").show();
			            	return true;
			            }
			        },success:function(data) {
			        $(".right1").hide();
						count=0;
					    addOrEdit(data.msg,data.statuNull);
					}
				});
				$("#dataForm").submit();
			}
		}
	});
	var optInit = {callback:function(page_index,jq){
			$.ajax({
				type:"post",
	    		url: contextPath + "/ftp/updateFtp/template",
	    		data:({name: $("#hiddenval").val(),pageIndex: page_index}),
	    		success: function(data){
	    			$("#content").html(data);
				    //单个删除
				  	$(".del").click(function(){
				  		var str = $(this).attr("id");
				  		var staid = $("input[name='sta']").val();
				  		var flag = false;
				  		if(parseInt(staid)==parseInt(str)){
				  			flag=true
				  		}
				  			deleteFtp(str,flag,'single');
				  	});
					//修改页面打开
				  	$('.edit').click(function(){
				  		var id = $(this).attr("id");
						$("#dlg").dialog({
							href:contextPath + '/ftp/editftp?id='+id,
							height: 510,width: 435,title: "修改FTP配置",
							modal: true,closed:false
						});
						$('#dlg').dialog('open');
						$.parser.parse('#dlg');
						$("#valiSelect").val("1");
					});
				  	//启用该项
					$(".staUse").click(function(){
						var sta = $(this).attr("name");
						var sid = parseInt($("#num"+sta).attr("name"));
						var id = parseInt(sta);
						if(sid==0){
							$.messager.confirm("提示","确认启用此项配置！",function(r){	
								if(r){
							  		$.ajax({
							  			type:'post',
									  	url: contextPath + "/ftp/staUse",
									  	data: ({id:id}),
									  	success:function(data) {
								        	if(data.msg==1){
								        		$.messager.alert("提示","启用成功！","success",function(){
													location.href= contextPath + "/ftp/updateFtp";
												});
								        	}else if(data.msg==0){
								        		$.messager.alert("提示","启用失败！","error");
								        	}
								        }
							  	  });
								}
							});
						}else if(sid==1){
							$.messager.alert("提示","此项已启动！","error");
						}
					});
					//修改该项
					$(".staOff").click(function(){
						var sta = $(this).attr("name");
						var sid = parseInt($("#num"+sta).attr("name"));
						var id = parseInt(sta);
						if(sid==1){
							$.messager.confirm("提示","确认要禁用这项配置！",function(r){	
								if(r){
							  		$.ajax({
							  			type:'post',
									  	url: contextPath + "/ftp/staOff",
									  	data: ({id:id}),
									  	success:function(data) {
								        	if(data.msg==1){
								        		$.messager.alert("提示","禁用成功！","success",function(){
													location.href= contextPath + "/ftp/updateFtp";
												});
								        	}else if(data.msg==0){
								        		$.messager.alert("提示","禁用失败！","error");
								        	}
								        }
							  	  });
								}
							});
						}else if(sid==0){
							$.messager.alert("提示","此项尚未启动！","error");
						}
					});
					
	    		}
			});
		}
	};
	$("#pager").pagination(pageTotal, optInit);//回调
	
	/**
	 * 多个删除
	 */
	$("#delall").click(function(){
		var str = "";
		var staid = $("input[name='sta']").val();
		var flag = false;
		$("input[name='ids']:checked").each(function(){
			str +=$(this).val()+",";
			if($(this).val()==staid){
				flag = true;
			}
		});
		if(str!=""&&str.length>1){
			str = str.substring(0, str.length-1);
			deleteFtp(str,flag,'multiterm');
		}else{
			$.messager.alert("提示","请选择你要删除的FTP配置！","warning");
		}
	});
});
/**
 * 执行删除方法
 */
function deleteFtp(ids,flag,quantity){
	var word ;
	if(flag){
		if(quantity == 'multiterm'){
			word = '将要删除的选项中包含启用项,是否要继续删除？';
		}else if(quantity == 'single'){
			word = '将要删除的为启用项,是否要继续删除？';
		}
	}else{
		word = '是否删除你选择的FTP配置？';
	}
	$.messager.confirm('提示', word,function(r){
		if(r){
			$.ajax({
				type:'post',
				url:contextPath +"/ftp/delftp",
				data:({ids:ids}),
				success:function(data){
					if(data.msg==1){
						$.messager.alert("提示","删除成功！","success",function(){
							location.href= contextPath + "/ftp/updateFtp";
						});
					}else{
						$.messager.alert("提示","删除失败！","error");
					}
				}
			});
		}
	});
}
/**
*修改和新增返回结果
*/
function addOrEdit(result,sta){
	var msg = parseInt(result);
	if(msg == 0){
  	  $.messager.alert("提示","FTP服务器新增成功！","success",function(){
  		  if(parseInt(sta)==0){
  			$.messager.alert("提示","你还没有任何一项配置被启用！","success",function(){
  				location.href= contextPath + "/ftp/updateFtp";
  			});
  		  }else{
  			  location.href= contextPath + "/ftp/updateFtp";
  		  }
  	  });
    }else if(msg == 1){
  	  $.messager.alert("提示","FTP服务器修改成功！","success",function(){
  		  if(parseInt(sta)==0){
  			$.messager.alert("提示","你还没有任何一项配置被启用！","success",function(){
  				location.href= contextPath + "/ftp/updateFtp";
  			});
  		  }else{
  			  location.href= contextPath + "/ftp/updateFtp";
  		  }
  	  });
    }else if(msg == 3){
  	  $.messager.alert("提示","IP或端口号错误！","error",function(){
  	  });
    }else if(msg == 4){
  	  $.messager.alert("提示","用户或密码错误！","error",function(){
  	  });
    }else if(msg == 5){
  	  $.messager.alert("提示","FTP服务器连接失败,请检查！","error",function(){
  	  });
    }else if(msg == 6){
  	  $.messager.alert("提示","FTP下行文件的路径不存在！","error",function(){
  	  });
    }else if(msg == 8){
      $.messager.alert("提示","FTP下行文件不存在！","error",function(){
      });
    }else if(msg == 9){
  	  $.messager.alert("提示","编号已存在！","error",function(){	
  	  });
    }else if(msg == 10){
  	  $.messager.alert("提示","编号为10位以内纯数字！","error",function(){
  	  });
    }else{
  	  $.messager.alert("提示","FTP服务器修改失败！","error",function(){	
  	  });
    }
}
function search(){
	var nameval = $.trim($("#name").val());
	$("#hiddenval").val(nameval);
	$("#name").val(nameval);
	$("#searchForm").submit();	
}