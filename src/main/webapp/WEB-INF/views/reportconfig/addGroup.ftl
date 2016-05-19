<form  method="post" id="addForm">
    <ul class="form">
      <li><span style="text-align:left;"><samp style="color:red">*</samp>分公司名称：</span>
        <p>
	      <input name="groupname" type="text" id="groupname"   class="flpi easyui-validatebox"  data-options="required:true,validType:['ench','lengthb[4,20]','remote[\'${application.getContextPath()}/reportconfig/isExsit\',\'groupname\',\'\',\'分公司名称\']']"/>
        </p>
      </li>
    </ul>
</form>
