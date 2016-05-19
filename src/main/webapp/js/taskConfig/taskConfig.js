var countValidate= 0;
$(function(){
	var optInit = {callback: 
    	function(page_index,jq){
	        $.ajax({
	        	type:"post",
	    		url: contextPath + "/taskconfig/taskconfig/template",
	    		data:({val : $("#val").val(),isverify : $("#isverify").val(),
	    			validstate : $("#validstate").val(),timeType : $("#timeType").val(),
	    			userid:$("#userid").val(),pageIndex: page_index+1}),
	    		success:function(data){
	    			$('#content').html(data);
	    			/**
	    			 * 打开修改工单
	    			 */
	    			$('.edit').click(function(){
	    				$("#dlgedit").dialog({
	    					href:contextPath + '/taskconfig/updateTask?id='+$(this).attr("name"),
	    					height: 465,width: 435,title: "修改工单",
	    					modal: true,closed:false,
	    					draggable:false
	    				});
	    				$('#dlgedit').dialog('open');
	    				$.parser.parse('#dlg');
	    			});
	    			
	    			/**
	    			 * 启用工单
	    			 */
	    			$(".staUse").live('click',function(){
	    				var url = contextPath + "/taskconfig/setValidstate";
	    				$.ajax({
	    					type:"post",
	    					url: url,
	    					data: {id:$(this).attr("name"),validstate:0},
	    					success: function(data){
	    						if(data==1){
	    							$.messager.alert("提示","修改成功！","success",function(){
	    								location.href= contextPath + "/taskconfig/taskconfig";
	    							});
	    						}else{
	    							$.messager.alert("提示","修改失败！","error");
	    						}
	    					}
	    				});
	    			});
	    			/**
	    			 * 禁用工单
	    			 */
	    			$(".staOff").live('click',function(){
	    				var url = contextPath + "/taskconfig/setValidstate";
	    				$.ajax({
	    					type:"post",
	    					url: url,
	    					data: {id:$(this).attr("name"),validstate:1},
	    					success: function(data){
	    						if(data==1){
	    							$.messager.alert("提示","修改成功！","success",function(){
	    								location.href= contextPath + "/taskconfig/taskconfig";
	    							});
	    						}else{
	    							$.messager.alert("提示","修改失败！","error");
	    						}
	    					}
	    				});
	    			});
	    		}
	        });
	        return false;
    	}
	};
	$("#pager").pagination(pageTotal, optInit);
	
	/**
	 * 打开新增工单
	 */
	$('.add').click(function(){
		if(noRegions()){
			$("#dlg").dialog({
				href:contextPath + '/taskconfig/addTask?userid='+$("#userid").val(),
				height: 330,width: 880,title: "新增工单",
				modal: true,closed:false,
				draggable:false
			});
			$('#dlg').dialog('open');
			$.parser.parse('#dlg');
		}
	});
	
	/**
	 * 新增工单点击加一行
	 */
	$(".change").die().live("click",function(){ 
		var name = $(this).attr("name");
		var _ul = $(this).parents("ul");
		if(name == "add"){
			var model = $(taskmore());
			_ul.after(model);
			$.parser.parse(model);
		}else{
			$(this).parents("ul").remove();
		}
	});
	
	/**
	 * 保存新增
	 * 
	 */
	$(".saveadd").live('click',function(){
		var flag = true;
		$(".areaid").each(function(){
			var val = $(this).combobox("getValue");
			if(val==null || val== ''){
				flag = false;
			}
		});
		$("input[name='testaddress']").each(function(){
			var val = $(this).val();
			if(val==null || val== ''){
				flag = false;
			}
		});
		if(flag){
			if(countValidate==0){
				countValidate++;
				var url = contextPath + "/taskconfig/saveAddTask";
				$('#dataForm').ajaxForm({
					url: url,
					success: function(data) {
						countValidate = 0;
						if(data==1){
							$.messager.alert("提示","保存成功！","success",function(){
								location.href= contextPath + "/taskconfig/taskconfig";
							});
						}else{
							$.messager.alert("提示","保存失败！","error");
						}
					}
				});
				$("#dataForm").submit();
			}
		}else{
			$.messager.alert("提示","必选项不能为空！","error");
		}
	});
	
	/**
	 * 保存工单修改
	 */
	$(".saveedit").live('click',function(){
		var testaddress = $("#testaddress").val();
		if(testaddress==null&&testaddress==''){
			$.messager.alert("提示","必选项不能为空！","error");
		}else{
		if(countValidate==0){
			countValidate++;
				var url = contextPath + "/taskconfig/saveUpdateTask";
				$('#dataForm').ajaxForm({
					url: url,
					success: function(data) {
						countValidate = 0;
						if(data==1){
							$.messager.alert("提示","保存成功！","success",function(){
								location.href= contextPath + "/taskconfig/taskconfig";
							});
						}else{
							$.messager.alert("提示","保存失败！","error");
						}
					}
				});
				$("#dataForm").submit();
			}
		}
	});
	
	/**
	 * 下载模板
	 */
	$("#getTemplate").click(function(){
		if(noRegions()){
			if(countValidate==0){
				countValidate++;
				 $.ajax({
			        	type:"post",
			    		url: contextPath + "/taskconfig/getTemplate",
			    		success:function(data){
			    			countValidate = 0;
			    			if(data=="-1"){
			    				$.messager.alert("提示","生成模版失败！","error");
			    			}else{
			    				location.href=contextPath +"/taskconfig/getExcel?name="+data;
			    			}
			    		},
			    		error:function(){
			    			countValidate = 0;
			    			$.messager.alert("提示","下载模板出错！","error");
			    		}
				 });
			}
		}
	});
	
	/**
	 *下拉条件筛选
	 */
	$(".queryCondition").combobox({
		onChange : function(newValue) {
			var val = newValue.split(',');
			var dom = val[0];
			var count = val[1];
			$("#"+dom).val(parseInt(count));
			searchForm.submit();
		}
	});
	
	/**
	 * 打开报表导出条件
	 */
	$("#download").click(function(){
		if(noRegions()){
			$("#dlgexcel").dialog({
				href:contextPath + '/taskconfig/excelTask',
				height: 265,width: 425,title: "报表条件",
				modal: true,closed:false,
				draggable:false
			});
			$('#dlgexcel').dialog('open');
			$.parser.parse('#dlg');
		}
	});
	
	/**
	 * 提交导出条件表单
	 */
	$(".leadExcel").live('click',function(){
		$('#dlgexcel').dialog('close');
		var url = contextPath + "/taskconfig/createExcel";
		$('#excelForm').ajaxForm({
			url: url,
			success: function(data) {
				if(data!=-1){
					location.href=contextPath +"/taskconfig/getExcel?name="+data;
				}else{
					$.messager.alert("提示","导出失败！","error");
				}
			}
		});
		$("#excelForm").submit();
	});
	
	/**
	 * 导入弹框
	 */
	$('#lead').click(function(){
		if(noRegions()){
			$("#fileshow").val("");
			$("#taskleaddlg").dialog({
				height: 150,width: 360,title: "导入模板",
				modal: true,closed:false,
				draggable:false
			});
			$('#taskleaddlg').dialog('open');
			$.parser.parse('#taskleaddlg');
			$("#file").css("display","block");
			$("#taskleaddlg-buttons").css("display","block");
			$("#ultext").css("display","block");
		}
	});
	$("#file").change(function(){
		$("#fileshow").val($(this).val());
	});
	
	/**
	 * 提交导入模板
	 */
	$("#leadsave").click(function(){
		var flag = false;
		var val = $("#fileshow").val();
		if(val==null||val==""){
			$.messager.alert("提示","请选择文件！","warning");
			flag = false;
		}else if(splitString(val)){
			flag = true;
		}
		if(flag){
			document.getElementById("analysisExcel").submit();
			$('#taskleaddlg').dialog('close');
		}
	});
});

function splitString(val){
	var str = val.split(".");
	if(str.length>0&str[1]=="xlsx"){
		return true;
	}else{
		$.messager.alert("提示","请选择正确的文件类型！","warning");
		return false;
	}
}
/**
 * 导入结果判定
 * @param value
 */
function lead(value){
	if(value=="1"){
		$.messager.alert("提示","导入成功！","success",function(){
			location.href= contextPath + "/taskconfig/taskconfig";
		});
	}else if(value=="-1"){
		$.messager.alert("提示","导入失败！","error");
	}else{
		$.messager.alert("提示","导入错误,导出错误模板！","error",function(){
			location.href=contextPath +"/taskconfig/getExcel?name="+value;
		});
	}
}
/**
 * 工单号或区域查询
 */
function search(){
	$("input[name='val']").val($("#val").val());
	searchForm.submit();
}
/**
*判断有无区域
*/
function noRegions(){
	var flag = $("#regions").val();
	if(flag==1){
		return true;
	}else{
		$.messager.alert("提示","你没有任何区域权限,请联系管理员！","error");
		return false;
	}
}