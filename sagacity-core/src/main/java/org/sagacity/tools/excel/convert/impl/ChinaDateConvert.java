/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ChinaDateConvert.java,Revision:v1.0,Date:Aug 1, 2008 11:52:13 AM $
 */
public class ChinaDateConvert implements IConvert {
	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	
	private static final String[] keys = { "○", "О","0","Ο","O","零", "一", "二", "三",
		"四", "五", "六", "七", "八", "九", "十", "年", "月", "日" };
	private static final String[] values ={ "0", "0","0","0","0", "0", "1", "2", "3",
		"4", "5", "6", "7", "8", "9", "10", "-", "-", "" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		if (key == null || StringUtil.isNullOrBlank(key.toString()))
			return null;
		String chinaDate = key.toString().trim();

		for (int i = 0; i < keys.length; i++) {
			chinaDate = chinaDate.replaceAll(keys[i], values[i]);
		}
		String[] chinaDateAry = chinaDate.split("-");
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < chinaDateAry.length; i++) {
			if (i == 0) {
				result.append(chinaDateAry[0]);
			} else {
				if (chinaDateAry[i].length() == 4)
					result.append(chinaDateAry[i].replaceFirst("10", ""));
				else if (chinaDateAry[i].length() == 3)
					result.append(chinaDateAry[i].replaceFirst("0", ""));
				else if (chinaDateAry[i].length() == 2)
					result.append(chinaDateAry[i]);
				else
					result.append("0" + chinaDateAry[i]);
			}
		}
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String key = "二ΟΟО年四月二十一日";
		String[] keys ={ "○", "О","0","Ο","O","零", "一", "二", "三",
				"四", "五", "六", "七", "八", "九", "十", "年", "月", "日" };
		String[] values = { "0", "0","0","0","0", "0", "1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "-", "-", "" };
		String chinaDate = key.toString().trim();

		for (int i = 0; i < keys.length; i++) {
			chinaDate = chinaDate.replaceAll(keys[i], values[i]);
		}
		String[] chinaDateAry = chinaDate.split("-");
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < chinaDateAry.length; i++) {
			if (i == 0) {
				result.append(chinaDateAry[0]);
			} else {
				if (chinaDateAry[i].length() == 4)
					result.append(chinaDateAry[i].replaceFirst("10", ""));
				else if (chinaDateAry[i].length() == 3)
					result.append(chinaDateAry[i].replaceFirst("0", ""));
				else if (chinaDateAry[i].length() == 2)
					result.append(chinaDateAry[i]);
				else
					result.append("0" + chinaDateAry[i]);
			}
		}
		
		System.err.println(result);
		System.err.println(result.toString().equalsIgnoreCase("20000421"));
	
	}
}
