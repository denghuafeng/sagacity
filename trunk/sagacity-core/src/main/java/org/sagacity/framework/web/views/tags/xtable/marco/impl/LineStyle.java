/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.utils.StringUtil;
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
 * @version $id:lineStyle.java,Revision:v1.0,Date:May 28, 2008 2:25:47 AM $
 */
public class LineStyle implements MarcoFacade {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.framework.web.views.tags.xtable.marco.TableMarcoExt#process(java.lang.Object,
	 *      java.lang.String)
	 */
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		String[] lineStyles = obj.toString().split(",");
		if ((rowIndex % 2) == 1)
			return StringUtil.replaceStr(template, "#["
					+ XTableConstants.PLACE_HOLDER + "]", lineStyles[1]);

		else
			return StringUtil.replaceStr(template, "#["
					+ XTableConstants.PLACE_HOLDER + "]", lineStyles[0]);
	}
}
