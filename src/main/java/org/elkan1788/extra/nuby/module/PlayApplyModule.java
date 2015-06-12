package org.elkan1788.extra.nuby.module;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.SHA1;
import org.elkan1788.extra.nuby.bean.*;
import org.elkan1788.extra.nuby.service.*;
import org.elkan1788.extra.nuby.weixin.WxWEBOauth;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 用户玩游戏与活动登记模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 */
@IocBean(create = "init")
@At("/")
public class PlayApplyModule extends BaseModule {

    private static final Log log = Logs.get();

    @Inject
    private MPAct mp;
    @Inject
    private WxApi wxapi;

    @Inject
    private SettingsService setService;
    @Inject
    private FollowersService flwService;
    @Inject
    private GamelogService gamelogService;
    @Inject
    private GameSettingService gameSetService;
    @Inject
    private BabyimgService babyimgService;
    @Inject
    private SharelogService sharelogService;
    @Inject
    private TrailService trailService;
    @Inject
    private TraillogService traillogService;
    @Inject
    private PropertiesProxy conf;

    private static ExecutorService pool = Executors.newFixedThreadPool(6);

    private String qnImgUrl;

    public void init() {
        qnImgUrl = conf.get("bucket_cname")+"/%1$s?"+conf.get("game_fop_style");
    }

    @At("/webchat/callback")
    @Ok(">>:${obj}")
    public String callback(@Param("code") String oauthCode, @Param("state") String state) {
        String path = "/static/html/sub.html";
        if (!Lang.isEmpty(oauthCode)
                && !Lang.equals("authdeny", oauthCode)) {
            // 获取用户信息
            String appId = mp.getAppId();
            String secret = mp.getAppSecret();
            String openId = null;
            try {
                WxWEBOauth oauth = new WxWEBOauth();
                openId = oauth.getOpenId(appId, secret, oauthCode);
            } catch (Exception e) {
                log.errorf("微信网页[%s]授权获取用户OPENID信息异常!!!", state);
                log.error(e.getLocalizedMessage());
            }

            if (null != openId) {
                Followers flw = flwService.get(openId);
                // 必须是关注用户
                if (null != flw && flw.getUserStatus().equals("1")){
                    switch (state) {
                        case "applyTrail":
                            path = "/traillog/apply.do?openid="+openId;
                            break;
                        case "rankings" :
                            path = "/games/rankings.do?openid=" + openId + "&token=" + R.UU16();
                            break;
                        default:
                            path = "/games/puzzle.do?openid=" + state + "&token=" + R.UU16() + "&player=" + openId;
                            break;
                    }
                }
            }

        } else if (Lang.equals(state, "applyTrail")){
            path = "/static/html/trailsub.html";
        }

        return path;
    }

