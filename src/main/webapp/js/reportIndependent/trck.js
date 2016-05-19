
/*******************************************************************************
 * 单次测试室内外地图
 * 
 * @author czj
 */

var map;
var colorlist;
var gsmlist, wcdmalist;
var first_id, first_type;// 第一个点ID
var last_id, last_type;// 最后一个点ID
var exlatAndlngMap_3g = new Map();// 封装Markerr 的偏移前经纬度KEY:marker;value:偏移前经纬度
var latAndlngMap_3g = new Map();// 封装Markerr 的偏移后经纬度KEY:marker;value:偏移后经纬度
var exlatAndlngMap_2g = new Map();// 封装Markerr 的偏移前经纬度KEY:marker;value:偏移前经纬度
var latAndlngMap_2g = new Map();// 封装Markerr 的偏移后经纬度KEY:marker;value:偏移后经纬度
var cenetr_lat_lng;// 中心位置
var zNodes = [];
var zTree;
/**
 * 对轨迹点数据进行处理判断室内外类型分别加载地图、图例
 * 并根据数据对各指标是否进行测试判断生成指标的下拉菜单选择器（默认2G-Rxlev、3G-RSCP）并进行事件绑定、
 * 生成叠加数据功能里的树形指标菜单与事件绑定
 * 判断轨迹的第一个点（以时间判断）并默认展示轨迹点详细数据
 * 
 * 
 * @param flowid
 * @param type
 * @param inside
 * @param flist
 */
