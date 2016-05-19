<#include "../layout.ftl" />
<@head title="重庆联通网络测试分析自处理系统" keywords="" description="">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${application.getContextPath()}/js/easyui/themes/icon.css">
</@head>
<@body>
	<style>
		.pjyz_color{ height:53px; width:98%; margin-left:1%;  margin-top:25px;}
 		.pjyz_color samp{ color:#a92c2c; display:block; height:26px; line-height:26px; width:60px; font-size:14px; font-weight:bolder; margin-left:15px; float:left; margin-right:10px;}
		.pjyz_color ul{float:left; width:580px; text-align:center; color:#FFF; line-height:26px;}
		.pjyz_color ul li{width:65px; height:26px;line-height:26px; float:left; margin-right:20px; cursor:pointer;}
	</style>
<div class="right"> 
  <!--========阈值配置开始========= -->
  <div class="forms"> 
    <!--========阈值配置头部========= -->
    <div class="forms_nav">
      <ul>
      	<li id="ratePage" name="${application.getContextPath()}/rateconfig/rateconfig">指标阈值</li>
        <li class="forms_nav_now">综合阈值</li>
      </ul>
      <samp></samp> </div>
    <!--========阈值配置头部结束========= --> 
    <!--========阈值配置中间开始========= -->
    <div class="border_left">
      <div class="pjyz"> 
        <!--========评价阈值中间颜色选择开始========= -->
        <div class="pjyz_color"> <samp>颜色：</samp>
        <form  method="post" id="colorForm">
          <ul>
           <#if colors?? && colors?size &gt; 0>
          		<#list  colors as color>
          			<li>
          			  	<input class="color {valueElement:'myValue'+${(color.rank_code)!},pickerFaceColor:'#00ccff'}" value="<#if color.rank_code=1>优</#if><#if color.rank_code=2>良</#if><#if color.rank_code=3>中</#if><#if color.rank_code=4>差</#if>" readonly="readonly" style="background:'#${(color.rank_color)!}';width:65px; padding:5px 0px; float:left; margin-right:20px; cursor:pointer;border:none;text-align:center;color:#fff"/>
	         			<input id='myValue${(color.rank_code)!}' name="colors" type="hidden" size="6px" value="${(color.rank_color)!}">
          			</li>
          		</#list>
          	</#if>
          </ul>
          </form>
          <div class="bottom_btn yfpz_btn" style="float:right; margin-right:10px;">
            <a href="javascript:void(0)"  id="saveColors" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"><span class="l-btn-text">确  定</span></span></a>
          </div>
        </div>
        <!--========计分规则========= -->
        <form method="post" id="dataForm">
        <div class="pjyz_jfgz">
          <div class="pjyz_jfgz_left" style="margin-right:100px;"> <span><samp>计分规则:</samp>（示例：若指标级别为优，计2分，良计5分，中计8分，差计11分）</span>
            <ul>
            <#if scoreRule??&&scoreRule?size &gt; 0>
              <li>
              <#list scoreRule as score>
              	<span style="background:<#if score.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if score.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if score.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if score.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px; display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if score.rank_code == 1>优</#if>
              	<#if score.rank_code == 2>良</#if>
              	<#if score.rank_code == 3>中</#if>
              	<#if score.rank_code == 4>差</#if>
              	</span>
                <input id="${'rank_code'+(score.rank_code)!}" name="rank_score" class="easyui-validatebox" data-options="required:true,validType:['integration']" type="text" value="${(score.rank_score)!}" oldval="${(score.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </li>
            </#if>
            </ul>
          </div>
          <!-- -->
          <div class="pjyz_jfgz_left"> <span><samp>评分规则:</samp>（该项目所有指标分值的平均值向上整取为该项目的分值）</span>
            <ul>
            <#if evaluateRule?? &&evaluateRule?size &gt; 0>
              <li>
              <#if evaluateRule.A?? &&evaluateRule.A?size &gt; 0>
              <#list evaluateRule.A as evaluate>
              <span style = " background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>; width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优+</#if>
              	<#if evaluate.rank_code==2>良+</#if>
              	<#if evaluate.rank_code==3>中+</#if>
              	<#if evaluate.rank_code==4>差+</#if>
              	</span>
                <input id="evaluate_scoreA<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreA" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
              <li>
              <#if evaluateRule.B?? &&evaluateRule.B?size &gt; 0>
              <#list evaluateRule.B as evaluate>
                <span style = "background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优</#if>
              	<#if evaluate.rank_code==2>良</#if>
              	<#if evaluate.rank_code==3>中</#if>
              	<#if evaluate.rank_code==4>差</#if>
              	</span>
                <input id="evaluate_scoreB<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreB" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
              <li>
              <#if evaluateRule.C?? &&evaluateRule.C?size &gt; 0>
              <#list evaluateRule.C as evaluate>
              <span style = " background:<#if evaluate.rank_code == 1>#${(colors[0].rank_color)!}</#if><#if evaluate.rank_code == 2>#${(colors[1].rank_color)!}</#if><#if evaluate.rank_code == 3>#${(colors[2].rank_color)!}</#if><#if evaluate.rank_code == 4>#${(colors[3].rank_color)!}</#if>;width:22px; height:22px;display:block; float:left; text-align:center; line-height:22px; color:#FFF; margin-left:15px;">
              	<#if evaluate.rank_code==1>优-</#if>
              	<#if evaluate.rank_code==2>良-</#if>
              	<#if evaluate.rank_code==3>中-</#if>
              	<#if evaluate.rank_code==4>差-</#if>
              	</span>
                <input id="evaluate_scoreC<#if evaluate.rank_code==1>1</#if><#if evaluate.rank_code==2>2</#if><#if evaluate.rank_code==3>3</#if><#if evaluate.rank_code==4>4</#if>" name="evaluate_scoreC" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(evaluate.rank_score)!}" oldval="${(evaluate.rank_score)!}" onChange="isChangeVal(this)" isc="0"/>
              </#list>
              </#if>
              </li>
            </#if>
            </ul>
          </div>
          <div class="clear"></div>
        </div>
<div class="pjyz_xzgz">
<samp>修正规则：</samp>
<div>
<span>项目修正：</span>
<ul>
<#if revisRule??&&revisRule?size &gt;0>
	<#list revisRule as revis>
	<#if revis.revis_type==1>
		<li>若项目等级为<#if revis.id==1>优</#if><#if revis.id==2>良</#if>（包含+/-）,但项目中有任一指标为差，该项目在原等级基础上降<input name="revis_left" type="hidden" value="${(revis.left_rate)!}" /><input name="revis_right" type="hidden" value="${(revis.right_rate)!}"/><input name="revis_level" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.revis_level)!}" oldval="${(revis.revis_level)!}" onChange="isChangeVal(this)" isc="0"/>档</li>
	</#if>
	</#list>
</#if>
</ul>
</div>
<div class="clear"></div>
<div>
<span>自由模式修正：</span>
<ul>
<#if revisRule??&&revisRule?size &gt;0>
	<#list revisRule as revis>
	<#if revis.revis_type==2>
		<#if revis_index != 11>
		<li>如果3G网络测试点所占比例在<input id="${'left'+(revis.id)!}" name="revis_left" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.left_rate)!}"  oldval="${(revis.left_rate)!}" onChange="isChangeVal(this)" isc="0"/>%　～<input id="${'right'+(revis.id)!}" name="revis_right" type="text" class="easyui-validatebox" <#if revis.id!=3>data-options="required:true,validType:['integration']"</#if><#if revis.id==3>data-options="required:true,validType:['absolutely']"</#if> value="${(revis.right_rate)!}" oldval="${(revis.right_rate)!}" onChange="isChangeVal(this)" isc="0"/>%  该项目在原等级基础上降<input id="${(revis.id)!}" name="revis_level" class="easyui-validatebox" data-options="required:true,validType:['integration']" type="text"  value="${(revis.revis_level)!}"  oldval="${(revis.revis_level)!}" onChange="isChangeVal(this)" isc="0"/>档</li>
		</#if>
		<#if revis_index == 11>
		<li>如果3G网络测试点所占比例在<input id="${'left'+(revis.id)!}" name="revis_left" type="hidden" value="${(revis.left_rate)!}"/><input id="right${(revis.id)!}" name="revis_right" type="text" class="easyui-validatebox" data-options="required:true,validType:['integration']" value="${(revis.right_rate)!}"  oldval="${(revis.right_rate)!}" onChange="isChangeVal(this)" isc="0"/>% <input name="revis_level" type="hidden" value="${(revis.revis_level)!}"/>以下，该项目定性为差</li>
		</#if>
	</#if>
	</#list>
</#if>
</ul>
</div>
</form>
<div class="clear"></div>



</div>
        <div class="bottom_btn yfpz_btn" style="float:right; margin-right:20px; margin-bottom:20px;">
          <a href="javascript:void(0)"  id="setIntegration" style="margin-left: 10px;" class="l-btn"><span class="l-btn-left"style="width:auto"><span class="l-btn-text">确  定</span></span></a>
        </div>
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
	<script src="${application.getContextPath()}/js/jscolor/jscolor.js" type="text/javascript"></script>
	<script src="${application.getContextPath()}/js/workorder/integration.js" type="text/javascript"></script>
	<script>
	var contextPath = '${application.getContextPath()}';
	var isChange = 0;
	</script>
</@script>