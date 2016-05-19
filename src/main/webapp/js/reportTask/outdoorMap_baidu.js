/**
 * 室外地图初始化并生成轨迹点的展示
 * 判断第一个点并默认展示RSCP、RXLEV的轨迹
 * 绑定地图与卫星地图切换功能与数据定位转换
 * @param wcdmalist
 * @param gsmlist
 * @param center
 * @param psc_xlist
 * @param bcch_xlist
 */
function initMap(wcdmalist, gsmlist, center, psc_xlist, bcch_xlist) {
$("#div_point").show();
	map = new BMap.Map("div_point");
	// 地图
	var zoom;
	if (center && center.length > 0) {
		var paths_pol=[new BMap.Point(center[0].max_lng, center[0].max_lat), new BMap.Point(center[0].min_lng,center[0].min_lat)];
		map.setViewport(paths_pol);
	} else {
		cenetr_lat_lng = new BMap.Point(106.57216193892,29.545994617885);
		zoom = 15;
		map.centerAndZoom(cenetr_lat_lng ,zoom);
	}
	//地图事件设置函数：
	map.enableDragging(); //启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom(); //启用地图滚轮放大缩小
	map.enableDoubleClickZoom(); //启用鼠标双击放大，默认启用(可不写)
	map.enableKeyboard(); //启用键盘上下左右键移动地图
	//地图控件添加函数：
    //向地图中添加缩放控件
    var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT,type: BMAP_NAVIGATION_CONTROL_SMALL});
    map.addControl(ctrl_nav);
    //向地图中添加缩略图控件
    var ctrl_ove = new BMap.OverviewMapControl({ anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1 });
    map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
    var ctrl_sca = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT });
    map.addControl(ctrl_sca);
	// 默认显示右边数据为第一个点数据
	var time_3g, time_2g, id_3g, id_2g, lat_3g, lng_3g, lat_2g, lng_2g;
	var time_3g_last,time_2g_last,id_3g_last,id_2g_last;
	if (gsmlist) {
		for ( var i = 0; i < gsmlist.length; i++) {
			if (i == 0) {
				time_2g = gsmlist[i].eptime;
				id_2g = gsmlist[i].id;
				lat_2g = gsmlist[i].lat_modi;
				lng_2g = gsmlist[i].lng_modi;
			}
			break;
		}
		if(gsmlist.length > 0){
			time_2g_last = gsmlist[gsmlist.length-1].eptime;
			id_2g_last = gsmlist[gsmlist.length-1].id;
		}
	}
	if (wcdmalist) {
		for ( var i = 0; i < wcdmalist.length; i++) {
			if (i == 0) {
				time_3g = wcdmalist[i].eptime;
				id_3g = wcdmalist[i].id;
				lat_3g = wcdmalist[i].lat_modi;
				lng_3g = wcdmalist[i].lng_modi;
			}
			break;
		}
		if(wcdmalist.length>0){
			time_3g_last = wcdmalist[wcdmalist.length-1].eptime;
			id_3g_last = wcdmalist[wcdmalist.length-1].id;
		}
	}

	// 第一个点比较时间大小,默认显示点
	if (time_3g && time_2g) {

		if (time_3g > time_2g) {
			first_id = id_2g;
			first_type = 1;
		} else {
			first_id = id_3g;
			first_type = 2;
		}
	} else {
		if (time_3g) {
			first_id = id_3g;
			first_type = 2;
		}
		if (time_2g) {
			first_id = id_2g;
			first_type = 1;
		}
	}
	
	if (time_3g_last && time_2g_last) {

		if (time_3g_last < time_2g_last) {
			// 2
			last_id = id_2g_last;
			last_type = 1;
		} else {
			last_id = id_3g_last;
			last_type = 2;
		}
	} else {
		if (time_3g_last) {
			last_id = id_3g_last;
			last_type = 2;
		}
		if (time_2g_last) {
			last_id = id_2g_last;
			last_type = 1;
		}
	}
	
	if (map) {
		// 3G室外点默认加载rscp
		setTimeout(function() {
			chooseKpi(wcdmalist, gsmlist, "1", 2, psc_xlist, bcch_xlist);
			// 2G室外点默认加载rxlev
			chooseKpi(wcdmalist, gsmlist, "6", 1, psc_xlist, bcch_xlist);
		},500);
	}
}
/*******************************************************************************
 * 室外对各指标的操作（包括下拉菜单与叠加菜单）数据的处理
 * 对下拉框里的指标值任务原始经纬度轨迹，其他指标轨迹按照选择的顺序进行定量（0.01/zoom）的偏移
 * @param wcdmalist
 *            3g数据
 * @param gsmlist
 *            2g数据
 * @param kpi用逗号隔开可多个','
 * @param type
 *            0-清除所有点，1－清除2G点，2－清除3G点
 */
