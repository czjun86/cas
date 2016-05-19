$(function() {
	
	//隐藏加载效果
	$("#background").hide();
	$("#bar_id").hide();
	
	//判断是否查询失败
	var msg = $("#msg").val();
	if (msg == "1") {
		$.messager.alert("提示", "查询失败！", "error");
	}
	
	$("#cc").combobox({
		onSelect : function() {
			var type = parseInt($(this).combobox("getValue"));
			if(type == 1){
				$("#m_timetitle").hide();
				$("#m_timediv").hide();
				$("#s_timetitle").show();
				$("#s_timediv").show();
				$("#e_timetitle").show();
				$("#e_timediv").show();
			}else if(type == 2){
				$("#m_timetitle").show();
				$("#m_timediv").show();
				$("#s_timetitle").hide();
				$("#s_timediv").hide();
				$("#e_timetitle").hide();
				$("#e_timediv").hide();
			}
			$("#t_type").val(type);
		}
	});
	
	
	/**
	 * 打开区域选择
	 */
	$("#areas").combobox({
		onShowPanel : function() {
			$("#areas").combobox("hidePanel");
			var qids = $("#queryids").val();
			var src = contextPath + '/complain/arealist?areaids='+qids;
			$("#areadlg").dialog({
				href:src,
				height: 400,width: 380,title: "选择区域",
				modal: true,closed:false
			});
			$('#areadlg').dialog('open');
			$.parser.parse('#areadlg');
			$("#areadlg");
		}
	});
	
	/**
	 * 点击确定,将区域值赋值给父页面hidden
	 */
	$(".sel_area").unbind('click').click(function() {
		var nodes = $('#areadlg').tree('getChecked');
		var strtext = "";
		var strids = "";
		var queryids = "";
		if (nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				var attr = nodes[i].attributes;
				if(attr !=null &&  attr!=""){
					strids += nodes[i].attributes + ",";
					queryids +=nodes[i].id+",";
				}else{
					queryids +=nodes[i].id+",";
					strids += nodes[i].id + ",";
				}
				strtext += nodes[i].text + ",";
			}
			if (strids != null && strids != "") {
				strids = strids.substring(0,
						strids.length - 1);
			}
			if(queryids != null && queryids!=""){
				queryids = queryids.substring(0,
						queryids.length - 1);
			}
			$("#queryids").val(queryids);
			$("#area_id").val(strids);
			if (strtext != null && strtext != "") {
				strtext = strtext.substring(0,
						strtext.length - 1);
				$("#areas")
						.combobox("setText", strtext);
				$("#areatext").val(strtext);
			}
		} else {
			$("#queryids").val('-1');
			$("#area_id").val('-1');
			$("#areatext").val('全部');
			$("#areas").combobox("setText", '全部');
		}
		$('#areadlg').dialog('close');
	});
	/**
	 * 下载导入报表页面
	 */
	$("#leadExcel").click(function(){
		var t_type = $("#cc").combobox("getValue");
		var s_time = $("#s_time").val();
		var e_time = $("#e_time").val();
		var m_time = $("#m_time").val();
		var area_id = $("#area_id").val();
		if(area_id!=''&&typeof(area_id)!='undefined'){
			if(validateTime(t_type,s_time,e_time,m_time)){
				var url = contextPath+"/complain/download?area_id="+area_id+"&s_time="+s_time+"&e_time="+e_time+"&m_time="+m_time+"&t_type="+t_type;
				var obja = document.getElementById("download");
				obja.src = url;
			}
		}else{
			$.messager.alert("提示", "区域不能为空！", "warning");
		}
	});
	$("#search").click(function(){
		var t_type = $("#cc").combobox("getValue");
		var s_time = $("#s_time").val();
		var e_time = $("#e_time").val();
		var m_time = $("#m_time").val();
		var area_id = $("#area_id").val();
		if(area_id!=''&&typeof(area_id)!='undefined'){
			if(validateTime(t_type,s_time,e_time,m_time)){
				//罩子加载效果
				$("#background").show();
				$("#bar_id").show();
				$("#searchForm").submit();
			}
		}else{
			$.messager.alert("提示", "区域不能为空！", "warning");
		}
	});
});

$(function(){
	var scWidth = document.body.scrollWidth;
	var rightWidth =scWidth*0.98;
	$(".tstj_right").css("width", rightWidth);
});
window.onresize = function() {
	var scWidth = document.body.scrollWidth;
	var rightWidth =scWidth*0.98;
	$(".tstj_right").css("width", rightWidth);
}
function showError() {
	$.messager.alert("提示", "导出报表失败！", "error");
}