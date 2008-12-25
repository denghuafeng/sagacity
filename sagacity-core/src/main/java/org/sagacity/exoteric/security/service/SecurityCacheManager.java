/**
 * 
 */
package org.sagacity.exoteric.security.service;

import java.util.List;

import org.sagacity.exoteric.security.resourcedetails.ResourceDetails;

/**
 *@project regs 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author chenrenfei $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SecurityCacheManager.java,Revision:v1.0,Date:Jun 12, 2008 12:19:22 AM $
 */
public interface SecurityCacheManager {
	/**
     * 初始化userCache
     */
    public void initUserCache();

    /**
     * 初始化resourceCache
     */
    public void initResourceCache();

    /**
     * 刷新resourceCache
     */
    public void refreshResourceCache();

    /**
     * 获取所有的url资源
     */
    public List getUrlResStrings();

    /**
     * 获取所有的Funtion资源
     */
    public List getFunctions();

    /**
     * 获取所有的Component资源
     */
    public List getComponents();

    /**
     * 根据资源串获取资源
     */
    public ResourceDetails getAuthorityFromCache(String resString);
}
