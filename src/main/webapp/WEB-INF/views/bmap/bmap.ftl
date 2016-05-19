<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="测试" description="测试description">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">

</@head>
<@body>
<!--弹出层开始-->
	<!--背景罩-->
	<div id="faqbg"></div>
	<!--信息层-->
<div data-options="handle:'#faqdiv2'" class="easyui-draggable gis-kuangxuan" id="faqdiv" style="display:none;">
		<div class="gis-kuangxuan-close"id="close"><span></span></div>
		<div class="gis-kuangxuan-exit"id="downloadchoose"><span></span>导出</div>
	</div>
   
<div data-options="handle:'#faqdiv2'" class="easyui-draggable gis-kuangxuan" id="fadown" style="position:absolute;display:none;">
		<div class="gis-kuangxuan-close"id="regiondown">评价导出</div>
        <div style="float:left; color:#b6b6b6;">|</div>
		<div class="gis-kuangxuan-exit"id="c_info_down">小区导出</div>
	</div>
	<!--弹出层结束-->
	<div class="right" style="margin-top:100px;padding-top:0px;">

  <div class="work_list" style="width:100%;background:#f0f0f0;float:left;">
   <form action="${application.getContextPath()}/workorder/workorderlist" id="searchForm" method="post">
   
   <div class="gis_nav">
<ul>
<#if (allowsearch?? && allowsearch == 'search')||(allowdownload?? && allowdownload =='download')>
<li id="query_id"><span class="gis_search"></span>查询条件</li>
</#if>
<#if buttons?? && buttons?size &gt;0>
  <#list buttons as button>
  	<#if (button.btntype)?? && button.btntype == '2nd'>
<li id="samp_id"><span class="gis_twosearch"></span>二次查询</li>
	</#if>
	<#if (button.btntype)?? && button.btntype == 'station'>
<li id="c_info_li"><span class="gis_information"></span>小区信息<i class="c-icon"></i>
<div class="gis_histogram-menu" id="gis_cell_loading" style="display:none;">
        <div class="gis_histogram-mainmenu">
          <ul>
            <li id="station">小区加载</li>
            <li  class="show_li"><span></span>小区显示
              <div id="net_div" class="gis_histogram-mainmenu gis_histogram-mainmenu-menu " style="display:none">
                <ul>
                  <li  class="show_li"><span></span>WCDMA
                    <div id="wcdma_pl" class="gis_histogram-submenu" style="display:none">
                      <ul>
                        <li class="isgou" name="psc" id="pp_sc">PSC</li>
                        <li class="isgou" style="border-bottom:none;" name="g3_name" id="jizhan_g3">基站名称</li>
                      </ul>
                    </div>
                  </li>
                  <li class="show_li" style="border-bottom:none;"><span></span>GSM
                  
                  <div id="gsm_pl" class="gis_histogram-submenu" style="display:none">
                      <ul>
                        <li class="isgou" name="bcch" id="bb_ch">BCCH</li>
                        <li class="isgou" style="border-bottom:none;" name="g2_name" id="jizhan_g2">基站名称</li>
                      </ul>
                    </div>
                  
                  </li>
                </ul>
              </div>
            </li>
            <li id="showlinqu" style="border-bottom:none;"><span></span>邻区加载
              <div class="gis_histogram-submenu submenu-p" id="linqu_ul" style="display:none;">
                <ul>
                  <li class="isgou" id="tp">
                    <p class="submenu-p1"></p>
                    <span></span>同频</li>
                  <li class="isgou" id="yp">
                    <p class="submenu-p2"></p>
                    <span></span>异频</li>
                  <li style="border-bottom:none;" class="isgou" id="ywl">
                    <p class="submenu-p3"></p>
                    <span></span>异系统</li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
      </div>
</li>
	</#if>
<!--
<li id="station"><span class="gis_chart"></span>加载基站</li>
<li id=""><span class="gis_chart"></span><input type="checkbox" checked="true" id="tp"/>同频<input type="checkbox" checked="true" id="yp"/>异频<input type="checkbox" checked="true" id="ywl"/>异网络<input type="checkbox" checked="true" id="pp_sc"/>PSC</li><input type="checkbox" checked="true" id="ji_zhan"/>名称</li>-->
	<#if (button.btntype)?? && button.btntype == 'histogram'>
<li id="show_flash"><span class="gis_histogram"></span>柱状图</li>
	</#if>
	<#if (button.btntype)?? && button.btntype == 'legend'>
<li id="img_demo"><span class="gis_chart"></span>图列</li>
	</#if>
</#list>
</#if>
<#if allowdownload?? && allowdownload =='download'><li  id="exports"><span class="gis_export" ></span>导出<i class="c-icon"></i>
<div class="gis_histogram-menu" id="gis_exports" style="display:none;position:absolute;margin-left:-10px;">
        <div class="gis_histogram-mainmenu">
          <ul>
            <li id="export_data" style="width:70px">评价导出</li>
            <li id="export_cell" style="width:70px">小区导出</li>
            <li id="export_near" style="width:70px">邻区导出</li>
          </ul>
        </div>
      </div>
