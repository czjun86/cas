<div class="forms_data_one" style="width:100%;margin-top:10px">
              <ul style="width:100%;">
                <li style="width:11%">TAC</li>
                <li style="width:11%">CID</li>
                <li style="width:11%">PCI</li>
                <li style="width:11%">RSRP</li>
                <li style="width:11%">RSRQ</li>
                <li style="width:11%">SINR</li>
                <li style="width:12%">FTP速率</li>
              </ul>
              <ul style="width:100%;">
               <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
                <li class="ts_color" style="width:11%">${w.tac!}</li>
                <li class="ts_color" style="width:11%">${w.cid!}</li>
                <li class="ts_color" style="width:11%">${w.pci!}</li>
                <li class="ts_color" style="width:11%">${w.rsrp}</li>
                <li class="ts_color" style="width:11%">${w.rsrq!}</li>
                <li class="ts_color" style="width:11%">${w.snr!}</li>
                <li class="ts_color" style="width:12%"><#if w.ftpSpeed?? && w.ftpSpeed!=-9999>${w.ftpSpeed!}</#if></li>
            		    </#if>
		</#list>
	  <#else>
	  <li style="width:100%;text-align: center;">
		没有数据！
	  </li>
	 </#if>
	 
              </ul>
            </div>

 <div class="forms_data_two"  style="float:left; width:50%;">

            </div>
            <!--指标数据--右边结束 -->
            <div class="forms_data_two"  style="float:left; width:50%;">
             
            </div>

     
     