package org.sagacity.lazyworker.domain.model;

import java.util.HashSet;
import java.util.Set;

/**
 * SysFavoriteType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFavoriteType implements java.io.Serializable {

	// Fields

	private Long typeNo;
	private SysLoginAccount sysLoginAccount;
	private String typeName;
	private Long isActive;
	private Set sysUserFavorites = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysFavoriteType() {
	}

	/** minimal constructor */
	public SysFavoriteType(Long typeNo, String typeName, Long isActive) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysFavoriteType(Long typeNo, SysLoginAccount sysLoginAccount,
			String typeName, Long isActive, Set sysUserFavorites) {
		this.typeNo = typeNo;
		this.sysLoginAccount = sysLoginAccount;
		this.typeName = typeName;
		this.isActive = isActive;
		this.sysUserFavorites = sysUserFavorites;
	}

	// Property accessors

	public Long getTypeNo() {
		return this.typeNo;
	}

	public void setTypeNo(Long typeNo) {
		this.typeNo = typeNo;
	}

	public SysLoginAccount getSysLoginAccount() {
		return this.sysLoginAccount;
	}

	public void setSysLoginAccount(SysLoginAccount sysLoginAccount) {
		this.sysLoginAccount = sysLoginAccount;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

}