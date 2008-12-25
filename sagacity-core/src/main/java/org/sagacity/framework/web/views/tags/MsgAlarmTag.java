/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 *@project sagacity-core
 *@description:$<p>页面操作消息提示</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:MsgAlarmTag.java,Revision:v1.0,Date:2008-11-11 上午11:37:33 $
 */
public class MsgAlarmTag extends BaseTagSupport {
	private final String COMPONENT_ID = "prompt";

	public int doStartTag() throws JspException {
		if (this.isExportData()) {
			this.release();
			return SKIP_BODY;
		}
		JspWriter writer = pageContext.getOut();
		try {
			// 输入组件需要的js,css等资源
			super.renderResources(this.COMPONENT_ID, writer);
		} catch (Exception e) {
			e.printStackTrace();
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
}
