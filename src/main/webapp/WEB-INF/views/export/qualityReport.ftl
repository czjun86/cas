<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
	<style type="text/css" >
		.red{
			background-color:red;
		}
		.green{
			background-color:#92d14f;
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
      <!-----搜索框 开始 ------->
  <div class="wls">
  <div class="wlf">
   <form action="${application.getContextPath()}/export/qualityreport" id="searchForm" method="post">
	    <#include "search.ftl"/>
	    <samp onclick="search()" style="margin-left:20px;">查询</samp>
	    <samp onclick="exportReport()" style="margin-left:15px;">导出</samp>
	    <samp onclick = "showError()" id="errorBtn" style="display:none;"></samp>
	    <iframe id="download" src="#" style="display:none;">&nbsp;</iframe>
     </form>
    </div>
  <div class="wlf" style="float:right;margin-right:1%;">
	      <form action="${application.getContextPath()}/export/setValue" id="setValueForm" method="post">
			<span class="wlfspan" style="margin-left:px;">累计起始时间：</span>
			<div class="search_input" style="width:148px;">
				 <h6></h6>
		      <h5><input name= "qualdate" value="${qualdate!}" oldVal = "${qualdate!}" id="qualdate" type="text" class="Wdate"  style="width:138px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy.MM.dd HH:mm:ss'})" />
		   		 </h5><h4></h4> 
		    </div>
		    <span class="wlfspan" style="margin-left:15px;">及时率(小时)：</span>
			<div class="search_input" style="width:80px;">
		      <h6></h6>
		      <h5>
		      <input name="timelyrate" type="text" value="${timelyrate!}" oldVal="${timelyrate!}" style="width:70px;" maxLength="6" id="timelyrate"/>
		      </h5><h4></h4>
		      </div>
		    <samp id="setBtn" style="margin-left:15px;">设置</samp>
		   </form>
	   </div>
  </div>
</div>

 <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="all_list_top1"><span></span><samp></samp></div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yybb_top"><span>移动网络投诉处理质量${typeVal!'X'}报<#if titleVal??> - <label style="font-size:26px;">${titleVal!}</label></#if></span>
        <p>
        	投诉处理质量综合评分最高<#if top1??><label style="font-weight:bolder;color:#00FF00;"><#if top1.comp_score_max?? && top1.comp_score_max!=""><#list top1.comp_score_max?split("、") as val><lable title="${val?substring(val?index_of("（") + 1,val?index_of("）"))}">${val?substring(0,val?index_of("（"))}（#{val?substring(val?index_of("（") + 1,val?index_of("）"))?number;M2}）<#if val_index != top1.comp_score_max?split("、")?size - 1>、</#if></lable></#list></#if></label><#else>XX</#if>，
        	最低<#if top1??><label style="font-weight:bolder;color:#FF0000;"><#if top1.comp_score_min?? && top1.comp_score_min!=""><#list top1.comp_score_min?split("、") as val><lable title="${val?substring(val?index_of("（") + 1,val?index_of("）"))}">${val?substring(0,val?index_of("（"))}（#{val?substring(val?index_of("（") + 1,val?index_of("）"))?number;M2}）<#if val_index != top1.comp_score_min?split("、")?size - 1>、</#if></lable></#list></#if></label><#else>XX</#if>；
        	累计实测率最高<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_max_rate!}</label><#else>XX</#if>，
        	最低<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_min_rate!}</label><#else>XX</#if>；
                                          累计真正解决率最高<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_solve_rate_max!}</label><#else>XX</#if>，
		             最低<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_solve_rate_min!}</label><#else>XX</#if>；
		            累计工单滞留率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_delay_rate_min!}</label><#else>XX</#if>，
		            最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_delay_rate_max!}</label><#else>XX</#if>；
		           累计工单驳回率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_reject_rate_min!}</label><#else>XX</#if>，
		            最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_reject_rate_max!}</label><#else>XX</#if>； 
		            累计工单超时率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_over_rate_min!}</label><#else>XX</#if>，
		            最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_over_rate_max!}</label><#else>XX</#if>；         
		            累计工单重派率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_send_rate_min!}</label><#else>XX</#if>， 
		            最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_send_rate_max!}</label><#else>XX</#if>；
		            累计工单重复投诉率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_complaint_rate_min!}</label><#else>XX</#if>， 
		            最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_complaint_rate_max!}</label><#else>XX</#if>；          
		           累计工单升级率最低<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.total_upgrade_rate_min!}</label><#else>XX</#if>，
		           最高<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.total_upgrade_rate_max!}</label><#else>XX</#if><#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>；${typeVal!'X'}测试及时率最高<#if top1??><label style="font-weight:bolder;color:#00FF00;">${top1.curr_test_timely_max!}</label><#else>XX</#if>，
    	           最低<#if top1??><label style="font-weight:bolder;color:#FF0000;">${top1.curr_test_timely_min!}</label><#else>XX</#if></#if>。	          
		            
        </p>
      </div>
      <!-- -->
      <div class="yybb_left">
        <div class="yybb_left_top" style='border-bottom:0px solid #828282;height:auto;'> 
        	<div>
			<ul>
				<li style="width:230px;height:69px;line-height:69px;">区域</li>
				<li class="yybb_bj" style="width:230px">投诉处理质量综合评分</li>
				<li style="width:230px">投诉处理质量综合排名</li>
				<li class="yybb_bj" style="width:230px">投诉处理质量考核得分</li>
			</ul>
			</div>
			<div class="clear"></div>
        	<span style="margin-top:5px;border-bottom:1px;<#if type?? && show?? && (show ==1 && type!=1) ||  show == 0><#else>line-height:20px;</#if>">投诉实测情况统计</span>
        	<span style="padding-top:300px;float:left;margin-top:<#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>209<#else>174</#if>px; border-top:1px solid #A2A2A2;position:absolute;">工单调度处理管控</span>
        	<#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
        	<span style="text-align:center;border-bottom:1px solid #A2A2A2;height:59px;margin-top:2169px;position:absolute;font-size:14px;border-top:1px solid #A2A2A2;padding-left:5px;width:58px;padding-top:10px">投诉测试评价</span>
        	<span style="text-align:center;border-bottom:1px solid #A2A2A2;height:59px;margin-top:2239px;position:absolute;font-size:14px;border-top:1px solid #A2A2A2;padding-left:5px;width:58px;padding-top:10px">投诉测试改善比例</span>
        	</#if>
          <ul>
            <li>累计需实测工单量</li>
            <li class="yybb_bj">${typeVal!'X'}需实测工单量</li>
            <li>累计实测量</li>
            <li class="yybb_bj">${typeVal!'X'}实测量</li>
            <li class="red">累计实测率</li>
            <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
            <li class="yybb_bj red">${typeVal!'X'}测试及时率</li>
            </#if>
            <li>累计网络投诉工单量</li>
            <li class="yybb_bj">累计真正解决工单量</li>
            <li class="red">累计真正解决率</li>
            <li>${typeVal!'X'}网络投诉工单量</li>
            <li class="yybb_bj">${typeVal!'X'}真正解决工单量</li>
            <li>累计优化真正解决量</li>
            <li class="yybb_bj green">累计优化真正解决比</li>
            <li>${typeVal!'X'}优化真正解决量</li>
            <li class="yybb_bj green">${typeVal!'X'}优化真正解决比</li>
            <li>累计建设真正解决量</li>
            <li class="yybb_bj green">累计建设真正解决比</li>
            <li>${typeVal!'X'}建设真正解决量</li>
            <li class="yybb_bj green">${typeVal!'X'}建设真正解决比</li>
            <li>累计维护真正解决量</li>
            <li class="yybb_bj green">累计维护真正解决比</li>
            <li>${typeVal!'X'}维护真正解决量</li>
            <li class="yybb_bj green">${typeVal!'X'}维护真正解决比</li>
            <li>累计其它真正解决量</li>
            <li class="yybb_bj green">累计其它真正解决比</li>
            <li>${typeVal!'X'}其它真正解决量</li>
            <li class="yybb_bj green">${typeVal!'X'}其它真正解决比</li>
            <li>累计工单滞留量</li>
            <li class="yybb_bj red">累计工单滞留率</li>
            <li>累计优化工单滞留量</li>
            <li class="yybb_bj green">累计优化工单滞留比</li>
            <li>累计建设工单滞留量</li>
            <li class="yybb_bj green">累计建设工单滞留比</li>
            <li>累计维护工单滞留量</li>
            <li class="yybb_bj green">累计维护工单滞留比</li>
            <li>累计工单驳回量</li>
            <li>${typeVal!'X'}工单驳回量</li>
            <li class="yybb_bj red">累计工单驳回率</li>
            <li>累计优化工单驳回量</li>
            <li class="yybb_bj green">累计优化工单驳回比</li>
            <li>${typeVal!'X'}优化工单驳回量</li>
            <li class="yybb_bj green">${typeVal!'X'}优化工单驳回比</li>
            <li>累计建设工单驳回量</li>
            <li class="yybb_bj green">累计建设工单驳回比</li>
            <li>${typeVal!'X'}建设工单驳回量</li>
            <li class="yybb_bj green">${typeVal!'X'}建设工单驳回比</li>
            <li>累计维护工单驳回量</li>
            <li class="yybb_bj green">累计维护工单驳回比</li>
            <li>${typeVal!'X'}维护工单驳回量</li>
            <li class="yybb_bj green">${typeVal!'X'}维护工单驳回比</li>
            <li>累计工单超时量</li>
            <li>${typeVal!'X'}工单超时量</li>
            <li class="yybb_bj red">累计工单超时率</li>
            <li>累计工单重派量</li>
            <li>${typeVal!'X'}工单重派量</li>
            <li class="yybb_bj red">累计工单重派率</li>
            <li>累计重复投诉量</li>
            <li>${typeVal!'X'}重复投诉量</li>
            <li class="yybb_bj red">累计重复投诉率</li>
            <li>累计工单升级量</li>
            <li class="yybb_bj">${typeVal!'X'}工单升级量</li>
            <li class="red">累计工单升级率</li>
            <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
            <li class="yybb_bj">${typeVal!'X'}3G测试评价改善评分 </li>
            <li>${typeVal!'X'}2G测试评价改善评分</li>
            <li class="yybb_bj">${typeVal!'X'}3G测试评价改善比例</li>
            <li>${typeVal!'X'}2G测试评价改善比例</li>
            </#if>
          </ul>
        </div>
      </div>
      <div class="yybb_right">
      	<div id = "overflow" val="<#if qr??>${qr.areaname?size}<#else>13</#if>" style="float:left;">
        <div class="yybb_right_top">
        <#if qr??>
        		<#assign count = 0> 
        		<#list groups as group>
        			<#assign num = 0> 
        			<#list qr.areaid as areaid>
        				<#if areaid_index &lt; qr.areaid?size - groupnum>
							<#if group.areas?index_of(areaid+'') &gt;=0>
								<#assign num = num + 1 > 
							</#if>
						</#if>
					</#list>
					<#if num &gt; 0>
						<ul style="float:left;">
							<li class="top-group" num="${num!}">${group.groupname}</li>
							<#list qr.areaname as val>
								<#if val_index &gt;= count  && val_index &lt; count + num>
									<li <#if val_index == count + num - 1>class="top-last-li"</#if>>${val!}</li>
								</#if>
							</#list>
						</ul>
					</#if>
					<#assign count = count + num> 
        		</#list>
    			<#if count + groupnum != qr.areaname?size>
    			<ul style="float:left;">
					<li class="top-group" num="${qr.areaname?size - count - groupnum!}">未归属区域</li>
					<#list qr.areaname as val>
						<#if val_index &gt;= count && val_index &lt; qr.areaname?size - groupnum>
							<li <#if val_index == qr.areaname?size - groupnum-1>class="top-last-li"</#if>>${val!}</li>
						</#if>
					</#list>
				</ul>
				</#if>
    			<#list qr.areaname as val>
    			<#if val_index &gt;=qr.areaname?size - groupnum>	
        			<ul style="height:69px;float:left;">
						<li style="height:69px;line-height:69px;" <#if val_index == qr.areaname?size - 1>class="top-last-all"</#if>>${val!}</li>
					</ul>
				</#if>
				</#list>
        		
        		<!-- 区域名称 -->
        		
				<!-- 投诉处理质量综合评分 -->
			    <ul class="yybb_bj">
			   		 <#list qr.comp_score as val>
						<li title="<#if val?? && val!=''>${val!}</#if>"><#if val?? && val!=''>#{val?number;M2}<#else>-</#if></li>
					</#list>
				</ul>
				<!-- 投诉处理质量综合排名 -->
		        <ul name="comp_score_rank" sort= '1'>
					<#list qr.comp_score_rank as val>
						<li><#if val??>${val!}<#else>-</#if></li>
					</#list>
			    </ul>
			    <!--移动网络服务考核得分 -->
			    <ul class="yybb_bj">
					<#list qr.assess_score as val>
						<li title="<#if val?? && val!=''>${val!}</#if>"><#if val?? && val!=''>#{val?number;M2}<#else>-</#if></li>
					</#list>
			    </ul>
			    
			    <!-- 累计需实测工单量 -->
		        <ul>
					<#list qr.total_workorder as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月需实测工单量-->
			    <ul class="yybb_bj">
					<#list qr.curr_workorder as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计实测量 -->
		        <ul>
		        	<#assign x=-1 />
					<#list qr.total_test as val>
						<#assign x=x+1 />
						<li style='cursor:pointer;' onmouseover='liOver(this)' onmouseout='liOut(this)' onclick='details_rpt("<#if val_index &lt;qr.areaname?size - groupnum>${qr.areaid[x]}</#if>","<#if val_index &gt;= qr.areaname?size - groupnum>${qr.areaid[x]}</#if>","1","2",event)'>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月实测量-->
			    <ul class="yybb_bj">
			    	<#assign y=-1 />
					<#list qr.curr_test as val>
						<#assign y=y+1 />
						<li style='cursor:pointer;' onmouseover='liOver(this)' onmouseout='liOut(this)' onclick='details_rpt("<#if val_index &lt;qr.areaname?size - groupnum>${qr.areaid[y]}</#if>","<#if val_index &gt;= qr.areaname?size - groupnum>${qr.areaid[y]}</#if>","2","2",event)'>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计实测率 -->
		        <ul name="total_test_rate" sort="0">
					<#list qr.total_test_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
			    <!--月测试及时率 -->
			    <ul class="yybb_bj">
					<#list qr.curr_test_timely as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    </#if>
			    <!-- 累计网络投诉工单量 -->
		        <ul>
					<#list qr.total_serialno as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计真正解决工单量-->
			    <ul class="yybb_bj">
					<#list qr.total_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计真正解决率 -->
		        <ul name="total_solve_rate" sort="0">
					<#list qr.total_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月网络投诉工单量-->
			    <ul class="yybb_bj">
					<#list qr.curr_serialno as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月真正解决工单量 -->
			    <ul>
					<#list qr.curr_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计优化真正解决量 -->
		        <ul  class="yybb_bj">
					<#list qr.total_major_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计优化真正解决比 -->
			    <ul>
					<#list qr.total_major_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月优化真正解决量 -->
		        <ul  class="yybb_bj">
					<#list qr.curr_major_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月优化真正解决比 -->
		        <ul>
					<#list qr.curr_major_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计建设真正解决量 -->
			    <ul class="yybb_bj">
					<#list qr.total_build_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计建设真正解决比 -->
		        <ul>
					<#list qr.total_build_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月建设真正解决量 -->
			    <ul class="yybb_bj">
					<#list qr.curr_build_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月建设真正解决比 -->
			    <ul>
					<#list qr.curr_build_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计维护真正解决量 -->
		        <ul class="yybb_bj">
					<#list qr.total_maintain_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计维护真正解决比-->
			    <ul>
					<#list qr.total_maintain_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月维护真正解决量 -->
		        <ul class="yybb_bj">
					<#list qr.curr_maintain_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月维护真正解决比 -->
		        <ul>
					<#list qr.curr_maintain_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计其它真正解决量 -->
		        <ul class="yybb_bj">
					<#list qr.total_other_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计其它真正解决比-->
			    <ul>
					<#list qr.total_other_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月其它真正解决量 -->
		        <ul class="yybb_bj">
					<#list qr.curr_other_solve as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月其它真正解决比 -->
		        <ul>
					<#list qr.curr_other_solve_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单滞留量 -->
			    <ul class="yybb_bj">
					<#list qr.total_delay as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计工单滞留率 -->
		        <ul name="total_delay_rate" sort="1">
					<#list qr.total_delay_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计优化工单滞留量-->
			    <ul class="yybb_bj">
					<#list qr.total_major_delay as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计优化工单滞留比-->
			    <ul>
					<#list qr.total_major_delay_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计建设工单滞留量 -->
		        <ul class="yybb_bj">
					<#list qr.total_build_delay as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计建设工单滞留比 -->
		        <ul>
					<#list qr.total_build_delay_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计维护工单滞留量 -->
			    <ul class="yybb_bj">
					<#list qr.total_maintain_delay as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计维护工单滞留比 -->
			    <ul>
					<#list qr.total_maintain_delay_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单驳回量 -->
			    <ul>
					<#list qr.total_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单驳回量 -->
			    <ul>
					<#list qr.curr_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单驳回率 -->
			    <ul name="total_reject_rate" sort="2">
					<#list qr.total_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计优化工单驳回量 -->
			    <ul>
					<#list qr.total_major_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计优化工单驳回比 -->
			    <ul>
					<#list qr.total_major_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月优化工单驳回量 -->
			    <ul>
					<#list qr.curr_major_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月优化工单驳回比 -->
			    <ul>
					<#list qr.curr_major_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计建设工单驳回量 -->
			    <ul>
					<#list qr.total_build_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计建设工单驳回比 -->
			    <ul>
					<#list qr.total_build_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月建设工单驳回量 -->
			    <ul>
					<#list qr.curr_build_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月建设工单驳回比 -->
			    <ul>
					<#list qr.curr_build_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计维护工单驳回量 -->
			    <ul>
					<#list qr.total_maintain_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计维护工单驳回比 -->
			    <ul>
					<#list qr.total_maintain_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月维护工单驳回量 -->
			    <ul>
					<#list qr.curr_maintain_reject as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--月维护工单驳回比 -->
			    <ul>
					<#list qr.curr_maintain_reject_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单超时量 -->
			    <ul class="yybb_bj">
					<#list qr.total_over as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单超时量 -->
		        <ul>
					<#list qr.curr_over as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单超时率-->
			    <ul class="yybb_bj" name='total_over_rate' sort='2'>
					<#list qr.total_over_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计工单重复量 -->
		        <ul>
					<#list qr.total_send as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单重复量-->
			    <ul class="yybb_bj">
					<#list qr.curr_send as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!--累计工单重派率 -->
		        <ul name="total_send_rate" sort="2">
					<#list qr.total_send_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			   
			    <!-- 累计工单重复投诉量 -->
		        <ul class="yybb_bj">
					<#list qr.total_complaint as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单重复投诉量-->
			    <ul>
					<#list qr.curr_complaint as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 累计重复投诉率 -->
		        <ul class="yybb_bj" name="total_complaint_rate" sort="2">
					<#list qr.total_complaint_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			     <!-- 累计工单升级量-->
			    <ul>
					<#list qr.total_upgrade as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单升级量 -->
		        <ul class="yybb_bj">
					<#list qr.curr_upgrade as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月工单升级率-->
			    <ul name="total_upgrade_rate" sort="2">
					<#list qr.total_upgrade_rate as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
			    <!--月3G综合改善评分 -->
			    <ul class="yybb_bj">
					<#list qr.curr_wcdma_impr as val>
						<li title="${val!}">#{val;M2}</li>
					</#list>
			    </ul>
			    <!-- 月2G综合改善评分 -->
		        <ul>
					<#list qr.curr_gsm_impr as val>
						<li title="${val!}">#{val;M2}</li>
					</#list>
			    </ul>
			    <!--月3G综合改善比例 -->
			    <ul class="yybb_bj">
					<#list qr.ratio_wcdma_impr as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    <!-- 月2G综合改善比例 -->
		        <ul>
					<#list qr.ratio_gsm_impr as val>
						<li>${val!}</li>
					</#list>
			    </ul>
			    </#if>
		<#else>
			<#list 1..71 as u>
			  <#if u_index == 0>
			 		 <ul style='float:left;'>
						<li class='top-group' num="5"></li>
					</ul>
					 <ul  style='float:left;'>
						<li class='top-group' num="4"></li>
					</ul>
					 <ul  style='float:left;'>
						<li class='top-group' num="4"></li>
					</ul>
			   <#else>
			   		<ul>
						<#list 1..13 as l>
							<li <#if u_index == 1 && (u_index == 4 || u_index == 8 || u_index == 12)>class="top-last-li"</#if>></li>
						</#list>
					</ul>
			  </#if>
					
			</#list>	    	
		</#if>
		
		
	 
	   
        </div>
        
        <div class="clear"></div>
      </div>
      <!-- -->
      <div class="clear"></div>
      </div>
      <!--========阈值配置中间结束========= --> 
    </div>
    <div class="all_list_bottom"><span></span><samp></samp></div>
  </div>
  <!--========阈值配置结束========= --> 
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
</@body>
<@script>
	<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/qualityReport.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/commReport.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = '${application.getContextPath()}';
		//报表类型
		var loadType = <#if type??>${type!}<#else>1</#if>;
		var areaids = <#if areaids?? && areaids!= ''>'${areaids!}'<#else>'-1'</#if>;
		var queryids = <#if queryids?? && queryids!= ''>'${queryids!}'<#else>'-1'</#if>;
		var show = <#if show?? >${show!}<#else>0</#if>;
		var groupnum = <#if groupnum?? >${groupnum!}<#else>0</#if>;
	</script>	
</@script>