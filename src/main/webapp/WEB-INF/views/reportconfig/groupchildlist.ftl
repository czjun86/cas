 <#if list?? &&  list?size &gt; 0>
	<#list list as group>
		 <ul <#if group_index%2 == 1>class="all_list_background"</#if> >
         	 <li style="width:20%">${(group.groupname)!}</li>
         	 <li class="all_list_riborder" style="width:80%">
         	 	 		${group.areas!}
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