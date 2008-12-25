/**
 * 
 */
package org.sagacity.exoteric.service.dev;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sagacity.exoteric.model.MenuModel;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          为开发者提供针对开发阶段页面菜单的xml加载实现方式
 *          </p>$
 * @author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:WebFrameDeveloper.java,Revision:v1.0,Date:Dec 19, 2008 10:38:46
 *          AM $
 */
public class WebFrameDeveloper {
	/**
	 * 定义全局日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	private static WebFrameDeveloper me;

	public static WebFrameDeveloper getInstance() {
		if (me == null)
			me = new WebFrameDeveloper();
		return me;

	}

	/**
	 * web页面菜单定义xml
	 */
	private static final String WEB_PAGE_CONFIG = "webFrame.xml";

	/**
	 * 系统高层子系统存放于hashMap中的key值
	 */
	private static final String TOP_MODULE_HASH_KEY = "topModuleHashKey";

	/**
	 * 配置文件最后修改时间
	 */
	private static long configXmlLastUpdate = -1;

	/**
	 * 存放被解析的菜单信息
	 */
	private static HashMap MENU_MAP = new HashMap();

	/**
	 * 获取高层模块信息，一般作为子系统
	 * 
	 * @return
	 */
	public List getTopModules() {
		loadParseXml();
		return (List) this.MENU_MAP.get(TOP_MODULE_HASH_KEY);
	}

	/**
	 * 获取模块对应的功能菜单
	 * 
	 * @return
	 */
	public List getModuleMenu(String moduleId) {
		loadParseXml();
		return (List) this.MENU_MAP.get(moduleId);
	}

	/**
	 * 加载解析菜单xml文件
	 */
	public void loadParseXml() {
		String file = this.getClass().getResource("/").getPath()
				+ WEB_PAGE_CONFIG;
		logger.info("load parse menu xml!");
		File configFile = new File(file);
		if (!configFile.exists())
			logger.info("please create or edit webFrame.xml file!");

		long lastUpdate = configFile.lastModified();
		// 如果文件被修改，则重新加载配置文件,用于开发阶段动态加载修改后的配置文件
		if (lastUpdate > configXmlLastUpdate) {

			logger.info("begin reload webFrame.xml file!");
			MENU_MAP = new HashMap();
			try {
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(configFile);
				Element root = doc.getRootElement();
				List topModules = new ArrayList();
				List subSysElts = root.elements("subsys");
				List moduleElts;
				List itemElts;
				if (subSysElts == null || subSysElts.isEmpty())
					return;
				Element subSys;

				Element moduleElt;
				Element itemElt;
				for (int i = 0; i < subSysElts.size(); i++) {
					subSys = (Element) subSysElts.get(i);
					MenuModel subSysModel = new MenuModel();
					subSysModel.setMenuId(subSys.attributeValue("id"));
					subSysModel.setMenuName(subSys.attributeValue("name"));
					subSysModel.setIcon(subSys.attributeValue("image"));
					subSysModel.setStyle(subSys.attributeValue("style"));
					subSysModel.setLeafFlag(0);
					topModules.add(subSysModel);
					moduleElts = subSys.elements("module");
					List moduleMenus = new ArrayList();
					for (int j = 0; j < moduleElts.size(); j++) {
						moduleElt = (Element) moduleElts.get(j);
						MenuModel moduleModel = new MenuModel();
						moduleModel.setMenuId(moduleElt.attributeValue("id"));
						moduleModel.setMenuName(moduleElt
								.attributeValue("name"));
						moduleModel.setPreNode(subSysModel.getMenuId());
						moduleModel.setLeafFlag(0);

						moduleModel.setIcon(moduleElt.attributeValue("image"));
						moduleModel.setStyle("");
						moduleMenus.add(moduleModel);
						itemElts = moduleElt.elements("item");
						for (int k = 0; k < itemElts.size(); k++) {
							itemElt = (Element) itemElts.get(k);
							MenuModel menuModel = new MenuModel();
							menuModel.setMenuId(itemElt.attributeValue("id"));
							menuModel.setMenuName(itemElt
									.attributeValue("name"));
							menuModel.setPreNode(moduleModel.getMenuId());
							menuModel.setUrl(itemElt.attributeValue("url"));
							menuModel.setIcon(itemElt.attributeValue("image"));
							menuModel.setStyle("");

							menuModel.setLeafFlag(1);
							moduleMenus.add(menuModel);
						}
					}
					this.MENU_MAP.put(subSysModel.getMenuId(), moduleMenus);
				}
				this.MENU_MAP.put(this.TOP_MODULE_HASH_KEY, topModules);
			} catch (Exception e) {
				e.printStackTrace();
			}
			configXmlLastUpdate = lastUpdate;
		}

	}

	public static void main(String[] args) {
		List moduleMenus = WebFrameDeveloper.getInstance().getModuleMenu("-1");
		MenuModel model;
		for (int i = 0; i < moduleMenus.size(); i++) {
			model=(MenuModel) moduleMenus.get(i);
			System.err.println("#############"+model.getMenuId()+""
					+ model.getLeafFlag()+" "
					+ model.getPreNode());
		}
	}
}
