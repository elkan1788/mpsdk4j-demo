package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.bean.Settings;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.FollowersService;
import org.elkan1788.extra.nuby.service.SettingsService;
import org.nutz.dao.Cnd;
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
import java.util.Date;
import java.util.Map;

/**
 * 微信服务配置功能模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 */
@IocBean
@At("/admin/wxcfg")
@Filters(@By(type = CheckSession.class,
        args = {Constant.CUR_USER, "/admin/login.do"}))
public class WeixinCfgModule extends BaseModule {

    private static final Log log = Logs.get();

    @Inject
    private SettingsService setService;
    @Inject
    private FollowersService flwService;

    // 总配置信息
    @At
    @Ok("jsp:views.admin.settings")
    public void setindex(@Param("mt")String moduleType, HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
        req.setAttribute("set", this.setService.get());
        req.setAttribute("mt", moduleType);
    }

    @POST
    @At
    public AjaxReturn updateset(@Param("..") Settings set,
                                @Param("pageToken") String pageToken) {

        log.infof("更新配置信息[%s]...", pageToken);
        log.info(set);

        this.initSuc();

        try {
            set.setUpdateTime(new Date());
            int rows = this.setService.updateSettings(set);
            if (rows < 0) {
                this.setErrCode(Constant.PER_ERR);
                this.msg = "更新配置信息失败!!!";
            }
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新Settings[%s]时出现异常!!!", this.pageToken);
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "更新配置信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    // 微信粉丝信息
    @At
    @Ok("jsp:views.admin.followers")
    public void flwindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String, Object> flwlist(@Param("startT")Date st,
                                    @Param("endT")Date et,
                                    @Param("nickName")String nickName,
                                    Integer page, Integer rows,
                                    HttpServletRequest req) {

        Map<String, Object> pager = flwService.list(page, rows, st, et, nickName);
        return pager;
    }

    @POST
    @At
    public AjaxReturn flwsync() {

        this.initSuc();

        int fls = flwService.syncFollowers();
        int subs = flwService.count(Cnd.where("userStatus", "=", "1"));
        int unsubs = flwService.count(Cnd.where("userStatus", "=", "0"));

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg)
                .setData(new Integer[]{fls, subs, unsubs});
    }
}
