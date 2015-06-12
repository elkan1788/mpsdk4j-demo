package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 微信自定菜单
*/
@Table("nuby_wx_menu")
public class Menu {

	/**
	 * 菜单唯一标识
	 */
	@Id
	@Column("menu_id")
	private Integer menuId;
	/**
	 * 父菜单标识
	 */
	@Column("parent_id")
	private Integer parentId;
	/**
	 * 菜单名称
	 */
	@Column("menu_name")
	private String menuName;
	/**
	 * C 点击菜单 V 视图菜单
	 */
	@Column("menu_type")
	private String menuType;
	/**
	 * 菜单值
	 */
	@Column("menu_key")
	private String menuKey;
	/**
	 * 显示次序
	 */
	@Column("sort")
	private Integer sort;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private Date updateTime;

	public Menu() {
	}

	public Menu(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"menuId=" + menuId +
				", parentId=" + parentId +
				", menuName='" + menuName + '\'' +
				", menuType='" + menuType + '\'' +
				", menuKey='" + menuKey + '\'' +
				", sort=" + sort +
				", updateTime=" + updateTime +
				'}';
	}
}