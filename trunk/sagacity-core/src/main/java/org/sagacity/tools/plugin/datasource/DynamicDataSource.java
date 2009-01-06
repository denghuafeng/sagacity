/**
 * 
 */
package org.sagacity.tools.plugin.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.StringUtil;

/**
 * @project sagacity-core
 * @description:$ <p>
 *                动态数据库连接，提供家庭和工作场所的数据库自动适应， 无须在不同环境下修改配置文件
 *                </p>
 *                $
 * @author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DynamicDataSource.java,Revision:v1.0,Date:Dec 24, 2008 3:41:03
 *          PM $
 */
public class DynamicDataSource extends BasicDataSource {
	private String backUrl = "";

	/**
	 * 工作时间,在此时间之外用backUrl作为数据库连接 默认为周一到周五 8:30到下午6点
	 */
	private String worktime = "1-5 8:30-18:00";

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

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
		// 启用备用地址情况下判断采取先判断连接，不能连接首选数据库后连接备选数据库
		if (StringUtil.isNotNullAndBlank(this.backUrl)) {
			String targetUrl;
			boolean useBakUrl = false;
			// 是否为工作时间
			if (isWorkTime())
				targetUrl = this.url;
			else {
				// 非工作时间启用备用数据库连接地址
				targetUrl = this.backUrl;
				useBakUrl = true;
			}
			// 数据库地址不能连接
			if (!validateConnect(targetUrl)) {
				// 没有启用备用地址
				if (!useBakUrl)
					this.url = this.backUrl;
			} else {
				if (useBakUrl)
					this.url = this.backUrl;
			}
		}
		System.out
				.println("#################目前连接数据库URL为############################");
		System.out.println((this.url.equals(this.backUrl) ? "备用数据库" : "主数据库")
				+ this.url);
		System.out
				.println("###############################################################");
		return super.createDataSource();
	}

	/**
	 * 判断数据是否能够连接
	 * 
	 * @return
	 */
	private boolean validateConnect(String url) {
		Connection conn = org.sagacity.framework.utils.DBUtil.getConnection(
				this.driverClassName, url, username, password);
		if (conn == null)
			return false;
		conn = null;
		return true;
	}

	/**
	 * 判断是否为工作时间范围
	 * 
	 * @return
	 */
	private boolean isWorkTime() {
		int weekBegin = 1;
		int weekEnd = 5;
		String beginTime = "8:30:00";
		String endTime = "18:00:00";
		String timeStr = worktime.substring(worktime.indexOf(" ") + 1);
		try {
			weekBegin = Integer.parseInt(worktime.substring(0, 1));
			weekEnd = Integer.parseInt(worktime.substring(2, 3));
			beginTime = timeStr.substring(0, timeStr.indexOf("-")) + ":00";
			endTime = timeStr.substring(timeStr.indexOf("-") + 1) + ":00";
		} catch (Exception e) {
			e.printStackTrace();
		}
		int nowDayOfWeek = DateUtil.getDayOfWeek();
		Date nowDate = DateUtil.getNowTime();
		String nowDateStr = DateUtil.formatDate(nowDate,
				DateUtil.FORMAT.DATEF_HORIZONTAL);
		if (nowDayOfWeek >= weekBegin && nowDayOfWeek <= weekEnd) {
			if (nowDate.after(DateUtil
					.parseString(nowDateStr + " " + beginTime))
					&& nowDate.before(DateUtil.parseString(nowDateStr + " "
							+ endTime)))
				return true;
			else
				return false;
		} else
			return false;
	}

	public static void main(String[] args) {
		String worktime = "1-6 8:40-13:30";
		int weekBegin = 1;
		int weekEnd = 5;
		String beginTime = "8:30";
		String endTime = "18:00";
		String timeStr = worktime.substring(worktime.indexOf(" ") + 1);
		try {
			weekBegin = Integer.parseInt(worktime.substring(0, 1));
			weekEnd = Integer.parseInt(worktime.substring(2, 3));
			beginTime = timeStr.substring(0, timeStr.indexOf("-"));
			endTime = timeStr.substring(timeStr.indexOf("-") + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isWorkTime;
		int nowDayOfWeek = DateUtil.getDayOfWeek();
		Date nowDate = DateUtil.getNowTime();
		String nowDateStr = DateUtil.formatDate(nowDate,
				DateUtil.FORMAT.DATEF_HORIZONTAL);
		if (nowDayOfWeek >= weekBegin && nowDayOfWeek <= weekEnd) {
			if (nowDate.after(DateUtil.parseString(nowDateStr + " " + beginTime
					+ ":00"))
					&& nowDate.before(DateUtil.parseString(nowDateStr + " "
							+ endTime + ":00")))
				isWorkTime = true;
			else
				isWorkTime = false;
		} else
			isWorkTime = false;

		System.err.println("weekBegin=" + weekBegin);
		System.err.println("weekEnd=" + weekEnd);
		System.err.println("beginTime=" + beginTime);
		System.err.println("endTime=" + endTime);
		System.err.println(isWorkTime);
	}
}
