<%--
  宝宝照片墙
  User: Senhui
  Date: 4/29/2015
  Time: 4:47 PM
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
    <title>潮童大show场</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/css/game/waterfall.css"/>
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
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>

        var images = [];

        wx.config({
            debug: false,
            appId: '${appid}',
            timestamp: ${timestamp},
            nonceStr: '${nonce}',
            signature: '${signature}',
            jsApiList: [
                'checkJsApi',
                'previewImage',
                'getNetworkType',
                'hideOptionMenu',
                'showOptionMenu'
            ]
        });

        wx.ready(function() {

            wx.checkJsApi({
                jsApiList: [
                    'getNetworkType'
                ],
                success: function (res) {
                   $('#loadMask').hide();
                }

            });

            wx.getNetworkType({
                success: function (res) {
                    var nt = res.networkType;
                    if (nt!='wifi'){
                        alert('亲，你当前用的非WIFI环境，照片载入需要稍作等待 ^_^');
                    }
                }
            });
        });

        function preViewImg(cur) {
            wx.previewImage({
                current: cur,
                urls: images
            });

        }
    </script>
</head>
<body>
<div id="loadMask">
    <img src="${ctx}/static/img/pub/loading.gif" alt="加载中..."/>
</div>
<div id="waterfall"></div>
<script src="${ctx}/static/vender/jquery-1.7.2.min.js"></script>
<script src="${ctx}/static/vender/jquery.waterfall.js"></script>
<script>
    $(function() {
        var winW = $(window).width();
        var cw = winW > 400 ? 300 : 92;
        $("#waterfall").waterfall({
            colWidth: cw,
            url: "${ctx}/games/babyimgs.do"
        });
    });

</script>
</body>
</html>
