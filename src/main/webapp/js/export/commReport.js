/**
 * 返回数组最大值
 * 
 * @param array
 * @returns
 */
function getMax(array) {
	return Math.max.apply(Math, array);
}
/**
 * 返回数组最小值
 * 
 * @param array
 * @returns
 */
function getMin(array) {
	return Math.min.apply(Math, array);
}
/**
 * 设置开始结束时间
 * 
 * @returns {Boolean}
 */
function checkDate() {
	var cctype = parseInt($("#cc").combobox("getValue"));
	var starttime = "";
	var endtime = "";
	// 拼接开始时间、结束时间
	if (cctype == 1) {
		starttime = $("#day").val();
		endtime = $("#day").val();
		if (starttime == null || starttime == "") {
			$.messager.alert("提示", "请选择时间！", "warning");
			return false;
		}
	} else if (cctype == 2) {
		starttime = $("#weekstart").val();
		endtime = $("#weekend").val();
		if (starttime == null || starttime == "") {
			$.messager.alert("提示", "请选择开始时间！", "warning");
			return false;
		}
		if (endtime == null || endtime == "") {
			$.messager.alert("提示", "请选择结束时间！", "warning");
			return false;
		}
		var startDate = Date.parse(starttime.replace(/-/g, "/"));
		if (isNaN(startDate)) {
			startDate = Date.parse(starttime);
		}
		var endDate = Date.parse(endtime.replace(/-/g, "/"));
		if (isNaN(endDate)) {
			endDate = Date.parse(endtime);
		}
		if (startDate > endDate) {
			$.messager.alert("提示", "开始时间应小于结束时间！", "warning");
			return false;
		}
	} else {
		var month = $("#month").val();
		if (month != null && month != "") {
			starttime = month + "-01";
			var arr1 = starttime.split("-");
			endtime = month + "-" + (new Date(arr1[0], arr1[1], 0).getDate());
		}
		if (starttime == null || starttime == "") {
			$.messager.alert("提示", "请选择时间！", "warning");
			return false;
		}
	}
	$("#starttime").val(starttime);
	$("#endtime").val(endtime);
	return true;
}
/**
 * 显示隐藏报表类型对应查询条件
 * 
 * @param showtype
 *            报表类型
 */
function showHide(showtype) {
	// 根据类型显示不同日报查询条件
	if (showtype == 1) {
		$("#daytitle").show();
		$("#daydiv").show();
		$("#weekstarttitle").hide();
		$("#weekstartdiv").hide();
		$("#weekendtitle").hide();
		$("#weekenddiv").hide();
		$("#monthtitle").hide();
		$("#monthdiv").hide();
	} else if (showtype == 2) {
		$("#daytitle").hide();
		$("#daydiv").hide();
		$("#weekstarttitle").show();
		$("#weekstartdiv").show();
		$("#weekendtitle").show();
		$("#weekenddiv").show();
		$("#monthtitle").hide();
		$("#monthdiv").hide();
	} else {
		$("#daytitle").hide();
		$("#daydiv").hide();
		$("#weekstarttitle").hide();
		$("#weekstartdiv").hide();
		$("#weekendtitle").hide();
		$("#weekenddiv").hide();
		$("#monthtitle").show();
		$("#monthdiv").show();
	}
}
/**
 * 获取上一天的时间
 * 
 * @param today
 *            当前时间
 */
function getLastDay(today) {
	var lastDay = today.getTime() - 24 * 60 * 60 * 1000;
	$("#day").val(dateFormat(new Date(lastDay), 'yyyy-MM-dd'));
}
/**
 * 获取上周的起止时间
 * 
 * @param today
 *            当前时间
 */
function getLastWeekDay(today) {
	var currentWeek = today.getDay();
	if (currentWeek == 0) {
		currentWeek = 7;
	}
	var monday = today.getTime() - (currentWeek + 6) * 24 * 60 * 60 * 1000; // 星期一
	var sunday = today.getTime() - (currentWeek) * 24 * 60 * 60 * 1000; // 星期日
	$("#weekstart").val(dateFormat(new Date(monday), 'yyyy-MM-dd'));
	$("#weekend").val(dateFormat(new Date(sunday), 'yyyy-MM-dd'));
}
/**
 * 格式化日期
 * 
 * @param date
 * @param fmt
 * @returns
 */
function dateFormat(date, fmt) {
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	fmt = fmt.replace("yyyy", year);
	fmt = fmt.replace("MM", addZero(month));
	fmt = fmt.replace("dd", addZero(day));
	return fmt;
}

/**
 * 获取前一个月的时间
 * 
 * @param today
 */
