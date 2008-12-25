/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.ArrayList;
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
 * @version $id:DBDictConvert.java,Revision:v1.0,Date:Jul 31, 2008 8:46:55 PM $
 */
public class DBDictConvert implements IConvert {
	private HashMap dictMap = null;
	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	
	/**
	 * sql
	 */
	private List params;

	/**
	 * 转换
	 * 
	 * @param key
	 * @return
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		if(key==null)
		{
			return null;
		}
		if (dictMap == null) {
			dictMap = new HashMap();
			if (params == null || params.isEmpty())
				return null;
			String sql = params.get(0).toString();
			logger.info("查询数据库获得数据对照关系,提供key,value形式的map取值!");
			List result = DBUtil.executSql(sql);
			List row;
			if (!result.isEmpty()) {
				for (int i = 0; i < result.size(); i++) {
					row = (List) result.get(i);
					if (row.get(0) instanceof String)
						dictMap.put(row.get(0).toString().trim(), row.get(1));
					else
						dictMap.put(row.get(0), row.get(1));
				}
			}
		}
		if (params != null && params.size() == 2) {
			List result = new ArrayList();
			String split = params.get(1).toString();
			String[] keys = key.toString().trim().split(split);
			/*
			 * if(keys.length==1) return dictMap.get(keys[0]);
			 */
			for (int i = 0; i < keys.length; i++) {
				if (StringUtil.isNotNullAndBlank(keys[i].trim())) {
					if(dictMap.get(keys[i].trim())==null)
						logger.error("字典对照:["+keys[i].trim()+"]对应的值为空!");
					result.add(dictMap.get(keys[i].trim()));
				}
			}
			if(result==null)
				logger.error("数据库取对照为空!key="+key.toString());
			return result;
		}
		if(dictMap.get(key.toString().trim())==null)
			logger.error("数据库取对照为空!key="+key.toString());
		return dictMap.get(key.toString().trim());
	}

	/**
	 * @return the params
	 */
	public List getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List params) {
		this.params = params;
	}

	public static void main(String[] args) {
		
	}
}
