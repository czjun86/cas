var paper;

var scale = 5; //比例  1为100%
var step = 1; //每次缩放比例
var r = 6; //radius
var maxScale =10; //最大比例300%
var minScale = 1; //最小比例100%
var animateDelay = 500;
var paperDown = false; //画布按下标识
var paperOx = 0; //画布原始位置left
var paperOy = 0; //画布原始位置top
var paperCx = 0; //画布当前位置left
var paperCy = 0; //画布当前位置top
var g2,g3,g4;
var sets = new Array();
var tempdatas;
function initArgs(wpage){
	var wid_len= 0;
	 var hie_len= 0;
	if(wpage == 1){
		var wid_len=(document.body.scrollWidth)*0.98;
		var hie_len=(document.body.scrollWidth)*(9/16);
	}else{
		var wid_len=400;
		var hie_len=400;
	}
	
	$('#parentContainer').css('width',wid_len);
	$('#container').css('width',wid_len*3).css('height',hie_len*3);
	paper.clear();
	paper.setSize(wid_len,hie_len);
	$('#container').css('top',wid_len);
	$('#container').css('left',hie_len);
	scale = 1; //比例  1为100%
	step = 1; //每次缩放比例
	r = 5; //radius
	maxScale = 10; //最大比例300%
	minScale = 1; //最小比例100%
	animateDelay = 500;
	paperDown = false; //画布按下标识
	paperOx = 0; //画布原始位置left
	paperOy = 0; //画布原始位置top
	paperCx = 0; //画布当前位置left
	paperCy = 0; //画布当前位置top
	 $("#div_page").show();
	 $("#div_page_max").hide();
	 $("#add_img_id").attr("src","../images/map_add.png");
	 $("#div_page").attr("style","margin-left:"+wid_len+"px;");
	 $("#div_left").attr("style","height:"+hie_len+"px;position: absolute;");
	 $("#div_point").attr("style","height:"+hie_len+"px;width:"+wid_len+"px; position: relative;");
	 $(".svg-div").attr("style","height:"+hie_len+"px;");
	 $("#add_img_id").attr("sta","smail");
	 $("#div_sn").css("margin-right","12px");
}
function initPaper(datas,args,id,type,id_last,type_last,wpage){
	var wid_len=(document.body.scrollWidth)*0.98;
	 var hie_len=(document.body.scrollWidth)*(9/16);
	 
	var parentObj = $('#parentContainer');
	var containerObj  =$('#container');
	var containerdiv = 'container';
	var changeScreen = $("#add_img_id");
	var expandObj = $('#expand');
	var narrowObj = $('#narrow');
	var paper_w = wid_len*4;
	var paper_h = hie_len*4;
	var cont_w = wid_len*3;
	var cont_h = hie_len*3;
	var parent_w = wid_len;
	tempdatas = datas;
	paper = Raphael(containerdiv, paper_w, paper_h);
	containerObj.css("top",-hie_len);
	containerObj.css("left",-wid_len);
	if(changeScreen.attr("sta") == "large"){
		initArgs(wpage);
	}
	initData(datas,args,id,type,id_last,type_last,wid_len,hie_len);
	changeScreen.unbind("click").bind("click", function(){
		var pw = parentObj.width();
		var sta = changeScreen.attr("sta");
		if(sta == "large"){
			var cw = containerObj.width();
			var ch = containerObj.height();
			var width = $("#div_page_max").width();
			parentObj.css('width',width);
		    containerObj.css('width',3*width);
			paper.setSize(containerObj.width(),containerObj.height());
			if(sets.length > 0){		
				for ( var i = 0; i < sets.length; i++) {
					sets[i].changeCxy(containerObj.position().left,pw,width,true);
				}
			}
		}else{
			parentObj.css('width',parent_w);
			containerObj.css('width',cont_w).css('height',cont_h);
			paper.setSize(paper_w,paper_h);
			if(sets.length > 0){		
				for ( var i = 0; i < sets.length; i++) {
					sets[i].changeCxy(containerObj.position().left,pw,parent_w,false);
				}
			}
			containerObj.css('top',Math.max(containerObj.position().top,(parentObj.height() - containerObj.height())));
			containerObj.css('left',Math.max(containerObj.position().left,(parentObj.width() - containerObj.width())));
		}
	});
	expandObj.unbind("click").bind("click",function(){
		scale += step;
		scale = parseFloat(scale);
		if(scale > maxScale){
			scale = maxScale;
			return;
		}
		toExpand();
	});
	narrowObj.unbind("click").bind("click",function(){
		scale -= step;
		scale = parseFloat(scale);
		if(scale < minScale){
			scale = minScale;
			return;
		}
		toNarrow();
	});
	containerObj.unbind("mousedown").bind("mousedown",function(event){
		if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape'){
			paperOx = event.pageX;
			paperOy = event.pageY;
			paperDown = true;
		}
	});
	containerObj.unbind("mouseup").bind("mouseup",function(event){
		if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape'){
			paperDown = false;
		}
	});
	containerObj.unbind("mousemove").bind("mousemove",function(event){
		if(event.target.nodeName != 'circle' && event.target.nodeName != 'shape' && paperDown){
			var dx = event.pageX- paperOx;
			var dy = event.pageY- paperOy;
			paperOx = event.pageX;
			paperOy = event.pageY;
			var ctop = parseInt(containerObj.position().top);
			var cleft = parseInt(containerObj.position().left);
			var cwidth = parseInt(containerObj.css('width')) - parseInt(parentObj.css('width'));
			var cheight = parseInt(containerObj.css('height')) - parseInt(parentObj.css('height'));
			ctop = ctop + dy;
			cleft = cleft + dx;
			ctop = Math.max(ctop,-cheight);
			cleft = Math.max(cleft,-cwidth);
			ctop = Math.min(ctop,0);
			cleft = Math.min(cleft,0);
			containerObj.css('top',ctop);
			containerObj.css('left',cleft);
		}
	});
	containerObj.unbind("mouseout").bind("mouseout",function(event){
		paperDown = false;
	});
