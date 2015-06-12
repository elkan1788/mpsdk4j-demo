<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/14/2015
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<html>
<head>
    <title>努比微信应用管理平台</title>
    <jsp:include page="common.jsp"/>
    <style>
        #loadMask {
            width:100%;
            height:100%;
            background-color:#fff;
            filter:alpha(opacity=98);
            -moz-opacity:0.98;
            opacity:0.98;
            position:absolute;
            left:0px;
            top:0px;
            z-index:1000;
            text-align: center;
        }

        #loadMask img {
            position:absolute;
            z-index: 99999;
            top: 50%;
            left: 45%;
            width: 18px;
            height: 18px;
        }
    </style>
</head>
<body class="easyui-layout">
    <div id="loadMask">
        <img src="${ctx}/static/img/pub/loading.gif" alt="加载中..."/>
    </div>
  <div class="top" id="topBg" data-options="region:'north',border:false">
    <jsp:include page="top.jsp"/>
  </div>
  <div data-options="region:'west',split:true,title:'&nbsp;&nbsp;导航',iconCls:'icon-directory'" style="width:165px;background-color: #EEF9EB;">
    <jsp:include page="left.jsp"/>
      <!--
      /*{
        "id": "0204",
        "text": "排行榜",
        "iconCls": "icon-list",
        "url": ""
      },
      {
        "id": "0204",
        "text": "游戏试玩",
        "iconCls": "icon-game",
        "url": ""
      },*/
       -->
  </div>
  <div data-options="region:'center',split:true">
    <div id="rightTabs" class="easyui-tabs" data-options="fit:true,border:false">
      <div title="欢迎使用!" data-options="closable:false,id:-1,iconCls:'icon-home'">
        <jsp:include page="main.jsp"/>
      </div>
    </div>
  </div>
  <div data-options="region:'south'" style="height: 30px;background-color: #E6EEF8">
    <div align="center" style="line-height:23px;font-size:12px;">
      Copyright &copy;  2014-2015 努比.版权所有&nbsp;&nbsp;&nbsp;&nbsp;技术支持：上海智众品牌整合传播机构
    </div>
  </div>
  <div id="repwd">
    <div class="con-tb">
      <form id="addFormRe" method="post">
        <table class="infobox" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5%"><label>帐号</label></td>
            <td>${sessionScope.current_user.account}</td>
          </tr>
          <tr>
            <td width="25%"><label>昵称</label></td>
            <td>
              <input type="text" name="nickname" id="nickname" class="easyui-validatebox"
                     value="${sessionScope.current_user.nickName}"
                     data-options="required:true"/>
            </td>
          </tr>
          <tr>
            <td width="25%"><label for="password">密码</label></td>
            <td>
              <input id="password"
                     name="password"
                     type="password" class="easyui-validatebox"
                                   data-options="required:true"/>
            </td>
          </tr>
          <tr>
            <td width="35%">
              <label for="password2">确认密码</label>
            </td>
            <td>
              <input id="password2"
                     name="password2"
                     type="password"
                     class="easyui-validatebox"
                                   data-options="required:true"/>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <div style="display: none">${pageToken}</div>
  <script>
    var repwdDg;

    function toRepwd(){
        repwdDg.dialog('open');
    }

    function onRepwd(){
      $.messager.progress();
      $("#addFormRe").form('submit',{
        url:'${ctx}/admin/updatePswd.do',
        onSubmit: function(){
          var isValid = $(this).form('validate');
          if (!isValid){
            $.messager.progress('close');
          }
          return isValid;
        },
        success:function(data){
          $.messager.progress('close');
          if(data.ok){
            $.messager.alert('提示','修改密码成功！','info',function(){
                $('#nickname').val(data.data.nickName);
                $('#welName').val(data.data.nickName);
              repwdDg.dialog('close');
            });
          }else if(data.errCode=-5){
            $.messager.alert('提示','确认密码不正确！','warning');
          }else{
            $.messager.alert('提示','修改密码失败！','warning');
          }
        }
      });
    }

    function onReset(){
      repwdDg.dialog('close');
    }

    $(function () {

      repwdDg = $("#repwd").dialog({
        title:'修改密码',
        resizable:false,
        width:400,
        draggable: false,
        modal: true,
        closed: true,
        buttons:[{
          text: '修改',
          iconCls: 'icon-save',
          handler: function(){
            onRepwd();
          }
        },{
          text: '取消',
          iconCls: 'icon-cancel',
          handler: function () {
            onReset();
          }
        }]
      });

      $("#newcomm").panel({
        title:'系统版本',
        doSize:true,
        height:156,
        collapsible:true,
        iconCls: 'icon-update',
        style: 'font-size:12px;'
      });

      $("#comm").panel({
        title:'系统信息',
        doSize:true,
        collapsible:true,
        iconCls: 'icon-sysinfo',
        style: 'font-size:12px;'
      });
    });

    function showMsg(title, msg) {
      $.messager.show({
          title: title,
          msg: msg,
          height: 140,
          timeout: 5000,
          showType: 'slide'
      });
    }

    function submitFrm(id, url, sucmsg, errmsg) {
        $.messager.progress();
        $('#'+id).form('submit',{
            url: '${ctx}'+url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress('close');
                var data = eval('(' + data + ')');
                if(data.ok && data.errCode == 0) {
                    showMsg('操作成功',sucmsg);
                } else {
                    showMsg('操作失败', errmsg+',错误代码:'+data.errCode+'.');
                }
            }
        });
    }

      function rmHtmlTag(html){
          var start_ptn = /<\/?[^>]*>/g;
          var end_ptn = /[ | ]*\n/g;
          var space_ptn = /&nbsp;/ig;
          html = html.replace(start_ptn, '');
          html = html.replace(end_ptn, '');
          html = html.replace(space_ptn, '');
          return html;
      }
  </script>
  <script>
      $(function(){
          $('#loadMask').fadeOut();
      });
  </script>
</body>
</html>
