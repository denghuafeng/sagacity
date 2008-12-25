/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:IOUtil.java,Revision:v1.0,Date:2008-12-14 下午07:53:54 $
 */
public class IOUtil {
	/**
	 * 转换String为InputStream
	 * @param str
	 * @return
	 */
	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	/**
	 * 转换InputStream为String
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * outputStream 转换为InputStream
	 * @param out
	 * @return
	 */
	public static InputStream convert2InputStream(ByteArrayOutputStream out) {
		return new ByteArrayInputStream(out.toByteArray());
	}
}