    @At("/games/puzzle")
    @Ok("fm:/WEB-INF/views/games/puzzle.html")
    @Fail(">>:/static/html/sub.html")
    public void puzzle(@Param("openid") final String openid,
                       @Param("token")String token,
                       @Param("player") final String player,
                       final HttpServletRequest req) {

        // TODO 微信的URL验证坑，参数顺序都不能乱的
        String url = req.getRequestURL().toString()+"?openid="+openid+"&token="+token;
        req.setAttribute("url", url);
        // 计算JSSDK SIGN
        Future jsFu = pool.submit(new Runnable() {
            @Override
            public void run() {
                String url = (String) req.getAttribute("url");
                if (!Lang.isEmpty(player)) {
                    url += "&player=" + player;
                    // 检查玩家是否已经关注
                    Followers flw = flwService.get(player);
                    if (!Lang.equals(flw.getFollowerId(), 0)
                            && Lang.equals("1", flw.getUserStatus())) {
                        req.setAttribute("sub", 1);
                    } else {
                        req.setAttribute("sub", 0);
                    }
                } else {
                    // 默认是公众号进入游戏
                    req.setAttribute("sub", 1);
                }

                String ticket = mp.getJsTicket();
                String timestamp = System.currentTimeMillis() / 1000 + "";
                String nonce = R.UU16();

                req.setAttribute("timestamp", timestamp);
                req.setAttribute("nonce", nonce);

                log.infof("ticket:%s, timestamp:%s, nonce:%s, url:%s",
                        ticket, timestamp, nonce, url);
                try {
                    String sign_str = "jsapi_ticket=" + ticket +
                            "&noncestr=" + nonce +
                            "&timestamp=" + timestamp +
                            "&url=" + url;
                    log.infof("sing_str: %s", sign_str);
                    String sign = SHA1.calculate(sign_str);
                    log.infof("signature: %s", sign);
                    req.setAttribute("signature", sign);
                } catch (AesException e) {
                    log.error("生成JSSDK签名失败!!!");
                }
            }
        });

        // 获取默认游戏设置
        Future gsFu = pool.submit(new Runnable() {
            @Override
            public void run() {
                Gamesettings gameSet = gameSetService.get();
                req.setAttribute("gameSet", gameSet);
            }
        });

        // 获取分享者宝宝照片
        Future ownerFu = pool.submit(new Runnable() {

            @Override
            public void run() {
                Babyimg ownerBaby = babyimgService.getUpSucBaby(openid);
                req.setAttribute("owner", ownerBaby);
            }
        });

        // 获取游戏者宝宝照片
        Future playerFu = null;
        if (!Lang.isEmpty(player)) {
            playerFu = pool.submit(new Runnable() {
                @Override
                public void run() {
                    Babyimg playerBaby = babyimgService.getUpSucBaby(player);
                    req.setAttribute("player", playerBaby);
                }
            });
        }

        // 计算排行榜
        Future rankFu = pool.submit(new Runnable() {
            @Override
            public void run() {
                List<Rankings> ranks = gamelogService.rankList();
                req.setAttribute("ranks", ranks);
            }
        });

        try {
            jsFu.get();
            ownerFu.get();
            if (null!=playerFu) {
                playerFu.get();
            }
            gsFu.get();
            rankFu.get();
        } catch (Exception e) {
            log.errorf("[%s-%s]等待所有线程完成时异常!!!", token, openid);
            log.error(e.getLocalizedMessage());
        }

        Gamesettings gameSet = (Gamesettings) req.getAttribute("gameSet");
        Babyimg ownerBaby = (Babyimg) req.getAttribute("owner");
        Babyimg playerBaby = (Babyimg) req.getAttribute("player");

        req.setAttribute("babyName", "");

        if (null != ownerBaby) {
            if (!Lang.isEmpty(ownerBaby.getImage1())){
                gameSet.setImage1(ownerBaby.getImage1());
            }
            if (!Lang.isEmpty(ownerBaby.getImage2())){
                gameSet.setImage3(ownerBaby.getImage2());
            }
        }

        if (null != playerBaby) {
            if (!Lang.isEmpty(playerBaby.getBabyName())) {
                req.setAttribute("babyName", playerBaby.getBabyName());
            }
        } else if (null != ownerBaby && null == player){
            // 用户玩自己的游戏
            req.setAttribute("babyName", ownerBaby.getBabyName());
        }

        // 公众号信息相关
        String cname = req.getScheme()+"://"+req.getServerName();
        req.setAttribute("cname", cname);
        req.setAttribute("mpid", mp.getMpId());
        req.setAttribute("appid", mp.getAppId());
        req.setAttribute("openid", openid);
        req.setAttribute("player", player);
        createToken16();
        req.setAttribute("pageToken", this.pageToken);

        // 分享回调链接
        String shareurl = cname + req.getContextPath()
                +"/webchat/callback.do";
        req.setAttribute("shareurl", shareurl);

        // 七牛云服务域名
        req.setAttribute("qnCname", conf.get("bucket_cname"));
        // 刷新PAGE_TOKEN防止页面缓存
        req.setAttribute("refreshPageToken", R.UU16());
    }

