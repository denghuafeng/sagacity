/**
 * 
 */
package org.sagacity.tools.excel.model;

import java.io.Serializable;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Result.java,Revision:v1.0,Date:Jul 29, 2008 3:15:16 PM $
 */
public class Result implements Serializable {
	/**
	 * 消息
	 */
	private String message="";
	
	/**
	 * 异常
	 */
	private Exception e;
	
	/**
	 * 结果标志
	 */
	private String resultFlag;

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
	 * @return the e
	 */
	public Exception getE() {
		return e;
	}

	/**
	 * @param e the e to set
	 */
	public void setE(Exception e) {
		this.e = e;
	}

	/**
	 * @return the resultFlag
	 */
	public String getResultFlag() {
		return resultFlag;
	}

	/**
	 * @param resultFlag the resultFlag to set
	 */
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
}
