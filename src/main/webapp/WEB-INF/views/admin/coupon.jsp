<%--
  优惠券信息
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/16/2015
  Time: 8:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="couponCon" class="con" style="margin: 0px;height:100%">
    <table id="coupondg"></table>
</div>
<div id="couponDialog">
<div class="con-tb">
    <form id="couponFrm" method="post">
    <input id="conId" name="conId" type="hidden"/>
    <table class="infobox" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="2" align="center">
                <a href="#" id="coupTipBtn">
                    请注意优惠券有且仅能有一个是有效状态
                </a>
            </td>
        </tr>
        <tr>
            <td width="20%">名称：</td>
            <td>
                <input type="text" name="conName" id="conName" class="easyui-validatebox infobox_input" />
            </td>
        </tr>
        <tr>
            <td width="20%">描述：</td>
            <td>
                <textarea name="conDesc" id="conDesc" rows="10" cols="50"></textarea>
            </td>
        </tr>
        <tr>
            <td width="20%">链接：</td>
            <td>
                <input type="text" name="conLink" id="conLink" class="easyui-validatebox infobox_input" />
            </td>
        </tr>
        <tr>
            <td width="20%">预览图片：</td>
            <td>
                <p>
                    <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-photos',selected:true">
                        图像的大小为360 * 200
                    </a>
                </p>
                <input type="button" id="uploadThumbImg" value="上传图片"/>
                <input type="hidden" id="conThumb" name="conThumb"  value="${cp.conThumb}"/>
                <c:if test="${not empty cp.conThumb}">
                    <p align="left">
                        <img id="thumbImgPreview" src="${cp.conThumb}" width="360" height="200" />
                    </p>
                </c:if>
            </td>
        </tr>
        <tr>
            <td width="20%">数量：</td>
            <td>
                <input type="text" name="conNum" id="conNum" class="easyui-validatebox infobox_input"/>
            </td>
        </tr>
        <tr>
            <td width="20%">有效时间：</td>
            <td>
                <input type="text" name="validTime" id="validTime" class="easyui-datebox"/>
            </td>
        </tr>
        <tr>
            <td width="20%">生效与否：</td>
            <td>
                <input type="radio" name="valid" id="valid_yes" value="1"><label for="valid_yes"> 是 </label>
                <input type="radio" name="valid" id="valid_no" value="0" checked="true"><label for="valid_no"> 否 </label>
            </td>
        </tr>
    </table>
    </form>
