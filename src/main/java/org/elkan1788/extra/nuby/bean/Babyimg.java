package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
* 宝宝相片
*/
@Table("nuby_wx_babyimg")
public class Babyimg {

	/**
	 * 相片唯一标识
	 */
	@Id
	@Column("img_id")
	private Integer imgId;
    /**
     * 宝宝名字
     */
    @Column("baby_name")
    private String babyName;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
    @One(target = Followers.class, field = "openid", key = "openid")
    private Followers followers;
	/**
	 * 相片1
	 */
	@Column("image1")
	private String image1;
	/**
	 * 相片2
	 */
	@Column("image2")
	private String image2;
	/**
	 * 相片1微信ID
	 */
	@Column("media_id1")
	private String mediaId1;
	/**
	 * 相片2微信ID
	 */
	@Column("media_id2")
	private String mediaId2;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private Date updateTime;

    @Column("valid")
    private String valide;

    //
    private String headImg;
    private String nickName;

	public Babyimg() {
	}

	public Babyimg(Integer imgId) {
		this.imgId = imgId;
	}

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getMediaId1() {
		return mediaId1;
	}

	public void setMediaId1(String mediaId1) {
		this.mediaId1 = mediaId1;
	}

	public String getMediaId2() {
		return mediaId2;
	}

	public void setMediaId2(String mediaId2) {
		this.mediaId2 = mediaId2;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

    public String getValide() {
        return valide;
    }

    public void setValide(String valide) {
        this.valide = valide;
    }

    @Override
	public String toString() {
		return "Babyimg{" +
				"imgId=" + imgId +
				", babyName='" + babyName + '\'' +
				", openid='" + openid + '\'' +
				", image1='" + image1 + '\'' +
				", image2='" + image2 + '\'' +
				", mediaId1='" + mediaId1 + '\'' +
				", mediaId2='" + mediaId2 + '\'' +
				", updateTime=" + updateTime +
				'}';
	}
}