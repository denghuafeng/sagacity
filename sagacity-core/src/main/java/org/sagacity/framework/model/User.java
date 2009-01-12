/**
 * 
 */
package org.sagacity.framework.model;

import java.io.Serializable;

/**
 *@project sagacity-core
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:User.java,Revision:v1.0,Date:2009-1-12 下午02:26:54 $
 */
public class User implements Serializable {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
