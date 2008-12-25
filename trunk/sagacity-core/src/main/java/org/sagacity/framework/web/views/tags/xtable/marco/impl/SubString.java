/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.marco.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.tags.xtable.XTableConstants;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 *@project abchina 
 *@description:$<p>字符串处理宏</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:SubString.java,Revision:v1.0,Date:Jul 25, 2008 2:20:25 PM $
 */
public class SubString implements MarcoFacade {

	/* (non-Javadoc)
	 * template like:L30,#placeHolder#...
	 * useing:@substr(#{property}$L30,#{placeHolder}#...)
	 * @see com.abchina.framework.web.views.tags.xtable.marco.MarcoFacade#process(javax.servlet.jsp.PageContext, com.abchina.framework.web.views.tags.xtable.model.XTableModel, java.util.List, int, java.lang.Object, java.lang.String)
	 */
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
			
			String[] subTemp=template.split(",");
			//取得L30字符串
			String olen=subTemp[0];
			//位置
			String opsition=olen.substring(0,1);
			//长度
			String len=olen.substring(1,olen.length());
			//数据
			String handleData=(String)data;
			
			//截取字符串长度
			int length=0;
			try{
				length=new Integer(len).intValue();
			}catch(Exception e){
				return  handleData;
			}
			
			if(handleData.length()>length&&length>0){
				//判断左边截取还是右边截取
				if(opsition.equals("L")){
					handleData=handleData.substring(0, length);
					handleData=StringUtil.replaceStr(template, "#["
							+ XTableConstants.PLACE_HOLDER + "]", handleData);
					handleData=StringUtil.replaceStr(handleData,"L"+length+",", "");
				}
				if(opsition.equals("R")){
					handleData=handleData.substring(handleData.length()-length, handleData.length());
					handleData=StringUtil.replaceStr(template, "#["
							+ XTableConstants.PLACE_HOLDER + "]", handleData);
					handleData=StringUtil.replaceStr(handleData,"R"+length+",", "");
				}
			}
			
			return handleData;
			
			//return DateUtil.formatDate(1000, ###.0);
		}
	}

}
