<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
<style>
.tuli{position:absolute;z-index:1000;float:right;}
.tuli table tr td{border:none;}
.tuli ul li {  float: left;font-weight: bold; height: 16px;line-height: 16px;margin-left: 10px;text-indent: 5px;margin-right:10px;}
.tuli .tuli_top{background:url(../images/tuli_top.png) no-repeat; width:30px; height:6px; overflow:hidden;}
.tuli .tuli_middle_top{background:url(../images/tuli_middle_top.png) repeat-x; height:6px;}
.tuli .tuli_center{background:url(../images/tuli_center.png) repeat-y; width:30px; cursor:pointer;}
.tuli .tuli_center span{ width:20px; line-height:16px; display:block; margin-left:5px; text-align:center; color:#FFFFFF;}
.tuli .tuli_bottom{background:url(../images/tuli_bottom.png) no-repeat; width:30px; height:6px; overflow:hidden;}
.tuli .tuli_middle_bottom{background:url(../images/tuli_middle_bottom.png) repeat-x; height:6px;}
</style>
</@head>
<@body>
<div class="right1">
	<div id="alertBackground" class="alertBackground"></div>
<div id="dialogBackground" class="dialogBackground"></div>
<div id='background' class='background'></div>
<div id="bar_id">
<img src="../images/jbar.gif" /></span>
</div>

<div class="tuli" id="aa">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="float:right;">
  <tr>
    <td class="tuli_top"></td>
    <td class="tuli_middle_top"></td>
  </tr>
  <tr>
    <td class="tuli_center" id="tuli_id"><span>图例</span></td>
    <td style="background:#fff;display:none;" id="tuli_ul">

   </td>
  </tr>
  <tr>
    <td class="tuli_bottom"></td>
     <td class="tuli_middle_bottom"></td>
  </tr>
</table>
<script>
    function scroll(p){
    	var d = document,w = window,o = d.getElementById(p.id),ie6 = /msie 6/i.test(navigator.userAgent);
    	if(o){
    		o.style.cssText +=";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.r?'right':"right")+":0;"+(p.t!=undefined?'top:'+p.t+'px':'top:0');
    		if(!p.f||ie6){
    			-function(){
	        		var t = 500,st = d.documentElement.scrollTop||d.body.scrollTop,c;
	                c = st  - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||d.documentElement.clientHeight)-o.offsetHeight);//如果你是html 4.01请改成d.body,这里不处理以减少代码
	            	c!=0&&(o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px',t=10);
	            	setTimeout(arguments.callee,t)
        		}()	
    		}
    	}  
    }
    scroll({
    	id:'aa',t:50    
    })
</script>
</div>

	<div style="float:left;">
	</div>
	<div class="forms_gongdan">
	</div>
	<div class="right">
		<div class="forms">
			<div class="forms_nav" id="net_id">
				<ul>
					<li>&nbsp;</li>
				</ul>
				<samp><a href="#"><img src="${application.getContextPath()}/images/export.png" /></a><a href="#"><img onclick="window.print()" src="${application.getContextPath()}/images/print.png" /></a></samp> 
			</div>
			<div class="border_left">
	    		<div class="clear"></div>
				<div class="forms_center">
					<div class="computer_map">
						<div class="computer_map_search">      
							<div class="work_list_find">
								<p>工单号：</p>
							    <div class="search_input" style="width:auto;">
							    	<p id="ss_id">${serialno!}</p>
							    </div>
	 						</div>
	 						<input type="hidden" value="${areaid!}" id="areaid"/>
	 						<input type="hidden" value="${s_id!}" id="s_id"/>
	  						<input type="button" class="lsh" onclick="span_choose()" value="流水号选择"/>
	  						<input type="hidden" id="fnames" />
	                        <input type="hidden" id="fids" />
	                        <input type="hidden" id="up_flid" value="${flowid!}"/>
						         <div id="win" class="easyui-dialog" title="测试流水选择" style="padding:10px" data-options="
									buttons: '#tree-button',
									modal:true ,
									closed:true
									">
						          </div>
								<div id="tree-button">
									<a href="javascript:void(0)" class="easyui-linkbutton treebtn" style="display:none">确定</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#win').dialog('close')" style="display:none">取消</a>
								</div>
	  					</div>
	  					<div class="clear"></div>
						<div id="max" style="display:none">&nbsp;</div>
	  					<div id="compare_map" style="height:450px;">
							 
						</div>
						 <div id="zhibiao" class="easyui-dialog" title="测试指标选择" style="padding:10px" data-options="
							buttons: '#tree-but',
							modal:true ,
							closed:true
							">
							<ul id="tul"></ul>
	                    </div>
						<div id="tree-but">
						    <div class="sure" style="display:none"></div>
							<a href="javascript:void(0)" class="easyui-linkbutton treeb" style="display:none">确定</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#zhibiao').dialog('close')" style="display:none">取消</a>
						</div>
			            <input type="hidden" id="undata" />
	                    <input type="hidden" id="undata_id" />
						<div class="clear"></div>
					</div>
	    			<div class="clear"></div>
	    			<div class="histogram" style="font-family:Arial,Helvetica,sans-serif;color:#999999;float:left;width:99%;margin-left:1%;" id="histogram1">
	    			</div>
	    			<div class="clear"></div>
				    <div class="histogram"style="font-family:Arial,Helvetica,sans-serif;color:#999999;float:left;width:99%;margin-left:1%;" id="histogram2">
				    </div>
	  			</div>
			</div>
	  		<div class=""><span></span><samp></samp></div>
	  		<div class="clear"></div>
		</div></div>
	</div>
</@body>
<@script>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/sj.base.library.1.0.1.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/highcharts1.0.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/ReportForms.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/raphael.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/map.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/graphicEle.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/canvas.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/compare/compareReport.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/utile_report.js"></script>
<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/compare/point.js"></script>

	<script>
	var contextPath = '${application.getContextPath()}';
var flowid = <#if flowid?? && flowid!= ''>'${flowid!}'<#else>''</#if>;
var areaid = <#if areaid?? && areaid!= ''>'${areaid!}'<#else>''</#if>;
var s_id = <#if s_id?? && s_id!= ''>'${s_id!}'<#else>''</#if>;
var flowname = <#if flowname?? && flowname!= ''>'${flowname!}'<#else>''</#if>;

    function scroll(p){
    	var d = document,w = window,o = d.getElementById(p.id),ie6 = /msie 6/i.test(navigator.userAgent);
    	if(o){
    		o.style.cssText +=";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.r?'right':"right")+":0;"+(p.t!=undefined?'top:'+p.t+'px':'top:0');
    		if(!p.f||ie6){
    			-function(){
	        		var t = 500,st = d.documentElement.scrollTop||d.body.scrollTop,c;
	                c = st  - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||d.documentElement.clientHeight)-o.offsetHeight);//如果你是html 4.01请改成d.body,这里不处理以减少代码
	            	c!=0&&(o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px',t=10);
	            	setTimeout(arguments.callee,t)
        		}()	
    		}
    	}  
    }
    scroll({
    	id:'aa',t:50    
    })
	</script>
</@script>