function queryPoint(flowid, type, inside, flist) {

	var idooorTag=[];
	var psc_xlist, bcch_xlist;
	for ( var t = 0; t < flist.length; t++) {
		if (flist[t].kpiId == 20) {
			psc_xlist = flist[t].x;
		}
		if (flist[t].kpiId == 21) {
			bcch_xlist = flist[t].x;
		}
	}
	$("#demo_div").html("");
	flay = true;
	$(".case_tu p").css({
		'background-image' : 'url(../images/case_tu_s.png)'
	});
	$(".case_zhi").hide();
	$("#d_s_id").show();
	flag = true;

	$
			.ajax({
				type : "post",
				url : contextPath + "/reportIndependent/showPoint",
				data : ({
					flowid : flowid,
					areaid:$("#areaid").val(),
					type : type
				}),
				success : function(data) {
					if (data) {
						gsmlist = data.gsmlist;
						wcdmalist = data.wcdmalist;
						var sum = 0;//全部点
//						无服务点统计
						var gmsnssum = 0;//2g无服务点
						var wcdmanssum = 0;//3g无服务点
						var gmssum = 0;//2g点
						var wcdmasum = 0;//3G点
//						2g,3g,无服务
						var noService = 0;
						var gsmProp =0;
						var wcdmaProp = 0;
						
						
						if (gsmlist) {
							sum += gsmlist.length;
							gmssum = gsmlist.length;
//							统计自由模式2G无服务点
								for(var i = 0;i < gsmlist.length;i++){
									if(gsmlist[i].realnet_type == -1){
										gmsnssum += 1;
									}
								}
						}
						if (wcdmalist) {
							sum += wcdmalist.length;
							wcdmasum = wcdmalist.length;
//							统计自由模式3G无服务点
								for(var i = 0;i < wcdmalist.length;i++){
									if(wcdmalist[i].realnet_type == -1){
										wcdmanssum += 1;
									}
								}
						}
//						用来去分是否为WCDMA自由模式
						var netType = $(".combo-text").val().split("_");
//						统计自由模式无服务占比
//						统计3g 2g 以及无服务占比，如果采点总数为0让他各项为初始值0
						if(sum!=0){
//							noService
							noService = ((wcdmanssum+gmsnssum)/sum)*100;
//							2G占比
							gsmProp = ((gmssum-gmsnssum)/sum)*100;
//							3G占比
							wcdmaProp = ((wcdmasum-wcdmanssum)/sum)*100;
							
						}
//							判断noService是否为0有没有必要添加
							var noServiceLast = "";
							var gsm_net ="";
							var wcdma_net ="";
							if(noService!=0){
									noServiceLast = NumOptimize(noService.toFixed(2))+"%";
							}else{
								noServiceLast = "0%";
							}
							
							if(gsmProp.toFixed(2)!=0){
									gsm_net = NumOptimize(gsmProp.toFixed(2))+"%";
							}else{
								gsm_net = "0%";
							}
							
							if(wcdmaProp.toFixed(2)!=0){
									wcdma_net = NumOptimize(wcdmaProp.toFixed(2))+"%";
							}else{
								wcdma_net = "0%";
							}
//						页面添加无服务行
						$("#wcdmaScale").html(wcdma_net);
						$("#gsmScale").html(gsm_net);
						$("#noServiceScale").html(noServiceLast);
						$("#sum_count").html("采样点：" + sum);
						colorlist = data.colorlist;
						center = data.center;
						// 根据数据显示下拉

						// 3G下拉 zNodes指标下拉列表里去掉id为1、2的，其他ID-2
						zNodes = [];
						var child = [];
						$("#s_sel1").hide();
						$("#s_sel2").hide();
						var dd = [], dd_id = [];
						for (i in wcdmalist) {
							if ($.inArray(1, dd_id) < 0
									&& wcdmalist[i].rscp > 0) {
								dd_id.push(1);
								dd.push({
									"text" : 'RSCP',
									"id" : '1',
									"selected" : true
								});
								child.push({
									id : 3,
									text : "RSCP"
								});

							}
							if ($.inArray(2, dd_id) < 0
									&& wcdmalist[i].ecno > 0) {
								dd_id.push(2);
								dd.push({
									"text" : 'EcNo',
									"id" : '2'
								});
								child.push({
									id : 4,
									text : "EcNo"
								});
							}
							if ($.inArray(3, dd_id) < 0
									&& wcdmalist[i].txpower > 0) {
								dd_id.push(3);
								dd.push({
									"text" : 'TXPOWER',
									"id" : '3'
								});
								child.push({
									id : 5,
									text : "TXPOWER"
								});
							}
							if ($.inArray(4, dd_id) < 0
									&& wcdmalist[i].ftpSpeed > 0) {
								dd_id.push(4);
								dd.push({
									"text" : 'FTP',
									"id" : '4'
								});
								child.push({
									id : 6,
									text : "FTP"
								});
							}
							if ($.inArray(20, dd_id) < 0
									&& wcdmalist[i].psc > 0) {
								dd_id.push(20);
								dd.push({
									"text" : 'PSC',
									"id" : '20'
								});
								child.push({
									id : 22,
									text : "PSC"
								});
							}

						}
						if ($("#s_sel2").css("display") == 'none'
								&& dd_id.length > 0) {
							$("#s_sel2").show();
							zNodes.push({
								'id' : 2,
								'text' : "3G",
								'children' : child
							});
							$("#SelectBox2").combobox({
								data : dd,
								valueField : 'id',
								textField : 'text'
							});
						} else {
							$("#SelectBox2").combobox("clear");
						}

						var dd = [], dd_id_1 = [], child = [];
						// 2g下拉
						for (i in gsmlist) {
							if ($.inArray(6, dd_id_1) < 0
									&& gsmlist[i].rxlev > 0) {
								dd_id_1.push(6);
								dd.push({
									"text" : 'RXLEV',
									"id" : '6',
									"selected" : true
								});
								child.push({
									'id' : 8,
									'text' : "RXLEV"
								});
							}
							if ($.inArray(7, dd_id_1) < 0
									&& gsmlist[i].rxqual > 0) {
								dd_id_1.push(7);
								dd.push({
									"text" : 'RXQUAL',
									"id" : '7'
								});
								child.push({
									'id' : 9,
									pId : 1,
									'text' : "RXQUAL"
								});
							}
							if ($.inArray(8, dd_id_1) < 0
									&& gsmlist[i].rxqual > 0) {
								dd_id_1.push(8);
								dd.push({
									"text" : 'C/I',
									"id" : '8'
								});
								child.push({
									'id' : 10,
									'text' : "C/I"
								});
							}
							// if(($("#CI").val()== undefined||$("#CI").val()==
							// 'undefined')&&gsmlist[i].ci>0){
							// var dd='<option value="8" id="CI">CI</option>';
							// $("#SelectBox1").append(dd);
							// zNodes.push({id:10, pId:1, name:"CI"});
							// }
							// if(($("#MOS").val()==
							// undefined||$("#MOS").val()==
							// 'undefined')&&gsmlist[i].mos>0){
							// var dd='<option value="9" id="MOS">MOS</option>';
							// $("#SelectBox1").append(dd);
							// zNodes.push({id:11, pId:1, name:"MOS"});
							// }
							if ($.inArray(21, dd_id_1) < 0
									&& gsmlist[i].bcch > 0) {
								dd_id_1.push(21);
								dd.push({
									"text" : 'BCCH',
									"id" : '21'
								});
								child.push({
									'id' : 23,
									pId : 1,
									'text' : "BCCH"
								});
							}

						}
						if ($("#s_sel1").css("display") == 'none'
								&& dd_id_1.length > 0) {
							$("#s_sel1").show();
							zNodes.push({
								'id' : 1,
								'text' : "2G",
								'children' : child
							});
							$("#SelectBox1").combobox({
								data : dd,
								valueField : 'id',
								textField : 'text'
							});
						} else {
							$("#SelectBox2").combobox("clear");
						}
						$("#forms_data_id").show();
						if (inside == 1) {
							// 室内
							$("#expand").show();
							$("#narrow").show();
							if (map) {
								$("#div_point").html(div_html);
							}
						var aaf=	showIndoorPoint(gsmlist, wcdmalist, colorlist,
									psc_xlist, bcch_xlist,idooorTag);
						
							if (dd_id_1.length > 0) {
								$("#SelectBox1")
										.combobox(
												{
													onSelect : function(n, o) {
														var sel1, sel2;
														if (dd_id_1.length > 0) {
															sel1 = $(
																	"#SelectBox1")
																	.combobox(
																			"getValue");
														}
														if (dd_id.length > 0) {
															sel2 = $(
																	"#SelectBox2")
																	.combobox(
																			"getValue");
														}
														var temp = "";
														var str="";
														if (sel1) {
															if (dd_id_1.length > 0) {
																str = $(
																"#SelectBox1")
																.combobox(
																		"getText");
																temp = str
																		+ ","
																		+ temp;
															}
														}
														if (sel2) {
															if (dd_id.length > 0) {
																str = $(
																"#SelectBox2")
																.combobox(
																		"getText");
																temp = str
																		+ ","
																		+ temp;
															}
														}
														if ($("#demo_div")
																.html()) {
															choose_demo(
																	flowid,
																	flist,
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getText"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getText"),
																	"#demo_div",
																	wcdmalist,
																	gsmlist);
														}
														
														var vs = new Array();
														var vs1 = new Array();
														vs = temp.split(",");
														for (var x=0;x<vs.length;x++){
															if(vs[x].length>0&&$.inArray(vs[x], idooorTag)<0){
																idooorTag.push(vs[x]);
																vs1.push(vs[x]);
															}
														}
														if(vs1.length>0){
															var obj=getindoorName(aaf.dfobj,vs1);
															initData(obj,vs1,aaf.firstId,aaf.firstType,aaf.lastId,aaf.lastType,aaf.wl.wid,aaf.wl.hie,idooorTag);
														}
														display(vs);
													}
												});
							}

							// 下拉列表3G点击事件
							if (dd_id.length > 0) {
								$("#SelectBox2")
										.combobox(
												{
													onSelect : function(n, o) {
														var sel1, sel2;
														if (dd_id_1.length > 0) {
															sel1 = $(
																	"#SelectBox1")
																	.combobox(
																			"getValue");
														}
														if (dd_id.length > 0) {
															sel2 = $(
																	"#SelectBox2")
																	.combobox(
																			"getValue");
														}
														var temp = "";
														var str = "";
														if (sel1) {
															if (dd_id_1.length > 0) {
																str = $(
																"#SelectBox1")
																.combobox(
																		"getText");
																temp = str
																		+ ","
																		+ temp;
															}
														}
														if (sel2) {
															if (dd_id.length > 0) {
																str = $(
																"#SelectBox2")
																.combobox(
																		"getText");
																temp = str
																		+ ","
																		+ temp;
															}
														}
														if ($("#demo_div")
																.html()) {
															choose_demo(
																	flowid,
																	flist,
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getText"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getText"),
																	"#demo_div",
																	wcdmalist,
																	gsmlist);
														}
														var vs = new Array();
														var vs1 = new Array();
														vs = temp.split(",");
														for (var x=0;x<vs.length;x++){
															if($.inArray(vs[x], idooorTag)<0){
																idooorTag.push(vs[x]);
																vs1.push(vs[x]);
															}
														}
														if(vs1.length>0){
															var obj=getindoorName(aaf.dfobj,vs1);
															initData(obj,vs1,aaf.firstId,aaf.firstType,aaf.lastId,aaf.lastType,aaf.wl.wid,aaf.wl.hie,idooorTag);
																												}
														display(vs);
													}
												});
							}
						} else if (inside == 0) {
							// 室外
							$("#expand").hide();
							$("#narrow").hide();
							initMap(wcdmalist, gsmlist, center, psc_xlist,
									bcch_xlist);
							// 下拉列表2G点击事件
							if (dd_id_1.length > 0) {
								$("#SelectBox1")
										.combobox(
												{
													onSelect : function(n, o) {
														chooseKpi(
																wcdmalist,
																gsmlist,
																$("#SelectBox1")
																		.combobox(
																				"getValue"),
																1, psc_xlist,
																bcch_xlist);
														if ($("#demo_div")
																.html()) {
															choose_demo(
																	flowid,
																	flist,
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getText"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getText"),
																	"#demo_div",
																	wcdmalist,
																	gsmlist);
														}
													}
												});
							}

							// 下拉列表3G点击事件
							if (dd_id.length > 0) {
								$("#SelectBox2")
										.combobox(
												{
													onSelect : function(n, o) {
														chooseKpi(
																wcdmalist,
																gsmlist,
																$("#SelectBox2")
																		.combobox(
																				"getValue"),
																2, psc_xlist,
																bcch_xlist);
														if ($("#demo_div")
																.html()) {
															choose_demo(
																	flowid,
																	flist,
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getValue"),
																	$(
																			"#SelectBox1")
																			.combobox(
																					"getText"),
																	$(
																			"#SelectBox2")
																			.combobox(
																					"getText"),
																	"#demo_div",
																	wcdmalist,
																	gsmlist);
														}
													}
												});
							}
						}
						// 图例比例显示点击事件

						$('#img_demo')
								.die()
								.live(
										"click",
										function(e) {
											$(".case_tu")
													.css("background",
															"none repeat scroll 0 0 #fff");
											$(".case_tu").css("border-bottom",
													"1px solid #979797");
											$(".case_tu").css("border-right",
													"1px solid #979797");

											var sel1 = 0, sel2 = 0;
											var sel1_n = "", sel2_n = "";
											if (dd_id_1.length > 0) {
												sel1 = $("#SelectBox1")
														.combobox("getValue");
												sel1_n = $("#SelectBox1")
														.combobox("getText");
											}
											if (dd_id.length > 0) {
												sel2 = $("#SelectBox2")
														.combobox("getValue");
												sel2_n = $("#SelectBox2")
														.combobox("getText");
											}
											if (flay == true) {
												choose_demo(flowid, flist,
														sel1, sel2, sel1_n,
														sel2_n, "#demo_div",
														wcdmalist, gsmlist);
												$(".case_tu p")
														.css(
																{
																	'background-image' : 'url(../images/case_tu.png)'
																});
												flay = false;
											} else {
												$(".case_tu").css("background",
														"");
												$(".case_tu").css(
														"border-bottom", "");
												$(".case_tu").css(
														"border-right", "");
												$("#demo_div").html("");
												flay = true;
												$(".case_tu p")
														.css(
																{
																	'background-image' : 'url(../images/case_tu_s.png)'
																});
											}

										});

						// 轨迹叠加选择
						$('.twoG')
								.click(
										function(e) {
											$(".easyui-linkbutton").show();
											var sel1 = 0, sel2 = 0;

											$("#zhibiao").dialog({
												height : 200,
												width : 380,
												title : "测试指标选择",
												modal : true,
												closed : false
											});
											$("#tul").tree({
												data : zNodes,
												animate : true,
												checkbox : true,
												onlyLeafCheck : true
											});
											if (dd_id_1.length > 0) {
												sel1 = $("#SelectBox1")
														.combobox("getValue");
											}
											if (dd_id.length > 0) {
												sel2 = $("#SelectBox2")
														.combobox("getValue");
											}
											for (i in zNodes) {
												child = zNodes[i].children;
												for (j in child) {
													if (child[j].id == (parseInt(sel1) + 2)) {
														var no = $("#tul")
																.tree(
																		"find",
																		child[j].id);
														$("#tul").tree(
																"remove",
																no.target);
													}
													if (child[j].id == (parseInt(sel2) + 2)) {
														var no = $("#tul")
																.tree(
																		"find",
																		child[j].id);
														$("#tul").tree(
																"remove",
																no.target);
													}
												}
											}
											$('#zhibiao').dialog('open');
											$.parser.parse('#zhibiao');
										});
						// 树形确定事件
						$('.treeb').unbind("click").click(
								function(e) {
									var sel1 = 0, sel2 = 0;
									if (dd_id_1.length > 0) {
										sel1 = $("#SelectBox1").combobox(
												"getValue");
									}
									if (dd_id.length > 0) {
										sel2 = $("#SelectBox2").combobox(
												"getValue");
									}
									var nodes = $('#zhibiao')
											.tree('getChecked');
									var v = "", id_v = "",str = "";
									for ( var i = 0; i < nodes.length; i++) {
										if (v != '') {
											v += ',';
											id_v += ',';
										}
										str = nodes[i].text;
										if (i != nodes.length - 1) {
											v += str + ",";
											id_v += nodes[i].id + ",";
										} else {
											v += str;
											id_v += nodes[i].id;
										}
									}

									if (nodes.length == 0) {
										$.messager.alert("warning",
												"至少选择一个指标！", "warning");
										return;
									}
									var idd = "";
									if (sel2) {
										idd += (parseInt(sel2) + 2) + ",";
									}
									if (sel1) {
										idd += (parseInt(sel1) + 2) + ",";
									}
									idd += id_v;
									$("#undata").val(v);
									$("#undata_id").val(idd);
									if (inside == 1) {
										if (sel1) {
											if (dd_id_1.length > 0) {
												str = $("#SelectBox1").combobox(
												"getText");
												v = str + "," + v;
											}
										}
										if (sel2) {
											if (dd_id.length > 0) {
												str = $("#SelectBox2").combobox(
												"getText");
												v = str + "," + v;
											}
										}
										var vs = new Array();
										var vs1 = new Array();
										vs = v.split(",");
										for (var x=0;x<vs.length;x++){
											if($.inArray(vs[x], idooorTag)<0){
												idooorTag.push(vs[x]);
												vs1.push(vs[x]);
											}
										}
										if(vs1.length>0){
											var obj=getindoorName(aaf.dfobj,vs1);
											initData(obj,vs1,aaf.firstId,aaf.firstType,aaf.lastId,aaf.lastType,aaf.wl.wid,aaf.wl.hie,idooorTag);
										}
										display(vs);
									} else if (inside == 0) {
										outclick(psc_xlist, bcch_xlist);
									}
									$('#zhibiao').dialog('close');
								});
						$("#div_point").show();
						$("#background").hide();
						$("#bar_id").hide();
						$(document).css({
							"overflow" : "auto"
						});
					}
				}
			});
}
// 树型菜单
var setting = {
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onCheck : onCheck
	}
};
function onCheck(e, treeId, treeNode) {
}

