package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Couponlog;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏记录业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/24
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class CouponlogService extends BaseService<Couponlog> {

    private static final Log log = Logs.get();

    public void add(Couponlog cl) {
        cl.setCreateTime(new Date());
        try {
            cl = dao().insert(cl);
            if (Lang.isEmpty(cl.getConId())){
                log.errorf("添加用户[%s-%s]优惠券发送记录失败!!!", cl.getOpenid(), cl.getType());
            }
        } catch (Exception e) {
           throw Lang.wrapThrow(e, "添加用户优惠券发送记录出现异常!!!");
        }
    }
    
    public boolean isSend(Integer couponId, String openId) {
        boolean flag = false;
        Criteria cri = Cnd.cri();
        cri.where().and("conId", "=", couponId).and("openid", "=", openId);
        try {
            List<Couponlog> couponlogs =  dao().query(Couponlog.class, cri);
            if (!Lang.isEmpty(couponlogs)) {
                flag = true;
            }
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "查询用户[%s]优惠券发送记录异常!!!");
        }
        return flag;
    }

    public Map<String, Object> list(int page, int rows, String nickName) {
        Criteria cri = Cnd.cri();
        if (!Lang.isEmpty(nickName)){

        }
        cri.getOrderBy().desc("createTime");
        Map<String, Object> map =  this.easyuiDGPager(page, rows, cri, true);
        return map;
    }
}
