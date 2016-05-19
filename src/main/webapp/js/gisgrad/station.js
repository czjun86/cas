var stationMap = [];//生成的基站图层数据
var changeCells = [];//领区数据
var stationInitData = [];//查询区域内的数据
var span_map_psc_g3=[];//G3PSC标注
var p_map_jizhan_g3=[];//G3基站名称标注
var p_map_jizhan_g2=[];//G2基站名称标注
var span_map_psc_g2=[];//G2PSC标注
var imgheight=[12,19,25,32,38,45];
//var click_cell = "";
/**
 * 画小区图
 * @param cells
 * @param map
 */
function drawStation(cells, map) {
	var s = null;
	if (cells.length >= 1) {
		var xxbhMap={};
		for ( var j = 0; j < cells.length; j++) {
			//根据经纬度分组
			if (xxbhMap[cells[j].celllat+"_"+cells[j].celllng] == undefined) {
				var list = [];
				list.push(cells[j]);
				xxbhMap[cells[j].celllat+"_"+cells[j].celllng] = list;
			} else {
				xxbhMap[cells[j].celllat+"_"+cells[j].celllng].push(cells[j]);
			}
		}
		for (ss in xxbhMap) {
			var jjzhan_modetype=[];
			var html = '';
			var st = xxbhMap[ss];	
			var bhMap={};
			for(var i=0;i<st.length;i++){
				if(st[i].indoor == 1){
					st[i].azimuth = st[i].self_azimuth;
				}else{
					if(sctype == '0'){
						st[i].azimuth = st[i].self_azimuth;
					}else{
						st[i].azimuth = st[i].ant_azimuth;
					}
				}
				//根据方位角分组
				if (bhMap[st[i].azimuth] == undefined) {
					var list = [];
					list.push(st[i]);
					bhMap[st[i].azimuth] = list;
				} else {
					bhMap[st[i].azimuth].push(st[i]);
				}
			}
			for(bk in bhMap){
				var bb=bhMap[bk];
				var count = 1;
				var C_count=1;
				var modetypes_psc=[];
				for(var k=0;k<bb.length;k++){
					modetypes_psc.push(bb[k].modetype);
					jjzhan_modetype.push(bb[k].modetype);
					//计算放入PSC
					if(sctype==1){
						var radian = (bb[k].azimuth-90) * Math.PI / 180; 
						var xMargin = Math.cos(radian) * imgheight[k];
						var yMargin = Math.sin(radian) * imgheight[k];
						var xx="",yy="";
						if(xMargin>0)
						xx="left:"+xMargin*2+"px;";
						if(xMargin<0)
						xx="right:"+Math.abs(xMargin)*2+"px;";
						if(bb[k].azimuth==270||bb[k].azimuth==180){
							yy="top:"+(10*k+yMargin*2)+"px;";
						}else{
							yy="top:"+yMargin*2+"px;";
						}
						var s_html='<span  class="img_span" style="'+xx+yy+'">'+bb[k].psc+'</span>';
						var obj_psc=new Object();
						obj_psc.id= bb[k].lac+"_"+bb[k].cellId;
						obj_psc.html=s_html;
						if($.inArray(0, modetypes_psc)>=0&&$.inArray(1, modetypes_psc)>=0)
						{
							obj_psc.net=3;
							if($("#pp_sc").find("span").length>0||$("#bb_ch").find("span").length>0){
								html=html+s_html;
							}
						}else if($.inArray(1, modetypes_psc)>=0){
							obj_psc.net=2;
							if($("#bb_ch").find("span").length>0){
								html=html+s_html;
							}
						}
						else if($.inArray(0, modetypes_psc)>=0){
							obj_psc.net=1;
							if($("#pp_sc").find("span").length>0){
								html=html+s_html;
							}
						}
						span_map_psc.push(obj_psc);
					}
					if(bb[k].indoor == 1){
						
						bb[k].fix=bb[k].fix==undefined?'c_':bb[k].fix;
						html += '<img class="cellimg" azimuth="'+bb[k].azimuth+'" flag="0" id ="img_' + bb[k].lac + '_' + bb[k].cellId
						+ '"  style="z-index:' + (6-C_count)
						+ ';margin-top:-' + (C_count - 1) * 6
						+ 'px;margin-left:-' + (C_count - 1) * 3
						+ 'px;" count="'+C_count+'" type="1" src="' + contextPath
						+ '/images/cell/'+bb[k].fix+C_count+'.png"/>';
						if(C_count ==6){
							break;
						}
						C_count++;
						
					}else{
						bb[k].fix=bb[k].fix==undefined?'sx':bb[k].fix;
						html += '<img class="cellimg" azimuth="'+bb[k].azimuth+'" flag="0" id ="img_' + bb[k].lac + '_' + bb[k].cellId
						+ '"  style="z-index:' + (6-count)
						+ ';margin-top:-' + (count - 1) * 7
						+ 'px;margin-left:-' + (count - 1) * 3
						+ 'px;" count="'+count+'" type="0" src="' + contextPath
						+ '/images/cell/'+bb[k].fix+count+'.png"/>';
						if(count == 6){
							break;
						}
						count++;
					}
					if(sctype==0){
						var radian = (bb[0].azimuth-90) * Math.PI / 180; 
						var xMargin = Math.cos(radian) * imgheight[0];
						var yMargin = Math.sin(radian) * imgheight[0];
						var xx="",yy="";
						if(xMargin>0)
						xx="left:"+xMargin*2+"px;";
						if(xMargin<0)
						xx="right:"+Math.abs(xMargin)*2+"px;";
						if(bb[0].azimuth==270||bb[0].azimuth==180){
							yy="top:"+(10*0+yMargin*2)+"px;";
						}else{
							yy="top:"+yMargin*2+"px;";
						}
						var s_html='<span  class="img_span" style="'+xx+yy+'">'+bb[0].psc+'</span>';
						var obj_psc=new Object();
						obj_psc.id= bb[0].lac+"_"+bb[0].cellId;
						obj_psc.html=s_html;
						if($.inArray(0, modetypes_psc)>=0&&$.inArray(1, modetypes_psc)>=0)
						{
							obj_psc.net=3;
							if($("#pp_sc").find("span").length>0||$("#bb_ch").find("span").length>0){
								html=html+s_html;
							}
						}else if($.inArray(1, modetypes_psc)>=0){
							obj_psc.net=2;
							if($("#bb_ch").find("span").length>0){
								html=html+s_html;
							}
						}
						else if($.inArray(0, modetypes_psc)>=0){
							obj_psc.net=1;
							if($("#pp_sc").find("span").length>0){
								html=html+s_html;
							}
						}
						span_map_psc.push(obj_psc);
					}
					
				}
			}
			var start_dex=0;
			 for(var i = 0;i < st[0].baseName.length;i++){
				 if(st[0].baseName.charCodeAt(i) > 128){
					 start_dex=i;
					 break;
				 }
			 }
			if (html != '') {
				var jizhan_html='<span  class="img_p">'+st[0].baseName.substring(start_dex,st[0].baseName.length)+'</span>';
				var obj_jizhan=new Object();
				obj_jizhan.id= st[st.length-1].lac+"_"+st[st.length-1].cellId;
				obj_jizhan.html=jizhan_html;
				if($.inArray(0, jjzhan_modetype)>=0&&$.inArray(1, jjzhan_modetype)>=0)
				{
					obj_jizhan.net=3;
					if($("#jizhan_g3").find("span").length>0||$("#jizhan_g2").find("span").length>0){
						html=html+jizhan_html;
					}
				}else if($.inArray(1, jjzhan_modetype)>=0){
					obj_jizhan.net=2;
					if($("#jizhan_g2").find("span").length>0){
						html=html+jizhan_html;
					}
				}
				else if($.inArray(0, jjzhan_modetype)>=0){
					obj_jizhan.net=1;
					if($("#jizhan_g3").find("span").length>0){
						html=html+jizhan_html;
					}
				}
				p_map_jizhan.push(obj_jizhan);
				var latLng = new google.maps.LatLng(st[0].celllat, st[0].celllng);
				st[0].isclick=st[0].isclick==undefined?0:st[0].isclick;
				st[0].isdelet=st[0].isdelet==undefined?0:st[0].isdelet;
				s = new stationLayer(st, html, map, latLng,st[0].isclick,st[0].baseName.substring(start_dex,st[0].baseName.length),st[0].isdelet);
				//stationMap.put(st[0].celllng + "_" + st[0].celllng, s);
				stationMap.push(s);
			}
		}
	}
}

