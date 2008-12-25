/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExportType.java,Revision:v1.0,Date:May 27, 2008 10:51:30 PM $
 */
public class ExportType implements MarcoFacade {

	/*
	 * (non-Javadoc) 解析和处理分页条中的export宏，根据xtable中定义的导出类型，设置对应的export url参数信息
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.TableMarcoExt#process(java.lang.Object,
	 *      java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		if (!tableModel.isHasExport())
			return "";
		
		if(obj.toString().equalsIgnoreCase("0"))
			return "";
		
		HashMap exportTypes = tableModel.getExportTypes();
		StringBuffer exportTypeBtns = new StringBuffer();
		Iterator iter = exportTypes.keySet().iterator();
		String exportType;
		ExportModel exportModel;
		
		while (iter.hasNext()) {
			exportType = (String) iter.next();
			exportModel = (ExportModel) exportTypes.get(exportType);
			String exportAction;

			exportAction = tableModel.getExportAction() + "&"
					+ tableModel.getExportParam() + "="
					+ URLEncoder.encode(exportModel.getParam());
			exportAction += "&xtable_is_export=" + URLEncoder.encode("true");

			exportTypeBtns.append(
					template.replaceFirst("\\#\\{title\\}",
							exportModel.getTitle()).replaceFirst(
							"\\#\\{exportAction\\}", exportAction));
		}
		return exportTypeBtns.toString();
	}
}
