package org.sagacity.lazyworker.domain.model;

import java.util.Date;

/**
 * SysUserRoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserRoles implements java.io.Serializable {

	// Fields

	private Long sequenceNo;
	private SysLoginAccount sysLoginAccount;
	private SysRoles sysRoles;
	private Long isLock;
	private Date createDate;
	private String createBy;
	private Long isActive;

	// Constructors

	/** default constructor */
	public SysUserRoles() {
	}

	/** minimal constructor */
	public SysUserRoles(Long sequenceNo, Long isActive) {
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysUserRoles(Long sequenceNo, SysLoginAccount sysLoginAccount,
			SysRoles sysRoles, Long isLock, Date createDate, String createBy,
			Long isActive) {
		this.sequenceNo = sequenceNo;
		this.sysLoginAccount = sysLoginAccount;
		this.sysRoles = sysRoles;
		this.isLock = isLock;
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

	public SysLoginAccount getSysLoginAccount() {
		return this.sysLoginAccount;
	}

	public void setSysLoginAccount(SysLoginAccount sysLoginAccount) {
		this.sysLoginAccount = sysLoginAccount;
	}

	public SysRoles getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(SysRoles sysRoles) {
		this.sysRoles = sysRoles;
	}

	public Long getIsLock() {
		return this.isLock;
	}

	public void setIsLock(Long isLock) {
		this.isLock = isLock;
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