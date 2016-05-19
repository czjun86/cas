$(function() {
	$.parser.parse('#cc');
	$.parser.parse('#ii');
	$.parser.parse('#gg');
	$.parser.parse('#nettype');
	$.parser.parse('#datatype');
	$.parser.parse('#jj');
	$.parser.parse('#kk');
	initeScence();
	initeTestType();
	initeTestnets();
	
	 var optInit = {callback: 
	    	function(page_index,jq){
	        $.ajax({
	        	type:"post",
	    		url: contextPath + "/independent/independentlist/template",
	    		data:({sernos : $("#hiddenval").val(),
	    			userid:userid,
	    			startTime:startTime,
	    			endTime:endTime,
	    			pageIndex: page_index+1,
	    			testphone:testphone,
	    			inside:inside,
	    		    senceids:senceids,
	    		    testtype:testtype,
	    		    datatype:datatype,
	    		    testnet:testnet,
	    			areaids:$("#areaids").val(),
	    			breakFlag:$("#breakFlag").val(),
	    			testAddr:$("#testAddr").val(),
	    			verify:verify,
	    			s_id:s_id}),
	    		success:function(data){
	    			$('#content').html(data);
	    			$(".compare_test").click(function(){
	    			   var snoid = $(this).attr("id");
	    			   var areaid = $(this).attr("areaid");
	    			   var s_id = $(this).attr("s_id");
	    			   var src=contextPath+"/reportIndependent/findFlowid?serialno="+snoid+"&s_id="+s_id+"&areaid="+areaid;
	    				$("#win").dialog({
	    					href:src,
	    					height: 400,width: 480,title: "测试流水选择",
	    					modal: true,closed:false
	    				});
	    				$('#win').dialog('open');
	    				$.parser.parse('#win');

	    				$(".comparetree").click(function(){
	    					var nodes = $('#win').tree('getChecked');
	    					var s = '',str='',s_na='';
	    					for(var i=0; i<nodes.length; i++){
	    						if (s != '') s += ',';
	    						s += nodes[i].id;
	    						if (s_na != '') s_na += ',';
	    						s_na+=nodes[i].text;
	    					}
//	    					var idstr = s.split(",");
//	    					var arr = [];
//	    					for ( var i = 0; i < idstr.length; i++) {
//		    						if (idstr[i].length > 1) {
//		    							if(idstr[i]!="1" && idstr[i] != "2"){
//		    									arr.push(idstr[i]);
//		    						}
//		    					}
//		    				}
//	    					for ( var i = 0; i < arr.length; i++) {
//	    						str += arr[i]+ ',';
//	    					}
	    					$('#win').dialog('open');
	    					window.location.href=contextPath+"/reportIndependent/reportCompare?flowid="+s+"&flowname="+s_na+"&serialno="+snoid+"&s_id="+s_id+"&areaid="+areaid;
	    				});
	    			});
	    		}
	        });
	        return false;
	    }
	};
	$("#pager").pagination(pageTotal, optInit);
	
	$("#areas").combobox({
		onShowPanel:function(){
			$("#areas").combobox("hidePanel");
			var src = contextPath + '/independent/arealist?areaids='+$("#areaids").val()+"&type=0";
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
	var sno = $.trim($("#serialno").val());
	 $("#hiddenval").val(sno);
	 $("#serialno").val(sno);
	//isDeal = $("#cc").combobox('getValue');
	//testphone = $.trim($("#testphone").val());
	//$("#testphone").val(testphone);
	areaids = $("#areaids").val();
	startTime = $("#startTime").val();
	endTime = $("#endTime").val();
    //inside = $("#ii").combobox('getValue');
	//senceids = $("#senceids").val();
	//testtype = $("#testtype").val();
	//nettype=$("#nettype").combobox('getValue');
	datatype=$("#datatype").combobox('getValue');
	//jobtype=$("#jj").combobox('getValue');
	//testnet = $("#testnet").val();
	verify = $("#verify").combobox('getValue');
	if (startTime != "" && startTime != null && endTime != "" && endTime != null) {
	var arr1 = startTime.split("/");  
	var startDate = new Date(arr1[0],parseInt(arr1[1])-1,arr1[2]);  
	  
	var arr2 = endTime.split("/");  
	var endDate = new Date(arr2[0],parseInt(arr2[1])-1,arr2[2]);  
		if(startDate > endDate){
			$.messager.alert("提示","开始时间应小于结束时间！","warning");
			return false;
		}  
	}else{
		$.messager.alert("提示","时间不能为空！","warning");
		return false;
	}

	$("#searchForm").submit();	
}


