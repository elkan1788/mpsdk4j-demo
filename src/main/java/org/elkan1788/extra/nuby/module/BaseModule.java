package org.elkan1788.extra.nuby.module;

import org.elkan1788.extra.nuby.init.Constant;
import org.nutz.lang.random.R;

/**
 * 控制层基类
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/16
 * @version 1.0/0
 */
public class BaseModule {

    /**
     * 页面密钥
     * (尝试解决微信页面缓存问题)
     */
    protected String pageToken;
    /**
     * AJAX操作标识
     */
    protected boolean ok;
    /**
     * AJAX信息返回
     */
    protected String msg = Constant.DEF_MSG;
    /**
     * AJAX错误代码
     */
    protected String errCode = String.valueOf(Constant.SUCCESS);

    /**
     * 创建16位密钥
     */
    protected void createToken16() {
        this.pageToken = R.UU16();
    }

    /**
     * 设置错误代码
     * @param errCode
     */
    protected void setErrCode(Integer errCode) {
        this.errCode = String.valueOf(errCode);
    }

    /**
     * 成功状态
     */
    protected void initSuc() {
        this.ok = true;
        this.setErrCode(Constant.SUCCESS);
        this.msg = Constant.DEF_MSG;
    }
}
