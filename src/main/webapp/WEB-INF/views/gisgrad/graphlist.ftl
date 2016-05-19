	<div style="float:left;margin:10px 0;">
		<span class="wlfspan">开始日期：</span>
	    	<div class="search_input" style="width:88px;">
	    		<h6></h6>
	          		<h5><input name= "startTime_" value="${startTime!}" id="startTime_" type="text" class="easyui-validatebox Wdate"  data-options="required:true"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime_\')}'})" /></h5>
	          	<h4></h4> 
	        </div>
        <span class="wlfspan" style="margin-left:13px;">结束日期：</span>
	    	<div class="search_input" style=" margin-right: 10px;width:88px;">
		       	<h6></h6>
		          <h5><input name="endTime_" value="${endTime!}" id="endTime_" type="text" class="easyui-validatebox Wdate"  data-options="required:true" style="width:78px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime_\')}'})"  readonly="readonly" /></h5>
		        <h4></h4>
	        </div>
	    <span class="wlfspan">时间类型：</span>
		    <div class="search_input" style="width:82px;height:28px;margin-right:38px;">
			      <select id="datatype_" class="easyui-combobox" name="datatype" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
			        <option value="1" <#if datatype?? && datatype == '1'>selected="selected"</#if>>测试时间</option>
			        <option value="2" <#if datatype?? && datatype == '2'>selected="selected"</#if>>接单时间</option>
			     </select> 
		    </div>
        <span class="wlfspan">区域：</span>
		    <div class="" style="float:left;margin-right:10px;">
	          <h6></h6>
	          <h5>
	          <select id="areas_" class="easyui-combobox areas"  style="width:82px;height:26px;" data-options="editable:false,panelHeight: '0px'">
	          		<option selected="selected"><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
	          </select>
	          <input type="hidden" id="areatext_" name="areatext_" value="${areatext!}"/>
	          <input type="hidden" name="areaids_" value="${areaids!}" id="areaids_"/>
	          </h5>
	          <h4></h4>
	         </div>
   </div>
   <div style="float:left;margin:10px 0;">
        <span class="wlfspan">	测试环境：</span>
	    	<div class="search_input" style="width:80px;height:28px;margin-right:20px;">
		      <select id="ii_" class="easyui-combobox" name="inside" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if inside?? && inside == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if inside?? && inside == '1'>selected="selected"</#if>>室内</option>
		        <option value="0" <#if inside?? && inside == '0'>selected="selected"</#if>>室外</option>
		     </select> 
	    	</div>
	    <span class="wlfspan" style="margin-right:3px;">网络制式：</span>
	    	<div class="search_input" style="width:80px;height:28px;margin-right:17px;">
		      <select id="nettype_" class="easyui-combobox" name="nettype" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if nettype?? && nettype == '-1'>selected="selected"</#if>>全网络</option>
		        <option value="1" <#if nettype?? && nettype == '1'>selected="selected"</#if>>WCDMA</option>
		        <option value="2" <#if nettype?? && nettype == '2'>selected="selected"</#if>>GSM</option>
		      </select> 
	   		</div>
	    <span class="wlfspan">测试网络：</span>
		    <div class="" style="float:left;margin-right:10px;margin-right:10px;">
	          <h6></h6>
	          <h5>
	          <select id="testnets_" class="easyui-combobox testnet"  style="width:82px;height:26px;" data-options="editable:false,panelHeight: '0px'">
	          		<option><#if senctext?? && senctext !=''>${testnetName!}<#else>全部</#if></option>	
	          </select>
	          <input type="hidden" id="testnetName_" name="testnetName" value="${testnetName!}"/>
	          <input type="hidden" name="testnet" value="${testnet!}" id="testnet_"/>
	          </h5>
	          <h4></h4>
	        </div>
		<span class="wlfspan">业务类型：</span>
	    	<div class="" style="float:left;margin-right:16px;">
	          <h6></h6>
	          <h5>
	          <select id="testtypes_" class="easyui-combobox testtypes"  style="width:82px;height:26px;" data-options="editable:false,panelHeight: '0px'">
	          		<option><#if testtypeText?? && testtypeText !=''>${testtypeText!}<#else>全部</#if></option>	
	          </select>
	          <input type="hidden" id="testtypeText_" name="testtypeText" value="${testtypeText!}"/>
	          <input type="hidden" name="testtype" value="${testtype!}" id="testtype_"/>
	          <input type="hidden" name="openType" value="${openType!}" id="openType"/>
	          </h5>
	          <h4></h4>
	        </div>
 </div>
 <div style="float:left;margin:10px 0;">
      <span class="wlfspan">路测指标：</span>
	    	<div class="search_input" style="width:82px;height:28px;margin-right:20px;">
		      <select id="kk_" class="easyui-combobox" name="kpi" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if kpi?? && kpi == '-1'>selected="selected"</#if>>综合</option>
		        <option value="1" <#if kpi?? && kpi == '1'>selected="selected"</#if>>RSCP</option>
		        <option value="2" <#if kpi?? && kpi == '2'>selected="selected"</#if>>Ec/No</option>
		        <option value="3" <#if kpi?? && kpi == '3'>selected="selected"</#if>>Txpower</option>
		        <option value="4" <#if kpi?? && kpi == '4'>selected="selected"</#if>>FTP上行</option>
		        <option value="5" <#if kpi?? && kpi == '5'>selected="selected"</#if>>FTP下行</option>
		        <option value="6" <#if kpi?? && kpi == '5'>selected="selected"</#if>>Rxlev</option>
		        <option value="7" <#if kpi?? && kpi == '5'>selected="selected"</#if>>RxQual</option>
		        <option value="8" <#if kpi?? && kpi == '8'>selected="selected"</#if>>C/I</option>
		     </select> 
	    	</div>
	  <span class="wlfspan" style="margin-right:2px;">评价等级：</span>
	  		<div class="search_input" style="width:82px;height:28px;margin-right:42px;">
		      <select id="gg_" class="easyui-combobox" name="grad" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if grad?? && grad == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if grad?? && grad == '1'>selected="selected"</#if>>优</option>
		        <option value="2" <#if grad?? && grad == '2'>selected="selected"</#if>>良</option>
		        <option value="3" <#if grad?? && grad == '3'>selected="selected"</#if>>中</option>
		        <option value="4" <#if grad?? && grad == '4'>selected="selected"</#if>>差</option>
		      </select> 
	    	</div>
     <span class="wlfspan">场景：</span>
		    <div class="" style="float:left;margin-right:10px;">
	          <h6></h6>
	          <h5>
	          <select id="sencs_" class="easyui-combobox sencs"  style="width:82px;height:26px;" data-options="editable:false,panelHeight: '0px'">
	          		<option><#if senctext?? && senctext !=''>${senctext!}<#else>全部</#if></option>	
	          </select>
	          <input type="hidden" id="senctext_" name="senctext" value="${senctext!}"/>
	          <input type="hidden" name="senceids" value="${senceids!}" id="senceids_"/>
	          </h5>
	          <h4></h4>
	        </div>
     <span class="wlfspan">任务类型：</span>
	   		<div class="search_input" style="width:82px;height:28px;">
		      <select id="jj_" class="easyui-combobox" name="jobtype" style="width:82px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if jobtype?? && jobtype == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if jobtype?? && jobtype == '1'>selected="selected"</#if>>投诉工单</option>
		     </select> 
	    	</div>
