package org.sagacity.lazyworker.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysLoginAccount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysLoginAccount implements java.io.Serializable {

	// Fields

	private String empNo;
	private String loginName;
	private String password;
	private Date createDate;
	private String createBy;
	private Long isActive;
	private Date updateDate;
	private Set sysUserRoleses = new HashSet(0);
	private Set sysUserGroupses = new HashSet(0);
	private Set sysFavoriteTypes = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysLoginAccount() {
	}

	/** minimal constructor */
	public SysLoginAccount(String empNo, String loginName, Long isActive) {
		this.empNo = empNo;
		this.loginName = loginName;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysLoginAccount(String empNo, String loginName, String password,
			Date createDate, String createBy, Long isActive, Date updateDate,
			Set sysUserRoleses, Set sysUserGroupses, Set sysFavoriteTypes) {
		this.empNo = empNo;
		this.loginName = loginName;
		this.password = password;
		this.createDate = createDate;
		this.createBy = createBy;
		this.isActive = isActive;
		this.updateDate = updateDate;
		this.sysUserRoleses = sysUserRoleses;
		this.sysUserGroupses = sysUserGroupses;
		this.sysFavoriteTypes = sysFavoriteTypes;
	}

	// Property accessors

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set getSysUserRoleses() {
		return this.sysUserRoleses;
	}

	public void setSysUserRoleses(Set sysUserRoleses) {
		this.sysUserRoleses = sysUserRoleses;
	}

	public Set getSysUserGroupses() {
		return this.sysUserGroupses;
	}

	public void setSysUserGroupses(Set sysUserGroupses) {
		this.sysUserGroupses = sysUserGroupses;
	}

	public Set getSysFavoriteTypes() {
		return this.sysFavoriteTypes;
	}

	public void setSysFavoriteTypes(Set sysFavoriteTypes) {
		this.sysFavoriteTypes = sysFavoriteTypes;
	}

}