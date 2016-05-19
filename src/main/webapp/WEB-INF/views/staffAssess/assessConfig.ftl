<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="内部考核配置配置" description="内部考核配置配置">
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
        <li class="forms_nav_now">内部考核配置</li>
      </ul>
      <samp></samp> </div>
    <!--========阈值配置头部结束========= -->
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="yfpz">
        <div class="yfpz_2g khbb-cs">
          <h5>综合评分</h5>
          <div class="khbb-cs-other"><span>综合评分基准分</span><p><input name="basic" type="text" value="${basic1!0}" oldval="${basic1!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></p></div>
          <div class="clear"></div>
          <ul class="khbb-cs-nav">
          <li class="khbb-cs-bj">指标项</li>
          <li>累计实测率</li>
          <li>月测试及时率</li>
          <li>累计解决率</li>
          <li>累计工单滞留率</li>
          <li>累计工单驳回率</li>
          <li>累计工单超时率</li>
          <li>累计工单重派率</li>
          <li>累计重复投诉率</li>
          <li>累计工单升级率</li>
          <li>月网络投诉工单量</li>
          </ul>
          <ul class="khbb-cs-nav khbb-cs-zhi kh-cs-center">
          <li class="khbb-cs-bj">均值比较得分步长</li>
          <#if steps??&& steps?size &gt;0>
          	<#list steps as step>
	          <li <#if step.KPI== 10>style="background-color:#cbcbcb;"</#if>><input name="svgstep" <#if step.KPI== 10>type="hidden"<#else>type="text"</#if> value="${step.SVG_STEP!0}" oldval="${step.SVG_STEP!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></li>
	        </#list>
          </#if>
          </ul>
          <ul class="khbb-cs-nav khbb-cs-zhi kh-cs-center" style="border-right:1px #a2a2a2 solid;">
          <li class="khbb-cs-bj">环比改善均值比较得分步长</li>
          <#if steps??&&steps?size &gt;0>
          	<#list steps as step>
	          <li <#if step.KPI== 2||step.KPI== 4>style="background-color:#cbcbcb;"</#if>><input name="annstep" <#if step.KPI== 2||step.KPI== 4>type="hidden"<#else>type="text"</#if> value="${step.ANNULAR_STEP!0}" oldval="${step.ANNULAR_STEP!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/>
	          <input name="kpi" type="hidden" value="${step.KPI!}"/>
	          </li>
	        </#list>
          </#if>
          </ul>
        </div>
        <!-- -->
        <div class="yfpz_2g khbb-cs kh-cs-center">
          <h5>考核得分</h5>
          <div class="khbb-cs-other khbb-cs-other-ts kh-cs-center-ts"><span style="border-left:1px #a2a2a2 solid;">大组末位加分基准分</span><p><input name="basic" type="text" value="${basic2!0}" oldval="${basic2!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></p><span>大组排名加分步长</span><p><input name="basic" type="text" value="${basic3!0}" oldval="${basic3!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></p></div>
          <div class="clear"></div>
          <div class="khbb-cs-other khbb-cs-other-ts"><span style="border-left:1px #a2a2a2 solid;">大组组长系数</span><p><input name="basic" type="text" value="${basic4!0}" oldval="${basic4!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></p><span>大组组员系数</span><p><input name="basic" type="text" value="${basic5!0}" oldval="${basic5!0}" class="easyui-validatebox" data-options="required:true,validType:['assessScore']" maxlength="6"/></p></div>
          
          <div class="clear"></div>
        </div>
        <div class="clear"></div>
        
         <!--==保存按钮开始==-->
           <div class="clear"></div>
        <div style="margin-right:45px;margin-bottom:16px; float:right;" class="bottom_btn">
			     <!-- <a class="l-btn" style="margin-left: 10px;" id="reset" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text">重 置</span></span></a>-->
			      <a class="l-btn" style="margin-left: 10px;" id="saveConfig" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text">确 定</span></span></a>
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
	<script src="${application.getContextPath()}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/commUtils.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/export/assessConfig.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = '${application.getContextPath()}';
	</script>
</@script>
