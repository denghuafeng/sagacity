/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.render;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.sagacity.framework.web.views.tags.xtable.model.XGridModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:Export.java,Revision:v1.0,Date:May 24, 2008 9:55:05 AM $
 */
public interface Render {
	/**
	 * 通过各种自定义的render实现html页面表格输出，pdf,excel等文件格式下载
	 * @param pageContext
	 * @param tableModel
	 * @return
	 */
	public void render(PageContext pageContext,BodyContent bodyContent, JspWriter writer,
			XTableModel tableModel);
}
