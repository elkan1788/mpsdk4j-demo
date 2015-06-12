package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.bean.Babyimg;
import org.elkan1788.extra.nuby.bean.Gamesettings;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.BabyimgService;
import org.elkan1788.extra.nuby.service.GameSettingService;
import org.elkan1788.extra.nuby.service.GamelogService;
import org.elkan1788.extra.nuby.service.SharelogService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏配置功能模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 */
@IocBean
@At("/admin/gamecfg")
@Filters(@By(type = CheckSession.class,
        args = {Constant.CUR_USER, "/admin/login.do"}))
public class GameCfgModule extends BaseModule {

    private static final Log log = Logs.get();

    @Inject
    private GameSettingService gameSetService;
    @Inject
    private GamelogService gamelogService;
    @Inject
    private BabyimgService babyimgService;
    @Inject
    private SharelogService sharelogService;

    // 游戏配置信息
    @At
    @Ok("jsp:views.admin.gamesettings")
    public void setindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
        Gamesettings gs = gameSetService.getForBack();
        req.setAttribute("gs", gs);
        List<String> imgs = new ArrayList<>();
        imgs.add(gs.getImage1());
        imgs.add(gs.getImage2());
        imgs.add(gs.getImage3());
        imgs.add(gs.getImage4());
        imgs.add(gs.getImage5());
        imgs.add(gs.getImage6());
        imgs.add(gs.getImage7());
        imgs.add(gs.getImage8());
        imgs.add(gs.getImage9());
        imgs.add(gs.getImage10());
        imgs.add(gs.getImage11());
        imgs.add(gs.getImage12());
        imgs.add(gs.getImage13());
        imgs.add(gs.getImage14());
        imgs.add(gs.getImage15());
        imgs.add(gs.getImage16());
        imgs.add(gs.getImage17());
        imgs.add(gs.getImage18());
        imgs.add(gs.getImage19());
        imgs.add(gs.getImage20());
        req.setAttribute("imgs", imgs);
    }

    @POST
    @At
    public AjaxReturn updateset(@Param("..")Gamesettings gs){

        log.infof("更新游戏配置信息[%s]...", pageToken);
        log.info(gs);

        this.initSuc();

        try {
            gs.setUpdateTime(new Date());
            int rows = gameSetService.updateGameset(gs);
            if (rows < 1){
                this.setErrCode(Constant.PER_ERR);
                this.msg = "更新游戏配置信息失败!!!";
            }
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新游戏配置[%s]数据失败!!!", this.pageToken);
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "更新游戏配置信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    // 游戏记录
    @At
    @Ok("jsp:views.admin.gamelog")
    public void logindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @POST
    @At
    public AjaxReturn updatelog(@Param("openid")String openId, @Param("token")String token,
                            @Param("type")String phoneType, @Param("level")Integer level,
                            @Param("time")Integer time, @Param("pass")String pass) {

        log.infof("更新游戏记录信息[%s]...", pageToken);
        log.infof("openid: %s, token: %s, type: %s, " +
                        "level: %d, time: %d, pass: %s", openId, token,
                phoneType, level, time, pass);

        this.initSuc();

        try {
            gamelogService.updatePlayLog(openId, token,
                                            phoneType, level,
                                            time, pass);
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新用户[%s]游戏记录时出现异常!!!");
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "更新游戏记录时系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String, Object> loglist(int page, int rows, String nickName) {

        Map<String, Object> map = gamelogService.list(page, rows, nickName);
        return map;
    }

    // 宝宝照片
    @At
    @Ok("jsp:views.admin.babyimg")
    public void babyindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String, Object> babylist(int page, int rows, String nickName) {

        Map<String, Object> data = babyimgService.list(page, rows, nickName);
        return  data;
    }

    @POST
    @At
    public AjaxReturn updatebabyimg(@Param("imgid")int imgid) {

        initSuc();

        Babyimg babyimg = new Babyimg(imgid);
        babyimg.setValide("1");
        try {
            Gamesettings gs = gameSetService.get();
            babyimg.setImage1(gs.getImage1());
            babyimg.setImage2(gs.getImage3());
            babyimgService.update(babyimg);
        } catch (Exception e) {
            log.error("更新宝宝照片状态时出现异常!!!");
            log.error(e.getLocalizedMessage());
            this.ok = false;
            this.setErrCode(Constant.SYS_ERR);
            this.msg = "更新宝宝照片状态失败!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    // 分享记录
    @At
    @Ok("jsp:views.admin.sharelog")
    public void shareindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @At
    @Ok("ioc:json")
    public Map<String, Object> sharelist(int page, int rows,
                                         String nickName) {

        Map<String, Object> map = sharelogService.list(page, rows, nickName);
        return  map;
    }
}
