/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable;

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
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.render.impl.HtmlRender;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * @project abchina
 * @description:$ <p>
 *                xtable目标:
 *                实现介于普通页面列表如displaytag和BI报表工具(如birt)之间的报表工具，适应非BI报表之外的 报表页面展示需求
 *                xtable 发展路线图： v1.0β版本：初始版本因时间和项目周期原因代码没有很好的优化，结构相对凌乱，满足现有应用
 *                v1.0GA版本:在β版上进行代码重构，优化结构形成稳定的接口，对部分代码进行优化，完善注释和错误提示，
 *                并统一xtable和xgrid接口
 *                v2.0β版本:整合分组汇总求平均值等功能，文件导出方式增加pdf,xml方式，形成完整的常用的宏扩展库，提供底层接口处理
 *                v2.0GA版本:优化2.0β版本代码部分代码重构，对外开源进行推广采纳用户的建议
 *                v3.0β版本:融入四维度以内交叉功能，优化底层接口方法，部分支持中国式报表，支持国际化
 *                v3.0GA版本:优化交叉算法，编写完整的使用文档和函数接口说明
 *                xtable源于陈仁飞的sagacity开源项目中的xtable,根据农行的要求将其迁移到农行的框架
 *                体系中，在众多开源table标签可选择的情况下特别开发xtable意义在于xtable有强于displaytag
 *                ,extrameTable
 *                之处，xtable支持以@xxx()方式定义的扩展标记，xtable除了一般页面所拥有的分页，导excel功能之外，它是支持
 *                真正意义上的分组合并，xtable支持求和，求平均值，xtable支持四维以内的交叉
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:XTableTag.java,Revision:v1.0β,Date:May 21, 2008 3:48:34 PM $
 * @publish Copyright@2008 版权归陈仁飞，不得抄袭，如引用请注明出处。
 */
public class XTableTag extends BaseTagSupport {
	// xtable 组件id,不要随便改变其定义，其用来定位资源的引用
	private final String COMPONENT_ID = "xtable";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2077445629922214779L;

	/**
	 * 表的标题
	 */
	private String caption;

	/**
	 * 简单模式
	 */
	private String model = "simple";

	/**
	 * 显示的属性
	 */
	private String displayProperties;

	/**
	 * 日期默认格式
	 */
	private String dateFormat = "yyyy-MM-dd";

	/**
	 * 汇总属性字段
	 */
	private String totalProperties;

	/**
	 * 偶数行样式
	 */
	private String oddLineStyle = "oddline";

	/**
	 * 奇数行样式
	 */
	private String evenLineStyle = "evenline";

	/**
	 * 行选中
	 */
	private String onclick = "xtable_selected(this)";

	private String onmouseover = "xtableTrMouseOver(this)";

	private String onmouseout = "xtableTrMouseOut(this)";

	/**
	 * 分组
	 */
	private String group;

	/**
	 * 分组别名
	 */
	private String groupAlias;

	/**
	 * 自定义宏标记的参数分割符
	 */
	private String marcoSplitSign = ",";

	/**
	 * 主要提供针对excel,xml等特殊格式导出功能的模版
	 */
	private String exportTemplate;

	/**
	 * 每页长度
	 */
	private String pageSize;

	/**
	 * 导出文件
	 */
	private String exportFile;

	/**
	 * 工具条的样式的样式
	 */
	private String toolBarStyle = "id=\"sag_listtable\"";

	/**
	 * 工具条存放位置
	 */
	private String toolsBarValign = "top";

	/**
	 * 是否有文件下载
	 */
	private String hasExport = "true";

	/**
	 * 是否有分页
	 */
	private String hasPaging = "true";

	/**
	 * 是否自动对齐
	 */
	private String autoAlign = "true";

	/**
	 * xtable数据模型,提供统一数据模型为了将来给后台提供接口，便于xtable今后支持 交叉报表以及中国式复杂报表等更多扩展特性
	 */
	private XTableModel xTableModel = null;

	/**
	 * 导出action url
	 */
	private String exportAction;

	private String exportParam = null;

	private String exportType = null;

	private boolean isExportData = false;

