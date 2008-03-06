package org.sagacity.lazyworker.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysRoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoles implements java.io.Serializable {

	// Fields

	private Long roleNo;
	private String roleName;
	private String roleCode;
	private String comment;
	private Long fatherRole;
	private Date createDate;
	private String createBy;
	private Long isActive;
	private Long isExtend;
	private Date updateDate;
	private String updateBy;
	private Set sysGroupRoleses = new HashSet(0);
	private Set sysUserRoleses = new HashSet(0);
	private Set sysRoleResourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysRoles() {
	}

	/** minimal constructor */
	public SysRoles(Long roleNo, String roleName, String roleCode,
			Long isActive, Long isExtend) {
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.isActive = isActive;
		this.isExtend = isExtend;
	}

	/** full constructor */
	public SysRoles(Long roleNo, String roleName, String roleCode,
			String comment, Long fatherRole, Date createDate, String createBy,
			Long isActive, Long isExtend, Date updateDate, String updateBy,
			Set sysGroupRoleses, Set sysUserRoleses, Set sysRoleResourceses) {
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.comment = comment;
		this.fatherRole = fatherRole;
		this.createDate = createDate;
		this.createBy = createBy;
		this.isActive = isActive;
		this.isExtend = isExtend;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.sysGroupRoleses = sysGroupRoleses;
		this.sysUserRoleses = sysUserRoleses;
		this.sysRoleResourceses = sysRoleResourceses;
	}

	// Property accessors

	public Long getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(Long roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getFatherRole() {
		return this.fatherRole;
	}

	public void setFatherRole(Long fatherRole) {
		this.fatherRole = fatherRole;
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

	public Long getIsExtend() {
		return this.isExtend;
	}

	public void setIsExtend(Long isExtend) {
		this.isExtend = isExtend;
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

	public Set getSysGroupRoleses() {
		return this.sysGroupRoleses;
	}

	public void setSysGroupRoleses(Set sysGroupRoleses) {
		this.sysGroupRoleses = sysGroupRoleses;
	}

	public Set getSysUserRoleses() {
		return this.sysUserRoleses;
	}

	public void setSysUserRoleses(Set sysUserRoleses) {
		this.sysUserRoleses = sysUserRoleses;
	}

	public Set getSysRoleResourceses() {
		return this.sysRoleResourceses;
	}

	public void setSysRoleResourceses(Set sysRoleResourceses) {
		this.sysRoleResourceses = sysRoleResourceses;
	}

}