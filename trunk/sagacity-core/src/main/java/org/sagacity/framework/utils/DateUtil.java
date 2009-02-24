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
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:DateUtil.java,Revision:v1.0,Date:2008-12-14 下午08:01:19 $
 */
public class DateUtil {
	/**
	 * 定义日期的格式
	 */
	public final static class FORMAT {
		/**
		 * 8位日期格式
		 */
		public final static String DATE_8CHAR = "yyyyMMdd";

		/**
		 * 点号日期格式
		 */
		public final static String DATE_DOT = "yyyy.MM.dd";

		/**
		 * 反斜杠日期格式
		 */
		public final static String DATE_SLASH = "yyyy/MM/dd";

		/**
		 * 横杠日期格式
		 */
		public final static String DATEF_HORIZONTAL = "yyyy-MM-dd";

		/**
		 * 日期时间(日期点格�?)
		 */
		public final static String DATATIME_DOT = "yyyy.MM.dd HH:mm:ss";

		/**
		 * 日期时间(日期反斜杠)
		 */
		public final static String DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

		/**
		 * 日期时间(日期横杠)
		 */
		public final static String DATETIME_HORIZONTAL = "yyyy-MM-dd HH:mm:ss";
	}

	/**
	 * 将日期字符串或时间转换成时间类型 日期字符串中的日期分隔符可是:"/",".","-"， 返回时间具体到秒 只提供常用的日期格式处理
	 * 
	 * @param str
	 * @return Date
	 * @throws UtilException
	 */
	public static Date parseString(String dateString) {
		if (dateString == null || "".equals(dateString)
				|| "null".equals(dateString.toLowerCase())) {
			System.err.println("The date string is null!");
			return null;
		}
		try {
			dateString = dateString.trim();
			if (dateString.length() == 4)
				dateString = dateString + "0101";
			else if (dateString.length() == 6)
				dateString = dateString + "01";
			else if (dateString.length() == 7)
				dateString = dateString + "/01";
			boolean isCommon = true;
			if (dateString.indexOf("-") != -1) {
				dateString = dateString.replaceAll("-", "/");
			} else if (dateString.indexOf(".") != -1) {
				dateString = dateString.replace('.', '/');
			} else if (dateString.indexOf("/") == -1)
				isCommon = false;
			// 如果日期格式�?0000/00/00则认为日期为�?
			if (dateString.equalsIgnoreCase("0000/00/00"))
				return null;
			String fmt = FORMAT.DATE_SLASH;

			if (dateString.indexOf(":") != -1) {
				if (isCommon)
					fmt = FORMAT.DATETIME_SLASH;
				else
					fmt = "yyyyMMdd HH:mm:ss";
			} else {
				if (!isCommon)
					fmt = FORMAT.DATE_8CHAR;
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
	 * 将日期字符串或时间转换成时间类型 日期字符串中的日期分隔符可是:"/",".","-"， 返回时间具体到秒 只提供常用的日期格式处理
	 * 
	 * @param str
	 * @return Date
	 * @throws UtilException
	 */
	public static Date parseString(String dateString,String format) {
		if (dateString == null || "".equals(dateString)
				|| "null".equals(dateString.toLowerCase())) {
			System.err.println("The date string is null!");
			return null;
		}
		try {
			if(StringUtil.isNullOrBlank(format))
				return parseString(dateString);
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(dateString);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 日期对象类型转换
	 * 
	 * @param dt
	 * @return
	 */
	private static java.util.Date convertDateObject(Object dt) {
		if (dt == null) {
			System.err.println("日期不能为空,请正确输�?!");
			return null;
		}
		if (!(dt instanceof String) && !(dt instanceof java.sql.Date)
				&& !(dt instanceof java.util.Date))
			throw new IllegalArgumentException("日期格式必须是String以及Date类型,请正确输�?!");
		Date result = null;
		if (dt instanceof String)
			result = parseString((String) dt);
		else if (dt instanceof java.util.Date)
			result = (java.util.Date) dt;
		else if (dt instanceof java.sql.Date)
			result = new java.util.Date(((java.sql.Date) dt).getTime());
		return result;
	}

	/**
	 * �?要改进增加非正常格式的日期转换功�?!�?20080718103020 �? 20080718 103020 将日期类型转换成字符�?
	 * 其格式包�?:通过DateFormat转换的标准格式，如yyyy/MM/dd HH:mm:ss或�?�yyyy-MM-dd HH:mm:ss
	 * 非标准的格式，如YYYY/MM/DD
	 * 
	 * @param dt
	 * @param fmt
	 * @return String
	 * @throws UtilException
	 */
	public static String formatDate(Object dt, String fmt) {
		if (dt == null)
			return null;
		DateFormat df = null;
		if (fmt.equalsIgnoreCase("yyyy"))
			return Integer.toString(getYear(dt));
		else if (fmt.equalsIgnoreCase("MM"))
			return Integer.toString(getMonth(dt));
		else if (fmt.equalsIgnoreCase("dd"))
			return Integer.toString(getDay(dt));
		if (fmt.equalsIgnoreCase("HH:mm:ss")
				|| fmt.equalsIgnoreCase("HH.mm.ss")
				|| fmt.equalsIgnoreCase("HHmmss")) {
			String result;
			Date tmp = null;
			if (dt instanceof String) {
				// 因为是取时分秒，�?以前面的年月日可以是任意�?
				if (dt.toString().length() == 8) {
					dt = "2008-01-01 " + ((String) dt);
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else if (dt.toString().length() < 8) {
					dt = "20080101 " + ((String) dt);
					df = new SimpleDateFormat("yyyyMMdd HHmmss");
				}
				try {
					tmp = df.parse(dt.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (dt instanceof java.util.Date)
				tmp = (java.util.Date) dt;
			else if (dt instanceof java.sql.Date)
				tmp = (java.sql.Date) dt;
			df = new SimpleDateFormat("yyyy-MM-dd " + fmt);
			if (tmp == null)
				return null;
			result = df.format(tmp);
			return result.substring(result.indexOf(" ") + 1);
		} else {
			df = new SimpleDateFormat(fmt);
			Date tmp = convertDateObject(dt);
			if (tmp == null)
				return null;
			return df.format(tmp);
		}
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
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Object date) {
		return new java.sql.Date(convertDateObject(date).getTime());
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
	public static Date addMilliSecond(Object dt, long millisecond) {
		Date tmp = convertDateObject(dt);
		tmp.setTime(tmp.getTime() + millisecond);
		return tmp;
	}

	public static Date addSecond(Object dt, long second) {
		return addMilliSecond(dt, 1000L * second);
	}

	public static Date addSecond(Object dt, double second) {
		Double millisecond = new Double(1000.0 * second);
		return addMilliSecond(dt, millisecond.longValue());
	}

	public static Date addMinute(Object dt, long minute) {
		return addMilliSecond(dt, 1000L * 60L * minute);
	}

	public static Date addMinute(Object dt, double minute) {
		Double millisecond = new Double(1000.0 * 60.0 * minute);
		return addMilliSecond(dt, millisecond.longValue());
	}

	public static Date addHour(Object dt, long hour) {
		return addMilliSecond(dt, 1000L * 60L * 60L * hour);
	}

	public static Date addHour(Object dt, double hour) {
		Double millisecond = new Double(1000.0 * 60.0 * 60.0 * hour);
		return addMilliSecond(dt, millisecond.longValue());
	}

	public static Date addDay(Object dt, long day) {
		return addMilliSecond(dt, 1000L * 60L * 60L * 24L * day);
	}

	public static Date addDay(Object dt, double day) {
		Double millisecond = new Double(1000.0 * 60.0 * 60.0 * 24.0 * day);
		return addMilliSecond(dt, millisecond.longValue());
	}

	// add month
	public static Date addMonth(Object dt, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDateObject(dt));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + month);
		return cal.getTime();
	}

	/**
	 * 计算日期+月数
	 * 
	 * @param dt
	 * @param month
	 * @return
	 */
	public static Date addMonth(Object dt, double month) {
		double floorMonth = Math.floor(month);
		double decimalMonth = month - floorMonth;
		Date tmp = convertDateObject(dt);
		tmp = addMonth(tmp, (new Double(floorMonth)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(tmp);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		Date nextdt = cal.getTime();
		long monthMillisecond = nextdt.getTime() - tmp.getTime();
		double millisecond = (double) monthMillisecond * decimalMonth;
		return addMilliSecond(tmp, (long) millisecond);
	}

	/**
	 * add year
	 * 
	 * @param dt
	 * @param year
	 * @return
	 */
	public static Date addYear(Object dt, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDateObject(dt));
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + year);
		return cal.getTime();
	}

	/**
	 * @todo 在给定的日期上做年的加减运算
	 * @param dt
	 * @param year
	 * @return
	 */
	public static Date addYear(Object dt, double year) {
		// 如果日期为空表示以当前日期为基数进行加减
		Date tmp = convertDateObject(dt);
		// throw new IllegalArgumentException("要增加的日期不能为空!");
		double floorYear = Math.floor(year);
		double decimalYear = year - floorYear;

		tmp = addYear(tmp, (new Double(floorYear)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(tmp);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		Date nextdt = cal.getTime();
		long yearMillisecond = nextdt.getTime() - tmp.getTime();
		double millisecond = (double) yearMillisecond * decimalYear;
		return addSecond(tmp, (long) millisecond);
	}

	public static int getYear() {
		return getYear(null);
	}

	public static int getYear(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.YEAR);
	}

	public static String getShortYear(Object dateValue) {
		int year = getYear(dateValue);
		return Integer.toString(year).substring(2);
	}

	public static int getMonth() {
		return getMonth(null);
	}

	public static int getMonth(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		return getDay(null);
	}

	public static int getDay(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour() {
		return getHour(null);
	}

	public static int getHour(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute() {
		return getMinute(null);
	}

	public static int getMinute(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.MINUTE);
	}

	public static int getSecond() {
		return getSecond(null);
	}

	public static int getSecond(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.SECOND);
	}

	/**
	 * 获取当前日历
	 * 
	 * @return
	 */
	public static GregorianCalendar getCalendar() {
		return new GregorianCalendar();
	}

	public static int getDayOfWeek() {
		return getDayOfWeek(null);
	}

	public static int getDayOfWeek(Object dateValue) {
		GregorianCalendar currentDate = getCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		int week = currentDate.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0)
			return 7;
		return week;
	}

	public static int getWeekOfMonth() {
		return getWeekOfMonth(null);
	}

	/**
	 * 获取给定日期所在月的第几周
	 * 
	 * @param dateValue
	 * @return
	 */
	public static int getWeekOfMonth(Object dateValue) {
		GregorianCalendar currentDate = getCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.WEEK_OF_MONTH) - 1;
	}

	public static int getWeekOfYear() {
		return getWeekOfYear(null);
	}

	/**
	 * 获取给定日期所在年的第几周
	 * 
	 * @param dateValue
	 * @return
	 */
	public static int getWeekOfYear(Object dateValue) {
		GregorianCalendar currentDate = getCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.WEEK_OF_YEAR) - 1;
	}

	/**
	 * @todo 获取相隔两个时间的周数
	 * @param floorDate
	 * @param goalDate
	 * @return
	 */
	public static double getIntervalWeeks(Object floorDate, Object goalDate) {
		return getIntervalHours(floorDate, goalDate) / (7 * 24);
	}

	/**
	 * @todo 获取两个时间间隔的小时数
	 * @param floorDate
	 * @param goalDate
	 * @return
	 */
	public static double getIntervalHours(Object floorDate, Object goalDate) {
		return getIntervalMillSeconds(floorDate, goalDate) / (3600 * 1000);
	}

	/**
	 * @todo 获取两时间的间隔分钟
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static double getIntervalMinutes(Object floorDate, Object goalDate) {
		return getIntervalMillSeconds(floorDate, goalDate) / (60 * 1000);
	}

	/**
	 * @todo 获取两时间的间隔分钟
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static double getIntervalSeconds(Object floorDate, Object goalDate) {
		return getIntervalMillSeconds(floorDate, goalDate) / (1000);
	}

	/**
	 * @todo 获取两时间间隔的毫秒数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static long getIntervalMillSeconds(Object floorDate, Object goalDate) {
		return convertDateObject(goalDate).getTime()
				- convertDateObject(floorDate).getTime();
	}

	/**
	 * @todo 获取两个时间之间的间隔并以格�?:xx天xx小时xx分钟xx秒输出
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return String
	 */
	public static String getIntervalDate(Object floorDate, Object goalDate) {
		long intervalSeconds = getIntervalMillSeconds(floorDate, goalDate) / 1000;
		StringBuffer intervalDate = new StringBuffer();
		if (intervalSeconds < 0)
			intervalSeconds = -intervalSeconds;

		long days = intervalSeconds / (24 * 60 * 60);
		intervalSeconds = intervalSeconds - days * 24 * 60 * 60;
		long hours = intervalSeconds / (60 * 60);
		intervalSeconds = intervalSeconds - hours * 3600;
		long minuts = intervalSeconds / 60;
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

	/**
	 * @todo 获取两时间间隔的毫秒数
	 * @param floorDate
	 *            Date
	 * @param goalDate
	 *            Date
	 * @return double
	 */
	public static long getIntervalYears(Object floorDate, Object goalDate) {
		return convertDateObject(goalDate).getYear()
				- convertDateObject(floorDate).getYear();
	}

	/**
	 * 获取给定日期的月份的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthDays(Object date) {
		Date target = convertDateObject(date);
		int year = target.getYear();
		int month = target.getMonth();
		GregorianCalendar beginCalendar = new GregorianCalendar();
		beginCalendar.set(year, month, 1);
		GregorianCalendar endCalendar = new GregorianCalendar();
		endCalendar.set(year, month + 1, 1);

		return new Double(getIntervalHours(beginCalendar.getTime(), endCalendar
				.getTime()) / 24).intValue();
		// return currentDate.get;
	}

	/**
	 * 将日期转化为中文格式
	 * 
	 * @param dateValue
	 * @return
	 */
	public static String format2China(Object dateValue) {
		Date date = convertDateObject(dateValue);
		if (date == null)
			return null;
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(convertDateObject(dateValue));
		String tmpDate;
		StringBuffer result = new StringBuffer();
		if (dateValue instanceof String) {
			tmpDate = (String) dateValue;
			if (tmpDate.length() >= 4)
				result.append(currentDate.get(Calendar.YEAR) + "年");
			if (tmpDate.length() >= 6)
				result.append((currentDate.get(Calendar.MONTH) + 1) + "月");
			if (tmpDate.length() >= 8)
				result.append(currentDate.get(Calendar.DAY_OF_MONTH) + "日");
			if (tmpDate.length() > 10) {
				result.append(currentDate.get(Calendar.HOUR_OF_DAY) + "时");
				result.append(currentDate.get(Calendar.MINUTE) + "分");
				result.append(currentDate.get(Calendar.SECOND) + "秒");
			}
		} else {
			result.append(currentDate.get(Calendar.YEAR) + "年");
			result.append((currentDate.get(Calendar.MONTH) + 1) + "月");
			result.append(currentDate.get(Calendar.DAY_OF_MONTH) + "日");
			result.append(currentDate.get(Calendar.HOUR_OF_DAY) + "时");
			result.append(currentDate.get(Calendar.MINUTE) + "分");
			result.append(currentDate.get(Calendar.SECOND) + "秒");
		}
		return result.toString();

	}

	public static void main(String[] args) {

		Date tmp = DateUtil.parseString("2002-4-12 20:20:11");
		System.err.println(DateUtil.formatDate(DateUtil.getNowTime(), "yyyy-MM-dd HH:mm:ss.sss"));
		System.err.println(DateUtil.parseString("2002-4","yyyy-MM"));
		System.err.println(DateUtil.getMonth("2002-4-12 20:20:11"));
		System.err.println(DateUtil.getDay("2002-4-12 20:20:11"));
		System.err.println(DateUtil.formatDate(tmp,
				DateUtil.FORMAT.DATETIME_HORIZONTAL));
		System.err.println(DateUtil.formatDate(addMonth(tmp, 12.3),
				"yyyy-MM-dd HH:mm:ss"));
		System.err.println(DateUtil.parseString("2008.03.20 20:20:11"));

		String begin = "2002-02-13";
		Date test = new Date();
		System.err.println(DateUtil.parseString("200101"));
		System.err.println(DateUtil.formatDate("20080809", "yyyy"));
		System.err.println(DateUtil.getMonthDays(DateUtil
				.parseString("2008-03-01")));
		System.err.println(DateUtil.format2China("2008-03-01 15:20:20"));
		System.err
				.println(DateUtil.formatDate(DateUtil.getNowTime(), "yyMMdd"));

		System.err.println(DateUtil.getIntervalDate("20080804", "20080901"));
		System.err.println(DateUtil.getDayOfWeek(null));
		System.err.println(DateUtil.getWeekOfMonth("20090104"));
		System.err.println(DateUtil.getWeekOfYear("20081227"));
		
		System.err.println(DateUtil.formatDate("2008-10", "yyyy-MM"));
	}
}
