<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 5/9/2015
  Time: 9:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="expires" content="0">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no;" name="format-detection">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <title>萌宝游戏排行榜</title>
    <style>
        *{
            -webkit-shape-outside: none;
            outline:none;
        }
        body{
            margin: 0;
            padding: 0;
            overflow-x: hidden;
        }
        a {
            outline: none;
        }
        .title {
            margin-top: 20px;
            text-align: center
        }
        .title h1 {
            font-size: 18px;
            font-weight: bold;
            padding: 3px;
            color: red;
            margin-bottom: 10px
        }
        #gameRanks .content {
            width: 99%;
            text-align: center;
            line-height: 20px;
            font-size: 15px;
            margin-bottom: 26px;
            text-align: center;
            padding-left: 8px;
        }

        #gameRanks table, tr, td {
            border-collapse: collapse;
            border-spacing: 0;
            vertical-align: middle;
        }

        #gameRanks td {
            height: 50px;
            border-bottom: 1px solid #D9D9D9;
        }

        #gameRanks td img {
            width: 36px;
            height: 36px;
            border-radius: 8px;
        }

        #gameRanks td font {
            font-size: 16px;
            color: #434445;
        }

        .pass_time {
            color: #909090;
            font-size: 16px;
            font-weight: bolder;
            text-align: right;
        }

        .gold {
            width: 24px;
            height: 24px;
            background-color: #EACE38;
            border-radius: 12px;
            border: 1px solid #E2985D;
        }

        .gold span {
            height: 24px;
            line-height: 22px;
            display: block;
            color: #E2985D;
            text-align: center;
        }

        .silver {
            width: 24px;
            height: 24px;
            background-color: #CDCFCD;
            border-radius: 12px;
            border: 1px solid #999897;
        }

        .silver span {
            height: 24px;
            line-height: 22px;
            display: block;
            color: #999897;
            text-align: center;
        }

        .bronze {
            width: 24px;
            height: 24px;
            background-color: #DDB78C;
            border-radius: 12px;
            border: 1px solid #A9845B;
        }

        .bronze span {
            height: 24px;
            line-height: 22px;
            display: block;
            color: #A9845B;
            text-align: center;
        }

        .dekaronGame {
            background-color: #fabe00;
            color: #000;
            padding: 8px 26px;
            font-size: 16px;
            font-weight: bold;
            border: 0;
            -moz-border-radius: 8px;
            -webkit-border-radius: 8px;
            border-radius: 16px;
            letter-spacing: 1px;
            box-shadow: 0 0 5px #bbb;
            outline: 0;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div id="gameRanks">
    <div class="title">
        <h1 style="color:#000;">游戏排名</h1>
    </div>
    <div class="content">
            <c:choose>
                <c:when test="${empty ranks}">
                    <div style="height: 160px">宝座等你来挑战哟，快来!</div>
                    <div style="padding: 14px">
                        <a class="dekaronGame">立刻挑战</a>
                    </div>
                </c:when>
                <c:otherwise>
                <table width="98%">
                    <c:forEach var="r" items="${ranks}" varStatus="loop">
                        <tr>
                            <td width="28">
                                <c:if test="${loop.first}">
                                    <div class="gold"><span>${loop.index+1}</span></div>
                                </c:if>
                                <c:if test="${loop.index eq 1}">
                                    <div class="silver"><span>${loop.index+1}</span></div>
                                </c:if>
                                <c:if test="${loop.index eq 2}">
                                    <div class="bronze"><span>${loop.index+1}</span></div>
                                </c:if>
                                <c:if test="${loop.index gt 2}">
                                    ${loop.index+1}
                                </c:if>
                            </td>
                            <td width="50">
                                <img src="${r.headImg}"/>
                            </td>
                            <td align="left">
                                ${r.nickName}
                            </td>
                            <td width="80" class="pass_time">
                                ${r.ptFormat}
                            </td>
                        </tr>
                    </c:forEach>
                        <tr>
                            <td colspan="4" style="border: none; padding: 32px;text-align: center;">
                                <a class="dekaronGame">立刻挑战</a>
                            </td>
                        </tr>
                </table>
                </c:otherwise>
            </c:choose>
        </table>
    </div>
</div>
</body>
<script>
    window.onload = function() {
        var deg = document.getElementsByClassName('dekaronGame');
        deg[0].onclick = function() {
            window.location.href = '${ctx}/games/puzzle.do?openid=${openid}&token=${pageToken}';
        }
    }
</script>
</html>
