/*----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// 版权所有。	
// E-mail：407666067@QQ.com
//
// 文件名：JDeviceColor.js
// 文件功能描述：选色器
//
// 创建标识：2012-08-13 10：16
//
// 修改标识：2012.11.14
// 修改描述：添加命名空间
//----------------------------------------------------------------*/
//注册一个命名空间
SJ.NameSpace.register("SJ.Component");
//创建一个取色器的类
SJ.Component["JDeviceColor"] = function(params, width, height, defaultColor){
	width = width ? width : 210;
	height = height ? height : 145;
	defaultColor = defaultColor ? defaultColor : "#000000";
	this.width = width, this.height = height; this.color = defaultColor;
	this.params = params;
	var _this = this;
	//定义样式表ID
	var styleSheetID = "jPlugin-labelBox-styleSheet-1.0.0";
	//定义labelBox主层ID
	var labelBoxID = "jPlugin-labelBox";
	//定义labelBox主层className
	var labelBoxClass = "jPlugin-labelBox";
	//labelBox主色
	var labelBoxColor = "#000000";
	var ColorHex=new Array('00','33','66','99','CC','FF');
	var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF');
	//保存labelBox皮肤颜色值
	var skin = {};
	//生成不同颜色值
	(function(){
		var a = labelBoxColor;
		skin.mainColor = a;
		skin.lightColor1 = SJ.getColor("light", a, 230);
		skin.lightColor2 = SJ.getColor("light", a, 80);
	})();
	//创建样式表
	(function(){
		//定义函数内部变量
		var a, b, c;
		//初始化变量值
		a = [];
		b = labelBoxClass;
		//labelBox主层样式
		a.push(
			'.'+b+'{'+
				'width:'+(width)+'px;height:'+(height)+'px;position:absolute;z-index:1000;top:100px;left:100px;overflow:hidden;'+
				'border:1px solid '+skin.lightColor2+';background:'+skin.lightColor1+';'+
				'display:none;font-size:12px;'+
			'}'
		);
		//show color
		a.push(
			'.'+b+' .'+b+'-showColor{'+
				'float:left;width:52px;height:18px;margin:2px;border:1px solid '+skin.mainColor+';background:#FFFFFF;'+
			'}'
		);
		//show color value
		a.push(
			'.'+b+' .'+b+'-showColorValue{'+
				'border:0px;float:left;width:60px;height:20px;margin:1px 8px;text-align:center;line-height:20px;background:'+skin.lightColor1+';'+
			'}'
		);
		//choose color table
		a.push('.'+b+' .'+b+'-chooseColorTbl{float:left;width:auto;height:auto;background:'+skin.mainColor+';}');
		//top bar
		a.push('.'+b+' .'+b+'-topBar{float:left;width:100%;height:24px;}');
		//close
		a.push('.'+b+' .'+b+'-close{float:right;cursor:pointer;margin:4px 5px;}');
		//圆角样式
		a.push('.'+b+'-Circle{-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:4px;}');
		a.push('.'+b+' .left{float:left;}');
		a.push('.'+b+' .right{float:right}');
		a = a.join('\n');
		SJ.createStyleSheet(a, styleSheetID);
	})();
	//创建一个弹窗
	(function(){
		//定义函数内部变量
		var a, b, c, d, e, f, g, h, i, j, k, l;
		//初始化变量值
		a = [];
		d = labelBoxClass;
		e = labelBoxID;
		//创建HTML
		a.push('<div class="'+d+' '+d+'-Circle" id="'+e+'">');
		a.push('	<div class="'+d+'-topBar">');
		a.push('		<div class="'+d+'-showColor" id="'+d+'-showColor"></div>');
		a.push('		<input type="text" class="'+d+'-showColorValue" id="'+d+'-showColorValue" value="#FFFFFF" />');
		a.push('		<label class="'+d+'-close" id="'+d+'-close">关闭</label>');
		a.push('	</div>');
		//生成颜色表
		a.push('	<table class="'+d+'-chooseColorTbl" cellpadding="0" cellspacing="1">');
		for(i=0;i<2;i++){
			for(j=0;j<6;j++){
				a.push('<tr>');
				a.push('<td data="#000000" style="background:#000000;font-size:0px;width:9px;height:9px;"></td>');
				colorValue = i==0 ? ColorHex[j]+ColorHex[j]+ColorHex[j] : SpColorHex[j];
				a.push('<td data="#'+colorValue+'" style="background:#'+colorValue+';font-size:0px;width:9px;height:9px;"></td>');
				a.push('<td data="#000000" style="background:#000000;font-size:0px;width:9px;height:9px;"></td>');
				for (k=0;k<3;k++){
					for (l=0;l<6;l++){
						colorValue = ColorHex[k+i*3]+ColorHex[l]+ColorHex[j];
						a.push('<td data="#'+colorValue+'" style="background:#'+colorValue+';font-size:0px;width:9px;height:9px;"></td>');
					}
				}
				a.push('</tr>');
			}
		}
		a.push('	</table>');
		a.push('</div>');
		a = a.join('\r\n');
		//判断labelBox是否存在，如果存在，就删除，重新创建
		f = document.getElementById(e);
		if(f) f.parentNode.removeChild(f);
		//创建一个零时元素，将创建的html添加到该零时元素中
		b = document.createElement("div");
		b.innerHTML = a;
		//获取创建的元素对象
		c = b.childNodes[0];
		//将创建的元素添加到body中
		document.body.appendChild(c);
	})();
	this.labelBox = document.getElementById(labelBoxID);
	this.closeBox = document.getElementById(labelBoxID+"-close");
	//绑定事件
	(function(){
		//获取颜色选择框
		_this.labelBox.onmouseover = function(event){
			event = event || window.event;
			//获取当前触发事件的元素
			var element = event.target || event.srcElement;
			if(element.nodeName == "TD"){
				var colors = element.getAttribute("data");
				document.getElementById(labelBoxID+"-showColor").style.background = colors;
				document.getElementById(labelBoxID+"-showColorValue").value = colors;
				_this.color = colors;
			}
		};
		_this.labelBox.onclick = function(event){
			event = event || window.event;
			//获取当前触发事件的元素
			var element = event.target || event.srcElement;
			var nodeName = element.nodeName;
			if(nodeName == "TD"){
				if(typeof(_this.params) == 'function'){ params(_this.color); }
				else if(_this.params.length>0){ _this.params.val(_this.color); }
				else{ _this.params.value = _this.color; }
				//关闭
				_this.close();
			}
			else if(nodeName == "LABEL"){ _this.close(); }
		};
	})();
};
//给类添加一个open方法
SJ.Component["JDeviceColor"].prototype.open = function(){
	var ev = window.event || arguments.callee.caller.arguments[0];
	var tar = ev.target || ev.srcElement;
	var tl = SJ.getElementTL(tar);
	//获取颜色选择框
	this.labelBox.style.top = (tl.top+tar.offsetHeight)+"px";
	this.labelBox.style.left = (tl.left)+"px";
	//显示
	this.labelBox.style.display = "block";
};
//给类添加一个close方法
SJ.Component["JDeviceColor"].prototype.close = function(){
	this.labelBox.style.display = "none";
};