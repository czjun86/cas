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
                <li style="width:75px;">LAC</li>
                <li style="width:75px;">CID</li>
                <li style="width:75px;">Freq_UL</li>
                <li style="width:75px;">Freq_DL</li>
                <li style="width:75px;">PSC</li>
              </ul>
</div>
<div class="forms_data_one">
                <ul>
                    <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
		    <li class="ts_color" style="width:75px;"><#if w.lac?? && w.lac!=-9999>${w.lac!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.cid?? && w.cid!=-9999>${w.cid!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.frequl?? && w.frequl!=-9999>${w.frequl!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.freqdl?? && w.freqdl!=-9999>${w.freqdl!}</#if></li>
            <li class="ts_color" style="width:75px;"><#if w.psc?? && w.psc!=-9999>${w.psc!}</#if></li>
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
                <li style="width:75px;">RSCP</li>
                <li style="width:75px;">Ec/No</li>
                <li style="width:75px;">TxPower</li>
                <li style="width:75px;">FTP速率</li>
                <li style="width:75px;"></li>
              </ul>
</div>
<div class="forms_data_one">
              <ul>           
          <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
		    <li class="ts_color" style="width:75px;"><#if w.rscp?? && w.rscp!=-9999>${w.rscp!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.ecNo?? && w.ecNo!=-9999>${w.ecNo!}</#if></li>
		    <li class="ts_color" style="width:75px;"><#if w.txpower?? && w.txpower!=-9999>${w.txpower!}</#if></li>
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
              <ul>
                <li style="width:75px;">邻区</li>
                <li style="width:75px">PSC</li>
                <li style="width:75px">RSCP</li>
                <li style="width:75px">Ec/No</li>
                <li style="width:75px">Freq_DL</li>
              </ul>
              <ul>
                <li style="height:92px; line-height:92px; width:75px;">A_set</li>
              <#if list?? &&  list?size &gt; 0>
			<#list list as w>
				<#if w_index==0>
				    <li class="ts_color" style="width:75px;"><#if w.aPsc1?? && w.aPsc1!=-9999>${w.aPsc1!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aRscp1?? && w.aRscp1!=-9999>${w.aRscp1!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aEcNo1?? && w.aEcNo1!=-9999>${w.aEcNo1!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aArfcn1?? && w.aArfcn1!=-9999>${w.aArfcn1!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aPsc2?? && w.aPsc2!=-9999>${w.aPsc2!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aRscp2?? && w.aRscp2!=-9999>${w.aRscp2!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aEcNo2?? && w.aEcNo2!=-9999>${w.aEcNo2!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aArfcn2?? && w.aArfcn2!=-9999>${w.aArfcn2!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aPsc3?? && w.aPsc3!=-9999>${w.aPsc3!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aRscp3?? && w.aRscp3!=-9999>${w.aRscp3!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aEcNo3?? && w.aEcNo3!=-9999>${w.aEcNo3!}</#if></li>
				    <li class="ts_color" style="width:75px;"><#if w.aArfcn3?? && w.aArfcn3!=-9999>${w.aArfcn3!}</#if></li>
				  </#if>
			 </#list>
		<#else>
	  <li style="width:100%;text-align: center;">
	    	没有数据！
	  </li>
	</#if>
	
              </ul>
              <ul>
                <li style="width:75px; height:185px; line-height:185px;">M_set</li>
                <#if list?? &&  list?size &gt; 0>
		<#list list as w>
			<#if w_index==0>
			    <li style="width:75px;"><#if w.mPsc1?? && w.mPsc1!=-9999>${w.mPsc1!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp1?? && w.mRscp1!=-9999>${w.mRscp1!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo1?? && w.mEcNo1!=-9999>${w.mEcNo1!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn1?? && w.mArfcn1!=-9999>${w.mArfcn1!}</#if></li>
			    <li style="width:75px;"><#if w.mPsc2?? && w.mPsc2!=-9999>${w.mPsc2!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp2?? && w.mRscp2!=-9999>${w.mRscp2!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo2?? && w.mEcNo2!=-9999>${w.mEcNo2!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn2?? && w.mArfcn2!=-9999>${w.mArfcn2!}</#if></li>
			    <li style="width:75px;"><#if w.mPsc3?? && w.mPsc3!=-9999>${w.mPsc3!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp3?? && w.mRscp3!=-9999>${w.mRscp3!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo3?? && w.mEcNo3!=-9999>${w.mEcNo3!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn3?? && w.mArfcn3!=-9999>${w.mArfcn3!}</#if></li>
			    <li style="width:75px;"><#if w.mPsc4?? && w.mPsc4!=-9999>${w.mPsc4!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp4?? && w.mRscp4!=-9999>${w.mRscp4!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo4?? && w.mEcNo4!=-9999>${w.mEcNo4!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn4?? && w.mArfcn4!=-9999>${w.mArfcn4!}</#if></li>
			    <li style="width:75px;"><#if w.mPsc5?? && w.mPsc5!=-9999>${w.mPsc5!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp5?? && w.mRscp5!=-9999>${w.mRscp5!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo5?? && w.mEcNo5!=-9999>${w.mEcNo5!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn5?? && w.mArfcn5!=-9999>${w.mArfcn5!}</#if></li>
			    <li style="width:75px;"><#if w.mPsc6?? && w.mPsc6!=-9999>${w.mPsc6!}</#if></li>
			    <li style="width:75px;"><#if w.mRscp6?? && w.mRscp6!=-9999>${w.mRscp6!}</#if></li>
			    <li style="width:75px;"><#if w.mEcNo6?? && w.mEcNo6!=-9999>${w.mEcNo6!}</#if></li>
			    <li style="width:75px;"><#if w.mArfcn6?? && w.mArfcn6!=-9999>${w.mArfcn6!}</#if></li>
			   </ul>
		  </#if>
    </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	
</#if>
              </ul>
            </div>

<!--hkhnuip -->
<div class="forms_data_two" style=" border-top:none;">
              <ul>
                <li style="width:22%; height:61px; line-height:61px; width:75px;">D_set</li>
             <#if list?? &&  list?size &gt; 0>
        <#list list as w>
        <#if w_index==0>
                <li style="width:75px;"><#if w.dPsc1?? && w.dPsc1!=-9999>${w.dPsc1!}</#if></li>
                <li style="width:75px;"><#if w.dRscp1?? && w.dRscp1!=-9999>${w.dRscp1!}</#if></li>
                <li style="width:75px;"><#if w.dEcNo1?? && w.dEcNo1!=-9999>${w.dEcNo1!}</#if></li>
                <li style="width:75px;"><#if w.dArfcn1?? && w.dArfcn1!=-9999>${w.dArfcn1!}</#if></li>
                <li style="width:75px;"><#if w.dPsc2?? && w.dPsc2!=-9999>${w.dPsc2!}</#if></li>
                <li style="width:75px;"><#if w.dRscp2?? && w.dRscp2!=-9999>${w.dRscp2!}</#if></li>
                <li style="width:75px;"><#if w.dEcNo2?? && w.dEcNo2!=-9999>${w.dEcNo2!}</#if></li>
                <li style="width:75px;"><#if w.dArfcn2?? && w.dArfcn2!=-9999>${w.dArfcn2!}</#if></li>
          </#if>
      </#list>
  <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	
</#if>

              </ul>
            </div>









