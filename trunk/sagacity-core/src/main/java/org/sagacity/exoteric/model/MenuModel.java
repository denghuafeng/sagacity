/**
 * 
 */
package org.sagacity.exoteric.model;

import java.io.Serializable;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>页面菜单数据模型</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:MenuModel.java,Revision:v1.0,Date:2008-12-9 上午11:31:06 $
 */
public class MenuModel implements Serializable {
	/**
	 * 菜单编号
	 */
	private String menuId;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 别名
	 */
	private String aliasName;
	
	/**
	 * url地址
	 */
	private String url;
	
	/**
	 * 上层节点
	 */
	private String preNode;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 是否为叶子节点
	 */
	private int leafFlag;
	
	/**
	 * 风格
	 */
	private String style;
	
	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName the aliasName to set
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public int getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(int leafFlag) {
		this.leafFlag = leafFlag;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the preNode
	 */
	public String getPreNode() {
		return preNode;
	}

	/**
	 * @param preNode the preNode to set
	 */
	public void setPreNode(String preNode) {
		this.preNode = preNode;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
