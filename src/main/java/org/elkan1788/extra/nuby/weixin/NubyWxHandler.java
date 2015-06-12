package org.elkan1788.extra.nuby.weixin;

import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.elkan1788.osc.weixin.mp.core.WxDefaultHandler;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 微信信息默认处理器
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/12
 * @version 1.0.0
 */
@IocBean(name = "wxHandler")
public class NubyWxHandler extends WxDefaultHandler {

    private static final Log log = Logs.get();

    @Inject
    private WxApi wxapi;
    @Inject
    private NubyWxService wxService;

    @Override
    public OutPutMsg eSub(ReceiveMsg rm) {

        log.infof("发现新用户[%s]关注...", rm.getFromUserName());
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = wxService.followerSubEvent(1, rm.getFromUserName());
        om.setContent(content);
        return om;
    }

    @Override
    public void eUnSub(ReceiveMsg rm) {
        log.infof("用户[%s]退订...", rm.getFromUserName());
        wxService.followerSubEvent(0, rm.getFromUserName());
    }

    @Override
    public OutPutMsg eClick(ReceiveMsg rm) {

        log.infof("用户[%s]点击了功能菜单[%s]...", rm.getFromUserName(), rm.getEventKey());
        OutPutMsg om = new OutPutMsg(rm);
        String menuKey = rm.getEventKey();

        switch (menuKey) {

            case Constant.PUZZLE_GAME_MENU:
                om.setMsgType(WxMsgType.news.name());
                om.setArticles(wxService.buildGameArt(rm.getFromUserName()));
                break;

            case Constant.BABY_EXPR_SHARE:
            case Constant.BABY_SPEC:
                om.setMsgType(WxMsgType.news.name());
                om.setArticles(wxService.bulidBabyShareArt());
                break;
        }
        wxService.menuClickLog(menuKey, rm.getFromUserName());
        return om;
    }

    @Override
    public void eView(ReceiveMsg rm) {

        log.infof("用户[%s]点击了视图菜单[%s]...", rm.getFromUserName(), rm.getEventKey());
        wxService.menuClickLog(rm.getEventKey(), rm.getFromUserName());
    }
}
