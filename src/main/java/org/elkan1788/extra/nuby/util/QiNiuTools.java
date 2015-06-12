package org.elkan1788.extra.nuby.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.File;

/**
 * 七牛服务相关的工具类
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/2
 * @version 1.0.0
 */
public class QiNiuTools {

    private static final Log log = Logs.get();

    private String ak;
    private String sk;
    private String uk;
    private Long expireIn;

    private PropertiesProxy conf;

    private UploadManager um = new UploadManager();

    private static int uploadCnt;

    public QiNiuTools() {
    }

    public QiNiuTools(PropertiesProxy conf) {
        this.conf = conf;
    }

    public QiNiuTools(String ak, String sk) {
        this.ak = ak;
        this.sk = sk;
    }

    /**
     * 刷新七牛UPLOAD_KEY
     */
    public void refreshUK() {
        if (Lang.isEmpty(ak)
                || Lang.isEmpty(sk)){
            log.error("请设置七牛的AK和SK!!!");
            return;
        }
        synchronized (this) {
            long refreshTime =  System.currentTimeMillis();
            Auth auth = Auth.create(ak, sk);
            String uk = auth.uploadToken(conf.get("bucket"));
            this.setUk(uk);
            this.setExpireIn(refreshTime+(3600-60)*1000);
        }
    }

    /**
     * 上传文件到七牛云服务
     * @param file  文件
     * @param key   保存名字
     * @return  成功或失败
     */
    public boolean uploadFile(File file, String key) {
        boolean flag = false;
        try {
            Response resp = um.put(file, key, this.uk);
            flag = resp.isOK();
            if (resp.isServerError()) {
                log.errorf("上传文件[%s]到七牛服务时服务器错误!!!", file.getName());
            } else {
                log.infof("上传文件[%s]到七牛服务时服务器成功.", file.getName());
            }
        } catch (QiniuException e) {
            log.errorf("上传文件[%s]到七牛服务时异常!!!", file.getName());
            if (uploadCnt < 5) {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e1) {
                    log.error("重新上传用户图片至七牛服务器时异常!!!");
                }
                uploadCnt++;
                uploadFile(file, key);
            }
            uploadCnt = 0;
        }
        uploadCnt = 0;
        return flag;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getUk() {
        return uk;
    }

    public void setUk(String uk) {
        this.uk = uk;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }

    public PropertiesProxy getConf() {
        return conf;
    }

    public void setConf(PropertiesProxy conf) {
        this.conf = conf;
    }

    @Override
    public String toString() {
        return "QiNiuTools{" +
                "ak='" + ak + '\'' +
                ", sk='" + sk + '\'' +
                ", uk='" + uk + '\'' +
                ", expireIn=" + expireIn +
                '}';
    }
}
