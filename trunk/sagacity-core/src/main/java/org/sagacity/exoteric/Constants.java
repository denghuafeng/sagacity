/**
 * 
 */
package org.sagacity.exoteric;

import java.io.Serializable;

/**
 *@project sagacity-core 
 *@description:$<p>通用web项目常量定义，扩展项目可以延续使用</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Constants.java,Revision:v1.0,Date:2008-12-9 下午01:36:13 $
 */
public class Constants implements Serializable {
	public static final String STATUS_INVALID = "0";
	public static final String STATUS_VALID = "1";

	public static final String STATUS_AUTH = "1";
	public static final String STATUS_UNAUTH = "0";

	public static final String RESOURCE_URL = "1";
	public static final String RESOURCE_FUNCTION = "2";
	public static final String RESOURCE_COMPONENT = "3";
	
	/**
	 * 存放用户信息的session id常量定义
	 */
	public static final String USER_INFO_SESSION_ID = "USER_INFO_SESSION_ID";
}
