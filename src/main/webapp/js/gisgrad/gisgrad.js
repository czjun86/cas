/**
 * 地图评价相关JS
 */
var flashHeight;
var DEFAULTMAPZOOM = 14; // default map zoom
var geocoder = null; // address parse
var searnum = 0;
$(function() {
	$("#wlf_query").hide();
	$("#background").hide();
	$("#bar_id").hide();
	 var len =document.body.scrollWidth*0.5;
	$("#div_map").css("height",len);
	initeArea();	
	initeScence();
	initeTestType();
	initeTestnets();
	map=initeMap();
	window._map = map;
	var markerlist=[];
	
	$("#show_flash").live('click',function(){
		$("#hiquery").show();
		$("#diatype").val("hi");
		//隐藏柱状图
		try{
			$('#flash').window('close');
		}catch(e){ 
			
		}
		//打开查询条件
		$("#cond").dialog({
			href: contextPath + '/gisgrad/graphlist?inside='+grap_.inside+'&grad='+grap_.grad+'&startTime='+grap_.startTime+
			   '&endTime='+grap_.endTime+'&areaids='+grap_.areaids+'&areatext='+encodeURIComponent(grap_.areatext)+'&senceids='+grap_.senceids+
			   '&senctext='+encodeURIComponent(grap_.senctext)+'&testtype='+grap_.testtype+'&testtypeText='+encodeURIComponent(grap_.testtypeText)+'&nettype='+grap_.nettype+
			   '&datatype='+grap_.datatype+'&jobtype='+grap_.jobtype+'&kpi='+grap_.kpi+'&sernos='+grap_.sernos+'&testnet='+grap_.testnet+
			   '&testnetName='+encodeURIComponent(grap_.testnetName)+'&openType='+1,
			height: 400,width: 708,title: "柱状图查询",
			modal: true,closed:false
		});
		$('#cond').dialog('open');
	});
	
	//页面第一次查询事件
	$("#serch").die().live("click",function(e) {
		if($("#sernos").validatebox("isValid")==false){
			$.messager.alert("提示","输入超出长度！","warning");
			return false;
		}
		for(var j=0;j<markerlist.length;j++){
			var marker=markerlist[j];
			marker.setMap(null);
		}
		markerlist=[];
		markerlist=queryData(map,1);
	});
	$("#queryscend").die().live("click",function(e) {
		if($("#secendSerno").validatebox("isValid")==false){
			$.messager.alert("提示","输入超出长度！","warning");
			return false;
		}
		//页面上有数据时才能进行二次查询
		if(markerlist.length>0&&$("#secendSerno").val().length>0){
			for(var j=0;j<markerlist.length;j++){
				var marker=markerlist[j];
				marker.setMap(null);
			}
			markerlist=[];
			markerlist=queryData(map,2);
		}
	});
	
	//二次查询菜单
	var ff=false;
	$("#samp_id").die().live("click",function(e) {
		if(ff==false){
			//$("#samp_id").css({'background-image':'url(../images/map_deploy.png)'});
			$(".map_two_search").toggle();
			$(".map_two").css('width','48px');
			$(".gis-mapgj ").css("top",'60px');
			ff=true;
		}else{
			//$("#samp_id").css({'background-image':'url(../images/map_stop.png)'});
			$(".map_two_search").toggle();
			$(".map_two").css('width','434px');
			$(".gis-mapgj ").css("top",'20px');
			ff=false;
		}

	});
	//全屏事件
	$("#quan_ping").die().live("click",function(e) {
		$(".mainmenu").toggle();
		if($(".right").css("margin-top")=='100px'){
			$(".right").css("margin-top","0px").css("padding-top","0px");
			$("#quan_ping").html('<span class="gis_screen"></span>退出全屏');
			}
		else {
			$(".right").css("margin-top","100px").css("padding-top","0px");
			$("#quan_ping").html('<span class="gis_screen"></span>全屏');
		}
	});
	//查询条件事件
	$("#query_id").die().live("click",function(e) {
		$("#wlf_query").toggle();
	});
	//小区信息事件
	$("#c_info_li").live('mouseover',function(e){
		$("#gis_cell_loading").show();
	});
	$("#c_info_li").live('mouseout',function(e){
		$("#gis_cell_loading").hide();
	});
	//标注信息事件
	$(".show_li").live('mouseover',function(e){
		$(this).find("div").first().show();
	});
	$(".show_li").live('mouseout',function(e){
		$(this).find("div").first().hide();
	});
	
	//领区事件
	$("#showlinqu").live('mouseover',function(e){
		$("#linqu_ul").show();
	});
	$("#showlinqu").live('mouseout',function(e){
		$("#linqu_ul").hide();
	});

	//导出事件
	$("#exports").live('mouseover',function(e){
		if(stationInitData.length == 0){
			$("#export_near").css('color','#CDCDCD');
		}
		$("#gis_exports").show();
	});
	$("#exports").live('mouseout',function(e){
		$("#gis_exports").hide();
	});
	 //数据导出点击事件
	   $("#export_data").die().live("click",function(e) {
		   if(searnum==1){
				var obj=getPram(1);
			   download_(obj);
		   }else if(searnum==0){
			   $("#hiquery").show();
			   $("#diatype").val("dl");
			 //打开查询条件
			$("#cond").dialog({
				href: contextPath + '/gisgrad/graphlist?inside='+grap_s.inside+'&grad='+grap_s.grad+'&startTime='+grap_s.startTime+
				   '&endTime='+grap_s.endTime+'&areaids='+grap_s.areaids+'&areatext='+encodeURIComponent(grap_s.areatext)+'&senceids='+grap_s.senceids+
				   '&senctext='+encodeURIComponent(grap_s.senctext)+'&testtype='+grap_s.testtype+'&testtypeText='+encodeURIComponent(grap_s.testtypeText)+'&nettype='+grap_s.nettype+
				   '&datatype='+grap_s.datatype+'&jobtype='+grap_s.jobtype+'&kpi='+grap_s.kpi+'&sernos='+grap_s.sernos+'&testnet='+grap_s.testnet+
				   '&testnetName='+encodeURIComponent(grap_s.testnetName)+'&openType='+2,
				height: 400,width: 705,title: "评价导出",
				modal: true,closed:false
			});
			$('#cond').dialog('open');
		   }
	   });
	 //点击小区导出
		$("#export_cell").die().live('click',function(e){
			var ids = '';
			var nettype = '0,1';
			var indoor = '0,1';
			var cellBands = '900,1800';
			$("#celldlg").dialog({
				href: contextPath + '/gisgrad/searchCell?type=1&areas=' + ids +'&indoor=' + indoor + '&modetypes=' + nettype + '&cellBands=' + cellBands,
				height: 400,width: 705,title: "小区导出",
				modal: true,closed:false
			});
			$('#celldlg').dialog('open');
			$.parser.parse('#celldlg');
			$(".sel_cell > span > span").html("导出");
		});
	   /**
	    * 标注与领区选择事件
	    */
	   $(".isgou").die().live("click",function(e) {
		   if($(this).find("span").length>0){
			   $(this).find("span").remove();
		   }else{
			   $(this).prepend("<span></span>");
		   }
		 if($(this).attr("name")=="psc"&&span_map_psc.length>0)
			 isShowPsc();
		 if($(this).attr("name")=="g3_name"&&p_map_jizhan.length>0)
			 isShowJizhan();
		 if($(this).attr("name")=="bcch"&&span_map_psc.length>0)
			 isShowPsc();
		 if($(this).attr("name")=="g2_name"&&p_map_jizhan.length>0)
			 isShowJizhan();
	   });
	   
	   
	   $("#cell_gsm").die().live("click",function(){
		   if($(this).is(':checked')){
			   $("#cell_bands").find("input").each(function(i){
				   $(this).attr('checked','checked');
			   });
			   $("#cell_bands").show();
		   }else{
			   $("#cell_bands").find("input").each(function(i){
				   $(this).removeAttr('checked');
			   });
			   $("#cell_bands").hide();
		   }
	   });
	   //点击邻区导出
	   $("#export_near").die().live("click",function(){
		   if(stationInitData.length > 0){
			   $(".gis-exporta").each(function(){
				   $(this).show();
			   });
				$('#nearcelldlg').dialog('open');
				$.parser.parse('#nearcelldlg');
				$("#near_type").find("input").each(function(){
					$(this).attr('checked','checked');
				});
				$("#report_cell_type").combobox({
					valueField: 'val',
					textField: 'text',
					data: [{
					        "val":3,
					        "text":"加载小区",
					        "selected":true
					        },{
					        "val":2,
					        "text":"点击小区"
					        }]
				});
			   
		   }
	   });
	   $(".sel_nearcell").die().live("click",function(){
		   var cellrel = "";
		   $("#near_type").find("input:checked:checked").each(function(){
			   cellrel += $(this).attr("value") +",";
			});
		   if(cellrel != "" && cellrel.length > 0){
			   cellrel = cellrel.substr(0 , cellrel.length - 1);
			   var lac_cid = clicktrunk.substr(clicktrunk.indexOf("_")+1,clicktrunk.length);
			   var report_cell_type = $("#report_cell_type").combobox("getValue");
			   if((report_cell_type == 2 && clicktrunk != "") || report_cell_type == 3){
				   var ids = $("#stationareaids").val();
				   var nettype = $("#cellnettype").val();
				   var indoor = $("#cellindoor").val();
				   var cellBands = $("#cellcellbands").val();
				   location.href = contextPath+'/gisgrad/downloadcellinfo?report_type='+report_cell_type+'&lac_cid='+lac_cid+'&cellrel='+cellrel+'&areas=' + ids +'&indoor=' + indoor + '&modetypes=' + nettype + '&cellBands=' + cellBands;
			   }else{
				   $.messager.alert("提示","请点击小区后再选择点击小区导出！","warning");
				   return; 
			   }
		   }else{
			   $.messager.alert("提示","请选择邻区关系！","warning");
			   return; 
		   }
		   $('#nearcelldlg').dialog('close');	
	   });
	 //小区加载
		$("#station").die().live('click',function(e){
			var ids = $("#stationareaids").val();
			var nettype = $("#cellnettype").val();
			var indoor = $("#cellindoor").val();
			var cellBands = $("#cellcellbands").val();
			$("#celldlg").dialog({
				href: contextPath + '/gisgrad/searchCell?type=0&areas=' + ids +'&indoor=' + indoor + '&modetypes=' + nettype + '&cellBands=' + cellBands,
				height: 380,width: 705,title: "小区加载条件",
				modal: true,closed:false
			});
			$('#celldlg').dialog('open');
			$.parser.parse('#celldlg');
		});
	   //查询小区 /小区导出
	   $(".sel_cell").die().live("click",function(e){
		   var type = $("#cell_btn_type").val();
		   var indoor = "";
		   var nettype = "";
		   var cellBands = "";
		   var areaids = "";
		   //室内/外
		   $("#cell_indoor").find("input:checkbox:checked").each(function(i){
			   indoor += $(this).attr('value') + ',';
		   });
		   if(indoor != "" && indoor.length > 0){
			   indoor = indoor.substr(0 , indoor.length - 1);
		   }else{
			   $.messager.alert("提示","请选择室内/外！","warning");
			   return; 
		   }
		   //网路类型
		   $("#cell_nettype").find("input:checkbox:checked").each(function(i){
			   nettype += $(this).attr('value') + ',';
		   });
		   if(nettype != "" && nettype.length > 0){
			   nettype = nettype.substr(0 , nettype.length - 1);
		   }else{
			   $.messager.alert("提示","请选择网路类型！","warning");
			   return; 
		   }
		   //频段
		   $("#cell_bands").find("input:checkbox:checked").each(function(i){
			   cellBands += $(this).attr('value') + ',';
		   });
		   if(cellBands != "" && cellBands.length > 0){
			   cellBands = cellBands.substr(0 , cellBands.length - 1);
		   }
		   //区域
		   $("#cell_areas").find("input:checkbox:checked").each(function(i){
			   areaids += $(this).attr('value') + ',';
		   });
		   if(areaids != "" && areaids.length > 0){
			   areaids = areaids.substr(0 , areaids.length - 1);
		   }else{
			   $.messager.alert("提示","请选择区域！！","warning");
				return;
		   }
		   //0 加载小区 1小区导出 
		   if(type == 0){
			   if($("#cell_areas").find("input:checkbox:checked").length > 3){
				   $.messager.alert("提示","加载小区信息,不能超过三个区域！","warning");
				   return; 
			   }
			   if(areaids != "" && areaids.length > 0){
				   	removeStationMap();
					changeCells = [];
					stationInitData = [];
					span_map_psc=[];
					p_map_jizhan=[];
					cacheCells = [];
					click_cell = "";
					clicktrunk = "";
				   $("#stationareaids").val(areaids);
				   $("#cellnettype").val(nettype);
				   $("#cellindoor").val(indoor);
				   $("#cellcellbands").val(cellBands);
			   }
			   $("#background").show();
		    	$("#bar_id").show();
			   $.ajax({
				   type : 'get',
					url : contextPath + '/gisgrad/showCell',
					data : {
						areas : areaids,
						indoor : indoor,
						modetypes : nettype,
						cellBands : cellBands
					},
					success : function(data) {
						if(data)
							is_showcell	=true;							
							
							var positionList = data.position;
							_map.positionList = positionList;
							if(positionList&&!list_marker){		
								var item = positionList[0];
								map.setZoom(16);
								map.setCenter(new google.maps.LatLng(item.longitude,item.latitude));
							}
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
							$("#export_near").css('color','');
							$("#background").hide();
					    	$("#bar_id").hide();
					}
			   });
		   }else{
			   var areaids = "";
			   if($("#cell_area_all").is(":checked")){
				   areaids = "-1,";
			   }else{
				   $("#cell_areas").find("input:checkbox:checked").each(function(i){
					   areaids += $(this).attr('value') + ',';
				   });
			   }
			   if(areaids != "" && areaids.length > 0){
				   areaids = areaids.substr(0 , areaids.length - 1);
				   location.href = contextPath+'/gisgrad/downloadcellinfo?report_type=1&areas=' + areaids +'&indoor=' + indoor + '&modetypes=' + nettype + '&cellBands=' + cellBands;
			   }else{
				   $.messager.alert("提示","请选择小区！","warning");
				   return; 
			   }
		   }
		   $('#celldlg').dialog('close');	
	   });
	   $("#cell_area_all").die().live("click",function(){
		   if($(this).is(":checked")){
			   $("#cell_areas").find("input:checkbox").each(function(i){
				   $(this).attr("checked","checked");
			   });
		   }else{
			   $("#cell_areas").find("input:checkbox").each(function(i){
				   $(this).removeAttr('checked');
			   });
		   }
		   
	   });
});
var queryContition;//页面查询条件
var is_showcell=false;//默认小区未加载
function getPram(isfirst){
	if(isfirst==1){
		   var obj=new Object();
		   obj.inside = $("#ii").combobox('getValue');
		   obj.grad = $("#gg").combobox('getValue');
		   obj.startTime = $("#startTime").val();
		   obj.endTime = $("#endTime").val();
		   obj.areaids = $("#areaids").val();
		   obj.senceids = $("#senceids").val();
		   obj.testtype = $("#testtype").val();
		   obj.scendvalue=$("#secendSerno").val();
		   obj.nettype=$("#nettype").combobox('getValue');
		   obj.datatype=$("#datatype").combobox('getValue');
		   obj.jobtype=$("#jj").combobox('getValue');
		   obj.kpi=$("#kk").combobox('getValue');
		   obj.sernos=$("#sernos").val();
		   obj.testnet = $("#testnet").val();
			if (obj.startTime != "" && obj.startTime != null && obj.endTime != "" && obj.endTime != null) {
			var arr1 = obj.startTime.split("/");  
			var startDate = new Date(arr1[0],parseInt(arr1[1])-1,arr1[2]);  
			var arr2 = obj.endTime.split("/");  
			var endDate = new Date(arr2[0],parseInt(arr2[1])-1,arr2[2]);  
				if(startDate > endDate){
					$.messager.alert("提示","开始时间应小于结束时间！","warning");
					return false;
				}  
			}else{
				$.messager.alert("提示","时间不能为空！","warning");
				return false;
			}
			queryContition=obj;	
	}else if(isfirst==3){//没查询过，点击柱状图和评价导出获取条件
		var obj=new Object();
		   obj.inside = $("#ii_").combobox('getValue');
		   obj.grad = $("#gg_").combobox('getValue');
		   obj.startTime = $("#startTime_").val();
		   obj.endTime = $("#endTime_").val();
		   obj.areaids = $("#areaids_").val();
		   obj.senceids = $("#senceids_").val();
		   obj.testtype = $("#testtype_").val();
		   obj.scendvalue="";
		   obj.nettype=$("#nettype_").combobox('getValue');
		   obj.datatype=$("#datatype_").combobox('getValue');
		   obj.jobtype=$("#jj_").combobox('getValue');
		   obj.kpi=$("#kk_").combobox('getValue');
		   obj.sernos=$("#sernos_").val();
		   obj.testnet = $("#testnet_").val();
			if (obj.startTime != "" && obj.startTime != null && obj.endTime != "" && obj.endTime != null) {
			var arr1 = obj.startTime.split("/");  
			var startDate = new Date(arr1[0],parseInt(arr1[1])-1,arr1[2]);  
			var arr2 = obj.endTime.split("/");  
			var endDate = new Date(arr2[0],parseInt(arr2[1])-1,arr2[2]);  
				if(startDate > endDate){
					$.messager.alert("提示","开始时间应小于结束时间！","warning");
					return false;
				}  
			}else{
				$.messager.alert("提示","时间不能为空！","warning");
				return false;
			}
			queryContition=obj;	
	}else{
		queryContition.scendvalue=$("#secendSerno").val();
	}

	return queryContition;
}

