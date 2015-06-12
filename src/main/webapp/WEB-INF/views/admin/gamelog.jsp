<%--
  游戏记录
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/24/2015
  Time: 7:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="gamelogCon" class="con" style="margin: 0px;height:100%">
    <table id="gameLogDataGrid"></table>
</div>
<div id="customSchGl" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwNkS" class="easyui-searchbox" style="width:260px">
</div>
<script>
    var gl;
    $(function () {

        gl = $('#gameLogDataGrid').datagrid({
            url: '${ctx}/admin/gamecfg/loglist.do',
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
            idField: 'gamelogId',
            columns:[[{
                field: 'gamelogId',
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
                field: 'phoneType',
                title: '设备',
                width: $(this).width() * 0.1,
                align: 'center',
                formatter: function(value,row,index) {

                    if (/Android/i.test(value)) {
                        return 'Android';
                    }

                    if (/iPhone/i.test(value)) {
                        return 'iPhone';
                    }

                    if (/iPad/i.test(value)) {
                        return 'iPad';
                    }

                    if (/iPod/i.test(value)) {
                        return 'iPod';
                    }

                    if (/(Windows Phone)/i.test(value)) {
                        return 'Windows Phone';
                    }

                    if (/Chrome/i.test(value)) {
                        return 'Chrome';
                    }
                }
            },{
                field: 'time1',
                title: '关卡1时间',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'time2',
                title: '关卡2时间',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'time3',
                title: '关卡3时间',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'time4',
                title: '关卡4时间',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'totalTime',
                title: '总耗时',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'pass',
                title: '通关与否',
                align: 'center',
                width: $(this).width() * 0.1,
                formatter: function(value,row,index) {
                    if(value == '1'){
                        return '<span style="color:#008000;">是</span>';
                    }
                    if(value == '0'){
                        return '<span style="color:#8b008b">否</span>';
                    }
                }
            },{
                field: 'gamelogTime',
                title: '记录时间',
                align: 'center',
                width: $(this).width() * 0.2
            }]],
            toolbar: [{
                iconCls: 'icon-reload',
                text : '刷新',
                handler: function(){
                    gl.datagrid('reload');
                }
            }],
            onBeforeLoad: function(){
                // 创建工具栏
                var dt = $('#gamelogCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#flwNkS');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSchGl").html() + '</td>';
                    $(dt).find('table tr').append(ldata);

                    $('#flwNkS').searchbox({
                        width: 260,
                        prompt:'输入粉丝昵称回车',
                        searcher: function(value, name) {

                        }
                    });
                }
            }
        });
    });
</script>
