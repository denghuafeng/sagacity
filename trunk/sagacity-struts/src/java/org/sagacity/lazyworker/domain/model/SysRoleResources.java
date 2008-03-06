package org.sagacity.lazyworker.domain.model;

import java.util.Date;

/**
 * SysRoleResources entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoleResources implements java.io.Serializable {

	// Fields

	private Long sequenceNo;
	private SysRoles sysRoles;
	private SysResources sysResources;
	private Date createDate;
	private String createBy;
	private Long isActive;

	// Constructors

	/** default constructor */
	public SysRoleResources() {
	}

	/** minimal constructor */
	public SysRoleResources(Long sequenceNo, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysRoleResources(Long sequenceNo, SysRoles sysRoles,
			SysResources sysResources, Date createDate, String createBy,
			Long isActive) {
		this.sequenceNo = sequenceNo;
		this.sysRoles = sysRoles;
		this.sysResources = sysResources;
		this.createDate = createDate;
		this.createBy = createBy;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public SysRoles getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(SysRoles sysRoles) {
		this.sysRoles = sysRoles;
	}

	public SysResources getSysResources() {
		return this.sysResources;
	}

	public void setSysResources(SysResources sysResources) {
		this.sysResources = sysResources;
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

	public Long getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

}