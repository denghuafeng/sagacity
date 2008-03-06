package org.sagacity.lazyworker.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysGroups entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysGroups implements java.io.Serializable {

	// Fields

	private Long groupNo;
	private String groupName;
	private String comment;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Long isActive;
	private Set sysGroupRoleses = new HashSet(0);
	private Set sysUserGroupses = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysGroups() {
	}

	/** minimal constructor */
	public SysGroups(Long groupNo, Long isActive) {
		this.groupNo = groupNo;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysGroups(Long groupNo, String groupName, String comment,
			String createBy, Date createDate, String updateBy, Date updateDate,
			Long isActive, Set sysGroupRoleses, Set sysUserGroupses) {
		this.groupNo = groupNo;
		this.groupName = groupName;
		this.comment = comment;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.isActive = isActive;
		this.sysGroupRoleses = sysGroupRoleses;
		this.sysUserGroupses = sysUserGroupses;
	}

	// Property accessors

	public Long getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Set getSysGroupRoleses() {
		return this.sysGroupRoleses;
	}

	public void setSysGroupRoleses(Set sysGroupRoleses) {
		this.sysGroupRoleses = sysGroupRoleses;
	}

	public Set getSysUserGroupses() {
		return this.sysUserGroupses;
	}

	public void setSysUserGroupses(Set sysUserGroupses) {
		this.sysUserGroupses = sysUserGroupses;
	}

}