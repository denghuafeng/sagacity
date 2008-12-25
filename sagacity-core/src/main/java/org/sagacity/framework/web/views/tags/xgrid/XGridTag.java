/**
 * 
 */
package org.sagacity.framework.web.views.tags.xgrid;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.model.PaginationModel;
import org.sagacity.framework.web.views.tags.BaseTagSupport;
import org.sagacity.framework.web.views.tags.TagUtil;
import org.sagacity.framework.web.views.tags.xtable.XTableConstants;
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;
import org.sagacity.framework.web.views.tags.xtable.model.XGridModel;
import org.sagacity.framework.web.views.tags.xtable.render.Render;
import org.sagacity.framework.web.views.tags.xtable.render.impl.HtmlRender;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          grid组件,v1.0版独立于xtable，之后将跟xtable进行整合，通过统一的属性facade="grid"定义，
 *          xgrid基于dhtmlGrid组件,提供其基于jsp taglib的封装便于使用,目前版本提供其今后的完整的结构
 *          框架，提供分页和下载功能，但针对dhtmlGrid的高级特性目前阶段不提供
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:GridTag.java,Revision:v1.0,Date:May 28, 2008 9:03:20 PM $
 */
public class XGridTag extends BaseTagSupport {
	private final String COMPONENT_ID="xgrid";
	/**
	 * 
	 */
	private static final long serialVersionUID = 5416182498285161017L;

	/**
	 * 应用模式
	 */
	private String model = "simple";

	/**
	 * 皮肤风格
	 */
	private String skin = "xp";

	/**
	 * 分割符
	 */
	private String delimiter = ",";

	/**
	 * 默认宽度
	 */
	private String width = "98%";

	/**
	 * 默认高度
	 */
	private String height;

	/**
	 * 层样式
	 */
	private String style = "background-color:white;";

	/**
	 * 表头
	 */
	private String titles;

	/**
	 * 自动设置高度
	 */
	private String enableAutoHeigth = "true";

	/**
	 * 每页记录数
	 */
	private String pageSize = "10";

	/**
	 * 显示属性
	 */
	private String displayProperties;

	/**
	 * 汇总属性
	 */
	private String totalProperties;

	/**
	 * 列的宽度定义
	 */
	private String colsWidth;

	/**
	 * 对齐方式
	 */
	private String colsAlign;

	/**
	 * 列的字段类型
	 */
	private String colsType;

	/**
	 * 多行
	 */
	private String multiRows;

	/**
	 * 导出文件
	 */
	private String exportFile;

	/**
	 * 导出文件对应的url
	 */
	private String exportAction;

	/**
	 * 标题
	 */
	private String caption;

	/**
	 * 工具条摆放位置(列表头或列表尾)
	 */
	private String toolsBarValign = "top";

	/**
	 * 导出数据模板,提供如excelUtil,freemarker,volecity等技术应用
	 */
	private String exportTemplate;

	/**
	 * grid数据模型
	 */
	private XGridModel xGridModel;

