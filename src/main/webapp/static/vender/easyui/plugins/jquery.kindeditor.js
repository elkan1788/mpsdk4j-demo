/**
 * jQuery EasyUI 1.3.6
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
(function ($, K) {
    if (!K)
        throw "KindEditor未定义!";
 
    function create(target) {
        var opts = $.data(target, 'kindeditor').options;
        var editor = K.create(target, opts);
        $.data(target, 'kindeditor').options.editor = editor;
    }
 
    $.fn.kindeditor = function (options, param) {
        if (typeof options == 'string') {
            var method = $.fn.kindeditor.methods[options];
            if (method) {
                return method(this, param);
            }
        }
        options = options || {};
        return this.each(function () {
            var state = $.data(this, 'kindeditor');
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, 'kindeditor', {
                        options : $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options)
                    });
            }
            create(this);
        });
    }
 
    $.fn.kindeditor.parseOptions = function (target) {
        return $.extend({}, $.parser.parseOptions(target, []));
    };
 
    $.fn.kindeditor.methods = {
        editor : function (jq) {
            return $.data(jq[0], 'kindeditor').options.editor;
        }
    };
 
    $.fn.kindeditor.defaults = {
        resizeType : 0,
		urlType:'domain',
        allowPreviewEmoticons : false,
		allowImageUpload : true,
    	uploadJson : '',
		allowFileManager : false,
		fileManagerJson : '', 
        items:[
			"selectall","|","justifyleft","justifycenter","justifyright","justifyfull","insertorderedlist","insertunorderedlist","indent","outdent","subscript","superscript","clearhtml","|",
"fontname","fontsize","|","forecolor","hilitecolor","bold","italic","underline","strikethrough","lineheight","removeformat","|",'link', 'unlink',"|",'code','image',"table","hr"],
        afterChange:function(){
            this.sync();//这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
        }
    };
    $.parser.plugins.push("kindeditor");
})(jQuery, KindEditor);
