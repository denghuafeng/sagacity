/**
 * 
 */
package org.sagacity.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * <servlet><servlet-name>downloadServlet</servlet-name>
 * <servlet-class>com.abchina.framework.web.servlet.FileDownloadServlet</servlet-class>
 * <init-param> <param-name>sagacity.global.file.path</param-name>
 * <param-value>/upload</param-value> </init-param> <init-param>
 * <param-name>sagacity.global.file.isRelative</param-name> <param-value>false</param-value>
 * </init-param> <load-on-startup>1</load-on-startup> </servlet>
 * <servlet-mapping> <servlet-name>downloadServlet</servlet-name>
 * <url-pattern>/ServletDownload/**</url-pattern> </servlet-mapping>
 * 
 * @project abchina
 * @description:$
 *          <p>
 *          统一文件下载Servlet，支持inputStream,File,byte[]类型 提供FileModel形式
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:FileDownload.java,Revision:v1.0,Date:Jun 17, 2008 5:20:27 PM $
 */
public class FileDownloadServlet extends HttpServlet {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8629284029034604557L;

	// private static final String decode

	/**
	 * 文件上传路径
	 */
	private static String uploadPath = "/upload";

	/**
	 * 文件路径servlet param-name参数
	 */
	private final static String UPLOAD_FILE_PATH_PARAM = "sagacity.global.file.path";

	/**
	 * 文件上传路径是否相对路径参数
	 */
	private final static String UPLOAD_FILE_PATH_RELATE_FLAG_PARAM = "sagacity.global.file.isRelative";

	/**
	 * 显示的文件名称
	 */
	private final static String DOWN_DISPLAY_FILENAME = "showFileName";

	/**
	 * 实际存放的文件名称
	 */
	private final static String DOWN_REAL_FILENAME = "realFileName";

	/**
	 * 是否是新窗口
	 */
	private final static String DOWN_IS_NEW_WINDOW = "isNewWindow";

	/**
	 * 文件不存在报警
	 */
	private final static String FILE_UN_EXIST_ALARM = "fileUnExistAlarm";

	/**
	 * 文件不存在提示信息内容
	 */
	private static String alarmMessage = "文件不存在,请跟管理员联系!";
	
	/**
	 * 
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			// 应用的相对路径前缀
			String prefix = config.getServletContext().getRealPath("/");
			if (prefix == null)
				prefix = "";
			// 获取配置文件的文件名称
			uploadPath = config.getInitParameter(this.UPLOAD_FILE_PATH_PARAM);
			// 文件不存在提醒信息
			alarmMessage = config.getInitParameter(this.FILE_UN_EXIST_ALARM) == null ? alarmMessage
					: config.getInitParameter(this.FILE_UN_EXIST_ALARM);
			// 是否是相对路径
			String isRelatePath = config
					.getInitParameter(this.UPLOAD_FILE_PATH_RELATE_FLAG_PARAM) == null ? "true"
					: config
							.getInitParameter(this.UPLOAD_FILE_PATH_RELATE_FLAG_PARAM);
			if (isRelatePath.equalsIgnoreCase("true"))
				uploadPath = prefix + (uploadPath.indexOf("/") == 0 ? "" : "/")
						+ uploadPath;
			if ((uploadPath.lastIndexOf("/")+1)!= uploadPath.length())
				uploadPath += "/";
			logger.info("file upload path="+uploadPath);
		} catch (Exception e) {
			logger.error("fileDownload Servlet init Error:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String downFileName = req.getParameter(this.DOWN_DISPLAY_FILENAME);
		String realFileName = req.getParameter(this.DOWN_REAL_FILENAME);
		String isNewWindow = null;
		try {
			isNewWindow = req.getParameter(this.DOWN_IS_NEW_WINDOW);
		} catch (Exception e) {
			logger.info("file downLoadServlet:本页面下载文件!");
			e.printStackTrace();
		}
		logger.info("下载文件为:"+uploadPath + realFileName);
		File downFile = new File(uploadPath + realFileName);
		
		resp.setContentType("text/html;charset=UTF-8");
		//resp.setCharacterEncoding("UTF-8");
		if (!downFile.exists()) {
			System.out.println("下载文件为="+downFileName);
			logger.info("下载文件为:"+new String(downFileName.getBytes(),"UTF-8")+"不存在!");
			String alarm = StringUtil.replaceStr(alarmMessage, "{0}",
					new String(downFileName.getBytes(),"UTF-8"));
			resp.getWriter().print("<script type=\"text/javascript\">");
			resp.getWriter().print("alert('" + alarm + "');");
			if (isNewWindow != null)
			{
				resp.getWriter().print("window.close();");
			}
			resp.getWriter().print("</script>");
			return;
		}
		try {
			resp.setContentType("application/x-download");
			resp.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(downFileName, "UTF-8"));
			ServletOutputStream out = resp.getOutputStream();
			InputStream inStream = new FileInputStream(downFile);
			// 循环取出流中的数据
			byte[] b = new byte[1024];
			int len;
			while ((len = inStream.read(b)) > 0)
				out.write(b, 0, len);
			out.flush();
			out.close();
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
