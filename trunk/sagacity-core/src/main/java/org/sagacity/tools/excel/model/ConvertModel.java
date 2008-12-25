/**
 * 
 */
package org.sagacity.tools.excel.model;

import java.io.Serializable;
import java.util.List;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ConvertModel.java,Revision:v1.0,Date:Jul 31, 2008 9:26:39 PM $
 */
public class ConvertModel implements Serializable {
	/**
	 * convertName
	 */
	private String name;

	/**
	 * 处理类
	 */
	private String processClass;

	/**
	 * 参数集
	 */
	private List params;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the processClass
	 */
	public String getProcessClass() {
		return processClass;
	}

	/**
	 * @param processClass
	 *            the processClass to set
	 */
	public void setProcessClass(String processClass) {
		this.processClass = processClass;
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
