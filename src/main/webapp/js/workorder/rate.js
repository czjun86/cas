$(function(){
	$(".easyui-combobox").combobox({
		onSelect: function(){
			var oldval = $(this).attr("oldval");
			var newval = $(this).combobox('getValue');
			var text = "";
			switch(parseInt(newval)){
				case 1:
					text = ">";
					break;
				case 2:
					text = "≥";
					break;
				case 3:
					text = "<";
					break;
				case 4:
					text = "≤";
					break;
			}
			$(this).combobox("setText",text);
			var isc = $(this).attr("isc");
			if(oldval != newval && isc ==0){
				isChange ++;
				$(this).attr("isc",1);
			}else{
				if(oldval == newval && isc==1){
					isChange --;
					$(this).attr("isc",0);
				}
			}
		},
		onBeforeLoad:function(){
			var text = $(this).combobox("getText");
			if(text == "&lt;"){
				text = $(this).combobox("setText","<");
			}
			if(text == "&gt;"){
				text = $(this).combobox("setText",">");
			}
		}
	});

	//给FTP平局速度的最大值不全正无穷符号
	$(".avgMax").each(function(){
		//失去焦点，检查为没有","直接添加
		$(this).live('blur',function(){
			//先获得一次对象的验证结果，为防止从错误格式快速改成正确又进行失去焦点事件，导致的处理完还有格式不正确的图表
			if($(this).validatebox("isValid")){
				var b = $(this).val();
				var a = addMaxValue(b);
				$(this).val(a);
				//增加正无穷后，判定与原来数据时候改变，修改判定改变的ischange和isc
				if(a!=b){
					var oldval = $(this).attr("oldval");
					var isc = $(this).attr("isc");
					if(oldval != a && isc ==0){
						
					}else{
						if(oldval == a && isc==1){
							isChange --;
							$(this).attr("isc",0);
						}
					}
				}
			}
		});
	});
});
	//自动添加+∞符号
	function addMaxValue(a){
		if(a.split(",").length==1){
			var b = a.substring(0,a.length-1)+",+∞)";
			a = b;
		}
		return a;
	}
	//给予没有输入两个数值的平均速率添加+∞
	function addMax(a){
		if(a.split(",")==1){
			var b = a.substring(0,a.split(",").length-1)+",+∞)";
			return b;
		}else{
			return a;
		}
	}
	function validate(){
		//验证rxlev，rxqual，rscp，ecno，txpower
		var type = ["rxlev","rxqual","ci","rscp","ecno","txpower","rsrp","rsrq","sinr"];
		for(var i=0;i<type.length;i++){
			if(!evalVerify(type[i],"outside")){
				return false;
			}
		}
		for(var i=0;i<type.length;i++){
			if(!evalVerify(type[i],"inside")){
				return false;
			}
		}
		//验证ftp
		var direct =["up","up","up","up","down","down","down","down"];
		var avg =["Value","Avg","Value","Avg","Value","Avg","Value","Avg"];
		var scene = ["Outside","Outside","Inside","Inside","Outside","Outside","Inside","Inside"];
		for(var i = 0;i<direct.length;i++){
			if(!evalftpVerify("ftp",direct[i],avg[i],scene[i])){
				return false;
			}
		}
		return true;
	}
	
/**
 * 自检区间值左边数值是否小于右边数字
 * @param a
 * @returns {Boolean}
 */
	function validateMyself(a){
		var num =a.split(",");
		var left = num[0].substring(1,num[0].length);
		var right;
		
		var leftSign="";
		var rightSign="";
		if(num.length>1){
			leftSign = num[0].substring(0,1);
			rightSign = num[1].substring(num[1].length-1,num[1].length);
		}
		if(num[1]==="+∞)"){
			 right = 999999;
		}else{
			 right = num[1].substring(0,num[1].length-1);
		}
		if(leftSign ==="[" &&rightSign === "]"){
			if(parseInt(left)<=parseInt(right)){
				return true;
			}else{
				return false;
			}
		}else{
			if(parseInt(left)<parseInt(right)){
				return true;
			}else{
				return false;
			}
		}
	}
	
/**
 * 比较相邻区间两个值是否是符号类型不同，数值相同
 * @param a
 * @param b
 * @returns {Boolean}
 */
	function validateInterval(a,b){
		//每个参数为[x,y]形式
		var num1 = a.split(","); 
		var num2 = b.split(",");
		
		var sn1 = num1[0].substring(1,num1[0].length);
		var sn2 = num2[1].substring(0,num2[1].length-1);
		
		var sign1= num1[0].substring(0,1);
		var sign2= num2[1].substring(num2[1].length-1,num2[1].length);
		var sign3;
		if(sign1==="("){
				sign3=")";
			}else{
				sign3="]";
		}
		if(sign2!=sign3 && parseInt(sn1)==parseInt(sn2)){
			return true;
		}else{
			return false;
		}
	}
	
/**
 * 不填右变数值的区间值转化为右边为最大值
 */
	function changeNoRight(a){
		if(a.split(",").length==1){
			var b = a.substring(0,a.length-1)+",+∞)";
			a = b;
		}
		return a;
	}

