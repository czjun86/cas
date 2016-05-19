<style>.forms .forms_data .forms_data_one ul li{border-bottom:none}
.div_eptime{float: right; position: absolute; top: 10px; right: 10px;}</style>
<#if list?? &&  list?size &gt; 0>
 <#list list as w>
<#if w_index==0>
 <div class="div_eptime"><#if (w.epTime)??>${w.epTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
</#if>
 </#list>
</#if>
<div class="forms_data_one">
              <ul>
                <li style="width:93px;">LAC</li>
                <li style="width:94px;">CID</li>
                <li style="width:94px;">BSIC</li>
                <li style="width:94px;">BCCH</li>
              </ul>
</div>
<div class="forms_data_one">
                <ul>
                 <#if list?? &&  list?size &gt; 0>
	            <#list list as w>
	            <#if w_index==0>
                <li class="ts_color" style="width:93px"><#if w.lac?? && w.lac!=-9999>${w.lac!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.cid?? && w.cid!=-9999>${w.cid!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.bsic?? && w.bsic!=-9999>${w.bsic!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.bcch?? && w.bcch!=-9999>${w.bcch!}</#if></li>
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
                <li style="width:93px;">RxLev_Full</li>
                <li style="width:94px;">RxLev_Sub</li>
                <li style="width:94px;">RxQual_Full</li>
                <li style="width:94px;">RxQual_Sub</li>
              </ul>
</div>
<div class="forms_data_one">
                <ul>
          <#if list?? &&  list?size &gt; 0>
	            <#list list as w>
	            <#if w_index==0>
                <li class="ts_color" style="width:93px;"><#if w.rxlevFull?? && w.rxlevFull!=-9999>${w.rxlevFull!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.rxlevSub?? && w.rxlevSub!=-9999>${w.rxlevSub!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.rxqualFull?? && w.rxqualFull!=-9999>${w.rxqualFull!}</#if></li>
                <li class="ts_color" style="width:94px"><#if w.rxqualSub?? && w.rxqualSub!=-9999>${w.rxqualSub!}</#if></li>
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
                <li style="width:93px;">C/I</li>
                <li style="width:94px;"></li>
                <li style="width:94px;"></li>
                <li style="width:94px;"></li>
              </ul>
</div>
<div class="forms_data_one">
                <ul>
          <#if list?? &&  list?size &gt; 0>
	            <#list list as w>
	            <#if w_index==0>
	            <li class="ts_color" style="width:93px;"><#if w.cI?? && w.cI!=-9999>${w.cI!}</#if></li>
                <li class="ts_color" style="width:94px;"></li>
                <li class="ts_color" style="width:94px;"></li>
                <li class="ts_color" style="width:94px;"></li>
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
                <li style="width:75px;">BSIC</li>
                <li style="width:75px;">BCCH</li>
                <li style="width:75px;">RxLev</li>
                <li style="width:75px;">C1</li>
                <li style="width:75px;">C2</li>
              </ul>
</div>
<div class="forms_data_one">
              <ul>
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic1?? && w.bsic1!=-9999>${w.bsic1!}</#if></li>
                <li  style="width:75px;"><#if w.bcch1?? && w.bcch1!=-9999>${w.bcch1!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev1?? && w.rxlev1!=-9999>${w.rxlev1!}</#if></li>
                <li  style="width:75px;"><#if w.c11?? && w.c11!=-9999>${w.c11!}</#if></li>
                <li  style="width:75px;"><#if w.c21?? && w.c21!=-9999>${w.c21!}</#if></li>
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
                
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic2?? && w.bsic2!=-9999>${w.bsic2!}</#if></li>
                <li  style="width:75px;"><#if w.bcch2?? && w.bcch2!=-9999>${w.bcch2!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev2?? && w.rxlev2!=-9999>${w.rxlev2!}</#if></li>
                <li  style="width:75px;"><#if w.c12?? && w.c12!=-9999>${w.c12!}</#if></li>
                <li  style="width:75px;"><#if w.c22?? && w.c22!=-9999>${w.c22!}</#if></li>
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
              
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic3?? && w.bsic3!=-9999>${w.bsic3!}</#if></li>
                <li  style="width:75px;"><#if w.bcch3?? && w.bcch3!=-9999>${w.bcch3!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev3?? && w.rxlev3!=-9999>${w.rxlev3!}</#if></li>
                <li  style="width:75px;"><#if w.c13?? && w.c13!=-9999>${w.c13!}</#if></li>
                <li  style="width:75px;"><#if w.c23?? && w.c23!=-9999>${w.c23!}</#if></li>
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
               
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic4?? && w.bsic4!=-9999>${w.bsic4!}</#if></li>
                <li  style="width:75px;"><#if w.bcch4?? && w.bcch4!=-9999>${w.bcch4!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev4?? && w.rxlev4!=-9999>${w.rxlev4!}</#if></li>
                <li  style="width:75px;"><#if w.c14?? && w.c14!=-9999>${w.c14!}</#if></li>    
                <li  style="width:75px;"><#if w.c24?? && w.c24!=-9999>${w.c24!}</#if></li>
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
              
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic5?? && w.bsic5!=-9999>${w.bsic5!}</#if></li>
                <li  style="width:75px;"><#if w.bcch5?? && w.bcch5!=-9999>${w.bcch5!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev5?? && w.rxlev5!=-9999>${w.rxlev5!}</#if></li>
                <li  style="width:75px;"><#if w.c15?? && w.c15!=-9999>${w.c15!}</#if></li>
                <li  style="width:75px;"><#if w.c25?? && w.c25!=-9999>${w.c25!}</#if></li>
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
              <ul style="border-bottom:1px #c2c2c2 solid;">
               
                      <#if list?? &&  list?size &gt; 0>
	         <#list list as w>
	            <#if w_index==0>
                <li  style="width:75px;"><#if w.bsic6?? && w.bsic6!=-9999>${w.bsic6!}</#if></li>
                <li  style="width:75px;"><#if w.bcch6?? && w.bcch6!=-9999>${w.bcch6!}</#if></li>
                <li  style="width:75px;"><#if w.rxlev6?? && w.rxlev6!=-9999>${w.rxlev6!}</#if></li>
                <li  style="width:75px;"><#if w.c16?? && w.c16!=-9999>${w.c16!}</#if></li>
                <li  style="width:75px;"><#if w.c26?? && w.c26!=-9999>${w.c26!}</#if></li>
            </#if>
             </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
</#if>
              </ul>
</div>