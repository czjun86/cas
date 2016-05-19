<div class="con-tk">
<div class="con-tk-left">
<span>名称</span>
<div class="clear"></div>
<ul id="typename">
<li id="grouplb" <#if type?? && type==1>class="con-tk-left-now"</#if>>分公司类别</li>
<li id="groupgs" <#if type?? && type==0>class="con-tk-left-now"</#if>>分公司归属</li>
</ul>
</div>
<div class="configure gs" <#if type?? && type==1>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>已归属</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if groups?? && groups?size &gt;0>
      	 <#list groups as group>
      	 	<li val="${group.groupid!}" class="configure-cur <#if group_index==0>now-ts now</#if>">${group.groupname!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if groups?? && groups?size &gt;0>
      	 <#list groups as group>
      	 	<ul class="configure-belong areas" id="areas_${group.groupid!}" val='${group.groupid!}'<#if group_index &gt; 0>style="display:none;"</#if>>
      	 	<#if group.list?size &gt; 0>
      	 		<#list group.list as area>
      	 		 	<#if area.areaid??><li val="${area.areaid!}">${area.areaname!}</li></#if>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span id="right" val='0'><img  src="../images/configure-01b.png" /></span><span id="left" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>待归属</span>
  <div class="clear"></div>
  	 	<ul class="configure-belong unareas">
  	 	<#if unlist?size &gt; 0>
  	 		<#list unlist as area>
  	 		 	<li val="${area.areaid!}">${area.areaname!}</li>
  	 		 </#list>
  	 	</#if>
  	 	</ul>
  </div>
</div>
 <div class="configure lb" <#if type?? && type==0>style="display:none;"</#if>>
  <div class="configure-lb"><span><samp id="addGroup"><img src="../images/configure-add.png">新增</samp><samp id="editGroup"><img src="../images/configure-edit.png">编辑</samp><samp id="deleteGroup"><img src="../images/configure-dele.png">删除</samp></span>
  <div class="clear"></div>
<ul id="groupschk" >
	 <#if groups?? && groups?size &gt;0>
      	 <#list groups as group>
      	 	<li style="width:<#if len??>${len*7 + 22}</#if>px;"><input name="chk" type="checkbox" value="${group.groupid}" groupname="${group.groupname!}"/>${group.groupname!}</li>
      	 </#list>	
      </#if>	
</ul>
  </div>
</div>
</div>