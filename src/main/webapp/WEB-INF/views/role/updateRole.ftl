
<input id="type" type="hidden" value="1"/>
  <form method="post" id="dataForm" class="form">
    <ul>
      <li><span style="text-align:left;"><samp style="color:red">*</samp>角色名称：</span>
        <p>
        <input name="roleid" type="hidden" value="${(role.roleid)!}" id="id"/>
          <input name="rolename" type="text" value="${(role.rolename)!}" id="name"  class="chkenter flpi easyui-validatebox"  data-options="required:true,validType:['ench','lengthb[4,20]','remote[\'${application.getContextPath()}/role/isExsit\',\'rolename\',\'${(role.rolename)!}\',\'角色名称\']']"/>
          <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
        </p>
      </li>
    </ul>
      
  </form>
<script>
$('.chkenter').keypress(function(e){
	if(e.keyCode == 13){
		return false;
	}
});
</script>