/**
*判断是否修改过
*/
function isChangeVal(arg){
	var oldval = $(arg).attr("oldval");
	var newval = $(arg).val();
	var isc = $(arg).attr("isc");
	if(oldval != newval && isc ==0){
		isChange ++;
		$(arg).attr("isc",1);
	}else{
		if(oldval == newval && isc==1){
			isChange --;
			$(arg).attr("isc",0);
		}
	}
}
/**
*区间值交接如果数字相同则，符号不同
*/
function intervalJudge(a,b){
	var c = b.split(",")[1];
	var maxnum = a.substring(1,a.split(",")[0].length);
	var minnum = c.substring(0,c.length-1);
	if(parseInt(maxnum) < parseInt(minnum)){
		return false;
	}else{
		if(parseInt(maxnum) == parseInt(minnum)){
			var tempSign = a.substring(0,1);
			var maxSign="";
			if(tempSign=="("){
				var maxSign = ")";
			}
			if(tempSign=="["){
				var maxSign = "]";
			}
			
			var minSign = b.substring(b.length-1,b.length);
			if(maxSign==minSign){
				return false;
			}
		}
	}
	return true;
}
/**
*判断条件相等时进行区间值判断
*/
function termJudge(type,scene,typeId){
	var typeValue1 = $("#"+typeId+1).val();
	var typeValue2 = $("#"+typeId+2).val();
	var typeValue3 = $("#"+typeId+4).val();
	var ari1 = $($("#"+type+"_arithmetic_"+scene+"1").next()[0]).find("input[name="+type+'_arithmetic'+"]").val();
	var val1 = $("#"+type+"_value_"+scene+"1").val();
	var ari2 = $($("#"+type+"_arithmetic_"+scene+"2").next()[0]).find("input[name="+type+'_arithmetic'+"]").val();
	var val2 = $("#"+type+"_value_"+scene+"2").val();
	var ari4 = $($("#"+type+"_arithmetic_"+scene+"4").next()[0]).find("input[name="+type+'_arithmetic'+"]").val();
	var val4 = $("#"+type+"_value_"+scene+"4").val();
	var sceneName;
	if(scene=="outside"){
		sceneName="室外";
	}
	if(scene=="inside"){
		sceneName="室内";
	}
	//FTP和其他数据相反是由小到大所以需要判定一次数据类型
	if(parseInt(ari1)==parseInt(ari2)&&parseInt(val1)==parseInt(val2)){
		if(type!="ftpup"&&type!="ftpdown"){
			var realTypeName="";
			var temp = type.substring(0,1);
			if(temp=="r" && temp!="rsrp" && temp!="rsrq"){
				realTypeName="R"+type.substring(1,type.length);
			}else if(temp=="e"){
				realTypeName="Ec/No";
			}else if(temp=="t"){
				realTypeName="TxPower";
			}else if(temp=="c"){
				realTypeName="C/I";
			}else if(temp=="rsrp"){
				realTypeName="RSRP";
			}else if(temp=="rsrq"){
				realTypeName="RSRQ";
			}else if(temp=="sinr"){
				realTypeName="SINR";
			}
			
			
			if(intervalJudge(typeValue1,typeValue2)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+1);
					addWrong(typeId+1,typeId+2);
				});
				return false;
			}
		}else{
			var realTypeName="";
			if(type=="ftpup"){
				realTypeName="Ftp上行";
			}
			if(type=="ftpdown"){
				realTypeName="Ftp下行";
			}
			if(intervalJudge(typeValue2,typeValue1)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+1);
					addWrong(typeId+1,typeId+2);
				});
				return false;
			}
		}
	}
	if(parseInt(ari2)==parseInt(ari4)&&parseInt(val2)==parseInt(val4)){
		if(type!="ftpup"&&type!="ftpdown"){
			var realTypeName="";
			var temp = type.substring(0,1);
			if(temp=="r"){
				realTypeName="R"+type.substring(1,type.length);
			}else if(temp=="e"){
				realTypeName="Ec/No";
			}else if(temp=="t"){
				realTypeName="TxPower";
			}else if(temp=="c"){
				realTypeName="C/I";
			}
			
			
			if(intervalJudge(typeValue2,typeValue3)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+2);
					addWrong(typeId+2,typeId+4);
				});
				return false;
			}
		}else{
			var realTypeName="";
			if(type=="ftpup"){
				realTypeName="Ftp上行";
			}
			if(type=="ftpdown"){
				realTypeName="Ftp下行";
			}
			if(intervalJudge(typeValue3,typeValue2)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+2);
					addWrong(typeId+2,typeId+4);
				});
				return false;
			}
		}
	}
	if(parseInt(ari1)==parseInt(ari4)&&parseInt(val1)==parseInt(val4)){
		if(type!="ftpup"&&type!="ftpdown"){
			var realTypeName="";
			var temp = type.substring(0,1);
			if(temp=="r"){
				realTypeName="R"+type.substring(1,type.length);
			}else if(temp=="e"){
				realTypeName="Ec/No";
			}else if(temp=="t"){
				realTypeName="TxPower";
			}else if(temp=="c"){
				realTypeName="C/I";
			}
			
			
			if(intervalJudge(typeValue1,typeValue3)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+1);
					addWrong(typeId+1,typeId+4);
				});
				return false;
			}
		}else{
			var realTypeName="";
			if(type=="ftpup"){
				realTypeName="Ftp上行";
			}else if(type=="ftpdown"){
				realTypeName="Ftp下行";
			}
			
			
			if(intervalJudge(typeValue3,typeValue1)){
				
			}else{
				$.messager.alert("警告",sceneName+realTypeName+"条件相同时,区间值不能同时属于两个等级","warning",function(){
					moveScroll(typeId+1);
					addWrong(typeId+1,typeId+4);
				});
				return false;
			}
		}
	}
	return true;
}

