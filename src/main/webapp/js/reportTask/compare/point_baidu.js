/**
 * 加入对比轨迹点展示
 * gsmlist,wcdmalist分别是2G与3G的轨迹数据
 * @author czj
 * **/
var map_arr=[];
var cans = [];
var canscount = 0;
var idooorTag = new Object();
var mapWforGoogle;
function queryPoint(gsmlist,wcdmalist,center,flist,flowids,flowname){
	var compare = 1;
	var psc_xlist=[],bcch_xlist=[];
	for(var t=0;t<flist.length;t++){
		if(flist[t].kpiId==20){
			psc_xlist=flist[t].y;
		}
        if(flist[t].kpiId==21){
        	bcch_xlist=flist[t].y;
		}
	}
	
	var arr_id=flowids.split(",");
	var arr_name=flowname.split(",");
	//浮动div
	$("#tuli_ul").html('');
	var tuils=[];
	tuils.push('<ul>');
	var cen_arr_f=[];
	for (var t=0;t<center.length;t++){
		cen_arr_f.push(center[t].flowid);
	}
	map_arr=[];
	$("#compare_map").html("");
	markerMap_fliwids_2g.clear();
	markerMap_fliwids_3g.clear();
	zNodes_map.clear();
	var sumlist=gsmlist;
	for(var i=0;i<wcdmalist.length;i++){
		sumlist.push(wcdmalist[i]);
	}
	//给2G数据 、3G数据按流水号分组
	var xxMap={};  
    for(var i=0;i<sumlist.length;i++){  
        if(xxMap[sumlist[i].flowid] == undefined){  
            var list = [];  
            list.push(sumlist[i]);  
            xxMap[sumlist[i].flowid] = list;  
        }else{  
        	xxMap[sumlist[i].flowid].push(sumlist[i]);  
        }  
    }  
    //计算宽度高度
    var wid_len=(document.body.scrollWidth-document.body.scrollWidth*0.15)/2;
    var hie_len=((document.body.scrollWidth-document.body.scrollWidth*0.15)/2)*(9/16);
    $("#max").css("width",(wid_len+15)*2+20);
    $("#max").css("height",((wid_len+15)*2+20)*(9/16));
    var i_s=0;
    for(ss in xxMap){
    	//一个流水号下的数据
    	    var yy_p=[],yy_b=[];
    	    if(psc_xlist.length>0){
    	    	for(var t=0;t<psc_xlist.length;t++){
    		    	if(psc_xlist[t].flowid==ss){
    		    		yy_p.push(psc_xlist[t].pscbcch);
    		    	}
    		    }
    	    }
    	    if(bcch_xlist.length>0){
    	    	for(var t=0;t<bcch_xlist.length;t++){
    		    	if(bcch_xlist[t].flowid==ss){
    		    		yy_b.push(bcch_xlist[t].pscbcch);
    		    	}
    		    }
    	    }
		    
    	 var na_index= 0;
    	 na_index= $.inArray(ss,arr_id);
    	 tuils.push('<li style="color:'+colorRoom_1[i_s]+';">'+arr_name[na_index]+'</li>');
    	 zNodes=[];
    	 var child=[],child_1=[];
    	 var st=xxMap[ss];
    	
    	 var map_html=[];
    	 map_html.push('<div class="computer_map_div" style="width:'+wid_len+'px;height:'+(hie_len+32)+'px;" id="mapdiv'+ss+'" > ');
    	 map_html.push('<div style="float:left"><select name="" id="g2_'+ss+'" class="g2select" seno="'+ss+'" style="z-index:998;width:100px" data-options="editable:false,panelHeight: \'auto\'"></select><select name="" class="g3select" seno="'+ss+'" id="g3_'+ss+'" style="z-index:998;width:100px" data-options="editable:false,panelHeight: \'auto\'"></select></div>');
    	 map_html.push('<samp style="z-index:998;margin:0px 0 0 8px;" class="samp_comp" id="samp_'+ss+'" inside="'+st[0].inside+'" title="轨迹叠加"></samp>');
    	 map_html.push('<div style="float:right;" id="count_'+ss+'"></div>');
    	 map_html.push('<p id="p_'+ss+'" style="color:'+colorRoom_1[i_s]+';margin-left:10px;float:right;width:auto;line-height:24px;margin-right:10px">'+ss.substring(0,12)+'</p>');
    	 map_html.push('<div style="height:'+(hie_len)+'px;position: absolute;margin-top:36px">');
    	 map_html.push('<div style="z-index:999;float:right;width:20px;position: absolute; right: 0px;">');
    	 map_html.push('<img src="'+contextPath+'/images/map_add.png"  class="changeImg" style="cursor:pointer;" id="imageid'+ss+'" sta= "smail" />');
    	 if(st[0].inside == 0){    		 
    		 map_html.push('<img src="'+contextPath+'/images/map_big.png" style="display:none;"  id="extid'+ss+'" />');
    		 map_html.push('<img src="'+contextPath+'/images/map_small.png" style="display:none;"  id= "naid'+ss+'"/></div>');
    	 }else{
    		 map_html.push('<img src="'+contextPath+'/images/map_big.png" style="cursor:pointer;" id="extid'+ss+'" />');
    		 map_html.push('<img src="'+contextPath+'/images/map_small.png" style="cursor:pointer;" id= "naid'+ss+'"/></div>');
    	 }	
    	//图例
    	 map_html.push('<div class="case_tuc" id="case_tuc_'+ss+'"><div class="case_tuc_yyy"><p id="img_demo_'+ss+'" style="width:19px;height:19px;"></p><div class="clear"></div><div id="demo_div_'+ss+'"></div></div></div>');
    	 map_html.push('<div id="map_'+ss+'" class="svg-div-main" style="height:'+(hie_len)+'px; width:'+wid_len+'px;overflow:hidden;">');
    	 map_html.push('<div id="container'+ss+'" class="svg-main" style="width:1500px;height:1500px;position:absolute;left:-400px;top:-400px;background-color:#fff"></div>');
    	 map_html.push('</div></div></div>');
    	 i_s++;
         $("#compare_map").append($(map_html.join('\r\n')));
 	     $("#g2_"+ss).hide();
	     $("#g3_"+ss).hide();
	     $("#g2_"+ss).empty();
	     $("#g3_"+ss).empty();
	     $("#count_"+ss).html("采样点："+st.length);
	     //根据数据生成菜单
	     var s_len=st.length;
	     var dd=[],dd_id=[];
	     var dd_1=[],dd_id_1=[];
	     for(var t=0;t<s_len;t++){
			 if($.inArray(1,dd_id)<0&&st[t].rscp>0){
				    dd_id.push(1);
	    			dd.push({"text":'RSCP', "id": '1',"selected":true});
	    			child.push({'id':3,'text':"RSCP"});
	    		 }
	    		 if($.inArray(2,dd_id)<0&&st[t].ecno>0){
	    		    dd_id.push(2);
	 	    		dd.push({"text":'EcNo', "id": '2'});
	 	    		child.push({'id':4,'text':"EcNo"});
	    		 }
	    		 if($.inArray(3,dd_id)<0&&st[t].txpower>0&&st[t].rscp>0){
	    			dd_id.push(3);
	 	    		dd.push({"text":'TXPOWER', "id": '3'});
	 	    		child.push({'id':5,'text':"TXPOWER"});
	    		 }
	    		 if($.inArray(4,dd_id)<0&&st[t].ftpSpeed>0){
	    			dd_id.push(4);
	 	    		dd.push({"text":'FTP', "id": '4'});
	 	    		child.push({'id':6,'text':"FTP"});
	    		 }
	    		 if($.inArray(20,dd_id)<0&&st[t].psc>0){
	    			dd_id.push(20);
	 	    		dd.push({"text":'PSC', "id": '20'});
	 	    		child.push({'id':22,'text':"PSC"});
	    		 }
	    		 if($.inArray(6,dd_id_1)<0&&st[t].rxlev>0){
	    			dd_id_1.push(6);
		    		dd_1.push({"text":'RXLEV', "id": '6',"selected":true});
	    			child_1.push({'id':8,'text':"RXLEV"});
	    		 }
	    		 if($.inArray(7,dd_id_1)<0&&st[t].rxqual>0){
	    			dd_id_1.push(7);
			    	dd_1.push({"text":'RXQUAL', "id": '7'});
			    	child_1.push({'id':9,'text':"RXQUAL"});
	    		 }
	    		 if($.inArray(8,dd_id_1)<0&&st[t].rxqual>0){
		    			dd_id_1.push(8);
				    	dd_1.push({"text":'C/I', "id": '8'});
				    	child_1.push({'id':10,'text':"C/I"});
		    		 }
	    		 if($.inArray(21,dd_id_1)<0&&st[t].bcch>0){
	    			dd_id_1.push(21);
				    dd_1.push({"text":'BCCH', "id": '21'});
				    child_1.push({'id':23, 'text':"BCCH"});
	    		 }
	    		
	   }
         if(dd_id.length>0){
        	 zNodes.push({'id':2, text:"3G",'children':child});
         }
         if(dd_id_1.length>0){
        	 zNodes.push({'id':1, text:"2G",'children':child_1});
         }
	     if(dd_id.length>0){
			 $("#g3_"+ss).combobox({
				 data:dd,
				 valueField: 'id',
				 textField: 'text'
			 });} 
	     if(dd_id_1.length>0){
			 $("#g2_"+ss).combobox({
				 data:dd_1,
				 valueField: 'id',
				 textField: 'text'
			 });} 
	     
	   zNodes_map.put(ss,zNodes);
	   
	  
       if(st[0].inside==0){
   	      //生成地图    
   	     var  map = new BMap.Map(document.getElementById("map_"+ss));
   	     map.centerAndZoom(new BMap.Point(106.54241,29.559247) ,10);
   	     //地图事件设置函数：
   		map.enableDragging(); //启用地图拖拽事件，默认启用(可不写)
   		map.enableScrollWheelZoom(); //启用地图滚轮放大缩小
   		map.enableDoubleClickZoom(); //启用鼠标双击放大，默认启用(可不写)
   		map.enableKeyboard(); //启用键盘上下左右键移动地图
   		//地图控件添加函数：
   	    //向地图中添加缩放控件
   	    var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT, type: BMAP_NAVIGATION_CONTROL_SMALL });
   	    map.addControl(ctrl_nav);
   	    //向地图中添加缩略图控件
   	    var ctrl_ove = new BMap.OverviewMapControl({ anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1 });
   	    map.addControl(ctrl_ove);
   	    //向地图中添加比例尺控件
   	    var ctrl_sca = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT });
   	    map.addControl(ctrl_sca);
   	    mapWforGoogle = new BMapLib.MapWrapper(map, BMapLib.COORD_TYPE_GOOGLE);
   	     
   	     
   	     
   	     
   	     var ija=$.inArray(ss,cen_arr_f);
   	     if(ija>=0){
   	    	var cenetr_fl=[new BMap.Point(center[0].max_lng, center[0].max_lat), new BMap.Point(center[0].min_lng,center[0].min_lat)];
   	    	map.setViewport(cenetr_fl);
   	     }
   	  else{
     	map.centerAndZoom(cenetr_fl[0] ,15);
     }
   	     map_arr.push(map);
   	  //下拉选择事件
	     (function(){      
	   	      var p = map;  
	   	      var s_fid = ss; 
	   	      var st_p=st,ww=[],gg=[];
	   	      var pp_y=yy_p,bb_y=yy_b;
	   	      for (var f in st_p){
	   	    	  if(st_p[f].rxlev>0){
	   	    		  gg.push(st_p[f]);
	   	    	  }else if(st_p[f].rscp>0){
	   	    		ww.push(st_p[f]);
	   	    	  }
	   	      }
	   	   var d=dd_id,d_1=dd_id_1;
	   	   if(d_1.length>0){
	   		$("#g2_"+s_fid).combobox({
	   		  onSelect: function (n,o) {
	   			  		var str = $("#g2_"+s_fid).combobox("getText");
			   		   chooseKpi(s_fid,p,null,st_p,str,1,pp_y,bb_y);
			   		  var sel1=0,sel2=0;
	  				   var sel1_n="",sel2_n="";
	  				   if(d_1.length>0){sel1=$("#g2_"+s_fid).combobox("getValue");
		  				   var str = $("#g2_"+s_fid).combobox("getText");
		  				   sel1_n=str;
	  				   }
	  		    	   if(d.length>0){sel2=$("#g3_"+s_fid).combobox("getValue");
	  		    	   sel2_n=$("#g3_"+s_fid).combobox("getText");}
	  		    	   if($("#demo_div_"+s_fid).html()){
		  		    	   choose_demo(s_fid,flist,sel1,sel2,sel1_n,sel2_n,"#demo_div_"+s_fid,ww,gg);
	  		    	   }
	   		 }
	   		});
	   	   }
				//下拉列表3G点击事件
	   	     if(d.length>0){
		   		$("#g3_"+s_fid).combobox({
		   		  onSelect: function (n,o) {
		   				 chooseKpi(s_fid,p,st_p,null,$("#g3_"+s_fid).combobox("getText"),2,pp_y,bb_y); 
		   				   var sel1=0,sel2=0;
		  				   var sel1_n="",sel2_n="";
		  				   if(d_1.length>0){sel1=$("#g2_"+s_fid).combobox("getValue");
		  				   sel1_n=$("#g2_"+s_fid).combobox("getText");}
		  		    	   if(d.length>0){sel2=$("#g3_"+s_fid).combobox("getValue");
		  		    	   sel2_n=$("#g3_"+s_fid).combobox("getText");}
		  		    	 if($("#demo_div_"+s_fid).html()){
		  		    	   choose_demo(s_fid,flist,sel1,sel2,sel1_n,sel2_n,"#demo_div_"+s_fid,ww,gg);}
		   		 }
		   		});
		   	   }
			 $("#imageid"+s_fid).unbind("click").bind("click", function(){
				var pw = $("#map_"+s_fid).width();
				var sta =  $(this).attr("sta");
				if(sta == "smail"){
					$(this).attr("src","../images/map_lessen.png");
					$(this).attr("sta","large");
					var width = $("#max").width();
					$("#map_"+s_fid).css('width',width);
					$("#mapdiv"+s_fid).css('width',width);
				}else{
					$(this).attr("src","../images/map_add.png");
					$(this).attr("sta","smail");
					$("#map_"+s_fid).css('width',wid_len);
					$("#mapdiv"+s_fid).css('width',wid_len);
				}
				//google.maps.event.trigger(p, 'resize');
				location.hash = "#mapdiv"+s_fid;
			});    
			 
  	   })();
	     //3G室外点默认加载rscp
		  chooseKpi(ss,map,st,null,"RSCP",2,psc_xlist,bcch_xlist);
		    //2G室外点默认加载rxlev
		  chooseKpi(ss,map,null,st,"RXLEV",1,psc_xlist,bcch_xlist);
       }else if(st[0].inside==1){
    	   //室内
    	   var canvas = showIndoorPoint(ss,st,st,colorlist,wid_len,yy_p,yy_b,compare);
    	   cans.push(canvas);
    	   $("#mapdiv"+ss).attr("index",canscount);
    	   canscount ++;
    	   (function(){ 
	  	  		var g2sel,g3sel,temp="";
			   	var vs= new Array();
			   	var s_fid = ss; 
			    var st_p=st,ww=[],gg=[];
		   	      for (var f in st_p){
		   	    	  if(st_p[f].rxlev>0){
		   	    		  gg.push(st_p[f]);
		   	    	  }else if(st_p[f].rscp>0){
		   	    		ww.push(st_p[f]);
		   	    	  }
		   	      }
			   	var d=dd_id,d_1=dd_id_1;
	  	  	if(d_1.length>0){
   			 $("#g2_"+s_fid).combobox({
					 onSelect: function (n,o) {
						      temp="";
						      var str ="";
				  	  			var seno = $(this).attr("seno");
				  	  			var _key = $(this).combobox("getText");
				  	  			g3sel=$("#g3_"+seno).val();
				  	  		    if(d_1.length>0){
				  	  		    	str = $("#g2_"+seno).combobox("getText");
				  	  		    	temp =  str+"," + temp;
				  	  		    	}
					  	  	   if(d.length>0){temp =  $("#g3_"+seno).combobox("getText")+"," + temp;}
					  	 	   var sel1=0,sel2=0;
			  				   var sel1_n="",sel2_n="";
			  				   if(d_1.length>0){sel1=$("#g2_"+seno).combobox("getValue");
			  				   sel1_n=$("#g2_"+seno).combobox("getText");}
			  		    	   if(d.length>0){sel2=$("#g3_"+seno).combobox("getValue");
			  		    	   sel2_n=$("#g3_"+seno).combobox("getText");}
			  		    	 if($("#demo_div_"+seno).html()){
			  		    	   choose_demo(seno,flist,sel1,sel2,sel1_n,sel2_n,"#demo_div_"+seno,ww,gg);
			  		    	 }
								vs=temp.split(",");
								var index = $("#mapdiv"+seno).attr("index");
								var ss = "data_" + seno;
								var draw_vs = new Array();
								for (var x=0;x<vs.length;x++){
									if($.inArray(vs[x], idooorTag[ss])<0){
										idooorTag[ss].push(vs[x]);
										draw_vs.push(vs[x]);
									}
								}
								var cans_index = cans[index];
								if(draw_vs.length>0){
									var obj = getIdooorTagData(cans_index.allDatas,draw_vs);
									cans_index.initData(obj,draw_vs,cans_index.fristId,cans_index.fristType,cans_index.id_last,cans_index.type_last,cans_index.cw/3,cans_index.ch/3,cans_index.isclick,ss);
								} 
								
								cans_index.display(vs);
								
					 }
					 });
   		    }
  				//下拉列表3G点击事件
  				if(d.length>0){
	    			$("#g3_"+s_fid).combobox({
						 onSelect: function (n,o) {
							        temp="";
							        var str = "";
				  					var seno = $(this).attr("seno");
				  					g2sel=$("#g2_"+seno).val();
				  					 if(d.length>0){temp =  $("#g3_"+seno).combobox("getText")+"," + temp;}
				  		  			 if(d_1.length>0){
				  		  				str = $("#g2_"+seno).combobox("getText");
				  		  				 temp =  str+"," + temp;
				  		  				 }
				  		  		 var sel1=0,sel2=0;
				  				  var sel1_n="",sel2_n="";
				  				   if(d_1.length>0){sel1=$("#g2_"+seno).combobox("getValue");
				  				   sel1_n=$("#g2_"+seno).combobox("getText");}
				  		    	   if(d.length>0){sel2=$("#g3_"+seno).combobox("getValue");
				  		    	   sel2_n=$("#g3_"+seno).combobox("getText");}
				  		    	 if($("#demo_div_"+seno).html()){
				  		    	   choose_demo(seno,flist,sel1,sel2,sel1_n,sel2_n,"#demo_div_"+seno,ww,gg);
				  		    	 }
									vs=temp.split(",");
									var index = $("#mapdiv"+seno).attr("index");
									var ss = "data_" + seno;
									var draw_vs = new Array();
									for (var x=0;x<vs.length;x++){
										if($.inArray(vs[x], idooorTag[ss])<0){
											idooorTag[ss].push(vs[x]);
											draw_vs.push(vs[x]);
										}
									}
									var cans_index = cans[index];
									if(draw_vs.length>0){
										var obj = getIdooorTagData(cans_index.allDatas,draw_vs);
										cans_index.initData(obj,draw_vs,cans_index.fristId,cans_index.fristType,cans_index.id_last,cans_index.type_last,cans_index.cw/3,cans_index.ch/3,cans_index.isclick,ss);
									} 
									
									cans_index.display(vs);
						 }
						 });
	    		}
  	  		})();
       }
     
       (function(){ 
    		 
    	    var ff=true;
    		var s_fid = ss;
    		 var st_p=st,ww=[],gg=[];
	   	      for (var f in st_p){
	   	    	  if(st_p[f].rxlev>0){
	   	    		  gg.push(st_p[f]);
	   	    	  }else if(st_p[f].rscp>0){
	   	    		ww.push(st_p[f]);
	   	    	  }
	   	      }
    		var d=dd_id,d_1=dd_id_1;
    	   $("#img_demo_"+s_fid).unbind("click").bind("click", function(){
    		   $("#case_tuc_"+s_fid).css("background","none repeat scroll 0 0 #fff");
     		  $("#case_tuc_"+s_fid).css("border-bottom","1px solid #979797");
     		  $("#case_tuc_"+s_fid).css("border-right","1px solid #979797");
 	         var sel1=0,sel2=0;
			  var sel1_n="",sel2_n="";
			   if(d_1.length>0){sel1=$("#g2_"+s_fid).combobox("getValue");
			   sel1_n=$("#g2_"+s_fid).combobox("getText");}
	    	   if(d.length>0){sel2=$("#g3_"+s_fid).combobox("getValue");
	    	   sel2_n=$("#g3_"+s_fid).combobox("getText");}
	     if(ff==true){
	    	   choose_demo(s_fid,flist,sel1,sel2,sel1_n,sel2_n,"#demo_div_"+s_fid,ww,gg);
	    	   $("#img_demo_"+s_fid).css({'background-image':'url(../images/case_tu.png)'});
	    	   ff=false;
		  }else{
			  $("#case_tuc_"+s_fid).css("background","");
    		  $("#case_tuc_"+s_fid).css("border-bottom","");
    		  $("#case_tuc_"+s_fid).css("border-right","");
			  $("#demo_div_"+s_fid).html("");
			  ff=true;
			  $("#img_demo_"+s_fid).css({'background-image':'url(../images/case_tu_s.png)'});
		  }
 	    });   
       })();
   	   //轨迹叠加树形菜单
       var inside = 0;
       (function(){ 
    	   var s_fid = ss;
       	var d=dd_id,d_1=dd_id_1;
      	   $('#samp_'+s_fid).die().live("click",function(e){ 
      		 $(".easyui-linkbutton").show();
      		   inside = $(this).attr("inside");
   	   	   $('.sure').attr("id",s_fid+"_sure");
   		   var zno=zNodes_map.get(s_fid); 
   		   $("#zhibiao").dialog({
   				height: 200,width: 380,title: "测试指标选择",
   				modal: true,closed:false
   			});
   		   $("#tul").tree({
   			   data:zno,
   			   animate:true,
   			   checkbox:true,
   			   onlyLeafCheck:true
   		   });
   	   	   var sel1,sel2;
   	   	   sel1="#g2_"+s_fid;
   	   	   sel2="#g3_"+s_fid;
   	   	   if(d_1.length>0){sel1 =$(sel1).combobox("getValue");}
   		   if(d.length>0){sel2 =$(sel2).combobox("getValue");}
   		   for(i in zno)
    		  {
    			 child=zno[i].children;
    			 for(j in child){
    				 if(child[j].id==(parseInt(sel1)+2)){
    					 var no= $("#tul").tree("find",child[j].id);
    					 $("#tul").tree("remove",no.target);
    				 }
   					if(child[j].id==(parseInt(sel2)+2)){
   						 var no= $("#tul").tree("find",child[j].id);
    					 $("#tul").tree("remove",no.target);
    				 }
    			 }
    		  }
   			$('#zhibiao').dialog('open');
   			$.parser.parse('#zhibiao');
          });
      	  //树形确定事件
          $('.treeb').unbind("click").click(function(e){
       	   var sel1,sel2;      
       	   var ss_f=$(".sure").attr("id").split("_")[0];
       	   sel1="#g2_"+ss_f;
           sel2="#g3_"+ss_f;
           var sgf=xxMap[ss_f];
           for (var rr in sgf){
        	   if(sgf[rr].rxlev>0){
        		   sel1=$(sel1).combobox("getValue");
        		   break;
        	   }
           }
           for (var rr in sgf){
             if(sgf[rr].rscp>0){
            	 sel2=$(sel2).combobox("getValue");
            	 break;
        	   }
           }
//          if(d_1.length>0){sel1=$(sel1).combobox("getValue");}
//   		  if(d.length>0){sel2=$(sel2).combobox("getValue");}
   		  var nodes = $('#zhibiao').tree('getChecked');
   		  var v="",id_v="",str="";
   		  if(nodes.length==0){
   				$.messager.alert("warning","至少选择一个指标！","warning");
   				return ;
   			}
   		  for(var i=0; i<nodes.length; i++){
   			  	str = nodes[i].text;
   				if(i != nodes.length-1){
   					v+=str + ",";
   					id_v+=nodes[i].id + ",";
   				}else{
   					v+=str;
   					id_v+=nodes[i].id ;
   				}
   			}
   		var idd="";
   		
   		if(sel2){
   			idd+=(parseInt(sel2)+2)+",";
   		}
   		if(sel1){
   			idd+=(parseInt(sel1)+2)+",";
   		}
   		idd+=id_v;
   		$("#undata").val(v);
   		$("#undata_id").val(idd);
   		 if(inside == 1){
   			 var slf=xxMap[ss_f];
   			 var str = "";
             for (var rr in slf){
          	   if(slf[rr].rxlev>0){
          		 str = $("#g2_"+ss_f).combobox("getText");
          		 v = str+"," + v; 
          		   break;
          	   }
             }
             for (var rr in slf){
                 if(slf[rr].rscp>0){
              	   v = $("#g3_"+ss_f).combobox("getText")+"," + v;
                	 break;
            	   }
               }
   			var vs= new Array();
   			vs = v.split(",");
   			var index = $("#mapdiv"+ss_f).attr("index");
   			var draw_vs = new Array();
   			var ss = "data_" + ss_f;
			for (var x=0;x<vs.length;x++){
				if($.inArray(vs[x], idooorTag[ss])<0){
					idooorTag[ss].push(vs[x]);
					draw_vs.push(vs[x]);
				}
			}
			var cans_index = cans[index];
			if(draw_vs.length>0){
				var obj = getIdooorTagData(cans_index.allDatas,draw_vs);
				cans_index.initData(obj,draw_vs,cans_index.fristId,cans_index.fristType,cans_index.id_last,cans_index.type_last,cans_index.cw/3,cans_index.ch/3,cans_index.isclick,ss);
			} 
   			cans[index].display(vs);
   			$('.wrapper,#dialData').hide();
   		 }else if(inside == 0){
   			 outclick($(".sure").attr("id").split("_")[0],map_arr,xxMap[$(".sure").attr("id").split("_")[0]],xxMap[$(".sure").attr("id").split("_")[0]],yy_p,yy_b);
   		 }
   		 $('#zhibiao').dialog('close');
       });
       })();
    }

   $("#background").hide();
   $("#bar_id").hide();
   $(document).css({"overflow":"auto"});

   tuils.push('</ul>');
   tuils = $(tuils.join('\r\n'));
   $("#tuli_ul").html(tuils);
}
/**
 * 室外多选点击事件
 * @param doc
 */
