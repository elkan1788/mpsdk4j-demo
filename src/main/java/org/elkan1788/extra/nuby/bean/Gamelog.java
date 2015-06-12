package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
* 游戏记录
*/
@Table("nuby_wx_gamelog")
public class Gamelog {

	/**
	 * 记录唯一标识
	 */
	@Id
	@Column("gamelog_id")
	private Integer gamelogId;
	/**
	 * 微信ID
	 */
	@Column("openid")
    private String openid;
    @One(target = Followers.class, field = "openid", key = "openid")
	private Followers followers;
    /**
     * 移动端类型
     */
    @Column("phone_type")
    private String phoneType;
	/**
	 * 关卡1时间
	 */
	@Column("time1")
	private Integer time1;
	/**
	 * 关卡2时间
	 */
	@Column("time2")
	private Integer time2;
	/**
	 * 关卡3时间
	 */
	@Column("time3")
	private Integer time3;
	/**
	 * 关卡4时间
	 */
	@Column("time4")
	private Integer time4;
    /**
     * 总耗时
     */
    @Column("total_time")
    private Integer totalTime;
	/**
	 * 1 表示通关成功， 0 表示通关失败
	 */
	@Column("pass")
	private String pass;
    /**
     * 页面凭证
     */
    @Column("token")
    private String token;
	/**
	 * 记录时间
	 */
	@Column("gamelog_time")
	private Date gamelogTime;

    private String headImg;
    private String nickName;

	public Gamelog() {
	}

	public Gamelog(Integer gamelogId) {
		this.gamelogId = gamelogId;
	}

	public Integer getGamelogId() {
		return gamelogId;
	}

	public void setGamelogId(Integer gamelogId) {
		this.gamelogId = gamelogId;
	}

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }



    public String getPhoneType() {
        return phoneType;
    }

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
//        this.followers = followers;
        if (null != followers) {
            this.setHeadImg(followers.getHeadimgurl());
            this.setNickName(followers.getNickname());
        }
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getTime1() {
		return time1;
	}

	public void setTime1(Integer time1) {
		this.time1 = time1;
	}

	public Integer getTime2() {
		return time2;
	}

	public void setTime2(Integer time2) {
		this.time2 = time2;
	}

	public Integer getTime3() {
		return time3;
	}

	public void setTime3(Integer time3) {
		this.time3 = time3;
	}

	public Integer getTime4() {
		return time4;
	}

	public void setTime4(Integer time4) {
		this.time4 = time4;
	}

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getGamelogTime() {
		return gamelogTime;
	}

	public void setGamelogTime(Date gamelogTime) {
		this.gamelogTime = gamelogTime;
	}

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
	public String toString() {
		return "Gamelog{" +
				"gamelogId=" + gamelogId +
//				", openid='" + flw.getOpenid() + '\'' +
				", phoneType='" + phoneType + '\'' +
				", time1=" + time1 +
				", time2=" + time2 +
				", time3=" + time3 +
				", time4=" + time4 +
				", totalTime=" + totalTime +
				", pass='" + pass + '\'' +
				", token='" + token + '\'' +
				", gamelogTime=" + gamelogTime +
				'}';
	}
}