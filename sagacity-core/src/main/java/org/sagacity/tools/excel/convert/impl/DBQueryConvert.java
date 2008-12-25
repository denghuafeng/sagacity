/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.List;

import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.utils.DBUtil;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:DBQueryConvert.java,Revision:v1.0,Date:Jul 31, 2008 10:12:02 PM $
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
