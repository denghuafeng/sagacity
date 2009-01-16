/**
 * 
 */
package org.sagacity.framework.web.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sagacity.framework.Constants;

/**
 *@project sagacity-core
 *@description:$<p>此Filter提供获取访问端IP地址,获取当前登陆用户Id等功能,以及统计</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:WebToolkitFilter.java,Revision:v1.0,Date:2009-1-12 下午01:08:27 $
 */
public class WebToolkitFilter implements Filter {
	private static ThreadLocal threadLocal = new ThreadLocal();

	/**
	 * The filter configuration object we are associated with. If this value is
	 * null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;

	// --------------------------------------------------------- Public Methods

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
		this.filterConfig = null;

	}

	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param result
	 *            The servlet response we are creating
	 * @param chain
	 *            The filter chain we are processing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HashMap currentHash = new HashMap();
		currentHash.put(Constants.HTTP_REQUIRE_IP, request.getRemoteAddr());
		currentHash.put(Constants.GLOBAL_CURRENT_USER_SESSION,
				((HttpServletRequest) request).getSession());
		threadLocal.set(currentHash);
		// Pass control on to the next filter
		chain.doFilter(request, response);
	}

	/**
	 * Place this filter into service.
	 * 
	 * @param filterConfig
	 *            The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// this.filterConfig = filterConfig;
		setFilterConfig(filterConfig);
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * 获取客户访问端的IP
	 * 
	 * @return
	 */
	public static String getClientIP() {
		Object obj = threadLocal.get();
		if (obj != null) {
			HashMap currentUserInfo = (HashMap) obj;
			return (String) currentUserInfo.get(Constants.HTTP_REQUIRE_IP);
		} else
			return null;
	}

	/**
	 * 获取当前用户的session信息
	 * 
	 * @return
	 */
	public static HttpSession getUserSession() {
		Object obj = threadLocal.get();
		if (obj != null) {
			HashMap currentUserInfo = (HashMap) obj;
			return (HttpSession) currentUserInfo
					.get(Constants.GLOBAL_CURRENT_USER_SESSION);
		} else
			return null;
	}
}
