<%--
  宝宝相片
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/27/2015
  Time: 9:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<style type="text/css">
    .c-label{
        display:inline-block;
        width:auto;
        font-weight:bold;
    }
    .c-label-img{
        float: right;
        clear: both;
        margin-left: 200px;
    }

    .c-label-img img {
        width: 150px;
        margin-left: 20px;
    }
</style>
<div id="babyimgCon" class="con" style="margin: 0px;height:100%">
    <table id="babyimgdg"></table>
</div>
<div id="customSch" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwNkS" class="easyui-searchbox" style="width:260px">
</div>
<script>
    var docW = $(document).width();
    var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
        renderRow: function(target, fields, frozen, rowIndex, rowData){
            var cc = [];
            cc.push('<td colspan="' + fields.length + '" style="border:0;text-align:center;width:'+docW+'px">');
            if (!frozen){
                var img1 = rowData.image1;
                var img2 = rowData.image2;
                cc.push('<div style="display:inline;float:right;padding:20px 50px 5px 50px; text-align: right;">');
                cc.push('<img src="' + img1 + '" style="width:150px;margin-right:40px;max-height:150px;">');
                cc.push('<img src="' + img2 + '" style="width:150px;max-height:150px;">');
                cc.push('</div>');

                cc.push('<div style="display:inline;float:left; text-align: left;padding:10px 50px;height:5px;">');
                cc.push('<table id="babyImgView" width="100%" border="0"><tr>');
                cc.push('<td width="140" style="border:0px;text-align:center"><a href="#" class="easyui-linkbutton" dir="'+rowData.imgId+'">删除</a> </td>');
                cc.push('<td width="50" align="center" style="border:0px;"><img style="margin: 4px 24px;" src="'+rowData[fields[1]]+'" border="0" width="64" height="64"/></td>');
                cc.push('<td style="border:0px;padding: 5px;">');
                for(var i=2; i<fields.length; i++){
                    if(i == 4 || i == 5){
                        continue;
                    }
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</td></tr></table></div>');
            }
            cc.push('</td>');
            return cc.join('');
        }
    });
    var babyimg;
    $(function () {

        babyimg = $('#babyimgdg').datagrid({
            url: '${ctx}/admin/gamecfg/babylist.do',
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
            columns:[[{
                field: 'imgId',
                hidden: true
            },{
                field: 'headImg',
                title: '头像',
                width: $(this).width() * 0.2,
                align: 'center',
                formatter: function(value,row,index) {
                    return '<img style="margin: 4px auto;" src="'+value+'" border="0" width="32" height="32"/>';
                }
            },{
                field: 'nickName',
                title: '用户昵称',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'
            },{
                field: 'babyName',
                title: '宝宝姓名',
                width: $(this).width() * 0.2,
                align: 'left',
                align: 'center'
            },{
                field: 'image1',
                title: '相片1',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'image2',
                title: '相片2',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'mediaId1',
                title: '微信记录1',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'mediaId2',
                title: '微信记录2',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'updateTime',
                title: '更新时间',
                align: 'center',
                width: $(this).width() * 0.2
            }]],
            view: cardview,
            toolbar: [{
                iconCls: 'icon-reload',
                text : '刷新',
                handler: function(){
                    babyimg.datagrid('reload');
                }
            }],
            onBeforeLoad: function(){
                // 创建工具栏
                var dt = $('#babyimgCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#flwNkS');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSch").html() + '</td>';
                    $(dt).find('table tr').append(ldata);

                    $('#flwNkS').searchbox({
                        width: 260,
                        prompt:'输入粉丝昵称回车',
                        searcher: function(value, name) {

                        }
                    });
                }
            },
            onLoadSuccess:function() {
                $('#babyImgView .easyui-linkbutton').each(function(r,i){
                    var imgid = $(this).attr('dir');
                    $(this).linkbutton({
                        plain: true,
                        iconCls: 'icon-remove',
                        onClick:function(){
                            updataUserImg(imgid);
                        }
                    });
                });
            }
        });
    });

    function updataUserImg(id){
        $.messager.confirm('操作提示', '是否替换当前用户上传图片?', function(r){
            if (r){
                $.messager.progress();
                $.post('${ctx}/admin/gamecfg/updatebabyimg.do',{
                    imgid: id,
                    valid: '0'
                }, function(data){
                    $.messager.progress('close');
                    if (data.ok==true){
                        babyimg.datagrid('reload');
                        showMsg('操作提示','替换用户宝宝图片成功');
                        return;
                    }
                },'json');
            }
        });
    }
</script>
