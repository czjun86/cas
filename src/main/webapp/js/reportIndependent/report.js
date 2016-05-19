
/**
 * 单次测试比例图
 * @author czj
 * 
 * */

var colorlist;// 查询出的颜色
var div_html = "";
var flist_excel;
var compEval_excel;
var colorRoom = [ '#440067', '#2e3c74', '#00626d', '#763d00', '#770057',
		'#969800', '#8b2c2c', '#c6de27', '#424242', '#a524a3', '#00c5dc',
		'#979797', '#dbabce', '#ff8400' ];// 初始化颜色装载器

var colorRoom_1 = [ '#2EA01C', '#0882FF', '#FFC948', '#B634FA', '#FE5A4E',
		'#ABABAB', '#440067', '#2e3c74', '#00626d', '#763d00', '#770057',
		'#969800', '#8b2c2c', '#c6de27', '#424242', '#a524a3', '#00c5dc',
		'#979797', '#dbabce', '#ff8400' ];// 初始化颜色装载器



/**
 * 页面加载完成调用方法
 * 加载窗体改变方法
 * 加载网络状态、业务状态点击方法
 * 取得下拉框流水号调用数据方法
 */
function initializePage() {
	changeWionw();
	// 窗体大小改变
	$(window).resize(function() {
		changeWionw();
	});
	$(document).css({
		"overflow" : "hidden"
	});
	$("#compare_ul").html("");
	div_html = $("#div_point").html();
	// 选择流水号事件跳转
	$.parser.parse();
	var flowid = $("#SelectBox").combobox("getValue");
	if (flowid != "-1111" && flowid && flowid.length > 0) {
		$(".verify_sty").addClass("isverify");
		$(".verify_sty").css("cursor","pointer");//审核按钮添加点击
		flowid = flowid.split("_")[0];
		getData(flowid);
	} else {
		$("#background").hide();
		$("#bar_id").hide();
		$(document).css({
			"overflow" : "auto"
		});
	}
	changeWionw();
	// 网络状态点击事件
	$(".netclass").click(
			function() {

				var netType = $(this).attr('netValue');
				var testType = $(".testType").attr('netValue');
				if (netType == 1) {
					$("#ps_id").hide();
				} else {
					$("#ps_id").show();
				}
				if (typeof (testType) == "undefined") {
					testType = "-1";
				}
				var ss = contextPath + "/reportIndependent/reportlist?serialno="
						+ $("#ss_id").html() + "&netType=" + netType
						+ "&testType=" + testType+"&areaid="+$("#areaid").val()+"&s_id="+$("#s_id").val();
				$("#nettype_a_id").attr("href", ss);
				$(this).addClass(" forms_nav_now netType netclass");
				$(this).siblings()
						.removeClass("forms_nav_now netType netclass");
				// $("#workFrame").click();
				$(".testclass").removeClass("forms_nav_erji_now testType");
				$(".testclass").first()
						.addClass(" forms_nav_erji_now testType");
				ajaxChangeSel();
			});

	// 业务状态点击事件
	$(".testclass").click(
			function() {
				var testType = $(this).attr('netValue');
				var netType = $(".netType").attr('netValue');
				var ss = contextPath + "/reportIndependent/reportlist?serialno="
						+ $("#ss_id").html() + "&testType=" + testType
						+ "&netType=" + netType+"&areaid="+$("#areaid").val()+"&s_id="+$("#s_id").val();
				$("#testtype_a_id").attr("href", ss);
				$(this).addClass(" forms_nav_erji_now testType");
				$(this).siblings().removeClass("forms_nav_erji_now testType");
				// $("#testworkFrame").click();
				ajaxChangeSel();
			});
	$("#SelectBox").combobox({
		onChange : function(n, o) {
			choose();
		}
	});
}
/**
 * 点击网络状态、业务状态时调用方法组装下拉框数据
 */
