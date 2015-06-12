package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Sysuser;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.List;

/**
 * 后台管理用户业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/13
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class SysuserService extends BaseService<Sysuser> {

    private static final Log log = Logs.get();

    public Sysuser get(String userName) {
        try {
            List<Sysuser> users = dao()
                    .query(Sysuser.class,
                            Cnd.where("account", "=", userName)
                                    .and("lockStatus", "=", "1"));
            Sysuser user = !Lang.isEmpty(users) ? users.get(0) : null;
            return user;
        } catch (Exception e) {
            log.errorf("获取管理员[%s]信息失败!!!", userName);
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public void update(Sysuser user) {
        try {
            int rows = dao().updateIgnoreNull(user);
            if (rows < 1) {
                log.errorf("更新管理员[%s]信息失败!!!", user.getAccount());
            }
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "更新管理员信息时出现异常!!!");
        }
    }
}
