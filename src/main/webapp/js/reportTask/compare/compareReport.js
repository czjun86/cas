/**
 * 多次对比柱状图
 * @author czj
 * 
 * */ 
var colorlist;//查询出的颜色
var div_html="";
var colorRoom=
['#440067','#2e3c74','#00626d','#763d00','#770057',
 '#969800','#8b2c2c','#c6de27','#424242','#a524a3',
 '#00c5dc','#979797','#dbabce','#ff8400'];//初始化颜色装载器

var colorRoom_1=
	['#2EA01C','#0882FF','#FFC948','#B634FA'
	 ,'#FE5A4E','#ABABAB','#440067','#2e3c74',
	 '#00626d','#763d00','#770057',
	 '#969800','#8b2c2c','#c6de27','#424242','#a524a3',
	 '#00c5dc','#979797','#dbabce','#ff8400'];//初始化颜色装载器


var markerMap_fliwids_2g = new Map();//KEY:流水号;value:marker数组
var markerArr_2g=[];
var markerMap_fliwids_3g = new Map();//KEY:流水号;value:marker数组
var markerArr_3g=[];

var markerMap_fliwids_2g_ex = new Map();//KEY:流水号;value:偏移前地址数组
var markerArr_2g_ex=[];
var markerMap_fliwids_3g_ex = new Map();//KEY:流水号;value:偏移前地址数组
var markerArr_3g_ex=[];

var zNodes_map =new Map();//KEY:流水号;value:zNodes
var zNodes =[];
//树型菜单
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback:{
		
	}
};

$(window).load(function()
 {
	$(window).resize(function(){
			changeWindow();
	 });
	$(document).css({"overflow":"hidden"});
	if(flowid && flowid != "-1111"){
		getData(flowid,flowname);
	  }else{
		  $("#background").hide();
	 	  $("#bar_id").hide();
	 	  $(document).css({"overflow":"auto"});
	  }
	
 });


function getData(flowid,flowname)
{
	$("#tuli_id").click( function () { $("#tuli_ul").toggle(); });
	 //删除图div重新加载
	 $(".histogram").html("");
	 //请求数据
	   	$.ajax({
					type:"post",
					url:contextPath+"/reportTask/reportCompare",
					data:({flowid :flowid,areaid:$("#areaid").val()}),
					success:function(data){
							var flist=data.flashlist;
							colorlist=data.colorlist;
						       //查询轨迹点数据
						    var gsmlist= data.gsmlist;
						    var wcdmalist=data.wcdmalist;
						    var center=data.center;
						    queryPoint(gsmlist,wcdmalist,center,flist,flowid,flowname);
						    
							var wid_len=(document.body.scrollWidth-document.body.scrollWidth*0.05)/2;

							for(var i=0;i<flist.length;i++)
							{
							  var xlist=flist[i].x;      //X轴数据
							  var indoor_x=flist[i].indoor_x;//室内X轴
							  var ylist=flist[i].y;      //Y轴数据
							  var ftp=flist[i].ftp;	     //ftp上下行数据
							
						    //柱状图
						    if(ylist.length>0){
								  //排除为ci,mos,2g_txpower
								  if(flist[i].kpiId<=8||(flist[i].kpiId>=20&&flist[i].kpiId<22)){
									  var tname = flist[i].kpiname;
										if(flist[i].kpiId == 8){
											tname = "C/I";
										}
									  var idname=tname+i;
								
									    if(flist[i].kpiId!=4&&flist[i].kpiId!=5)
										  {
										     $("#histogram1").append("<div class='histogram_flash' id='"+idname+"'  style='background:url(../../img/bb.png) no-repeat;width:"+wid_len+"px;margin:0 auto;position: relative;'></div>");
										      creatFlash(indoor_x,xlist,ylist,idname,null,colorlist, flist[i].kpiname);
										  }else{
											  //FTP柱状图
											  $("#histogram2").append("<div class='histogram_flash' id='"+idname+"'  style='background:url(../../img/bb.png) no-repeat;width:"+wid_len+"px;margin:0 auto;position: relative;'></div>");
										      creatFlash(indoor_x,xlist,ylist,idname,null,colorlist, flist[i].kpiname);
										      //FTP曲线图
										      if(ftp&&ftp.length>0){
										    	  $("#histogram2").append("<div class='histogram_flash' id='"+idname+"2'  style='background:url(../../img/bb.png) no-repeat;width:"+wid_len+"px;margin:0 auto;position: relative;'></div>");
											      creatFlash(indoor_x,xlist,ylist,idname+"2",ftp,colorlist, "");
										      }
									    	  
									      }
									   
								      }	
								 
								  }
							} 
							  $("#background").hide();
						 	  $("#bar_id").hide();
						 	  $(document).css({"overflow":"auto"});
						 	
						 	 
					}
				});		
}


