package org.elkan1788.extra.nuby.session;

import org.nutz.lang.random.R;

import java.util.Date;

/**
 * 管理员用户会话
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/13
 * @version 1.0.0
 */
public class UserSession {

    private String sessionId = R.UU16();

    private Integer userId;

    private String account;

    private String nickName;

    private Integer loginCnt;

    private Date lastLoginTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getLoginCnt() {
        return loginCnt;
    }

    public void setLoginCnt(Integer loginCnt) {
        this.loginCnt = loginCnt;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
