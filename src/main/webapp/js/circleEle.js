/**
 * 圆
 * @param data
 * @param count
 * @param isshow 是否显示
 * @param name
 * @param scale 比例  1为100%
 * @param step 每次缩放比例
 * @param id
 * @param type 
 * @param isclick
 * @param paper
 * @param r
 * @returns {___circle0}
 */
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
	  for(var i = 0;i<circle.data.length;i++){
		  var x = circle.data[i].x * circle.scale+left+100;
		  var y = circle.data[i].y+(circle.count*10) * circle.scale+top+100;
		  var cir = paper.circle(circle.data[i].x * circle.scale+100+left, circle.data[i].y+100+top+(circle.count*10) * circle.scale, r).attr({fill:circle.data[i].color,title:name+':'+circle.data[i].va}).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0).data("id", circle.data[i].id).data("type",circle.data[i].type).data("index",i).mousedown(
					function () {
						var tran = "t"+this.data("mx")+","+this.data("my"); 
						this.transform(tran+"s1.5");
						this.data("s",1.5);
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
						var tran = "t"+this.data("mx")+","+this.data("my"); 
						this.transform(tran+"s1");
						this.data("s",1);
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
			  _me.data('cmx',_me.data("mx"));
			  _me.data('cmy',_me.data("my"));
			});
		};
	  var onmove = function (dx, dy) {
		 circle.set.forEach(function(_me){
			 var cmx = _me.data("mx") + dx;
			 var cmy = _me.data("my") + dy;
			  var tran = "t"+cmx+","+cmy+"s"+(_me.data("s"));
			  _me.data('cmx',cmx);
			  _me.data('cmy',cmy);
			  _me.transform(tran);
		});
	  };
	  var onend = function () {
			 circle.set.forEach(function(_me){
				 var tran = "t"+_me.data('cmx')+","+_me.data('cmy')+"s1";
				 _me.transform(tran);
				 _me.data("mx",_me.data('cmx'));
				 _me.data("my",_me.data('cmy'));
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
	  circle.changeCxy = function(left,ow,nw,islarge){
		  circle.set.forEach(function(_me){
			  if(islarge){
				  var tran = "t"+(_me.data("mx")+(nw/ow)*100)+","+(_me.data("my"))+"s"+(_me.data("s"));
				  _me.transform(tran);
				  _me.data("mx",_me.data("mx")+(nw/ow)*100);
			  }else{
				  var tran = "t"+(_me.data("mx")-((ow/nw)*100))+","+(_me.data("my"))+"s"+(_me.data("s"));
				  _me.transform(tran);
				  _me.data("mx",_me.data("mx")-((ow/nw)*100));
			  }
		  });
	  }
	  
	  circle.cxyExpand = function(scale,step){
		  circle.scale = scale;
		  circle.step = step;
		  circle.set.forEach(function(_me){
			  var mx = _me.data("mx");
			  var my = _me.data("my");
			  var index = _me.data("index");
			  var tran = "t"+(mx+ 100 + circle.step*index)+","+(my+ 100  + circle.step*index)+"s"+(_me.data("s"));
			  _me.transform(tran);
			  _me.data("mx",(mx+100 + circle.step*index));
			  _me.data("my",(my+100 + circle.step*index));
			});
	  };
	  circle.cxyNarrow = function(scale,step){
		  circle.scale = scale;
		  circle.step = step;
		  circle.set.forEach(function(_me){
			  var mx = _me.data("mx");
			  var my = _me.data("my");
			  var index = _me.data("index");
			  var tran = "t"+(mx-100-circle.step*index)+","+(my-100-circle.step*index)+"s"+(_me.data("s"));
			  _me.transform(tran);
			  _me.data("mx",(mx-100-circle.step*index));
			  _me.data("my",(my-100-circle.step*index));
			});
	  };
	  return circle; 
} 

