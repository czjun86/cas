$("#save").click(function(){
	var type = $("input:radio:checked").val();
	$.ajax({
		type: "POST",
	  	url: contextPath + "/map/update",
	  	data:{'mapType':type},
	    success: function(data) {
	      if(data.msg == 1){
	    	  $.messager.alert("提示","操作成功！","success",function(){	
		    	  window.location.href =  window.location.href;
	    	  });
	      }else{
	    	  $.messager.alert("提示","操作失败！","warning");
	      }
		}
	});
});