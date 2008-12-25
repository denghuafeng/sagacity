/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:BeanUtil.java,Revision:v1.0,Date:2008-11-10 下午10:27:57 $
 */
public class BeanUtil {
	public static void loadJars(String filePath)throws Exception {
		File file=new File(filePath);
		URL url=file.toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] { url });
		//classLoader.
	}
	

	public void addPath(String paths) {
		if (paths == null || paths.length() <= 0) {
			return;
		}

		String separator = System.getProperty("path.separator");
		String[] pathToAdds = paths.split(separator);

		for (int i = 0; i < pathToAdds.length; i++) {
			if (pathToAdds[i] != null && pathToAdds[i].length() > 0) {
				try {

					File pathToAdd = new File(pathToAdds[i]).getCanonicalFile();
					/*addURL(pathToAdd.toURL());
					System.out.println(pathToAdd.toURL());
					Class c = this.loadClass("test.testLoad"); // class名（飽含包名)

					Field[] f = c.getDeclaredFields();
					for (int k = 0; k < f.length; k++) {
						System.out.println(f[k]);
					}

					Method mainMethod = c.getMethod("testing",
							new Class[] { String.class });
					String params = "小橙�?";
					String ret = (String) mainMethod.invoke(null, params);// 執行方法
					System.out.println("test.testLoad.testing() return:" + ret);*/

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

}
