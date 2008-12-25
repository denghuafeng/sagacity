/**
 * 
 */
package org.sagacity.framework.web.views.resources.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *@project abchina
 *@description:$<p>用来从核心类包中解压web中图片、样式、脚本等资料</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:LoadResourcesListener.java,Revision:v1.0,Date:May 21, 2008
 *          10:31:11 AM $
 */
public class LoadResourcesListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		final String WEB_HOME = servletContextEvent.getServletContext()
				.getRealPath("/");
		try {
			ResourcesLoader.load(WEB_HOME);
		} catch (LoadResourcesException le) {
			le.printStackTrace();
		}
	}

}
