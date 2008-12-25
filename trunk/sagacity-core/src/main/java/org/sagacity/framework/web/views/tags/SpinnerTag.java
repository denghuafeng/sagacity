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
 * 
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:SpinnerTag.java,Revision:v1.0,Date:May 24, 2008 2:14:24 PM $
 */
public class SpinnerTag extends BaseTagSupport {
	private String id = "";

	private String min = "";

	private String max = "";

	private String step = "";

	private String divid = "div_";

	private String buttonid = "button_";

	/**
	 * 
	 * @return int
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		// 获取微调器默认值
		String inputValue = null;
		// 页面传值则用传过来的值
		if (StringUtil.isNotNullAndBlank(this.value))
			inputValue = this.value;
		// 如果自动绑定则获取绑定的值
		else if (this.autobind.equalsIgnoreCase("true"))
			inputValue = (String) this.getPropertyValue();

		try {
			JspWriter writer = pageContext.getOut();
			String sprinnerid = ((this.id != null) && (!"".equals(this.id))? this.id : this.property);
			
			writer.println("<div id=\"" + this.divid + this.property + "\">");
			writer.println("<td>");
			writer.println("<span class=\"zxTime_frame\" style=\"width:40 px;\" valign=\"middle\">");
			writer.println("<input type=\"text\" id=\"" + sprinnerid + "\" name=\"" + this.property + "\" " +
					"onchange=\"initspinner('" + this.min + "', '" + this.max + "'); " +
							"sprinnerchange(this)\" onfocus=\"setvalue(this)\" value='" + 
							(StringUtil.isNotNullAndBlank(inputValue)? this.value:this.min)  + "'/>");
			writer.println("</span>");
			writer.println("</td>");
			writer.println("<td>");
			writer.println("<span class=\"zxTime_frame\" style=\"width:18 px; height:26 px;\" valign=\"middle\">");
			writer.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >");
			writer.println("<tr><td><button id=\""+this.buttonid + this.property + "_up\" class=\"zxTime_arrow\" " +
					"onmouseup=\"initspinner('" + this.min +"', '" + this.max + "'); " +
							"evaluate(" + this.property + ",'" + this.step + "');\" >5</button></td></tr>");
			writer.println("<tr><td><button id=\""+this.buttonid + this.property + "_down\" class=\"zxTime_arrow\" " +
					"onmouseup=\"initspinner('" + this.min +"', '" + this.max + "'); " +
					"evaluate(" + this.property + ",'-" + this.step + "');\" >6</button></td></tr>");
			writer.println("</table>");
			writer.println("</span>");
			writer.println("</td>");
			writer.println("</div>");
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
		this.releaseSource();
		return this.EVAL_PAGE;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
