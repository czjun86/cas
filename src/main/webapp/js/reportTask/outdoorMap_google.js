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
	// 地图
	var zoom=15,bounds;
	if (center && center.length > 0) {
	    	var Item_1 = new google.maps.LatLng(center[0].max_lat ,center[0].max_lng);
			var myPlace = new google.maps.LatLng(center[0].min_lat, center[0].min_lng);
			bounds = new google.maps.LatLngBounds();
			bounds.extend(myPlace);
			bounds.extend(Item_1);
	}
	var myOptions_dt = {
		zoom : 15,
		center :  new google.maps.LatLng(29.559247, 106.54241),
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		mapTypeControl : true,
		streetViewControl : false,
		mapTypeControlOptions : {
			style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
			position : google.maps.ControlPosition.BOTTOM
		},
		panControl : true,
		panControlOptions : {
			position : google.maps.ControlPosition.RIGHT_BOTTOM
		},
		zoomControl : true,
		zoomControlOptions : {
			// style: google.maps.ZoomControlStyle.LARGE,
			position : google.maps.ControlPosition.RIGHT_BOTTOM
		}
	};
	// 生成地图
	map = new google.maps.Map(document.getElementById("div_point"),
			myOptions_dt);
	map.setCenter(bounds.getCenter());
	map.fitBounds(bounds);
	if (map) {
		google.maps.event.trigger(map, 'resize');
	}
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
	// 卫星地图切换事件
	google.maps.event.addListener(map, 'maptypeid_changed', function() {

		if (map.getMapTypeId() == google.maps.MapTypeId.ROADMAP) {// 判断是否普通地图
			for ( var i = 0; i < latAndlngMap_2g.size(); i++) {
				var marker = latAndlngMap_2g.elements[i].key;
				marker.setPosition(latAndlngMap_2g.get(marker));
			}
			for ( var i = 0; i < latAndlngMap_3g.size(); i++) {
				var marker = latAndlngMap_3g.elements[i].key;
				marker.setPosition(latAndlngMap_3g.get(marker));
			}
		} else {
			// 卫星地图
			for ( var i = 0; i < exlatAndlngMap_2g.size(); i++) {
				var marker = exlatAndlngMap_2g.elements[i].key;
				marker.setPosition(exlatAndlngMap_2g.get(marker));
			}
			for ( var i = 0; i < exlatAndlngMap_3g.size(); i++) {
				var marker = exlatAndlngMap_3g.elements[i].key;
				marker.setPosition(exlatAndlngMap_3g.get(marker));
			}
		}
	});
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
			m.setMap(null);
		}
		markerArr_2g = [];
		exlatAndlngMap_2g.clear();
		latAndlngMap_2g.clear();
	}
	if (type == 2) {
		for (i in markerArr_3g) {
			var m = markerArr_3g[i];
			m.setMap(null);
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
							new google.maps.LatLng(wcdmalist[i].lat_modi + t
									* (0.01 / zoom), wcdmalist[i].lng_modi - t
									* (0.01 / zoom)), i, "RSCP",
							new google.maps.LatLng(wcdmalist[i].lat + t
									* (0.01 / zoom), wcdmalist[i].lng - t
									* (0.01 / zoom)), wcdmalist[i].rscp_,
							wcdmalist[i].realnet_type);
				}
			
			break;
		// ecno
		case 2:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].ecno, wcdmalist[i].id, 2,
						new google.maps.LatLng(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "EC/No",
						new google.maps.LatLng(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].ecno_,
						wcdmalist[i].realnet_type);
			}
			break;
		// txpower
		case 3:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].txpower, wcdmalist[i].id, 2,
						new google.maps.LatLng(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "TxPower",
						new google.maps.LatLng(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].txpower_,
						wcdmalist[i].realnet_type);
			}
			break;
		// ftp
		case 4:
			for ( var i = 0; i < wcdmalist.length; i++) {
				addMarker(wcdmalist[i].ftpSpeed, wcdmalist[i].id, 2,
						new google.maps.LatLng(wcdmalist[i].lat_modi + t
								* (0.01 / zoom), wcdmalist[i].lng_modi - t
								* (0.01 / zoom)), i, "FTP",
						new google.maps.LatLng(wcdmalist[i].lat + t
								* (0.01 / zoom), wcdmalist[i].lng - t
								* (0.01 / zoom)), wcdmalist[i].ftpSpeed_,
						wcdmalist[i].realnet_type);
			}
			break;
		// rxlev
		case 6:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].rxlev, gsmlist[i].id, 1,
						new google.maps.LatLng(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "Rxlev",
						new google.maps.LatLng(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].rxlev_,
						gsmlist[i].realnet_type);
			}
			break;
		// rxqual
		case 7:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].rxqual, gsmlist[i].id, 1,
						new google.maps.LatLng(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "Rxqual",
						new google.maps.LatLng(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].rxqual_,
						gsmlist[i].realnet_type);
			}
			break;
		// ci
		case 8:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].ci, gsmlist[i].id, 1,
						new google.maps.LatLng(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "C/I",
						new google.maps.LatLng(gsmlist[i].lat + t
								* (0.01 / zoom), gsmlist[i].lng - t
								* (0.01 / zoom)), gsmlist[i].ci_,
						gsmlist[i].realnet_type);
			}
			break;
		// mos
		case 9:
			for ( var i = 0; i < gsmlist.length; i++) {
				addMarker(gsmlist[i].mos, gsmlist[i].id, 1,
						new google.maps.LatLng(gsmlist[i].lat_modi + t
								* (0.01 / zoom), gsmlist[i].lng_modi - t
								* (0.01 / zoom)), i, "MOS",
						new google.maps.LatLng(gsmlist[i].lat + t
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
							new google.maps.LatLng(wcdmalist[i].lat_modi + t
									* (0.01 / zoom), wcdmalist[i].lng_modi - t
									* (0.01 / zoom)), i, "PSC",
							new google.maps.LatLng(wcdmalist[i].lat + t
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
					addMarker(dc[ij], gsmlist[i].id, 1, new google.maps.LatLng(
							gsmlist[i].lat_modi + t * (0.01 / zoom),
							gsmlist[i].lng_modi - t * (0.01 / zoom)), i,
							"BCCH", new google.maps.LatLng(gsmlist[i].lat + t
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
function addMarker(state, id, type, location, index, kpi, exlocation, intevl,
		realnetType) {
	intevl = ":" + intevl;
	var scale = 1;
	var icon, path, marker, strokeWeight;
	if (type == 2) {
		// 3g图标
		// path= 'M0,0 C130,75 30,25';
		path = 'M0,8,A4,4,0,1,1,0,8.01Z';
		strokeWeight = 1;
	} else if (type == 1) {
		// 2G图标
		path = 'M 0 0 L 10 0 L 5 10 z';
		strokeWeight = 1;
	}
	// psc与bcch用的颜色不一样
	if (kpi != 'PSC' && kpi != 'BCCH') {
		if (state > 0) {
			var fil;
			if (realnetType != -1) {
				// if(kpi=='TxPower'||kpi=='Rxqual'){
				// fil=colorlist[5-(state-1)].colourcode;
				// }else{
				fil = colorlist[state - 1].colourcode;
				//	}
			} else {
				//无服务-菱形
				fil = '#000000';
				path = 'M 0 0 L 5 8 L 0 16 L -5 8 z';
				intevl = "";
			}

			icon = {
				path : path,
				fillOpacity : 1.0,
				fillColor : fil,
				scale : scale,
				rotation : 0,
				strokeColor : fil,
				strokeWeight : strokeWeight
			};
			marker = new google.maps.Marker({
				position : location,
				zIndex : index,
				map : map,
				icon : icon,
				title : kpi + intevl

			});
		}
	} else {
		if (realnetType == -1) {
			//无服务-菱形
			state = '#000000';
			path = 'M 0 0 L 5 8 L 0 16 L -5 8 z';
			intevl = "";
		}
		icon = {
			path : path,
			fillOpacity : 1.0,
			scale : scale,
			rotation : 0,
			fillColor : state,
			strokeColor : state,
			strokeWeight : strokeWeight
		};
		marker = new google.maps.Marker({
			position : location,
			zIndex : index,
			map : map,
			icon : icon,
			title : kpi + intevl

		});
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
			marker.setIcon({
				path : path,
				fillOpacity : 1.0,
				scale : scale,
				rotation : 0,
				fillColor : state,
				strokeColor : "#fe9a10",
				strokeWeight : 2
			});
			//					 marker.setAnimation(google.maps.Animation.BOUNCE);
			//					 marker_first.push(marker);
			var message = new google.maps.InfoWindow( {
				content : "<div style='margin:0px;padding:0px;width:280px;text-align:center;margin-top:3px;font-size:15px;'><strong>起点("+marker.getPosition().lat().toFixed(8)+","+marker.getPosition().lng().toFixed(8)+")<strong></div>"
				,maxWidth: 500
			});
			message.setZIndex(2);
			message.open(map, marker);
			google.maps.event.addListener(marker, "click", function (e) {
				message.open(map, marker);
			});
			clickPoint(type, id, realnetType);
		}
		if (last_type == type && last_id == id) {
//			info中显示内容
//			var boxText = document.createElement("div");
//			boxText.style.cssText = "font-weight:bold;font-size:15px;border: 1px solid rgb(171, 171, 171); margin-top: 8px; background: white; padding: 5px;";
//			boxText.innerHTML = "终点<br>经度："+marker.getPosition().lat().toFixed(8)+"<br>纬度："+marker.getPosition().lng().toFixed(8);
//			自定义info样式
//			var infoOptions = {
//					content: boxText
//					,disableAutoPan: false
//					,maxWidth: 0
//					,pixelOffset: new google.maps.Size(-137, 8)
//					,zIndex: null
//					,boxStyle: { 
//					  background: "url('"+ contextPath + "/images/tipbox.gif') no-repeat"
//					  ,opacity: 1
//					  ,width: "280px"
//					 }
//					,closeBoxMargin: "10px 2px 2px 2px"
//					,closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif"
//					,infoBoxClearance: new google.maps.Size(1, 1)
//					,isHidden: false
//					,pane: "floatPane"
//					,enableEventPropagation: false
//				};
//			var ib = new InfoBox(infoOptions);
//			google.maps.event.addListener(marker, "click", function (e) {
//				ib.open(map, this);
//			});
//			ib.open(map, marker);
			var message2 = new google.maps.InfoWindow( {
				content : "<div style='margin:0px;padding:0px;width:280px;text-align:center;margin-top:3px;font-size:15px;'><strong>终点("+marker.getPosition().lat().toFixed(8)+","+marker.getPosition().lng().toFixed(8)+")<strong></div>"
			});
			message2.setZIndex(1);
			message2.open(map, marker);
			google.maps.event.addListener(marker, "click", function (e) {
				message2.open(map, marker);
			});
		}
		//点击事件
		google.maps.event.addListener(marker, 'click', function() {
			clickPoint(type, id, realnetType);
			var scale = marker.getIcon().scale;
			var fc = marker.getIcon().fillColor;
			var pa = marker.getIcon().path;
			var sw = marker.getIcon().strokeWeight;
			var stc = marker.getIcon().strokeColor;
			if (stc != '#fe9a10') {
				stc = '#222025';
				sw = 1;
				var sc = 0;
				var sc = 2;
				marker.setIcon({
					path : pa,
					fillOpacity : 1.0,
					scale : sc,
					rotation : 0,
					fillColor : fc,
					strokeColor : fc,
					strokeWeight : sw
				});
				setInterval(function() {
					if (sc != 1) {
						sc--;
						marker.setIcon({
							path : pa,
							fillOpacity : 1.0,
							scale : sc,
							rotation : 0,
							fillColor : fc,
							strokeColor : fc,
							strokeWeight : sw
						});
					} else {
						return;
					}
				}, 300);
				if (marker_click) {
					var ff = marker_click.getIcon().fillColor;
					marker_click.setIcon({
						path : marker_click.getIcon().path,
						fillOpacity : 1.0,
						scale : marker_click.getIcon().scale,
						rotation : 0,
						fillColor : ff,
						strokeColor : ff,
						strokeWeight : marker_click.getIcon().strokeWeight
					});
				}
				marker_click = marker;
			}
			//			  
			//			  if(marker_first.length>0){
			//				  for(i in marker_first){
			//					  marker_first[i].setAnimation(null);
			//				  }
			//				  marker_first=[];
			//				 }
			//			  if(type==1){
			//				  marker.setZIndex(marker.getZIndex()-markerArr_2g.length);
			//			  }else{
			//				  marker.setZIndex(marker.getZIndex()-markerArr_3g.length);
			//			  }

			//			  marker.setAnimation(google.maps.Animation.BOUNCE);
		});
	}
}