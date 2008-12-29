/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.filter.resolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;

/**
 *@project sagacity-core
 *@description:$<p>定义</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ViewResolver.java,Revision:v1.0,Date:2008-12-29 下午02:11:31 $
 */
public interface ViewResolver {
	/**
	 * 处理表格数据
	 * @param request
	 * @param response
	 * @param viewData
	 * @throws Exception
	 */
	public void resolveView(ServletRequest request, ServletResponse response,
			ExportModel viewData) throws Exception;
}
