<%--
  优惠券发送记录
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/24/2015
  Time: 7:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="couponlogCon" class="con" style="margin: 0px;height:100%">
    <table id="gamelogdg"></table>
</div>
<%--<div id="customSchCl" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwNkS" class="easyui-searchbox" style="width:260px">
</div>--%>
<script>
    var cl;
    $(function () {

        cl = $('#gamelogdg').datagrid({
            url: '${ctx}/admin/coupon/loglist.do',
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
            idField: 'conlogId',
            columns:[[{
                field: 'conlogId',
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
                field: 'coupName',
                title: '优惠券名称',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'
            },{
                field: 'type',
                title: '消息类型',
                align: 'center',
                width: $(this).width() * 0.2,
                formatter: function(value,row,index) {
                    if(value == '1'){
                        return '<span style="color:#0b93d5;">中奖提示</span>';
                    }
                    if(value == '2'){
                        return '<span style="color:#8b008b">过期提醒</span>';
                    }
                }
            },{
                field: 'createTime',
                title: '记录时间',
                align: 'center',
                width: $(this).width() * 0.2
            }]],
            toolbar: [{
                iconCls: 'icon-reload',
                text : '刷新',
                handler: function(){
                    cl.datagrid('reload');
                }
            }],
            onBeforeLoad: function(){
                // 创建工具栏
                /*var dt = $('#couponlogCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#flwNkS');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSchCl").html() + '</td>';
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
