/**----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// E-mail：407666067@QQ.com
//
// 文件名： JCheckBoxs.js
// 文件功能描述：多选按钮
// 创建标识：2012年12月11日 17:01:42
//
// 修改标识：
// 修改描述：
//----------------------------------------------------------------*/

SJ.NameSpace.register("SJ.Component");
SJ.Component["JCheckBox"] = function(args){
	//版本
	this.Version = '1.0.2';
	//组件创建日期
	this.CreateData = '2012年12月11日 17:01:42';
	//组件最后修改日期
	this.FinallyData = '2013年02月05日 13:06:20';
	//组件描述
	this.Remark = 'SJ Component 复选框';
	//版权所有
	this.CopyRight = '思建科技';
	//遵循变量先定义，后使用原则，定义相关变量
	var private, public, fn, handle, result;
	//私有变量对象，存放非外部传入的变量
	private = {
		//样式表ID
		StyleSheetIdentity: "SJ_JCheckBox_StyleSheet",
		//复选框ClassName
		mainClass: "JCheckBox-Main",
		//禁用的字体颜色
		disColor: '#A1A1A1',
		disLightColor: '#DDDDDD',
		//选中
		checked: '<font class="label">&radic;</font>',
		//半选中
		indeterminate: '<div class="JCheckBox-Half"></div>',
		//皮肤颜色值
		skin: {
			mainColor: SJ.mainColor,
			lightColor1: SJ.lightColor1,
			lightColor2: SJ.lightColor2,
			lightColor5: SJ.lightColor5,
			lightColor8: SJ.lightColor8,
			lightColor12: SJ.lightColor12,
			lightColor14: SJ.lightColor14,
			lightColor16: SJ.lightColor16,
			lightColor20: SJ.lightColor20,
			darkColor2: SJ.darkColor2,
			darkColor5: SJ.darkColor5,
			darkColor10: SJ.darkColor10
		}
	};
	//公共变量对象，存放外部传入参数
	public = {
		//是否需要背景
		isBg:false,
		//自定义类名
		clas: null,
		//宽
		width: 26,
		//高
		height: 26,
		//复选框数据[{显示文字，选项Value，当前项的key(取值时可通过该key来取),是否被选中，是否被禁用，需要添加到的父级，它对应的系统的复选框，点击事件函数}]
		checkbox: [{text:'CheckBox',value:0,key:'chkbox',checked:false,disabled:null,parent:null,node:null,fn:null}],
		//皮肤颜色
		color: '#1E1E1E'
	};
	//内部实现函数对象
	fn = {
		/**
		 * 生成皮肤
		 */
		skins: function(){
			if(public.color){
				var skin = private.skin, color = public.color;
				skin.mainColor = color;
				skin.lightColor1 = SJ.getColor('light', color, 10);
				skin.lightColor2 = SJ.getColor('light', color, 20);
				skin.lightColor5 = SJ.getColor('light', color, 50);
				skin.lightColor8 = SJ.getColor('light', color, 80);
				skin.lightColor12 = SJ.getColor('light', color, 120);
				skin.lightColor14 = SJ.getColor('light', color, 140);
				skin.lightColor16 = SJ.getColor('light', color, 160);
				skin.lightColor20 = SJ.getColor('light', color, 200);
				skin.darkColor2 = SJ.getColor('dark', color, 20);
				skin.darkColor5 = SJ.getColor('dark', color, 50);
				skin.darkColor10 = SJ.getColor('dark', color, 100);
			}
		},
		/**
		 * 创建样式表
		 */
		createStyleSheet: function(){
			var style, mClass, skin, fs1, fs2, fs3, fs4, fs5, fs_over, fs_down;
			style = [], mClass=private.mainClass, skin = private.skin;
			fs1 = SJ.colStyleCss(skin.lightColor8, skin.lightColor5);
			fs2 = SJ.colStyleCss(skin.lightColor12, skin.lightColor5);
			fs3 = SJ.colStyleCss(private.disLightColor, private.disColor);
			fs4 = SJ.colStyleCss(skin.lightColor5, skin.mainColor);
			fs5 = SJ.colStyleCss(private.disColor, private.disLightColor);
			fs_over = SJ.colStyleCss(skin.lightColor5, skin.lightColor12);
			fs_down = SJ.colStyleCss(skin.lightColor1, skin.lightColor5);
			if(public.isBg){
				style.push('.'+mClass+'{'+
					'width:auto;height:25px;line-height:25px;float:left;padding-right:4px;'+
					'text-decoration:none;margin-right:1px;border:1px solid '+skin.mainColor+
					';-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px;'+fs1+
				'}');
			}
			else{
				style.push('.'+mClass+'{'+
					'width:auto;height:25px;line-height:25px;padding-right:4px;text-decoration:none;'+
					'position:relative;float:left;'+
				'}');
			}
			style.push('.'+mClass+' .JCheckBox-Box{'+
				'width:15px;height:15px;line-height:16px;font-size:15px;border:1px solid '+skin.lightColor2+';margin:4px;'+
				'float:left;text-align:center;font-weight:bolder;color:'+skin.lightColor20+';'+fs2+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box .label{'+
				'float:left;margin-left:-2px;font-weight:bolder;font-family:"楷体";'+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box .JCheckBox-Half{'+
				'float:left;margin:2px;width:6px;height:6px;border:1px solid '+skin.darkColor2+';'+fs4+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box-Dis .JCheckBox-Half{'+
				'border:1px solid '+private.disColor+';'+fs5+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box-Dis{'+
				'border:1px solid '+private.disColor+';color:'+private.disColor+';'+fs3+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box-Over{'+
				'border:1px solid '+skin.mainColor+';'+fs_over+
			'}');
			style.push('.'+mClass+' .JCheckBox-Box-Down{'+
				'border:1px solid '+skin.darkColor5+';'+fs_down+
			'}');
			style.push('.'+mClass+' .JCheckBox-Text{'+
				'float:left;width:auto;line-height:26px;font-weight:bold;font-size:13px;color:'+skin.darkColor5+';'+
				'color:'+skin.lightColor20+';'+
			'}');
			SJ.createStyleSheet(style.join('\n'), private.StyleSheetIdentity);
		},
		/**
		 * 创建复选框
		 */
		createCheckBox: function(){
			var html, checkboxs, checkbox, tempDiv, check, len, i, clas, ced, dab, ind;
			html = [];
			html.push('<span class="#MAINCLASS#" data="#DATA#" key="#KEY#" title="#TEXT#" disabl="#DISABLED#">');
			html.push('	<div class="#CHECKBOX#"></div>');
			html.push('	<label class="#CHECKTEXT#">#TEXT#</label>');
			html.push('</span>');
			html = html.join('\r\n').replace('#CHECKBOX#', 'JCheckBox-Box')
									.replace('#CHECKTEXT#', 'JCheckBox-Text');
			checkboxs = public.checkbox;
			tempDiv = document.createElement("div");
			len = checkboxs.length;
			clas = private.mainClass+(public.clas?' '+public.clas:'');
			for(i=0;i<len;i++){
				checkbox = checkboxs[i];
				clas = checkbox.clas ? clas+' '+checkbox.clas : clas;
				tempDiv.innerHTML = html.replace('#MAINCLASS#', clas)
										.replace('#DATA#', checkbox.value)
										.replace('#KEY#', checkbox.key?checkbox.key:'chkbox'+i)
										.replaceAll('#TEXT#', checkbox.text);
				check = SJ.children([tempDiv])[0];
				SJ.disableSelection(check, 'pointer');
				check.onmouseover = function(event){
					if(this.getAttribute('disabl') != 'disabled'){
						var chkbox = SJ.findElement([this], '>div')[0];
						chkbox.className += ' JCheckBox-Box-Over';
						this.onmouseout = function(){
							SJ.removeClass([chkbox], 'JCheckBox-Box-Over');
						};
						this.onmousedown = function(){
							SJ.addClass([chkbox], 'JCheckBox-Box-Down');
							this.onmouseup = function(){
								SJ.removeClass([chkbox], 'JCheckBox-Box-Down');
							};
							this.onmouseout = function(){
								SJ.removeClass([chkbox], 'JCheckBox-Box-Down');
								SJ.removeClass([chkbox], 'JCheckBox-Box-Over');
							};
						};
					}
					else{
						this.onmouseout = null; this.onmousedown = null; this.onmouseup = null;
					}
				};
				check.onclick = function(event){
					if(this.getAttribute('disabl') != 'disabled'){
						var statu = this.getAttribute('statu');
						var chkbox = SJ.findElement([this], '>div')[0];
						var sys_chkbox = SJ.nextPrev([this], null, true)[0];
						var sys_chkbox_type = sys_chkbox ? sys_chkbox.getAttribute('type'):"";
						if(statu == 'checked'){
							chkbox.innerHTML = '';
							this.setAttribute('statu', '');
						}
						else{
							chkbox.innerHTML = private.checked;
							this.setAttribute('statu', 'checked');
						}
						if(sys_chkbox&&sys_chkbox_type=='checkbox'){
							sys_chkbox.click(); sys_chkbox.checked = this.getAttribute('statu');
						}					
					}
				};
				if(checkbox.node && checkbox.node.length>0){ SJ.after(checkbox.node, [check]); }
				else{ SJ(checkbox.parent)[0].appendChild(check); }
				ced = checkbox.checked, dab = checkbox.disabled, ind = checkbox.indeterminate;
				if(ced == true || ced == 'true' || ced == 'checked'){ check.click(); }
				if(dab == true || dab == 'true' || dab == 'disabled'){ fn.disabled(check); }
				if(ind == true || ind == 'true' || ind == 'indeterminate'){ fn.indeterminate(check); }
			}
		},
		/**
		 * 获取选中项的Value
		 * @params args <Object/String> 元素对象，可根据该对象获取其下面选中项的value值集合
		 * @return <JSON> 以每项中的key属性作为键以data作为值的键值对对象
		 */
		value: function(args){
			var checkboxs = args ? SJ.findElement(SJ(args), '.JCheckBox-Main') : SJ('.JCheckBox-Main');
			var values = new Object(), len = checkboxs.length, checkbox, status;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				status = checkbox.getAttribute('statu');
				if(status == 'checked' || status == 'indeterminate'){
					values[checkbox.getAttribute('key')] = checkbox.getAttribute('data');
				}
			}
			return values;
		},
		/**
		 * 获取选中项的Text
		 * @params args <Object/String> 元素对象，可根据该对象获取其下面选中项的Text集合
		 * @return <JSON> 以每项中的key属性作为键以text作为值的键值对对象
		 */
		text: function(args){
			var checkboxs = args ? SJ.findElement(SJ(args), '.JCheckBox-Main') : SJ('.JCheckBox-Main');
			var texts = new Object(), len = checkboxs.length, checkbox, status;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				status = checkbox.getAttribute('statu');
				if(status == 'checked' || status == 'indeterminate'){
					texts[checkbox.getAttribute('key')] = checkbox.getAttribute('title');
				}
			}
			return texts;
		},
		/**
		 * 半选中
		 * @params args0 <String> 需要选中项的值
		 * @params args1 <String> 需要选中项的键(如：value、text，默认为key)
		 */
		indeterminate: function(args0, args1){			
			args1 = args1 ? args1 : 'key';
			args1 = args1 == 'value' ? 'data' : args1;
			args1 = args1 == 'text' ? 'title' : args1;
			checkboxs = typeof(args0) == 'object' ? SJ(args0) : SJ('.JCheckBox-Main['+args1+'='+args0+']');
			var len = checkboxs.length, checkbox, chkChild, sysChkbox;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				checkbox.setAttribute('statu', 'indeterminate');
				chkChild = SJ.children([checkbox])[0];
				chkChild.innerHTML = private.indeterminate;
				//查找其对应系统复选框
				sysChkbox = SJ.nextPrev([checkbox], 'input[value='+checkbox.getAttribute('data')+']', true)[0];
				if(sysChkbox){ sysChkbox.indeterminate = true; }
			}
		},
		/**
		 * 选中某项
		 * @params args0 <String> 需要选中项的值
		 * @params args1 <String> 需要选中项的键(如：value、text，默认为key)
		 */
		checked: function(args0, args1){
			args1 = args1 ? args1 : 'key';
			args1 = args1 == 'value' ? 'data' : args1;
			args1 = args1 == 'text' ? 'title' : args1;
			checkboxs = typeof(args0) == 'object' ? SJ(args0) : SJ('.JCheckBox-Main['+args1+'='+args0+']');
			var len = checkboxs.length, checkbox;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				if(checkbox.getAttribute('statu') != 'checked'){ checkbox.click(); }
			}
		},
		/**
		 * 取消选中(包括全选和半选)
		 * @params args0 <String> 需要取消选中项的值
		 * @params args1 <String> 需要取消选中项的键(如：value、text，默认为key)
		 */
		unchecked: function(args0, args1){
			args1 = args1 ? args1 : 'key';
			args1 = args1 == 'value' ? 'data' : args1;
			args1 = args1 == 'text' ? 'title' : args1;
			checkboxs = typeof(args0) == 'object' ? SJ(args0) : SJ('.JCheckBox-Main['+args1+'='+args0+']');
			var len = checkboxs.length, checkbox, chkChild, sysChkbox;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				checkbox.setAttribute('statu', '');
				chkChild = SJ.children([checkbox])[0];
				chkChild.innerHTML = '';
				//查找其对应系统复选框
				sysChkbox = SJ.nextPrev([checkbox], 'input[value='+checkbox.getAttribute('data')+']', true)[0];
				if(sysChkbox){ sysChkbox.checked = false; sysChkbox.indeterminate = false; }
			}
		},
		/**
		 * 禁用复选按钮
		 * @params args0 <String> 需要禁用项的值
		 * @params args1 <String> 需要禁用项的键(如：value、text，默认为key)
		 */
		disabled: function(args0, args1){
			args1 = args1 ? args1 : 'key';
			args1 = args1 == 'value' ? 'data' : args1;
			args1 = args1 == 'text' ? 'title' : args1;
			checkboxs = typeof(args0) == 'object' ? SJ(args0) : SJ('.JCheckBox-Main['+args1+'='+args0+']');
			var len = checkboxs.length, checkbox, chkChild, sysChkbox;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				if(checkbox.getAttribute('disabl') != 'disabled'){
					checkbox.setAttribute('disabl', 'disabled');
					chkChild = SJ.children([checkbox]);
					chkChild[0].className += ' JCheckBox-Box-Dis';
					chkChild[1].style.color = private.disColor;
					//查找其对应系统复选框
					sysChkbox = SJ.nextPrev([checkbox], 'input[value='+checkbox.getAttribute('data')+']', true)[0];
					if(sysChkbox){ sysChkbox.disabled = true; }
				}
			}
		},
		/**
		 * 取消对复选按钮的禁用
		 * @params args0 <String> 需要取消禁用项的值
		 * @params args1 <String> 需要取消禁用项的键(如：value、text，默认为key)
		 */
		undisabled: function(args0, args1){
			args1 = args1 ? args1 : 'key';
			args1 = args1 == 'value' ? 'data' : args1;
			args1 = args1 == 'text' ? 'title' : args1;
			checkboxs = typeof(args0) == 'object' ? SJ(args0) : SJ('.JCheckBox-Main['+args1+'='+args0+']');
			var len = checkboxs.length, checkbox, chkChild;
			for(var i=0;i<len;i++){
				checkbox = checkboxs[i];
				checkbox.setAttribute('disabl', '');
				chkChild = SJ.children([checkbox]);
				chkChild[0].className = chkChild[0].className.replaceAll(' JCheckBox-Box-Dis', '');
				chkChild[1].style.color = private.skin.darkColor5;
				//查找其对应系统复选框
				sysChkbox = SJ.nextPrev([checkbox], 'input[value='+checkbox.getAttribute('data')+']', true)[0];
				if(sysChkbox){ sysChkbox.disabled = false; }
			}
		}
	};
	//内部函数操作对象
	handle = {		
		/**
		 * 初始化参数
		 * @param args <JSON Object> 配置参数
		 */
		initArgs: function(args){
			if(args){ SJ.extend(public, args, true); }
			fn.skins();
			fn.createStyleSheet();
			fn.createCheckBox();
		}
	};
	//初始化
	handle.initArgs(args);
	//返回对象，提供外部调用的函数
	result = {		
		/**
		 * 获取选中项的Value
		 * @params args <Object/String> 元素对象，可根据该对象获取其下面选中项的value值集合
		 * @return <JSON> 以每项中的key属性作为键以data作为值的键值对对象
		 */
		value: function(args){ return fn.value(args); },
		/**
		 * 获取选中项的Text
		 * @params args <Object/String> 元素对象，可根据该对象获取其下面选中项的Text集合
		 * @return <JSON> 以每项中的key属性作为键以text作为值的键值对对象
		 */
		text: function(args){ return fn.text(args); },
		/**
		 * 禁用复选按钮
		 * @params args0 <String> 需要禁用项的值
		 * @params args1 <String> 需要禁用项的键(如：value、text，默认为key)
		 */
		disabled: function(args0, args1){ fn.disabled(args0, args1); },
		/**
		 * 取消对复选按钮的禁用
		 * @params args0 <String> 需要取消禁用项的值
		 * @params args1 <String> 需要取消禁用项的键(如：value、text，默认为key)
		 */
		undisabled: function(args0, args1){ fn.undisabled(args0, args1); },
		/**
		 * 半选中
		 * @params args0 <String> 需要选中项的值
		 * @params args1 <String> 需要选中项的键(如：value、text，默认为key)
		 */
		indeterminate: function(args0, args1){ fn.indeterminate(args0, args1); },
		/**
		 * 选中某项
		 * @params args0 <String> 需要选中项的值
		 * @params args1 <String> 需要选中项的键(如：value、text，默认为key)
		 */
		checked: function(args0, args1){ fn.checked(args0, args1); },
		/**
		 * 取消选中(包括全选和半选)
		 * @params args0 <String> 需要取消选中项的值
		 * @params args1 <String> 需要取消选中项的键(如：value、text，默认为key)
		 */
		unchecked: function(args0, args1){ fn.unchecked(args0, args1) }
	};
	SJ.extend(this, result);
};
SJ.Component['JCheckBoxs'] = function(){
	var checkboxs, checkbox, len, i, items, val, txt, key, chk, dis, half;
	//获取所有复选框
	checkboxs = SJ('input[type=checkbox]');
	len = checkboxs.length;
	items = [];
	for(i=0;i<len;i++){
		checkbox = checkboxs[i];
		if(checkbox.getAttribute('flag') != 'false' && SJ.hasClass(checkbox, 'SJ-JCheckBox')){
			val = checkbox.value, txt = checkbox.getAttribute('text');
			txt = txt ? txt : val;
			key = checkbox.getAttribute('id');
			key = key ? key : checkbox.getAttribute('name');
			key = key ? key : 'chkbox'+i;
			chk = checkbox.getAttribute('checked');
			dis = checkbox.getAttribute('disabled');
			half = checkbox.getAttribute('indeterminate');
			items.push({value:val,text:txt,key:key,checked:chk,disabled:dis,indeterminate:half,node:[checkbox]});
			checkbox.setAttribute("flag", false);
			checkbox.style.cssText = "position:absolute;z-index:-1;display:none;";
		}
	}
	return new SJ.Component['JCheckBox']({checkbox:items});
};