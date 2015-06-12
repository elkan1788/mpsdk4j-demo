package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.bean.Coupon;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.service.CouponService;
import org.elkan1788.extra.nuby.service.CouponlogService;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
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
 * 优惠券功能模块
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 */
@IocBean
@At("/admin/coupon")
@Filters(@By(type = CheckSession.class,
        args = {Constant.CUR_USER, "/admin/login.do"}))
public class CouponModule extends BaseModule {

    private static final Log log = Logs.get();

    @Inject
    private CouponService couponService;
    @Inject
    private CouponlogService couponlogService;

    // 优惠券信息
    @At
    @Ok("jsp:views.admin.coupon")
    public void index(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @POST
    @At
    public AjaxReturn add(@Param("..") Coupon cp) {

        log.infof("新增优惠券信息[%s]...", pageToken);
        log.info(cp);

        this.initSuc();

        try {
            couponService.updateValid();
            couponService.add(cp);
        } catch (Exception e) {
            Lang.wrapThrow(e, "新增优惠券信息时系统异常!!!");
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "新增优惠券信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At
    public Object update(@Param("..") Coupon cp) {

        log.infof("更新优惠券信息[%s]...", pageToken);
        log.info(cp);

        this.initSuc();

        try {
            if (cp.getValid().equals("1")){
                couponService.updateValid();
            }
            couponService.update(cp);
        } catch (Exception e) {
            Lang.wrapThrow(e, "更新优惠券信息时系统异常!!!");
            this.ok = false;
            setErrCode(Constant.SYS_ERR);
            this.msg = "更新优惠券信息系统异常!!!";
        }

        return Ajax.ok()
                .setOk(this.ok)
                .setErrCode(this.errCode)
                .setMsg(this.msg);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String,Object> list(int page, int rows) {

        Criteria cri = Cnd.cri();
        cri.getOrderBy().desc("createTime");
        Map<String, Object> map = couponService.easyuiDGPager(page, rows, cri, false);
        return map;
    }

    // 优惠券记录信息
    @At
    @Ok("jsp:views.admin.couponlog")
    public void logindex(HttpServletRequest req) {
        createToken16();
        req.setAttribute("pageToken", this.pageToken);
    }

    @POST
    @At
    @Ok("ioc:json")
    public Map<String, Object> loglist(int page, int rows, String nickName) {

        Map<String, Object> map = couponlogService.list(page, rows, nickName);
        return map;
    }
}
