 <span class="wlfspan" style="margin-left:20px;">报表类型：</span>
    <div class="search_input" style="width:70px;height:28px;">
	      <select id="cc" class="easyui-combobox" name="type" style="width:65px;height:26px;" data-options="editable:false,panelHeight: 'auto'">  
	        <option  <#if type?? && type == 1>selected="selected"</#if> value="1">日报表</option>
	        <option <#if type?? && type == 2>selected="selected"</#if> value="2">周报表</option>
	        <option <#if type?? && type == 3>selected="selected"</#if> value="3">月报表</option>
	     </select> 
	 </div>
  	<span class="wlfspan" id= "daytitle" style="margin-left:15px;">日期：</span>
	<div class="search_input" id = "daydiv" style="width:88px;">
		 <h6></h6>
      <h5><input name= "day" value="${day!}" id="day" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker()" />
   		 </h5><h4></h4> 
    </div>
    <span class="wlfspan" id= "monthtitle" style="margin-left:15px;display:none;">月份：</span>
	<div class="search_input" id = "monthdiv" style="width:88px;display:none;">
		 <h6></h6>
      <h5><input name= "month" value="${month!}" id="month" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy-MM'})" />
   		 </h5><h4></h4> 
    </div>
    <span class="wlfspan" id= "weekstarttitle" style="margin-left:15px;display:none;">开始日期：</span>
	<div class="search_input" id = "weekstartdiv" style="width:88px;display:none;">
		 <h6></h6>
      <h5><input name= "weekstart" value="${weekstart!}" id="weekstart" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker()" />
   		 </h5><h4></h4> 
    </div>
    <span class="wlfspan" id= "weekendtitle" style="margin-left:15px;display:none;">结束日期：</span>
	<div class="search_input" id = "weekenddiv" style="width:88px;display:none;">
		 <h6></h6>
      <h5><input name= "weekend" value="${weekend!}" id="weekend" type="text" class="Wdate"  style="width:78px;"  readonly="readonly" onFocus="WdatePicker()" />
   		 </h5><h4></h4> 
    </div>
	 <span class="wlfspan" style="margin-left:15px;">区域：</span>
	    <div class="" style="float:left;margin-right:10px;">
          <h6></h6>
          <h5>
          <select id="areas" class="easyui-combobox areas"  style="width:80px;height:26px;" data-options="editable:false,panelHeight: '0px'">
          		<option><#if areatext?? && areatext !=''>${areatext!}<#else>全部</#if></option>	
          </select>
          <input type="hidden" id="areatext" name="areatext" value="${areatext!}"/>
          <input type="hidden" name="areaids" value="${areaids!-1}" id="areaids"/>
          <input type="hidden" name="queryids" value="${queryids!-1}" id="queryids"/>
          <input type="hidden" name="starttime" value="${starttime!}" id="starttime"/>
          <input type="hidden" name="endtime" value="${endtime!}" id="endtime"/>
          <input type="hidden" name="systime" value="${systime!}" id="systime" />
          <input type="hidden" value="${msg!}" id="msg"/>
          </h5><h4></h4>
         </div>