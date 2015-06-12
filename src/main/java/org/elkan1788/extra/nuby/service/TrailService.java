package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Trail;
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
 * 试用活动信息业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/28
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class TrailService extends BaseService<Trail> {

    private static final Log log = Logs.get();

    public void add(Trail trail) {
        trail.setCreateTime(new Date());
        try {
            trail = dao().insert(trail);
            if (Lang.isEmpty(trail.getTrailId())) {
                log.error("添加试用活动信息失败!!!");
            }
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "添加试用活动信息异常!!!");
        }
    }

    public void updateValid() {
        try {
            Sql sql = Sqls.create("UPDATE nuby_wx_trail SET valid = '0' WHERE valid = '1'");
            dao().execute(sql);
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "更新已存在的有效活动信息异常!!!");
        }
    }

    public void update(Trail trail) {
        try {
            trail.setCreateTime(new Date());
            int rows = dao().updateIgnoreNull(trail);
            if (rows < 1) {
                log.error("更新试用活动信息失败!!!");
            }
        } catch (Exception e) {
           throw  Lang.wrapThrow(e, "更新试用活动信息异常!!!");
        }
    }

    public void makeInvalid() {

        List<Trail> trails = dao().query(Trail.class, Cnd.where("valid", "=", "1"));
        if (Lang.isEmpty(trails)) {
            return ;
        }

        long curTime = System.currentTimeMillis();
        for (Trail tr : trails) {

            long validTime = tr.getValidTime().getTime();
            if (validTime <= curTime) {
                tr.setValid("0");
                update(tr);
            }
        }
    }
}
