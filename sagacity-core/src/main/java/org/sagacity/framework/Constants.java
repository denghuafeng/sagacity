/**
 * 
 */
package org.sagacity.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 *@project sagacity(睿智J2EE 框架)
 *@description:$<p>sagacity 项目</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Constants.java,Revision:v1.0,Date:May 21, 2008 10:09:59 AM $
 */
public class Constants implements Serializable {
	
	/**
     * The name of this package.
     */
    public static final String Package = "org.apache.struts.taglib.html";
    
    /**
     * 组件资源url对应的html属性定义,pageContext.getAttribute(#this)获取相应的css,js资源map对象
     */
    public static final String SAGACITY_COMPONENT_RESOURCES="sagacity.component.resource.url.html";


    /**
     * The attribute key for the bean our form is related to.
     */
    public static final String BEAN_KEY = Package + ".BEAN";
    
    /**
     * 上下文路径宏定义
     */
    public static final String CONTEXT_PATH_MACO="${contextPath}";
    
    public static final String COMPONENT_RESOURCE_BASE="base";
    
	
	/**
	 * 睿智项目默认参数配置文件
	 */
	private final static String SAGACITY_CONFIG = "/sagacity.properties";
	
	
	/**
	 * web 框架类型
	 */
	public final static String MVC_TYPE="sagacity.mvc.type";
	
	/**
	 * 组件资源版本
	 */
	public final static String COMPONENT_RES_VERSION="sagacity.component.resources.version";
	
	/**
	 * 是否自动解压
	 */
	public final static String COMPONENT_AUTO_UNZIP="sagacity.component.resources.auto.unzip";
	
	/**
	 * sql伪指令开始标记
	 */
	public final static String SQL_PSEUDO_START_MARK="#[";
	
	/**
	 * sql伪指令收尾标记
	 */
	public final static String SQL_PSEUDO_END_MARK="]";
	
	/**
	 * 
	 */
	private static Properties propElements=new Properties(); 
	
	/**
	 * 系统参数是否加载标志
	 */
	private static boolean HAS_LOAD_FLAG=false;
	
	
	
	/**
	 * 获取常量值
	 * @param key
	 * @return
	 */
	public static String getKeyValue(String key){	
		return getPropElements().getProperty(key);
	}
	
	public static Properties getPropElements()
	{
		//先加载默认的框架常量参数,便于应用自己加载参数时可以修改框架默认的参数值
		if(!HAS_LOAD_FLAG){
			InputStream fis=Constants.class.getResourceAsStream(Constants.SAGACITY_CONFIG);
			Properties sagacityProps=new Properties();
			try {
				sagacityProps.load(fis);
				propElements.putAll(sagacityProps);
				fis.close();
				HAS_LOAD_FLAG=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return propElements;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		Properties extProps=new Properties();
		extProps.put("sagacity.mvc.type", "struts1.x");
		Constants.getPropElements().putAll(extProps);
		String str=Constants.getKeyValue(Constants.MVC_TYPE);
		System.out.println("str="+str);
	}

}
