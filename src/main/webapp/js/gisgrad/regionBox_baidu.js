var minX,maxX,minY,maxY;//框选的最大最小值
var mousex,mousey;//监控鼠标位置
var mousex2,mousey2;//鼠标最后一次按下时位置
var pr;//框选层
var dm;
var drawingManager;
var nowx;
var nowy;
function chooseMap(map){
	 drawingManager =new BMapLib.DrawingManager(map, {isOpen: false, 
		    drawingType: BMAP_DRAWING_MARKER, enableDrawingTool: true,
		    enableCalculate: false,
		    drawingToolOptions: {
		        anchor: BMAP_ANCHOR_TOP_LEFT,
		        offset: new BMap.Size(5, 5),
		        drawingTypes : [
		            BMAP_DRAWING_RECTANGLE 
		         ]
		    },
		    polylineOptions: {
		        strokeColor: "#8FA4F5"
		    }});
	  dm = drawingManager;
	  drawingManager.addEventListener('rectanglecomplete', function(re) {//监控矩形完成事件
		 var bo =re.getBounds();
		 //打印出坐标后恢复为拖拽地图设置
		 if(!(typeof(pr)=="undefined")){
			 close(map);//当再次框选是之前矩形框消失
		 }
		 pr = re;
		 getinformation(bo,re);
		});
}
function getinformation(bo,re){
	maxX=bo.getNorthEast().lat; 
	minX=bo.getSouthWest().lat; 
	maxY=bo.getNorthEast().lng; 
	minY=bo.getSouthWest().lng;
	var c_info = $("#stationareaids").val();
	if((typeof(regionCondition)!= 'undefined' && regionCondition!='' && regionCondition != null)||(typeof(c_info)!= 'undefined' && c_info!="\'\'" && c_info != null)){
		layerDiv();
	}else{
		$.messager.alert("提示","请先查询数据或加载小区！","warning",function(){
			close(map);
		});
	}
	//re.setMap(null);
}
/**
*弹出层
*/
function layerDiv(){
		var xx;
		var yy;
		if(mousex<=mousex2){
			xx = mousex2-90;
			nowx = xx;
		}else{
			xx = mousex-90;
			nowx = xx;
		}
		if(mousey2>=mousey){
			yy = mousey2+2;
			nowy = yy;
		}else{
			yy = mousey+2;
			nowy = yy;
		}
		$("#faqdiv").css("left",xx+"px");
		$("#faqdiv").css("top",yy+"px");
		$("#faqdiv").css("display","block");
}
/**
 * 关闭罩子
 */
function close(map){
		$("#faqbg").css("display","none");
		$("#faqdiv").css("display","none");
		$("#fadown").css("display","none");
		if(!(typeof(pr)=="undefined")){
			map.removeOverlay(pr);
		}
};
/**
 * 菜单一点击事件
 */
$(function(){
	$("#close").click(function(){
		close(map);
	});
	$("#regiondown").click(function(){
		if(typeof(regionCondition)!='undefined'&&typeof(minX)!='undefined'&&typeof(minY)!='undefined'&&typeof(maxY)!='undefined'&&typeof(maxX)!='undefined'){
		var obj=regionCondition;
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
			str_vo+="&minlat="+minX;
			str_vo+="&maxlat="+maxX;
			str_vo+="&minlng="+minY;
			str_vo+="&maxlng="+maxY;
			
		location.href = contextPath+"/gisgrad/download?"+str_vo;
		close(map);
		}else{
			$.messager.alert("提示","请先查询数据！","warning",function(){
				close(map);
			});
		}
	});
	/**
	 * 点击下载显示下载菜单
	 */
	$("#downloadchoose").live('click',function(){
		if($("#fadown").css("display") == "none"){
			$("#fadown").css("left",nowx-41+"px");
			$("#fadown").css("top",(nowy+28)+"px");
			$("#fadown").css("display","block");
		}else if($("#fadown").css("display") == "block"){
			$("#fadown").css("display","none");
		}
	});
	/**
	 * 小区框选导出
	 */
	$("#c_info_down").click(function(){
			var ids = $("#stationareaids").val();
			var nettype = $("#cellnettype").val();
			var indoor = $("#cellindoor").val();
			var cellBands = $("#cellcellbands").val();
		if(ids!="\'\'"&& typeof(minX)!='undefined'&&typeof(minY)!='undefined'&&typeof(maxY)!='undefined'&&typeof(maxX)!='undefined'){
			var str_vo="";
			str_vo += 'areas=' + ids;
			str_vo += '&indoor=' + indoor;
			str_vo += '&modetypes=' + nettype;
			str_vo += '&cellBands=' + cellBands;
			str_vo+="&minlat="+minX;
			str_vo+="&maxlat="+maxX;
			str_vo+="&minlng="+minY;
			str_vo+="&maxlng="+maxY;
			if(clicktrunk!=''&&typeof(clicktrunk)!='undefined'){
				var lac = clicktrunk.substring(clicktrunk.indexOf('_')+1,clicktrunk.lastIndexOf('_'));
				var cid = clicktrunk.substring(clicktrunk.lastIndexOf('_')+1,clicktrunk.length);
				str_vo+="&lac_cid="+lac+"_"+cid;
			}
			var cellrel = "";
			if($("#tp").find("span").length>0){//同屏0
				cellrel +="0,";
			}
			if($("#yp").find("span").length>0){//异屏
				cellrel +="1,";
			}
			if($("#ywl").find("span").length>0){//异系统
				cellrel +="2,";
			}
			str_vo+="&cellrel="+cellrel;
			str_vo+="&report_type=0";
			location.href = contextPath+'/gisgrad/downloadcellinfo?'+str_vo;
			close(map);
		}else{
			$.messager.alert("提示","请先加载小区！","warning",function(){
				close(map);
			});
		}
	});
	/**
	 * 选择切换图片
	 */
	$("#marquee").click(function(){
		drawingManager.open();
		drawingManager.setDrawingMode(BMAP_DRAWING_RECTANGLE);
		$(this).hide();
		$("#hand_hover").hide();
		$("#hand").show();
		$("#marquee_hover").show();
	});
	$("#hand").click(function(){
		if(drawingManager.getDrawingMode() == 'rectangle'){
			//切换为拖拽是，移除已有的框选图层
			close(map);

			drawingManager.close();
			$(this).hide();
			$("#hand_hover").show();
			$("#marquee").show();
			$("#marquee_hover").hide();
		  }
	});
	$(".gis-mapgj ").css("left",document.documentElement.clientWidth-70);
	window.onresize = function(){
		$(".gis-mapgj ").css("left",document.documentElement.clientWidth-70);
	};
	/**
	*获取当前鼠标所在位置
	*/
	$("#div_map").mousemove(function (e) {
		mousex = e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft;
		mousey = e.clientY+document.body.scrollTop+document.documentElement.scrollTop;
	});
	
});
/**
*获取鼠标最后一次按下时位置
*/
function mousedown(e) {
	mousex2 = e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft;
	mousey2 = e.clientY+document.body.scrollTop+document.documentElement.scrollTop;
	//alert(mousex2+":"+mousey2);
}