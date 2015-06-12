<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/14/2015
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<div class="logo">
  <img id="logoImg" src="${ctx}/static/css/admin/default/logo.png" />
</div>
<div class="show">
  <div class="l2">
    <span id="localtime" style="margin-right:18px"></span>
      <span class="hi">你好：
        <strong style="margin-left:2px;margin-right: 10px;" id="welName">${sessionScope.current_user.nickName}</strong>
        <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-password',selected:true,onClick:toRepwd">修改密码</a>
        <a class="easyui-linkbutton" target="_top" href="" data-options="plain:false,iconCls:'icon-exit',iconAlign:'right',onClick:function(){logout();}">注销</a>
      </span>
  </div>
</div>
<script runat="server" language="javascript">
  function tick(){
    var today;
    today = new Date();
    document.getElementById("localtime").innerHTML = showLocale(today);
    window.setTimeout("tick()", 1000);
  }
  tick();

  function logout() {
      $.messager.confirm('确认对话框', '您想要退出该系统吗？', function(r){
          if (r){
              window.location.href = '${ctx}/admin/logout.do';
          }
      });
  }
</script>