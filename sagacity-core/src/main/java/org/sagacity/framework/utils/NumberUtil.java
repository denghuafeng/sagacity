/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @project sagacity-core
 * @description:$
 *          <p>
 *          数据处理工具类
 *          <li>提供数字类型的数据转换成特定格式的字符串</li>
 *          <li>提供转换字符串到数字类型数据</li>
 *          <li>提供随机数获取方法，包括给定范围的数据取出不重复的数�?</li>
 *          <li>提供字符串表达式函数的执行</li>
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作�??</a>$
 * @version $id:DataUtil.java,Revision:v1.0,Date:Oct 18, 2007 9:19:50 AM $
 */
public class NumberUtil {
	protected final BigDecimal ONE_BIGDECIMAL = new BigDecimal(1);

	/**
	 * 根据给定的模式将数据对象转换成格式化的字符串
	 * 
	 * @param target
	 * @param pattern:example:
	 *            '整数�?.小数�?' as '####.#####'; '#.####' 表示整数位不限制 '#,###.####'
	 *            整数部分采用三位分割，小数四�?,不足则不�? '#,###.0000' 整数部分采用三位分割，小数四�?,不足则补�?
	 * 
	 * @return
	 */
	public static String format(Object target, String pattern) {
		if (target == null)
			return null;
		try {
			String tmpStr = target.toString();
			try {
				BigDecimal tmp = new BigDecimal(tmpStr);
				if (tmp.doubleValue() == 0)
					return "0";
			} catch (Exception e) {

			}
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();

			df.applyPattern(pattern);
			return df.format(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换百分�?
	 * 
	 * @param percent:example:
	 *            90% return 0.9
	 * @return
	 */
	public static Float parsePercent(String percent) {
		if (StringUtil.isNullOrBlank(percent))
			return null;
		NumberFormat nf = NumberFormat.getPercentInstance();
		Number number;
		try {
			number = nf.parse(percent);
			return new Float(number.floatValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析float 字符�?
	 * 
	 * @param floatStr
	 * @param maxIntDigits
	 * @param maxFractionDigits
	 * @return
	 */
	public static Float parseFloat(String floatStr, Integer maxIntDigits,
			Integer maxFractionDigits) {
		Number number = parseStr(floatStr, maxIntDigits, null,
				maxFractionDigits, null);
		if (number != null)
			return new Float(number.floatValue());
		return null;
	}

	/**
	 * 解析decimal 字符�?
	 * 
	 * @param decimalStr
	 * @param maxIntDigits
	 * @param maxFractionDigits
	 * @return
	 */
	public static BigDecimal parseDecimal(String decimalStr,
			Integer maxIntDigits, Integer maxFractionDigits) {
		Number number = parseStr(decimalStr, maxIntDigits, null,
				maxFractionDigits, null);
		if (number != null)
			return new BigDecimal(number.doubleValue());

		return null;
	}

	/**
	 * 解析integer 字符�?
	 * 
	 * @param integerStr
	 * @param maxIntDigits
	 * @return
	 */
	public static Integer parseInteger(String integerStr) {
		Number number = parseStr(integerStr, null, null, null, null);
		if (number != null)
			return new Integer(number.intValue());
		return null;
	}

	/**
	 * 解析double 字符�?
	 * 
	 * @param doubleStr
	 * @param maxIntDigits
	 * @param maxFractionDigits
	 * @return
	 */
	public static Double parseDouble(String doubleStr, Integer maxIntDigits,
			Integer maxFractionDigits) {
		Number number = parseStr(doubleStr, maxIntDigits, null,
				maxFractionDigits, null);
		if (number != null)
			return new Double(number.doubleValue());

		return null;
	}

	/**
	 * 私有方法，为parseDouble,parseFloat等提供统�?的处理实�?
	 * 
	 * @param parseTarget
	 * @param maxIntDigits
	 * @param minIntDigits
	 * @param maxFractionDigits
	 * @param minFractionDigits
	 * @return
	 */
	private static Number parseStr(String parseTarget, Integer maxIntDigits,
			Integer minIntDigits, Integer maxFractionDigits,
			Integer minFractionDigits) {
		if (StringUtil.isNullOrBlank(parseTarget))
			return null;
		NumberFormat nf = NumberFormat.getInstance();
		Number number;
		try {
			// �?大整数位
			if (maxIntDigits != null)
				nf.setMaximumIntegerDigits(maxIntDigits.intValue());
			// �?小整数位
			if (minIntDigits != null)
				nf.setMinimumIntegerDigits(minIntDigits.intValue());

			// �?大小数位
			if (maxFractionDigits != null)
				nf.setMaximumFractionDigits(maxFractionDigits.intValue());
			// �?小小数位
			if (minFractionDigits != null)
				nf.setMinimumFractionDigits(minFractionDigits.intValue());

			return nf.parse(parseTarget);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;

		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}

		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}

	public static void main(String[] args) {
		System.err.println(NumberUtil.parsePercent("90%"));
		System.err.println(NumberUtil.parseDouble("9080.980", null,
				new Integer(2)));
		System.err.println(NumberUtil.parseInteger("80.980"));
		System.err.println(NumberUtil.format(new Double(10.3), "###,###.000"));
		String[][] tmp = new String[][] { { "", "" } };
		System.err.println(tmp instanceof Object[]);
	}
}
