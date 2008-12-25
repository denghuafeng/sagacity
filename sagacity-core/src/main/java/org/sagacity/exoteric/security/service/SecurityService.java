/**
 * 
 */
package org.sagacity.exoteric.security.service;

import java.util.Collection;

import org.acegisecurity.userdetails.UserDetails;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>基于acegi的安全服务接口定义</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SecurityService.java,Revision:v1.0,Date:2008-12-16 上午11:16:11 $
 */
public interface SecurityService {
	/**
	 * 获取用户的权限信息
	 * @param username
	 * @return
	 */
	public UserDetails loadUserByUsername(String username);
	
	/**
	 * 获取所有资源对应的角色匹配关系
	 * @return
	 */
	public Collection getUrlResourcesDetails();
	
	/**
	 * 获取方法对应资源的角色匹配关系
	 * @return
	 */
	public Collection getMethodsResourceDetails();
}
