package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 试用活动信息
*/
@Table("nuby_wx_trail")
public class Trail {

	/**
	 * 活动唯一标识
	 */
	@Id
	@Column("trail_id")
	private Integer trailId;
	/**
	 * 活动名称
	 */
	@Column("trail_name")
	private String trailName;
	/**
	 * 活动图片
	 */
	@Column("trail_img")
	private String trailImg;
	/**
	 * 活动内容
	 */
	@Column("trail_content")
	private String trailContent;
	/**
	 * 有效时间
	 */
	@Column("valid_time")
	private Date validTime;
	/**
	 * 是否生效
	 */
	@Column("valid")
	private String valid;
    /**
     * 创建时间
     */
    @Column("create_time")
    private Date createTime;

	public Trail() {
	}

	public Trail(Integer trailId) {
		this.trailId = trailId;
	}

	public Integer getTrailId() {
		return trailId;
	}

	public void setTrailId(Integer trailId) {
		this.trailId = trailId;
	}

	public String getTrailName() {
		return trailName;
	}

	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}

	public String getTrailImg() {
		return trailImg;
	}

	public void setTrailImg(String trailImg) {
		this.trailImg = trailImg;
	}

	public String getTrailContent() {
		return trailContent;
	}

	public void setTrailContent(String trailContent) {
		this.trailContent = trailContent;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
	public String toString() {
		return "Trail{" +
				"trailId=" + trailId +
				", trailName='" + trailName + '\'' +
				", trailImg='" + trailImg + '\'' +
				", trailContent='" + trailContent + '\'' +
				", validTime=" + validTime +
				", valid='" + valid + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}