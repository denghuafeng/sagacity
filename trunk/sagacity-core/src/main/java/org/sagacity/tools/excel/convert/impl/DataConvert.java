/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.math.BigDecimal;
import java.util.List;

import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DataConvert.java,Revision:v1.0,Date:Aug 14, 2008 10:25:07 AM $
 */
public class DataConvert implements IConvert {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		if (key == null)
			return null;
		else
		{
			BigDecimal value=(BigDecimal)key;
			return new BigDecimal(value.doubleValue()*10000);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		// TODO Auto-generated method stub

	}

}
