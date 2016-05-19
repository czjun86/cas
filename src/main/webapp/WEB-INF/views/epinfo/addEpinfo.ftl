<style>
.flp-ts span input{float:left;}
</style>
<input id="type" type="hidden" value="0"/>
<form  method="post" id="dataForm" class="addef">
    <ul>
      <li class="fl"><span class="fls"><samp style="color:red">*</samp>区域选择：</span>
        <p class="flp flp-ts">
          <select name = "areaid" class="easyui-combobox" style="border:1px solid #D3D3D3;height:20px;width:80px;" data-options="editable:false,validType:'selectCheck[\'areaid\',\'valiSelect\']',float:'left'">
          <#if (areas)??>
	          <#list areas as area>
	          	<option value="${(area.areaid)!}">${area.areaname!}</option>
	          </#list>
          </#if>
          </select>
        </p>
       </li>
       <li class="fl"><span class="fls"><samp style="color:red">*</samp>UUID：</span>
        <p class="flp">
          <input name="uuid" type="text" uuid="uuid"  class="chkent qer easyui-validatebox" style="width:130px;height:20px;border:1px solid #D3D3D3;" data-options="required:true,validType:['uuid','remote[\'${application.getContextPath()}/epinfo/uuidIsExsit\',\'uuid\',\'\',\'UUID\']','equalsArray[\'uuid\',\'uuid\',\'UUID\']']" maxlength="15"/>
        </p>
       </li>
       <li class="fl"><span class="fls"><samp style="color:red">*</samp>联系人：</span>
        <p class="flp">
          <input name="functionary" type="text" class="chkenter easyui-validatebox" style="width:60px;height:20px;border:1px solid #D3D3D3;" data-options="required:true,validType:['functionary']" maxlength="12"/>
        </p>
       </li>
       <li class="fl"><span class="fls"><samp style="color:red">*</samp>联系电话：</span>
        <p class="flp">
          <input name="teltphone" type="text" class="chkenter easyui-validatebox" style="width:100px;height:20px;border:1px solid #D3D3D3;" data-options="required:true,validType:['teltphone']" maxlength="12" />
        </p>
       </li>
       <li class="fl">
          <a name="add" class="change"><img src="${application.getContextPath()}/images/add.png" title="点击可以连续添加"/></a>
      	  <a style="display:none;" name="del" class="change"><img src="${application.getContextPath()}/images/del.png" title="删除当前行"/></a>
       </li>
    </ul>
    <input type="hidden" name="${tokename!}" value="${tokenkey!}"/>
</form>
<script>
	var areaopname = [];
	var areaopid =[];
	var i =0;
	<#if (areas)??>
	    <#list areas as area>
	    areaopid[i] = ${(area.areaid)!};
	    areaopname[i] = '${(area.areaname)!}';
	    i++;
	    </#list>
	</#if>
$('.chkenter').keypress(function(e){
	if(e.keyCode == 13){
		return false;
	}
});
</script>