/**
 * 
 */
package org.sagacity.framework.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>日期处理支持类，提供日期的基本操作处理</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:DateUtil.java,Revision:v1.0,Date:Oct 18, 2007 9:22:11 AM $
 */
public class DateUtil {
	/**
	 * 定义日期的格式
	 */
	public final static class DateFormate
	{
		/**
		 * 8位日期格式
		 */
		public final static String DATE_8CHAR="yyyyMMdd";
		
		/**
		 * 点号日期格式
		 */
		public final static String DATE_DOT="yyyy.MM.dd";
		
		/**
		 * 反斜杠日期格式
		 */
		public final static String DATE_SLASH="yyyy/MM/dd";
		
		/**
		 * 横杠日期格式
		 */
		public final static String DATEF_HORIZONTAL="yyyy-MM-dd";
		
		/**
		 * 日期时间(日期点格式)
		 */
		public final static String DATATIME_DOT="yyyy.MM.dd HH:mm:ss";
		
		/**
		 * 日期时间(日期反斜杠)
		 */
		public final static String DATETIME_SLASH="yyyy/MM/dd HH:mm:ss";
		
		/**
		 * 日期时间(日期横杠)
		 */
		public final static String DATETIME_HORIZONTAL="yyyy-MM-dd HH:mm:ss";
	}
	
