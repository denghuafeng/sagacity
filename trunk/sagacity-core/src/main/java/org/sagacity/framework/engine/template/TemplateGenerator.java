/**
 * 
 */
package org.sagacity.framework.engine.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.sagacity.framework.utils.IOUtil;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TemplateGenerator.java,Revision:v1.0,Date:2008-11-24 下午11:07:07 $
 */
public class TemplateGenerator {
	private static Configuration cfg = null;

	/**
	 * 将模板和数据结合产生到目的文件中
	 * 
	 * @param keys
	 * @param templateData
	 * @param templatePath
	 * @param templateFile
	 * @param distFile
	 */
	public static void create(String[] keys, Object[] templateData,
			String templatePath, String templateFile, String distFile) {
		if (keys == null || templateData == null)
			return;
		try {
			if (cfg == null)
				cfg = new Configuration();
			FileTemplateLoader templateLoader = new FileTemplateLoader(
					new File(templatePath));
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate(templateFile);
			Map root = new HashMap();
			for (int i = 0; i < keys.length; i++) {
				root.put(keys[i], templateData[i]);
			}

			FileOutputStream fout = new FileOutputStream(distFile);
			Writer writer = new OutputStreamWriter(fout);
			try {
				template.process(root, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			writer.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将模板和数据结合产生到目的文件中
	 * 
	 * @param keys
	 * @param templateData
	 * @param templatePath
	 * @param templateFile
	 * @param distFile
	 */
	public static void create(String[] keys, Object[] templateData,
			File templateFile, String distFile) {
		if (keys == null || templateData == null)
			return;
		try {
			if (cfg == null)
				cfg = new Configuration();
			FileTemplateLoader templateLoader = new FileTemplateLoader(
					new File(templateFile.getParent()));
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate(templateFile.getName());
			Map root = new HashMap();
			for (int i = 0; i < keys.length; i++) {
				root.put(keys[i], templateData[i]);
			}

			FileOutputStream fout = new FileOutputStream(distFile);
			Writer writer = new OutputStreamWriter(fout);
			try {
				template.process(root, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			writer.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将模板和数据结合产生到目的文件中
	 * 
	 * @param keys
	 * @param templateData
	 * @param templatePath
	 * @param templateFile
	 * @param distFile
	 */
	public static void create(String[] keys, Object[] templateData,
			String templatePath, String templateFile, OutputStream out) {
		if (keys == null || templateData == null)
			return;
		try {
			if (cfg == null)
				cfg = new Configuration();
			FileTemplateLoader templateLoader = new FileTemplateLoader(
					new File(templatePath));
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate(templateFile);
			Map root = new HashMap();
			for (int i = 0; i < keys.length; i++) {
				root.put(keys[i], templateData[i]);
			}
			Writer writer = new OutputStreamWriter(out);
			try {
				template.process(root, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将模板和数据结合产生到目的文件中
	 * 
	 * @param keys
	 * @param templateData
	 * @param templatePath
	 * @param templateFile
	 * @param distFile
	 */
	public static void create(String[] keys, Object[] templateData,
			InputStream is, String distFile) {
		if (keys == null || templateData == null)
			return;
		try {
			if (cfg == null)
				cfg = new Configuration();
			StringTemplateLoader templateLoader = new StringTemplateLoader();
			templateLoader.putTemplate("template", IOUtil
					.inputStream2String(is));
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate("template");
			Map root = new HashMap();
			for (int i = 0; i < keys.length; i++) {
				root.put(keys[i], templateData[i]);
			}

			FileOutputStream fout = new FileOutputStream(distFile);
			Writer writer = new OutputStreamWriter(fout);
			try {
				template.process(root, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			writer.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将模板和数据结合产生到目的文件中
	 * 
	 * @param keys
	 * @param templateData
	 * @param templatePath
	 * @param templateFile
	 * @param distFile
	 */
	public static void create(String[] keys, Object[] templateData,
			InputStream is, OutputStream out) {
		if (keys == null || templateData == null)
			return;
		try {
			if (cfg == null)
				cfg = new Configuration();
			StringTemplateLoader templateLoader = new StringTemplateLoader();
			templateLoader.putTemplate("template", IOUtil
					.inputStream2String(is));
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate("template");
			Map root = new HashMap();
			for (int i = 0; i < keys.length; i++) {
				root.put(keys[i], templateData[i]);
			}
			Writer writer = new OutputStreamWriter(out);
			try {
				template.process(root, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
/*
		SmsVO smsVO = new SmsVO();
		smsVO.setContent("测试");
		smsVO.setCreateDate("20080827");
		List MobileVOs = new ArrayList();
		MobileVO mobileVO = new MobileVO();
		mobileVO.setMobileNo("1397777777");
		MobileVOs.add(mobileVO);
		MobileVO mobileVO1 = new MobileVO();
		mobileVO1.setMobileNo("1397888888");
		MobileVOs.add(mobileVO1);
		smsVO.setMobileNos(MobileVOs);
		TemplateGenerator.create(new String[] { "smsVO" },
				new Object[] { smsVO }, "D:/workspace/nantian/abchina/docs",
				"smsTemplate.ftl",
				"D:/workspace/nantian/abchina/docs/smsList.txt");*/
	}
}
