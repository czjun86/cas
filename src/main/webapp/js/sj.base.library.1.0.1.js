/**----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// E-mail：407666067@QQ.com
//
// 文件名：sj.base.library.1.0.1.js
// 文件功能描述：JS的一些公共函数，以及一些封装
// 创建标识：2012年10月29日 10:10:21
//
// 修改标识：
// 修改描述：
//----------------------------------------------------------------*/
(function(_window){
	
	//遵循变量先定义，后使用原则，定义相关变量
	var private, fn, Main, elementHandleFn, objectLib, stringLib, Tween;
	
	/**
	 * 私有参数对象
	 */
	private = {
		//config
		conf: {
			//皮肤颜色
			color: "#397817",
			//Http对象
			xmlHttp: null
		},
        //函数用到的存储数据的临时全局变量对象
        tempData: {
			//fn.findElement函数的结果(每次调用执行操作时需要清空)
            findElem: [],
			//存放获取所有的父级的对象
            pNode: [],
			//存放获取所有的子级的对象
            cNode: []
        },
		//通过绑定的事件库(每次调用执行操作时需要清空)
        eventLib: [],
        //事件名称库
        eventName: [
			"click", "keydown", "keyup", "keypress", "mousedown", "mouseup", "focus", "blur",
			"mouseover", "mouseout", "mousemove", "select", "dblclick", "change", "load",
			"resize", "selectstart", "contextmenu"
		],
        //Cookie对象相关参数
        cookie: {
			//cookie保存天数
            day: 7
        },
        //正则表达式库
        regExp: {
			//十六进制颜色值的正则表达式
            R000: /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/,
			//动态导入js、css方法中用到的一个正则
            R001: /^\s|\s$/g,
			//去掉字符串中间的空格所用的正则
            R002: /[ ]/g,
			//去掉字符串开始的空格
            R003: /(^\s*)/g,
			//去掉字符串结束的空格
            R004: /(\s*$)/g,
			//是否为RGB颜色值
			R005: /^#([\da-f]{2})([\da-f]{2})([\da-f]{2})$/i
        },
		//Ajax
		ajax: {
			'-1': "未初始化!",
			'0' : "请求失败!",
			'1': "载入中...",
			'2': "载入完成!",
			'3': "交互中...",
			'4': "交互完成,正在等待服务器响应!",
			'100': "客户必须继续发出请求!",
			'101': "客户要求服务器根据请求转换HTTP协议版本!",
			'200': "操作成功!",
			'201': "发现新文件的URL!",
			'202': "接受和处理,但处理未完成!",
			'203': "返回信息不确定或不完整!",
			'204': "请求收到,但返回信息为空!",
			'205': "服务器完成了请求,用户代理必须复位当前已经浏览过的文件!",
			'206': "服务器已经完成了部分用户的GET请求!",
			'300': "请求的资源可在多处得到!",
			'301': "删除请求数据!",
			'302': "在其他地址发现了请求数据!",
			'303': "建议客户访问其他URL或访问方式!",
			'304': "客户端已经执行了GET,但文件未变化!",
			'305': "请求的资源必须从服务器指定的地址得到!",
			'306': "前一版本HTTP中使用的代码,现行版本中不再使用!",
			'307': "申明请求的资源临时性删除!",
			'400': "请求发生错误!",
			'401': "请求授权失败!",
			'402': "保留有效ChargeTo头响应!",
			'403': "请求不允许!",
			'404': "没有发现文件,查询或URl!",
			'405': "用户在Request-Line字段定义的方法不允许!",
			'406': "请求资源不可访问!",
			'407': "代理服务器未得到授权!",
			'408': "请求超时!",
			'409': "当前资源状态发生错误,请求被终止!",
			'410': "服务器上不再有此资源且无进一步的参考地址!",
			'411': "服务器拒绝用户定义的Content-Length属性请求!",
			'412': "一个或多个请求头字段在当前请求中错误!",
			'413': "请求的资源超载!",
			'414': "请求的资源URL长于服务器允许的长度!",
			'415': "请求资源不支持请求项目格式!",
			'416': "请求中包含Range请求头字段,在当前请求资源范围内没有range指示值,请求也不包含If-Range请求头字段!",
			'417': "服务器不满足请求Expect头字段指定的期望值,如果是代理服务器,可能是下一级服务器不能满足请求!",
			'500': "服务器产生内部错误!",
			'501': "服务器不支持请求的函数!",
			'502': "服务器暂时不可用!",
			'503': "服务器过载或暂停维修!",
			'504': "服务器超时!",
			'505': "服务器不支持或拒绝支请求头中指定的HTTP版本!"
		}
	};
	
	/**
	 * 基础函数库
	 */
	fn = {
		/**
		 * 判断一个对象是否为数组
		 * @param a <Object> 判断的对象
		 * @return <Boolean> 返回是否为数组（true||false）
		 * @remark 未使用第三方插件代码
		 */
		isArray: function (a) {
			/// <summary>判断一个对象是否为数组</summary>
			/// <param name="a" type="Object">判断的对象</param>
			/// <returns type="Boolean" />返回是否为数组-true/false
			return Object.prototype.toString.call(a) === "[object Array]";
		},
		/**
		 * 判断是否为json对象
		 * @param obj <Object> 需要判断的对象
		 * @return <Boolean> 是否为JSON（true||false）
		 * @remark 未使用第三方插件代码
		 */
		isJson: function(obj){
			return (typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length);
		},
		/**
		 * 移除html标签
		 * @param a <String> 需要移除html的字符串
		 * @return <String> 去除html后的字符串
		 * @remark 未使用第三方插件代码
		 */
		removeHtml: function (a) {
			/// <summary>移除html标签</summary>
			/// <param name="a" type="String">需要移除html的字符串</param>
			/// <returns type="Object" />返回去除html后的字符串
			return a.replace(/<[^>].*?>/g, "").replace(/\r\n/img, "").replace(/\n/img, "").replace(/\r/img, "");
		},
		/**
		 * 判断一个元素对象（默认为浏览器对象）是否出现滚动条
		 * @param a <Object> 元素对象
		 * @return <JSON> {x: 下(true:有 false:无), y: 右(true:有 false:无)}
		 * @remark 未使用第三方插件代码
		 */
		isScroll: function (a) {
			/// <summary>判断一个元素对象（默认为浏览器对象）是否出现滚动条</summary>
			/// <param name="a" type="Object">元素对象</param>
			/// <returns type="JSON">{x: 下(true:有 false:无), y: 右(true:有 false:无)}
			//定义函数内部变量
			var b, c, d, e, f, g, h, i;
			//初始化变量值
			b = a ? [a] : [document.documentElement, document.body], c = false, d = false;
			for (e = 0, f = b.length; e < f; e++) {
				g = b[e];
				//获取scrollLeft
				h = g.scrollLeft;
				g.scrollLeft += h > 0 ? -1 : 1;
				g.scrollLeft !== h && (c = c || true);
				g.scrollLeft = h;
				//获取scrollTop
				i = g.scrollTop;
				g.scrollTop += i > 0 ? -1 : 1;
				g.scrollTop !== i && (d = d || true);
				g.scrollTop = i;
			}
			//删除临时变量
			delete b, delete e, delete f, delete g, delete h, delete i;
			return { x: c, y: d };
		},
		/**
		 * 获取滚动条距顶部的距离和距左边的距离
		 * @return <JSON> {left: 横向滚动条距左边的距离, top: 纵向滚动条距上边的距离}
		 * @remark 未使用第三方插件代码
		 */
		getScroll: function () {
			/// <summary>判断浏览器是否出现滚动条</summary>
			/// <returns type="JSON">{x: 横向滚动条距左边的距离, y: 纵向滚动条距上边的距离}
			//定义函数内部变量
			var x, y;
			if (window.pageYOffset) {
				x = window.pageXOffset; y = window.pageYOffset;
			}
			else if (document.compatMode && document.compatMode != 'BackCompat') {
				x = document.documentElement.scrollLeft; y = document.documentElement.scrollTop;
			}
			else if (document.body) {
				x = document.body.scrollLeft; y = document.body.scrollTop;
			}
			return {left: x, top: y};
		},
		/**
		 * 获取浏览器的宽和高(可见区域，不包括滚动条)
		 * @return <JSON> {width: 宽, height: 高}
		 * @remark 未使用第三方插件代码
		 */
		clientWH: function () {
			/// <summary>获取浏览器的宽和高(可见区域，不包括滚动条)</summary>
			/// <returns type="JSON">{width: 宽, height: 高}
			//定义函数内部变量
			var a, b, c;
            //初始化变量值
            a = 0, b = 0;
            if (self.innerHeight && self.innerWidth) { a = self.innerWidth, b = self.innerHeight; }
            else { c = document.documentElement; a = c.clientWidth, b = c.clientHeight; }
            //删除临时变量
            delete c;
            return { width: a, height: b };
		},
		/**
		 * 获取浏览器的宽和高(可操作区域，包括滚动条) 
		 * @return <JSON> {width: 宽, height: 高}
		 * @remark 未使用第三方插件代码
		 */
		scrollWH: function () {
			/// <summary>获取浏览器的宽和高(可操作区域，包括滚动条)</summary>
			/// <returns type="JSON">{width: 宽, height: 高}
			//定义函数内部变量
			var b, c, d, e, height, width;
			b = document.documentElement.clientHeight;
			c = document.body.offsetHeight;
			d = document.documentElement.offsetHeight;
			e = document.body.scrollHeight;
			height = c >= b ? c : b;
			height = height < d ? d : height;
			height = height < e ? e : height;
			b = document.documentElement.clientWidth;
			c = document.body.offsetWidth;
			d = document.documentElement.offsetWidth;
			e = document.body.scrollWidth;
			width = c >= b ? c : b;
			width = width < d ? d : width;
			width = width < e ? e : width;
			return {width: width, height: height};
		},
		/**
		 * 事件冒泡
		 * @param params <Array>
			[
				{nodeName:标签名, className:唯一标识类名, fn:函数名（event, element[, 自定义参数]）, params:自定义参数[json对象]}
			]
		 * @param events <String> 事件名称
		 * @param element <Object> 触发事件的元素
		 * @remark 已使用第三方插件代码（JQuer）
		 * @remark 未使用第三方插件代码
		 */
		eventBubbling: function(params, events, element){
			var a, b, c, d, f, g, h, i, j, k, l, m, n, o, p;
			var callBack = {
				install: function(e){
					e = e || window.event;
					a = e.target || e.srcElement;
					b = a.nodeName;
					c = a.className;
					o = a.type;
					g = params.length;
					for(d=0; d<g; d++){
						h = params[d];
						p = true;
						try{ if(h.type){ p = h.type.indexOf(o)>-1 ? true : false; } }catch(ex){ p = false; break; }
						if(h.nodeName == ""){ b = ""; }
						if(h.nodeName.toUpperCase() == b && p){
							i = h.className, m = false, j = a;
							if(i != ""){
								i = i.split(",");
								for(k=0, l=i.length; k<l; k++){
									if(fn.hasClass(j, i[k])){
										n = typeof(h.fn) == 'string' ? eval(h.fn+'(e, j, '+h.params+');') : h.fn(e, j, h.params);
										m = true;
										break;
									}
								}
							}
							else{
								n = typeof(h.fn) == 'string' ? eval(h.fn+'(e, j, '+h.params+');') : h.fn(e, j, h.params);
								m = true;
								break;
							}
							if(m){ break; }
						}
					}
					return n;
				}
			};
			if(events){
				element = element?element:document.body;
				var evFn = function(event){return callBack.install(event);};
				fn.removeEvent(element, events);
				fn.bindEvent(element, events, evFn);
			}
			return callBack;
		},
		/**
		 * 创建样式表
		 * @param a <String> 样式表字符串
		 * @param b <String> 给style标签添加一个指定的ID
		 * @remark 未使用第三方插件代码
		 */
		createStyleSheet: function(a, b){
			//定义函数内部变量
			var c, d, e;
			e = function(){
				d = document.createElement("style");
				d.id = b;
				d.type = "text/css";
				document.getElementsByTagName("head").item(0).appendChild(d);				
				try{ document.getElementById(b).styleSheet.cssText = a; }
				catch(ex){ document.getElementById(b).innerHTML=a; }
			};
			b = b ? b : "***";
			d = document.getElementById(b);
			//创建一个style标签
			if(!d){ e(); }
		},
        /**
		 * 创建cookie
		 * @param a <String> cookie名称
		 * @param b <Object> cookie值
		 * @param c <Int> cookie保存天数(选填，默认为7天)
		 * @remark 未使用第三方插件代码
		 */
        setCookie: function (a, b, c) {
            /// <summary>创建元素</summary>
            /// <param name="a" type="String">cookie名称</param>
            /// <param name="b" type="String">cookie值</param>
            /// <param name="c" type="Int">cookie保存天数(选填，默认为7天)</param>
            var exp = new Date();
            exp.setTime(exp.getTime() + (c ? c : private.cookie.day) * 24 * 60 * 60 * 1000);
            document.cookie = a + "=" + escape(b) + ";expires=" + exp.toGMTString();
            //删除临时变量
            delete exp;
        },
        /**
		 * 读取cookie
		 * @param a <String> cookie名称
		 * @return <Object> cookie值
		 * @remark 未使用第三方插件代码
		 */
        getCookie: function (a) {
            /// <summary>读取cookie</summary>
            /// <param name="a" type="String">cookie名称</param>
            /// <returns type="Object">cookie值
            //定义函数内部变量
            var b, c;
            //初始化变量值
            b = new RegExp("(^| )" + a + "=([^;]*)(;|$)");
            c = document.cookie.match(b);
			return c ? unescape(c[2]) : null;
        },
        /**
		 * 删除cookie
		 * @param a <String> cookie名称
		 * @remark 未使用第三方插件代码
		 */
        delCookie: function (a) {
            /// <summary>删除cookie</summary>
            /// <param name="a" type="String">cookie名称</param>
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var b = this.getCookie(a);
            if (b != null) { document.cookie = a + "=" + b + ";expires = " + exp.toGMTString(); }
        },
        /**
		 * 创建一个兼容多浏览器的颜色渐变特效CSS样式字符串
		 * @param a <String> 开始颜色值
		 * @param b <String> 结束颜色值
		 * @param c <Int> 0.由上到下 1.由下到上 2.由左到右 3.由右到左 选填，默认为0
		 * @return <String> CSS样式字符串
		 * @remark 未使用第三方插件代码
		 */
        colStyleCss: function (a, b, c) {
            /// <summary>创建一个兼容多浏览器的颜色渐变特效CSS样式字符串</summary>
            /// <param name="a" type="String">开始颜色值</param>
            /// <param name="b" type="String">结束颜色值</param>
            /// <param name="c" type="Int">0.由上到下 1.由下到上</param>
            /// <returns type="String" />CSS样式字符串
            //定义函数内部变量
            var d, e, f, g, h;
            //初始化变量值
            c = c ? c : 0;
			switch(c){
				case 0: d = fn.toRgb(b); g="top",h=0; break;
				case 1: d = fn.toRgb(a); f=a;a=b;b=f;g="top",h=0; break;
				case 2: d = fn.toRgb(b); g="left",h=1; break;
				case 3: d = fn.toRgb(a); f=a;a=b;b=f;g="left",h=1; break;
			}			
			e = "filter:alpha(opacity=100 finishopacity=100 style=1 startx=0,starty=0,finishx=0,finishy=100)" +
				"progid:DXImageTransform.Microsoft.gradient(startcolorstr=" + a + ",endcolorstr=" + b + ",gradientType="+h+");" +
				"-ms-filter:alpha(opacity=100 finishopacity=100 style=1 startx=0,starty=0,finishx=0,finishy=100)" +
				"progid:DXImageTransform.Microsoft.gradient(startcolorstr=" + a + ",endcolorstr=" + b + ",gradientType="+h+");" +
				"background:-moz-linear-gradient("+g+", " + a + ", rgba(" + d + ",1));" +
				"background:-webkit-gradient(linear, 0 0, 0 "+g+", from(" + a + "), to(rgba(" + d + ",1)));";
            //删除临时变量
            delete a, delete b, delete c, delete d;
            return e;
        },
        /**
		 * 16进制颜色转为RGB格式
		 * @param a <String> 16进制颜色值
		 * @return <String> RGB颜色值
		 * @remark 未使用第三方插件代码
		 */
        toRgb: function (a) {
            /// <summary>16进制颜色转为RGB格式</summary>
            /// <param name="a" type="String">16进制颜色值</param>
            /// <returns type="String" />转换后的RGB颜色值
            //定义函数内部变量
            var b, c, d, e;
            //初始化变量值
            b = a.toLowerCase();
            if (b && private.regExp.R000.test(a)) {
                if (b.length == 4) {
                    c = "#";
                    for (d = 1; d < 4; d++) { c += b.slice(d, d + 1).concat(b.slice(d, d + 1)); }
                    b = c;
                }
                e = [];
                for (d = 1; d < 7; d += 2) { e.push(parseInt("0x" + b.slice(d, d + 2))); }
                b = e.join(",");
            }
            //删除临时变量
            delete a, delete c, delete d, delete e;
            return b;
        },
        /**
		 * 屏蔽右键
		 * @param a <Object> 需要屏蔽右键的元素对象
		 * @param b <Function||String> 右键时执行的函数
		 * @return <Object> 元素对象a
		 * @remark 未使用第三方插件代码
		 */
        shieldRightMenu: function (a, b) {
            /// <summary>屏蔽右键</summary>
            /// <param name="a" type="Object">需要屏蔽右键的元素对象</param>
			/// <param name="b" type="Function||String">右键时执行的函数</param>
            /// <returns type="Object" />当前元素对象 a
			a.oncontextmenu = function (event) {
				b ? typeof(b) == 'function' ? b(event) : eval(b+'('+event+')') : "";
				return false;
			};
            return a;
        },
        /**
		 * 设置元素中的文本不能被选择
		 * @param a <Object> 需要禁止文本选择的元素对象
		 * @param b <String> 鼠标样式 选填，默认为"default"
		 * @return <Object> 元素对象a
		 * @remark 未使用第三方插件代码
		 */
        disableSelection: function (a, b) {
            /// <summary>设置元素中的文本不能被选择</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="String">鼠标样式 选填，默认为"default"</param>
            /// <returns type="Object" />当前元素对象 a
            b = b ? b : "default";
			if (typeof a.onselectstart != "undefined") { a.onselectstart = function(){ return false; }; }
			else if (typeof a.style.MozUserSelect != "undefined") { a.style.MozUserSelect = "none"; }
			a.style.cursor = b;
            return a;
        },
        /**
		 * 单个替换，将字符串a中的b字符串替换成c字符串
		 * @param a <String> 原字符串
		 * @param a <String> 被替换字符串
		 * @param a <String> 替换的字符串
		 * @return <String> 替换后的字符串
		 * @remark 未使用第三方插件代码
		 */
        replaces: function (a, b, c) {
            /// <summary>将字符串a中的b字符串替换成c字符串</summary>
            /// <param name="a" type="String">原字符串</param>
            /// <param name="b" type="String">a中被替换字符串</param>
            /// <param name="c" type="String">替换b的字符串</param>
            /// <returns type="Object" />替换后的字符串 a
            return a.replace(b, c);
        },
        /**
		 * 替换所有
		 * @param a <String> 原字符串
		 * @param b <String> 被替换字符串
		 * @param b <String> 替换字符串
		 * @return <String> 替换后的字符串
		 * @remark js中没有java中的replaceAll()函数,为了达到与java的replaceAll()一样的效果,我们可以用如下代码实现,未使用第三方插件代码
		 */
        replaceAll: function (a, b, c) {
            /// <summary>全部替换</summary>
            /// <param name="a" type="String">原字符串</param>
            /// <param name="b" type="String">被替换字符串</param>
            /// <param name="c" type="String">替换字符串</param>
            /// <returns type="String" />替换后的字符串
            return a.replace(new RegExp(b, "gm"), c);
        },
        /**
		 * 动态加载css,js文件,支持同步和异步
		 * @param a <String> 文件路径(文件全路径，且文件全名)
		 * @param b <Function> 加载完后执行的函数
		 * @param c <Boolean> 如果要加载的文件已经加载过，是否重新加载
		 * @remark 未使用第三方插件代码
		 */
        include: function (a, b, n) {
            /// <summary>动态加载css,js文件,支持同步和异步</summary>
            /// <param name="a" type="String">文件路径(文件全路径，且文件全名)</param>
            /// <param name="b" type="Function">加载完后执行的函数</param>
            //定义函数内部变量
            var k, l, c, d, e, f, g, h, i, j, m;
            //初始化变量值
            m = private.regExp.R001;
			if(typeof(b) == "boolean"){ n = b; b = null; }
			//替换字符(这里不知道是替换什么)
			c = a.replace(m, "");
			//获取后缀
			d = c.split('.');
			//获取后缀，并转换为小写
			e = d[d.length - 1].toLowerCase();
			//判断是css还是js
			f = e == "css" ? "link" : "script";
			i = e == "css" ? { type: "text/css", href: c, rel: "stylesheet", id: c} :
				{ type: "text/javascript", src: c, language: "javascript", defer: "true", id: c };
			//查找当前要加载路径下的文件是否已经加载
			h = document.getElementById(c);
			//判断是否需要重新加载
			if(n == true){ if(h){ h.parentNode.removeChild(h); h = null; } }
			//如果h不为空，还未加载
			if(!h){
				//创建标签
				h = document.createElement(f);
				//设置标签属性
				for (j in i) { h.setAttribute(j, i[j]); }
				//添加标签，加载文件
				document.getElementsByTagName("head")[0].appendChild(h);
				//文件加载后执行函数
				if(b) h.onload = function(){ b(); };
			}
			else{ if(b) b(); }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g, delete h, delete i, delete j, delete k, delete l, delete m;
        },
        /**
		 * 注册命名空间
		 * @param a <String> 空间名(如："xx.yy")
		 * @remark 未使用第三方插件代码
		 */
        namespace: function (a) {
            /// <summary>注册命名空间</summary>
            /// <param name="a" type="String">空间名(如："xx.yy")</param>
            //定义函数内部变量
            var b, c, d, e, f, g;
            //初始化变量值
            b = ".";
            c = a.split(b);
            d = c.length;
            f = "";
            g = "";
            //循环c
            for (e = 0; e < d; e++) {
				if(e!=0) f+=b;
				f+=c[e];
                // 依次创建构造命名空间对象（假如不存在的话）的语句
                // 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
                g += "if(typeof(" + f + ") == 'undefined') " + f + " = new Object();";
            }
            if (g != "") eval(g);
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g;
        },
        /**
		 * 截取字符串，超过指定长度以"..."代替(默认，可自定义)
		 * @param a <String> 需要截取的字符串
		 * @param b <Int> 需要截取字符串的长度
		 * @param c <String> 截取字符串，超过指定长度以"..."代替(默认，可自定义)，该参数就是用户自定义的字符或字符串
		 * @return <String> 截取后的字符串
		 * @remark 未使用第三方插件代码
		 */
        subString: function (a, b, c) {
            /// <summary>截取字符串，超过指定长度以"..."代替(默认，可自定义)</summary>
            /// <params name="a" type="String">需要截取的字符串</params>
            /// <params name="b" type="Int">需要截取字符串的长度</params>
            /// <params name="c" type="String">截取字符串，超过指定长度以"..."代替(默认，可自定义)，该参数就是用户自定义的字符或字符串</params>
            /// <returns type="Object" />截取后的字符串
            return (a.length > b && b) ? (a.substring(0, b) + (c ? c : "...")) : (a);
        },
        /**
		 * 获取一个字符串的字节数
		 * @param a <String> 当前字符串
		 * @return <Int> 字节数
		 * @remark 未使用第三方插件代码
		 */
        getCharByte: function (a) {
            /// <summary>获取一个字符串的字节数</summary>
            /// <params name="a" type="String">当前字符串</params>
            /// <returns type="Int" />字符串的字节数
            //定义函数内部变量
            var b, c, d;
            //初始化变量值
            b = a.length;
            c = 0;
            //循环字符串的每一个字符
            for (d = 0; d < b; d++) { a.charCodeAt(d) < "128" && a.charCodeAt(d) > "0" ? c += 1 : c += 2; }
            //删除临时变量
            delete a, delete b, delete d;
            return c;
        },
        /**
		 * 判断字符是单字节还是双字节
		 * @param a <String> 当前字符串
		 * @return <Int> (-1.单字节 0.双字节 1.单双字节共存)
		 * @remark 未使用第三方插件代码
		 */
        charByte: function (a) {
            /// <summary>判断字符是单字节还是双字节(-1.单字节 0.双字节 1.单双字节共存)</summary>
            /// <params name="a" type="String">当前字符串</params>
            /// <returns type="Int" />-1.单字节 0.双字节 1.单双字节共存
            //定义函数内部变量
            var b, c, d, e, f, g, h;
            //初始化变量值
            b = a.length;
            c = d = e = h = 0;
            //循环字符串每一个字符
            for (f = 0; f < b; f++) {
                //获取单个字符(判断单个字符是单字节还是双字节)
                g = a.charCodeAt(f);
                //单字节等于1，双字节等于2
                g < "128" && g > "0" ? c = 1 : d = 2;
            }
            //求c、d的和
            e = c + d;
            //判断e(1.单字节 2.双字节 3.单双字节共存，进而设置对应的返回值h的值：-1.单字节 0.双字节 1.单双字节共存)
            switch (e) {
                case 1: h = -1; break;
                case 2: h = 0; break;
                case 3: h = 1; break;
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g;
            return h;
        },
        /**
		 * 查找字符串b在字符串a中出现的次数
		 * @param a <String> 当前字符串
		 * @param b <String> 需要判断出现次数的字符串
		 * @param c <Boolean> 是否忽略大小写(true.区分大小写 false.不区分大小写 默认为true)
		 * @return <Int>字符串b在字符串a中出现的次数
		 * @remark 未使用第三方插件代码
		 */
        countSubstr: function (a, b, c) {
            /// <summary>查找字符串b在字符串a中出现的次数,c是否忽略大小写</summary>
            /// <params name="a" type="String">当前字符串</params>
            /// <params name="b" type="String">在a中出现次数的字符串</params>
            /// <params name="c" type="String">是否忽略大小写(true.区分大小写 false.不区分大小写 默认为true)</params>
            /// <returns type="Int" />字符串b在字符串a中出现的次数
            //定义函数内部变量
            var d, e;
            //初始化变量值
            c = c == undefined || c == null ? true : c;
            //构建正则表达式
            d = c == true ? "/" + b + "/g" : "/" + b + "/gi";
            //获取出现的次数
            e = a.match(eval(d) || "").length;
            //删除临时变量
            delete a, delete b, delete c, delete d;
            return e;
        },
		/**
		 * 判断参数a.a数组对象集合中元素的属性a.b是否与a.c相等，如果相等，就添加一个属性及值a.d，并返回添加属性后的对象
		 * @param a <JSON>
		 * @remark 未使用第三方插件代码
		 */
		addObjAttr: function(a){
			var b, c, d, e, f, g, h;
			//需要比较的对象
			b = a.a;
			//需要比较对象中的属性
			c = a.b;
			//需要比较对象中的属性值
			d = a.c;
			//需要添加的属性及值，(格式：a=b或a:b)
			e = a.d.replace(":", "=");
			for(f=0, g=b.length; f<g; f++){ h = b[f]; if(h[c] == d){ eval(h+"."+e); } break; }
			return b;
		},
		/**
		 * 获取颜色
		 * @remark 未使用第三方插件代码
		*/
		getColor: function(key, color, num){
			if(!private.regExp.R005.test(color)){alert( "你传递的颜色不符合规则: #RRGGBB "); return color;}
			var r = parseInt("0x"+ RegExp.$1, 16);
			var g = parseInt("0x"+ RegExp.$2, 16);
			var b = parseInt("0x"+ RegExp.$3, 16);
			switch(key) {
				case "red": r += parseInt(num, 10); break;
				case "green": g += parseInt(num, 10); break;
				case "blue": b += parseInt(num, 10); break;
				case "light": r += parseInt(num, 10); g += parseInt(num, 10); b += parseInt(num, 10); break;
				case "dark": r -= parseInt(num, 10); g -= parseInt(num, 10); b -= parseInt(num, 10); break;
			}
			r = r>255 ? 255 : r<0 ? 0 : r;
			g = g>255 ? 255 : g<0 ? 0 : g;
			b = b>255 ? 255 : b<0 ? 0 : b;
			r = r.toString(16);
			g = g.toString(16);
			b = b.toString(16);
			r = ("00"+r).substr(r.length);
			g = ("00"+g).substr(g.length);
			b = ("00"+b).substr(b.length);
			return "#"+ r + g + b;
		},		
		/**
		 * 获取元素在页面上的坐标
		 * @param a <Element> 需要获取坐标的元素
		 * @return <JSON> {top, left}
		 * @remark 未使用第三方插件代码
		 */
        getElementTL: function (a) {
            /// <summary>获取元素在页面上的坐标</summary>
            /// <param name="a" type="Object">需要获取坐标的元素</param>
            /// <returns type="Object">{top: 坐标y, left: 坐标x}
            //定义函数内部变量
            var b, e, f, g, h, i;
            //初始化变量值
            b = navigator.userAgent.toLowerCase();
            e = { top: 0, left: 0 };
            //如果元素的父级为空或者元素为隐藏的，则返回false
            if (a.parentNode == null || a.style.display == "none") { e = false; }
            if (a.getBoundingClientRect) {
                f = a.getBoundingClientRect();
                //获取滚动条信息
                g = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
                h = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
                e.top = f.top + g, e.left = f.left + h;
            }
            else if (document.getBoxObjectFor) {
                f = document.getBoxObjectFor(a);
                g = a.style.borderLeftWidth ? parseInt(a.style.borderLeftWidth) : 0;
                h = a.style.borderTopWidth ? parseInt(a.style.borderTopWidth) : 0;
                e.top = f.y - h, e.left = f.x - g;
            }
            else {
                e.top = a.offsetTop, e.left = a.offsetLeft, i = a.offsetParent;
                if (i != a) { while (i) { e.top += i.offsetTop, e.left += i.offsetLeft, i = i.offsetParent; } }
                if (b.indexOf("opera") != -1 || (b.indexOf("safari") != -1 && a.style.position == "absolute")) {
                    e.top -= document.body.offsetTop, e.left -= document.body.offsetLeft;
                }
            }
            i = a.parentNode ? a.parentNode : null;
            while (i && i.tagName != "BODY" && i.tagName != "HTML") {
                e.top -= i.scrollTop, e.left -= i.scrollLeft;
                i = i.parentNode ? i.parentNode : null;
            }
            //删除临时变量
            delete a, delete b, delete f, delete g, delete h, delete i;
            return e;
        },
		/**
		 * drag 拖动
		 * @param a.当前事件 event
		 * @param b.当前事件触发对象 element
		 * @param e.当前操作的弹窗主层(需要移动的层)
		 * @param area.在哪个区域拖动
		 * @remark 未使用第三方插件代码
		 */
		Drag: function(a, b, e, area){
			//定义函数内部变量
			var c, d, f, g, h, i, j, k, mt, ml, diffW, diffH, xy;
			e = e ? e : b;
			//获取弹出框的实际宽和高
			j = parseInt(e.offsetWidth), k = parseInt(e.offsetHeight);
			var eStyle = fn.getElementTL(e);
			//获取当前鼠标位置于当前需要拖动元素位置的差值
			f = a.clientX - parseInt(eStyle.left);
			g = a.clientY - parseInt(eStyle.top);
			//获取当前可见区域的宽和高
			if(area){ h = area.offsetWidth, i = area.offsetHeight; }
			else{
				h = document.documentElement.clientWidth || document.body.clientWidth;
				i = document.documentElement.clientHeight || document.body.clientHeight;
			}
			//获取拖动元素的margin-top，并取绝对值
			mt = Math.abs(parseInt(e.style.marginTop));
			mt = mt ? mt : 0;
			//获取拖动元素的margin-left，并取绝对值
			ml = Math.abs(parseInt(e.style.marginLeft));
			ml = ml ? ml : 0;
			//获取滚动条距顶部的距离
			xy = fn.getScroll();
			//获取弹出框在可见区域的最大定位值
			diffW = h - j + ml+xy.left, diffH = i - k + mt + xy.top;
			//拖动函数
			var drag = function(event){
				//定义函数内部变量
				var a, b, c, d, top, left, al=0, at=0, xys, scrol;
				a = event || window.event;
				//获取鼠标当前坐标位置
				b = a.clientX, c = a.clientY;
				if(area){
					xys = fn.getElementTL(area);
					scrol = fn.getScroll();
					al = xys.left + scrol.left, at = xys.top + scrol.top;
				}
				//获取当前需要移动元素需要设置的top和left
				left = b - f - al, top = c - g - at;
				//不允许拖出可见区域，须做一下处理
				left = (left-ml) < 0 ? ml : left>diffW ? diffW : left;
				top = (top-mt) < 0 ? mt : top>diffH ? diffH : top;
				//设置需要移动元素的top和left
				e.style.top = top+"px"; e.style.left = left+"px";
				return false;
			};
			document.onmousemove = drag;
			document.onmouseup = function(){ document.onmousemove = ""; };
		},
		/**
		 * 移除数组相同元素
		 * @param a <Array> 数组对象
		 * @return <Array> 移除相同元素后的数组
		 * @remark 未使用第三方插件代码
		 */
		removeRepeat: function(a){
			var b=[], bFound;
			for(var i=0;i<a.length-1;i++){
				bFound = false;
				for(var j=i-1;j>=0;j--){
					if(a[i]==a[j]){ bFound=true; break; }
				}
				if(!bFound) b[b.length]=a[i]
			}
			return b;
		},
		/**
		 * 解决ie下textarea的maxlength的限制
		 * @param a.需要操作的元素集合
		 * @remark 未使用第三方插件代码
		*/
		limitLength: function(a){
			a = a.length ? a[0] : a;
			var maxLength = a.getAttribute("maxlength");
			maxLength = maxLength ? maxLength : 100;
			var doBeforePaste = function(){ event.returnValue = false; };
			var doKeypress = function(){
				var oTR = document.selection.createRange();
				if(oTR.text.length >= 1) event.returnValue = true;
				else if(this.value.length > maxLength-1) event.returnValue = false;
			};
			var doKeydown = function(event){
				var _obj=this;
				setTimeout(function(){
					if(_obj.value.length > maxLength-1){
						var oTR = window.document.selection.createRange();
						oTR.moveStart("character", -1*(_obj.value.length-maxLength));
						oTR.text = "";
					}
				},1);
			};
			var doPaste = function(){
				event.returnValue = false;
				var oTR = document.selection.createRange();
				var iInsertLength = maxLength - this.value.length + oTR.text.length;
				var sData = window.clipboardData.getData("Text").substr(0, iInsertLength);
				oTR.text = sData;
			};
			//a.onkeydown = doKeydown;
			a.onkeypress = doKeypress;
			a.onbeforepaste = doBeforePaste;
			a.onpaste = doPaste;
		},
		/**
		 * 模拟线程
		 * @param a.线程运行中需要执行的自定义函数
		 * @param b.间隔b毫秒执行一次函数a
		 * @remark 未使用第三方插件代码
		*/
		Thread: function(a, b){
			var c;
			this.run = function(){ c = window.setInterval(function(){ a(); }, b); };
			this.stop = function(){ window.clearInterval(c); c = null; };
		},
		/**
		 * 创建XMLHttpRequest对象
		 * @remark 未使用第三方插件代码
		*/
		createXMLHttpRequest : function(){
			try{
				//code for IE6, IE5
				if(window.ActiveXObject){ private.conf.xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); }
				//code for IE7+, Firefox, Chrome, Opera, Safari
				else if(window.XMLHttpRequest){ private.conf.xmlHttp = new XMLHttpRequest(); }
				//code for xml
				else{ private.conf.xmlHttp=new ActiveXObject("Msxml2.XMLHTTP"); }
			}
			catch(err) { window.onerror = function(){ alert(err); }; }
		},
		/**
		 * 发送XMLHttp请求
		 * @param args1: { method, url, async, data, contentType, dataType, error, complete, success }
		 * @remark 未使用第三方插件代码
		*/
		sendXMLHttpRequest: function(args){
			//创建XMLHttp请求对象
			fn.createXMLHttpRequest();
			//获取XMLHttpRequest对象
			var xmlHttp = private.conf.xmlHttp;
			//打开链接
			xmlHttp.open(args.method, args.url, args.async);
			//修改MIME类别
			xmlHttp.setRequestHeader("Content-Type", args.contentType);
			//监控请求状态
			xmlHttp.onreadystatechange=function(){ fn.httpRequestStateChange(args); };
			//发送请求
			xmlHttp.send(args.data);
		},
		/**
		 * 请求状态监控
		 * @remark 未使用第三方插件代码
		*/
		httpRequestStateChange: function(args){
			var msg, xmlHttp, status, response, ajax, thread;
			xmlHttp = private.conf.xmlHttp;
			status = xmlHttp.readyState;
			ajax = private.ajax;
			thread = new fn.Thread(function(){ xmlHttp.abort();thread.stop(); }, args.timeout);
			msg = ajax[status];
			response = {status: status, msg: msg};
			if(status == 4){
				//完成
				if(args.complete) args.complete(xmlHttp.responseText, response);				
				status = xmlHttp.status;
				msg = ajax[status];
				response = {status: status, msg: msg};
				//成功
				if(status == 200){ if(args.success) args.success(xmlHttp.responseText, response);thread.stop(); }
				//失败
				else{ if(args.error) args.error(xmlHttp.responseText, response);thread.stop(); }
			}
			//启动一个线程，监控当前的timeout
			if(args.timeout){ thread.run(); }
		},
		/**
		 * 添加事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		 * @remark 未使用第三方插件代码
		*/
        addEvent: function (a, b, c) {			
            if(window.attachEvent){ a.attachEvent("on" + b, c); }
            else if(window.addEventListener){ a.addEventListener(b, c, false); }
            else{ a["on" + b] = c; }
			
            //保存该对象事件到库中
            //定义函数内部变量
            var d, e, f, g, h;
            //初始化变量值
            e = true;
            h = private.eventLib;
            //事件库中是否存在该对象的事件
            for (f = 0, g = h.length; f < g; f++) {
                d = h[f];
                if (d.e == a && d.n == b) { e = false; break; }
            }
            if (e) { private.eventLib.push({ e: a, n: b, f: c }); }
        },
		
		/**
		 * 删除事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		 * @remark 未使用第三方插件代码
		*/
        delEvent: function (a, b, c) {
            if (window.detachEvent) { a.detachEvent("on" + b, c); }
            else if (window.removeEventListener) { a.removeEventListener(b, c, false); }
            else { a["on" + b] = null; }			
            //删除事件库中该对象
            //定义函数内部变量
            var d, e, f;
            //初始化变量值
            f = private.eventLib;
            for (e = 0; e < f.length; e++) {
                d = f[e];
                if (d.e == a && d.n == b) { private.eventLib = private.eventLib.slice(0, e); break; }
            }
        },
		/**
		 * 绑定事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		 * @remark 未使用第三方插件代码
		*/
        bindEvent: function (a, b, c) {
            /// <summary>绑定事件</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">事件名称(必填)</param>
            /// <param name="c" type="Function">选填 事件执行的函数，若不填，则是执行该元素的b事件</param>
            /// <returns type="Object" />返回当前元素集合本身a
            c != undefined && c != null ? fn.addEvent(a, b, c) : eval("a." + b + "();");
            return a;
        },
		/**
		 * 移除事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		 * @remark 未使用第三方插件代码
		*/
        removeEvent: function (a, b, c) {
            /// <summary>移除事件</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">事件名称(选填 默认为所有的事件)</param>
            /// <returns type="Object" />返回当前元素集合本身a
			
			b = b ? [b] : private.eventName;
			var eLib = private.eventLib;
			var len1 = b.length, len2 = eLib.length, i, j;
			var name, ev;
			for(i=0;i<len1;i++){
				name = b[i];
				for(j=0;j<len2;j++){
					ev = eLib[j];
					if(a == ev.e && name == ev.n){ fn.delEvent(a, name, ev.f); break; }
				}
			}
			return a;
        },
        /**
         * 查找element元素的class中是否包含hasVal
         * @param {Object} element 要查找class的元素
         * @param {Object} hasVal 要匹配的值
         * @return {Boolean} true 包含，false 不包含
         */
        hasClass: function(element, hasValue){
        	var a, b, c, d, e;
        	//获取element的class
			e = element.className;
        	c = false;
			if(e){
				a = e.split(" ");
				b = a.length;
				for(d=0;d<b;d++){ if(a[d] == hasValue){ c = true; break; } }
			}
        	return c;
        },
        /**
         * 文档加载
         * @param {Object} args 文档加载完成后执行的函数
         */
        ready: function(args){
        	fn.addEvent(window, "load", args);
        },
		/**
		 * Panel
		 * @remark 未使用第三方插件代码
		*/
		Panel: function(a){
			var b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, Panel, Content, Title;
			//Panel相关参数
			b = {
				//标题
				title: "Panel",
				//宽度
				width: null,
				//高度
				height: 120,
				//右上角上的那个收缩按钮，设为false则不显示
				collapsible:true,
				//面板内容
				html: "[空白面板]",
				//在ID上显示面板
				renderTo: document.body,
				//在ID后面显示面板
    			applyTo: null,
				//自动加载url页面内容到面板
				autoLoad: null,
				//自动适应父级宽
				autoParentWidth: false,
				//自动适应父级高
				autoParentHeight: false,
				//边框
				border: null,
				//浮动
				float: null,
				//最小宽度
				minWidth: 100,
				//最小高度
				minHeight: 180
			};
			if(a){ b = Main.extend(b, a); }
			//创建Panel StyleSheet
			(function(){
				c = [];
				d = Main.Attr.pluginName;
				e = Main.Skin;
				f = d+"-Panel-StyleSheet";
				//Main
				c.push(
					'.'+d+'-Panel-Main{'+
						'margin:0px;padding:0px;font-size:13px;font-family:"楷体";color:'+e.darkColor10+';border:1px solid '+e.darkColor1+';'+
					'}'
				);
				//Title Bar
				c.push(
					'.'+d+'-Panel-Main .'+d+'-Panel-TitleBar{'+
						'border-bottom:1px solid '+e.darkColor1+';width:100%;height:25px;float:left;'+
					'}'
				);
				//Title Bar Top
				c.push(
					'.'+d+'-Panel-Main .'+d+'-Panel-TitleBar-Top{'+
						'height:12px;width:100%;float:left;position:relative;top:0px;left:0px;'+SJ.colStyleCss(SJ.lightColor8,SJ.lightColor3)+
					'}'
				);
				//Title Bar Bottom
				//d = SJ.colStyleCss(SJ.lightColor6, SJ.lightColor1);
				c.push(
					'.'+d+'-Panel-Main .'+d+'-Panel-TitleBar-Bottom{'+
						'height:13px;width:100%;float:left;position:relative;top:0px;left:0px;'+SJ.colStyleCss(SJ.lightColor1,SJ.lightColor5)+
					'}'
				);
				//Title Bar Content
				c.push(
					'.'+d+'-Panel-Main .'+d+'-Panel-TitleBar-Content{'+
						'height:100%;float:left;position:relative;z-index:2;top:-25px;left:0px;'+
						'padding-left:5px;line-height:25px;font-weight:bold;'+
						'overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'+
					'}'
				);
				//Panel Content
				c.push(
					'.'+d+'-Panel-Main .'+d+'-Panel-Content{'+
						'float:left;font-size:14px;background:'+e.lightColor16+';'+
					'}'
				);
				//text error
				c.push(
					'.text-error{color:#FF0000;}'
				);
				c = c.join('\n');
				fn.createStyleSheet(c, f);
			})();
			//创建Panel Html
			(function(){
				c = [];
				d = Main.Attr.pluginName;
				h = b.height;
				c.push('<div class="'+d+'-Panel-Main'+(b.id?" "+b.id:'')+'" id="'+(b.id?b.id:'')+'" style="width:'+(b.width)+'px;height:'+(h)+'px;">');
				if(b.title){
					c.push('<div class="'+d+'-Panel-TitleBar">');
					c.push('	<div class="'+d+'-Panel-TitleBar-Top"></div>');
					c.push('	<div class="'+d+'-Panel-TitleBar-Bottom"></div>');
					c.push('	<div class="'+d+'-Panel-TitleBar-Content" style="width:'+(b.width-5)+'px;">'+b.title+'</div>');
					c.push('</div>');
					h = h - 25-1;
				}
				c.push('<div class="'+d+'-Panel-Content'+(b.id?" "+b.id+"-Content":'')+'"');
				c.push(' style="width:'+(b.width)+'px;height:'+(h)+'px;">'+(b.html ? b.html : "")+'</div>');
				c.push('</div>');
				c = c.join('\r\n');
				e = document.createElement("div");
				e.innerHTML = c;
				c = e.childNodes[0];
				f = b.renderTo;
				g = b.applyTo;
				if(f){
					if(typeof(f) == 'string') document.getElementById(f).appendChild(c);
					else f.appendChild(c);
				}
				else if(g){
					if(typeof(g) == 'string') document.getElementById(g).parentNode.appendChild(c);
					else g.parentNode.appendChild(c);
				}
				Panel = c;
				//设置边框
				if(b.border){ c.style.border = b.border; }
				//设置浮动
				if(b.float){ c.style.cssFloat = b.float;c.style.styleFloat = b.float; }
				//设置样式
				if(b.style){ c.style.cssText += b.style; }
				//设置居中，
				if(b.align){ c.style.textAlign = b.align; }
				//在renderTo指定的元素上显示Panel
				if(typeof(d.renderTo) == 'string'){
					
				}
				//获取Content和Title元素对象
				j = c.childNodes;
				for(i=0, l=j.length; i<l; i++){
					k = j[i];
					if(k && k.nodeName != "#text"){
						if(k["className"].indexOf(d+'-Panel-Content')>-1) Content = k;
						else if(k["className"].indexOf(d+"-Panel-TitleBar")>-1){
							m = k.childNodes;
							for(n=0, o=m.length; n<o; n++){
								p = m[n];
								if(p && p.nodeName != "#text"){
									if(p["className"].indexOf(d+"-Panel-TitleBar-Content")>-1) Title = p;
								}
							}
						}
					}
				}
				//自动加载内容到Content
				if(b.autoLoad){
					Content.innerHTML = "自动加载中...";
					SJ.ajax({
						url: b.autoLoad,
						success: function(response, data){ Content.innerHTML = response; },
						error: function(response, data){ Content.innerHTML = '<label class="text-error">消息：'+data.msg+'<br/>状态：'+data.status+'</label>'; }
					});
				}
			})();
			//绑定自适应事件
			(function(){
				var resize = function(){
					//获取可见区域的宽和高
					var client = fn.clientWH();
					//设置Panel的宽和高
					if(b.autoParentWidth) Panel.style.width = (client.width-4+(b.width?b.width:0))+"px";
					if(b.autoParentHeight) Panel.style.height = (client.height-7+(b.height?b.height:0))+"px";
					//获取当前Panel的实际宽和高
					var pw = parseInt(Panel.offsetWidth), ph = parseInt(Panel.offsetHeight);
					//pw = pw<b.minWidth ? b.minWidth : pw;
					//ph = ph<b.minHeight ? b.minHeight : ph;
					//设置Title的宽
					if(b.title) Title.style.width = (pw-5)+"px";
					//设置Content的宽和高
					Content.style.width = (pw<5?pw:pw-2)+"px";
					ph = (b.title ? (ph-25-2-1) : (ph-2-1));
					Content.style.height = (ph)+"px";
					//设置align
					if(b.align) Content.style.textAlign = b.align;
					//设置valign
					if(b.valign == "middle") Content.style.lineHeight = (ph)+"px";
				};
				//执行一次resize函数
				resize();
				//绑定resize事件
				fn.addEvent(window, "resize", resize);
			})();
			//设置返回对象
			this.Panel = Panel;
			this.Content = function(content){ Content.innerHTML = content; };
			this.Title = function(title){ Title.innerHTML = title; };
		},
		/**
		 * 判断浏览器类型
		 * @return <Object> 浏览器类型
		 */
		browser: function () {
			/// <summary>获取浏览器类型及版本</summary>
			/// <returns type="Object" />截取后的字符串
			var Sys = {}, ua = navigator.userAgent.toLowerCase(), s;
			(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
				(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
				(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
				(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
				(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
			return Sys;
		},
		/**
		 * 判断浏览器类型是否为360
		 * @return <Boolean>
		 */
		is360: function () {
			var a = false;
			if (window.navigator.userAgent.toLowerCase().indexOf("360se") >= 1){ a = true; }
			if (window.external && window.external.twGetRunPath) { 
				var b = external.twGetRunPath();
				if (b && b.toLowerCase().indexOf("360se") > -1){ a = true }
			}
			return a;
		},
		/**
		 * 合并数组元素
		 * @params args <Array> 需要合并的数组集合，该集合也是一个数组
		 * @params args1 <Array> 将args1中元素添加到args中，并返回
		 * @ return <Array> 合并后的数组
		 */
		merger: function(args, args1){
			var len1 = args.length, len2, i, j, items;
			var array = [];
			if(args1){
				len1 = args1.length;
				for(i=0;i<len1;i++){ args.push(args1[i]); }
				array = args;
			}
			else{
				for(i=0;i<len1;i++){
					items = args[i];
					len2 = items.length;
					for(j=0;j<len2;j++){ if(items[j]){ array.push(items[j]); } }
				}
			}
			return array;
		},
		/**
		 * 将html字符串转换为元素标签
		 * @params args <String> html字符串
		 * @return <Element> html元素
		 */
		formatToElement: function(args){
			var temp = document.createElement('div');
			temp.innerHTML = args;
			return elementHandleFn.children([temp]);
		},
		/**
		 * 获取一组数据中的最大值
		 * @param args <Array> 数据集合
		 * @return <Int> 最大值
		 */
		maxValue: function(args){
			var len = args.length, i, a, b, result = null;
			for(i=0;i<len;i++){
				a = parseInt(args[i]), b = parseInt(args[i+1]);
				if(len == 1){ result = a; }
				else{
					if(result == null){ result = a > b ? a : b; }
					else{ result = result > a ? result : a; }
				}
			}
			return result;
		},
		/**
		 * 获取一组数据中的最小值
		 * @param args <Array> 数据集合
		 * @return <Int> 最小值
		 */
		minValue: function(args){
			var len = args.length, i, a, b, result = null;
			for(i=0;i<len;i++){
				a = parseInt(args[i]), b = parseInt(args[i+1]);
				if(len == 1){ result = a; }
				else{
					if(result == null){ result = a < b ? a : b; }
					else{ result = result < a ? result : a; }
				}
			}
			return result;
		},
		/**
		 * 获取一组数据中的平均值
		 * @param args <Array> 数据集合
		 * @param args1 <Int> 保留小数位数(如果有小数，没有则不受影响)
		 * @return <Int> 平均值
		 */
		aveValue: function(args, args1){
			var len = args.length, i, a = 0, result = null;
			for(i=0;i<len;i++){ a += parseInt(args[i]); }
			if(len > 0){ result = a/len; }
			if(args1 != undefined && args1 != null){ result = fn.Round(result, args1); }			
			return result;
		},
		/**
		 * 四舍五入
		 * @param args1 <Int> 需要四舍五入的数
		 * @param args2 <Int> 保留小数位数(默认为0，取整)
		 * @return <Int> 四舍五入后的数
		 */
		Round: function(args1, args2){
			args1 = args1 + '';
			//判断是否有小数
			var isXS = args1.indexOf('.');
			var result = args1;
			args2 = parseInt(args2);
			args2 = args2 ? args2 : 0;
			args2 = args2 >= 0 ? args2 : 0;
			if(isXS > -1){
				//获取小数位数
				var xsws = args1.length - isXS - 1;
				if(xsws < args2){ result = args1; }
				else{
					var flag = args2 == 0 ? 0 : 1;
					result = args1.substring(0, isXS+args2+flag);
					flag = args2 == 0 ? 1 : 1;
					var R = args1.substring(isXS+args2+flag, isXS+args2+1+flag);
					if(R>4){
						var M = '', i;
						for(i=0;i<args2;i++){ M += '0'; }
						var F = 1/parseInt(1+M);
						result = parseFloat(result) + F;
					}
				}
			}
			return result;
		}
	};
	
	/**
	 *主函数入口
	 */
	Main = function(args){ return elementHandleFn.install(args); };
	
	/**
	 * 继承
	 */
    Main.extend = function (a, b, c) {
        /// <summary>SJ继承</summary>
        /// <param name="a" type="Object">原始对象</param>
        /// <param name="b" type="Object">继承的对象</param>
        /// <param name="c" type="Boolean">是否深度继承</param>
        /// <returns type="Object" />继承后的新对象
    	var d, e, f;
    	if (!b) { b = a, a = this; }
    	e = a;
		if(e != null){
			for(d in b) {
				f = b[d];
				//判断是否需要深度继承
				if (c == true && typeof(f) == 'object' && f != null) {
					e[d] = fn.isJson(f) ? Main.extend(e[d], f, true): f;
				}
				else { e[d] = f; }
			}
		}
		return e;
    };
	
	/**
	 * 元素操作函数库
	 */
	elementHandleFn = {
		/**
		 * 初始化函数
		 * @param {Object} selector 选择器
		 * @return {Array} 元素对象集合 
		 */
		install: function(selector){
			var result = null;
			//如果为对象
			if(typeof(selector) == 'object'){ result = fn.isArray(selector) ? selector : [selector]; }
			//如果为函数
			else if(typeof(selector) == 'function'){ fn.ready(selector); }
			//如果为字符串
			else{ result = elementHandleFn.findElement([document], selector) }
			//返回结果
			return result;
		},
        //创建元素
        createElement: function (a) {
            /// <summary>创建元素</summary>
            /// <param name="a" type="String">创建的标签名</param>
            /// <returns type="Array">[创建的元素]
            //返回数组-数组元素为创建的元素
            return [document.createElement(a)];
        },
        //通过条件查找满足条件的元素
        queryByTerm: function (a, b, k) {
            /// <summary>通过条件查找满足条件的元素</summary>
            /// <param name="a" type="Object">参照元素</param>
            /// <param name="b" type="String">条件字符串(不包括前缀)</param>
            /// <param name="k" type="Int">0.class 1.ID 2.>直接的子级 3.元素名称</param>
            //定义函数内部变量
            var c, d, e, f, g, h, i, j, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, a1, b1, c1;
            //初始化变量值
            //定义一个查找字符串b的中间可能包含的字符串集合数组
            c = [".", "#", ">", "["];
            //存放当前条件字符串的第二级条件前缀
            h = "";
            //获取条件字符串的长度
            i = v = b.length;
            //存放临时元素
            p = [];
            //循环查找
            for (d = 0, e = c.length; d < e; d++) {
                //获取单个字符串
                f = c[d];
                //查找字符串中是否存在该字符(返回该字符在条件字符串中的位置)
                g = b.indexOf(f);
                //如果存在，将g赋值给i，将f赋值给h，以最小值，即最先出现在条件字符串中的字符分隔，获取第一个条件字符串(最后h即给需要的分隔符)
                if (g > -1) { if (g < i) { i = g; h = f; } }
            }
            //获取条件中的第一(取第0个)个条件字符串
            j = h == "" ? b : b.split(h)[0];
            //获取条件中的后部分字符串
            q = h != "[" ? b.substring(b.indexOf(h), v) : "";
            //获取所有子级(如果为2，只查找直接的子级)
            l = k == 2 ? elementHandleFn.children(a) : elementHandleFn.childrens(a);
            //筛选出满足条件的元素
            for (m = 0, n = l.length; m < n; m++) {
                //获取单个元素
                o = l[m];
                //判断状态
                if (k == 0) { if (fn.hasClass(o, j)) { p.push(o); } }
                else if (k == 1) { if (o.id == j) { p.push(o); } }
                else if (k == 2 || k == 3) { if (o.nodeName == j.toLocaleUpperCase()) { p.push(o); } }
            }
            //q为空[""]，说明需要元素的属性匹配
            if (q == "") {
                t = [];
                //获取第一个"]"在b中的位置
                u = b.indexOf("]");
                //截取"[*]"中的内容，并去掉空格" "，且将"=="替换为"="，以此统一
                q = b.substring(b.indexOf(h) + 1, u).replaceAll(" ", "").replaceAll("==", "=");
                //属性与只的链接字符串(以某些值开始、以某些值结尾、以包含某些值、不含有或者不等于特定值、等于特定值)
                w = ["^=", "$=", "*=", "!=", "="];
                //判断q中的条件字符串是属于哪一种(匹配上了就立即终止)
                for (r = 0, s = w.length; r < s; r++) { if (q.indexOf(w[r]) > -1) { break; } }
                //分隔开属性和值
                x = q.split(w[r]), z = r;
                //分别获取属性及属性值，去掉空格"'"和'"'，以此统一
                a1 = x[0], b1 = x[1].replaceAll("'", "").replaceAll('"', '');
                for (r = 0, s = p.length; r < s; r++) {
                    //获取单个元素
                    y = p[r];
                    //获取元素属性a1值
					c1 = a1 == 'class' ? y.className : y.getAttribute(a1);
                    switch (z) {
                        //属性a1的值以b1开头
                        case 0: if (c1.indexOf(b1) == 0) { t.push(y); } break;
                        //属性a1的值以b1结尾
                        case 1: if (c1.substring(c1.length - b1.length, c1.length) == b1) { t.push(y); } break;
                        //属性a1的值包含b1
                        case 2: if (c1.indexOf(b1) > -1) { t.push(y); } break;
                        //属性a1的值不等于b1
                        case 3: if (c1 != b1) { t.push(y); } break;
                        //属性a1的值等于b1
                        case 4: if (c1 == b1) { t.push(y); } break;
                        //否则，只要属性a1在元素中存在即可(c1!=null)，不管属性a1其值是否为空[""]
                        default: if (c1 != null) { t.push(y); } break;
                    }
                }
                //从新赋值p
                p = t;
                //重新赋值q
                q = b.substring(u + 1, v);
            }
            //递归，以p为参照元素集合对象，以q作为条件继续查找满足条件的元素
            if (q != j && q != "") { elementHandleFn.findElement(p, q, (h == "#" || h == "." ? true : false)); }
            else { private.tempData.findElem = p; }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g, delete h;
            delete i, delete j, delete l, delete m, delete n, delete o, delete p, delete q;
            delete r, delete s, delete t, delete u, delete v, delete w, delete x, delete y;
            delete z, delete a1, delete b1, delete c1;
        },
        //查找元素
        findElement: function (a, b, c) {
            /// <summary>查找元素</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">查找条件</param>
            /// <param name="c" type="String">查找范围(选填 true.在当前对象a中查找 false.在当前对象a的子集中查找 默认为false)</param>
            /// <returns type="Object" />[查找到的元素集合]
            //定义函数内部变量
            var d, e, f, g, h;
            //初始化变量值
            //是否在当前元素集合中查找
            c = c == true ? true : false;
            //获取当前元素集合的长度
            d = a.length;
            //获取查找字符串b中的第一个字符
            e = b.substring(0, 1);
            //如果c等于true，在当前对象a中查找
            if (c) {
                h = [];
                //循环元素，将所有元素添加到一个临时div中
                //for (f = 0; f < d; f++) { g = document.createElement("div"); g.appendChild(a[f]); }
                //循环元素，获取每一个元素的父级
                for (f = 0; f < d; f++) { h.push(a[f].parentNode); }
                //将g赋值给a
                a = h;
            }
            switch (e) {
                //以元素class名作为条件查找
                case ".": elementHandleFn.queryByTerm(a, b.substring(1, b.length), 0); break;
                //以元素ID名作为条件查找
                case "#": elementHandleFn.queryByTerm(a, b.substring(1, b.length), 1); break;
                //以当前元素的直接子级的标签名作为条件查找
                case ">": elementHandleFn.queryByTerm(a, b.substring(1, b.length), 2); break;
                //以当前元素的所有子级的标签名作为条件查找
                default: 
					if(b.indexOf(',') > -1){
						var splits = b.split(',');
						var len1 = splits.length, array = [], count;
						for(count=0;count<len1;count++){
							elementHandleFn.queryByTerm(a, splits[count], 3);
							array = fn.merger(array, private.tempData.findElem);
						}
						private.tempData.findElem = array;
					}
					else{ elementHandleFn.queryByTerm(a, b, 3); }
					break;
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g, delete h;
            return private.tempData.findElem;
        },
        //获取元素的子集元素对象
        children: function (a, b) {
            /// <summary>获取元素的子集元素对象</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="Int/Sring">获取第几项(从0开始)/条件字符串</param>
            /// <returns type="Object" />子级集合对象
            //定义函数内部变量
            var c, d, e, f, g, h, i, j, k, l;
            //初始化变量值
            c = a.length;
            d = [];
            j = [];
            k = b - 0; 	//确定b是否为数字
			//循环元素
			for (e = 0; e < c; e++) {
				//获取单个元素的子集
				f = a[e].childNodes;
				//循环子集
				for (g = 0, h = f.length; g < h; g++) {
					//获取单个子元素
					i = f[g];
					//判断是否为空的文本标签
					if (i.nodeName != "#text") { d.push(i); }
				}
				//如果b不为空
				if (b != undefined && d != null && d != "") {
					//如果b为数字，并且d的长度大于b，就添加d的第b个元素到j中，然后清空d(d需要存放下一个元素的子元素)
					if (k || k == 0) { d.length > b ? j.push(d[k]) : ""; d = []; }
					//否则不为数字，为条件字符串
					else {
						//从d中查找满足条件的元素
						d = elementHandleFn.findElement(d, b, true);
						//如果d不为空，循环d中的元素，添加到j中
						if (d) { for (i = 0, l = d.length; i < l; i++) { j.push(d[i]); } }
					}
				}
				//如果b为空，循环d中元素，添加到j中，然后清空d(d需要存放下一个元素的子元素)
				else { for (i = 0, l = d.length; i < l; i++) { j.push(d[i]); } d = []; }
			}
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f, delete g, delete h, delete i, delete k, delete l;
            return j;
        },
        //往下获取所有子集(包括子集的子集)
        childrens: function (a, b) {
            /// <summary>往下获取所有子集(包括子集的子集)</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="String">查找子级的条件字符串</param>
            /// <returns type="Object" />子级集合对象
            //定义函数内部变量
            var c, d, e, f, g, h, i, j, k, l, m, n, o, p;
            //初始化变量值
            c = a.length;
            //d = private.tempData.cNode;
			d = [];
            //循环元素
            for (e = 0; e < c; e++) {
                //获取单个元素
                f = a[e];
                //获取单个元素的子级
                g = f.childNodes;
                //判断子级是否存在
                if (g) {
                    //循环子级
                    for (i = 0, h = g.length; i < h; i++) {
                        //获取单个子级
                        j = g[i];
                        //判断查找条件是否为空
                        if (b) {
                            //通过条件查找该元素是否满足条件
                            k = elementHandleFn.findElement([j], b, true);
                            //循环查找结果(这里只有一个元素，就不用循环了)，如果结果length大于0，添加到存放数据的对象中
                            if (k.length > 0) {
                                //添加元素
                                d.push(j);
                                //继续查找子级的子级
                                n = elementHandleFn.childrens([j], b);
								p = n.length;
								for(o=0;o<p;o++){ d.push(n[o]); }
                            }
                        }
                        //查找条件不存在
                        else {
                            //如果不为空文本标签
                            if (j.nodeName != "#text") {
                                //添加到存放数据的对象中
                                d.push(j);
                                //继续查找子级的子级
                                n = elementHandleFn.childrens([j]);
								p = n.length;
								for(o=0;o<p;o++){ d.push(n[o]); }
                            }
                        }
                    }
                }
            }
            //删除临时变量
            delete a, delete b, delete c, delete e, delete f, delete g, delete h, delete i, delete j, delete k, delete l, delete m;
            return d;
        },
        //获取、设置css style属性值
        css: function (a, b, c) {
            /// <summary>获取、设置css style属性值</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">style属性(必填)</param>
            /// <param name="c" type="String">选填 默认值为获取属性值，填写了则为设置style属性值为当前填写的内容</param>
            /// <returns type="Object" />如果为获取属性值则返回的是第一个元素的属性值，如果为设置属性值则返回的是当前元素集合本身a
            //定义函数内部变量
            var d, e;
            //初始化变量值
            e = a.length;
            //如果是设置值
            if (c != undefined && c != null) {
                //定义函数内部变量
                var f, g;
                for (f = 0; f < e; f++) { g = a[f]; if (g.style != undefined) { g.style[b] = c; } }
                //删除临时变量
                delete f, delete g;
                //设置当前返回的对象
                d = a;
            }
            else {
                c = "";
                if (e > 0) { c = a[0].style[b]; }
                //设置当前返回的对象
                d = c;
            }
            //删除临时变量
            delete a, delete b, delete c, delete e;
            return d;
        },
        //获取、设置对象的style属性值
        styleText: function (a, b, c) {
            /// <summary>获取、设置对象的style属性值</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">选填 style属性值</param>
            /// <param name="c" type="String">选填 true.替换原有的值 false.追加到原有的值后面</param>
            /// <returns type="Object" />如果为获取属性值则返回的是第一个元素的属性值，如果为设置属性值则返回的是当前元素集合本身a
            //定义函数内部变量
            var d, e, f = "";
            //初始化变量值
            d = a.length;
            c = c == undefined || c == null && c == "" ? true : c;
            //如果为设置值
            if (b != undefined && d != null) {
                for (e = 0; e < d; e++) {
                    f = a[e];
                    //if (c) { f.setAttribute("style", b); }
                   // else { f.setAttribute("style", f.getAttribute("style") + b); }
				   if(c){ f.style.cssText = b; }
				   else{ f.style.cssText += b; }
                }
                f = a;
            }
            //获取第一项的style值
            //else { if (d > 0) { f = a[0].getAttribute("style"); } }
			else { if (d > 0) { f = a[0].style.cssText; } }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e;
            return f;
        },
        //添加元素属性值
        setAttribute: function (a, b, c, d) {
            /// <summary>添加元素属性值</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">属性名称(必填)</param>
            /// <param name="c" type="String">属性值(必填)</param>
            /// <param name="d" type="Boolean">如果该属性有值，是替换还是追加(选填 默认为替换，如class属性有时需要追加)</param>
            /// <returns type="Object" />返回当前元素集合本身a
            //定义函数内部变量
            var e, f, g, h, i, j, k, l;
            //初始化变量值
            e = a.length;
            d = d == false ? false : true;
            //循环元素
            for (f = 0; f < e; f++) {
                //a集合的单个元素
                g = a[f];
                //如果是替换元素属性值
                if (d) { g.setAttribute(b, c); }
                //否则，如果元素g的属性值不为空，就最佳
                else {
                    //元素g的属性b的值
                    h = g.getAttribute(b);
					if(h == null && b == "class"){ h = g.className; }
					//查找是否已经存在需要设置的属性值
					i = h.split(" "), l = true;
					for(j=0, k=i.length; j<k; j++){ if(i[j] == c){ l = false; } }
					if(l){ g.setAttribute(b, h ? h + " " + c : c); }
                }
            }
            //删除临时变量
            delete b, delete c, delete d, delete e, delete f, delete g, delete h;
            return a;
        },
        //获取元素属性值
        getAttribute: function (a, b) {
            /// <summary>获取元素属性值</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">属性名称(必填)</param>
            /// <returns type="String" />返回元素集合中第一项元素的属性值
            //定义函数内部变量
            var c, d, e;
            //初始化变量值
            c = a.length;
            d = "";
            //如果集合中的元素长度大于0，就操作第一个元素(因为结果只能有一个)
            if (c > 0) {
                //获取集合a中的第一个元素
                e = a[0];
                //获取集合中第一个元素e的属性b的值
                d = e.getAttribute(b);
                //判断属性值是否为空，如果为空就返回引号空("")
                d = d ? d : "";
            }
            //删除临时变量
            delete a, delete b, delete c, delete e;
            return d;
        },
        //从每一个匹配的元素中删除一个属性
        removeAttribute: function (a, b, c) {
            /// <summary>从每一个匹配的元素中删除一个属性</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">属性名称(必填)</param>
            /// <param name="c" type="String">属性值(选填)</param>
            /// <returns type="Object" />返回当前元素集合本身a
            //定义函数内部变量
            var d, e, f, g, h, i, j, k, l;
            //初始化变量值
            d = a.length;
            k = "";
            //循环元素，移除元素集合中的每一个元素的属性b
            for (e = 0; e < d; e++) {
                //获取元素集合中的单个元素
                f = a[e];
                //如果属性值c不为空，就判断属性值等于c的才移除该元素的属性
                if (c) {
                    //获取单个元素的属性值
                    g = f.getAttribute(b);
                    //如果属性值相等删除元素属性b
                    if (g == c) { f.removeAttribute(b); }
                    //如果属性值中包含c，则移除等于c的部分(主要用于移除class)
                    else if (g.indexOf(c) > -1 && g.indexOf(" ") > -1) {
                        h = g.split(" ");
                        i = h.length;
                        for (j = 0; j < i; i++) {
                            //获取单个class
                            l = h[j];
                            //如果单个class不等于c，就从新构造class
                            if (l != c) { k = (k == "") ? (l) : (k + " " + l); }
                        }
                        //从新给属性赋值(移除c后的值)
                        f.setAttribute(b, k);
                    }
                }
                //否则，直接删除该元素的属性b
                else { f.removeAttribute(b); }
            }
            //删除临时变量
            delete b, delete c, delete d, delete e, delete f, delete g, delete h, delete i, delete j, delete k, delete l;
            return a;
        },
        //从DOM中删除所有匹配的元素
        removeElement: function (a, b) {
            /// <summary>从DOM中删除所有匹配的元素</summary>
            /// <param name="a" type="Object">元素对象集合(必填)</param>
            /// <param name="b" type="String">查询条件(选填)</param>
            //定义函数内部变量
            var c, d, e, f;
            //初始化变量值
            c = a.length;
            //如果存在查询条件，则通过条件筛选出结果再删除元素
            if (b) {
                //从当前元素集合a中筛选出满足条件的元素集合并从新赋值给a，此时，a元素集合对象为需要删除的所有元素对象集合
                a = elementHandleFn.findElement(a, b, true);
                //从新设置c的值
                c = a.length;
            }
            //循环元素
            for (d = 0; d < c; d++) {
                //获取元素集合中的单个元素
                e = a[d];
				if(e){
					//获取元素的父级，由父级删除该元素
					f = e.parentNode;
					//如果父级不为空，则删除元素
					if (f) { f.removeChild(e); }
				}
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e, delete f;
        },
        //匹配元素的某个属性值是否包含指定的关键字
        hasAttribute: function (a, b, c) {
            /// <summary>匹配元素的某个属性值是否包含指定的关键字</summary>
            /// <param name="a" type="Object">元素对象集合(必填)</param>
            /// <param name="b" type="String">属性名称(必填)</param>
            /// <param name="c" type="String">关键字(必填)</param>
            /// <returns type="Boolean" />返回元素集合中第一个元素匹配的结果(true.包含 false.不包含)
            //定义函数内部变量
            var d, e, f, g, h;
            //初始化变量值
            d = a.length;
            e = false;
            //如果集合中的元素长度大于0，就操作第一个元素(因为结果只能有一个)
            if (d > 0) {
                //获取集合a中的第一个元素
                f = a[0];
                //获取集合中第一个元素f的属性b的值
                g = f.getAttribute(b);
                //indexOf获取属性值g中是否包含要匹配的关键字c
                h = g.indexOf(c);
                //如果存在，将e设为true，否则设为false
                e = h > -1 ? true : false;
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete f, delete g, delete h;
            return e;
        },
        //获取父级
        parent: function (a) {
            /// <summary>获取父级</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <returns type="Object" />父级集合对象
            //定义函数内部变量
            var b, c, d;
            //初始化变量值
            b = [];
            c = a.length;
            //循环当前元素
            for (d = 0; d < c; d++) { b.push(a[d].parentNode); }
            //删除临时变量
            delete a, delete c, delete d;
            return b;
        },
        //往上获取所有父级(包括父级的父级)
        parents: function (a, b, c) {
            /// <summary>往上获取所有父级(包括父级的父级)</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="String">查找父级的条件字符串</param>
            /// <param name="c" type="Boolean">在查找所有父级时，是否包括BODY元素对象，默认为false(不包括)</param>
            /// <returns type="Object" />父级集合对象
            //定义函数内部变量
            var d, e, f, g, h, i, j, k;
            //初始化变量值
            c = c ? c : false;
            d = a.length;
            //e = private.tempData.pNode;
			e = [];
            //循环元素
            for (f = 0; f < d; f++) {
                //获取单个元素
                g = a[f];
                //获取单个元素父级
                h = g.parentNode;
                //如果父级存在
                if (h) {
                    //如果存在查找条件
                    if (b) {
                        //如果不限制元素是否为body及以上的元素
                        if (c == true) {
                            //通过条件，判断当前父级是否满足条件
                            h = elementHandleFn.findElement([h], b, true);
                            //如果h不为空，循环h中的元素(这里只有一个元素，就不用循环了)，添加到存放数据的对象中
                            if (h) { e.push(h[0]); }
                        }
                        else {
                            //获取元素的标签名称
                            i = h.nodeName;
                            //如果查找到body，就停止查找(body以上的元素不做操作)
                            if (i == "BODY") { break; }
                            else {
                                //通过条件，判断当前父级是否满足条件
                                h = elementHandleFn.findElement([h], b, true);
                                //如果h不为空，循环h中的元素(这里只有一个元素，就不用循环了)，添加到存放数据的对象中
                                if (h) { e.push(h[0]); }
                            }
                        }
                    }
                    //如果不存在条件
                    else {
                        //如果不限制元素是否为body及以上的元素，直接将父级添加到存放数据的对象中
                        if (c == true) { e.push(h); }
                        else {
                            //获取元素的标签名称
                            i = h.nodeName;
                            //如果查找到body，就停止查找(body以上的元素不做操作)
                            if (i == "BODY") { break; }
                            //将父级添加到存放数据的对象中，并查找其父级
                            else { e.push(h); }
                        }
                    }
                    //继续查找父级的父级
                    h = elementHandleFn.parents([h], b, c);
					j = h.length;
					for(k=0;k<j;k++){ e.push(h[j]); }
                }
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete f, delete g, delete h;
            return e;
        },
        //向指定元素中添加指定的子元素
        appendChild: function (a, b) {
            /// <summary>向指定元素中添加指定的子元素</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="Object">需要添加到当前元素作为子级的元素对象</param>
            /// <returns type="Object" />当前集合对象 a
            //定义函数内部变量
            var c, d, e, f, g, h;
            //初始化变量值
            c = a.length;
            //循环当前元素
            for (d = 0; d < c; d++) {
                //获取单个元素
                e = a[d];
                //循环需要添加的元素
                for (f = 0, g = b.length; f < g; f++) {
                    //获取单个需要添加的元素
                    h = b[f];
                    //添加子级
                    e.appendChild(h);
                }
            }
            //删除临时变量
            delete b, delete c, delete d, delete e, delete f, delete g, delete h;
            return a;
        },
        //获取指定元素的直接子级的最后一个元素
        last: function (a) {
            /// <summary>获取指定元素的直接子级的最后一个元素</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <returns type="Object" />指定元素的直接子级的最后一个元素
            //定义函数内部变量
            var b, c, d, e;
            //初始变量值
            b = a.length;
            c = [];
            //循环元素
            for (d = 0; d < b; d++) {
                //获取单个元素的子级
                //e = a.eq(d).children();
				e = elementHandleFn.children([a[d]]);
                //将最后一项添加到返回对象中
                c.push(e[e.length - 1]);
            }
            //删除临时变量
            delete a, delete b, delete d, delete e;
            return c;
        },
        //克隆元素
        clone: function (a, b) {
            /// <summary>克隆元素</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="Object">可选。布尔值。规定是否复制元素的所有事件处理。默认为false，副本中不包含事件处理器</param>
            /// <returns type="Object" />当前克隆元素对象
            //定义函数内部变量
            var c, d, e, f;
            //初始化变量值
            c = a.length;
            f = [];
            //循环元素
            for (d = 0; d < c; d++) {
                //获取单个元素
                e = a[d];
                //判断是否clone元素的所有事件处理
                e = f ? e.cloneNode(true) : e.cloneNode();
                //添加到返回对象中
                f.push(e);
            }
            //删除临时变量
            delete a, delete b, delete c, delete d, delete e;
            return f;
        },
        //在指定元素之前添加元素
        before: function (a, b) {
            /// <summary>在指定元素之前添加元素</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="Object">需要添加的元素对象</param>
            /// <returns type="Object" />当前元素对象 a
            //定义函数内部变量
            var c, d, e, f, g, h;
            //初始化变量值
            c = a.length;
            b = SJ(b);
            //循环元素
            for (d = 0; d < c; d++) {
                //获取单个元素
                e = a[d]
                //循环需要添加的元素
                for (f = 0, g = b.length; f < g; f++) {
                    //获取单个需要添加的元素的clone对象
                    //h = elementHandleFn.clone([b[f]], true);
                    //添加元素
                    //e.parentNode.insertBefore(h[0], e);
					e.parentNode.insertBefore(b[f], e);
                }
            }
            //移除b
            //elementHandleFn.removeElement(b);
            //删除临时变量
            delete b, delete c, delete d, delete e, delete f, delete g, delete h;
            return a;
        },
        //在指定元素之后添加元素
        after: function (a, b) {
            /// <summary>在指定元素之后添加元素</summary>
            /// <param name="a" type="Object">当前元素对象</param>
            /// <param name="b" type="Object">需要添加的元素对象</param>
            /// <returns type="Object" />当前元素对象 a
            //定义函数内部变量
            var c, d, e, f, g, h, i, j;
            //初始化变量值
            c = a.length;
            b = SJ(b);
            //循环元素
            for (d = 0; d < c; d++) {
                //获取单个元素
                e = a[d];
                //获取单个元素的父级
                f = e.parentNode;
                //循环需要添加的元素(从最后一个往第一个循环)
                for (h = b.length; h > 0; h--) {
                    //获取添加元素的单个元素
                    i = b[h - 1];
                    //获取添加元素的单个元素的clone对象
                    //g = elementHandleFn.clone([i], true);
                    //获取f的最后一个子元素
                    j = f.lastChild;
                    //判断是否为空文本标签
                    j = j.nodeName == "#text" ? SJ.nextPrev([j], null, true)[0] : j;
                    //如果f的最后一个子元素等于需要添加大单个元素i，就直接在f中添加i的clone对象j
                    if (j == e) { f.appendChild(i); }
                    //否则，就在当前元素的下一个元素之前添加一个元素(即当前元素的后面)
                    //else { f.insertBefore(g[0], [e].next()[0]); }
					else { f.insertBefore(i, SJ.nextPrev([e])[0]); }
                }
            }
            //移除b
            //elementHandleFn.removeElement(b);
            //删除临时变量
            delete b, delete c, delete d, delete e, delete f, delete g, delete h, delete i, delete j;
            return a;
        },
        //获取元素的下一个元素或上一个元素
        nextPrev: function (a, b, c) {
            /// <summary>获取元素的下一个元素或上一个元素</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">查找条件(选填)</param>
            /// <param name="c" type="Boolean">true.上一个元素 false.下一个元素(选填 默认值为false)</param>
            /// <returns type="Object" />[查找到的元素集合]
            //定义函数内部变量
            var d, e, f, g, h, i, n;
            //初始化变量值
            //v1.最后返回的集合对象数组
            //v2.是否有查找条件(即b是否为空)
            //v3.元素对象集合的长度
            //v4.什么时候停止添加查找到的元素(为false时停止)
            d = [], e = false, f = a.length, n = c ? c : false;
            if (b) {
                //如果查找条件不为空
                var j, k, l, m, o, p, q, r;
                //初始化变量值
                //v1.是否有查找条件(即b是否为空)
                //v2.存放满足查找条件的元素，用作最后条件筛选做最后结果返回
                //v3.同上v2，存放的元素都是相同的克隆版本，只是用作最后条件筛选
                e = true, o = [], p = [];
            }
            //循环元素
            for (i = 0; i < f; i++) {
                g = a[i];
                //如果查询条件不为空
                if (e) {
                    //获取元素的同级所有元素
                    j = g.parentNode.childNodes;
                    //循环所有的同级元素
                    for (k = 0, l = j.length; k < l; k++) {
                        m = j[k];
                        //如果c等于true，则是查找上一个元素，所以当g等于m，n设为false，停止添加元素，之前所添加的元素就是以上的所有元素集合了
                        //如果c等于false，则是查找下一个元素，所以当g等于m，n设为true，开始添加元素，直到结束，添加的元素就是以下所有元素集合
                        if (m == g) { n = c ? false : true; }
                        else if (n) {
                            //判断是否为空标签，并且添加到两个临时存放元素集合对象中
                            if (m.nodeName != "#text") { o.push(m); p.push(m.cloneNode(true)); }
                        }
                    }
                }
                //如果条件为空
                else {
                    //查找上一个或下一个元素
                    h = c ? g.previousSibling : g.nextSibling;
                    //如果查找到的元素不为空
                    if (h) {
                        //不等于"#text"，就添加到d中，否则继续执行查找
                        if (h.nodeName != "#text") { d.push(h); }
                        //else { d = c ? [h].prev() : [h].next(); }
						//else{ d = c ? SJ.nextPrev([h], null, true) : SJ.nextPrev([h], null, false); }
						else{ d = SJ.nextPrev([h], null, c); }
                    }
                }
            }
            //如果查询条件不为空，再从之上或之下的所有查找到的元素集合中查找满足条件的元素
            if (e) {
                //创建一个div元素，将查找到的克隆的元素添加到div中，再通过该div下的这些子集元素条件筛选出满足条件的元素
                q = elementHandleFn.appendChild(elementHandleFn.createElement("div"), p);//.appendChild(p);
                //查找满足条件的元素集合
                r = elementHandleFn.findElement(q, b);// q.find(b);
                //该结果只是在克隆元素集合中找到的结果集合，还需要从未克隆的元素集合中找出与该结果集合相同的元素并添加到最后返回的集合对象中
                for (i = 0, f = r.length; i < f; i++) { for (k = 0, l = p.length; k < l; k++) { if (r[i] == p[k]) { d.push(o[k]); break; } } }
                //删除临时变量
                delete j, delete k, delete l, delete m, delete o, delete p, delete q, delete r;
            }
            //删除临时变量
            delete a, delete b, delete c, delete e, delete f, delete g, delete h, delete i, delete n;
            return d;
        },
        //获取元素之前所有同级元素或之后所有同级元素
        nextPrevAll: function (a, b, c) {
            /// <summary>获取元素的下一个元素或上一个元素</summary>
            /// <param name="a" type="Object">元素对象集合</param>
            /// <param name="b" type="String">查找条件(选填)</param>
            /// <param name="c" type="Boolean">true.上一个元素 false.下一个元素(选填 默认值为false)</param>
            /// <returns type="Object" />[查找到的元素集合]
            //定义函数内部变量
            var d, e, f, g, h, i, j, k, l, m;
            //初始化变量值
            //v1.最后返回的集合对象数组
            //v2.是否有查找条件(即b是否为空)
            //v3.元素对象集合的长度
            //v4.什么时候停止添加查找到的元素(为false时停止)
            d = [], e = false, f = a.length; k = c ? c : false;
            if (b) {
                //如果查找条件不为空
                var n, o, p, q;
                //初始化变量值
                //v1.是否有查找条件(即b是否为空)
                //v2.存放满足查找条件的元素，用作最后条件筛选做最后结果返回、
                //v3.同上v2，存放的元素都是相同的克隆版本，只是用作最后条件筛选
                e = true, o = [], p = [];
            }
            //循环元素
            for (i = 0; i < f; i++) {
                //a元素集合的单个元素
                g = a[i];
                //获取元素g的同级所有元素
                h = g.parentNode.childNodes;
                //循环所有的同级元素
                for (j = 0, l = h.length; j < l; j++) {
                    m = h[j];
                    //如果c等于true，则是查找以上的元素，所以当g等于m，k设为false，停止添加元素，之前所添加的元素就是以上的所有元素集合了
                    //如果c等于false，则是查找以下的元素，所以当g等于m，k设为true，开始添加元素，直到结束，添加的元素就是以下所有元素集合
                    if (g == m) { k = c ? false : true; }
                    else if (k) {
                        //判断是否为空标签
                        if (m.nodeName != "#text") {
                            //如果有查找条件，添加到两个临时存放元素集合对象中
                            if (e) { o.push(m); p.push(m.cloneNode(true)); }
                            //否则，直接添加到最后返回的集合对象中
                            else { d.push(m); }
                        }
                    }
                }
            }
            //最后再判断是否有查询条件，如果有，就从临时元素存放集合对象中执行条件筛选
            if (e) {
                //创建一个div元素，将查找到的克隆的元素添加到div中，再通过该div下的这些子集元素条件筛选出满足条件的元素
                q = SJ.createElement("div").append(p);
                //查找满足条件的元素集合
                n = q.find(b);
                //该结果只是在克隆元素集合中找到的结果集合，还需要从未克隆的元素集合中找出与该结果集合相同的元素并添加到最后返回的集合对象中
                for (i = 0, f = n.length; i < f; i++) { for (j = 0, l = p.length; j < l; j++) { if (n[i] == p[j]) { d.push(o[j]); } } }
                //删除临时变量
                delete n, delete o, delete p, delete q;
            }
            //删除临时变量
            delete a, delete b, delete c, delete e, delete f, delete g, delete h, delete i, delete j, delete k, delete l, delete m;
            return d;
        },
		/**
		 * 给元素添加类名
		 * @params a <Element> 元素对象
		 * @params b <String> 需要添加的类名
		 * @return <Element> 当前元素集合
		 */
		addClass: function(a, b){
			var c, d, e, f;
			c = a.length;
			for(d=0;d<c;d++){
				e = a[d];
				f = e.className;
				e.className = f ? f + ' '+b : b;
			}
			return a;
		},
		/**
		 * 移除元素的类名
		 * @params a <Element> 元素对象
		 * @params b <String> 需要移除的类名
		 * @return <Element> 当前元素集合
		 */
		removeClass: function(a, b){
			var c, d, e, f, g, h, i, j, k;
			c = a.length;
			for(d=0;d<c;d++){
				e = a[d];
				f = e.className;
				e.className = f.replace(b, '').replace(' '+b, '');
			}
			return a;
		},
		/**
		 * 获取元素的宽
		 * @params <Element> 当前元素
		 * @params <Int> 设置元素的宽(若不填，则是获取)
		 * @return <Int/Element> 获取的宽度/当前元素
		 */
		width: function(args0, args1){
			var a;
			if(args1){
				for(var i=0,len=args0.length;i<len;i++){
					args0[i].style.width = args1+'px';
				}
				a = args0;
			}
			else{ a = args0[0].offsetWidth; }
			return a;
		},
		/**
		 * 获取元素的高
		 * @params <Element> 当前元素
		 * @params <Int> 设置元素的高(若不填，则是获取)
		 * @return <Int/Element> 获取的高度/当前元素
		 */
		height: function(args0, args1){			
			var a;
			if(args1){
				for(var i=0,len=args0.length;i<len;i++){
					args0[i].style.height = args1+'px';
				}
				a = args0;
			}
			else{ a = args0[0].offsetHeight; }
			return a;
		}
	};
	Main.extend(elementHandleFn);
	
	/**
	 * SJ库属性
	 */
    Main.Attr = {
        pluginName: "SJ", 																//插件名
        version: "1.0.1", 																//版本号
        author: "PanYi", 																//作者
        createDate: "2012.10.29", 														//创建日期
        updateDate: "2013.01.23", 														//最后修改日期
        remark: "SJ(BS) JavaScript Baseic Library", 									//插件备注
        basic: { qq: "407666067", email: "pye-mail@163.com", phone: "13594137732"} 		//QQ、邮箱、电话
    };
    Main.extend(Main.Attr);
	
	/**
	 * SJ库皮肤颜色值
	 */
    Main.Skin = new Object();
	(function(skin){
		var colors, color, i, j, len;
		colors = ['red', 'dark', 'blue', 'light', 'green'];
		len = colors.length;
		skin['mainColor'] = private.conf.color;
		for(i=0;i<len;i++){
			color = colors[i];
			for(j=10;j<250;j+=10){
				skin[color+'Color'+(j/10)] = fn.getColor(color, skin['mainColor'], j);
			}
		}
	})(Main.Skin);
    Main.extend(Main.Skin);
	
	/**
	 * 普通函数库，包含了一些普通的公共方法
	 */
	Main.fn = {
		/**
		 * 判断是否为数组
		 * @param a <Object> 判断的对象
		 * @return <Boolean> 返回是否为数组（true||false）
		 */
		isArray: function (a) { return fn.isArray(a); },
		/**
		 * 判断是否为json对象
		 * @param obj <Object> 需要判断的对象
		 * @return <Boolean> 是否为JSON（true||false）
		 */
		isJson: function(obj){ return fn.isJson(obj); },
		/**
		 * 判断一个元素对象（默认为浏览器对象）是否出现滚动条
		 * @param a <Object> 元素对象
		 * @return <JSON> {x: 下(true:有 false:无), y: 右(true:有 false:无)}
		 */
		isScroll: function (a) { return fn.isScroll(a); },
		/**
		 * 获取滚动条距顶部的距离和距左边的距离
		 * @return <JSON> {x: 横向滚动条距左边的距离, y: 纵向滚动条距上边的距离}
		 */
		getScroll: function () { return fn.getScroll(); },
		/**
		 * 获取浏览器的宽和高(可见区域，不包括滚动条)
		 * @return <JSON> {width: 宽, height: 高}
		 */
		clientWH: function () { return fn.clientWH(); },
		/**
		 * 获取浏览器的宽和高(可操作区域，包括滚动条) 
		 * @return <JSON> {width: 宽, height: 高}
		 */
		scrollWH: function () { return fn.scrollWH(); },
		/**
		 * 创建样式表
		 * @param a <String> 样式表字符串
		 * @param b <String> 给style标签添加一个指定的ID
		 */
		createStyleSheet: function(a, b){ fn.createStyleSheet(a, b); },
        /**
		 * 创建cookie
		 * @param a <String> cookie名称
		 * @param b <Object> cookie值
		 * @param c <Int> cookie保存天数(选填，默认为7天)
		 */
        setCookie: function (a, b, c) { fn.setCookie(a, b, c); },
        /**
		 * 读取cookie
		 * @param a <String> cookie名称
		 * @return <Object> cookie值
		 */
        getCookie: function (a) { return fn.getCookie(a); },
        /**
		 * 删除cookie
		 * @param a <String> cookie名称
		 */
        delCookie: function (a) { fn.delCookie(a); },
        /**
		 * 创建一个兼容多浏览器的颜色渐变特效CSS样式字符串
		 * @param a <String> 开始颜色值
		 * @param b <String> 结束颜色值
		 * @param c <Int> 0.由上到下 1.由下到上 2.由左到右 3.由右到左 选填，默认为0
		 * @return <String> CSS样式字符串
		 */
        colStyleCss: function (a, b, c) { return fn.colStyleCss(a, b, c); },
        /**
		 * 16进制颜色转为RGB格式
		 * @param a <String> 16进制颜色值
		 * @return <String> RGB颜色值
		 */
        toRgb: function (a) { return fn.toRgb(a); },
        /**
		 * 动态加载css,js文件
		 * @param a <String> 文件路径(文件全路径，且文件全名)
		 * @param b <Function> 加载完后执行的函数
		 * @param c <Boolean> 如果要加载的文件已经存在，是否重新加载
		 */
        include: function (a, b, c) { fn.include(a, b, c); },
		/**
		 * drag 拖动
		 * @param a.当前事件 event
		 * @param b.当前事件触发对象 element
		 * @param e.当前操作的弹窗主层(需要移动的层)
		 * @param d.在哪个区域拖动
		 */
		Drag: function(a, b, c, d){ fn.Drag(a, b, c, d); },
		/**
		 * drags 拖动
		 * @param a.当前事件触发对象 element
		 * @param b.当前操作的弹窗主层(需要移动的层)
		 * @param c.在哪个区域拖动
		 */
		Drags: function(a, b, c){ a.onmousedown = function(event){ fn.Drag(event, a, b, c); } },
		/**
		 * 获取颜色
		*/
		getColor: function(key, color, num){ return fn.getColor(key, color, num); },
		/**
		 * 获取元素在页面上的坐标
		 * @param element <Element> 需要获取坐标的元素
		 * @return <JSON> {top, left}
		 */
        getElementTL: function (element) { return fn.getElementTL(element); },
		/**
		 * 移除数组相同元素
		 * @param a <Array> 数组对象
		 * @return <Array> 移除相同元素后的数组
		 */
		removeRepeat: function(a){ return fn.removeRepeat(a); },
        /**
		 * 命名空间
		 */
        NameSpace: {
			/**
			 * 注册命名空间
			 * @param a <String> 空间名(如："xx.yy")
			 */
			register: function (a) { fn.namespace(a); }
		},
		/**
		 * 事件冒泡
		 * @param params <Array>
		 * @param events <String> 事件名称
		 * @param element <Object> 触发事件的元素
		 * @remark 已使用第三方插件代码（JQuer）
		 */
		eventBubbling: function(params, events, element){ return fn.eventBubbling(params, events, element); },
        /**
		 * 屏蔽右键
		 * @param element <Object> 需要屏蔽右键的元素对象
		 * @param a <Function||String> 右键时执行的函数
		 * @return <Object> 元素对象this
		 */
        shieldRightMenu: function (element, a) { return fn.shieldRightMenu(element, a); },
        /**
		 * 设置元素中的文本不能被选择
		 * @param element <Object> 需要禁止文本选择的元素对象
		 * @param a <String> 鼠标样式 选填，默认为"default"
		 * @return <Object> 元素对象this
		 */
        disableSelection: function (element, a) { return fn.disableSelection(element, a); },
		/**
		 * 解决ie下textarea的maxlength的限制
		 * @param element <Element> 需要操作的元素
		*/
		limitLength: function(element){ fn.limitLength(element); },		
		/**
		 * 模拟线程
		 * @param a.执行函数
		 * @param b.间隔b毫秒执行一次函数a
		 * @remark 未使用第三方插件代码
		*/
		Thread: function(a, b){ return new fn.Thread(a, b) },
		/**
		 * Ajax
		 * @param a: 
		*/
		ajax: function(a){
			var b = {
				method: "POST",
				url: null,
				async: true,
				data: null,
				contentType: "application/x-www-form-urlencoded",
				dataType: "html",
				error: null,
				complete: null,
				success: null,
				timeout: 30000
			};
			Main.extend(b, a);
			fn.sendXMLHttpRequest(b);
		},
		/**
		 * get
		*/
		get: function(url, data, success, error, dataType){
			if(typeof(data) == 'function'){ error = success; dataType = error; success = data; data = null; }
			var b = {
				method: "GET",
				url: url,
				async: true,
				data: data,
				contentType: "application/x-www-form-urlencoded",
				dataType: dataType ? dataType : "html",
				error: error?error:null,
				complete: null,
				success: success,
				timeout: 30000
			};
			fn.sendXMLHttpRequest(b);
		},
		/**
		 * post
		*/
		post: function(url, data, success, error, dataType){
			if(typeof(data) == 'function'){ error = success; dataType = error; success = data; data = null; }
			var b = {
				method: "POST",
				url: url,
				async: true,
				data: data,
				contentType: "application/x-www-form-urlencoded",
				dataType: dataType ? dataType : "html",
				error: error?error:null,
				complete: null,
				success: success,
				timeout: 30000
			};
			fn.sendXMLHttpRequest(b);
		},
		/**
		 * Panel
		*/
		Panel: function(a){ return new fn.Panel(a); },
		/**
		 * 绑定事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		*/
		bind: function(a, b, c){ fn.bindEvent(a, b, c); },
		/**
		 * 取消事件
		 * @param a <Object> 元素对象
		 * @param b <String> 事件名字
		 * @param c <Function> 事件的执行函数
		*/
		unbind: function(a, b, c){ fn.removeEvent(a, b, c); },		
        /**
         * 查找element元素的class中是否包含hasVal
         * @param {Object} element 要查找class的元素
         * @param {Object} hasVal 要匹配的值
         * @return {Boolean} true 包含，false 不包含
         */
		hasClass: function(element, hasValue){ return fn.hasClass(element, hasValue); },
        /**
         * 文档加载
         * @param {Object} args 文档加载完成后执行的函数
         */
        ready: function(args){ fn.ready(args); },
		/**
		 * 删除数组中指定元素
		 * @param args0 <Array> 元素数组
		 * @param args1 <Int> 须要删除元素的索引
		 * @return <Array>
		 */
		removeAry: function(args0, args1){ return args0.splice(args1, 1); },
		/**
		 * 判断浏览器类型
		 * @return <Object> 浏览器类型
		 */
		browser: function(){ return fn.browser(); },
		/**
		 * 判断浏览器类型是否为360
		 * @return <Boolean>
		 */
		is360: function(){ return fn.is360(); },
		/**
		 * 合并数组元素
		 * @params args <Array> 需要合并的数组集合，该集合也是一个数组
		 * @return <Array> 合并后的数组
		 */
		merger: function(args){ return fn.merger(args); },		
		/**
		 * 将html字符串转换为元素标签
		 * @params args <String> html字符串
		 * @return <Element> html元素
		 */
		formatToElement: function(args){ return fn.formatToElement(args); },
		/**
		 * 获取一组数据中的最大值
		 * @param args <Array> 数据集合
		 * @return <Int> 最大值
		 */
		maxValue: function(args){ return fn.maxValue(args); },
		/**
		 * 获取一组数据中的最小值
		 * @param args <Array> 数据集合
		 * @return <Int> 最小值
		 */
		minValue: function(args){ return fn.minValue(args); },
		/**
		 * 获取一组数据中的平均值
		 * @param args <Array> 数据集合
		 * @param args1 <Int> 保留小数位数(如果有小数，没有则不受影响)
		 * @return <Int> 平均值
		 */
		aveValue: function(args, args1){ return fn.aveValue(args, args1); },
		/**
		 * 四舍五入
		 * @param args1 <Int> 需要四舍五入的数
		 * @param args2 <Int> 保留小数位数(默认为0，取整)
		 * @return <Int> 四舍五入后的数
		 */
		Round: function(args1, args2){ return fn.Round(args1, args2); }
	};
    Main.fn = Main.extend(Main.fn);
	
	/**
	 * Object对象函数库，包含了操作Object对象的公共函数
	 */
	objectLib = {
		/**
		 * 遍历元素
		 * @params <Function> 变量执行的函数
		 * @return <Element> 当前元素对象this
		 */
		each: function(fun){
			for(var i=0,len=this.length;i<len;i++){ fun(i, this[i]); }
			return this;
		},
		/**
		 * 事件冒泡
		 * @param params <Array>
		 * @param events <String> 事件名称
		 * @param element <Object> 触发事件的元素
		 * @return <Element> 当前元素对象this
		 * @remark 未使用第三方插件代码
		 */
		eventBubbling: function(params, events){ return this.each(function(i, elem){ fn.eventBubbling(params, events, elem); }); },
        /**
		 * 屏蔽右键
		 * @param a <Function||String> 右键时执行的函数
		 * @return <Element> 当前元素对象this
		 */
        shieldRightMenu: function (a) { return this.each(function(i, elem){ fn.shieldRightMenu(elem, a); }); },
        /**
		 * 设置元素中的文本不能被选择
		 * @param a <String> 鼠标样式 选填，默认为"default"
		 * @return <Element> 当前元素对象this
		 */
        disableSelection: function (a) { return this.each(function(i, elem){ fn.disableSelection(elem, a); }); },
		/**
		 * 获取元素在页面上的坐标
		 * @return <JSON> {top, left}
		 */
        getElementTL: function () { return fn.getElementTL(this[0]); },
		/**
		 * 解决ie下textarea的maxlength的限制
		 * @return <Element> 当前元素对象this
		*/
		limitLength: function(){ return this.each(function(i, elem){ fn.limitLength(elem); }); },
		/**
		 * 获取单个对象
		 * @param <Int> 下标，从0开始，默认为0
		 * @return <Element> 当前元素对象this
		 */
		get: function(a){ return this[a ? a : 0]; },
		/**
		 * 获取单个对象
		 * @param <Int> 下标，从0开始，默认为0
		 * @return <Element> 当前元素对象this
		 */
		eq: function(a){ return [this[a ? a : 0]]; },
		/**
		 * 取消事件绑定
		 * @param <String> 事件名称，默认为所有的
		 * @return <Element> 当前元素对象this
		 */
		unbind: function(a){ return this.each(function(i, elem){ fn.removeEvent(elem, a); }); },
		/**
		 * 绑定点击事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		click: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'click', fun); }); },
		/**
		 * 绑定mouseover事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		mouseover: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'mouseover', fun); }); },
		/**
		 * 绑定mouseout事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		mouseout: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'mouseout', fun); }); },
		/**
		 * 绑定mousedown事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		mousedown: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'mousedown', fun); }); },
		/**
		 * 绑定mousemove事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		mousemove: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'mousemove', fun); }); },
		/**
		 * 绑定keydown事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		keydown: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'keydown', fun); }); },
		/**
		 * 绑定keyup事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		keyup: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'keyup', fun); }); },
		/**
		 * 绑定keypress事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		keypress: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'keypress', fun); }); },
		/**
		 * 绑定keypress事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		focus: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'focus', fun); }); },
		/**
		 * 绑定keypress事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		blur: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'blur', fun); }); },
		/**
		 * 绑定keypress事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		select: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'select', fun); }); },
		/**
		 * 绑定dblclick事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		dblclick: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'dblclick', fun); }); },
		/**
		 * 绑定change事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		change: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'change', fun); }); },
		/**
		 * 绑定change事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		load: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'load', fun); }); },
		/**
		 * 绑定resize事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		resize: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'resize', fun); }); },
		/**
		 * 绑定selectstart事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		selectstart: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'selectstart', fun); }); },
		/**
		 * 绑定contextmenu事件
		 * @param <Function> 事件执行函数
		 * @return <Element> 当前元素对象this
		 */
		contextmenu: function(fun){ return this.each(function(i, elem){ fn.bindEvent(elem, 'contextmenu', fun); }); },
		/**
		 * 元素查找
		 * @param <String> 查找条件
		 * @return <Element> 当前查找到的元素对象this
		 */
		find: function(a){ return elementHandleFn.findElement(this, a); },
		/**
		 * 直接子集
		 * @param <String> 筛选条件
		 * @return <Element> 子集元素对象
		 */
		children: function(a){ return elementHandleFn.children(this, a); },
		/**
		 * 所有子集(包括子集的子集)
		 * @param <String> 筛选条件
		 * @return <Element> 子集元素对象
		 */
		childrens: function(a){ return elementHandleFn.childrens(this, a); },
		/**
		 * 获取、设置元素的css
		 * @param <String> 属性名
		 * @param <String> 属性值(如果不填，就为获取)
		 * @return <Element> 如果为设置，返回当前元素对象this，如果为获取，就返回当前获取到的第一个元素的属性值
		 */
		css: function(a, b){ return elementHandleFn.css(this, a, b); },
		/**
		 * 获取、设置元素的style属性值
		 * @param <String>选填 style属性值
		 * @param <String>选填 true.替换原有的值 false.追加到原有的值后面
		 * @return <Object>如果为获取属性值则返回的是第一个元素的属性值，如果为设置属性值则返回的是当前元素集合本身this
		 */
		styleText: function(a, b){ return elementHandleFn.styleText(this, a, b); },
		/**
		 * 获取、设置元素属性
		 * @param <String> 属性名
		 * @param <String> 属性值(如果不填，就为获取)
		 * @param <Boolean> 是替换还是追加(选填 默认为替换，如class属性有时需要追加)
		 * @return <Element> 如果为设置，返回当前元素对象this，如果为获取，就返回当前获取到的第一个元素的属性值
		 */
		attr: function(a, b, c){
			if(b){ return elementHandleFn.setAttribute(this, a, b, c); }
			else{ return elementHandleFn.getAttribute(this, a); }
		},
		/**
		 * 移出元素属性
		 * @param <String> 属性名
		 * @param <String> 属性值
		 * @return <Element> 当前元素对象this
		 */
		removeAttribute: function(a, b){ return elementHandleFn.removeAttribute(this, a, b); },
		/**
		 * 从DOM中删除所有匹配的元素
		 */
		remove: function(){ elementHandleFn.removeElement(this); },
		/**
		 * 匹配元素是否存在某个类名
		 * @params <String> 需要匹配的类名
		 * @return <Boolean> 匹配结构
		 */
		hasClass: function(a){ return fn.hasClass(this[0], a); },
		/**
		 * 给元素添加类名
		 * @params <String> 需要添加的类名
		 * @return <Element> 当前元素对象this
		 */
		addClass: function(a){ return elementHandleFn.addClass(this, a); },
		/**
		 * 移除元素的类名
		 * @params <String> 需要移除的类名
		 * @return <Element> 当前元素集合
		 */
		removeClass: function(a){ return elementHandleFn.removeClass(this, a); },
		/**
		 * 获取父级
		 * @return <Element> 父级元素
		 */
        parent: function () { return elementHandleFn.parent(this); },
		/**
		 * 往上获取所有父级(包括父级的父级)
		 * @params <String> 查找父级的条件字符串
		 * @params <Boolean> 在查找所有父级时，是否包括BODY元素对象，默认为false(不包括)
		 * @return <Element> 父级元素集合
		 */
        parents: function (a, b) { return elementHandleFn.parents(this, a, b); },
        /**
		 * 向指定元素中添加指定的子元素级)
		 * @params <String> 需要添加到当前元素作为子级的元素对象
		 * @return <Element> 当前元素对象this
		 */
        append: function (a) { return elementHandleFn.appendChild([this[0]], a); },
        /**
		 * 获取指定元素的直接子级的最后一个元素
		 * @return <Element> 指定元素的直接子级的最后一个元素
		 */
        last: function (a) { return elementHandleFn.last(this); },
        /**
		 * 在指定元素之前添加元素
		 * @params <String> 需要添加的元素对象
		 * @return <Element> 当前元素对象this
		 */
        before: function (a) { return elementHandleFn.before(this, a); },
        /**
		 * 在指定元素之后添加元素
		 * @params <String> 需要添加的元素对象
		 * @return <Element> 当前元素对象this
		 */
        after: function (a) { return elementHandleFn.after(this, a); },
		/**
		 * 获取元素的上一个元素
		 * @params <String> 查找条件
		 * @return <Element> 获取到的元素对象
		 */
		prev: function(a){ return elementHandleFn.nextPrev(this, a, true); },
		/**
		 * 获取元素的下一个元素
		 * @params <String> 查找条件
		 * @return <Element> 获取到的元素对象
		 */
		next: function(a){ return elementHandleFn.nextPrev(this, a, false); },
        /**
		 * 克隆元素
		 * @params <String> 可选。布尔值。规定是否复制元素的所有事件处理。默认为false，副本中不包含事件处理器
		 * @return <Element> 克隆后的元素对象
		 */
        clone: function (a, b) { return elementHandleFn.clone(this, a); },
		/**
		 * 获取元素的宽
		 * @params <Int> 设置元素的宽(若不填，则是获取)
		 * @return <Int/Element> 获取的宽度/当前元素
		 */
		width: function(args){ return elementHandleFn.width(this, args); },
		/**
		 * 获取元素的高
		 * @params <Int> 设置元素的高(若不填，则是获取)
		 * @return <Int/Element> 获取的高度/当前元素
		 */
		height: function(args){ return elementHandleFn.height(this, args); }
	};
	//Main.extend(Array.prototype, objectLib);
	
    /**
	 * String对象函数库，包含了操作String对象的公共函数
	 */
    stringLib = {
        /**
		 * 去掉字符串中间的空格(包括开始和结束)
		 * @param a <String> 自定义需要将空格替换成什么字符(选填，默认为为空字符[""]，即是去掉空格)
		 * @return <String> 替换后的字符串
		 */
        trim: function (a) { return fn.replaces(this, private.regExp.R002, a); },
        /**
		 * 去掉字符串开始的空格
		 * @param a <String> 自定义需要将空格替换成什么字符(选填，默认为为空字符[""]，即是去掉空格)
		 * @return <String> 替换后的字符串
		 */
        lTrim: function (a) { return fn.replaces(this, private.regExp.R003, a); },
        /**
		 * 去掉字符串结束的空格
		 * @param a <String> 自定义需要将空格替换成什么字符(选填，默认为为空字符[""]，即是去掉空格)
		 * @return <String> 替换后的字符串
		 */
        rTrim: function (a) { return fn.replaces(this, private.regExp.R004, a); },
        /**
		 * 替换所有
		 * @param a <String> 被替换字符串
		 * @param b <String> 替换字符串
		 * @return <String> 替换后的字符串
		 * @remark js中没有java中的replaceAll()函数,为了达到与java的replaceAll()一样的效果,我们可以用如下代码实现
		 */
        replaceAll: function (a, b) { return fn.replaceAll(this, a, b); },
        /**
		 * 截取字符串，超过指定长度以"..."代替(默认，可自定义)
		 * @param a <Int> 需要截取字符串的长度
		 * @param b <String> 截取字符串，超过指定长度以"..."代替(默认，可自定义)，该参数就是用户自定义的字符或字符串
		 * @return <String> 截取后的字符串
		 */
        subString: function (a, b) { return fn.subString(this, a, b); },
        /**
		 * 获取一个字符串的字节数
		 * @return <Int> 字节数
		 */
        getCharByte: function () { return fn.getCharByte(this); },
        /**
		 * 判断字符是单字节还是双字节
		 * @return <Int> (-1.单字节 0.双字节 1.单双字节共存)
		 */
        charByte: function () { return fn.charByte(this); },
        /**
		 * 查找字符串a在字符串this中出现的次数
		 * @param a <String> 需要判断出现次数的字符串
		 * @param b <Boolean> 是否忽略大小写(true.区分大小写 false.不区分大小写 默认为true)
		 * @return <Int>字符串a在字符串this中出现的次数
		 */
        countSubstr: function (a, b) { return fn.countSubstr(this, a, b); },
		/**
		 * 移除html标签
		 * @return <String> 去除html后的字符串
		 */
		removeHtml: function () { return fn.removeHtml(this); }
    };
    Main.fn.extend(String.prototype, stringLib);
	
	/**
	 * Tween 常用算法
	 */
	Tween = {
		Linear: function(t,b,c,d){ return c*t/d + b; },
		Quadratic: {
			easeIn: function(t,b,c,d){ return c*(t/=d)*t + b; },
			easeOut: function(t,b,c,d){ return -c *(t/=d)*(t-2) + b; },
			easeInOut: function(t,b,c,d){
				if ((t/=d/2) < 1) return c/2*t*t + b;
				return -c/2 * ((--t)*(t-2) - 1) + b;
			}
		},
		Cubic: {
			easeIn: function(t,b,c,d){ return c*(t/=d)*t*t + b; },
			easeOut: function(t,b,c,d){ return c*((t=t/d-1)*t*t + 1) + b;},
			easeInOut: function(t,b,c,d){
				if ((t/=d/2) < 1) return c/2*t*t*t + b;
				return c/2*((t-=2)*t*t + 2) + b;
			}
		},
		Quartic: {
			easeIn: function(t,b,c,d){ return c*(t/=d)*t*t*t + b; },
			easeOut: function(t,b,c,d){ return -c * ((t=t/d-1)*t*t*t - 1) + b; },
			easeInOut: function(t,b,c,d){
				if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
				return -c/2 * ((t-=2)*t*t*t - 2) + b;
			}
		},
		Quintic: {
			easeIn: function(t,b,c,d){ return c*(t/=d)*t*t*t*t + b; },
			easeOut: function(t,b,c,d){ return c*((t=t/d-1)*t*t*t*t + 1) + b; },
			easeInOut: function(t,b,c,d){
				if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
				return c/2*((t-=2)*t*t*t*t + 2) + b;
			}
		},
		Sinusoidal: {
			easeIn: function(t,b,c,d){ return -c * Math.cos(t/d * (Math.PI/2)) + c + b; },
			easeOut: function(t,b,c,d){ return c * Math.sin(t/d * (Math.PI/2)) + b;},
			easeInOut: function(t,b,c,d){ return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b; }
		},
		Exponential: {
			easeIn: function(t,b,c,d){ return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b; },
			easeOut: function(t,b,c,d){ return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b; },
			easeInOut: function(t,b,c,d){
				if (t==0) return b;
				if (t==d) return b+c;
				if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
				return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
			}
		},
		Circular: {
			easeIn: function(t,b,c,d){ return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b; },
			easeOut: function(t,b,c,d){ return c * Math.sqrt(1 - (t=t/d-1)*t) + b; },
			easeInOut: function(t,b,c,d){
				if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
				return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
			}
		},
		Elastic: {
			easeIn: function(t,b,c,d,a,p){
				if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
				if (!a || a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
			},
			easeOut: function(t,b,c,d,a,p){
				if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
				if (!a || a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				return (a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b);
			},
			easeInOut: function(t,b,c,d,a,p){
				if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
				if (!a || a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
				return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
			}
		},
		Back: {
			easeIn: function(t,b,c,d,s){
				if (s == undefined) s = 1.70158;
				return c*(t/=d)*t*((s+1)*t - s) + b;
			},
			easeOut: function(t,b,c,d,s){
				if (s == undefined) s = 1.70158;
				return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
			},
			easeInOut: function(t,b,c,d,s){
				if (s == undefined) s = 1.70158; 
				if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
				return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
			}
		},
		Bounce: {
			easeIn: function(t,b,c,d){return c - Tween.Bounce.easeOut(d-t, 0, c, d) + b;},
			easeOut: function(t,b,c,d){
				if ((t/=d) < (1/2.75)) {
					return c*(7.5625*t*t) + b;
				} else if (t < (2/2.75)) {
					return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
				} else if (t < (2.5/2.75)) {
					return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
				} else {
					return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
				}
			},
			easeInOut: function(t,b,c,d){
				if (t < d/2) return Tween.Bounce.easeIn(t*2, 0, c, d) * .5 + b;
				else return Tween.Bounce.easeOut(t*2-d, 0, c, d) * .5 + c*.5 + b;
			}
		}
	};
	Main.extend(Tween);
	
	/**
	 * 基础库接口
	 */
	_window.SJ = _window.BS = Main;
	
})(window);
/**JSON2*/
if (!this.JSON) {this.JSON = {};}(function () {function f(n) {return n < 10 ? '0' + n : n;}if (typeof Date.prototype.toJSON !== 'function') {Date.prototype.toJSON = function (key) {return isFinite(this.valueOf())?this.getUTCFullYear()+'-'+f(this.getUTCMonth()+1)+'-'+f(this.getUTCDate())+ 'T' +f(this.getUTCHours())+':'+f(this.getUTCMinutes())+ ':' +f(this.getUTCSeconds())+'Z':null;};String.prototype.toJSON =Number.prototype.toJSON =Boolean.prototype.toJSON = function (key) {return this.valueOf();};}var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta = {'\b': '\\b','\t': '\\t','\n': '\\n','\f': '\\f','\r': '\\r','"' : '\\"','\\': '\\\\'},rep;function quote(string) {escapable.lastIndex = 0;return escapable.test(string)?'"' + string.replace(escapable, function (a) {var c = meta[a];return typeof c === 'string' ? c :'\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);}) + '"' :'"' + string + '"';}function str(key, holder) {var i,k,v,length,mind = gap,partial,value = holder[key];if (value && typeof value === 'object' &&typeof value.toJSON === 'function') {value = value.toJSON(key);}if (typeof rep === 'function') {value = rep.call(holder, key, value);}switch (typeof value) {case 'string':return quote(value);case 'number':return isFinite(value) ? String(value) : 'null';case 'boolean':case 'null':return String(value);case 'object':if(!value) {return 'null';}gap += indent;partial = [];if (Object.prototype.toString.apply(value) === '[object Array]') {length = value.length;for (i = 0; i < length; i += 1) {partial[i] = str(i, value) || 'null';}v = partial.length === 0 ? '[]' :gap ? '[\n' + gap +partial.join(',\n' + gap) + '\n' +mind + ']' :'[' + partial.join(',') + ']';gap = mind;return v;}if (rep && typeof rep === 'object') {length = rep.length;for (i = 0; i < length; i += 1) {k = rep[i];if (typeof k === 'string') {v = str(k, value);if (v) {partial.push(quote(k) + (gap ? ': ' : ':') + v);}}}} else {for (k in value) {if (Object.hasOwnProperty.call(value, k)) {v = str(k, value);if (v) {partial.push(quote(k) + (gap ? ': ' : ':') + v);}}}}v = partial.length === 0 ? '{}' :gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +mind + '}' : '{' + partial.join(',') + '}';gap = mind;return v;}}if (typeof JSON.stringify !== 'function') {JSON.stringify = function (value, replacer, space) {var i;gap = '';indent = '';if (typeof space === 'number') {for (i = 0; i < space; i += 1) {indent += ' ';}} else if (typeof space === 'string') {indent = space;}rep = replacer;if (replacer && typeof replacer !== 'function' &&(typeof replacer !== 'object' ||typeof replacer.length !== 'number')){throw new Error('JSON.stringify');}return str('', {'': value});};} if (typeof JSON.parse !== 'function') { JSON.parse = function (text, reviver) { var j; function walk(holder, key) { var k, v, value = holder[key]; if (value && typeof value === 'object') { for (k in value) { if (Object.hasOwnProperty.call(value, k)) { v = walk(value, k); if (v !== undefined) { value[k] = v; } else { delete value[k]; } } } } return reviver.call(holder, key, value); } text = String(text); cx.lastIndex = 0; if (cx.test(text)) { text = text.replace(cx, function (a) { return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4); }); }if (/^[\],:{}\s]*$/.
test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))){ j = eval('(' + text + ')'); return typeof reviver === 'function' ? walk({'': j}, '') : j; }throw new SyntaxError('JSON.parse'); }; } }());