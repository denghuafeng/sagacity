/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SwitchTag.java,Revision:v1.0,Date:Aug 8, 2008 10:39:59 AM $
 */
public class SwitchTag extends BaseTagSupport {
	public int doStartTag() throws JspException {
		
		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		return this.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		this.releaseSource();
		return this.EVAL_PAGE;
	}
	
}
