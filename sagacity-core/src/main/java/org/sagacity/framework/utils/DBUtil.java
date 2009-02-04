/**
 * 
 */
package org.sagacity.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * @project sagacity-core
 * @description:$ <p>
 *                数据库连接工具类
 *                </p>
 *                $
 * @author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DBUtil.java,Revision:v1.0,Date:Dec 24, 2008 3:52:05 PM $
 */
public class DBUtil {
	protected final static Log logger = LogFactory.getFactory().getLog(
			DBUtil.class);

	/**
	 * 获取数据库连接
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static Connection getConnection(String driver, String url,
			String username, String password) {
		logger.info("获取数据库连接!");
		Connection conn = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException connectionException) {
			System.out.println(connectionException.getMessage());
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException connectionException) {
			System.out.println(connectionException.getMessage());
			logger.error("获取数据库失败!" + connectionException.getStackTrace());
		}
		return conn;
	}

	/**
	 * 销毁数据库连接
	 * 
	 * @param conn
	 */
	public static void destroy(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
