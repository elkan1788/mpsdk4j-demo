package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.bean.Trail;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.TrailService;
import org.elkan1788.extra.nuby.service.TraillogService;
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
import java.util.Map;

/**
 * 试用活动功能模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 */
@IocBean
@At("/admin/trail")
@Filters(@By(type = CheckSession.class,
        args = {Constant.CUR_USER, "/admin/login.do"}))
public class TrailModule extends BaseModule {

    private static Log log = Logs.get();

    @Inject
    private TrailService trailService;
    @Inject
    private TraillogService traillogService;

    // 试用活动信息
    @At
    @Ok("jsp:views.admin.trail")
    public void index(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }
    
    @POST
    @At
    public AjaxReturn add(@Param("..")Trail trail) {

        log.infof("新增试用活动信息[%s]...", pageToken);
        log.info(trail);

        this.initSuc();

        try {
            trailService.updateValid();
            trailService.add(trail);
        } catch (Exception e) {
            Lang.wrapThrow(e, "新增试用活动[%s]数据失败!!!", this.pageToken);
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "新增试用活动信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @At
    public AjaxReturn update(@Param("..")Trail trail) {

        log.infof("更新试用活动信息[%s]...", pageToken);
        log.info(trail);

        this.initSuc();

        try {
            if (trail.getValid().equals("1")) {
                trailService.updateValid();
            }
            trailService.update(trail);
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新试用活动[%s]数据失败!!!", this.pageToken);
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "更新试用活动信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String, Object> list(int page, int rows) {

        Map<String, Object> data = trailService.easyuiDGPager(page, rows, null, false);
        return data;
    }

    // 试用活动登记信息
    @At
    @Ok("jsp:views.admin.traillog")
    public void logindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @At
    @Ok("ioc:json")
    public Map<String, Object> loglist(int page, int rows,
                                       @Param("username")String userName) {

        Map<String, Object> map = traillogService.list(page, rows, userName);
        return map;
    }
}
