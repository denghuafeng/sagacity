/**
 * 
 */
package org.sagacity.framework.web.views.resources.loader;

import org.sagacity.framework.exception.BaseException;

/**
 *@project sagacity-core
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:LoadResourcesException.java,Revision:v1.0,Date:2008-11-10
 *          下午04:41:37 $
 */
public class LoadResourcesException extends BaseException {
	public LoadResourcesException(String msg) {
		super(msg);
	}

	public LoadResourcesException(String msg, Exception e) {
		super(msg, e);
	}
}