//根据条件查询数据
//聚合变量
var list_marker;
var markerCluster;
function queryData(map,isfirst){
	searnum=1;//查询过一次，点评价导出，导出当前的信息
	polyline.setMap(null);
	markerLatLng_bit=null;
	if(markerCluster){
		markerCluster.clearMarkers();
	}
	var markerlist=[];
	var obj=getPram(isfirst);
	regionCondition = obj;//获取框选条件
	$("#background").show();
	$("#bar_id").show();
	$.post(contextPath + "/gisgrad/gisgrad",
			{areaids:obj.areaids,
		    inside:obj.inside,
		    grad:obj.grad,
		    isFirst:isfirst,
		    secendSerno:obj.scendvalue,
		    startTime:obj.startTime,
		    endTime:obj.endTime,
		    sernos:obj.sernos,
		    senceids:obj.senceids,
		    testtype:obj.testtype,
		    nettype:obj.nettype,
		    datatype:obj.datatype,
		    jobtype:obj.jobtype,
		    kpi:obj.kpi,
		    testnet:obj.testnet,
		    colorversion:colorversion
			},
			function(data){
				//若图例开启，隐藏图例
				if(data.list.length<=0){
					$(".case_tu").css("background","");
					$(".case_tu").css("border-bottom", "");
					$(".case_tu").css("border-right", "");
					$("#demo_div").html("");
					$(".gis_nav span").css({'background-image':'url(../images/gis_images.png)'});
					$("#img_demo span").removeAttr("class").addClass("gis_chart");
					//隐藏柱状图
					try{
						$('#flash').window('close');
					}catch(e){ 
						
					}
				}
				list_marker=data.list;
				$("#caiyang").html("采样点："+list_marker.length);
				var colorlist=data.color;
				var count=data.count;
				var center=data.center;
	        	var flay=false;
	        	isColorChange = data.ischange;
	        	//图例点击事件
        		$('#img_demo').die().live("click",function(e) {
        					flay=getDemo(list_marker,flay,count,colorlist,obj.grad,obj.kpi);
        		});
        		if($("#demo_div").html()){flay=getDemo(list_marker,flay,count,colorlist,obj.grad,obj.kpi);}
	           for(var i=0;i<list_marker.length;i++){
	        	   var li=list_marker[i];
	        	 var marker=  addMarker(li,map,colorlist,obj.nettype,i);
	        	 markerlist.push(marker);
	           }
	      	  markerCluster = new MarkerClusterer(map, markerlist,{
	      		 maxZoom: DEFAULTMAPZOOM-1,
	      		averageCenter: true
	      	});
	           if(list_marker.length>0&&count.length>0){
	        	   if(is_showcell==false){
	        		   setMapCenter(map,center);
	        	   }
	           }
	           if(list_marker.length>0&&count.length>0){
	        	   //柱状图显示事件
	        	   $("#show_flash").show();
	        	   $("#show_flash").die().live("click",function(e) {
	        		   initeFlash();
	        	   });
	        	   var wid_len=(document.body.scrollWidth-document.body.scrollWidth*0.05)/2;
	        	   $("#flash").append("<div class='histogram_flash' id='div_state'  style='width:"+wid_len+"px;margin:0 auto;position: relative;'></div>");
	        	   creatFlash('div_state',count,colorlist,obj.grad,obj.kpi);
	        	   flashHeight = $("#flash").css("height");
	           }else{
	        	   $("#flash").html("");
	        	   $("#show_flash").hide();
	           }
	       	$("#background").hide();
	    	$("#bar_id").hide();
			});
	return markerlist;
}
//初始化地图
function initeMap(){
	var myOptions_dt = {
			zoom :14,
			center :new google.maps.LatLng(29.5,106.5) ,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			mapTypeControl : true,
			streetViewControl : false,
			mapTypeControl:false,
//			mapTypeControlOptions : {
//				style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
//				position : google.maps.ControlPosition.BOTTOM
//			},
			panControl : true,
			panControlOptions : {
				position : google.maps.ControlPosition.RIGHT_BOTTOM
			},
			zoomControl : true,
			zoomControlOptions : {
				position : google.maps.ControlPosition.RIGHT_BOTTOM
			}
		};
		// 生成地图
	var	map = new google.maps.Map(document.getElementById("div_map"),
				myOptions_dt);
	
	
	geocoder = new google.maps.Geocoder(); //申明地址解析对象
	//比例改变事件
	google.maps.event.addListener(map, 'zoom_changed', function(re) {
		   //重画连线
			if(cells&&cells.length>0&&markerLatLng_bit){
				polyline.setPath(countLatLng(cells,markerLatLng_bit));
				polyline.setMap(map);
			}
	});

	google.maps.event.addListener(map, 'zoom_changed', function(re) {
		if(map.getZoom()>=DEFAULTMAPZOOM){
		//setTimeout(function(){
			changeBound(map);
			//},1000);
		}else{
			removeStationMap();
            //取消小区弹出消息
			if(_infowindow)
				_infowindow.close();
		}

	});
	google.maps.event.addListener(map, 'dragend', function(re) {
		if(map.getZoom()>=DEFAULTMAPZOOM){
				changeBound(map);
			}else{
				removeStationMap();
	            //取消小区弹出消息
				if(_infowindow)
					_infowindow.close();
			}	

	});	
	
	google.maps.event.addListener(map, 'rightclick', function(event) {
		if(map.getZoom()>=DEFAULTMAPZOOM && stationInitData && stationInitData.length>0){
			var currentLatLng = event.latLng;				
			//var newPos = new google.maps.LatLng(mk.position.lat(),mk.position.lng());
			geocoder.geocode({location:currentLatLng},function(results,status){
				if(status == google.maps.GeocoderStatus.OK){
					if(results[0]){
						var address = results[0].formatted_address;
						showRightMenu(event.pixel.x, event.pixel.y,currentLatLng,address);								
					}
				}
			});
			
		}
	});
	
	
	chooseMap(map);
	return map;
}

