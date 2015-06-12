<%--
  试用活动信息
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/24/2015
  Time: 8:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="trailCon" class="con" style="margin: 0px;height:100%">
    <table id="traildg"></table>
</div>
<div id="trailDialog">
<div class="con-tb">
    <form id="trailFrm" method="post">
    <input id="trailId" name="trailId" type="hidden"/>
    <table class="infobox" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="2" align="center">
                <a href="#" id="trailTipBtn">
                    请注意活动有且仅能有一个是有效状态
                </a>
            </td>
        </tr>
        <tr>
            <td width="20%">名称：</td>
            <td>
                <input type="text" name="trailName" id="trailName" class="infobox_input"/>
            </td>
        </tr>
        <tr>
            <td width="20%">描述：</td>
            <td>
                <textarea name="trailContent" id="trailContent" style="width:98%;height:200px;visibility:hidden;background:url(${ctx}/static/img/pub/demobg.jpg) no-repeat right bottom fixed"></textarea>
            </td>
        </tr>
        <tr>
            <td width="20%">活动图片：</td>
            <td>
                <input type="button" id="uploadTrailImg" value="上传图片"/>
                <input type="hidden" id="trailImg" name="trailImg"  value="${cp.trailImg}"/>
                <c:if test="${not empty cp.trailImg}">
                    <p align="left">
                        <img id="thumbImgPreview" src="${cp.trailImg}" width="360" height="200" />
                    </p>
                </c:if>
            </td>
        </tr>
        <tr>
            <td width="20%">有效时间：</td>
            <td>
                <input type="text" name="validTime" id="validTime" class="easyui-validatebox"/>
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

    var editor, trailUpBtn;

    $.getScript('${ctx}/static/vender/kindeditor/kindeditor-min.js', function () {

        KindEditor.basePath = '${ctx}/static/vender/kindeditor/';
        if (typeof(trailUpBtn) == 'undefined'
                || null == trailUpBtn) {

            trailUpBtn = KindEditor.uploadbutton({
                button: $('#uploadTrailImg')[0],
                fieldName: 'imgFile',
                url: '${ctx}/nutz/ke4plugin/upload.do?dir=image',
                afterUpload: function (data) {
                    if (data.error == 0) {
                        $('#trailImg').val(data.url);
                        var imgpre = $('#thumbImgPreview');
                        if (imgpre.length < 1) {
                            var pimg = $('<p align="left"><img id="thumbImgPreview"/></p>');
                            $(pimg).find('img').attr('src',data.url).css({'width':'300','height':'300','margin':'5'});
                            $(pimg).appendTo($('#trailImg').parent());
                        } else {
                            imgpre.attr('src', data.url);
                        }
                    } else {
                        showMsg('操作失败', data.message);
                    }
                },
                afterError: function (str) {
                    showMsg('操作失败', '自定义错误信息: ' + str);
                }
            });

            trailUpBtn.fileBox.change(function (e) {
                trailUpBtn.submit();
            });

            console.info('trailBtn********');
            console.info(trailUpBtn);
        }

        editor = KindEditor.create('textarea[name="trailContent"]', {
            resizeType: 1,
            allowPreviewEmoticons: false,
            allowImageUpload: false,
            items: ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'link']
        });
    });

    var trail, trailDg;
    var edit = 0;
    $(function () {
        $('#trailTipBtn').linkbutton({
            iconCls: 'icon-warning',
            selected: true,
            plain: true
        });

       /* $('#trailFrm input:not(:button)').each(function(r,i){
            if(!(this.id=='')) {
                $(this).validatebox({required:true});
            }
        });*/

        $('#conDesc').validatebox({required:true});
        $('#validTime').datebox({required:true});
        $('#trailName').validatebox({required: true});

        trail = $('#traildg').datagrid({
            url: '${ctx}/admin/trail/list.do',
            border: false,
            fit: true,
            fitColumns: true,
            rownumbers: true,
            nowrap: true,
            striped: true,
            animate: true,
            singleSelect: true,
            showFooter: true,
            pagination: true,
            idField: 'trailId',
            columns: [[{
                field: 'trailImg',
                title: '缩略图',
                width: $(this).width() * 0.2,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<img style="margin: 4px auto;" src="' + value + '" border="0" width="90" height="100"/>';
                }
            }, {
                field: 'trailName',
                title: '名称',
                width: $(this).width() * 0.15,
                align: 'left',
                halign: 'center'
            }, {
                field: 'trailContent',
                title: '描述',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center',
                formatter: function(value, row, index) {
                    return rmHtmlTag(value);
                }
            }, {
                field: 'validTime',
                title: '有效时间',
                align: 'center',
                width: $(this).width() * 0.05,
                formatter: function (value, row, index) {
                    return value.substring(0, value.lastIndexOf(" "));
                }
            }, {
                field: 'valid',
                title: '生效与否',
                align: 'center',
                width: $(this).width() * 0.05,
                formatter: function (value, row, index) {
                    if (value == '1') {
                        return '<span style="color:#0b93d5;">有效</span>';
                    }
                    return '<span style="color:#ff0000">过期</span>';
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: $(this).width() * 0.15
            }, {
                field: 'trailId',
                title: '操作',
                align: 'center',
                width: $(this).width() * 0.15,
                formatter: function (value, row, index) {
                    var edit = '<a class="edit" href="#" id="trail_editor_' + $.trim(value) + '" dir="' + $.trim(row.valid) + '" onclick="editTrail(' + index + ');">编辑</a>';
                    var del = '<a class="del" href="#" id="trail_editor_' + $.trim(value) + '" dir="' + $.trim(row.valid) + '" onclick="delTrail(' + value + ');">失效</a>';
                    return edit + '<div style="margin:3px;width:24px;display: inline">&nbsp;</div>' + del;
                }
            }]],
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加',
                handler: function () {
                    edit = 0;
                    $('#trailFrm').form('clear');
                    editor.html('');
                    trailDg.dialog('setTitle', '新增试用活动信息');
                    trailDg.dialog('open');
                }
            }, {
                iconCls: 'icon-reload',
                text: '重载',
                handler: function () {
                    trail.datagrid('reload');
                }
            }],
            onBeforeLoad: function () {
            },
            onLoadSuccess: function () {
                var dc = $('#trailCon').find('.edit, .del');
                if (dc.length < 1) {
                    return;
                }

                $(dc).each(function (r, d) {
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

        trailDg = $('#trailDialog').dialog({
            width: 780,
            height: 580,
            modal: true,
            closed: true,
            onClose: function () {
                var tmp;
                trailUpBtn = tmp;
            },
            buttons: [{
                text: '确定',
                iconCls: 'icon-save',
                handler: function () {
                    var cnt = editor.text();
                    if (cnt < 10) {
                        showMsg('操作失败', '请输入正确的活动内容介绍信息.');
                        return;
                    }
                    $('#trailContent').val(editor.html());
                    if (edit == 0) {
                        // 添加
                        submitFrm('trailFrm',
                                '/admin/trail/add.do',
                                '新增试用活动信息成功.', '新增试用活动信息失败!');
                    } else {
                        // 更新
                        submitFrm('trailFrm',
                                '/admin/trail/update.do',
                                '更新试用活动信息成功.', '更新试用活动信息失败!');
                    }

                    trail.datagrid('reload');
                    trailDg.dialog('close');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#trailDialog').dialog('close');
                }
            }]
        });
    });

    window.setTimeout(function () {
        var frm = $('#uploadTrailImg').prev().find('form');
        $(frm).css('width', '100px');
    }, 2000);

    function editTrail(index) {
        trail.datagrid('selectRow', index);
        var row = trail.datagrid('getSelected');
        row.validTime = row.validTime.substring(0, row.validTime.lastIndexOf(' '));
        editor.html(row.trailContent);
        if ($('#thumbImgPreview').length < 1) {
            var pimg = $('<p align="left"><img id="thumbImgPreview"/></p>');
            $(pimg).find('img').attr('src', row.trailImg).css({'width': '90', 'height': '100', 'margin': '5'});
            $(pimg).appendTo($('#trailImg').parent());
        } else {
            $('#thumbImgPreview').attr('src', row.trailImg);
        }
        edit = 1;
        $('#trailFrm').form('load', row);
        trailDg.dialog('setTitle', '更新试用活动信息');
        trailDg.dialog('open');
    }

    function delTrail(id) {
        $.messager.confirm('操作提示', '是否将当前试用活动失败?', function (r) {
            if (r) {
                $.messager.progress();
                $.post('${ctx}/admin/trail/update.do', {
                    trailId: id,
                    valid: '0'
                }, function (data) {
                    $.messager.progress('close');
                    if (data.ok && data.errCode == 0) {
                        showMsg('操作提示', "试用活动失效成功.");
                        trail.datagrid('reload');
                        $('#' + id).linkbutton('disable');
                        return;
                    }

                    showMsg('操作提示', "试用活动失效失败.");
                }, 'json');
            }
        });
    }
</script>
