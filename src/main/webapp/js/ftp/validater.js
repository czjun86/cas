$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		ip : {//只能输入数字 
	        validator : function(value) { 
	            return /^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$/i.test(value); 
	        }, 
	        message : 'ip格式，例如172.31.0.43' 
	    },
	    port : {//只能输入数字 
	        validator : function(value) { 
	            return /^[0-9]{1,5}$/i.test(value); 
	        }, 
	        message : '端口号为5位以内数字' 
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
            message: 'ftp下行路径以/开头,加目录名,并且加上文件的后缀'
	    },
	    ftpup:{
	    	validator : function(value) { 
	    		var a = value.substring(0,1);
	    		if(a == "/"){
	    			return true; 
	    		}else{
	    			return false; 
	    		};
            },
            message: 'ftp上行路径以/开头,加目录名,且不能有中文'
	    },
	    ftpsize:{
	    	validator : function(value) { 
	    		return /^[0-9]+(.[0-9]{1,3})?$/i.test(value);; 
            },
            message: '文件大小不能为空'
	    },
	    num : {//只能输入数字 
	        validator : function(value) { 
	            return /^[0-9]{0,10}$/i.test(value); 
	        }, 
	        message : '编号为10以内数字，不输入会给予默认值' 
	    },
	});
});