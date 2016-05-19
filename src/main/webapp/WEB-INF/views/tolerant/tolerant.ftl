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
      <!-----搜索框 开始 ------->
		<div class="wls">
			<form action="${application.getContextPath()}/tolerant/tolerant" id="searchForm" method="post">
				<div class="wlf" style="margin-bottom: 12px;margin-left:5px;">
					<span class="wlfspan">开始日期：</span>
					<div class="search_input" style="width:88px;">
						<h6></h6>
						<h5>
							<input type="text" name="startTime" class="Wdate" value="${(startTime)!}" style="width:78px;"  readonly="readonly" onFocus="WdatePicker()" />
						</h5>
						<h4></h4> 
					</div>
					<span class="wlfspan" style="margin-left:13px;">结束日期：</span>
					<div class="search_input" style=" margin-right: 10px;width:88px;">
						<h6></h6>
						<h5>
							<input type="text" name="endTime" class="Wdate" value="${(endTime)!}" style="width:78px;"  readonly="readonly" onFocus="WdatePicker()" />
						</h5>
						<h4></h4>
					</div>
		        	<!--类别条件-->
					<span class="wlfspan">查询类别：</span>
					<div class="search_input" style="width:80px;height:28px;">
						<select id="isTolerant" name="isTolerant" class="easyui-combobox" name="inside" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
							<option value="0" <#if isTolerant?? && isTolerant == '0'>selected="selected"</#if>>全部</option>
							<option value="1" <#if isTolerant?? && isTolerant == '1'>selected="selected"</#if>>已标记</option>
							<option value="2" <#if isTolerant?? && isTolerant == '2'>selected="selected"</#if>>未标记</option>
						</select>  
					</div>
					<!--区域条件-->
					<span class="wlfspan">区域：</span>
					<div class="" style="float:left;margin-right:10px;">
						<h6></h6>
						<h5>
						<select id="areas" class="easyui-combobox areas"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
							<option><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
						</select>
						<input type="hidden" id="areatext" name="areatext" value="${areatext!}"/>
						<input type="hidden" id="areaids" name="areaids" value="${areaids!}"/>
						</h5>
						<h4></h4>
					</div>
					<!--工单号-->
					<span class="wlfspan">工单号：</span>
					<div class="search_input" style=" margin-right: 10px;width:231px">
						<h6></h6>
						<h5>
						<input type="text" name="serialno" value="${(serialno)!}" style="width:221px" />
						</h5>
						<h4></h4>
					</div>
					<samp onclick="search()" style="float:right;margin:0 5px 0 0;">查询</samp>
				</div>
			</form>
			<!--查询条件结束-->
		
			<div class="clear"></div>
			<!--容差信息开始-->
			<div class="all_list">
				<div class="all_list_top"><span></span><samp></samp></div>
				<!--列表标题-->
				<ul class="all_list_ts">
					<li style="width:20%;" class="all_list_tsleft"><span>工单号</span></li>
					<li style="width:12%">区域</li> 
					<li style="width:22%">系统接单时间</li>
					<li style="width:22%">测试时间</li>
					<li style="width:12%">是否标记</li>
					<li style="width:12%" class="all_list_tsright">操作</li>
				</ul>
				<!--详细数据 -->
				<div id="content">
					<ul>
			          <li style="width:100%;text-align: center;">
			            	数据加载中...
			          </li>
		   			</ul>
				</div>
	        	<!--分页标签-->
				<div class="badoo"  style="width:auto">
					<div class="rght_border" id="pager" > 
					
					</div>
					<form action="${application.getContextPath()}/tolerant/tolerant" id="reload" method="post">
				        <input type="hidden" name ="startTime" value="${startTime!}" id="startTimeHidden"/>
				        <input type="hidden" name ="endTime" value="${endTime!}" id="endTimeHidden"/>
				        <input type="hidden" name ="serialno" value="${serialno!}" id="serialnoHidden"/>
				        <input type="hidden" name ="isTolerant" value="${isTolerant!}" id="isTolerantHidden"/>
				        <input type="hidden" name ="areaids" value="${areaids!}" id="areaidsHidden"/>
				        <input type="hidden" name="areatext" value="${areatext!}"/>
			        </form>
					<div class="clear"></div>
				</div>
	        	<!--底线 -->
				<div class="all_list_bottom"><span></span><samp></samp></div>
				<div id="dlg" class="easyui-dialog" title="请选择容差指标" style="padding:10px"
					data-options="
						buttons: '#dlg-buttons',
						modal:true ,
						height: 400,
						width: 750,
						closed:true
					">
				</div>
				<div id="dlg-buttons">
					<a href="javascript:void(0)" class="easyui-linkbutton save">确定</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a>
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
	        	<div class="clear"></div>
			</div>
		</div>
	</div>
</div>
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/tolerant/tolerant.js" type="text/javascript"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
		var s_id = <#if s_id?? && s_id!= ''>'${s_id!}'<#else>''</#if>;
		var pageTotal = ${pb.totalPage!0};
		var pageSize = ${pb.pageSize!5};
		var pageIndex = ${pb.pageIndex!1};
	</script>
</@script>