/**
 * google map 叠加层
 * @param cells 小区数组
 * @param html 展示的html
 * @param map  map对象
 * @param latLng 叠加层显示的经纬度
 * @param isclick 单击事件是否有效
 * @param labelText 标记
 * @param isdelet 是否删除
 * @returns
 */
function stationLayer(cells, html, map, latLng,isclick,labelText,isdelet) {
	this.map_ = map;
	this.text_ = html;
	this.latLng_ = latLng;
    this.labelText = labelText; 
	this.div_ = null;
	this.data_ = cells;
	this.isdelet_=isdelet;
	this.isclick_ = isclick;
	this.setMap(map);
}

stationLayer.prototype = new google.maps.OverlayView();
stationLayer.prototype.onAdd = function() {
	var div = document.createElement('P');
	div.setAttribute('class', 'test');
	div.style.border = 'none';
	div.style.position = 'absolute';
	$(div).html(this.text_);
	this.div_ = div;
//	var label = document.createElement('p');//创建文字标签  
//	label.innerHTML = this.labelText;  
//	label.style.position = 'absolute';  
//	label.style.width = '200px';  
//	div.appendChild(label);     
	var panes = this.getPanes();
	panes.overlayImage.appendChild(div);
	// $(div).parent().parent().css('z-index', 999);
	rotateStation(this.data_, this.map_,this.isclick_);
};
var overlayProjection;
stationLayer.prototype.draw = function() {
	overlayProjection = this.getProjection();
	var latLng = overlayProjection.fromLatLngToDivPixel(this.latLng_);
	var div = this.div_;
	var ww = $(div).find('img').width() / 2;
	var hh = $(div).find('img').height();
	var size = new google.maps.Size(ww, hh);
	div.style.left = (latLng.x - size.width) + 'px';
	div.style.top = (latLng.y - size.height) + 'px';
};
stationLayer.prototype.onRemove = function() {
	if(this.div_.parentNode != null){
		this.div_.parentNode.removeChild(this.div_);
	}
	this.div_ = null;
};
stationLayer.prototype.toggleDOM = function() {
	if (this.getMap()) {
		this.setMap(null);
	} else {
		this.setMap(this.map_);
	}
};

