<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/14/2015
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<script language="javascript">
  function onClickTree(node){
    var id = node.id;
    var tit = node.text;
    var url = node.url;
    var icon = node.iconCls;
    if(url){
      addTabs(id,tit,url,icon);
    }
  }
  function addTabs(idd,tit,url,icon){
    var ishas = $("#rightTabs").tabs('exists',tit);
    if(!ishas){
      $("#rightTabs").tabs('add',{
        id : idd,
        title : tit,
        href : '${ctx}'+url,
        closable : false,
        iconCls : icon
      });
    }else{
        $("#rightTabs").tabs('select',tit);
    }
  }

  $(function () {
    $('#left_menu').tree({
      url: '${ctx}/static/js/left_menu.json',
      type: 'get',
      editable: false,
      lines: true,
      onClick: function(node){
        onClickTree(node);
      }
    });
    $('#left_menu').tree('expandAll');
  })
</script>
<ul id="left_menu" class="easyui-tree left-tree"></ul>
