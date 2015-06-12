package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 优惠券
*/
@Table("nuby_wx_coupon")
public class Coupon {

	/**
	 * 券唯一标识
	 */
	@Id
	@Column("con_id")
	private Integer conId;
	/**
	 * 券名称
	 */
	@Column("con_name")
	private String conName;
	/**
	 * 券缩略图
	 */
	@Column("con_thumb")
	private String conThumb;
	/**
	 * 券说明
	 */
	@Column("con_desc")
	private String conDesc;
	/**
	 * 券链接
	 */
	@Column("con_link")
	private String conLink;
	/**
	 * 券数量
	 */
	@Column("con_num")
	private Integer conNum;
	/**
	 * 已发放
	 */
	@Column("send_num")
	private Integer sendNum;
	/**
	 * 有效时间
	 */
	@Column("valid_time")
	private Date validTime;
	/**
	 * 创建时间
	 */
	@Column("create_time")
	private Date createTime;
	/**
	 * 是否生效
	 */
	@Column("valid")
	private String valid;

	public Coupon() {
	}

	public Coupon(Integer conId) {
		this.conId = conId;
	}

	public Integer getConId() {
		return conId;
	}

	public void setConId(Integer conId) {
		this.conId = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConThumb() {
		return conThumb;
	}

	public void setConThumb(String conThumb) {
		this.conThumb = conThumb;
	}

	public String getConDesc() {
		return conDesc;
	}

	public void setConDesc(String conDesc) {
		this.conDesc = conDesc;
	}

	public String getConLink() {
		return conLink;
	}

	public void setConLink(String conLink) {
		this.conLink = conLink;
	}

	public Integer getConNum() {
		return conNum;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Coupon{" +
				"conId=" + conId +
				", conName='" + conName + '\'' +
				", conThumb='" + conThumb + '\'' +
				", conDesc='" + conDesc + '\'' +
				", conLink='" + conLink + '\'' +
				", conNum=" + conNum +
				", sendNum=" + sendNum +
				", validTime=" + validTime +
				", createTime=" + createTime +
				", valid='" + valid + '\'' +
				'}';
	}
}