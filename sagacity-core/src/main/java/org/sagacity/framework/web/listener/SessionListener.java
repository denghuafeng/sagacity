/**
 * 
 */
package org.sagacity.framework.web.listener;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.sagacity.framework.Constants;

/**
 * 
 *@project sagacity-core
 *@description:$<p>监听当前系统session的创建和销毁,统计在线人数</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SessionListener.java,Revision:v1.0,Date:2009-1-12 下午01:14:40 $
 */
public class SessionListener implements HttpSessionListener {
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// 在application范围由一个HashSet集保存所有的session
		HashSet sessions = (HashSet) application
				.getAttribute(Constants.GLOBAL_APP_SESSION);
		if (sessions == null) {
			sessions = new HashSet();
			application.setAttribute(Constants.GLOBAL_APP_SESSION, sessions);
		}

		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为"在线人数"
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet) application
				.getAttribute(Constants.GLOBAL_APP_SESSION);

		// 销毁的session均从HashSet集中移除
		sessions.remove(session);
	}

}
