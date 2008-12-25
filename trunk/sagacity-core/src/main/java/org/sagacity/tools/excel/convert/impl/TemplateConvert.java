/**
 * 
 */
package org.sagacity.tools.excel.convert.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *@project abchina 
 *@description:$<p>利用freemarker模板转换</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TemplateConvert.java,Revision:v1.0,Date:Aug 25, 2008 8:47:12 PM $
 */
public class TemplateConvert implements IConvert {
	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	
	private String tempateName="item";
	private String marcTemplate;
	private List params;
	private Configuration cfg=new Configuration();
	private StringTemplateLoader tmpLoader=new StringTemplateLoader();

	/* (non-Javadoc)
	 * @see com.abchina.tools.excel.convert.IConvert#convert(java.lang.Object, java.util.List)
	 */
	public Object convert(Object key, List rowData,ColumnModel colModel) {
		if(params==null || params.size()==0)
			return key;
		//like #{item[0]}-#{item[1]}-
		if(params.size()==2)
		{
			tempateName=(String)params.get(0);
			marcTemplate=(String)params.get(1);
		}
		else
			marcTemplate=(String)params.get(0);
		
		
		String result=null;
		try {
			tmpLoader.putTemplate(tempateName, marcTemplate);
			cfg.setTemplateLoader(tmpLoader);
			Template template = cfg.getTemplate(tempateName);
			Map root = new HashMap();
			root.put(tempateName, rowData);
			
			StringWriter writer = new StringWriter(); 
			try {
				template.process(root, writer);
				writer.flush();
				result=writer.getBuffer().toString();
			} catch (TemplateException e) {
				e.printStackTrace();
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("模板匹配错误!", e.fillInStackTrace());
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.abchina.tools.excel.convert.IConvert#setParams(java.util.List)
	 */
	public void setParams(List params) {
		// TODO Auto-generated method stub
		this.params=params;
	}
	
	public static void main(String[] args)
	{
		//String marcTemplate="${maro[0]}-${maro[1]}-${maro[2]?left_pad(3,'0')}";
		String marcTemplate="${maro[0]?string}0101";
		List rowData=new ArrayList();
		rowData.add("2006");
	
		Configuration cfg=new Configuration();
		try {
			StringTemplateLoader tmpLoader=new StringTemplateLoader();
			tmpLoader.putTemplate("marc", marcTemplate);
			cfg.setTemplateLoader(tmpLoader);
			
			Template template = cfg.getTemplate("marc");
			Map root = new HashMap();
			root.put("maro", rowData);
			
			StringWriter writer = new StringWriter(); 
			try {
				template.process(root, writer);
				writer.flush();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			System.err.println(writer.getBuffer().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