/**
 * ie8
 * @param _data
 * @param _map
 * @param _isclick
 */
function rotateStationByIE(_data, _map,_isclick){
	var len = _data.length;
	for(var i=0;i<len;i++){
		if( _data[i].indoor==1)
		_data[i].azimuth=0;
		$("#img_" + _data[i].lac + "_" + _data[i].cellId).die().rotate({
			angle : _data[i].azimuth,
			center : [ "50%", "100%" ],
			map : _map,
			isclick : _isclick
		});
	}
}
					
/**
 *  绑定小区单击事件和图片旋转
 * @param _data
 * @param _map
 * @param _isclick
 */
function rotateStation(_data, _map,_isclick) {
	if(commUtils.browser()=="ie" && commUtils.ieVersion() <= 8){
		rotateStationByIE(_data, _map,_isclick);
	}else{
		var len = _data.length;
		if(commUtils.browser()=="ie"){
			for(var i=0;i<len;i++){
				if( _data[i].indoor==1)
					_data[i].azimuth=0;
				var t;
				$("#img_" + _data[i].lac + "_" + _data[i].cellId).unbind().rotate({
					bind : {
						click : function() {
							if(_isclick!=1){
								$("#export_near").css('color','');
								//清空上次点击的小区邻区颜色
								var id = $(this).attr('id');
//								if(click_cell != id){
									clicktrunk = id
									cleanCellImg();
									changeCells = [];
									var ccsObj = {};
									var fix = 'red_';
									var type = $(this).attr('type');
									//当前点击小区设为默认颜色
									var src=contextPath+'/images/cell/red_'+$(this).attr('count')+'.png';
									//判断是否是时分
									if(type==1){
										src=contextPath+'/images/cell/c_red_'+$(this).attr('count')+'.png';
										fix = 'c_red_';
									}
									ccsObj["fix"] = fix;
									ccsObj["img_id"] = "#"+id;
									changeCells.push(ccsObj);
									$(this).css('margin-left',($(this).attr('count')-1)*-5);
									$(this).attr('src',src);
									//显示邻区关系
									showNearInfo(id,_map);
//								}
							}
						},
						mouseover:function(){
							//if(_isclick!=1){
							var id = $(this).attr('id');
							t=setTimeout(function(){showcells(id,_map,$(this))},1000);
							//}
						},mouseout:function(){
							//if(_isclick!=1)
							clearTimeout(t);
						}
					},
					angle : _data[i].azimuth,
					center : [ "50%", "100%" ]
				});
			}
		}else{
			for(var i=0;i<len;i++){
				if( _data[i].indoor==1)
					_data[i].azimuth=0;
				var t;
				$("#img_" + _data[i].lac + "_" + _data[i].cellId).unbind().rotate({
					bind : {
						mouseup : function() {
							if(_isclick!=1){
								$("#export_near").css('color','');
								//清空上次点击的小区邻区颜色
								var id = $(this).attr('id');
//								if(click_cell != id){
									clicktrunk = id
									cleanCellImg();
									changeCells = [];
									var ccsObj = {};
									var fix = 'red_';
									var type = $(this).attr('type');
									//当前点击小区设为默认颜色
									var src=contextPath+'/images/cell/red_'+$(this).attr('count')+'.png';
									//判断是否是时分
									if(type==1){
										src=contextPath+'/images/cell/c_red_'+$(this).attr('count')+'.png';
										fix = 'c_red_';
									}
									ccsObj["fix"] = fix;
									ccsObj["img_id"] = "#"+id;
									changeCells.push(ccsObj);
									$(this).css('margin-left',($(this).attr('count')-1)*-5);
									$(this).attr('src',src);
									//显示邻区关系
									showNearInfo(id,_map);
//								}
							}
						},
						mouseover:function(){
							//if(_isclick!=1){
							var id = $(this).attr('id');
							t=setTimeout(function(){showcells(id,_map,$(this))},1000);
							//}
						},mouseout:function(){
							//if(_isclick!=1)
							clearTimeout(t);
						}
					},
					angle : _data[i].azimuth,
					center : [ "50%", "100%" ]
				});
			}
		}
	}
}
// 小区连线方法
/*******************************************************************************
 * markerLatLng 点位置 cells 连接多个小区
/**
 * 根据CID、LAC查询小区的信息
 * @param cid
 * @param lac
 */