</div>
</div>
<script>
    var couponUpBtn;

    $.getScript('${ctx}/static/vender/kindeditor/kindeditor-min.js', function () {

        KindEditor.basePath = '${ctx}/static/vender/kindeditor/';
        if (typeof(couponUpBtn) == 'undefined'
                || null == couponUpBtn) {
            couponUpBtn = KindEditor.uploadbutton({
                button: $('#uploadThumbImg')[0],
                fieldName: 'imgFile',
                url: '${ctx}/nutz/ke4plugin/upload.do?dir=image',
                afterUpload: function (data) {
                    if (data.error == 0) {
                        $('#conThumb').val(data.url);
                        var imgpre = $('#thumbImgPreview');
                        if (imgpre.length < 1) {
                            var pimg = $('<p align="left"><img id="thumbImgPreview"/></p>');
                            $(pimg).find('img').attr('src', data.url).css({'width': '360', 'height': '200', 'margin': '5'});
                            $(pimg).appendTo($('#conThumb').parent());
                        } else {
                            imgpre.attr('src', url);
                        }
                    } else {
                        showMsg('操作失败', data.message);
                    }
                },
                afterError: function (str) {
                    showMsg('操作失败', '自定义错误信息: ' + str);
                }
            });

            couponUpBtn.fileBox.change(function (e) {
                couponUpBtn.submit();
            });
        }
    });
    var coupondg, cdg;
    var edit = 0;
    $(function () {

        $('#coupTipBtn').linkbutton({
            iconCls: 'icon-warning',
            selected: true,
            plain: true
        });

        $('#couponFrm input:not(:button)').each(function(r,i){
            if(!(this.id=='')) {
                $(this).validatebox({required:true});
            }
        });

        $('#conDesc').validatebox({required:true});
        $('#validTime').datebox({required:true});

        $('#couponFrm').form('disableValidation');

        coupondg = $('#coupondg').datagrid({
            url: '${ctx}/admin/coupon/list.do',
            border: false,
            fit: true,
            fitColumns: true,
            rownumbers: true,
            nowrap: true,
            striped: true,
            animate:true,
            singleSelect : true,
            showFooter: true,
            pagination: true,
            idField: 'conId',
            columns:[[{
                field: 'conThumb',
                title: '缩略图',
                width: $(this).width() * 0.2,
                align: 'center',
                formatter: function(value,row,index) {
                    return '<img style="margin: 4px auto;" src="'+value+'" border="0" width="32" height="32"/>';
                }
            },{
                field: 'conName',
                title: '名称',
                width: $(this).width() * 0.15,
                align: 'left',
                halign: 'center'
            },{
                field: 'conDesc',
                title: '描述',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'
            },{
                field: 'conLink',
                title: '链接',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'
            },{
                field: 'conNum',
                title: '数量',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'
            },{
                field: 'sendNum',
                title: '已发送',
                align: 'center'
            },{
                field: 'validTime',
                title: '有效时间',
                align: 'center',
                width: $(this).width() * 0.1,
                formatter: function(value,row,index) {
                  return value.substring(0, value.lastIndexOf(' '));
                }
            },{
                field: 'valid',
                title: '生效与否',
                align: 'center',
                width: $(this).width() * 0.05,
                formatter: function(value,row,index) {
                    if(value == '1'){
                        return '<span style="color:#0b93d5;">有效</span>';
                    }
                    if(value == '0'){
                        return '<span style="color:#ff0000">过期</span>';
                    }
                }
            },{
                field: 'conId',
                title: '操作',
                width: $(this).width() * 0.15,
                align: 'center',
                formatter: function(value, row, index) {
                    var edit = '<a class="edit" href="#" id="conp_editor_' + $.trim(value) + '" dir="' + $.trim(row.valid) + '" onclick="editCoupon(' + index + ');">编辑</a>';
                    var del = '<a class="del" href="#" id="conp_editor_' + $.trim(value) + '" dir="' + $.trim(row.valid) + '" onclick="delCoupon(' + value + ');">失效</a>';
                    return edit +  del;
                }
            }]],
            toolbar: [{
                iconCls: 'icon-add',
                text : '添加',
                handler: function(){
                    edit = 0;
                    $('#couponFrm').form('clear');
                    cdg.dialog('setTitle','新增优惠券信息');
                    cdg.dialog('open');
                }
            },{
                iconCls: 'icon-reload',
                text : '重载',
                handler: function(){
                    coupondg.datagrid('reload');
                }
            }],
            onBeforeLoad: function(){
            },
            onLoadSuccess: function() {
                var dc = $('#couponCon').find('.edit, .del');
                if (dc.length < 1) {
                    return;
                }

                $(dc).each(function(r,d){
                    var cls = $(this).attr('class');
                    if (cls == 'edit') {
                        $(this).linkbutton({
                            plain: true,
                            iconCls: 'icon-edit'
                        });
                    } else {
                        $(this).linkbutton({
                            plain: true,
                            disabled: $(this).attr('dir') == '1' ? false : true,
                            iconCls: 'icon-remove'
                        });
                    }
                });
            }
        });

        cdg = $('#couponDialog').dialog({
            width: 780,
            height: 560,
            modal: true,
            closed: true,
            onClose: function(){
                coupondg.datagrid('reload');
            },
            buttons:[{
                text: '确定',
                iconCls: 'icon-save',
                handler: function(){
                    if (edit==0){
                        // 添加
                        submitFrm('couponFrm',
                                '/admin/coupon/add.do?pageToken=${pageToken}',
                                '新增优惠券信息成功.', '新增优惠券信息失败!');
                    } else {
                        // 更新
                        submitFrm('couponFrm',
                                '/admin/coupon/update.do?pageToken=${pageToken}',
                                '更新优惠券信息成功.', '更新优惠券信息失败!');
                    }

                    cdg.dialog('close');
                }
            },{
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $('#couponDialog').dialog('close');
                }
            }]
        });

    });

    function editCoupon(index) {
        coupondg.datagrid('selectRow', index);
        var row = coupondg.datagrid('getSelected');
        row.validTime = row.validTime.substring(0, row.validTime.lastIndexOf(' '));
        $('#validTime').datebox('setValue', row.validTime);
        if ($('#thumbImgPreview').length < 1) {
            var pimg = $('<p align="left"><img id="thumbImgPreview"/></p>');
            $(pimg).find('img').attr('src', row.conThumb).css({'width': '360', 'height': '200', 'margin': '5'});
            $(pimg).appendTo($('#conThumb').parent());
        } else {
            $('#thumbImgPreview').attr('src', row.conThumb);
        }
        $('#couponFrm').form('load', row);
        edit = 1;
        cdg.dialog('setTitle','更新优惠券信息');
        cdg.dialog('open');
    }

    function delCoupon(id) {
        $.messager.confirm('操作提示', '是否将当前优惠券失败?', function(r){
            if (r){
                $.messager.progress();
                $.post('${ctx}/admin/coupon/update.do',{
                    conId: id,
                    valid: '0'
                }, function(data){
                    $.messager.progress('close');
                    if (data.ok==true){
                        coupondg.datagrid('reload');
                        showMsg('操作提示','优惠券失效成功');
                        return;
                    }
                },'json');
            }
        });
    }

    window.setTimeout(function () {
        $('.ke-upload-area').css('width', '100px');
    }, 2000);
</script>
