 <#if list?? &&  list?size &gt; 0>
	<#list list as task>
		 <ul <#if task_index%2 == 1>class="all_list_background"</#if> >
          <li style="width:14%">${(task.serialno)!}</li>
          <li style="width:10%">${(task.create_date)?string("yyyy-MM-dd HH:mm:ss")}</li>
          <li style="width:5%">${(task.areaname)!}</li>
          <li style="width:7%">${(task.networktype)!}</li>
          <li style="width:7%">
          <#if (task.breakflag)??>
	          <#switch task.breakflag >
			      <#case 1>优化<#break>
			      <#case 2>建设<#break>
			      <#case 3>维护<#break>
			      <#case 4>其他<#break>
			      <#default>
	          </#switch>
          </#if>
          </li>
          <li style="width:17%" title="${(task.test_address)!}">
          <#if task.test_address?? >
	          <#if task.test_address?length &gt; 15>
	          	${(task.test_address?substring(0,15))!}..
	          <#else>
	          	${(task.test_address)!}
	          </#if>
	      </#if></li>
          <li style="width:8%"><#if (task.num)?? && task.num &gt; 0>已测试<#else>未测试</#if></li>
          <li style="width:8%">
		  <#if (task.isverify)??>
	          <#switch task.isverify >
			      <#case 0>未审核<#break>
			      <#case 1>已通过<#break>
			      <#case 2>未通过<#break>
			      <#default>
	          </#switch>
     	 </#if>
		  </li>
		  <li style="width:8%"><#if (task.validstate)?? && task.validstate ==1>已失效</#if></li>
          <li style="width:16%" class="all_list_riborder">
      			<input type="button" class="edit"  name="${(task.id)!}" />
      			<#if (task.validstate)?? && task.validstate ==1>
      				<input type="button" class="resetpsw staUse"  name="${(task.id)!}"/>
      			<#else>
			    	<input type="button" class="resetpsw staOff"  name="${(task.id)!}"/>
      			</#if>
          </li>
        </ul>
	</#list>
<#else>
	 <ul>
          <li style="width:100%;text-align: center;" >
            	没有数据！
          </li>
    </ul>
</#if>