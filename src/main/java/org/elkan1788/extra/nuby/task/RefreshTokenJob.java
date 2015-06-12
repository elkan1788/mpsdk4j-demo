package org.elkan1788.extra.nuby.task;

import org.elkan1788.extra.nuby.service.SettingsService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时检查所有ACCESS_TOKEN状态
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/4
 */
@IocBean
public class RefreshTokenJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    private SettingsService setService;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("Refresh Token...");
        synchronized (setService) {
            setService.validQnUK(true);
            setService.validAccessToken(true);
            setService.validJsapiTicket(true);
        }
        log.info("Refresh Token success...");
    }
}
