/**
 * 
 */
package org.sagacity.exoteric.security.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.dao.UserCache;
import org.sagacity.exoteric.security.dao.SecurityDAO;
import org.sagacity.exoteric.security.resourcedetails.Resource;
import org.sagacity.exoteric.security.resourcedetails.ResourceCache;
import org.sagacity.exoteric.security.resourcedetails.ResourceDetails;
import org.sagacity.exoteric.security.service.SecurityCacheManager;
import org.sagacity.framework.service.BaseService;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SecurityCacheManagerImpl.java,Revision:v1.0,Date:2008-12-16 上午11:32:38 $
 */
public class SecurityCacheManagerImpl extends BaseService implements
		SecurityCacheManager {

	private UserCache userCache;

	private ResourceCache resourceCache;

	private SecurityDAO resourcesDAO;

	private boolean cacheInitialized = false;

	/**
	 * 修改User时更改userCache
	 * 
	 * @param user
	 * @param orgUsername
	 *//*
	public void modifyUserInCache(Object user, String orgUsername) {

		UserDetails ud = userCache.getUserFromCache(orgUsername);
		if (ud != null)
			userCache.removeUserFromCache(orgUsername);
		userDetailsInCache(user);

	}*/

	/**
	 * 修改Resource时更改resourceCache
	 */
	public void modifyResourceInCache(Resource resource, String orgResourcename) {
		ResourceDetails rd = resourceCache
				.getAuthorityFromCache(orgResourcename);
		if (rd != null)
			resourceCache.removeAuthorityFromCache(orgResourcename);
		resourceDetailsInCache(resource);
	}

	public void initUserCache() {
	}

	/**
	 * 初始化resourceCache
	 */
	public void initResourceCache() {
		if (resourceCache.getUrlResStrings() != null
				&& resourceCache.getUrlResStrings().size() <= 0) {
			synchronized (this) {
				Collection resources = resourcesDAO.getUrlResourcesDetails();
				for (Iterator iter = resources.iterator(); iter.hasNext();) {
					Resource resource = (Resource) iter.next();
					resourceCache.putAuthorityInCache(resource);
				}
				cacheInitialized = true;
			}
		}
	}

	/**
	 * 刷新resource cache(不刷新也可) 见applicationContext-quartz.xml下cacheTrigger
	 * 
	 * @return
	 */
	public void refreshResourceCache() {
		cacheInitialized = false;
		try {
			resourceCache.getCache().removeAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		initResourceCache();
	}

	/**
	 * 获取所有的url资源
	 */
	public List getUrlResStrings() {
		if (resourceCache.getUrlResStrings() == null
				|| resourceCache.getUrlResStrings().size() == 0)
			refreshResourceCache();
		return resourceCache.getUrlResStrings();
	}

	/**
	 * 获取所有的Funtion资源
	 */
	public List getFunctions() {
		return resourceCache.getFunctions();
	}

	/**
	 * 获取所有的Component资源
	 */
	public List getComponents() {
		return resourceCache.getComponents();
	}

	/**
	 * 根据资源串获取资源
	 */
	public ResourceDetails getAuthorityFromCache(String resString) {
		return resourceCache.getAuthorityFromCache(resString);
	}

	/**
	 * 获取resource相应的权限并置于resourcecache中
	 * 
	 * @param resource
	 */
	private void resourceDetailsInCache(Resource resource) {
		GrantedAuthority[] authorities = resource.getAuthorities();
		ResourceDetails rd = new Resource(resource.getResString(), resource
				.getResType(), authorities);
		resourceCache.putAuthorityInCache(rd);
	}

	public void setResourcesDAO(SecurityDAO resourcesDAO) {
		this.resourcesDAO = resourcesDAO;
	}

	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public ResourceCache getResourceCache() {
		return resourceCache;
	}

	public UserCache getUserCache() {
		return userCache;
	}
}
