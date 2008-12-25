/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:RowSelected.java,Revision:v1.0,Date:May 29, 2008 9:00:59 AM $
 */
public class RowSelected implements MarcoFacade {
	/*
	 * (non-Javadoc) page using example:
	 * <tr @rowSelect("#{param}"+marcoSplitSign+"pageElement,class=\"rowselected\"")
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.TableMarcoExt#process(com.abchina.framework.web.views.tags.xtable.model.XTableModel,
	 *      int, java.lang.Object, java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		HashMap propertiesMap = tableModel.getPropertiesMap();
		Iterator propertyIter = propertiesMap.keySet().iterator();
		String paramName;
		int paramIndex = -1;
		Object date;
		while (propertyIter.hasNext()) {
			paramName = (String) propertyIter.next();
			paramIndex = ((Integer) propertiesMap.get(paramName)).intValue();
			if (obj.equals("#[" + paramName + "]"))
				break;
		}
		if (paramIndex == -1)
			return "";
		else {
			date = rowDataList.get(paramIndex);
			String pageElement = template.substring(0, template.indexOf(","));
			if (pageContext.getRequest().getParameter(pageElement)
					.equalsIgnoreCase(date.toString()))
				return template.substring(template.indexOf(",") + 1);
			else
				return "";
		}
	}

}
