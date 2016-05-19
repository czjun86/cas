
//区域查询 
function initeArea(){
	$.parser.parse('#ii');
	$.parser.parse('#gg');
	$.parser.parse('#nettype');
	$.parser.parse('#datatype');
	$.parser.parse('#jj');
	$.parser.parse('#kk');
	$("#areas").combobox({
		onShowPanel:function(){
			$("#areas").combobox("hidePanel");
			var ids = $("#areaids").val();
			openAreaDialog(0,ids);
		}
	});
}
function openAreaDialog(type,ids,map){
	var src = contextPath + '/workorder/arealist?areaids='+ids+'&type='+type;
	$("#areadlg").dialog({
		href:src,
		height: 400,width: 380,title: "选择区域",
		modal: true,closed:false
	});
	$('#areadlg').dialog('open');
	$.parser.parse('#areadlg');
	$("#areadlg");
	$(".sel_area").unbind('click').click(function(){
		var nodes = $('#areadlg').tree('getChecked');
		var strtext = "";
		var	strids = "";
		var len = nodes.length;
		if(type == 0){
			if(len > 0){
				for(var i = 0; i < len; i++){
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
		}else{
			if(len > 0){
				if(len <= 3){
					for(var i = 0; i < len; i++){
						strids += nodes[i].id + ",";
					}
					if(strids !=null && strids != ""){
						removeStationMap();
						changeCells = [];
						stationInitData = [];
						span_map_psc=[];
						p_map_jizhan=[];
						cacheCells = [];
						click_cell = "";
						strids = strids.substring(0,strids.length-1);
						$("#stationareaids").val(strids);
						var latLngBounds = map.getBounds();
						var latLngNE = latLngBounds.getNorthEast();
						var latLngSW = latLngBounds.getSouthWest();
						$("#background").show();
				    	$("#bar_id").show();
						$.ajax({
							type:'get',
							url:contextPath + '/gisgrad/test',
							data:{areas:strids},
							success:function(data){
								if(data)
								is_showcell	=true;							
								
								
								var positionList = data.position;
								_map.positionList = positionList;
								if(positionList&&!list_marker){		
									var item = positionList[0];
									map.setZoom(16);
									map.setCenter(new google.maps.LatLng(item.longitude,item.latitude));
								}
								//从查询出来的数据筛选出当前屏幕的数据
//								if(!positionList && !list_marker){
//									map.setZoom(16);
//									if(inMapData.length>0)
//										map.setCenter(new google.maps.LatLng(inMapData[0].bsList[0].celllat,inMapData[0].bsList[0].celllng));
//								}
								//清空小区信息
								if(_infowindow)
									_infowindow.close();
								//画小区
								if(map.getZoom()>=DEFAULTMAPZOOM){
									stationInitData = data.data;
									var inMapData = getBoundsStation(map);	
									for (var  j = 0; j < inMapData.length; j++) {
										drawStation(inMapData[j].bsList, map);
									}
								}
								$("#background").hide();
						    	$("#bar_id").hide();
							}
						});
					}
				}else{
					$.messager.alert("提示","加载基站信息,不能超过三个区域！","warning");
				}
			}else{
				//清空小区信息
				removeStationMap();
				changeCells = [];
				stationInitData = [];
				span_map_psc=[];
				p_map_jizhan=[];
				cacheCells = [];
				click_cell = "";
				$("#stationareaids").val('-1');
			}
		}
		$('#areadlg').dialog('close');
		
	});
	
}
//查询场景
function initeScence(){
	$("#sencs").combobox({
		onShowPanel:function(){
			$("#sencs").combobox("hidePanel");
			$.parser.parse('#ii');
			var src = contextPath + '/gisgrad/sencelist?senceids='+$("#senceids").val()+"&inside="+$("#ii").combobox('getValue');
			$("#sencedlg").dialog({
				href:src,
				height: 400,width: 380,title: "选择场景",
				modal: true,closed:false
			});
			$('#sencedlg').dialog('open');
			$.parser.parse('#sencedlg');
			$(".sel_scene").unbind('click').click(function(){
				var nodes = $('#sencedlg').tree('getChecked');
				var strtext = "";
				var	strids = "";
				if(nodes.length > 0){
					for(var i = 0; i < nodes.length; i++){
						strids += nodes[i].id + ",";
						strtext += nodes[i].text + ",";
					}
					if(strids !=null && strids != ""){
						strids = strids.substring(0,strids.length-1);
						$("#senceids").val(strids);
					}
					if(strtext !=null && strtext != ""){
						strtext = strtext.substring(0,strtext.length-1);
						$("#sencs").combobox("setText",strtext);
						$("#senctext").val(strtext);
					}
					$('#sencedlg').dialog('close');
				}else{
					$.messager.alert("提示","请选择场景！","warning");
				}
				
				
			});
		}
	});
}
//业务类型
function initeTestType(){
	$("#testtypes").combobox({
		onShowPanel:function(){
			$("#testtypes").combobox("hidePanel");
			$.parser.parse('#nettype');
			var src = contextPath + '/workorder/testtypelist?testtype='+$("#testtype").val()+'&nettype='+$("#nettype").combobox('getValue');
			$("#testtypedlg").dialog({
				href:src,
				height: 400,width: 380,title: "业务类型",
				modal: true,closed:false
			});
			$('#testtypedlg').dialog('open');
			
			$.parser.parse('#testtypedlg');
			$(".sel_testtype").unbind('click').click(function(){
				var nodes = $('#testtypedlg').tree('getChecked');
				var strtext = "";
				var	strids = "";
				if(nodes.length > 0){
					for(var i = 0; i < nodes.length; i++){
						strids += nodes[i].id + ",";
						strtext += nodes[i].text + ",";
					}
					if(strids !=null && strids != ""){
						strids = strids.substring(0,strids.length-1);
						$("#testtype").val(strids);
					}
					if(strtext !=null && strtext != ""){
						strtext = strtext.substring(0,strtext.length-1);
						$("#testtypes").combobox("setText",strtext);
						$("#testtypeText").val(strtext);
					}
					$('#testtypedlg').dialog('close');
				}else{
					$.messager.alert("提示","请选择业务类型！","warning");
				}
				
				
			});
		}
	});
}



//测试网络
function initeTestnets(){
	$("#testnets").combobox({
		onShowPanel:function(){
			$("#testnets").combobox("hidePanel");
			$.parser.parse('#nettype');
			var src = contextPath + '/workorder/testNetlist?nettype='+$("#nettype").combobox('getValue')+'&testnet='+$("#testnet").val();
			$("#testnetdlg").dialog({
				href:src,
				height: 400,width: 380,title: "选择网络",
				modal: true,closed:false
			});
			$('#testnetdlg').dialog('open');
			$.parser.parse('#testnetdlg');
			$(".sel_testnet").unbind('click').click(function(){
				var nodes = $('#testnetdlg').tree('getChecked');
				var strtext = "";
				var	strids = "";
				if(nodes.length > 0){
					for(var i = 0; i < nodes.length; i++){
						strids += nodes[i].id + ",";
						strtext += nodes[i].text + ",";
					}
					if(strids !=null && strids != ""){
						strids = strids.substring(0,strids.length-1);
						$("#testnet").val(strids);
					}
					if(strtext !=null && strtext != ""){
						strtext = strtext.substring(0,strtext.length-1);
						$("#testnets").combobox("setText",strtext);
						$("#testnetName").val(strtext);
					}
					$('#testnetdlg').dialog('close');
				}else{
					$.messager.alert("提示","请选择网络！","warning");
				}
				
				
			});
		}
	});
}
//投诉网络
function initeWorkerOrdernets(){
	$("#workerOrderNets").combobox({
		onShowPanel:function(){
			$("#workerOrderNets").combobox("hidePanel");
			var src = contextPath + '/workorder/workerOrderNetList?netIds='+$("#workerOrderNet").val();
			$("#workerOrderNetdlg").dialog({
				href:src,
				height: 400,width: 380,title: "选择投诉网络",
				modal: true,closed:false
			});
			$('#workerOrderNetdlg').dialog('open');
			$.parser.parse('#workerOrderNetdlg');
			$(".sel_workerOrderNet").unbind('click').click(function(){
				var nodes = $('#workerOrderNetdlg').tree('getChecked');
				var strtext = "";
				var	strids = "";
				if(nodes.length > 0){
					for(var i = 0; i < nodes.length; i++){
						if(nodes[i].id=="-1"){
							strids = nodes[i].id + ",";
							strtext = nodes[i].text + ",";
							break;
						}
						strids += nodes[i].id + ",";
						strtext += nodes[i].text + ",";
					}
					if(strids !=null && strids != ""){
						strids = strids.substring(0,strids.length-1);
						$("#workerOrderNet").val(strids);
					}
					if(strtext !=null && strtext != ""){
						strtext = strtext.substring(0,strtext.length-1);
						$("#workerOrderNets").combobox("setText",strtext);
						$("#workerOrderNetName").val(strtext);
					}
					$('#workerOrderNetdlg').dialog('close');
				}else{
					$.messager.alert("提示","请选择投诉网络！","warning");
				}
			});
		}
	});
}
function initeFlash(){
	if($("#flash").css("display")=="none"){
		//var width=$("#flash").css("width");
	    //var height=	$("#flash").css("height");
	    var height= flashHeight.substring(0,flashHeight.length-2);
	    //width=width.substring(0,width.length-2);
	    width=(document.body.scrollWidth-document.body.scrollWidth*0.05)/2*1.05;
	    height=height*1.1;
		$("#flash").window({
			height: height,width: width,title: "柱状图",minimizable:false,maximizable:false,
			modal: false,collapsed:false
		});
	}
	$('#flash').window('open');
	$("#flash").show();
}
/***
 * 图例事件样式修改
 * @param flay
 * @returns
 */
function getDemo(list,flay,count,colorlist,grad,kpi){
	if(flay==false&&list.length>0){
		$(".case_tu").css("background","none repeat scroll 0 0 #fff");
		$(".case_tu").css("border-bottom","1px solid #979797");
		$(".case_tu").css("border-right","1px solid #979797");
		$(".gis_nav span").css({'background-image':'url(../images/gis_images.png)'});
		$("#img_demo span").removeAttr("class").addClass("gis_chart_sq");
		getKpiPocente(list,count,colorlist,grad,kpi);
		flay=true;
	}else{
		$(".case_tu").css("background","");
		$(".case_tu").css("border-bottom", "");
		$(".case_tu").css("border-right", "");
		$("#demo_div").html("");
		$(".gis_nav span").css({'background-image':'url(../images/gis_images.png)'});
		$("#img_demo span").removeAttr("class").addClass("gis_chart");
		flay=false;
	}
	return flay;
}

/**
 * 图例百分比
 * @param list
 * @param count
 * @param colorlist
 * @param grad
 * @param kpi
 */
function getKpiPocente(list,count,colorlist,grad,kpi){
	var kpiName=$("#kk").combobox('getText');
	 var sum=0;
	 for(var t=0;t<count.length;t++){
		 sum+=count[t];
	 }
	$("#demo_div").html('');
	if(list.length>0){
		var demo_div=[];
		   demo_div.push('<div class="case_tu_one">');
		   demo_div.push('<span style="padding-left:2px;margin-left:20px;position:relative;float:left;top:-20px">'+kpiName+'</span>');
		   demo_div.push('<div class="clear"></div>');
		   demo_div.push('<ul>');
		var list=getCount(count,kpi,grad,colorlist);
		for(var t=0;t<list.length;t++){
			var ca=list[t];
			 demo_div.push('<li><span><font style="background:'+ca.color+'; font-size: 12px; display: block;');
			 demo_div.push('text-align: center; height: 22px; width: 22px; ">'+ca.xx+'</font></span>');
			 demo_div.push('<pre class="case_tu_one_yyy">'+ca.yy+'%</li></pre>');
		}
		 demo_div.push('</ul>');
		 demo_div.push('</div>');
		$("#demo_div").html($(demo_div.join('\r\n')));
	}else{
		$("#demo_div").html('');
	}
}