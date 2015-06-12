package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 系统用户
*/
@Table("nuby_wx_sysuser")
public class Sysuser {

	/**
	 * 管理员唯一标识
	 */
	@Id
	@Column("user_id")
	private Integer userId;
	/**
	 * 用户名
	 */
	@Column("account")
	private String account;
	/**
	 * 密码
	 */
	@Column("actpswd")
	private String actpswd;
	/**
	 * 用户昵称
	 */
	@Column("nickname")
	private String nickname;
	/**
	 * 登录次数
	 */
	@Column("login_cnt")
	private Integer loginCnt;
	/**
	 * 最后登录时间
	 */
	@Column("last_login_time")
	private Date lastLoginTime;
	/**
	 * 1 表示正常，0 表示锁定
	 */
	@Column("lock_status")
	private String lockStatus;

	public Sysuser() {
	}

	public Sysuser(Integer userId) {
		this.userId = userId;
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

	public String getActpswd() {
		return actpswd;
	}

	public void setActpswd(String actpswd) {
		this.actpswd = actpswd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Override
	public String toString() {
		return "Sysuser{" +
				"userId=" + userId +
				", account='" + account + '\'' +
				", actpswd='" + actpswd + '\'' +
				", nickname='" + nickname + '\'' +
				", loginCnt=" + loginCnt +
				", lastLoginTime=" + lastLoginTime +
				", lockStatus='" + lockStatus + '\'' +
				'}';
	}
}