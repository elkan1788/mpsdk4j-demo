package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
* 试用活动登记
*/
@Table("nuby_wx_traillog")
public class Traillog {

	/**
	 * 记录唯一标识
	 */
	@Id
	@Column("trailog_id")
	private Integer trailogId;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
    @One(target = Followers.class, field = "openid", key = "openid")
    private Followers followers;
	/**
	 * 活动唯一标识
	 */
	@Column("trail_id")
	private Integer trailId;
    @One(target = Trail.class, field = "trailId")
    private Trail trail;
	/**
	 * 姓名
	 */
	@Column("username")
	private String username;
	/**
	 * 手机号码
	 */
	@Column("phonenum")
	private String phonenum;
	/**
	 * 地址
	 */
	@Column("address")
	private String address;
	/**
	 * 邮编
	 */
	@Column("zipcode")
	private String zipcode;
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
	 * 创建时间
	 */
	@Column("create_time")
	private Date createTime;

    private String headImg;
    private String nickName;
    private String trailName;

	public Traillog() {
	}

	public Traillog(Integer trailogId) {
		this.trailogId = trailogId;
	}

	public Integer getTrailogId() {
		return trailogId;
	}

	public void setTrailogId(Integer trailogId) {
		this.trailogId = trailogId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getTrailId() {
		return trailId;
	}

	public void setTrailId(Integer trailId) {
		this.trailId = trailId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
            this.setHeadImg(followers.getHeadimgurl());
            this.setNickName(followers.getNickname());
        }
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        if (null != trail) {
            this.setTrailName(trail.getTrailName());
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

    public String getTrailName() {
        return trailName;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
    }

    @Override
	public String toString() {
		return "Traillog{" +
				"trailogId=" + trailogId +
				", openid='" + openid + '\'' +
				", trailId=" + trailId +
				", username='" + username + '\'' +
				", phonenum='" + phonenum + '\'' +
				", address='" + address + '\'' +
				", zipcode='" + zipcode + '\'' +
				", city='" + city + '\'' +
				", province='" + province + '\'' +
				", createTime=" + createTime +
				'}';
	}
}