	/**
	 * 宏标记的分割符号
	 */
	private String marcoSplitSign = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		try {
			// 获取选项
			Object dataSource = this.getDataResource();

			// 数据源的类型标志
			int dataClassFlag = 0;
			if (dataSource == null) {
				System.out.println("the " + this.property + " XTable is Null!");
				return this.EVAL_PAGE;
			}

			// 处理数据源的格式类型,类型包括xtableModel,list,pagination三类格式
			if (dataSource instanceof XGridModel) {
				xGridModel = (XGridModel) dataSource;
				if (xGridModel.getPaginationModel() != null)
					xGridModel.setItems(xGridModel.getPaginationModel()
							.getItems());
			} else {
				xGridModel = new XGridModel();
				if (dataSource instanceof PaginationModel) {
					dataClassFlag = 1;
					xGridModel.setPaginationModel((PaginationModel) dataSource);
					xGridModel.setItems(xGridModel.getPaginationModel()
							.getItems());
				} else {
					xGridModel.setItems(dataSource);
				}
			}

			// 判断数据源是否为空, 不为空则根据properties提取相关属性数据构造表格每行的数据
			if (xGridModel.getItems() != null) {
				this.displayProperties = XTableUtil.processDisplayProperties(
						this.displayProperties, xGridModel.getItems());
				List rowsData = TagUtil.getInstance()
						.reflactList(
								xGridModel.getItems(),
								this.displayProperties,
								true,
								this.model
										.equalsIgnoreCase(XTableConstants.XTABLE_MODEL.XTABLE_MODEL_UNLIMITWIDTH) ? 0
										: 1);

				// 如果xtable是不定宽模式,将数据的第一行取出作为表头信息
				if (this.model
						.equalsIgnoreCase(XTableConstants.XTABLE_MODEL.XTABLE_MODEL_UNLIMITWIDTH)) {
					List titlesList = (List) rowsData.get(0);
					StringBuffer titlesBuffer = new StringBuffer();
					StringBuffer widthsBuffer = new StringBuffer();
					StringBuffer typesBuffer = new StringBuffer();
					for (int i = 0; i < titlesList.size(); i++) {
						if (i > 0) {
							titlesBuffer.append(",");
							typesBuffer.append(",");
							widthsBuffer.append(",");
						}
						titlesBuffer.append(titlesList.get(i).toString());
						typesBuffer.append("ro");
						widthsBuffer.append("60");
					}
					this.titles = titlesBuffer.toString();
					this.colsType = typesBuffer.toString();
					this.colsWidth = widthsBuffer.toString();
					xGridModel
							.setRowsData(rowsData.subList(1, rowsData.size()));
					List firstRowData = (List) xGridModel.getRowsData().get(0);
					StringBuffer alignsBuffer = new StringBuffer();
					String align;
					for (int i = 0; i < firstRowData.size(); i++) {
						align = "center";
						if (firstRowData.get(i) != null
								&& firstRowData.get(i) instanceof Number)
							align = "right";
						if (i > 0)
							alignsBuffer.append(",");
						alignsBuffer.append(align);
					}
					this.colsAlign = alignsBuffer.toString();

				} else
					xGridModel.setRowsData(rowsData);

				// 自动增加sequence
				if (this.displayProperties.toLowerCase().trim().indexOf(
						"xtable_seqNo,") != 0)
					this.displayProperties = "xtable_seqNo,"
							+ this.displayProperties.trim();
				// 参数分割后的顺序map
				HashMap propertiesMap = TagUtil.getInstance()
						.splitProperties(this.displayProperties);
				xGridModel.setPropertiesMap(propertiesMap);
			} else {
				System.err
						.println("table "
								+ (this.caption == null ? (this.id == null ? this.datasource
										: this.id)
										: this.caption)
								+ " datasource is empty!");
			}

			xGridModel.setGridId(this.styleId);
			//
			if (this.dataContainer.equalsIgnoreCase("pojo")) {
				if (dataClassFlag == 0) {
					xGridModel.setPageNoProperty(this.datasource
							+ ".paginationModel.pageNo");
					xGridModel.setPageSizeProperty(this.datasource
							+ ".paginationModel.pageNo");
				} else if (dataClassFlag == 1) {
					xGridModel.setPageNoProperty(this.datasource + ".pageNo");
					xGridModel.setPageSizeProperty(this.datasource + ".pageNo");
				}
			} else {
				xGridModel.setPageNoProperty(this.styleId + "PageNo");
				xGridModel.setPageSizeProperty(this.styleId + "PageSize");
			}

			// 设置标题
			xGridModel.setCaption(this.caption);
			xGridModel.setAligns(this.colsAlign);
			xGridModel.setTitles(this.titles);
			xGridModel.setTypes(this.colsType);
			xGridModel.setWidths(this.colsWidth);
			xGridModel.setGridHeight(this.height);
			xGridModel.setGridWidth(this.width);
			xGridModel.setGridStyle(this.style);
			xGridModel.setGridskin(this.skin);
			xGridModel.setDelimiter(this.delimiter);
			xGridModel.setEnableAutoHeigth(this.enableAutoHeigth);
			xGridModel.setToolsBarValign(this.toolsBarValign);
			xGridModel.setMarcoSplitSign(this.marcoSplitSign);
			//设置上下文路径
			xGridModel.setContextPath(((HttpServletRequest) this.pageContext
					.getRequest()).getContextPath());

			// 设置列表数据的下载导出方式,一般包括excel等格式
			xGridModel
					.setExportTypes(XTableUtil.getInstance().getExportTypes());

			xGridModel.setToolsBarValign(this.toolsBarValign);
			// 组合导文件的url
			String exportUrl = XTableUtil.getInstance().assembExportUrl(
					this.exportAction,
					(HttpServletRequest) this.pageContext.getRequest(),
					xGridModel.getPageNoProperty());

			// 导出url
			exportUrl += (exportUrl.indexOf("?") != -1 ? "&" : "?")
					+ xGridModel.getPageNoProperty() + "="
					+ URLEncoder.encode("-1");

			xGridModel.setExportAction(exportUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return this.EVAL_PAGE;
		}
		return this.EVAL_BODY_BUFFERED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	public int doAfterBody() throws JspException {
		try {
			// 解析标签体内容,提取相关指令和样式等信息
			BodyContent body = getBodyContent();
			xGridModel.setGridBodyMap(XTableUtil.parseGridBody(TagUtil.getInstance()
					.clearHtmlMark(body.getString())));
			// 构造导出文件时url parameter名称
			String exportParam = (this.styleId != null ? this.styleId : "")
					+ "xtable_export_type";
			this.xGridModel.setExportParam(exportParam);

			String isExport = pageContext.getRequest().getParameter(
					"xtable_is_export");

			// 解决单个页面存在多个分页,当执行导出时xtable不输出html
			if (isExport != null && isExport.equalsIgnoreCase("true")) {
				// 从页面请求参数中获取导出数据的类型,包括excel,pdf,xml等
				String exportType = pageContext.getRequest().getParameter(
						exportParam);
				// 根据导出类型获取导出处理类的实体，导出
				if (StringUtil.isNotNullAndBlank(exportType)) {
					// 获取导出文件的头
					ExportModel exportModel = (ExportModel) xGridModel
							.getExportTypes().get(exportType);
					exportModel
							.setExportFile(this.exportFile == null ? this.caption
									: this.exportFile);
					exportModel.setTemplateFile(this.exportTemplate);
					Render render = (Render) Class.forName(
							exportModel.getExportRender()).newInstance();

					//render.render(pageContext, null,null, this.xGridModel);
					body.clear();
					body = pageContext.pushBody();
					return SKIP_PAGE;
				}
			} else {
				JspWriter writer = body.getEnclosingWriter();
				//输入组件需要的js,css等资源
				super.renderResources(this.COMPONENT_ID, writer);
				HtmlRender htmlRender = new HtmlRender();
				//htmlRender.render(null,null, writer, xGridModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
	 */
	public void release() {
		this.releaseSource();
		this.model = "simple";
		this.skin = "xp";
		this.delimiter = ",";
		this.width = "98%";
		this.height = "400px";
		this.style = "background-color:white;";
		this.titles = null;
		this.pageSize = "10";
		this.displayProperties = null;
		this.totalProperties = null;
		this.colsWidth = null;
		this.colsAlign = null;
		this.colsType = null;
		this.multiRows = null;
		this.exportFile = null;
		this.exportAction = null;
		this.enableAutoHeigth = "true";
		this.toolsBarValign = "top";
		this.exportTemplate = null;
	}

	private void printHtml() {

	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the skin
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * @param skin
	 *            the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * @param delimiter
	 *            the delimiter to set
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the titles
	 */
	public String getTitles() {
		return titles;
	}

	/**
	 * @param titles
	 *            the titles to set
	 */
	public void setTitles(String titles) {
		this.titles = titles;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the displayProperties
	 */
	public String getDisplayProperties() {
		return displayProperties;
	}

	/**
	 * @param displayProperties
	 *            the displayProperties to set
	 */
	public void setDisplayProperties(String displayProperties) {
		this.displayProperties = displayProperties;
	}

	/**
	 * @return the totalProperties
	 */
	public String getTotalProperties() {
		return totalProperties;
	}

	/**
	 * @param totalProperties
	 *            the totalProperties to set
	 */
	public void setTotalProperties(String totalProperties) {
		this.totalProperties = totalProperties;
	}

	/**
	 * @return the colsWidth
	 */
	public String getColsWidth() {
		return colsWidth;
	}

	/**
	 * @param colsWidth
	 *            the colsWidth to set
	 */
	public void setColsWidth(String colsWidth) {
		this.colsWidth = colsWidth;
	}

	/**
	 * @return the colsAlign
	 */
	public String getColsAlign() {
		return colsAlign;
	}

	/**
	 * @param colsAlign
	 *            the colsAlign to set
	 */
	public void setColsAlign(String colsAlign) {
		this.colsAlign = colsAlign;
	}

	/**
	 * @return the colsType
	 */
	public String getColsType() {
		return colsType;
	}

	/**
	 * @param colsType
	 *            the colsType to set
	 */
	public void setColsType(String colsType) {
		this.colsType = colsType;
	}

	/**
	 * @return the multiRows
	 */
	public String getMultiRows() {
		return multiRows;
	}

	/**
	 * @param multiRows
	 *            the multiRows to set
	 */
	public void setMultiRows(String multiRows) {
		this.multiRows = multiRows;
	}

	/**
	 * @return the exportFile
	 */
	public String getExportFile() {
		return exportFile;
	}

	/**
	 * @param exportFile
	 *            the exportFile to set
	 */
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}

	/**
	 * @return the exportAction
	 */
	public String getExportAction() {
		return exportAction;
	}

	/**
	 * @param exportAction
	 *            the exportAction to set
	 */
	public void setExportAction(String exportAction) {
		this.exportAction = exportAction;
	}

	/**
	 * @return the enableAutoHeigth
	 */
	public String getEnableAutoHeigth() {
		return enableAutoHeigth;
	}

	/**
	 * @param enableAutoHeigth
	 *            the enableAutoHeigth to set
	 */
	public void setEnableAutoHeigth(String enableAutoHeigth) {
		this.enableAutoHeigth = enableAutoHeigth;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the toolsBarValign
	 */
	public String getToolsBarValign() {
		return toolsBarValign;
	}

	/**
	 * @param toolsBarValign
	 *            the toolsBarValign to set
	 */
	public void setToolsBarValign(String toolsBarValign) {
		this.toolsBarValign = toolsBarValign;
	}

	/**
	 * @return the exportTemplate
	 */
	public String getExportTemplate() {
		return exportTemplate;
	}

	/**
	 * @param exportTemplate
	 *            the exportTemplate to set
	 */
	public void setExportTemplate(String exportTemplate) {
		this.exportTemplate = exportTemplate;
	}

	/**
	 * @return the marcoSplitSign
	 */
	public String getMarcoSplitSign() {
		return marcoSplitSign;
	}

	/**
	 * @param marcoSplitSign
	 *            the marcoSplitSign to set
	 */
	public void setMarcoSplitSign(String marcoSplitSign) {
		this.marcoSplitSign = marcoSplitSign;
	}
}