/**
 * setAreaCenter
 * @param _this
 */
function setAreaCenter(_this){
	var latLngList,address,lat,lng,i,ids=[];
	var latLng = $(_this).attr("latLng");
	var nodes = $('#cell_areas').find("input:checkbox:checked");
	latLngList = latLng.split(",");
	address = $(_this).attr("address");
	lat = latLngList[0];
	lng = latLngList[1];
	if(nodes && nodes.length>0){
		for(i = 0,len=nodes.length; i < len; i++){
			ids.push($(nodes[i]).val());
		}		
		$.ajax({
			type:"POST",
			url:contextPath + '/gisgrad/setAreaCenter',
			data:{areaids:ids.join(","),lat:lat,lng:lng,address:address},
			error:function(){
				$.messager.alert("提示","设置失败！","ok");
			},
			success:function(data){
				$.messager.alert("提示","设置成功！","ok");
			}
		});
	}
	
}

/**
 * 显示右键菜单
 * @param x
 * @param y
 */
function showRightMenu(x,y,latLng,address){
	var rightMenu = $("#rightMenu");
	var centerItme = $("#centerItme");
	var rightMenu_cancel = $("#rightMenu_cancel");
	if(rightMenu.find("div.area_item")){
		rightMenu.find("div.area_item").remove();
	}
	addShortcutArea(rightMenu_cancel,rightMenu);
	
	centerItme.attr("latLng",latLng.lat()+","+latLng.lng()).attr("address",address);
	rightMenu.menu('show', {
           left: x,
           top: y+rightMenu.height()
       });
	$.parser.parse(rightMenu);
}

