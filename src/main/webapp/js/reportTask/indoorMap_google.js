/**
 * 加载indoorMap的小区
 * */
var span_map_psc = new Array();
var p_map_jizhan = new Array();
var sctype;
var angle;
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
				var heatmapData = [
				      new google.maps.LatLng(cells[i].celllat,cells[i].celllng),
				      new google.maps.LatLng(lat,lng)
				                   ];
				var stanceValue = getFlatternDistance(parseFloat(lat),parseFloat(lng),parseFloat(cells[i].celllat),parseFloat(cells[i].celllng)).toFixed(2);
				var message = stanceValue+"m";
				distance[i] = stanceValue;
				//线属性
				var polyOptions = {
						path: heatmapData,
						strokeColor: '#00ccff',//颜色
						strokeOpacity: 1.0,//透明度
						strokeWeight: 2,//宽度
						title: message
					};
				//把线添加到google里
				var poly = new google.maps.Polyline(polyOptions);
			 	//装载
				poly.setMap(map);
				google.maps.event.addListener(poly,'click',function(event){
					$("#currvalue").val(this.title);
				});
				//画图
				/*var marker = new google.maps.Marker({
					position : new google.maps.LatLng(cells[i].celllat,cells[i].celllng),
					title:"LAC:"+cells[i].lac+",CID"+cells[i].cellId+"小区名称"+cells[i].cellName+"",
					map : map
				});*/
			}
			
			stationInitData = data.list;
			for (var  j = 0; j < stationInitData.length; j++) {
				drawStation(stationInitData[j].bsList, map);
			}
			//drawStation(cells, map);
			
			//计算最远和最近距离
			if(distance.length>0){
				getDistance(distance);
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