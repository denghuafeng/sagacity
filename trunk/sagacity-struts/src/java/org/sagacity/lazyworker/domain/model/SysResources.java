package org.sagacity.lazyworker.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysResources entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysResources implements java.io.Serializable {

	// Fields

	private Long resNo;
	private SysModules sysModules;
	private String resName;
	private String comment;
	private Long pageIndex;
	private String resValue;
	private Long resType;
	private String pageIcon;
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	private Long isActive;
	private Set sysUserFavorites = new HashSet(0);
	private Set sysRoleResourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysResources() {
	}

	/** minimal constructor */
	public SysResources(Long resNo, Long pageIndex, Long isActive) {
		this.resNo = resNo;
		this.pageIndex = pageIndex;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysResources(Long resNo, SysModules sysModules, String resName,
			String comment, Long pageIndex, String resValue, Long resType,
			String pageIcon, Date createDate, String createBy, Date updateDate,
			String updateBy, Long isActive, Set sysUserFavorites,
			Set sysRoleResourceses) {
		this.resNo = resNo;
		this.sysModules = sysModules;
		this.resName = resName;
		this.comment = comment;
		this.pageIndex = pageIndex;
		this.resValue = resValue;
		this.resType = resType;
		this.pageIcon = pageIcon;
		this.createDate = createDate;
		this.createBy = createBy;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.isActive = isActive;
		this.sysUserFavorites = sysUserFavorites;
		this.sysRoleResourceses = sysRoleResourceses;
	}

	// Property accessors

	public Long getResNo() {
		return this.resNo;
	}

	public void setResNo(Long resNo) {
		this.resNo = resNo;
	}

	public SysModules getSysModules() {
		return this.sysModules;
	}

	public void setSysModules(SysModules sysModules) {
		this.sysModules = sysModules;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getResValue() {
		return this.resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}

	public Long getResType() {
		return this.resType;
	}

	public void setResType(Long resType) {
		this.resType = resType;
	}

	public String getPageIcon() {
		return this.pageIcon;
	}

	public void setPageIcon(String pageIcon) {
		this.pageIcon = pageIcon;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Long getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public Set getSysUserFavorites() {
		return this.sysUserFavorites;
	}

	public void setSysUserFavorites(Set sysUserFavorites) {
		this.sysUserFavorites = sysUserFavorites;
	}

	public Set getSysRoleResourceses() {
		return this.sysRoleResourceses;
	}

	public void setSysRoleResourceses(Set sysRoleResourceses) {
		this.sysRoleResourceses = sysRoleResourceses;
	}

}