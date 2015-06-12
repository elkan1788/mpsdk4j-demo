package org.elkan1788.extra.nuby.weixin;

import org.elkan1788.extra.nuby.bean.Coupon;
import org.elkan1788.extra.nuby.bean.Couponlog;
import org.elkan1788.extra.nuby.bean.Gamesettings;
import org.elkan1788.extra.nuby.bean.Menulog;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.*;
import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.vo.Article;
import org.elkan1788.osc.weixin.mp.vo.Follower;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信高级接口功能
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/25
 * @version 1.0.10
 */
@IocBean(name = "wxService")
public class NubyWxService {

    private static final Log log = Logs.get();

    @Inject
    private MPAct mp;
    @Inject
    private WxApi wxapi;

    @Inject
    private FollowersService flwService;
    @Inject
    private GameSettingService gameSetService;
    @Inject
    private MenulogService mlogService;
    @Inject
    private MenuService mService;
    @Inject
    private CouponService couponService;
    @Inject
    private CouponlogService couponlogService;
    @Inject
    private SettingsService setService;

    @Inject
    private PropertiesProxy conf;

    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    /**
     * 创建游戏图文消息
     * @param openid    用户微信ID
     */
    public List<Article> buildGameArt(String openid) {

        Gamesettings gs = gameSetService.get();
        Article article = new Article();
        article.setTitle(gs.getNewsTitle());
        article.setDescription(gs.getWxNews());
        article.setPicUrl(gs.getNewsImg());
        String url = gs.getNewsLink()+"?openid="+openid+"&token="+ R.UU16();
        article.setUrl(url);
        return Arrays.asList(article);
    }

    /**
     * 创建育宝分享图文消息
     */
    public List<Article> bulidBabyShareArt() {

        String picUrl = conf.get("bucket_cname")+"/%1$s?"+conf.get("news_fop_style");

        Article art1 = new Article();
        art1.setTitle("看看奥巴马夫妇是如何带娃的，珍藏版");
        art1.setPicUrl(String.format(picUrl, "001.jpg"));
        art1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDg1OTY4NA==&mid=205157646&idx=1&sn=e271d5c32256fba271c2702169c9add3#rd");

        Article art2 = new Article();
        art2.setTitle("从亲子游戏挖掘宝宝语言天赋，麻麻必藏");
        art2.setPicUrl(String.format(picUrl, "002.jpg"));
        art2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDg1OTY4NA==&mid=205157646&idx=2&sn=6cb815852df046d0bb6959ef7c3648b5#rd");

        Article art3 = new Article();
        art3.setTitle("你的赞美式教育真的起效了吗？");
        art3.setPicUrl(String.format(picUrl, "003.jpg"));
        art3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDg1OTY4NA==&mid=205157646&idx=3&sn=3b2046fbafce9c91ee452c0f2f387c28#rd");

        Article art4 = new Article();
        art4.setTitle("十二星座爸爸，快来对号入座吧？超准哦~~");
        art4.setPicUrl(String.format(picUrl, "004.jpg"));
        art4.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5NDg1OTY4NA==&mid=205157646&idx=4&sn=ef0f63285b39723c8b7d5bc7c127a3a6#rd");

        return Arrays.asList(art1, art2, art3, art4);
    }

    /**
     * 用户订阅事件处理
     * @param sub       订阅状态 1 关注 0 退订
     * @param openid    用户微信ID
     * @return
     */
    public String followerSubEvent(int sub, String openid) {

        String content = "";
        Follower follower = new Follower();
        if (sub==1) {
            content = setService.getWelCon();
            log.info("微信用户信息同步开始...");
            try {
                follower =  wxapi.getFollower(openid, "");
                log.infof("微信粉丝[%s]信息抓取成功...", openid);
                log.info(follower);
            } catch (WxRespException e) {
                throw Lang.wrapThrow(e, "获取微信粉丝[%s]信息失败!!!", openid);
            }
            if (!Lang.isEmpty(follower.getNickName())) {
               content = follower.getNickName() + "， " + content;
            }
        } else {
            follower.setOpenId(openid);
            follower.setSubscribe(0);
        }

        pool.execute(new FollowerInfoSyncThread(follower));

        return content;
    }

