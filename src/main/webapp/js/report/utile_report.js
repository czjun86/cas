/**
 * 树形菜单中选择流水跳转
 * 单次、多次图例
 * @author czj
 * 
 * */ 
 //流水号树形框
function span_choose()
{ 
	$(".easyui-linkbutton").show();
	var dd;
	if(!$("#SelectBox").val()){
		dd=$("#up_flid").val();
	}else{
		dd=$("#SelectBox").combobox("getValue");
	}
	var areaid=$("#areaid").val();
	var s_id=$("#s_id").val();
	var src=contextPath+"/report/findFlowid?serialno="+$("#ss_id").html()+"&flowid="+dd+"&areaid="+areaid+"&s_id="+s_id;
	 //src='<iframe src='+src+' id="report" style="width:100%;height:100%;" frameborder="0"></iframe>';

	$("#win").dialog({
		href:src,
		height: 500,width: 480,title: "测试流水选择",
		modal: true,closed:false
	});
	$('#win').dialog('open');
	$.parser.parse('#win');

	$(".treebtn").click(function(){
		var nodes = $('#win').tree('getChecked');
		var s = '',str='',s_na='',ss_name="";
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
			if (s_na != '') s_na += ',';
			s_na+=nodes[i].text;
		}
		window.location.href=contextPath+"/report/reportCompare?flowid="+s+"&flowname="+s_na+"&serialno="+$("#ss_id").html()+"&areaid="+areaid+"&s_id="+s_id;
	});
}

//图例比例显示
var flay=true;

