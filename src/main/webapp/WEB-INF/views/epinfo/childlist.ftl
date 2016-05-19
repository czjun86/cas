 <#if list?? &&  list?size &gt; 0>
	<#list list as epinfo>
		 <ul <#if epinfo_index%2 == 1>class="all_list_background"</#if> >
		  <li style="width:5%;">
		    <p>
              	<input name="ids" type="checkbox" value="${(epinfo.id)!}" id="childs" checked="false" />
            </p>
           
          </li>
          <li style="width:10%"><#if (epinfo.areaid)?? && (epinfo.areaname)??>${(epinfo.areaname)!}<#else>无</#if></li>
          <li style="width:20%">${(epinfo.uuid)!}</li>
          <li style="width:12%"><#if (epinfo.functionary)??>${(epinfo.functionary)!}<#else>无</#if></li>
          <li style="width:12%"><#if (epinfo.teltphone)??>${(epinfo.teltphone)!}<#else>无</#if></li>
          <li style="width:10%"><#if (epinfo.islock)?? && (epinfo.islock) == 1>已授权<#else>未授权</#if></li>
          <li style="width:12%">
          <#if (epinfo.updatetime)??>
		  	${(epinfo.updatetime)?string("yyyy-MM-dd HH:mm:ss")}
		  </#if>	
	  	  </li>
          <li style="width:19%" class="all_list_riborder">
          		<#if buttons?? && buttons?size &gt; 0>
	          	<#list buttons as button>
	          		<#if (button.btntype)?? && button.btntype == 'update'>
	          			<input type="button" class="edit"  id="${(epinfo.id)!}" />
          			</#if>
	          		<#if (button.btntype)?? && button.btntype == 'delete'>
		          		<input type="button" class="del"   id="${(epinfo.id)!}" />
	          		</#if>
	          	</#list>
	          </#if>
          </li>
        </ul>
	</#list>
<#else>
	 <ul>
          <li style="width:100%;text-align: center;">
            	没有数据！
          </li>
    </ul>
</#if>