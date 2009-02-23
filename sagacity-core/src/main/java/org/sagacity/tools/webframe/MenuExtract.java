/**
 * 
 */
package org.sagacity.tools.webframe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 *@project sagacity-core
 *@description:$<p>抽取开发过程中xml菜单文件中的数据保存到excel或数据库</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:MenuExtract.java,Revision:v1.0,Date:2009-2-19 上午10:55:24 $
 */
public class MenuExtract {
	/**
	 * 日志参数定义文件
	 */
	private final static String logFile = "/org/sagacity/tools/webframe/webframe.properties";

	/**
	 * 导出模块数据的excel模板
	 */
	private final static String MODULE_EXCEL_TEMPLATE = "/org/sagacity/tools/webframe/moduleTemplate.xls";

	/**
	 * 导出菜单数据的excel模板
	 */
	private final static String MENU_EXCEL_TEMPLATE = "/org/sagacity/tools/webframe/menuTemplate.xls";

	/**
	 * 加载环境
	 */
	private void loadEnv() {
		try {
			Properties props = new Properties();
			InputStream stream = Thread.currentThread().getClass()
					.getResourceAsStream(logFile);
			props.load(stream);
			PropertyConfigurator.configure(props);
			System.out.println("log4j properties is loaded");
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private List parseXml(String webFrameXmlFile)
	{
		return null;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//读取xml文件以及导出excel文件路径
		String webFrameXmlFile = null;
		String targetExcelPath = null;
		if (args != null && args.length > 0) {
			webFrameXmlFile = args[0];
			if (args.length > 1)
				targetExcelPath = args[1];
		} else {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("请输入菜单配置xml文件路径,并用回车符结束!");
			webFrameXmlFile = stdin.readLine();
			System.out.println("请输入excel数据输出路径,并用回车符结束!");
			targetExcelPath = stdin.readLine();
		}
		
	}
}
