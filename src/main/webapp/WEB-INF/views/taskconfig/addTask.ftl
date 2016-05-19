<style>
.flp-ts span input{float:left;}
</style>
<input id="type" type="hidden" value="0"/>
<input id="areaid_option" type="hidden" value="<#if (arealist)??><#list arealist as area>${(area.areaid+',')!}</#list></#if>"/>
<input id="areaname_option" type="hidden" value="<#if (arealist)??><#list arealist as area>${(area.areaname+',')!}</#list></#if>"/>
<form  method="post" id="dataForm" class="addef">
    <ul>
		<li class="fl"><span class="fls"><samp style="color:red">*</samp>区域选择：</span>
			<p class="flp flp-ts">
			  <select name = "areaid" class="easyui-combobox areaid" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,required:true">
			  <#if (arealist)??>
			      <#list arealist as area>
			      	<option value="${(area.areaid)!}">${area.areaname!}</option>
			      </#list>
			  </#if>
			  </select>
			</p>
		</li>
  		<li class="fl"><span class="fls"><samp style="color:red">*</samp>测试网络：</span>
			<p class="flp flp-ts">
			  <select name = "nettype" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
			  	<option value="1">2g数据</option>
			  	<option value="2">2g语音</option>
			  	<option value="3">3g数据</option>
			  	<option value="4">3g语音</option>
			  </select>
			</p>
		</li>
		<li class="fl"><span class="fls"><samp style="color:red">*</samp>测试环节：</span>
			<p class="flp flp-ts">
			  <select name = "breakflag" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,panelHeight: 'auto'">
			  	<option value="1">优化</option>
			  	<option value="2">建设</option>
			  	<option value="3">维护</option>
			  	<option value="4">其他</option>
			  </select>
			</p>
		</li>
       <li class="fl"><span class="fls"><samp style="color:red">*</samp>测试地址：</span>
			<p class="flp">
			  <input name="testaddress" type="text" uuid="uuid"  class="chkent qer easyui-validatebox" style="width:130px;height:20px;border:1px solid #D3D3D3;"  maxlength="100"/>
			</p>
       </li>
       <li class="fl">
          <a name="add" class="change"><img src="${application.getContextPath()}/images/add.png" title="点击可以连续添加"/></a>
       </li>	
    </ul>
    <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
</form>