<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="用户列表" description="用户列表">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
	<style>
		body{background:url(${application.getContextPath()}/images/bj.png) repeat;}
	</style>
</@head>
<@body>
	<div class="right">
	  <div class="sys_use" style="width:98%;margin-left:1%">
	      <!-----搜索框 开始 ------->
	      <form action="${application.getContextPath()}/user/userlist" id="searchForm" method="post">
	      <div class="sys_use_find"><span>用户名：</span>
	      <!--公共输入框 -->
	        <div class="search_input">
	          <h6></h6>
	          <h5>
	          <input name="name" type="text" value="${name!}" id="name"/>
	          </h5><h4></h4>
	          </div>
	               <!--公共输入框 -->
	      <samp onclick="search()">查询</samp>
	      </div>
	      </form>
	      <div class="clear"></div>
	      <div class="sys_use_addbtn">
	      <#if buttons?? && buttons?size &gt;0>
		      <#list buttons as button>
		      	<#if (button.btntype)?? && button.btntype == 'add'>
			      <input type="button" class="add"/>
			      </#if>
			      <#if (button.btntype)?? && button.btntype = 'delete'>
			     	 <input type="button" id="delall"/>
			      </#if>
		      </#list>
		    </#if>
	      </div>
	      <!-------搜索框 结束-------->
	      <!--用户管理列表 开始-->
	      <div class="all_list">
	        <div class="all_list_top"><span></span><samp></samp></div>
	        <ul class="all_list_ts">
	          <li style="width:5%;" class="all_list_tsleft"><span>
	            <p>
	              <input type="checkbox" value="123" id="parent" />
	            </p>
	            </span></li>
	          <li style="width:12%">登录名</li>
	          <li style="width:10%">用户名</li>
	          <li style="width:12%">用户组</li>
	          <li style="width:10%">电话</li>
	          <li style="width:17%">邮箱</li>
	          <li style="width:5%">禁用</li>
	          <li style="width:29%" class="all_list_tsright">操作</li>
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
	        <input type="hidden" value="${name!}" id="hiddenval"/>
	        <!--==== -->
	        <div class="clear"></div>
	      </div>
	      <!--用户管理列表 结束-->
	  </div>
	</div>
	<div id="dlg" class="easyui-dialog" title="用户信息" style="padding:10px"
			data-options="
				buttons: '#dlg-buttons',
				modal:true ,
				closed:true
			">
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton saveedit">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<input type="hidden" id="valiSelect" value="0"/>
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
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/user/userlist.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var pageTotal = ${(pb.totalPage)!0};
	var pageSize = ${(pb.pageSize)!5};
	var pageIndex = ${(pb.pageIndex)!1}; 
	</script>
</@script>