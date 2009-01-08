/**
 * 
 */
package org.sagacity.framework.web.views.struts2.interceptor;

import org.sagacity.framework.exception.BaseException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.StaticParametersInterceptor;

/**
 *@project sagacity-core
 *@description:$<p>全局异常处理,想errorMessage中添加对终端用户可读的异常信息</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:GlobalExceptionInterceptor.java,Revision:v1.0,Date:2009-1-7
 *          上午10:01:33 $
 */
public class GlobalExceptionInterceptor extends StaticParametersInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1891886036813407166L;
	
	/**
	 * 
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Exception e) {
			ActionSupport actionSupport = (ActionSupport) invocation
					.getAction();
			exception(e, actionSupport);
			return "globalException";
		}
	}

	/**
	 * 处理异常将异常增加到ActionError堆栈中
	 * 
	 * @param e
	 * @param actionSupport
	 * @throws Exception
	 */
	private void exception(Exception e, ActionSupport actionSupport)
			throws Exception {
		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			if (be.getErrorKey() != null) {
				String errMsg = actionSupport.getText(be.getErrorKey(), be
						.getErrorArgs());
				actionSupport.addActionError(errMsg);
			} else {
				be.printStackTrace();
				String errMsg = actionSupport.getText("global.error.exception",
						be.getMessage());
				actionSupport.addActionError(errMsg);
			}
		} else {
			StackTraceElement[] elements = e.getStackTrace();
			for (int i = 0; i < elements.length; i++) {
				actionSupport.addActionError(elements[i].toString());
			}
		}
	}
}
