$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		/**
		 * uuid
		 */
	    uuid:{
	    	validator : function(value){
	    		return /^[0-9]{15}$/i.test(value);
	    	},
	    	message : '只能为15位数字'
	    },
		/**
		 * 电话验证
		 */
		teltphone : {
			//只能为1开头11为手机号码，3位区号-8号码，4位区号-7位号码，8位号码
			validator : function(value){
				return /^(([1]{1}\d{10})|(\d{3}\-{1}\d{8})|(\d{8})|(\d{4}\-{1}\d{7})|(\d{7})){1}$/.test(value);
			},
			message : '请输入：手机号,电话号码 或 区号-电话号码'
		},
		/**
		 * 责任人
		 */
		functionary : {
			//责任人只能为汉字
			validator : function(value){
				return /^[\u4e00-\u9fa5]+$/.test(value);
			},
			message : '请输入汉字'
		}
	});
});