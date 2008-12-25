package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * 
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DataFormat.java,Revision:v1.0,Date:May 22, 2008 5:38:39 PM $
 */
public class DataFormat implements MarcoFacade {
	private static DecimalFormat df = (DecimalFormat) DecimalFormat
			.getInstance();

	private static String pattern = "";

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
		Object data;
		while (propertyIter.hasNext()) {
			paramName = (String) propertyIter.next();
			paramIndex = ((Integer) propertiesMap.get(paramName)).intValue();
			if (obj.equals("#[" + paramName + "]"))
				break;
		}
		if (paramIndex == -1)
			return "";
		else {
			data = rowDataList.get(paramIndex);
			if (data == null)
				return "";
			try {
				//判断模式是否改变
				if (!pattern.equalsIgnoreCase(template)) {
					df.applyPattern(template);
					pattern = template;
				}
				if (data instanceof String) {
					return df.format(new Double((String) data));
				} else
					return df.format(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
