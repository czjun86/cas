 <#if list?? &&  list?size &gt; 0>
	<#list list as role>
		 <ul <#if role_index%2 == 1>class="all_list_background"</#if> >
		  <li style="width:5%;">
		  <#if role.roleid == 1 || role.roleid == 2|| role.roleid == 3>
		    <p>
              	--
            </p>
		  <#else>
		    <p>
              	<input name="ids" type="checkbox" value="${(role.roleid)!}" id="childs" checked="false" />
            </p>
		  </#if>
           
          </li>
          <li style="width:50%">${(role.rolename)!}</li>
          <li style="width:45%" class="all_list_riborder">
          <#if buttons?? && buttons?size &gt; 0>
          	<#list buttons as button>
          		<#if (button.btntype)?? && button.btntype == 'access'>
		          	<#if role.roleid == 1 || role.roleid == 2>
		          	<#else>
		          		<input type="button" class="access"  id="${role.roleid}"/>
		          	</#if>
	          	</#if>
	          </#list>
	      </#if>
          <#if buttons?? && buttons?size &gt; 0>
          	<#list buttons as button>
          		
          		<#if (button.btntype)?? && button.btntype == 'update'>
          			<input type="button" class="edit"  id="${role.roleid}"/>
          		</#if>
          		<#if (button.btntype)?? && button.btntype == 'delete'>
	          		<#if role.roleid == 1 || role.roleid == 2 || role.roleid == 3>
	          		<#else>
	          			<input type="button" class="del"   id="${(role.roleid)!}"/>
	          		</#if>
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