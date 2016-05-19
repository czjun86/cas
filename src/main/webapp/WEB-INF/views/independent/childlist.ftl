<#if list?? &&  list?size &gt; 0>
	<#list list as workorder>
	 <ul <#if  workorder_index%2 == 1>class="all_list_background"</#if>>
	 <li style="width:16%;" title='${(workorder.serialno)!}'><a href="${contextPath!}/reportIndependent/reportlist?serialno=${(workorder.serialno)!}&netType=-1&testType=-1&areaid=${(workorder.areaid)!}&s_id=${(workorder.id)!}"  style="display:block;margin:0px 10px 0px 10px;text-overflow:ellipsis; white-space:nowrap;overflow:hidden;">${(workorder.serialno)!""}</a></li>
	  <li style="width:8%">${workorder.areaname!""}</li>	
	  <li style="width:8%">
	  	<#if (workorder.breakflag)?? && workorder.breakflag == 1>
	  		优化
	  	<#elseif (workorder.breakflag)?? && workorder.breakflag == 2>
	  		建设
	  	<#elseif (workorder.breakflag)?? && workorder.breakflag == 3>
	  		维护
	  	<#elseif (workorder.breakflag)?? && workorder.breakflag == 4>
	  		其它
	  	<#else>
	  		
	  	</#if>
	  </li>
	  <li style="width:12%">
		   <#if (workorder.create_date)??>
		  	${(workorder.create_date)?string("yyyy-MM-dd HH:mm:ss")}
		  </#if>
	  </li>
	  <li style="width:20%" title="${(workorder.test_address)!}">
	  	<#if workorder.test_address?? >
	          <#if workorder.test_address?length &gt; 19>
	          	${(workorder.test_address?substring(0,19))!} ...
	          <#else>
	          	${(workorder.test_address)!}
	          </#if>
	      </#if>
	  </li>
	  <li style="width:12%">
	  <#if (workorder.testtime)??>
	  	${(workorder.testtime)?string("yyyy-MM-dd HH:mm:ss")}
	  </#if>
	  </li>
	  <li style="width:12%">
	  <#if (workorder.testPhone)??>
	  	${(workorder.testPhone)!""}
	  </#if>
	  </li>
	  <li style="width:12%" class="all_list_riborder">
	  <#if (workorder.num)?? && (workorder.num) &gt;0 >
	  	<input type="button" class="compare_test"  title='加入对比' id="${(workorder.serialno)!}" s_id="${(workorder.id!)}" areaid="${(workorder.areaId)!}" />
	  	<input type="button" class="export_test" title='导出' style="display:none;"/>
	  	<input type="button" class="print_test" title='打印' style="display:none;" />
	  </#if>
	  </li>
	</ul>
	</#list>
<#else>
	 <ul>
	      <li class="all_list_riborder"  style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	</ul>
</#if>