/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

import java.io.Serializable;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExportTypeModel.java,Revision:v1.0,Date:May 25, 2008 6:40:07 PM $
 */
public class ExportModel implements Serializable {
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 导出处理器
	 */
	private String exportRender;
	
	/**
	 * 导出方法的url
	 */
	private String exportUrl;
	
	/**
	 * 导出文件扩展名
	 */
	private String extName;
	
	/**
	 * 导出文件
	 */
	private String exportFile;
	
	/**
	 * 模板文件,excel用excelUtil,xml,text用freemarker
	 */
	private String templateFile;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the exportRender
	 */
	public String getExportRender() {
		return exportRender;
	}

	/**
	 * @param exportRender the exportRender to set
	 */
	public void setExportRender(String exportRender) {
		this.exportRender = exportRender;
	}

	/**
	 * @return the exportUrl
	 */
	public String getExportUrl() {
		return exportUrl;
	}

	/**
	 * @param exportUrl the exportUrl to set
	 */
	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
	}

	/**
	 * @return the extName
	 */
	public String getExtName() {
		return extName;
	}

	/**
	 * @param extName the extName to set
	 */
	public void setExtName(String extName) {
		this.extName = extName;
	}

	/**
	 * @return the templateFile
	 */
	public String getTemplateFile() {
		return templateFile;
	}

	/**
	 * @param templateFile the templateFile to set
	 */
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * @return the exportFile
	 */
	public String getExportFile() {
		return exportFile;
	}

	/**
	 * @param exportFile the exportFile to set
	 */
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}
}
