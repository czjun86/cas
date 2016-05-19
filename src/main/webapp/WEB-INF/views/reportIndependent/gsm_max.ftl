 <div class="forms_data_one" style="width:100%;margin-top:10px">
              <ul style="width:100%;">
                <li style="width:20%">LAC</li>
                <li style="width:20%">CID</li>
                <li style="width:20%">BSIC</li>
                <li style="width:20%">BCCH</li>
                <li style="width:20%">RxLev_Full</li>
              </ul>
              <ul style="width:100%;">
              <#if list?? &&  list?size &gt; 0>
	            <#list list as w>
	            <#if w_index==0>
                <li class="ts_color" style="width:20%">${w.lac!}</li>
                <li class="ts_color" style="width:20%">${w.cid!}</li>
                <li class="ts_color" style="width:20%">${w.bsic!}</li>
                <li class="ts_color" style="width:20%">${w.bcch!}</li>
                <li class="ts_color" style="width:20%"><#if w.rxlevFull?? && w.rxlevFull!=-9999>${w.rxlevFull!}</#if></li>
                        </#if>
             </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	
</#if>
              </ul>
                <ul style="width:100%;">
                <li style="width:20%">RxLev_Sub</li>
                <li style="width:20%">RxQual_Full</li>
                <li style="width:20%">RxQual_Sub</li>
                <li style="width:20%"></li>
                <li style="width:20%"></li>
              </ul>
              <ul style="width:100%;">
              
                <#if list?? &&  list?size &gt; 0>
	            <#list list as w>
	            <#if w_index==0>
                <li class="ts_color" style="width:20%"><#if w.rxlevSub?? && w.rxlevSub!=-9999>${w.rxlevSub!}</#if></li>
                <li class="ts_color" style="width:20%"><#if w.rxqualFull?? && w.rxqualFull!=-9999>${w.rxqualFull!}</#if></li>
                <li class="ts_color" style="width:20%"><#if w.rxqualSub?? && w.rxqualSub!=-9999>${w.rxqualSub!}</#if></li>
                <li class="ts_color" style="width:20%"></li>
                <li class="ts_color" style="width:20%"></li>
                
                     </#if>
             </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	
</#if>

              </ul>
            </div>
           
            <div class="forms_data_two"  style="float:left; width:100%;">
              <ul>
                <li  style="width:20%; height:216px; line-height:216px;">邻区</li>
                <li  style="width:16%">BSIC</li>
                <li  style="width:16%">BCCH</li>
                <li  style="width:16%">RxLev</li>
                <li  style="width:16%">C1</li>
                 <li style="width:16%">C2</li>
                <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:16%"><#if w.bsic1?? && w.bsic1!=-9999>${w.bsic1!}</#if></li>
                <li  style="width:16%"><#if w.bcch1?? && w.bcch1!=-9999>${w.bcch1!}</#if></li>
                <li  style="width:16%"><#if w.rxlev1?? && w.rxlev1!=-9999>${w.rxlev1!}</#if></li>
                <li  style="width:16%"><#if w.c11?? && w.c11!=-9999>${w.c11!}</#if></li>
                <li  style="width:16%"><#if w.c21?? && w.c21!=-9999>${w.c21!}</#if></li>
                <li  style="width:16%"><#if w.bsic2?? && w.bsic2!=-9999>${w.bsic2!}</#if></li>
                <li  style="width:16%"><#if w.bcch2?? && w.bcch2!=-9999>${w.bcch2!}</#if></li>
                <li  style="width:16%"><#if w.rxlev2?? && w.rxlev2!=-9999>${w.rxlev2!}</#if></li>
                <li  style="width:16%"><#if w.c12?? && w.c12!=-9999>${w.c12!}</#if></li>
                <li  style="width:16%"><#if w.c22?? && w.c22!=-9999>${w.c22!}</#if></li>
                <li  style="width:16%"><#if w.bsic3?? && w.bsic3!=-9999>${w.bsic3!}</#if></li>
                <li  style="width:16%"><#if w.bcch3?? && w.bcch3!=-9999>${w.bcch3!}</#if></li>
                <li  style="width:16%"><#if w.rxlev3?? && w.rxlev3!=-9999>${w.rxlev3!}</#if></li>
                <li  style="width:16%"><#if w.c13?? && w.c13!=-9999>${w.c13!}</#if></li>
                <li  style="width:16%"><#if w.c23?? && w.c23!=-9999>${w.c23!}</#if></li>
                <li  style="width:16%"><#if w.bsic4?? && w.bsic4!=-9999>${w.bsic4!}</#if></li>
                <li  style="width:16%"><#if w.bcch4?? && w.bcch4!=-9999>${w.bcch4!}</#if></li>
                <li  style="width:16%"><#if w.rxlev4?? && w.rxlev4!=-9999>${w.rxlev4!}</#if></li>
                <li  style="width:16%"><#if w.c14?? && w.c14!=-9999>${w.c14!}</#if></li>    
                <li  style="width:16%"><#if w.c24?? && w.c24!=-9999>${w.c24!}</#if></li>
                <li  style="width:16%"><#if w.bsic5?? && w.bsic5!=-9999>${w.bsic5!}</#if></li>
                <li  style="width:16%"><#if w.bcch5?? && w.bcch5!=-9999>${w.bcch5!}</#if></li>
                <li  style="width:16%"><#if w.rxlev5?? && w.rxlev5!=-9999>${w.rxlev5!}</#if></li>
                <li  style="width:16%"><#if w.c15?? && w.c15!=-9999>${w.c15!}</#if></li>
                <li  style="width:16%"><#if w.c25?? && w.c25!=-9999>${w.c25!}</#if></li>
                <li  style="width:16%"><#if w.bsic6?? && w.bsic6!=-9999>${w.bsic6!}</#if></li>
                <li  style="width:16%"><#if w.bcch6?? && w.bcch6!=-9999>${w.bcch6!}</#if></li>
                <li  style="width:16%"><#if w.rxlev6?? && w.rxlev6!=-9999>${w.rxlev6!}</#if></li>
                <li  style="width:16%"><#if w.c16?? && w.c16!=-9999>${w.c16!}</#if></li>
                <li  style="width:16%"><#if w.c26?? && w.c26!=-9999>${w.c26!}</#if></li>
            </#if>
             </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
</#if>

              </ul>
            </div>
            <!--指标数据--右边结束 -->