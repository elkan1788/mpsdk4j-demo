package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Gamelog;
import org.elkan1788.extra.nuby.bean.Rankings;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 游戏记录业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/24
 * @version 1.0.0
 */
@IocBean(fields = {"dao"})
public class GamelogService extends BaseService<Gamelog> {

    private static final Log log = Logs.get();

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 添加用户游戏记录<p/>
     * 一般添加新游戏记录都为关卡1时的记录<p/>
     * 故其它属性均为0
     * @param gl 记录实体
     */
    public void add(Gamelog gl) {
        gl.setTime2(0);
        gl.setTime3(0);
        gl.setTime4(0);
        gl.setPass("0");
        gl.setGamelogTime(new Date());
        try {
            gl = dao().insert(gl);
            if (Lang.isEmpty(gl.getGamelogId())){
                log.errorf("添加用户[%s-%s]游戏记录失败!!!", gl.getOpenid(), gl.getToken());
            }
        } catch (Exception e) {
           throw Lang.wrapThrow(e, "添加用户游戏记录出现异常!!!");
        }
    }


    public void update(Gamelog gl) {
        gl.setGamelogTime(new Date());
        try {
            int rows = dao().updateIgnoreNull(gl);
            if (rows < 1) {
                log.error("更新用户游戏记录失败!!!");
            }
        } catch (Exception e) {
           throw Lang.wrapThrow(e, "更新用户游戏记录异常!!!");
        }
    }
    
    public void updatePlayLog(String openId, String token,
                              String phoneType, Integer level,
                              Integer time, String pass) {

        Gamelog oldGl = null;

        Criteria cri = Cnd.cri();
        cri.where().and("openid", "=", openId).and("token", "=", token);

        try {
            List<Gamelog> gls = dao().query(Gamelog.class, cri);
            if (!Lang.isEmpty(gls)) {
                oldGl = gls.get(0);
            }
        } catch (Exception e) {
            Lang.wrapThrow(e, "查找用户游戏记录异常!!!");
        }
        int total = time;
        if (null == oldGl) {
            oldGl = new Gamelog();
            oldGl.setOpenid(openId);
            oldGl.setToken(token);
            oldGl.setPhoneType(phoneType);
            oldGl.setTime1(time);
            oldGl.setTotalTime(time);
            this.add(oldGl);
        } else {
            switch (level) {
                case 1:
                    oldGl.setTime1(time);
                    oldGl.setTotalTime(total);
                    break;
                case 2:
                    oldGl.setTime2(time);
                    total += oldGl.getTime1();
                    oldGl.setTotalTime(total);
                    break;
                case 3:
                    oldGl.setTime3(time);
                    total += oldGl.getTime1()+oldGl.getTime2();
                    oldGl.setTotalTime(total);
                    break;
                default:
                    oldGl.setTime4(time);
                    total += oldGl.getTime1()+oldGl.getTime2()+oldGl.getTime3();
                    oldGl.setTotalTime(total);
                    break;
            }
            oldGl.setPass(pass);
            this.update(oldGl);
        }
    }

    public Map<String, Object> list(int page, int rows, String nickName) {

        Criteria cri = Cnd.cri();
        if (!Lang.isEmpty(nickName)){

        }

        cri.getOrderBy().desc("gamelogTime");

        Map<String, Object> map = easyuiDGPager(page, rows, cri, true);
        return map;
    }

    public List<Rankings> rankList() {

        String sql = "SELECT gl.openid AS openId, f.nickname AS nickName, f.headimgurl AS headImg, gl.total_time AS passTime FROM " +
                "(SELECT openid, MIN(total_time) total_time FROM nuby_wx_gamelog WHERE pass = '1'AND (gamelog_time BETWEEN @st AND @et) GROUP BY openid ORDER BY total_time ASC LIMIT 0, 11) " +
                "AS gl LEFT JOIN nuby_wx_followers f ON gl.openid = f.openid";
        Sql rankSql = Sqls.create(sql);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        rankSql.params().set("et", SDF.format(cal.getTime())+" 23:59:59");
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        rankSql.params().set("st", SDF.format(cal.getTime())+" 00:00:00");
        rankSql.setCallback(Sqls.callback.entities());
        rankSql.setEntity(dao().getEntity(Rankings.class));
        List<Rankings> ranks = null;
        try {
            dao().execute(rankSql);
            ranks = rankSql.getList(Rankings.class);
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "统计用户游戏记录排行榜异常!!!");
        }

        return ranks;
    }
}