	/**
	 * 将日期字符串或时间转换成时间类型 日期字符串中的日期分隔符可是是"/",".","-"，返回时间具体到秒
	 * 只提供常用的日期格式处理
	 * @param str
	 * @return Date
	 * @throws UtilException
	 */
	public static Date parseString(String dateString) {
		// return stringToCalendar(str, fmt).getTime();
		if (dateString == null || "".equals(dateString)
				|| "null".equals(dateString.toLowerCase())) {
			System.err.println("The date string is null!");
			return null;
		}

		try {
			dateString = dateString.trim();
			boolean isCommon = true;
			if (dateString.indexOf("-") != -1) {
				dateString = dateString.replaceAll("-", "/");
			} else if (dateString.indexOf(".") != -1) {
				dateString = dateString.replace('.', '/');
			} else if (dateString.indexOf("/") == -1)
				isCommon = false;

			String fmt =DateFormate.DATE_SLASH;

			if (dateString.indexOf(":") != -1) {
				if (isCommon)
					fmt = DateFormate.DATETIME_SLASH;
				else
					fmt = "yyyyMMdd HH:mm:ss";
			} else {
				if (!isCommon)
					fmt = DateFormate.DATE_8CHAR;
			}

			DateFormat df = new SimpleDateFormat(fmt);
			return df.parse(dateString);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将日期类型转换成字符串 其格式包括:通过DateFormat转换的标准格式，如yyyy/MM/dd HH:mm:ss或者yyyy-MM-dd
	 * HH:mm:ss 非标准的格式，如YYYY/MM/DD
	 * 
	 * @param dt
	 * @param fmt
	 * @return String
	 * @throws UtilException
	 */
	public static String formatDate(Date dt, String fmt) {
		if (dt == null) {
			System.err.println("The date is null!");
			return null;
		}
		try {
			DateFormat df = new SimpleDateFormat(fmt);
			return df.format(dt);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前操作系统的日期
	 * 
	 * @return Date
	 */
	public static java.sql.Date getSqlDate() {
		try {
			return new java.sql.Date((new java.util.Date()).getTime());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前以sql.date的日期
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date) {
		try {
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();	
		}
		return null;
	}

	public static java.sql.Date getSqlDate(String dateString) {
		try {
			return new java.sql.Date(parseString(dateString).getTime());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前操作系统的时间
	 * 
	 * @return 当前操作系统的时间
	 */
	public static Date getNowTime() {
		return Calendar.getInstance().getTime();
	}

	// Add millisecond
	public static Date addMilliSecond(Date dt, int millisecond) {
		return addSecond(dt, (long) millisecond);
	}

	public static Date addMilliSecond(Date dt, long millisecond) {
		dt.setTime(dt.getTime() + millisecond);
		return dt;
	}

	// add second
	public static Date addSecond(Date dt, int second) {
		return addSecond(dt, (long) second);
	}

	public static Date addSecond(Date dt, float second) {
		return addSecond(dt, (double) second);
	}

	public static Date addSecond(Date dt, long second) {
		return addMilliSecond(dt, 1000L * second);
	}

	public static Date addSecond(Date dt, double second) {
		Double millisecond = new Double(1000.0 * second);
		return addMilliSecond(dt, millisecond.longValue());
	}

	// add minute
	public static Date addMinute(Date dt, int minute) {
		return addMinute(dt, (long) minute);
	}

	public static Date addMinute(Date dt, float minute) {
		return addMinute(dt, (double) minute);
	}

	public static Date addMinute(Date dt, long minute) {
		return addMilliSecond(dt, 1000L * 60L * minute);
	}

	public static Date addMinute(Date dt, double minute) {
		Double millisecond = new Double(1000.0 * 60.0 * minute);
		return addMilliSecond(dt, millisecond.longValue());
	}

	// add hour
	public static Date addHour(Date dt, int hour) {
		return addHour(dt, (long) hour);
	}

	public static Date addHour(Date dt, float hour) {
		return addHour(dt, (double) hour);
	}

	public static Date addHour(Date dt, long hour) {
		return addMilliSecond(dt, 1000L * 60L * 60L * hour);
	}

	public static Date addHour(Date dt, double hour) {
		Double millisecond = new Double(1000.0 * 60.0 * 60.0 * hour);
		return addMilliSecond(dt, millisecond.longValue());
	}

	// add day
	public static Date addDay(Date dt, int day) {
		return addDay(dt, (long) day);
	}

	public static Date addDay(Date dt, float day) {
		return addDay(dt, (double) day);
	}

	public static Date addDay(Date dt, long day) {
		return addMilliSecond(dt, 1000L * 60L * 60L * 24L * day);
	}

	public static Date addDay(Date dt, double day) {
		Double millisecond = new Double(1000.0 * 60.0 * 60.0 * 24.0 * day);
		return addMilliSecond(dt, millisecond.longValue());
	}

	// add month
	public static Date addMonth(Date dt, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + month);
		return cal.getTime();
	}

	public static Date addMonth(Date dt, float month) {
		return addMonth(dt, (double) month);
	}

	public static Date addMonth(Date dt, long month) {
		return addMonth(dt, (new Long(month)).intValue());
	}

	public static Date addMonth(Date dt, double month) {
		double floorMonth = Math.floor(month);
		double decimalMonth = month - floorMonth;
		dt = addMonth(dt, (new Double(floorMonth)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		Date nextdt = cal.getTime();
		long monthMillisecond = nextdt.getTime() - dt.getTime();
		double millisecond = (double) monthMillisecond * decimalMonth;
		return addMilliSecond(dt, (long) millisecond);
	}

	// add year
	public static Date addYear(Date dt, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + year);
		return cal.getTime();
	}

	public static int getYear() {
		GregorianCalendar currentDay = new GregorianCalendar();
		return currentDay.get(Calendar.YEAR);
	}

	public static int getMonth() {
		GregorianCalendar currentDay = new GregorianCalendar();
		return currentDay.get(Calendar.MONTH) + 1;
	}

	public static int getHour() {
		GregorianCalendar currentDay = new GregorianCalendar();
		return currentDay.get(Calendar.HOUR);
	}

	public static int getMinute() {
		GregorianCalendar currentDay = new GregorianCalendar();
		return currentDay.get(Calendar.MINUTE);
	}

	public static int getSecond() {
		GregorianCalendar currentDay = new GregorianCalendar();
		return currentDay.get(Calendar.SECOND);
	}

	public static Date addYear(Date dt, float year) {
		return addYear(dt, (double) year);
	}

	public static Date addYear(Date dt, long year) {
		return addYear(dt, (new Long(year)).intValue());
	}

	/**
	 * @todo 在给定的日期上做年的加减运算
	 * @param dt
	 * @param year
	 * @return
	 */
	public static Date addYear(Date dt, double year) {
		//如果日期为空表示以当前日期为基数进行加减
		if(dt==null)
			dt=getNowTime();
			//throw new IllegalArgumentException("要增加的日期不能为空!");
		double floorYear = Math.floor(year);
		double decimalYear = year - floorYear;
		dt = addYear(dt, (new Double(floorYear)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		Date nextdt = cal.getTime();
		long yearMillisecond = nextdt.getTime() - dt.getTime();
		double millisecond = (double) yearMillisecond * decimalYear;
		return addSecond(dt, (long) millisecond);
	}

	/**
	 * @todo 获取个时间的周数
	 * @param floorDate
	 * @param goalDate
	 * @return
	 */
	public static double getIntervalWeeks(Date floorDate, Date goalDate) {
		return getIntervalHours(floorDate, goalDate) / (7 * 24);
	}

	/**
	 * @todo 获取两个时间间隔的小时数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static Double getIntervalHours(String floorDate, String goalDate)
	{
		if(floorDate==null || goalDate==null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		Date dt1 = parseString(floorDate);
		Date dt2 = parseString(goalDate);
		return getIntervalHours(dt1, dt2);
	}

	/**
	 * @todo 获取两个时间间隔的小时数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static double getIntervalHours(Date floorDate, Date goalDate) {
		if(floorDate==null || goalDate==null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		double intervalHours = 0;
		long milliseconds = getIntervalMillSeconds(floorDate, goalDate);
		intervalHours = milliseconds / (3600f * 1000);
		return intervalHours;
	}

	/**
	 * @todo 获取两时间的间隔分钟数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static double getIntervalMinutes(Date floorDate, Date goalDate) {
		if(floorDate==null || goalDate==null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		double intervalMinutes = 0;
		long milliseconds = getIntervalMillSeconds(floorDate, goalDate);
		intervalMinutes = milliseconds / (60f * 1000);
		return intervalMinutes;
	}

	/**
	 * @todo 获取两时间间隔的毫秒数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static long getIntervalMillSeconds(String floorDate, String goalDate)
	{
		if(floorDate==null || goalDate==null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		Date dt1 = parseString(floorDate);
		Date dt2 = parseString(goalDate);
		return getIntervalMillSeconds(dt1, dt2);
	}

	/**
	 * @todo 获取两时间间隔的毫秒数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static long getIntervalMillSeconds(Date floorDate, Date goalDate) {
		if(floorDate==null || goalDate==null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		long milliseconds = goalDate.getTime() - floorDate.getTime();
		return milliseconds;
	}

	/**
	 * @todo 获取两个时间之间的间隔并以格式:xx天xx小时xx分钟xx秒输出
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return String
	 */
	public static String getIntervalDate(Date floorDate, Date goalDate) {
		if (floorDate == null || goalDate == null)
			throw new IllegalArgumentException("获取两时间间隔时输入时间不能为空!");
		StringBuffer intervalDate = new StringBuffer();
		long intervalSeconds = getIntervalMillSeconds(floorDate, goalDate) / 1000;
		if (intervalSeconds < 0)
			intervalSeconds = -intervalSeconds;
		long days;
		long hours;
		long minuts;

		days = intervalSeconds / (24 * 60 * 60);
		intervalSeconds = intervalSeconds - days * 24 * 60 * 60;
		hours = intervalSeconds / (60 * 60);
		intervalSeconds = intervalSeconds - hours * 3600;
		minuts = intervalSeconds / 60;
		intervalSeconds = intervalSeconds - minuts * 60;
		if (days > 0)
			intervalDate.append(days + "天");
		if (hours > 0)
			intervalDate.append(hours + "小时");
		if (minuts > 0)
			intervalDate.append(minuts + "分钟");
		if (intervalSeconds > 0)
			intervalDate.append(intervalSeconds + "秒");

		return intervalDate.toString();
	}
	
	
}