function getLastMonth(today) {
	var month = today.getMonth();
	var year = today.getFullYear();
	if (month == 0) {
		year = year - 1;
		month = 12;
	}
	$("#month").val(year + "-" + addZero(month));
}
/**
 * 不足10补0
 * 
 * @param num
 * @returns
 */
function addZero(num) {
	if (num < 10) {
		return "0" + num;
	} else {
		return num;
	}
}
/**
 * 改变时间类型,显示不同的默认时间
 * 
 * @param showtype
 */
function changeDefDate(showtype) {
	var date = new Date();
	if (showtype == 1) {
		getLastDay(date);
	} else if (showtype == 2) {
		getLastWeekDay(date);
	} else {
		getLastMonth(date);
	}
}

function showError() {
	$.messager.alert("提示", "导出报表失败！", "error");
}
/**
 * 得到颜色
 * @param val 当前值
 * @param sort 0、越大越好 1越小越好（0、1最好最差都有） 2越大越差 3、越小越差 （2、3只有最差） 
 * @param max 最大值
 * @param min 最小值
 * @returns {String}
 */
function getColor(val, sort, max, min) {
	if(isNaN(Number(val))){
		return '';
	}
	var green = '#00FF00', red = '#FF0000';
	if(sort == 0){
		if (max == min) {
			return red;
		} else {
			if (parseFloat(max) == parseFloat(val)) {
				return green;
			}
			if (parseFloat(min) == parseFloat(val)) {
				return red;
			}
		}
	}else if(sort == 1){
		if (max == min) {
			return green;
		} else {
			if (parseFloat(max) == parseFloat(val)) {
				return red;
			}
			if (parseFloat(min) == parseFloat(val)) {
				return green;
			}
		}
	}else if(sort == 2){
		if(max == min){
			return '';
		}else{
			if (parseFloat(max) == parseFloat(val)) {
				return red;
			}
		}
	}else if(sort == 3){
		if(max == min){
			return '';
		}else{
			if (parseFloat(min) == parseFloat(val)) {
				return red;
			}
		}
	}
	return '';
}
/**
 * 填充背景颜色
 * @param names  需要填充颜色的kpi名称
 * @param _this 需要填充jq区域
 * @param num 
 */
function fillBgColor(names,_this,num){
	if(num == undefined || num == null || num == ""){
		num = 0;
	}
	//需要填充颜色的kpi名称
	if(show != 0){
		$(_this + ' ul').each(function(){
			var arr = [];
			var _ul = $(this);
			var name = _ul.attr("name");//这里取到的name必须与定义的数组一致
			if(name != null && name != ''){
				if($.inArray(name,names) >= 0){
					//0 从大到小 1从小到大
					var sort = _ul.attr('sort');
					// 找到ul下的li
					var _lis = _ul.find('li');
					var len = _lis.length - num;
					//如果只有一个区域则不标色
					if(len <= 1){
						return;
					}
					var count = 0;
					//将li的值装进数组
					for(var i = 0; i < len; i ++){
						var val = $(_lis[i]).html().replace('%', '');
						if(!isNaN(Number(val))){
							arr[count] = val;
							count ++;
						}
					}
					//获取最大值
					var max = getMax(arr);
					//获取最小值
					var min =getMin(arr);
					
					for(var i = 0; i < len; i ++){
						var val = $(_lis[i]).html().replace('%', '');
						//填充颜色
						$(_lis[i]).css("background-color", getColor(val,sort,max,min));
					}
				}
			}
		});
	//	}
	}
}
/**
 * 初始化区域dialog
 */
