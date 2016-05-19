 <#if list?? &&  list?size &gt; 0>
	<#list list as user>
		 <ul <#if user_index%2 == 1>class="all_list_background"</#if> >
		  <li style="width:5%;">
            <p>
	            <#if (user.userid)?? && (user.userid == 1||user.userid == 2)>
	            --
	            <#else>
	              <input name="ids" type="checkbox" value="${user.userid}" id="childs" checked="false" />
	            </#if>
            </p>
          </li>
          <li style="width:12%">${(user.userName)!}</li>
          <li style="width:10%">${(user.name)!}</li>
          <li style="width:12%">${(user.rolename)!}</li>
          <li style="width:10%">${(user.phone)!}</li>
          <li style="width:17%">${(user.email)!}</li>
          <li style="width:5%">
	          <#if user.islock == 1>
	          	是
	          <#else>
	          	否
	          </#if>
       	  </li>
          <li style="width:29%" class="all_list_riborder">
	          <#if buttons?? && buttons?size &gt; 0>
	          		
		          <#list buttons as button>
			          <#if (button.btntype)?? && button.btntype == 'area'>
				          <#if (user.userid)?? && (user.userid == 1||user.userid == 2)>
				          	<#else>
				          		<input type="button" class="access2" id="${(user.userid)!}"/>
				          	</#if>
			          </#if>
			          <#if (button.btntype)?? && button.btntype == 'update'>
			          <#if (user.userid)?? && (user.userid == 1||user.userid == 2)>
			          	<#else>
				          	<input type="button" class="edit"  id="${(user.userid)!}"/>
			          	</#if>
			          </#if>
			          <#if (button.btntype)?? && button.btntype == 'delete'>
			          	<#if (user.userid)?? && (user.userid == 1||user.userid == 2)>
			          	<#else>
			          		<input type="button" class="del"  id="${(user.userid)!}"/>
			          	</#if>
			          </#if>
			          <#if (button.btntype)?? && button.btntype == 'reset'>
			          	<#if (user.userid)?? && (user.userid == 1||user.userid == 2)>
			          	<#else>
			          		 <input type="button" class="resetpsw"  id="${(user.userid)!}"/>
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