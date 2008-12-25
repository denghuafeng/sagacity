/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.render.impl;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.render.Render;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>xtable 数据导出为pdf</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:PdfRender.java,Revision:v1.0,Date:2008-12-14 下午02:36:18 $
 */
public class PdfRender implements Render {

	/* 
	 * (non-Javadoc)
	 * @see org.sagacity.framework.web.views.tags.xtable.render.Render#render(javax.servlet.jsp.PageContext, javax.servlet.jsp.tagext.BodyContent, javax.servlet.jsp.JspWriter, org.sagacity.framework.web.views.tags.xtable.model.XTableModel)
	 */
	public void render(PageContext pageContext,BodyContent bodyContent,JspWriter writer, XTableModel tableModel) {
		// TODO Auto-generated method stub
		
	}
}
