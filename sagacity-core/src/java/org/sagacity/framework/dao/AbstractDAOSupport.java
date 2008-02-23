/**
 * 
 */
package org.sagacity.framework.dao;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * 
 * @Project Name:sagacity
 * @Desciption:
 * @author Administrator
 * @version $Id:AbstractDAOSupport.java, Revision v1.0, Oct 7, 2007 9:34:05 AM $
 */
public abstract class AbstractDAOSupport<T> {
	/**
     * Dao所管理的Entity类型.
     */
    protected Class<T> entityClass;
    
	//protected final Logger logger = LogFactory.getFactory().getLog(getClass());
}
