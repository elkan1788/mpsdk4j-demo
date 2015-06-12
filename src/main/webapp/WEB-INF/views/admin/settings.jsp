<%--
  公众号信息配置页面
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/16/2015
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:if test="${mt eq 1}">
<div id="mpActInfo" class="tb-panel">
    <form id="mpActForm"  method="post">
        <input type="hidden" name="setId" value="${set.setId}"/>
        <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="2" align="center" height="100" valign="middle">
                    <a href="#" id="mpTipBtn">
                        这是微信的主要配置文件，如无特殊需要请勿自行修改，谢谢。
                    </a>
                </td>
            </tr>
            <tr>
                <td width="20%">原始ID：</td>
                <td>
                    <input name="mpId" id="mpId" class="easyui-validatebox infobox_input" value="${set.mpId}"/>
                </td>
            </tr>
            <tr>
                <td width="20%">应用ID：</td>
                <td>
                    <input name="appId" id="appId" class="easyui-validatebox infobox_input" value="${set.appId}"/>
                </td>
            </tr>
            <tr>
                <td width="20%">应用密钥：</td>
                <td>
                    <input name="secret" id="secret" class="easyui-validatebox infobox_input" value="${set.secret}"/>
                </td>
            </tr>
            <tr>
                <td width="20%">Token：</td>
                <td>
                    <input name="token" id="token" class="easyui-validatebox infobox_input" value="${set.token}"/>
                </td>
            </tr>
            <tr>
                <td width="20%">AES密钥：</td>
                <td>
                    <input name="aesKey" id="aesKey" class="easyui-validatebox infobox_input" value="${set.aesKey}"/>
                </td>
            </tr>
            <tr>
                <td width="20%">高级API密钥：</td>
                <td>
                    <input value="${set.accessToken}" readonly="readonly" class="easyui-validatebox infobox_input"  style="width: 80%"/>
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td width="20%">API密钥有效时间：</td>
                <td>
                    <c:set value="${set.expiresIn}" var="ei" scope="application"/>
                    <%
                        Long ei = Long.valueOf(application.getAttribute("ei").toString());
                        application.setAttribute("asTime", new Date(ei));
                    %>
                    <input value="<fmt:formatDate value="${asTime}" pattern="yyyy/MM/dd:HH:mm:ss"/>" class="easyui-validatebox infobox_input" readonly="readonly"/>
                    <input value="${set.expiresIn}" name="expiresIn" type="hidden"/>
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td width="20%">文件上传API密钥：</td>
                <td>
                    <input value="${set.jsapiTicket}" readonly="readonly" class="easyui-validatebox infobox_input" style="width: 60%"/>
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td width="20%">文件上传密钥有效时间：</td>
                <td>
                    <c:set value="${set.jsapiExpiresIn}" var="jsEi" scope="application"/>
                    <%
                       Long jsei = Long.valueOf(application.getAttribute("jsEi").toString());
                        application.setAttribute("jsTime", new Date(jsei));
                    %>
                    <input value="<fmt:formatDate value="${jsTime}" pattern="yyyy/MM/dd:HH:mm:ss"/>" class="easyui-validatebox infobox_input" readonly="readonly"/>
                    <input value="${set.jsapiExpiresIn}" name="jsapiExpiresIn" type="hidden">
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td width="20%">七牛云服务AK：</td>
                <td>
                    <input value="${set.qnAK}" name="qnAK" id="qnAK" class="easyui-validatebox infobox_input" style="width: 60%"/>
                </td>
            </tr>
            <tr>
                <td width="20%">七牛云服务SK：</td>
                <td>
                    <input value="${set.qnSK}" name="qnSK" id="qnSK" class="easyui-validatebox infobox_input" style="width: 60%"/>
                </td>
            </tr>
            <tr>
                <td width="20%">七牛云服务UK：</td>
                <td>
                    <input value="${set.qnUK}" readonly="readonly" class="easyui-validatebox infobox_input" style="width: 80%"/>
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td width="20%">七牛云服务密钥有效时间：</td>
                <td>
                    <c:set value="${set.qnExpiresIn}" var="qnEi" scope="application"/>
                    <%
                       Long qnei = Long.valueOf(application.getAttribute("qnEi").toString());
                        application.setAttribute("qnTime", new Date(qnei));
                    %>
                    <input value="<fmt:formatDate value="${qnTime}" pattern="yyyy/MM/dd:HH:mm:ss"/>" class="easyui-validatebox infobox_input" readonly="readonly"/>
                    <input value="${set.qnExpiresIn}" name="qnExpiresIn" type="hidden">
                    <span>(自动生成)</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" style="height: 46px;">
                    <a href="#" style="margin-right: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save',onClick: function(){subMpInfoFrm();}">确定</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    $(function () {
        $('#mpTipBtn').linkbutton({
            iconCls: 'icon-warning',
            selected: true,
            plain: true
        });

        $('#mpActInfo').panel({
            title:'&nbsp;&nbsp;',
            border: false,
            fit: true
        });

    });

    function subMpInfoFrm() {
        submitFrm('mpActForm',
                '/admin/wxcfg/updateset.do?pageToken=${pageToken}',
                '微信公众号信息更新成功.',
                '微信公众号信息更新失败');
    }