/**
 * current area shortcut menu
 * @param rightMenu_cancel
 * @param rightMenu
 */
function addShortcutArea(rightMenu_cancel,rightMenu){
	var nodes = $('#cell_areas').find("input:checkbox:checked");
	var currentArea = rightMenu.attr("currentArea");
	var len = nodes.length;
	len = len>3 ? 3 : len;
	for(var i=0; i<len; i++){
		var node = $(nodes[i]);
		var item = rightMenu_cancel.clone(true);
		var div = item.find("div");
		item.attr("areaid",node.val());
		div.html(node.parent().text());
		item.removeAttr("id");
		item.addClass("area_item");
		if(currentArea == node.val()){			
			div
			.before("<span style='background:url(../images/gis_images.png) no-repeat;background-position:-141px -2px; width:16px; height:18px; display:block; float:left; margin:4px 6px 0 0;'></span>")
			.css({"marginLeft":"-22px"});
		}
		item.bind({
			click:function(){
				var areaid = $(this).attr("areaid");
				rightMenu.attr("currentArea",areaid);
				changeMapZoom(areaid);
			},
			mouseover:function(){
				rightMenu.find("div.menu-active").removeClass("menu-active");
				$(this).addClass("menu-active");
				
			},
			mouseout:function(){
				$(this).removeClass("menu-active");
			}
		});
		rightMenu_cancel.before(item);
	}
}

