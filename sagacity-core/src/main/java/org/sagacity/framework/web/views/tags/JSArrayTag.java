/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import org.sagacity.framework.utils.StringUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:JSArrayTag.java,Revision:v1.0,Date:Sep 17, 2008 1:30:23 PM $
 */
public class JSArrayTag extends BaseTagSupport {
	/**
	 * javascript 数组变量名称
	 */
	private String var = null;

	private String properties;

	private HashMap paramHash;
	
	private List optionList;
	
	/** @todo Method Definition ---------------------------------------------- */
	public int doStartTag() throws JspException {
		if (this.isExportData()) {
			this.release();
			return SKIP_BODY;
		}
		try {
			// 获取选项
			Object dataSource = this.getDataResource();
			paramHash = TagUtil.getInstance().splitProperties(this.properties);
			optionList = TagUtil.getInstance().reflactList(dataSource, TagUtil.getInstance()
					.parseParamHash(paramHash), false, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		try {
			BodyContent body = getBodyContent();
			// 页面输出
			printHtmlToPage(body.getEnclosingWriter(),body.getString().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}

	/**
	 * out print to jsp page
	 * 
	 * @param writer
	 *            JspWriter
	 * @throws JspException
	 */
	private void printHtmlToPage(JspWriter writer, String tagBody) throws JspException {
		try {
			writer.print("<script type=\"text/javascript\">");
			writer.println("var " + this.var + "=[");	
			if (null != optionList && 0 < optionList.size()) {
				List item;
				int paramIndex;
				String paramName;
				String tmpStr="";
				Iterator propertyIter;
				int index = 0;
				for (int i = 0; i < optionList.size(); i++) {
					item = (List) optionList.get(i);
					tmpStr = tagBody;
					propertyIter = paramHash.keySet().iterator();
					while (propertyIter.hasNext()) {
						paramName = (String) propertyIter.next();
						paramIndex = ((Integer) paramHash.get(paramName))
								.intValue();
						tmpStr = StringUtil.replaceAllStr(tmpStr, "#{"
								+ paramName + "}",
								item.get(paramIndex) == null ? "" : item.get(
										paramIndex).toString());
					}				
					if (index != 0)
						writer.println(",");
					writer.println(tmpStr);
					index++;
				}
				
			}
			writer.println("];");
			writer.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @todo release Memery Source
	 */
	public void release() {
		this.releaseSource();
		this.var = null;
		this.properties = null;
		this.paramHash = null;
		this.optionList = null;
	}

	/**
	 * @return the var
	 */
	public String getVar() {
		return var;
	}

	/**
	 * @param var the var to set
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * @return the properties
	 */
	public String getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}

}
