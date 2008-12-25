/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.sagacity.framework.web.model.PaginationModel;


/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:XGridModel.java,Revision:v1.0,Date:May 29, 2008 2:48:18 PM $
 */
public class XGridModel implements Serializable {
	/**
	 * 原始数据
	 */
	private Object items;
	
	/**
	 * 提取后的数据
	 */
	private List rowsData;
	
	/**
	 * 分页模型
	 */
	private PaginationModel paginationModel;
	
	/**
	 * 分页中的自定义标记
	 */
	private List paginationMarcos;
	
	/**
	 * 标志号
	 */
	private String gridId;
	
	/**
	 *标题
	 */
	private String caption;
	
	/**
	 * 
	 */
	private String pageNoProperty;
	
	/**
	 * 分页长度对应页面属性
	 */
	private String pageSizeProperty;
	
	/**
	 * 导出action
	 */
	private String exportAction;
	
	/**
	 * 列属性hashmap{colNameKey,colValue}
	 */
	private HashMap propertiesMap;
	
	/**
	 * 导出类型定义
	 */
	private HashMap exportTypes;
	
	/**
	 * gridbody 解析后的相关内容
	 */
	private HashMap gridBodyMap;
	
	/**
	 * grid 头
	 */
	private String titles;
	
	/**
	 * 列数据的类型
	 */
	private String types;
	
	/**
	 * 列的对齐方式
	 */
	private String aligns;
	
	/**
	 * 列的宽度
	 */
	private String widths;
	
	/**
	 * grid 宽度
	 */
	private String gridWidth;
	
	/**
	 * grid 高度
	 */
	private String gridHeight;
	
	/**
	 * 
	 */
	private String gridStyle;
	
	private String gridskin;
	
	private String delimiter;
	
	private String enableAutoHeigth;
	
	/**
	 * grid行中的每列数据属性
	 */
	private List rowCellsDefine;
	
	/**
	 * 通用分割符号
	 */
	private String gridComonDelimiter=",";
	
	/**
	 * 分页工具条的摆放位置(列表头或列表尾)
	 */
	private String toolsBarValign;
	
	/**
	 * 自定义宏标记的参数分割符
	 */
	private String marcoSplitSign;
	
	/**
	 * 导出参数
	 */
	private String exportParam;
	
	/**
	 * 上下文路径
	 */
	private String contextPath;
	

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * @return the toolsBarValign
	 */
	public String getToolsBarValign() {
		return toolsBarValign;
	}

	/**
	 * @param toolsBarValign the toolsBarValign to set
	 */
	public void setToolsBarValign(String toolsBarValign) {
		this.toolsBarValign = toolsBarValign;
	}

	/**
	 * @return the paginationModel
	 */
	public PaginationModel getPaginationModel() {
		return paginationModel;
	}

	/**
	 * @param paginationModel the paginationModel to set
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
	 * @param items the items to set
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
	 * @param rowsData the rowsData to set
	 */
	public void setRowsData(List rowsData) {
		this.rowsData = rowsData;
	}

	/**
	 * @return the pageNoProperty
	 */
	public String getPageNoProperty() {
		return pageNoProperty;
	}

	/**
	 * @param pageNoProperty the pageNoProperty to set
	 */
	public void setPageNoProperty(String pageNoProperty) {
		this.pageNoProperty = pageNoProperty;
	}

	/**
	 * @return the gridId
	 */
	public String getGridId() {
		return gridId;
	}

	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the exportTypes
	 */
	public HashMap getExportTypes() {
		return exportTypes;
	}

	/**
	 * @param exportTypes the exportTypes to set
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
	 * @param exportAction the exportAction to set
	 */
	public void setExportAction(String exportAction) {
		this.exportAction = exportAction;
	}

	/**
	 * @return the propertiesMap
	 */
	public HashMap getPropertiesMap() {
		return propertiesMap;
	}

	/**
	 * @param propertiesMap the propertiesMap to set
	 */
	public void setPropertiesMap(HashMap propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	/**
	 * @return the gridBodyMap
	 */
	public HashMap getGridBodyMap() {
		return gridBodyMap;
	}

	/**
	 * @param gridBodyMap the gridBodyMap to set
	 */
	public void setGridBodyMap(HashMap gridBodyMap) {
		this.gridBodyMap = gridBodyMap;
	}

	/**
	 * @return the titles
	 */
	public String getTitles() {
		return titles;
	}

	/**
	 * @param titles the titles to set
	 */
	public void setTitles(String titles) {
		this.titles = titles;
	}

	/**
	 * @return the types
	 */
	public String getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String types) {
		this.types = types;
	}

