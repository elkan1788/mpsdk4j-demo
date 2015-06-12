package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
* 优惠券发送记录
*/
@Table("nuby_wx_couponlog")
public class Couponlog {

	/**
	 * 记录唯一标识
	 */
	@Id
	@Column("conlog_id")
	private Integer conlogId;
	/**
	 * 券唯一标识
	 */
	@Column("con_id")
	private Integer conId;
    @One(target = Coupon.class, field = "conId")
    private Coupon coupon;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
    @One(target = Followers.class, field = "openid", key = "openid")
    private Followers followers;
	/**
	 * 1 表示中奖信息，2 表示提示信息
	 */
	@Column("type")
	private String type;
	/**
	 * 创建时间
	 */
	@Column("create_time")
	private Date createTime;

    private String headImg;
    private String nickName;
    private String coupName;

	public Couponlog() {
	}

	public Couponlog(Integer conlogId) {
		this.conlogId = conlogId;
	}

	public Integer getConlogId() {
		return conlogId;
	}

	public void setConlogId(Integer conlogId) {
		this.conlogId = conlogId;
	}

	public Integer getConId() {
		return conId;
	}

	public void setConId(Integer conId) {
		this.conId = conId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
       if (null != followers) {
           this.setNickName(followers.getNickname());
           this.setHeadImg(followers.getHeadimgurl());
       }
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

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        if (null != coupon) {
            this.setCoupName(coupon.getConName());
        }
    }

    public String getCoupName() {
        return coupName;
    }

    public void setCoupName(String coupName) {
        this.coupName = coupName;
    }

    @Override
	public String toString() {
		return "Couponlog{" +
				"conlogId=" + conlogId +
				", conId=" + conId +
				", openid='" + openid + '\'' +
				", type='" + type + '\'' +
				", createTime=" + createTime +
				'}';
	}
}