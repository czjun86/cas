/*----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// 版权所有。	
// E-mail：407666067@QQ.com
//
// 文件名：JVerfier.js
// 文件功能描述：表单验证js
//
// 创建标识：2012-09-04 16:46
// 描述：表单验证，需要给需要验证的元素用一个容器装起，最好是form(默认)，可根据自己定义
//
// 修改标识：2012.11.14
// 修改描述：添加命名空间
//----------------------------------------------------------------*/
SJ.NameSpace.register("SJ.Component");
SJ.Component["JVerfier"] = function(){
	//定义一个对象，用于存储该js需要用到的私有变量
	var private = {
		//正则表达式库
        regExp: {
			R000: /_/gi,											//设置样式替换下划线
			R001: /^#([\da-f]{2})([\da-f]{2})([\da-f]{2})$/i,		//是否为RGB颜色值
            R002: /(^\s*)/g, 										//去掉字符串开始的空格
            R003: /(\s*$)/g											//去掉字符串结束的空格
        },
		conf: {
			//深色的颜色值
			darkColor: "#FF0000",
			//浅色的颜色值
			lightColor: "#FFDCDC",
			//文字的颜色
			fontColor: "",
			border: null,
			bubble: '<span class="bMain"><div class="bLabel">*</div><div class="bSpan"></div></span>',
			mClass: "required",
			inputBg: "m/images/input_bg.png",
			verClass: [
				"V000","V001","V002",
				"V003",'V004',"float2","float","Alphanumeric",
				"letters","int","num",
				"email","tel","phone",
				"url","postcode","longitude",
				"latitude","idcard","telPhone",
				"qq", "Alphanumerics","uuid"
			]
		},
		style: {
			bMain: {
				border: "none",
				background: "none",
				padding: "0px",
				width: "auto",
				height: "30px",
				font_size: "11px",
				position: "absolute",
				z_index: "10",
				float: "left",
				margin_top: "-32px"
			},
			bLab: {
				width: "auto",
				height: "32px",
				float: "left",
				opacity: "0.85"
			},
			bLab1: {
				width: "1px",
				font_size: "0px",
				height: "20px",
				float: "left",
				margin_top: "2px"
			},
			bLab2: {
				width: "1px",
				font_size: "0px",
				height: "22px",
				float: "left",
				margin_top: "1px"
			},
			bLabel: {
				width: "auto",
				height: "23px",
				line_height: "24px",
				padding: "0px 2px",
				float: "left",
				text_indent: "0px"
			},
			bSpan: {
				width: "9px",
				height: "10px",
				float: "left",
				position: "absolute",
				left: "10px",
				top: "24px",
				opacity: "0.85"
			}
		}
	};
	//定义一个对象public，用于存储该js需要从外部传入的参数
	var public = {
		conf: {
			//颜色值
			color: "#FF0000",
			//focus控件的颜色
			focusColor: "#C8C8C8",
			forms: ".form",
			//是否启用输入框背景
			isBg: false,
			//是否在验证通过时,返回JSON数据对象
			isData: true,
			//提示消息框的三角形显示位置
			layout: "bottom",
			//需要做验证的标签名
			element: "input,textarea,select"
		}
	};
	//定义一个对象，用于存储该js需要用到的文本字符串
	var lang = {
		S000: "请输入正确格式的内容",
		S001: "*必填",
		V000: "只能为数字,字母,下划线,中划线,点",
		V001: "只能为中文,括号,数字或字母,中划线,下划线",
		V002: "只能为中文,字母,中划线,下划线",
		V003: "只能为中文",
		V004: '只能为数字，字母，下划线，中划线和常见的特殊符号',
		uuid: '只能为24位的字母和数字的组合',
		float2: "请输入浮点数(两位有效数字)",
		float:"请输入浮点数",
		Alphanumeric: "只能为字母和数字",
		Alphanumerics: "必须以字母开头的字母和数字组合",
		letters: "只能为字母",
		int: "请输入正整数",
		num: "请输入数字",
		email: "请输入正确的邮件(如:xxx@163.com)",
		tel: "请输入正确的座机号码(如:68888888)",
		phone: "请输入正确的手机号码(如:13888888888)",
		url: "请输入正确的地址",
		postcode: "请输入正确的邮编(如:400000)",
		longitude: "经度(-180.00000~180.00000)",
		latitude: "纬度(-90.00000~90.00000)",
		idcard: "请输入正确的身份证号码",
		telPhone: "请输入电话号码(座机或手机)",
		qq: "请输入正确的QQ"
	};
	//定义一个对象，用于存储该js需要用到的页面元素对象
	var elem = {};
	//内部私有公共函数库
	private.fn = {
		/**
		 * 设置样式
		 * @param a.需要设置样式的元素
		 * @param b.样式json对象
		 */
		setStyle: function(a, b){
			var c = private.regExp;
			for(var d in b){ a.css(d.replace(c.R000, "-"), b[d]); }
			return a;
		},
		/**
		 * 表单验证
		 * @param a.需要验证的内容
		 * @param b.验证的类型
		 * num 数字、email 邮件、tel 电话、phone 手机、postcode 邮编、idcard 身份证、url URL地址、Alphanumeric 字母数字混合、
		 * V000 数字,字母,下划线,中划线,点、V001 中文,括号,数字或字母,中划线,下划线、V002 中文,字母,中划线
		 */
		validate:function(a, b){
			var c, d, e, f, g;
			b = b ? b : "Alphanumeric";
			e = a.length;
			switch(b){
				//数字,字母,下划线,中划线,点
				case "V000": c = /^[0-9a-zA-Z_.\-]{1,}$/; break;
				//中文,括号,数字或字母,中划线,下划线
				case "V001": c = /^[\u4E00-\u9FA5\uF900-\uFA2D0-9a-zA-Z\-\_\(\)\（\）]{1,}$/; break;
				//中文,字母,中划线
				case "V002": c = /^[\u4E00-\u9FA5\uF900-\uFA2Da-zA-Z\-\_]{1,}$/; break;
				//中文
				case "V003": c = /^[\u4E00-\uFA29]*$/; break;
				//数字，字母，下划线，点，中划线，@，#，$，%，^,&,*
				case "V004": c = /^[0-9a-zA-Z\_\.\-\!\@\#\$\%|^\&\*]{1,}$/; break;
				case "uuid": c = /^[A-Za-z0-9]{24}$/; break;
				//浮点数(两位有效数字)
				case "float2": c = /^(-?\d+)(\.\d{1,2})?$/; break;
				case "float": c = /^(-?\d+)(\.\d{1,})?$/; break;
				//字母数字混合
				case "Alphanumeric": c = /^[A-Za-z0-9]+$/; break;
				//必须以字母开头的字母和数字组合
				case "Alphanumerics": c = /^[A-Za-z0-9]+$/; break;
				//只能为字母
				case "letters": c = /^[A-Za-z]+$/; break;
				//正整数
				case "int": c = /^[0-9]*[0-9][0-9]*$/; break;
				//数字
				case "num": c = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/; break;
				//邮件
				case "email": c = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/; break;
				//座机
				case "tel": c = /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?$/; break;
				//手机
				case "phone": c = /^1[3-9]{1}[0-9]{9}$/; break;
				//URL链接地址
				case "url": c = /^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/; break;
				//邮政编码
				case "postcode": c = /^[1-9][0-9]{5}$/; break;
				//经度
				case "longitude": c = /^-?(?:(?:180(?:\.0{1,5})?)|(?:(?:(?:1[0-7]\d)|(?:[1-9]?\d))(?:\.\d{1,5})?))$/; break;
				//纬度
				case "latitude": c = /^-?(?:90(?:\.0{1,5})?|(?:[1-8]?\d(?:\.\d{1,5})?))$/; break;
				//身份证
				case "idcard": c = /^(\d{11}|\d{14})\d{3}?(\d|X|x)$/; break;
				//QQ
				case "qq": c = /^[1-9]*[1-9][0-9]*$/; break;
			};
			d = new RegExp(c).test(a);
			if(b == "telPhone"){
				d = private.fn.validate(a, "tel");
				if(!d){ d = private.fn.validate(a, "phone"); }
			}
			if(b == "Alphanumerics"){
				a = a.substring(0, 1);
				if(!isNaN(a)){ d = false; }
			}
			return d;
		}
	};
	//函数体对象
	var fn = {
		//页面初始化加载数据
		loadInit: function(a){
			if(a != undefined){ $.extend(public.conf, a); }
			fn.inpEach();
			fn.initBubble();
		},
		/**
		 * init bubble
		 */
		initBubble: function(){
			var a, b, c, d, e, f, g, h, i, j, k, l;
			a = private.conf;
			b = public.conf;
			l = b.layout;
			f = private.style;
			g = a.darkColor = b.color;
			h = a.lightColor = SJ.getColor("light", b.color, 220);
			i = a.fontColor = SJ.getColor("dark", b.color, 180);
			c = [];
			c.push('<span class="bMain">');
			c.push('	<div class="bLab">');
			c.push('		<div class="bLab1 bLabB"></div>');
			c.push('		<div class="bLab2 bLabB"></div>');
			c.push('		<div class="bLabel">*</div>');
			c.push('		<div class="bLab2 bLabB"></div>');
			c.push('		<div class="bLab1 bLabB"></div>');
			c.push('	</div>');
			c.push('	<div class="bSpan">');
			for(d=1; d<=9; d++){
				e = d<6?d:10-d;
				//添加尖角元素
				c.push('<b class="bb'+(e)+'"></b>');
				//添加对应尖角样式
				if(l == "top"){					
					f["bb"+e] = {
						font_size: "0", height: (e)+"px", width: "1px", float: "left", background: h,
						margin_top: (9-e)+"px", border_bottom: "1px solid "+h, border_top: "2px solid "+g
					};
				}
				else if(l == "right"){
					f["bb"+e] = {
						font_size: "0", width: (e)+"px", height: "1px", float: "left", background: h,
						margin_right: (9-e)+"px", border_right: "1px solid "+h, border_left: "2px solid "+g
					};
				}
				else if(l == "bottom"){
					f["bb"+e] = {
						font_size: "0", height: (e)+"px", width: "1px", float: "left", background: h,
						border_top: "1px solid "+h, border_bottom: "2px solid "+g
					};
				}
				else if(l == "left"){
					f["bb"+e] = {
						font_size: "0", width: (e)+"px", height: "1px", float: "left", background: h,
						margin_left: (9-e)+"px", border_right: "1px solid "+h, border_left: "2px solid "+g
					};
				}
			}
			c.push('	</div>');
			c.push('</span>');
			a.bubble = c.join("\r\n");
			//设置关于颜色的样式
			j = f.bLabB = {};
			j.border_top = "2px solid "+g;
			j.border_bottom = "2px solid "+g;
			j.background = g;
			f.bLabel.border_top = "2px solid "+g;
			f.bLabel.border_bottom = "2px solid "+g;
			f.bLabel.background = h;
			f.bLabel.color = i;			
			return a.bubble;
		},
		/**
		 * input each
		*/
		inpEach: function(){
			var c, d, e, f, g, h, i, j, k, l;
			j = private.conf;
			k = public.conf;
			//必填文本框背景图片地址
			g = j.inputBg;
			$(k.forms).find(k.element).each(function(a, b){
				b = $(b);
				//获取元素的type和nodeName
				h = b.attr("type"), i = b.get(0).nodeName.toUpperCase();
				if(h == "text" || h == "password" || i == "TEXTAREA"){
				//	b.css("width", (parseInt(b.css("width"))-10)+"px").css("padding-right", "10px");
					//获取水印文字
					c = b.attr("alt");
					//添加水印
					if(c) SJ.Component["JWatermark"](b, c);
					//如果为textarea，并且有maxlength
					if(i == "TEXTAREA"){
						l = b.attr("maxlength");
						if(l) SJ.limitLength(b);
					}
					//是否为必填项
					d = b.hasClass(j.mClass);
					//获取文本框的宽和高
					e = parseInt(b.width()), f = parseInt(b.height());
					if(d && k.isBg){
						b.css("background", "url("+g+") no-repeat 0 0 #BEDAD5").css("background-position", (e-1000+10)+"px "+((f/2)-3)+"px");
					}
				}
			});
		},
		/**
		 * input event
		 * @param a.event object 当前事件对象
		 * @param b.element object 当前元素对象
		 */
		inpEvent: function(a, b){
			var c, d, e, blurs, keyups;
			b = b ? b : $(this);
			c = private.conf;
			d = public.conf;
			e = 0;
			if(b.attr("type")!="radio" && b.attr("type")!="checkbox"){
				window.clearTimeout(private.conf.thread); private.conf.thread = null;
				//通过blur事件函数，显示验证错误消息
				fn.blurEvent(b, false);
				blurs = function(){
					if(b.val() == ""){ fn.focusEvent(b); }
					if(e == 0){ fn.blurEvent(b, true); e++; }
					if(!b.hasClass('errors')){
						var url = b[0].getAttribute('async');
						var vals = b.val();
						var keys = b.attr('key');
						var oldval = b.attr("oldval");
						var tag = true;
						if(url){
							if(vals != keys){
								var data = {};
								data[b.attr('name')] = vals;
								if(oldval != null && oldval != undefined){
									data["oldval"] = oldval;
									if(oldval == vals){
										tag = false;
									}
								}
								if(tag){
									$.ajax({
										url: url,
										type: 'POST',
										data: data,
										dataType: 'json',					
										timeout: 60000,
										error: function(){alert('Error loading PHP document');},					
										success: function(result){
											if(result.msg != "-1"){ b.attr('key', vals).attr('alt', result.msg); fn.error(b, result.msg); }
										} 
									});
								}
							
							}
							else{ fn.error(b, b.attr('alt')); }
						}
					}
				};
				b.unbind("blur").blur(blurs);
				//通过keyup时时验证
				keyups = function(){fn.blurEvent(b, false);};
				b.unbind("keyup").keyup(keyups);
			}
		},
		/**
		 * input focus
		 * @param a.element object 当前元素对象
		 */
		focusEvent: function(a){
			var b= private.conf;
			a = a ? a : $(this);
			//if(!b.border){ b.border = a.css("border"); }
			b.border = "1px solid "+public.conf.focusColor;
			a.css("border", b.border).removeClass("errors");
			a.prev("span.bMain").remove();
		},
		/**
		 * input blur
		 * @param a.element object 当前元素对象
		 * @param h.是否显示提示消息
		 */
		blurEvent: function(a, h){
			var b, c, d, e, f, g;
			b = a.val();
			//去掉开始和结束的空格
			b = b.replace(private.regExp.R002, "").replace(private.regExp.R003, "");
			c = a.hasClass(private.conf.mClass);
			if(c){
				if(b){ fn.regVerfier(a, b, h); }
				else{ fn.error(a, lang.S001); h ? a.prev("span.bMain").remove() : ""; }
			}
			//如果非必填，有其他验证
			d = private.conf.verClass;
			for(e=0, f=d.length; e<f; e++){
				g=d[e];
				if(a.hasClass(g)){ if(b){ flag = fn.regVerfier(a, b, h); } }else{ g = null; }
			}
		},
		/**
		 * input verfier error
		 * @param a.element object 当前元素对象
		 * @param b.error content 错误消息内容
		 */
		error: function(a, b){
			var c, d, e;
			c = private.conf;
			//e = a.next("div.jSelPanel");
			//a = e.length == 0 ? a : e;
			a.addClass("errors");
			f = a.attr("type");
			if(f!="radio"&&f!="checkbox"){
				d = { border: "1px solid "+c.darkColor };
				private.fn.setStyle(a, d);
			}
			if(b){ fn.showBubble(a, b); }
		},
		len: function(s) {
		 var l = 0;
		 var a = s.split("");
		 for (var i=0;i<a.length;i++) {
		  if (a[i].charCodeAt(0)<299) {
		   l++;
		  } else {
		   l+=2;
		  }
		 }
		 return l;
		},
		/**
		 * verfier error, show message
		 * @param a.element object 当前元素对象
		 * @param b.error content 错误消息内容
		 * @param isSub：是否为提交，如果为提交，不显示提示消息
		 */
		showBubble: function(a, b, isSub){
			//首先移除提示消息
			a.prev("span.bMain").remove();
			if(isSub != true){
				var c, d, e, f, g, h, i, j, k, l, m;
				b = b ? b : lang.S000;
				c = private.conf;
				d = private.style;
				e = public.conf;
				f = $(c.bubble);
				var label = f.find("div.bLabel").html(b);
				if(e.layout == "top"){ d.bSpan.top = "-9px", d.bSpan.left = "10px"; }
				if(e.layout == "right"){ d.bSpan.top = "10px", d.bSpan.left = "0px"; }
				if(e.layout == "bottom"){ d.bSpan.top = "24px", d.bSpan.left = "10px"; }
				if(e.layout == "left"){ d.bSpan.top = "10px", d.bSpan.left = "-9px"; }
				fn.setElemTL(a, f.find(".dMain"), d.bMain);
				//设置样式
				for(g in d){
					h = f.find("."+g);
					h = h.length > 0 ? h : f;
					private.fn.setStyle(h, d[g]);
				}
				a.before(f);
				m = b.length*13;
				f.css("width", (m+9)+"px");
				//2012-10-14 兼容IE文档模式样式
				var bb = f.find(".bSpan");
				if((bb.find(".bb1").get(0).offsetHeight)>3){
					bb.css("top", (parseInt(bb.css("top"))-4)+"px");
					label.css("line-height", (parseInt(label.css("line-height"))-4)+"px");
					bb.find(".bb1").css("height", "4px");
					bb.find(".bb2").css("height", "5px");
					bb.find(".bb3").css("height", "6px");
					bb.find(".bb4").css("height", "7px");
					bb.find(".bb5").css("height", "8px");
				}
				//禁止选择内容	
				SJ.disableSelection(f.get(0));
			}
		},
		//设置提示框的坐标
		setElemTL: function(a, b, f){
			var c, d, e, g, h, i;
			c = private.style;
			i = public.conf;
			d = SJ.getElementTL(a.get(0));
			g = (d.top)+"px", h = (d.left)+"px";
			if(i.layout == "top"){ g = (d.top+a.height()+40)+"px", h = (d.left)+"px"; }
			else if(i.layout == "right"){ g = (d.top+a.height())+"px", h = (d.left)+"px"; }
			else if(i.layout == "bottom"){ g = (d.top)+"px", h = (d.left)+"px"; }
			else if(i.layout == "left"){ g = (d.top+(30))+"px", h = (d.left+a.width()+10)+"px"; }
			f = f ? f : {};
			//f.left = h;
			private.fn.setStyle(b, f);
		},
		/**
		 * regexp verfier
		 * @param b.element object 当前元素对象
		 * @param val.element value 元素的value值字符串
		 * @param isSub：是否为提交，如果为提交，不显示提示消息
		 */
		regVerfier: function(b, val, isSub){
			var e, f, g, h, h1, i, j, k, l, m, hh;
			e = private.conf.verClass;
			//验证长度
			i = parseInt(b.attr("minlength"));
			hh = h = fn.len(val) < i ? false : true;
			if(!h){ fn.error(b); fn.showBubble(b, "至少需要"+i+"个字符", isSub); hh=false; return false; }
			i = parseInt(b.attr("maxlength"));
			hh = h = fn.len(val) > i ? false : true;
			if(!h){ fn.error(b); fn.showBubble(b, "最多只需"+i+"个字符", isSub); hh=false; return false; }
			
			for(f=0, g=e.length; f<g; f++){
				h1=e[f];
				if(b.hasClass(h1)){ break; }else{ h1 = null; }
			}
			if(h1){
				i = lang[h1];
				h = private.fn.validate(val, h1);
				if(!h){ fn.error(b); fn.showBubble(b, i, isSub); }
			}
			//验证选择个数
			if(b.attr("type") == "checkbox"){
				k = $(public.conf.forms).find("input[name='"+b.attr("name")+"']");
				k.eq(0).prev("span.bMain").remove();
				l = 0;
				k.each(function(){ this.checked ? l++ : ""; });
				i = parseInt(k.eq(0).attr("minlength"));
				h = l < i ? false : true;
				if(!h){ fn.error(k.eq(0)); fn.showBubble(k.eq(0), "至少需要选择"+i+"项", isSub);hh=false; }
				i = parseInt(k.eq(0).attr("maxlength"));
				if(i > 0){
					h = l > i ? false : true;
					if(!h){ fn.error(k.eq(0)); fn.showBubble(k.eq(0), "最多只能选择"+i+"项", isSub);hh=false; }
				}
			}
			//验证关联元素值是否相等
			i = b.attr("equalto");
			if(i){
				var key = b.attr("key");
				i = $(public.conf.forms).find(i);
				h = i.length == 0 ? false : true;
				if(h){
					if(key){
						var tFlag = false;
						var vals = i.val();
						vals = vals ? vals : i.html();
						vals = vals ? vals : 999;
						var str = key == ">" ? "大于" : key == ">=" ? "大于等于" : key == "<=" ? "小于等于" : "小于";
						eval("if(parseInt(b.val())"+key+"parseInt(vals)){ tFlag = true; }");
						if(!tFlag){ fn.error(b); fn.showBubble(b, "值必须"+str+vals, isSub);hh=false; }
					}
					else if(b.val() != i.val()){ fn.error(b); fn.showBubble(b, "与新密码不相符", isSub);hh=false; }
				}
			}
			if(!hh) h = hh;
			//如果验证通过，关闭提示；否则，从新让元素获取焦点
			if(h){ fn.focusEvent(b); }
			return h;
		},
		/**
		 * submit verfier
		 */
		subVerfier: function(){
			var c, d, e, f, g, h, i, j, k, l, m;
			c = private.conf;
			i = public.conf;
			f = 0;
			k = [];
			$(i.forms).find(i.element).each(function(a, b){
				b = $(b);
				//获取当前项的value
				e = b.val();
				//去掉开始和结束的空格
				e = e.replace(private.regExp.R002, "").replace(private.regExp.R003, "");
				//获取元素的type
				j = b.attr("type");
				//判断是否为必填项
				d = b.hasClass(c.mClass);
				//如果是必填项，则需要判断用户是否输入数据
				if(d){
					if(j == "radio" || j == "checkbox"){ k.push(b.attr("name")); }
					//if(!e){ fn.error(b, lang.S001); ++f; }
					if(!e){ fn.error(b); ++f; }
					else{
						//先移除气泡
						b.prev("span.bMain").remove();
						g = fn.regVerfier(b, e, true); if(g == false) ++f;
					}
				}
				//否则，再判断是否符合配置的内容格式
				else{ if(e){ g = fn.regVerfier(b, e, true); if(g == false) ++f; } }
			});
			k = SJ.removeRepeat(k);
			for(a=0, b=k.length; a<b; a++){
				e = $(i.forms).find("input[name='"+k[a]+"']");
				g = false;
				e.each(function(){ if(this.checked){ g = true; } });
				if(!g){ fn.error(e.eq(0), lang.S001); ++f; }
			}
			f = f == 0 ? true : false;
			f = i.isData && f ? fn.getSubData() : f;
			return f;
		},
		/**
		 * get submit data
		*/
		getSubData: function(){
			var a, b, c, d, e, f, g, h, i, j, k, l, m;
			a = public.conf;
			b = $(a.forms);
			c = [];
			//遍历form
			b.each(function(index, element){
				element = $(element);
				//获取input
				e = element.find("input");
				//获取select
				f = element.find("select");
				//获取textarea
				g = element.find("textarea");
				if(e.length>0||f.length>0||g.length>0){
					//d.当前表单下的数据JSON对象, j.用于判断同一个分组的radio或checkbox
					d = {}, j = "";
					//遍历input
					e.each(function(ind, ele){
						ele = $(ele);
						//获取当前元素value
						m = ele.val();						
						//去掉开始和结束的空格
						m = m.replace(private.regExp.R002, "").replace(private.regExp.R003, "");
						//获取当前元素id，用作key
						h = ele.attr("id");
						//如果没有id，就为该标签名加index
						h = h ? h : ele.get(0).nodeName+ind;
						//获取标签的type，用于判断标签类型
						i = ele.attr("type");
						if(i == "radio" || i == "checkbox"){
							//获取name，该key用name代替
							k = ele.attr("name");
							j = k != j ? k : j;
							if(k == j){
								//获取是否为选中
								l = ele.attr("checked");
								if(l){ d[k] = d[k] ? d[k]+","+m : m; }
							}
						}
						else if(i == "text" || i == "password" || i == "hidden"){ d[h] = m; }
					});
					//遍历select
					f.each(function(ind, ele){
						ele = $(ele);
						h = ele.attr("id");
						h = h ? h : ele.get(0).nodeName+ind;
						d[h] = ele.val();
						//去掉开始和结束的空格
						d[h] = d[h].replace(private.regExp.R002, "").replace(private.regExp.R003, "");
					});
					//遍历textarea
					g.each(function(ind, ele){
						ele = $(ele);
						h = ele.attr("id");
						h = h ? h : ele.get(0).nodeName+ind;
						d[h] = ele.val();
						//去掉开始和结束的空格
						d[h] = d[h].replace(private.regExp.R002, "").replace(private.regExp.R003, "");
					});
					if(d!={}){ c.push(d); }
				}
			});
			return c;
		}
	};
	//函数体操作对象
	var handle = {
		//初始化需要初始化的函数
		fnInit: function(a){
			//初始化页面加载参数
			fn.loadInit(a);
		},
		//绑定需要绑定的事件
		bindEvent: function(){
			var a = public.conf, m, b, c;
			$(a.forms).find(a.element).unbind("focus").focus(fn.inpEvent);
			$(window).resize(function(){
				m = $(".bMain");
				m.each(function(index, element){
					element = $(element);
					b = element.css("position");
					if(b == "absolute"){
						c = element.next("input");
						c = c.length == 0 ? element.next("textarea") : c;
						if(c.length>0){ fn.setElemTL(c, element); }
					}
				});
			});
		}
	};
	//返回对象(该对象中一般只包括一个init函数对象，根据需要，可以自行添加)
	var callBack = {
		init: function(a){
			handle.fnInit(a);
			handle.bindEvent();
		},
		verfier: function(a){
			if(a != undefined){ $.extend(public.conf, a); }
			var errors = $(public.conf.forms).find('.errors');
			var flag = true;
			fn.initBubble();
			var b = fn.subVerfier();
			if(b == false){
				var element = $(".errors").eq(0);
				element.focus();
			}
			errors.eq(0).blur();
			if(errors.length>0){ fn.error(errors.eq(0), errors.eq(0).attr('alt')); b = false; }
			return b;
		},
		showBubble: function(a, b, c, d){
			var e = typeof(c);
			if(e != 'object'){ d = c; c = null; }
			handle.fnInit(c); handle.bindEvent(); fn.showBubble(a, b);
			if(d) setTimeout(function(){ a.prev("span.bMain").remove(); }, d);
		}
	};
	return callBack;
};
//$(function(){ SJ.Component["JVerfier"]().init(); });