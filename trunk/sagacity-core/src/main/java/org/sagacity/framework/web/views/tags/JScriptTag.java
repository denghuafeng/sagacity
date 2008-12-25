/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:JScriptTag.java,Revision:v1.0,Date:May 28, 2008 3:20:20 PM $
 */
public class JScriptTag extends BaseTagSupport {
	/**
	 * javascript 数组变量名称
	 */
	private String var = null;

	// firstName用于页面自定义首个选项
	private String firstName = null;

	// firstValue用于页面自定义首个选项对应的值
	private String firstValue = null;

	// selectTag Data
	private String display;

	/** @todo Constructor ---------------------------------------------------- */
	/** @todo Method Definition ---------------------------------------------- */
	public int doStartTag() throws JspException {
		if (this.isExportData())
		{
			this.release();
			return SKIP_BODY;
		}
		try {
			// 获取选项
			Object dataSource = this.getDataResource();

			HashMap paramHash = TagUtil.getInstance().parseParams(this.display + this.value);

			List optionList = TagUtil.getInstance().reflactList(dataSource, TagUtil.getInstance()
					.parseParamHash(paramHash), false, 1);

			// 页面输出
			printHtmlToPage(pageContext.getOut(), paramHash, optionList);
			return this.SKIP_BODY;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.releaseSource();
		}
		return this.SKIP_BODY;
	}

	public int doAfterBody() {
		return this.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}

	/** @todo Support Function ----------------------------------------------- */

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the firstValue
	 */
	public String getFirstValue() {
		return firstValue;
	}

	/**
	 * @param firstValue
	 *            the firstValue to set
	 */
	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * out print to jsp page
	 * 
	 * @param writer
	 *            JspWriter
	 * @throws JspException
	 */
	private void printHtmlToPage(JspWriter writer, HashMap paramHash,
			List optionList) throws JspException {
		try {
			writer.print("<script type=\"text/javascript\">");
			writer.println("var " + this.var + "=[");
			int index = 0;
			if (this.firstName != null && this.firstValue != null) {
				writer.println("[\"" + this.firstName + "\",\""
						+ this.firstValue + "\"]");
				index++;
			}
			if (null != optionList && 0 < optionList.size()) {
				String displayName = null;
				String optionValue = null;
				List item;
				for (int i = 0; i < optionList.size(); i++) {
					item = (List) optionList.get(i);
					displayName = replacePlaceHolder(item, this.display,
							paramHash);
					optionValue = replacePlaceHolder(item, this.value,
							paramHash);
					if (index != 0)
						writer.println(",");
					writer.println("[\"" + TagUtil.getInstance().quote(displayName) + "\",\""
							+ TagUtil.getInstance().quote(optionValue) + "\"]");
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
	 * 
	 * @param item
	 * @param placeHolderSource
	 * @param paramsHash
	 * @return
	 */
	private String replacePlaceHolder(List item, String placeHolderSource,
			HashMap paramsHash) {
		int index = 0;
		String param;
		for (Iterator iter = paramsHash.keySet().iterator(); iter.hasNext();) {
			param = (String) iter.next();
			if (placeHolderSource.indexOf("#{" + param + "}") != -1) {
				index = ((Integer) paramsHash.get(param)).intValue();
				placeHolderSource = StringUtil.replaceStr(placeHolderSource,
						"#{" + param + "}", item.get(index).toString());
			}
		}
		return placeHolderSource;
	}

	/**
	 * @todo release Memery Source
	 */
	public void release() {
		this.releaseSource();
		this.var = null;
		this.firstName = null;
		this.firstValue = null;
		this.display = null;
	}

	/**
	 * @return the var
	 */
	public String getVar() {
		return var;
	}

	/**
	 * @param var
	 *            the var to set
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/** @todo Accessor Definition -------------------------------------------- */

}
