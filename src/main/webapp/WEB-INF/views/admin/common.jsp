<%--
    页面公用变量
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ver" value="1.b.23" scope="request"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="css" value="${ctx}/static/css" scope="request"/>
<c:set var="js" value="${ctx}/static/js" scope="request"/>
<c:set var="ven" value="${ctx}/static/vender" scope="request"/>
<c:set var="img" value="${ctx}/static/img" scope="request"/>
<!-- 样式引入 -->
<link type="text/css" rel="stylesheet" href="${ven}/easyui/themes/default/easyui.css?q_=${ver}"/>
<link type="text/css" rel="stylesheet" href="${ven}/easyui/themes/icon.css?q_=${ver}"/>
<link type="text/css" rel="stylesheet" href="${css}/admin/index.css?q_=${ver}"/>
<link type="text/css" rel="stylesheet" href="${css}/admin/default/style.css?q_=${ver}"/>
<link type="text/css" rel="stylesheet" href="${ven}/kindeditor/themes/default/default.css?q_=${ver}"/>
<style>
    .tb-panel {
        padding: 10px;
    }

    .tb-panel table td {
        height: 36px;
        line-height: 20px;
        padding: 12px;
    }

    .tb-panel table input {
        width: 25%;
    }
</style>
<!-- 脚本引入 -->
<script src="${ven}/datepicker/WdatePicker.js?q_=${ver}"></script>
<script src="${ven}/datepicker/lang/zh-cn.js?q_=${ver}"></script>
<script src="${ven}/jquery-1.9.1.min.js?q_=${ver}"></script>
<script src="${ven}/jquery.form.js?q_=${ver}"></script>
<script src="${ven}/easyui/jquery.easyui.min.js?q_=${ver}"></script>
<script src="${ven}/easyui/local/easyui-lang-zh_CN.js?q_=${ver}"></script>
<script src="${ven}/objFunc.js?q_=${ver}"></script>
<script src="${ven}/kindeditor/kindeditor-all-min.js?q_=${ver}"></script>
<script src="${ven}/kindeditor/lang/zh_CN.js?q_=${ver}"></script>
<%--<script src="${ven}/xheditor/xheditor-1.2.2.min.js?q_=${ver}"></script>
<script src="${ven}/xheditor/xheditor_lang/zh_cn.js?q_=${ver}"></script>--%>
