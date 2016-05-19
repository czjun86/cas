<style>
.hptable tr td{border:none;}
</style>
	      
	      
 <#if hplist?? && hplist?size &gt;0>
<#list hplist as hp>	      
<table width="96%" border="0" cellspacing="1" cellpadding="0"  style="background:#A2A2A2; text-align:center; margin-left: 2%;" class="hptable">
  <tr>
    <td rowspan="2" bgcolor="#f0f0f0">PING</td>
    <td bgcolor="#f0f0f0">丢包率(%)</td>
    <td bgcolor="#f0f0f0">最大延迟(ms)</td>
    <td bgcolor="#f0f0f0">最小延迟(ms)</td>
    <td bgcolor="#f0f0f0">平均延迟(ms)</td>
    <td rowspan="2" bgcolor="#f0f0f0">HTTP</td>
    <td bgcolor="#f0f0f0">最大下载速度(Kbps)</td>
    <td bgcolor="#f0f0f0">最小下载速度(Kbps)</td>
    <td bgcolor="#f0f0f0">平均下载速度(Kbps)</td>
    <td bgcolor="#f0f0f0">最大响应时间(ms)</td>
    <td bgcolor="#f0f0f0">最小响应时间(ms)</td>
    <td bgcolor="#f0f0f0">平均响应时间(ms)</td>
  </tr>
  <tr>
    <td bgcolor="#f0f0f0" name="pinglo" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.plostate?? && hp.plostate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.pinglo!}</td>
    <td bgcolor="#f0f0f0" name="pingdmax" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.pdmaxstate?? && hp.pdmaxstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.pingdmax!}</td>
    <td bgcolor="#f0f0f0" name="pingdmix" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.pdminstate?? && hp.pdminstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.pingdmix!}</td>
    <td bgcolor="#f0f0f0" name="pingdavg" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.pdavgstate?? && hp.pdavgstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.pingdavg!}</td>
    <td bgcolor="#f0f0f0" name="httpsmax" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.hsmaxstate?? && hp.hsmaxstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httpsmax!}</td>
    <td bgcolor="#f0f0f0" name="httpsmin" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.hsminstate?? && hp.hsminstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httpsmin!}</td>
    <td bgcolor="#f0f0f0" name="httpsavg" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.hsavgstate?? && hp.hsavgstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httpsavg!}</td>
    <td bgcolor="#f0f0f0" name="httptmax" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.htmaxstate?? && hp.htmaxstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httptmax!}</td>
    <td bgcolor="#f0f0f0" name="httptmix" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.htminstate?? && hp.htminstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httptmix!}</td>
    <td bgcolor="#f0f0f0" name="httptavg" style="color:<#if colorlist?? && colorlist?size &gt; 0>
		        <#if hp.htavgstate?? && hp.htavgstate==1>
		            <#list colorlist as c>
		               <#if c_index==0>
		                 ${c.colourcode}
		               </#if>
		            </#list>
		        </#if>
		          </#if>">${hp.httptavg!}</td>
  </tr>
</table>
</#list>
</#if>