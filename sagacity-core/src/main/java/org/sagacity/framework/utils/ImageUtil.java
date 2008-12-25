/**
 * 
 */
package org.sagacity.framework.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 *@project abchina 
 *@description:$<p>图片处理工具类，在linux非xwindow环境下需要在应用启动时
 *增加:-Djava.awt.headless=true,同时将jvm heap 最小值调整到256，最大值512(或更大)</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ImageUtil.java,Revision:v1.0,Date:Sep 18, 2008 8:23:51 PM $
 */
public class ImageUtil {

	/**
	 * 对图片进行缩小和放大处理
	 * @param srcImage
	 * @param distImage
	 * @param width
	 * @param height
	 */
	public static void zoomImage(Object srcImage, Object distImage, int width,
			int height) {
		zoomImage(srcImage, distImage, new Integer(width), new Integer(height));
	}

	/**
	 * 对图片进行缩小和放大处理
	 * @param srcImage
	 * @param distImage
	 * @param width
	 * @param height
	 */
	public static void zoomImage(Object srcImage, Object distImage,
			Integer width, Integer height) {
		try {
			if (srcImage == null)
				return;
			if (width == null && height == null)
				return;
			Image img = convertImage(srcImage);
			if (img == null) {
				System.err.println("参数数据类型不正�?,请正确输�?!");
				return;
			}
			int scaleWidth = 0;
			int scaleHeight = 0;
			int oldWidth = img.getWidth(null);
			int oldHeight = img.getHeight(null);
			if (width != null)
				scaleWidth = width.intValue();
			if (height != null)
				scaleHeight = height.intValue();
			if (scaleWidth <= 0 && scaleHeight <= 0) {
				System.err.println("縮放大小不能全小于等于零,请正确输�?!");
				return;
			}
			OutputStream out = convertDistImage(srcImage, distImage);
			if (out == null) {
				System.err.println("对象文件参数数据类型不正�?,请正确输�?!");
				return;
			}
			if (scaleWidth > 0 && scaleHeight > 0)
				process(img, out, scaleWidth, scaleHeight);
			else if (scaleWidth > 0)
				process(img, out, scaleWidth, oldHeight * scaleWidth / oldWidth);
			else
				process(img, out, oldWidth * scaleHeight / oldHeight, oldHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对图片进行缩小和放大处理
	 * @param srcImage
	 * @param distImage
	 * @param ratio
	 */
	public static void zoomImage(Object srcImage, Object distImage, float ratio) {
		try {
			if (srcImage == null || ratio < 0) {
				System.err.println("文件不能为空縮放比例不能为零,请正确输�?!");
				return;
			}
			Image img = convertImage(srcImage);
			if (img == null) {
				System.err.println("参数数据类型不正�?,请正确输�?!");
				return;
			}
			
			int width = new Float(img.getWidth(null) * ratio).intValue();
			int height = new Float(img.getHeight(null) * ratio).intValue();
			OutputStream out = convertDistImage(srcImage, distImage);
			if (out == null) {
				System.err.println("对象文件参数数据类型不正�?,请正确输�?!");
				return;
			}
			process(img, out, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对图片进行缩小和放大处理
	 * @param srcImage
	 * @param distImage
	 * @param width
	 * @param height
	 */
	private static void process(Image srcImage, OutputStream distImage,
			int width, int height) {
		try {
			BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics()
					.drawImage(srcImage, 0, 0, width, height, null);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(distImage);
			encoder.encode(buffImg);
			buffImg.getGraphics().dispose();
			distImage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将传入的目标图片数据转换成Image类型
	 * 
	 * @param srcImage
	 * @return
	 * @throws IOException
	 */
	private static Image convertImage(Object srcImage) throws Exception {
		Image img = null;
		ImageIcon imgIcon = null;
		if (srcImage instanceof String) {
			imgIcon = new ImageIcon((String) srcImage);
			img = imgIcon.getImage();
		} else if (srcImage instanceof File) {
			imgIcon = new ImageIcon(((File) srcImage).getPath());
			img = imgIcon.getImage();
		} else if (srcImage instanceof InputStream) {
			imgIcon = new ImageIcon(NumberUtil.getBytes((InputStream) srcImage));
			img = imgIcon.getImage();
		}
		return img;
	}

	/**
	 * 将目标文件对象转成outputStream
	 * 
	 * @param srcImage
	 * @param distImage
	 * @return
	 * @throws IOException
	 */
	private static OutputStream convertDistImage(Object srcImage,
			Object distImage) throws IOException {
		OutputStream out = null;
		if (distImage == null) {
			if (srcImage instanceof String)
				out = new FileOutputStream((String) srcImage);
			else if (srcImage instanceof File)
				out = new FileOutputStream((File) srcImage);
		} else {
			if (distImage instanceof OutputStream) {
				out = (OutputStream) distImage;
			} else {
				if (distImage instanceof String)
					out = new FileOutputStream((String) distImage);
				else if (distImage instanceof File)
					out = new FileOutputStream((File) distImage);
			}
		}
		return out;
	}

	/**
	 * 
	 * @param srcImage
	 * @param distImage
	 * @param width
	 * @param height
	 */
	private static void zoomImage(Image srcImage, OutputStream distImage,
			int width, int height) {
		try {
			System.setProperty("java.awt.headless", "true");
			BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics()
					.drawImage(srcImage, 0, 0, width, height, null);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(distImage);
			encoder.encode(buffImg);
			buffImg.getGraphics().dispose();
			distImage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)throws Exception {
		ImageUtil
				.zoomImage("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG", "D:/test.jpg", 150, 155);
		ImageUtil.zoomImage("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG", "D:/test1.jpg",
				new Integer(150), null);
		ImageUtil.zoomImage("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG", "D:/test2.jpg", null,
				new Integer(155));
		ImageUtil.zoomImage("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG", "D:/test3.jpg", 0.2f);
		ImageUtil.zoomImage(new FileInputStream(new File("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG")), "D:/test4.jpg", 0.2f);
		ImageUtil.zoomImage(new FileInputStream(new File("L:/慈溪网点概貌�?/范市/三北分理处营业场.JPG")), "D:/test5.jpg", 150,155);
	}
}
