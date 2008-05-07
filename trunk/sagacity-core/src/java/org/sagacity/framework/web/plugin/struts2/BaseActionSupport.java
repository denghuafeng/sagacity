/**
 * 
 */
package org.sagacity.framework.web.plugin.struts2;

import com.opensymphony.xwork2.ActionSupport;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * 
 * @project sagacity-core
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseActionSupport.java,Revision:v1.0,Date:Mar 7, 2008 12:35:38
 *          PM $
 */
public class BaseActionSupport extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final String DELETE = "delete";

	protected final String UPDATE = "update";

	protected final String QUERY = "query";

	protected final String DOWNLOAD = "download";

	protected final String UPLOAD = "upload";
	
	protected final String CREATE = "create";

	/**
	 * 分页当前页码
	 */
	public Integer pageNo;

	/**
	 * 定义日志,在Service层代码继承BaseService,直接在程序中使用log.info,log.error等
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 增加操作提示信息
	 * 
	 * @param msgKey
	 */
	protected void addActionMessages(String msgKey) {

	}

	/**
	 * 增加操作提示信息
	 * @param msgKey
	 * @param arg
	 */
	protected void addActionMessages(String msgKey, String arg) {

	}

	/**
	 * 增加操作提示信息
	 * @param msgKey
	 * @param args
	 */
	protected void addActionMessages(String msgKey, String[] args) {

	}
}
