<%--
  游戏配置页面
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/20/2015
  Time: 10:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<div id="gsTabCon">
    <div title="微信图文消息" data-options="iconCls:'icon-weixin'" class="con-tb">
        <form id="wxNewsFrm" method="post">
            <input name="setId" id="setId" type="hidden" value="${gs.setId}" />
            <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20%">消息标题： </td>
                    <td>
                        <input type="text" class="infobox_input" id="newsTitle" name="newsTitle" value="${gs.newsTitle}"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%">游戏规则说明：</td>
                    <td>
                        <textarea id="wxNews" name="wxNews" rows="13" cols="80" class="easyui-validatebox">${gs.wxNews}</textarea>
                    </td>
                </tr>
                <tr>
                    <td width="20%">展示图片：</td>
                    <td>
                        <p>
                            <a href="#" class="imgTipBtn">
                                图像的大小为360 * 300
                            </a>
                        </p>
                        <input type="button" id="uploadImgBtn" value="上传图片"/>
                        <input type="hidden" id="newsImg" name="newsImg" value="${gs.newsImg}"/>
                        <c:if test="${not empty gs.newsImg}">
                            <p align="left">
                            <img id="newsImgPreview" src="${gs.newsImg}" width="360" height="300"/>
                            </p>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style="height: 46px;">
                        <a href="#" style="margin-right: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save',onClick: function(){subWxNewsFrm();}">确定</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div title="游戏图片" data-options="iconCls:'icon-photos'">
        <form id="gsImgFrm" method="post">
            <input name="setId" type="hidden" value="${gs.setId}" />
            <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="8" align="center" height="60" valign="middle">
                        <a href="#" class="imgTipBtn">
                            上传的游戏图片建议采用320 * 320的倍数，在保证质量的同时最大限度的压缩图片体积
                        </a>
                    </td>
                </tr>
                <tr>
                <c:forEach varStatus="cnt" begin="1" end="20" step="1">
                    <td>图片${cnt.index}：</td>
                    <td>
                        <input type="button" id="uploadImg${cnt.index}" value="上传图片${cnt.index}"/>
                        <input type="hidden" id="image${cnt.index}" name="image${cnt.index}" value="${imgs[cnt.index-1]}"/>
                        <c:if test="${not empty imgs[cnt.index-1]}">
                            <p align="left">
                                <img id="image${cnt.index}Preview" src="${imgs[cnt.index-1]}" width="320" height="320" />
                            </p>
                        </c:if>
                    </td>
                    <c:if test="${cnt.index % 4 == 0 && !cnt.last}">
                        </tr><tr>
                    </c:if>
                </c:forEach>
                </tr>
                <tr>
                    <td>广告图片：</td>
                    <td colspan="7">
                        <p>
                        <a href="#" class="imgTipBtn">
                            广告图像的大小比例保持为512 * 150
                        </a>
                        </p>
                        <input type="button" id="uploadAdImg" value="上传广告图片"/>
                        <input type="hidden" id="adImg" name="adImg"  value="${gs.adImg}"/>
                        <c:if test="${not empty gs.adImg}">
                            <p align="left">
                            <img id="adImgPreview" src="${gs.adImg}" width="256" height="75" />
                            </p>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" style="height: 46px;">
                        <a href="#" style="margin-right: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save',onClick: function(){subGsImgFrm();}">确定</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div title="通关时间" data-options="iconCls:'icon-time'">
        <form id="gsTimeFrm" method="post">
            <input name="setId" id="setId2" type="hidden" value="${gs.setId}" />
            <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="2" align="center" height="100" valign="middle">
                        <a href="#" class="easyui-linkbutton" data-options="plain:true, selected:true">
                            游戏通关的设置时间单位为：秒
                        </a>
                    </td>
                </tr>
                <tr>
                    <td width="20%">关卡1时间： </td>
                    <td>
                        <input type="text" class="infobox_input" id="time1" name="time1" value="${gs.time1}"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%">关卡2时间：</td>
                    <td>
                        <input type="text" class="infobox_input" id="time2" name="time2" value="${gs.time2}"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%">关卡3时间：</td>
                    <td>
                        <input type="text" class="infobox_input" id="time3" name="time3"  value="${gs.time3}"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%">关卡4时间：</td>
                    <td>
                        <input type="text" class="infobox_input" id="time4" name="time4"  value="${gs.time4}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style="height: 46px;">
                        <a href="#" style="margin-right: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save',onClick: function(){subGsTimeFrm();}">确定</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                    </td>
                </tr>
            </table>
            </table>
        </form>
    </div>
