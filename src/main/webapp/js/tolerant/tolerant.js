$(function(){
	var optInit = {callback:function(page_index,jq){
		$.ajax({
			type:"post",
    		url: contextPath + "/tolerant/tolerant/template",
    		data:({startTime: $("#startTimeHidden").val(),endTime: $("#endTimeHidden").val(),serialno: $("#serialnoHidden").val(),isTolerant:$("#isTolerantHidden").val(),areaids:$("#areaidsHidden").val(),pageIndex: page_index}),
    		success: function(data){
    			$("#content").html(data);

    			$(".cache1").live('mouseenter',function(){
    				$(this).css('text-decoration','underline');
    				$(this).css('color','rgb(0,0,255)');
    				$(this).css('opacity',1);
    			});
    			$(".cache1").live('mouseleave',function(){
    				$(this).css('text-decoration','');
    				$(this).css('color','');
    				$(this).css('opacity','');
    			});
    		}
		});
	}};
	$("#pager").pagination(pageTotal, optInit);//回调
	
	
	//增加页面打开
	$('.compare_test').live('click',function(){
		var areaId = $(this).attr('val');
		var serialno = $(this).attr('serialno');
		$("#dlg").dialog({
			href:contextPath + '/tolerant/edit?serialno='+serialno+"&areaId="+areaId,
			height: 400,width: 750,title: "请选择容差指标",
			modal: true,closed:false
		});
		$('#dlg').dialog('open');
		$.parser.parse('#dlg');
	});
	
	//保存容差修改
	$(".save").live('click' ,function(){
		var str="";
		$("input[name = \"tolerantval\"]:checked").each(function(){   
			str+=$(this).val()+",";   
		});
		str = str.substring(0,str.length-1);
		var serialno = $("#serialno_info").val();
		var areaId = $("#areaId_info").val();
		$.ajax({
			type:"post",
			url: contextPath + "/tolerant/update",
			data:({serialno : serialno ,areaId : areaId , ids : str}),
			success:function(data){
				if(data.msg == 1){
					$('#dlg').dialog('close');
					$.messager.alert("提示","修改成功！","success" ,function(){
						$("#reload").submit();
					});
				}else{
					$.messager.alert("提示","删除失败！","error");
				}
			}
		});
	});
	
	$("#areas").combobox({
		onShowPanel:function(){
			$("#areas").combobox("hidePanel");
			var src = contextPath + '/workorder/arealist?areaids='+$("#areaids").val()+"&type=0";
			$("#areadlg").dialog({
				href:src,
				height: 400,width: 380,title: "选择区域",
				modal: true,closed:false
			});
			$('#areadlg').dialog('open');
			$.parser.parse('#areadlg');
			$(".sel_area").unbind('click').click(function(){
				var nodes = $('#areadlg').tree('getChecked');
				var strtext = "";
				var	strids = "";
				if(nodes.length > 0){
					for(var i = 0; i < nodes.length; i++){
						strids += nodes[i].id + ",";
						strtext += nodes[i].text + ",";
					}
					if(strids !=null && strids != ""){
						strids = strids.substring(0,strids.length-1);
						$("#areaids").val(strids);
					}
					if(strtext !=null && strtext != ""){
						strtext = strtext.substring(0,strtext.length-1);
						$("#areas").combobox("setText",strtext);
						$("#areatext").val(strtext);
					}
				}else{
					$("#areaids").val('-1');
					$("#areatext").val('全部');
					$("#areas").combobox("setText",'全部');
				}
				$('#areadlg').dialog('close');
				
			});
		}
	});
});
function search(){
	var startTime = $("input[name='startTime']").val();
	var endTime = $("input[name='endTime']").val();
	if(startTime!=null&&startTime!=''&&endTime!=null&&endTime!=''){
		$("#searchForm").submit();
	}else{
		$.messager.alert("警告","时间不能为空！","error");
	}
}