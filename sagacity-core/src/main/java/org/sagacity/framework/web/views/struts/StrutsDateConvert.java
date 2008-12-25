/**
 * 
 */
package org.sagacity.framework.web.views.struts;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.ConvertUtils;
import org.sagacity.framework.web.views.struts.convert.DatePropertyConvert;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:StrutsDateConvert.java,Revision:v1.0,Date:Jun 7, 2008 4:34:48 PM $
 */
public class StrutsDateConvert extends HttpServlet {
	public void init() throws ServletException {
		System.out.println("system regist dateConvert success!");
		ConvertUtils.register(new DatePropertyConvert(),Date.class);
	}
}
