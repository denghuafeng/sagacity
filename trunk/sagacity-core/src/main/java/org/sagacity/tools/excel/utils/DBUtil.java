/**
 * 
 */
package org.sagacity.tools.excel.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.Constants;

/**
 * @project abchina
 * @description:$ <p>
 *                请在此说明此文件的功能
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DBUtil.java,Revision:v1.0,Date:Jul 30, 2008 12:49:46 AM $
 */
public class DBUtil {
	private final static Log logger = LogFactory.getFactory().getLog(
			DBUtil.class);
	private static Connection conn;

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		logger.info("获取数据库连接!");
		if (conn != null)
			return conn;
		else {
			String driver = System.getProperty(Constants.DB_DRIVER);
			String url = System.getProperty(Constants.DB_URL);
			String user = System.getProperty(Constants.DB_USERNAME);
			String password = System.getProperty(Constants.DB_PASSWORD);

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException connectionException) {
				System.out.println(connectionException.getMessage());
			}
			try {
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false);
				logger.info("成功连接数据库!");

			} catch (SQLException connectionException) {
				System.out.println(connectionException.getMessage());
				logger.error("获取数据库失败!" + connectionException.getStackTrace());
			}
			return conn;
		}
	}

	/**
	 * 注销连接
	 * 
	 */
	public static void destory() {
		logger.info("注销数据库连接!");
		try {
			if (conn != null)
				conn.close();
			conn = null;
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * 执行修改数据库SQL语句
	 * 
	 * @param tableName
	 */
	public static void executeSql(String sql) {
		logger.info("执行修改数据库SQL语句:" + sql);
		if (StringUtil.isNullOrBlank(sql))
			return;
		Connection conn = getConnection();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static List executSql(String sql) {
		List items = new ArrayList();
		logger.info("执行查询数据库SQL语句:" + sql);
		if (StringUtil.isNullOrBlank(sql))
			return items;

		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			// 取得字段列数,在没有rowCallbackHandler用数组返回
			int rowCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				List rowData = new ArrayList();
				for (int i = 0; i < rowCnt; i++) {
					rowData.add(rs.getObject(i + 1));
				}
				items.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return items;
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static List executSql(String sql, Object param) {
		List items = new ArrayList();
		logger.info("执行修改数据库SQL语句:" + sql);
		if (StringUtil.isNullOrBlank(sql))
			return items;
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setObject(1, param);
			rs = pst.executeQuery();
			// 取得字段列数,在没有rowCallbackHandler用数组返回
			int rowCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				List rowData = new ArrayList();
				for (int i = 0; i < rowCnt; i++) {
					rowData.add(rs.getObject(i + 1));
				}
				items.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return items;
	}
}
