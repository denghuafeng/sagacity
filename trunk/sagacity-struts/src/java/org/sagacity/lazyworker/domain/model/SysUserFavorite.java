package org.sagacity.lazyworker.domain.model;

import java.util.Date;

/**
 * SysUserFavorite entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserFavorite implements java.io.Serializable {

	// Fields

	private Long favoriteNo;
	private SysFavoriteType sysFavoriteType;
	private SysResources sysResources;
	private String favoriteName;
	private Long pageIndex;
	private String empNo;
	private Date createDate;
	private String createBy;
	private String updateBy;
	private Date updateDate;
	private Long isActive;

	// Constructors

	/** default constructor */
	public SysUserFavorite() {
	}

	/** minimal constructor */
	public SysUserFavorite(Long favoriteNo, Date createDate, Long isActive) {
		this.favoriteNo = favoriteNo;
		this.createDate = createDate;
		this.isActive = isActive;
	}

	/** full constructor */
	public SysUserFavorite(Long favoriteNo, SysFavoriteType sysFavoriteType,
			SysResources sysResources, String favoriteName, Long pageIndex,
			String empNo, Date createDate, String createBy, String updateBy,
			Date updateDate, Long isActive) {
		this.favoriteNo = favoriteNo;
		this.sysFavoriteType = sysFavoriteType;
		this.sysResources = sysResources;
		this.favoriteName = favoriteName;
		this.pageIndex = pageIndex;
		this.empNo = empNo;
		this.createDate = createDate;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getFavoriteNo() {
		return this.favoriteNo;
	}

	public void setFavoriteNo(Long favoriteNo) {
		this.favoriteNo = favoriteNo;
	}

	public SysFavoriteType getSysFavoriteType() {
		return this.sysFavoriteType;
	}

	public void setSysFavoriteType(SysFavoriteType sysFavoriteType) {
		this.sysFavoriteType = sysFavoriteType;
	}

	public SysResources getSysResources() {
		return this.sysResources;
	}

	public void setSysResources(SysResources sysResources) {
		this.sysResources = sysResources;
	}

	public String getFavoriteName() {
		return this.favoriteName;
	}

	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	public Long getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
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