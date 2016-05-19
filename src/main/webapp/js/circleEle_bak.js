function MyCircle(data,count,isshow,name,scale,step,id,type,isclick,paper,r,left,top){
	  var circle = new Object; 
	  circle.data = data;
	  circle.count = count;
	  circle.isshow = isshow;
	  circle.name = name;
	  circle.scale = scale;
	  circle.step = step; 
	  circle.set = paper.set(); 
	  circle.id = id;
	  circle.type = type;
	  circle.left = left;
	  circle.top = top;
	  for(var i = 0;i<circle.data.length;i++){
		  var cir = paper.circle(circle.data[i].x * circle.scale+100+left, circle.data[i].y+100+top+(circle.count*10) * circle.scale, r).attr({fill:circle.data[i].color,title:name+':'+circle.data[i].va}).data("id", circle.data[i].id).data("type",circle.data[i].type).mousedown(
					function () {
						this.animate({r: r * 2}, 1); 
						if(isclick){
							 $.ajax({
				        		type:"post",
				        		url:contextPath+"/report/wcdmaOrGsm",
				        		data:({id :this.data("id"),type:this.data("type")}),
				        		success:function(data){
				        			$('#div_page').html(data.content);
				        			$('#div_page_max').html(data.contentMax);
				        		}
				        	});
						}
					}).mouseup(function(){
						this.animate({r: r}, 1000); 
					});
		  circle.set.push(cir);	
		  if(isclick){			  
			  if( circle.data[i].id == circle.id && circle.data[i].type == circle.type){
				  cir.attr("stroke", "#fe9a10");
			  }
		  }
		}
	  var onstart = function (event) {
		  circle.set.forEach(function(_me){
				_me.ox = _me.attr('cx');
				_me.oy = _me.attr('cy');
			});
		};
	  var onmove = function (dx, dy) {
		 circle.set.forEach(function(_me){
			 _me.attr({cx : parseInt(_me.ox + dx),cy : parseInt(_me.oy + dy)});
		});
	  };
	  var onend = function () {
			 circle.set.forEach(function(_me){
				_me.ox = _me.attr('cx');
				_me.oy = _me.attr('cy');
			 	_me.animate({r: r}, 1); 
			});
	  };
	  circle.set.drag(onmove,onstart,onend);
	  if(isshow){		  
		  circle.set.show();
	  }else{
		  circle.set.hide();
	  }
	 
	  circle.hidden = function(){
		  circle.set.hide();
	  };
	  circle.getName = function(){
		  return circle.name;
	  };
	  circle.show = function(){
		  circle.set.show();
	  };
	  circle.setIsShow = function(sh){
		  circle.isshow = sh;
	  };
	  circle.setOxy = function(){
		  circle.set.forEach(function(_me){
				_me.ox = _me.attr('cx');
				_me.oy = _me.attr('cy');
		  });
	  };
	  circle.setR = function(r){
		  circle.set.attr({'r':r});
	  };
	  circle.setCxy = function(){
		  circle.set.forEach(function(_me){
			  _me.ox = _me.attr('cx');
			  _me.oy = _me.attr('cy');
			  _me.attr({cx : parseInt(_me.ox),cy : parseInt(_me.oy)});
		  });
	  };
	  circle.setOCxy = function(){
		  circle.setCxy();
		  circle.setOxy();
	  };
	  circle.changeCxy = function(left,ow,nw,islarge){
		  circle.set.forEach(function(_me){
			  _me.ox = _me.attr('cx');
			  _me.oy = _me.attr('cy');
			  _me.attr({cx : parseInt((_me.ox-Math.abs(left))*nw/ow+Math.abs(left)),cy : parseInt(_me.oy)});
			  _me.ox = _me.attr('cx');
			  _me.oy = _me.attr('cy');
		  });
	  }
	  
	  circle.cxyExpand = function(scale,step){
		  circle.scale = scale;
		  circle.step = step;
		  circle.set.forEach(function(_me){
				if(_me.ox == undefined && _me.oy == undefined){
					_me.ox = _me.attr('cx');
					_me.oy = _me.attr('cy');
				}
				if(_me.ox == _me.attr('cx') && _me.oy == _me.attr('cy')){					
					_me.ox = _me.ox/(circle.scale - circle.step);
					_me.oy = _me.oy/(circle.scale - circle.step);
				}
				_me.attr({cx: parseInt(_me.ox * circle.scale),cy: parseInt(_me.oy * circle.scale)});
			});
	  };
	  circle.cxyNarrow = function(scale,step){
		  circle.scale = scale;
		  circle.step = step;
		  circle.set.forEach(function(_me){
				if(_me.ox == undefined && _me.oy == undefined){
					_me.ox = _me.attr('cx');
					_me.oy = _me.attr('cy');
				}
				if(_me.ox == _me.attr('cx') && _me.oy == _me.attr('cy')){				
					_me.ox = _me.ox/(circle.scale + circle.step);
					_me.oy = _me.oy/(circle.scale + circle.step);
				}
				_me.attr({cx: parseInt((_me.attr('cx') * circle.scale)/(circle.scale + circle.step)),cy: parseInt((_me.attr('cy') * circle.scale)/(circle.scale + circle.step))});
			});
	  };
	  return circle; 
} 