var _infowindow =new google.maps.InfoWindow({disableAutoPan:false});
function showcells(id,map,obj){
	if(_infowindow)
	_infowindow.close();
	 $.post(contextPath + "/gisgrad/showContent",
				{lac:id.split("_")[1],
		         cid:id.split("_")[2]},
				function(data){
					cell=data.cell;
					cell.sctype=sctype;
					var html="";
					if(cell.modetype==0){
						 html = new EJS({url: contextPath+'/js/gisgrad/cell_info.ejs'}).render(cell);
					}else if(cell.modetype==1){
						html = new EJS({url: contextPath+'/js/gisgrad/cell_info_gsm.ejs'}).render(cell);
					}
					_infowindow.setContent(html);
					var jinwei=new google.maps.LatLng(cell.celllat,cell.celllng);
					_infowindow.setPosition(jinwei);
					_infowindow.open(map);
				});
}

//小区连线方法
/***
 * markerLatLng 点位置
 * cells 连接多个小区
 */
function countLatLng(cells, markerLatLng) {
	var line=[];//线路径
	if(overlayProjection){
	polyline.setMap(null);
	for(var j=0;j<cells.length;j++){
		var cc=cells[j];
		var obj=$("#img_"+cc.lac+"_"+cc.cellId);
//		if(obj.length>0){
			line.push(getlanlngBypx(cc,obj));
			line.push(markerLatLng);
		//}
	}
	}
	return line;
}


function getlanlngBypx(cc,obj){
	if(sctype == 0){
		cc.azimuth = cc.self_azimuth;
	}else{
		cc.azimuth = cc.ant_azimuth;
	}
	if(obj.attr('type') != 0){
		cc.azimuth=0;
	}
	var dis=obj.height()-1;
	var latLng= new google.maps.LatLng(cc.celllat,cc.celllng);
	var pp = overlayProjection.fromLatLngToDivPixel(latLng);
	var radian = (cc.azimuth-90) * Math.PI / 180; 
	var xMargin = Math.cos(radian) * dis;
	var yMargin = Math.sin(radian) * dis;
	//扇叶边缘经纬度
	var ln=overlayProjection.fromDivPixelToLatLng(new google.maps.Point(pp.x + xMargin,pp.y + yMargin));
	return ln;
}

/***
 * 显示邻区
 * @param id
 * @param _map
 */
