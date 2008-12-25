/**
 * 
 */
package org.sagacity.exoteric.security.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *@project sagacity-core
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourceVO.java,Revision:v1.0,Date:2008-12-10 下午04:33:37 $
 */
public class ResourcesVO implements Serializable {
	/**
	 * url
	 */
	private String resString;

	/**
	 * 功能编号
	 */
	private String resNo;

	/**
	 * 功能名称
	 */
	private String resName;

	/**
	 * 说明
	 */
	private String remark;

	/**
	 * @return the resString
	 */
	public String getResString() {
		return resString;
	}

	/**
	 * @param resString the resString to set
	 */
	public void setResString(String resString) {
		this.resString = resString;
	}

	/**
	 * @return the resNo
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param resNo the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the resType
	 */
	public String getResType() {
		return resType;
	}

	/**
	 * @param resType the resType to set
	 */
	public void setResType(String resType) {
		this.resType = resType;
	}

	/**
	 * @return the resIndex
	 */
	public Long getResIndex() {
		return resIndex;
	}

	/**
	 * @param resIndex the resIndex to set
	 */
	public void setResIndex(Long resIndex) {
		this.resIndex = resIndex;
	}

	/**
	 * @return the moduleNo
	 */
	public Long getModuleNo() {
		return moduleNo;
	}

	/**
	 * @param moduleNo the moduleNo to set
	 */
	public void setModuleNo(Long moduleNo) {
		this.moduleNo = moduleNo;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the moduleIcon
	 */
	public String getModuleIcon() {
		return moduleIcon;
	}

	/**
	 * @param moduleIcon the moduleIcon to set
	 */
	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	/**
	 * @return the moduleIndex
	 */
	public Long getModuleIndex() {
		return moduleIndex;
	}

	/**
	 * @param moduleIndex the moduleIndex to set
	 */
	public void setModuleIndex(Long moduleIndex) {
		this.moduleIndex = moduleIndex;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the roleNo
	 */
	public String getRoleNo() {
		return roleNo;
	}

	/**
	 * @param roleNo the roleNo to set
	 */
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	/**
	 * @return the isActive
	 */
	public Long getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 功能类型
	 */
	private String resType;

	/**
	 * 功能显示顺序
	 */
	private Long resIndex;

	/**
	 * 所属模块编号
	 */
	private Long moduleNo;

	/**
	 * 所属模块名称
	 */
	private String moduleName;

	/**
	 * 模块图标
	 */
	private String moduleIcon;

	/**
	 * 模块顺序
	 */
	private Long moduleIndex;

	/**
	 * 角色代码
	 */
	private String roleCode;

	/**
	 * 角色编号
	 */
	private String roleNo;

	/**
	 * 是否有效
	 */
	private Long isActive;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 修改人
	 */
	private String updateBy;

	/**
	 * 修改日期
	 */
	private Date updateDate;
}
