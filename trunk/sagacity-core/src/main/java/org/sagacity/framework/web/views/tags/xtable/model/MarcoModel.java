/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

import java.io.Serializable;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:MarcoModel.java,Revision:v1.0,Date:May 27, 2008 11:51:16 PM $
 */
public class MarcoModel implements Serializable {
	private String marcoStr;
	private int begin;
	private int end;
	private String marcoName;
	private String param;

	private String template;

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the marcoStr
	 */
	public String getMarcoStr() {
		return marcoStr;
	}

	/**
	 * @param marcoStr
	 *            the marcoStr to set
	 */
	public void setMarcoStr(String marcoStr) {
		this.marcoStr = marcoStr;
	}

	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @return the marcoName
	 */
	public String getMarcoName() {
		return marcoName;
	}

	/**
	 * @param marcoName
	 *            the marcoName to set
	 */
	public void setMarcoName(String marcoName) {
		this.marcoName = marcoName;
	}
}