/**
*对rxlev，rxqual，ci，rscp，ecno，Txpower验证
*/
function evalVerify(type,scene){
	var upperScene = scene.substring(0,1).toUpperCase()+scene.substring(1,scene.length);
	var sceneName = null;
	if(scene=="outside"){
		sceneName="室外";
	}else if(scene=="inside"){
		sceneName="室内";
	}
	var a = document.getElementById(type+upperScene+"1").value;
	var b = document.getElementById(type+upperScene+"2").value;
	var c = document.getElementById(type+upperScene+"4").value;
	if(validateMyself(a)&&validateMyself(b)&&validateMyself(c)){
		if(termJudge(type,scene,type+upperScene)){
			
		}else{
			return false;
		}
	}else{
		$.messager.alert("警告",sceneName+type+"区间值应该从小到大","warning",function(){
			if(!validateMyself(a)){
				moveScroll(type+upperScene+"1");
				addWrong(type+upperScene+"1",null);
			}else if(!validateMyself(b)){
				moveScroll(type+upperScene+"2");
				addWrong(type+upperScene+"2",null);
			}else if(!validateMyself(c)){
				moveScroll(type+upperScene+"4");
				addWrong(type+upperScene+"4",null);
			}
		});
		return false;
	}
	return true;
}
/**
*对ftp数据进行验证
**/
function evalftpVerify(type,direct,avg,scene){
	var sceneName;
		if(scene=="Inside"){
			sceneName="室内";
		}
		else if(scene=="Outside"){
			sceneName="室外";
		}
	var directName;
		if(direct=="up"){
			directName="上行";
		}else if(direct=="down"){
			directName="下行";
		}
	var typeName;
	if(avg=="Value"){
		typeName="平均速度";
		var a = document.getElementById(type+direct+avg+scene+"1").value;
		var b = document.getElementById(type+direct+avg+scene+"2").value;
		var c = document.getElementById(type+direct+avg+scene+"4").value;
		if(validateMyself(a)&&validateMyself(b)&&validateMyself(c)){
			if(termJudge(type+direct,scene.substring(0,1).toLowerCase()+scene.substring(1,scene.length),type+direct+avg+scene)){
				
			}else{
				return false;
			}
		}else{
			$.messager.alert("警告",sceneName+type+directName+"占比区间值，应该从小到大","warning",function(){
				if(!validateMyself(a)){
					moveScroll(type+direct+avg+scene+"1");
					addWrong(type+direct+avg+scene+"1",null);
				}else if(!validateMyself(b)){
					moveScroll(type+direct+avg+scene+"2");
					addWrong(type+direct+avg+scene+"2",null);
				}else if(!validateMyself(c)){
					moveScroll(type+direct+avg+scene+"4");
					addWrong(type+direct+avg+scene+"4",null);
				}
			});
			return false;
		}
	}
	else if(avg=="Avg"){
		typeName="";
		var a = changeNoRight(document.getElementById(type+direct+avg+scene+"1").value);
		var b = changeNoRight(document.getElementById(type+direct+avg+scene+"2").value);
		var c = changeNoRight(document.getElementById(type+direct+avg+scene+"4").value);
		if(validateMyself(a)&&validateMyself(b)&&validateMyself(c)){
			if(validateInterval(a,b)){
				
			}else{
				$.messager.alert("警告",sceneName+type+directName+"优级 和 良级 平均速率区间值交界应该括号类型不同，值一样","warning",function(){
					moveScroll(type+direct+avg+scene+"1");
					addWrong(type+direct+avg+scene+"1",type+direct+avg+scene+"2");
				});
				return false;
			}
		}else{
			$.messager.alert("警告",sceneName+type+directName+"平均速率区间值，应该从小到大","warning",function(){
				if(!validateMyself(a)){
					moveScroll(type+direct+avg+scene+"1");
					addWrong(type+direct+avg+scene+"1",null);
				}else if(!validateMyself(b)){
					moveScroll(type+direct+avg+scene+"2");
					addWrong(type+direct+avg+scene+"2",null);
				}else if(!validateMyself(c)){
					moveScroll(type+direct+avg+scene+"4");
					addWrong(type+direct+avg+scene+"4",null);
				}
			});
			return false;
		}
	}
	return true;
}