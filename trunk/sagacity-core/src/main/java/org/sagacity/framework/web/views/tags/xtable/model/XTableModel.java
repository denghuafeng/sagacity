/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.sagacity.framework.web.model.PaginationModel;

/**
 * @project sagacity-core
 * @description:$ <p>
 *                xtable多功能标签的数据对象模型
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:XTable.java,Revision:v1.0,Date:May 12, 2008 9:58:38 AM $
 */
public class XTableModel implements Serializable {
	/**
	 * table的唯一标志
	 */
	private String tableId;

	/**
	 * 导出时url对应参数名称,一般格式为:tableId+"xtable_export_type";
	 */
	private String exportParam;

	/**
	 * 原始数据集合:items必须是list,hashset,Array格式
	 */
	private Object items;

	/**
	 * 分页条的样式
	 */
	private String toolBarStyle;

	/**
	 * 存放每行数据的集合,通过对items数据以displayProperties处理
	 * 将实际需要的数据以list方式存放在rowsData中,如果displayProperties为空
	 * items则即是非pojo的数据，通过list转换放入rowsData中
	 */
	private List rowsData;

	/**
	 * 表头数据集
	 */
	private List headerList;

	/**
	 * 表体数据集
	 */
	private List bodyList;

	/**
	 * 表尾数据集
	 */
	private List footList;

	/**
	 * 奇数行
	 */
	private String evenLine;

	/**
	 * 偶数行
	 */
	private String oddLine;

	/**
	 * 行选中事件
	 */
	private String onclickEvent;
	
	/**
	 * 
	 */
	private String onMouseOver;
	
	/**
	 * 
	 */
	private String onMouseOut;
	
	/**
	 * 模板文件
	 */
	private String templateFile;
	
	/**
	 * 导出处理类
	 */
	private String exportResolver;
	
	public String getExportResolver() {
		return exportResolver;
	}

	public void setExportResolver(String exportResolver) {
		this.exportResolver = exportResolver;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * 导出文件名称
	 */
	private String exportFile;

	public String getExportFile() {
		return exportFile;
	}

	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}

	/**
	 * @return the onMouseOver
	 */
	public String getOnMouseOver() {
		return onMouseOver;
	}

	/**
	 * @param onMouseOver the onMouseOver to set
	 */
	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	/**
	 * @return the onMouseOut
	 */
	public String getOnMouseOut() {
		return onMouseOut;
	}

	/**
	 * @param onMouseOut the onMouseOut to set
	 */
	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	/**
	 * @return the evenLine
	 */
	public String getEvenLine() {
		return evenLine;
	}

	/**
	 * @param evenLine
	 *            the evenLine to set
	 */
	public void setEvenLine(String evenLine) {
		this.evenLine = evenLine;
	}

	/**
	 * @return the oddLine
	 */
	public String getOddLine() {
		return oddLine;
	}

	/**
	 * @param oddLine
	 *            the oddLine to set
	 */
	public void setOddLine(String oddLine) {
		this.oddLine = oddLine;
	}

	/**
	 * @return the onclickEvent
	 */
	public String getOnclickEvent() {
		return onclickEvent;
	}

	/**
	 * @param onclickEvent
	 *            the onclickEvent to set
	 */
	public void setOnclickEvent(String onclickEvent) {
		this.onclickEvent = onclickEvent;
	}

	/**
	 * 单元格数据信息
	 */
	private List cellDataList;

	/**
	 * 导出文件action
	 */
	private String exportAction;

	/**
	 * 文件导出类型
	 */
	private HashMap exportTypes;

	/**
	 * 
	 */
	private HashMap tagBodyDefine;

	/**
	 * 分页数据模型
	 */
	private PaginationModel paginationModel;

	/**
	 * 分页中的自定义标记
	 */
	private List paginationMarcos;

	/**
	 * 表头中的自定义标记
	 */
	private List headerMarcos;

	/**
	 * 表体中的自定义标记
	 */
	private List bodyMarcos;

	/**
	 * 表尾中的自定义标记
	 */
	private List footerMarcos;

	/**
	 * 第几页对象名称
	 */
	private String pageNoProperty;

	/**
	 * 自定义宏标记的参数分割符
	 */
	private String marcoSplitSign;

	/**
	 * 工具条排放位置，默认为顶端
	 */
	private String toolsBarValign = "top";

	/**
	 * 表格的caption,格式在tag body中开头书写:<caption class="">caption内容</caption>
	 * 或则自定义表头的列表名称，将替换自定义表头的占位标签@placeHolder(#caption)
	 */
	private String caption;

	/**
	 * 参数属性map
	 */
	private HashMap propertiesMap;

	/**
	 * 每页记录数对应页面元素名称
	 */
	private String PageSizeProperty;

	/**
	 * 每页记录条数
	 */
	private String pageSize;

	/**
	 * 是否自动对齐
	 */
	private boolean autoAlign = false;

	/**
	 * 是否提供导出excel，pdf等操作，目前当表头为复杂多行表头时不提供文件导出功能
	 */
	private boolean hasExport = true;

	/**
	 * 是否有分页条
	 */
	private boolean hasPaging = true;

	/**
	 * 上下文路径
	 */
	private String contextPath;

	/**
	 * @return the hasExport
	 */
	public boolean isHasExport() {
		return hasExport;
	}

	/**
	 * @param hasExport
	 *            the hasExport to set
	 */
	public void setHasExport(boolean hasExport) {
		this.hasExport = hasExport;
	}