function choose_demo(flowid,flist,sel1,sel2,sel3,sel1_n,sel2_n,sel3_n,div_name,wcdmalist,gsmlist,ltelist){  
    	   var demo_div_2g=[]; 
    	   var demo_div_3g=[],demo_div_4g=[]; 
    	   $(div_name).html('');
    	   var xlist_out=[],xlist=[];      //X轴数据
    	   var xlist_in=[];      //室内X轴数据
		   var ylist=[];      //Y轴数据
		   for(var i=0;i<flist.length;i++){
			   if((flist[i].kpiId==4||flist[i].kpiId==5)&&sel3==71)sel3=4;
			   if((flist[i].kpiId==parseInt(sel2)&&flist[i].netType=='2')||
					   flist[i].kpiId==parseInt(sel1)||
					   (flist[i].kpiId==parseInt(sel3)&&flist[i].netType=='3')||
					   (flist[i].kpiId==5&&((flist[i].netType=='2'&&parseInt(sel2)==4)||(flist[i].netType=='3'&&parseInt(sel3)==4))))
				 {
				   var demo_div=[]; 
				    xlist_out=flist[i].x;      //X轴数据
				    xlist_in=flist[i].indoor_x; //室内X轴数据  
				    ylist=flist[i].y;      //Y轴数据
				    var yy=[];
				    var inside=0;
				    for(var t=0;t<ylist.length;t++){
				    	if(ylist[t].flowid==flowid){
				    		yy.push(ylist[t]);
				    		if(yy[0].inside==0){
					    		inside=0;
					    		xlist=xlist_out;
					    	}else{
					    		inside=1;
					    		xlist=xlist_in;
					    	}
				    	}
				    }
				    var x_list=[];
					   for(var t=0;t<xlist.length;t++){
						   if(xlist[t]!=""){
							   x_list.push(xlist[t]); 
						   }
					   }
					   var test_span="", img="";
					   if(yy.length>0){
					   if(flist[i].kpiId==parseInt(sel1)){
						   test_span=sel1_n;
						   img="▼";
						  // no_s_rato=no_s_rato_g;
					   }else if(flist[i].netType=='2'&&flist[i].kpiId==parseInt(sel2)){
						   test_span=sel2_n;
						   img="●";
						 //  no_s_rato=no_s_rato_w;
					   }else if(flist[i].netType=='3'&&flist[i].kpiId==parseInt(sel3)){
						   test_span=sel3_n;
						   img="■";
						 //  no_s_rato=no_s_rato_w;
					   }
					   else if(flist[i].netType=='2'&&(flist[i].kpiId==5&&parseInt(sel2)==4)){
						   test_span=sel2_n;
						   img="●";
					   } else if(flist[i].netType=='3'&&(flist[i].kpiId==5&&parseInt(sel3)==4)){
						   test_span=sel3_n;
						   img="■";
					   }
					   demo_div.push('<div class="case_tu_one">');
					   demo_div.push('<span style="padding-left:2px;margin-left:20px;position:relative;float:left;top:-20px">'+test_span+'</span>');
					   demo_div.push('<div class="clear"></div>');
					   demo_div.push('<ul>');
					   for (var j=0;j<x_list.length;j++){
						   if(x_list[j]!=""&&yy.length>0){
							   if(flist[i].kpiId!='20'&&flist[i].kpiId!='21'&&flist[i].kpiId!='26'){
								   demo_div.push('<li><span><font style="color:'+colorlist[j].colourcode+'">'+img+'</font></span>');
								   if(flist[i].kpiId=='3'||flist[i].kpiId=='7'||flist[i].kpiId=='22'){
									   if(j==x_list.length-1){
										     demo_div.push('<pre style="float: left;"><'+x_list[j]+'</pre>');
										 }
										 else if(j==0){
											 demo_div.push('<pre style="float: left;">>='+x_list[j]+'</pre>');
										 }else{
											 demo_div.push('<pre style="float: left;">['+x_list[j]+')</pre>');
										 }
								   }else{
									     if(j==x_list.length-1){
										     demo_div.push('<pre style="float: left;">>='+x_list[j]+'</pre>');
										 }
										 else if(j==0){
											 demo_div.push('<pre style="float: left;"><'+x_list[j]+'</pre>');
										 }else{
											 demo_div.push('<pre style="float: left;">['+x_list[j]+')</pre>');
										 }
									     
								   }
								  
								   var sum=(yy[0].six*100
								    		  +yy[0].five*100+
								    		  yy[0].four*100+
								    		  yy[0].three*100+
								    		  yy[0].two*100+yy[0].one*100)/100;
									  if(sum>100||sum<100){
										  var intv=[];
										  intv.push(yy[0].one); intv.push(yy[0].two);
										  intv.push(yy[0].three); intv.push(yy[0].four);
										  intv.push(yy[0].five); intv.push(yy[0].six);
										for(var h=0;h<intv.length;h++){
											if(Math.max.apply(null,intv)==intv[h]){
											switch (h) {
											case 0:
												if(sum>100)
													yy[0].one=(parseInt(yy[0].one*100-1))/100;
												if(sum<100)	
													yy[0].one=(parseInt(yy[0].one*100+1))/100;
												break;
											case 1:
												if(sum>100)
													yy[0].two=(parseInt(yy[0].two*100-1))/100;
												if(sum<100)	
													yy[0].two=(parseInt(yy[0].two*100+1))/100;
												break;
											case 2:
												if(sum>100)
													yy[0].three=(parseInt(yy[0].three*100-1))/100;
												if(sum<100)	
													yy[0].three=(parseInt(yy[0].three*100+1))/100;
												break;
											case 3:
												if(sum>100)
													yy[0].four=(parseInt(yy[0].four*100-1))/100;
												if(sum<100)	
													yy[0].four=(parseInt(yy[0].four*100+1))/100;
												break;
											case 4:
												if(sum>100)
													yy[0].five=(parseInt(yy[0].five*100-1))/100;
												if(sum<100)	
													yy[0].five=(parseInt(yy[0].five*100+1))/100;
												break;
											case 5:
												if(sum>100)
													yy[0].six=(parseInt(yy[0].six*100-1))/100;
												if(sum<100)	
													yy[0].six=(parseInt(yy[0].six*100+1))/100;
												break;
											default:
												break;
											}
										}}
									  }
								   if(j==0){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].one+'%</li></pre>');}
								   if(j==1){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].two+'%</li></pre>');}
								   if(j==2){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].three+'%</li></pre>');}
								   if(j==3){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].four+'%</li></pre>');}
								   if(j==4){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].five+'%</li></pre>');}
								   if(j==5){demo_div.push('<pre class="case_tu_one_yyy">'+yy[0].six+'%</li></pre>');}
							   }
						   }
					   }
					   if(flist[i].kpiId==20||flist[i].kpiId==21||flist[i].kpiId==26){
						  // PSC
						   for(var x = 0;x <yy.length;x++){
								   if(x<6){
									   demo_div.push('<li><span><font style="color:'+colorlist[(colorlist.length-1)-x].colourcode+'">'+img+'</font></span>');
								   }else{
									   demo_div.push('<li><span><font style="color:'+colorRoom[x-colorlist.length]+'">'+img+'</font></span>');
								   }
								   demo_div.push('<pre style="float: left;">'+x_list[$.inArray(yy[x].pscbcch,xlist)]+'</pre>');
								   demo_div.push('<pre class="case_tu_one_yyy"> '+yy[x].percent+'%</li></pre>');
						   }
					   }
				       demo_div.push('<li><span><font size="2">◆无服务</font></span>');
					   demo_div.push('</ul>');
					   demo_div.push('</div>');
					   if(sel1>0&&flist[i].kpiId==parseInt(sel1)){
						   demo_div_2g=demo_div;
					   }else if(sel2>0&&flist[i].netType=='2'&&(flist[i].kpiId==parseInt(sel2)||flist[i].kpiId==5)){
						 //  console.log(1)
						   demo_div_3g=demo_div;
					   }else if(sel3>0&&flist[i].netType=='3'&&(flist[i].kpiId==parseInt(sel3)||flist[i].kpiId==5)){
						//   console.log(2)
						   demo_div_4g=demo_div;
					   }
				    }
			   }
		   }    
		  var c= demo_div_2g.concat(demo_div_3g).concat(demo_div_4g);
		   $(div_name).html($(c.join('\r\n')));
		   
		   if(!$(div_name).html()){
			   $(div_name).html("<div></div>");
		   }
}