</div>
<div style="float:left;margin:10px 0;">
	 <span class="wlfspan" style="margin-left:13px;">工单号：</span>
        	<div class="search_input" style=" margin-right: 10px;width:231px;margin-right:10px;">
	          <h6></h6>
	          <h5><input  name = "sernos_" value="${sernos!}" class="easyui-validatebox" validType="length[0,25]"  maxlength="25" invalidMessage="不能超过25个字符！" type="text" id="sernos_" style="width:221px"/></h5>
	          <h4></h4>
        	</div>
</div>
<script>
$("#areas_").combobox({
		onShowPanel:function(){
			$("#areas_").combobox("hidePanel");
			var ids = $("#areaids_").val();
			openAreaDialog_(0,ids);
		}
	});
//柱状图查询区域
function openAreaDialog_(type,ids,map){
	var src = contextPath + '/workorder/arealist?areaids='+ids+'&type='+type;
	$("#areadlg").dialog({
		href:src,
		height: 400,width: 380,title: "选择区域",
		modal: true,closed:false
	});
	$('#areadlg').dialog('open');
	$.parser.parse('#areadlg');
	$("#areadlg");
	$(".sel_area").unbind('click').click(function(){
		var nodes = $('#areadlg').tree('getChecked');
		var strtext = "";
		var	strids = "";
		var len = nodes.length;
		if(type == 0){
			if(len > 0){
				for(var i = 0; i < len; i++){
					strids += nodes[i].id + ",";
					strtext += nodes[i].text + ",";
				}
				if(strids !=null && strids != ""){
					strids = strids.substring(0,strids.length-1);
					$("#areaids_").val(strids);
				}
				if(strtext !=null && strtext != ""){
					strtext = strtext.substring(0,strtext.length-1);
					$("#areas_").combobox("setText",strtext);
					$("#areatext_").val(strtext);
				}
			}else{
				$("#areaids_").val('-1');
				$("#areatext_").val('全部');
				$("#areas_").combobox("setText",'全部');
			}
		}
	$('#areadlg').dialog('close');
	});
}
//柱状图测试网络
$("#testnets_").combobox({
	onShowPanel:function(){
		$("#testnets_").combobox("hidePanel");
		$.parser.parse('#nettype');
		var src = contextPath + '/gisgrad/testNetlist?nettype='+$("#nettype_").combobox('getValue')+'&testnet='+$("#testnet_").val();
		$("#testnetdlg").dialog({
			href:src,
			height: 400,width: 380,title: "选择网络",
			modal: true,closed:false
		});
		$('#testnetdlg').dialog('open');
		$.parser.parse('#testnetdlg');
		$(".sel_testnet").unbind('click').click(function(){
			var nodes = $('#testnetdlg').tree('getChecked');
			var strtext = "";
			var	strids = "";
			if(nodes.length > 0){
				for(var i = 0; i < nodes.length; i++){
					strids += nodes[i].id + ",";
					strtext += nodes[i].text + ",";
				}
				if(strids !=null && strids != ""){
					strids = strids.substring(0,strids.length-1);
					$("#testnet_").val(strids);
				}
				if(strtext !=null && strtext != ""){
					strtext = strtext.substring(0,strtext.length-1);
					$("#testnets_").combobox("setText",strtext);
					$("#testnetName_").val(strtext);
				}
				$('#testnetdlg').dialog('close');
			}else{
				$.messager.alert("提示","请选择网络！","warning");
			}
			
			
		});
	}
});
//柱状图查询场景
$("#sencs_").combobox({
	onShowPanel:function(){
		$("#sencs_").combobox("hidePanel");
		$.parser.parse('#ii_');
		var src = contextPath + '/gisgrad/sencelist?senceids='+$("#senceids_").val()+"&inside="+$("#ii_").combobox('getValue');
		$("#sencedlg").dialog({
			href:src,
			height: 400,width: 380,title: "选择场景",
			modal: true,closed:false
		});
		$('#sencedlg').dialog('open');
		$.parser.parse('#sencedlg');
		$(".sel_scene").unbind('click').click(function(){
			var nodes = $('#sencedlg').tree('getChecked');
			var strtext = "";
			var	strids = "";
			if(nodes.length > 0){
				for(var i = 0; i < nodes.length; i++){
					strids += nodes[i].id + ",";
					strtext += nodes[i].text + ",";
				}
				if(strids !=null && strids != ""){
					strids = strids.substring(0,strids.length-1);
					$("#senceids_").val(strids);
				}
				if(strtext !=null && strtext != ""){
					strtext = strtext.substring(0,strtext.length-1);
					$("#sencs_").combobox("setText",strtext);
					$("#senctext_").val(strtext);
				}
				$('#sencedlg').dialog('close');
			}else{
				$.messager.alert("提示","请选择场景！","warning");
			}
			
			
		});
	}
});
//柱状图业务类型
$("#testtypes_").combobox({
	onShowPanel:function(){
		$("#testtypes_").combobox("hidePanel");
		$.parser.parse('#nettype');
		var src = contextPath + '/gisgrad/testtypelist?testtype='+$("#testtype_").val()+'&nettype='+$("#nettype_").combobox('getValue');
		$("#testtypedlg").dialog({
			href:src,
			height: 400,width: 380,title: "业务类型",
			modal: true,closed:false
		});
		$('#testtypedlg').dialog('open');
		
		$.parser.parse('#testtypedlg');
		$(".sel_testtype").unbind('click').click(function(){
			var nodes = $('#testtypedlg').tree('getChecked');
			var strtext = "";
			var	strids = "";
			if(nodes.length > 0){
				for(var i = 0; i < nodes.length; i++){
					strids += nodes[i].id + ",";
					strtext += nodes[i].text + ",";
				}
				if(strids !=null && strids != ""){
					strids = strids.substring(0,strids.length-1);
					$("#testtype_").val(strids);
				}
				if(strtext !=null && strtext != ""){
					strtext = strtext.substring(0,strtext.length-1);
					$("#testtypes_").combobox("setText",strtext);
					$("#testtypeText_").val(strtext);
				}
				$('#testtypedlg').dialog('close');
			}else{
				$.messager.alert("提示","请选择业务类型！","warning");
			}
			
			
		});
	}
});
</script>