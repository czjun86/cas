$(function(){
	var count = 0;
	/**
	 * 考核配置保存
	 */
	$("#saveConfig").click(function(){
		if(count == 0){
			count++;
			var svgstep=getVal('svgstep');
			var annstep=getVal('annstep');
			var basic=getVal('basic');
			var kpi = getVal('kpi');
			var _input = $('input');
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
		    		url: contextPath+"/staffAssess/saveConfig",
		    		data: ({svgstep:svgstep,annstep:annstep,basic:basic,kpi:kpi}),
		    		success: function(data){
		    			count--;
		    			if(data==0){
		    				$.messager.alert("提示","数据保存成功","success",function(){
								 window.location.href =  window.location.href;
							});
		    			}else{
		    				$.messager.alert("提示","数据保存失败","error");
		    			}
		    		}
				});
			}else{
				count--;
				$.messager.alert("提示","请检查数据是否正确！","error");
			}
		}
	});
	
	$('input').bind('focus blur',function(e) {
		if(e.type == 'focus'){
			$(this).css('background-color','');
		}else{
			var _val = $(this).val();
			if(!/^(-?\d{1,3})(\.\d{1,2})?$/i.test(_val)){
				$(this).css('background-color','red');
			}
		}
	});
	$("#reset").click(function(){
		//window.location.href =  window.location.href;
		$.messager.confirm("提示","确认要重置所有内容？",function(r){	
			$("input").each(function(){
				var val = $(this).attr("oldval");
				if(val!=''&&typeof(val)!='undifend'){
					$(this).val(val);
				}
			});
		});
	});
});

function getVal(name){
	var str='';
	var value='';
	$("input[name='"+name+"']").each(function(){
		str = $(this).val();
		if($(this).val()==''||typeof(str)=='undifend'){
			value += 0+",";
		}else{
			value += str+",";
		}
	});
	return value.substring(0, value.length-1);
}