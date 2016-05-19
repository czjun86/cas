$(function(){
	var num = 0;
	$("#saveAll").click(function(){
		//防止快速点击递交多次
		if(num==0){
			num++;
			if(isChange>0){
			//阈值验证
			if(rateAndIntegrationValidate()){
				isColorChange();
				$("#saveAllForm").ajaxForm({
					url: contextPath + "/rateconfig/saveAll",
					beforeSubmit:function(){ 
			            if(!$('#saveAllForm').form('validate')){
			            	$.messager.alert("提示","数值部分格式不正确，请检查","success");
			            	num = 0;
			            	return false;
			            }
			            return true;
			        },
					success:function(data){
						num = 0;
						if(data.msg==0){
							$.messager.alert("提示","保存成功","success",function(){
								 window.location.href =  window.location.href;
							});
						}else if(data.msg==1){
							$.messager.alert("提示","颜色保存时失败","error");
						}else if(data.msg==2){
							$.messager.alert("提示","指标阈值保存时失败","error");
						}else if(data.msg==3){
							$.messager.alert("提示","综合阈值保存时失败","error");
						}else{
							$.messager.alert("提示","保存失败","error");
						}
					}
				});
				$("#saveAllForm").submit();
			}else{
				num = 0;
			}
			}else{
				$.messager.alert("提示","没有数据变动","success");
				num = 0;
			}
		}
	});
});

/**
 * 颜色是否更新
 */
function isColorChange(){
	var colors = $("#colorUL>li").find("input[name='colors']");
	var colorChangein = $("#colorChangein");
	for(var i=0,len=colors.length; i<len; i++){
		var color = $(colors[i]);
		if(color.attr("oldval") != color.val()){
			colorChangein.val(1);
			break;
		}
	}
}

/**
 * 指标阈值和综合阈值验证
 */
function rateAndIntegrationValidate(){
	if(!$('#saveAllForm').form('validate')){
		return false;
	}
	//指标阈值验证
	if(!validate()){
		return false;
	}
	//综合阈值
	if(!rankCode()){
		return false;
	}else if(!evaluateCode()){
		return false;
	}else if(!validateself()){
		return false;
	}else if(!validateIntervalIntegration()){
		return false;
	}
	return true;
}

/**
*悬浮按钮
*/
/*function scroll(p){
	var d = document,w = window,o = d.getElementById(p.id),ie6 = /msie 6/i.test(navigator.userAgent);
	if(o){
		o.style.cssText +=";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.r?'right':"right")+":50px;"+(p.t!=undefined?'bottom:'+p.t+'px':'top:0');
		if(!p.f||ie6){
			-function(){
        		var t = 500,st = d.documentElement.scrollTop||d.body.scrollTop,c;
                c = st  - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||d.documentElement.clientHeight)-o.offsetHeight);//如果你是html 4.01请改成d.body,这里不处理以减少代码
            	c!=0&&(o.style.top = (o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1))-3 + 'px',t=10);
            	setTimeout(arguments.callee,t)
    		}()	
		}
	}  
}
scroll({
	id:'saveButton'    
});*/

/**
 *错误单元格背景标红，3秒后移除
 */
function addWrong(a,b){
	if(a!=null){
		$("#"+a).addClass("wrongRed");
		setTimeout(function(){
			$("#"+a).removeClass("wrongRed");
			},3000
		);
	}
	if(b!=null){
		$("#"+b).addClass("wrongRed");
		setTimeout(function(){
			$("#"+b).removeClass("wrongRed");
			},3000
		);
	}
}

/**
*根据偏移值移动下拉条
**/
function moveScroll(id){
	//判断leng>0时候含有该元素
	//var t = ($("#rateDom").has($("#"+id))).length;
	var centerDom = document.getElementById(id);
	var oRect = centerDom.getBoundingClientRect();
	//获取可视窗口高度居中
	var height = (document.documentElement.clientHeight)/2;
	//滚动条位置
	var currScrollTop = document.documentElement.clientHight;
	var val;
	//单元格在页面可视上方以外情况
	if(oRect.top<=0){
		val =  -oRect.top+height;
		document.documentElement.scrollTop = document.documentElement.scrollTop-val;
	}
	//单元格在页面可视窗口中上半部的情况
	else if(oRect.top>0&oRect.top<=height){
		val =height-oRect.top;
		document.documentElement.scrollTop = document.documentElement.scrollTop-val;
	}
	//单元格在页面可视窗口中下半部的情况
	else if(oRect.top>0&oRect.top<=height*2){
		val =height-oRect.top;
		document.documentElement.scrollTop = document.documentElement.scrollTop-val;
	}
	//单元格在页面可视窗口下方以外情况
	else if(oRect.top>0&oRect.top>height*2){
		val =oRect.top-height;
		document.documentElement.scrollTop = document.documentElement.scrollTop+val;
	}
}