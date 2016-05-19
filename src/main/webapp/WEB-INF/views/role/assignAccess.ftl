<ul id="tt" class="easyui-tree"></ul>
<input type="hidden" id="hidenroleid" value="${roleid!}"/>
<script>
var contextPath = '${application.getContextPath()}';
var access = 0;
$('#tt').tree({  
   url:contextPath +"/role/assign?roleid="+${roleid!}+"&timesamp="+new Date().getTime(),
   animate:true,
   checkbox:true,
   cascadeCheck:true,
   onBeforeCheck: function(node){
   		//勾选项是否有关联项，有就勾上
		if(node.attributes!=0){
			if(!node.checked){
				var changeNode = $('#tt').tree('find', node.attributes);
				if (changeNode){
					$('#tt').tree('update', {
						target: changeNode.target,
						checked: true
					});
				}
			}
		}
		//判断勾选项是否取消，是否有被其他关联，有就取消打钩
		if(node.checked){
			var CheckedNodes = $('#tt').tree('getChecked');
			for(var i in CheckedNodes){
				var nodes = CheckedNodes[i];
				if(nodes.attributes!=0 && nodes.attributes ==node.id){
					$('#tt').tree('update', {
						target: nodes.target,
						checked: false
					});
				}
			}
		}
	}
});
$(".treebtn").click(function(){
	if(access == 0){
		access ++;
		var nodes = $('#treedlg').tree('getChecked');
		var s = '';
		var checkbox2 = $('#treedlg').find("span.tree-checkbox2").parent();
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		$.each(checkbox2,function(){
	      var node = $.extend({}, $.data(this, "tree-node"), {
	        target : this
	      });
	     if (s != '') s += ',';
			s += node.id;
	    });
	    var norm = s.split(",");
	    var reportedit = false;
	    var reportWatch = false;
	    for(var j=0 ; j<norm.length;j++){
	    	if(norm[j] == 25){
	    		reportedit = true;
	    	}
	    	if(norm[j] == 26){
	    		reportWatch = true;
	    	}
	    }
	    //判断能否发送请求，有编辑没有查看是不能发送
	    if(!(reportedit == true&&reportWatch == false)){
			$.ajax({
				type:"post",
				url: contextPath + "/role/updateAccess",
				data:{ids:s,roleid:$("#hidenroleid").val(),timesamp:new Date().getTime()},
				success:function(data){
					if(data.msg == 1){
						$.messager.alert("提示","权限分配成功！","success",function(){	
							$('#treedlg').dialog('close');
							top.parent.location.href =  top.parent.location.href;
						});
					}else{
						$.messager.alert("提示","权限分配失败！","error");
					}
					access = 0;
				}
			});
		}else{
			$.messager.alert("提示","报表分析页面有编辑权限就必须有查看权限！","error");
			access = 0;
		}
	}
});
</script>