    /**
     * 记录菜单点击
     * @param menuKey   菜单KEY
     * @param openid    用户微信ID
     */
    public void menuClickLog(String menuKey, String openid) {
        pool.execute(new MenuLogThread(menuKey, openid));
    }

    /**
     * 发送优惠券通知消息
     * @param openId 用户微信ID
     */
    public void sendCoupon(String openId) {
        OutPutMsg om = new OutPutMsg();
        om.setFromUserName(mp.getMpId());
        om.setToUserName(openId);
        Coupon coupon = couponService.get();
        log.info("优惠券信息....");
        log.info(coupon);
        // 已经发送的用户直接跳过
        boolean isSend = couponlogService.isSend(coupon.getConId(), openId);
        if (isSend) {
            log.infof("用户[%s]已经获得优惠券...", openId);
            return;
        }
        if (null != coupon) {
            int surplus = coupon.getConNum() - coupon.getSendNum();

            if (surplus > 0) {
                // 发放奖品
                om.setMsgType(WxMsgType.news.name());
                Article art = new Article();
                art.setTitle(String.format(Constant.PRIZE_PREFIX, coupon.getConName()));
                art.setDescription(coupon.getConDesc());
                art.setPicUrl(coupon.getConThumb().trim());
                art.setUrl(coupon.getConLink().trim());
                om.setArticles(Arrays.asList(art));

                pool.execute(new UpdateCouponThread(coupon));
                pool.execute(new UpdateCouponLogThread(openId, coupon.getConId()));
            } else {
                om.setMsgType(WxMsgType.text.name());
                om.setContent(Constant.NO_PRIZE_TIP);
            }

            pool.execute(new SendCustomMsgThread(om));
        }
    }



    /**
     * 用户订阅时的同步抓取
     */
    public class FollowerInfoSyncThread implements Runnable {

        private Follower follower;

        public FollowerInfoSyncThread(Follower follower) {
            this.follower = follower;
        }


        @Override
        public void run() {
            if (null!=follower) {
                log.info("异步存储用户信息...");
                flwService.update(follower, false);
            }
        }
    }

    /**
     * 新增菜单点击记录
     */
    class MenuLogThread implements Runnable {

        private String menuKey;
        private String openId;

        public MenuLogThread(String menuKey, String openId) {
            this.menuKey = menuKey;
            this.openId = openId;
        }

        @Override
        public void run() {
            Integer menu_id = mService.getMenuId(menuKey);
            if (!Lang.equals(menu_id, 0)) {
                Menulog ml = new Menulog();
                ml.setMenuId(menu_id);
                ml.setOpenid(openId);
                mlogService.addLog(ml);
            }
        }
    }

    /**
     * 发送微信优惠券通知消息
     */
    class SendCustomMsgThread implements Runnable {

        private OutPutMsg om;

        public SendCustomMsgThread(OutPutMsg om) {
            this.om = om;
        }

        @Override
        public void run() {
            try {
                boolean result = wxapi.sendCustomerMsg(om);
                if (!result) {
                    log.errorf("发送给用户[%s]的优惠券消息发送失败!!!", om.getToUserName());
                    log.error(om);
                }
            } catch (WxRespException e) {
                Lang.wrapThrow(e, "发送给用户[%s]的优惠券消息异常!!!", om.getToUserName());
                log.error(om);
            }
        }
    }

    /**
     * 更新优惠券信息
     */
    class UpdateCouponThread implements Runnable {

        private Coupon coupon;

        public UpdateCouponThread(Coupon coupon) {
            this.coupon = coupon;
        }

        @Override
        public void run() {
            synchronized (couponService){
                int curNum = coupon.getSendNum()+1;
                if (curNum > coupon.getConNum()) {
                    coupon.setSendNum(coupon.getConNum());
                    coupon.setValid("0");
                } else {
                    coupon.setSendNum(coupon.getSendNum()+1);
                }

                couponService.update(coupon);
            }
        }
    }

    /**
     * 新增优惠券的发送记录
     */
    class UpdateCouponLogThread implements Runnable {

        private String openId;
        private Integer conId;

        public UpdateCouponLogThread(String openId, Integer conId) {
            this.openId = openId;
            this.conId = conId;
        }

        @Override
        public void run() {
            Couponlog cl = new Couponlog();
            cl.setOpenid(openId);
            cl.setConId(conId);
            cl.setType("1");
            couponlogService.add(cl);
        }
    }
}
