<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>

<@body>
<div class="right"> 
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="forms_nav">
      <ul>
        <li class="forms_nav_now">质量报表配置</li>
      </ul>
      <samp></samp> </div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yfpz">
        <div class="yfpz_2g khbb-cs">
          <h5>综合评分</h5>
          <div class="khbb-cs-other"><span>综合评分基准分</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 1>
	              			<input name="" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>	
              
            </p>
          </div>
          <div class="clear"></div>
          <ul class="khbb-cs-nav" style="position: relative;left: 0px;margin-left:10px;">
            <li style="height:71px; line-height:71px;" class="khbb-cs-bj">指标项</li>
            <li>累计实测率</li>
            <li>月测试及时率</li>
            <li>累计真正解决率</li>
            <li>累计工单滞留率</li>
            <li>累计工单驳回率</li>
            <li>累计工单超时率</li>
            <li>累计工单重派率</li>
            <li>累计重复投诉率</li>
            <li>累计工单升级率</li>
            <li>月网络投诉工单量</li>
          </ul>
          <div id="right" style="float:left;overflow:auto;border-right: 1px solid  #A2A2A2;border-top: 1px solid  #A2A2A2;" val="<#if list?? && list?size &gt; 0  >${list?size}</#if>">
          <div style="width:<#if list?? && list?size &gt; 0  >${list?size*90*2 + 5 -1}</#if>px;">
          <div id="svg_step" style="float:left;">
            <ul class="khbb-cs-nav khbb-cs-zhi" style="float:none;border-right:1px solid  #A2A2A2; border-top:none; width:<#if list?? && list?size &gt; 0  >${list?size*90}</#if>px;position: relative;left: 0px;">
              <li style=" width:<#if list?? && list?size &gt; 0  >${list?size*90 }</#if>px; float:none;" class="khbb-cs-bj">均值比较分步长</li>
            </ul>
            <#if list?? && list?size &gt; 0>
            	<#list list as group>
	            <ul class="khbb-cs-nav khbb-cs-zhi" style="width:90px; border-top:none;position: relative;left: 0px;">
	              <li class="khbb-cs-bj">${group.groupname!}</li>
	              <#if group.quals??>		
		              <#list group.quals as qual>
			              <#if qual_index+1 == 10>
			              	 <li style="background-color:#cbcbcb;">
			                	<input name="kpi_${qual.kpi!}_${group.groupid}" operid="${qual.id!}" value="0" type="hidden" />
			             	 </li>
			              <#else>
			             	  <li>
			               		 <input name="kpi_${qual.kpi!}_${group.groupid}" operid="${qual.id!}" value="${qual.svgstep!}" type="text" />
			                 </li>
			              </#if>
		              </#list>
		              <#if group.quals?size &lt; 10>
		              	<#list 1..(10 - group.quals?size) as li>
		              		 <#if group.quals?size + li_index + 1 == 10>
			              	     <li style="background-color:#cbcbcb;">
			                	    <input name="kpi_${group.quals?size + li_index + 1!}_${group.groupid}" operid="-1" value="0" type="hidden" />
			             	     </li>
			                 <#else>
			             	    <li>
			               		   <input name="kpi_${group.quals?size + li_index + 1!}_${group.groupid}" operid="-1"  type="text" />
			                    </li>
			                 </#if> 	
		              	</#list>
		              </#if>
	              </#if>
	            </ul>
	            </#list>
            </#if>
          </div>
          <div id="annular_step" style="float:left;border-right:1px #a2a2a2 solid;border-left:1px #a2a2a2 solid;">
            <ul class="khbb-cs-nav khbb-cs-zhi" style="float:none;   border-top:none;width:<#if list?? && list?size &gt; 0  >${list?size*90+1}</#if>px;position: relative;left: 0px;">
              <li style=" width:<#if list?? && list?size &gt; 0  >${list?size*90}</#if>px; float:none;" class="khbb-cs-bj">环比比较分步长</li>
            </ul>
			<#if list?? && list?size &gt; 0>
            	<#list list as group>
	            <ul  class="khbb-cs-nav khbb-cs-zhi" style="width:90px; border-top:none;position: relative;left: 0px;">
	              <li class="khbb-cs-bj">${group.groupname}</li>
	              <#if group.quals?? >		
		              <#list group.quals as qual>
		              	  <#if qual_index+1 == 2 || qual_index+1 == 4>
		              	  	 <li style="background-color:#cbcbcb;">
			               		 <input name="kpi_${qual.kpi!}_${group.groupid}" operid="${qual.id!}" value="0" type="hidden" />
			             	 </li>
			              <#else>
			              	  <li>
			                	<input name="kpi_${qual.kpi!}_${group.groupid}" operid="${qual.id!}" value="${qual.annularstep!}" type="text" />
			            	  </li> 	 
		              	  </#if>	
		              </#list>
		              <#if group.quals?size &lt; 10>
		              	<#list 1..(10 - group.quals?size) as li>
		              		 <#if group.quals?size + li_index + 1 == 2 || group.quals?size + li_index + 1 == 4>
			              		 <li style="background-color:#cbcbcb;">
				                	<input name="kpi_${group.quals?size + li_index + 1!}_${group.groupid}"  operid="-1" value="0" type="hidden"/>
				              	</li>
				             <#else>
				             	 <li>
				                	<input name="kpi_${group.quals?size + li_index + 1!}_${group.groupid}" operid="-1"  type="text" />
				              	</li>	
			              	</#if>
		              	</#list>
		              </#if>
	              </#if>
	            </ul>
	            </#list>
            </#if>
          </div>
          </div>
         </div>
        </div>
        <!-- -->
        <div class="yfpz_2g khbb-cs">
          <h5>考核得分</h5>
          <div class="khbb-cs-other khbb-cs-other-ts">
          <!--<span style="width:170px;border-left:1px #a2a2a2 solid;">考核分值</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 2>
	              			<input name="" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>
            </p>
            <span>末位考核基本得分</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 3>
	              			<input name="" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>
            </p>
            <span>满分排名最小值</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 4>
	              			<input name="" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>
            </p>
            -->
            <span style="width:170px;border-left:1px #a2a2a2 solid;">考核总分</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 5>
	              			<input name="tscroe" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>
            </p>
            <span style="width:170px;">考核步长</span>
            <p>
              <#if basics?? && basics?size &gt;0>
	              <#list basics as b>
	              		<#if b.id == 6>
	              			<input name="stepc" type="text" value="${b.val!}" operid="${b.id!}"/>
	              		</#if>
	              </#list>
	           <#else>
              </#if>
            </p>
          </div>
          <div class="clear"></div>
        </div>
        <div class="clear"></div>
        
        <!--==保存按钮开始==-->
        <div class="clear"></div>
        <div style="margin-right:45px;margin-bottom:16px; float:right;" class="bottom_btn"> <a class="l-btn" style="margin-left: 10px;" id="ok" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a> 
          <!--<div class="btn" id="reset" style="padding-right:0px;">重 置</div>
			      <div class="btn" id="ok">确 定</div>--> 
        </div>
        <!--==保存按钮结束==--> 
      </div>
      <div class="clear"></div>
      <!--========阈值配置中间结束========= --> 
    </div>
    <div class="all_list_bottom"><span></span><samp></samp></div>
  </div>
  <!--========阈值配置结束========= --> 
</div>
</@body>
<@script>
<script src="${application.getContextPath()}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/easyui/jquery.easyui.min.js"></script>
	<script src="${application.getContextPath()}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${application.getContextPath()}/js/validater.js"></script>
	<script src="${application.getContextPath()}/js/jquery.form.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/reportconfig/qualityconfig.js" type="text/javascript"></script>
	<script>
		var contextPath = '${application.getContextPath()}';
	</script>
</@script>
