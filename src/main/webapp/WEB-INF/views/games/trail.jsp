<%--
  试用活动
  User: Senhui
  Date: 4/29/2015
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Nuby努比</title>
    <style>
        * {
            outline: none;
        }
        body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            background: #F4D11F;
            text-align: center;
            overflow: hidden;
        }

        #loadMask {
             width:100%;
             height:100%;
             background-color:#fff;
             filter:alpha(opacity=98);
             -moz-opacity:0.98;
             opacity:0.98;
             position:fixed;
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

        .trail_img {
            width: 100%;
            max-width: 640px;
            max-height: 780px;
            border: 0;
        }

        .trail_content {
            width: 92%;
            margin-left: 5%;
            margin-right: 5%;
            text-align: left;
        }

        .trail_introduce {
            font-family: Microsoft YaHei, Helvitica, Verdana, Tohoma, Arial, san-serif;
            font-size: 14px;
            margin-top: 18px;
            margin-bottom: 12px;
        }

        .trail_log p {
            margin: 8px;
        }

        .trail_log_input {
            width: 80%;
            height: 28px;
            line-height: 15px;
            padding-left: 8px;
            border-radius: 16px;
            border: 2px solid #E60013;
            box-shadow:0 0 6px #bbb;
            outline: none;
        }

        select {
            width: 30%;
            padding: 4px;
            border-radius:16px;
            border: 2px solid #E60013;
            box-shadow:0 0 6px #bbb;
            background: #f8f8f8;
            color:#888;
            outline:none;
            display: inline-block;
            -webkit-appearance:none;
            appearance:none;
            cursor:pointer;

        }

        .sel_label {
            position:relative;
        }

        .sel_label:after {
            content:'<>';
            font:12px "Consolas", monospace;
            font-weight: bold;
            color:#aaa;
            -webkit-transform:rotate(90deg);
            transform:rotate(90deg);
            right:11px;
            top:3px;
            padding:0 0 2px;
            border-bottom:1px solid #ddd;
            position:absolute;
            pointer-events:none;
            color: #E60013;
        }

        label:before {
            content:'';
            right:11px;
            top:3px;
            width:16px;
            height:16px;
            background:#f8f8f8;
            position:absolute;
            pointer-events:none;
            display:block;
        }

        .trail_btn {
            width: 110px;
            height: 34px;
            margin-top: 10px;
            padding:8px 26px 8px 26px;
            border: 0px;
            border-radius: 8px;
            background-color: #E60013;
            outline: none;
            font-size: 1.06rem;
            color: #fff;
            text-decoration: none;
        }
    </style>

    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
        var shareData = {
            title: '${trail.trailName}',
            desc: '${trail.trailName}',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri='+encodeURIComponent('${shareurl}')+'&response_type=code&scope=snsapi_base&state=applyTrail#wechat_redirect',
            imgUrl: '${trail.trailImg}'
        }
        wx.config({
            debug: false,
            appId: '${appid}',
            timestamp: ${timestamp},
            nonceStr: '${nonce}',
            signature:'${signature}',
            jsApiList:[
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'hideMenuItems',
                'showMenuItems',
                'hideAllNonBaseMenuItem',
                'showAllNonBaseMenuItem',
                'chooseImage',
                'previewImage',
                'uploadImage',
                'getNetworkType',
                'hideOptionMenu',
                'showOptionMenu']
        });

        wx.ready(function () {
            wx.checkJsApi({
                jsApiList: ['getNetworkType'],
                success: function (res) {
                    $('#loadMask').hide();
                }
            });

            wx.getNetworkType({
                success: function (res) {
                    var nt = res.networkType;
                    if (nt!='wifi'){
                        alert('亲,您当前用的不是WIFI环境,加载较缓慢请稍后.');
                    }
                }
            });

            regShareEvent();
        });

        wx.error(function(res) {
            $('#loadMask').show();
            alert('亲,当前申请人数过多,请稍后再试.');
            window.top.location.href = '${base}/traillog/apply.do?openid=${openid}&token=${refreshPageToken}';
        });

        function regShareEvent() {
            wx.onMenuShareTimeline({
                title: shareData.title,
                link: shareData.link,
                imgUrl: shareData.imgUrl,
                success: function () {
                },
                cancel: function () {
                }
            });
            wx.onMenuShareAppMessage({
                title: shareData.title,
                desc: shareData.desc,
                link: shareData.link,
                imgUrl: shareData.imgUrl,
                success: function () {

                },
                cancel: function () {

                }
            });
            wx.onMenuShareQQ({
                title: shareData.title,
                desc: shareData.desc,
                link: shareData.link,
                imgUrl: shareData.imgUrl,
                success: function () {

                },
                cancel: function () {

                }
            });
            wx.onMenuShareWeibo({
                title: shareData.title,
                desc: shareData.desc,
                link: shareData.link,
                imgUrl: shareData.imgUrl,
                success: function () {

                },
                cancel: function () {

                }
            });
        }
    </script>
    <script>
        var cities = new Object();

        cities['北京']=new Array('北京');

        cities['上海']=new Array('上海');

        cities['天津']=new Array('天津');

        cities['重庆']=new Array('重庆');

        cities['河北省']=new Array('石家庄', '张家口', '承德', '秦皇岛', '唐山', '廊坊', '保定', '沧州', '衡水', '邢台', '邯郸');

        cities['山西省']=new Array('太原', '大同', '朔州', '阳泉', '长治', '晋城', '忻州', '吕梁', '晋中', '临汾', '运城');

        cities['辽宁省']=new Array('沈阳', '朝阳', '阜新', '铁岭', '抚顺', '本溪', '辽阳', '鞍山', '丹东', '大连', '营口', '盘锦', '锦州', '葫芦岛');

        cities['吉林省']=new Array('长春', '白城', '松原', '吉林', '四平', '辽源', '通化', '白山', '延边');

        cities['黑龙江省']=new Array('哈尔滨', '齐齐哈尔', '黑河', '大庆', '伊春', '鹤岗', '佳木斯', '双鸭山', '七台河', '鸡西', '牡丹江', '绥化', '大兴安');

        cities['江苏省']=new Array('南京', '徐州', '连云港', '宿迁', '淮阴', '盐城', '扬州', '泰州', '南通', '镇江', '常州', '无锡', '苏州');

        cities['浙江省']=new Array('杭州', '湖州', '嘉兴', '舟山', '宁波', '绍兴', '金华', '台州', '温州', '丽水');

        cities['安徽省']=new Array('合肥', '宿州', '淮北', '阜阳', '蚌埠', '淮南', '滁州', '马鞍山', '芜湖', '铜陵', '安庆', '黄山', '六安', '巢湖', '池州', '宣城');

        cities['福建省']=new Array('福州', '南平', '三明', '莆田', '泉州', '厦门', '漳州', '龙岩', '宁德');

        cities['江西省']=new Array('南昌', '九江', '景德镇', '鹰潭', '新余', '萍乡', '赣州', '上饶', '抚州', '宜春', '吉安');

        cities['山东省']=new Array('济南', '聊城', '德州', '东营', '淄博', '潍坊', '烟台', '威海', '青岛', '日照', '临沂', '枣庄', '济宁', '泰安', '莱芜', '滨州', '菏泽');

        cities['河南省']=new Array('郑州', '三门峡', '洛阳', '焦作', '新乡', '鹤壁', '安阳', '濮阳', '开封', '商丘', '许昌', '漯河', '平顶山', '南阳', '信阳', '周口', '驻马店');

        cities['湖北省']=new Array('武汉', '十堰', '襄攀', '荆门', '孝感', '黄冈', '鄂州', '黄石', '咸宁', '荆州', '宜昌', '恩施', '襄樊');

        cities['湖南省']=new Array('长沙', '张家界', '常德', '益阳', '岳阳', '株洲', '湘潭', '衡阳', '郴州', '永州', '邵阳', '怀化', '娄底', '湘西');

        cities['广东省']=new Array('广州', '清远', '韶关', '河源', '梅州', '潮州', '汕头', '揭阳', '汕尾', '惠州', '东莞', '深圳', '珠海', '江门', '佛山', '肇庆', '云浮', '阳江', '茂名', '湛江');

        cities['海南省']=new Array('海口', '三亚');

        cities['四川省']=new Array('成都', '广元', '绵阳', '德阳', '南充', '广安', '遂宁', '内江', '乐山', '自贡', '泸州', '宜宾', '攀枝花', '巴中', '达川', '资阳', '眉山', '雅安', '阿坝', '甘孜', '凉山');

        cities['贵州省']=new Array('贵阳', '六盘水', '遵义', '毕节', '铜仁', '安顺', '黔东南', '黔南', '黔西南');

        cities['云南省']=new Array('昆明', '曲靖', '玉溪', '丽江', '昭通', '思茅', '临沧', '保山', '德宏', '怒江', '迪庆', '大理', '楚雄', '红河', '文山', '西双版纳');

        cities['陕西省']=new Array('西安', '延安', '铜川', '渭南', '咸阳', '宝鸡', '汉中', '榆林', '商洛', '安康');

        cities['甘肃省']=new Array('兰州', '嘉峪关', '金昌', '白银', '天水', '酒泉', '张掖', '武威', '庆阳', '平凉', '定西', '陇南', '临夏', '甘南');

        cities['青海省']=new Array('西宁', '海东', '西宁', '海北', '海南', '黄南', '果洛', '玉树', '海西');

        cities['内蒙古']=new Array('呼和浩特', '包头', '乌海', '赤峰', '呼伦贝尔盟', '兴安盟', '哲里木盟', '锡林郭勒盟', '乌兰察布盟', '鄂尔多斯', '巴彦淖尔盟', '阿拉善盟');

        cities['广西']=new Array('南宁', '桂林', '柳州', '梧州', '贵港', '玉林', '钦州', '北海', '防城港', '南宁', '百色', '河池', '柳州', '贺州');

        cities['西藏']=new Array('拉萨', '那曲', '昌都', '林芝', '山南', '日喀则', '阿里');

        cities['宁夏']=new Array('银川', '石嘴山', '吴忠', '固原');

        cities['新疆']=new Array('乌鲁木齐', '克拉玛依', '喀什', '阿克苏', '和田', '吐鲁番', '哈密', '博尔塔拉', '昌吉', '巴音郭楞', '伊犁', '塔城', '阿勒泰');

        cities['香港']=new Array('香港');

        cities['澳门']=new Array('澳门');

        function set_city(province, city)  {

            var pv, cv;

            var i, ii;

            pv=province.value;

            cv=city.value;

            city.length=1;

            if(pv=='0') return;

            if(typeof(cities[pv])=='undefined') return;

            for(i=0; i<cities[pv].length; i++)

            {

                ii = i+1;

                city.options[ii] = new Option();

                city.options[ii].text = cities[pv][i];

                city.options[ii].value = cities[pv][i];

            }

        }
    </script>
