/**
 * 
 */
package org.sagacity.framework.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:RowCallbackHandler.java,Revision:v1.0,Date:2008-12-9 下午10:18:14 $
 */
public abstract class RowCallbackHandler {
	private List result=new ArrayList();

	public RowCallbackHandler() {
	}

	/**
	 * 
	 * @param arg0
	 * @throws SQLException
	 */
	public abstract void processRow(ResultSet rs) throws SQLException;

	public List getResult() {
		return result;
	}
}
