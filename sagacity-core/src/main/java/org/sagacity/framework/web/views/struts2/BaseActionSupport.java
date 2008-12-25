package org.sagacity.framework.web.views.struts2;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 *@project sagacity-core
 *@description: $
 *               <p>
 *               实现actionMessages与acctionErrors跨redirect保存.
 *               实现绕过jsp/freemaker,直接输出文本.
 *               </p>
 *               $
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SimpleActionSupport.java,Revision:v1.0,Date:2008-10-24
 *          上午11:07:39 $
 */
public class BaseActionSupport extends ActionSupport implements ServletRequestAware {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String EDIT = "edit";
	protected final String DELETE = "delete";
	protected final String UPDATE = "update";

	protected final String QUERY = "query";

	protected final String DOWNLOAD = "download";

	protected final String UPLOAD = "upload";

	protected final String CREATE = "create";

	protected HttpServletRequest request = null;

	public void setServletRequest(HttpServletRequest servletRequest) {
		request = servletRequest;
	}
	/**
	 * 绕过Template,直接输出内容的简便函数. 
	 */
	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 直接输出字符串.
	 */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出HTML.
	 */
	protected String renderHtml(String html) {
		return render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出XML.
	 */
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=UTF-8");
	}
}