function showNearInfo(id,_map){
	var lac_cid = id.substring(id.indexOf('_')+1,id.length);
	//var cid = id.substring(id.lastIndexOf('_')+1,id.length);
	var areas =  $("#stationareaids").val();
	var nettype = $("#cellnettype").val();
	var indoor = $("#cellindoor").val();
    var cellBands = $("#cellcellbands").val();
	$.ajax({
		type : 'get',
		url : contextPath + '/gisgrad/getnearcell',
		data : {
			lac_cid : lac_cid,
			areas : areas,
			modetypes : nettype,
			indoor : indoor,
			cellBands : cellBands
		},
		async:false,
		success : function(data) {
			var thisCell = data.tcc;
			var list = data.list;
			var obj = new Object();
			var bids = [];
			var len = list.length;
			var cells = [];
			// 首先判断该小区是否在地图上画过了
			for ( var i = 0; i < len; i++) {
				var cell = list[i];
				var img_id = "#img_" + cell.lac + "_" + cell.cellId;
				var _this = $(img_id);
				var src = contextPath + "/images/cell/";
				var fix = "";
				var ccsObj = {};
				if(cell.indoor == 1){
					fix+='c_';
				}else{
					fix+='';
				}
				//判断邻区关系1、同频2、异频3、异网络
				if (cell.nearrel == 1) {
					if($("#tp").find("span").length>0){
						fix += 'green_';
					}else{
						continue;
					}
				} else if (cell.nearrel == 2) {
					if($("#yp").find("span").length>0){
						fix += 'blue_';
					}else{
						continue;
					}
				} else if (cell.nearrel == 3) {
					if($("#ywl").find("span").length>0){
						fix += 'yellow_';
					}else{
						continue;
					}
				}
				//邻区关系 1、正向 2、反向 3、双向
				if (cell.neartype == 1) {
					fix += 'left_';
					
				} else if (cell.neartype == 2) {
					fix += 'right_';
				}
			    //判断邻区是否已经画出来
				if (_this.length == 0) {
					cell.fix=fix;
					cell.isclick=1;
					//cell.isdelet = 1;
					cells.push(cell);
					ccsObj["img_id"] = img_id;
					ccsObj["fix"] = fix;
					changeCells.push(ccsObj);
				}else{
					_this.attr('src', src + fix + _this.attr('count') + '.png');
					ccsObj["img_id"] = img_id;
					ccsObj["fix"] =  fix;
					changeCells.push(ccsObj);
				}
			}
			groupBybid(cells,_map);
		}
	});
}
/**
 * 根据基站ID分组再进行画
 * @param cells
 * @param _map
 */
function groupBybid(cells,_map){
	if(cells.length>0){
		var bidMap={};
		for ( var j = 0; j < cells.length; j++) {
			if (bidMap[cells[j].bid] == undefined) {
				var list = [];
				list.push(cells[j]);
				bidMap[cells[j].bid] = list;
			} else {
				bidMap[cells[j].bid].push(cells[j]);
			}
		}
		for (ss in bidMap) {
			var st = bidMap[ss];
			drawStation(st, _map);
		}
	}
}
/**
 *  清除上次点击的小区换的颜色
 */
function cleanCellImg() {
	for ( var i = 0; i < changeCells.length; i++) {
		var img = $(changeCells[i].img_id);
		var count = img.attr('count');
		$(img).css('margin-left',(count-1)*-3);
		if(img.attr('type') == 0){
			img.attr('src', contextPath + '/images/cell/sx' + count + ".png");
		}else{
			img.attr('src', contextPath + '/images/cell/c_' + count + ".png");
		}
	}
}
/**
 * 删除所有基站信息
 */
function removeStationMap(){
	for ( var i = 0; i < stationMap.length; i++) {
		stationMap[i].setMap(null);
	}
	stationMap = [];
	if(polyline){
		polyline.setMap(null);
		markerLatLng_bit=null;
	}
	//取消小区弹出消息
	if(_infowindow)
		_infowindow.close();
}
/**
 * 筛选当前可视区域的基站信息
 * @param map
 * @returns {Array}
 */
