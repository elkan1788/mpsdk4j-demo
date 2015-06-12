package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Sharelog;
import org.elkan1788.extra.nuby.weixin.NubyWxService;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Map;

/**
 * 分享记录业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/25
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class SharelogService extends BaseService<Sharelog> {

    private static final Log log = Logs.get();

    @Inject
    private NubyWxService wxService;

    public void add(String openId, Integer type) {
        Sharelog sl = new Sharelog();
        sl.setOpenid(openId);
        switch (type) {
            case 0:
                sl.setFriend(1);
                sl.setQq(0);
                sl.setWeibo(0);
                sl.setCircle(0);
                break;
            case 1:
                sl.setQq(1);
                sl.setFriend(0);
                sl.setWeibo(0);
                sl.setCircle(0);
                break;
            case 2:
                sl.setWeibo(1);
                sl.setFriend(0);
                sl.setQq(0);
                sl.setCircle(0);
                break;
            default:
                sl.setCircle(1);
                sl.setFriend(0);
                sl.setWeibo(0);
                sl.setQq(0);
                break;
        }
        try {
            sl = dao().insert(sl);
            if (Lang.isEmpty(sl.getSharelogId())) {
                log.error("记录用户分享失败!!!");
            }
        } catch (Exception e) {
            log.error("记录用户分享异常!!!");
        }
    }

    public void update(Sharelog sl, Integer type) {

        switch (type) {
            case 0:
                int friend = sl.getFriend();
                sl.setFriend(++friend);
                break;
            case 1:
                int qq = sl.getQq();
                sl.setQq(++qq);
                break;
            case 2:
                int weibo = sl.getWeibo();
                sl.setWeibo(++weibo);
                break;
            default:
                int cricle = sl.getCircle();
                sl.setCircle(++cricle);
                break;
        }

        try {
            int rows = dao().update(sl);
            if (rows < 1) {
                log.errorf("更新用户[%s-%d]分享失败!!!", sl.getOpenid(), type);
            }
        } catch (Exception e) {
            log.error("更新用户分享异常!!!");
        }
    }

    public void addShareLog(String openid, Integer type) {
        Sharelog old_sl = get("openid", openid);
        if (null == old_sl) {
            add(openid, type);
        } else {
            update(old_sl, type);
        }

        wxService.sendCoupon(openid);
    }

    public Map<String, Object> list(int page, int rows, String nickName) {

        Criteria cri = Cnd.cri();
        if (!Lang.isEmpty(nickName)) {

        }
        Map<String, Object> map = easyuiDGPager(page, rows, cri, true);
        return map;
    }
}
