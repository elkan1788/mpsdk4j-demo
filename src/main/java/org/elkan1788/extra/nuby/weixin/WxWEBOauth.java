package org.elkan1788.extra.nuby.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elkan1788.osc.weixin.mp.commons.WxApiUrl;
import org.elkan1788.osc.weixin.mp.util.SimpleHttpReq;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.IOException;

/**
 * 微信网页授权
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/06/11
 */
public class WxWEBOauth {

    protected static final Log log = Logs.get();

    /**
     * 取得网页授权生成的ACCESS_TOKEN
     * @param appid     公众号APPID
     * @param secret    公众号SECRET
     * @param code      网页授权码
     * @return  JSON对象
     */
    protected JSONObject getAccessToken(String appid, String secret, String code) {

        String oauthUrl = "https://api.weixin.qq.com" + String.format(WxApiUrl.OAUTH_TOKEN, appid, secret, code);
        try {
            String result =  SimpleHttpReq.get(oauthUrl);
            log.infof("授权回调信息: ", result);
            return JSON.parseObject(result);
        } catch (IOException e) {
            throw Lang.wrapThrow(e, "获取网页授权ACCESS_TOKEN异常!!!");
        }
    }

    /**
     * 取得用户的ID
     * @param appid     公众号APPID
     * @param secret    公众号SECRET
     * @param code      网页授权码
     * @return  用户ID
     */
    public String getOpenId(String appid, String secret, String code) {
        JSONObject oauth_result = getAccessToken(appid, secret, code);
        if (null != oauth_result) {
            return oauth_result.getString("openid");
        }
        return null;
    }
}
