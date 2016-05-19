
<input id="type" type="hidden" value="1"/>
  <form  method="post" id="dataForm">
    <ul>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>归属区域：</span>
      	<p class="flp">
     	 <select name="areaid" id = "areaid" class="easyui-combobox flpi" data-options="editable:false,validType:'selectCheck[\'areaid\',\'valiSelect\']'">
         <!--<select name="areaid" class="required" style="border:2px grey solid;height:20px;width:65px;">-->
         	<#if !(epinfo.areaid)??><option value="">无</option></#if>
         	<#list areas as area>
	         <option value="${(area.areaid)!}" <#if (epinfo.areaid)?? && epinfo.areaid == area.areaid>selected</#if>>${(area.areaname)!}</option>
	        </#list>
          </select>
        </p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>UUID：</span>
        <p class="flp">
        	<input name="id" type="hidden" value="${(epinfo.id)!}" id="id"/>
        	<input class="easyui-validatebox flpi" name="uuid" type="text" value="${(epinfo.uuid)!}"  id="uuid" data-options="required:true,validType:['uuid','remote[\'${application.getContextPath()}/epinfo/uuidIsExsit\',\'uuid\',\'${(epinfo.uuid)!}\',\'UUID\']']" maxlength="15"/>
        </p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>责任人：</span>
      	<p class="flp">
          <input name="functionary" type="text" value="${(epinfo.functionary)!}"  id="functionary" class="easyui-validatebox flpi" data-options="required:true,validType:['functionary']" maxlength="12"/>	
      	</p>
      </li>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>联系电话：</span>
      	<p class="flp">
          <input name="teltphone" type="text" value="${(epinfo.teltphone)!}"  id="teltphone" class="easyui-validatebox flpi" data-options="required:true,validType:['teltphone']" maxlength="12"/>	
      	</p>
      </li>
      <li class="fl"><span class="fls">是否授权：</span>
      	<p class="flp">
          <select name="islock" id="islock" class="easyui-combobox flpi" data-options="editable:false,panelHeight:'auto',validType:'selectCheck[\'islock\',\'valiSelect\']'">
	         <option value="0" <#if (epinfo.islock)?? && epinfo.islock == 0>selected</#if>>未授权</option>
	       	 <option value="1" <#if (epinfo.islock)?? && epinfo.islock == 1>selected</#if>>已授权</option>
          </select>
        </p>
      </li>
      <li class="fl"><span class="fls">添加时间：</span>
      	<p class="flp">
          <#if (epinfo.addtime)??>
		  	${(epinfo.addtime)?string("yyyy-MM-dd HH:mm:ss")}
		  </#if>
		</p>
      </li>
      <li class="fl"><span class="fls">更新时间：</span>
       	<p class="flp">
          <#if (epinfo.updatetime)??>
		  	${(epinfo.updatetime)?string("yyyy-MM-dd HH:mm:ss")}
		  </#if>
		</p>
      </li>
    </ul>
      <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
  </form>
<script>
$('.chkenter').keypress(function(e){
	if(e.keyCode == 13){
		return false;
	}
});
</script>
