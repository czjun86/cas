/**----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// E-mail：407666067@QQ.com
//
// 文件名： JSelects.js
// 文件功能描述：JS下拉
// 创建标识：2012年12月17日 10:52:33
	需求：
		1.自定义组件的宽和高，所需创建到的父级
		2.自定义组件的类名和ID
		3.自定义下拉改变事件、特定样式
		4.自定义组件的皮肤颜色
		5.自定义下拉数据
	功能：
		1.提供动态选中某项
		2.提供重新给组件绑定数据
		3.提供动态获取当前选中项的下标、值和文本
		4.提供动态设置禁用和取消禁用功能
		5.可通过JS创建，也可通过使用系统的下拉来创建(可在系统下拉上设置选中、禁用、点击事件属性)
	开发周期：45个工作日    测试：2个工作日    维护：60个工作日
// 修改标识：2013年1月24日 15:05:56
// 修改描述：添加禁用功能
//----------------------------------------------------------------*/

SJ.NameSpace.register("SJ.Component");
SJ.Component["JSelect"] = function(args){
	//版本
	this.Version = '1.0.1';
	//组件创建日期
	this.CreateData = '2012年12月17日 10:52:33';
	//组件最后修改日期
	this.FinallyData = '2013年1月24日 15:05:56';
	//组件描述
	this.Remark = 'SJ Component 下拉(<label class="red">维护期</label>)';
	//版权所有
	this.CopyRight = '思建科技';
	//遵循变量先定义，后使用原则，定义相关变量
	var private, public, element, fn, handle, result;
	//私有变量对象，存放非外部传入的变量
	private = {
		//在不显示滚动条情况下的最多下拉项
		maxItem: 10,
		//每一项下拉的高度
		itemsHeight: 24,
		//下拉框主层ClassName
		mainClass: 'SJ-JSelects',
		//下拉框样式ID
		StyleSheetIdentity: 'SJ_JSelects_StyleSheet',	
		//下拉框皮肤
		skin: {
			mainColor: SJ.mainColor,
			lightColor1: SJ.lightColor1,
			lightColor4: SJ.lightColor4,
			lightColor6: SJ.lightColor6,
			lightColor8: SJ.lightColor8,
			lightColor10: SJ.lightColor10,
			lightColor12: SJ.lightColor12,
			lightColor14: SJ.lightColor14,
			lightColor15: SJ.lightColor15,
			darkColor10: SJ.darkColor10,
			darkColor5: SJ.darkColor5
		}
	};
	//公共变量对象，存放外部传入参数
	public = {
		//是否将当前选择的项在list中选中
		isSelect: true,
		//自定义类名
		clas: "JSelects",
		//自定义ID
		ids: null,
		//宽
		width: 120,
		//高
		height: 31,
		//下拉选项
		items: [{val: 0, txt: "请选择"}],
		//change事件
		change: null,
		//是否禁用下拉
		disabled: null,
		//父级元素对象
		pNode: document.body,
		//特定样式
		style: null,
		//是否使用图片做皮肤
		useImage: true,
		//图片皮肤
		skinImage: {
			//下拉框左边部分
			select_left: '../images/select-left.png',
			//下拉框右边部分
			select_right: '../images/select-right.png',
			//下拉框中间部分
			select_middle: '../images/select-middle.png',
			//下拉框按钮部分
			select_button: '../images/select-button.png',
			//下拉框List的左上角
			select_list_lt: '../images/select-list-lt.png',
			//下拉框List的左下角
			select_list_lb: '../images/select-list-lb.png',
			//下拉框List的右上角
			select_list_rt: '../images/select-list-rt.png',
			//下拉框List的右下角
			select_list_rb: '../images/select-list-rb.png'
		},
		//颜色值
		color: '#8E8E8E'
		//color: null
	};
	//存放内部公共元素对象
	element = {
		jSelPanel: null,
		jSelText: null,
		jSelButton: null,
		jSelList: null
	};
	//内部实现函数对象
	fn = {
		/**
		 * 生成皮肤颜色值
		 */
		skins: function(){
			if(public.color){
				var skin = private.skin, color = public.color;
				skin.mainColor = color,
				skin.lightColor1 = SJ.getColor("light", color, 10);
				skin.lightColor4 = SJ.getColor("light", color, 40);
				skin.lightColor6 = SJ.getColor("light", color, 60);
				skin.lightColor8 = SJ.getColor("light", color, 80);
				skin.lightColor10 = SJ.getColor("light", color, 100);
				skin.lightColor12 = SJ.getColor("light", color, 120);
				skin.lightColor14 = SJ.getColor("light", color, 140);
				skin.lightColor15 = SJ.getColor("light", color, 150);
				skin.darkColor10 = SJ.getColor("dark", color, 100);
				skin.darkColor5 = SJ.getColor("dark", color, 50);
			}
		},
		/**
		 * 创建样式表
		 */
		createStyleSheet: function(){
			var style, mainClass, id, skin, fs1, fs2, fs3, fs4, fs5;
			style = [], mainClass = private.mainClass, id = private.StyleSheetIdentity, skin = private.skin;
			fs1 = public.useImage?'':SJ.colStyleCss(skin.lightColor14,skin.lightColor6);
			fs2 = SJ.colStyleCss(skin.lightColor12,skin.lightColor4);
			fs3 = SJ.colStyleCss(skin.lightColor10,skin.lightColor1);
			fs4 = SJ.colStyleCss(skin.lightColor10,skin.lightColor6);
			fs5 = SJ.colStyleCss(skin.lightColor8,skin.lightColor4);
			style.push(
				'.'+mainClass+'{'+
					'font-size:12px;position:relative;border:1px solid '+skin.mainColor+';background:'+skin.lightColor15+';'+
					'-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;color:'+skin.darkColor10+';'+
				'}'
			);
			style.push('.'+mainClass+' .B-Circle{-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;}');
			style.push(
				'.'+mainClass+' .JSelect-Left{'+
					'float:left;overflow:hidden;width:0px;height:100%;font-size:0px;'+
				'}'+
				'.'+mainClass+' .JSelect-Right{'+
					'float:left;overflow:hidden;width:0px;height:100%;font-size:0px;'+
				'}'+
				'.'+mainClass+' .JSelect-Text{'+
					'float:left;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;padding-left:2px;'+
				'}'+
				'.'+mainClass+' .JSelect-Button{float:left;width:16px;height:100%;}'+
				'.'+mainClass+' .JSelect-List{'+
					'border:1px solid '+skin.mainColor+';background:'+skin.lightColor15+';overflow-x:hidden;overflow-y:auto;'+
					'position:absolute;top:-9999px;left:-9999px;-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;'+
					'filter:alpha(opacity=30);_moz_opacity:0.3;opacity:0.3;'+
				'}'
			);
			style.push(
				'.JSelect-Button .JSelect-Btn{'+
					'border:1px solid '+skin.mainColor+';margin:2px 2px;'+
					'overflow:hidden;width:100%;height:75%;cursor:pointer;'+fs1+
				'}'+
				'.JSelect-Button .JSelect-Btns{'+
					'border:1px solid '+skin.mainColor+';margin:2px 2px;'+
					'overflow:hidden;width:100%;height:75%;cursor:default;background:none;'+
				'}'+
				'.JSelect-Button .JSelect-Btn-Over{'+fs2+'}'+
				'.JSelect-Button .JSelect-Btn-Down{'+fs3+'}'
			);
			if(!public.useImage){
				style.push(
					'.JSelect-Button b{'+
						'border-style:solid dashed dashed;border-width:5px;font-size:0px;width:0px;height:0px;cursor:pointer;'+
						'float:left;margin-top:6px;margin-left:4px;border-color:'+skin.darkColor5+' transparent transparent;'+
					'}'
				);
			}
			style.push(
				'.JSelect-List .JSelect-Item{cursor:pointer;float:left;overflow:hidden;}'+
				'.JSelect-List .JSelect-Item-Text{'+
					'cursor:pointer;float:left;overflow:hidden;'+
					'white-space:nowrap;text-overflow:ellipsis;margin:1px;border:1px solid '+skin.lightColor15+';'+
				'}'
			);
			style.push('.JSelect-List .JSelect-Item-Over{border:1px solid '+skin.lightColor1+';'+fs4+'}');
			style.push('.JSelect-List .JSelect-Item-Down{border:1px solid '+skin.lightColor1+';'+fs5+'}');
			SJ.createStyleSheet(style.join('\n'), id);
		},
		/**
		 * 创建Html
		 */
		createHtml: function(){
			var html, mainClass, tempDiv, pNode, cNode, cName1, cName2, clas, ids;
			html = [], mainClass = private.mainClass, pNode = public.pNode;
			clas = public.clas, ids = public.ids;
			var mclas = mainClass + (clas ? ' '+clas : '');
			var mids = ids ? ' id="'+ids+'" ' : '';
			html.push('<div class="'+mclas+'"'+mids+'>');
			html.push('	<div class="JSelect-Left"></div>');
			html.push('	<div class="JSelect-Text"></div>');
			html.push('	<div class="JSelect-Button"><div class="JSelect-Btn B-Circle"><b class="jSelect-B"></b></div></div>');
			html.push('	<div class="JSelect-List">');
			/*html.push('		<div class="JSelect-Lists-Item"></div>');
			html.push('		<div class="JSelect-List-Border"></div>');
			html.push('		<div class="JSelect-List-LT"></div>');
			html.push('		<div class="JSelect-List-LB"></div>');
			html.push('		<div class="JSelect-List-RT"></div>');
			html.push('		<div class="JSelect-List-RB"></div>');*/
			html.push('	</div>');
			html.push('	<div class="JSelect-Right"></div>');
			html.push('</div>');
			tempDiv = document.createElement('div');
			tempDiv.innerHTML = html.join('\r\n');
			element.jSelPanel = SJ.children([tempDiv])[0];
			cNode = SJ.children([element.jSelPanel]);
			element.jSelLeft = cNode[0];
			element.jSelText = cNode[1];
			element.jSelButton = cNode[2];
			element.jSelBtn = SJ.children([element.jSelButton])[0];
			element.jSelBtnB = SJ.children([element.jSelBtn])[0];
			element.jSelList = cNode[3];
			element.jSelRight = cNode[4];
			fn.bindItems(public.items, element.jSelList);
			if(pNode){ SJ(pNode)[0].appendChild(element.jSelPanel); }
			SJ.disableSelection(element.jSelPanel);
			if(public.disabled){ fn.disabled(); }
		},
		/**
		 * 参数配置
		 */
		argsConf: function(){
			var width, height, style, jSelPanel, jSelText, jSelList, key, len, count, items;
			var jSelButton, jSelBtnB, jSelLeft, jSelRight, txtF = 0;
			width = public.width, height = public.height, style = public.style;
			jSelPanel = element.jSelPanel.style;
			jSelText = element.jSelText.style;
			jSelList = element.jSelList.style;
			jSelButton = element.jSelButton.style;
			jSelBtn = element.jSelBtn.style;
			jSelBtnB = element.jSelBtnB.style;
			jSelLeft = element.jSelLeft.style;
			jSelRight = element.jSelRight.style;
			//判断是否使用图片作为皮肤
			if(public.useImage){
				jSelLeft.width = '8px';
				jSelLeft.background = 'url("'+public.skinImage.select_left+'")';
				jSelRight.width = '8px';
				jSelRight.background = 'url("'+public.skinImage.select_right+'")';
				jSelText.background = 'url("'+public.skinImage.select_middle+'")';
				jSelButton.background = 'url("'+public.skinImage.select_button+'")';
				jSelButton.border = 'none';
				jSelButton.borderRadius = '0px';
				jSelBtn.background = 'none';
				jSelBtn.border = 'none';
				jSelPanel.border = 'none';
				jSelPanel.background = 'none';
				txtF = -5;
			}			
			len = public.items.length;
			count = len>private.maxItem ? private.maxItem : len;
			jSelPanel.width = width+'px', jSelPanel.height = height+'px';
			for(key in style){ jSelPanel[key] = style[key]; }
			var leftWidth = element.jSelLeft.offsetWidth;
			var rightWidth = element.jSelRight.offsetWidth;
			jSelText.width = (width-24-leftWidth-rightWidth-txtF)+'px';
			jSelText.height = (height)+'px';
			jSelText.lineHeight = height+'px';
			jSelList.width = (width)+'px';
			jSelList.height = (count*private.itemsHeight)+'px';
			jSelBtn.height = (height - 6)+'px';
			jSelBtnB.height = (height - 6)+'px';
			jSelBtnB.marginTop = (((height - 6)/2)-1)+'px';
			jSelBtnB.marginLeft = (((element.jSelButton.offsetWidth)/2)-5)+'px';
		},
		/**
		 * 下拉框的Click事件
		 * @param <Event> 当前事件对象
		 */
		clickSelect: function(event){
			event = event || window.event;
			var target, cName, node, jSelList, lists, list, i, len, filter, thread, timer, B, Btn, Button, _This;
			target = event.target || event.srcElement;
			cName = target.className;
			node = target.nodeName.toUpperCase();
			filter = 30, timer = SJ.browser().firefox ? 20 : 10;
			lists = SJ('.JSelect-List[JSelConf=show]');
			len = lists.length;
			for(i=0;i<len;i++){
				list = lists[i].style; list.top = "-9999px"; list.left = "-9999px";
				list['filter'] = 'alpha(opacity='+filter+')';
				list['opacity'] = filter/100;
				list['_moz_opacity'] = filter/100;
			}
			B = SJ.hasClass(target,"jSelect-B");
			Btn = SJ.hasClass(target,"JSelect-Btn");
			Button = SJ.hasClass(target,"JSelect-Button");
			if((node=="B"&&B)||(node=="DIV"&&Btn||Button)){
				if(B){ target = target.parentNode.parentNode.parentNode; }
				else if(Btn){ target = target.parentNode.parentNode; }
				else if(Button){ target = target.parentNode; }
				if(!target.getAttribute('disabl')){
					jSelList = SJ.children([target])[3];
					jSelList.setAttribute("JSelConf", "show");
					_This = SJ.findElement([jSelList], '.this')[0];
					_This.className += ' JSelect-Item-Over';
					jSelList = jSelList.style;
					jSelList.left = "-1px";
					jSelList.top = (element.jSelPanel.offsetHeight-2)+'px';				
					thread = new SJ.Thread(function(){
						filter += 5;
						jSelList['filter'] = 'alpha(opacity='+filter+')';
						jSelList['opacity'] = filter/100;
						jSelList['_moz_opacity'] = filter/100;
						if(filter >= 100){ thread.stop(); }
					}, timer);
					thread.run();
				}
			}
		},
		/**
		 * 下拉框的Over事件
		 * @param <Event> 当前事件对象
		 */
		overSelect: function(event){
			event = event || window.event;
			var target, _target, cName, node, cName1, cName2, jSelBtn, flag;
			target = event.target || event.srcElement;
			cName = target.className;
			node = target.nodeName.toUpperCase();
			if((node=="B"&&SJ.hasClass(target,"jSelect-B"))||(node=="DIV"&&SJ.hasClass(target,"JSelect-Btn"))){
				if(!public.useImage){
					cName1 = " JSelect-Btn-Over";
					cName2 = " JSelect-Btn-Down";
					jSelBtn = element.jSelBtn;
					jSelBtn.className += cName1;
					target.onmouseout = function(){ jSelBtn.className = jSelBtn.className.replaceAll(cName1, ""); };
					target.onmousedown = function(){
						jSelBtn.className = jSelBtn.className.replaceAll(cName1, cName2);
						this.onmouseup = function(){ jSelBtn.className = jSelBtn.className.replaceAll(cName2, cName1); };
						this.onmouseout = function(){
							jSelBtn.className = jSelBtn.className.replaceAll(cName2, cName1).replaceAll(cName1, '');
						};
					};
				}
			}
			else if(node=="DIV"&&(SJ.hasClass(target,"JSelect-Item-Text")||SJ.hasClass(target,"JSelect-Item"))){
				cName1 = " JSelect-Item-Over";
				cName2 = " JSelect-Item-Down";
				flag = SJ.hasClass(target,"JSelect-Item");
				if(flag){
					_target = SJ.children([target])[0];
					if(!SJ.hasClass(_target, 'this')){
						_target.className += cName1;
						target.onmouseout = function(){
							var $this = SJ.children([this])[0];
							if(!SJ.hasClass($this, 'this')){ $this.className  = $this.className.replaceAll(cName1, ""); }
						};
						target.onmousedown = function(){
							var $this = SJ.children([this])[0];
							$this.className  = $this.className.replaceAll(cName1, cName2);
							this.onmouseout = function(){
								if(!SJ.hasClass($this, 'this')){ $this.className  = $this.className.replaceAll(cName2, ""); }
							};
						};
					}
				}
				else{
					if(!SJ.hasClass(target, 'this')){ 
						target.className += cName1;
						target.onmouseout = function(){ this.className  = this.className.replaceAll(cName1, ""); };
						target.onmousedown = function(){
							this.className  = this.className.replaceAll(cName1, cName2);
							this.onmouseout = function(){ this.className  = this.className.replaceAll(cName2, ""); };
						};
					}
				}
			}
		},
		/**
		 * 下拉框选项的Click事件
		 * @param <Event> 当前事件对象
		 */
		clickItems: function(event){
			event = event || window.event;
			var target, cName, node;
			target = event.target || event.srcElement;
			cName = target.className;
			node = target.nodeName.toUpperCase();
			if(node=="DIV"&&(SJ.hasClass(target,"JSelect-Item-Text")||SJ.hasClass(target,"JSelect-Item"))){
				flag = SJ.hasClass(target,"JSelect-Item");
				var html, val, index, textNode, _ThisNode;
				if(flag){ target = SJ.children([target])[0]; }
				html = target.innerHTML, val = target.getAttribute("data"), index = target.getAttribute("selectedIndex");
				_ThisNode = SJ.findElement([target.parentNode.parentNode], '.this')[0];
				if(_ThisNode){
					_ThisNode.className = _ThisNode.className.replaceAll(' JSelect-Item-Over', '')
																.replaceAll(' JSelect-Item-Down', '')
																.replaceAll(' this', '');
				}
				target.className += ' this';
				textNode = target.parentNode.parentNode.parentNode;
				textNode.setAttribute("data", val);
				textNode.setAttribute("selectedIndex", index);					
				textNode.setAttribute("title", html);
				SJ.children([textNode])[1].innerHTML = html;
				if(public.change){
					public.change({elem: element.jSelPanel, val: val, txt: html, selectedIndex: index});
				}
			}
		},
		/**
		 * 绑定下拉的选项
		 * @param <Array> args1 下拉的选项集合
		 * @param <Object> args2 List元素
		 */
		bindItems: function(args1, args2){
			args2 = args2 ? args2 : element.jSelList;
			var html, i, len, items, selected, textNode, w, h, temp;
			html = [];
			public.items = args1;
			len = args1.length;
			w = public.width, h = private.itemsHeight;
			for(i=0;i<len;i++){
				items = args1[i];
				selected = items.selected;
				if(i==0||selected=="true"||selected=="selected"||selected==true){
					textNode = args2.parentNode;
					textNode.setAttribute("data", items.val);
					textNode.setAttribute("selectedIndex", i);					
					textNode.setAttribute("title", items.txt);
					SJ.children([textNode])[1].innerHTML = items.txt;
					temp = i;
				}
				html.push('<div class="JSelect-Item" style="width:'+(w)+'px;height:'+(h)+'px;">');
				html.push('<div class="JSelect-Item-Text" ');
				html.push('style="width:'+(w-4)+'px;height:'+(h-4)+'px;line-height:'+(h-4)+'px;"');
				html.push(' data="'+items.val+'" selectedIndex="'+i+'" title="'+items.txt+'">'+items.txt+'</div></div>');
			}
			args2.innerHTML = html.join('\r\n');
			SJ.children(SJ.children([args2], temp))[0].className += ' this';
			args2.onclick = function(event){ fn.clickItems(event); };
			fn.argsConf();
		},
		/**
		 * 指定select选中哪一项
		 * @param <String> args1.选中哪一项（或其它），默认是以list的下标从0开始
		 * @param <String> args2.如何指定选中项的key值，可以是selectIndex（默认）、data(当前项的value值)
		 */
		selected: function(args1, args2){
			var items, i, len, node;
			args2 = args2 ? args2 : "data";
			items = SJ.children(SJ.children([element.jSelList]));
			for(i=0, len=items.length; i<len; i++){
				node = items[i];
				if(args1 == node.getAttribute(args2)){ node.click(); break; }
			}
		},
		/**
		 * 禁用下拉框
		 */
		disabled: function(){
			try{
				var jSelPanel = element.jSelPanel;
				jSelPanel.setAttribute('disabl', 'disabl');
				jSelPanel.style.color = '#A0A0A0';
				jSelPanel.style.borderColor="#A0A0A0";
				jSelPanel.style.background = '#EEEEEE';
				var JSelectButton = SJ.findElement([jSelPanel], '.JSelect-Btn')[0];
				JSelectButton.style.borderColor="#A0A0A0";
				JSelectButton.className = JSelectButton.className.replace('JSelect-Btn', 'JSelect-Btns');
				SJ.children([JSelectButton])[0].style.borderColor = '#A0A0A0 transparent transparent';
				element.jSelBtnB.className = 'jSelect-Bs';
			}catch(ex){}
		},
		/**
		 * 取消下拉禁用
		 */
		undisabled: function(){
			try{
				var skin = private.skin;
				var jSelPanel = element.jSelPanel;
				jSelPanel.setAttribute('disabl', '');
				jSelPanel.style.color = skin.darkColor10;
				jSelPanel.style.borderColor = skin.mainColor;
				jSelPanel.style.background = skin.lightColor15;
				var JSelectButton = SJ.findElement([jSelPanel], '.JSelect-Btns')[0];
				JSelectButton.style.borderColor = skin.mainColor;
				JSelectButton.className = JSelectButton.className.replace('JSelect-Btns', 'JSelect-Btn');
				SJ.children([JSelectButton])[0].style.borderColor = skin.darkColor5+' transparent transparent';
				element.jSelBtnB.className = 'jSelect-B';
			}catch(ex){}
		}
	};
	//内部函数操作对象
	handle = {
		/**
		 * 初始化参数
		 */
		initArgs: function(args){
			if(args){ SJ.extend(public, args, true); }
			if(args.style){ public.style = args.style; }
			fn.skins();
			fn.createStyleSheet();
			fn.createHtml();
			fn.argsConf();
			handle.bindEvent();
		},
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			var click_fn = function(event){ fn.clickSelect(event); };
			var over_fn = function(event){ fn.overSelect(event); };
			SJ.unbind(document, 'click', click_fn);
			SJ.unbind(element.jSelPanel, 'mouseover', over_fn);
			SJ.bind(document, 'click', click_fn);
			SJ.bind(element.jSelPanel, 'mouseover', over_fn);
		}
	};
	handle.initArgs(args);
	//返回对象，提供外部调用的函数
	result = {
		/**
		 * 初始化
		 */
		init: function(args){ handle.init(args); },
		/**
		 * 指定select选中哪一项
		 * @param <String> args1.选中哪一项（或其它），默认是以list的下标从0开始
		 * @param <String> args2.如何指定选中项的key值，可以是selectIndex（默认）、data(当前项的value值)
		 */
		selected: function(args1, args2){ fn.selected(args1, args2); },
		/**
		 * 绑定下拉的选项
		 * @param <Array> args1 下拉的选项集合
		 */
		bind: function(args){ fn.bindItems(args); },
		/**
		 * 获取当前选中项的下标
		 */
		selectedIndex: function(){ var index = element.jSelPanel.getAttribute("selectedIndex"); return parseInt(index ? index : 0); },
		/**
		 * 获取当前选中项的value
		 */
		value: function(){ return element.jSelPanel.getAttribute("data"); },
		/**
		 * 获取当前选中项的text
		 */
		text: function(){ return element.jSelText.innerHTML; },
		/**
		 * 当前下拉的元素
		 */
		panel: function(){ return element.jSelPanel; },
		/**
		 * 禁用下拉框
		 */
		disabled: function(){ fn.disabled(); },
		/**
		 * 取消下拉禁用
		 */
		undisabled: function(){ fn.undisabled(); }
	};
	SJ.extend(this, result);
};
SJ.Component["JSelects"] = function(args){
	var JSel, JSelBox, JSelBoxs, SelBox, option, options, lists, items, change, disabled, required;
	//获取页面下拉
	JSelBoxs = SJ("select[class*='SJ-JSelBox']");
	//JSelBoxs = SJ("select");
	for(k = 0, l=JSelBoxs.length; k<l; k++){
		JSelBox = JSelBoxs[k];
		if(JSelBox.getAttribute("conf") == "hide"){ continue; }
		options = SJ.children([JSelBox]);
		lists = [];
		for(g=0, h=options.length; g<h; g++){
			option = options[g];
			items = {};
			items.val = option.value;
			items.txt = option.innerHTML;
			if(option.getAttribute("selected")){items.selected = "true";}
			lists.push(items);
		}
		change = function(params){
			SelBox = SJ.nextPrev([params.elem], null, true)[0];
			SelBox.value = params.val;
			try{ SelBox.onchange(); }catch(ex){}
		};
		required = SJ.hasClass(JSelBox, 'required');
		required ? SJ.removeClass([JSelBox], 'required') : '';
		required = required ? 'required' : null;
		JSel = new SJ.Component["JSelect"]({
			clas: required,
			width:JSelBox.offsetWidth,
			disabled: JSelBox.getAttribute('disabled'),
			items:lists,
			change:change
			//style:{cssFloat:'left',styleFloat:'left'}
		});
		JSelBox.parentNode.appendChild(JSel.panel());
		JSelBox.setAttribute("conf", "hide");
		JSelBox.style.cssText = 'position:absolute;z-index:-1;';
	}
	SJ.extend(this, JSel);
};