function getBoundsStation(map){
	//获取当前屏幕最大最小经纬度
	var latLngBounds = map.getBounds();
	var latLngNE = latLngBounds.getNorthEast();
	var latLngSW = latLngBounds.getSouthWest();
	var latNE = latLngNE.lat();
	var lngNE = latLngNE.lng();
	var latSW = latLngSW.lat();
	var lngSW = latLngSW.lng();
	var objArr = [];
	var lat,lng;
	var bslist = null;
	//组装数据
	for(var i = 0;i < stationInitData.length; i++){
		bslist = stationInitData[i].bsList;
		var obj = new Object();
		obj["bsList"] = [];
		var bid = stationInitData[i].bid;
		var bslists = [];
		for(var j = 0; j < bslist.length; j++){
			lat = bslist[j].celllat;
			lng = bslist[j].celllng;
			if(lat >= latSW && lng >= lngSW && lat <= latNE && lng <= lngNE){
				bslists.push(bslist[j]);
			}
		}
		if(bslists.length > 0){
			obj["bsList"] = bslists;
			obj["bid"] = bid ;
			objArr.push(obj);
		}
		
	}
	
	return objArr;
}
/**`
 * 是否显示小区PSC标注
 */
function isShowPsc(){
	if($("#pp_sc").find("span").length==1){
		for(var i=0;i<span_map_psc.length;i++){
			var obj_psc=span_map_psc[i];
			if((obj_psc.net==1||obj_psc.net==3)&&$("#img_"+obj_psc.id).prev(".img_span").length==0){
				$("#img_"+obj_psc.id).before(obj_psc.html);
			}
		}
	}else{
		for(var i=0;i<span_map_psc.length;i++){
			var obj_psc=span_map_psc[i];
			if(obj_psc.net==1){
				$("#img_"+obj_psc.id).prev(".img_span").remove();
			}
		}
	}
	if($("#bb_ch").find("span").length==1){
		for(var i=0;i<span_map_psc.length;i++){
			var obj_psc=span_map_psc[i];
			if((obj_psc.net==2||obj_psc.net==3)&&$("#img_"+obj_psc.id).prev(".img_span").length==0){
				$("#img_"+obj_psc.id).before(obj_psc.html);
			}
		}
	}else{
		for(var i=0;i<span_map_psc.length;i++){
			var obj_psc=span_map_psc[i];
			if(obj_psc.net==2){
				$("#img_"+obj_psc.id).prev(".img_span").remove();
			}
		}
	}
	if($("#bb_ch").find("span").length==0&&$("#pp_sc").find("span").length==0){
		$(".img_span").remove();
	}
}
/**`
 * 是否显示基站名称标注
 */
function isShowJizhan(){
	if($("#jizhan_g3").find("span").length==1){
		for(var i=0;i<p_map_jizhan.length;i++){
			var obj_jizhan=p_map_jizhan[i];
			if((obj_jizhan.net==1||obj_jizhan.net==3)&&$("#img_"+obj_jizhan.id).next(".img_p").length==0){
				$("#img_"+obj_jizhan.id).after(obj_jizhan.html);
			}
		}
	}else{
		for(var i=0;i<p_map_jizhan.length;i++){
			var obj_jizhan=p_map_jizhan[i];
			if(obj_jizhan.net==1){
				$("#img_"+obj_jizhan.id).next(".img_p").remove();
			}
		}
	}
	if($("#jizhan_g2").find("span").length==1){
		for(var i=0;i<p_map_jizhan.length;i++){
			var obj_jizhan=p_map_jizhan[i];
			if((obj_jizhan.net==2||obj_jizhan.net==3)&&$("#img_"+obj_jizhan.id).next(".img_p").length==0){
				$("#img_"+obj_jizhan.id).after(obj_jizhan.html);
			}
		}
	}else{
		for(var i=0;i<p_map_jizhan.length;i++){
			var obj_jizhan=p_map_jizhan[i];
			if(obj_jizhan.net==2){
				$("#img_"+obj_jizhan.id).next(".img_p").remove();
			}
		}
	}
	if($("#jizhan_g3").find("span").length==0&&$("#jizhan_g2").find("span").length==0){
		$(".img_p").remove();
	}
}


var cacheCells = [];
var clicktrunk = "";
var clearTimeoutId;

