package org.sagacity.lazyworker.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysModules entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysModules implements java.io.Serializable {

	// Fields

	private Long moduleNo;
	private String moduleName;
	private String description;
	private Long pageIndex;
	private String pageIcon;
	private Long preModule;
	private Date createDate;
	private String createBy;
	private Long isSubsystem;
	private Long isActive;
	private Date updateDate;
	private String updateBy;
	private Set sysResourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysModules() {
	}

	/** minimal constructor */
	public SysModules(Long moduleNo, String moduleName, Long pageIndex,
			Long isSubsystem, Long isActive) {
		this.moduleNo = moduleNo;
		this.moduleName = moduleName;
		this.pageIndex = pageIndex;
		this.isSubsystem = isSubsystem;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysModules(Long moduleNo, String moduleName, String description,
			Long pageIndex, String pageIcon, Long preModule, Date createDate,
			String createBy, Long isSubsystem, Long isActive, Date updateDate,
			String updateBy, Set sysResourceses) {
		this.moduleNo = moduleNo;
		this.moduleName = moduleName;
		this.description = description;
		this.pageIndex = pageIndex;
		this.pageIcon = pageIcon;
		this.preModule = preModule;
		this.createDate = createDate;
		this.createBy = createBy;
		this.isSubsystem = isSubsystem;
		this.isActive = isActive;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.sysResourceses = sysResourceses;
	}

	// Property accessors

	public Long getModuleNo() {
		return this.moduleNo;
	}

	public void setModuleNo(Long moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageIcon() {
		return this.pageIcon;
	}

	public void setPageIcon(String pageIcon) {
		this.pageIcon = pageIcon;
	}

	public Long getPreModule() {
		return this.preModule;
	}

	public void setPreModule(Long preModule) {
		this.preModule = preModule;
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

	public Long getIsSubsystem() {
		return this.isSubsystem;
	}

	public void setIsSubsystem(Long isSubsystem) {
		this.isSubsystem = isSubsystem;
	}

	public Long getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
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

	public Set getSysResourceses() {
		return this.sysResourceses;
	}

	public void setSysResourceses(Set sysResourceses) {
		this.sysResourceses = sysResourceses;
	}

}