<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="分组配置" description="分组配置">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<div class="right">
	  <div class="sys_use" style="width:98%;margin-left:1%">
	      <!-----搜索框 开始 ------->
	      <form action="${application.getContextPath()}/teamgroup/teamgroup" id="searchForm" method="post">
	      <div class="sys_use_find"><span>小组人员：</span>
	      <!--公共输入框 -->
	        <div class="search_input">
	          <h6></h6>
	          <h5>
	          <input name="name" type="text" value="${name!}" id="name"/>
	          </h5><h4></h4>
	          </div>
	               <!--公共输入框 -->
	      <samp id="search">查询</samp>
	      </div>
	      </form>
	      <div class="clear"></div>
	      <div div class="sys_use_addbtn">
	      	<input class="editgroup" type="button">
	      </div>
	      <!-------搜索框 结束-------->
	      <!--用户管理列表 开始-->
	      <div class="all_list">
	        <div class="all_list_top"><span></span><samp></samp></div>
	        <ul class="all_list_ts">
	          <li style="width:20%" class="all_list_tsleft"><span>大组</span></li>
	          <li style="width:20%">小组</li>
	          <li style="width:20%">人员</li>
	          <li style="width:40%" class="all_list_tsright">区域</li>
	        </ul>
	        <div id="content">
				<ul>
		          <li style="width:100%;text-align: center;">
		            	数据加载中...
		          </li>
	    		</ul>
	        </div>
	        <div class="badoo"  style="width:auto;border-top:none;height:1px;">
	          <div class="clear"></div>
	        </div>
	        <!--===== -->
	        <div class="all_list_bottom"><span></span><samp></samp></div>
	        <input type="hidden" value="${name!}" id="hiddenval"/>
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
	<input type="hidden" id="valiSelect" value="0"/>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/teamgroup/teamgroup.js"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var pageTotal = ${(pb.totalPage)!0};
	var pageSize = ${(pb.pageSize)!5};
	var pageIndex = ${(pb.pageIndex)!1}; 
	</script>
</@script>
