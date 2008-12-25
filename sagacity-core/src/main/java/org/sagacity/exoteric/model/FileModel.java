/**
 * 
 */
package org.sagacity.exoteric.model;

import java.io.Serializable;

/**
 *@project sagacity-core 
 *@description:$<p>文件模型,主要用于文件下载</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:FileModel.java,Revision:v1.0,Date:Jun 17, 2008 5:32:49 PM $
 */
public class FileModel implements Serializable {
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件内容
	 */
	private Object fileContent;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 文件消息摘要，用于验证文件是否被修改
	 */
	private String messageDigest;

	/**
	 * @return the messageDigest
	 */
	public String getMessageDigest() {
		return messageDigest;
	}

	/**
	 * @param messageDigest the messageDigest to set
	 */
	public void setMessageDigest(String messageDigest) {
		this.messageDigest = messageDigest;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileContent
	 */
	public Object getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(Object fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
