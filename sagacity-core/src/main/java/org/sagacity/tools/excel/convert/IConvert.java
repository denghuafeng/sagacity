/**
 * 
 */
package org.sagacity.tools.excel.convert;

import java.util.List;

import org.sagacity.tools.excel.model.ColumnModel;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:IConvert.java,Revision:v1.0,Date:Jul 31, 2008 9:27:34 PM $
 */
public interface IConvert {
	/**
	 * 参数导入
	 * @param params
	 */
	public void setParams(List params);
	
	/**
	 * 数据转换
	 * @param key
	 * @param rowData
	 * @return
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel);
}
