/**
 * jQuery EasyUI 1.3.6
 * 
 * Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
  
(function ($) {      
    //如果没有datepicker控件，则添加之。      
    (function () {      
        var hasdatepicker = false;      
        var plugins = $.parser.plugins;      
        for (var i = plugins.length - 1; i > -1; i--) {      
            if (plugins[i] === "datepicker") {      
                hasdatepicker = true;      
                break;      
            }      
        }      
        if (hasdatepicker == false) {      
            $.parser.plugins[$.parser.plugins.length] = "datepicker";      
        }      
    })();      
     
    function init(target) {      
        $(target).addClass("datepicker-text")      
        var wrap = $("<span class=\"datepicker-wrap\"></span>").insertBefore(target);      
        wrap[0].appendChild(target);      
        var arrow = $("<span class=\"datepicker-arrow\"></span>").insertAfter(target);      
        return wrap;      
    };      
     
    /**
     * 绑定事件用以触发原生的datepicker控件    
     * @param  {[type]} target [description]    
     * @return {[type]}        [description]    
     */     
     
    function bindEvents(target) {      
        var data = $.data(target, "datepicker");      
        var opts = data.options;      
        var wrap = $.data(target, "datepicker").datepicker;      
        var input = wrap.find(".datepicker-text");      
        var arrow = wrap.find(".datepicker-arrow");      
     
        input.unbind(".datepicker");      
        arrow.unbind(".datepicker");      
        if (!opts.disabled) {      
            input.bind("click.datepicker", function (e) {      
                //TODO 触发datepicker      
                WdatePicker(opts);      
            });      
            arrow.bind("click.datepicker",function () {      
                //TODO 触发datepicker      
                WdatePicker($.extend({}, opts, {el:opts.id}));      
            }).bind('mouseenter.datepicker',      
                function (e) {      
                    $(this).addClass('datepicker-arrow-hover');      
                }).bind('mouseout.datepicker',      
                function (e) {      
                    $(this).removeClass('datepicker-arrow-hover');      
                }      
            );      
        }      
    };      
     
    /**
     * 销毁组件    
     * @param  {document object} target 承载组件的输入框    
     * @return {[type]}        [description]    
     */     
    function destroy(target) {      
        var input = $.data(target, "datepicker").datepicker.find("input.datepicker-text");      
        input.validatebox("destroy");      
        $.data(target, "datepicker").datepicker.remove();      
        $(target).remove();      
    };      
     
    function validate(target, doit) {      
        var opts = $.data(target, "datepicker").options;      
        var input = $.data(target, "datepicker").datepicker.find("input.datepicker-text");      
        input.validatebox(opts);      
        if (doit) {      
            input.validatebox("validate");      
        }      
    };      
     
    function initValue(target) {      
        var opts = $.data(target, "datepicker").options;      
        var input = $.data(target, "datepicker").datepicker.find("input.datepicker-text");      
        input.val(opts.value);      
    }      
     
    function setDisabled(target, disabled) {      
        var ops = $.data(target, "datepicker").options;      
        var datepicker = $.data(target, "datepicker").datepicker;      
        var arrow = datepicker.find('.datepicker-arrow');      
        if (disabled) {      
            ops.disabled = true;      
            $(target).attr("disabled", true);      
            arrow.unbind('click.datepicker');      
            arrow.unbind('hover.datepicker');      
        } else {      
            ops.disabled = false;      
            $(target).removeAttr("disabled");      
            arrow.unbind('click.datepicker').bind('click.datepicker', function () {      
                WdatePicker(opts);      
            });      
            arrow.unbind('mouseenter.datepicker').unbind('mouseout').bind('mouseenter.datepicker',      
                function (e) {      
                    this.addClass('datepicker-arrow-hover');      
                }).bind('mouseenter.datepicker',      
                function (e) {      
                    this.removeClass('datepicker-arrow-hover');      
                }      
            );      
        }      
    };      
     
    /**
     * 设置输入框宽度，主要这里是指box模型的width    
     * @param {document object} target 承载控件的输入框    
     * @param {number} width  宽度    
     */     
    function setWidth(target, width) {      
        var opts = $.data(target, "datepicker").options;      
        opts.width = width;      
        $(target).width(width);      
    }      
     
    function setValue(target, value) {      
        $(target).val(value);      
    }      
     
    function getValue(target) {      
        return $(target).val();      
    }      
     
    /**
     * 因为datepicker图片触发方式，必要id，所以在没有设置id的情况下，设置一个唯一ID    
     * @param {[type]} target [description]    
     */     
    function setId(target) {      
        var pre = "_easyui_datepicker_id_";      
        var opts = $.data(target, "datepicker").options;      
        opts.id = pre + $.fn.datepicker.defaults.count;      
        $(target).attr("id", opts.id);      
        $.fn.datepicker.defaults.count++;      
    }      
     
    $.fn.datepicker = function (options, param) {      
        if (typeof options == 'string') {      
            return $.fn.datepicker.methods[options](this, param);      
        }      
        options = options || {};      
        return this.each(function () {      
            var state = $.data(this, 'datepicker');      
            var opts;      
            if (state) {      
                opts = $.extend(state.options, options);      
            } else {      
                opts = $.extend({}, $.fn.datepicker.defaults, $.fn.datepicker.parseOptions(this), options);      
                var wrap = init(this);      
                state = $.data(this, 'datepicker', {      
                    options:opts,      
                    datepicker:wrap      
                });      
            }      
            if (opts.id == undefined) {      
                setId(this);      
            }      
            setWidth(this, state.options.width);      
            setDisabled(this, state.options.disabled);      
            bindEvents(this);      
            validate(this);      
            initValue(this);      
        });      
    };      
    $.fn.datepicker.methods = {      
        options:function (jq) {      
            return $.data(jq[0], 'datepicker').options;      
        },      
        destroy:function (jq, param) {      
            return jq.each(function () {      
                destroy(this, param);      
            });      
        },      
        setWidth:function (jq, param) {      
            return jq.each(function () {      
                setWidth(this, param);      
            });      
        },      
        setValue:function (jq, param) {      
            setValue(jq[0], param);      
        },      
        getValue:function (jq, param) {      
            return getValue(jq[0]);      
        }      
    };      
    /**
     * 属性转换器，继承validatebox组件属性    
     * @param  {document object} target 承载datepicker的输入框    
     * @return {object}        属性列表    
     */     
    $.fn.datepicker.parseOptions = function (target) {      
        var t = $(target);      
        return $.extend({}, $.fn.validatebox.parseOptions(target), $.parser.parseOptions(target, ["width", "height", "weekMethod", "lang", "skin", "dateFmt", "realDateFmt", 'realTimeFmt', 'realFullFmt', 'minDate', 'maxDate', 'startDate',      
            {      
                doubleCalendar:"boolean",      
                enableKeyboard:"boolean",      
                enableInputMask:"boolean",      
                isShowWeek:"boolean",      
                highLineWeekDay:"boolean",      
                isShowClear:"boolean",      
                isShowOthers:"boolean",      
                readOnly:"boolean",      
                qsEnabled:"boolean",      
                autoShowQS:"boolean",      
                opposite:"boolean"     
            }, {      
                firstDayOfWeek:"number",      
                errDealMode:"number"     
            }]),      
            {      
                value:(t.val() || undefined),      
                disabled:(t.attr("disabled") ? true : undefined),      
                id:(t.attr("id") || undefined)      
            });      
    };      
    $.fn.datepicker.defaults = {      
        id:null,      
        count:0,      
        value:'',      
        width:'',      
        height:22,      
        disabled:false,     
        doubleCalendar:false,      
        enableKeyboard:true,      
        enableInputMask:true,      
        weekMethod:'ISO8601',      
        position:{},      
        lang:'auto',      
        skin:'default',      
        dateFmt:'yyyy-MM-dd',      
        realDateFmt:'yyyy-MM-dd',      
        realTimeFmt:'HH:mm:ss',      
        realFullFmt:'%Date %Time',      
        minDate:'1900-01-01 00:00:00',      
        maxDate:'2099-12-31 23:59:59',      
        startDate:'',      
        firstDayOfWeek:0,      
        isShowWeek:false,      
        highLineWeekDay:true,      
        isShowClear:true,      
        isShowOthers:true,      
        readOnly:false,      
        errDealMode:0,      
        qsEnabled:true,      
        autoShowQS:false,      
        opposite:false,      
        quickSel:[],      
        disabledDays:null,      
        disabledDates:null,      
        specialDates:null,      
        specialDays:null,      
        onpicking:function () {      
        },      
        onpicked:function () {      
        },      
        onclearing:function () {      
        },      
        oncleared:function () {      
        }      
    };      
})(jQuery);