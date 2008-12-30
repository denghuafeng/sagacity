/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sagacity.framework.web.views.tags.xtable.XTableConstants;
import org.sagacity.framework.web.views.tags.xtable.filter.resolver.ViewResolver;
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;

/**
 *@project sagacity-core
 *@description:$<p>数据导出过滤器</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:AbstractExportFilter.java,Revision:v1.0,Date:2008-12-28
 *          下午07:32:14 $
 */
public abstract class AbstractExportFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 判断是否有xtable或xgrid的数据导出操作
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		boolean isExported = this.isExport(httpRequest);

		Object exportData = request
				.getAttribute(XTableConstants.XTABLE_EXPORT_VIEW);
		ExportModel exportModel = null;
		if (exportData != null)
			exportModel = (ExportModel) exportData;
		if (isExported) {
			String exportFileName = (exportModel.getExportFile() == null ? Long
					.toString(System.currentTimeMillis()) : exportModel
					.getExportFile())
					+ "." + exportModel.getExtName();
			doFilterInternal(request, response, chain, exportFileName);
			handleExport((HttpServletRequest) request,
					(HttpServletResponse) response,exportModel);
		} else {
			chain.doFilter(request, response);
		}
	}

	protected void handleExport(HttpServletRequest request,
			HttpServletResponse response,ExportModel exportModel) {
		try {
			if (exportModel != null) {
				//String viewResolver = exportModel.getExportRender();
				String viewResolver="org.sagacity.framework.web.views.tags.xtable.filter.resolver.XlsViewResolver";
				Class classDefinition = Class.forName(viewResolver);
				ViewResolver handleExportViewResolver = (ViewResolver) classDefinition
						.newInstance();
				handleExportViewResolver.resolveView(request, response,
						exportModel);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setResponseHeaders(HttpServletResponse response,
			String exportFileName) {
		String mimeType = "application/x-download";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ exportFileName + "\"");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	}

	/**
	 * 判断是否导出
	 * 
	 * @param request
	 * @return
	 */
	private boolean isExport(HttpServletRequest request) {
		String isExport = null;
		try {
			isExport = request.getParameter(XTableConstants.IS_EXPORT_PARAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isExport == null)
			return false;
		if (isExport.equalsIgnoreCase(XTableConstants.IS_EXPORT_PARAM_RESULT))
			return true;
		else
			return false;
	}

	protected abstract void doFilterInternal(ServletRequest request,
			ServletResponse response, FilterChain chain, String exportFileName)
			throws IOException, ServletException;
}