/**
 * shortcut menu item change for area 
 * @param _this
 */
function changeMapZoom(areaid){
	if(_map && _map.positionList){
		for(var i=0,len=_map.positionList.length; i<len; i++){
			var item = _map.positionList[i];
			if(areaid == item.areaid){
				//清空小区信息
				removeStationMap();
				_map.setCenter(new google.maps.LatLng(item.longitude,item.latitude));
				var inMapData = getBoundsStation(_map);		
				if(_map.getZoom()>=DEFAULTMAPZOOM){
					for (var  j = 0; j < inMapData.length; j++) {
						drawStation(inMapData[j].bsList, _map);
					}
				}
				break;
			}
		}
	}
}

/**
 * 地图放大拖动事件
 * @param map
 */
function changeBound(map){
	//获取当前屏幕最大最小经纬度
	var latLngBounds = map.getBounds();
	var latLngNE = latLngBounds.getNorthEast();
	var latLngSW = latLngBounds.getSouthWest();
	var latNE = latLngNE.lat();
	var lngNE = latLngNE.lng();
	var latSW = latLngSW.lat();
	var lngSW = latLngSW.lng();
	//循环判断在地图上的小区信息是否在当前屏幕，不在则删除
//	if(_infowindow&&_infowindow.getContent()){
//		if(_infowindow.getPosition().lat() <= latSW ||
//				_infowindow.getPosition().lng() <= lngSW ||
//				_infowindow.getPosition().lat() >= latNE ||
//				_infowindow.getPosition().lng() >= lngNE){
//			_infowindow.close();
//	  }
//	}

	for(var i = 0; i < stationMap.length; i++){
		var lat = stationMap[i].latLng_.lat(),lng = stationMap[i].latLng_.lng();
		if(lat <= latSW || lng <= lngSW || lat >= latNE || lng >= lngNE){
			if(stationMap[i].isdelet_<1){
				//删除小区信息
				stationMap[i].setMap(null);
				stationMap.splice(i, 1);
				span_map_psc.splice(i, 1);
			}
		}
	}
	
	var bslist = null;
	//在可视范围的基站数据
	var inMapData = getBoundsStation(map);
	for(var i = 0;i < inMapData.length; i++){
		bslist = inMapData[i].bsList;
		//是否需要画小区
		var flag = false;
		for(var j = 0; j < bslist.length; j++){
			var img_ ="#img_"+bslist[j].lac+"_"+bslist[j].cellId;
			var del = false;
			for(var k = 0; k < changeCells.length; k++){
				//判断小区是否在邻区数组中
				if(img_ == changeCells[k].img_id){
					//保存该小区图片前缀
					bslist[j].fix = changeCells[k].fix;
					del = true;
					break;
				}else{
					if(typeof(bslist[j].fix)!='undefined'){
						bslist[j].fix=undefined;
					}
				}
			}
			//判断当前小区是否在地图上
			if($(img_).length == 0){
				flag = true;
				del = false;
			}else{
				//删除叠加层
				if(del){
					$(img_).parent().remove();
					flag = true;
				}
			}
		}
		if(flag){
			//画小区
			drawStation(bslist,map);
		}
	}
}
//地图上增加点与点击事件
/**
 * li:对象
 */
