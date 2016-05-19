$(function(){
	var count = 0;
	//隐藏加载效果
	$("#background").hide();
	$("#bar_id").hide();
	
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
	 * 下载导入报表页面
	 */
	$("#leadExcel").click(function(){
		var t_type = $("#cc").combobox("getValue");
		var s_time = $("#s_time").val();
		var e_time = $("#e_time").val();
		var m_time = $("#m_time").val();
		if(validateTime(t_type,s_time,e_time,m_time)){
			var url = contextPath+"/staffAssess/download?s_time="+s_time+"&e_time="+e_time+"&m_time="+m_time+"&t_type="+t_type;
			var obja = document.getElementById("download");
			obja.src = url;
		}
	});
	
	/**
	 * 查询信息
	 */
	$("#search").click(function(){
		var t_type = $("#cc").combobox("getValue");
		var s_time = $("#s_time").val();
		var e_time = $("#e_time").val();
		var m_time = $("#m_time").val();
		if(validateTime(t_type,s_time,e_time,m_time)){
			//罩子加载效果
			$("#background").show();
			$("#bar_id").show();
			$("#searchForm").submit();
		}
	});
	
	/**
	 * 修改起始时间
	 */
	$("#setBtn").click(function(){
		if(count == 0){
			count++;
			var time = $("#reportdate").val();
			var oldtime = $("#reportdate").attr("oldVal");
			if(time!=oldtime){
				$.ajax({
					type:"post",
		    		url: contextPath+"/staffAssess/updateDate",
		    		data: ({time:time}),
		    		success: function(data){
		    			count--;
		    			if(data==0){
		    				$.messager.alert("提示","起始时间设置成功！","success");
		    			}else{
		    				$.messager.alert("提示", "起始时间设置失败！", "error");
		    			}
		    		}
				});
			}else{
				$.messager.alert("提示", "时间没有变动！", "error");
				count--;
			}
		}
	});
	
	/**
	 * 保存增减分数
	 */
	$("#saveScore").click(function(){
		if(count==0){
			count++;
			var groupid='';
			var score='';
			$("input[name='groupid']").each(function(){
				groupid+=$(this).val()+",";
			});
			$("input[name='score']").each(function(){
				var num = $(this).val();
				if(num!=''&&typeof(num)!='undifend'){
					score+=$(this).val()+",";
				}else{
					score+=0+",";
				}
			});
			groupid = groupid.substring(0,groupid.length-1);
			score = score.substring(0,score.length-1);
			var _input = $('input[name="score"]');
			var flag = true;
			for(var i = 0;i<_input.length;i++){
				var _val = $(_input[i]).val();
				if(!/^(-?\d{1,3})(\.\d{1,2})?$/i.test(_val)){
					flag = false;
					$(_input[i]).css('background-color','red');
				}
			}
			if(flag){
				$.ajax({
					type:"post",
		    		url: contextPath+"/staffAssess/updateScore",
		    		data: ({groupid:groupid,score:score}),
		    		success: function(data){
		    			count--;
		    			if(data==0){
		    				$.messager.alert("提示","加减分设置成功！","success");
		    			}else if(data==-1){
		    				$.messager.alert("提示", "数据没有变动！", "error");
		    			}else{
		    				$.messager.alert("提示", "加减分设置失败！", "error");
		    			}
		    		}
				});
			}else{
				$.messager.alert("提示", "请检查数据是否正确！", "error");
				count--;
			}
		}
	});
	
	$('input[name="score"]').bind('focus blur',function(e) {
		if(e.type == 'focus'){
			$(this).css('background-color','');
		}else{
			var _val = $(this).val();
			if(!/^(-?\d{1,3})(\.\d{1,2})?$/i.test(_val)){
				$(this).css('background-color','red');
			}
		}
	});
	//给分数框和下拉条赋宽度
	var width = (parseInt($("#size").val()))*120;
	var cliwidth = document.body.clientWidth*0.98-122;
	$("#cliwidth").css("width",cliwidth);
	$("#zxnbkh").css("width",width);
});
window.onresize = function() {
	var cliwidth = document.body.clientWidth*0.98-122;
	$("#cliwidth").css("width",cliwidth);
};


