<%--
  粉丝用户表
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/16/2015
  Time: 8:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="followersCon" class="con" style="margin: 0px;height:100%">
    <table id="followerdg"></table>
</div>
<div id="customSchFlw" style="display: none;">
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwST" type="text">
    &nbsp;至&nbsp;
    <input id="flwET" type="text">
    <a id="flwTSB" href="#">查询</a>
    <span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span>
    <input id="flwNkS" class="easyui-searchbox" style="width:260px">
</div>

<script>
    var f;
    $(function () {

        f = $('#followerdg').datagrid({
            url: '${ctx}/admin/wxcfg/flwlist.do',
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
            idField: 'followerId',
            columns:[[{
                field: 'followerId',
                hidden: true
            },{
                field: 'headimgurl',
                title: '头像',
                width: $(this).width() * 0.1,
                align: 'center',
                formatter: function(value,row,index) {
                    return '<img style="margin: 4px auto;" src="'+value+'" border="0" width="32" height="32"/>';
                }
            },{
                field: 'openid',
                title: '微信用户ID',
                width: $(this).width() * 0.15,
                align: 'left',
                halign: 'center'
            },{
                field: 'nickname',
                title: '微信昵称',
                width: $(this).width() * 0.2,
                align: 'left',
                halign: 'center'

            },{
                field: 'sex',
                title: '性别',
                align: 'center',
                width: $(this).width() * 0.05,
                formatter: function(value,row,index) {
                    if(value == '1'){
                        return '<span style="color:#0b93d5;">男</span>';
                    }
                    if(value == '2'){
                        return '<span style="color:#8b008b">女</span>';
                    }
                    if(value == '0'){
                        return '<span style="color:#bfbfbf">保密</span>';
                    }
                }
            },{
                field: 'country',
                title: '国家',
                align: 'center',
                width: $(this).width() * 0.05
            },{
                field: 'province',
                title: '省份',
                align: 'left',
                halign: 'center',
                width: $(this).width() * 0.05
            },{
                field: 'city',
                title: '城市',
                align: 'left',
                halign: 'center',
                width: $(this).width() * 0.05
            },{
                field: 'language',
                title: '语言',
                align: 'left',
                halign: 'center',
                width: $(this).width() * 0.05
            },{
                field: 'subTime',
                title: '关注时间',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'unsubTime',
                title: '取消时间',
                align: 'center',
                width: $(this).width() * 0.1
            },{
                field: 'userStatus',
                title: '关注状态',
                align: 'center',
                width: $(this).width() * 0.05,
                formatter: function(value,row,index) {
                    if (value == 0) {
                        return '<span style="color:#ff0000">退订</span>';
                    }

                    return '<span style="color:#008000">正常</span>';
                }
            }]],
            toolbar: [{
                iconCls: 'icon-reload',
                text : '重载',
                handler: function(){
                    var ps = f.datagrid('options').queryParams;
                    ps.startT = '';
                    ps.endT = '';
                    ps.nickName = '';
                    f.datagrid('reload');
                }
            },{
                iconCls: 'icon-excel',
                text: '同步微信用户',
                handler: function(){
                    $.messager.progress();
                    $.post('${ctx}/admin/wxcfg/flwsync.do',{},function(data){
                        $.messager.progress('close');
                        showMsg('操作成功提示','系统用户数量:'+data.data[1]+'(关注),<font color="red">'
                        +data.data[2]+'</font>(退订)'
                                    +'<br/>'
                                    +'微信服务器用户数量:'+data.data[0]
                                    +'<br/>'
                                    +'完成同步任务需要等待几分钟，请稍后再查看.');
                    },'json');
                }
            }],
            queryParams: {
                startT: '',
                endT: '',
                nickName: ''
            },
            onBeforeLoad: function(){
                // 创建工具栏
                var dt = $('#followersCon').find('.datagrid-toolbar');
                var csf = $(dt).find('#flwST');
                if(csf.length == 0) {
                    var ldata = '<td>' + $("#customSchFlw").html() + '</td>';
                    $(dt).find('table tr').append(ldata);

                    // 组件初始化
                    $('#flwST').datebox();
                    $('#flwET').datebox();
                    $('#flwTSB').linkbutton({
                        iconCls: 'icon-search',
                        plain: true,
                        onClick: function() {
                            var ps = f.datagrid('options').queryParams;
                            ps.startT = $('#flwST').datebox('getValue');
                            ps.endT = $('#flwET').datebox('getValue');
                            f.datagrid('reload');
                        }
                    });
                    $('#flwNkS').searchbox({
                        width: 260,
                        prompt:'输入粉丝昵称回车',
                        searcher: function(value, name) {
                            var ps = f.datagrid('options').queryParams;
                            ps.nickName = value;
                            f.datagrid('reload');
                        }
                    });
                }
            }
        });
    });
</script>
