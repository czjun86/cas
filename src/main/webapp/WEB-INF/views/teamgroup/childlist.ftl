<#if groupManager?? &&  groupManager?size &gt; 0>
	<#list groupManager as gm>
		<ul  <#if gm_index%2 == 1>class="all_list_background"</#if>>
          <li style="width:20%">${gm.group_big_name!'&nbsp;'}</li>
          <li style="width:20%">${gm.group_small_name!'&nbsp;'}</li>
          <li style="width:20%">
          		${gm.manager_name!'&nbsp;'}
          </li>
          <li name="areaname" style="width:40%;height:auto;" class="all_list_riborder">
          		${gm.area_name!'&nbsp;'}
          </li>
        </ul>
	</#list>
<#else> 
	<ul>
      <li style="width:100%;text-align: center;">
        	没有数据！
      </li>
    </ul>
</#if>
<script>
function heightnum(str){
	var num = str.substring(0,str.length);
	return parseInt(num);
}
$(function(){
	$("li[name='areaname']").each(function(){
		var area = $(this);
		var manager = $(this).prev("li");
		var sgroup = $(this).prev("li").prev("li");
		var bgroup = $(this).prev("li").prev("li").prev("li");
		
		var areanum = heightnum(area.css("height"));
		var managernum = heightnum(manager.css("height"));
		var sgroupnum = heightnum(sgroup.css("height"));
		var bgroupnum = heightnum(bgroup.css("height"));
		var num = areanum;
		if(managernum>areanum){
			num = managernum;
		}
		if(sgroupnum>managernum){
			num = sgroupnum;
		}
		if(bgroupnum>sgroupnum){
			num = bgroupnum;
		}
		if(num>36){
			area.css("height",num);
			if(num>areanum){
				area.css("line-height",num+"px");
			}
			
			manager.css("height",num);
			if(num>managernum){
				manager.css("line-height",num+"px");
			}
			
			sgroup.css("height",num);
			if(num>sgroupnum){
				sgroup.css("line-height",num+"px");
			}
			
			bgroup.css("height",num);
			if(num>bgroupnum){
				bgroup.css("line-height",num+"px");
			}
			
			area.parent("ul").css("height",num);
		}
	});
});
</script>