</script>
</c:if>
<c:if test="${mt eq 2}">
<style>
    .faceWrap {
        bottom: 94px;
        left: 15px;
    }

    .faceBox {
        position: relative;
        clear: both;
        width: 435px;
        overflow: hidden;
        background: url('${ctx}/static/img/pub/faceicon.png') no-repeat;
        cursor: pointer;
    }

    .faceBox a {
        display: block;
        float: left;
        width: 28px;
        height: 28px;
        border-right: 1px solid #DFE6F6;
        border-bottom: 1px solid #DFE6F6
    }

    .facePreview {
        clear: both;
        position: absolute;
        top: 0;
        right: 1px;
        width: 53px;
        height: 53px;
        padding: 1px;
        text-align: center;
        border: 1px solid #DFE6F6;
        background: #008AFF;
    }

    .facePreview div {
        padding-top: 6px;
        border: 1px solid #E5F3FF;
        background: #fff;
    }

    .facePreview p {
        display: block !important;
        height: 28px;
        overflow: hidden;
    }

    .facePreview .faceName {
        height: 17px;
        color: #999;
        line-height: 19px;
        background: #F1F1F1;
    }

    .faceWrap a.borderRightNone {
        border-right: none;
        margin-right: 0px;
    }

    .faceWrap a.borderBottomNone {
        border-bottom: none;
    }

    .faceWrap .faceWrapFooter {
        margin: 0px;
        margin-top: 10px;
        position: relative;
    }

    .faceWrap {
        zoom:1;outline:none;display:none;position:absolute;z-index:99999;
        background-color: #FFF;
        width: 434px;
        border: 1px solid #9FA0A0;
        padding: 10px;
        margin-bottom: 10px;
        _height: 172px;
        overflow-y: hidden;
        left: 5px;
        border-radius: 3px;
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        -moz-box-shadow: 0px 0px 6px rgba(94, 89, 89, 0.57);
        -o-box-shadow: 0px 0px 6px rgba(94, 89, 89, 0.57);
        -webkit-box-shadow: 0px 0px 6px rgba(94, 89, 89, 0.57);
        -ms-box-shadow: 0px 0px 6px rgba(94, 89, 89, 0.57);
        box-shadow: 0px 0px 6px rgba(94, 89, 89, 0.57);
    }
