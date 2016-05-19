/**
 * 图形
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
 * @returns {___graphic0}
 */
function MyGraphic(data,count,isshow,name,scale,step,id,type,id_last,type_last,isclick,paper,r,left,top,compare){
	  var graphic = new Object; 
	  graphic.data = data;
	  graphic.count = count;
	  graphic.isshow = isshow;
	  graphic.name = name;
	  graphic.scale = scale;
	  graphic.step = step; 
	  graphic.set = paper.set(); 
	  graphic.id = id;
	  graphic.type = type;
	  graphic.id_last = id_last;
	  graphic.type_last = type_last;
	  //graphic.offset =70
	  var startIndex=0,endIndex=0;
	  var startX=0,startY=0,startIntevl=0;
	  var endX=0,endY=0,endIntevl=0;
	  for(var i = 0;i<graphic.data.length;i++){
		  var x = graphic.data[i].x * graphic.scale+left+(left/40)*(count-1);
		  var y = graphic.data[i].y* graphic.scale +top;
		  var gra = null;
		  if(graphic.data[i].reltype == -1){
			  //画菱形
			  var rhopath = [["M", x, y-r], ["L", x+r, y], ["L", x, y+r],["L",x-r,y], ["Z"]];
			  gra = paper.path(rhopath);
			  gra.attr({fill:'black'});
			  gra.attr({stroke:'black'});
		  }else{
			  if(graphic.data[i].type == 1){
				  //画三角形
				  var tripath = [["M", x-r, y-Math.sqrt(3)/2*r],["L", x+r, y-Math.sqrt(3)/2*r] ,["L", x, y+Math.sqrt(3)/2*r] , ["Z"]];
				  gra = paper.path(tripath);
			  }else{
				  //画圆
				  gra = paper.circle(x, y, r);
			  }
			  gra.attr({fill:graphic.data[i].color});
			  gra.attr({stroke:graphic.data[i].color});
		  }
		  var intevl=":"+graphic.data[i].va;
		  if(data[i].reltype==-1){
			  intevl="";
		  }
		  if(isclick){
			  if( graphic.data[i].id == graphic.id && graphic.data[i].type == graphic.type){
				  //给起点加上外圈
				  gra.attr("stroke", "red");
				  //获取起点下标
				  startIndex=i;
				  //获取起点的x和y坐标
				  startX =x;
				  startY =y;
				  //获取起点的提示值
				  startIntevl =intevl;
			  }
			  if( graphic.data[i].id == graphic.id_last && graphic.data[i].type == graphic.type_last){
				  //给终点加上外圈
				  gra.attr("stroke", "red");
				  //获取终点的下标
				  endIndex=i;
				  //获取终点的想x和y坐标
				  endX =x;
				  endY =y;
				  //获取终点的提示值
				  endIntevl =intevl;
			  }
		  }
		  if(i == graphic.data.length-1 && compare!=1){
			  //画起点箭头
			  var rhopath =  [["M", startX-r, startY], 
			                  ["L", startX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), startY-5],
			                  ["L", startX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), startY-1],
			                  ["L", startX - (2+r) * Math.sin(2 * Math.PI / 360 * 60)-25,startY-1],
			                  ["L", startX - (2+r) * Math.sin(2 * Math.PI / 360 * 60)-25,startY+1],
			                  ["L", startX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), startY+1],
			                  ["L", startX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), startY+5],
			                  ["Z"]
			  				];
			  var g1= paper.path(rhopath).attr({fill: "red", stroke: "none"});
			  //帮点start的拖拽事件
			  g1.attr({title:name+startIntevl}).data("reltype",data[i].reltype).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0)
			  .data("id", graphic.data[i].id).data("type",graphic.data[i].type)
			  //放大缩小点之间距离设定
			  .data("index",graphic.data[startIndex].x+":"+graphic.data[startIndex].y).mouseup(function(){
					var tran = "t"+this.data("mx")+","+this.data("my"); 
					this.transform(tran+"s1");
					this.data("s",1);
				});
			  //将生成信息放入点集合中
			  graphic.set.push(g1);
			  
			  //标出起点提示语
			  var g2= paper.text(startX-45,startY+2,"Start");
			  g2.attr({stroke:"red"});
			  //帮点箭头的拖拽事件
			  g2.attr({title:name+startIntevl}).data("reltype",data[i].reltype).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0)
			  .data("id", graphic.data[i].id).data("type",graphic.data[i].type)
			  //放大缩小点之间距离设定
			  .data("index",graphic.data[startIndex].x+":"+graphic.data[startIndex].y).mouseup(function(){
					var tran = "t"+this.data("mx")+","+this.data("my"); 
					this.transform(tran+"s1");
					this.data("s",1);
				});
			  //将生成图形放入点集合中
			  graphic.set.push(g2);
		  }
		  if(i == graphic.data.length-1 && compare!=1){
			  //画终点箭头
			  var rhopath =  [["M", endX-r, endY], 
			                  ["L", endX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), endY-5],
			                  ["L", endX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), endY-1],
			                  ["L", endX - (2+r) * Math.sin(2 * Math.PI / 360 * 60)-25,endY-1],
			                  ["L", endX - (2+r) * Math.sin(2 * Math.PI / 360 * 60)-25,endY+1],
			                  ["L", endX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), endY+1],
			                  ["L", endX - (10+r) * Math.sin(2 * Math.PI / 360 * 60), endY+5],
			                  ["Z"]
			  				];
			  var g1= paper.path(rhopath).attr({fill: "red", stroke: "none"});
			  //绑定终点箭头拖拽事件
			  g1.attr({title:name+endIntevl}).data("reltype",data[i].reltype).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0)
			  .data("id", graphic.data[i].id).data("type",graphic.data[i].type)
			  //放大缩小点位置坐标绑定
			  .data("index",graphic.data[endIndex].x+":"+graphic.data[endIndex].y).mouseup(function(){
					var tran = "t"+this.data("mx")+","+this.data("my"); 
					this.transform(tran+"s1");
					this.data("s",1);
				});
			  //箭头图像放入点集合
			  graphic.set.push(g1);
			  
			  //终点提示语句
			  var g2=paper.text(endX-45, endY+2, "End");
			  g2.attr({stroke: "red"});
			  //帮点start的拖拽事件
			  g2.attr({title:name+endIntevl}).data("reltype",data[i].reltype).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0)
			  .data("id", graphic.data[i].id).data("type",graphic.data[i].type)
			  //放大缩小点位置坐标绑定
			  .data("index",graphic.data[endIndex].x+":"+graphic.data[endIndex].y).mouseup(function(){
					var tran = "t"+this.data("mx")+","+this.data("my"); 
					this.transform(tran+"s1");
					this.data("s",1);
				});
			  //终点提示语放入点集合
			  graphic.set.push(g2);
		  }
		  gra.attr({title:name+intevl}).data("reltype",data[i].reltype).data("odx",0).data("ody",0).data("mx",0).data("my",0).data("cmx",0).data("cmy",0).data("s",1).data("c",0).data("id", graphic.data[i].id).data("type",graphic.data[i].type).data("index",graphic.data[i].x+":"+graphic.data[i].y).mousedown(
					function () {
						var tran = "t"+this.data("mx")+","+this.data("my"); 
						this.transform(tran+"s1.5");
						this.data("s",1.5);
						if(isclick){
							if(this.data("reltype") == -1){
								$('#div_page').html("<div class=\"noservice\">No Service<\/div>");
							}else{
								 $.ajax({
					        		type:"post",
					        		url:contextPath+"/reportIndependent/wcdmaOrGsm",
					        		data:({id :this.data("id"),type:this.data("type")}),
					        		success:function(data){
					        			$('#div_page').html(data.content);
					        			$('#div_page_max').html(data.contentMax);
					        		}
					        	});
							}
							
						}
					}).mouseup(function(){
						var tran = "t"+this.data("mx")+","+this.data("my"); 
						this.transform(tran+"s1");
						this.data("s",1);
					});
		  graphic.set.push(gra);	
		  
		}
	  var onstart = function (event) {
		  graphic.set.forEach(function(_me){
			  _me.data('cmx',_me.data("mx"));
			  _me.data('cmy',_me.data("my"));
			});
		};
	  var onmove = function (dx, dy) {
		 graphic.set.forEach(function(_me){
			 var cmx = _me.data("mx") + dx;
			 var cmy = _me.data("my") + dy;
			  var tran = "t"+cmx+","+cmy+"s"+(_me.data("s"));
			  _me.data('cmx',cmx);
			  _me.data('cmy',cmy);
			  _me.transform(tran);
		});
	  };
	  var onend = function () {
			 graphic.set.forEach(function(_me){
				 var tran = "t"+_me.data('cmx')+","+_me.data('cmy')+"s1";
				 _me.transform(tran);
				 _me.data("mx",_me.data('cmx'));
				 _me.data("my",_me.data('cmy'));
			});
	  };
	  graphic.set.drag(onmove,onstart,onend);
	  if(isshow){		  
		  graphic.set.show();
	  }else{
		  graphic.set.hide();
	  }
	 
	  graphic.hidden = function(){
		  graphic.set.hide();
	  };
	  graphic.getName = function(){
		  return graphic.name;
	  };
	  graphic.show = function(){
		  graphic.set.show();
	  };
	  graphic.setIsShow = function(sh){
		  graphic.isshow = sh;
	  };
	  graphic.changeCxy = function(left,ow,nw,islarge){
		  graphic.set.forEach(function(_me){
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
	  
	  graphic.cxyExpand = function(scale,step){
		  graphic.scale = scale;
		  graphic.step = step;
		  graphic.set.forEach(function(_me){
			  var mx = _me.data("mx");
			  var my = _me.data("my");
			  var index = _me.data("index");
			  var in_x=index.split(":")[0];
			  var in_y=index.split(":")[1];
			  var tran = "t"+(mx+(graphic.step*in_x))+","+(my+(graphic.step*in_y))+"s"+(_me.data("s"));
			  _me.transform(tran);
			  _me.data("mx",(mx+(graphic.step*in_x)));
			  _me.data("my",(my+(graphic.step*in_y)));
			});
	  };
	  graphic.cxyNarrow = function(scale,step){
		  graphic.scale = scale;
		  graphic.step = step;
		  graphic.set.forEach(function(_me){
			  var mx = _me.data("mx");
			  var my = _me.data("my");
			  var index = _me.data("index");
			  var in_x=index.split(":")[0];
			  var in_y=index.split(":")[1];
			  var tran = "t"+(mx-(graphic.step*in_x))+","+(my-(graphic.step*in_y))+"s"+(_me.data("s"));
			  _me.transform(tran);
			  _me.data("mx",(mx-(graphic.step*in_x)));
			  _me.data("my",(my-(graphic.step*in_y)));
			});
	  };
	  return graphic; 
} 

