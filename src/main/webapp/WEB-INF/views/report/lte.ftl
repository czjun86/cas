<style>.forms .forms_data .forms_data_one ul li{border-bottom:none}
.div_eptime{float: right; position: absolute; top: 10px; right: 10px;}
</style>
<#if list?? &&  list?size &gt; 0>
 <#list list as w>
<#if w_index==0>
 <div class="div_eptime"><#if (w.epTime)??>${w.epTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
</#if>
 </#list>
</#if>
<div class="forms_data_one">
              <ul>
                <li style="width:75px;">TAC</li>
                <li style="width:75px;">CID</li>
                <li style="width:75px;">PCI</li>
                <li style="width:75px;">CQI</li>
                <li style="width:75px;">BID</li>
              </ul>
</div>
<div class="forms_data_one">
                <ul>
                    <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
		    <li class="ts_color" style="width:75px;"><#if w.tac?? && w.tac!=-9999>${w.tac!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.cid?? && w.cid!=-9999>${w.cid!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.pci?? && w.pci!=-9999>${w.pci!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.cqi?? && w.cqi!=-9999&& w.cqi!=-9998>${w.cqi!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.ebid?? && w.ebid!=-9999&& w.ebid!=-9998>${w.ebid!}</#if></li>
		    </#if>
		</#list>
	  <#else>
	  <li style="width:100%;text-align: center;">
		没有数据！
	  </li>
	 </#if>
              </ul>
</div>
<div class="forms_data_one">
              <ul>
                <li style="width:75px;">RSRP</li>
                <li style="width:75px;">RSRQ</li>
                <li style="width:75px;">SINR</li>
                <li style="width:75px;">FTP速率</li>
                <li style="width:75px;"></li>
              </ul>
</div>
<div class="forms_data_one">
              <ul>           
          <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
		    <li class="ts_color" style="width:75px;"><#if w.rsrp?? && w.rsrp!=-9999>${w.rsrp!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.rsrq?? && w.rsrq!=-9999>${w.rsrq!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.snr?? && w.snr!=-9999>${w.snr!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.ftpSpeed?? && w.ftpSpeed!=-9999>${w.ftpSpeed!}</#if></li>
            <li class="ts_color" style="width:75px;"></li>
		    </#if>
		</#list>
	  <#else>
	  <li style="width:100%;text-align: center;">
		没有数据！
	  </li>
	 </#if>
              </ul>
</div>
<!--===== -->
<div class="forms_data_two">
           
            </div>

<!--hkhnuip -->
<div class="forms_data_two" style=" border-top:none;">
           
            </div>