	/**
	 * @return the aligns
	 */
	public String getAligns() {
		return aligns;
	}

	/**
	 * @param aligns the aligns to set
	 */
	public void setAligns(String aligns) {
		this.aligns = aligns;
	}

	/**
	 * @return the rowCellsDefine
	 */
	public List getRowCellsDefine() {
		return rowCellsDefine;
	}

	/**
	 * @param rowCellsDefine the rowCellsDefine to set
	 */
	public void setRowCellsDefine(List rowCellsDefine) {
		this.rowCellsDefine = rowCellsDefine;
	}

	/**
	 * @return the pageSizeProperty
	 */
	public String getPageSizeProperty() {
		return pageSizeProperty;
	}

	/**
	 * @param pageSizeProperty the pageSizeProperty to set
	 */
	public void setPageSizeProperty(String pageSizeProperty) {
		this.pageSizeProperty = pageSizeProperty;
	}

	/**
	 * @return the exportParam
	 */
	public String getExportParam() {
		return exportParam;
	}

	/**
	 * @param exportParam the exportParam to set
	 */
	public void setExportParam(String exportParam) {
		this.exportParam = exportParam;
	}

	/**
	 * @return the widths
	 */
	public String getWidths() {
		return widths;
	}

	/**
	 * @param widths the widths to set
	 */
	public void setWidths(String widths) {
		this.widths = widths;
	}

	/**
	 * @return the gridWidth
	 */
	public String getGridWidth() {
		return gridWidth;
	}

	/**
	 * @param gridWidth the gridWidth to set
	 */
	public void setGridWidth(String gridWidth) {
		this.gridWidth = gridWidth;
	}

	/**
	 * @return the gridHeight
	 */
	public String getGridHeight() {
		return gridHeight;
	}

	/**
	 * @param gridHeight the gridHeight to set
	 */
	public void setGridHeight(String gridHeight) {
		this.gridHeight = gridHeight;
	}

	/**
	 * @return the gridStyle
	 */
	public String getGridStyle() {
		return gridStyle;
	}

	/**
	 * @param gridStyle the gridStyle to set
	 */
	public void setGridStyle(String gridStyle) {
		this.gridStyle = gridStyle;
	}

	/**
	 * @return the gridskin
	 */
	public String getGridskin() {
		return gridskin;
	}

	/**
	 * @param gridskin the gridskin to set
	 */
	public void setGridskin(String gridskin) {
		this.gridskin = gridskin;
	}

	/**
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * @param delimiter the delimiter to set
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * @return the enableAutoHeigth
	 */
	public String getEnableAutoHeigth() {
		return enableAutoHeigth;
	}

	/**
	 * @param enableAutoHeigth the enableAutoHeigth to set
	 */
	public void setEnableAutoHeigth(String enableAutoHeigth) {
		this.enableAutoHeigth = enableAutoHeigth;
	}

	/**
	 * @return the gridComonDelimiter
	 */
	public String getGridComonDelimiter() {
		return gridComonDelimiter;
	}

	/**
	 * @param gridComonDelimiter the gridComonDelimiter to set
	 */
	public void setGridComonDelimiter(String gridComonDelimiter) {
		this.gridComonDelimiter = gridComonDelimiter;
	}

	/**
	 * @return the marcoSplitSign
	 */
	public String getMarcoSplitSign() {
		return marcoSplitSign;
	}

	/**
	 * @param marcoSplitSign the marcoSplitSign to set
	 */
	public void setMarcoSplitSign(String marcoSplitSign) {
		this.marcoSplitSign = marcoSplitSign;
	}

	/**
	 * @return the paginationMarcos
	 */
	public List getPaginationMarcos() {
		return paginationMarcos;
	}

	/**
	 * @param paginationMarcos the paginationMarcos to set
	 */
	public void setPaginationMarcos(List paginationMarcos) {
		this.paginationMarcos = paginationMarcos;
	}
}
