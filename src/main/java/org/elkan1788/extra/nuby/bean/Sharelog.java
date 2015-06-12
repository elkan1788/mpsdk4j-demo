package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.*;

/**
* 分享记录
*/
@Table("nuby_wx_sharelog")
public class Sharelog {

	/**
	 * 记录唯一标识
	 */
	@Id
	@Column("sharelog_id")
	private Integer sharelogId;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
    @One(target = Followers.class, field = "openid", key = "openid")
    private Followers followers;
	/**
	 * 分享朋友
	 */
	@Column("friend")
	private Integer friend;
	/**
	 * 分享微博
	 */
	@Column("weibo")
	private Integer weibo;
	/**
	 * 分享朋友圈
	 */
	@Column("circle")
	private Integer circle;
	/**
	 * 分享QQ
	 */
	@Column("qq")
	private Integer qq;

    private String headImg;
    private String nickName;

	public Sharelog() {
	}

	public Sharelog(Integer sharelogId) {
		this.sharelogId = sharelogId;
	}

	public Integer getSharelogId() {
		return sharelogId;
	}

	public void setSharelogId(Integer sharelogId) {
		this.sharelogId = sharelogId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        if (null != followers) {
            this.setHeadImg(followers.getHeadimgurl());
            this.setNickName(followers.getNickname());
        }
    }

    public Integer getFriend() {
		return friend;
	}

	public void setFriend(Integer friend) {
		this.friend = friend;
	}

	public Integer getWeibo() {
		return weibo;
	}

	public void setWeibo(Integer weibo) {
		this.weibo = weibo;
	}

	public Integer getCircle() {
		return circle;
	}

	public void setCircle(Integer circle) {
		this.circle = circle;
	}

	public Integer getQq() {
		return qq;
	}

	public void setQq(Integer qq) {
		this.qq = qq;
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
		return "Sharelog{" +
				"sharelogId=" + sharelogId +
				", openid='" + openid + '\'' +
				", friend=" + friend +
				", weibo=" + weibo +
				", circle=" + circle +
				", qq=" + qq +
				'}';
	}
}