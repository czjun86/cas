<#if list?? &&  list?size &gt; 0>
	<#list list as workorder>
	 <ul <#if  workorder_index%2 == 1>class="all_list_background"</#if>>
	  <li style="width:16%;" title='${(workorder.serialno)!}'><a href="${contextPath!}/reportTask/reportlist?serialno=${(workorder.serialno)!}&netType=-1&testType=-1&areaid=${(workorder.areaid)!}&s_id=${(workorder.id)!}"  style="display:block;margin:0px 10px 0px 10px;text-overflow:ellipsis; white-space:nowrap;overflow:hidden;">${(workorder.serialno)!""}</a></li>
	  <li style="width:8%">${workorder.areaname!""}</li>	
	  <li style="width:8%">${(workorder.networktype)!""}</li>
	  <!--<li style="width:8%">${(workorder.usersBrand)!""}</li>-->
	  <li style="width:18%">
		   <#if (workorder.create_date)??>
		  	${(workorder.create_date)?string("yyyy-MM-dd HH:mm:ss")}
		  </#if>
	  </li>
	  <!--<li style="width:6%">-->
	  	<!--<#if (workorder.isDeal)?? && workorder.isDeal == 1>-->
	  		<!--已处理-->
	  	<!--<#else>-->
	  		<!--未处理-->
	  	<!--</#if>-->
	  <!--</li>-->
	  <li style="width:8%">
	  	<#if (workorder.breakflag)??>
	          <#switch workorder.breakflag >
			      <#case 1>优化<#break>
			      <#case 2>建设<#break>
			      <#case 3>维护<#break>
			      <#case 4>其他<#break>
			      <#default>
	          </#switch>
          </#if>
	  </li>
	  <li style="width:18%">
	  <#if (workorder.testtime)??>
	  	${(workorder.testtime)?string("yyyy-MM-dd HH:mm:ss")}
	  </#if>
	  </li>
	  <li style="width:12%">${(workorder.testPhone)!""}</li>
	  <li style="width:12%" class="all_list_riborder">
	  <#if (workorder.num)?? && (workorder.num) &gt;0 >
	  	<input type="button" class="compare_test"  title='加入对比' id="${(workorder.serialno)!}" s_id="${(workorder.id!)}" areaid="${(workorder.areaid)!}" />
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