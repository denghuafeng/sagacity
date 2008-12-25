/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.HashMap;
import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:EnumConvert.java,Revision:v1.0,Date:Jul 31, 2008 8:58:53 PM $
 */
public class EnumConvert implements IConvert {
	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	private HashMap enumMap = null;
	
	/**
	 * 枚举参数
	 */
	private List params;

	/**
	 * 转换
	 * 
	 * @param key
	 * @return
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		Object result=null;
		try {
			if (enumMap == null) {
				enumMap=new HashMap();
				String split = ",";
				String keys[];
				String values[];
				if (params.size() == 3) {
					split = params.get(0).toString();
					keys = params.get(1).toString().split(split);
					values = params.get(2).toString().split(split);
				} else {
					keys = params.get(0).toString().split(split);
					values = params.get(1).toString().split(split);
				}
				for(int i=0;i<keys.length;i++)
				{
					if(i>values.length-1)
						enumMap.put(keys[i], null);
					else
						enumMap.put(keys[i], values[i]);
				}
			}
			return enumMap.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("枚举匹配错误!key="+key, e.fillInStackTrace());
		}
		return result;
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
}