var cells=null;//点击点连接的小区
var markerLatLng_bit=null;//点击的marker
var isColorChange = 0;//阈值颜色是否更新
var polyline=new google.maps.Polyline({
	strokeColor: "#898ce7",
    strokeOpacity: 1.0,
    strokeWeight: 2});
function addMarker(li,map,colorlist,nettype,index){
	 var position = new google.maps.LatLng(li.lat_m,li.lng_m);
	 var n="";
	 if(li.net==1){n='g';}
	 if(li.net==2){n='w';}
	 if(li.net==3){n='w';}
	 if(li.net==4){n='g';}
	 if(li.isf&&li.isf==2){n='s';}
	 if(li.color=='优')li.aa=1;
	 if(li.color=='良')li.aa=2;
	 if(li.color=='中')li.aa=3;
	 if(li.color=='差')li.aa=4;
	var infowindow = null;
	var icon = isColorChange==1 ? contextPath+'/images/integration/'+n+'_'+li.aa+'.png'+"?t="+new Date().getTime() : contextPath+'/images/integration/'+n+'_'+li.aa+'.png'+"";
	var marker=new google.maps.Marker({
		position :position,
		icon:icon,
		map : map,
		zIndex:index
	});
	//点击事件
	 google.maps.event.addListener(marker, 'click', function(){
		 if (infowindow) {
			 infowindow.close();
             infowindow = null;
		 }else{
			 var le= checkKpi(li,colorlist);
			 var content="";
			 content+='<div class="map_tankuang" id="info_'+index+'">';
			 content+='<div style="font-weight: bold; padding-left: 10px;">'+li.ser+'</div>';
			 content+='<h1>'+li.title+'（'+li.inside+'）</h1><span style="background:'+li.cc+'">'+li.realgrad+'</span>';
			 content+='<div class="clear"></div>';
			 content+='<p>测试地址：'+li.add+'<br />';
			 content+=le.kpitext;
			 content+='</p></div>';
			 infowindow = new google.maps.InfoWindow({
                 content:content,
                 disableAutoPan:false
             });
             infowindow.open(map, marker);
             infowindow.setZIndex(997);
             //气泡关闭事件
        	 google.maps.event.addListener(infowindow, 'closeclick', function(){
                 infowindow = null;
             });
        	}
		// 判断小区是否显示
		 if(is_showcell==true){
			 polyine_cell(li,map);
		 }
		 //点击连接关联小区
    	 
	 });

	return marker;
}
//点击流水连线小区，如果没有在地图区域内生成重新生成
function polyine_cell(li,map){
	 $.post(contextPath + "/gisgrad/giscell",
				{flowid:li.flowid},
				function(data){
					cells=data.cell;
					//不在区域内的小区
					var cell_qita=[];
					for(var j=0;j<cells.length;j++){
						var cc=cells[j];
						/*var obj=$("#img_"+cc.lac+"_"+cc.cellId);
						if(obj.length==0){
							cc.isclick=1;
							cc.isdelet=1;
							cell_qita.push(cc);
						}*/
						var flag = true;
						var bslist = null;
						var lac,cid;
						var sd= stationInitData;
						var bid;
						for(var i = 0;i < sd.length; i++){
							bslist = sd[i].bsList;
							bid = sd[i].bid;
							if(cc.bid===bid){
								for(var j = 0; j < bslist.length; j++){
									lac = bslist[j].lac;
									cid = bslist[j].cellId;
									if(lac == cc.lac && cid == cc.cellId){
										flag = false;
									}
								}
							}
						}
						if(flag){
							cc.isclick=1;
							cc.isdelet=1;
							cell_qita.push(cc);
						}
						
					}
					if(cell_qita.length>0){
						groupBybid(cell_qita,map);
					}
					setTimeout(function(){
						//连线
						markerLatLng_bit=new google.maps.LatLng(li.lat_m+0.000003,li.lng_m);
						polyline.setPath(countLatLng(cells,markerLatLng_bit));
						polyline.setMap(map);
						},200);
				});
}

/***
 * 检查对象各测试指标
 * @param li
 */
