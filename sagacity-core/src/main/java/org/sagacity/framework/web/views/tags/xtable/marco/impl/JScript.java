/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:Script.java,Revision:v1.0,Date:May 22, 2008 5:23:32 PM $
 */
public class JScript implements MarcoFacade {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.TableMarcoExt#process(com.abchina.framework.web.views.tags.xtable.model.XTableModel,
	 *      int, java.lang.Object, java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		HashMap result = XTableUtil.parseMarcoObject(tableModel
				.getPropertiesMap(), obj, rowDataList, rowIndex);
		Object paramValue = result.get("paramValue");
		String paramName = (String) result.get("paramName");
		
		return "<script type=\"text/javascript\">"
				+ StringUtil.replaceStr(template, "#[" + paramName + "]",
						paramValue == null ? "" : paramValue.toString())
				+ "</script>";
	}
	
	
}
