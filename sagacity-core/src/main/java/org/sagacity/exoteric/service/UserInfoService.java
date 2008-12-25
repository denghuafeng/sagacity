/**
 * 
 */
package org.sagacity.exoteric.service;

import org.sagacity.framework.exception.BaseException;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>获取当前登陆系统用户的个人信息</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:UserInfoService.java,Revision:v1.0,Date:2008-12-16 上午11:33:10 $
 */
public interface UserInfoService {
	/**
	 * 获取当前系统登陆用户信息
	 * @return
	 */
	public Object getCurrentUserInfo()throws BaseException;
}
