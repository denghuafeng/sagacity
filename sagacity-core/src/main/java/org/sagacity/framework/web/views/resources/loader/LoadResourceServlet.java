/**
 * 
 */
package org.sagacity.framework.web.views.resources.loader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 *@project sagacity-core
 *@description:$<p>加载sagacity 组件资源的Servlet,解压相关css,js,image等文件到应用下</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:LoadResourceServlet.java,Revision:v1.0,Date:2008-11-10
 *          下午10:53:29 $
 */
public class LoadResourceServlet extends HttpServlet {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	
	public void init(ServletConfig config) throws ServletException {
		final String WEB_HOME = config.getServletContext().getRealPath("/");
		try {
			ResourcesLoader.load(WEB_HOME);
		} catch (LoadResourcesException le) {
			le.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}

	public void destroy() {
		super.destroy();
	}

}
