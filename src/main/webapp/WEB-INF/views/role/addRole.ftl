<input id="type" type="hidden" value="0"/>
<form  method="post" id="dataForm">
    <ul class="form">
      <li><span style="text-align:left;"><samp style="color:red">*</samp>角色名称：</span>
        <p>
	      <input name="rolename" type="text" id="name"   class="chkenter flpi easyui-validatebox"  data-options="required:true,validType:['ench','lengthb[4,20]','remote[\'${application.getContextPath()}/role/isExsit\',\'rolename\',\'\',\'角色名称\']']"/>
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