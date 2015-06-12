package org.elkan1788.extra.nuby.task;

import org.elkan1788.extra.nuby.service.CouponService;
import org.elkan1788.extra.nuby.service.TrailService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 检验优惠券和活动是否有效的任务
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/4
 * @version 1.0.0
 */
@IocBean
public class CheckValidJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    private TrailService trailService;
    @Inject
    private CouponService couponService;

    @Override
    public void execute(JobExecutionContext jec)
            throws JobExecutionException {

        trailService.makeInvalid();
        couponService.makeInvalid();
    }
}
