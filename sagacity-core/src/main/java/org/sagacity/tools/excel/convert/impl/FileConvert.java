/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:FileConvert.java,Revision:v1.0,Date:Aug 9, 2008 3:34:53 PM $
 */
public class FileConvert implements IConvert {
	private String template;
	private String regx = "#placeHolder#";
	private String filePath;
	private String distPath;

	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	private List params;

	private String fileName;

	private String randFileName;

	private String realFileName;
	private int result = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		result = -1;
		if (params != null && !params.isEmpty()) {
			template = params.get(0).toString();
			filePath = params.get(1).toString();
			if (filePath.indexOf("/") != filePath.length() - 1)
				filePath += "/";
			if (params.size() == 3) {
				distPath = params.get(2).toString();
				if (distPath.indexOf("/") != distPath.length() - 1)
					distPath += "/";
				if (!new File(distPath).exists()) {
					new File(distPath).mkdir();
				}
			} else
				distPath = filePath;
		}
		if (template == null || regx == null || key == null)
			return key;
		else {
			// /System.err.println("修改文件名称=" + key.toString());
			fileName = key.toString().trim();
			realFileName = filePath + fileName;
			realFileName = StringUtil.replaceStr(template, regx, realFileName);
			try {
				Thread.currentThread().sleep(25);
				randFileName = "" + System.currentTimeMillis();

				randFileName = StringUtil.replaceStr(template, regx,
						randFileName);
				result = copyFile(realFileName, distPath + randFileName);
				if (result == 1)
					return randFileName;
				else if (result == 0)
					logger.error("文件=" + realFileName + "改名为=" + randFileName
							+ "操作失败!");
				else
					logger.error("文件=" + realFileName + "不存在!计划改名对应的文件为="
							+ randFileName);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("文件转换出错!", e.fillInStackTrace());
			}

			return randFileName;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		// TODO Auto-generated method stub
		this.params = params;
	}
	
	/**
	 * 复制文件
	 * @param oldPathFile
	 * @param newPathFile
	 * @return
	 */
	private int copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				return 1;
			} else {
				logger.error("文件=" + realFileName + "不存在!计划改名对应的文件为="
						+ randFileName);
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("复制文件:" + oldPathFile + " 到目标文件:" + newPathFile
					+ " 操作失败!");
			System.err.println("复制单个文件操作出错");
			return 0;
		}
	}
}
