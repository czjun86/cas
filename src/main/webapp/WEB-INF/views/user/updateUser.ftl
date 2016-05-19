
<input id="type" type="hidden" value="1"/>
  <form  method="post" id="dataForm">
    <ul>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>登录名：</span>
        <p  class="flp">
          <input name="userName" type="text" value="${(user.userName)!}" class="flpi" readonly="readonly" maxlength="20" minlength="4"/>
           <input name="userid" type="hidden" value="${(user.userid)!}" />
        </p>
      </li>
      <li class="fl" ><span class="fls"><samp style="color:red">*</samp>用户名：</span>
        <p  class="flp">
          <input name="name" type="text" value="${(user.name)!}" id="name" class="easyui-validatebox flpi" data-options="required:true,validType:['ench','lengthb[4,20]']"/>
        </p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>用户组：</span>
      	<p  class="flp">
	      	 <select id="roleid"  class="easyui-combobox flpi" data-options="editable:false,panelHeight:'auto',validType:'selectCheck[\'roleid\',\'valiSelect\']'"  name="roleid">	
	         <!--<select name="roleid" class="easyui-validatebox" data-options="required:true">-->
	         	<#if user.userid == 1>
		         	<option value="${user.roleid}">${(user.rolename)!}</option>
		        <#else>
		          <option value="-1">--请选择角色--</option>
		          <#if roles?? && roles?size &gt;0>
		          	<#list roles as role>
		          		<option value="${role.roleid}" <#if user.roleid == role.roleid>selected</#if>>${(role.rolename)!}</option>
		          	</#list>
		          </#if>
	         	</#if>
	          </select>
         </p> 
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>电话：</span>
        <p  class="flp">
          <input name="phone" type="text" value="${(user.phone)!}" id="phone" class="easyui-validatebox flpi" data-options="required:true,validType:['phone']"//>
        </p>
      </li>
      <li class="fl"><span class="fls" style="text-indent:6px">邮箱：</span>
        <p  class="flp">
          <input name="email" type="text" value="${(user.email)!}" id="email" class="easyui-validatebox flpi" data-options="validType:['email']"/>
        </p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>禁用：</span>
        <p  class="flp">
			是<input name="islock" type="radio" value="1" style="width:25px" <#if (user.islock)?? && user.islock == 1>checked</#if>/>
			否<input name="islock" type="radio" value="0" style="width:25px" <#if (user.islock)?? && user.islock == 0>checked</#if>/>
        </p>
      </li>
    </ul>
    <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
  </form>