/***
 * 创建柱状图
 * @param xlist x轴数据
 * @param ylist Y轴数据
 * @param kpiname  指标名称
 * @param ftp    ftp上下行数据
 * @param colorlist 颜色集合
 */
 function creatFlash(indoor_x,xlist,ylist,kpiname,ftp,colorlist, title)
 {

	var arr_color=[];
	for(var t=0;t<colorlist.length;t++)
	{
		var color=colorlist[t];
		arr_color.push(color.colourcode);
	}
	var tempData = [
		{ color: arr_color[5], y: 0 },
		{ color: arr_color[4], y: 0 },
		{ color: arr_color[3], y: 0 },
		{ color: arr_color[2], y: 0 },
		{ color: arr_color[1], y: 0 },
		{ color: arr_color[0], y: 0 }
	];

	
	//如果是ftp在x轴补空
	 if(ftp){
		 for(var i=0;i<ftp.length;i++){
			 xlist.push("");
		 }
	 }
	   //X轴3,7,22倒序
	 var xlist_desc=[],xlist_desc_in=[];
	 if(ylist[0].kpi=='3'||ylist[0].kpi=='7'||ylist[0].kpi=='22')
		{
		 for(var i=xlist.length-1;i>=0;i--){
			 if(i==xlist.length-1){
				 xlist_desc.push("室外:<"+xlist[i]+"");
				 xlist_desc_in.push("室内:<"+indoor_x[i]+"");
			 }
			 else if(i==0){
				 xlist_desc.push(">="+xlist[i]+"");
				 xlist_desc_in.push(">="+indoor_x[i]+"");
			 }else{
				 xlist_desc.push("["+xlist[i]+")");
				 xlist_desc_in.push("["+indoor_x[i]+")");
			 }
		 }
		}else{
			 for(var i=0;i<xlist.length;i++){
				 if(i==xlist.length-1){
				     xlist_desc.push(">="+xlist[i]+"");
				     xlist_desc_in.push(">="+indoor_x[i]+"");
				 }
				 else if(i==0){
					 xlist_desc.push("室外:<"+xlist[i]+"");
					 xlist_desc_in.push("室内:<"+indoor_x[i]+"");
				 }else{
					 xlist_desc.push("["+xlist[i]+")");
					 xlist_desc_in.push("["+indoor_x[i]+")");
				 }
			 }
		}
	var options = {
		max: 100,
		chart: { renderTo:kpiname, backgroundColor: '#f0f0f0',plotBackgroundImage: '../images/bb.png' ,marginTop:50,marginBottom:50},
		title: { text: title,y:10,x:-10,align:'left'},
		subtitle: { useHTML: true, x: -15,y:55},
		xAxis: [{categories:xlist_desc}]
	};

	if(ylist[0].kpi!='20'&&ylist[0].kpi!='21'&&!ftp)
	{
		var attr_num=[];
		options.xAxis[1]={ categories:xlist_desc_in,labels: {style: {color:'#020202'}},offset: 20};
	    var arr=new Array();
		for(var i=0;i<ylist.length;i++)
		{
			 //加入对比时柱状图字体颜色
			var ca={},ct="";
			    	$(".computer_map_div").find("p").each(function(){
			    	    if((this).id.split("_")[1].replace(/^\s\s*/, '').replace(/\s\s*$/, '')==ylist[i].flowid){
			    	    	ct=$(this).css("color");
			    	    	ca.color=ct;
			    	    }
			    	  });
				      var sum=(ylist[i].six*100+ylist[i].five*100+ylist[i].four*100+ylist[i].three*100+ylist[i].two*100+ylist[i].one*100)/100;
					  if(sum>100||sum<100){
						  var intv=[];
						  intv.push(ylist[i].one); intv.push(ylist[i].two);
						  intv.push(ylist[i].three); intv.push(ylist[i].four);
						  intv.push(ylist[i].five); intv.push(ylist[i].six);
						for(var h=0;h<intv.length;h++){
							if(Math.max.apply(null,intv)==intv[h]){
								
							switch (h) {
							case 0:
								if(sum>100)
									ylist[i].one=(parseInt(ylist[i].one*100-1))/100;
								if(sum<100)	
									ylist[i].one=(parseInt(ylist[i].one*100+1))/100;
								break;
							case 1:
								if(sum>100)
									ylist[i].two=(parseInt(ylist[i].two*100-1))/100;
								if(sum<100)	
									ylist[i].two=(parseInt(ylist[i].two*100+1))/100;
								break;
							case 2:
								if(sum>100)
									ylist[i].three=(parseInt(ylist[i].three*100-1))/100;
								if(sum<100)	
									ylist[i].three=(parseInt(ylist[i].three*100+1))/100;
								break;
							case 3:
								if(sum>100)
									ylist[i].four=(parseInt(ylist[i].four*100-1))/100;
								if(sum<100)	
									ylist[i].four=(parseInt(ylist[i].four*100+1))/100;
								break;
							case 4:
								if(sum>100)
									ylist[i].five=(parseInt(ylist[i].five*100-1))/100;
								if(sum<100)	
									ylist[i].five=(parseInt(ylist[i].five*100+1))/100;
								break;
							case 5:
								if(sum>100)
									ylist[i].six=(parseInt(ylist[i].six*100-1))/100;
								if(sum<100)	
									ylist[i].six=(parseInt(ylist[i].six*100+1))/100;
								break;
							default:
								break;
							}
						}}
					  }
					  
			 var obj={},x_a=0; 
			 if(ylist[i].inside==0){
				 x_a=0;
				 if($.inArray(0,attr_num)==-1){
					 attr_num.push(0);
				 }
			 }else{
				 x_a=1;
				 if($.inArray(1,attr_num)==-1){
					 attr_num.push(1);
				 }
			 }
			
	    	 if(ylist[0].kpi=='3'||ylist[0].kpi=='7'||ylist[0].kpi=='22'){
				  obj={name:'', type:'column',xAxis:x_a,data:
						 [
				//按数值从小到大
										{ color: ca.color, y: parseFloat(ylist[i].six),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].five),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].four),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].three),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].two) ,dataLabels:ca},
										{ color: ca.color, y: parseFloat(ylist[i].one),dataLabels:ca}
										
						 ]}; 
			  }else{
				  obj={name:'', type:'column',xAxis:x_a,data:
						 [
				//按数值从小到大
										{ color: ca.color, y: parseFloat(ylist[i].one),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].two),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].three),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].four),dataLabels:ca },
										{ color: ca.color, y: parseFloat(ylist[i].five) ,dataLabels:ca},
										{ color: ca.color, y: parseFloat(ylist[i].six),dataLabels:ca}
										
						 ]}; 
			  }
		 arr.push(obj);
		}
		//如果只有一条记录采用上面个坐标
		if(attr_num.length==1){
			for(var a=0;a<arr.length;a++){

				arr[a].xAxis=0;
			}
			var mmst=ylist[0].inside==0?xlist_desc:xlist_desc_in;
			options.xAxis[0]={ categories:mmst};
		}
	    options.series=arr;
	}else{
//PSC、BCCH
		if(!ftp){
		var xxbhMap={};  
        var len = ylist.length;  
        for(var i=0;i<len;i++){  
            if(xxbhMap[ylist[i].flowid] == undefined){  
                var list = [];  
                list.push(ylist[i]);  
                xxbhMap[ylist[i].flowid] = list;  
            }else{  
                xxbhMap[ylist[i].flowid].push(ylist[i]);  
            }  
        }  
        var arrp=new Array();
		for(ss in xxbhMap)
		{
		  var st=xxbhMap[ss];
		  var arrt=new Array();
		  var obj={name:'', type:'column'}; 
		  //加入对比时柱状图字体颜色
		  var ca={},ct="";
		    	$(".computer_map_div").find("p").each(function(){
		    	    if((this).id.split("_")[1].replace(/^\s\s*/, '').replace(/\s\s*$/, '')==ss){
		    	    	ct=$(this).css("color");
		    	    	ca.color=ct;
		    	    }
		    	  });
		   //颜色顺序
		   var psc_arr_color=[];
		   
		   for (var j=arr_color.length-1;j>=0;j--){
			   psc_arr_color.push(arr_color[j]);
		   }
		   var pscbchlist=[];
		   for(var i = 0;i < st.length;i++)
		   {
			   var cc;
			   var ij=$.inArray(st[i].pscbcch,xlist);
             //判断值在X轴的位置取对应的颜色
				   if(ij<psc_arr_color.length){
			    	   cc=psc_arr_color[ij];
			      }else{
			    	  cc=colorRoom[ij-psc_arr_color.length];
			      }
			     var cc1={ color: ca.color, y: parseFloat(st[i].percent),dataLabels:ca};
		        //放在对应的X数据里
		    	  arrt[ij]=cc1;
		      //判断值如果值在X轴不存在补空值取色
		      pscbchlist.push(st[i].pscbcch);
			      if(i==st.length-1){
			    	  for(var j=0;j<xlist.length;j++){
				    	  if($.inArray(xlist[j],pscbchlist)==-1){
				    		  var cc1={ color: '', y: parseFloat(0.0),dataLabels:ca};
				    		  arrt[j]=cc1;
				    	  }
				      }
			      }
		   }
          obj.data=arrt;
          obj.xAxis=0;
		  arrp.push(obj);
		}
		 options.series=arrp;
	   //补图
	    var datas = options.series[0].data;
		var len = tempData.length, i=0;
		for(i=0;i<len;i++){
			if(!datas[i]){ datas.push(tempData[i]);xlist.push("");}else{
			}
		}
		options.xAxis[0].categories=xlist;
		options.tooltip={formatter: function() {
			if(this.x==''){
				this.x=0;
			}
			return " "+this.x+": "+this.y;
		}};
//		options.plotOptions={xAxis: { categories: xlist}, column: { dataLabels: { formatter: function() {
//			var result = Math.round((this.y/100)*100);
//			return result ? result+'' : '';
//		} } } };
		options.subtitle= { useHTML: true,text: ''};
	}
		//ftp
		 if(ftp&&ftp.length>0){
	    //判断FTP上行或下行曲线图
		 if(ylist[0].kpi=='4'||ylist[0].kpi=='5')
		 { 
		       var xxbhMap={};  
		        var len = ftp.length;  
		        for(var i=0;i<len;i++){  
		          
		            if(xxbhMap[ftp[i].flowid] == undefined){  
		                var list = [];  
		                list.push(ftp[i]);  
		                xxbhMap[ftp[i].flowid] = list;  
		            }else{  
		                xxbhMap[ftp[i].flowid].push(ftp[i]);  
		            }  
		        }  
		        var arrp=new Array();
		        var arrx=new Array();
				for(ss in xxbhMap)
				{
				 var color="";
				 $(".computer_map_div").find("p").each(function(){
			    	    if((this).id.split("_")[1].replace(/^\s\s*/, '').replace(/\s\s*$/, '')==ss){
			    	    	color=$(this).css("color");
			    	    }
			    	  });
				  var st=xxbhMap[ss];
				  var arrt=new Array();
				  var obj1={name:'Rainfall', type:'spline',color: color,yAxis: 1}; 
				   for(var i = 0;i < st.length;i++)
				   {
					 if(st[i].percent!=-9998){
					     var cc=parseFloat(st[i].percent);
					       arrt.push(cc);
					       arrx.push(""); 
					 } else{
						   arrt.push(null);
					       arrx.push(""); 
					 } 
				   }
		          obj1.data=arrt;
		          arrp.push(obj1);
				}
				options.series=arrp;
				options.xAxis[0]={categories: arrx};
				options.subtitle={ useHTML: true,text: '', x: -20,y:100};
		 }
		 }
	}
	options.plotOptions={ column: { dataLabels: {enabled: false, formatter: function() {
		var result = Math.round((this.y/100)*100);
		return result ? result+'' : '';
	} } } };
	options.xAxis[0].labels={style: {color:'#020202'}};
	var reportForms = new ReportForms();
	reportForms.Create(options);
 }
