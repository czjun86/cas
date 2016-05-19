<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="测试" description="测试description">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
<style>
* { margin:0 auto; padding:0; border:0;}

</style>
</@head>
<@body>
	<div class="right" style="margin-top:100px;">
  <div class="work_list" style="width:98%;margin-left:1%">
   <form action="${application.getContextPath()}/task/tasklist" id="searchForm" method="post">
      <!-----搜索框 开始 ------->
      <div class="wls">
      <div class="wlf">
      <span class="wlfspan">开始日期：</span>
    	<div class="search_input" style="width:88px;">
    		 <h6></h6>
          <h5><input name= "startTime" value="${startTime!}" id="startTime" type="text" class="easyui-validatebox Wdate"  data-options="required:true"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" />
       		 </h5><h4></h4> 
       
        </div>
        <span class="wlfspan" style="margin-left:13px;">结束日期：</span>
    	<div class="search_input" style=" margin-right: 10px;width:88px;">
       	<h6></h6>
          <h5><input name="endTime" value="${endTime!}" id="endTime" type="text" class="easyui-validatebox Wdate"  data-options="required:true" style="width:78px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"  readonly="readonly" /></h5><h4></h4>
        </div>
          <span class="wlfspan">时间类型：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="datatype" class="easyui-combobox" name="datatype" style="width:75px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="1" <#if datatype?? && datatype == '1'>selected="selected"</#if>>创建时间</option>
		        <option value="2" <#if datatype?? && datatype == '2'>selected="selected"</#if>>测试时间</option>
		     </select> 
	    </div>
	    
        <span class="wlfspan">区域：</span>
	    <div class="" style="float:left;margin-right:10px;">
          <h6></h6>
          <h5>
          <select id="areas" class="easyui-combobox areas"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="areatext" name="areatext" value="${areatext!}"/>
          <input type="hidden" name="areaids" value="${areaids!}" id="areaids"/>
          </h5><h4></h4>
         </div>
        <span class="wlfspan">测试状态：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="cc" class="easyui-combobox" name="isDeal" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if isDeal?? && isDeal == 0>selected="selected"</#if>>全部</option>
		        <option value="0" <#if isDeal?? && isDeal == 0>selected="selected"</#if>>未测试</option>
		        <option value="1" <#if isDeal?? && isDeal == 1>selected="selected"</#if>>已测试</option>
		     </select> 
	    </div>
	    
            <span class="wlfspan">	测试环境：</span>
	    <div class="search_input" style="width:80px;height:28px;margin-right:19px;">
		      <select id="ii" class="easyui-combobox" name="inside" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if inside?? && inside == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if inside?? && inside == '1'>selected="selected"</#if>>室内</option>
		        <option value="0" <#if inside?? && inside == '0'>selected="selected"</#if>>室外</option>
		     </select> 
	    </div>
	    
	    <span class="wlfspan">测试人：</span>
	    <div class="search_input" style=" margin-right: 10px;width:90px">
          <h6></h6>
          <h5>
          <input  name = "testphone" value="${testphone!}" type="text" id="testphone" style="width:80px"/>
          </h5><h4></h4>
         </div>
	    
	     <div class="wlf" style="width:100%;margin:10px 0px 10px 0px">
          
	     <span class="wlfspan">网络制式：</span>
	    <div class="search_input" style="width:98px;height:28px;">
		      <select id="nettype" class="easyui-combobox" name="nettype" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if nettype?? && nettype == '-1'>selected="selected"</#if>>全网络</option>
		        <option value="1" <#if nettype?? && nettype == '1'>selected="selected"</#if>>WCDMA</option>
		        <option value="2" <#if nettype?? && nettype == '2'>selected="selected"</#if>>GSM</option>
		     </select> 
	    </div>
	    <span class="wlfspan" style="margin-left:2px;">测试网络：</span>
	    <div class="search_input" style="width:80px;margin-right:16px;margin-left:3px;">
          <select id="testnets" class="easyui-combobox testnet"  style="width:75px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if testnetName?? && testnetName !=''>${testnetName!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="testnetName" name="testnetName" value="${testnetName!}"/>
          <input type="hidden" name="testnet" value="${testnet!}" id="testnet"/>
         </div>
           
           <span class="wlfspan">业务类型：</span>
	    <div class="search_input" style="width:80px;height:28px;">
          <select id="testtypes" class="easyui-combobox testtypes"  style="width:75px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if testtypeText?? && testtypeText !=''>${testtypeText!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="testtypeText" name="testtypeText" value="${testtypeText!}"/>
          <input type="hidden" name="testtype" value="${testtype!}" id="testtype"/>
         </div>
         <span class="wlfspan">场景：</span>
	    <div class="" style="float:left;margin-right:10px;">
          <h6></h6>
          <h5>
          <select id="sencs" class="easyui-combobox sencs"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if senctext?? && senctext !=''>${senctext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="senctext" name="senctext" value="${senctext!}"/>
          <input type="hidden" name="senceids" value="${senceids!}" id="senceids"/>
          </h5><h4></h4>
         </div>
         
         
     	<span class="wlfspan" style="display:none;">任务类型：</span>
	    <div class="" style="float:left;margin-right:16px;display:none;">
          <h6></h6>
          <h5>
		      <select id="jj" class="easyui-combobox" name="jobtype" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if jobtype?? && jobtype == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if jobtype?? && jobtype == '1'>selected="selected"</#if>>投诉工单</option>
		     </select> 
	    </h5><h4></h4>
         </div>
        <span class="wlfspan">测试环节：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="breakflag" class="easyui-combobox" name="breakflag" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if breakflag?? && breakflag == -1>selected="selected"</#if>>全部</option>
		        <option value="1" <#if breakflag?? && breakflag == 1>selected="selected"</#if>>优化</option>
		        <option value="2" <#if breakflag?? && breakflag == 2>selected="selected"</#if>>建设</option>
		        <option value="3" <#if breakflag?? && breakflag == 3>selected="selected"</#if>>维护</option>
	        	<option value="4" <#if breakflag?? && breakflag == 4>selected="selected"</#if>>其他</option>
		     </select> 
	    </div>
	    
	    	<span class="wlfspan">	审核状态：</span>
	    <div class="search_input" style="width:80px;height:28px;margin-right:19px;">
		      <select id="verify" class="easyui-combobox" name="verify" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if verify?? && verify == -1>selected="selected"</#if>>全部</option>
		        <option value="0" <#if verify?? && verify == 0>selected="selected"</#if>>未审核</option>
		        <option value="1" <#if verify?? && verify == 1>selected="selected"</#if>>已通过</option>
		        <option value="2" <#if verify?? && verify == 2>selected="selected"</#if>>未通过</option>
		     </select> 
	    </div>
	    <span class="wlfspan">工单号：</span>
        <div class="search_input" style=" margin-right: 10px;width:241px">
          <h6></h6>
          <h5>
          <input  name = "sernos" value="${sernos!}" class="easyui-validatebox" validType="length[0,25]"  maxlength="25" invalidMessage="不能超过25个字符！" type="text" id="sernos" style="width:231px"/>
          </h5><h4></h4>
         </div>
         
	    
	    <samp onclick="search()" style="float:right;margin:-20px 5px 0 0;">查询</samp>
      </div>
      </div>
     </form> 
      <div class="clear"></div>
      <div class="all_list">
        <div class="all_list_top"><span></span><samp></samp></div>
        <ul class="all_list_ts">
          <li style="width:16%;" class="all_list_tsleft"><span>任务工单号
            </span></li>
          <li style="width:8%">区域</li> 
          <li style="width:8%">网络类型</li>
          <!--<li style="width:8%">重要性分类</li>-->
          <li style="width:18%">创建工单时间</li>
          <!--<li style="width:6%">状态</li>-->
          <li style="width:8%">测试环节</li>
          <li style="width:18%">测试时间</li>
          <li style="width:12%">测试人</li>
          <li style="width:12%" class="all_list_tsright">操作</li>
        </ul>
        <!--===== -->
         <div id="content">
			<ul>
	          <li style="width:100%;text-align: center;">
	            	数据加载中...
	          </li>
   			</ul>
        </div>
	    <div class="badoo"  style="width:auto">
	      <div class="rght_border" id="pager" > 
	      	
	      </div>
	      <div class="clear"></div>
	    </div>
        <!--===== -->
        <div class="all_list_bottom"><span></span><samp></samp></div>
          <input type="hidden" value="${Request.sernos!}" id="hiddenval"/>
          <input type="hidden" id="fnames" />
	      <input type="hidden" id="fids" />
        <!--==== -->
        <div class="clear"></div>
      </div>
  </div>
</div>
<div id="win" class="easyui-dialog" title="加入对比" style="padding:10px"
		data-options="
			buttons: '#dlg-buttons',
			modal:true ,
			closed:true
		">
</div>
<div id="dlg-buttons" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton comparetree">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#win').dialog('close')">取消</a>
</div>
<div id="areadlg" class="easyui-dialog" title="选择区域" style="padding:10px"
		data-options="
			buttons: '#select-area',
			modal:true ,
			closed:true
		">
</div>
<div id="select-area" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_area">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#areadlg').dialog('close')">取消</a>
</div>

<div id="sencedlg" class="easyui-dialog" title="选择场景" style="padding:10px"
		data-options="
			buttons: '#select-scene',
			modal:true ,
			closed:true
		">
</div>
<div id="select-scene" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_scene">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#sencedlg').dialog('close')">取消</a>
</div>

<div id="testtypedlg" class="easyui-dialog" title="选择业务类型" style="padding:10px"
		data-options="
			buttons: '#select-testtype',
			modal:true ,
			closed:true
		">
</div>
<div id="select-testtype" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_testtype">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#testtypedlg').dialog('close')">取消</a>
</div>

<div id="select-scene" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_scene">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#sencedlg').dialog('close')">取消</a>
</div>

<div id="testnetdlg" class="easyui-dialog" title="选择网络" style="padding:10px"
		data-options="
			buttons: '#select-testnet',
			modal:true ,
			closed:true
		">
</div>
<div id="select-testnet" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_testnet">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#testnetdlg').dialog('close')">取消</a>
</div>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/task/task.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/gisgrad/gradquery.js" type="text/javascript"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
		var s_id = <#if s_id?? && s_id!= ''>'${s_id!}'<#else>''</#if>;
		var pageTotal = ${pb.totalPage!0};
		var pageSize = ${pb.pageSize!5};
		var pageIndex = ${pb.pageIndex!1};
		var isDeal = ${isDeal!-1};
		var startTime = <#if startTime?? && startTime!= ''>'${startTime!}'<#else>''</#if>;
		var endTime = <#if endTime?? && endTime!= ''>'${endTime!}'<#else>''</#if>;
		var testphone =  <#if testphone?? && testphone!= ''>'${testphone!}'<#else>''</#if>;
		var areaids = <#if areaids?? && areaids!= ''>'${areaids!}'<#else>'-1'</#if>;
		var inside = <#if inside?? && inside!= ''>'${inside!}'<#else>'-1'</#if>;
		var senceids =<#if senceids?? && senceids!= ''>'${senceids!}'<#else>'-1'</#if>;
		var testtype = <#if testtype?? && testtype!= ''>'${testtype!}'<#else>'-1'</#if>;
		var nettype=<#if nettype?? && nettype!= ''>'${nettype!}'<#else>'-1'</#if>;
		var datatype=<#if datatype?? && datatype!= ''>'${datatype!}'<#else>'-1'</#if>;
		var jobtype=<#if jobtype?? && jobtype!= ''>'${jobtype!}'<#else>'-1'</#if>;
		var testnet = <#if testnet?? && testnet!= ''>'${testnet!}'<#else>'-1'</#if>;
		var verify = <#if verify??>'${verify!}'<#else>-1</#if>;
		var breakflag = <#if breakflag??>'${breakflag!}'<#else>-1</#if>;
		$(function(){
			
		});
	</script>
</@script>
