package org.sagacity.framework.web.views.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:FileUpTag.java,Revision:v1.0,Date:2008-10-26 上午10:24:59 $
 */
public class FileUpTag extends BaseTagSupport {
	private String id = "";
	private String filetype = "";
	private String fileTxtLabl ="";
	private String fileLabl="";

	/**
	 * 
	 * @return int
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		if (this.isExportData())
		{
			this.release();
			return SKIP_BODY;
		}
		
		// 获取时间值
		String inputValue = null;
		// 页面传值则用传过来的日期值
		if (StringUtil.isNotNullAndBlank(this.value))
			inputValue = this.value;
		// 如果自动绑定则获取绑定的值
		else if (this.autobind.equalsIgnoreCase("true"))
			inputValue = (String) this.getPropertyValue();
		String contextPath =((HttpServletRequest) this.pageContext
				.getRequest()).getContextPath();
		try {
			JspWriter writer = pageContext.getOut();
			String fileId = StringUtil.isNotNullAndBlank(this.id) ? this.id : this.property;
			
			writer.println("<table >");
			writer.println("<tr>");
			writer.println("<td>" + fileLabl + "</td>");
			writer.println("<td>");
			writer.println("<div id=\"" + fileId + "\" class=\"" + fileId + "\"></div>");
			writer.println("</td>");
			writer.println("</tr>");
			
			writer.println("<tr>");
			writer.println("<td valign=\"top\"><div id=\"" + fileId + "_txt_lab\"><font color=\"red\" size=\"2\">" + fileTxtLabl + "</font></div></td>");
			writer.println("<td valign=\"top\">");
			writer.println("<div id=\"" + fileId + "_txt\"></div>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			
			writer.println("<script language=\"javascript\">");
			writer.println("CreateFile('" + fileId + "','" + fileId + "_txt', '" + this.filetype + "');");
			writer.println("</script>");
			
			writer.println("<style>");
			writer.println("form{ margin:0; padding:0;}");
			writer.println("." + fileId + "{ width:63px; height:13px; background:url("+contextPath+"/resources/images/addfile.gif) no-repeat left top; overflow:hidden;}");
			writer.println(".fileHover{ width:63px; height:13px; background:url("+contextPath+"/resources/images/addfilehover.gif) no-repeat left top; overflow:hidden;}");
			writer.println("#" + fileId + " input{ width:1%;size:0%; position:absolute; margin-left:-16px; z-index:100;filter:alpha(opacity=0);-moz-opacity:0;cursor:hand;}");
			writer.println("* html #" + fileId + " input{ margin-top:-5px; margin-left:-10px;}");
			writer.println("*+html #" + fileId + " input{ margin-top:-5px; margin-left:-10px;}");
			/*writer.println("#" + fileId + "_txt{ font-size:12px; color:#555; padding:10px;}");
			writer.println("#" + fileId + "_txt{ font-size:12px; color:#555; padding:10px;}");
			writer.println("#" + fileId + "_txt img{ cursor:point;}");
			writer.println("#" + fileId + "_txt_lab{ font-size:12px; color:#555; padding:10px;}");
			writer.println("#" + fileId + "_txt_lab{ font-size:12px; color:#555; padding:10px;}");
			writer.println("#" + fileId + "_txt_lab img{ cursor:point;}");*/
			writer.println("</style>");
			
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFileLabl() {
		return fileLabl;
	}

	public void setFileLabl(String fileLabl) {
		this.fileLabl = fileLabl;
	}

	public String getFileTxtLabl() {
		return fileTxtLabl;
	}

	public void setFileTxtLabl(String fileTxtLabl) {
		this.fileTxtLabl = fileTxtLabl;
	}

}
