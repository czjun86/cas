<form  method="post" id="addForm">
	<input type="hidden" name="type" value="${type!}"/>
    <ul class="form">
      <li><span style="text-align:left;"><samp style="color:red">*</samp><#if type?? && type==1>大组<#else>小组</#if>名称：</span>
        <p>
	      <input name="groupname" type="text" id="groupname"  class="flpi easyui-validatebox"  data-options="required:true,validType:['ench','lengthb[4,20]','remote[\'${application.getContextPath()}/teamgroup/isExsit\',\'groupname\',\'\',\'<#if type?? && type==1>大组<#else>小组</#if>名称\']']"/>
        </p>
      </li>
    </ul>
</form>
