<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/14/2015
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ver" value="1.b.0" scope="request"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>努比微信应用管理平台--登录</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/vender/easyui/themes/default/easyui.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/vender/easyui/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/css/admin/login.css">
</head>
<body>
  <div class="headbg"></div>
  <div class="login">
    <div class="logo"></div>
    <div class="line"></div>
    <div class="input">
      <div class="logo2"><img src="${ctx}/static/img/admin/l_titleemp.png" width="245" height="44"/></div>
      <form name="login" action="${ctx}/admin/login/check.do"  method="post">
        <div class="inpt">
          <div class="text">
            <span>账 号：</span><input id="username" name="user" type="text" class="user" maxlength="16"/>
          </div>
          <div class="text" style="margin-top:14px; margin-top:12px;\9; *margin-top:9px;;">
            <span>密 码：</span><input id="password" name="pswd" type="password" class="pwd" maxlength="16"/>
          </div>
        </div>
        <div class="submit">
          <input name="login" type="submit" class="but" value=" "/>
        </div>
        <div style="display: none;">
          <input id="pageToken" name="token" value="${pageToken}">
        </div>
      </form>
    </div>
  </div>
  <div class="footbg">
    <div class="copy">Copyright &copy;  2014-2015 努比.版权所有&nbsp;&nbsp;&nbsp;&nbsp;技术支持：上海智众品牌整合传播机构</div>
  </div>
  <script src="${ctx}/static/vender/jquery-1.9.1.min.js"></script>
  <script src="${ctx}/static/vender/jquery.form.js"></script>
  <script src="${ctx}/static/vender/easyui/jquery.easyui.min.js"></script>
  <script src="${ctx}/static/vender/easyui/local/easyui-lang-zh_CN.js"></script>
  <script>
      var token, user, pswd;
      var reg = /^\w+$/;
      $(function () {
          token = $('#pageToken').val();
          $('form').on('submit', function () {

              $.messager.progress();
              $(this).ajaxSubmit({
                  type: 'post',
                  dataType: 'json',
                  beforeSubmit: function () {
                      user = $('#username').val();
                      pswd = $('#password').val();
                      console.info(user + '--' + pswd);
                      // 检验用户名与密码
                      if (user.length < 4
                              || user.length > 16
                              || !reg.test(user)) {
                          $.messager.progress('close');
                          $.messager.alert('操作提示', '请输入合法的用户名!!', 'warning');
                          return false;
                      }
                      if (pswd.length < 4
                              || pswd.length > 16
                              || !reg.test(pswd)) {
                          $.messager.progress('close');
                          $.messager.alert('操作提示', '请输入合法的密码!!', 'warning');
                          return false;
                      }
                      return true;
                  },
                  success: function (data) {

                      if (!data.ok) {
                          $.messager.progress('close');
                          $.messager.alert('操作提示', '登录失败!原因:' + data.msg, 'error');
                          return;
                      }

                      window.location.href = '${ctx}'+data.data;
                  }
              });

              return false;
          });
      });
  </script>
</body>
</html>
