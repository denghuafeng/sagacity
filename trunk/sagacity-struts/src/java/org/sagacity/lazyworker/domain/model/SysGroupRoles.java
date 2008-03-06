package org.sagacity.lazyworker.domain.model;

import java.util.Date;

/**
 * SysGroupRoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysGroupRoles implements java.io.Serializable {

	// Fields

	private Long sequenceNo;
	private SysGroups sysGroups;
	private SysRoles sysRoles;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Long isActive;

	// Constructors

	/** default constructor */
	public SysGroupRoles() {
	}

	/** minimal constructor */
	public SysGroupRoles(Long sequenceNo, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysGroupRoles(Long sequenceNo, SysGroups sysGroups,
			SysRoles sysRoles, String createBy, Date createDate,
			String updateBy, Date updateDate, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.sysGroups = sysGroups;
		this.sysRoles = sysRoles;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public SysGroups getSysGroups() {
		return this.sysGroups;
	}

	public void setSysGroups(SysGroups sysGroups) {
		this.sysGroups = sysGroups;
	}

	public SysRoles getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(SysRoles sysRoles) {
		this.sysRoles = sysRoles;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

}