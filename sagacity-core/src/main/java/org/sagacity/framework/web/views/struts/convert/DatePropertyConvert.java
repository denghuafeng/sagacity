/**
 * 
 */
package org.sagacity.framework.web.views.struts.convert;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

import org.sagacity.framework.utils.DateUtil;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:DatePropertyConvert.java,Revision:v1.0,Date:Jun 7, 2008 4:36:04 PM $
 */
public class DatePropertyConvert implements Converter {
	/**
	 * 日期类型转换
	 */
	public Object convert(Class type, Object value) {
		if(value == null)
		{
			return null;
		}
		if(((String)value).trim().length() == 0)
		{
			return null;
		}
		if(value instanceof String)
		{
			try{
				return DateUtil.parseString((String)value);
			}
			catch(Exception e)
			{
				e.printStackTrace();;
			}
		}
		return null;
	}
}