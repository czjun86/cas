$(function() {
	var addCount = 0;
	var editCount = 0;
	var leadCount = 0;
	$('.add').click(function(){
		$("#dlg").dialog({
			href:contextPath + '/epinfo/addEpinfo',
			height: 330,width: 880,title: "终端用户信息",
			modal: true,closed:false,
			draggable:false
		});
		$('#dlg').dialog('open');
		$.parser.parse('#dlg');
		
		var count = 0;
		$(".change").die().live("click",function(){ 
			var name = $(this).attr("name");
			var _ul = $(this).parents("ul");
			if(name == "add"){
				var model = $(epinfomore());
				_ul.after(model);
				$.parser.parse(model);
				count++;
			}else{
				$(this).parents("ul").remove();
				count--;
			}
		});
	});
	
	$(".saveedit").click(function(){
		var type = $("#type").val();
		if(type == 0){
			if(addCount == 0){
				addCount ++;
				var url = contextPath + "/epinfo/addEpinfo";
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
							$.messager.alert('提示','操作成功！',"success",function(){
								$('#dlg').dialog('close');
								window.location.href =  window.location.href;
							});
						}else if(data.msg == -1){
							$.messager.alert("提示","UUID已经存在！","warning");
							
						}else if(data.msg == -2){
							$.messager.alert("提示","请不要输入相同的UUID！","warning");
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
						addCount = 0;
					}
				});
				$("#dataForm").submit();
			}
		}else{
			if(editCount == 0){							
				editCount++;
				var url = contextPath + "/epinfo/updateEpinfo";
				$('#dataForm').ajaxForm({
					url: url,
					beforeSubmit:function(){ 
			            if(!$('#dataForm').form('validate')){
			            	editCount = 0;
			            	return false;
			            }else{
			            	return true;
			            }
			        },
					success: function(data) {
						if(data.msg == 1){
							$.messager.alert('提示','操作成功！',"success",function(){
								$('#dlg').dialog('close');
								window.location.href =  window.location.href;
							});
							
						}else if(data.msg == -100){
							//重复提交
							$('#dlg').dialog('close');
							window.location.href =  window.location.href;
						}else if (data.msg == -1){
							$.messager.alert("提示","UUID已经存在！","warning");
						}else{
							$.messager.alert("提示","操作失败！","error",function(){	
								$('#dlg').dialog('close');
								window.location.href =  window.location.href;
							});
						}
						editCount = 0;
					}
				});
				$("#dataForm").submit();
			}
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
			deleteInfo(str);
		}else{
			$.messager.alert("提示","请选择你要删除的终端用户信息！","warning");
		}
	});
	function deleteInfo(ids){
		$.messager.confirm('提示', '是否删除你选择的终端用户？', function(r){
			if (r){
				$.ajax({
					type:"post",
					url: contextPath + "/epinfo/deleteEpinfo",
					data:({ids : ids}),
					success:function(data){
						if(data.msg == 1){
							$.messager.alert("提示","删除成功！","success",function(){								
								location.href=contextPath +"/epinfo/epinfolist";
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
	    		url: contextPath + "/epinfo/epinfolist/template",
	    		data:({uuid : uuid,pageIndex: page_index+1}),
	    		success:function(data){
	    			$('#content').html(data);
	    			$('.edit').click(function(){
	    				$("#dlg").dialog({
	    					href: contextPath + '/epinfo/updateEpinfo?id='+$(this).attr("id"),
	    					height: 360,width: 500,title: "终端用户信息",
	    					modal: true,closed:false,
	    					draggable:false
	    				});
	    				$('#dlg').dialog('open');
	    				$.parser.parse('#dlg');
	    			});
	    			$(".del").click(function(){
	    				var id = $(this).attr("id");
	    				deleteInfo(id);
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
	 * 下载工单信息报表
	 */
	$("#download").click(function(){
		location.href = contextPath+"/epinfo/download";
	});
	/**
	 * 下载导入报表页面
	 */
	$("#getTemplate").click(function(){
		location.href = contextPath+"/epinfo/getTemplate";
	});
	/**
	 * 导出错误文件
	 */
	$(".errordown").click(function(){
		location.href = contextPath+"/epinfo/getErrorFile?errorname="+$("#errordown").val();
	});
	/**
	 * 导入excel
	 */
	
	$("#leadsave").click(function(){
		var name = $("#file").val();
		if(name!=""){
			if(name.split(".")[1] =="xlsx"){
				document.getElementById("leadExcel").submit();
				//$('#epleaddlg').dialog('close');
			}else{
				$.messager.alert("提示","文件类型不正确！","error");
				$("#fileshow").val("");
			}
		}else{
			$.messager.alert("提示","请选择文件！","error");
			$("#fileshow").val("");
		}
	});
	/**
	 * 导入弹框
	 */
	$('#lead').click(function(){
		$("#epleaddlg").dialog({
			height: 150,width: 360,title: "导入模板",
			modal: true,closed:false,
			draggable:false
		});
		$('#epleaddlg').dialog('open');
		$.parser.parse('#epleaddlg');
		$("#file").css("display","block");
		$("#epleaddlg-buttons").css("display","block");
		$("#ultext").css("display","block");
	});
	$("#file").change(function(){
		$("#fileshow").val($(this).val());
	});
});
function search(){
	uuid = $.trim($("#uuid").val());
	$("#uuid").val(uuid);
	$("#searchForm").submit();	
}
function lead(value,addnum,updatenum,unchangenum,errorname){
	if(value == 0){
		var addstr ='';
		var updatestr ='';
		var unchangestr = '';
		var jd = "";
		if(parseInt(addnum)!=0||parseInt(updatenum)!=0){
			if(parseInt(addnum)!=0){
				addstr = "新增 "+addnum+" 个";
			}
			if(parseInt(updatenum)!=0){
				updatestr = "修改 "+updatenum+" 个";
			}
			if(parseInt(unchangenum)!=0){
				unchangestr = "有 "+unchangenum+" 个没有变动";
			}
			$.messager.alert("提示",addstr+updatestr+unchangestr,"success",function(){								
				location.href=contextPath +"/epinfo/epinfolist";
			});
		}else{
			jd = "没有变动";
			$.messager.alert("提示",jd+addstr+updatestr+unchangestr,"success",function(){								
				location.href=contextPath +"/epinfo/epinfolist";
			});
		}
	}else if(value == 3){
		$.messager.alert("提示","数据有错","success",function(){								
			location.href=contextPath +"/epinfo/epinfolist?errorname="+errorname;
		});
	}
}