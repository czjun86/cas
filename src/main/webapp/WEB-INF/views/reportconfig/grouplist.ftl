<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	
<div class="right">
	  <div class="sys_use" style="width:98%;margin-left:1%">
	      <!-----搜索框 开始 ------->
	      <div class="clear"></div>
	      <div class="sys_use_addbtn">
	      	<input type="button"id="edit" class="editgroup"/>
	      </div>
	      <!-------搜索框 结束-------->
	      <!--用户管理列表 开始-->
	      <div class="all_list">
	        <div class="all_list_top"><span></span><samp></samp></div>
	        <ul class="all_list_ts">
	          <li class="all_list_tsleft" style="width:20%"><span>公司名称</span></li>
	          <li class="all_list_tsright" style="width:80%">区域</li>
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
<div id="managerdlg" class="easyui-dialog" style="padding:10px;"
		data-options="
			buttons: '#btn_dlg',
			modal:true ,
			closed:true
		">
</div>
<div id="btn_dlg" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton btn_save">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#managerdlg').dialog('close')">取消</a>
</div>	
<div id="editdlg" class="easyui-dialog" title="分配区域" style="padding:10px;"
		data-options="
			buttons: '#edit_btn',
			modal:true ,
			closed:true
		">
</div>
<div id="edit_btn" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton editGroup_btn">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#editdlg').dialog('close')">取消</a>
</div>	
<div id="adddlg" class="easyui-dialog" style="padding:10px;"
		data-options="
			buttons: '#btn_add',
			modal:true ,
			closed:true
		">
</div>
<div id="btn_add" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton addGroup_btn">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#adddlg').dialog('close')">取消</a>
</div>	
	
</@body>
<@script>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/reportconfig/groupconfig.js" type="text/javascript"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
		var pageTotal = ${pb.totalPage!0};
		var pageSize = ${pb.pageSize!5};
		var pageIndex = ${pb.pageIndex!1};
	</script>
</@script>
