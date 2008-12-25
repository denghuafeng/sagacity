/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.HashMap;
import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.utils.DBUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:SequenceConvert.java,Revision:v1.0,Date:Aug 1, 2008 4:05:08 PM $
 */
public class SequenceConvert implements IConvert {
	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	private HashMap seqMap = new HashMap();

	/**
	 * sql
	 */
	private List params;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key, List rowData,ColumnModel colModel) {
		String mapKey;
		if (key == null)
			mapKey = colModel.getFieldName();
		else
			mapKey = key.toString();
		long sequenceNo = 0;
		String sql = null;
		//判断是否有sql
		if (params != null && params.size() > 0)
			sql = params.get(0).toString();
		//通过数据库获取当前最大值
		if (StringUtil.isNotNullAndBlank(sql)) {
			logger.info("查询当前表的Sequence 最大值!");
			List result = DBUtil.executSql(sql);
			List row;
			if (!result.isEmpty()) {
				//取第一行的第一个值
				for (int i = 0; i < result.size(); i++) {
					row = (List) result.get(i);
					if (row.get(0) instanceof Long)
						sequenceNo = ((Long) row.get(0)).longValue();
					else if (row.get(0) instanceof Integer)
						sequenceNo = ((Integer) row.get(0)).longValue();
					break;
				}
			} 
			//自动加一
			sequenceNo++;
		} else {
			//第一次
			if (seqMap.get(mapKey) == null)
				sequenceNo++;
			//非第一次
			else
				sequenceNo = ((Long) seqMap.get(mapKey)).longValue()+1;
			//放进hashMap中
			seqMap.put(mapKey, new Long(sequenceNo));
		}
		///System.err.println("$$$$$$$$$$$$$$$$$$$$$$="+sequenceNo);
		return new Long(sequenceNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		this.params = params;
	}

}