function outclick(ss,map_arr,wcdmalist,gsmlist,yy_p,yy_b){
	var map=null;
	for(t in map_arr){
		if($(map_arr[t].getContainer()).attr("id").split("_")[1]==ss){
			map=map_arr[t];
		}
	}
	$('.wrapper,#dialData').hide();
	  var obj_id = $("#undata_id").attr("value");
	  var sid=obj_id.split(",");
	  var g3="";g2="";
	  for(var i in sid){
		  switch (parseInt(sid[i])) {
		case 3:
			g3+=",RSCP";
			break;
		case 4:
			g3+=",EcNo";
			break;
		case 5:
			g3+=",TXPOWER";
			break;
		case 6:
			g3+=",FTP";
			break;
		case 22:
			g3+=",PSC";
			break;
		case 8:
			g2+=",RXLEV";
			break;
		case 9:
			g2+=",RXQUAL";
			break;
		case 10:
			g2+=",C/I";
			break;
		case 11:
			g2+=",MOS";
			break;
		case 23:
			g2+=",BCCH";
			break;

		default:
			break;
		}
	  }
	  g2= g2.length>0?g2.substring(1,g2.length):"";
	  g3= g3.length>0?g3.substring(1,g3.length):"";
      if(g2!=""){
    	  chooseKpi(ss,map,null,gsmlist,g2,1,yy_p,yy_b);
      }
	  if(g3!=""){
		  chooseKpi(ss,map,wcdmalist,null,g3,2,yy_p,yy_b);
	  }
}

