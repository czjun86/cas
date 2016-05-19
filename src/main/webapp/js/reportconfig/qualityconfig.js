$(function(){
	window.onresize();
	var oknum = 0;
	$('#ok').click(function(){
		if(oknum == 0){
			oknum ++;
			var _input = $('input');
			var flag = false;
			var count = 0;
			for(var i = 0;i<_input.length;i++){
				var _val = $(_input[i]).val();
				if(!/^(-?\d+)(\.\d{1,2})?$/i.test(_val) || parseFloat(_val) > 100){
					$(_input[i]).css('background-color','red');
					if(count == 0){
						flag = true;
						var _t = $(_input[i]).offset().left;
						var _div = $('#right').offset().left;
						var _left = _t - _div;
						if(_left > $('#right').width() - 90){
							$('#right').scrollLeft(_t - _div); 
						}
						//$(_input[i]).focus();
						count++;
						oknum = 0;
					}
					
				}
			}

			var tscroe = $("input[name='tscroe']").val();
			var stepc = $("input[name='stepc']").val();
				if(!flag){
					if(tscroe>=stepc){
					var vals = new Array();
					//基本数据
					$('.khbb-cs-other').find("input").each(function(){
						var obj = {};
						obj['id'] = $(this).attr('operid');
						obj['val'] = $(this).val();
						obj['type'] = 2;
						vals.push(obj);
					});
					//均值比较分步长
					$("#svg_step").find("ul > li > input").each(function(){
						var _this = $(this);
						var obj = {};
						var _name = _this.attr("name");
						obj['id'] = _this.attr('operid');
						obj['svg_step'] = _this.val();
						//根据
						obj['annular_step'] = $("#annular_step").find("input[name='"+_name+"']").val();
						obj['groupid'] = _name.split('_')[2];
						obj['kpi'] = _name.split('_')[1];
						obj['type'] = 1;
						vals.push(obj);
					});
					$.ajax({
						type:'post',
						url:contextPath + '/reportconfig/updatestep',
						data:{groups : JSON.stringify(vals)},
						success:function(data){
							if(data.msg == 1){
								$.messager.alert('提示','操作成功！','success');
							}else{
								$.messager.alert('提示','操作失败！','error');
							}
							oknum = 0;
						}
					});
				}else{
					$.messager.alert('提示','考核步长应该小于等于考核总分',"warning");
					oknum = 0;
				}
			}
		}
	});
	$('input').bind('focus blur',function(e) {
		if(e.type == 'focus'){
			$(this).css('background-color','');
		}else{
			var _val = $(this).val();
			if(!/^(-?\d+)(\.\d{1,2})?$/i.test(_val) || parseFloat(_val) > 100){
				$(this).css('background-color','red');
			}
		}
	});
});
window.onresize = function() {
	var scWidth = document.body.scrollWidth;
	var rightWidth = scWidth * 0.98 * 0.98 - 2 - 170 - 10 - 2;
	var _this = $("#right");
	var _right = _this.attr("val") *90 * 2 + 5 - 1;
	if(rightWidth > _right){
		_this.css("width", _right);
	}else{
		_this.css("width", rightWidth);
	}
//	list?size*90*2 + list?size * 2 - 1
	
}