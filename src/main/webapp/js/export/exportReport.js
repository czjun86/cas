$(function() {
	// 计算yybb_right的div宽度
	window.onresize();
	//隐藏及时率修改.yybb_left_top,.yybb_right_top的高度
	if((show ==1 && loadType!=1) ||  show == 0){
		$(".yybb_left_top,.yybb_right_top").css('height',419);
		
	}else{
		$(".yybb_left_top,.yybb_right_top").css('height',349);
	}
	//隐藏加载效果
	$("#background").hide();
	$("#bar_id").hide();
	var msg = $("#msg").val();
	if (msg == "1") {
		$.messager.alert("提示", "查询失败！", "error");
	}
	$.parser.parse('#cc');
	//绑定下拉框选择时间
	$("#cc").combobox({
		onSelect : function() {
			//$("#weekend").show();
			var type = parseInt($(this).combobox("getValue"));
			showHide(type);
			changeDefDate(type);
		}
	});
	showHide(parseInt($("#cc").combobox("getValue")));
	//进入页面显示默认时间
	if (show == 0) {
		getLastDay(new Date());
	}
	
	//需要填充kpi名称
	var names = ["total_test","curr_test","total_test_rate","curr_test_rank","total_test_rate_rank","timely_rate","timely_rate_rank"];
	// 填充颜色
	fillBgColor(names,'.yybb_right_top',0);
	// 初始化区域dialog
	initAreaDialog();
	var count = 0;
	$("#setBtn").click(function(){
		if(count == 0){
			var timelyrate = $("#timelyrate").val();
			var reportdate = $("#reportdate").val();
			if(reportdate != null && reportdate != ""){
				if(timelyrate != null && timelyrate != "" && /^[1-9]\d{0,2}$/.exec(timelyrate)){
					count ++;
					$("#setValueForm").ajaxForm({
						type: "POST",
					  	url: contextPath + "/export/updateTest",
					    success: function(data) {
					    	count = 0; 
					      if(data.msg == 1){
					    	  $.messager.alert("提示","设置成功！","success");
					    	  //修改隐藏域的累计起始时间(现在不修改是为了导出时和页面上的数量保持一致)
					    	  //$("#systime").val(reportdate);
					      }else{
					    	  $.messager.alert("提示","设置失败！","error");
					      }
						}
					});
					$("#setValueForm").submit();
				}else{
					$.messager.alert("提示","及时率默认时间只能是在1~999之间的整数!","warning");
				}
			}else{
				$.messager.alert("提示","请选择累计起始时间！","warning");
			}
		}
	});
});

// 查询
function search() {
	if(checkDate()){
		$("#background").show();
		$("#bar_id").show();
		$("#searchForm").submit();
	}

}
// 导出excel
function exportReport() {
	if(checkDate()){
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var areaids = $("#areaids").val();
		var queryids = $("#queryids").val();
		var type = $("#cc").combobox("getValue");
		var url = contextPath + "/export/download?type=" + type + "&starttime="
				+ starttime + "&endtime=" + endtime + "&areaids=" + areaids+"&queryids=" +queryids;
		// $("#download").attr("href",url).click();
		var obja = document.getElementById("download");
		obja.src = url;
	}
}
/**
 * 填充颜色
 */