/**
 * 关闭弹出框
 */
function closeclick(){
   $("#background").hide();
   $("#bar_id").hide();
   $("body").removeClass('background');
   $(document).css({"overflow":"show"});
	$('.wrapper,#dialData').hide();
}
/***
 *  根据展示室内轨迹点数据*
 *
 */
function showIndoorPoint(ss,gsmlist,wcdmalist,colorlist,winlen,yy_p,yy_b,compare)
{
	var pscmap = new Map();//键：PSC值，值：颜色集id
	var bcchmap=new Map();//键：PSC值，值：颜色集id
	var time_3g="",time_2g="",id_3g,id_2g;
	var colorall=[];
	var arr_color=[];
	var co_len=colorlist.length;
	for(var t=0;t<co_len;t++)
	{
		var color=colorlist[t];
		arr_color.push(color.colourcode);
	}
	  var psc_arr_color=[];
	   var arr_co_len=arr_color.length;
	   for (var j=arr_co_len-1;j>=0;j--){
		   psc_arr_color.push(arr_color[j]);
	   }
	var dc=colorall.concat(psc_arr_color,colorRoom);//两个颜色合并作为PSC颜色用
	//指标数据
	var rscp=[],ecno=[],TxPower=[],psc=[],ftpSpeed=[];
	//3G
	for(var i=0;i<wcdmalist.length;i++)
	{
		var wcdma=wcdmalist[i];
		//RSCP
		if(wcdma.rscp>0){
			var colorid=wcdma.rscp-1;
			var obj={x:wcdma.x,y:wcdma.y,color:arr_color[colorid],id:wcdma.id,type:'2',va:wcdma.rscp_,reltype:wcdma.realnet_type};
			rscp.push(obj);
		}
		//ECNO
		if(wcdma.ecno>0){
			var colorid=wcdma.ecno-1;
			var obj={x:wcdma.x,y:wcdma.y,color:arr_color[colorid],id:wcdma.id,type:'2',va:wcdma.ecno_,reltype:wcdma.realnet_type};
			ecno.push(obj);
		}
		//TxPower
		if(wcdma.txpower>0){
			var colorid=wcdma.txpower-1;
			var obj={x:wcdma.x,y:wcdma.y,color:arr_color[colorid],id:wcdma.id,type:'2',va:wcdma.txpower_,reltype:wcdma.realnet_type};
			TxPower.push(obj);
		}
		//FTP
		if(wcdma.ftpSpeed>0){
			var colorid=wcdma.ftpSpeed-1;
			var obj={x:wcdma.x,y:wcdma.y,color:arr_color[colorid],id:wcdma.id,type:'2',va:wcdma.ftpSpeed_,reltype:wcdma.realnet_type};
			ftpSpeed.push(obj);
		}
		//PSC
		if(wcdma.psc){
			 var ij=$.inArray(wcdma.psc.toString(),yy_p);
			   if(ij<20){
				   var obj={x:wcdma.x,y:wcdma.y,color:dc[ij],id:wcdma.id,type:'2',va:wcdma.psc,reltype:wcdma.realnet_type};
					psc.push(obj);
		       }
		}
		
	}
	//2G
	var rxlev=[],txpower_2g=[],rxqual=[],bcch=[],ci=[],mos=[];
	for(var i=0;i<gsmlist.length;i++)
	{
		var gsm=gsmlist[i];
		//RxLev
		if(gsm.rxlev>0){
			var colorid=gsm.rxlev-1;
			var obj={x:gsm.x,y:gsm.y,color:arr_color[colorid],id:gsm.id,type:'1',va:gsm.rxlev_,reltype:gsm.realnet_type};
			rxlev.push(obj);
		}
		//TxPower
		if(gsm.txpower>0){
			var colorid=gsm.txpower-1;
			var obj={x:gsm.x,y:gsm.y,color:arr_color[colorid],id:gsm.id,type:'1',va:gsm.txpower_,reltype:gsm.realnet_type};
			txpower_2g.push(obj);
		}
		//rxqual
		if(gsm.rxqual>0){
			var colorid=gsm.rxqual-1;
			var obj={x:gsm.x,y:gsm.y,color:arr_color[colorid],id:gsm.id,type:'1',va:gsm.rxqual_,reltype:gsm.realnet_type};
			rxqual.push(obj);
		}
		//ci
		if(gsm.ci>0){
			var colorid=gsm.ci-1;
			var obj={x:gsm.x,y:gsm.y,color:arr_color[colorid],id:gsm.id,type:'1',va:gsm.ci_,reltype:gsm.realnet_type};
			ci.push(obj);
		}
		//mos
		if(gsm.mos>0){
			var colorid=gsm.mos-1;
			var obj={x:gsm.x,y:gsm.y,color:arr_color[colorid],id:gsm.id,type:'1',va:gsm.mos_,reltype:gsm.realnet_type};
			mos.push(obj);
		}
		//bcch
		if(gsm.bcch){
			 var ij=$.inArray(gsm.bcch.toString(),yy_b);
			   if(ij<20){
				   var obj={x:gsm.x,y:gsm.y,color:dc[ij],id:gsm.id,type:'1',va:gsm.bcch,reltype:gsm.realnet_type};
					bcch.push(obj);
		       }
		}
		
	}
	//室内轨迹点数据
	    var dataObj = new Object();
	    if(rscp.length>0){dataObj.RSCP=rscp;}
	    if(ecno.length>0){dataObj.EcNo=ecno;}
	    if(TxPower.length>0){dataObj.TXPOWER=TxPower;}
	    if(psc.length>0){dataObj.PSC=psc;}
	    if(ftpSpeed.length>0){dataObj.FTP=ftpSpeed;}

	    if(rxlev.length>0){dataObj.RXLEV=rxlev;}
	    if(rxqual.length>0){dataObj.RXQUAL=rxqual;}
	    if(ci.length>0){dataObj['C/I']=ci;}
	    if(bcch.length>0){dataObj.BCCH=bcch;}

	    var s = new Array();
	    s.push("RSCP");
	    s.push("RXLEV");
	    idooorTag["data_"+ss] = s;
	    var obj = getIdooorTagData(dataObj,s);
	    var canvas = MyCanvas(dataObj,obj,s,"map_"+ss,"container"+ss,"imageid"+ss,"extid"+ss,"naid"+ss,"max",3000,1500,winlen,"mapdiv"+ss,false,null,null,null,null,compare);
  		canvas.initPaper("data_"+ss);
  		return canvas;
}
function  getIdooorTagData(datas,vs){
	var _2G = new Object();
	var _3G = new Object();
	if(vs.length>0){
		for(var i=0;i<vs.length;i++){
//			var data = datas[ss];
			if(vs[i]=='RXLEV'||vs[i]=='RXQUAL'||vs[i]=='C/I'||vs[i]=='BCCH'){
				if(datas[vs[i]]){
					_2G[vs[i]]=datas[vs[i]];
				}
			}	
			if(vs[i]=='RSCP'|| vs[i]=='EcNo' || vs[i]=='TXPOWER' || vs[i]=='PSC' || vs[i]=='FTP'){
				if(datas[vs[i]]){
					_3G[vs[i]]=datas[vs[i]];
				}
			}	
		}
	}
	var obj = new Object();
	obj["3G"] = _3G;
	obj["2G"] = _2G;
	return obj; 
}
/***
 * 公共选择指标加载数据
 * @param wcdmalist 3g数据
 * @param gsmlist  2g数据
 * @param kpi用逗号隔开可多个','
 * @param type 0-清除所有点，1－清除2G点，2－清除3G点
 */
