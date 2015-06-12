package org.elkan1788.extra.nuby.weixin;

import org.elkan1788.osc.weixin.mp.core.WxBase;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信服务交互控制层
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/20
 * @version 1.0.2
 */
@IocBean(create = "init")
@At("/wxinter")
public class WxInteractModule {

    private static final Log log = Logs.get();

    private static final String NONE = "";

    private WxBase wxBase = new WxBase();

    @Inject
    private MPAct mp;
    @Inject
    private NubyWxHandler wxHandler;

    public void init() {
        // 初始化
        wxBase.setMpAct(mp);
        wxBase.setWxHandler(wxHandler);
    }

    @At
    @Ok("raw")
    public String message(HttpServletRequest req) {

        // 微信交互开始
        wxBase.init(req);
        try {
            String method = req.getMethod();
            String result = "";
            if (method.equals("GET")){
                log.info("微信服务接入验证...");
                result = wxBase.check();
            } else {
                log.info("微信服务互动...");
                result = wxBase.handler();
            }
            return result;
        } catch (Exception e) {
            log.error("与微信服务交互时出现异常!!!");
            log.error(e.getLocalizedMessage());
        }

        return NONE;
    }
}