</head>
<body>
    <div style="display:none">
        <img src="https://mmbiz.qlogo.cn/mmbiz/qkCXJyugFJias5L2PySbG47CYTzgg6rYhUCBM2OiaRx6Z2M8m2raKtZiao7O2Cwytnyt6ttJvX7OjJXrRK7AkTuhQ/0"/>
    </div>
    <div id="loadMask">
        <img src="${ctx}/static/img/pub/loading.gif" alt="加载中..."/>
    </div>
    <img src="${trail.trailImg}" class="trail_img"/>
    <div class="trail_content">
        <div class="trail_introduce">
            ${trail.trailContent}
        </div>
        <form id="traillogFrm" onsubmit="return false;">
        <div class="trail_log">
            <p>
                <span>姓名：</span>
                <input type="text" id="name" class="trail_log_input" placeholder="用户名"/>
            </p>
            <p>
                <span>省份：</span>
                <label class="sel_label">
                <select id="province" onchange="set_city(this, document.getElementById('city'));">
                    <option value=0>不限</option>
                    <option value=北京>北京</option>
                    <option value=上海>上海</option>
                    <option value=天津>天津</option>
                    <option value=重庆>重庆</option>
                    <option value=河北省>河北省</option>
                    <option value=山西省>山西省</option>
                    <option value=辽宁省>辽宁省</option>
                    <option value=吉林省>吉林省</option>
                    <option value=黑龙江省>黑龙江省</option>
                    <option value=江苏省>江苏省</option>
                    <option value=浙江省>浙江省</option>
                    <option value=安徽省>安徽省</option>
                    <option value=福建省>福建省</option>
                    <option value=江西省>江西省</option>
                    <option value=山东省>山东省</option>
                    <option value=河南省>河南省</option>
                    <option value=湖北省>湖北省</option>
                    <option value=湖南省>湖南省</option>
                    <option value=广东省>广东省</option>
                    <option value=海南省>海南省</option>
                    <option value=四川省>四川省</option>
                    <option value=贵州省>贵州省</option>
                    <option value=云南省>云南省</option>
                    <option value=陕西省>陕西省</option>
                    <option value=甘肃省>甘肃省</option>
                    <option value=青海省>青海省</option>
                    <option value=内蒙古>内蒙古</option>
                    <option value=广西>广西</option>
                    <option value=西藏>西藏</option>
                    <option value=宁夏>宁夏</option>
                    <option value=新疆>新疆</option>
                    <option value=香港>香港</option>
                    <option value=澳门>澳门</option>
                </select>
                </label>
                <span>城市：</span>
                <label class="sel_label">
                <select id="city">
                    <option value=0>不限</option>
                </select>
                </label>
            </p>
            <p>
                <span>地址：</span>
                <input type="text" id="address" class="trail_log_input" placeholder="通讯地址"/>
            </p>
            <p>
                <span>邮编：</span>
                <input type="text" id="zipcode" class="trail_log_input" placeholder="邮编"/>
            </p>
            <p>
                <span>手机：</span>
                <input type="text" id="phonenum" class="trail_log_input" placeholder="请填写您的手机号码"/>
            </p>
            <p align="center" style="padding-top: 16px">
                <a href="#" class="trail_btn">确 定</a>
            </p>
            <div style="height: 30px;">&nbsp;</div>
        </div>
        </form>
    </div>
    <script src="${ctx}/static/vender/zepto.min.js"></script>
    <script>
        $(function(){

            $('.trail_btn').click(function(){
                $.ajax({
                    type: 'POST',
                    url: '${ctx}/traillog/register.do',
                    dataType: 'json',
                    data: {
                        username: $('#name').val(),
                        address: $('#address').val(),
                        zipcode: $('#zipcode').val(),
                        phonenum: $('#phonenum').val(),
                        province: $('#province').val(),
                        city: $('#city').val(),
                        trailId: '${trail.trailId}',
                        openid: '${openid}'
                    },
                    beforeSend: checkForm,
                    success: function(data){
                        $('#loadMask').hide();
                        if(data.ok){
                           var code = data.errCode;
                            if (code == -6) {
                                alert('您的信息已经登记过了，请勿重复申请.');
                                return;
                            }

                            if (code == 0) {
                                alert('感谢您的参与，您的申请已登记，申请结果敬请关注努比官方微信公众号.');
                            } else {
                                alert('当前申请人数过多，请稍后再试.');
                            }
                        }
                    }
                });
            });
        });

        function checkForm() {
            var name = $('#name').val();
            if (name.length < 2) {
                alert('请输入您的姓名.');
                $('#name').focus();
                return false;
            }

            var city = $('#city').val();
            if(city == '0') {
                alert('请选择您所在的城市.');
                $('#province').focus();
                return false;
            }

            var addr = $('#address').val();
            if (addr.length < 1) {
                alert('请填写您的具体通讯地址.');
                $('#address').focus();
                return false;
            }

            var phone = $('#phonenum').val();
            if ( !(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/g.test(phone))) {
                alert('请填写您的手机号码.');
                $('#phonenum').focus();
                return false;
            }

            $('#loadMask').show();
            return true;
        }
    </script>
</body>
</html>
