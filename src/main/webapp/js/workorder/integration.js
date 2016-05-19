	//区间值自验证
	function validateself(){
		for(var i=3;i<12;i++){
			var left = $("#left"+i).val();
			var right = $("#right"+i).val();
			if(parseInt(left)<parseInt(right)){
				
			}else{
				if(parseInt(left)==0&&parseInt(right)==0){
					
				}else{
					$.messager.alert("提示","综合阈值中修正规则区间值自身左值小于右值", "success",function(){
						moveScroll("left"+i);
						addWrong("left"+i,"right"+i);
					});
					return false;
				}
			}
		}
		return true;
	}
	//验证区间值左值小于右值
	function validateIntervalIntegration(){
		for(var i=3;i<12;i++){
			var left = $("#left"+i).val();
			var right = $("#right"+(i+1)).val();
			if(parseInt(left)==parseInt(right)+1){
				
			}else{
				if(parseInt(left)==0 && parseInt(right) ==0){
					
				}else{
					$.messager.alert("提示","综合阈值中修正规则自由模式修正的区间值应该上一个左值比下一个右值大1", "success",function(){
						moveScroll("right"+(i+1));
						addWrong("left"+i,"right"+(i+1));
					});
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	*判断评分规则格式是否正确
	*/
	function evaluateCode(){
		if(statusRank()){
			if(diffRank()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * 评分规则格式中同级比较
	 */
	function statusRank(){
		for(var i=1;i<5;i++){
			var a = $("#evaluate_scoreA"+i).val();
			var b = $("#evaluate_scoreB"+i).val();
			var c = $("#evaluate_scoreC"+i).val();
			if(parseInt(a)<parseInt(b)&&parseInt(b)<parseInt(c)){
				
			}else{
				$.messager.alert("提示","综合阈值中评分规则值,应该由低到高", "success",function(){
					if(parseInt(a)>=parseInt(b)){
						moveScroll("evaluate_scoreB"+i);
						addWrong("evaluate_scoreA"+i,"evaluate_scoreB"+i);
					}else if(parseInt(b)>=parseInt(c)){
						moveScroll("evaluate_scoreC"+i);
						addWrong("evaluate_scoreB"+i,"evaluate_scoreC"+i);
					}
				});
				return false;
			}
		}
		return true;
	}
	/**
	*评分规则格式中跨级衔接比较
	*/
	function diffRank(num1,num2){
		for(var i=1;i<4;i++){
			var a = $("#evaluate_scoreC"+i).val();
			var b = $("#evaluate_scoreA"+(i+1)).val();
			if(parseInt(a)<parseInt(b)){
				
			}else{
				$.messager.alert("提示","综合阈值中评分规则值,应该由低到高", "success",function(){
					moveScroll("evaluate_scoreC"+i);
					addWrong("evaluate_scoreA"+(i+1),"evaluate_scoreC"+i);
				});
				return false;
			}
		}
		return true;
	}
	
	/**
	*计分规则判定为由小到大
	*/
	function rankCode(){
		for(var i = 1;i<4;i++){
			var a = $("#rank_code"+i).val();
			var b = $("#rank_code"+(i+1)).val();
			if(parseInt(a)<parseInt(b)){
				
			}else{
				$.messager.alert("提示","综合阈值中计分规则值,应该由低到高", "success",function(){
					moveScroll("rank_code"+i);
					addWrong("rank_code"+i,"rank_code"+(i+1));
				});
				return false;
			}
		}
		return true;
	}