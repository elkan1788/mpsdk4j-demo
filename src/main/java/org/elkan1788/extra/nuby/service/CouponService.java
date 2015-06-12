package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Coupon;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Date;
import java.util.List;

/**
 * 优惠券配置业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/21
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class CouponService extends BaseService<Coupon> {

    private static final Log log = Logs.get();

    public void add(Coupon coupon) {
        coupon.setCreateTime(new Date());
        try {
            coupon.setSendNum(0);
            Coupon result = dao().insert(coupon);
            if (null == result) {
                log.error("新增优惠券信息失败!!!");
            }
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "新增优惠券信息异常!!!");
        }
    }

    public void update(Coupon coupon) {
        try {
            coupon.setCreateTime(new Date());
            int rows = dao().updateIgnoreNull(coupon);
            if (rows < 1) {
                log.error("更新优惠券信息失败!!!");
            }
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "更新优惠券信息异常!!!");
        }
    }

    public Coupon get() {
        Coupon coupon = get("valid", "1");
        return coupon;
    }

    public void updateValid() {
        try {
            Sql sql = Sqls.create("UPDATE nuby_wx_coupon SET valid = '0' WHERE valid = '1'");
            dao().execute(sql);
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "更新已存在的有效优惠券信息异常!!!");
        }
    }

    public void makeInvalid() {

        List<Coupon> coupons = dao().query(Coupon.class, Cnd.where("valid", "=", "1"));
        if (Lang.isEmpty(coupons)) {
            return;
        }

        long curTime = System.currentTimeMillis();
        for (Coupon cp : coupons) {

            long validTime = cp.getValidTime().getTime();
            if (validTime <= curTime) {
                cp.setValid("0");
                update(cp);
            }
        }
    }
}
