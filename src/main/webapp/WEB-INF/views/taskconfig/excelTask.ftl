<form  method="post" id="excelForm">
	<ul>
	  <li class="fl"><span class="fls">开始日期：</span>
	  	<p class="flp">
	      <input name= "startTime" value="${startTime!}" id="startTime" type="text" class="easyui-validatebox Wdate"  data-options="required:true"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" />
	  	</p>
	  <span class="fls">结束日期：</span>
	  	<p class="flp">
	      <input name="endTime" value="${endTime!}" id="endTime" type="text" class="easyui-validatebox Wdate"  data-options="required:true" style="width:78px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"  readonly="readonly" />
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>区域：</span>
	  	<p class="flp">
	  		<select name = "areaid" class="easyui-combobox areaid" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,required:true">
			  	  <option value="-1">全部</option>
			  <#if (arealist)??>
			      <#list arealist as area>
			      	<option value="${(area.areaid)!}" <#if (to.areaname)?? && to.areaid=area.areaid>selected</#if>>${area.areaname!}</option>
			      </#list>
			  </#if>
			</select>
	  	</p>
	   <span class="fls"><samp style="color:red">*</samp>测试网络：</span>
	  	<p class="flp">
		  <select name = "networktype" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
	  		<option value="-1">全部</option>
	  		<option value="2g数据">2g数据</option>
		  	<option value="2g语音">2g语音</option>
		  	<option value="3g数据">3g数据</option>
		  	<option value="3g语音">3g语音</option>
		  </select>
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试环节：</span>
	  	<p class="flp">
	  	  <select name = "breakflag" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="-1">全部</option>
		  	<option value="1">优化</option>
		  	<option value="2">建设</option>
		  	<option value="3">维护</option>
		  	<option value="4">其他</option>
		  </select>	
	  	</p>
	    <span class="fls"><samp style="color:red">*</samp>测试状态：</span>
	  	<p class="flp">
	  	  <select name = "num" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="-1">全部</option>
		  	<option value="0">未测试</option>
		  	<option value="1">已测试</option>
		  </select>
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>审核状态：</span>
	  	<p class="flp">
	  	  <select name = "isverify" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="-1">全部</option>
		  	<option value="0">未审核</option>
		  	<option value="1">已通过</option>
		  	<option value="2">未通过</option>
		  </select>	
	  	</p>
	    <span class="fls"><samp style="color:red">*</samp>有效状态：</span>
	  	<p class="flp">
	  	  <select name = "validstate" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="-1">全部</option>
		  	<option value="0">有效</option>
		  	<option value="1">失效</option>
		  </select>	
	  	</p>
	  </li>
	</ul>
 </form>
