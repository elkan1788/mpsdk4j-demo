package org.elkan1788.extra.nuby.util;

import org.elkan1788.extra.nuby.service.SettingsService;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.nutz.filepool.NutFilePool;
import org.nutz.img.Images;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 所有上传图像处理加工
 * 宝宝图片按640 * 640的比例增大或缩放
 * 微信息消息图片按360*200的比例增大或缩放
 * 上传至七牛云服务
 *
 * @author  凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/4
 * @version 1.0.0
 */
@IocBean(create = "init",name = "uploadImgP")
public class UploadImgProcess {

    private static final Log log = Logs.get();

    private static final Integer BABYIMG_W_H = 640;
    private static final Integer WXNEWS_IMG_W = 360;
    private static final Integer WXNEWS_IMG_H = 200;

    @Inject
    private WxApi wxapi;
    @Inject
    private QiNiuTools qn;
    @Inject
    private PropertiesProxy conf;
    @Inject
    private SettingsService setService;

    private NutFilePool syncPool;

    private static int syncImgCnt = 0;

    public void init() {
        String tmpPath = Mvcs.getHttpSession().getServletContext().getRealPath("/sync-tmp");
        log.infof("NutFilePool Temp Path: %s", tmpPath);
        syncPool = new NutFilePool(tmpPath);
    }

    /**
     * 微信同步抓取图片
     * @param mediaId   媒体ID
     * @param openId    用户微信ID
     */
    public boolean syncBabyImg(String mediaId, String openId) {

        boolean sync = false;
        String imgName = mediaId + ".jpg";
        File img = syncPool.createFile(imgName);

        log.infof("Temp File: %s", img.getName());
        log.infof("Temp File Path: %s", img.getAbsolutePath());

        // 1. 抓取微信服务器图像
        try {
            wxapi.dlMedia(mediaId, img);
        } catch (Exception e) {
            log.errorf("抓取用户[%s]微信图片出现异常!!!", openId);
            log.error(e.getLocalizedMessage());
            if (syncImgCnt < 5) {
                try {
                    Thread.sleep(2* 1000);
                } catch (InterruptedException e1) {
                    log.error("重新抓取用户微信图片睡眠时出现异常!!!");
                }
                syncImgCnt++;
                syncBabyImg(mediaId, openId);
            }

            syncImgCnt = 0;
        }

        // 2. 裁剪图像加工
        BufferedImage tmpBI = null;
        try {
            tmpBI =  Images.clipScale(img, img, BABYIMG_W_H, BABYIMG_W_H);
        } catch (IOException e) {
            Lang.wrapThrow(e, "处理用户图像[%s-%s]时出现异常!!!", openId, img.getName());
        }

        // 3. 同步图像至七牛服务
        sync = qn.uploadFile(img, imgName);

        if (sync) {
            tmpBI = null;
        }

        syncImgCnt = 0;

        return sync;
    }

    /**
     * KindEditor组件上传图像同步
     * @param img       图像
     * @param imgName   图像名称
     */
    public boolean syncKEUploadImg(File img, String imgName) {

        boolean sync = qn.uploadFile(img, imgName);
        return sync;
    }

}
