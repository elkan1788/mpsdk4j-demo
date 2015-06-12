<%--
  试用活动记录
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/27/2015
  Time: 7:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="traillogCon" class="con" style="margin: 0px;height:100%">
    <table id="traillogdg"></table>
</div>
<div id="customSchTrailLog" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="trlFlwNkS" class="easyui-searchbox" style="width:260px">
</div>
<script>
    var gl;
    $(function () {

        gl = $('#traillogdg').datagrid({
            url: '${ctx}/admin/trail/loglist.do',
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
            idField: 'trailogId',
            columns:[[{
                field: 'trailogId',
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
                field: 'trailName',
                title: '活动名称',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'username',
                title: '登记姓名',
                width: $(this).width() * 0.1,
                align: 'center'
            },{
                field: 'phonenum',
                title: '手机号码',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'city',
                title: '城市',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'province',
                title: '省份',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'address',
                title: '通讯地址',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'zipcode',
                title: '邮编',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'createTime',
                title: '登记时间',
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
            queryParams: {
                username: ''
            },
            onBeforeLoad: function(){
                // 创建工具栏
                var dt = $('#traillogCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#trlFlwNkS');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSchTrailLog").html() + '</td>';
                    $(dt).find('table tr').append(ldata);

                    $('#trlFlwNkS').searchbox({
                        width: 260,
                        prompt:'输入用户姓名回车',
                        searcher: function(value, name) {
                            gl.datagrid('options').queryParams.username = value;
                            gl.datagrid('reload');
                        }
                    });
                }
            }
        });
    });
</script>