function checkKpi(li,colorlist){

 var kpitext="指标评价：";
 kpitext+=li.rscp==null?"":"RSCP:"+li.rscp+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.ecno==null?"":"Ec/No:"+li.ecno+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.tx==null?"":"TXPOWER:"+li.tx+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.fu==null?"":"FTP上行:"+li.fu+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.fd==null?"":"FTP下行:"+li.fd+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.rx==null?"":"Rxlev:"+li.rx+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.rq==null?"":"Rxqual:"+li.rq+"&nbsp;&nbsp;&nbsp;&nbsp";
 kpitext+=li.ci==null?"":"C/I:"+li.ci+"&nbsp;&nbsp;&nbsp;&nbsp";
 li.kpitext=kpitext;
 if(li.color=='优')li.cc=colorlist[0].rank_color;
 if(li.color=='良')li.cc=colorlist[1].rank_color;
 if(li.color=='中')li.cc=colorlist[2].rank_color;
 if(li.color=='差')li.cc=colorlist[3].rank_color;
 li.inside=li.inside=='0'?'室外':'室内';
 return li;
}
/***
 * 生成优良中差百分比图
 * @param count
 * @param colorlist颜色
 */
function creatFlash(idname,count,colorlist,grad,kpi){
	var tempData = [
    		{y: 0 },{y: 0 },{y: 0 },
    		{y: 0 },{y: 0 },{y: 0 }
	        	];
	var xlist=[];
	var options = {
			max: 100,
			chart: { renderTo:idname, backgroundColor: '#f0f0f0',plotBackgroundImage: '../images/bb.png' ,marginTop:50,marginBottom:50},
			title: { text: '等级计算比例图',y:10,x:-10,align:'left'},
			subtitle: { useHTML: true, x: -15,y:55},
			xAxis: [{categories:[]}],
			plotOptions : {
				column : {
					groupPadding : 0.05
				}
			}
		};
	 var ylist=[];
	 var obj={name:'Temperature', type:'column'}; 
	 var list=getCount(count,kpi,grad,colorlist);
	 for(var t=0;t<list.length;t++){
		 var ca=list[t];
		 var cc1={ color:ca.color, y:(ca.yy),dataLabels:ca};
		 ylist.push(cc1);
		 xlist.push(ca.xx);
	 }
	 obj.data=ylist;
	 var objarr=[];
	 objarr.push(obj);
	 options.series=objarr;
	 //补图
	 var datas = options.series[0].data;
		var len = tempData.length, i=0;
		for(i=0;i<len;i++){
			if(!datas[i]){datas.push(tempData[i]);xlist.push("");}else{
			}
		}
		options.plotOptions = {
				xAxis : {
					categories : xlist
				},
				column : {
					dataLabels : {
						formatter : function() {
							var result = this.y;
							return result ? result + '' : '';
						}
					}
				}
			};
	    options.xAxis[0].categories=xlist;
		options.xAxis[0].labels={style: {color:'#020202'}};
		var reportForms = new ReportForms();
		reportForms.Create(options);
}

/***
 * 根据路测指标、等级处理百分比数据
 * @param count
 * @param kpi
 * @param grad
 */
function getCount(count,kpi,grad,colorlist){
	//综合
	//处理百分比数据和不为1的情况
	var sum=0;
	 for(var t=0;t<count.length;t++){
		 sum+=count[t];
	 }
	 var num=0;
	 var arr_num=[];
	var list=[];
	if(kpi==-1){
		 for(var t=1;t<=count.length;t++){
			 var ca={};
			 var str="";
			 if(grad==-1){
				 if(t==1||t==2||t==3){ca.color=colorlist[0].rank_color;str+="优";if(t==1)str+="+";if(t==3)str+="-";}
				 if(t==4||t==5||t==6){ca.color=colorlist[1].rank_color;str+="良";if(t==4)str+="+";if(t==6)str+="-";}
				 if(t==7||t==8||t==9){ca.color=colorlist[2].rank_color;str+="中";if(t==7)str+="+";if(t==9)str+="-";}
				 if(t==10||t==11||t==12){ca.color=colorlist[3].rank_color;str+="差";if(t==10)str+="+";if(t==12)str+="-";}
			 }else{
				 if(grad==1)str+="优";
				 if(grad==2)str+="良";
				 if(grad==3)str+="中";
				 if(grad==4)str+="差";
				 if(t==1||t==2||t==3){ca.color=colorlist[grad-1].rank_color;if(t==1)str+="+";if(t==3)str+="-";}
			 }
			 ca.xx=str;
			 ca.yy=count[t-1];
			// var nn=((ca.yy*100)/sum).toFixed(2);
			 //alert(ca.yy+":"+sum+":"+(ca.yy/sum)+":"+nn+":"+parseInt((ca.yy*100)/sum*10000)/100);
			 ca.yy=Math.round(parseFloat(ca.yy/sum)*10000)/100;
			 num+=ca.yy;
			 arr_num.push(ca.yy);
			 list.push(ca);
		 }
	}else{
	//单指标
		 for(var t=1;t<=count.length;t++){
			 var ca={};
			 var str="";
			 if(grad==-1){
				 if(t==1){ca.color=colorlist[0].rank_color;str+="优";}
				 if(t==2){ca.color=colorlist[1].rank_color;str+="良";}
				 if(t==3){ca.color=colorlist[2].rank_color;str+="中";}
				 if(t==4){ca.color=colorlist[3].rank_color;str+="差";}
			 }else{
				 if(grad==1){ca.color=colorlist[0].rank_color;str+="优";}
				 if(grad==2){ca.color=colorlist[1].rank_color;str+="良";}
				 if(grad==3){ca.color=colorlist[2].rank_color;str+="中";}
				 if(grad==4){ca.color=colorlist[3].rank_color;str+="差";}
			 }
			 ca.xx=str;
			 ca.yy=count[t-1];
			 //var nn=((ca.yy*100)/sum).toFixed(2);
			 ca.yy=Math.round(parseFloat(ca.yy/sum)*10000)/100;
			 num+=ca.yy;
			 arr_num.push(ca.yy);
			 list.push(ca);
		 }
	}
	 num=num*100-10000;
	 var ll=[];
	 var ii=0;
	 for(var t=0;t<list.length;t++){
		 var ca=list[t];
		 var cc_nn=ca.yy;
			 if ((num>0||num<0)&&Math.max.apply(null, arr_num) == cc_nn&&ii==0){
				 cc_nn=parseInt(cc_nn*100-num)/100;
				 ii++;
			 }
		 ca.yy=cc_nn;
		 ll.push(ca);
	 }
	return ll;
}

