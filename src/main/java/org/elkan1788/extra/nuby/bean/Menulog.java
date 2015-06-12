package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 微信菜单点击记录
*/
@Table("nuby_wx_menulog")
public class Menulog {

	/**
	 * 记录唯一标识
	 */
	@Id
	@Column("menulog_id")
	private Integer menulogId;
	/**
	 * 微信ID
	 */
	@Column("openid")
	private String openid;
	/**
	 * 菜单唯一标识
	 */
	@Column("menu_id")
	private Integer menuId;
	/**
	 * 创建时间
	 */
	@Column("create_time")
	private Date createTime;

	public Menulog() {
	}

	public Menulog(Integer menulogId) {
		this.menulogId = menulogId;
	}

	public Integer getMenulogId() {
		return menulogId;
	}

	public void setMenulogId(Integer menulogId) {
		this.menulogId = menulogId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Menulog{" +
				"menulogId=" + menulogId +
				", openid='" + openid + '\'' +
				", menuId=" + menuId +
				", createTime=" + createTime +
				'}';
	}
}