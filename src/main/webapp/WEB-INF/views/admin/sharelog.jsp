<%--
  分享记录
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/29/2015
  Time: 7:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="sharelogCon" class="con" style="margin: 0px;height:100%">
    <table id="sharelogDataGrid"></table>
</div>
<%--<div id="customSchsharelog" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwNkS" class="easyui-searchbox" style="width:260px">
</div>--%>
<script>
    var sharelog;
    $(function () {

        sharelog = $('#sharelogDataGrid').datagrid({
            url: '${ctx}/admin/gamecfg/sharelist.do',
            border: false,
            fit: true,
            fitColumns: true,
            rownumbers: true,
            nowrap: true,
            striped: true,
            animate:true,
            sinsharelogeSelect : true,
            showFooter: true,
            pagination: true,
            idField: 'sharelogId',
            columns:[[{
                field: 'sharelogId',
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
                field: 'friend',
                title: '分享朋友',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'weibo',
                title: '微博分享',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'circle',
                title: '朋友圈分享',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'qq',
                title: 'QQ分享',
                align: 'center',
                width: $(this).width() * 0.1
            }]],
            toolbar: [{
                iconCls: 'icon-reload',
                text : '刷新',
                handler: function(){
                    sharelog.datagrid('reload');
                }
            }],
            onBeforeLoad: function(){
                // 创建工具栏
               /* var dt = $('#sharelogCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#flwNkS');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSchsharelog").html() + '</td>';
                    $(dt).find('table tr').append(ldata);

                    $('#flwNkS').searchbox({
                        width: 260,
                        prompt:'输入粉丝昵称回车',
                        searcher: function(value, name) {

                        }
                    });
                }*/
            }
        });
    });
</script>