    @POST
    @At("/games/upload")
    public AjaxReturn upload(@Param(value = "level", df = "1") int level,
                             @Param("name") String babyName,
                             @Param("openid")String openid,
                             @Param("serverid")String mediaId) {
        log.infof("level: %d, name: %s, openid: %s, serverid: %s",
                level, babyName, openid, mediaId);
        initSuc();
        Babyimg bi = new Babyimg();
        bi.setBabyName(babyName);
        bi.setOpenid(openid);
        String imgName = String.format(qnImgUrl, mediaId + ".jpg");
        if (level == 1){
            bi.setImage1(imgName);
            bi.setMediaId1(mediaId);
        } else {
            bi.setImage2(imgName);
            bi.setMediaId2(mediaId);
        }

        babyimgService.uploadImg(bi, level);

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At("/games/playlog")
    public Object playlog(@Param("openid")String openId, @Param("token")String token,
                          @Param("type")String phoneType, @Param("level")Integer level,
                          @Param("time")Integer time, @Param("pass")String pass) {

        initSuc();

        gamelogService.updatePlayLog(openId, token, phoneType, level, time, pass);

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At("/games/sharelog")
    public AjaxReturn sharelog(@Param("openid")String openid, @Param("type")Integer type) {

        initSuc();

        sharelogService.addShareLog(openid, type);

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @At("/games/babyfall")
    @Ok("jsp:views.games.waterfall")
    public void  babayfall(@Param("token")String token, HttpServletRequest req) {

        String ticket = mp.getJsTicket();
        String timestamp = System.currentTimeMillis()/1000+"";
        String nonce = R.UU16();
        String url = req.getRequestURL().toString()+"?token="+token;
        log.infof("ticket:%s, timestamp:%s, nonce:%s, url:%s",
                ticket, timestamp, nonce, url);
        try {
            String sign_str  = "jsapi_ticket=" + ticket +
                    "&noncestr=" + nonce +
                    "&timestamp=" + timestamp +
                    "&url=" + url;
            log.infof("sing_str: %s", sign_str);
            String signature = SHA1.calculate(sign_str);
            log.infof("signature: %s", signature);
            req.setAttribute("signature", signature);
        } catch (AesException e) {
            Lang.wrapThrow(e, "生成JSSDK签名失败!!!");
        }
        req.setAttribute("timestamp", timestamp);
        req.setAttribute("nonce", nonce);

        // 公众号信息相关
        req.setAttribute("appid", mp.getAppId());
    }

    @At("/games/babyimgs")
    @Ok("ioc:json")
    public Object babyimgs(@Param("num")Integer num) {
        List<ImageSrc> imgs = babyimgService.getImgs(num);
        return imgs;
    }

    /**
     * 活动申请页面
     */
    @At("/traillog/apply")
    @Ok("jsp:views.games.trail")
    public void apply(@Param("openid") String openid, final HttpServletRequest req) {

        //TODO 有待改进
        Future trailFu = pool.submit(new Runnable() {

            @Override
            public void run() {
                Trail trail =  trailService.get("valid", "1");
                req.setAttribute("trail", trail);
            }
        });

        final String url = req.getRequestURL().toString()+"?openid="+openid;
        Future jsFu = pool.submit(new Runnable() {
            @Override
            public void run() {

                String ticket = mp.getJsTicket();
                String timestamp = System.currentTimeMillis() / 1000 + "";
                String nonce = R.UU16();

                req.setAttribute("timestamp", timestamp);
                req.setAttribute("nonce", nonce);

                log.infof("ticket:%s, timestamp:%s, nonce:%s, url:%s",
                        ticket, timestamp, nonce, url);
                try {
                    String sign_str = "jsapi_ticket=" + ticket +
                            "&noncestr=" + nonce +
                            "&timestamp=" + timestamp +
                            "&url=" + url;
                    log.infof("sing_str: %s", sign_str);
                    String sign = SHA1.calculate(sign_str);
                    log.infof("signature: %s", sign);
                    req.setAttribute("signature", sign);
                } catch (AesException e) {
                    log.error("生成JSSDK签名失败!!!");
                    log.error(e.getLocalizedMessage());
                }
            }
        });

        // 分享地址
        String shareurl = req.getScheme()+"://"+req.getServerName()
                + req.getContextPath() +"/webchat/callback.do";
        req.setAttribute("shareurl", shareurl);
        log.infof("shareurl: %s", shareurl);
        req.setAttribute("appid", mp.getAppId());
        req.setAttribute("openid", openid);
        req.setAttribute("refreshPageToken", R.UU16());

        try {
            trailFu.get();
            jsFu.get();
        } catch (Exception e) {
            log.errorf("[%s]等待所有线程完成时异常!!!", openid);
            log.error(e.getLocalizedMessage());
        }
    }

    @POST
    @At("/traillog/register")
    public Object register(@Param("..")Traillog traillog) {

        int result = traillogService.add(traillog);
        return Ajax.ok().setErrCode(""+ result);
    }

    @At("/games/oauthranks")
    @Ok(">>:${obj}")
    public String oauthranks(@Param("code") String oauthCode,
                            @Param("state") String state) {

        String path = "/static/html/sub.html";

        if (!Lang.isEmpty(oauthCode)
                && !Lang.equals("authdeny", oauthCode)) {
            String[] states = state.split(";");
            String appId = states[0];
            String secret = states[1];
            WxWEBOauth oauth = new WxWEBOauth();
            String openId = oauth.getOpenId(appId, secret, oauthCode);
            path = "/games/rankings.do?openid=" + openId + "&token=" + R.UU16();
        }

        return path;
    }

    @At("/games/rankings")
    @Ok("jsp:views.games.ranks")
    public void rankings(@Param("openid")String openid,
                         @Param("token")String token, HttpServletRequest req) {

        req.setAttribute("openid", openid);
        req.setAttribute("pageToken", token);
        req.setAttribute("ranks", gamelogService.rankList());
    }
}