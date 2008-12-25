package org.sagacity.framework.web.views.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

public class UploadTag extends BaseTagSupport {
	private String id="";
	private String picwidth="60";
	private String picheight="60";
	
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
			String picid = ((this.id != null) && (!"".equals(this.id))? this.id : this.property);
			
			writer.println("<p>");
			writer.println("<input type=\"file\" name=\"" + picid + "\" id=\"" + picid + "\" size=\"40\" onchange=\"" + picid + 
					"_show.src=" + picid +".value;pic_checkfile(" + picid + ", " + picid + "_show);\" " +
							"onblur=\"pic_checksize(" + picid + ", " + picid + "_show)\">");
			writer.println("<input type=\"button\" value=\"清空\" onclick=\"pic_clear(" + picid + ", " + picid + "_show)\">");
			writer.println("</p>");
			writer.println("<p>");
			writer.println("<img name=\"" + picid + "_show\" id=\"" + picid + "_show\" src=\"http://localhost:8080\" title=\"图像预览\"" +
					"width=\"" + this.picwidth + "\" height=\"" + this.picheight + "\">");
			writer.println("</p>");
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
	
	public String getPicheight() {
		return picheight;
	}
	public void setPicheight(String picheight) {
		this.picheight = picheight;
	}
	public String getPicwidth() {
		return picwidth;
	}
	public void setPicwidth(String picwidth) {
		this.picwidth = picwidth;
	}
}