<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
	
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
      <!--搜索框 开始 -->
  <div class="wls">
  <div class="wlf">
 <#if buttons??>
  <#if !(buttons?size == 1 && buttons[0].btntype == 'set')>
  <form action="${application.getContextPath()}/export/export" id="searchForm" method="post">
  	<#include "search.ftl"/>
    <!--判断用户查询权限-->
    <#if buttons?? && buttons?size &gt;0>
      <#list buttons as button>
      	<#if (button.btntype)?? && button.btntype == 'show'>
    		<samp onclick="search()" style="margin-left:20px;">查询</samp>
        </#if>
      </#list>
    </#if>
    <!--判断用户导出去权限-->
     <#if buttons?? && buttons?size &gt;0>
      <#list buttons as button>
      	<#if (button.btntype)?? && button.btntype == 'export'>
      		<samp onclick="exportReport()" style="margin-left:15px;">导出</samp>
      	</#if>
      </#list>
    </#if>
    <samp onclick = "showError()" id="errorBtn" style="display:none;"></samp>
    <iframe id="download" src="#" style="display:none;">&nbsp;</iframe>
      </form>
       </#if>
  </#if>
      </div>
      <!--判断用户默认值权限-->
     <#if buttons?? && buttons?size &gt;0>
      <#list buttons as button>
      	<#if (button.btntype)?? && button.btntype == 'set'>
	      <div class="wlf" style="float:right;margin-right:1%;">
		      <form action="${application.getContextPath()}/export/setValue" id="setValueForm" method="post">
				<span class="wlfspan" style="margin-left:px;">累计起始时间：</span>
				<div class="search_input" style="width:148px;">
					 <h6></h6>
			      <h5><input name= "reportdate" value="${reportdate!}" oldVal = "${reportdate!}" id="reportdate" type="text" class="Wdate"  style="width:138px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy.MM.dd HH:mm:ss'})" />
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
	      </#if>
      </#list>
    </#if>
  </div>
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="all_list_top1"><span></span><samp></samp></div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yybb_top"><span>移动网络投诉测试${typeVal!'X'}报<#if titleVal??> - <label style="font-size:26px;">${titleVal!}</label></#if></span>
        <p>
        	 ${typeVal!'X'}实测量最多的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.curr_max_test!}</label><#else>XX</#if>，
        	最少的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.curr_min_test!}</label><#else>XX</#if>；
			累计实测率最高的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.total_max_test!}</label><#else>XX</#if>，
        	最低的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.total_min_test!}</label><#else>XX</#if>；
        	${typeVal!'X'}3G综合评价最好的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.comp_eval_3g_max!}</label><#else>XX</#if>，
        	最差的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.comp_eval_3g_min!}</label><#else>XX</#if>；
        	${typeVal!'X'}2G综合评价最好的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.comp_eval_2g_max!}</label><#else>XX</#if>，
        	最差的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.comp_eval_2g_min!}</label><#else>XX</#if><#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>；${typeVal!'X'}3G综合改善最大的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.comp_impr_3g_max!}</label><#else>XX</#if>，
        	最小的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.comp_impr_3g_min!}</label><#else>XX</#if>；
			${typeVal!'X'}2G综合改善最大的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.comp_impr_2g_max!}</label><#else>XX</#if>，
        	最小的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.comp_impr_2g_min!}</label><#else>XX</#if>；
        	${typeVal!'X'}测试及时率最好的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#00FF00;">${mostTestRate.timely_rate_max!}</label><#else>XX</#if>，
        	最差的区域是<#if mostTestRate??><label style="font-weight:bolder;color:#FF0000;">${mostTestRate.timely_rate_min!}</label><#else>XX</#if></#if>。
        </p>
      </div>
      <!-- -->
      <div class="yybb_left">
        <div class="yybb_left_top"> <span style="margin-top:70px;">投诉实测情况统计</span>
          <ul>
            <li>区域</li>
            <li class="yybb_bj">累计工单量</li>
            <li>${typeVal!'X'}工单量</li>
            <li class="yybb_bj">累计实测量</li>
            <li>${typeVal!'X'}实测量</li>
            <li class="yybb_bj">${typeVal!'X'}实测量排名</li>
            <li>累计实测率</li>
            <li class="yybb_bj">累计实测率排名</li>
            <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
            <li>${typeVal!'X'}测试及时率 </li>
            <li class="yybb_bj">${typeVal!'X'}测试及时率排名</li>
            </#if>
            <li>${typeVal!'X'}室内测试量</li>
            <li class="yybb_bj"style="border:none;">${typeVal!'X'}室外测试量</li>
          </ul>
        </div>
        <!-- -->
        <div class="yybb_left_top yybb_left_bottom"> <span style="margin-top:300px;">${typeVal!'X'}移动网络投诉测试评价</span>
          <ul>
            <li>
              <p>RSCP</p>
               <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>	
            </li>
            <li>
              <p>EcIo</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
            <li>
              <p>TxPower</p>
            	<#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>  
			</li>
            <li>
              <p>上行速率</p>
              	<#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
            <li>
              <p>下行速率</p>
               <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
             </li>
            <li>
              <p>RxLev</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
            <li>
              <p>RxQual</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
              <li>
              <p>C/I</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
           <li>
              <p>${typeVal!'X'}3G综合评价</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
            <li>
              <p>${typeVal!'X'}2G综合评价</p>
              <#if colors?? &&  colors?size &gt; 0>
					<#list colors as color>
						<#if color?? && color.rank_code == 1>
							<samp style="background:${color.rank_color}">优</samp>
						</#if>
						<#if color?? && color.rank_code == 2>
							<samp style="background:${color.rank_color}">良</samp>
						</#if>
						<#if color?? && color.rank_code == 3>
							<samp style="background:${color.rank_color}">中</samp>
						</#if>
						<#if color?? && color.rank_code == 4>
							<samp style="background:${color.rank_color}">差</samp>
						</#if>
					</#list>
				</#if>
              </li>
              <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
              <li style="height:34px;line-height:34px;"> ${typeVal!'X'}3G测试评价改善评分 </li>
              <li style="height:34px;line-height:34px;"> ${typeVal!'X'}2G测试评价改善评分 </li>
              <li style="height:34px;line-height:34px;"> ${typeVal!'X'}3G测试评价改善比例 </li>
              <li style="height:34px;line-height:34px;"> ${typeVal!'X'}2G测试评价改善比例  </li>
              </#if>
          </ul>
        </div>
      </div>
      <div class="yybb_right">
      	<div id = "overflow" val="<#if statistics??>${statistics.area_name?size}<#else>13</#if>" style="float:left;">
        <div class="yybb_right_top">
          <ul>
          <#if statistics?? >
				<#list statistics.area_name as areaname>
						<li>${areaname!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		  </#if>
          </ul>
          <!--累积工单量 -->
          <ul class="yybb_bj"  sort= '-1'>
            <#if statistics?? && statistics.total_workorder??>
				<#list statistics.total_workorder as totalworkorder>
						<li>${totalworkorder!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		  </#if>
          </ul>
          <!-- 当前工单量-->
          <ul  sort= '-1'>
            <#if statistics?? && statistics.curr_workorder??>
				<#list statistics.curr_workorder as currworkorder>
						<li>${currworkorder!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <!-- 累计实测量-->
          <#assign x=-1 />
         <ul class="yybb_bj"  sort= '0' name='total_test'>
            <#if statistics?? && statistics.total_test??>
				<#list statistics.total_test as totaltest>
				    <#assign x=x+1 />
					<li style='cursor:pointer;' onmouseover='liOver(this)' onmouseout='liOut(this)' onclick='details_rpt("${statistics.area_id[x]}","","1","1",event)'>${totaltest!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <!--当实测量-->
          <#assign y=-1 />
          <ul  sort= '0' name='curr_test'>
            <#if statistics?? && statistics.curr_test??>
				<#list statistics.curr_test as currtest>
					<#assign y=y+1 />
					<li style='cursor:pointer;' onmouseover='liOver(this)' onmouseout='liOut(this)' onclick='details_rpt("${statistics.area_id[y]}","","2","1",event)'>${currtest!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		  </#if>
          </ul>
          <!--实测量排名-->
          <ul class="yybb_bj"  sort= '1' name='curr_test_rank'>
            <#if statistics?? && statistics.curr_test_rank??>
				<#list statistics.curr_test_rank as currtestrank>
						<li>${currtestrank!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <!--累计实测率-->
          <ul  sort= '0' name='total_test_rate'>
            <#if statistics?? && statistics.total_test_rate??>
				<#list statistics.total_test_rate as totaltestrate>
						<li>${totaltestrate!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		  </#if>
          </ul>
          <!--累计实测率排名-->
          <ul  class="yybb_bj" sort= '1' name='total_test_rate_rank'>
            <#if statistics?? && statistics.total_test_rate_rank??>
				<#list statistics.total_test_rate_rank as totaltestraterank>
						<li>${totaltestraterank!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <!--及时率-->
         <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
          <ul  sort= '0' name='timely_rate'>
           <#if evaluate?? && evaluate.timely_rate??>
				<#list evaluate.timely_rate as tr>
						<li>${tr!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          </#if>
          <!-- 及时率排名-->
         <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
          <ul  class="yybb_bj"  sort= '1' name='timely_rate_rank'>
          <#if evaluate?? && evaluate.timely_rate_rank??>
     		 <#list evaluate.timely_rate_rank as trr>
					<li>${trr!}</li>
			</#list>
		 <#else>
			<#list 1..13 as l>
				<li></li>
			</#list>	
          </#if>
          </ul>
          </#if>
          <ul  sort= '-1'>
            <#if statistics?? && statistics.curr_in_test??>
				<#list statistics.curr_in_test as currintest>
						<li>${currintest!}</li>
				</#list>
		    <#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		    </#if>
          </ul>
          <ul  class="yybb_bj"  sort= '-1'>
           <#if statistics?? && statistics.curr_out_test??>
				<#list statistics.curr_out_test as currouttest>
						<li>${currouttest!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          
        </div>
        <div class="yybb_right_bottom">
       		<ul  sort= '0' name='rscp_a'>
           <#if evaluate?? && evaluate.rscp_a??>
				<#list evaluate.rscp_a as rscp_1>
						<li>${rscp_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
            <#if evaluate?? && evaluate.rscp_b??>
				<#list evaluate.rscp_b as rscp_2>
						<li>${rscp_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
            <#if evaluate?? && evaluate.rscp_c??>
				<#list evaluate.rscp_c as rscp_3>
						<li>${rscp_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '1' name='rscp_d'>
           <#if evaluate?? && evaluate.rscp_d??>
				<#list evaluate.rscp_d as rscp_4>
						<li>${rscp_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '0' name='ec_no_a'>
           <#if evaluate?? && evaluate.ec_no_a??>
				<#list evaluate.ec_no_a as ec_no_1>
						<li>${ec_no_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ec_no_b??>
				<#list evaluate.ec_no_b as ec_no_2>
						<li>${ec_no_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ec_no_c??>
				<#list evaluate.ec_no_c as ec_no_3>
						<li>${ec_no_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '1' name='ec_no_d'>
           <#if evaluate?? && evaluate.ec_no_d??>
				<#list evaluate.ec_no_d as ec_no_4>
						<li>${ec_no_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
         <ul  sort= '0' name='txpower_a'>
           <#if evaluate?? && evaluate.txpower_a??>
				<#list evaluate.txpower_a as txpower_1>
						<li>${txpower_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
		  <ul>
           <#if evaluate?? &&  evaluate.txpower_b??>
				<#list evaluate.txpower_b as txpower_2>
						<li>${txpower_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.txpower_c??>
				<#list evaluate.txpower_c as txpower_3>
						<li>${txpower_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '1' name='txpower_d'>
           <#if evaluate?? &&  evaluate.txpower_d??>
				<#list evaluate.txpower_d as txpower_4>
						<li>${txpower_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          
          <ul class="yybb_bj"  sort= '0' name='ftp_up_a'>
           <#if evaluate?? && evaluate.ftp_up_a??>
				<#list evaluate.ftp_up_a as ftp_up_1>
						<li>${ftp_up_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ftp_up_b??>
				<#list evaluate.ftp_up_b as ftp_up_2>
						<li>${ftp_up_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ftp_up_c??>
				<#list evaluate.ftp_up_c as ftp_up_3>
						<li>${ftp_up_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '1' name='ftp_up_d'>
           <#if evaluate?? && evaluate.ftp_up_d??>
				<#list evaluate.ftp_up_d as ftp_up_4>
						<li>${ftp_up_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '0' name='ftp_down_a'>
           <#if evaluate?? && evaluate.ftp_down_a??>
				<#list evaluate.ftp_down_a as ftp_down_1>
						<li>${ftp_down_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.ftp_down_b??>
				<#list evaluate.ftp_down_b as ftp_down_2>
						<li>${ftp_down_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.ftp_down_c??>
				<#list evaluate.ftp_down_c as ftp_down_3>
						<li>${ftp_down_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '1' name='ftp_down_d'>
           <#if evaluate?? && evaluate.ftp_up_d??>
				<#list evaluate.ftp_down_d as ftp_down_4>
						<li>${ftp_down_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '0' name='rxlev_a'>
           <#if evaluate?? && evaluate.rxlev_a??>
				<#list evaluate.rxlev_a as rxlev_1>
						<li>${rxlev_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.rxlev_b??>
				<#list evaluate.rxlev_b as rxlev_2>
						<li>${rxlev_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.rxlev_c??>
				<#list evaluate.rxlev_c as rxlev_3>
						<li>${rxlev_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '1' name='rxlev_d'>
           <#if evaluate?? && evaluate.rxlev_d??>
				<#list evaluate.rxlev_d as rxlev_4>
						<li>${rxlev_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '0' name='rxqual_a'>
           <#if evaluate?? && evaluate.rxqual_a??>
				<#list evaluate.rxqual_a as rxqual_1>
						<li>${rxqual_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.rxqual_b??>
				<#list evaluate.rxqual_b as rxqual_2>
						<li>${rxqual_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.rxqual_c??>
				<#list evaluate.rxqual_c as rxqual_3>
						<li>${rxqual_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.rxqual_d??>
				<#list evaluate.rxqual_d as rxqual_4>
						<li>${rxqual_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ci_a??>
				<#list evaluate.ci_a as ci_1>
						<li>${ci_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.ci_b??>
				<#list evaluate.ci_b as ci_2>
						<li>${ci_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj" >
           <#if evaluate?? && evaluate.ci_c??>
				<#list evaluate.ci_c as ci_3>
						<li>${ci_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj" >
           <#if evaluate?? && evaluate.ci_d??>
				<#list evaluate.ci_d as ci_4>
						<li>${ci_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.comp_eval_3g_a??>
				<#list evaluate.comp_eval_3g_a as comp_eval_3g_1>
						<li>${comp_eval_3g_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.comp_eval_3g_b??>
				<#list evaluate.comp_eval_3g_b as comp_eval_3g_2>
						<li>${comp_eval_3g_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul>
           <#if evaluate?? && evaluate.comp_eval_3g_c??>
				<#list evaluate.comp_eval_3g_c as comp_eval_3g_3>
						<li>${comp_eval_3g_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul sort= '1' name='comp_eval_3g_d'>
           <#if evaluate?? && evaluate.comp_eval_3g_d??>
				<#list evaluate.comp_eval_3g_d as comp_eval_3g_4>
						<li>${comp_eval_3g_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <!--综合评价-->
          <ul class="yybb_bj"  sort= '0' name='comp_eval_2g_a'>
           <#if evaluate?? && evaluate.comp_eval_2g_a??>
				<#list evaluate.comp_eval_2g_a as comp_eval_2g_1>
						<li>${comp_eval_2g_1!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj">
           <#if evaluate?? && evaluate.comp_eval_2g_b??>
				<#list evaluate.comp_eval_2g_b as comp_eval_2g_2>
						<li>${comp_eval_2g_2!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj" >
           <#if evaluate?? && evaluate.comp_eval_2g_c??>
				<#list evaluate.comp_eval_2g_c as comp_eval_2g_3>
						<li>${comp_eval_2g_3!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <ul class="yybb_bj"  sort= '1' name='comp_eval_2g_d'>
           <#if evaluate?? && evaluate.comp_eval_2g_d??>
				<#list evaluate.comp_eval_2g_d as comp_eval_2g_4>
						<li>${comp_eval_2g_4!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li></li>
				</#list>	
		  </#if>
          </ul>
          <#if type?? && show?? && (show ==1 && type!=1) ||  show == 0>
          <ul  sort= '0' name='comp_impr_value_3g'>
           <#if evaluate?? && evaluate.comp_impr_value_3g??>
				<#list evaluate.comp_impr_value_3g as val>
						<li style="height:34px;line-height:34px;" title="${val!}">#{val;M2}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li style="height:34px;line-height:34px;"></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '0' name='comp_impr_value_2g'>
           <#if evaluate?? && evaluate.comp_impr_value_2g??>
				<#list evaluate.comp_impr_value_2g as val>
						<li style="height:34px;line-height:34px;" title="${val!}">#{val;M2}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li style="height:34px;line-height:34px;"></li>
				</#list>	
		  </#if>
          </ul>
          <ul  sort= '0' name='comp_impr_ratio_3g'>
           <#if evaluate?? && evaluate.comp_impr_ratio_3g??>
				<#list evaluate.comp_impr_ratio_3g as val>
						<li style="height:34px;line-height:34px;">${val!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li style="height:34px;line-height:34px;"></li>
				</#list>	
		   </#if>
          </ul>
          <ul  sort= '0' name='comp_impr_ratio_2g'>
           <#if evaluate?? && evaluate.comp_impr_ratio_2g??>
				<#list evaluate.comp_impr_ratio_2g as val>
						<li style="height:34px;line-height:34px;">${val!}</li>
				</#list>
			<#else>
				<#list 1..13 as l>
					<li style="height:34px;line-height:34px;"></li>
				</#list>	
		   </#if>
          </ul>
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
	<script src="${application.getContextPath()}/js/export/exportReport.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/commReport.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = '${application.getContextPath()}';
		//报表类型
		var loadType = <#if type??>${type!}<#else>1</#if>;
		var areaids = <#if areaids?? && areaids!= ''>'${areaids!}'<#else>'-2'</#if>;
		var queryids = <#if queryids?? && queryids!= ''>'${queryids!}'<#else>'-2'</#if>;
		var show = <#if show?? >${show!}<#else>0</#if>;
	</script>
</@script>