/**
 * 设置地图中心经纬度与等级
 * @param map
 * @param center
 */
function setMapCenter(map,center){
	if (center) {
		var Item_1 = new google.maps.LatLng(center.max_lat ,center.max_lng);
		var myPlace = new google.maps.LatLng(center.min_lat, center.min_lng);
		var bounds = new google.maps.LatLngBounds();
		bounds.extend(myPlace);
		bounds.extend(Item_1);
		map.setCenter(bounds.getCenter());
		map.fitBounds(bounds);
	}
}
$(function(){
	$(".cond_").live('click',function(){
		if($("#sernos_").validatebox("isValid")==false){
			$.messager.alert("提示","输入超出长度！","warning");
			return false;
		}
		var str = $("#diatype").val();
		var openType = $("#openType").val();
		var obj=getPram(3);
		conditionSave(obj,openType);
		if(str==='hi'){
			queryData_(1,obj);
		}else if(str==='dl'){
			download_(obj);
		}
	});
});
function queryData_(time,obj){
	$("#background").show();
	$("#bar_id").show();
	$('#cond').dialog('close');
	$.post(contextPath + "/gisgrad/gisgrad",
			{areaids:obj.areaids,
		    inside:obj.inside,
		    grad:obj.grad,
		    isFirst:time,
		    secendSerno:obj.scendvalue,
		    startTime:obj.startTime,
		    endTime:obj.endTime,
		    sernos:obj.sernos,
		    senceids:obj.senceids,
		    testtype:obj.testtype,
		    nettype:obj.nettype,
		    datatype:obj.datatype,
		    jobtype:obj.jobtype,
		    kpi:obj.kpi,
		    testnet:obj.testnet,
		    colorversion:colorversion
			},
		function(data){
			$("#background").hide();
			$("#bar_id").hide();
			if(data.list.length==0){
				$.messager.alert("提示","查询条件内没有相应数据！","warning");
			}
			list_marker=data.list;
			var colorlist=data.color;
			var count=data.count;
        	isColorChange = data.ischange;
			if(list_marker.length>0&&count.length>0){
	        	   //柱状图显示事件
	        	   //$("#show_flash").show();
	        		   
	        	   var wid_len=(document.body.scrollWidth-document.body.scrollWidth*0.05)/2;
	        	   $("#flash").append("<div class='histogram_flash' id='div_state'  style='width:"+wid_len+"px;margin:0 auto;position: relative;'></div>");
	        	   creatFlash('div_state',count,colorlist,obj.grad,obj.kpi);
	        	   flashHeight = $("#flash").css("height");
	        	   
	        	   initeFlash();
	           }
		}
	);
}
/**
 * 评价导出
 */
function download_(obj){
	   var str_vo="";
	    str_vo+="areaids="+obj.areaids;
	    str_vo+="&inside="+obj.inside;
	    str_vo+="&grad="+obj.grad;
	    str_vo+="&isFirst="+1;
	    str_vo+="&secendSerno="+obj.scendvalue;
	    str_vo+="&startTime="+obj.startTime;
		str_vo+="&endTime="+obj.endTime;
		str_vo+="&senceids="+obj.senceids;
		str_vo+="&testtype="+obj.testtype;
		str_vo+="&nettype="+obj.nettype;
		str_vo+="&datatype="+obj.datatype;
		str_vo+="&jobtype="+obj.jobtype;
		str_vo+="&sernos="+obj.sernos;
		str_vo+="&kpi="+obj.kpi;
		str_vo+="&testnet="+obj.testnet;
	   location.href = contextPath+"/gisgrad/download?"+str_vo;
		$('#cond').dialog('close');
}
/**
*提交柱状图查询时保留查询条件
*/
function conditionSave(obj,openType){
	if(openType==1){
	   grap_.inside = obj.inside;
	   grap_.grad = obj.grad;
	   grap_.startTime = obj.startTime ;
	   grap_.endTime = obj.endTime;
	   grap_.areaids = obj.areaids;
	   grap_.areatext = $("#areatext_").val();
	   grap_.senceids = obj.senceids;
	   grap_.senctext = $("#senctext_").val();
	   grap_.testtype = obj.testtype;
	   grap_.testtypeText = $("#testtypeText_").val();
	   grap_.nettype= obj.nettype;
	   grap_.datatype=obj.datatype;
	   grap_.jobtype=obj.jobtype;
	   grap_.kpi=obj.kpi;
	   grap_.sernos=obj.sernos;
	   grap_.testnet = obj.testnet;
	   grap_.testnetName = $("#testnetName_").val();
	}else if(openType==2){
	   grap_s.inside = obj.inside;
	   grap_s.grad = obj.grad;
	   grap_s.startTime = obj.startTime ;
	   grap_s.endTime = obj.endTime;
	   grap_s.areaids = obj.areaids;
	   grap_s.areatext = $("#areatext_").val();
	   grap_s.senceids = obj.senceids;
	   grap_s.senctext = $("#senctext_").val();
	   grap_s.testtype = obj.testtype;
	   grap_s.testtypeText = $("#testtypeText_").val();
	   grap_s.nettype= obj.nettype;
	   grap_s.datatype=obj.datatype;
	   grap_s.jobtype=obj.jobtype;
	   grap_s.kpi=obj.kpi;
	   grap_s.sernos=obj.sernos;
	   grap_s.testnet = obj.testnet;
	   grap_s.testnetName = $("#testnetName_").val();
	}
}