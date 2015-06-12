package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.util.UploadImgProcess;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * KindEditor在线编辑器文件上传,管理模块
 *
 * @author Elkan(elkan1788@gmail.com)
 */
@At("/nutz/ke4plugin")
@IocBean(create = "init")
public class KindEditor4Module {

    // 日志输出对象
    private static Log log = Logs.get();

    @Inject
    private UploadImgProcess uploadImgP;
    @Inject
    private PropertiesProxy conf;

    // 允许上传文件后缀MAP数组
    private static final HashMap<String, String> extMap = new HashMap<String, String>();
    // 允许上传文件大小MAP数组
    private static final HashMap<String, Long> sizeMap = new HashMap<String, Long>();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_S");

    private String qnImgUrl;

    static {
        // 初始后缀名称MAP数组
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,txt,zip,rar");
        // 初始文件大小MAP数组
        sizeMap.put("image", 100 * 100 * 1024l);
        sizeMap.put("flash", 10 * 1024 * 1024l);
        sizeMap.put("media", 10 * 1024 * 1024l);
        sizeMap.put("file", 10 * 1024 * 1024l);
    }

    public void init() {
        qnImgUrl = conf.get("bucket_cname")+"/%1$s?"+conf.get("ke_fop_style");
    }

    @At
    @AdaptBy(type = UploadAdaptor.class)
    @Ok("ioc:json")
    public Map<String, Object> upload(@Param("imgFile") File tempFile,
                                      @Param("dir") String fileDir) {
        // 初始相关变量
        Map<String, Object> execute = new HashMap<String, Object>();
        // 检查是否有上传文件
        if (null == tempFile) {
            execute.put("error", 1);
            execute.put("message", "请选择上传文件.");
            return execute;
        }
        // 计算出文件后缀名
        String tempFileName = tempFile.getName();
        String fileExt = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
        // 检查上传文件类型
        if (!Arrays.<String>asList(extMap.get(fileDir).split(",")).contains(fileExt)) {
            execute.put("error", 1);
            execute.put("message", "上传文件的格式被拒绝,\n只允许"
                    + extMap.get(fileDir) + "格式的文件.");
            return execute;
        }
        // 检查上传文件的大小
        long maxSize = sizeMap.get(fileDir);
        if (tempFile.length() > maxSize) {
            execute.put("error", 1);
            String size = null;
            if (maxSize < 1024) {
                size = maxSize + "B";
            }
            if (maxSize > 1024 && maxSize < 1024 * 1024) {
                size = maxSize / 1024 + "KB";
            }
            if (maxSize > 1024 * 1024) {
                size = maxSize / (1024 * 1024) + "MB";
            }
            execute.put("message", "上传文件大小超过限制,只允\n许上传小于 " + size + " 的文件.");
            return execute;
        }

        String imgName = sdf.format(new Date()) + "." + fileExt;

        boolean flag = uploadImgP.syncKEUploadImg(tempFile, imgName);

        // 返回上传文件的输出路径至前端
        if (!flag) {
            execute.put("error", 1);
            execute.put("message", "同步图片至七牛云服务失败!请稍后再试. ^_^");
        } else {
            execute.put("error", 0);
            execute.put("url", String.format(qnImgUrl, imgName));
        }

        return execute;
    }
}
