<form  method="post" id="dataForm">
	<ul>
		<input type="hidden" name="id" value = "${(to.serialno)!}" >
		<input type="hidden" name="handler" value = "${(user)!}" >
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>工单号：</span>
	  	<p class="flp">
	      <input name="serialno" class="flpi" disabled="disabled" style="width:180px;" type="text" value="${(to.serialno)!}"  />	
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>创建时间：</span>
	  	<p class="flp">
	      <input name="creatdate" class="flpi" disabled="disabled" style="width:180px;" type="text" value="${(to.create_date)?string("yyyy-MM-dd HH:mm:ss")}"  />	
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>区域：</span>
	  	<p class="flp">
	  		<select name = "areaid" class="easyui-combobox areaid" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,required:true">
			  <#if (arealist)??>
			      <#list arealist as area>
			      	<option value="${(area.areaid)!}" <#if (to.areaname)?? && to.areaid=area.areaid>selected</#if>>${area.areaname!}</option>
			      </#list>
			  </#if>
			</select>
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试网络：</span>
	  	<p class="flp">
		  <select name = "networktype" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
	  		<option value="2g数据" <#if (to.breakflag)??&&to.networktype=='2g数据'>selected</#if>>2g数据</option>
		  	<option value="2g语音" <#if (to.breakflag)??&&to.networktype=='2g语音'>selected</#if>>2g语音</option>
		  	<option value="3g数据" <#if (to.breakflag)??&&to.networktype=='3g数据'>selected</#if>>3g数据</option>
		  	<option value="3g语音" <#if (to.breakflag)??&&to.networktype=='3g语音'>selected</#if>>3g语音</option>
		  </select>
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试环节：</span>
	  	<p class="flp">
	  	  <select name = "breakflag" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="1" <#if (to.breakflag)??&&to.breakflag==1>selected</#if>>优化</option>
		  	<option value="2" <#if (to.breakflag)??&&to.breakflag==2>selected</#if>>建设</option>
		  	<option value="3" <#if (to.breakflag)??&&to.breakflag==3>selected</#if>>维护</option>
		  	<option value="4" <#if (to.breakflag)??&&to.breakflag==4>selected</#if>>其他</option>
		  </select>	
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试地址：</span>
	  	<p class="flp">
	  	  <textarea rows="3" cols="20" id="testaddress" name="test_address" style="border: 1px solid #D3D3D3;resize: none;" maxlength="100">${(to.test_address)!}</textarea>
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试状态：</span>
	  	<p class="flp">
	      <input name="testtime" class="flpi" disabled="disabled"  style="width:180px;" type="text" value="<#if (to.num)?? && to.num &gt; 0>已测试<#else>未测试</#if>"/>	
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>审核状态：</span>
	  	<p class="flp">
	      <input name="isverify" class="flpi" disabled="disabled"  style="width:180px;" type="text" value="<#if (to.isverify)??><#switch to.isverify ><#case 0>未审核<#break><#case 1>已通过<#break><#case 2>未通过<#break><#default></#switch></#if>"/>	
	  	</p>
	  </li>
	  <li class="fl"><span class="fls"><samp style="color:red">*</samp>有效状态：</span>
	  	<p class="flp">
	  	  <select name = "validstate" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
		  	<option value="0" <#if (to.validstate)??&&to.validstate==0>selected</#if>>有效</option>
		  	<option value="1" <#if (to.validstate)??&&to.validstate==1>selected</#if>>失效</option>
		  </select>	
	  	</p>
	  </li>
	</ul>
 </form>
