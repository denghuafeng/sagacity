/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * @project abchina
 * @description:$ <p>
 *                FTP功能
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作�??</a>$
 * @version $id:FTPUtil.java,Revision:v1.0,Date:Aug 17, 2008 4:36:09 PM $
 */
public class FTPUtil {
	/**
	 * 定义全局日志
	 */
	protected final static Log logger = LogFactory.getFactory().getLog(
			FTPUtil.class);

	/**
	 * 获取ftp连接
	 * 
	 * @param server
	 * @param user
	 * @param password
	 * @return
	 */
	public static FtpClient getFtpConnection(String server, String user,
			String password) {
		FtpClient ftpClient = null;
		try {
			ftpClient = new FtpClient();
			ftpClient.openServer(server);
			ftpClient.login(user, password);
			logger.info("Connect Server:" + server + " Success...........");
		} catch (FtpLoginException fe) {
			fe.printStackTrace();
			logger.error("无权跟主�?:" + server + "连接!", fe);
		} catch (IOException ie) {
			ie.printStackTrace();
			logger.error("连接主机:" + server + "失败!", ie);
		} catch (SecurityException se) {
			se.printStackTrace();
			logger.error("无权限与主机:" + server + "连接!", se);
		}
		return ftpClient;
	}

	/**
	 * 上传文件
	 * 
	 * @param ftpClient
	 *            :ftpClient连接
	 * @param path
	 *            :上传的文件路�?
	 * @param fileName
	 *            :上传的文件名�?
	 */
	public static void upload(FtpClient ftpClient, String remotPath,
			String remoteFile, String localPath, String localFile) {
		try {
			if (StringUtil.isNotNullAndBlank(remotPath))
				ftpClient.cd(remotPath);
			ftpClient.binary();

			if (StringUtil.isNullOrBlank(remoteFile))
				remoteFile = localFile;
			TelnetOutputStream os = ftpClient.put(remoteFile);

			if (StringUtil.isNotNullAndBlank(localPath))
				localFile = localPath
						+ ((localPath.lastIndexOf("/") == localPath.length() - 1) ? ""
								: "/") + localFile;
			File file_in = new File(localFile);
			FileInputStream is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
			logger.info("上传文件:" + localFile + " 成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件操作失败!" + e.getMessage());
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param ftpClient
	 *            :ftp连接
	 * @param remotPath
	 *            :远程目录
	 * @param remoteFile
	 *            :远程文件
	 * @param is
	 *            ：文件内容流
	 */
	public static void upload(FtpClient ftpClient, String remotPath,
			String remoteFile, InputStream is) {
		try {
			if (StringUtil.isNotNullAndBlank(remotPath))
				ftpClient.cd(remotPath);
			ftpClient.binary();
			TelnetOutputStream os = ftpClient.put(remoteFile);

			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
			logger.info("上传文件:" + remoteFile + " 成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件:" + remoteFile + "操作失败!" + e.getMessage());
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param ftpClient
	 */
	public static void close(FtpClient ftpClient) {
		try {
			ftpClient.closeServer();
		} catch (IOException ie) {
			ie.printStackTrace();
			logger.error("取消连接操作失败!", ie);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param ftpClient
	 *            :ftpClient连接
	 * @param path
	 *            :上传的文件路�?
	 * @param fileName
	 *            :上传的文件名�?
	 */
	public static void download(FtpClient ftpClient, String remotePath,
			String remoteFile, String localPath, String localFile) {
		try {
			if (StringUtil.isNotNullAndBlank(remotePath))
				ftpClient.cd(remotePath);
			ftpClient.binary();
			TelnetInputStream is = ftpClient.get(remoteFile);
			if (StringUtil.isNullOrBlank(localFile))
				localFile = remoteFile;
			if (StringUtil.isNotNullAndBlank(localPath))
				localFile = localPath
						+ ((localPath.lastIndexOf("/") == localPath.length() - 1) ? ""
								: "/") + localFile;
			File file_in = new File(localFile);
			FileOutputStream os = new FileOutputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
			logger.info("下载文件:" + remoteFile + " 成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		/*
		 * SmsVO smsVO = new SmsVO(); smsVO.setContent("测试");
		 * smsVO.setCreateDate("20080827"); List MobileVOs = new ArrayList();
		 * MobileVO mobileVO = new MobileVO();
		 * mobileVO.setMobileNo("1397777777"); MobileVOs.add(mobileVO); MobileVO
		 * mobileVO1 = new MobileVO(); mobileVO1.setMobileNo("1397888888");
		 * MobileVOs.add(mobileVO1); smsVO.setMobileNos(MobileVOs);
		 * ByteArrayOutputStream bout = new ByteArrayOutputStream();
		 * java.net.URL
		 * url=Thread.currentThread().getClass().getResource("/smsTemplate.ftl"
		 * );
		 * 
		 * String realPath=url.getPath(); System.err.println(realPath); int
		 * pos=realPath.indexOf("file:/"); if(pos>-1)
		 * realPath=realPath.substring(6); System.err.println(realPath);
		 * InputStream is =
		 * Thread.currentThread().getClass().getResourceAsStream(
		 * "/smsTemplate.ftl"); File tmp=new File(realPath);
		 * System.err.println(tmp.getParent()); TemplateGenerator.create(new
		 * String[] { "smsVO" }, new Object[] { smsVO
		 * },tmp.getParent(),"smsTemplate.ftl", "tmp.txt");
		 * 
		 * FtpClient ftpClient = FTPUtil.getFtpConnection("202.7.3.199",
		 * "upload", "upload");
		 * 
		 * FTPUtil.upload(ftpClient, "/soft/upload/", "hytz20080908.txt",
		 * "","tmp.txt"); FTPUtil.close(ftpClient);
		 */
	}
}
