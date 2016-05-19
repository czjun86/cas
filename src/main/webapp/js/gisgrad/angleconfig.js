$(function(){
	var num = 0;
	/**
	 *修改角度配置
	 */
	$("#saveangle").click(function(){
		if(num == 0){
			num++;
			$('#dataForm').ajaxForm({
				url: contextPath + "/gisgrad/saveangle",
				type:'post',
				beforeSubmit:function(){ 
					var ty = $("#angletype").val();
					var an = $("#angle").val();
					
					if(parseInt(ty)==0){
						if(an==null||an==""){
							$.messager.alert("提示","角度不能为空","success");
							num=0;
							return false;
						}
						if(parseInt(an)<0 || parseInt(an)>360){//判定取值范围
							$.messager.alert("提示","角度应该0-360","success");
								num=0;
								return false;
						}
					}
						return true;
		        },
				success:function(data){
					if(data == 1){
						num=0;
						$.messager.alert("提示","角度配置修改成功！","success",function(){
							location.href = contextPath+"/gisgrad/angleconfig";
						});
					}else{
						num=0;
						$.messager.alert("提示","角度配置修改失败！","success",function(){
							location.href = contextPath+"/gisgrad/angleconfig";
						});
					}
				}
			});
			$('#dataForm').submit();
		}
	});
	
	/**
	 * 当选中自身方位角的时候角度不输入
	 */
		$("#angletype").combobox({
			onSelect:function(){
				var nowvalue = $(this).combobox('getValue');
				if(parseInt(nowvalue) == 0){
					$("#angleli").show();
				}else if(parseInt(nowvalue) == 1){
					$("#angleli").hide();
				}
			},
			onBeforeLoad:function(){
				var nowvalue = $(this).combobox('getValue');
				if(parseInt(nowvalue) == 0){
					$("#angleli").show();
				}else if(parseInt(nowvalue) == 1){
					$("#angleli").hide();
				}
			}
		});
});