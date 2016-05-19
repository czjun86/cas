<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="终端用户列表" description="终端用户列表">
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
	      <form action="${application.getContextPath()}/epinfo/epinfolist" id="searchForm" method="post">
	      <div class="sys_use_find"><span>UUID：</span>
	      <!--公共输入框 -->
	        <div class="search_input">
	          <h6></h6>
	          <h5>
	          <input name="uuid" type="text" value="${uuid!}" id="uuid"/>
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
		    <#if eplead?? && eplead == 'eplead'>
		    <!--下载模板-->
		    	<input type="button" id="getTemplate"/>
	      	</#if>
		    <#if epdown?? && epdown == 'epdown'>
		    <!--导出-->
		    	<input type="button" id="download"/>
		    </#if>
	        <#if eplead?? && eplead == 'eplead'>
	        <!--导入-->
			    <input type="button" id="lead" />
		    </#if>
		    <#if errorname?? && errorname != ''>
	        <!--导入-->
			    <input type="button" class="errordown" />
		    </#if>
	      		<input type="hidden" id="errordown" value="${errorname!}" />
	      </div>
	      <div id="epleaddlg" class="easyui-dialog" title="导入模板" style="padding:10px"
			data-options="
				buttons: '#epleaddlg-buttons',
				modal:true ,
				closed:true
			">
				<form method="post" target="leadwindow" id="leadExcel" enctype="multipart/form-data" action="${application.getContextPath()}/epinfo/leadExcel">
		      		<ul id="ultext" style="display:none">
		      			<li class="fl"><span class="fls" style="margin-top:5px;">选择文件:</span>
		      			<p><input id="fileshow" type="text" class="flpi" disabled="disabled"/><input type="button" value="浏览" style="margin:5px;height:22px;width:50px;background:#BBB"></p>
		      			</li>
		      		</ul>
		      		<input type="file" class="file" size="1" id="file" value="post" name="excelFile" style="display:none;width:210px;"/>
	      		</form>
		  </div>
		    	<iframe name="leadwindow" style="display:none"></iframe>
		  <div id="epleaddlg-buttons" style="display:none">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="leadsave">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#epleaddlg').dialog('close')">取消</a>
		  </div>
	      <!-------搜索框 结束-------->
	      <!--用户管理列表 开始-->
	      <div class="all_list">
	        <div class="all_list_top"><span></span><samp></samp></div>
	        <ul class="all_list_ts">
	          <li style="width:5%;" class="all_list_tsleft"><span>
	            <p>
	              <input type="checkbox" value="" id="parent" />
	            </p>
	            </span></li>
	          <li style="width:10%">区域</li>
	          <li style="width:20%">UUID</li>
	          <li style="width:12%">责任人</li>
	          <li style="width:12%">联系电话</li>
	          <li style="width:10%">授权状态</li>
	          <li style="width:12%">更新时间</li>
	          <li style="width:19%" class="all_list_tsright">操作</li>
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
	<div id="dlg" class="easyui-dialog" title="终端用户信息" style="padding:10px"
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
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/epinfo/epinfolistValidate.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/epinfo/epinfolist.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/epinfo/epinfomore.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var pageTotal = ${pb.totalPage!0};
	var pageSize = ${pb.pageSize!5};
	var pageIndex = ${pb.pageIndex!1};
	var uuid = <#if uuid?? && uuid!= ''>'${uuid!}'<#else>''</#if>;
	</script>
</@script>

