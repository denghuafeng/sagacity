/**
 * 
 */
package org.sagacity.framework.dao.handler;

import org.hibernate.Criteria;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:CriteriaCallbackHandler.java,Revision:v1.0,Date:Jun 18, 2008 8:35:57 PM $
 */
public abstract class CriteriaCallbackHandler {
	public abstract void addRestrictions(Criteria criteria);
}
