/**
 * 
 */
package org.sagacity.framework.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          web页面验证码图片产生器
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ValidateCode.java,Revision:v1.0,Date:Apr 29, 2008 3:05:04 PM $
 */
public class ValidateCodeGen extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 设置图片的长宽
		int width = 62, height = 22;

		// ////// 创建内存图像
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.createGraphics();

		// 设定图像背景色(因为是做背景，所以偏淡)
		g.setColor(getRandColor(180, 250));
		g.fillRect(0, 0, width, height);
		// 设置字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 17));

		// /////设置默认生成4个验证码
		int length = 4;
		java.util.Random rand = new Random(); // 设置随机种子

		/*
		 * if (request.getParameter("length") != null) { try { length =
		 * Integer.parseInt(request.getParameter("length")); }catch
		 * (NumberFormatException e) {} }
		 */
		// 设置备选验证码:包括"a-z"和数字"0-9"
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		int size = base.length();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int start = rand.nextInt(size);
			String tmpStr = base.substring(start, start + 1);

			str.append(tmpStr);
			// 生成随机颜色(因为是做前景，所以偏深)
			g.setColor(getRandColor(10, 150));

			// 将此字画到图片上
			// g.drawString(str.toString(), 4, 17);
			g.drawString(tmpStr, 13 * i + 6 + rand.nextInt(5), 14 + rand
					.nextInt(6));
		}

		// 将认证码存入session
		request.getSession().setAttribute("valiCode", str.toString());

		// 图象生效
		g.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	// 给定范围获得一个随机颜色
	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
