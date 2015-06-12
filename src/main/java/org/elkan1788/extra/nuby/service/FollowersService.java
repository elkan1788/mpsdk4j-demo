package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Followers;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.vo.FollowList;
import org.elkan1788.osc.weixin.mp.vo.Follower;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信公众号配置业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/19
 * @version 1.0.10
 */
@IocBean(name = "flwService",fields = {"dao"})
public class FollowersService extends BaseService<Followers> {

    private static final Log log = Logs.get();

    @Inject
    private WxApi wxapi;

    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public Followers get(String openId) {
        List<Followers> flws = dao().query(Followers.class, Cnd.where("openId", "=", openId));
        Followers flw = !Lang.isEmpty(flws) ? flws.get(0) : new Followers(0);
        if (Lang.equals(flw.getFollowerId(), 0)) {
            log.errorf("未找到微信用户[%s]的订阅信息!!!", openId);
        }
        return flw;
    }

    /**
     * 更新用户信息
     * @param follower  用户信息
     * @param sync      是否同步[同步即不重新抓取用户信息]
     */
    public void update(Follower follower, boolean sync) {

        Followers flw = get(follower.getOpenId());
        flw.setEncrypt(true);
        if (Lang.equals(flw.getFollowerId(), 0)){
            flw.setFollowerId(null);
            flw.setOpenid(follower.getOpenId());
            flw.setNickname(follower.getNickName());
            flw.setSex(follower.getSex() + "");
            flw.setCountry(follower.getCountry());
            flw.setProvince(follower.getProvince());
            flw.setCity(follower.getCity());
            flw.setHeadimgurl(follower.getHeadImgUrl());
            flw.setLanguage(follower.getLanguage());
            flw.setSubTime(new Date(follower.getSubscribeTime()*1000));
            flw.setUserStatus("1");
            try {
                flw = dao().insert(flw);
                log.infof("新增订阅用户信息成功--%d", flw.getFollowerId());
            } catch (Exception e) {
                Lang.wrapThrow(e, "新增订阅用户[%s]信息异常!!!", flw.getOpenid());
                flw.setNickname(flw.getOpenid());
                dao().insert(flw);
            }
        }

        else if (Lang.equals(follower.getSubscribe(), 1)){
            if (sync) {
                return;
            }
            flw.setOpenid(follower.getOpenId());
            flw.setNickname(follower.getNickName());
            flw.setSex(follower.getSex() + "");
            flw.setCountry(follower.getCountry());
            flw.setProvince(follower.getProvince());
            flw.setCity(follower.getCity());
            flw.setHeadimgurl(follower.getHeadImgUrl());
            flw.setLanguage(follower.getLanguage());
            flw.setSubTime(new Date(follower.getSubscribeTime()*1000));
            flw.setUserStatus("1");
            flw.setUnsubTime(null);
            int rows = dao().updateIgnoreNull(flw);
            if (rows < 1) {
                log.error("粉丝重新订阅失败!!!");
            }
        }

        else {
            // 微信用户昵称真是坑!!!
            flw.setNickname(flw.getNickname());
            flw.setUnsubTime(new Date());
            flw.setUserStatus("0");
            int rows = dao().updateIgnoreNull(flw);
            if (rows < 1) {
                log.error("粉丝取消订阅失败!!!");
            }
        }
    }

    public Map<String, Object> list(Integer curPage, Integer pageSize, Date st, Date et, String nickName) {

        Criteria cri = Cnd.cri();
        if (null != st && null != et) {
            cri.where().andBetween("subTime", st, et);
        }

        if (!Lang.isEmpty(nickName) && nickName.length()>1) {
            nickName = Base64.encodeToString(nickName.getBytes(), false);
            cri.where().andLike("nickName", nickName);
        }
        cri.getOrderBy().desc("subTime");

        Map<String, Object> map  = easyuiDGPager(curPage, pageSize, cri, false);
        return map;
    }

    public Integer syncFollowers() {

        FollowList fl = null;
        try {
            fl =  wxapi.getFollowerList("");
        } catch (WxRespException e) {
            throw Lang.wrapThrow(e, "获取公众号用户列表失败!!!");
        }

        if (null != fl) {
            log.infof("当前公众号已有粉丝数量为: %d", fl.getTotal());
            for (String openid : fl.getOpenIds()) {
                pool.execute(new GetFollowerInfoThread(openid, true));
            }
        }

        return fl.getTotal();
    }

    class GetFollowerInfoThread implements Runnable {

        private String openid;
        private boolean sync;

        public GetFollowerInfoThread(String openid, boolean sync) {
            this.openid = openid;
            this.sync = sync;
        }

        @Override
        public void run() {
            Follower f = null;
            try {
                f = wxapi.getFollower(openid, "");
                update(f, sync);
            } catch (WxRespException e) {
              throw Lang.wrapThrow(e, "获取粉丝信息失败!!!");
            }
        }
    }
}