function ajaxChangeSel() {

	var serid = $("#ss_id").html();
	var testtype = $(".forms_nav_erji_now").attr('netvalue');
	var nettype = $(".netType").attr("netvalue");
	$.ajax({
		type : "post",
		url : contextPath + "/reportIndependent/changelist",
		data : ({
			serialno : serid,
			testType : testtype,
			areaid:$("#areaid").val(),
			s_id:$("#s_id").val(),
			netType : nettype
		}),
		success : function(data) {
			$("#SelectBox").empty();
			// $("#SelectBox").append("<option
			// value=\"-1111\">请选择流水号</option>");
			var dd = [];
			dd.push({
				"text" : '请选择流水号',
				"id" : '-1111',
				"selected" : true
			});

			var fl = data.flowlist;
			if (fl.length > 0) {
				var temp;
				for ( var i = 0; i < fl.length; i++) {
					temp = "";
					// temp += "<option value='"+fl[i].flowid+"'>";
					if (fl[i] != null && fl[i].flowid.length > 12) {
						temp += fl[i].flowid.substring(0, 12);
					}
					if (fl[i].netsystem != null) {
						var netstr = "";
						switch (fl[i].netsystem) {
						case 1:
							netstr = "_GSM";
							break;
						case 2:
							netstr = "_WCDMA_锁频";
							break;
						case 3:
							netstr = "_WCDMA_自由模式";
							break;
						default:
							break;
						}
						temp += netstr;
					}
					if (fl[i].teststatus != null) {
						var teststr = "";
						switch (fl[i].teststatus) {
						case 1:
							teststr = "_IDLE";
							break;
						case 2:
							teststr = "_CS";
							break;
						case 3:
							teststr = "_PS";
							break;
						default:
							break;
						}
						temp += teststr;
					}
					if (fl[i].calltype != null && fl[i].calltype != 0) {
						var callstr = "";
						if (fl[i].calltype == 1) {
							callstr = "_短呼";
						} else if (fl[i].calltype == 2) {
							callstr = "_长呼";
						} else {
						}
						temp += callstr;
					}
					if (fl[i].ftpUpdown != null && fl[i].ftpUpdown != 0) {
						var ftpstr = "";
						if (fl[i].ftpUpdown == 1) {
							ftpstr = "_上行";
						} else if (fl[i].ftpUpdown == 2) {
							ftpstr = "_下行";
						} else {
						}
						temp += ftpstr;
					}

					if (fl[i].inside != null) {

						var inside = "";
						if (fl[i].inside == 0) {
							inside = "(室外)";
						} else if (fl[i].inside == 1) {
							inside = "(室内)";
						}
						temp += inside;
					}
					// temp += "</option>";
					// $("#SelectBox").append(temp);
					//					
					dd.push({
						"text" : temp,
						"id" : fl[i].flowid
					});

				}
			}
			$("#SelectBox").combobox({
				data : dd,
				valueField : 'id',
				textField : 'text'
			});
			$("#SelectBox").combobox("setValues", [ '-1111' ]);
		}
	});
}
/**
 * 时间转换
 */
function date2str(x, y) {
	var z = {
		M : x.getMonth() + 1,
		d : x.getDate(),
		h : x.getHours(),
		m : x.getMinutes(),
		s : x.getSeconds()
	};
	y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
		return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
	});
	return y.replace(/(y+)/g, function(v) {
		return x.getFullYear().toString().slice(-v.length)
	});
}

/**
 * 根据流水号返回数据
 * （包括颜色-colorlist、柱状图－flashlist、判断是否有数据业务数据）
 * 动态生成流水测试结果
 * 计算页面宽设置地图宽高（16：9）、设置柱状图宽－每排两个
 * 并调用生成柱状图方法－creatFlash
 * 调用室内外地图生成方法－queryPoint
 * @param flowid
 */
