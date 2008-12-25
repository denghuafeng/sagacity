package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 *@project abchina 
 *@description:$<p>对URL编码</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:URLEncode.java,Revision:v1.0,Date:Aug 2, 2008 11:29:30 PM $
 */
public class URLEncode implements MarcoFacade{
	
	public String process(PageContext pageContext, XTableModel tableModel,
			List rowDataList, int rowIndex, Object obj, String template) {
		
		HashMap propertiesMap = tableModel.getPropertiesMap();
		Iterator propertyIter = propertiesMap.keySet().iterator();
		String paramName;
		int paramIndex = -1;
		Object data;
		while (propertyIter.hasNext()) {
			paramName = (String) propertyIter.next();
			paramIndex = ((Integer) propertiesMap.get(paramName)).intValue();
			if (obj.equals("#[" + paramName + "]"))
				break;
		}
		if (paramIndex == -1)
			return "";
		else {
			data = rowDataList.get(paramIndex);
			if (data == null)
				return "";
			//对url编码
			String url=(String)data;
			try {
				url=URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return url;
			}
			
			return url;
		}
	}
}
