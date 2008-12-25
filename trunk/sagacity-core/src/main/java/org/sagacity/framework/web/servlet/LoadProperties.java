/**
ZX工具类包:系统参数加载,设置properties文件，在web.xml中添加
 <servlet>
     <servlet-name>ServiceNaming</servlet-name>
     <servlet-class>com.abchina.framework.web.servlet.LoadProperties</servlet-class>
     <init-param>
       <param-name>sagacity.global.service.naming</param-name>
       <param-value>/WEB-INF/config/config.xml</param-value>
     </init-param>
     <load-on-startup>1</load-on-startup>
 </servlet>
 *
 *对应/WEB-INF/config/config.xml配置文件格式如下
 * <?xml version="1.0" encoding="UTF-8"?>
<project name="eworker">
 <desc></desc>
 <properties>
   <property>
     <ispropertyfile>true</ispropertyfile>
     <addcontextpath>true</addcontextpath>
     <name>log4j</name>
     <value><![CDATA[/WEB-INF/log4j.properties]]></value>
   </property>
   <property>
     <ispropertyfile>false</ispropertyfile>
     <addcontextpath>true</addcontextpath>
     <name>java.security.auth.login.config</name>
     <value><![CDATA[/WEB-INF/login.config]]></value>
   </property>
 </properties>
</project>

当web服务器启动时系统根据给定的config.xml文件加载相关系统参数
 */
package org.sagacity.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sagacity.framework.Constants;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;

/**
 * <p>
 * Title: zhongxu Foundation Class
 * </p>
 * <p>
 * Description: chenrenfei的Java类工具包,版权所有不经允许不得抄袭
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: zx
 * </p>
 * 
 * @author chenrenfei
 * @version 1.0.0
 */
public class LoadProperties extends HttpServlet {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	/**
	 *系统参数
	 */
	// private static Properties propElements = new Properties();
	/**
	 * 对应文件路径的参数名称必须命名为:sagacity.global.service.naming
	 */
	private final String SERVLET_LOAD_PRARAM = "sagacity.global.service.naming";

	/**
	 * 文件路径前缀
	 */
	private String prefix;

	/**
	 * 
	 * @param config
	 *            ServletConfig
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			prefix = config.getServletContext().getRealPath("/");
			if (prefix == null)
				prefix = "";
			// 获取配置文件的文件名称
			String configfile = config
					.getInitParameter(this.SERVLET_LOAD_PRARAM);
			if (StringUtil.isNullOrBlank(configfile))
				return;
			configfile = configfile.trim();
			logger.info("configFile=" + configfile);
			parseCfgFile(configfile);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}

	public void destroy() {
		super.destroy();
	}

	/**
	 * 解析配置文件,获取相应的需要加载的property属性和文件
	 * 
	 * @param file
	 */
	private void parseCfgFile(String configfile) {
		try {
			/**
			 * xml 文件
			 */
			if (configfile.toLowerCase().indexOf(".xml") != -1) {
				SAXReader saxReader = new SAXReader();
				Document doc = null;
				// 增加对classpath目录下文件的处理 2008.7.2
				if (StringUtil.IndexOfIgnoreCase(configfile, "classpath:") == 0)
					doc = saxReader.read(this.getClass().getResourceAsStream(
							configfile.trim().substring(10).trim()));
				else {
					if (configfile.indexOf("/") == -1) {
						configfile = "/" + configfile;
					}
					doc = saxReader.read(new File(prefix + configfile));
				}

				Element properties = doc.getRootElement().element("properties");
				Iterator iter = properties.elementIterator();
				Element element;
				String ispropertyfile;
				String addcxtPath;
				String propertyName;
				String propertyValue;
				while (iter.hasNext()) {
					element = (Element) iter.next();
					ispropertyfile = element.elementTextTrim("ispropertyfile");
					addcxtPath = element.elementTextTrim("addcontextpath");
					propertyName = element.elementTextTrim("name");
					propertyValue = element.elementTextTrim("value");

					// 是否是property 文件
					if ("true".equalsIgnoreCase(ispropertyfile)) {
						Properties propElements = new Properties();
						// 是否要加上相对路径
						if ("true".equalsIgnoreCase(addcxtPath)) {
							if (propertyValue.indexOf("/") == -1) {
								propertyValue = "/" + propertyValue;
							}
							propertyValue = prefix + propertyValue;
						}

						// 是否是classpath下的文件
						if (StringUtil.IndexOfIgnoreCase(propertyValue,
								"classpath:") == 0) {
							propertyValue = propertyValue.trim().substring(10)
									.trim();
							if (propertyValue.indexOf("/") == -1) {
								propertyValue = "/" + propertyValue;
							}
							propElements.load(this.getClass()
									.getResourceAsStream(propertyValue));
						} else
							propElements.load(new FileInputStream(new File(
									propertyValue)));
						Constants.getPropElements().putAll(propElements);
					} else
						Constants.getPropElements().put(propertyName,
								propertyValue);
				}
			}//properties 文件,组成格式为classpath:system.properties;D:/xxx.properties
			else {
				String[] configFiles = configfile.split(";");
				String propertyFile = null;
				for (int i = 0; i < configFiles.length; i++) {
					if (configFiles[i].toLowerCase().indexOf(".properties") != -1) {
						Properties propElements = new Properties();
						// 增加对classpath目录下文件的处理 2008.7.2
						if (StringUtil.IndexOfIgnoreCase(configFiles[i].trim(),
								"classpath:") == 0) {
						propertyFile = configFiles[i].trim().substring(10)
									.trim();
							if (propertyFile.indexOf("/") == -1) {
								propertyFile = "/" + propertyFile;
							}
							propElements.load(this.getClass()
									.getResourceAsStream(propertyFile));
						} else {
							if (configFiles[i].indexOf("/") == -1) {
								propertyFile = "/" + configFiles[i];
							}
							propElements.load(new FileInputStream(new File(
									prefix + propertyFile)));
						}
						Constants.getPropElements().putAll(propElements);
					}
				}
			}
		} catch (DocumentException de) {
			de.printStackTrace();
			logger.error(de.getMessage());

		} catch (IOException ie) {
			ie.printStackTrace();
			logger.error(ie.getMessage());
		}
	}

	public static String getProperty(String key) {
		Object obj = Constants.getKeyValue(key);
		if (obj != null)
			return obj.toString();
		else
			return null;
	}

	public static void main(String[] args) {
		System.err.println("classpath:config.xml".trim().substring(10).trim());
		// LoadProperties loadproperties = new LoadProperties();
	}
}
