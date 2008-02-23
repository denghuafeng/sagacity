/**
 * 
 */
package org.sagacity.framework.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>字符串处理常用功能定义</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:StringUtil.java,Revision:v1.0,Date:Oct 19, 2007 10:09:42 AM $
 */
public class StringUtil {
	//size is 40,为了提高性能避免循环补零,提供字符串补零的源
    private final static String ZEROSTR="0000000000000000000000000000000000000000";
    private final static String BLANKSTR="                                        ";
    /**
     * private constructor,bannot be instantiated by other class
     **/
    private StringUtil() {}

    /**
     * 判断是否为空指针或者空字符
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
        if (str == null || str.trim().equals("") || str.equalsIgnoreCase("null")) {
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
     * 将字符串前加字符"0"转换成多位字符串
     * 比如 1-> 01 两位
     *      1->001 三位
     * @param str
     * @param length
     * @return String
     */
    public static String formatString(String str, int length) {
        StringBuffer sb = new StringBuffer();

        //如果为空指针,空字符串则返回空字符串,否则进行处理

        if (str != null && !(str.trim().equals("null"))) {

            //字符串长度小于需要长度时前面补"0"

            if (str.length() < length) {
                for (int i = 0; i < length - str.length(); i++) {
                    sb.append("0");
                }
                sb.append(str);
            } else { //直接返回

                sb.append(str);
            }
        } else {
            return "";
        }

        return sb.toString();
    }

    /**
     * 将字符串前加字符"0"转换成多位字符串
     * 比如 1-> 01 两位
     *      1->001 三位
     * @param intStr
     * @param length
     * @return String
     */
    public static String formatString(int intStr, int length) {
        return formatString(String.valueOf(intStr), length);
    }

    /**
     * 将字符串前加字符"0"转换成多位字符串
     * 比如 1-> 01 两位
     *      1->001 三位
     * @param intStr
     * @param length
     * @return String
     */
    public static String formatString(long intStr, int length) {
        return formatString(String.valueOf(intStr), length);
    }