function chooseKpi(wcdmalist, gsmlist, kpi, type, psc_xlist, bcch_xlist) {
	// 清除数据点
	if (type == 1) {
		for (i in markerArr_2g) {
			var m = markerArr_2g[i];
			 map.removeOverlay(m);
		}
		markerArr_2g = [];
		exlatAndlngMap_2g.clear();
		latAndlngMap_2g.clear();
	}
	if (type == 2) {
		for (i in markerArr_3g) {
			var m = markerArr_3g[i];
		}
		markerArr_3g = [];
		exlatAndlngMap_3g.clear();
		latAndlngMap_3g.clear();
	}
	var pscmap = new Map();// 键：PSC值，值：颜色集id
	var bcchmap = new Map();// 键：PSC值，值：颜色集id
	var arr_color = [];
	for ( var t = 0; t < colorlist.length; t++) {
		var color = colorlist[t];
		arr_color.push(color.colourcode);
	}
	var colorall = [];
	var psc_arr_color = [];

	for ( var j = arr_color.length - 1; j >= 0; j--) {
		psc_arr_color.push(arr_color[j]);
	}
	var dc = colorall.concat(psc_arr_color, colorRoom);// 两个颜色合并作为PSC颜色用
	var str = kpi.split(',');
	var len = str.length;// 选择指标个数
	var zoom = 1;
	if (map) {
		zoom = map.getZoom();
	}
	// 循环选中的指标marker
	for ( var t = 0; t < str.length; t++) {
		switch (parseInt(str[t])) {
		// rscp
		case 1:
				for ( var i = 0; i < wcdmalist.length; i++) {
					addMarker(wcdmalist[i].rscp, wcdmalist[i].id, 2,
							new BMap.Point(wcdmalist[i].lat_modi + t
									* (0.01 / zoom), wcdmalist[i].lng_modi - t
									* (0.01 / zoom)), i, "RSCP",
							new BMap.Point(wcdmalist[i].lat + t
									* (0.01 / zoom), wcdmalist[i].lng - t
									* (0.01 / zoom)), wcdmalist[i].rscp_,
							wcdmalist[i].realnet_type);
				}
			
			break;
		// ecno
		case 2:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].ecno, wcdmalist[i].id, 2,
						new BMap.Point(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "EC/No",
						new BMap.Point(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].ecno_,
						wcdmalist[i].realnet_type);
			}
			break;
		// txpower
		case 3:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].txpower, wcdmalist[i].id, 2,
						new BMap.Point(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "TxPower",
						new BMap.Point(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].txpower_,
						wcdmalist[i].realnet_type);
			}
			break;
		// ftp
		case 4:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].ftpSpeed, wcdmalist[i].id, 2,
						new BMap.Point(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "FTP",
						new BMap.Point(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].ftpSpeed_,
						wcdmalist[i].realnet_type);
			}
			break;
		// rxlev
		case 6:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].rxlev, gsmlist[i].id, 1,
						new BMap.Point(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "Rxlev",
						new BMap.Point(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].rxlev_,
						gsmlist[i].realnet_type);
			}
			break;
		// rxqual
		case 7:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].rxqual, gsmlist[i].id, 1,
						new BMap.Point(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "Rxqual",
						new BMap.Point(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].rxqual_,
						gsmlist[i].realnet_type);
			}
			break;
		// ci
		case 8:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].ci, gsmlist[i].id, 1,
						new BMap.Point(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "C/I",
						new BMap.Point(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].ci_,
						gsmlist[i].realnet_type);
			}
			break;
		// mos
		case 9:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].mos, gsmlist[i].id, 1,
						new BMap.Point(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "MOS",
						new BMap.Point(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].mos_,
						gsmlist[i].realnet_type);
			}
			break;
		// psc
		case 20:
			for ( var i = 0; i < wcdmalist.length; i++) {
				// PSC

				var ij = $.inArray(wcdmalist[i].psc.toString(), psc_xlist);
				if (ij < 20) {
					addMarker(dc[ij], wcdmalist[i].id, 2,
							new BMap.Point(wcdmalist[i].lat_modi + t
									* (0.01 / zoom), wcdmalist[i].lng_modi - t
									* (0.01 / zoom)), i, "PSC",
							new BMap.Point(wcdmalist[i].lat + t
									* (0.01 / zoom), wcdmalist[i].lng - t
									* (0.01 / zoom)), wcdmalist[i].psc,
							wcdmalist[i].realnet_type);
				}
			}
			break;
		// bcch
		case 21:

			for ( var i = 0; i < gsmlist.length; i++) {
				var ij = $.inArray(gsmlist[i].bcch.toString(), bcch_xlist);
				if (ij < 20) {
					addMarker(dc[ij], gsmlist[i].id, 1, new BMap.Point(
							gsmlist[i].lat_modi + t * (0.01 / zoom),
							gsmlist[i].lng_modi - t * (0.01 / zoom)), i,
							"BCCH", new BMap.Point(gsmlist[i].lat + t
									* (0.01 / zoom), gsmlist[i].lng - t
									* (0.01 / zoom)), gsmlist[i].bcch,
							gsmlist[i].realnet_type);
				}
			}
			break;
		default:
			break;
		}
	}

}

