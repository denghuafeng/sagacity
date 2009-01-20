/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.List;

import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.utils.DBUtil;

/**
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:DBQueryConvert.java,Revision:v1.0,Date:2009-1-20 下午04:44:23 $
 */
public class DBQueryConvert implements IConvert {
	/**
	 * sql
	 */
	private List params;

	/* (non-Javadoc)
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		List result=DBUtil.executSql(params.get(0).toString(), key);
		if(result!=null && !result.isEmpty())
			return ((List)result.get(0)).get(0);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		this.params=params;
	}

}
