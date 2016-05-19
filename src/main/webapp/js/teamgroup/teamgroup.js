//保存点击的名称
var _clicktype = null;
$(function(){
	//var optInit = {callback:function(page_index,jq){
			$.ajax({
				type:"post",
	    		url: contextPath + "/teamgroup/teamgroup/template",
	    		data:({name: $("#hiddenval").val()}),
	    		success: function(data){
	    			$("#content").html(data);
	    		}
			});
		//}
	//};
	//$("#pager").pagination(pageTotal, optInit);//回调
	

	/**
	*模糊查询
	*/
	$("#search").click(function(){
		var nameval = $.trim($("#name").val());
		$("#hiddenval").val(nameval);
		$("#name").val(nameval);
		$("#searchForm").submit();
	});
	//点击编辑按钮
	$(".editgroup").click(function(){
		$("#managerdlg").dialog({
			href : contextPath + "/teamgroup/teammanager?type=4",
			height : 478,
			width : 668,
			title : "编辑",
			modal : true,
			closed : false,
			onClose:function(){
				window.location.href = window.location.href;
			}
		});
		$('#managerdlg').dialog('open');
		$.parser.parse('#managerdlg');
	});
	var saveCount = 0;
	// 编辑页面确定单击事件
	$('.btn_save').die().live('click',function(){
		if(saveCount == 0){
			saveCount ++;
			var _val = $('.con-tk-left-now').attr('val');
			var type = -1;
			if(_val == 'bigs_smalls'){
				type = 4;
			}else if(_val == 'smalls_personnels'){
				type = 5;
			}else if(_val == 'personnels_areas'){
				type = 6;
			}else if(_val == 'big_leader'){
				type = 7;
			}else if(_val == 'small_leader'){
				type = 8;
			}
			if(type != -1){
				var groups = new Array();
				_clicktype.find('.select').each(function(){
					var obj = {};
					obj['id'] = $(this).attr('val');
					obj['list'] = [];
					$(this).find('li').each(function(){
						obj['list'].push($(this).attr('val'));
					});
					groups.push(obj);
				});
				$.ajax({
					type:'post',
					url:contextPath + '/teamgroup/updatelist',
					data:{groups : JSON.stringify(groups),type:type},
					dataType:"json",
					success:function(data){
						if(data.msg == 1){
							$.messager.alert('提示','操作成功','success',function(){
								$('#managerdlg').dialog('refresh',contextPath + '/teamgroup/teammanager?type='+type);
							});
						}else{
							$.messager.alert('提示','操作失败！','error');
						}
						saveCount = 0;
					}
				});
			}else{
				$('#managerdlg').dialog('close');
				window.location.href = window.location.href;
			}
		}
	});
	//名称点击事件
	$('#typename').find('li').die().live('click',function(){
		$('.con-tk-left-now').removeClass('con-tk-left-now');
		$(this).addClass('con-tk-left-now');
		$('.configure').hide();
		_clicktype = $('.' +$(this).attr('val'));
		_clicktype.show();
	});
	// 类别点击事件
	$('.configure-family').find('li').die().live('click', function() {
		if (!$(this).hasClass('now')) {
			_clicktype.find(".right_btn").find('img').attr('src', '../images/configure-01b.png');
			_clicktype.find(".right_btn").attr('val', 0);
			_clicktype.find(".left_btn").find('img').attr('src', '../images/configureb.png');
			_clicktype.find(".left_btn").attr('val', 0);
			_clicktype.find('.select').find('li').removeClass('areanow');
			_clicktype.find('.unselect').find('li').removeClass('areanow');
			_clicktype.find('.configure-family').find('li').removeClass('now');
			$(this).addClass('now');
			// 隐藏已归属的区域
			_clicktype.find('.select').hide();
			
			var id = $(this).attr('val');
			// 显示当前已归属的区域
			_clicktype.find('#select_' + id).show();
			//小组、大组组长配置处理
			var _val = $('.con-tk-left-now').attr('val');
			if(_val == 'big_leader' || _val == 'small_leader'){
				_clicktype.find('.unselect').hide();
				_clicktype.find('#unselect_' + id).show();
			}
			
		}
	});
	var rightFn = null;
	var leftFn = null;
	// 已归属区域点击事件
	$('.select').find('li').die().live('click', function() {
		// 取消上次延时未执行的方法
		clearTimeout(rightFn);
		var _this = $(this);
		rightFn = setTimeout(function() {
			_clicktype.find(".right_btn").find('img').attr('src', '../images/configure-01.png');
			_clicktype.find(".right_btn").attr('val', 1);
			_clicktype.find('.select').find('li').removeClass('areanow');
			_this.addClass('areanow');
		}, 300);

	});

	// 已归属区域双击击事件
	$('.select').find('li').live('dblclick', function() {
		// 取消上次延时未执行的方法
		clearTimeout(rightFn);
		_clicktype.find(".right_btn").find('img').attr('src', '../images/configure-01b.png');
		_clicktype.find(".right_btn").attr('val', 0);
		_clicktype.find('.select').find('li').removeClass('areanow');
		_clicktype.find('.unselect').append($(this));
	});
	// 未归属区域点击事件
	$('.unselect').find('li').live('click', function() {
		// 取消上次延时未执行的方法
		clearTimeout(leftFn);
		var _this = $(this);
		leftFn = setTimeout(function() {
			var groupid = _clicktype.find(".now").attr('val');
			var len =  _clicktype.find('#select_' + groupid).find('li').length;
			var val = $('.con-tk-left-now').attr('val');
			if((val != 'big_leader' && val != 'small_leader') || ((val == 'big_leader' || val == 'small_leader') && len == 0)){
				_clicktype.find(".left_btn").find('img').attr('src', '../images/configure.png');
				_clicktype.find(".left_btn").attr('val', 1);
				//$('#areas_' + groupid).append($(this));
				_clicktype.find('.unselect').find('li').removeClass('areanow');
				_this.addClass('areanow');
			}
		}, 300);

	});
	// 未归属区域双击击事件
	$('.unselect').find('li').live('dblclick', function() {
		// 取消上次延时未执行的方法
		clearTimeout(leftFn);
		var groupid = _clicktype.find(".now").attr('val');
		var len =  _clicktype.find('#select_' + groupid).find('li').length;
		var val = $('.con-tk-left-now').attr('val');
		if((val != 'big_leader' && val != 'small_leader') || ((val == 'big_leader' || val == 'small_leader') && len == 0)){
			_clicktype.find('.unselect').find('li').removeClass('areanow');
			_clicktype.find(".left_btn").find('img').attr('src', '../images/configureb.png');
			_clicktype.find(".left_btn").attr('val', 0);
			_clicktype.find('#select_' + groupid).append($(this));
		}
	});
	// 点击右移
	$('.right_btn').die().live('click', function() {
		if ($(this).attr('val') == '1') {
			$(this).find('img').attr('src', '../images/configure-01b.png');
			// 得到当前选中区域
			var _areanow = _clicktype.find('.select').find('.areanow');
			_areanow.removeClass('areanow');
			_clicktype.find('.unselect').append(_areanow);
			// _areanow.remove();
			$(this).attr('val', 0);
		}
	});
	// 点击左移
	$('.left_btn').die().live('click', function() {
		var groupid = _clicktype.find(".now").attr('val');
		var len =  _clicktype.find('#select_' + groupid).find('li').length;
		var val = $('.con-tk-left-now').attr('val');
		if((val != 'big_leader' && val != 'small_leader') || ((val == 'big_leader' || val == 'small_leader') && len == 0)){
			if ($(this).attr('val') == '1') {
				$(this).find('img').attr('src', '../images/configureb.png');
				$(this).attr('val', 0);
				// 得到当前选中区域
				var _this = _clicktype.find('.unselect').find('.areanow');;
				_this.removeClass('areanow');
				//var groupid = _clicktype.find(".now").attr('val');
				_clicktype.find('#select_' + groupid).append(_this);
			}
		}
		
	});
	//新增  点击事件
	$('.addTeam').die().live('click',function(){
		var val = $('.con-tk-left-now').attr('val');
		var url = '';
		var title = '';
		var width = 435;
		var height = 130;
		if(val == 'bigs'){
			url = contextPath + '/teamgroup/addteam?type=1';
			title = '添加大组';
		}else if(val == 'smalls'){
			url = contextPath + '/teamgroup/addteam?type=2';
			title = '添加小组';
			
		}else{
			url = contextPath + '/teamgroup/addteam?type=3';
			title = '添加人员';
			height = 160;
		}
		$("#adddlg").dialog({
			href : url,
			height : height,
			width : width,
			title : title,
			modal : true,
			closed : false
		});
		$('#adddlg').dialog('open');
		$.parser.parse('#adddlg');
	});
	var addCount = 0;
	// 新增的确定事件
	$('.addGroup_btn').die().live('click',function(){
		if (addCount == 0) {
			addCount++;
			$('#addForm').ajaxForm({
				url : contextPath + '/teamgroup/add',
				beforeSubmit : function() {
					if (!$('#addForm').form('validate')) {
						addCount = 0;
						return false;
					} else {
						return true;
					}
				},
				success : function(data) {
					if (data.msg == 1) {
						$.messager.alert("提示", "操作成功！", "success", function() {
							// 刷新
							$('#managerdlg').dialog('refresh',contextPath + '/teamgroup/teammanager?type='+data.type);
							$('#adddlg').dialog('close');
						});
					}else{
						$.messager.alert("提示", "操作失败！", "error");
					}
					addCount = 0;
				}
			});
			$('#addForm').submit();
		}
		
	});
	// 点击编辑
	$('.editTeam').die().live('click',function(){
		var _chk = _clicktype.find("input[name='chk']:checked");
		var len = _chk.length;
		if(len > 0){
			var content = '<form  method="post" id="updateForm"><ul class="form">';
			for(var i=0;i<len;i++){
				var groupname =  $(_chk[i]).attr('groupname');
				var groupid = $(_chk[i]).val();
				if($('.con-tk-left-now').attr('val') == 'personnels'){
					var phone =  $(_chk[i]).attr('phone');
					content +='<li><span style="text-align:left;"><samp style="color:red">*</samp>'+$('.con-tk-left-now').html()+'名称：</span><p>' 
					+ '<input name="groupname" type="text"  groupid="'+groupid+'" value="' + groupname + '" class="flpi easyui-validatebox" '
					+ ' data-options="required:true,validType:[\'ench\',\'lengthb[4,20]\']"/>'
				    + '</p></li>';
					content +='<li><span style="text-align:left;"><samp style="color:red">*</samp>电话：</span><p>' 
						+ '<input name="phone" type="text" id="phone" value="'+phone+'" class="easyui-validatebox flpi" data-options="required:true,validType:[\'phone\']"/>'
					    + '</p></li>';
				}else{
					content +='<li><span style="text-align:left;"><samp style="color:red">*</samp>'+$('.con-tk-left-now').html()+'名称：</span><p>' 
					+ '<input name="groupname" type="text"  groupid="'+groupid+'" value="' + groupname + '" class="flpi easyui-validatebox" '
					+ ' data-options="required:true,validType:[\'ench\',\'lengthb[4,20]\',\'remote[\\\''+contextPath+'/teamgroup/isExsit\\\',\\\'groupname\\\',\\\''+groupname+'\\\',\\\''+$('.con-tk-left-now').html()+'名称\\\']\']"/>'
				    + '</p></li>';
				}
				
			}
			content += '</ul></from>';
			var width = 435;
			var height = 300;
			if($('.con-tk-left-now').attr('val') == 'personnels'){
				if(len >= 3){
					height = 300;
				}else{
					height = len * 33 * 2 + 100;
				}
			}else{
				if(len >= 6){
					height = 300;
				}else{
					height = len * 33  + 100;
				}
			}
			$("#editdlg").dialog({
				height : height,
				width : width,
				content:content,
				title : "编辑",
				modal : true,
				closed : false
			});
			$('#editdlg').dialog('open');
			$.parser.parse('#editdlg');
		}else{
			$.messager.alert("提示","请选择你要编辑的"+$('.con-tk-left-now').html()+"！","warning");
		}
	});
	var updateCount = 0;
	// 编辑的确定单击事件
	$(".editGroup_btn").die().live('click',function(){
		if (updateCount == 0) {
			updateCount++;
			var val = $('.con-tk-left-now').attr('val');
			if(val == 'bigs'){
				type = 1;
			}else if(val == 'smalls'){
				type = 2;
			}else{
				type = 3;
			}
			if (!$('#updateForm').form('validate')) {
				updateCount = 0;
				return false;
			}
			var groups = new Array();
			var _name = $('#updateForm').find('input[name=groupname]');
			var _phone = $('#updateForm').find('input[name=phone]');
			//判断大组小组是否重复
			if(type == 1 || type == 2){
				for(var i = 0; i < _name.length; i ++){
					var val_i = $( _name[i]).val();
					var count = 0;
					for(var j = 0; j < _name.length; j ++){
						var val_j = $(_name[j]).val();
						if(val_i == val_j){
							count++;
							if(count >= 2){
								$.messager.alert("提示",(type == 1?"大组":"小组") + "名称不能相同！","warning");
								updateCount = 0;
								return;
							}
						}
					}
				}
			}
			//封装数据
			for(var i = 0; i < _name.length; i++){
				var obj = {};
				obj["groupid"] = $(_name[i]).attr('groupid');
				obj["groupname"] =$( _name[i]).val();
				// 判断是否是修改人员
				if( type == 3){
					obj["phone"] = $(_phone[i]).val();
				}
				groups.push(obj);
			}
			$.ajax({
				type:'post',
				url:contextPath + '/teamgroup/update',
				data:{groups : JSON.stringify(groups),type:type},
				success:function(data){
					if(data.msg == 1){
						$.messager.alert('提示','操作成功！','success',function(){
							$('#editdlg').dialog('close');
							$('#managerdlg').dialog('refresh',contextPath + '/teamgroup/teammanager?type='+data.type);
						});
					}else{
						$.messager.alert('提示','操作失败！','error');
					}
					updateCount = 0;
				}
			});
		}
	});
	// 删除
	$('.deleteTeam').die().live('click',function(){
		var str="";
		var val = $('.con-tk-left-now').attr('val');
		var type;
		if(val == 'bigs'){
			type = 1;
		}else if(val == 'smalls'){
			type = 2;
		}else{
			type = 3;
		}
		_clicktype.find("input[name='chk']:checked").each(function(){   
			str+=$(this).val()+",";   
		});   
		if(str != "" && str.length > 1){
			str = str.substring(0, str.length-1);
			deleteTeam(str,type);
		}else{
			$.messager.alert("提示","请选择你要删除的"+$('.con-tk-left-now').html()+"！","warning");
		}
	});
});
function deleteTeam(ids,type){
	$.messager.confirm('提示', '是否删除你选择的'+$('.con-tk-left-now').html()+'？', function(r){
		if (r){
			$.ajax({
				type:'post',
				url:contextPath + '/teamgroup/delete',
				data:({ids : ids,type:type}),
				success:function(data){
					if(data.msg == 1){
						$.messager.alert('提示','删除成功！','success',function(){
							$('#managerdlg').dialog('refresh',contextPath + '/teamgroup/teammanager?type='+data.type);
						});
					}else{
						$.messager.alert('提示','删除失败！','error');
					}
				}
			});
		}
	});
}
