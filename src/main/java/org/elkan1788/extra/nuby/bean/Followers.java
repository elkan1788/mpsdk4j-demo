package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Lang;
import org.nutz.repo.Base64;

import java.util.Date;

/**
* 微信粉丝用户信息
*/
@Table("nuby_wx_followers")
public class Followers {

	/**
	 * 粉丝唯一标识
	 */
	@Id
	@Column("follower_id")
	private Integer followerId;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
	/**
	 * 用户昵称
	 */
	@Column("nickname")
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 
	 */
	@Column("sex")
	private String sex;
	/**
	 * 头像
	 */
	@Column("headimgurl")
	private String headimgurl;
	/**
	 * 城市
	 */
	@Column("city")
	private String city;
	/**
	 * 省份
	 */
	@Column("province")
	private String province;
	/**
	 * 国家
	 */
	@Column("country")
	private String country;
	/**
	 * 语言
	 */
	@Column("language")
	private String language;
	/**
	 * 关注时间
	 */
	@Column("sub_time")
	private Date subTime;
	/**
	 * 退订时间
	 */
	@Column("unsub_time")
	private Date unsubTime;
	/**
	 * 1表示订阅中，0 表示退订
	 */
	@Column("user_status")
	private String userStatus;

    private boolean encrypt = false;

    public Followers() {
	}

	public Followers(Integer followerId) {
		this.followerId = followerId;
	}

	public Integer getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Integer followerId) {
		this.followerId = followerId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
        return  this.nickname;
	}

	public void setNickname(String nickname) {
        if (!isEncrypt()) {
            byte[] decode = new byte[0];
            try {
                decode = Base64.decodeFast(nickname);
            } catch (Exception e) {
                throw Lang.wrapThrow(e,
                        "使用Base64解密用户[%s]昵称[%s]异常!!!", openid, nickname);
            }
            this.nickname = new String(decode);
        } else {
            try {
                this.nickname = Base64.encodeToString(nickname.getBytes(),false);
            } catch (Exception e) {
                throw Lang.wrapThrow(e,
                        "使用Base64加密用户[%s]昵称[%s]异常!!!", openid, nickname);
            }
        }
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	public Date getUnsubTime() {
		return unsubTime;
	}

	public void setUnsubTime(Date unsubTime) {
		this.unsubTime = unsubTime;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    @Override
	public String toString() {
		return "Followers{" +
				"followerId=" + followerId +
				", openid='" + openid + '\'' +
				", nickname='" + nickname + '\'' +
				", sex='" + sex + '\'' +
				", headimgurl='" + headimgurl + '\'' +
				", city='" + city + '\'' +
				", province='" + province + '\'' +
				", country='" + country + '\'' +
				", language='" + language + '\'' +
				", subTime=" + subTime +
				", unsubTime=" + unsubTime +
				", userStatus='" + userStatus + '\'' +
				'}';
	}
}