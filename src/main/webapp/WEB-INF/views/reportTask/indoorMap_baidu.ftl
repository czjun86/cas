
<!--<div class="maxmixSize distanceValue">-->
	<!--<div id="offDistance" style="background: url(&quot;/cas09/images/case_zhi_s.png;cursor:pointer&quot;) no-repeat scroll 0px 0px transparent; width: 20px; z-index: 999; float: left; cursor: pointer; height: 20px;margin-right:10px;margin-top:2px;"></div>-->
	<!--<span>最远距离:<input type="text" id="maxvalue" disabled="disabled" style="height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;"></span>-->
	<!--<span>最近距离:<input type="text" id="minvalue" disabled="disabled" style="height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;"></span>-->
	<!--<span>当前距离:<input type="text" id="currvalue" disabled="disabled" style="height:20px;border-color:#C8C8C8;width:70px;text-align:center;color:#ff00ff;"></span>-->
<!--</div>-->
<!--<div class="maxmixSize distanceNone" style="width:22px;display:none;">-->
	<!--<div id="onDistance" style="background: url(&quot;/cas09/images/case_zhi.png;cursor:pointer&quot;) no-repeat scroll 0px 0px transparent; width: 20px; z-index: 999; float: right; cursor: pointer; height: 20px;margin-right:5px;margin-top:2px;"></div>-->
<!--</div>-->
<div id="indoor_div" style="width:98.5%; height:92%;position:absolute;">

</div>
<script>
var lat = <#if lat?? && lat!= ''>'${lat!}'<#else>''</#if>;
var lng = <#if lng?? && lng!= ''>'${lng!}'<#else>''</#if>;
var flowid_ = <#if flowid_?? && flowid_!= ''>'${flowid_!}'<#else>''</#if>;
$(document).ready(function(){
	 initMap_baidu();
});
</script>
<ul style="display:none;">
	<li class="show_li">
		<span></span>
		WCDMA
		<div id="wcdma_pl" class="gis_histogram-submenu" style="display: none;">
		<ul>
			<li id="pp_sc" class="isgou" name="psc">
				<span></span>
				PSC
			</li>
			<li id="jizhan_g3" class="isgou" name="g3_name" style="border-bottom:none;">基站名称</li>
		</ul>
	</li>
	<li class="show_li" style="border-bottom:none;">
		<span></span>
		GSM
		<div id="gsm_pl" class="gis_histogram-submenu" style="display: none;">
		<ul>
			<li id="bb_ch" class="isgou" name="bcch">
				<span></span>
				BCCH
			</li>
			<li id="jizhan_g2" class="isgou" name="g2_name" style="border-bottom:none;">基站名称</li>
			</ul>
		</div>
	</li>
</ul>