function initAreaDialog() {
	$("#areas").combobox(
			{
				onShowPanel : function() {
					$("#areas").combobox("hidePanel");
					var src = contextPath + '/export/arealist?areaids='
							+ $("#areaids").val();
					$("#areadlg").dialog({
						href : src,
						height : 400,
						width : 380,
						title : "选择区域",
						modal : true,
						closed : false
					});
					$('#areadlg').dialog('open');
					$.parser.parse('#areadlg');
					$(".sel_area").unbind('click').click(
							function() {
								var nodes = $('#areadlg').tree('getChecked');
								var strtext = "";
								var strids = "";
								var queryids = "";
								if (nodes.length > 0) {
									for ( var i = 0; i < nodes.length; i++) {
										var attr = nodes[i].attributes;
										if (attr != null && attr != "") {
											queryids += nodes[i].attributes
													+ ",";
										} else {
											queryids += nodes[i].id + ",";
										}
										strids += nodes[i].id + ",";
										strtext += nodes[i].text + ",";
									}
									if (strids != null && strids != "") {
										strids = strids.substring(0,
												strids.length - 1);
									}
									if (queryids != null && queryids != "") {
										queryids = queryids.substring(0,
												queryids.length - 1);
									}
									$("#queryids").val(queryids);
									$("#areaids").val(strids);
									if (strtext != null && strtext != "") {
										strtext = strtext.substring(0,
												strtext.length - 1);
										$("#areas")
												.combobox("setText", strtext);
										$("#areatext").val(strtext);
									}
								} else {
									$("#areaids").val('-1');
									$("#areatext").val('全部');
									$("#areas").combobox("setText", '全部');
								}
								$('#areadlg').dialog('close');

							});
				}
			});
}
// 根据分辨率计算每个li的宽度
window.onresize = function() {
	var scWidth = document.body.scrollWidth;
	var rightWidth = scWidth * 0.98 - 232 - 2;
	$(".yybb_right").css("width", rightWidth);
	var ov = $("#overflow").attr("val");
	if (ov != null && ov != "") {
		$("#overflow").css("width", rightWidth / 13 * parseInt(ov));
	}
	$(".yybb_right ul li").each(function(){
		var _this = $(this);
		var liWidth = (rightWidth - 13.1) / 13;
		if(_this.hasClass('top-group')){
			var num = parseInt(_this.attr('num'));
			liWidth = liWidth * num +  num - 1;
			_this.parent().css("width", (rightWidth - 13.1) / 13 * num +  num - 1 + 1);
		}
//		if(ov != null && ov != "" && parseInt(ov) > 13){
			if(_this.hasClass('top-last-li')){
				liWidth = liWidth - 0.2;
			}
////			if(_this.hasClass('top-last-all')){
////				liWidth = liWidth + 0.2;
////			}
//		}
		_this.css("width", liWidth);
	});
}


$(function(){
	//页面点击事件
	$("body").unbind('click').click(
			function() {
				//如果存在导出实测明细提示层(downExlDiv)就删除
				if($("#downExlDiv").length>0){
					$("#downExlDiv").remove();
				}
			});
});

//累积实测和日实测详情
function details_rpt(areaid,groupid,testType,reportType,event){
	//添加一个浮动层
	var e = event || window.event;
     var x = e.pageX || e.clientX + document.body.scroolLeft;
     var y = e.pageY || e.clientY + document.boyd.scrollTop;

     //alert(x+"::"+y);
     //在鼠标位置添加一个层
     var downDiv = "<div id='downExlDiv' onmouseover='exlOver()' onmouseout='exlOut()' style='background:url(../images/downExl_1.png) center bottom no-repeat;cursor:pointer;text-align:center;vertical-align:middle;width:80px;height:20px;position:absolute;left:"+x+"px;top:"+y+"px;' onclick='downExcle(\""+areaid+"\",\""+groupid+"\",\""+testType+"\",\""+reportType+"\")'>导出实测明细</div>";
     setTimeout(function(){
    	 $("body").append(downDiv);
     },300);
     
}

function exlOver(){
	if($("#downExlDiv").length>0){
		$("#downExlDiv").css("background","url(../images/downExl_2.png) center bottom no-repeat");
	}
}

function exlOut(){
	if($("#downExlDiv").length>0){
		$("#downExlDiv").css("background","url(../images/downExl_1.png) center bottom no-repeat");
	}
}

function liOver(evt){
	$(evt).css("color","#2d1efb");
	$(evt).css("text-decoration","underline");
}

function liOut(evt){
	$(evt).css("color","#343434");
	$(evt).css("text-decoration","none");
}


function downExcle(areaid,groupid,testType,reportType){
	$.messager.defaults = { ok: "是", cancel: "否" };
	$.messager.confirm("操作提示", "是否导出实测明细？", function (data) {
	    if (data) {
	    	//获取日期
	    	var _starttime=$("#starttime").val();
	    	var _endtime=$("#endtime").val();
	    	var _systime=$("#systime").val();
	    	
	    	$.ajax({
	    		   type: "POST",
	    		   url: contextPath+"/export/detailsExcel",
	    		   data: {'starttime':_starttime,
	    			   	  'endtime':_endtime,
	    			   	  'systime':_systime,
	    			   	  'areaid':areaid,
	    			   	  'groupid':groupid,
	    			   	  'testType':testType,
	    			   	  'reportType':reportType
	    			   	  },
	    		   success: function(data){
	    				//验证返回的连接
	    			   if(data.sucFlag =="成功"){
	    				  window.location.href=contextPath+"/export/downLoadExcel?name="+data.fname+"&testType="+data.testType+"&reportType="+data.reportType;
	    			   }else{
	    				   $.messager.alert("提示","生成报表失败！","error");
	    			   }
	    		   },
	    		   error: function(){
	    			   $.messager.alert("提示","请求服务器失败！","error");
	    		   }
	    	});
	    }
    });
	//还原设置，为了不影响其它样式
	$.messager.defaults = { ok: "确定", cancel: "取消" };
}