	/**
	 * 开始标签的处理，提供表格标签的数据提取功能，以及部分表格标签的数据模型的构造
	 */
	public int doStartTag() throws JspException {
		try {
			// 构造导出文件时url parameter名称
			exportParam = (this.styleId != null ? this.styleId : "")
					+ "xtable_export_type";
			exportType = pageContext.getRequest().getParameter(exportParam);
			isExportData = this.isExportData();
			// 导出列表且本列表exportType为空则表示不导出本列表
			if (isExportData && exportType == null) {
				this.release();
				return this.SKIP_BODY;
			}
			
			// 获取表格的数据源
			Object dataSource = this.getDataResource();		
			if (dataSource == null) {
				logger.debug("the " + this.property + " XTable is Null!");
				return this.SKIP_BODY;
			}
			//构造数据模型
			construtDataModel(dataSource);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.SKIP_BODY;
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
			BodyContent body = getBodyContent();

			// 解析table的体内容获取样式定义和相应的字符串指令
			HashMap tagBodyHash = XTableUtil.parseTagBody(TagUtil.getInstance()
					.clearHtmlMark(body.getString()));
			this.xTableModel.setTagBodyDefine(tagBodyHash);

			// 如果导出列表
			if (this.isExportData) {
				// 根据导出类型获取导出处理类的实体，导出
				if (StringUtil.isNotNullAndBlank(exportType)) {
					String headStr = XTableUtil.executeTableHeadMarco(
							this.xTableModel, XTableUtil.getInstance()
									.getMarcExtLanguage());
					// 提取标签头信息，供导出excel以及输出html分页时用来计算头的colspan
					this.xTableModel.setHeaderList(XTableUtil.getInstance()
							.parseHead(headStr));
				}
				// 导出处理实现类
				xTableModel.setExportResolver(((ExportModel) xTableModel
						.getExportTypes().get(exportType)).getExportRender());
				this.pageContext.getRequest().setAttribute(
						XTableConstants.XTABLE_EXPORT_VIEW, this.xTableModel);
				return this.SKIP_PAGE;
			}

			JspWriter writer = body.getEnclosingWriter();

			// 输出组件需要的js,css等资源
			super.renderResources(this.COMPONENT_ID, writer);

			xTableModel.setToolsBarValign(this.toolsBarValign);
			HtmlRender htmlRender = new HtmlRender();
			htmlRender.render(null, null, writer, xTableModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	/**
	 * 
	 */
	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}
		
	/**
	 * 构造数据模型
	 * @param dataSource
	 * @throws Exception
	 */
	private void construtDataModel(Object dataSource) throws Exception
	{
		// 数据源的类型标志
		int dataClassFlag = 0;
		// 处理数据源的格式类型,类型包括xtableModel,list,pagination三类格式
		if (dataSource instanceof XTableModel) {
			xTableModel = (XTableModel) dataSource;
			if (xTableModel.getPaginationModel() != null)
				xTableModel.setItems(xTableModel.getPaginationModel()
						.getItems());
		} else {
			xTableModel = new XTableModel();
			if (dataSource instanceof PaginationModel) {
				dataClassFlag = 1;
				xTableModel
						.setPaginationModel((PaginationModel) dataSource);
				xTableModel.setItems(xTableModel.getPaginationModel()
						.getItems());
				xTableModel.setPageSize(Integer.toString(xTableModel
						.getPaginationModel().getPageSize()));
			} else {
				xTableModel.setItems(dataSource);
			}
		}

		// 判断数据源是否为空, 不为空则根据properties提取相关属性数据构造表格每行的数据
		if (xTableModel.getItems() != null) {
			this.displayProperties = XTableUtil.processDisplayProperties(
					this.displayProperties, xTableModel.getItems());
			List rowsData = TagUtil.getInstance().reflactList(xTableModel.getItems(),
							this.displayProperties,
							true,
							this.model
									.equalsIgnoreCase(XTableConstants.XTABLE_MODEL.XTABLE_MODEL_UNLIMITWIDTH) ? 0
									: 1);

			// 如果xtable是不定宽模式,将数据的第一行取出作为表头信息
			if (this.model
					.equalsIgnoreCase(XTableConstants.XTABLE_MODEL.XTABLE_MODEL_UNLIMITWIDTH)) {
				xTableModel.setHeaderList(rowsData.subList(0, 1));
				xTableModel.setRowsData(rowsData
						.subList(1, rowsData.size()));
			} else
				xTableModel.setRowsData(rowsData);

			// 自动增加sequence
			if (this.displayProperties.toLowerCase().trim().indexOf(
					"xtable_seqNo,") != 0)
				this.displayProperties = "xtable_seqNo,"
						+ this.displayProperties.trim();
			// 参数分割后的顺序map
			HashMap propertiesMap = TagUtil.getInstance().splitProperties(
					this.displayProperties);
			xTableModel.setPropertiesMap(propertiesMap);
		} else {
			System.out
					.println("table "
							+ (this.caption == null ? (this.id == null ? this.datasource
									: this.id)
									: this.caption)
							+ " datasource is empty!");
		}

		xTableModel.setTableId(this.styleId);

		// 判断数据是从form或者是scope中直接取出来
		if (this.dataContainer.equalsIgnoreCase("pojo")) {
			if (dataClassFlag == 0) {
				xTableModel.setPageNoProperty(this.datasource
						+ ".paginationModel.pageNo");
				xTableModel.setPageSizeProperty(this.datasource
						+ ".paginationModel.pageNo");
			} else if (dataClassFlag == 1) {
				xTableModel.setPageNoProperty(this.datasource + ".pageNo");
				xTableModel.setPageSizeProperty(this.datasource
						+ ".pageSize");
			}
		} else {
			xTableModel.setPageNoProperty(this.styleId + "PageNo");
			xTableModel.setPageSizeProperty(this.styleId + "PageSize");
		}
		xTableModel.setToolBarStyle(this.toolBarStyle);

		xTableModel.setEvenLine(this.evenLineStyle);
		xTableModel.setOddLine(this.oddLineStyle);
		xTableModel.setOnclickEvent(this.onclick);

		xTableModel.setOnMouseOut(this.onmouseout);
		xTableModel.setOnMouseOver(this.onmouseover);

		// 构造导出文件时url parameter名称
		this.xTableModel.setExportParam(this.exportParam);

		/**
		 * 导出文件
		 */
		xTableModel.setExportFile(this.exportFile == null ? this.caption
				: this.exportFile);

		/**
		 * 设置模板文件
		 */
		xTableModel.setTemplateFile(this.exportTemplate);

		// 设置标题
		xTableModel.setCaption(this.caption);
		xTableModel.setExportTypes(XTableUtil.getInstance()
				.getExportTypes());

		// 设置上下文路径
		xTableModel.setContextPath(((HttpServletRequest) this.pageContext
				.getRequest()).getContextPath());

		// 是否自动对齐
		if (!this.autoAlign.equalsIgnoreCase("true"))
			xTableModel.setAutoAlign(false);

		// 是否导出文件
		if (!this.hasExport.equalsIgnoreCase("true"))
			xTableModel.setHasExport(false);

		// 是否有分页条
		if (!this.hasPaging.equalsIgnoreCase("true"))
			xTableModel.setHasPaging(false);

		// 设置自定义宏标记
		xTableModel.setMarcoSplitSign(this.marcoSplitSign);
		// 组合导文件的url
		String exportUrl = XTableUtil.getInstance().assembExportUrl(
				this.exportAction,
				(HttpServletRequest) this.pageContext.getRequest(),
				xTableModel.getPageNoProperty());

		exportUrl += (exportUrl.indexOf("?") != -1 ? "&" : "?")
				+ xTableModel.getPageNoProperty() + "="
				+ URLEncoder.encode("-1");
		xTableModel.setExportAction(exportUrl);
	}
	
	/**
	 * 释放资源
	 */
	public void release() {
		this.releaseSource();
		this.caption = null;
		this.model = "simple";
		this.displayProperties = null;
		this.dateFormat = "yyyy-MM-dd";
		this.totalProperties = null;
		this.oddLineStyle = "oddline";
		this.evenLineStyle = "evenline";
		this.group = null;
		this.groupAlias = null;
		this.pageSize = null;
		this.exportFile = null;
		this.xTableModel = null;
		this.toolsBarValign = "top";
		this.exportAction = null;
		this.autoAlign = "false";
		this.hasExport = "true";
		this.hasPaging = "true";
		this.exportParam = null;
		this.exportType = null;
		this.toolBarStyle = "id=\"sag_listtable\"";
		this.isExportData = false;
		this.onclick = "xtable_selected(this)";
		this.onclick = "xtable_selected(this)";
		this.onmouseover = "xtableTrMouseOver(this)";
		this.onmouseout = "xtableTrMouseOut(this)";

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
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
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
	 * @return the oddLineStyle
	 */
	public String getOddLineStyle() {
		return oddLineStyle;
	}

	/**
	 * @param oddLineStyle
	 *            the oddLineStyle to set
	 */
	public void setOddLineStyle(String oddLineStyle) {
		this.oddLineStyle = oddLineStyle;
	}

	/**
	 * @return the evenLineStyle
	 */
	public String getEvenLineStyle() {
		return evenLineStyle;
	}

	/**
	 * @param evenLineStyle
	 *            the evenLineStyle to set
	 */
	public void setEvenLineStyle(String evenLineStyle) {
		this.evenLineStyle = evenLineStyle;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the groupAlias
	 */
	public String getGroupAlias() {
		return groupAlias;
	}

	/**
	 * @param groupAlias
	 *            the groupAlias to set
	 */
	public void setGroupAlias(String groupAlias) {
		this.groupAlias = groupAlias;
	}

	/**
	 * @return the exportfile
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
	 * @return the hasExport
	 */
	public String getHasExport() {
		return hasExport;
	}

	/**
	 * @param hasExport
	 *            the hasExport to set
	 */
	public void setHasExport(String hasExport) {
		this.hasExport = hasExport;
	}

	/**
	 * @return the hasPaging
	 */
	public String getHasPaging() {
		return hasPaging;
	}

	/**
	 * @param hasPaging
	 *            the hasPaging to set
	 */
	public void setHasPaging(String hasPaging) {
		this.hasPaging = hasPaging;
	}

	/**
	 * @return the autoAlign
	 */
	public String getAutoAlign() {
		return autoAlign;
	}

	/**
	 * @param autoAlign
	 *            the autoAlign to set
	 */
	public void setAutoAlign(String autoAlign) {
		this.autoAlign = autoAlign;
	}

	/**
	 * @return the toolBarStyle
	 */
	public String getToolBarStyle() {
		return toolBarStyle;
	}

	/**
	 * @param toolBarStyle
	 *            the toolBarStyle to set
	 */
	public void setToolBarStyle(String toolBarStyle) {
		this.toolBarStyle = toolBarStyle;
	}

	/**
	 * @return the onclick
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick
	 *            the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return the onmouseover
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * @param onmouseover
	 *            the onmouseover to set
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * @return the onmouseout
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * @param onmouseout
	 *            the onmouseout to set
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

}