function chooseKpi(ss,map,wcdmalist,gsmlist,kpi,type,yy_p,yy_b){
	//清除数据点
	markerArr_2g=[];
	markerArr_3g=[];
	markerArr_2g_ex=[];
	markerArr_3g_ex=[];
	if(type==1){
		var arr=markerMap_fliwids_2g.get(ss);
		if(arr){
			for(var d=0;d<arr.length; d++){
				 map.removeOverlay(arr[d]);
		       }
		}
		markerMap_fliwids_2g.remove(ss);
		markerMap_fliwids_2g_ex.remove(ss);
	}
	if(type==2){
		var arr=markerMap_fliwids_3g.get(ss);
		if(arr){
			for(var d=0;d<arr.length; d++){
				 map.removeOverlay(arr[d]);
		       }
		}
		markerMap_fliwids_3g.remove(ss);
		markerMap_fliwids_3g_ex.remove(ss);
	}
	var pscmap = new Map();//键：PSC值，值：颜色集id
	var bcchmap=new Map();//键：PSC值，值：颜色集id
	var arr_color=[];
	for(var t=0;t<colorlist.length;t++)
	{
		var color=colorlist[t];
		arr_color.push(color.colourcode);
	}
	var colorall=[];
	  var psc_arr_color=[];
	   var arr_co_len=arr_color.length;
	   for (var j=arr_co_len-1;j>=0;j--){
		   psc_arr_color.push(arr_color[j]);
	   }
	var dc=colorall.concat(psc_arr_color,colorRoom);//两个颜色合并作为PSC颜色用
	var str=kpi.split(',');
	var wd_len=0;
	if(wcdmalist){
		wd_len=wcdmalist.length;
	}
	var gs_len=0;
	  if(gsmlist)
		{
		gs_len=gsmlist.length;
		}	
	 for(var i=0;i<wd_len;i++){
				if(wcdmalist[i].rscp>0&&$.inArray('RSCP',str)>=0){
					addMarker(map,wcdmalist[i].rscp,wcdmalist[i].id,2,new BMap.Point(wcdmalist[i].lat_modi+$.inArray('RSCP',str)/(map.getZoom()*100),wcdmalist[i].lng_modi-$.inArray('RSCP',str)/(map.getZoom()*100)),i,"RSCP",
							new BMap.Point(wcdmalist[i].lat+$.inArray('RSCP',str)/(map.getZoom()*100),wcdmalist[i].lng-$.inArray('RSCP',str)/(map.getZoom()*100)),wcdmalist[i].rscp_,wcdmalist[i].realnet_type);
				}
				if(wcdmalist[i].ecno>0&&$.inArray('EcNo',str)>=0){
					addMarker(map,wcdmalist[i].ecno,wcdmalist[i].id,2,new BMap.Point(wcdmalist[i].lat_modi+$.inArray('EcNo',str)/(map.getZoom()*100),wcdmalist[i].lng_modi-$.inArray('EcNo',str)/(map.getZoom()*100)),i,"EC/No",
							new BMap.Point(wcdmalist[i].lat+$.inArray('EcNo',str)/(map.getZoom()*100),wcdmalist[i].lng-$.inArray('EcNo',str)/(map.getZoom()*100)),wcdmalist[i].ecno_,wcdmalist[i].realnet_type);
				}
				if(wcdmalist[i].txpower>0&&$.inArray('TXPOWER',str)>=0){
					addMarker(map,wcdmalist[i].txpower,wcdmalist[i].id,2,new BMap.Point(wcdmalist[i].lat_modi+$.inArray('TXPOWER',str)/(map.getZoom()*100),wcdmalist[i].lng_modi-$.inArray('TXPOWER',str)/(map.getZoom()*100)),i,"TxPower",
							new BMap.Point(wcdmalist[i].lat+$.inArray('TXPOWER',str)/(map.getZoom()*100),wcdmalist[i].lng-$.inArray('TXPOWER',str)/(map.getZoom()*100)),wcdmalist[i].txpower_,wcdmalist[i].realnet_type);
					}
				if(wcdmalist[i].ftpSpeed>0&&$.inArray('FTP',str)>=0){
					addMarker(map,wcdmalist[i].ftpSpeed,wcdmalist[i].id,2,new BMap.Point(wcdmalist[i].lat_modi+$.inArray('FTP',str)/(map.getZoom()*100),wcdmalist[i].lng_modi-$.inArray('FTP',str)/(map.getZoom()*100)),i,"FTP",
							new BMap.Point(wcdmalist[i].lat+$.inArray('FTP',str)/(map.getZoom()*100),wcdmalist[i].lng-$.inArray('FTP',str)/(map.getZoom()*100)),wcdmalist[i].ftpSpeed_,wcdmalist[i].realnet_type);
					}
				if(wcdmalist[i].psc&&$.inArray('PSC',str)>=0)
					{
					//PSC
						 var ij=$.inArray(wcdmalist[i].psc.toString(),yy_p);
						   if(ij<20){
							   addMarker(map,dc[ij],wcdmalist[i].id,2,new BMap.Point(wcdmalist[i].lat_modi+$.inArray('PSC',str)/(map.getZoom()*100),wcdmalist[i].lng_modi-$.inArray('PSC',str)/(map.getZoom()*100)),i,"PSC",
									   new BMap.Point(wcdmalist[i].lat+$.inArray('PSC',str)/(map.getZoom()*100),wcdmalist[i].lng-$.inArray('PSC',str)/(map.getZoom()*100)),wcdmalist[i].psc,wcdmalist[i].realnet_type);
					       }
					}
		    }
			
			for(var i=0;i<gs_len;i++){
				if(gsmlist[i].rxlev>0&&$.inArray('RXLEV',str)>=0){
				addMarker(map,gsmlist[i].rxlev,gsmlist[i].id,1,new BMap.Point(gsmlist[i].lat_modi+$.inArray('RXLEV',str)/(map.getZoom()*100),gsmlist[i].lng_modi-$.inArray('RXLEV',str)/(map.getZoom()*100)),i,"Rxlev",
						new BMap.Point(gsmlist[i].lat+$.inArray('RXLEV',str)/(map.getZoom()*100),gsmlist[i].lng-$.inArray('RXLEV',str)/(map.getZoom()*100)),gsmlist[i].rxlev_,gsmlist[i].realnet_type);
				}
				if(gsmlist[i].rxqual>0&&$.inArray('RXQUAL',str)>=0){
					addMarker(map,gsmlist[i].rxqual,gsmlist[i].id,1,new BMap.Point(gsmlist[i].lat_modi+$.inArray('RXQUAL',str)/(map.getZoom()*100),gsmlist[i].lng_modi-$.inArray('RXQUAL',str)/(map.getZoom()*100)),i,"Rxqual",
							new BMap.Point(gsmlist[i].lat+$.inArray('RXQUAL',str)/(map.getZoom()*100),gsmlist[i].lng-$.inArray('RXQUAL',str)/(map.getZoom()*100)),gsmlist[i].rxqual_,gsmlist[i].realnet_type);
					}
				if(gsmlist[i].ci>0&&$.inArray('C/I',str)>=0){
					addMarker(map,gsmlist[i].ci,gsmlist[i].id,1,new BMap.Point(gsmlist[i].lat_modi+$.inArray('C/I',str)/(map.getZoom()*100),gsmlist[i].lng_modi-$.inArray('C/I',str)/(map.getZoom()*100)),i,"C/I",
							new BMap.Point(gsmlist[i].lat+$.inArray('C/I',str)/(map.getZoom()*100),gsmlist[i].lng-$.inArray('C/I',str)/(map.getZoom()*100)),gsmlist[i].ci_,gsmlist[i].realnet_type);
					}
				if(gsmlist[i].bcch&&$.inArray('BCCH',str)>=0){
					
					 var ij=$.inArray(gsmlist[i].bcch.toString(),yy_b);
					   if(ij<20){
						   addMarker(map,dc[ij],gsmlist[i].id,1,new BMap.Point(gsmlist[i].lat_modi+$.inArray('BCCH',str)/(map.getZoom()*100),gsmlist[i].lng_modi-$.inArray('BCCH',str)/(map.getZoom()*100)),i,"BCCH",
									new BMap.Point(gsmlist[i].lat+$.inArray('BCCH',str)/(map.getZoom()*100),gsmlist[i].lng-$.inArray('BCCH',str)/(map.getZoom()*100)),gsmlist[i].bcch,gsmlist[i].realnet_type);
				       }
				}
					
	    }
	
	if(type==1){
		 markerMap_fliwids_2g.put(ss,markerArr_2g);
		 markerMap_fliwids_2g_ex.put(ss,markerArr_2g_ex);
	}else{
		 markerMap_fliwids_3g.put(ss,markerArr_3g);
		 markerMap_fliwids_3g_ex.put(ss,markerArr_3g_ex);
	}
}
/***
 * 室外添加marker方法
 * @param state颜色状态
 * @param id
 * @param type类型1-2g,2-3g
 * @param location_地方——偏移后
 * @param exlocation地方-偏移前
 * @param index顺序
 * @param kpi
 * * @param intevl指标值
 * @param realnetType真实网络状态
 */

