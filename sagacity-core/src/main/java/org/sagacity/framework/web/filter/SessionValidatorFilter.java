/**
 * 
 */
package org.sagacity.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *@project abchina 
 *@description:$<p>验证session是否过期的filter</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SessionValidatorFilter.java,Revision:v1.0,Date:Jul 3, 2008 3:07:04 PM $
 */
public class SessionValidatorFilter implements Filter {
	/**
	 * 排除session验证的页面,主要是登陆操作页面
	 */
	private String excludes="/login.jsp*";
	
	/**
	 * 页面如果运用框架，建议使用中间页面跳转到登陆页面
	 */
	private String redirectUrl="/relogin.jsp";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
