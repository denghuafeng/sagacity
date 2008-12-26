/**
 * 
 */
package org.sagacity.framework.web.views.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.resources.model.ResourceFile;
import org.sagacity.framework.web.views.resources.model.ResourceLink;

/**
 *@project sagacity-core
 *@description:$<p>解析resources相关的配置文件，提供一些公共处理方法</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourceWrapper.java,Revision:v1.0,Date:2008-11-11 下午04:09:11 $
 */
public class ResourceWrapper {
	/**
	 * 组件资源，通过解析sagacity-resources.xml获取组件的css,js
	 */
	private static HashMap componentResources = null;

	private static final String COMPONENT_LINK = "/components/sagacity-component-link.xml";

	/**
	 * 解析组件版本配置文件
	 * 
	 * @param componentFile
	 * @return
	 */
	public static List getComponentFileConfig(String componentFile) {
		List configSet = new ArrayList();
		try {
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(ResourceWrapper.class
					.getResourceAsStream(componentFile));

			Element root = doc.getRootElement();
			Iterator comIter = root.elementIterator("component");
			Element component = null;

			while (comIter.hasNext()) {
				component = (Element) comIter.next();
				ResourceFile resModel = new ResourceFile();
				resModel.setId(component.attributeValue("id"));
				resModel.setName(component.attributeValue("name"));
				resModel.setDescription(component
						.elementTextTrim("description"));
				resModel.setUrl(component.elementTextTrim("url"));
				resModel.setResource(component.elementTextTrim("resource"));
				resModel.setUnzipPath(component.elementTextTrim("unzipPath"));
				configSet.add(resModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configSet;
	}

	/**
	 * 
	 * @param componentId
	 * @return
	 */
	public static List getComponentLinks(final String componentId) {
		if (componentResources == null) {
			componentResources = new HashMap();
			try {
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(ResourceWrapper.class
						.getResourceAsStream(COMPONENT_LINK));

				Element root = doc.getRootElement();
				Iterator comIter = root.elementIterator("component");
				Element component = null;
				String id;
				Iterator linkIter;
				Element linkElement;
				while (comIter.hasNext()) {
					component = (Element) comIter.next();
					id = component.attributeValue("id");
					List links = new ArrayList();
					linkIter = component.element("urls").elementIterator();
					while (linkIter.hasNext()) {
						linkElement = (Element) linkIter.next();
						if (StringUtil.isNotNullAndBlank(linkElement.getTextTrim())) {
							ResourceLink resLink = new ResourceLink();
							resLink.setId(id);
							resLink.setLinkHtml(linkElement.getTextTrim());
							links.add(resLink);
						}
					}
					componentResources.put(id, links);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object result = componentResources.get(componentId);
		if (result == null)
			return null;
		return (List) result;
	}

	public static void main(String[] args) {
		List tmp = ResourceWrapper.getComponentLinks("base");
		for (int i = 0; i < tmp.size(); i++)
			System.err.println(((ResourceLink) tmp.get(i)).getLinkHtml());
	}
}
