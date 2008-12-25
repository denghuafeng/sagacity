/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:StringConvert.java,Revision:v1.0,Date:Aug 2, 2008 11:30:55 AM $
 */
public class StringConvert implements IConvert {
	private String template;
	private String regx = "#placeHolder#";

	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	private List params;

	private String[] replaceSource;
	private String[] replaceTarget;

	private String result;
	private boolean isTrim = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object)
	 */
	public Object convert(Object key,List rowData,ColumnModel colModel) {
		if (params != null && !params.isEmpty()) {
			String replaceEnum = null;
			if (params.size() == 3) {
				if (params.get(0).toString().equalsIgnoreCase("trim"))
					isTrim = true;
				replaceEnum = params.get(1).toString();
				template = params.get(2).toString();
			}

			if (params.size() == 2) {
				if (params.get(0).toString().equalsIgnoreCase("trim")) {
					isTrim = true;
					if (params.get(1).toString().indexOf(regx) != -1)
						template = params.get(1).toString();
					else
						replaceEnum = params.get(1).toString();
				} else {
					replaceEnum = params.get(0).toString();
					template = params.get(1).toString();
				}
			}

			if (params.size() == 1) {
				if (params.get(0).toString().equalsIgnoreCase("trim"))
					isTrim = true;
				else {
					if (params.get(0).toString().indexOf(regx) != -1)
						template = params.get(0).toString();
					else
						replaceEnum = params.get(0).toString();
				}
			}

			if (replaceEnum != null && replaceEnum.indexOf("$") != -1) {
				replaceSource = (replaceEnum.split("\\$")[0]).split(",");
				replaceTarget = (replaceEnum.split("\\$")[1]).split(",");
			}

		}
		if (key == null)
			return key;
		result = key.toString();
		try {
			if (replaceSource != null && replaceSource.length > 0) {
				for (int i = 0; i < replaceSource.length; i++) {
					result = StringUtil.replaceAllStr(result, replaceSource[i],
							(i + 1 > replaceTarget.length ? ""
									: replaceTarget[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error("字符替换处理错误,请正确配置StringConvert的param!example=<param><![CDATA[〔,〕, $(,),]]></param>");
		}
		if (template != null)
			return StringUtil.replaceStr(template, regx, result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		// TODO Auto-generated method stub
		this.params = params;
	}

	public static void main(String[] args) {
		String tmp = "〔,〕, $(,),";
		String[] tmp1 = tmp.split("\\$")[1].split(",");
		System.err.println("(,),".split(",").length);
	}
}
