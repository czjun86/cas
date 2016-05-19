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
function initMap(wcdmalist, gsmlist,ltelist, center, psc_xlist, bcch_xlist,pci_xlist) {
$("#div_point").show();
	map = new BMap.Map("div_point");
	//map.centerAndZoom("重庆市" ,15);
	// 地图
	var zoom;
	
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
    if(center.length>0)
    	map.setViewport(center);
	// 默认显示右边数据为第一个点数据
	var time_3g="", time_2g="", id_3g,time_4g="", id_4g, id_2g, lat_3g, lng_3g,lat_4g, lng_4g, lat_2g, lng_2g;
	var time_3g_last="",time_2g_last="",id_3g_last,id_2g_last,time_4g_last="",id_4g_last;
	if (gsmlist) {
		if(gsmlist.length > 0){
			time_2g = gsmlist[0].eptime;
			id_2g = gsmlist[0].id;
			lat_2g = gsmlist[0].lat_modi;
			lng_2g = gsmlist[0].lng_modi;
			time_2g_last = gsmlist[gsmlist.length-1].eptime;
			id_2g_last = gsmlist[gsmlist.length-1].id;
		}
	}
	if (wcdmalist) {
		if(wcdmalist.length>0){
			time_3g = wcdmalist[0].eptime;
			id_3g = wcdmalist[0].id;
			lat_3g = wcdmalist[0].lat_modi;
			lng_3g = wcdmalist[0].lng_modi;
			time_3g_last = wcdmalist[wcdmalist.length-1].eptime;
			id_3g_last = wcdmalist[wcdmalist.length-1].id;
		}
	}
	if (ltelist) {
		if(ltelist.length>0){
			time_4g = ltelist[0].eptime;
			id_4g = ltelist[0].id;
			lat_4g = ltelist[0].lat_modi;
			lng_4g = ltelist[0].lng_modi;
			time_4g_last = ltelist[ltelist.length-1].eptime;
			id_4g_last = ltelist[ltelist.length-1].id;
		}
		
	}
	// 第一个比较时间大小,默认显示点
	if (time_3g || time_2g||time_4g) {
	var tt_ty="";
		if(time_3g!=''&&time_2g!=''){tt_ty=time_3g>time_2g?time_2g:time_3g;}else{if(time_3g!='')tt_ty=time_3g;if(time_2g!='')tt_ty=time_2g};
		if(time_4g!=''&&tt_ty!=''){tt_ty=time_4g>tt_ty?tt_ty:time_4g;}else{if(tt_ty=="")tt_ty=time_4g;}
		if(tt_ty==time_3g){first_type=2;first_id=id_3g;};
		if(tt_ty==time_2g){first_type=1;first_id=id_2g;};
		if(tt_ty==time_4g){first_type=3;first_id=id_4g;};
	}
	// 最后一个点比较时间大小,默认显示点
	if (time_3g_last|| time_2g_last||time_4g_last) {
			var tt_ty_la="";
			if(time_3g_last!=''&&time_2g_last!=''){tt_ty_la=time_3g_last>time_2g_last?time_3g_last:time_2g_last;}else{if(time_3g_last!='')tt_ty_la=time_3g_last;if(time_2g_last!='')tt_ty_la=time_2g_last};
			if(time_4g_last!=''&&tt_ty_la!=''){tt_ty_la=time_4g_last>tt_ty_la?time_4g_last:tt_ty_la;}else{if(tt_ty_la=="")tt_ty_la=time_4g_last;}
			if(tt_ty_la==time_3g_last){last_type=2;last_id=id_3g_last;};
			if(tt_ty_la==time_2g_last){last_type=1;last_id=id_2g_last;};
			if(tt_ty_la==time_4g_last){last_type=3;last_id=id_4g_last;};
	}
//	if (map) {
//		// 3G室外点默认加载rscp
//		setTimeout(function() {
			chooseKpi(wcdmalist, gsmlist,ltelist, "1", 2, psc_xlist, bcch_xlist,pci_xlist);
			chooseKpi(wcdmalist, gsmlist,ltelist, "6", 1, psc_xlist, bcch_xlist,pci_xlist);
			chooseKpi(wcdmalist, gsmlist,ltelist, "23", 3, psc_xlist, bcch_xlist,pci_xlist);
//		},200);
	//}
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
function chooseKpi(wcdmalist, gsmlist,ltelist, kpi, type, psc_xlist, bcch_xlist,pci_xlist) {
	// 清除数据点
	if (type==1) {
		for (i in markerArr_2g) {
			 map.removeOverlay(markerArr_2g[i]);
		}
		markerArr_2g = [];
		exlatAndlngMap_2g.clear();
		latAndlngMap_2g.clear();
	}
	if (type==2) {
		for (i in markerArr_3g) {
			map.removeOverlay(markerArr_3g[i]);
		}
		markerArr_3g = [];
		exlatAndlngMap_3g.clear();
		latAndlngMap_3g.clear();
	}
	if (type==3) {
		for (i in markerArr_4g) {
			map.removeOverlay(markerArr_4g[i]);
		}
		markerArr_4g = [];
		exlatAndlngMap_4g.clear();
		latAndlngMap_4g.clear();
	}
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
	var str = [];
	str=kpi.length>1?kpi.split(','):str;
	if(str.length==0)str.push(kpi);
	var len = str.length;// 选择指标个数
	var zoom = 1;
	if (map) {
		zoom = map.getZoom();
	}
	// 循环选中的指标marker
	for ( var t = 0; t < len; t++) {
				for ( var i = 0; i < wcdmalist.length; i++) {
					switch (parseInt(str[t])) {
					// rscp
					case 1:
					addMarker(wcdmalist[i].rscp, wcdmalist[i].id, 2,
							new BMap.Point(wcdmalist[i].lng_modi + t
									* (0.01 / zoom), wcdmalist[i].lat_modi - t
									* (0.01 / zoom)), i, "RSCP",
							new BMap.Point(wcdmalist[i].lng + t
									* (0.01 / zoom), wcdmalist[i].lat - t
									* (0.01 / zoom)), wcdmalist[i].rscp_,
							wcdmalist[i].realnet_type);
					break;
					// ecno
					case 2:
							addMarker(wcdmalist[i].ecno, wcdmalist[i].id, 2,
									new BMap.Point(wcdmalist[i].lng_modi + t
											* (0.01 / zoom), wcdmalist[i].lat_modi - t
											* (0.01 / zoom)), i, "EC/No",
									new BMap.Point(wcdmalist[i].lng + t
											* (0.01 / zoom), wcdmalist[i].lat - t
											* (0.01 / zoom)), wcdmalist[i].ecno_,
									wcdmalist[i].realnet_type);
					
						break;
					// txpower
					case 3:
							addMarker(wcdmalist[i].txpower, wcdmalist[i].id, 2,
									new BMap.Point(wcdmalist[i].lng_modi + t
											* (0.01 / zoom), wcdmalist[i].lat_modi - t
											* (0.01 / zoom)), i, "TxPower",
									new BMap.Point(wcdmalist[i].lng + t
											* (0.01 / zoom), wcdmalist[i].lat - t
											* (0.01 / zoom)), wcdmalist[i].txpower_,
									wcdmalist[i].realnet_type);
						
						break;
					case 4:
							addMarker(wcdmalist[i].ftpSpeed, wcdmalist[i].id, 2,
									new BMap.Point(wcdmalist[i].lng_modi + t
											* (0.01 / zoom), wcdmalist[i].lat_modi - t
											* (0.01 / zoom)), i, "FTP3G",
									new BMap.Point(wcdmalist[i].lng + t
											* (0.01 / zoom), wcdmalist[i].lat - t
											* (0.01 / zoom)), wcdmalist[i].ftpSpeed_,
									wcdmalist[i].realnet_type);
							break;
							
							// psc
							case 20:
									// PSC
								var ij = $.inArray(wcdmalist[i].psc.toString(), psc_xlist);
									if (ij < 20) {
										addMarker(dc[ij], wcdmalist[i].id, 2,
												new BMap.Point(wcdmalist[i].lng_modi + t
														* (0.01 / zoom), wcdmalist[i].lat_modi - t
														* (0.01 / zoom)), i, "PSC",
												new BMap.Point(wcdmalist[i].lng + t
														* (0.01 / zoom), wcdmalist[i].lat - t
														* (0.01 / zoom)), wcdmalist[i].psc,
												wcdmalist[i].realnet_type);
									}
								break;
					default:
						break;
					}
				}
			
					for ( var i = 0; i < gsmlist.length; i++) {
						switch (parseInt(str[t])) {
						case 6:
							addMarker(gsmlist[i].rxlev, gsmlist[i].id, 1,
									new BMap.Point(gsmlist[i].lng_modi + t
											* (0.01 / zoom), gsmlist[i].lat_modi - t
											* (0.01 / zoom)), i, "Rxlev",
									new BMap.Point(gsmlist[i].lng + t
											* (0.01 / zoom), gsmlist[i].lat - t
											* (0.01 / zoom)), gsmlist[i].rxlev_,
									gsmlist[i].realnet_type);
						break;
						// rxqual
						case 7:
								addMarker(gsmlist[i].rxqual, gsmlist[i].id, 1,
										new BMap.Point(gsmlist[i].lng_modi + t
												* (0.01 / zoom), gsmlist[i].lat_modi - t
												* (0.01 / zoom)), i, "Rxqual",
										new BMap.Point(gsmlist[i].lng + t
												* (0.01 / zoom), gsmlist[i].lat - t
												* (0.01 / zoom)), gsmlist[i].rxqual_,
										gsmlist[i].realnet_type);
							break;
						// ci
						case 8:
								addMarker(gsmlist[i].ci, gsmlist[i].id, 1,
										new BMap.Point(gsmlist[i].lng_modi + t
												* (0.01 / zoom), gsmlist[i].lat_modi - t
												* (0.01 / zoom)), i, "C/I",
										new BMap.Point(gsmlist[i].lng + t
												* (0.01 / zoom), gsmlist[i].lat - t
												* (0.01 / zoom)), gsmlist[i].ci_,
										gsmlist[i].realnet_type);
							break;
						// mos
						case 9:
								addMarker(gsmlist[i].mos, gsmlist[i].id, 1,
										new BMap.Point(gsmlist[i].lng_modi + t
												* (0.01 / zoom), gsmlist[i].lat_modi - t
												* (0.01 / zoom)), i, "MOS",
										new BMap.Point(gsmlist[i].lng + t
												* (0.01 / zoom), gsmlist[i].lat - t
												* (0.01 / zoom)), gsmlist[i].mos_,
										gsmlist[i].realnet_type);
							break;
							case 21:
											var ij = $.inArray(gsmlist[i].bcch.toString(), bcch_xlist);
											if (ij < 20) {
												addMarker(dc[ij], gsmlist[i].id, 1, new BMap.Point(
														gsmlist[i].lng_modi + t * (0.01 / zoom),
														gsmlist[i].lat_modi - t * (0.01 / zoom)), i,
														"BCCH", new BMap.Point(gsmlist[i].lng + t
																* (0.01 / zoom), gsmlist[i].lat - t
																* (0.01 / zoom)), gsmlist[i].bcch,
														gsmlist[i].realnet_type);
											}
										break;
							default:
								break;
						}
				}
				
						for ( var i = 0; i < ltelist.length; i++) {
							switch (parseInt(str[t])) {
							case 71:
								addMarker(ltelist[i].ftpSpeed, ltelist[i].id, 3,
										new BMap.Point(ltelist[i].lng_modi + t
												* (0.01 / zoom), ltelist[i].lat_modi - t
												* (0.01 / zoom)), i, "FTP4G",
										new BMap.Point(ltelist[i].lng + t
												* (0.01 / zoom), ltelist[i].lat - t
												* (0.01 / zoom)), ltelist[i].ftpSpeed_,
												ltelist[i].realnet_type);
							break;
							case 23:
											addMarker(ltelist[i].rsrp, ltelist[i].id, 3,
													new BMap.Point(ltelist[i].lng_modi + t
															* (0.01 / zoom), ltelist[i].lat_modi - t
															* (0.01 / zoom)), i, "RSRP",
													new BMap.Point(ltelist[i].lng + t
															* (0.01 / zoom), ltelist[i].lat - t
															* (0.01 / zoom)), ltelist[i].rsrp_,
															ltelist[i].realnet_type);
										break;
									case 24:
											addMarker(ltelist[i].rsrq, ltelist[i].id, 3,
													new BMap.Point(ltelist[i].lng_modi + t
															* (0.01 / zoom), ltelist[i].lat_modi - t
															* (0.01 / zoom)), i, "RSRQ",
													new BMap.Point(ltelist[i].lng + t
															* (0.01 / zoom), ltelist[i].lat - t
															* (0.01 / zoom)), ltelist[i].rsrq_,
															ltelist[i].realnet_type);
										break;
									case 25:
											addMarker(ltelist[i].snr, ltelist[i].id, 3,
													new BMap.Point(ltelist[i].lng_modi + t
															* (0.01 / zoom), ltelist[i].lat_modi - t
															* (0.01 / zoom)), i, "SINR",
													new BMap.Point(ltelist[i].lng + t
															* (0.01 / zoom), ltelist[i].lat - t
															* (0.01 / zoom)), ltelist[i].snr_,
															ltelist[i].realnet_type);
										break;
									case 26:
											var ij = $.inArray(ltelist[i].pci.toString(), pci_xlist);
											if (ij < 20) {
												addMarker(dc[ij], ltelist[i].id, 3, new BMap.Point(
														ltelist[i].lng_modi + t * (0.01 / zoom),
														ltelist[i].lat_modi - t * (0.01 / zoom)), i,
														"PCI", new BMap.Point(ltelist[i].lng + t
																* (0.01 / zoom), ltelist[i].lat - t
																* (0.01 / zoom)), ltelist[i].pci,
																ltelist[i].realnet_type);
										}
										break;
									default:
										break;
							}
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
var markerArr_3g = [], markerArr_2g = [], markerArr_4g = [],marker_first = [];// 封装2g、3g点数据数组
function addMarker(state, id, type, location_, index, kpi, exlocation, intevl,
		realnetType) {
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
	else if (type == 3) {
		// 4G图标
		path = 'l_';
		strokeWeight = 1;
	}
	// psc与bcch用的颜色不一样
	if (kpi != 'PSC' && kpi != 'BCCH'&& kpi != 'PCI') {
		if (state > 0) {
			var fil;
			if (realnetType != -1) {
				path += colorlist[state - 1].colourcode.substring(1,colorlist[state - 1].colourcode.length);
			} else {
				path="linxin";
				intevl="";
			}
			icon =new BMap.Icon(contextPath+'/images/integration/'+path+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
		  
			marker = new BMap.Marker(location_,{
				title : kpi + intevl,
				icon:icon});
		}
	} else {
		if (realnetType == -1) {
			path="linxin";
			intevl="";
			state=",";
		}
		path=path+state.substring(1,state.length);
		icon =new BMap.Icon(contextPath+'/images/integration/'+path+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
		marker = new BMap.Marker(
				location_,{
			title : kpi + intevl,
			icon:icon});
	}

	if (marker) {

		//把各自的点装入数组中
		if (type == 1) {
			markerArr_2g.push(marker);
			exlatAndlngMap_2g.put(marker, exlocation);
			latAndlngMap_2g.put(marker, location_);
		} else if (type == 2){
			markerArr_3g.push(marker);
			exlatAndlngMap_3g.put(marker, exlocation);
			latAndlngMap_3g.put(marker, location_);
		}else if (type == 3){
			markerArr_4g.push(marker);
			exlatAndlngMap_4g.put(marker, exlocation);
			latAndlngMap_4g.put(marker, location_);
		}
		//如果是第一个跳动
		if (first_type == type && first_id == id) {
			if (kpi != 'PSC' && kpi != 'BCCH'&& kpi != 'PCI')
				state = fil;
			 var label = new BMap.Label("起点",{offset:new BMap.Size(10,-10)});
			 marker.setLabel(label);
			clickPoint(type, id, realnetType);
		}
		if (last_type == type && last_id == id) {
			 var label = new BMap.Label("终点",{offset:new BMap.Size(10,-10)});
			 marker.setLabel(label);
		}
		map.addOverlay(marker);
		//点击事件
		marker.addEventListener('click', function() {
			clickPoint(type, id, realnetType);		
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


