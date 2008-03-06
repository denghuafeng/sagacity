package org.sagacity.lazyworker.domain.model;

import java.util.Date;

/**
 * SysUserGroups entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserGroups implements java.io.Serializable {

	// Fields

	private Long sequenceNo;
	private SysGroups sysGroups;
	private SysLoginAccount sysLoginAccount;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Long isActive;

	// Constructors

	/** default constructor */
	public SysUserGroups() {
	}

	/** minimal constructor */
	public SysUserGroups(Long sequenceNo, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysUserGroups(Long sequenceNo, SysGroups sysGroups,
			SysLoginAccount sysLoginAccount, String createBy, Date createDate,
			String updateBy, Date updateDate, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.sysGroups = sysGroups;
		this.sysLoginAccount = sysLoginAccount;
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

	public SysLoginAccount getSysLoginAccount() {
		return this.sysLoginAccount;
	}

	public void setSysLoginAccount(SysLoginAccount sysLoginAccount) {
		this.sysLoginAccount = sysLoginAccount;
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