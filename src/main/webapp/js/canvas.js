/**
 * 
 * @param allDatas 所有数据
 * @param initDatas 初始化数据
 * @param args 显示的指标数据
 * @param pcname  父容器的id
 * @param cname   子容器的id
 * @param imageid  图片id
 * @param exid  放大id
 * @param naid  缩小的id
 * @param maxid 画布变大的id（需要根据这个元素获取放大后宽度）
 * @param cw  子容器的宽度
 * @param ch  子容器的高度
 * @param parentw 父容器的初始宽度
 * @param isclick 是否触发元素点击事件
 * @fristId 第一个点的ID
 * @fristType 第一个点的类型
 */
function MyCanvas(allDatas,initDatas,args,pcname,cname,imageid,exid,naid,maxid,cw,ch,parentw,cmdivid,isclick,fristId,fristType,id_last,type_last,compare){
	var canvas = new Object;
	canvas.allDatas = allDatas;
	canvas.parentObj = $('#'+pcname+'');
	canvas.containerObj  =$('#'+cname);
	canvas.containerdiv = cname;
	canvas.changeScreen = $("#"+imageid);
	canvas.cmdivid = cmdivid;
	canvas.expandObj = $('#'+exid);
	canvas.narrowObj = $('#'+naid);
	canvas.maxDivObj = $("#"+maxid);
	canvas.cmdivObj = $("#"+cmdivid);
	canvas.fristId = fristId;
	canvas.fristType = fristType;
	canvas.id_last = id_last;
	canvas.type_last = type_last;
	canvas.cw = cw;
	canvas.ch = ch;
	canvas.parentw = parentw;
	canvas.isclick = isclick;
	var paper;
	var scale = 2; //比例  1为100%
	var step = 0.2; //每次缩放比例
	var r = 5; //radius
	var maxScale = 5; //最大比例300%
	var minScale = 1; //最小比例100%
	var animateDelay = 500;
	var paperDown = false; //画布按下标识
	var paperOx = 0; //画布原始位置left
	var paperOy = 0; //画布原始位置top
	var paperCx = 0; //画布当前位置left
	var paperCy = 0; //画布当前位置top
	var g2,g3,g4;
	var sets = new Array();
	canvas.init = function(){
		scale = 1; 
		step = 0.2; //每次缩放比例
		r = 5; //radius
		maxScale = 3; //最大比例300%
		minScale = 1; //最小比例100%
		animateDelay = 500;
		paperDown = false; //画布按下标识
		paperOx = 0; //画布原始位置left
		paperOy = 0; //画布原始位置top
		paperCx = 0; //画布当前位置left
		paperCy = 0; //画布当前位置top
	};
	
	canvas.pushDataToSet = function(key,type,count,args,id,firstType,id_last,type_last,left,top,isclick){
		var data;
		if(type == 2){
			data = g3[key];
		}else if(type == 3){
			data = g4[key];
		}else{
			data = g2[key];
		}
		var ishow = false;
		for ( var i = 0; i < args.length; i++) {
			if(key == args[i]){
				ishow = true;
				break;
			}
		}
		if(key != null && key != ""){	
			sets.push(MyGraphic(data,count,ishow,key,scale,step,id,firstType,id_last,type_last,isclick,paper,r,left/1.7,(left/2)*(9/16)*1.5,compare));
		}
	};
	canvas.initData = function(svgDate,args,id,type,id_last,type_last,left,top,isclick,ss){
		var count = 0;
		var count_3 = 0,count_4=0;
		for(var key in svgDate){
		    if(key == "2G"){
		    	g2 = svgDate[key];   	
		    	for(var key2 in g2){
		    		count = $.inArray(key2, idooorTag[ss]);
		    		canvas.pushDataToSet(key2,1,count,args,id,type,id_last,type_last,left,top,isclick);
		    	}
		    }else if(key == "3G"){	    	
		    	g3 = svgDate[key];
		    	for(var key3 in g3){
	    			count_3 = $.inArray(key3, idooorTag[ss]);
		    		canvas.pushDataToSet(key3,2,count_3,args,id,type,id_last,type_last,left,top,isclick);
		    	}
		    }else if(key == "4G"){	  
		    	g4 = svgDate[key];
		    	for(var key4 in g4){
	    			count_4 = $.inArray(key4, idooorTag[ss]);
		    		canvas.pushDataToSet(key4,3,count_4,args,id,type,id_last,type_last,left,top,isclick);
		    	}
		    }
		}
	};
	canvas.hiddenAll = function(){
		if(sets.length > 0){		
			for ( var i = 0; i < sets.length; i++) {
				sets[i].hidden();
			}
		}
	};
	canvas.display = function(args){
		canvas.hiddenAll();
		if(sets.length > 0){		
			for ( var i = 0; i < sets.length; i++) {
				for ( var j = 0; j < args.length; j++) {
					if(args[j] != "3G" && args[j] != "2G"&& args[j] != "4G" && sets[i].getName() == args[j]){
						sets[i].show();
					}
				}
			}
		}
	};
	canvas.toExpand = function(){
		if(sets.length > 0){		
			for ( var i = 0; i < sets.length; i++) {
				sets[i].cxyExpand(scale,step);
			}
		}
	};
	canvas.toNarrow = function(){
		if(sets.length > 0){		
			for ( var i = 0; i < sets.length; i++) {
				sets[i].cxyNarrow(scale,step);
			}
		}
	};
	canvas.initArgs = function(top,left,paper){
		canvas.parentObj.css('width',canvas.parentw);
		canvas.containerObj.css('width',canvas.cw).css('height',canvas.ch);
		paper.clear();
		paper.setSize(canvas.cw,canvas.ch);
		canvas.containerObj.css('top',top);
		canvas.containerObj.css('left',left);
		scale = 1; //比例  1为100%
		step = 0.2; //每次缩放比例
		r = 5; //radius
		maxScale = 3; //最大比例300%
		minScale = 1; //最小比例100%
		animateDelay = 500;
		paperDown = false; //画布按下标识
		paperOx = 0; //画布原始位置left
		paperOy = 0; //画布原始位置top
		paperCx = 0; //画布当前位置left
		paperCy = 0; //画布当前位置top
		canvas.changeScreen.attr("src","../images/map_add.png");
		canvas.changeScreen.attr("sta","smail");
	};
	canvas.initPaper = function(ss){	
		paper = Raphael(canvas.containerdiv, canvas.cw, canvas.ch);
		if(canvas.changeScreen.attr("sta") == "large"){
			canvas.initArgs(canvas.cw/3,canvas.ch/3,paper);
		}
		canvas.initData(initDatas,args,canvas.fristId,canvas.fristType,canvas.id_last,canvas.type_last,canvas.cw/3,canvas.ch/3,canvas.isclick,ss);
		canvas.changeScreen.unbind("click").bind("click", function(){
			var pw = canvas.parentObj.width();
			var sta = canvas.changeScreen.attr("sta");
			if(sta == "smail"){
				canvas.changeScreen.attr("src","../images/map_lessen.png");
				canvas.changeScreen.attr("sta","large");
				var c_w = canvas.containerObj.width();
				var c_h = canvas.containerObj.height();
				var width = canvas.maxDivObj.width();
				canvas.parentObj.css('width',width);
				canvas.cmdivObj.css('width',width);
			    canvas.containerObj.css('width',3*width);
				paper.setSize(c_w,c_h);
				if(sets.length > 0){		
					for ( var i = 0; i < sets.length; i++) {
						sets[i].changeCxy(canvas.containerObj.position().left,pw,width,true);
					}
				}
			}else{
				canvas.parentObj.css('width',canvas.parentw);
				canvas.containerObj.css('width',canvas.cw).css('height',canvas.ch);
				canvas.changeScreen.attr("src","../images/map_add.png");
				canvas.changeScreen.attr("sta","smail");
				paper.setSize(canvas.cw,canvas.ch);
				canvas.cmdivObj.css('width',canvas.parentw);
				if(sets.length > 0){		
					for ( var i = 0; i < sets.length; i++) {
						sets[i].changeCxy(canvas.containerObj.position().left,pw,canvas.parentw,false);
					}
				}
				canvas.containerObj.css('top',Math.max(canvas.containerObj.position().top,(canvas.parentObj.height() - canvas.containerObj.height())));
				canvas.containerObj.css('left',Math.max(canvas.containerObj.position().left,(canvas.parentObj.width() - canvas.containerObj.width())));
			}
			location.hash= canvas.cmdivid;
		});
		canvas.expandObj.unbind("click").bind("click",function(){
			scale += step;
			scale = parseFloat(scale);
			if(scale > maxScale){
				scale = maxScale;
				return;
			}
			canvas.toExpand();
		});
		canvas.narrowObj.unbind("click").bind("click",function(){
			scale -= step;
			scale = parseFloat(scale);
			if(scale < minScale){
				scale = minScale;
				return;
			}
			canvas.toNarrow();
		});
		canvas.containerObj.unbind("mousedown").bind("mousedown",function(event){
			if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape'){
				paperOx = event.pageX;
				paperOy = event.pageY;
				paperDown = true;
			}
		});
		canvas.containerObj.unbind("mouseup").bind("mouseup",function(event){
			if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape'){
				paperDown = false;
			}
		});
		canvas.containerObj.unbind("mousemove").bind("mousemove",function(event){
			if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape' && paperDown){
				var dx = event.pageX- paperOx;
				var dy = event.pageY- paperOy;
				paperOx = event.pageX;
				paperOy = event.pageY;
				var ctop = parseInt(canvas.containerObj.position().top);
				var cleft = parseInt(canvas.containerObj.position().left);
				var cwidth = parseInt(canvas.containerObj.css('width')) - parseInt(canvas.parentObj.css('width'));
				var cheight = parseInt(canvas.containerObj.css('height')) - parseInt(canvas.parentObj.css('height'));
				ctop = ctop + dy;
				cleft = cleft + dx;
				ctop = Math.max(ctop,-cheight);
				cleft = Math.max(cleft,-cwidth);
				ctop = Math.min(ctop,0);
				cleft = Math.min(cleft,0);
				canvas.containerObj.css('top',ctop);
				canvas.containerObj.css('left',cleft);
			}
		});
		canvas.containerObj.unbind("mouseout").bind("mouseout",function(event){
			paperDown = false;
		});
	};
	return canvas;
}