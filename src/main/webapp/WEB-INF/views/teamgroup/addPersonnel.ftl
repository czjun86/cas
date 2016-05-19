<form  method="post" id="addForm">
	<input type="hidden" name="type" value="${type!}"/>
    <ul class="form">
      <li><span style="text-align:left;"><samp style="color:red">*</samp>人员名称：</span>
        <p>
	      <input name="groupname" type="text" id="groupname"  class="flpi easyui-validatebox"  data-options="required:true,validType:['ench','lengthb[4,20]']"/>
        </p>
      </li>
      <li class="fl"><span  style="text-align:left;"><samp style="color:red">*</samp>电话：</span>
        <p class="flp">
          <input name="phone" type="text" id="phone" class="easyui-validatebox flpi" data-options="required:true,validType:['phone']"/>
        </p>
      </li>
    </ul>
</form>
