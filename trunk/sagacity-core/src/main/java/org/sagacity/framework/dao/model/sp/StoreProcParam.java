/**
 * 
 */
package org.sagacity.framework.dao.model.sp;

import java.io.Serializable;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:StoreProcParam.java,Revision:v1.0,Date:Jun 6, 2008 1:08:40 PM $
 */
public class StoreProcParam implements Serializable {
private int serial;
	
	private StoreProcParamType type;
	
	private Object paramValue;
	
	private int sqlType;
	
	/**
	 * 输入参数类型
	 */
	public static final StoreProcParamType IN = new StoreProcParamType("in");
	/**
	 * 输出参数类型 
	 */
	public static final StoreProcParamType OUT = new StoreProcParamType("out");
	
	/**
	 * 
	 * @param serial 参数的序号 从 1 开始
	 * @param type 参数的类型，是in 还是 out 
	 * @param paramValue 
	 */
	public StoreProcParam(int serial , StoreProcParamType type ,Object paramValue ,int sqlType)
	{
		this.serial = serial;
		this.type = type;
		this.paramValue = paramValue;
		this.sqlType = sqlType;
	}


	public Object getParamValue() {
		return paramValue;
	}


	public int getSerial() {
		return serial;
	}


	public StoreProcParamType getType() {
		return type;
	}


	public int getSqlType() {
		return sqlType;
	}
}
