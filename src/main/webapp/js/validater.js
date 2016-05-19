$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		/**
		 * 综合阈值
		 */
		integration : {//只能输入数字 
	        validator : function(value) { 
	            return /^(([1-9]\d{0,1})|(\d{1})){1}$/i.test(value); 
	        }, 
	        message : '请输入1-2位整数' 
	    },
	    absolutely : {//只能输入数字 
	        validator : function(value) { 
	            return /^([1][0][0]){1}$/i.test(value); 
	        }, 
	        message : '最大值只能为100' 
	    },
	/**
	 * 评级阈值验证开始
	 */
		rxLevFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(\-){1}[1-9]\d{1,2}$/i.test(value); 
	        }, 
	        message : 'RxLev参考取值范围为-47 ~ -110' 
	    },
	    rxQualFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^[0-7]$/i.test(value); 
	        }, 
	        message : 'RxQual参考取值范围为0 ~ 7' 
	    },
	    ciFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(\-)?\d{1,3}$/i.test(value)&&(parseFloat(value)>=-30&&parseFloat(value)<=30); 
	        }, 
	        message : 'C/I参考取值范围为-30 ~ 30' 
	    },
	    rscpFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(((\-){1}[1-9](\d{1,2})?)|[0]){1}$/.test(value); 
	        }, 
	        message : 'Rscp参考取值范围为-115 ~ 0' 
	    },
	    ecnoFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(((\-){1}\d{1,3})|[0]){1}$/i.test(value); 
	        }, 
	        message : 'Ec/No参考取值范围为-30 ~ 0' 
	    },
	    txpowerFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(\-)?\d{1,3}$/i.test(value); 
	        }, 
	        message : 'TxPower参考取值范围为-50 ~ 50' 
	    },
	    rsrpFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(((\-){1}[1-9](\d{1,2})?)|[0]){1}$/.test(value) && parseFloat(value)>=-130 && parseFloat(value)<=0; 
	        }, 
	        message : 'RSRP参考取值范围为-130 ~ 0dBm' 
	    },
	    rsrqFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(((\-){1}[1-9](\d{1,2})?)|[0]){1}$/.test(value) && parseFloat(value)>=-30 && parseFloat(value)<=0; 
	        }, 
	        message : 'RSRQ参考取值范围为0 ~ -30db' 
	    },
	    sinrFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^((((\-){1})?[1-9](\d{1,2})?)|[0]){1}$/.test(value) && parseFloat(value)>=-30 && parseFloat(value)<=30; 
	        }, 
	        message : 'SINR参考取值范围为30 ~ -30db' 
	    },
	    ftpupFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,4}$/i.test(value); 
	        }, 
	        message : 'FTP上传参考取值范围为0 ~ 5830Kbps' 
	    },
	    ftpdownFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,4}$/i.test(value); 
	        }, 
	        message : 'FTP下载参考取值范围为0 ~ 7372Kbps' 
	    },
	    ftpupLteFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,8}$/i.test(value); 
	        }, 
	        message : 'FTP上传参考取值范围为0 ~ 1500000Kbps' 
	    },
	    ftpdownLteFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,8}$/i.test(value); 
	        }, 
	        message : 'FTP下载参考取值范围为0 ~ 1500000Kbps' 
	    },
	    aotherFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^(\-){1}\d{1,3}$/i.test(value); 
	        }, 
	        message : '只能输入3位及以下的负数 ' 
	    },
		onlyFigure : {//只能输入3位及以下的正负数 
	        validator : function(value) { 
	            return /^(\-)?\d{1,3}$/i.test(value); 
	        }, 
	        message : '只能输入3位及以下的正、负数 ' 
	    },
	    speedFigure : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,4}$/i.test(value); 
	        }, 
	        message : '只能为4位及以下正整数' 
	    },
	    maxValue : {// 百分比中-100]
	        validator : function(value) { 
	            return /^(\[|\(){1}((([1-9]([0-9])?(\.[0-9]([0-9])?)?)|([0]\.[1-9]([0-9])?)|([0]\.[0][1-9]))){1}[,][1][0][0](\]){1}$/i.test(value); 
	        }, 
	        message : '阈值的最小值应该以,100]结尾' 
	    },
	    normalValue : {// 百分比中[1-99]
	        validator : function(value) { 
	            return /^(\[|\(){1}([1-9])?\d(\.\d(\d)?)?[,]((([1-9])?\d(\.\d(\d)?)?)|([1][0][0])){1}(\]|\)){1}$/i.test(value); 
	        }, 
	        message : '阈值的占比范围应该为0~100的开闭区间,最多包含两位小数' 
	    },
	    minValue : {// 百分比中[0-
	        validator : function(value) { 
	            return /^\[[0][,]((([1-9]([0-9])?(\.[0-9]([0-9])?)?)|([0]\.[1-9]([0-9])?)|([0]\.[0][1-9]))){1}(\]|\)){1}$/i.test(value); 
	        }, 
	        message : '阈值的最小值应该以(0,开始' 
	    },
	    ftpMax : {// 平均速度最大值可到+∞)
	        validator : function(value) { 
	            return /^(\[|\(){1}(([1-9]\d{0,8})|[0]){1}(([,](\+){1}[∞](\)){1})|([,][1-9]\d{0,8}(\)|\]))|(\))){1}$/i.test(value); 
	        }, 
	        message : '平均速度格式不正确,范围为[0,+∞),如果不填右边参数,默认为正无穷'
	    },
	    ftpNormal : {// 平均速度一般值
	        validator : function(value) { 
	            return /^(\[|\(){1}\d{1,8}[,]\d{1,8}(\]|\)){1}$/i.test(value); 
	        }, 
	        message : '平均速度格式不正确,应该以4位数字组成横纵坐标逗号隔开,中括号或小括号包含' 
	    },
	    ftpMin : {// 平均速度最小值可以到零
	        validator : function(value) { 
	            return /^(\[){1}(0)[,][1-9]\d{0,8}(\]|\)){1}$/i.test(value); 
	        }, 
	        message : '平均速度格式不正确,最小值应该以(0,开始'
	    },
		
		
	/**
	 * 评级阈值验证结束
	 */
	 idcard : {// 验证身份证 
	        validator : function(value) { 
	            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
	        }, 
	        message : '身份证号码格式不正确' 
	    },
	    alphanumeric :{
	    	validator : function(value){
	    		return /^[A-Za-z0-9.]+$/i.test(value);
	    	},
	    	message : '只能为字母,数字和点'
	    },
	    float:{
	    	validator : function(value){
	    		return /^(-?\d+)(\.\d{1,2})?$/i.test(value);
	    	},
	    	message : '请输入浮点数(两位有效数字)'		
	    },
	    uuid:{
	    	validator : function(value){
	    		return /^[0-9]{15}$/i.test(value);
	    	},
	    	message : '只能为15位数字'
	    },
	    equalsArray:{
	    	 validator: function(value,param){
	    		 var count = 0;
	    		 $('input['+param[0]+'="'+param[1]+'"]').each(function(){
	    			 var _me = $(this).val();
	    			 if(_me != null && _me != "" && typeof(_me) != "undefined"){
	    				 if(_me == value){
	    					 count ++;
	    				 }
	    			 }
	    		 });	 
    			 if(count > 1){
    				 return false;
    			 }else{
    				 return true;
    			 }
	         },  
	         message: '请不要输入相同的{2}！'  
	    },
	    minLength: {
	        validator: function(value, param){
	            return value.length >= param[0];
	        },
	        message: '请输入至少{0}个字符.'
	    },
	    length:{validator:function(value,param){ 
	        var len=$.trim(value).length; 
	            return len>=param[0]&&len<=param[1]; 
	        }, 
	            message:"输入内容长度必须介于{0}和{1}之间." 
	        }, 
	    phone : {// 验证电话号码 
	        validator : function(value) { 
	            return /^1[3-9]{1}[0-9]{9}$/i.test(value); 
	        }, 
	        message : '请输入正确的手机号码(如:13888888888)' 
	    }, 
	    mobile : {// 验证手机号码 
	        validator : function(value) { 
	            return /^(13|15|18)\d{9}$/i.test(value); 
	        }, 
	        message : '手机号码格式不正确' 
	    }, 
	    intOrFloat : {// 验证整数或小数 
	        validator : function(value) { 
	            return /^\d+(\.\d+)?$/i.test(value); 
	        }, 
	        message : '请输入数字，并确保格式正确' 
	    }, 
	    currency : {// 验证货币 
	        validator : function(value) { 
	            return /^\d+(\.\d+)?$/i.test(value); 
	        }, 
	        message : '货币格式不正确' 
	    }, 
	    qq : {// 验证QQ,从10000开始 
	        validator : function(value) { 
	            return /^[1-9]\d{4,9}$/i.test(value); 
	        }, 
	        message : 'QQ号码格式不正确' 
	    }, 
	    integer : {// 验证整数 
	        validator : function(value) { 
	            return /^[+]?[1-9]+\d*$/i.test(value); 
	        }, 
	        message : '请输入整数' 
	    }, 
	    age : {// 验证年龄
	        validator : function(value) { 
	            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value); 
	        }, 
	        message : '年龄必须是0到120之间的整数' 
	    }, 
	    
	    chinese : {// 验证中文 
	        validator : function(value) { 
	            return /^[\Α-\￥]+$/i.test(value); 
	        }, 
	        message : '请输入中文' 
	    }, 
	    ench: {   
	        validator: function(value,param){   
	        	var c = /^[\u4E00-\u9FA5\uF900-\uFA2Da-zA-Z]{1,}$/;
	        	d = new RegExp(c).test(value);
	            return d;   
	       },   
	        message: '只能输入中文或字母'  
	   },
	    english : {// 验证英语 
	        validator : function(value) { 
	            return /^[A-Za-z]+$/i.test(value); 
	        }, 
	        message : '请输入英文' 
	    }, 
	    unnormal : {// 验证是否包含空格和非法字符 
	        validator : function(value) { 
	            return /.+/i.test(value); 
	        }, 
	        message : '输入值不能为空和包含其他非法字符' 
	    }, 
	    username : {// 验证用户名 
	        validator : function(value) { 
	            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
	        }, 
	        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）' 
	    }, 
	    faxno : {// 验证传真 
	        validator : function(value) { 
	//            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
	            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
	        }, 
	        message : '传真号码不正确' 
	    }, 
	    zip : {// 验证邮政编码 
	        validator : function(value) { 
	            return /^[1-9]\d{5}$/i.test(value); 
	        }, 
	        message : '邮政编码格式不正确' 
	    }, 
	    ip : {// 验证IP地址 
	        validator : function(value) { 
	            return /\d+\.\d+\.\d+\.\d+/i.test(value); 
	        }, 
	        message : 'IP地址格式不正确' 
	    }, 
	    port : {// 验证端口
	        validator : function(value) { 
	            return /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])?$/i.test(value); 
	        }, 
	        message : '端口格式不正确' 
	    }, 
	    name : {// 验证姓名，可以是中文或英文 
	            validator : function(value) { 
	                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
	            }, 
	            message : '请输入姓名' 
	    },
	    date : {// 验证姓名，可以是中文或英文 
	        validator : function(value) { 
	        //格式yyyy-MM-dd或yyyy-M-d
	            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value); 
	        },
	        message : '清输入合适的日期格式'
	    },
	    msn:{ 
	        validator : function(value){ 
	        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
	    }, 
	    message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)' 
	    },
	    same:{ 
	        validator : function(value, param){ 
	            if($("#"+param[0]).val() != "" && value != ""){ 
	                return $("#"+param[0]).val() == value; 
	            }else{ 
	                return true; 
	            } 
	        }, 
	        message : '两次输入的密码不一致！'    
	    },
	    vname:{// 验证姓名，只能为中文,字母,中划线,下划线
            validator : function(value) { 
                return /^[\u4E00-\u9FA5\uF900-\uFA2Da-zA-Z\-\_]{1,}$/.test(value); 
            }, 
            message : '只能为中文,字母,中划线,下划线' 
	    },
	    maxLength:{
	        validator: function(value, param){
	            return value.length < param[0];
	        },
	        message: '最多只能输入{0}个字符.'
	    },
	    telPhone:{//验证座机或者手机号码
	    	validator : function(value) { 
                return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?$/.test(value) || /^1[3-9]{1}[0-9]{9}$/.test(value); 
            },
            message: '请输入电话号码(座机或手机)'
	    },
	    password:{
	    	validator : function(value) { 
                return /^[0-9a-zA-Z\_\.\-\!\@\#\$\%|^\&\*]{1,}$/.test(value); 
            },
            message: '只能为数字，字母，下划线，中划线和常见的特殊符号'
	    },
	    ftpPath:{
	    	validator : function(value) { 
	    		var a = value.substring(0,1);
	    		var size = value.split("\.");
	    		if(a == "/" & size.length>1){
	    			return true; 
	    		}else{
	    			return false; 
	    		}
            },
            message: 'ftp路径以/开头,加目录名,并且加上文件的后缀'
	    },
	    ftpup:{
	    	validator : function(value) { 
	    		var a = value.substring(0,1);
	    		if(a == "/"){
	    			return true; 
	    		}else{
	    			return false; 
	    		}
            },
            message: 'ftp路径以/开头,加目录名'
	    },
	    selectCheck: {   
	        validator: function(value,param){
	        	if($("#"+param[1]).val()==0){
	        		$("#"+param[1]).val("1");
	        		return true;
	        	}else{
	        		return $("#"+param[0]).combo("getValue")!=-1;
	        	}
	       },   
	        message: '该选项是必选项'  
	   },
	   lengthb:{
		   validator:function(value,param){
			   var len=0;
			   for(var i = 0; i < value.length; i ++){
				   if((value.charCodeAt(i)>=0) && (value.charCodeAt(i)<=255)){
					   len+=1;
				   }else{
					   len+=2;
				   }
		   	   }
			   return len>=param[0]&&len<=param[1]; 
		   },
		   message : '输入内容长度必须介于{0}和{1}字节之间.'
	   },
	   assessScore : {//只能输入数字 
	        validator : function(value) { 
	        	var str = value.split(".");
	        	if(str.length==1){
	        		if(!/^(\-)?\d{1,3}$/i.test(value)){
	        			$.fn.validatebox.defaults.rules.assessScore.message="请输入3位以内数字";
	        			return false;
	        		}else{
	        			return true;
	        		}
	        	}else if(str.length==2){
	        		var str1 = str[0];
	        		var str2 = str[1];
	        		if(!/^(\-)?\d{1,3}$/i.test(str1)){
	        			$.fn.validatebox.defaults.rules.assessScore.message="整数位数请输入3位以内数字";
	        			return false;
	        		}else if(!/^\d{1,2}$/i.test(str2)){
	        			$.fn.validatebox.defaults.rules.assessScore.message="小数位数请输入2位以内数字";
	        			return false;
	        		}
	        		return true;
	        	}else{
	        		$.fn.validatebox.defaults.rules.assessScore.message="请输入数字";
        			return false;
	        	}
	        }, 
	        message : ''
	    },
	   centerStep : {//只能输入数字 
	        validator : function(value) { 
	            return /^\d{1,3}(([.]\d{1,2}){1})?$/i.test(value); 
	        }, 
	        message : '请输入三位以内的数字,若有小数请不要超过小数点后两位!' 
	    }
	});
});