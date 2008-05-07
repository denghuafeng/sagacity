/**
 * 
 */
package org.sagacity.framework.dao;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>DAO基类</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:AbstractDAOSupport.java,Revision:v1.0,Date:Mar 7, 2008 2:49:09 PM $
 */
public abstract class AbstractDAOSupport<T> {
	/**
     * Dao所管理的Entity类型.
     */
    protected Class<T> entityClass;
    
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
}
