<div class="con-tk">
<div class="con-tk-left">
<span>名称</span>
<div class="clear"></div>
<ul id="typename">
<li val='bigs' <#if type?? && type == 1 >class="con-tk-left-now"</#if>>大组</li>
<li val='smalls' <#if type?? && type == 2 >class="con-tk-left-now"</#if>>小组</li>
<li val='personnels' <#if type?? && type == 3 >class="con-tk-left-now"</#if>>人员</li>
<li val='bigs_smalls' <#if type?? && type == 4 >class="con-tk-left-now"</#if>>大组与小组关系</li>
<li val='smalls_personnels' <#if type?? && type == 5 >class="con-tk-left-now"</#if>>小组与人员关系</li>
<li val='personnels_areas' <#if type?? && type == 6 >class="con-tk-left-now"</#if>>人员与区域关系</li>
<li val='big_leader' <#if type?? && type == 7 >class="con-tk-left-now"</#if>>大组组长设置</li>
<li val='small_leader' <#if type?? && type == 8 >class="con-tk-left-now"</#if>>小组组长设置</li>
</ul>
</div>
<!-- 大组与小区关系-->
<div class="configure bigs_smalls" <#if type?? && type!=4>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>已归属</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if bigs?? && bigs?size &gt;0>
      	 <#list bigs as big>
      	 	<li val="${big.groupid!}" class="configure-cur big <#if big_index==0>now-ts now</#if>">${big.groupname!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if bigs?? && bigs?size &gt;0>
      	 <#list bigs as big>
      	 	<ul class="configure-belong select" id="select_${big.groupid!}" val='${big.groupid!}'<#if big_index &gt; 0>style="display:none;"</#if>>
      	 	<#if big.list?size &gt; 0>
      	 		<#list big.list as small>
      	 		 	<#if small.groupid??><li val="${small.groupid!}">${small.groupname!}</li></#if>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span class="right_btn" val='0'><img  src="../images/configure-01b.png" /></span><span class="left_btn" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>待归属</span>
  <div class="clear"></div>
  	 	<ul class="configure-belong unselect" >
  	 	<#if notbigs?size &gt; 0>
  	 		<#list notbigs as big>
  	 		 	<li val="${big.groupid!}">${big.groupname!}</li>
  	 		 </#list>
  	 	</#if>
  	 	</ul>
  </div>
</div>
<!-- 小组与人员-->
<div class="configure smalls_personnels" <#if type?? && type!=5>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>已归属</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if smalls?? && smalls?size &gt;0>
      	 <#list smalls as small>
      	 	<li val="${small.groupid!}" class="configure-cur big <#if small_index==0>now-ts now</#if>">${small.groupname!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if smalls?? && smalls?size &gt;0>
      	 <#list smalls as small>
      	 	<ul class="configure-belong select" id="select_${small.groupid!}" val='${small.groupid!}'<#if small_index &gt; 0>style="display:none;"</#if>>
      	 	<#if small.plist?size &gt; 0>
      	 		<#list small.plist as p>
      	 		 	<#if p.id??><li val="${p.id!}">${p.name!}</li></#if>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span class="right_btn" val='0'><img  src="../images/configure-01b.png" /></span><span class="left_btn" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>待归属</span>
  <div class="clear"></div>
  	 	<ul class="configure-belong unselect" >
  	 	<#if notsmalls?size &gt; 0>
  	 		<#list notsmalls as p>
  	 		 	<li val="${p.id!}">${p.name!}</li>
  	 		 </#list>
  	 	</#if>
  	 	</ul>
  </div>
</div>
<!-- 人员与区域 -->
<div class="configure personnels_areas" <#if type?? && type!=6>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>已归属</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if personnels?? && personnels?size &gt;0>
      	 <#list personnels as personnel>
      	 	<li val="${personnel.id!}" class="configure-cur big <#if personnel_index==0>now-ts now</#if>">${personnel.name!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if personnels?? && personnels?size &gt;0>
      	 <#list personnels as personnel>
      	 	<ul class="configure-belong select" id="select_${personnel.id!}" val='${personnel.id!}'<#if personnel_index &gt; 0>style="display:none;"</#if>>
      	 	<#if personnel.list?size &gt; 0>
      	 		<#list personnel.list as area>
      	 		 	<#if area.areaid??><li val="${area.areaid!}">${area.areaname!}</li></#if>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span class="right_btn" val='0'><img  src="../images/configure-01b.png" /></span><span class="left_btn" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>待归属</span>
  <div class="clear"></div>
  	 	<ul class="configure-belong unselect" >
  	 	<#if notpersonnels?size &gt; 0>
  	 		<#list notpersonnels as area>
  	 		 	<li val="${area.areaid!}">${area.areaname!}</li>
  	 		 </#list>
  	 	</#if>
  	 	</ul>
  </div>
</div>
<!-- 大组组长配置 -->
<div class="configure big_leader" <#if type?? && type!=7>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>组长</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if bigs?? && bigs?size &gt;0>
      	 <#list bigs as big>
      	 	<li val="${big.groupid!}" class="configure-cur big <#if big_index==0>now-ts now</#if>">${big.groupname!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if bigs?? && bigs?size &gt;0>
      	 <#list bigs as big>
      	 	<ul class="configure-belong select" id="select_${big.groupid!}" val='${big.groupid!}'<#if big_index &gt; 0>style="display:none;"</#if>>
      	 	<#if big.psl??>
      	 		 <#if big.psl??><li val="${big.psl.id!}">${big.psl.name!}</li></#if>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span class="right_btn" val='0'><img  src="../images/configure-01b.png" /></span><span class="left_btn" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>未分配人员</span>
  <div class="clear"></div>
  	 	<#if bigs?? && bigs?size &gt;0>
      	 <#list bigs as big>
      	 	<ul class="configure-belong unselect" id="unselect_${big.groupid!}" val='${big.groupid!}' <#if big_index &gt; 0>style="display:none;"</#if>>
      	 	<#if big.plist?size &gt; 0>
      	 		<#list big.plist as p>
      	 		 	<li val="${p.id!}">${p.name!}</li>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
</div>
<!-- 小组组长配置 -->
<div class="configure small_leader" <#if type?? && type!=8>style="display:none;"</#if>>
  <div class="configure-left"> <span style="border-right:1px #d9d9d9 solid;">类别</span><span>组长</span>
    <div class="clear"></div>
    <ul class="configure-family">
      <#if smalls?? && smalls?size &gt;0>
      	 <#list smalls as small>
      	 	<li val="${small.groupid!}" class="configure-cur big <#if small_index==0>now-ts now</#if>">${small.groupname!}</li>
      	 </#list>	
      </#if>	
    </ul>
    <#if smalls?? && smalls?size &gt;0>
      	 <#list smalls as small>
      	 	<ul class="configure-belong select" id="select_${small.groupid!}" val='${small.groupid!}'<#if small_index &gt; 0>style="display:none;"</#if>>
      	 	<#if small.psl?? >
      	 		
      	 		 	<#if small.psl.id??><li val="${small.psl.id!}">${small.psl.name!}</li></#if>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
  <div class="configure-bottom"><span class="right_btn" val='0'><img  src="../images/configure-01b.png" /></span><span class="left_btn" val='0'><img src="../images/configureb.png"/></span></div>
  <div class="configure-right">
  <span>未分配人员</span>
  <div class="clear"></div>
  	 	<#if smalls?? && smalls?size &gt;0>
      	 <#list smalls as small>
      	 	<ul class="configure-belong unselect" id="unselect_${small.groupid!}" val='${small.groupid!}' <#if small_index &gt; 0>style="display:none;"</#if>>
      	 	<#if small.notlarders?size &gt; 0>
      	 		<#list small.notlarders as p>
      	 		 	<li val="${p.id!}">${p.name!}</li>
      	 		 </#list>
      	 	</#if>
      	 	</ul>
      	 </#list>	
    </#if>
  </div>
</div>
<!-- 大组 -->
 <div class="configure bigs" <#if type?? && type!=1>style="display:none;"</#if>>
 <div class="configure-lb"><span><samp class="addTeam"><img src="../images/configure-add.png">新增</samp><samp class="editTeam"><img src="../images/configure-edit.png">编辑</samp><samp class="deleteTeam"><img src="../images/configure-dele.png">删除</samp></span>
  <div class="clear"></div>
	<ul id="chk">
	      <#if bigs?? && bigs?size &gt;0>
	      	 <#list bigs as big>
	      	 	<li style="width:<#if biglen??>${biglen*7 + 22}</#if>px;"><input name="chk" type="checkbox" value="${big.groupid}" groupname="${big.groupname!}"/>${big.groupname!}</li>
	      	 </#list>	
	      </#if>	
	</ul>
  </div>
</div>
<!--小组-->
<div class="configure smalls" <#if type?? && type!=2>style="display:none;"</#if>>
 <div class="configure-lb"><span><samp class="addTeam"><img src="../images/configure-add.png">新增</samp><samp class="editTeam"><img src="../images/configure-edit.png">编辑</samp><samp class="deleteTeam"><img src="../images/configure-dele.png">删除</samp></span>
  <div class="clear"></div>
	<ul id="chk">
	      <#if smalls?? && smalls?size &gt;0>
	      	 <#list smalls as small>
	      	 	<li style="width:<#if smalllen??>${smalllen*7 + 22}</#if>px;"><input name="chk" type="checkbox" value="${small.groupid}" groupname="${small.groupname!}"/>${small.groupname!}</li>
	      	 </#list>	
	      </#if>	
	</ul>
  </div>
</div>
<!-- 人员 -->
<div class="configure personnels" <#if type?? && type!=3>style="display:none;"</#if>>
 <div class="configure-lb"><span><samp class="addTeam"><img src="../images/configure-add.png">新增</samp><samp class="editTeam"><img src="../images/configure-edit.png">编辑</samp><samp class="deleteTeam"><img src="../images/configure-dele.png">删除</samp></span>
  <div class="clear"></div>
	<ul id="chk">
	      <#if personnels?? && personnels?size &gt;0>
	      	 <#list personnels as p>
	      	 	<li style="width:<#if personnellen??>${personnellen*7 + 22}</#if>px;"><input name="chk" type="checkbox" value="${p.id}" phone="${p.phone!}" groupname="${p.name!}"/>${p.name!}</li>
	      	 </#list>	
	      </#if>	
	</ul>
 </div>
</div>
</div>
<script>
   var _loadType = <#if type??>${type!}<#else>4</#if>;	
   var _thisVal = "";
   switch(_loadType){
	case 1:
	  _clicktype = $('.bigs');	
	  break;
	case 2:
	  _clicktype = $('.smalls');
	  break;
	case 3:
	  _clicktype = $('.personnels');
	  break;
	case 4:
	  _clicktype = $('.bigs_smalls');
	  break;
    case 5:
	  _clicktype = $('.smalls_personnels');
	  break;
	case 6:
	  _clicktype = $('.personnels_areas');
	  break;
	case 7:
	  _clicktype = $('.big_leader');
	  break;
	case 8:
	  _clicktype = $('.small_leader');
	  break;
	}		
	
</script>