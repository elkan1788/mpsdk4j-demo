$.extend($.fn.validatebox.defaults.rules, { 
	normalText:{
		validator: function(value, param){   
            return value.length >= param[0];   
        },   
        message: 'Please enter at least {0} characters.'  
	},
	idCard: { 
        validator: function(value, param){
			var pg =   value.search(/([a-z][A-Z][0-9])+/);
			if(pg){
				return $.trim(value.length) == param[0] || $.trim(value.length) == param[0];
			} 
        },   
        message: '长度不合法，应为：'+param[0]+"位，或 "+param[0]+"位"
    },
    minLength: {   
        validator: function(value, param){ 
            return $.trim(value.length) >= param[0];   
        },   
        message: '至少输入'+param[0]+'个字符'
    },
	maxLength: {   
        validator: function(value, param){   
            return $.trim(value.length) <= param[0];   
        },   
        message: '最多输入'+param[0]+'个字符'
    } 
}); 