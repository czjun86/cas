 <input id="type" type="hidden" value="0"/>
  <form method="post" id="dataForm">
    <ul>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>登录名：</span>
        <p class="flp">
          <input name="userName" type="text" id="userName" class="easyui-validatebox flpi"  data-options="required:true,validType:['alphanumeric','length[4,20]','remote[\'${application.getContextPath()}/user/isExsitUserName\',\'userName\',\'\',\'登录名\']']"/>
        </p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>用户名：</span>
        <p class="flp">
          <input name="name" type="text" id="name" class="easyui-validatebox flpi" data-options="required:true,validType:['ench','lengthb[4,20]']"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>用户组：</span>
        <p class="flp">
        	<select id="roleid"  class="easyui-combobox flpi" data-options="editable:false,panelHeight:'auto',validType:'selectCheck[\'roleid\',\'valiSelect\']'"  name="roleid">	
          <!--<select name="roleid" class="required" class="easyui-validatebox" data-options="required:true">-->
          	<option value="-1">--请选择角色--</option>
	          <#if roles?? && roles?size &gt;0>
	          	<#list roles as role>
	          		<option value="${(role.roleid)!}">${(role.rolename)!}</option>
	          	</#list>
	          </#if>
          </select>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>电话：</span>
        <p class="flp">
          <input name="phone" type="text" id="phone" class="easyui-validatebox flpi" data-options="required:true,validType:['phone']"/>
        </p>
      </li>
      <li class="fl"><span  class="fls" style="text-indent:6px">邮箱：</span>
        <p class="flp">
          <input name="email" type="text" id="email" class="easyui-validatebox flpi" data-options="validType:['email']"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>禁用：</span>
        <p class="flp">
			是<input name="islock" type="radio" value="1"  style="width:25px"/>
			否<input name="islock" type="radio" value="0" style="width:25px" checked/>
        </p>
      </li>
    </ul>
      <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
</form>