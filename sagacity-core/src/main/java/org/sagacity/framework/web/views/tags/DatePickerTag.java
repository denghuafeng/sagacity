/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.StringUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          多功能日期控件
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DatePickerTag.java,Revision:v1.0,Date:Aug 18, 2008 8:33:25 PM $
 */
public class DatePickerTag extends BaseTagSupport {
	private final String COMPONENT_ID="DatePicker";
	
	// 日期格式
	private String format = "yyyy-MM-dd";

	// 是否可以为空
	private String empty = "true";

	/**
	 * 脚本
	 */
	private String script = "";

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
		
		// 获取时间值
		Object inputDate = null;
		Date dateValue = null;

		// 如果自动绑定则获取绑定的值
		if (this.autobind.equalsIgnoreCase("true"))
			inputDate = this.getPropertyValue();

		// 页面传值则用传过来的日期值
		if (inputDate == null && StringUtil.isNotNullAndBlank(this.value))
			inputDate = this.value;

		if (inputDate != null) {
			if (inputDate instanceof java.util.Date)
				dateValue = (java.util.Date) inputDate;
			else if (inputDate instanceof java.sql.Date)
				dateValue = (java.sql.Date) inputDate;
			else 
				dateValue = DateUtil.parseString(inputDate.toString());
		} else if (this.empty.equalsIgnoreCase("false"))
			dateValue = DateUtil.getNowTime();

		try {
			JspWriter writer = pageContext.getOut();
			//输入组件需要的js,css等资源
			super.renderResources(this.COMPONENT_ID, writer);
			String dateId = (this.styleId != null ? this.styleId
					: this.property);
			StringBuffer dateInput = new StringBuffer();
			dateInput.append("<input type=\"text\" id=\"" + dateId
					+ "\" class=\"Wdate\"");
			dateInput.append(" name=\"" + this.property + "\"");
			if (StringUtil.isNotNullAndBlank(this.onchange))
				dateInput.append(" onchange=\"" + this.onchange + "\"");
			if (StringUtil.isNotNullAndBlank(this.style))
				dateInput.append(" style=\"" + this.style + "\"");
			if (StringUtil.isNotNullAndBlank(this.onblur))
				dateInput.append(" onblur=\"" + this.onblur + "\"");
			if (dateValue != null) {
					dateInput.append(" value=\""
							+ DateUtil.formatDate(dateValue, this.format)
							+ "\"");
			}
			dateInput.append(" onclick=\"WdatePicker({dateFmt:'" + this.format
					+ "'})\"/>");
			writer.println(dateInput.toString());
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
		this.format = "yyyy-MM-dd";
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
	 * @return the empty
	 */
	public String getEmpty() {
		return empty;
	}

	/**
	 * @param empty
	 *            the empty to set
	 */
	public void setEmpty(String empty) {
		this.empty = empty;
	}
}