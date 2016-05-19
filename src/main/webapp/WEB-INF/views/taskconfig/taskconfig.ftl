<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="任务工单配置" description="任务工单配置">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
<style type="text/css">
	.file{position:absolute; right:40px; top:49px; font-size:12px; opacity:0; filter:alpha(opacity=0);}
</style>
	<div class="right">
	  <div class="sys_use" style="width:98%;margin-left:1%">
	      <!-----搜索框 开始 ------->
	      <form action="${application.getContextPath()}/taskconfig/taskconfig" id="searchForm" method="post">
		      <div class="sys_use_find"><span>工单号或地址：</span>
			      	<!--公共输入框 -->
			        <div class="search_input">
			          <h6></h6>
			          <h5>
			          <input id="val" value="${val!}" />
			          <!--工单号或地址条件-->
			          <input type="hidden" value="${val!}" name="val"/>
			          <!--审核状态条件-->
			          <input type="hidden" value="${isverify!-1}" id="isverify" name="isverify"/>
			          <!--是否失效条件-->
			          <input type="hidden" value="${validstate!-1}" id="validstate" name="validstate"/>
			          <!--时间条件条件-->
			          <input type="hidden" value="${timeType!1}" id="timeType" name="timeType"/>
			          </h5><h4></h4>
			          </div>
			               <!--公共输入框 -->
			      <samp onclick="search()">查询</samp>
		      </div>
		  </form> 
		  <input style="display:none;" name="userid" value="${userid!}" id="userid"/>
	      <div class="clear"></div>
	      <div class="sys_use_addbtn">
			    <input type="button" class="add"/>
		    <!--下载模板-->
		    	<input type="button" id="getTemplate"/>
		    <!--导出-->
		    	<input type="button" id="download"/>
	        <!--导入-->
			    <input type="button" id="lead" />
		    <#if errorname?? && errorname != ''>
	        <!--导入-->
			    <input type="button" class="errordown" />
		    </#if>
	      		<input type="hidden" id="errordown" value="${errorname!}" />
	      		<input type="hidden" id ="regions" value = "<#if regions?? && regions?size &gt; 0>1<#else>0</#if>"/>
			<p class="flp flp-ts" style="float:right;margin-right:20px;">
				有效状态：<select name="validstate_select" class="easyui-combobox queryCondition" style="border:1px solid #D3D3D3;width: 75px; height: 26px; " data-options="editable:false,panelHeight: 'auto'">
			  	<option value="validstate,-1"  <#if validstate?? && validstate == -1>selected</#if>>全部</option>
			  	<option value="validstate,0" <#if validstate?? && validstate == 0>selected</#if>>有效</option>
			  	<option value="validstate,1" <#if validstate?? && validstate == 1>selected</#if>>失效</option>
			  </select>
			</p>
			<p class="flp flp-ts" style="float:right;margin-right:20px;">
				审核：<select name="isverify_select" class="easyui-combobox queryCondition" style="border:1px solid #D3D3D3;width: 75px; height: 26px; " data-options="editable:false,panelHeight: 'auto'">
			  	<option value="isverify,-1" <#if isverify?? && isverify == -1>selected</#if>>全部</option>
			  	<option value="isverify,0" <#if isverify?? && isverify == 0>selected</#if>>未审核</option>
			  	<option value="isverify,1" <#if isverify?? && isverify == 1>selected</#if>>已通过</option>
			  	<option value="isverify,2" <#if isverify?? && isverify == 2>selected</#if>>未通过</option>
			  </select>
			</p>
			<p class="flp flp-ts" style="float:right;margin-right:20px;">
			  	时间：<select name="timeType_select" class="easyui-combobox queryCondition" style="border:1px solid #D3D3D3;width: 75px; height: 26px; " data-options="editable:false,panelHeight: 'auto'">
			  	<option value="timeType,1" <#if timeType?? && timeType == 1>selected</#if>>近一周</option>
			  	<option value="timeType,2" <#if timeType?? && timeType == 2>selected</#if>>近一月</option>
			  	<option value="timeType,3" <#if timeType?? && timeType == 3>selected</#if>>近三月</option>
			  	<option value="timeType,-1" <#if timeType?? && timeType == -1>selected</#if>>全部</option>
			  </select>
			</p>
	      </div>
	      
	      <!-------搜索框 结束-------->
	      <!--用户管理列表 开始-->
	      <div class="all_list">
	        <div class="all_list_top"><span></span><samp></samp></div>
	        <ul class="all_list_ts">
	          <li style="width:14%" class="all_list_tsleft"><span>
	            <p>任务工单号</p>
	            </span></li>
	          <li style="width:10%">创建时间</li>
	          <li style="width:5%">区域</li>
	          <li style="width:7%">测试网络</li>
	          <li style="width:7%">测试环节</li>
	          <li style="width:17%">测试地址</li>
	          <li style="width:8%">测试状态</li>
	          <li style="width:8%">审核状态</li>
	          <li style="width:8%">有效状态</li>
	          <li style="width:16%" class="all_list_tsright">操作</li>
	        </ul>
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
	        <!--==== -->
	        <div class="clear"></div>
	      </div>
	      <!--用户管理列表 结束-->
	  </div>
	</div>
	<div id="dlg" class="easyui-dialog" title="新增工单" style="padding:10px"
			data-options="
				buttons: '#dlg-buttons',
				modal:true ,
				closed:true
			">
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton saveadd">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<div id="dlgedit" class="easyui-dialog" title="修改工单" style="padding:10px"
			data-options="
				buttons: '#edit-buttons',
				modal:true ,
				closed:true
			">
	</div>
	<div id="edit-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton saveedit">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlgedit').dialog('close')">取消</a>
	</div>
	<div id="dlgexcel" class="easyui-dialog" title="修改工单" style="padding:10px"
			data-options="
				buttons: '#excel-buttons',
				modal:true ,
				closed:true
			">
	</div>
	<div id="excel-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton leadExcel">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlgexcel').dialog('close')">取消</a>
	</div>
	<div id="taskleaddlg" class="easyui-dialog" title="导入模板" style="padding:10px"
		data-options="
			buttons: '#taskleaddlg-buttons',
			modal:true ,
			closed:true
		">
			<form method="post" target="leadwindow" id="analysisExcel" enctype="multipart/form-data" action="${application.getContextPath()}/taskconfig/analysisExcel">
	      		<ul id="ultext" style="display:none">
	      			<li class="fl"><span class="fls" style="margin-top:5px;">选择文件:</span>
	      			<p><input id="fileshow" type="text" class="flpi" disabled="disabled"/><input type="button" value="浏览" style="margin:5px;height:22px;width:50px;background:#BBB"></p>
	      			</li>
	      		</ul>
	      		<input type="file" class="file" size="1" id="file" value="post" name="excelFile" style="display:none;width:210px;"/>
      		</form>
	  </div>
	    	<iframe name="leadwindow" style="display:none"></iframe>
	  <div id="taskleaddlg-buttons" style="display:none">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="leadsave">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#taskleaddlg').dialog('close')">取消</a>
	  </div>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/taskConfig/taskConfig.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/taskConfig/taskmore.js"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var pageTotal = ${pb.totalPage!0};
	var pageSize = ${pb.pageSize!5};
	var pageIndex = ${pb.pageIndex!1};
	</script>
</@script>