/*******************************************************************************
 * 室外添加arker方法
 * 判断数据测试类型，如果是：
 * 3G采用圆路径－M0,8,A4,4,0,1,1,0,8.01Z按照测试颜色
 * 2G倒三角型－M 0 0 L 10 0 L 5 10 z按照测试颜色
 * 无服务菱形－M 0 0 L 5 8 L 0 16 L -5 8 z黑色
 * 设置marker的title
 * 绑定marker点击事件调用clickPoint展示预处理数据，对点效果进行处理，变大后变小，形成外圈
 * 如果是第一个点默认带红色外圈
 * @param state颜色状态
 * @param id
 * @param type类型1-2g,2-3g
 * @param location地方——偏移后
 * @param exlocation地方-偏移前
 * @param index顺序
 * @param kpi
 * @param intevl指标值
 * @param realnetType真实网络状态
 */
var marker_click;
var markerArr_3g = [], markerArr_2g = [], marker_first = [];// 封装2g、3g点数据数组
function addMarker(state, id, type, location_, index, kpi, exlocation, intevl,
		realnetType) {
	var location = new BMap.Point(location_.lat,location_.lng);
	intevl = ":" + intevl;
	var icon, path, marker;
	if (type == 2) {
		// 3g图标
		// path= 'M0,0 C130,75 30,25';
		path = 'w_';
		strokeWeight = 1;
	} else if (type == 1) {
		// 2G图标
		path = 'g_';
		strokeWeight = 1;
	}
	// psc与bcch用的颜色不一样
	if (kpi != 'PSC' && kpi != 'BCCH') {
		if (state > 0) {
			var fil;
			if (realnetType != -1) {
				path += colorlist[state - 1].colourcode.substring(1,colorlist[state - 1].colourcode.length);
			} else {
				path="linxin";
			}
			icon =new BMap.Icon(contextPath+'/images/integration/'+path+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
		  
			marker = new BMap.Marker(location,{
				title : kpi + intevl,
				icon:icon});
		}
	} else {
		if (realnetType == -1) {
			//无服务-菱形
//			state = '#000000';
//			path = 'M 0 0 L 5 8 L 0 16 L -5 8 z';
//			intevl = "";
			path="linxin";
			state=",";
		}
		path=path+state.substring(1,state.length);
		icon =new BMap.Icon(contextPath+'/images/integration/'+path+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
		marker = new BMap.Marker(
			location,{
			title : kpi + intevl,
			icon:icon});
	}

	if (marker) {

		//把各自的点装入数组中
		if (type == 1) {
			markerArr_2g.push(marker);
			exlatAndlngMap_2g.put(marker, exlocation);
			latAndlngMap_2g.put(marker, location);
		} else {
			markerArr_3g.push(marker);
			exlatAndlngMap_3g.put(marker, exlocation);
			latAndlngMap_3g.put(marker, location);
		}
		//如果是第一个跳动
		if (first_type == type && first_id == id) {
			if (kpi != 'PSC' && kpi != 'BCCH')
				state = fil;
			
			var message = new BMap.InfoWindow("<div style='margin:0px;padding:0px;width:280px;text-align:center;margin-top:3px;font-size:15px;'><strong>起点("+location.lat.toFixed(8)+","+location.lng.toFixed(8)+")<strong></div>",{enableMessage:false,enableCloseOnClick:false});
					
//			BMap.Convertor.translate(marker.getPosition(),2,function(p){
//				map.openInfoWindow(message, p);      // 打开信息窗口
//			});
			map.openInfoWindow(message, marker.getPosition());      // 打开信息窗口
			marker.addEventListener("click", function (e) {
				marker.openInfoWindow(message);
			});
			clickPoint(type, id, realnetType);
		}
		if (last_type == type && last_id == id) {
			var message2 = new BMap.InfoWindow(
				"<div style='margin:0px;padding:0px;width:280px;text-align:center;margin-top:3px;font-size:15px;'><strong>终点("+location.lat.toFixed(8)+","+location.lng.toFixed(8)+")<strong></div>"
			,{enableMessage:false,enableCloseOnClick:false});
			
//			BMap.Convertor.translate(marker.getPosition(),2,function(p){
//				map.openInfoWindow(message2, p);      // 打开信息窗口
//			});
			
			map.openInfoWindow(message2, marker.getPosition());      // 打开信息窗口
			marker.addEventListener( "click", function (e) {
				marker.openInfoWindow(message2); 
			});
		}
		map.addOverlay(marker);
		//点击事件
		marker.addEventListener('click', function() {
			clickPoint(type, id, realnetType);		
//			if (stc != '#fe9a10') {
//				stc = '#222025';
				var sc= 20;
				setInterval(function() {
					if (sc != 14) {
						sc=sc-2;
						icon=marker.getIcon();
						icon.setSize(new BMap.Size(sc,sc));
						icon.setImageSize(new BMap.Size(sc,sc));
						marker.setIcon(icon);
						
					} else {
						return;
//						sc=sc+2;
//						icon=marker.getIcon();
//						icon.setSize(new BMap.Size(sc,sc));
//						icon.setImageSize(new BMap.Size(sc,sc));
//						marker.setIcon(icon);
					}
				}, 300);
				if (marker_click) {
					icon=marker.getIcon();
					icon.setSize(new BMap.Size(14,14));
					icon.setImageSize(new BMap.Size(14,14));
					marker_click.setIcon(icon);
				}
				marker_click = marker;
		//	}
		});
	}
}
