/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 * @switch("#[status]$[1,2,3];[a,b,c]")
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:Case.java,Revision:v1.0,Date:Jun 10, 2008 9:44:06 PM $
 */
public class Switch implements MarcoFacade {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.MarcoFacade#process(javax.servlet.jsp.PageContext,
	 *      com.abchina.framework.web.views.tags.xtable.model.XTableModel,
	 *      java.util.List, int, java.lang.Object, java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		String caseResult = "&nbsp;";
		try {
			HashMap param = XTableUtil.parseMarcoObject(tableModel
					.getPropertiesMap(), obj, rowDataList, rowIndex);
			Object paramValue = param.get("paramValue");
	
			if (paramValue == null)
				paramValue = "null";
			String splitSign = ",";
			
			// 提取分割符号
			if (template.trim().indexOf("[") != 0)
				splitSign = template.trim().substring(0,
						template.trim().indexOf("["));

			template = template.substring(template.trim().indexOf("[") + 1,
					template.lastIndexOf("]"));

			String[] casesAndResults = template.split("\\]\\s*;\\s*\\[");
			String[] results = (casesAndResults[0] + splitSign + casesAndResults[1])
					.split(splitSign);
		
			for (int i = 0; i < results.length / 2; i++) {
				if (results[i].trim().equalsIgnoreCase(paramValue.toString().trim()))
				{
					caseResult = results[i + results.length / 2];
					break;
				}
				else if (i == results.length / 2 - 1) {
					if (results.length % 2 == 1)
						caseResult = results[results.length - 1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caseResult;
	}

	public static void main(String[] args) {
		String splitSign="";
		String template="@[";
		if (template.trim().indexOf("[") != 0)
			splitSign = template.trim().substring(0,
					template.trim().indexOf("["));
		System.err.println(splitSign);

	}
}
