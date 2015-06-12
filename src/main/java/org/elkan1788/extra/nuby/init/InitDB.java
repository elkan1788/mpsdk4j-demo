package org.elkan1788.extra.nuby.init;

import org.elkan1788.extra.nuby.bean.*;
import org.nutz.dao.impl.NutDao;
import org.nutz.lang.Lang;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化数据库信息
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/06/12
 */
public class InitDB {

    private static List<Class<?>> tables = new ArrayList<>();

    static {
        tables.add(Babyimg.class);
        tables.add(Coupon.class);
        tables.add(Couponlog.class);
        tables.add(Followers.class);
        tables.add(Gamelog.class);
        tables.add(Gamesettings.class);
        tables.add(Menu.class);
        tables.add(Menulog.class);
        tables.add(Settings.class);
        tables.add(Sharelog.class);
        tables.add(Sysuser.class);
        tables.add(Trail.class);
        tables.add(Traillog.class);
    }

    public static void createDB(NutDao dao) {
        for (Class<?> table : tables) {
            if (dao.exists(table)) {
                dao.create(table, false);
            }
        }

        // add sys user
        Sysuser user = new Sysuser();
        user.setUserId(1);
        user.setNickname("admin");
        user.setAccount("admin");
        user.setLockStatus("1");
        user.setLoginCnt(0);
        user.setActpswd(Lang.sha1("a123456"));
        dao.insert(user);

        // settings
        Settings set = new Settings();
        set.setSetId(1);
        set.setMpId("your weixin mp origin id");
        set.setAppId("your weixin appid");
        set.setSecret("your weixin appsecret");
        set.setWelcome("new user subscribe message");
        set.setQnUK("your qiniu uk");
        set.setQnAK("your qiniu ak");
        set.setQnSK("your qiniu sk");
        dao.insert(set);

        // game settings
        Gamesettings gs = new Gamesettings();
        gs.setSetId(1);
        gs.setWxNews("weixin news message.");
        dao.insert(gs);
    }

}
