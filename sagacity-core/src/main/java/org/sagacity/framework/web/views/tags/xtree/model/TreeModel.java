/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtree.model;

import java.io.Serializable;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TreeModel.java,Revision:v1.0,Date:May 24, 2008 10:04:32 AM $
 */
public class TreeModel implements Serializable {
	/**
	 * 节点id
	 */
	private String nodeId;
	
	/**
	 * 节点值
	 */
	private String nodeValue;
	
	/**
	 * 节点名称
	 */
	private String nodeName;
	
	/**
	 * 上层节点编号
	 */
	private String preNodeId;
	
	/**
	 * 是否为叶子节点
	 */
	private String isLeaf;
	
	/**
	 * 节点类型
	 */
	private String nodeType;
	
	/**
	 * 点击事件
	 */
	private String action;
	
	/**
	 * 节点图标
	 */
	private String nodeIcon="0";
	
	/**
	 * 节点打开图标
	 */
	private String openIcon;
	
	/**
	 * 节点关闭图标
	 */
	private String closeIcon;

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeValue
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	/**
	 * @param nodeValue the nodeValue to set
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	/**
	 * @return the preNodeId
	 */
	public String getPreNodeId() {
		return preNodeId;
	}

	/**
	 * @param preNodeId the preNodeId to set
	 */
	public void setPreNodeId(String preNodeId) {
		this.preNodeId = preNodeId;
	}

	/**
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeIcon
	 */
	public String getNodeIcon() {
		return nodeIcon;
	}

	/**
	 * @param nodeIcon the nodeIcon to set
	 */
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}

	/**
	 * @return the openIcon
	 */
	public String getOpenIcon() {
		return openIcon;
	}

	/**
	 * @param openIcon the openIcon to set
	 */
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}

	/**
	 * @return the closeIcon
	 */
	public String getCloseIcon() {
		return closeIcon;
	}

	/**
	 * @param closeIcon the closeIcon to set
	 */
	public void setCloseIcon(String closeIcon) {
		this.closeIcon = closeIcon;
	}
}
