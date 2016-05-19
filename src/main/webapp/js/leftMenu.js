$(document).ready(function() {
	if($("#linkid").val() != null && $("#linkid").val() != ""){
		if($("#linkid").val() == "toInfo"){
			$(".JTreeMenu-PNode").each(function(){
				var _cur= $(this);
				if(_cur.html().indexOf("个人信息") != -1){
					_cur.click();
					location.href = _cur.attr("url");
					var ys = _cur.attr("ys");
					_cur.addClass("menu_bg");
					_cur.children().removeClass(ys).addClass(ys+"_w");
					var _next = _cur.next("ul");
					if(_next.css('display') == 'none'){			
						_next.show();
					}else{			
						_next.hide();
					}
				}
			});
		}
	}else{
		var _cur =  $(".JTreeMenu-PNode:first");
		location.href = _cur.attr("url");
		var ys = _cur.attr("ys");
		_cur.addClass("menu_bg");
		_cur.children().removeClass(ys).addClass(ys+"_w");
		var _next = _cur.next("ul");
		if(_next.css('display') == 'none'){			
			_next.show();
		}else{			
			_next.hide();
		}
	}
	$('.JTreeMenu-PNode').click(function(){
		var _this = $(this);
		var _target = _this.next('ul');
		//移除同级的二级背景样式
		_this.siblings().children(".jtm-node").removeClass("menu_bg");
		var _parent = _this.parent();
		_parent.siblings().children(".JTreeMenu-PNode").removeClass("menu_bg");
		_parent.siblings().children(".JTreeMenu-CNode").css("display","none");
		//还原所有一级的图片样式
		$(".JTreeMenu-PNode samp").each(function(){
			var otherys = $(this).parent().attr("ys");
			$(this).removeClass().addClass(otherys);
		});
		//在当前点击的元素上添加图片白色样式
		var ys = _this.attr("ys");
		_this.addClass("menu_bg");
		_this.children().removeClass(ys).addClass(ys+"_w");
		if(_target.css('display') == 'none'){			
			_target.show();
		}else{			
			_target.hide();
		}
		if(_target.children('li').length == 0){
			if($.browser.msie) {
				parent.frames['iframeContent'].location.href = _this.attr('url');
			}else{
				parent.frames['iframeContent'].src =  _this.attr('url');
			}
		}
	});
	
	$('.jtm-node').click(function(){
		var _this = $(this);
		var _parent = _this.parent();
		_parent.siblings().removeClass("menu_bg");
		_this.siblings().removeClass("menu_bg");
		//还原所有一级的图片样式
		$(".JTreeMenu-PNode samp").each(function(){
			var otherys = $(this).parent().attr("ys");
			$(this).removeClass().addClass(otherys);
		});
		_parent.parent().siblings().children(".JTreeMenu-PNode").removeClass("menu_bg");
		_parent.parent().siblings().children(".JTreeMenu-CNode").children(".jtm-node").removeClass("menu_bg");
		_this.addClass("menu_bg");
		if($.browser.msie) {
			parent.frames['iframeContent'].location.href = _this.attr('url');
		}else{
			parent.frames['iframeContent'].src =  _this.attr('url');
		}
	});
});