//	IE9,IE10自动生成dy元素，导致页面不显示，页面加载完后移除dy
	window.onload = $("tspan").removeAttr("dy"); 
	var wl=new Object();
	wl.wid=wid_len;
	wl.hie=hie_len;
	return wl;
}
function pushDataToSet(key,type,count,args,id,firstType,id_last,type_last,left,top){
	var data;
	if(type == 1){
		data = g2[key];
	}else if(type==3){
		data = g4[key];
	}else{
		data = g3[key];
	}
	var ishow = false;
	for ( var i = 0; i < args.length; i++) {
		if(key == args[i]){
			ishow = true;
			break;
		}
	}
	if(key != null && key != ""){		
		sets.push(MyGraphic(data,count,ishow,key,scale,step,id,firstType,id_last,type_last,true,paper,r,left*1.3,top*1.2));
	}
}
function initData(datas,args,id,type,last_id,last_type,left,top,idooorTag){
	var count = 0;
	var count_3 = 0,count_4=0;
	//var datas = tempdatas;
	for(var key in datas)
	{
	    if(key == "2G"){
	    	g2 = datas[key];
		    	for(var key2 in g2){
		    		count=$.inArray(key2, idooorTag);
		    		//count++;
		    		pushDataToSet(key2,1,count,args,id,type,last_id,last_type,left,top);
		    	}
	    }else if(key == "3G"){	    	
	    	g3 = datas[key];
		    	for(var key3 in g3){
		    		//count_3++;
		    		count_3=$.inArray(key3, idooorTag);
		    		pushDataToSet(key3,2,count_3,args,id,type,last_id,last_type,left,top);
		    	}
	    }
	    else if(key == "4G"){	    	
	    	g4= datas[key]; 
		    	for(var key4 in g4){
		    		count_4=$.inArray(key4, idooorTag);
		    		pushDataToSet(key4,3,count_4,args,id,type,last_id,last_type,left,top);
		    	}
	    }
	}
}
function hiddenAll(){
	if(sets.length > 0){		
		for ( var i = 0; i < sets.length; i++) {
			sets[i].hidden();
		}
	}
}
function display(args){
	hiddenAll();
	if(sets.length > 0){		
		for ( var i = 0; i < sets.length; i++) {
			for ( var j = 0; j < args.length; j++) {
				if(args[j] != "3G" && args[j] != "2G"&& args[j] != "4G" && sets[i].getName() == args[j]&&args[j]!=""){
					sets[i].show();
				}
			}
		}
	}
}

function toExpand(){
	if(sets.length > 0){		
		for ( var i = 0; i < sets.length; i++) {
			sets[i].cxyExpand(scale,step);
		}
	}
}
function toNarrow(){
	if(sets.length > 0){		
		for ( var i = 0; i < sets.length; i++) {
			sets[i].cxyNarrow(scale,step);
		}
	}
}
