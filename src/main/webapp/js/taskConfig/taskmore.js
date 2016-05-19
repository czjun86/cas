function taskmore(){
	var areaid = $("#areaid_option").val();
	var areaname = $("#areaname_option").val();
	var areaids = [];
	var areanames = [];
	if(areaid!=null && areaid!=''){
		areaids = (areaid.substring(0,areaid.length-1)).split(',');
	}
	if(areaname!=null && areaname!=''){
		areanames = (areaname.substring(0,areaname.length-1)).split(',');
	}
	var option = "";
	for(var i=0;i<areanames.length;i++){
		option+="<option value="+areaids[i]+">"+areanames[i]+"</option>";
	}
	var more = "<ul style=\"float:left;\">"+
		"<li class=\"fl\"><span class=\"fls\"><samp style=\"color:red\">*</samp>区域选择：</span>"+
			"<p class=\"flp flp-ts\">"+
			  "<select name = \"areaid\" class=\"easyui-combobox areaid\" style=\"border:1px solid #D3D3D3;height:20px;width:80px;\" data-options=\"editable:false,required:true\">"+
			  option+
			  "</select>"+
			"</p>"+
		"</li>"+
       "<li class=\"fl\"><span class=\"fls\"><samp style=\"color:red\">*</samp>测试网络：</span>"+
			"<p class=\"flp flp-ts\">"+
			  "<select name = \"nettype\" class=\"easyui-combobox\" style=\"border:1px solid #D3D3D3;height:20px;width:80px;\" data-options=\"editable:false,panelHeight: 'auto'\">"+
			  	"<option value=\"1\">2g数据</option>"+
			  	"<option value=\"2\">2g语音</option>"+
			  	"<option value=\"3\">3g数据</option>"+
			  	"<option value=\"4\">3g语音</option>"+
			  "</select>"+
			"</p>"+
		"</li>"+
		"<li class=\"fl\"><span class=\"fls\"><samp style=\"color:red\">*</samp>测试环节：</span>"+
			"<p class=\"flp flp-ts\">"+
			  "<select name = \"breakflag\" class=\"easyui-combobox\" style=\"border:1px solid #D3D3D3;height:20px;width:80px;\" data-options=\"editable:false,panelHeight: 'auto'\">"+
			  	"<option value=\"1\">优化</option>"+
			  	"<option value=\"2\">建设</option>"+
			  	"<option value=\"3\">维护</option>"+
			  	"<option value=\"4\">其他</option>"+
			  "</select>"+
			"</p>"+
		"</li>"+
       "<li class=\"fl\"><span class=\"fls\"><samp style=\"color:red\">*</samp>测试地址：</span>"+
			"<p class=\"flp\">"+
			  "<input name=\"testaddress\" type=\"text\" uuid=\"uuid\"  class=\"chkent qer easyui-validatebox\" style=\"width:130px;height:20px;border:1px solid #D3D3D3;\"  maxlength=\"100\"/>"+
			"</p>"+
       "</li>"+
       "<li class=\"fl\">"+
          "<a name=\"add\" class=\"change\"><img src=\""+contextPath+"/images/add.png\" title=\"点击可以连续添加\"/></a>"+
          "<a name=\"del\" class=\"change\"><img src=\""+contextPath+"/images/del.png\" title=\"删除当前行\"/></a>"+
       "</li>"+
    "</ul>";
		
	return more;
}