	/**
	 * @return the hasPaging
	 */
	public boolean isHasPaging() {
		return hasPaging;
	}

	/**
	 * @param hasPaging
	 *            the hasPaging to set
	 */
	public void setHasPaging(boolean hasPaging) {
		this.hasPaging = hasPaging;
	}

	/**
	 * @return the cellDataList
	 */
	public List getCellDataList() {
		return cellDataList;
	}

	/**
	 * @param cellDataList
	 *            the cellDataList to set
	 */
	public void setCellDataList(List cellDataList) {
		this.cellDataList = cellDataList;
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
	 * @return the paginationModel
	 */
	public PaginationModel getPaginationModel() {
		return paginationModel;
	}

	/**
	 * @param paginationModel
	 *            the paginationModel to set
	 */
	public void setPaginationModel(PaginationModel paginationModel) {
		this.paginationModel = paginationModel;
	}

	/**
	 * @return the items
	 */
	public Object getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Object items) {
		this.items = items;
	}

	/**
	 * @return the rowsData
	 */
	public List getRowsData() {
		return rowsData;
	}

	/**
	 * @param rowsData
	 *            the rowsData to set
	 */
	public void setRowsData(List rowsData) {
		this.rowsData = rowsData;
	}

	/**
	 * @return the headerList
	 */
	public List getHeaderList() {
		return headerList;
	}

	/**
	 * @param headerList
	 *            the headerList to set
	 */
	public void setHeaderList(List headerList) {
		this.headerList = headerList;
	}

	/**
	 * @return the bodyList
	 */
	public List getBodyList() {
		return bodyList;
	}

	/**
	 * @param bodyList
	 *            the bodyList to set
	 */
	public void setBodyList(List bodyList) {
		this.bodyList = bodyList;
	}

	/**
	 * @return the footList
	 */
	public List getFootList() {
		return footList;
	}

	/**
	 * @param footList
	 *            the footList to set
	 */
	public void setFootList(List footList) {
		this.footList = footList;
	}

	/**
	 * @return the exportTypes
	 */
	public HashMap getExportTypes() {
		return exportTypes;
	}

	/**
	 * @param exportTypes
	 *            the exportTypes to set
	 */
	public void setExportTypes(HashMap exportTypes) {
		this.exportTypes = exportTypes;
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
	 * @return the tagBodyDefine
	 */
	public HashMap getTagBodyDefine() {
		return tagBodyDefine;
	}

	/**
	 * @param tagBodyDefine
	 *            the tagBodyDefine to set
	 */
	public void setTagBodyDefine(HashMap tagBodyDefine) {
		this.tagBodyDefine = tagBodyDefine;
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
	 * @return the pageNoProperty
	 */
	public String getPageNoProperty() {
		return pageNoProperty;
	}

	/**
	 * @param pageNoProperty
	 *            the pageNoProperty to set
	 */
	public void setPageNoProperty(String pageNoProperty) {
		this.pageNoProperty = pageNoProperty;
	}

	/**
	 * @return the tableId
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * @param tableId
	 *            the tableId to set
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	/**
	 * @return the exportParam
	 */
	public String getExportParam() {
		return exportParam;
	}

	/**
	 * @param exportParam
	 *            the exportParam to set
	 */
	public void setExportParam(String exportParam) {
		this.exportParam = exportParam;
	}

	/**
	 * @return the paginationMarcos
	 */
	public List getPaginationMarcos() {
		return paginationMarcos;
	}

	/**
	 * @param paginationMarcos
	 *            the paginationMarcos to set
	 */
	public void setPaginationMarcos(List paginationMarcos) {
		this.paginationMarcos = paginationMarcos;
	}

	/**
	 * @return the headerMarcos
	 */
	public List getHeaderMarcos() {
		return headerMarcos;
	}

	/**
	 * @param headerMarcos
	 *            the headerMarcos to set
	 */
	public void setHeaderMarcos(List headerMarcos) {
		this.headerMarcos = headerMarcos;
	}

	/**
	 * @return the bodyMarcos
	 */
	public List getBodyMarcos() {
		return bodyMarcos;
	}

	/**
	 * @param bodyMarcos
	 *            the bodyMarcos to set
	 */
	public void setBodyMarcos(List bodyMarcos) {
		this.bodyMarcos = bodyMarcos;
	}

	/**
	 * @return the footerMarcos
	 */
	public List getFooterMarcos() {
		return footerMarcos;
	}

	/**
	 * @param footerMarcos
	 *            the footerMarcos to set
	 */
	public void setFooterMarcos(List footerMarcos) {
		this.footerMarcos = footerMarcos;
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
	 * @return the propertiesMap
	 */
	public HashMap getPropertiesMap() {
		return propertiesMap;
	}

	/**
	 * @param propertiesMap
	 *            the propertiesMap to set
	 */
	public void setPropertiesMap(HashMap propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	/**
	 * @return the pageSizeProperty
	 */
	public String getPageSizeProperty() {
		return PageSizeProperty;
	}

	/**
	 * @param pageSizeProperty
	 *            the pageSizeProperty to set
	 */
	public void setPageSizeProperty(String pageSizeProperty) {
		PageSizeProperty = pageSizeProperty;
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
	 * @return the autoAlign
	 */
	public boolean isAutoAlign() {
		return autoAlign;
	}

	/**
	 * @param autoAlign
	 *            the autoAlign to set
	 */
	public void setAutoAlign(boolean autoAlign) {
		this.autoAlign = autoAlign;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath
	 *            the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
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

}
