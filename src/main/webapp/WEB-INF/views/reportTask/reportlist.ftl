<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
    <style>body .div_left{padding-top:0px;}
.down{width:0;height:0;line-height:0;border-top:40px solid transparent;border-left:40px solid transparent; position:absolute; right:-14px;top:-22px;}
.down span{font-size: 12px;right:17px;position: relative;top: -28px; color:#FFF;}
    </style>
    	<style>
.gis-mapgj {
z-index: 10; position:relative; top: 20px;float:left;

}
.gis-mapgj .gis-mapgj-hand, .gis-mapgj .gis-mapgj-marquee,.gis-mapgj .gis-mapgj-marquee-hover,.gis-mapgj .gis-mapgj-hand-hover {
	float:left;
	direction: ltr;
	overflow: hidden;
	text-align: left;
	position: relative;
	color: rgb(0, 0, 0);
	font-family: Roboto, Arial, sans-serif;
	-moz-user-select: none;
	font-size: 11px;
	background:#fff;
	padding: 4px;
	border-bottom-left-radius: 2px;
	border-top-left-radius: 2px;
	background-clip: padding-box;
	border: 1px solid rgba(0, 0, 0, 0.15);
	box-shadow: 0px 1px 4px -1px rgba(0, 0, 0, 0.3);
    width:16px; height:16px;
}
.gis-mapgj .gis-mapgj-hand img, .gis-mapgj .gis-mapgj-marquee img,.gis-mapgj .gis-mapgj-hand-hover img,.gis-mapgj .gis-mapgj-marquee-hover img{position: absolute; left: 3px; top: -48px; -moz-user-select: none; border: 0px none; padding: 0px; margin: 0px; width: 16px; height: 103px; cursor:pointer;}
.gis-mapgj .gis-mapgj-marquee img{top:-2px; left:4px;}
.gis-mapgj .gis-mapgj-hand-hover img{top:-74px;}
.gis-mapgj .gis-mapgj-marquee-hover img{top:-24px; left:4px;}
.gis-xqjz span{float:left; font-weight:bold; width:70px;}
.gis-xqjz ul{float:left;}
.gis-xqjz ul li input{margin-right:5px; margin-top:-2px; height:14px; width:14px;}
.gis-xqjz .gis-xqjz-div{ float:left; padding:5px 40px 5px 10px;}
.gis-xqjz .gis-xqjz-qy ul li{float:left; width:80px; padding:0px 0px 10px 0;}
.gis-xqjz .gis-xqjz-qy{ padding:20px 10px; border-top:1px #dddddd solid;}
.gis-xqjz .gis-xqjz-qy ul{float:left; display:block;}
.gis-xqjz .gis-bottom{border-top:1px #dddddd solid}
.gis-xqjz ul li{color:#5D5D5D;font-family:Arial, Helvetica, sans-serif;}
.gis-exporta p{margin:0px 0px 10px 8px; display:block; float:left;}
.gis-exporta ul li{float:left; margin-left:10px; margin-right:10px;}
.gis-exporta ul li input{margin:-2px 6px 0 0}
</style>
	<style type="text/css">
#faqbg{background-color:#666666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.3;filter:alpha(opacity=30);-moz-opacity:0.3;}
img{
	cursor:pointer;
	position:absolute;
}
.img_span{
	cursor:pointer;
	position:absolute;
	color:red;
}
.img_p{
position:absolute;
width:200px;
}
.gis-tk{width:200px;margin:0px; padding:0px;border-collapse:collapse; font-family:Arial; color:#5d5d5d;border-top:1px  solid #BFBFBF}
.gis-tk ul{float:left; height:auto; line-heigh:auto;background:none;}
.gis-tk ul li{background:none;float:none;text-align:left;height:24px;line-height:24px;}
.gis-tk ul li span{ float:left; width:78px; display:block; color:#000}

/*提示框*/
#tips-cont{  
    border-top: 1px solid #CCCCCC;  
    border-bottom: 1px solid #CCCCCC;  
    left: -3px;  
    top: -3px;  
        _margin-left: 1px; /*for IE 6 BUG*/  
    background-color: #CC0000;  
    color:white;
}  
#tips-cont div{  
    border-left: 1px solid #CCCCCC;  
    border-right: 1px solid #CCCCCC;  
    padding: 5px;  
    margin: 0px -1px;  
}  
.maxmixSize{
	background:none repeat scroll 0 0 #F5F4F1;
	border-bottom:1px solid #C8C8C8;
	border-left:1px solid #C8C8C8;
	border-right:1px solid #C8C8C8;
	border-radius:5px 5px 5px 5px;
	border-top:1px solid #C8C8C8;
	padding-left:10px;
	padding-top:2px;
	width:420px;
	height:25px;
	position:relative;
	z-index:9999;
	top:10px;
	float:right;
}
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


	<div class="right">
	<div style="float:left;">
	</div>
	  <!--========报表开始========= -->
	 <!--========工单详情开始========= -->
	  <div class="forms_gongdan">
	  <div class="forms">
	<!--========报表头部========= -->
	<#if serlist?? && serlist?size &gt; 0>
		<#list serlist as s>
			<#if s_index==0 >
				<#if s.verify?? && s.verify == 0>
					<div id="popup-tips" style="right:5px;bottom:5px;z-index:9999;position:fixed;background-color: #CCCCCC;">  
					    <div id="tips-cont" ><div>该工单还未审核</div></div>  
					</div> 
				</#if>
			</#if>
		</#list>
	</#if>
	<div class="forms_nav">
	  <ul>
	    <li class="forms_nav_now">工单</li>
	  </ul>
	  <samp><a href="#" onclick="show_hidden()"><img src="${application.getContextPath()}/images/zhankai.png"  id="a_img"/></a></samp>  </div>
	<!--========报表头部结束========= -->
	<!--========报表中间开始========= -->
	   
	    <div class="border_left">
	  <div class="forms_center">
	    <#if serlist?? && serlist?size &gt; 0>
	   <#list serlist as s>
		<#if s_index==0>
	  <div class="forms_gongdan" id="work_list">
	  <table class="mytable" id="temptable2" width="100%" height="58"   border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  		<td width="15%">工单流水号：</td>
	  		<td width="20%"><p><span id="ss_id">${s.serialno!}</span>
	  		<#if s.verify?? && s.verify == 0><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_0.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">未审核</span></#if>
	  		<#if s.verify?? && s.verify == 1><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_1.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">已通过</span></#if>
	  		<#if s.verify?? && s.verify == 2><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_2.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">未通过</span></#if>
			</p></td>
	  		<td width="15%">系统接单时间：</td>
	  		<td><#if (s.submitDatetime)??>${s.submitDatetime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
	  	</tr>
	  	<tr>
	  		<td>网络类型：</td>
	  		<td>${s.netWorktype!}</td>
	  		<td>最后测试时间：</td>
	  		<td><#if (s.testendtime)?? >${s.testendtime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
	  		<!--<td>受理路径：</td>-->
	  		<!--<td>${s.admissiblePath!}</td>-->
	  	</tr>
	  </table>
	  <table class="mytable" width="100%" id="temptable1" height="116"  style="display:none" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  		<td width="15%">工单流水号：</td>
	  		<td width="20%"><p><span id="ss_id">${s.serialno!}</span>
	  		<#if s.verify?? && s.verify == 0><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_0.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">未审核</span></#if>
	  		<#if s.verify?? && s.verify == 1><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_1.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">已通过</span></#if>
	  		<#if s.verify?? && s.verify == 2><span class='verify_sty' style="float:right;width:40px;color:white;text-align:center;margin-right:5px;background:url('${application.getContextPath()}/images/bg_2.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);">未通过</span></#if>
	  		</p></td>
	  		<td width="15%">系统接单时间：</td>
	  		<td name="exc_submitDateTime"><#if (s.submitDatetime)??>${s.submitDatetime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
	  	</tr>
	  	<tr>
	  		<td>网络类型：</td>
	  		<td name="exc_netType">${s.netWorktype!}</td>
	  		<td>最后测试时间：</td>
	  		<td name="exc_testDateTime"><#if (s.testendtime)?? >${s.testendtime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
	  	</tr>
	  	<tr>
	  		<td>测试区域：</td>
	  		<td name="exc_area">${s.areaname!}</td>
	  		<td>测试次数：</td>
  			<td name="exc_testTime">${s.testNumber!}</td>
	  	</tr>
	  	<tr style="display:none;">
	  		<td>派单员工号：</td>
	  		<td name="exc_workerid">${s.acceptHuman!}</td>
	  		<td>业务产品分类：</td>
	  		<td name="exc_operationClassify">${s.productName!}</td>
	  	</tr>
	  	<tr style="display:none;">
	  		<td>受理号码：</td>
	  		<td name="exc_acceptance">${s.acceptanceNumber!}</td>
	  		<td>用户品牌：</td>
	  		<td name="exc_clientid">${s.usersBrand!}</td>
	  	</tr>
	  	<tr style="display:none;">
	  		<td>客户联系电话：</td>
	  		<td name="exc_customer">${s.acceptanceNumber!}</td>
	  		<td>受理路径：</td>
	  		<td name="exc_dealPath">${s.admissiblePath!}</td>
	  	</tr>
	  	<tr>
	  		<td>详细投诉地址：</td>
	  		<td colspan="3"  name="exc_url">${s.problemsAddress!}</td>
	  	</tr>
	  	<tr style="display:none;">
	  		<td>投诉内容：</td>
	  		<td colspan="3" name="exc_contentrs">${s.complaint!}</td>
	  	</tr>
	  	<tr style="display:none;">
	  		<td>模块内容：</td>
	  		<td colspan="3" name="exc_module">${s.templateContent!}</td>
	  	</tr>
	  </table>
	</div>
	  </#if>			         
	 </#list>
	</#if>
	
	  </div>
	  <!--========报表中间结束========= -->
	  
	  
	  
	  
	  <div class="clear"></div>
	</div>
	<div class="all_list_bottom"><span></span><samp></samp></div>
	<!--========报表结束========= -->
	  </div>
	  
	  
	  
	
	  </div>
	   
	  
	
	  
	  
	<!--========工单详情结束========= -->
	  <div class="forms">
	<!--========报表头部========= -->
	    <div class="forms_nav" id="net_id">
	  <ul>
	    <li class="<#if netType?? && netType==-1>forms_nav_now netType</#if> netclass" netValue="-1">全网</li>
		<li class="<#if netType?? && netType==1>forms_nav_now netType</#if> netclass" netValue="1">GSM</li>
		<li class="<#if netType?? && netType==2>forms_nav_now netType</#if> netclass" netValue="2">WCDMA</li>
			      
	  
	  </ul>
	
	  <samp> <#if flowlist?? && flowlist?size &gt; 0>
	     <a href="#" style="width:60px;">
	    <img onclick="span_choose()" src="${application.getContextPath()}/images/contrast.png" / style="margin-right:8px;"></a>
	    <input type="hidden" id="fnames" />
	    <input type="hidden" id="fids" />
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
	     <#else>
	    </#if>
	  <a href="#" class="reportExcel"><img src="${application.getContextPath()}/images/export.png" /></a><a href="#"><img onclick="window.print()" src="${application.getContextPath()}/images/print.png" /></a></samp> </div>
	  
	  <input type="hidden" value="${areaid!}" id="areaid"/>
	  <input type="hidden" value="${s_id!}" id="s_id"/>
	<!--========报表头部结束========= -->
	<!--========报表中间开始========= -->
	<div class="border_left">
	  <div  style="width:auto;height:30px;float:right;margin:15px 10px 0 0;">
	  <input type="hidden" value="" id="s_f_id"/>
	  <select id="SelectBox"  style="width:400px;" class="easyui-combobox" data-options="editable:false,panelHeight: 'auto'">
	  		<option value="-1111">请选择流水号</option>
			  <#if flowlist?? && flowlist?size &gt; 0>
		          <#list flowlist as s>
				          <#if s_index==0>
				           <option value=${s.flowid!} selected="selected">
				           <#else>
				            <option value=${s.flowid!} ></#if><#if (s.testtime)??>${s.testtime?string("yyyyMMdd_HHmmss")}</#if><#if s.netsystem?? && s.netsystem==1>_GSM</#if><#if s.netsystem?? &&s.netsystem==2>_WCDMA_锁频</#if><#if s.netsystem?? &&s.netsystem==3>_WCDMA_自由模式</#if><#if s.teststatus?? && s.teststatus==1>_IDLE</#if><#if s.teststatus?? && s.teststatus==2>_CS</#if><#if s.teststatus?? && s.teststatus==3>_PS</#if><#if s.calltype?? && s.calltype != 0><#if s.calltype == 1>_短呼</#if><#if s.calltype == 2>_长呼</#if></#if><#if s.ftpUpdown?? && s.ftpUpdown != 0><#if s.ftpUpdown == 1>_上行</#if><#if s.ftpUpdown == 2>_下行</#if></#if><#if s.inside?? && s.inside==0>(室外)</#if><#if s.inside?? && s.inside==1>(室内)</#if></option>  
		          </#list>
			  </#if>
	 </select>
	  
	      </div>
	  <!--网络状态选择-->
	   <a href="javascript:;" id="nettype_a_id"><span id="workFrame"></span></a>
	   <!--业务状态选择-->
	  <a href="javascript:;" id="testtype_a_id"><span id="testworkFrame"></span></a>
	  <!--下拉隐藏-->
	 <a id="a_select_id" href="javascript:;" onclick="choose()"></a>
	  <!--========报表头部二级========= -->
	<div class="forms_nav_erji">
	<ul>
	         
	          <li style="cursor:pointer" class="<#if testType?? && testType==-1>forms_nav_erji_now testType</#if> testclass" netValue="-1">全业务</li>
			  <li style="cursor:pointer" class="<#if testType?? && testType==1>forms_nav_erji_now testType</#if> testclass" netValue="1">IDLE</li>
			  <li style="cursor:pointer" class="<#if testType?? && testType==2>forms_nav_erji_now testType</#if> testclass" netValue="2">CS</li>
			  <#if netType?? && netType!=1>
	          <li id="ps_id" style="cursor:pointer" class="<#if testType?? && testType==3>forms_nav_erji_now testType</#if> testclass" netValue="3">PS</li>
	          </#if>
	
	</ul>
	</div>
	<!--========报表头部二级结束========= -->
	 <!--测试结果开始 -->
	    <div class="clear"></div>
	
	
	    <div class="forms_csjg" id="result_ul">
	    <div id="indoormap" class="easyui-dialog" data-options="
                buttons: '#dlg-bb',
				modal:true ,
				closed:true
				">
		</div>
		<div id="dlg-bb">
			<a href="javascript:void(0)" class="easyui-linkbutton saveedit" style="display:none">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#indoormap').dialog('close')" style="display:none">取消</a>
		</div>
		<div id="isverify" class="easyui-dialog"  data-options="
        		buttons: '#dlg-isverify',
				modal:true ,
				closed:true,
				height: 150,
				width: 400,
				title: '审核',
				">
				<#if serlist?? && serlist?size &gt; 0>
					<#list serlist as s>
						<#if s_index==0>
				<div id="isverify_neirong" style="display:none;height:79px;line-height:79px;">
					<span><input type="radio" value="0" name="isverify_val" style="margin-left:50px;margin-bottom:3px;" <#if s.verify?? && s.verify == 0>checked="true"</#if>>  未审核</span>
					<span><input type="radio" value="1" name="isverify_val" style="margin-left:60px;margin-bottom:3px;" <#if s.verify?? && s.verify == 1>checked="true"</#if>>  通过</span>
					<span><input type="radio" value="2" name="isverify_val" style="margin-left:60px;margin-bottom:3px;" <#if s.verify?? && s.verify == 2>checked="true"</#if>>  未通过</span>
				</div>
						</#if>
					</#list>
				</#if>
		</div>
		<div id="dlg-isverify">
			<a href="javascript:void(0)" class="easyui-linkbutton saveVerify">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton closeVerify" onclick="javascript:$('#isverify').dialog('close')">取消</a>
		</div>
	    </div> 
	    <!--测试结果结束 -->
	
	  <div class="forms_center">
	    <!--指标数据 -->
	     <#if flowlist?? && flowlist?size &gt; 0>
	    <div class="forms_data">
	    	<div class="svg-div-title">	    	
				<input type="hidden" id="undata" />
				<input type="hidden" id="undata_id" />
	           <span id="s_sel1" style="display:none"> <select id="SelectBox1"  class="easyui-combobox" style="width:100px;" data-options="editable:false,panelHeight: 'auto'"></select></span>
		       <span id="s_sel2" style="display:none"> <select id="SelectBox2"  class="easyui-combobox" style="width:100px;" data-options="editable:false,panelHeight: 'auto'"></select></span>
	  <input type="button"  class="twoG" title="轨迹叠加"/>	
	  <div style="float:right;margin-right:10px;" id="sum_count"></div>
	  <div id="zhibiao" class="easyui-dialog" title="测试指标选择" style="padding:10px" data-options="
				buttons: '#tree-but',
				modal:true ,
				closed:true
				">
				<ul id="tul"></ul>
	          </div>
			<div id="tree-but">
				<a href="javascript:void(0)" class="easyui-linkbutton treeb" style="display:none">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#zhibiao').dialog('close')" style="display:none">取消</a>
			</div>
	</div>
	<div style="float:left;position:relative" id="div_czj">
			<div  class="div_yyy" id="div_left">

			<div id="div_sn" style="z-index:800;position: relative;float: right; right:0;width:20px;top:21px">
			<img id="expand" src="${application.getContextPath()}/images/map_big.png" />
			<img id="narrow" src="${application.getContextPath()}/images/map_small.png" style="margin-top:25px;"/>
			
			</div>
	      <div class="forms_data_flash" id="div_point"style="height:650px; width:97.8%;display: none;position:absolute;">
	      
	         <!---->
	         <div class="svg-div" style="height:690px; width:100%;">
				
				    <div id="parentContainer" class="svg-div-main" style="width:100%;height:690px;overflow:hidden;position:absolute;" >
						<div id="container"  class="svg-main" style="position:absolute;background-color:#fff;" >
							
						</div>
					</div>
				</div>
	      </div>
	      </div>
	    </div>
	      
<!--图例-->	      
<div class="case_tu">
<div style="border-bottom:1px #979797 solid;border-right:1px #979797 solid; float:left;background:#f0f0f0;">
<p id="img_demo"></p>
<div class="clear"></div>
<div id="demo_div" style="float;left;">
</div>
</div>
</div>


	<!--指标数据--右边开始 -->
	<div class="case_zhi" style="display:none">
    <span onclick="isShowChoose()"></span>
	<div id="div_page"></div>
	<!--指标数据--右边结束 -->
	</div>
	<div onclick="isShowChoose()" style="background:url(${application.getContextPath()}/images/case_zhi.png) no-repeat; width:20px; height:20px; z-index:999; float:right;position:relative;cursor:pointer" id="add_img"></div>
	    <!--全频指标数据 -->
	    <!--全频指标数据结束 -->
	       <!--对比开始 -->
	    <div class="clear"></div>
	    <div class="duibi">
	    <h6></h6>
	    <ul id="compare_ul">
	
	    </ul>
	    <div class="clear"></div>
	    </div>
	    <!--对比结束 -->
	    <!--flash柱状图开始 -->
	    <div class="histogram"style="font-family:Arial,Helvetica,sans-serif;color:#999999" id="histogram1">
	      
	      
	    </div>
	    <!--flash柱状图结束 -->
	    
	      <div class="forms_data_all" id="httpping_div">
	      </div>      
	       
	    <!--FTP FLASH-->
	    <div class="histogram"style="font-family:Arial,Helvetica,sans-serif;color:#999999" id="histogram2">
	      
	      
	    </div>
	    
	  </div>
	  <#else>
			  
			    <!--无数据展示-->
			     <div class="forms_data" style="padding-top:60px;padding-bottom:120px">
			 	   <div class="none_deteddd" ></div>
			    </div>
			  </#if>
			  </div>
	  <!--========报表中间结束========= -->
	  	<div class="all_list_bottom"><span></span><samp></samp></div>
	  <div class="clear"></div>
	</div>
	
	<!--========报表结束========= -->
	
	
		</div>	  
	  </div>
	</div>
</@body>

<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=WrMcR9V4PmcYTP226GGd6VRM"></script>
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/sj.base.library.1.0.1.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/highcharts1.0.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/ReportForms.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/raphael.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/map.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/report.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/utile_report.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/graphicEle.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/scatter.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/reportTask/trck.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/ejs_production.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jQueryRotate.js"></script>
	<script src="${application.getContextPath()}/js/commUtils.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/reportTask/indoorMap_google.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/reportTask/outdoorMap_google.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/reportTask/station_google.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/reportTask/reportExcel.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var serialno = <#if serialno?? && serialno!= ''>'${serialno!}'<#else>''</#if>;
    document.body.load=initializePage();
	$(document).ready(function(){
		setTimeout(function(){
			$("#popup-tips").hide(1000);
			},5000);
	});
	</script>
</@script>
