package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.bean.Sysuser;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.SysuserService;
import org.elkan1788.extra.nuby.session.UserSession;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;
import org.nutz.lang.hardware.Networks;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * 管理员登录修改信息功能模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @version 1.0.3
 * @since 2015/4/14
 */
@IocBean
@At("/admin")
public class AdminModule extends BaseModule {

    private static final Log log = Logs.get();

    @Inject
    private SysuserService sysuserService;

    @At
    @Ok("jsp:views.admin.login")
    public void login(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @At("/login/check")
    public AjaxReturn check(@Param("user") String user,
                        @Param("pswd") String pswd,
                        @Param("token") String token,
                        HttpServletRequest req,
                        HttpSession session) {

        log.infof("登录检验: user: %s, pswd: %s, token: %s", user, pswd, token);

        if (Lang.isEmpty(user)
                || Lang.isEmpty(pswd)
                || Lang.isEmpty(token)) {
            log.error("非法登录!!!");
            this.ok = false;
            this.setErrCode(Constant.ILLEGAL_CONTROL);
            this.msg = "非法登录!!";
        }

        Sysuser sysuser = sysuserService.get(user);
        if (!Lang.isEmpty(sysuser)) {
            if (Lang.equals(Lang.sha1(pswd), sysuser.getActpswd())) {
                // 创建SESSION
                UserSession us = new UserSession();
                us.setUserId(sysuser.getUserId());
                us.setAccount(sysuser.getAccount());
                us.setNickName(sysuser.getNickname());
                us.setLoginCnt(sysuser.getLoginCnt());
                us.setLastLoginTime(sysuser.getLastLoginTime());
                session.setAttribute(Constant.CUR_USER, us);

                sysuser.setLastLoginTime(new Date());
                int cnt = !Lang.isEmpty(sysuser.getLoginCnt())
                        ? sysuser.getLoginCnt()+1 : 1;
                sysuser.setLoginCnt(cnt);

                this.ok = true;
                sysuserService.update(sysuser);
                this.setErrCode(Constant.SUCCESS);
                this.msg = "登录成功.";
            } else {
                this.ok = false;
                this.setErrCode(Constant.NOT_EXIST);
                this.msg = "用户名或密码不正确.";
            }
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg)
                .setData("/admin/index.do");
    }

    @At
    @Filters(@By(type = CheckSession.class,
            args = {Constant.CUR_USER, "/admin/login.do"}))
    @Ok("jsp:views.admin.index")
    public void index(HttpServletRequest req) {

        Properties mapping = System.getProperties();
        log.info(mapping);
        String encoding = Encoding.defaultEncoding();
        String curpath = new File(".").getAbsolutePath();
        String version = mapping.get("java.version").toString();
        String filesep = mapping.get("file.separator").toString();
        String timezone = mapping.get("user.timezone").toString();
        String osname = mapping.get("os.name").toString()
                + "," + mapping.get("os.arch").toString();

        NutConfig config = Mvcs.getNutConfig();
        String serverinfo = config.getServletContext().getServerInfo();
        String servletapi = Integer.valueOf(config.getServletContext().getMajorVersion()) + "."
                + Integer.valueOf(config.getServletContext().getMinorVersion());
        if (config.getServletContext().getMajorVersion() > 2
                || config.getServletContext().getMinorVersion() > 4) {
            String context = config.getServletContext().getContextPath();
            req.setAttribute("context", context);
        }

        String mac = Networks.mac();
        String ip = Networks.ipv4();

        req.setAttribute("encoding", encoding);
        req.setAttribute("curpath", curpath);
        req.setAttribute("version", version);
        req.setAttribute("filesep", filesep);
        req.setAttribute("timezone", timezone);
        req.setAttribute("osname", osname);
        req.setAttribute("serverinfo", serverinfo);
        req.setAttribute("servletapi", servletapi);
        req.setAttribute("mac", mac);
        req.setAttribute("ip", ip);

        createToken16();
        req.setAttribute("pageToken", pageToken);

    }

    @At
    @Filters(@By(type = CheckSession.class,
            args = {Constant.CUR_USER, "/admin/login.do"}))
    public Object updatePswd(@Param("nickname")String nickname,
                             @Param("password")String password,
                             @Param("password2")String password2,
                             @Attr(Constant.CUR_USER)UserSession us) {

        Sysuser su = new Sysuser(us.getUserId());
        su.setNickname(nickname.trim());
        su.setActpswd(Lang.sha1(password.trim()));
        try {
            sysuserService.update(su);
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新管理员的密码信息失败!!!");
            this.ok = false;
            this.msg = "更新管理员信息时系统异常!!!";
            this.setErrCode(Constant.SYS_ERR);
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg)
                .setData(us);
    }

    @At
    @Ok("jsp:views.admin.monitor")
    public void monitoring(@Param("mt")int moduleType, HttpServletRequest req) {
        if (moduleType == 1){
            req.setAttribute("path", "/druid/index.html");
        } else {
            req.setAttribute("path", "/monitoring");
        }
    }

    @At
    @Ok(">>:/admin/login.do")
    public void logout(HttpSession session) {

        session.removeAttribute(Constant.CUR_USER);
    }
}
