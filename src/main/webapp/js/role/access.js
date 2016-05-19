var setting = {
	check: {
		enable: true //是否为复选框模式
},
data: {
	simpleData: {
		enable: true //是否显示父子关联模式
		}
	},
    callback:{
        onCheck:onCheck
    }
};
/*
数据参数说明：
checked:节点输入框勾选状态，true和false;
children:子节点数据保存模式;
chkDisabled:是否禁用选择框，true和flase;
open:初始化数据时，设定节点是否展开，true和flase;
target:设定点击节点后再何处打开页面;
url:设定节点需要访问的页面（也可设定click属性来进行跳转回调控制）;
name:设定节点展示名称，如：name:投诉管理;
icon:设定父节点展开和折叠时使用相同图标（子节点也使用此属性设置）;
iconOpen:设定父节点展开时使用的图标;
iconClose:设定父节点折叠时使用的图标;

*/
var zNodes =new Array();
$(document).ready(function(){
	$.ajax({
		type:"post",
		url: contextPath + "/role/assign",
		datatype: 'json',
		data:{roleid:roleid},
		success:function(data){
	        $.fn.zTree.init($("#treeDemo"), setting, data.accesses);
		}
	});
	$("#sure").click(function(){
		var treeObj=$.fn.zTree.getZTreeObj("treeDemo"), //初始化zTree方法
		nodes=treeObj.getCheckedNodes(true), //获取选中的节点集合，true为选中，false为未选中
		v="";
		for(var i=0;i<nodes.length;i++){
			v+=nodes[i].id + ",";
		}
		window.parent.document.getElementById("msg").value = v;
    });
});

function onCheck(e,treeId,treeNode){
}