<div class="right1" id="full" style="display:none;">
	<div id="alertBackground" class="alertBackground"></div>
		<div id="dialogBackground" class="dialogBackground"></div>
		<div id='background' class='background'></div>
	
	<div id="bar_id">
<img src="../images/jbar.gif" /></span>
</div>
</div>
 <input id="type" type="hidden" value="${type!}"/>
  <form method="post" id="dataForm">
    <ul>
      <li class="fl"><span  class="fls">名称：</span>
        <p class="flp">
          <input name="server_name" type="text" id="server_name" class="easyui-validatebox flpi" value="${(tf.server_name)!}" maxlength="15"/>
        </p>
      </li>
      <li class="fl"><span class="fls">编号：</span>
        <p class="flp">
          <input name="server_num" type="text" id="server_num" class="easyui-validatebox flpi" data-options="validType:['num']" value="${(tf.server_num)!}" maxlength="10"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>用户名：</span>
        <p class="flp">
       	  <input name="username" type="text" id="username" class="easyui-validatebox flpi" data-options="required:true" value="${(tf.username)!}" maxlength="50"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>密码：</span>
        <p class="flp">
       	  <input name="pwd" type="password" id="pwd" class="easyui-validatebox flpi"  data-options="required:true" value="${(tf.pwd)!}" maxlength="20"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>IP：</span>
        <p class="flp">
       	  <input name="ip" type="text" id="ip" class="easyui-validatebox flpi"  data-options="required:true,validType:['ip']" value="${(tf.ip)!}"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>端口：</span>
        <p class="flp">
       	  <input name="port" type="text" id="port" class="easyui-validatebox flpi"  data-options="required:true,validType:['port']" value="<#if (tf.port)?? && tf.port == 0>${(tf.port)!}<#else>21</#if>" maxlength="5"/>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>上行路径：</span>
        <p class="flp">
       	  <input name="file_dir" type="text" id="file_dir" class="easyui-validatebox flpi"  data-options="required:true,validType:['ftpup']" value="${(tf.file_dir)!}" maxlength="100"/><p>例:/a</p>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>下行文件：</span>
        <p class="flp">
       	  <input name="file_name" type="text" id="file_name" class="easyui-validatebox flpi"  data-options="required:true,validType:['ftpPath']" value="${(tf.file_name)!}" maxlength="100"/><p>例:/a/a.txt</p>
        </p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>是否启用：</span>
        <p class="flp">
       	  <select name = "status" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="panelHeight: 'auto',editable:false,float:'left'">
       	  	<option value="0" <#if (tf.status)?? && tf.status == 0>selected</#if>>否</option>
       	  	<option value="1" <#if (tf.status)?? && tf.status == 1>selected</#if>>是</option>
       	  </select>
      	</p>
      </li>
      <li class="fl"><span  class="fls"><samp style="color:red">*</samp>文件大小：</span>
        <p class="flp">
       	  <input name="file_size" type="text" id="file_size" class="easyui-validatebox flpi" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="20" data-options="required:true" value="${(tf.file_size)!}" /><p>(Bytes)</p>
        </p>
      </li>
      <li class="fl"><span  class="fls">备注：</span>
        <p class="flp">
       	  <textarea rows="3" cols="20" id="remarker" name="remarker" style="border: 1px solid #D3D3D3;resize: none;" maxlength="30">${(tf.remarker)!}</textarea>
        </p>
      </li>
    </ul>
    	<input type="hidden" name="id" value="${(tf.id)!}"/>
      <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
</form>