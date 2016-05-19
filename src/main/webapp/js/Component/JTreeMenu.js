/**----------------------------------------------------------------
// Copyright (C) 2012 思建科技-潘毅
// E-mail：407666067@QQ.com
//
// 文件名： JPopUp.js
// 文件功能描述：JS弹出框
// 创建标识：2012年12月12日 14:38:57
//
// 修改标识：
// 修改描述：
//----------------------------------------------------------------*/

SJ.NameSpace.register("SJ.Component");
SJ.Component["JTreeMenu"] = function(args){
	//版本
	this.Version = '1.0.0';
	//组件创建日期
	this.CreateData = '2013年01月29日 13:07:51';
	//组件最后修改日期
	this.FinallyData = '2013年01月29日 13:08:00';
	//组件描述
	this.Remark = 'SJ Component 折叠菜单';
	//版权所有
	this.CopyRight = '思建科技';
	//遵循变量先定义，后使用原则，定义相关变量
	var private, public, lang, element, eventLibFn, fn, handle, result;
	//私有变量对象，存放非外部传入的变量
	private = {
		//弹出框对象的class
		mClass: "SJ-JTreeMenu-Main",
		//样式标签的ID
		StyleSheetIdentity: "SJ_JTreeMenu_StyleSheet",
		//线程
		thread: null,
		//菜单Click事件参数配置
		EventClick: [
			{nodeName:"span",className:"JTreeMenu-PNode",fn:function(event, element){ fn.PNodeClick(event, element); }},
			{nodeName:"samp",className:"JTreeMenu-CNode-Item",fn:function(event, element){ fn.CNodeClick(event, element); }}
		]
	};
	//公共变量对象，存放外部传入参数
	public = {
		//要添加到的父级元素
		pNode: null,
		//菜单项
		menu: [
			{
				node:'用户管理',
				ico: '',
				fn: null,
				childNode: [
					{node:'用户管理',fn:null},
					{node:'权限管理',fn:null}
				]
			}
		],
		//皮肤颜色
		color: null
	};
	//内部实现函数对象
	fn = {
		/**
		 * 菜单父级项Click事件
		 * @params event <Event> 事件对象
		 * @params node <Element> 当前事件触发元素
		 */
		PNodeClick: function(event, node){
			//找到该项的子集
			var cNode = SJ.nextPrev([node])[0];
			var cNodeItems = SJ.children([cNode]);
			var len = cNodeItems.length, h = 36, h1;
			var cNodeH = parseInt(cNode.style.height);
			var i = SJ.browser().firefox ? 4 : 8;
			if(private.thread){ private.thread.stop(); }
			if(isNaN(cNodeH) || cNodeH == 1){
				cNodeH = 1, h1 = len*h;
				private.thread = new SJ.Thread(function(){
					cNodeH += i;
					if(cNodeH >= h1){ private.thread.stop(); cNodeH = h1 == 0 ? 1 : h1; }
					cNode.style.height = (cNodeH)+'px';
				}, 1);
				private.thread.run();
			}
			else{
				h1 = 1;
				private.thread = new SJ.Thread(function(){
					cNodeH -= i;
					if(cNodeH <= h1){ private.thread.stop(); cNodeH = h1; }
					cNode.style.height = (cNodeH)+'px';
				}, 1);
				private.thread.run();
			}
			if(len == 0){
				node = $(node);
				var url = node.attr('url');
				var equalto = node.attr('equalto');
				if(url){ fn.jumpPage(url, equalto); }
			}
		},
		jumpPage: function(url, equalto){
			equalto = equalto ? equalto : '#iframeContent';
			$($(top.document).find(equalto)).attr('src',url);
		},
		/**
		 * 菜单子级项Click事件
		 * @params event <Event> 事件对象
		 * @params node <Element> 当前事件触发元素
		 */
		CNodeClick: function(event, node){
			var cNodes = SJ.findElement(SJ('.SJ-JTreeMenu-Main'), '.JTreeMenu-CNode-Item');
			var len = cNodes.length, i;
			for(i=0;i<len;i++){
				SJ.removeClass([cNodes[i].parentNode], 'menu_left_now');
			}
			SJ.addClass([node.parentNode], 'menu_left_now');
			node = $(node).parent();
			var url = node.attr('url');
			var equalto = node.attr('equalto');
			if(url){ fn.jumpPage(url, equalto); }
		}
	};
	//内部函数操作对象
	handle = {
		/**
		 * 初始化
		 */
		init: function(args){
			if(args){ SJ.extend(public, args, true); }
			//绑定事件
			handle.bindEvent();
		},
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			var TreeMenu = SJ('.SJ-JTreeMenu-Main')[0];
			//禁止内容选择
			SJ.disableSelection(TreeMenu);
			//禁止右键
			SJ.shieldRightMenu(TreeMenu);
			SJ.eventBubbling(private.EventClick, "click", TreeMenu);
		}
	};
	//初始化实例
	handle.init(args);
	//返回对象，提供外部调用的函数
	result = {
		
	};
	SJ.extend(this, result);
};