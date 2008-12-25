/**
 * 
 */
package org.sagacity.exoteric.service;

import java.util.List;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>提供页面框架基本业务接口定义,当前用户的菜单获取</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:WebFrameService.java,Revision:v1.0,Date:2008-12-9 上午11:22:15 $
 */
public interface WebFrameService {
	/**
	 * 获取当前用户的系统模块(或子系统)
	 * @return
	 */
	public List getAppTopModules();
	
	/**
	 * 获取用户模块下的子菜单
	 * @param moduleId
	 * @return
	 */
	public List getModuleMenus(String moduleId);
	
	/**
	 * 获取用户收藏夹
	 * @return
	 */
	public List getUserFavorites();
	
	/**
	 * 获取用户页面个性化定义信息
	 * @return
	 */
	public Object getUserPageStyle();
}
