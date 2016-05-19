$(function(){
	$(".reportExcel").click(function(){
		var col=["submitDateTime","netType","dealPath","area","testDateTime",
		         "workerid","operationClassify","acceptance","clientid","customer","testTime",
		         "url"
		        ,"contentrs"
		         ,"module","serialno","indoor","roomName","room","gpsName",
		         "scene","operationType","wcdmaRatio","gsmRatio","lteRatio","noService","evaluate"];
		var value="{";
		value +="\"serialnoFlow\":\""+ $("#ss_id").text()+"\"";
		value +=",\"gps\":\""+$("span[name='exc_gps']").text()+"\"";
		value +=",\"testUrl\":\""+$("span[name='exc_testUrl']").text()+"\"";
		var vl;
		for(var i=0;i<col.length;i++){
			lv = ($("td[name=exc_"+col[i]+"]").text().replaceAll(/\s+/g, "")).replaceAll('\"', "!_!");
			lv = lv.replace(/\ +/g, ""); //去掉空格
			lv = lv.replace(/[\r\n]/g, ""); //去掉回车换行
			value +=",\""+col[i]+"\":\""+ lv +"\"";
		}
		//选中项
		value +=",\"targetType\":\""+"Rxlev"+"\"";
		//采集点数
		var aa = $("#sum_count").text();
		value +=",\"point\":\""+aa.substring(4,aa.length)+"\"";
		
		value +="}";
		
		//工单流水号
		var flowid = $("#SelectBox").combobox("getValue");
		var graph= graphList();
		//评价
		var eval_value = compEvalList();
		
		$("#background").show();
		$("#bar_id").show();
		$(document).css({
			"overflow" : "hidden"
		});
		 var ping = pingList();
		$.ajax({
			url:contextPath + "/report/reportExcel",  
            type:"post",  
            data:{value:value,graph:graph,eval:eval_value,flowid:flowid,ping:ping},  
            dataType:"json",  
            success:function(data){
            	location.href=contextPath +"/report/downLoad?name="+data;
            	
            	$("#background").hide();
        		$("#bar_id").hide();
        		$(document).css({
        			"overflow" : "auto"
        		});
            }
		});
	});
});
function pingList(){
	var pingList = ["pinglo","pingdmax","pingdmix","pingdavg","httpsmax","httpsmin","httpsavg","httptmax","httptmix","httptavg"];
	var value="{";
	value +="\"test\":\"0\"";
	for(var i=0;i<pingList.length;i++){
		value +=",\""+pingList[i]+"\":\""+ $("td[name="+pingList[i]+"]").text()+"\"";
	}
	value+="}";
	return value;
}
/**
 * 柱状图数据
 * @returns {String}
 */
function graphList(){
	var graph = "{";
	for(var i =0;i<flist_excel.length;i++){
		var flist = flist_excel[i];
		if(flist.x.length<=0||flist.y.length<=0){
			continue;
		}
		var name = flist.kpiname;
		var netType = flist.netType
		var listx = flist.x;
		var listy = flist.y;
		var x = listx.length;
		var y = listy.length;
		if(name==="BCCH"||name==="PSC"||name==="PCI"){
			for(var j = 0;j<6;j++){
				if(j<x){
					graph +="\""+name+"_x\":\""+ listx[j]+"\",";
				}else{
					graph +="\""+name+"_x\":\"\",";
				}
				if(j<y){
					var yv = listy[j].percent;
					graph +="\""+name+"_y\":\""+ yv+"\",";
				}else{
					graph +="\""+name+"_y\":\"\",";
				}
			}
		}else{
			var ftpNetName="";
			if(name=="FTP下行"||name=="FTP上行"){
				if(netType=="2"){
					ftpNetName = "3G";
				}else if(netType=="3"){
					ftpNetName = "4G";
				}
			}
			for(var j = 0;j<6;j++){
				if(j<x){
					graph +="\""+ftpNetName+name+"_x\":\""+ listx[j]+"\",";
				}else{
					graph +="\""+ftpNetName+name+"_x\":\"\",";
				}
			}

			
			if(listy[0].one!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].one+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
			
			if(listy[0].two!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].two+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
			
			if(listy[0].three!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].three+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
			
			if(listy[0].four!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].four+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
			
			if(listy[0].five!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].five+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
			
			if(listy[0].six!=""){
				graph +="\""+ftpNetName+name+"_y\":\""+listy[0].six+"\",";
			}else{
				graph +="\""+ftpNetName+name+"_y\":\"\",";
			}
		}
	}
	graph = graph.substring(0,graph.length-1);
	graph +="}";
	return graph;
}
/**
 * 评价数据
 */
function compEvalList(){
	var eval_value = "{";
	
	if(compEval_excel[0]!=null){
		eval_value +="\"gsm\":\""+ compEval_excel[1]+"\",";
		eval_value +="\"gsm_c\":\""+ compEval_excel[0]+"\",";
	}
	if(compEval_excel[2]!=null){
		eval_value +="\"wcdma\":\""+ compEval_excel[3]+"\",";
		eval_value +="\"wcdma_c\":\""+ compEval_excel[2]+"\",";
	}
	if(compEval_excel[4]!=null){
		eval_value +="\"lte\":\""+ compEval_excel[5]+"\",";
		eval_value +="\"lte_c\":\""+ compEval_excel[4]+"\",";
	}
	if(compEval_excel[0]==null&&compEval_excel[2]==null&&compEval_excel[4]==null){
		eval_value +="\"noservice\":\"无\",";
	}
	
	for(var i =0;i<flist_excel.length;i++){
		var flist = flist_excel[i];
		if(flist.gradName==null||flist.gradColor==null){
			continue;
		}
		var name = flist.kpiname;
		var netType = flist.netType;
		if(name=="FTP下行"||name=="FTP上行"){
			if(netType=="2"){
				eval_value +="\""+"3G"+flist.kpiname+"\":\""+ flist.gradName+"\",";
				eval_value +="\""+"3G"+flist.kpiname+"_c\":\""+ flist.gradColor+"\",";
			}else if(netType=="3"){
				eval_value +="\""+"4G"+flist.kpiname+"\":\""+ flist.gradName+"\",";
				eval_value +="\""+"4G"+flist.kpiname+"_c\":\""+ flist.gradColor+"\",";
			}
		}else{
			eval_value +="\""+flist.kpiname+"\":\""+ flist.gradName+"\",";
			eval_value +="\""+flist.kpiname+"_c\":\""+ flist.gradColor+"\",";
		}
	}
	eval_value = eval_value.substring(0,eval_value.length-1);
	eval_value += "}";
	return eval_value;
}