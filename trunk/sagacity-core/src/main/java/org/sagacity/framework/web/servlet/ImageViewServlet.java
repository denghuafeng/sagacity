/**
 * 
 */
package org.sagacity.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.ImageUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          图片浏览Servlet
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ImageViewServlet.java,Revision:v1.0,Date:Aug 1, 2008 5:43:16 PM $
 */
public class ImageViewServlet extends HttpServlet {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

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
	 * 文件不存在报警
	 */
	private final static String FILE_UN_EXIST_ALARM = "fileUnExistAlarm";

	/**
	 * 文件不存在提示信息内容
	 */
	private static String alarmMessage = "图片不存在,请跟管理员联系!";

	/**
	 * 实际存放的文件名称
	 */
	private final static String DOWN_REAL_FILENAME = "image";

	/**
	 * 缩放比率
	 */
	private final static String IMAGE_ZOOM_RATIO = "ratio";

	/**
	 * 缩放宽度
	 */
	private final static String IMAGE_ZOOM_WIDTH = "width";

	/**
	 * 高度
	 */
	private final static String IMAGE_ZOOM_HEIGHT = "height";

	/**
	 * 使用上下问路径
	 */
	private final static String CONTEXT_PATH = "ctxPath";

	private String prefix;

	/**
	 * 
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			// 应用的相对路径前缀
			prefix = config.getServletContext().getRealPath("/");
			if (prefix == null)
				prefix = "";
			if (prefix.lastIndexOf("/") != prefix.length() - 1)
				prefix = prefix + "/";
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
			if ((uploadPath.lastIndexOf("/") + 1) != uploadPath.length())
				uploadPath += "/";
			logger.info("file upload path=" + uploadPath);
		} catch (Exception e) {
			logger.error("fileDownload Servlet init Error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 写出图片流
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		try {
			String path = "";
			req.setCharacterEncoding("UTF-8");
			String realFileName = req.getParameter(this.DOWN_REAL_FILENAME);
			String ctxPath = null;
			try {
				ctxPath = req.getParameter(this.CONTEXT_PATH);
			} catch (Exception e) {

			}
			File image = null;
			if (ctxPath != null && ctxPath.equalsIgnoreCase("true"))
				path = prefix;
			else
				path = uploadPath;

			image = new File(path + realFileName);
			
			if (!image.exists()) {
				resp.setContentType("text/html;charset=UTF-8");
				resp.getWriter().print("<script type=\"text/javascript\">");
				resp.getWriter().print("alert('" + alarmMessage + "');");
				resp.getWriter().print("</script>");
				return;
			}

			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);
			resp.setContentType("image/jpeg;charset=UTF-8");
			float ratio = -1;
			int width = -1;
			int height = -1;
			try {
				if (req.getParameter(IMAGE_ZOOM_RATIO) != null)
					ratio = Float.valueOf(
							(String) req.getParameter(IMAGE_ZOOM_RATIO))
							.floatValue();
				else {
					if (req.getParameter(IMAGE_ZOOM_WIDTH) != null
							&& req.getParameter(IMAGE_ZOOM_HEIGHT) != null) {
						width = Float.valueOf(
								(String) req.getParameter(IMAGE_ZOOM_WIDTH))
								.intValue();
						height = Float.valueOf(
								(String) req.getParameter(IMAGE_ZOOM_HEIGHT))
								.intValue();
					}
				}
			} catch (Exception e) {

			}
			OutputStream out = resp.getOutputStream();

			// 按比例缩放
			if (ratio != -1)
				ImageUtil.zoomImage(path + realFileName, out, ratio);
			// 按给定的高度，宽度缩放
			else if (width != -1) {
				ImageUtil.zoomImage(path + realFileName, out, width,
						height);
			} else {
				// 以byte流的方式打开文件
				InputStream inStream = new FileInputStream(image);

				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len;
				while ((len = inStream.read(b)) > 0)
					out.write(b, 0, len);
				inStream.close();
			}
			out.flush();
			out.close();
		} catch (IOException e) // 错误处理
		{
			e.printStackTrace();
		}
	}
}