    /**
    * 将字符串前加字符"0"转换成多位字符串
    * 比如 1-> 01 两位
    *      1->001 三位
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
     * 比较两个字符串，如果第一个参数为空，就取第二个参数
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
        //return firstValue==0?(int)defaultValue:(int)firstValue;
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
        return getDefaultOrSpecifiedValue(firstValue,
                                          (double) defaultValue.intValue());
    }

    public static int getDefaultOrSpecifiedValue(double firstValue,
                                                 Double defaultValue) {
        return getDefaultOrSpecifiedValue(firstValue, defaultValue.doubleValue());
    }


    /**
     * 将多位阿拉伯数字转换成中文表示
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
                    targetStr = "万" + targetStr;
                } else if (ii == 8) {
                    targetStr = "亿" + targetStr;
                } else if (ii != 0) {
                    targetStr = "零" + targetStr;

                }
                continue;
            }

            if (ii == 0) {
                targetStr = targetStr +
                            convertSingleDigitToChineseCharacter(temp);
            } else if (ii == 1) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "十" +
                            targetStr;
            } else if (ii == 2) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "百" +
                            targetStr;
            } else if (ii == 3) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "千" +
                            targetStr;
            } else if (ii == 4) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "万" +
                            targetStr;
            } else if (ii == 5) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "十" +
                            targetStr;
            } else if (ii == 6) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "百" +
                            targetStr;
            } else if (ii == 7) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "千" +
                            targetStr;
            } else if (ii == 8) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "亿" +
                            targetStr;
            } else if (ii == 9) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "十" +
                            targetStr;
            } else if (ii == 10) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "百" +
                            targetStr;
            } else if (ii == 11) {
                targetStr = convertSingleDigitToChineseCharacter(temp) + "千" +
                            targetStr;
            }

        }

        return targetStr;
    }

    /**
     * 将一位阿拉伯数字转换成中文表示
     * @param i
     * @return String
     */
    public static String convertSingleDigitToChineseCharacter(int i) {
        String tempS = null;
        switch (i) {
        case 0:
            tempS = "零";
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
     * 目前只支持去除";"，如原字符为"001;002;003",则返回的集合元素为:[001],[002],[003]
     * @param sourceStr
     * @return Collection
     */
    public static Collection filterStrBySpeciaChar2List(String
            sourceStr) {
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
     * 目前只支持去除";"，如原字符为"001;002;003",则返回的集合元素为:[001],[002],[003]
     * @param sourceStr
     * @return Collection
     */
    public static Collection filterStrBySpeciaChar2List(String
            sourceStr,String sign) {
        Collection target = new ArrayList();
        String tempStr = null;
        int begin = 0;
        
        while (sourceStr.indexOf(sign, begin) != -1) {
            tempStr = sourceStr.substring(begin, sourceStr.indexOf(sign, begin));
            target.add(tempStr);
            begin = sourceStr.indexOf(sign, begin) + 1;
        }

        return target;
    }

    /**
     * 过滤字符串中的特殊字符
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
     * @param sourceStr String
     * @return String
     */
    public static String firstToUpperCase(String sourceStr) {
        if (isNullOrBlank(sourceStr))
            return sourceStr;
        if (sourceStr.length() == 1)
            return sourceStr.toUpperCase();

        return formatString(sourceStr.substring(0, 1).toUpperCase() +
                            sourceStr.substring(1));

    }

    /**
     *
     * @param source String
     * @param target String
     * @return int
     */
    public static int IndexOfIgnoreCase(String source, String target) {
    	if(source==null || target==null)
    		return -1;
        source = source.toLowerCase();
        target = target.toLowerCase();
        return source.indexOf(target);
    }

    /**
     *
     * @param source String
     * @param target String
     * @param index int
     * @return int
     */
    public static int IndexOfIgnoreCase(String source, String target, int index) {
        source = source.toLowerCase();
        target = target.toLowerCase();
        return source.indexOf(target, index);
    }

    /**
     * @todo 字符串去掉空比较是否相等
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean strTrimedEqual(String str1,String str2)
    {
        if(str1!=null && str2!=null)
            return str1.trim().equals(str2.trim());
        else
            return str1.equals(str2);
    }

    /**
     * 左补零
     * length <= 30
     * @param source String
     * @param length int
     * @return String
     */
    public static String addLeftZero2Len(String source,int length)
    {
        if(isNullOrBlank(source))
            return ZEROSTR.substring(0,length);
        else
        {
            if(source.length()>length)
                return source.substring(source.length()-length);
            else
                return ZEROSTR.substring(0,length-source.length())+source;
        }
    }

    /**
     * 右补零
     * length <= 30
     * @param source String
     * @param length int
     * @return String
     */
    public static String addRightZero2Len(String source, int length) {
        if (isNullOrBlank(source))
            return ZEROSTR.substring(0, length);
        else {
            if (source.length() > length)
                return source.substring(source.length() - length);
            else
                return source+ZEROSTR.substring(0, length - source.length());
        }
    }


    /**
     * 用空字符给字符串补足不足指定长度部分
     * @param source String
     * @param length int
     * @return String
     */
    public static String addRightBlank2Len(String source,int length)
    {
        if (isNullOrBlank(source))
            return BLANKSTR.substring(0, length);
        else {
            if (source.length() > length)
                return source.substring(source.length() - length);
            else
                return source+BLANKSTR.substring(0, length - source.length());
        }

    }

    /**
     * 用空字符给字符串补足不足指定长度部分
     * @param source String
     * @param length int
     * @return String
     */
    public static String addLeftBlank2Len(String source,int length)
    {
        String result;
        if (isNullOrBlank(source))
             result=BLANKSTR.substring(0, length);
        else {
            if (source.length() > length)
                result=source.substring(source.length() - length);
            else
                result=BLANKSTR.substring(0, length - source.length()) + source;
        }
        
        return result;
    }
    
    /**
     * 乱序字符
     * @param result
     * @param chars
     */
    public static void mixChars(StringBuffer result,char[] chars)
	{
		if(chars!=null && chars.length>0)
		{
			int pos=(int)Math.floor(Math.random()*chars.length);
			if(result==null)
				result=new StringBuffer();
			result.append(chars[pos]);
			if(chars.length>1)
			{
				char[] tmp=new char[chars.length-1];
				int index=0;
				for(int i=0;i<chars.length;i++)
				{
					if(i!=pos)
					{
						tmp[index]=chars[i];
						index++;
					}
				}
				mixChars(result,tmp);
			}
		}
	}
}
