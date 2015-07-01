package org.elkan1788.extra.nuby.init;

import org.elkan1788.extra.nuby.bean.Settings;
import org.elkan1788.extra.nuby.service.SettingsService;
import org.elkan1788.extra.nuby.util.QiNiuTools;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.Dao;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * 系统初始化
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/19
 * @version 1.0.0
 */
public class SetupMethod implements Setup {

    public static final Log log = Logs.get();

    @Override
    public void init(NutConfig nutCfg) {

        Ioc ioc = nutCfg.getIoc();

        InitDB.createDB((NutDao)ioc.get(Dao.class));

        ioc.get(NutQuartzCronJobFactory.class);

        log.info("初始化微信公众号及七牛云服务信息...");
        MPAct mp = ioc.get(MPAct.class, "mp");
        QiNiuTools qn = ioc.get(QiNiuTools.class, "qn");
        SettingsService setService = ioc.get(SettingsService.class, "setService");
        Settings set = setService.get();
        if (null != set){
            // 七牛云服务信息生成
            qn.setAk(set.getQnAK());
            qn.setSk(set.getQnSK());
            qn.setUk(set.getQnUK());
            qn.setExpireIn(set.getQnExpiresIn());
            // 更新UPLOAD_KEY
            setService.validQnUK(true);
            log.info(ioc.get(QiNiuTools.class, "qn"));
            // 微信公众号信息生成
            mp.setMpId(set.getMpId());
            mp.setAppId(set.getAppId());
            mp.setAppSecret(set.getSecret());
            mp.setToken(set.getToken());
            mp.setJsTicket(set.getJsapiTicket());
            mp.setJsExpiresIn(set.getJsapiExpiresIn());
            // 开启AES加密
            if (!Lang.isEmpty(set.getAesKey())){
                mp.setAESKey(set.getAesKey());
            }
            // 更新ACCESS_TOKEN
            setService.validAccessToken(true);
            // 更新JSAPI_TICKET
            setService.validJsapiTicket(true);
            log.info(ioc.get(MPAct.class, "mp"));
        } else {
            log.error("初始化微信公众号及七牛云服务信息时,未找到配置信息!!!");
        }
        log.info("微信公众号及七牛云服务信息初始化成功.");

    }

    @Override
    public void destroy(NutConfig nutCfg) {

    }
}
