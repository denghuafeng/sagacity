/**
 * 
 */
package org.sagacity.framework.service;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>基础业务处理类，提供服务层常用功能的接口</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">陈仁飞</a>$
 *@version $id:BaseService.java,Revision:v1.0,Date:Jan 25, 2008 11:24:23 AM $
 */
public abstract class BaseService {
	/**
	 * 定义日志,在Service层代码继承BaseService,直接在程序中使用log.info,log.error等
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
}
