

SJ.NameSpace.register("SJ.Component");
SJ.Component["JPopUps"] = function(args){
	var private, public, element, fn, handle;
	private = {
		EventClick: [
			{nodeName:"a",className:"sure",fn:function(event, elem){ fn.EventClick(event, elem, 'sure'); }},
			{nodeName:"a",className:"cancel",fn:function(event, elem){ fn.EventClick(event, elem, 'cancel'); }},
			{nodeName:"img",className:"close",fn:function(event, elem){ fn.EventClick(event, elem, 'close'); }}
		],		
		EventDown: [
			{nodeName:"div",className:"pop_up_top",fn:function(event, elem){ SJ.Drag(event, elem, element.POPUP[0]); return false; }},
			{nodeName:"span",className:"pop_up_title",fn:function(event, elem){ SJ.Drag(event, elem, element.POPUP[0]); return false; }}
		]
	};
	public = {
		title: 'PopUps',
		width: 400,
		height: 395,
		html: '',
		button: {
			sure: { text: '确定', isClose: true, fn: null }
		}
	};
	element = {};
	fn = {
		createHtml: function(){
			if(element.POPUP){ element.POPUP.remove(); element.POPUPBG.remove(); }
			var html = [], buttons, key, w, h;
			html.push('<div class="pop_up" style="position:absolute;top:-9999px;left:-9999px;z-index:1000;">');
			html.push('<div class="pop_up_top"><span class="pop_up_title">#TITLE#</span>');
			html.push('<samp><img class="close" src="' + contextPath + '/images/close.png" /></samp></div>');
			html.push('<div class="pop_up_center">');
			html.push('<div class="pop_up_content"><img src="' + contextPath + '/images/032.gif" id="loading" style="float:left;"></div>');
			html.push('<div class="clear"></div>');
			html.push('<div class="bottom_btn">');
			buttons = public.button;
			for(key in buttons){
				html.push('<div class="btn" style="margin:auto 5px;"><a class="'+key+'">'+buttons[key].text+'</a></div>');
			}
			html.push('</div>');
			html.push('</div>');
	//		html.push('<div class="all_list_bottom"><span></span><samp></samp></div>');
			html.push('</div>');
			html = $(html.join('\r\n').replace('#TITLE#', public.title));
			w = public.width, h = public.height;
			html.css('width', w+'px').css('height', h+'px');
			html.find('.pop_up_center').css('width', w+'px').css('height', (h-40-30)+'px');
			html.find('.pop_up_content').css('width', '100%').css('height', (h-40-42-30)+'px');
			element.POPUP = html;
			element.POPUPBG = $('<div class="pop_up_bg"></div>')
					.attr('style', 'position:absolute;top:-9999px;left:-9999px;z-index:-1;width:100%;background:#000000;'+
						'filter:alpha(opacity=10);opacity:0.1;_moz_opacity:0.1;'
					);
			$(document.body).append(html);
			$(document.body).append(element.POPUPBG);
			var content = html.find('.pop_up_content');
			var h = content.height(), w = content.width();
			var img = content.find('img#loading');
			img.css('margin-top', ((h/2)-(img.height()/2))+'px').css('margin-left', ((w/2)-(img.width()/2)-30)+'px');
			if(public.html){ content.html(public.html); }
		},
		EventClick: function(event, elem, clas){
			if(clas == 'close'){ fn.hidden(); }
			else{
				var key = public.button[clas];
				if(key.fn){ key.fn(); }
				if(key.isClose){ fn.hidden(); }
			}
		},
		show: function(args){
			var top, left;
			top = ((SJ.clientWH().height/2)-(public.height/2)+(SJ.getScroll().top));
			left = ((SJ.scrollWH().width/2)-(public.width/2));
			top = top < 0 ? 0 : top;
			element.POPUP.css('top', (top)+'px').css('left', (left)+'px');
			var background = element.POPUPBG;
			background.css('height', (SJ.scrollWH().height)+'px').css('top', '0px').css('left', '0px').css('z-index', '999');			
			var filter = 10;
			var times = SJ.browser().firefox ? 20 : 5;
			background = background[0].style;
			var thread = new SJ.Thread(function(){
				background['filter'] = 'alpha(opacity='+filter+')';
				background['opacity'] = filter/100;
				background['_moz_opacity'] = filter/100;
				filter += 4;
				if(filter >= 50){ thread.stop(); }
			}, times);
			thread.run();
		},
		hidden: function(){
			element.POPUP.css('top', '-9999px').css('left', '-9999px');
			var background = element.POPUPBG;
			background.css('top', '-9999px').css('left', '-9999px');			
			var filter = 70;
			var times = SJ.browser().firefox ? 20 : 5;
			background = background[0].style;
			var thread = new SJ.Thread(function(){
				background['filter'] = 'alpha(opacity='+filter+')';
				background['opacity'] = filter/100;
				background['_moz_opacity'] = filter/100;
				filter -= 4;
				if(filter <= 10){ thread.stop(); }
			}, times);
			thread.run();
		}
	};
	handle = {
		initArgs: function(args){
			if(args){ $.extend(public, args); }
			fn.createHtml();
			handle.bindEvent();
		},
		bindEvent: function(){
			var popups = element.POPUP[0];
			SJ.eventBubbling(private.EventClick, "click", popups);
			SJ.eventBubbling(private.EventDown, "mousedown", popups);
			var top, t;
			$(window).scroll(function(){
				$(".pop_up").each(function(index, elem){
					elem = $(elem);
					top = parseInt(elem.css('top'));
					if(top != -9999 && !isNaN(top)){
						t = (SJ.clientWH().height/2)-(elem.height()/2)+(SJ.getScroll().top);
						elem.css('top', (t<0?0:t)+"px")
					}
				});
			});
		}
	};
	handle.initArgs(args);
	this.panel = function(){ return element.POPUP; };
	this.open = function(args){ if(args){ handle.initArgs(args); } fn.show(args); };
	this.load = function(url, data, callBack){
		fn.show();
		var timestamp = '&';
		if(url.indexOf('?') == -1){
			timestamp = '?';
		}
		timestamp = timestamp + 'timestamp=' + new Date().getTime();
		element.POPUP.find('.pop_up_content').load(url+timestamp, data, callBack);
	},
	this.close = function(){ fn.hidden(); };
};