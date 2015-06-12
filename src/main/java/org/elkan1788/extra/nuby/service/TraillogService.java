package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Traillog;
import org.elkan1788.extra.nuby.init.Constant;
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
 * 试用活动记录业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/28
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class TraillogService extends BaseService<Traillog> {

    private static final Log log = Logs.get();

    public int add(Traillog traillog) {

        List<Traillog> tls = dao().query(Traillog.class,
                Cnd.where("openid", "=", traillog.getOpenid())
                        .and("trailId", "=", traillog.getTrailId()));

        Traillog tl = Lang.isEmpty(tls) ? null : tls.get(0);
        if (null != tl) {
            return Constant.REPEAT_APPLY;
        }

        try {
            traillog.setCreateTime(new Date());
            traillog = dao().insert(traillog);
            if (Lang.isEmpty(traillog.getTrailogId())) {
                log.error("登记用户试用活动信息失败!!!");
                return Constant.SQL_ERR;
            }
        } catch (Exception e) {
            log.error("登记用户试用活动信息异常!!!");
            log.error(e.getLocalizedMessage());
            return Constant.SYS_ERR;
        }
        return Constant.SUCCESS;
    }

    public Map<String, Object> list (int page, int rows, String userName) {

        Criteria cri = Cnd.cri();
        if (!Lang.isEmpty(userName) && userName.length() > 0) {
            cri.where().andLike("username", userName);
        }

        cri.getOrderBy().desc("createTime");
        Map<String, Object> map = easyuiDGPager(page, rows, cri, true);
        return  map;
    }
}