/**
 * 生成室内地图并对数据进行处理并调用Raphael相关画图JS方法
 * 对2G、3G数据分别传入
 * 判断第一个点并默认展示RSCP、RXLEV的轨迹
 * @param gsmlist
 * @param wcdmalist
 * @param colorlist
 * @param psc_xlist
 * @param bcch_xlist
 */
function showIndoorPoint(gsmlist, wcdmalist, colorlist, psc_xlist, bcch_xlist,idooorTag) {
	var pscmap = new Map();// 键：PSC值，值：颜色集id
	var bcchmap = new Map();// 键：PSC值，值：颜色集id

	var time_3g = "", time_2g = "", id_3g, id_2g;
	var real_t_3g, real_t_2g;
	
	var time_3g_last = "", time_2g_last = "", id_3g_last, id_2g_last;
	
	var colorall = [];
	var arr_color = [];
	for ( var t = 0; t < colorlist.length; t++) {
		var color = colorlist[t];
		arr_color.push(color.colourcode);
	}
	var psc_arr_color = [];

	for ( var j = arr_color.length - 1; j >= 0; j--) {
		psc_arr_color.push(arr_color[j]);
	}
	var dc = colorall.concat(psc_arr_color, colorRoom);// 两个颜色合并作为PSC颜色用

	// 指标数据
	var rscp = [], ecno = [], TxPower = [], psc = [], ftpSpeed = [];
	// 3G
	for ( var i = 0; i < wcdmalist.length; i++) {
		var wcdma = wcdmalist[i];
		// RSCP
		if (wcdma.rscp != 0) {
			var colorid = wcdma.rscp - 1;
			var obj = {
				x : wcdma.x,
				y : wcdma.y,
				color : arr_color[colorid],
				id : wcdma.id,
				type : '2',
				va : wcdma.rscp_,
				reltype : wcdma.realnet_type
			};
			rscp.push(obj);
		}
		// ECNO
		if (wcdma.ecno != 0) {
			var colorid = wcdma.ecno - 1;
			var obj = {
				x : wcdma.x,
				y : wcdma.y,
				color : arr_color[colorid],
				id : wcdma.id,
				type : '2',
				va : wcdma.ecno_,
				reltype : wcdma.realnet_type
			};
			ecno.push(obj);
		}
		// TxPower
		if (wcdma.txpower != 0) {
			var colorid = wcdma.txpower - 1;
			var obj = {
				x : wcdma.x,
				y : wcdma.y,
				color : arr_color[colorid],
				id : wcdma.id,
				type : '2',
				va : wcdma.txpower_,
				reltype : wcdma.realnet_type
			};
			TxPower.push(obj);
		}
		// FTP
		if (wcdma.ftpSpeed != 0) {
			var colorid = wcdma.ftpSpeed - 1;
			var obj = {
				x : wcdma.x,
				y : wcdma.y,
				color : arr_color[colorid],
				id : wcdma.id,
				type : '2',
				va : wcdma.ftpSpeed_,
				reltype : wcdma.realnet_type
			};
			ftpSpeed.push(obj);
		}
		// PSC
		if (wcdma.psc) {
			var ij = $.inArray(wcdma.psc.toString(), psc_xlist);
			if (ij < 20) {
				var obj = {
					x : wcdma.x,
					y : wcdma.y,
					color : dc[ij],
					id : wcdma.id,
					type : '2',
					va : wcdma.psc,
					reltype : wcdma.realnet_type
				};
				psc.push(obj);
			}
		}

		// 取第一个点
		if (i == 0) {
			time_3g = wcdma.eptime;
			id_3g = wcdma.id;
			real_t_3g = wcdma.realnet_type;
		}
		if (i == wcdmalist.length-1) {
			time_3g_last = wcdma.eptime;
			id_3g_last = wcdma.id;
		}
	}
	// 2G
	var rxlev = [], txpower_2g = [], rxqual = [], bcch = [], ci = [], mos = [];
	for ( var i = 0; i < gsmlist.length; i++) {
		var gsm = gsmlist[i];
		// RxLev
		if (gsm.rxlev > 0) {
			var colorid = gsm.rxlev - 1;
			var obj = {
				x : gsm.x,
				y : gsm.y,
				color : arr_color[colorid],
				id : gsm.id,
				type : '1',
				va : gsm.rxlev_,
				reltype : gsm.realnet_type
			};
			rxlev.push(obj);
		}
		// TxPower
		if (gsm.txpower > 0) {
			var colorid = gsm.txpower - 1;
			var obj = {
				x : gsm.x,
				y : gsm.y,
				color : arr_color[colorid],
				id : gsm.id,
				type : '1',
				va : gsm.txpower_,
				reltype : gsm.realnet_type
			};
			txpower_2g.push(obj);
		}
		// rxqual
		if (gsm.rxqual > 0) {
			var colorid = gsm.rxqual - 1;
			var obj = {
				x : gsm.x,
				y : gsm.y,
				color : arr_color[colorid],
				id : gsm.id,
				type : '1',
				va : gsm.rxqual_,
				reltype : gsm.realnet_type
			};
			rxqual.push(obj);
		}
		// ci
		if (gsm.ci > 0) {
			var colorid = gsm.ci - 1;
			var obj = {
				x : gsm.x,
				y : gsm.y,
				color : arr_color[colorid],
				id : gsm.id,
				type : '1',
				va : gsm.ci_,
				reltype : gsm.realnet_type
			};
			ci.push(obj);
		}
		// mos
		if (gsm.mos > 0) {
			var colorid = gsm.mos - 1;
			var obj = {
				x : gsm.x,
				y : gsm.y,
				color : arr_color[colorid],
				id : gsm.id,
				type : '1',
				va : gsm.mos_,
				reltype : gsm.realnet_type
			};
			mos.push(obj);
		}
		// bcch
		if (gsm.bcch) {
			var ij = $.inArray(gsm.bcch.toString(), bcch_xlist);
			if (ij < 20) {
				var obj = {
					x : gsm.x,
					y : gsm.y,
					color : dc[ij],
					id : gsm.id,
					type : '1',
					va : gsm.bcch,
					reltype : gsm.realnet_type
				};
				bcch.push(obj);
			}
		}
		// 取第一个点
		if (i == 0) {
			time_2g = gsm.eptime;
			id_2g = gsm.id;
			real_t_2g = gsm.realnet_type;
		}
		// 去最后一个点
		if (i == gsmlist.length-1) {
			time_2g_last = gsm.eptime;
			id_2g_last = gsm.id;
		}
	}
	// 第一个比较时间大小,默认显示点
	if (time_3g && time_2g) {

		if (time_3g > time_2g) {
			// 2
			clickPoint(1, id_2g, real_t_2g);
			first_id = id_2g;
			first_type = 1;
		} else {
			clickPoint(2, id_3g, real_t_3g);
			first_id = id_3g;
			first_type = 2;
		}
	} else {
		if (time_3g) {
			clickPoint(2, id_3g, real_t_3g);
			first_id = id_3g;
			first_type = 2;
		}
		if (time_2g) {
			clickPoint(1, id_2g, real_t_2g);
			first_id = id_2g;
			first_type = 1;
		}
	}
	// 最后一个点比较时间大小,默认显示点
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
	var svgData = {
		// 入口的经纬度
		origin : [ 106.54252000, 29.56392000 ],
		station : {}
	};
	// 室内轨迹点数据
	var dd = new Object();
	var dfobj=new Object();
	if (rscp.length > 0) {
		dfobj.RSCP = rscp;
	}
	if (ecno.length > 0) {
		dfobj.EcNo = ecno;
	}
	if (TxPower.length > 0) {
		dfobj.TXPOWER = TxPower;
	}
	if (psc.length > 0) {
		dfobj.PSC = psc;
	}
	if (ftpSpeed.length > 0) {
		dfobj.FTP = ftpSpeed;
	}
	var db = new Object();
	if (rxlev.length > 0) {
		dfobj.RXLEV = rxlev;
	}
	if (rxqual.length > 0) {
		dfobj.RXQUAL = rxqual;
	}
	if (ci.length > 0) {
		dfobj['C/I'] = ci;
	}
	// if(ci.length>0){db.CI=ci;}
	// if(mos.length>0){db.MOS=mos;}
	// if(txpower_2g.length>0){db.TXPOWER=txpower_2g;}
	if (bcch.length > 0) {
		dfobj.BCCH = bcch;
	}
//	var obj = new Object();
//	obj["3G"] = dd;
//	obj["2G"] = db;
	
	var s = new Array();
	s.push("RSCP");
	s.push("RXLEV");
	idooorTag.push("RSCP");
	idooorTag.push("RXLEV");
	var aaf=new Object();
	aaf.firstId=first_id;
	aaf.firstType=first_type;
	aaf.lastId=last_id;
	aaf.lastType=last_type;
	var obj=getindoorName(dfobj,s);
	svgData.datas = obj;
	var wl=initPaper(svgData.datas, s, first_id, first_type,last_id,last_type);
	aaf.wl=wl;
	aaf.dfobj=dfobj;
	return aaf;
}
function getindoorName(datas,vs){
	var newObj=new Object();
	var newObj2=new Object();
	if(vs.length>0){
		for(var i=0;i<vs.length;i++){
			if(vs[i]=='RSCP'||vs[i]=='EcNo'||vs[i]=='TXPOWER'||vs[i]=='PSC'||vs[i]=='FTP'){
			if(datas[vs[i]])newObj[vs[i]]=datas[vs[i]];}
			if(vs[i]=='RXLEV'||vs[i]=='RXQUAL'||vs[i]=='C/I'||vs[i]=='BCCH')
			{if(datas[vs[i]])
			newObj2[vs[i]]=datas[vs[i]];}
		}
	}
	var obj = new Object();
	obj["3G"] = newObj;
	obj["2G"] = newObj2;
	return obj;
}
/**
 * 点击地图中某一轨迹点时查询并展示详细数据
 * 
 * @param id
 *            点ID
 * @param type：2－WCDMA或是1－GSM
 * @realnet_type -1无服务
 */
