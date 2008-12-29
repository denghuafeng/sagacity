/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.sagacity.framework.utils.StringUtil;

/**
 *@project sagacity-core
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ExportFilter.java,Revision:v1.0,Date:2008-12-28 下午07:28:29 $
 */
public class ExportFilter extends AbstractExportFilter {
	private boolean responseHeadersSetBeforeDoFilter;

	public void init(FilterConfig filterConfig) throws ServletException {
		String responseHeadersSetBeforeDoFilter = filterConfig
				.getInitParameter("responseHeadersSetBeforeDoFilter");
		if (StringUtil.isNotNullAndBlank(responseHeadersSetBeforeDoFilter)) {
			this.responseHeadersSetBeforeDoFilter = new Boolean(
					responseHeadersSetBeforeDoFilter).booleanValue();
		}
	}

	public void destroy() {
	}

	protected void doFilterInternal(ServletRequest request,
			ServletResponse response, FilterChain chain, String exportFileName)
			throws IOException, ServletException {
		if (responseHeadersSetBeforeDoFilter) {
			setResponseHeaders((HttpServletResponse) response, exportFileName);
		}
		chain.doFilter(request, new ExportResponseWrapper(
				(HttpServletResponse) response));
		if (!responseHeadersSetBeforeDoFilter) {
			setResponseHeaders((HttpServletResponse) response, exportFileName);
		}
	}
}
