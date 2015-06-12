package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Settings;
import org.elkan1788.extra.nuby.init.Constant;
import org.elkan1788.extra.nuby.util.QiNiuTools;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.Date;

/**
 * 微信公众号配置业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/13
 * @version 1.0.5
 */
@IocBean(name = "setService",fields = {"dao"})
public class SettingsService extends BaseService<Settings> {

    private static final Log log = Logs.get();

    @Inject
    private WxApi wxapi;
    @Inject
    private MPAct mp;
    @Inject
    private QiNiuTools qn;

    // 403错误重刷计数
    private static int refreshASCnt = 0;
    private static int refreshJSCnt = 0;
    private static int refreshUKCnt = 0;

    /**
     * 更新公众号信息
     * @param set   公众号信息
     * @return 大于0表示成功
     */
    public int updateSettings(Settings set) {
        int rows = dao().updateIgnoreNull(set);
        return rows;
    }

    /**
     * 获取有效的配置
     */
    public Settings get() {

        Settings set = get("valid", 1);
        if (null == set) {
            log.error("未找到公众号的配置信息.");
        }
        return set;
    }

    /**
     * 获取微信欢迎语
     * @return  默认返回（[玫瑰]感谢您的关注[微笑]）
     */
    public String getWelCon() {
        Settings set = get();
        if (null == set) {
            return Constant.DEF_WELCOME;
        }
        return set.getWelcome();
    }

    /**
     * 检验ACCESS_TOKEN是否可用.
     */
    public void validAccessToken(boolean flush) {

        Settings set = get();
        if (null == set){
            log.error("验证ACCESS_TOKEN时出现异常!!!");
            log.error("未找到公众号的配置信息.");
            return;
        }
        // 校验时间是否失效
        long cur_time = System.currentTimeMillis();
        if (set.getExpiresIn() < cur_time || flush) {
            // 失效
            try {
                log.info("重新刷新ACCESS_TOKEN...");
                wxapi.refreshAccessToken();
            } catch (Exception e) {

                log.errorf("重新刷新ACCESS_TOKEN时出异常!!!");
                log.error(e.getLocalizedMessage());
                log.infof("第%d次重刷ACCESS_TOKEN...", refreshASCnt);
                // 尝试用递归解决403错误
                if (refreshASCnt < 5) {
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e1) {
                        log.error("重新刷新ACCESS_TOKEN睡眠异常!!!");
                    }
                    refreshASCnt++;
                    validAccessToken(true);
                }
                refreshASCnt = 0;
            }

            // 更新ACCESS_TOKEN
            set.setAccessToken(mp.getAccessToken());
            set.setExpiresIn(mp.getExpiresIn());
            set.setUpdateTime(new Date());
            int rows = dao().updateIgnoreNull(set);
            if (rows < 1) {
                log.error("更新ACCESS_TOKEN持久化时出现异常!!!");
            }
            log.infof("ACCESS_TOKEN更新成功: [%s - %d]", set.getAccessToken(), set.getExpiresIn());
        } else {
            log.infof("获取已有ACCESS_TOKEN成功: [%s - %d]", mp.getAccessToken(), mp.getExpiresIn());
        }

        refreshASCnt = 0;
    }

    /**
     * 检验JSSDK TICKET是否可用.
     */
    public void validJsapiTicket(boolean flush) {
        Settings set = get();
        if (null == set){
            log.error("验证JSAPI_TICKET时出现异常!!!");
            log.error("未找到公众号的配置信息.");
            return;
        }
        // 校验时间是否失效
        long cur_time = System.currentTimeMillis();
        if (set.getJsapiExpiresIn() < cur_time || flush) {
            // 失效
            try {
                log.info("重新刷新JSAPI_TICKET...");
                wxapi.refreshJsAPITicket();
            } catch (Exception e) {
                log.errorf("重新刷新JSAPI_TICKET时出异常!!!");
                log.error(e.getLocalizedMessage());
                log.infof("第%d次重刷JSAPI_TICKET...", refreshJSCnt);
                if (refreshJSCnt < 5) {
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e1) {
                        log.error("重刷JSAPI_TICKET睡眠时异常!!!");
                    }
                    refreshJSCnt++;
                    validJsapiTicket(true);
                }

                refreshJSCnt = 0;
            }

            // 更新ACCESS_TOKEN
            set.setJsapiTicket(mp.getJsTicket());
            set.setJsapiExpiresIn(mp.getJsExpiresIn());
            set.setUpdateTime(new Date());
            int rows = dao().updateIgnoreNull(set);
            if (rows < 1) {
                log.error("更新JSAPI_TICKET持久化时出现异常!!!");
            }
            log.infof("JSAPI_TICKET更新成功: [%s - %d]", set.getJsapiTicket(), set.getJsapiExpiresIn());
        } else {
            log.infof("获取已有JSAPI_TICKET成功: [%s - %d]", mp.getJsTicket(), mp.getJsExpiresIn());
        }

        refreshJSCnt = 0;
    }

    /**
     * 检验七牛UPLOAD_KEY是否可用.
     */
    public void validQnUK(boolean flush) {
        Settings set = get();
        if (null == set){
            log.error("验证UPLOAD_KEY时出现异常!!!");
            log.error("未找到七牛的配置信息.");
            return;
        }
        // 校验时间是否失效
        long cur_time = System.currentTimeMillis();
        if (set.getQnExpiresIn() < cur_time || flush) {
            // 失效
            try {
                log.info("重新刷新UPLOAD_KEY...");
                qn.refreshUK();
            } catch (Exception e) {
               log.error("重新刷新UPLOAD_KEY时出异常!!!");
                log.error(e.getLocalizedMessage());
                log.infof("第%d次重刷UPLOAD_KEY...", refreshUKCnt);
                if (refreshUKCnt < 5) {
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e1) {
                        log.error("重刷UPLOAD_KEY睡眠时异常!!!");
                    }
                    refreshUKCnt++;
                    validQnUK(true);
                }

                refreshUKCnt = 0;
            }

            // 更新UPLOAD_KEY
            set.setQnUK(qn.getUk());
            set.setQnExpiresIn(qn.getExpireIn());
            set.setUpdateTime(new Date());
            int rows = dao().updateIgnoreNull(set);
            if (rows < 1) {
                log.error("更新UPLOAD_KEY持久化时出现异常!!!");
            }
            log.infof("UPLOAD_KEY更新成功: [%s - %d]", set.getQnUK(), set.getQnExpiresIn());
        } else {
            log.infof("获取已有UPLOAD_KEY成功: [%s - %d]", qn.getUk(), qn.getExpireIn());
        }

        refreshUKCnt = 0;
    }
}