function clickPoint(type, id, realnet_type) {
	// 点图缩小时列表
	if (realnet_type != -1) {
		$.ajax({
			type : "post",
			url : contextPath + "/reportIndependent/wcdmaOrGsm",
			data : ({
				id : id,
				areaid:$("#areaid").val(),
				type : type
			}),
			success : function(data) {

				$('#div_page').html(data.content);
				// $('#div_page_max').html(data.contentMax);
			}
		});
	} else {
		$('#div_page').html('<div class="noservice">No Service</div>');
	}

	// 点图放大时列表
}


/**
 * 室外多选对指标ID进行字符组装并调用chooseKpi方法
 * @param doc
 */
function outclick(psc_xlist, bcch_xlist) {
	$('.wrapper,#dialData').hide();
	var obj_id = $("#undata_id").attr("value");
	var sid = obj_id.split(",");
	var g3 = "";
	g2 = "";
	for ( var i = 0; i < sid.length; i++) {
		switch (parseInt(sid[i])) {
		case 3:
			g3 += ",1";
			break;
		case 4:
			g3 += ",2";
			break;
		case 5:
			g3 += ",3";
			break;
		case 6:
			g3 += ",4";
			break;
		case 22:
			g3 += ",20";
			break;
		case 8:
			g2 += ",6";
			break;
		case 9:
			g2 += ",7";
			break;
		case 10:
			g2 += ",8";
			break;
		case 11:
			g2 += ",9";
			break;
		case 23:
			g2 += ",21";
			break;

		default:
			break;
		}
	}
	g2 = g2.length > 0 ? g2.substring(1, g2.length) : "";
	g3 = g3.length > 0 ? g3.substring(1, g3.length) : "";
	if (g2 != "") {
		chooseKpi(wcdmalist, gsmlist, g2, 1, psc_xlist, bcch_xlist);
	} else {
		for (i in markerArr_2g) {
			var m = markerArr_2g[i];
			m.setMap(null);
		}
		markerArr_2g = [];
		exlatAndlngMap_2g.clear();
		latAndlngMap_2g.clear();
	}
	if (g3 != "") {
		chooseKpi(wcdmalist, gsmlist, g3, 2, psc_xlist, bcch_xlist);
	} else {
		for (i in markerArr_3g) {
			var m = markerArr_3g[i];
			m.setMap(null);
		}
		markerArr_3g = [];
		exlatAndlngMap_3g.clear();
		latAndlngMap_3g.clear();
	}
}
/**
 * 计算页面宽度并改变页面里的地图与比例图组件宽度与高度
 */
