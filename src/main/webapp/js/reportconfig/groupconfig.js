$(function() {
	var optInit = {callback: 
	    	function(page_index,jq){
	        $.ajax({
	        	type:"post",
	    		url: contextPath + "/reportconfig/grouplist/template",
	    		data:({pageIndex: page_index+1}),
	    		success:function(data){
	    			$('#content').html(data);
	    		}
	        });
	        return false;
	    }
	};
	$("#pager").pagination(pageTotal, optInit);
	
	$("#edit").click(function() {
		$("#managerdlg").dialog({
			href : contextPath + "/reportconfig/groupmanager?type=0",
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
	// 分公司归属点击事件
	$('.btn_save').die().click(function(){
		if(saveCount == 0){
			saveCount ++;
			var _id = $('.con-tk-left-now').attr('id');
			if(_id == 'groupgs'){
				var groups = new Array();
				$('.areas').each(function(){
					var obj = {};
					obj['groupid'] = $(this).attr('val');
					obj['areas'] = [];
					$(this).find('li').each(function(){
						obj['areas'].push($(this).attr('val'));
					});
					groups.push(obj);
				});
				$.ajax({
					type:'post',
					url:contextPath + '/reportconfig/guishugroup',
					data:({groups : JSON.stringify(groups)}),
					dataType:"json",
					success:function(data){
						if(data.msg == 1){
							$.messager.alert('提示','操作成功','success',function(){
								$('#managerdlg').dialog('refresh',contextPath + '/reportconfig/groupmanager?type=0');
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
	// 名称点击事件
	$('#grouplb').die().live('click', function() {
		$('#groupgs').removeClass('con-tk-left-now');
		$(this).addClass('con-tk-left-now');
		$('.gs').hide();
		$('.lb').show();
	});
	$('#groupgs').die().live('click', function() {
		$('#grouplb').removeClass('con-tk-left-now');
		$(this).addClass('con-tk-left-now');
		$('.lb').hide();
		$('.gs').show();
	});
	// 类别点击事件
	$('.configure-family').find('li').die().live('click', function() {
		if (!$(this).hasClass('now')) {
			$("#right").find('img').attr('src', '../images/configure-01b.png');
			$("#right").attr('val', 0);
			$("#left").find('img').attr('src', '../images/configureb.png');
			$("#left").attr('val', 0);
			$('.areas').find('li').removeClass('areanow');
			$('.unareas').find('li').removeClass('areanow');
			$('.configure-family').find('li').removeClass('now');
			$(this).addClass('now');
			// 隐藏已归属的区域
			$('.areas').hide();
//			// 隐藏未归属区域
//			$('.unareas').hide();
			var groupid = $(this).attr('val');
			// 显示当前已归属的区域
			$('#areas_' + groupid).show();
//			// 显示当前未归属区域
//			$('#unareas_' + groupid).show();
		}
	});

	var rightFn = null;
	var leftFn = null;
	// 已归属区域点击事件
	$('.areas').find('li').die().live('click', function() {
		// 取消上次延时未执行的方法
		clearTimeout(rightFn);
		var _this = $(this);
		rightFn = setTimeout(function() {
			$("#right").find('img').attr('src', '../images/configure-01.png');
			$("#right").attr('val', 1);
			$('.areas').find('li').removeClass('areanow');
			_this.addClass('areanow');
		}, 300);

	});

	// 已归属区域双击击事件
	$('.areas').find('li').live('dblclick', function() {
		// 取消上次延时未执行的方法
		clearTimeout(rightFn);
//		var groupid = $(this).parent().attr('val');
		$('.areas').find('li').removeClass('areanow');
		$('.unareas').append($(this));
	});
	// 未归属区域点击事件
	$('.unareas').find('li').live('click', function() {
		// 取消上次延时未执行的方法
		clearTimeout(leftFn);
		var _this = $(this);
		leftFn = setTimeout(function() {
			$("#left").find('img').attr('src', '../images/configure.png');
			$("#left").attr('val', 1);
			$('.unareas').find('li').removeClass('areanow');
			_this.addClass('areanow');
		}, 300);

	});
	// 未归属区域双击击事件
	$('.unareas').find('li').live('dblclick', function() {
		// 取消上次延时未执行的方法
		clearTimeout(leftFn);
		var groupid = $(".now").attr('val');
		$('.unareas').find('li').removeClass('areanow');
		$('#areas_' + groupid).append($(this));
	});

	// 点击右移
	$('#right').die().live('click', function() {
		if ($(this).attr('val') == '1') {
			$(this).find('img').attr('src', '../images/configure-01b.png');
			// 得到当前选中区域
			var _areanow = $('.areas').find('.areanow');
			_areanow.removeClass('areanow');
			$('.unareas').append(_areanow);
			// _areanow.remove();
			$(this).attr('val', 0);
		}
	});
	// 点击左移
	$('#left').die().live('click', function() {
		if ($(this).attr('val') == '1') {
			$(this).find('img').attr('src', '../images/configureb.png');
			// 得到当前选中区域
			var _unareanow = $('.unareas').find('.areanow');
			_unareanow.removeClass('areanow');
			var groupid = $(".now").attr('val');
			$('#areas_' + groupid).append(_unareanow);
			$(this).attr('val', 0);
		}
	});
	// 新增分公司
	$("#addGroup").die().live('click', function() {
		$("#adddlg").dialog({
			href : contextPath + "/reportconfig/addgroup",
			height : 130,
			width : 435,
			title : "添加分公司",
			modal : true,
			closed : false
		});
		$('#adddlg').dialog('open');
		$.parser.parse('#adddlg');

	});
	var addCount = 0;
	//添加分公司
	$('.addGroup_btn').click(function() {
		if (addCount == 0) {
			addCount++;
			$('#addForm').ajaxForm({
				url : contextPath + '/reportconfig/addgroup',
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
							$('#managerdlg').dialog('refresh',contextPath + '/reportconfig/groupmanager?type=1');
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
	//编辑分公司
	$('#editGroup').die().live('click',function(){
		var _chk = $("input[name='chk']:checked");
		var len = _chk.length;
		if(len > 0){
			var content = '<form  method="post" id="updateForm"><ul class="form">';
			for(var i=0;i<len;i++){
				var groupname =  $(_chk[i]).attr('groupname');
				var groupid = $(_chk[i]).val();
				content +='<li><span style="text-align:left;"><samp style="color:red">*</samp>分公司名称：</span><p>' 
						+ '<input name="groupname" type="text"  groupid="'+groupid+'" value="' + groupname + '" class="flpi easyui-validatebox" '
						+ ' data-options="required:true,validType:[\'ench\',\'lengthb[4,20]\',\'remote[\\\''+contextPath+'/reportconfig/isExsit\\\',\\\'groupname\\\',\\\''+groupname+'\\\',\\\'分公司名称\\\']\']"/>'
					    + '</p></li>';
			}
			content += '</ul></from>';
			
			if(len >= 6){
				height = 300;
			}else{
				height = len * 33  + 100;
			}
			$("#editdlg").dialog({
				height : height,
				width : 435,
				content:content,
				title : "编辑",
				modal : true,
				closed : false
			});
			$('#editdlg').dialog('open');
			$.parser.parse('#editdlg');
		}else{
			$.messager.alert("提示","请选择你要编辑的分公司！","warning");
		}
	});
	var updateCount = 0;
	$(".editGroup_btn").die().live('click',function(){
		if (updateCount == 0) {
			updateCount++;
			if (!$('#updateForm').form('validate')) {
				updateCount = 0;
				return false;
			}
			var _groups = $('#updateForm').find('input[name=groupname]');
			var _len = _groups.length;
			for(var i = 0; i< _len; i++){
				var val_i = $(_groups[i]).val();
				var count = 0;
				for(var j = 0; j< _len; j++){
					var val_j = $(_groups[j]).val();
					if(val_i == val_j){
						count ++;
						if(count >= 2){
							$.messager.alert("提示","分公司名称不能相同！","warning");
							updateCount = 0;
							return;
						}
					}
				}
			}
			var groups = new Array();
			$('#updateForm').find('input[name=groupname]').each(function(){
				var obj = {};
				obj["groupid"] = $(this).attr('groupid');
				obj["groupname"] = $(this).val();
				groups.push(obj);
			});
			$.ajax({
				type:'post',
				url:contextPath + '/reportconfig/updategroup',
				data:({groups : JSON.stringify(groups)}),
				success:function(data){
					if(data.msg == 1){
						$.messager.alert('提示','操作成功！','success',function(){
							$('#editdlg').dialog('close');
							$('#managerdlg').dialog('refresh',contextPath + '/reportconfig/groupmanager?type=1');
						});
					}else{
						$.messager.alert('提示','操作失败！','error');
					}
					updateCount = 0;
				}
			});
		}
	});
	//删除分公司
	$('#deleteGroup').die().live('click',function(){
		var str="";   
		$("input[name='chk']:checked").each(function(){   
			str+=$(this).val()+",";   
		});   
		if(str != "" && str.length > 1){
			str = str.substring(0, str.length-1);
			deleteGroup(str);
		}else{
			$.messager.alert("提示","请选择你要删除的分公司！","warning");
		}
	});
});
function deleteGroup(ids){
	$.messager.confirm('提示', '是否删除你选择的分公司？', function(r){
		if (r){
			$.ajax({
				type:'post',
				url:contextPath + '/reportconfig/deletegroup',
				data:({ids : ids}),
				success:function(data){
					if(data.msg == 1){
						$.messager.alert('提示','删除成功！','success',function(){
							$('#managerdlg').dialog('refresh',contextPath + '/reportconfig/groupmanager?type=1');
						});
					}else{
						$.messager.alert('提示','删除失败！','error');
					}
				}
			});
		}
	});
}
