/**
 * 加载indoorMap的小区
 * */
var span_map_psc = new Array();
var p_map_jizhan = new Array();
var sctype;
var angle;
var mapWforGoogle;
function getCell(map,lat,lng,flowid){
	$.ajax({
		type : "post",
		url : contextPath + "/reportTask/getCell",
		data : ({flowid : flowid}),
		success : function(data) {
			angle = data.angle;
			var cells = data.cell;
			sctype = data.type;
			var distance=[];
			for(var i=0;i<cells.length;i++){
				//画线
				//连线的点组合
//				var stanceValue = map.getDistance(new BMap.Point(lng,lat),new BMap.Point(cells[i].celllng,cells[i].celllat)).toFixed(2);
//				var message = stanceValue+"m";
//				distance[i] = stanceValue;
				(function(i) { 
					//封装经纬度数据转换成百度经纬度
					var paths_pol=[new BMap.Point(cells[i].celllng, cells[i].celllat), new BMap.Point(lng, lat)];

					/*var paths_pol_baidu=[];
						BMap.Convertor.translate(paths_pol[0],2,function(p){
							paths_pol_baidu.push(p);
							paths_pol_baidu.push(paths_pol[1]);*/
								var polyline= new BMap.Polyline(paths_pol,
				                        {
											strokeColor:"#00ccff", //颜色
											strokeWeight:4, 		//宽度
											strokeOpacity:1			//透明度
										}
								);
								var dd=map.getDistance(polyline.getPath()[0],polyline.getPath()[1]).toFixed(2);
								distance.push(dd);
								polyline.addEventListener('click', function(i){
									$("#currvalue").val(map.getDistance(polyline.getPath()[0],polyline.getPath()[1]).toFixed(2)+"m");
								});
								map.addOverlay(polyline);
								//最后次画线时计算最远和最近距离并重新调整中心点
								if(i==cells.length-1){
									if(distance.length>0){
										getDistance(distance);
									}
									
								}
						//});
				})(i);
			}
			stationInitData = data.list;
			
			for (var  j = 0; j < stationInitData.length; j++) {
				drawStation(stationInitData[j].bsList, map);
			}
			
			
			
		}
	});
}
var EARTH_RADIUS = 6378137.0; //单位M 
var PI = Math.PI; 

function getRad(d){ 
return d*PI/180.0; 
}
/**
 * 初始化百度地图
 */
function initMap_baidu(){
	//创建地图
	var map = new BMap.Map("indoor_div");  // 创建地图实例  
	var point = new BMap.Point(lng,lat);  // 创建点坐标 
	map.enableDragging();					//启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom(); 			//启用地图滚轮放大缩小
	map.enableDoubleClickZoom(); 			//启用鼠标双击放大，默认启用(可不写)
	map.enableKeyboard(); 					//启用键盘上下左右键移动地图
	addMapControl(map);
	var marker = new BMap.Marker(point);
	var label = new BMap.Label('测试位置',{"offset":new BMap.Size(9,-15)});
	marker.setLabel(label);
	map.addOverlay(marker);
	map.centerAndZoom(point, 15);         // 初始化地图，设置中心点坐标和地图级别
	getCell(map,lat,lng,flowid_);//加载连线和小区
}

//地图控件添加函数：
function addMapControl(map) {
    //向地图中添加缩放控件
    var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT, type: BMAP_NAVIGATION_CONTROL_SMALL });
    map.addControl(ctrl_nav);
    //向地图中添加缩略图控件
    var ctrl_ove = new BMap.OverviewMapControl({ anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1 });
    map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
    var ctrl_sca = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT });
    map.addControl(ctrl_sca);
}
/**
 * 计算两点距离
 */
function getFlatternDistance(lat1,lng1,lat2,lng2){ 
	var radLat1 = getRad(lat1); 
	var radLat2 = getRad(lat2); 

	var a = radLat1 - radLat2; 
	var b = getRad(lng1) - getRad(lng2); 

	var s = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))); 
	s = s*EARTH_RADIUS; 
	s = Math.round(s*10000)/10000.0; 

	return s; 
} 
/**
 * 计算最远和最近的一个距离
 */
function getDistance(distance){
	var max;
	var min;
	for(var i=0;i<distance.length;i++){
		if(i==0){
			max = distance[i];
			min = distance[i];
		}else{
			if(distance[i]>max){
				max = distance[i];
			}
			if(distance[i]<min){
				min = distance[i];
			}
		}
	}
	$("#titleName").html("<span>当前距离:<input type=\"text\" id=\"currvalue\" disabled=\"disabled\" style=\"height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;\"></span>"+
	"<span>最远距离:<input type=\"text\" id=\"maxvalue\" disabled=\"disabled\" style=\"height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;\"></span>"+
	"<span>最近距离:<input type=\"text\" id=\"minvalue\" disabled=\"disabled\" style=\"height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;\"></span>");
	$("#maxvalue").val(max+"m");
	$("#minvalue").val(min+"m");
}