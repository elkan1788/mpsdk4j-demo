package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Babyimg;
import org.elkan1788.extra.nuby.bean.ImageSrc;
import org.elkan1788.extra.nuby.util.QiNiuTools;
import org.elkan1788.extra.nuby.util.UploadImgProcess;
import org.elkan1788.osc.weixin.mp.core.WxApi;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 宝宝图片业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @version 1.0.0
 * @since 2015/4/25
 */
@IocBean(fields = {"dao"})
public class BabyimgService extends BaseService<Babyimg> {

    private static final Log log = Logs.get();

    @Inject
    private WxApi wxapi;
    @Inject
    private QiNiuTools qn;
    @Inject
    private PropertiesProxy conf;

    @Inject
    private SettingsService setService;
    @Inject
    private UploadImgProcess uploadImgP;

    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    public Babyimg getUpSucBaby(String openid) {

        List<Babyimg> list = dao().query(getEntityClass(), Cnd.where("openid", "=", openid).and("valid", "=", "1"));
        Babyimg babyimg = null;
        try {
            babyimg = !Lang.isEmpty(list) ? list.get(0) : null;
        } catch (Exception e) {
            log.error("获取已上传宝宝照片时出现异常!!!");
            log.error(e.getLocalizedMessage());
        }

        return babyimg;

    }

    public void add(Babyimg babyimg) {
        try {
            babyimg.setValide("0");
            babyimg.setUpdateTime(new Date());
            babyimg = dao().insert(babyimg);
            if (Lang.isEmpty(babyimg.getImgId())) {
                log.errorf("保存用户[%s]宝宝照片失败!!!", babyimg.getOpenid());
            }
        } catch (Exception e) {
            log.errorf("保存用户[%s]上传的宝宝照片异常!!!", babyimg.getOpenid());
            log.error(e.getLocalizedMessage());
        }
    }

    public void update(Babyimg babyimg) {
        try {
            babyimg.setUpdateTime(new Date());
            log.info(babyimg);
            int rows = dao().updateIgnoreNull(babyimg);
            if (rows < 1) {
                log.errorf("更新用户[%s]宝宝照片失败!!!", babyimg.getOpenid());
            }
        } catch (Exception e) {
            log.errorf("更新用户[%s]宝宝照片异常!!!", babyimg.getOpenid());
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 微信端照片上传更新
     *
     * @param babyimg 照片信息
     * @param level   上传张数
     */
    public void uploadImg(Babyimg babyimg, int level) {
        Babyimg oldBi = get("openid", babyimg.getOpenid());
        if (null == oldBi) {
            log.info("新用户上传宝宝照片: " + babyimg);
            add(babyimg);
        } else {
            oldBi.setBabyName(babyimg.getBabyName());
            oldBi.setValide("0");
            if (level == 1) {
                oldBi.setImage1(babyimg.getImage1());
                oldBi.setMediaId1(babyimg.getMediaId1());
            } else {
                oldBi.setImage2(babyimg.getImage2());
                oldBi.setMediaId2(babyimg.getMediaId2());
            }

            update(oldBi);
        }

        if (level == 2) {
            pool.execute(new SyncImageThread(oldBi));
        }
    }

    public Map<String, Object> list(int page, int rows, String nickName) {

        Criteria cri = Cnd.cri();
        if (!Lang.isEmpty(nickName)) {

        }
        cri.where().andEquals("valid", "1");
        cri.getOrderBy().desc("updateTime");
        Map<String, Object> map = easyuiDGPager(page, rows, cri, true);
        return map;
    }

    /**
     * 获取当前页码的照片信息
     *
     * @param num 页码
     * @return
     */
    public List<ImageSrc> getImgs(Integer num) {
        List<ImageSrc> imgs = new ArrayList<>();
        Pager pager = dao().createPager(num, 10);
        List<Babyimg> data = dao().query(Babyimg.class, Cnd.where("valid","=","1"), pager);
        if (!Lang.isEmpty(data)) {
            for (Babyimg img : data) {
                imgs.add(new ImageSrc(img.getBabyName(), img.getImage1()));
                imgs.add(new ImageSrc(img.getBabyName(), img.getImage2()));
            }
        }
        return imgs;
    }

    /**
     * 抓取用户上传照片
     * 而后上传到七牛云服务
     */
    class SyncImageThread implements Runnable {

        private Babyimg babyimg;

        public SyncImageThread(Babyimg babyimg) {
            this.babyimg = babyimg;
        }

        @Override
        public void run() {
            log.infof("抓取用户[%s]第1张图片[%s]....", babyimg.getOpenid(), babyimg.getMediaId1());
            uploadImgP.syncBabyImg(babyimg.getMediaId1(), babyimg.getOpenid());

            log.infof("抓取用户[%s]第2张图片[%s]....", babyimg.getOpenid(), babyimg.getMediaId1());
            uploadImgP.syncBabyImg(babyimg.getMediaId2(), babyimg.getOpenid());

            log.info("图片抓取成功...");
            babyimg.setValide("1");
            try {
                dao().updateIgnoreNull(babyimg);
            } catch (Exception e) {
                log.error("图片抓取完成后更新宝宝相片信息异常!!!");
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