function getData(flowid) {
	// 删除图div重新加载
	$(".histogram").html("");
	// 请求数据
	$
			.ajax({
				type : "post",
				url : contextPath + "/reportIndependent/reportlist",
				data : ({
					flowid : flowid,
					areaid:$("#areaid").val()
				}),
				success : function(data) {
					var flist = data.flashlist;
					flist_excel = data.flashlist;
					compEval_excel = data.compEval;
					//单个流水号总评价
					var compEval = data.compEval;
					colorlist = data.colorlist;
					// 数据业务
					if (data.contenthp) {
						$("#httpping_div").html(data.contenthp);
					}
					var wid_len = (document.body.scrollWidth - document.body.scrollWidth * 0.05) / 2;

					for ( var i = 0; i < flist.length; i++) {
						var xlist = flist[i].x; // X轴数据
						var indoor_x = flist[i].indoor_x;// 室内X轴
						var ylist = flist[i].y; // Y轴数据
						var ftp = flist[i].ftp; // ftp上下行数据
						var gradName=flist[i].gradName;
						var gradColor=flist[i].gradColor;
						// alert(xlist)
						if (ylist.length > 0) {
							// 排除为ci,mos,2g_txpower
							if (flist[i].kpiId <= 8
									|| (flist[i].kpiId >= 20 && flist[i].kpiId < 22)) {
								var tname = flist[i].kpiname;
								if(flist[i].kpiId == 8){
									tname = "C/I";
								}
								var idname = tname + i;
								if (flist[i].kpiId != 4 && flist[i].kpiId != 5) {
									
									$("#histogram1")
											.append(
													"<div class='histogram_flash' id='"
															+ idname
															+ "'  style='background:url(../../img/bb.png) no-repeat;width:"
															+ wid_len
															+ "px;margin:0 auto;position: relative;'></div>");
									
									creatFlash(indoor_x, xlist, ylist, idname,
											null, colorlist, flist[i].kpiname,
											flist[i].max, flist[i].min, null,gradName,gradColor);
								} else {
									// FTP柱状图
									$("#histogram2")
											.append(
													"<div class='histogram_flash' id='"
															+ idname
															+ "'  style='background:url(../../img/bb.png) no-repeat;width:"
															+ wid_len
															+ "px;margin:0 auto;position: relative;'></div>");
									creatFlash(indoor_x, xlist, ylist, idname,
											null, colorlist, flist[i].kpiname,
											flist[i].max, flist[i].min,
											flist[i].avg,gradName,gradColor);
									// FTP曲线图
									if (ftp && ftp.length > 0) {
										$("#histogram2")
												.append(
														"<div class='histogram_flash' id='"
																+ idname
																+ "2'  style='background:url(../../img/bb.png) no-repeat;width:"
																+ wid_len
																+ "px;margin:0 auto;position: relative;'></div>");
										creatFlash(indoor_x, xlist, ylist,
												idname + "2", ftp, colorlist,
												"", "", "", "","","");
									}

								}
							}
						}

					}

					// 测试结果
					var sernolist = data.sernolist;
					if (sernolist && sernolist.length > 0) {
						var inside = "", isindoor = "";
						if (sernolist[0].inside == 1) {
							inside = "室内";
							if (sernolist[0].isindoor == 1) {
								isindoor = "无";
							} else if (sernolist[0].isindoor == 2) {
								isindoor = "有";
							}
						} else if (sernolist[0].inside == 0) {
							inside = "室外";
						}
						var htl = [];
						// var day = new Date(sernolist[0].testtime);
						// var currentDate = "";
						// currentDate=date2str(day,"yyyy-MM-dd hh:mm:ss");
						htl
								.push("<table class=\"mytable\" id=\"temptable2\" width=\"100%\" height=\"106\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						htl.push("<tr>");
						htl.push("<td width=\"14%\">工单号：</td>");
						htl.push("<td id=\width=\"36%\" name='exc_serialno'>" + sernolist[0].serialno
								+ "_" + sernolist[0].testtime + "</td>");
						htl.push("<td style='display:none' id='fwid'>" + sernolist[0].flowid
								+ "_" + sernolist[0].testtime + "</td>");
						htl.push("<td width=\"14%\">室内/外：</td>");
						htl.push("<td width=\"36%\" name='exc_indoor'>" + inside + "</td>");
						htl.push("</tr>");
						if (inside == "室内") {
							htl.push("<tr>");
							htl.push("<td name='exc_roomName'>室分：</td><td name='exc_room'>" + isindoor + "</td>");
							htl.push("<td name='exc_gpsName'>GPS：</td><td><span style='float:left;margin-right:8px;'  name='exc_gps'>"
									+sernolist[0].latitude+ ", "
									+sernolist[0].longitude+ "</span><a href='#'><img src='"+contextPath+"/images/GPS.png' onClick='showIndoorMap("+sernolist[0].latitudeModify+","+sernolist[0].longitudeModify+",1,null)'></img></a></td>");
							htl.push("</tr>");
						} else {
							var obstruct = (sernolist[0].obstruct == null ? ""
									: sernolist[0].obstruct);
							obstruct = obstruct == 1 ? "无" : obstruct;
							obstruct = obstruct == 2 ? "有" : obstruct;
							var density = (sernolist[0].density == null ? ""
									: sernolist[0].density);
							density = density == 1 ? "密集 " : density;
							density = density == 2 ? "稀疏" : density;
							htl.push("<tr>");
							htl.push("<td name='exc_roomName'>阻挡：</td><td name='exc_room'>" + obstruct + "</td>");
							htl.push("<td name='exc_gpsName'>建筑密度：</td><td><span name='exc_gps'>" + density + "</span></td>");
							htl.push("</tr>");
						}
						var temp = "";
						if (sernolist[0].calltype != null
								&& sernolist[0].calltype != 0) {
							var callstr = "";
							var callp = +sernolist[0].callphone == null ? ""
									: "_(" + sernolist[0].callphone;
							var duration = +sernolist[0].duration == null ? ""
									: ",时长:" + sernolist[0].duration;
							duration = duration == ",时长:-9999" ? "" : duration;
							var space = +sernolist[0].space == null ? "" : ",间隔:"
									+ sernolist[0].space + ")";
							space = space == ",间隔:-9999)" ? ")" : space;
							if (sernolist[0].calltype == 1) {
								callstr = "_短呼" + callp +duration +space;
							} else if (sernolist[0].calltype == 2) {
								callstr = "_长呼" + callp +")";
							} else {
							}
							temp += callstr;
						}
						if (sernolist[0].ftpUpdown != null
								&& sernolist[0].ftpUpdown != 0) {
							var ftpstr = "";
							if (sernolist[0].ftpUpdown == 1) {
								ftpstr = "_上行";
							} else if (sernolist[0].ftpUpdown == 2) {
								ftpstr = "_下行";
							} else {
							}
							temp += ftpstr;
						}
						sernolist[0].teststatus = sernolist[0].teststatus == 1 ? '_IDLE'
								: sernolist[0].teststatus;
						sernolist[0].teststatus = sernolist[0].teststatus == 2 ? '_语音'
								: sernolist[0].teststatus;
						sernolist[0].teststatus = sernolist[0].teststatus == 3 ? '_PS'
								: sernolist[0].teststatus;
						htl.push("<tr>");
						var net_type = sernolist[0].netsystem;
						sernolist[0].netsystem = sernolist[0].netsystem == 1 ? 'GSM'
								: sernolist[0].netsystem;
						sernolist[0].netsystem = sernolist[0].netsystem == 2 ? 'WCDMA锁频'
								: sernolist[0].netsystem;
						sernolist[0].netsystem = sernolist[0].netsystem == 3 ? 'WCDMA自由模式'
								: sernolist[0].netsystem;
						htl.push("<td>场景：</td><td name='exc_scene'>" + sernolist[0].scenName
								+ "</td>");
						htl.push("<td>业务状态：</td><td name='exc_operationType'>"
								+ (sernolist[0].netsystem == null ? ""
										: sernolist[0].netsystem)
								+ sernolist[0].teststatus + temp + "</td>");
						htl.push("</tr>");
//						增加noService行用于添加3G,2G,以及无服务占比
						htl.push("<tr><td>3G占比：</td><td id='wcdmaScale' name='exc_wcdmaRatio'></td><td>2G占比：</td><td id='gsmScale'  name='exc_gsmRatio'></td></tr>");
						var gsm_eval="";
						var wcdma_eval="";
						var noservice_eval="";
							if(compEval[0]!=null){
								gsm_eval="<div><div style='float:left; display:block;'>2G：</div><span style='background:"+compEval[0]+";float:left;width:22px; height:22px;display:block; text-align:center; line-height:22px; color:#FFF; margin-right:25px;margin-top:1px;'>"+compEval[1]+"</span></div>";
							}
							if(compEval[2]!=null){
								wcdma_eval="<div><div style='float:left; display:block;'>3G：</div><span style='background:"+compEval[2]+";float:left;width:22px; height:22px;display:block; text-align:center; line-height:22px; color:#FFF; margin-right:3px;margin-top:1px;'>"+compEval[3]+"</span></div>";
							}
							if(compEval[0]==null&&compEval[2]==null){
								noservice_eval="无";
							}
						htl.push("<tr>");
						htl.push("<td>脱网率：</td><td id='noServiceScale' name='exc_noService'></td>");
						htl.push("<td>本次综合评价：</td><td name='exc_evaluate'>"+gsm_eval+wcdma_eval+noservice_eval+"</td>");
						htl.push("</tr>");
						htl.push("<tr>");
						htl.push("<td>实测位置：</td><td colspan='3' style='border-top:1px'><span id='add_span' name='exc_testUrl'>"
								+ (sernolist[0].testaddress == null ? ""
										: sernolist[0].testaddress.replace(/>/gi,'&gt;').replace(/</gi,'&lt;')) + "</span>");
						//判断编辑按钮权限
						var buttons=data.buttons;
						for(var b=0;b<buttons.length;b++){
							if(buttons[b]&& buttons[b].btntype == 'update'){
								htl.push("<input  class='in_edit' type='button' onClick='showIndoorMap(29,106,2,\""+sernolist[0].testaddress+"\")'/>");
							}
						}
						htl.push("</td>");
						htl.push("</tr>");
						htl.push("<tr>");
//						htl.push("<td>故障：</td><td colspan='3'>"
//								+ (sernolist[0].failure == null ? ""
//										: sernolist[0].failure) + "</td>");
//						htl.push("</tr>");
						htl.push("</table>");
						htl = $(htl.join('\r\n'));
						$("#result_ul").html(htl);
						// 查询点数据
						queryPoint(flowid, net_type, sernolist[0].inside, flist);
					} else {
						$("#background").hide();
						$("#bar_id").hide();
						$(document).css({
							"overflow" : "auto"
						});
					}
				}
			});
}

/**
 * 室内结果集在GPS处点击事件展示地图与修改结果集地址功能
 * @param lat
 * @param lng
 * @param type 1-室内定位地图2－修改地址
 */
function showIndoorMap(lat,lng,type,address){
	var ss_d=$("#add_span").html();
	address=ss_d;
	if(type==1){
		var str = $("#fwid").text();
		var flowid = str.split("_")[0];
		$(".easyui-linkbutton").hide();
		$("#indoormap").dialog({
			href:contextPath + '/reportIndependent/indoorMap?lat='+lat+'&lng='+lng+'&flowid='+flowid,
			height: 400,width: 800,title: "<div style=\"float:left;\">位置信息</div><div id=\"titleName\" style=\"float:right;margin-right:40px;\"><div>",
			modal: true,closed:false
		});
		$('#indoormap').dialog('open');
		$.parser.parse('#indoormap');
	}else if(type==2){
		// 选择流水号事件跳转
		$(".easyui-linkbutton").show();
		$("#indoormap").dialog({
			href:contextPath + '/reportIndependent/showAddress',
			height: 130,width: 435,title: "修改地址",
			modal: true,
			closed:false
		});
		$('#indoormap').dialog('open');
		$.parser.parse('#indoormap');
		$(".saveedit").unbind("click").click(function(){
			var flowid = $("#fwid").html();
			if(flowid){
				flowid = flowid.substring(0,flowid.indexOf('_'));
			}
			var add=$("#address").val().replace(/>/gi,'&gt;').replace(/</gi,'&lt;');
			$('#dataForm').ajaxForm({
			  	url: contextPath + '/reportIndependent/updateAddress?flowid='+flowid+"&areaid="+$("#areaid").val(),
			    success: function(data) {
			      if(data.msg == 1){
			    	  $.messager.alert("提示","操作成功！","success",function(){	
			    		  $('#indoormap').dialog('close');
			    		  $("#add_span").html(add);
			    	  });
			      }else{
			    	  $.messager.alert("提示","操作失败！","error",function(){	
			    		  $('#indoormap').dialog('close');
			    		  $("#add_span").html(add);
				    	  
			    	  });
			      }
				}
			});
			$("#dataForm").submit();
		});
	}
}
/**
 * 
 * @param str
 * @param idstr
 */
function comparshow(str, idstr) {
	var flowids = [];
	var ss = str.split(",");
	var ids = idstr.split(",");
	for ( var t = 0; t < ss.length; t++) {
		if (ss[t].length > 0) {
			flowids.push("<li style='color:");
			flowids.push(colorRoom_1[t]);
			flowids.push("' id='" + ids[t] + "'>");
			flowids.push(ss[t]);
			flowids.push("</li>");
		}

	}
	flowids = $(flowids.join('\r\n'));
	$("#compare_ul").html(flowids);
}
/*******************************************************************************
 * 创建柱状图
 * 
 * @param xlist
 *            x轴数据
 * @param ylist
 *            Y轴数据
 * @param kpiname
 *            指标名称
 * @param ftp
 *            ftp上下行数据
 * @param colorlist
 *            颜色集合
 *            
 *  判断数据室内外类型并选择使用X轴数据
 *  取得最大最小值判断是否无服务
 *  取得颜色合初始化颜色加载器作为PSC、BCCH颜色选择器
 *  初始化tempData数组设置6个颜色与0值作为PSC、BCCH不足6个值时进行填补
 *  循环X轴字符数据进行区间大小组装，倒序正序指标分别进行
 *  设置比例图highcharts属性，设置数据与柱子颜色
 *  
 *  
 */
function creatFlash(indoor_x, xlist, ylist, kpiname, ftp, colorlist, title,
		max, min, avg,gradeName,gradColor) {
	// 判断室内外
	if (ylist[0].inside == 1) {
		xlist = indoor_x;
	}
	if (min == -9998) {
		min = "No Service";
	}
	if (max == -9998) {
		max = "No Service";
	}
	var arr_color = [];
	for ( var t = 0; t < colorlist.length; t++) {
		var color = colorlist[t];
		arr_color.push(color.colourcode);
	}
	var tempData = [ {
		color : arr_color[5],
		y : 0
	}, {
		color : arr_color[4],
		y : 0
	}, {
		color : arr_color[3],
		y : 0
	}, {
		color : arr_color[2],
		y : 0
	}, {
		color : arr_color[1],
		y : 0
	}, {
		color : arr_color[0],
		y : 0
	} ];
	// 最大最小值

	// 如果是ftp在x轴补空
	if (ftp) {
		for ( var i = 0; i < ftp.length; i++) {
			xlist.push("");
		}
	}
	// X轴3,7,22倒序
	var xlist_desc = [];
	if (ylist[0].kpi == '3' || ylist[0].kpi == '7' || ylist[0].kpi == '22') {
		for ( var i = xlist.length - 1; i >= 0; i--) {
			if (i == xlist.length - 1) {
				xlist_desc.push("<" + xlist[i] + "");
			} else if (i == 0) {
				xlist_desc.push(">=" + xlist[i] + "");
			} else {
				xlist_desc.push("[" + xlist[i] + ")");
			}
		}
	} else {
		for ( var i = 0; i < xlist.length; i++) {
			if (i == xlist.length - 1) {
				xlist_desc.push(">=" + xlist[i] + "");
			} else if (i == 0) {
				xlist_desc.push("<" + xlist[i] + "");
			} else {
				xlist_desc.push("[" + xlist[i] + ")");
			}
		}
	}
	var grad="";
	if(gradeName&&gradeName!=null)
     {grad='<div class="down" style="border-top-color:'+gradColor+'"><span>'+gradeName+'</span></div>'; }
	var options = {
		max : 100,
		chart : {
			renderTo : kpiname,
			backgroundColor : '#f0f0f0',
			plotBackgroundImage : '../images/bb.png',
			marginTop : 30,
			marginBottom : 30
		},
		title : {
			text : title,
			y : 10,
			x : -10,
			align : 'left'
		},
		subtitle : {
			useHTML : true,
			text : grad+'Max:' + max + '<br/>Min:' + min,
			x : -15,
			y : 55
		},
		xAxis : [ {
			categories : xlist_desc
		} ],
		plotOptions : {
			column : {
				groupPadding : 0.05
			}
		}
	};

	if (ylist[0].kpi != '20' && ylist[0].kpi != '21' && !ftp) {

		var arr = new Array();

		for ( var i = 0; i < ylist.length; i++) {
			// 加入对比时柱状图字体颜色
			var ca = {}, ct = "";
			if ($("#compare_ul").find("li").length > 1) {
				$("#compare_ul").find("li").each(
						function() {
							if ($(this).attr("id").replace(/^\s\s*/, '')
									.replace(/\s\s*$/, '') == ylist[i].flowid) {
								ct = $(this).css("color");
								ca.color = ct;
							}
						});
			}
			var sum = (parseInt(ylist[i].six * 100)
					+ parseInt(ylist[i].five * 100)
					+ parseInt(ylist[i].four * 100)
					+ parseInt(ylist[i].three * 100)
					+ parseInt(ylist[i].two * 100)
					+ parseInt(ylist[i].one * 100) - 10000) / 100;
			if (sum > 0 || sum < 0) {
				var intv = [];
				intv.push(ylist[i].one);
				intv.push(ylist[i].two);
				intv.push(ylist[i].three);
				intv.push(ylist[i].four);
				intv.push(ylist[i].five);
				intv.push(ylist[i].six);
				for ( var h = 0; h < intv.length; h++) {
					if (Math.max.apply(null, intv) == intv[h]) {

						switch (h) {
						case 0:
							ylist[i].one = (parseInt(ylist[i].one * 100
									- parseInt(sum * 100))) / 100;
							break;
						case 1:
							ylist[i].two = (parseInt(ylist[i].two * 100
									- parseInt(sum * 100))) / 100;
							break;
						case 2:
							ylist[i].three = (parseInt(ylist[i].three * 100
									- parseInt(sum * 100))) / 100;
							break;
						case 3:
							ylist[i].four = (parseInt(ylist[i].four * 100
									- parseInt(sum * 100))) / 100;
							break;
						case 4:
							ylist[i].five = (parseInt(ylist[i].five * 100
									- parseInt(sum * 100))) / 100;
							break;
						case 5:
							ylist[i].six = (parseInt(ylist[i].six * 100
									- parseInt(sum * 100))) / 100;
							break;
						default:
							break;
						}
					}
				}
			}
			var five = accAdd(ylist[i].six, ylist[i].five);
			var four = accAdd(five, ylist[i].four);
			var three = accAdd(four, ylist[i].three);
			var two = accAdd(three, ylist[i].two);
			var obj = {}, obj1 = {};
			if (ylist[0].kpi == '3' || ylist[0].kpi == '7'
					|| ylist[0].kpi == '22') {
				obj = {
					name : 'Temperature',
					type : 'column',
					data : [
					// 按数值从小到大
					{
						color : arr_color[5],
						y : ylist[i].six,
						dataLabels : ca
					}, {
						color : arr_color[4],
						y : ylist[i].five,
						dataLabels : ca
					}, {
						color : arr_color[3],
						y : ylist[i].four,
						dataLabels : ca
					}, {
						color : arr_color[2],
						y : ylist[i].three,
						dataLabels : ca
					}, {
						color : arr_color[1],
						y : ylist[i].two,
						dataLabels : ca
					}, {
						color : arr_color[0],
						y : ylist[i].one,
						dataLabels : ca
					}

					]
				};
				obj1 = {
					name : 'Rainfall',
					type : 'line',
					color : '#579c48',
					data : [
					// 按数值从小到大
					(ylist[i].six > 100) ? 100 : ylist[i].six,
							(five > 100) ? 100 : five,
							(four > 100) ? 100 : four,
							(three > 100) ? 100 : three,
							(two > 100) ? 100 : two, 100

					]
				};

			} else {
				obj = {
					name : 'Temperature',
					type : 'column',
					data : [
					// 按数值从小到大
					{
						color : arr_color[0],
						y : parseFloat(ylist[i].one),
						dataLabels : ca
					}, {
						color : arr_color[1],
						y : parseFloat(ylist[i].two),
						dataLabels : ca
					}, {
						color : arr_color[2],
						y : parseFloat(ylist[i].three),
						dataLabels : ca
					}, {
						color : arr_color[3],
						y : parseFloat(ylist[i].four),
						dataLabels : ca
					}, {
						color : arr_color[4],
						y : parseFloat(ylist[i].five),
						dataLabels : ca
					}, {
						color : arr_color[5],
						y : parseFloat(ylist[i].six),
						dataLabels : ca
					}

					]
				};
				obj1 = {
					name : 'Rainfall',
					type : 'line',
					color : '#579c48',
					data : [ 100, (two > 100) ? 100 : two,
							(three > 100) ? 100 : three,
							(four > 100) ? 100 : four,
							(five > 100) ? 100 : five,
							(ylist[i].six > 100) ? 100 : ylist[i].six

					]
				};
			}

			arr.push(obj);
			arr.push(obj1);
		}

		if (avg != null && avg >= 0) {
			options.subtitle = {
				useHTML : true,
				text : grad+'Max:' + max + '<br/>Avg:' + avg + '<br/>Min:' + min,
				x : -15,
				y : 55
			};
		}
		options.series = arr;
	} else {
		// PSC、BCCH
		if (!ftp) {
			var xxbhMap = {};
			var len = ylist.length;
			for ( var i = 0; i < len; i++) {
				if (xxbhMap[ylist[i].flowid] == undefined) {
					var list = [];
					list.push(ylist[i]);
					xxbhMap[ylist[i].flowid] = list;
				} else {
					xxbhMap[ylist[i].flowid].push(ylist[i]);
				}

			}

			var arrp = new Array();
			for (ss in xxbhMap) {

				var st = xxbhMap[ss];
				var arrt = new Array();
				var obj = {
					name : '',
					type : 'column'
				};

				// 加入对比时柱状图字体颜色
				var ca = {}, ct = "";
				if ($("#compare_ul").find("li").length > 1) {
					$("#compare_ul").find("li")
							.each(
									function() {
										if ($(this).attr("id").split("_")[0]
												.replace(/^\s\s*/, '').replace(
														/\s\s*$/, '') == ss) {
											ct = $(this).css("color");
											ca.color = ct;
										}
									});
				}

				// 颜色顺序
				var psc_arr_color = [];

				for ( var j = arr_color.length - 1; j >= 0; j--) {
					psc_arr_color.push(arr_color[j]);
				}
				var pscbchlist = [];
				for ( var i = 0; i < st.length; i++) {
					var cc;
					var ij = $.inArray(st[i].pscbcch, xlist);

					// 判断值在X轴的位置取对应的颜色

					if (ij < psc_arr_color.length) {
						cc = psc_arr_color[ij];
					} else {
						cc = colorRoom[ij - psc_arr_color.length];
					}

					var cc1 = {
						color : cc,
						y : parseFloat(st[i].percent),
						dataLabels : ca
					};

					// 放在对应的X数据里
					arrt[ij] = cc1;

					// 判断值如果值在X轴不存在补空值取色
					pscbchlist.push(st[i].pscbcch);
					if (i == st.length - 1) {
						for ( var j = 0; j < xlist.length; j++) {
							if ($.inArray(xlist[j], pscbchlist) == -1) {
								var cc1 = {
									color : '',
									y : parseFloat(0.0),
									dataLabels : ca
								};
								arrt[j] = cc1;
							}
						}
					}
				}
				obj.data = arrt;
				arrp.push(obj);
			}
			options.series = arrp;
			// 补图
			var datas = options.series[0].data;
			var len = tempData.length, i = 0;
			for (i = 0; i < len; i++) {
				if (!datas[i]) {
					datas.push(tempData[i]);
					xlist.push("");
				} else {
				}
			}
			options.xAxis[0].categories = xlist;
			options.tooltip = {
				formatter : function() {
					if (this.x == '') {
						this.x = 0;
					}
					return " " + this.x + ": " + this.y;
				}
			};
			options.subtitle = {
				useHTML : true,
				text : ''
			};
		}
		// ftp
		if (ftp && ftp.length > 0) {
			// 判断FTP上行或下行曲线图
			if (ylist[0].kpi == '4' || ylist[0].kpi == '5') {
				var xxbhMap = {};
				var len = ftp.length;
				for ( var i = 0; i < len; i++) {
					if (xxbhMap[ftp[i].flowid] == undefined) {
						var list = [];
						list.push(ftp[i]);
						xxbhMap[ftp[i].flowid] = list;
					} else {
						xxbhMap[ftp[i].flowid].push(ftp[i]);
					}
				}
				var arrp = new Array();
				var arrx = new Array();
				for (ss in xxbhMap) {
					var st = xxbhMap[ss];
					var arrt = new Array();
					var obj1 = {
						name : 'Rainfall',
						type : 'spline',
						color : '#959595',
						yAxis : 1
					};
					for ( var i = 0; i < st.length; i++) {
						if (st[i].percent != -9998) {
							var cc = parseFloat(st[i].percent);
							arrt.push(cc);
							arrx.push("");
						} else {
							arrt.push(null);
							arrx.push("");
						}
					}
					obj1.data = arrt;
					arrp.push(obj1);
				}
				options.series = arrp;
				options.xAxis[0] = {
					categories : arrx
				};
				options.subtitle = {
					useHTML : true,
					text : '',
					x : -20,
					y : 100
				};
			}
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
	options.xAxis[0].labels={style: {color:'#020202'}};
	var reportForms = new ReportForms();
	reportForms.Create(options);
}

//下拉菜单选择流水号事件
function choose() {

	var ss = $("#SelectBox").combobox("getValue");
	if (ss != "-1111") {
		if (ss.length > 0) {
			changeWionw();
			$("#add_img").show();
			$(".case_zhi").hide();
			$(".case_tu").css("background", "");
			$(".case_tu").css("border-bottom", "");
			$(".case_tu").css("border-right", "");
			flag = true;
			ss = ss.split("_")[0];
			if (ss) {
				$("#compare_ul").html("");
				$("#background").show();
				$("#bar_id").show();
				$(document).css({
					"overflow" : "hidden"
				});
				getData(ss);
			}
		}
	}
}
//是否展示工单详情
function show_hidden() {
	var _target = $("#temptable1");
	var _temp = $("#temptable2");
	if (_target.css('display') == 'none') {
		_target.show();
		_temp.hide();
		$("#a_img").attr("src", "../images/shouqi.png");
	} else {
		_target.hide();
		_temp.show();
		$("#a_img").attr("src", "../images/zhankai.png");
	}
}

/***
 * 列表展示事件
 */
var flag = true;
function isShowChoose() {
	if (flag == true) {
		$(".case_zhi").show();
		$("#add_img").hide();
		flag = false;
	} else {
		$("#add_img").show();
		$(".case_zhi").hide();
		flag = true;
	}
}
//小数相加
function accAdd(arg1, arg2) {

	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return parseInt((arg1 * m + arg2 * m)) / m;

}
$(function(){
	/**
	 * 审核工单
	 */
	$(".isverify").live('click',function(){

		$('#isverify').dialog('open');
		$('#isverify_neirong').show();
		$('#dlg-isverify').show();
		$(".saveVerify").show();
		$(".closeVerify").show();
		$.parser.parse('#isverify');
	});
	/**
	 * 保存审核修改
	 */
	$(".saveVerify").live('click',function(){
		var obj = document.getElementsByName("isverify_val");
		var val;
		 if(obj!=null){
	        var i;
	        for(i=0;i<obj.length;i++){
	            if(obj[i].checked){
	            	val = obj[i].value;           
	            }
	        }
	    }
		var serialno = $("#ss_id").val();
		var id= $("#s_id").val();
		$.ajax({
			type : "post",
			url : contextPath + "/reportIndependent/updateVerify",
			data : ({
				id : id,
				val : val
			}),
			success : function(data) {
				$('#isverify').dialog('close');
				if(data.flag==1){
					$.messager.alert("提示", "操作成功！", "success",function(){
						//window.location.href = window.location.href;
						//修改页面提示
						$(".verify_sty").css("background","url('"+contextPath+"/images/bg_"+val+".png') no-repeat scroll 0 0 rgba(0, 0, 0, 0)");
						$(".verify_sty").text(val==0?'未审核':val==1?'已通过':val==2?'未通过':'未审核');
					});
				}else{
					$.messager.alert("提示", "操作失败！", "error");
				}
			}
		});
	});
	
	
});


