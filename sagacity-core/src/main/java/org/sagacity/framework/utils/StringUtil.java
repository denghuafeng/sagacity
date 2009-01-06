/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

/**
 * 
 * @project sagacity-core
 * @description:$ <p>
 *                字符串处理常用功能定�?
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作�??</a>$
 * @version $id:StringUtil.java,Revision:v1.0,Date:Oct 19, 2007 10:09:42 AM $
 */
public class StringUtil {
	// size is 40,为了提高性能避免循环补零,提供字符串补零的�?
	private final static String ZEROSTR = "0000000000000000000000000000000000000000";
	private final static String BLANKSTR = "                                        ";

	/**
	 * private constructor,bannot be instantiated by other class
	 */
	private StringUtil() {
	}

	/**
	 * 判断是否为空指针或�?�空字符
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotNullAndBlank(String str) {
		if (isNullOrBlank(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNullOrBlank(String str) {
		if (str == null || str.trim().equals("")
				|| str.equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	public static String ifNullToBlank(String str) {
		if (str != null && !(str.trim().equals("null"))) {
			return str.trim();
		} else {
			return "";
		}
	}

	public static String ifNullToBlank(Object obj) {
		String ret = "";
		String s = String.valueOf(obj);

		if (s == null || "".equals(s) || "null".equals(s) || "NULL".equals(s)) {
			ret = "";
		} else {
			ret = s;
		}

		return ret;
	}

	public static String toLower(String str) {
		if (str != null && !(str.trim().equals("null"))) {
			return str.trim().toLowerCase();
		} else {
			return "";
		}
	}

	/**
	 * 将字符串前加字符"0"转换成多位字符串 比如 1-> 01 两位 1->001 三位
	 * 
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String formatString(String str, int length) {
		StringBuffer sb = new StringBuffer();

		// 如果为空指针,空字符串则返回空字符�?,否则进行处理

		if (str != null && !(str.trim().equals("null"))) {

			// 字符串长度小于需要长度时前面�?"0"

			if (str.length() < length) {
				for (int i = 0; i < length - str.length(); i++) {
					sb.append("0");
				}
				sb.append(str);
			} else { // 直接返回

				sb.append(str);
			}
		} else {
			return "";
		}

		return sb.toString();
	}

	/**
	 * 将字符串前加字符"0"转换成多位字符串 比如 1-> 01 两位 1->001 三位
	 * 
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(int intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	/**
	 * 将字符串前加字符"0"转换成多位字符串 比如 1-> 01 两位 1->001 三位
	 * 
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(long intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	/**
	 * 将字符串前加字符"0"转换成多位字符串 比如 1-> 01 两位 1->001 三位
	 * 
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(BigDecimal intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	public static String formatString(String str) {
		return formatString(str, 2);
	}

	public static String formatString(int intStr) {
		return formatString(Integer.toString(intStr), 2);
	}

	/**
	 * 比较两个字符串，如果第一个参数为空，就取第二个参�?
	 * 
	 * @param firstStr
	 * @param defaultStr
	 * @return String
	 */
	public static String getDefaultOrSpecifiedValue(String firstStr,
			String defaultStr) {
		if (isNullOrBlank(firstStr)) {
			return defaultStr;
		} else {
			return firstStr;
		}
	}

	public static int getDefaultOrSpecifiedValue(double firstValue,
			double defaultValue) {
		// return firstValue==0?(int)defaultValue:(int)firstValue;
		return (int) firstValue;
	}

	/**
	 * 
	 * @param firstValue
	 * @param defaultValue
	 * @return int
	 */
	public static int getDefaultOrSpecifiedValue(double firstValue,
			Integer defaultValue) {
		return getDefaultOrSpecifiedValue(firstValue, (double) defaultValue
				.intValue());
	}

	public static int getDefaultOrSpecifiedValue(double firstValue,
			Double defaultValue) {
		return getDefaultOrSpecifiedValue(firstValue, defaultValue
				.doubleValue());
	}