function eventHandler(_this,_isclick){
	$(_this).bind({
		click:function(){
			if(_isclick != 1){
				$("#export_near").attr('color','');
				var group = _this.parentNode,
				span = group.parentNode,
				img = $("#"+span.getAttribute("myid")),
				id = img.attr("id"),
				count = img.attr('count'),
				azimuth = img.attr("azimuth"),
				isclick = img.attr("isclick"),
				type = img.attr("type");
//				if(click_cell != id){
					clicktrunk = id;
					clearCells();
					cacheCells.length = 0;
					var src = contextPath+'/images/cell/red_'+count+'.png';
					if(type==1){
						src=contextPath+'/images/cell/c_red_'+count+'.png';
					}
					img.attr("src",src);
					$(span).empty().remove();
					nearHandler(id);
					img.rotate({
						angle : type==1?0:azimuth-360,
						center : [ "50%", "100%" ],
						isclick : isclick});
					cacheCells.push(id);
//				}
			}
			
		},
		mouseover:function(){
			//if(_isclick != 1){
				var group = _this.parentNode,
			    span = group.parentNode,
			    img = $("#"+span.getAttribute("myid")),
			    id = img.attr("id");
				clearTimeoutId=setTimeout(function(){showcells(id,_map,$(_this));},1000);
			//}
		},
		mouseout:function(){
			//if(_isclick != 1){
				if(clearTimeoutId)
					clearTimeout(clearTimeoutId);
			//}
		}
	});
}
/**
 * clear near
 */
function clearCells(){
	for(var i=0,len=cacheCells.length; i<len; i++){
		(function(id){
			var img = $("#"+id),
			    azimuth = img.attr("azimuth"),
			    count = img.attr('count'),
			    type = img.attr("type"),
			    isclick = img.attr("isclick"),
			    src = contextPath+'/images/cell/sx'+count+'.png';
			if(type == 1){
				src = contextPath+'/images/cell/c_'+count+'.png';
			}
			img.attr("src",src); 
			$("#"+id+"_span").empty().remove();
			img.rotate({
				angle : type==1?0:azimuth-360,
				center : [ "50%", "100%" ],
				isclick : isclick});
		})(cacheCells[i]);
	}
}
	
/**
 * near handler
 */
function nearHandler(id){
	var lac_cid = id.substring(id.indexOf('_')+1,id.length);
	//var cid = id.substring(id.lastIndexOf('_')+1,id.length);
	var areas =  $("#stationareaids").val();
	var nettype = $("#cellnettype").val();
	var indoor = $("#cellindoor").val();
    var cellBands = $("#cellcellbands").val();
	$.ajax({
		type : 'get',
		url : contextPath + '/gisgrad/getnearcell',
		data : {
			lac_cid : lac_cid,
			areas : areas,
			modetypes : nettype,
			indoor : indoor,
			cellBands : cellBands
		},
		async:false,
		success : function(data) {
			var list = data.list;
			var len = list.length;
			var cells = [];
			// 首先判断该小区是否在地图上画过了
			for ( var i = 0; i < len; i++) {
				var cell = list[i];
				var img_id = "img_" + cell.lac + "_" + cell.cellId;
				var _this = document.getElementById(img_id);
				var src = contextPath + "/images/cell/";
				var fix = "";
				if(cell.indoor == 1){
					fix+='c_';
				}else{
					fix+='';
				}
				//判断邻区关系1、同频2、异频3、异网络
				if (cell.nearrel == 1) {
					if($("#tp").find("span").length>0){
						fix += 'green_';
					}else{
						continue;
					}
				} else if (cell.nearrel == 2) {
					if($("#yp").find("span").length>0){
						fix += 'blue_';
					}else{
						continue;
					}
				} else if (cell.nearrel == 3) {
					if($("#ywl").find("span").length>0){
						fix += 'yellow_';
					}else{
						continue;
					}
				}
				//邻区关系 1、正向 2、反向 3、双向
				if (cell.neartype == 1) {
					fix += 'left_';
					
				} else if (cell.neartype == 2) {
					fix += 'right_';
				}
			    //判断邻区是否已经画出来
				if (!_this) {
					cell.fix=fix;
					cell.isclick=1;
					//cell.isdelet = 1;
					cells.push(cell);
				}else{
					var type = _this.getAttribute('type');
					_this.src = src + fix + _this.getAttribute('count') + '.png';
					$("#"+img_id+"_span").empty().remove();
					$(_this).rotate({
						angle : type==1?0:_this.getAttribute("azimuth")-360,
						center : [ "50%", "100%" ],
						isclick : _this.getAttribute("isclick")});
				}
				cacheCells.push(img_id);
			}
			if(cells.length>0){
				drawStation(cells, _map);
			}
		}
	});

}
	