</li></#if>
<li id="quan_ping"><span class="gis_screen"></span>全屏</li>
</ul>
<div class="clear"></div>
<div id="shad_h"></div>
</div>

      <!-----搜索框 开始 ------->
      <div class="wls" style="float:none;margin-bottom:0px;">
      <div class="wlf" id="wlf_query" style="width:98%;margin-top:0px;padding-top:10px;margin-left:1%">
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
		        <option value="1" <#if datatype?? && datatype == '1'>selected="selected"</#if>>测试时间</option>
		        <option value="2" <#if datatype?? && datatype == '2'>selected="selected"</#if>>接单时间</option>
		     </select> 
	    </div>
        <span class="wlfspan">区域：</span>
	    <div class="" style="float:left;margin-right:10px;">
          <h6></h6>
          <h5>
          <select id="areas" class="easyui-combobox areas"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option selected="selected"><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="areatext" name="areatext" value="${areatext!}"/>
          <input type="hidden" name="areaids" value="${areaids!}" id="areaids"/>
          </h5><h4></h4>
         </div>
             <span class="wlfspan">	测试环境：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="ii" class="easyui-combobox" name="inside" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if inside?? && inside == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if inside?? && inside == '1'>selected="selected"</#if>>室内</option>
		        <option value="0" <#if inside?? && inside == '0'>selected="selected"</#if>>室外</option>
		     </select> 
	    </div>
	       <span class="wlfspan">网络制式：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="nettype" class="easyui-combobox" name="nettype" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if nettype?? && nettype == '-1'>selected="selected"</#if>>全网络</option>
		        <option value="1" <#if nettype?? && nettype == '1'>selected="selected"</#if>>WCDMA</option>
		        <option value="2" <#if nettype?? && nettype == '2'>selected="selected"</#if>>GSM</option>
		     </select> 
	    </div>
	        <span class="wlfspan">测试网络：</span>
	    <div class="" style="float:left;margin-right:10px;">
          <h6></h6>
          <h5>
          <select id="testnets" class="easyui-combobox testnet"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if senctext?? && senctext !=''>${senctext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="testnetName" name="testnetName" value="${testnetName!}"/>
          <input type="hidden" name="testnet" value="${testnet!}" id="testnet"/>
          </h5><h4></h4>
         </div>

          <div class="wlf" style="width:100%;margin:10px 0px 0px 0px">
           <span class="wlfspan">业务类型：</span>
	    <div class="" style="float:left;margin-right:16px;">
          <h6></h6>
          <h5>
          <select id="testtypes" class="easyui-combobox testtypes"  style="width:84px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if testtypeText?? && testtypeText !=''>${testtypeText!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="testtypeText" name="testtypeText" value="${testtypeText!}"/>
          <input type="hidden" name="testtype" value="${testtype!}" id="testtype"/>
          </h5><h4></h4>
         </div>
         <span class="wlfspan">路测指标：</span>
	    <div class="search_input" style="width:84px;height:28px;margin-right:14px;">
		      <select id="kk" class="easyui-combobox" name="kpi" style="width:84px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
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
	       <span class="wlfspan">评价等级：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="gg" class="easyui-combobox" name="grad" style="width:75px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
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
          <select id="sencs" class="easyui-combobox sencs"  style="width:65px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if senctext?? && senctext !=''>${senctext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="senctext" name="senctext" value="${senctext!}"/>
          <input type="hidden" name="senceids" value="${senceids!}" id="senceids"/>
          </h5><h4></h4>
         </div>
         
	   
        <span class="wlfspan">任务类型：</span>
	    <div class="search_input" style="width:80px;height:28px;">
		      <select id="jj" class="easyui-combobox" name="jobtype" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
		        <option value="-1" <#if jobtype?? && jobtype == '-1'>selected="selected"</#if>>全部</option>
		        <option value="1" <#if jobtype?? && jobtype == '1'>selected="selected"</#if>>投诉工单</option>
		     </select> 
	    </div>
	    <span class="wlfspan">工单号：</span>
        <div class="search_input" style=" margin-right: 10px;width:231px">
          <h6></h6>
          <h5>
          <input  name = "sernos" value="${sernos!}" class="easyui-validatebox" validType="length[0,25]"  maxlength="25" invalidMessage="不能超过25个字符！" type="text" id="sernos" style="width:221px"/>
          </h5><h4></h4>
         </div>
         <samp id="export" style="display:none;float:right;margin:-20px 5px 0 0;">导出</samp>
         <#if allowsearch?? && allowsearch == 'search'>
	    <samp id="serch" style="float:right;margin:-20px 5px 0 0;">查询</samp>
	   	 </#if>
      </div>
      <!--采样点-->
      <div class="wlf" style="width:100%;">
           <span style="float: right; margin-top: -25px;" class="wlfspan" id="caiyang">采样点：0</span>
      </div>
      </div>
     </form> 
      <div class="clear"></div>
      <input type = "hidden" id="diatype" value="hi"/>
       <!--图例-->	      
			<div class="case_tu" style="position:absolute;margin-top:0px;">
				<div style="border-bottom:1px #979797 solid;border-right:1px #979797 solid; float:left;background:#f0f0f0;">
				<p style="background-image:none;width:0px;"></p>
				<div class="clear"></div>
				<div id="demo_div" style="float;left;"></div>
				</div>
			</div>
			<div class="map_two">
				<div class="map_two_search" style="display:none;"><p>工单号或地址:</p>
				<div style="margin-right: 10px;width:220px" class="search_input">
				          <h6></h6>
				          <h5>
				          <input name="secendSerno" class="easyui-validatebox" validType="length[0,25]" maxlength="25" invalidMessage="不能超过25个字符！"  type="text" id="secendSerno" style="width:210px" value="">
				    </h5><h4></h4>
				         </div>
				 <div class="bottom_btn yfpz_btn" style="float: right; margin-right: 5px; margin-top: 2px;">
      <a href="javascript:void(0)" id="queryscend" style="" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">查 询</span></span></a>
    </div>
				</div>
				</div>
          </div>
          <div class="gis-mapgj">
			  <div id="hand" class="gis-mapgj-hand" style="display:none;" title="拖拽"><img src="${application.getContextPath()}/images/drawing.png"></div>
			  <div id="hand_hover" class="gis-mapgj-hand-hover" title="拖拽"><img src="${application.getContextPath()}/images/drawing.png"></div>
			  <#if buttons?? && buttons?size &gt;0>
				  <#list buttons as button>
				  	<#if (button.btntype)?? && button.btntype == 'marquee'>
			  <div id="marquee" class="gis-mapgj-marquee" title="框选"><img src="${application.getContextPath()}/images/drawing.png"></div>
			  <div id="marquee_hover" class="gis-mapgj-marquee-hover"  style="display:none;" title="框选"><img src="${application.getContextPath()}/images/drawing.png"></div>
		  			</#if>
		  		  </#list>
		  	  </#if>
		  </div>
      <div class="all_list" id="div_map" onmousedown="mousedown(event);" style="width:100%; position: absolute; "></div>
      </div></div>
      
	       

      <div id="flash" style="display:none;float:left;background-color:#e4e4e4">
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
<div id="rightMenu" class="easyui-menu" style="width: 120px; display:none;">
    <div id="centerItme" onclick="setAreaCenter(this);">
                    设为中心经纬度
    </div> 
    <div id="rightMenu_cancel">
                  取消
    </div>
</div>
<div id="select-testnet" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_testnet">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#testnetdlg').dialog('close')">取消</a>
</div>
<div id="celldlg" class="easyui-dialog" title="选择区域" style="padding:10px"
		data-options="
			buttons: '#select-celldlg',
			modal:true ,
			closed:true
		">
</div>
<div id="select-celldlg" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_cell">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#celldlg').dialog('close')">取消</a>
</div>
<div id="nearcelldlg"  class="easyui-dialog" title="邻区导出" style="padding:10px"
		data-options="
			buttons: '#select-nearcelldlg',
			modal:true ,
			closed:true,height:150,width: 320
		">
		<div class="gis-exporta" style="display:none;">
		<p>小区类型：</p>
		<ul>
		<li>
		<div class="search_input" style="width:80px;height:28px;">
		      <select id="report_cell_type" class="easyui-combobox" name="datatype" style="width:75px;height:26px;float:letf;" data-options="editable:false,panelHeight: 'auto'">  
		        
		     </select> 
	    </div>
		</li>
		</ul>
		</div>
		<div class="clear"></div>
		<div class="gis-exporta" style="display:none;">
			<p>邻区类型：</p>
			<ul id="near_type">
			<li><input name="" type="checkbox" value="0" />同频</li>
			<li><input name="" type="checkbox" value="1" />异频</li>
			<li><input name="" type="checkbox" value="2" />异系统</li>
			</ul>
			
		</div>
</div>
<div id="select-nearcelldlg" style="display:none">
	<a href="javascript:void(0)" class="easyui-linkbutton sel_nearcell">导出</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#nearcelldlg').dialog('close')">取消</a>
</div>
<div id="cond"  class="easyui-dialog" title="选择条件" style="padding:10px;"
		data-options="
			buttons: '#select-cond',
			modal:true ,
			closed:true">

</div>


</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/ejs_production.js"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=WrMcR9V4PmcYTP226GGd6VRM"></script>
	<script src="${application.getContextPath()}/js/gisgrad_baidu.js" type="text/javascript"></script>
	<script>

		var contextPath = '${application.getContextPath()}';
		
	</script>
</@script>
