/**----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// E-mail：407666067@QQ.com
//
// 文件名： JCalendar.js
// 文件功能描述：JS日历控件
// 创建标识：2012年12月18日 14:29:23
//
// 修改标识：
// 修改描述：
//----------------------------------------------------------------*/

SJ.NameSpace.register("SJ.Component");
SJ.Component["JCalendar"] = function(){
	//版本
	this.Version = '1.0.0';
	//组件创建日期
	this.CreateData = '2012年12月18日 14:29:23';
	//组件最后修改日期
	this.FinallyData = '2013年02月04日 15:27:57';
	//组件描述
	this.Remark = 'SJ Component 日期选择控件';
	//版权所有
	this.CopyRight = '思建科技';
	//遵循变量先定义，后使用原则，定义相关变量
	var private, public, element, fn, handle, result;
	//私有变量对象，存放非外部传入的变量
	private = {
		//样式表ID
		StyleSheetIdentity: "SJ_JCalendar_StyleSheet",
		//自定义属性参数
		cusAttr: { format: "format", fors: "for" },
		//星期
		weeks: ['日','一','二','三','四','五','六'],
		//月份
		months: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
		//月份天数
		monthDays: [31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31],
		//当前在控件上选择的日期
		date: { year: "", month: "", day: "", hour: "", minute: "", second: "" },		
		//皮肤颜色配置
		skin: {
			//主色
			mainColor: SJ.mainColor,
			//深色
			darkColor: SJ.darkColor2,
			//浅色
			lightColor: SJ.lightColor6,
			lightColor1: SJ.lightColor15,
			lightColor2: SJ.lightColor4,
			//反主色
			fanColor: SJ.lightColor11,
			//字体色
			fontColor: SJ.darkColor21
		},
		//点击事件配置
		clickEventConf: [
			{nodeName: "td", className: "days", fn: function(event, elem){ fn.clickItem(event, elem); }},
			{nodeName: "td", className: "lYear", fn: function(){ fn.lastYear(); }},
			{nodeName: "td", className: "lMonth", fn: function(){ fn.lastMonth(); }},
			{nodeName: "td", className: "rYear", fn: function(){ fn.nextYear(); }},
			{nodeName: "td", className: "rMonth", fn: function(){ fn.nextMonth(); }},
			{nodeName: "td", className: "calYear", fn: function(event, elem){ fn.chooseYear(event, elem); }},
			{nodeName: "td", className: "calMonth", fn: function(event, elem){ fn.chooseMonth(event, elem); }},
			{nodeName: "label", className: "botToday", fn: function(){ fn.today(); }},
			{nodeName: "label", className: "botClear", fn: function(){ fn.clear(); }},
			{nodeName: "label", className: "botClose", fn: function(){ fn.close(); }}
		],
		//over事件配置
		overEventConf: [
			{nodeName: "td", className: "days", fn: function(event, elem){ fn.overItem(event, elem); }}
		],
		//触发元素显示日历控件面板事件配置
		showEventConf: [
			{nodeName: "", className: "calendar", fn: function(event, elem){ fn.show(elem); }}
		]
	};
	//公共变量对象，存放外部传入参数
	public = {
		//日历控件主层的className
		className: "SJ-JCalendar",
		//关闭时是否删除日历控件元素（如果删除，第二次打开将从新创建）
		isDelete: false,
		//宽度
		width: 180,
		//高度
		height: 200,
		//皮肤颜色
		color: '#A2A2A2'
	};
	//存放内部公共元素对象
	element = {
		watermark: null,
		calPanel: null,
		calTop: null,
		calChgTbl: null,
		lYear: null,
		lMonth: null,
		calYear: null,
		calMonth: null,
		rYear: null,
		rMonth: null,
		calMiddle: null,
		calBottom: null,
		botTime: null,
		botToday: null,
		botClear: null,
		botClose: null,
		calTbl: null,
		calWeeks: null,
		calDays: null,
		chooseMonth: null,
		items: null,
		chooseYear: null
	};
	//内部实现函数对象
	fn = {
		/**
		 * 初始化参数
		 */
		initArgs: function(args){
			if(args){ SJ.extend(public, args, true); }
			fn.skins();
			fn.createStyleSheet();
			fn.createHtml();
		},
		/**
		 * 生成皮肤颜色值
		 */
		skins: function(){
			var skin = private.skin, color = public.color;
			if(color){
				skin.mainColor = color,
				skin.darkColor = SJ.getColor("dark", color, 20);
				skin.lightColor = SJ.getColor("light", color, 60);
				skin.lightColor1 = SJ.getColor("light", color, 150);
				skin.lightColor2 = SJ.getColor("light", color, 40);
				skin.fanColor = SJ.getColor("light", color, 110);
				skin.fontColor = SJ.getColor("dark", color, 200);
				skin.lightColor14 = SJ.getColor("light", color, 140);
				skin.lightColor15 = SJ.getColor("light", color, 150);
				skin.darkColor10 = SJ.getColor("dark", color, 100);
				skin.darkColor5 = SJ.getColor("dark", color, 50);
			}
		},
		/**
		 * 获取月份的天数
		 * @param a.year年
		 * @param b.month月
		 * @return 天数
		*/
		getDays: function(a, b){
			var c, d;
			c = private.monthDays;
			d = c[a];
			if(a == 1) d = ((0 == b % 4) && (0 != (b % 100))) ||(0 == b % 400) ? 29 : 28;
			return d;
		},
		/**
		 * 获取当前日期
		*/
		getToday: function() {
			this.now = new Date();
			this.year = this.now.getFullYear();
			this.month = this.now.getMonth()+1;
			this.day = this.now.getDate();					
			this.hour = this.now.getHours();
			this.minute = this.now.getMinutes();
			this.second = this.now.getSeconds();
		},
		/**
		 * 创建样式表
		 */
		createStyleSheet: function(){
			var style, mainClass, id, skin, fs1, fs2;
			style = [], mainClass = public.className, id = private.StyleSheetIdentity;
			skin = private.skin;
			fs1 = SJ.colStyleCss(skin.darkColor,skin.mainColor);
			fs2 = SJ.colStyleCss(skin.lightColor2,skin.darkColor);
			style.push(
				'.'+mainClass+'{'+
					'border:1px solid '+skin.mainColor+';font-size:11px;position:absolute;float:left;z-index:10000;'+
					'background:'+skin.fanColor+';color:'+skin.fontColor+';'+
				'}'
			);
			style.push(
				'.'+mainClass+' .watermark{'+
					'width:100%;float:left;text-align:center;font-weight:bold;font-family:"微软雅黑";'+
					'color:'+skin.lightColor1+';position:absolute;z-index:4;'+
				'}'+
				'.'+mainClass+' .calPanel{width:100%;float:left;position:absolute;margin-top:top;margin-left:0px;z-index:5;}'+
				'.'+mainClass+' .calTop{'+
					'width:100%;height:30px;line-height:29px;text-align:center;color:'+skin.fanColor+';'+fs1+
					'font-weight:bold;'+
				'}'+
				'.'+mainClass+' .calChgTbl{width:100%;height:100%;text-align:center;font-size:13px;}'+
				'.'+mainClass+' .lYear{width:30px;cursor:pointer;}'+
				'.'+mainClass+' .lMonth{width:25px;cursor:pointer;}'+
				'.'+mainClass+' .calYear{width:auto;cursor:pointer;}'+
				'.'+mainClass+' .calMonth{width:auto;cursor:pointer;}'+
				'.'+mainClass+' .rYear{width:30px;cursor:pointer;}'+
				'.'+mainClass+' .rMonth{width:25px;cursor:pointer;}'+
				'.'+mainClass+' .calMiddle{width:100%;text-align:center;}'+
				'.'+mainClass+' .calBottom{width:100%;height:30px;line-height:30px;text-align:center;font-size:12px;'+fs2+'}'+
				'.'+mainClass+' .botTime{float:left;margin-left:5px;color:'+skin.lightColor1+';}'+
				'.'+mainClass+' .botToday{float:right;margin-right:10px;cursor:pointer;color:'+skin.lightColor1+';}'+
				'.'+mainClass+' .botClear{float:right;margin-right:5px;cursor:pointer;color:'+skin.lightColor1+';}'+
				'.'+mainClass+' .botClose{float:right;margin-right:5px;cursor:pointer;color:'+skin.lightColor1+';}'+
				'.'+mainClass+' .calTbl{width:100%;height:100%;text-aligh:center;}'+
				'.'+mainClass+' .calWeeks{width:100%;font-weight:bold;background:'+skin.lightColor+';}'+
				'.'+mainClass+' .calDays{font-size:13px;}'+
				'.'+mainClass+' .chooseMonth{'+
					'width:60px;height:120px;top:24px;left:80px;position:relative;opacity:0.8;filter:alpha(opacity=80);_moz_opacity:0.8;'+
					'display:none;text-align:center;z-index:10;border:1px solid '+skin.darkColor+';background:'+skin.lightColor+';'+
				'}'+
				'.'+mainClass+' .items{'+
					'width:30px;height:20px;line-height:20px;float:left;cursor:pointer;font-size:12px;'+
				'}'+
				'.'+mainClass+' .chooseYear{'+
					'width:60px;height:140px;top:24px;left:45px;position:relative;opacity:0.8;filter:alpha(opacity=80);_moz_opacity:0.8;'+
					'display:none;text-align:center;z-index:10;border:1px solid '+skin.darkColor+';background:'+skin.lightColor+';'+
				'}'
			);
			SJ.createStyleSheet(style.join('\n'), id);
		},
		/**
		 * 创建Html
		 * @return <Object> 创建的Html对象
		 */
		createHtml: function(){
			var html, mainClass, tempDiv, weeks, monthDays, i, len, j, k, calendar;
			html = [], mainClass = public.className, j = 1;
			weeks = private.weeks, monthDays = private.monthDays;			
			html.push('<div class="'+mainClass+'">');			
			html.push('	<div class="watermark"></div>');
			html.push('	<div class="calPanel">');
			html.push('		<div class="calTop">');
			html.push('			<table cellpadding="0" cellspacing="0" class="calChgTbl"><tr>');
			html.push('				<td class="lYear" title="上一年"><<</td><td class="lMonth" title="上一月""><</td>');
			html.push('				<td class="calYear" title="选择年"></td><td class="calMonth" title="选择月"></td>');
			html.push('				<td class="rMonth" title="下一月">></td><td class="rYear" title="下一年">>></td>');
			html.push('			</tr></table>');
			html.push('		</div>');
			html.push('		<div class="calMiddle">');
			html.push('			<table cellpadding="0" cellspacing="0" class="calTbl">');
			html.push('				<thead class="calWeeks"><tr>');
			for(i=0, len=weeks.length; i<len; i++){ html.push('<td>'+weeks[i]+'</td>'); }
			html.push('				</tr></thead>');
			for(i=0; i<6; i++){
				html.push('				<tr class="calDays">');
				for(k=j, len=(j+7); k<len; k++){ html.push('<td>'+k+'</td>'); j++; }
				html.push('				</tr>');
			}
			html.push('			</table>');
			html.push('		</div>');
			html.push('		<div class="calBottom">');
			html.push('			<label class="botTime">18:00:00</label>');
			html.push('			<label class="botClose" title="关闭">关闭</label>');
			html.push('			<label class="botClear" title="清空选择">清空</label>');
			html.push('			<label class="botToday" title="转到今天">今天</label>');
			html.push('		</div>');
			html.push('	</div>');
			//创建选择年列表
			html.push('<div class="chooseYear"></div>');
			//创建选择月列表
			html.push('<div class="chooseMonth">');
			for(i = 1; i<=12; i++){ html.push('<div class="month items">'+(i<10?("0"+i):i)+'</div>'); }
			html.push('</div>');			
			html.push('</div>');
			tempDiv = document.createElement('div');
			tempDiv.innerHTML = html.join('\r\n');
			caleandar = SJ.children([tempDiv])[0];
			for(i in element){ element[i] = SJ.findElement([caleandar], '.'+i)[0]; }
			element.cMain = caleandar;
			fn.calendarConf();
			SJ.eventBubbling(private.clickEventConf, "click", caleandar);
			SJ.eventBubbling(private.overEventConf, "mouseover", element.calTbl);
		},
		/**
		 * 创建日历
		 * @param a.年
		 * @param b.月
		 * @param s.日
		*/
		calendar: function(a, b){
			var c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, t;
			a = a.toString().length == 1 ? "0"+a : a;
			b = b.toString().length == 1 ? "0"+b : b;
			t = private.date;
			//获取当前年月的第一天排在第几
			c = new Date(a, b-1, 1);
			//获取当前日期
			d = new fn.getToday();
			//start day
			e = c.getDay();
			//end day
			f = fn.getDays(c.getMonth(), c.getFullYear());
			g = -1, h = 0;
			if(d.year == c.getFullYear() && d.month == c.getMonth()){ g = d.day; }
			//获取存放日期的元素
			i = SJ.findElement([element.cMain], '.calDays');
			//day
			q = 0;
			//移除当前选中的项的class this
			var tdThis = SJ.findElement([element.calTbl], 'td.this')[0];
			if(tdThis){
				tdThis.style.background = 'none';
				tdThis.className = tdThis.className.replaceAll(' this', '');
			}
			//循环行
			for(j=0,k=i.length; j<k; j++){
				//获取行下面的列
				l = SJ.findElement([i[j]], 'td');
				n=l.length;
				for(m=0;m<n;m++){ l[m].className = '', l[m].innerHTML = ''; }
				//开始位置
				o = j == 0 ? e : 0;
				for(m=o; m<n; m++){
					p = l[m];
					if(q == f){ break; }else{ ++q; }
					q = q.toString().length == 1 ? "0"+q : q;
					p.style.cursor = 'pointer', p.className = 'days', p.innerHTML = q;
					if(q == t.day){
						p.className += ' this';
						p.style.background = private.skin.lightColor2;
					}
				}
			}
			var month = t.month.toString().length == 1 ? "0"+t.month : t.month;
			//设置头部日期
			element.calYear.innerHTML = t.year, element.calMonth.innerHTML = month;
			//设置年月为水印
			element.watermark.innerHTML = t.year+"-"+month;
		},
		/**
		 * 点击日期项事件
		*/
		clickItem: function(event, elem){
			var a, b, c, d;
			a = private;
			c = private.date;
			//设置当前点击的日
			c.day = elem.innerHTML;
			c.month = c.month.toString().length == 1 ? "0"+c.month : c.month;
			//获取当前的时分秒	
			d = new fn.getToday();
			c.hour = d.hour.toString().length == 1 ? "0"+d.hour : d.hour;
			c.minute = d.minute.toString().length == 1 ? "0"+d.minute : d.minute;
			c.second = d.second.toString().length == 1 ? "0"+d.second : d.second;
			//获取用户自定义的格式
			b = element.bElem.getAttribute(a.cusAttr.format);
			b = b.replace("YYYY", c.year).replace("MM", c.month).replace("DD", c.day)
				.replace("hh", c.hour).replace("mm", c.minute).replace("ss", c.second);
			element.eElem.value = b;
			fn.close();
		},
		/**
		 * Over日期项事件
		*/
		overItem: function(event, elem){
			elem.style.background = private.skin.lightColor;
			elem.onmouseout = function(){
				setTimeout(function(){
					elem.style.background = SJ.hasClass(elem, 'this') ? private.skin.lightColor2 : 'none';
				},50);
			};
		},
		/**
		 * LastYear 上一年
		*/
		lastYear: function(){
			var a = private.date;
			a.year -= 1;
			fn.calendar(a.year, a.month);
		},
		/**
		 * LastMonth 上一月
		*/
		lastMonth: function(){
			var a = private.date;
			a.month -= 1;
			if(a.month == 0){ a.year -= 1; a.month = 12; }
			fn.calendar(a.year, a.month);
		},
		/**
		 * NextYear 下一年
		*/
		nextYear: function(){
			var a = private.date;
			a.year = parseInt(a.year)+1;
			fn.calendar(a.year, a.month);
		},
		/**
		 * NextMonth 下一月
		*/
		nextMonth: function(){
			var a = private.date;
			if(a.month.toString().substring(0,1) == "0"){ a.month = a.month.substring(1, 2); }
			a.month = parseInt(a.month) + 1;
			if(a.month == 13){ a.year = parseInt(a.year) + 1; a.month = 1; }
			fn.calendar(a.year, a.month);
		},
		/**
		 * today 今天
		*/
		today: function(){
			var a, b;
			a = private.date;
			b = new fn.getToday();
			a.year = b.year;
			a.month = b.month;
			a.day = b.day;
			fn.calendar(a.year, a.month);
		},
		/**
		 * clear 清空
		*/
		clear: function(){
			element.eElem.value = '';
		},
		/**
		 * 选中年
		 * @param a:window event
		 * @param b:event element
		*/
		chooseYear: function(a, b){
			var c, d;
			//重新设置选择年的列表
			fn.setChooseYear(private.date.year);
			element.chooseMonth.style.display = 'none';
			c = element.chooseYear;
			c.style.display = 'block';
			c.onmouseover = function(){ window.clearTimeout(d); d == null; c.style.display = 'block'; };
			c.onmouseout = function(){ d = window.setTimeout(function(){ c.style.display = 'none'; }, 100); };
			c.onmouseup = function(event){ fn.chooseItems(event, "year", c, b); };
		},
		/**
		 * 选中月
		 * @param a:window event
		 * @param b:event element
		*/
		chooseMonth: function(a, b){
			var c, d;
			element.chooseYear.style.display = 'none';
			c = element.chooseMonth;
			c.style.display = 'block';
			c.onmouseover = function(){ window.clearTimeout(d); d == null; c.style.display = 'block'; };
			c.onmouseout = function(){ d = window.setTimeout(function(){ c.style.display = 'none'; }, 100); };
			c.onmouseup = function(event){ fn.chooseItems(event, "month", c, b); };
		},
		/**
		 * choose year/month items
		 * @param a:window event
		 * @param b:flag
		 * @param c:element
		*/
		chooseItems: function(a, b, c, d){
			var e, f, g, h, i, j;
			e = a || window.event;
			f = e.target || e.srcElement;
			g = f.nodeName;
			h = f.className;
			if(g == "DIV"){
				if(SJ.hasClass(f, b)){
					i = f.innerHTML, j = private.date;
					d.innerHTML = i, j[b] = i, c.style.display = 'none';
					fn.calendar(j.year, j.month);					
				}
				else if(SJ.hasClass(f, "up")){
					fn.setChooseYear(parseInt(SJ.findElement([element.chooseYear], '.items')[0].innerHTML)-12);
				}
				else if(SJ.hasClass(f, "next")){
					fn.setChooseYear(parseInt(SJ.findElement([element.chooseYear], '.items')[0].innerHTML)+12);
				}
			}
		},
		/**
		 * set choose year
		 * @param a:开始年份
		*/
		setChooseYear: function(a){
			var b, c;
			a = parseInt(a);
			c = [];
			for(b=a; b<a+12; b++){ c.push('<div class="year items">'+(b)+'</div>'); }
			c.push('<div class="up items" title="上一批"><<</div>');
			c.push('<div class="next items" title="下一批">>></div>');
			element.chooseYear.innerHTML = c.join('\r\n');
		},
		/**
		 * times 时间
		*/
		times: function(){
			var a, b, c, d, e, f;
			a = function(){
				b = new fn.getToday();
				c = b.hour, d = b.minute, e = b.second;
				c = c.toString().length == 1 ? "0"+c : c;
				d = d.toString().length == 1 ? "0"+d : d;
				e = e.toString().length == 1 ? "0"+e : e;
				f = c;
				if(c.toString().substring(0,1) == "0"){ f = c.substring(1, 2); }
				element.botTime.innerHTML = ((f<12 ? "AM " : "PM ")+c+":"+d+":"+e);
				setTimeout(a, 1000);
			};
			a();
		},
		/**
		 * 参数配置
		 */
		calendarConf: function(){
			var skin, width, height, cssText;
			skin = private.skin;
			width = parseInt(public.height);
			height = parseInt(public.height);
			cssText = "width:"+width+"px;height:"+height+"px;";
			element.cMain.style.cssText = cssText;
			cssText = "height:"+(height/2)+"px;line-height:"+(height/2)+"px;margin-top:"+(height/3.5)+"px;font-size:"+(height/5)+"px;"
			element.watermark.style.cssText = cssText;
			element.calPanel.style.height = height+"px";
			element.calMiddle.style.height = (height-60)+"px";
		},
		/**
		 * 显示日历控件
		 * @param a.当前触发显示日历控件事件的元素
		*/
		show: function(a){
			var b, d, e, f, g, k, l, m, n, o, p, q;
			e = private;
			f = public;
			q = private.date;
			//获取当前操作元素上的自定义配置属性
			l = a.getAttribute(e.cusAttr.fors);
			o = a.getAttribute(e.cusAttr.format);
			//获取当前元素关联元素
			m = l ? SJ(l)[0] : a;
			//获取元素的日期值
			n = m.value;
			//定义日期接收变量
			var date = new fn.getToday();
			var YY = null, MM = null, DD = null, hh = null, mm = null, ss = null;
			if(n){
				p = o.indexOf("YYYY");
				YY = p>-1 ? n.substring(p, p+4) : null;
				p = o.indexOf("MM");
				MM = p>-1 ? n.substring(p, p+2) : null;
				p = o.indexOf("DD");
				DD = p>-1 ? n.substring(p, p+2) : null;
				p = o.indexOf("hh");
				hh = p>-1 ? n.substring(p, p+2) : null;
				p = o.indexOf("mm");
				mm = p>-1 ? n.substring(p, p+2) : null;
				p = o.indexOf("ss");
				ss = p>-1 ? n.substring(p, p+2) : null;
			}
			q.year = YY = YY ? YY : date.year;
			q.month = MM = MM ? MM : date.month;
			q.month = q.month.toString().length == 1 ? "0"+q.month : q.month;
			q.day = DD = DD ? DD : date.day;
			q.day = q.day.toString().length == 1 ? "0"+q.day : q.day;
			q.hour = hh = hh ? hh : date.hour;
			q.hour = q.hour.toString().length == 1 ? "0"+q.hour : q.hour;
			q.minute = mm = mm ? mm : date.minute;
			q.minute = q.minute.toString().length == 1 ? "0"+q.minute : q.minute;
			q.second = ss = ss ? ss : date.second;
			q.second = q.second.toString().length == 1 ? "0"+q.second : q.second;
			//创建日历面板
			if(!element.cMain){ fn.createHtml(); }
			fn.calendar(YY, MM, DD);
			//保存存放日期选择值的元素
			d = SJ(a.getAttribute(e.cusAttr.fors));
			element.bElem = a;
			element.eElem = d!='' ? d[0] : a;
			g = SJ.getElementTL(a);
			element.cMain.style.top = (g.top+parseInt(a.offsetHeight))+"px";
			element.cMain.style.left = g.left+"px";
			//添加日历面板元素到页面
			SJ.before([a], [element.cMain]);
			element.cMain.style.display = 'block';
			//隐藏选中年和选中月的列表
			element.chooseYear.style.display = 'none', element.chooseMonth.style.display = 'none';
			//禁止内容被选择
			SJ.disableSelection(element.cMain);
			fn.times();
		},
		/**
		 * 关闭日历控件
		*/
		close: function(){
			var cMain = element.cMain;
			if(cMain){ public.isDelete ? SJ.removeElement([cMain]) : cMain.style.display = 'none'; }
		}
	};
	//内部函数操作对象
	handle = {
		/**
		 * 初始化
		 */
		init: function(args){
			fn.initArgs(args);
			handle.bindEvent();
		},
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			SJ.eventBubbling(private.showEventConf, "click");
			var calendar, input, TL;
			SJ.bind(window, 'resize', function(){
				calendar = element.cMain;
				if(calendar.length > 0){
					input = SJ.nextPrev(calendar);
					TL = SJ.getElementTL(input[0]);
					calendar = calendar[0].style;
					calendar.top = (TL.top+(parseInt(input.offsetHeight)+4))+"px";
					calendar.left = (TL.left)+"px";
				}
			});
		}
	};
	handle.init();
	//返回对象，提供外部调用的函数
	result = {};
	SJ.extend(this, result);
};