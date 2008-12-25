/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.NumberUtil;
import org.sagacity.framework.utils.DateUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:FormatInputTag.java,Revision:v1.0,Date:Sep 12, 2008 10:19:07 PM $
 */
public class FormatInputTag extends BaseTagSupport {
	/**
	 * 格式
	 */
	private String format;
	/**
	 * 数据类型
	 */
	private String dataType = "String";
	
	/**
	 * html元素类型,一般支持input
	 */
	private String type = "input";


	/**
	 * 
	 * @return int
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		if (this.isExportData()) {
			this.release();
			return SKIP_BODY;
		}
		Object inputData = this.getPropertyValue();

		try {
			JspWriter writer = pageContext.getOut();
			String tagParams = this.getTagParamsContent();
			String tagValue = "";
			if (this.format != null && inputData != null) {
				if (dataType.equalsIgnoreCase("number"))
					tagValue = NumberUtil.format(inputData, this.format);
				else if (dataType.equalsIgnoreCase("date"))
					tagValue = DateUtil.formatDate(inputData, this.format);
			}
			if (!this.type.equalsIgnoreCase("input"))
				writer.write(tagValue);
			else {
				writer.write("<input type=\"text\" " + tagParams);
				if (inputData != null)
					writer.write(" value=\"" + tagValue + "\"");
				writer.write(">");
			}

		} catch (Exception ei) {
			ei.printStackTrace();
			System.err.println(ei.getMessage());
		}
		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		return this.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}

	public void release() {
		this.format = null;
		this.dataType = "String";
		this.type = "input";
		this.releaseSource();
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
