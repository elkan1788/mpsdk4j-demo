package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Menu;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.List;

/**
 * 微信自定义菜单业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/19
 * @version 1.0.0
 */
@IocBean(name = "mService",fields = {"dao"})
public class MenuService extends BaseService<Menu> {

    private static final Log log = Logs.get();

    public Integer getMenuId(String menuKey) {
        Integer menu_id = 0;
        if (!Lang.isEmpty(menuKey)) {
            List<Menu> menus = dao().query(Menu.class, Cnd.where("menuKey", "=", menuKey));
            Menu m = (!Lang.isEmpty(menus)) ? menus.get(0) : null;
            menu_id = (null!=m) ? m.getMenuId() : 0;
        } else {
           log.error("自定菜单的KEY值不能为空!!!");
        }

        return menu_id;
    }
}