function changeWionw() {
	//计算宽度高度
	var wid_len = (document.body.scrollWidth);
	var hie_len = wid_len * (9 / 16);
	$("#div_left").css("width", wid_len * 0.98);
	$("#div_left").css("height", hie_len);
	$("#div_point").css("width", (wid_len) * 0.98);
	$("#div_point").css("height", hie_len);
	$(".svg-div").css("width", wid_len);
	$(".svg-div").css("height", hie_len);
	$(".svg-div-main").css("width", wid_len);
	$(".svg-div-main").css("height", hie_len);
	$(".svg-main").css("width", wid_len * 3);
	$(".svg-main").css("height", hie_len * 3);
	$(".case_zhi").css("height", hie_len);
	$("#div_czj").css("height", hie_len);
	var wid = (document.body.scrollWidth - document.body.scrollWidth * 0.05) / 2;
	$(".histogram_flash").css("width", wid);
}

/**
 * 小数优化,把位数占位的0去掉,返回类型为string
 **/
function NumOptimize(num){
//	判断是否位小数
	var param =/^\d+[.]\d+$/;
	if(param.test(num)){
		var str = num.split(".");
		var lastNum;
		if(str.length=2){
			var strSecond =str[1];
//			排除1.后面没有数的情况
			for(var i=0;i<strSecond.length;i++){
				var tempNum = strSecond.substring(strSecond.length-i-1,strSecond.length-i);
				if(parseInt(tempNum)!=0){
					var index = strSecond.length-i;
					lastNum = str[0]+"."+strSecond.substring(0,index);
					return lastNum;
				}
				if(i==strSecond.length-1){
					lastNum = str[0];
					return lastNum;
				}
			}
		}
	}else{
	return num;
	}
}
