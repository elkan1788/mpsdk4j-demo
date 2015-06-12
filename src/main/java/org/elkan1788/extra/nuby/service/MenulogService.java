package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Menulog;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Date;

/**
 * 菜单点击记录业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/20
 * @version 1.0.0
 */
@IocBean(name = "mlogService",fields = {"dao"})
public class MenulogService extends BaseService<Menulog> {

    private static final Log log = Logs.get();

    public void addLog(Menulog ml) {
        ml.setCreateTime(new Date());
        dao().insert(ml);
    }
}
