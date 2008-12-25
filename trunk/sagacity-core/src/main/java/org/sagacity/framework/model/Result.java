/**
 * 
 */
package org.sagacity.framework.model;

import java.io.Serializable;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Result.java,Revision:v1.0,Date:Jun 19, 2008 12:47:45 AM $
 */
public class Result implements Serializable {
	private Object backObject;
	
	private String message;
	
	private int keyCode;

	/**
	 * @return the backObject
	 */
	public Object getBackObject() {
		return backObject;
	}

	/**
	 * @param backObject the backObject to set
	 */
	public void setBackObject(Object backObject) {
		this.backObject = backObject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the keyCode
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/**
	 * @param keyCode the keyCode to set
	 */
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
}
