$(document).ready(function(){
	  var elem = {
			box: $('.box'),
			lMenu: $('#leftMenu'),
			ifraCont: $('#iframeContent'),
			hDiv: $('#hideDiv'),
			cDiv: $("#conDiv"),
			wHeight: $(window).height(),
			wWidth: $(window).width()
		};
	
		var resize = function(){
			elem.box.css({'height':(elem.wHeight)+'px','overflow':'hidden'});
			elem.lMenu.attr('height',(elem.wHeight-99)+'px');
			elem.ifraCont.css({'height':(elem.wHeight)+'px','width':(elem.wWidth - 203)+'px'});
			elem.hDiv.css('height',(elem.wHeight-99)+'px');
			elem.cDiv.css('width', (elem.wWidth - 221)+'px');
		};
		
		resize();
		
		$(window).resize(function(){
		  resize();
		});
		  
	  elem.hDiv.click(function(){
			if(elem.lMenu.css('display') == 'none'){
				elem.lMenu.show();
				elem.ifraCont.css('width',elem.wWidth - 203);
			}else{
				elem.lMenu.hide();
				elem.ifraCont.css('width',elem.wWidth - 5);
			}
		});
});
		