</div>
<script>
    var newsImgBtn, upBtn1, upBtn2, upBtn3, upBtn4, adImgBtn;

    $.getScript('${ctx}/static/vender/kindeditor/kindeditor-min.js', function () {

        KindEditor.basePath = '${ctx}/static/vender/kindeditor/';

        if (typeof(newsImgBtn) == 'undefined'
                || null == newsImgBtn) {
            newsImgBtn = KindEditor.uploadbutton({
                button: $('#uploadImgBtn')[0],
                fieldName: 'imgFile',
                url: '${ctx}/nutz/ke4plugin/upload.do?dir=image',
                afterUpload: function (data) {
                    if (data.error == 0) {
                        KindEditor('#newsImg').val(data.url);
                        var imgpre = $('#newsImgPreview');
                        if (imgpre.length < 1) {
                            var pimg = $('<p align="left"><img id="newsImgPreview"/></p>');
                            $(pimg).find('img').attr('src',data.url).css({'width':'300','height':'300','margin':'5'});
                            $(pimg).appendTo($('#newsImg').parent());
                        } else {
                            imgpre.attr('src', data.url);
                        }
                    } else {
                        showMsg('操作失败', data.message);
                    }
                },
                afterError: function (str) {
                    showMsg('操作失败', '自定义错误信息: ' + str);
                }
            });

            newsImgBtn.fileBox.change(function (e) {
                newsImgBtn.submit();
            });
        }

        <c:forEach varStatus="upCnt" begin="1" end="20" step="1">

           var  upBtn${upCnt.index} = KindEditor.uploadbutton({
                button: $('#uploadImg${upCnt.index}')[0],
                fieldName: 'imgFile',
                url: '${ctx}/nutz/ke4plugin/upload.do?dir=image',
                afterUpload: function (data) {
                    if (data.error == 0) {
                        $('#image${upCnt.index}').val(data.url);
                        var imgpre = $("#image${upCnt.index}Preview");
                        if (imgpre.length < 1) {
                            var pimg = $('<p align="left"><img id="image${upCnt.index}Preview"/></p>');
                            $(pimg).find('img').attr('src',data.url).css({'width':'300','height':'300','margin':'5'});
                            $(pimg).appendTo($('#image${upCnt.index}').parent());
                        } else {
                            imgpre.attr('src', data.url);
                        }
                    } else {
                        showMsg('操作失败', data.message);
                    }
                },
                afterError: function (str) {
                    showMsg('操作失败', '自定义错误信息: ' + str);
                }
            });

            upBtn${upCnt.index}.fileBox.change(function (e) {
                upBtn${upCnt.index}.submit();
            });
        </c:forEach>

        if (typeof(adImgBtn) == 'undefined'
                || null == adImgBtn) {
            adImgBtn = KindEditor.uploadbutton({
                button: $('#uploadAdImg')[0],
                fieldName: 'imgFile',
                url: '${ctx}/nutz/ke4plugin/upload.do?dir=image',
                afterUpload: function (data) {
                    if (data.error == 0) {
                        $('#adImg').val(data.url);
                        var imgpre = $('#adImgPreview');
                        if (imgpre.length < 1) {
                            var pimg = $('<p align="left"><img id="adImgPreview"/></p>');
                            $(pimg).find('img').attr('src',data.url).css({'width':'251','height':'75','margin':'5'});
                            $(pimg).appendTo($('#adImg').parent());
                        } else {
                            imgpre.attr('src', data.url);
                        }
                    } else {
                        showMsg('操作失败', data.message);
                    }
                },
                afterError: function (str) {
                    showMsg('操作失败', '自定义错误信息: ' + str);
                }
            });

            adImgBtn.fileBox.change(function (e) {
                adImgBtn.submit();
            });
        }
    });

    $(function(){
        $('#gsTabCon').tabs({
            border: false,
            fit: true,
            tabPosition: 'bottom'
        });

        $('.imgTipBtn').linkbutton({
            iconCls: 'icon-photos',
            selected: true,
            plain: true
        });

        $('#wxNews').validatebox({required:true});
        $('#gsTabCon form input:not(:button)').each(function(r,i){
           if(!(this.id=='')) {
               $(this).validatebox({required:true});
           }
        });

        $('#time1, #time2, #time3, #time4').spinner({
            width: 300,
            height: 25,
            required:true,
            increment:5,
            min: 10,
            max: 100
        });
    });

    window.setTimeout(function () {
        $('.ke-upload-area').css('width', '100px');
    }, 2000);

    var updataUrl = '/admin/gamecfg/updateset.do?token=${pageToken}';

    function subWxNewsFrm() {
        submitFrm('wxNewsFrm',
                updataUrl,
                '更新游戏微信消息成功.', '更新游戏微信消息失败');
    }

    function subGsImgFrm() {
        $('#gsImgFrm').form('disableValidation');
        submitFrm('gsImgFrm',
                updataUrl,
                '更新游戏默认图片成功.', '更新游戏默认图片失败');
        /*$.messager.progress();
        $('#gsImgFrm').form('submit',{
            url: '/nubywx'+updataUrl,
            onSubmit: function () {
                return true;
            },
            success: function(data) {
                $.messager.progress('close');
                var data = eval('(' + data + ')');
                if(data.ok && data.errCode == 0) {
                    showMsg('操作成功', '更新游戏默认图片成功.');
                } else {
                    showMsg('操作失败', '更新游戏默认图片失败,错误代码:'+data.errCode+'.');
                }
            }
        });*/
    }

    function subGsTimeFrm() {
        submitFrm('gsTimeFrm',
                updataUrl,
                '更新游戏通关时间成功.', '更新游戏通关时间失败');
    }
</script>