	/**
	 * 将多位阿拉伯数字转换成中文表�?
	 * 
	 * @param sourceInt
	 * @return String
	 */
	public static String convertDigitToChineseCharacter(int sourceInt) {
		//
		String sourceStr = Integer.toString(sourceInt);
		int temp = 0;
		String targetStr = "";
		for (int ii = 0; ii < sourceStr.length(); ii++) {
			temp = sourceInt % 10;
			sourceInt = sourceInt / 10;

			if (temp == 0) {

				if (ii == 4) {
					targetStr = "�?" + targetStr;
				} else if (ii == 8) {
					targetStr = "�?" + targetStr;
				} else if (ii != 0) {
					targetStr = "�?" + targetStr;

				}
				continue;
			}

			if (ii == 0) {
				targetStr = targetStr
						+ convertSingleDigitToChineseCharacter(temp);
			} else if (ii == 1) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 2) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 3) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 4) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 5) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 6) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 7) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 8) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 9) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 10) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			} else if (ii == 11) {
				targetStr = convertSingleDigitToChineseCharacter(temp) + "�?"
						+ targetStr;
			}

		}

		return targetStr;
	}

	/**
	 * 将一位阿拉伯数字转换成中文表�?
	 * 
	 * @param i
	 * @return String
	 */
	public static String convertSingleDigitToChineseCharacter(int i) {
		String tempS = null;
		switch (i) {
		case 0:
			tempS = "";
			break;
		case 1:
			tempS = "一";
			break;
		case 2:
			tempS = "二";
			break;
		case 3:
			tempS = "三";
			break;
		case 4:
			tempS = "四";
			break;
		case 5:
			tempS = "五";
			break;
		case 6:
			tempS = "六";
			break;
		case 7:
			tempS = "七";
			break;
		case 8:
			tempS = "八";
			break;
		case 9:
			tempS = "九";
			break;
		case 10:
			tempS = "十";
			break;

		}
		return tempS;
	}

	/**
	 * 将以特殊符号为分隔的字符串分割，去除特殊符号，并加入集合
	 * 目前只支持去�?";"，如原字符为"001;002;003",则返回的集合元素�?:[001],[002],[003]
	 * 
	 * @param sourceStr
	 * @return Collection
	 */
	public static Collection filterStrBySpeciaChar2List(String sourceStr) {
		Collection target = new ArrayList();
		String tempStr = null;
		int begin = 0;

		while (sourceStr.indexOf(";", begin) != -1) {
			tempStr = sourceStr.substring(begin, sourceStr.indexOf(";", begin));
			target.add(tempStr);
			begin = sourceStr.indexOf(";", begin) + 1;
		}

		return target;
	}

	/**
	 * 将以特殊符号为分隔的字符串分割，去除特殊符号，并加入集合
	 * 目前只支持去�?";"，如原字符为"001;002;003",则返回的集合元素集:[001],[002],[003]
	 * 
	 * @param sourceStr
	 * @return Collection
	 */
	public static Collection filterStrBySpeciaChar2List(String sourceStr,
			String sign) {
		Collection target = new ArrayList();
		String tempStr = null;
		int begin = 0;

		while (sourceStr.indexOf(sign, begin) != -1) {
			tempStr = sourceStr
					.substring(begin, sourceStr.indexOf(sign, begin));
			target.add(tempStr);
			begin = sourceStr.indexOf(sign, begin) + 1;
		}

		return target;
	}

	/**
	 * 过滤字符串中的特殊字�?
	 * 
	 * @param sourceStr
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public static String filter(String sourceStr, char filterChar) {
		if (sourceStr == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < sourceStr.length(); i++) {
			if (sourceStr.charAt(i) != filterChar) {
				buffer.append(sourceStr.substring(i, i + 1));
			}

		}
		return buffer.toString();
	}

	/**
	 * 返回第一个字符大写，其余保持不变的字符串
	 * 
	 * @param sourceStr
	 *            String
	 * @return String
	 */
	public static String firstToUpperCase(String sourceStr) {
		if (isNullOrBlank(sourceStr))
			return sourceStr;
		if (sourceStr.length() == 1)
			return sourceStr.toUpperCase();

		return formatString(sourceStr.substring(0, 1).toUpperCase()
				+ sourceStr.substring(1));

	}

	/**
	 * 
	 * @param source
	 *            String
	 * @param target
	 *            String
	 * @return int
	 */
	public static int IndexOfIgnoreCase(String source, String target) {
		if (source == null || target == null)
			return -1;
		source = source.toLowerCase();
		target = target.toLowerCase();
		return source.indexOf(target);
	}

	/**
	 * 
	 * @param source
	 *            String
	 * @param target
	 *            String
	 * @return int
	 */
	public static int lastIndexOfIgnoreCase(String source, String target) {
		if (source == null || target == null)
			return -1;
		source = source.toLowerCase();
		target = target.toLowerCase();
		return source.lastIndexOf(target);
	}

	/**
	 * 
	 * @param source
	 *            String
	 * @param target
	 *            String
	 * @param index
	 *            int
	 * @return int
	 */
	public static int IndexOfIgnoreCase(String source, String target, int index) {
		source = source.toLowerCase();
		target = target.toLowerCase();
		return source.indexOf(target, index);
	}

	/**
	 * @todo 字符串去掉空比较是否相等
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @return boolean
	 */
	public static boolean strTrimedEqual(String str1, String str2) {
		if (str1 != null && str2 != null)
			return str1.trim().equals(str2.trim());
		else
			return str1.equals(str2);
	}

	/**
	 * 左补零 length <= 30
	 * 
	 * @param source
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String addLeftZero2Len(String source, int length) {
		if (isNullOrBlank(source))
			return ZEROSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return ZEROSTR.substring(0, length - source.length()) + source;
		}
	}

	/**
	 * 右补零 length <= 30
	 * 
	 * @param source
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String addRightZero2Len(String source, int length) {
		if (isNullOrBlank(source))
			return ZEROSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return source + ZEROSTR.substring(0, length - source.length());
		}
	}

	/**
	 * 用空字符给字符串补足不足指定长度部分
	 * 
	 * @param source
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String addRightBlank2Len(String source, int length) {
		if (isNullOrBlank(source))
			return BLANKSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return source + BLANKSTR.substring(0, length - source.length());
		}

	}

	/**
	 * 用空字符给字符串补足不足指定长度部分
	 * 
	 * @param source
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String addLeftBlank2Len(String source, int length) {
		String result;
		if (isNullOrBlank(source))
			result = BLANKSTR.substring(0, length);
		else {
			if (source.length() > length)
				result = source.substring(source.length() - length);
			else
				result = BLANKSTR.substring(0, length - source.length())
						+ source;
		}

		return result;
	}

	/**
	 * 乱序字符
	 * 
	 * @param result
	 * @param chars
	 */
	public static void mixChars(StringBuffer result, char[] chars) {
		if (chars != null && chars.length > 0) {
			int pos = (int) Math.floor(Math.random() * chars.length);
			if (result == null)
				result = new StringBuffer();
			result.append(chars[pos]);
			if (chars.length > 1) {
				char[] tmp = new char[chars.length - 1];
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (i != pos) {
						tmp[index] = chars[i];
						index++;
					}
				}
				mixChars(result, tmp);
			}
		}
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	public static StringBuffer inputStream2Buffer(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 针对jdk1.4 replace(char,char)提供jdk1.5中replace(String,String)的功能
	 * 
	 * @param source
	 * @param template
	 * @param target
	 * @return
	 */
	public static String replaceStr(String source, String template,
			String target) {
		int index = source.indexOf(template);
		if (index != -1) {
			source = source.substring(0, index) + target
					+ source.substring(index + template.length());
		}
		return source;
	}

	/**
	 * 针对jdk1.4 replace(char,char)提供jdk1.5中replace(String,String)的功能
	 * 
	 * @param source
	 * @param template
	 * @param target
	 * @return
	 */
	public static String replaceAllStr(String source, String template,
			String target) {
		int index = source.indexOf(template);
		while (index != -1) {
			source = source.substring(0, index) + target
					+ source.substring(index + template.length());
			index = source.indexOf(template);
		}
		return source;
	}

	/**
	 * 查询标记符号的结束位置
	 * 
	 * @param beginMarkSign
	 * @param endMarkSign
	 * @param source
	 * @param index
	 * @return
	 */
	public static int getMarkEndIndex(String beginMarkSign, String endMarkSign,
			String source, int startIndex) {
		int index = source.indexOf(endMarkSign, startIndex);
		int beginSignIndex = source.indexOf(beginMarkSign, startIndex);
		if (beginSignIndex == -1)
			return index;
		int tmpIndex = 0;

		while (index > beginSignIndex) {
			beginSignIndex = source.indexOf(beginMarkSign, beginSignIndex + 1);
			if (beginSignIndex == -1 || beginSignIndex > index)
				return index;
			tmpIndex = index;
			index = source.indexOf(endMarkSign, index + 1);
			// 找不到则返回
			if (beginSignIndex == -1) {
				if (index == -1)
					return tmpIndex;
				break;
			}
		}
		return index;
	}

	/**
	 * 查询标记符号的结束位置
	 * 
	 * @param beginMarkSign
	 * @param endMarkSign
	 * @param source
	 * @param index
	 * @return
	 */
	public static int getIgnoreCaseMarkEndIndex(String beginMarkSign,
			String endMarkSign, String source, int startIndex) {
		String tmpSource = source.toLowerCase();
		String beginMarkSignTmp = beginMarkSign.toLowerCase();
		String endMarkSignTmp = endMarkSign.toLowerCase();
		return getMarkEndIndex(beginMarkSignTmp, endMarkSignTmp, tmpSource,
				startIndex);
	}

	/**
	 * 通过正则表达式判断是否匹配
	 * 
	 * @param source
	 * @param regex
	 * @return
	 */
	public static boolean matchs(String source, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		return m.find();
	}

	/**
	 * md5加密
	 * 
	 * @param plaintext
	 * @return
	 */
	public static String encodeByMd5(String plaintext) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(plaintext, null);
	}

	public static String encodeBySha(String plaintext) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		return encoder.encodePassword(plaintext, null);
	}

	public static void main(String[] args) {

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		System.err.println(encoder.encodePassword("admin", null));

		String tmp = "@switch(#{paramType}$#[8];[createSelect(\"paramItems\",\"#{chooseValues}\",\"#\")#&nbsp;])";

		System.err.println(StringUtil.getMarkEndIndex("(", ")", tmp, 0));
		System.err.println(tmp.lastIndexOf(")"));
		String[] values = "a,b,c,d".split(",");
		for (int i = 0; i < values.length; i++) {
			System.err.println("value=" + values[i]);
		}
		System.err.println(StringUtil.encodeByMd5("admin"));
		System.err.println(StringUtil.encodeBySha("admin"));
	}

}