</style>
<div id="welContent" class="tb-panel">
    <form id="welContentForm" method="post">
        <input type="hidden" name="setId" value="${set.setId}"/>
        <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20%" valign="top" align="right">关注回复内容：</td>
                <td>
                    <textarea id="welcome" name="welcome"  rows="15" cols="70" style="margin: 10px;">${set.welcome}</textarea>
                    <div>
                        <a id="faceBtn" href="javascript:void(0)">
                            <img style="margin-left: 12px;margin-bottom: 12px" title="添加表情" src="${ctx}/static/img/pub/weixin_face.jpg" onclick="faceToggle()">
                        </a>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" style="height: 46px;">
                    <a href="#" style="margin-right: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save',onClick:function(){subWelConFrm();}">保存</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="faceWrap" hidefocus="true" tabindex="0">
    <div click="chooseEmoji" style="" class="faceBox">
        <a href="javascript:;" onclick="faceSel(this);" class="f14" title="微笑"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f1" title="撇嘴"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f2" title="色"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f3" title="发呆"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f4" title="得意"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f5" title="流泪"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f6" title="害羞"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f7" title="闭嘴"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f8" title="睡"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f9" title="大哭"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f10" title="尴尬"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f11" title="发怒"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f12" title="调皮"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f13" title="呲牙"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f0 borderRightNone" title="惊讶"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f15" title="难过"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f16" title="酷"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f96" title="冷汗"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f18" title="抓狂"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f19" title="吐"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f20" title="偷笑"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f21" title="愉快"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f22" title="白眼"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f23" title="傲慢"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f24" title="饥饿"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f25" title="困"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f26" title="惊恐"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f27" title="流汗"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f28" title="憨笑"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f29 borderRightNone" title="悠闲"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f30" title="奋斗"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f31" title="咒骂"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f32" title="疑问"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f33" title="嘘"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f34" title="晕"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f35" title="疯了"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f36" title="衰"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f37" title="骷髅"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f38" title="敲打"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f39" title="再见"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f97" title="擦汗"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f98" title="抠鼻"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f99" title="鼓掌"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f100" title="糗大了"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f101 borderRightNone" title="坏笑"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f102" title="左哼哼"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f103" title="右哼哼"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f104" title="哈欠"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f105" title="鄙视"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f106" title="委屈"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f107" title="快哭了"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f108" title="阴险"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f109" title="亲亲"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f110" title="吓"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f111" title="可怜"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f112" title="菜刀"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f89" title="西瓜"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f113" title="啤酒"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f114" title="篮球"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f115 borderRightNone" title="乒乓"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f60" title="咖啡"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f61" title="饭"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f46" title="猪头"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f63" title="玫瑰"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f64" title="凋谢"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f116" title="嘴唇"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f66" title="爱心"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f67" title="心碎"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f53" title="蛋糕"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f54" title="闪电"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f55" title="炸弹"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f56" title="刀"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f57" title="足球"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f117" title="瓢虫"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f59 borderRightNone" title="便便"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f75" title="月亮"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f74" title="太阳"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f69" title="礼物"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f49" title="拥抱"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f76" title="强"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f77" title="弱"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f78" title="握手"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f79" title="胜利"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f118" title="抱拳"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f119" title="勾引"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f120" title="拳头"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f121" title="差劲"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f122" title="爱你"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f123" title="NO"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f124 borderRightNone" title="OK"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f42 borderBottomNone" title="爱情"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f85 borderBottomNone" title="飞吻"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f43 borderBottomNone" title="跳跳"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f41 borderBottomNone" title="发抖"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f86 borderBottomNone" title="怄火"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f125 borderBottomNone" title="转圈"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f126 borderBottomNone" title="磕头"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f127 borderBottomNone" title="回头"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f128 borderBottomNone" title="跳绳"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f129 borderBottomNone" title="挥手"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f130 borderBottomNone" title="激动"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f131 borderBottomNone" title="街舞"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f132 borderBottomNone" title="献吻"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f133 borderBottomNone" title="左太极"></a>
        <a href="javascript:;" onclick="faceSel(this);" class="f134 borderBottomNone borderRightNone" title="右太极"></a>
        <div class="facePreview" style="display: none;">
            <div>
                <p class="faceImg"></p>
                <p class="faceName"></p>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#welContent').panel({
            title:'&nbsp;&nbsp;',
            border: false,
            fit: true
        });
    });

    function faceSel(faceA){
        var selFaceTitle = $(faceA).attr('title');
        $('#welcome').append('['+selFaceTitle+']');
        $(faceA).parent().parent().hide();
    }

    function faceToggle(){
        var faceImg = $('div[class="faceWrap"]');
        if(faceImg.is(":hidden")){
            faceImg[0].style.bottom = "42%";
            faceImg[0].style.left = "22%";
            faceImg.show();
        }else{
            faceImg.hide();
        }
    }

    function subWelConFrm() {
        submitFrm('welContentForm', '/admin/wxcfg/updateset.do?token=${pageToken}', '更新粉丝关注欢迎语成功.', '更新粉丝关注欢迎语失败');
    }
</script>
</c:if>
