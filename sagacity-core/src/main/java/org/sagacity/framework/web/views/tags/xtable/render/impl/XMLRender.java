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
 *@description:$<p>xtable数据导为xml文件渲染</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:XMLRender.java,Revision:v1.0,Date:2008-12-14 下午02:26:07 $
 */
public class XMLRender implements Render {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.export.Export#export(javax.servlet.jsp.PageContext,
	 *      com.abchina.framework.web.views.tags.xtable.model.XTableModel,
	 *      java.lang.String, java.lang.String)
	 */
	public void render(PageContext pageContext,BodyContent bodyContent,JspWriter writer, XTableModel tableModel) {
		// TODO Auto-generated method stub
	}

}