function addMarker(map,state,id,type,location_,index,kpi,exlocation,intevl,realnetType){
	var location = new BMap.Point(location_.lat,location_.lng);
	intevl=":"+intevl;
	var scale=1;
	var icon,path,marker,strokeWeight;
	if(type==2){
		//3g图标
		//path= 'M0,0 C130,75 30,25';
		path = 'w_';
		strokeWeight=1;
	}else if(type==1){
		//2G图标
		path = 'g_';
		strokeWeight=1;
	}
	//psc与bcch用的颜色不一样
	if(kpi!='PSC'&&kpi!='BCCH'){
		if(state>0){
			var fil;
			if(realnetType!=-1){
				path+=colorlist[state-1].colourcode.substring(1,colorlist[state - 1].colourcode.length);
			}else{
				path="linxin";
			}
			
			icon =new BMap.Icon(contextPath+'/images/integration/'+path+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
			 marker = new BMap.Marker(location,
					 {
					title : kpi + intevl,
					icon:icon});
			 map.addOverlay(marker);
		}
	}else{
		if(realnetType == -1){
			//无服务-菱形
			path="linxin";
			state=",";
		}
		icon =new BMap.Icon(contextPath+'/images/integration/'+path+state.substring(1,state.length)+'.png'+"?t="+new Date().getTime(),new BMap.Size(14,14), {imageSize:new BMap.Size(14,14)});
		 marker = new BMap.Marker(location,
				 {
				title : kpi + intevl,
				icon:icon});
		 map.addOverlay(marker);
	}
	 //把各自的点装入数组中
	 if(type==1){
		 markerArr_2g.push(marker);
		 markerArr_2g_ex.push(exlocation);
	 }else{
		 markerArr_3g.push(marker);
		 markerArr_3g_ex.push(exlocation);
	 }

}
function changeWindow(){
    //计算宽度高度
	var wid_len=(document.body.scrollWidth-document.body.scrollWidth*0.15)/2;
	var hie_len=((document.body.scrollWidth-document.body.scrollWidth*0.15)/2)*(9/16);
    $("#max").css("width",(wid_len+15)*2+20);
    $("#max").css("height",((wid_len+15)*2+20)*(9/16));
    $(".computer_map_div").css("width",wid_len);
    $(".svg-div-main").css("width",wid_len);
    $(".computer_map_div").css("height",hie_len+32);
    $(".svg-div-main").css("width",wid_len);
    $(".svg-div-main").css("height",hie_len);
    $(".svg-div").css("height",hie_len);
    $(".svg-div").css("width",wid_len);
    $("#parentContainer").css("height",hie_len);
    $("#parentContainer").css("width",wid_len);
    $("#container").css("height",hie_len);
    $("#container").css("width",wid_len);
    var wid=(document.body.scrollWidth-document.body.scrollWidth*0.05)/2;
    $(".histogram_flash").css("width",wid);
}