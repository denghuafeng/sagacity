/**
 * 
 */
package org.sagacity.tools.plugin.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          在此说明类的功能
 *          </p>$
 * @author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DynamicDataSource.java,Revision:v1.0,Date:Dec 24, 2008 3:41:03
 *          PM $
 */
public class DynamicDataSource extends BasicDataSource {
	private String backUrl;

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	/**
	 * 创建数据库DataSource
	 */
	protected synchronized DataSource createDataSource() throws SQLException {
		if (!validateConnect()) {
			this.url = this.backUrl;
			System.out.println("目前连接数据库URL为:" + this.url);
		}
		return super.createDataSource();
	}

	/**
	 * 判断数据是否能够连接
	 * 
	 * @return
	 */
	private boolean validateConnect() {
		Connection conn = org.sagacity.framework.utils.DBUtil.getConnection(
				this.driverClassName, this.url, username, password);
		if (conn == null)
			return false;
		conn = null;
		return true;
	}
}
