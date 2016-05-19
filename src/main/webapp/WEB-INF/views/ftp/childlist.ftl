 <#if list?? &&  list?size &gt; 0>
	<#list list as ftp>
		 <ul <#if ftp_index%2 == 1>class="all_list_background"</#if> >
		  <li style="width:5%;">
          	<span>
	            <p>
	              <input type="checkbox" value="${(ftp.id)!}" name="ids" id="childs" />
	             <#if (ftp.status)?? && ftp.status == 1><input type="hidden" name = "sta" value="${(ftp.id)!}"></#if>
	            </p>
            </span>
	      </li>
          <li style="width:10%">${(ftp.server_name)!}</li>
          <li style="width:8%">${(ftp.server_num)!}</li>
          <li style="width:11%">${(ftp.username)!}</li>
          <li style="width:12%">${(ftp.ip)!}</li>
          <li style="width:5%">${(ftp.port)!}</li>
          <li style="width:10%" title='${(ftp.file_name)!}'><div class='ftpoverflow'>${(ftp.file_dir)!}</div></li>
          <li style="width:10%" title='${(ftp.file_name)!}'><div class='ftpoverflow'>${(ftp.file_name)!}</div></li>
          <li style="width:5%"><#if (ftp.status)?? && ftp.status == 0>否</#if><#if (ftp.status)?? && ftp.status == 1>是</#if></li>
          <li style="width:24%" class="all_list_riborder">
          	<#if buttons?? && buttons?size &gt; 0>
	      		<#list buttons as button>
		    		<#if (button.btntype)?? && button.btntype == 'edit'>
			          	<input type="button" class="edit"  id="${(ftp.id)!}" name="${(ftp.status)!}"/>
			        </#if>
			        <#if (button.btntype)?? && button.btntype == 'delete'>
			          	<input type="button" class="del"  id="${(ftp.id)!}" style="margin-right:5px;"/>
			        </#if>
			        <#if (button.btntype)?? && button.btntype == 'use'>
			          	<input type="button" class="resetpsw staUse"  name="${(ftp.id)!}"/>
			          	<input type="button" class="resetpsw staOff"  name="${(ftp.id)!}"/>
			          	
			          	<input type="hidden"  id="num${(ftp.id)!}" value="${(ftp.id)!}" name="${(ftp.status)!}"/>
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