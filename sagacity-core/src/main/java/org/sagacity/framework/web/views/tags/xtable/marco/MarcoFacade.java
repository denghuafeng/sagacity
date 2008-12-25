/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          table标签自定义类型的标记宏接口定义
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:IXTablePlugin.java,Revision:v1.0,Date:May 14, 2008 10:30:58 PM $
 */
public interface MarcoFacade {
	/**
	 * 自定义宏扩展接口定义，通过实现该接口扩展列表页面展示功能，比方日期转换等
	 * @param pageContext
	 * @param tableModel
	 * @param rowDataList
	 * @param rowIndex
	 * @param obj
	 * @param template
	 * @return
	 */
	public String process(PageContext pageContext,XTableModel tableModel, List rowDataList,int rowIndex,
			Object obj, String template);
}
