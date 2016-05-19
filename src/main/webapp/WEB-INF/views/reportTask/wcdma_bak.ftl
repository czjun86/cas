<div class="forms_data_one">
	  <ul>
	    <li style="width:10%">LAC</li>
	    <li style="width:10%">PSC</li>
	    <li style="width:10%">CID</li>
	    <li style="width:10%">Freq_UL</li>
        <li style="width:10%">Freq_DL</li>
        <li style="width:10%">PSC</li>
	    <li style="width:10%">RSCP</li>
	    <li style="width:10%">Ec/No</li>
	    <li style="width:10%">TxPower</li>
	    <li style="width:10%">FTP速率</li>
	  </ul>
	  <ul>
	  <#if list?? &&  list?size &gt; 0>
	    <#list list as w>
		    <#if w_index==0>
		    <li class="ts_color" style="width:10%">${w.lac!}</li>
		    <li class="ts_color" style="width:10%">${w.psc!}</li>
		    <li class="ts_color" style="width:10%">${w.cid!}</li>
		    <li class="ts_color" style="width:10%"><#if w.frequl?? && w.frequl!=-9999>${w.frequl!}</#if></li>
            <li class="ts_color" style="width:10%"><#if w.freqdl?? && w.freqdl!=-9999>${w.freqdl!}</#if></li>
            <li class="ts_color" style="width:10%">${w.psc!}</li>
		    <li class="ts_color" style="width:10%">${w.rscp}</li>
		    <li class="ts_color" style="width:10%">${w.ecNo!}</li>
		    <li class="ts_color" style="width:10%"><#if w.txpower?? && w.txpower!=-9999>${w.txpower!}</#if></li>
		    <li class="ts_color" style="width:10%"><#if w.ftpSpeed?? && w.ftpSpeed!=-9999>${w.ftpSpeed!}</#if></li>
		    </#if>
		</#list>
	  <#else>
	  <li style="width:100%;text-align: center;">
		没有数据！
	  </li>
	 </#if>
	  </ul>
</div>

<div class="forms_data_two">
  <ul>
    <li style="width:25%">邻区</li>
    <li style="width:25%">PSC</li>
    <li style="width:25%">RSCP</li>
    <li style="width:25%">Ec/No</li>
  </ul>
  <ul>
    <li style="width:25%; height:92px; line-height:92px;">激活集(A_set)</li>
		<#if list?? &&  list?size &gt; 0>
			<#list list as w>
				<#if w_index==0>
				    <li class="ts_color" style="width:25%"><#if w.aPsc1?? && w.aPsc1!=-9999>${w.aPsc1!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aRscp1?? && w.aRscp1!=-9999>${w.aRscp1!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aEcNo1?? && w.aEcNo1!=-9999>${w.aEcNo1!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aPsc2?? && w.aPsc2!=-9999>${w.aPsc2!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aRscp2?? && w.aRscp2!=-9999>${w.aRscp2!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aEcNo2?? && w.aEcNo2!=-9999>${w.aEcNo2!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aPsc3?? && w.aPsc3!=-9999>${w.aPsc3!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aRscp3?? && w.aRscp3!=-9999>${w.aRscp3!}</#if></li>
				    <li class="ts_color" style="width:25%"><#if w.aEcNo3?? && w.aEcNo3!=-9999>${w.aEcNo3!}</#if></li>
				  </#if>
			 </#list>
		<#else>
	  <li style="width:100%;text-align: center;">
	    	没有数据！
	  </li>
	</#if>
  </ul>
  <ul>
    <li style="width:25%; height:185px; line-height:185px;">监视集(M_set)</li>
	<#if list?? &&  list?size &gt; 0>
		<#list list as w>
			<#if w_index==0>
			    <li style="width:25%"><#if w.mPsc1?? && w.mPsc1!=-9999>${w.mPsc1!}</#if></li>
			    <li style="width:25%"><#if w.mRscp1?? && w.mRscp1!=-9999>${w.mRscp1!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo1?? && w.mEcNo1!=-9999>${w.mEcNo1!}</#if></li>
			    <li style="width:25%"><#if w.mPsc2?? && w.mPsc2!=-9999>${w.mPsc2!}</#if></li>
			    <li style="width:25%"><#if w.mRscp2?? && w.mRscp2!=-9999>${w.mRscp2!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo2?? && w.mEcNo2!=-9999>${w.mEcNo2!}</#if></li>
			    <li style="width:25%"><#if w.mPsc3?? && w.mPsc3!=-9999>${w.mPsc3!}</#if></li>
			    <li style="width:25%"><#if w.mRscp3?? && w.mRscp3!=-9999>${w.mRscp3!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo3?? && w.mEcNo3!=-9999>${w.mEcNo3!}</#if></li>
			    <li style="width:25%"><#if w.mPsc4?? && w.mPsc4!=-9999>${w.mPsc4!}</#if></li>
			    <li style="width:25%"><#if w.mRscp4?? && w.mRscp4!=-9999>${w.mRscp4!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo4?? && w.mEcNo4!=-9999>${w.mEcNo4!}</#if></li>
			    <li style="width:25%"><#if w.mPsc5?? && w.mPsc5!=-9999>${w.mPsc5!}</#if></li>
			    <li style="width:25%"><#if w.mRscp5?? && w.mRscp5!=-9999>${w.mRscp5!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo5?? && w.mEcNo5!=-9999>${w.mEcNo5!}</#if></li>
			    <li style="width:25%"><#if w.mPsc6?? && w.mPsc6!=-9999>${w.mPsc6!}</#if></li>
			    <li style="width:25%"><#if w.mRscp6?? && w.mRscp6!=-9999>${w.mRscp6!}</#if></li>
			    <li style="width:25%"><#if w.mEcNo6?? && w.mEcNo6!=-9999>${w.mEcNo6!}</#if></li>
			   </ul>
		  </#if>
    </#list>
        <#else>
	 
	      <li style="width:100%;text-align: center;">
	        	没有数据！
	      </li>
	
</#if>
              <ul>
                <li style="width:25%; height:61px; line-height:61px;">检测集(D_set)</li>
      <#if list?? &&  list?size &gt; 0>
        <#list list as w>
        <#if w_index==0>
                <li style="width:25%"><#if w.dPsc1?? && w.dPsc1!=-9999>${w.dPsc1!}</#if></li>
                <li style="width:25%"><#if w.dRscp1?? && w.dRscp1!=-9999>${w.dRscp1!}</#if></li>
                <li style="width:25%"><#if w.dEcNo1?? && w.dEcNo1!=-9999>${w.dEcNo1!}</#if></li>
                <li style="width:25%"><#if w.dPsc2?? && w.dPsc2!=-9999>${w.dPsc2!}</#if></li>
                <li style="width:25%"><#if w.dRscp2?? && w.dRscp2!=-9999>${w.dRscp2!}</#if></li>
                <li style="width:25%"><#if w.dEcNo2?? && w.dEcNo2!=-9999>${w.dEcNo2!}</#if></li>
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

     