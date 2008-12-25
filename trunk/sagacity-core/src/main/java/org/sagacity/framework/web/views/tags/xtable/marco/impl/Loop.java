/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.XTableConstants;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:Loop.java,Revision:v1.0,Date:May 31, 2008 12:32:05 PM $
 */
public class Loop implements MarcoFacade {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.MarcoFacade#process(javax.servlet.jsp.PageContext,
	 *      com.abchina.framework.web.views.tags.xtable.model.XTableModel,
	 *      java.util.List, int, java.lang.Object, java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		try {
			String param = obj.toString().trim();
			if (param.indexOf("#[row.size()]") != -1)
				param = param.replaceAll("\\#\\[row\\.size\\(\\)\\]", Integer
						.toString(rowDataList.size()));
			int beginIndex = Integer.parseInt(param.substring(0, param
					.indexOf("..")));
			String endStr = param.substring(param.indexOf("..") + 2);
			
			int endIndex;
		
			if (endStr.indexOf("-") != -1)
				endIndex = Integer.parseInt(endStr.substring(0, endStr
						.indexOf("-")))
						- Integer.parseInt(endStr
								.substring(endStr.indexOf("-") + 1));
			else
				endIndex = Integer.parseInt(endStr);
			StringBuffer result = new StringBuffer();
			for (int i = beginIndex; i < endIndex; i++) {
				result.append(template.replaceAll("\\#\\["
						+ XTableConstants.PLACE_HOLDER + "\\]", rowDataList
						.get(i) == null ? "&nbsp;" : rowDataList.get(i)
						.toString()));
			}
			return result.toString();
		} catch (Exception e) {
			System.err
					.println("loop marco using like @loop(1..#[row.size()]-1+"
							+ tableModel.getMarcoSplitSign() + "<td>#["
							+ XTableConstants.PLACE_HOLDER + "]</td>)");
			e.printStackTrace();
		}
		return template;
	}

}
