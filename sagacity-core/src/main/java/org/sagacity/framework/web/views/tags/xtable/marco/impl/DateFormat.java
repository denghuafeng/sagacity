/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DateFormat.java,Revision:v1.0,Date:May 22, 2008 5:22:02 PM $
 */
public class DateFormat implements MarcoFacade {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.TableMarcoExt#process(java.lang.Object,
	 *      java.lang.String)
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
			if (date == null)
				return "";
			return DateUtil.formatDate(date, template);
		}
	}
}
