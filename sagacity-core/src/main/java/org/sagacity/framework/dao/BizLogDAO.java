/**
 * 
 */
package org.sagacity.framework.dao;

import org.sagacity.framework.model.BizLogModel;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          记录应用业务操作的日志，主要记录
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:TransactionLogDAO.java,Revision:v1.0,Date:Jul 2, 2008 9:21:12 AM $
 */
public interface BizLogDAO {
	/**
	 * 记录事务的日志
	 * @param logVO
	 */
	public void logger(BizLogModel bizLogModel);
	
	/**
	 * 获取当前用户id
	 * @return
	 */
	public String getCurrentUserId();
}
