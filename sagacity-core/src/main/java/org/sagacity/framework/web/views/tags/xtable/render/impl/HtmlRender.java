/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.render.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.model.PaginationModel;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.MarcoModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.render.Render;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>xtable 表格标签向页面输出html</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:HtmlRender.java,Revision:v1.0,Date:2008-12-14 下午03:55:28 $
 */
public class HtmlRender implements Render {
	/*
	 * (non-Javadoc)
	 * @see org.sagacity.framework.web.views.tags.xtable.render.Render#render(javax.servlet.jsp.PageContext, javax.servlet.jsp.tagext.BodyContent, javax.servlet.jsp.JspWriter, org.sagacity.framework.web.views.tags.xtable.model.XTableModel)
	 */
	public void render(PageContext pageContext, BodyContent bodyContent,
			JspWriter writer, XTableModel xTableModel) {
		try {
			String headStr;
			if (xTableModel.getHeaderList() == null) {
				headStr = (String) xTableModel.getTagBodyDefine().get("thead");
			} else {
				headStr = XTableUtil.executeTableHeadMarco(xTableModel,
						XTableUtil.getInstance().getMarcExtLanguage());
			}

			// 判断工具分页栏摆放位置，顶端则提前输出
			if (xTableModel.getToolsBarValign().equalsIgnoreCase("top")) {
				// 输出分页或表头
				if (xTableModel.getPaginationModel() == null)
					printPageHeader(writer, xTableModel);
				else
					printPagination(writer, xTableModel);
				writer.println((String) xTableModel.getTagBodyDefine().get(
						"table"));
				writer.println(headStr);
				printTableRows(writer, xTableModel);
				writer.println("</table>");
			} else {
				writer.println((String) xTableModel.getTagBodyDefine().get(
						"table"));
				writer.println(headStr);
				printTableRows(writer, xTableModel);
				writer.println("</table>");
				// 输出分页或表头
				if (xTableModel.getPaginationModel() == null)
					printPageHeader(writer, xTableModel);
				else
					printPagination(writer, xTableModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出分页信息
	 * 
	 * @param writer
	 * @param tableModel
	 * @param exportModel
	 */
	private void printPagination(JspWriter writer, XTableModel tableModel)
			throws Exception {
		// 有分页功能,输出分页信息
		if (tableModel.getPaginationModel() != null) {
			String paginationTemplate = XTableUtil.getInstance()
					.getPaginationTemplate().toString();
			// 增加分页工具条按钮等
			if (paginationTemplate.indexOf("#{pageExtToolbar}") != -1) {
				String pageExtBar = tableModel.getTagBodyDefine()
						.get("pageExt") == null ? "" : (String) tableModel
						.getTagBodyDefine().get("pageExt");
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{pageExtToolbar}", pageExtBar);
			}
			HashMap tableConstantNames = XTableUtil.getInstance()
					.getTableConstantNames();
			Iterator iter = tableConstantNames.keySet().iterator();
			String constantName;
			while (iter.hasNext()) {
				constantName = (String) iter.next();
				paginationTemplate = StringUtil.replaceAllStr(
						paginationTemplate, "#{" + constantName + "}",
						(String) tableConstantNames.get(constantName));
			}

			PaginationModel pageModel = tableModel.getPaginationModel();
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{toolbarStyle}", tableModel.getToolBarStyle());
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{goPage}", tableModel.getPageNoProperty());
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{pageSizeName}", tableModel.getPageSizeProperty());
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{pageSize}", tableModel.getPageSize());
			paginationTemplate = StringUtil.replaceStr(paginationTemplate,
					"#{firstPage}", Integer.toString(pageModel.getFirstPage()));

			paginationTemplate = StringUtil.replaceStr(paginationTemplate,
					"#{prePage}", Integer.toString(pageModel.getPriorPage()));
			paginationTemplate = StringUtil.replaceStr(paginationTemplate,
					"#{nextPage}", Integer.toString(pageModel.getNextPage()));

			paginationTemplate = StringUtil.replaceStr(paginationTemplate,
					"#{lastPage}", Integer.toString(pageModel.getLastPage()));
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{recordCount}", Integer.toString(pageModel
							.getRecordCount()));

			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{totalPage}", Integer.toString(pageModel.getTotalPage()));

			// 替换上下文路径
			if (StringUtil.IndexOfIgnoreCase(paginationTemplate,
					"#{contextPath}") != -1) {
				paginationTemplate = StringUtil.replaceAllStr(
						paginationTemplate, "#{contextPath}", tableModel
								.getContextPath());
			}

			// 替换当前页号
			paginationTemplate = StringUtil.replaceAllStr(paginationTemplate,
					"#{pageNo}", Integer.toString(pageModel.getPageNo()));

			if (pageModel.getPageNo() == -1) {
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{display}", " style=\"display:none;\"");
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{showAllOrPageNo}", "1");
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{showAllOrPagination}", (String) tableConstantNames
								.get("paginationName"));
			} else {
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{display}", "");
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{showAllOrPageNo}", "-1");
				paginationTemplate = StringUtil.replaceStr(paginationTemplate,
						"#{showAllOrPagination}", (String) tableConstantNames
								.get("showAllPageName"));
			}
			paginationTemplate = executePaginationMarco(tableModel,
					paginationTemplate, tableModel.getMarcoSplitSign());

			writer.println(paginationTemplate);
		}
	}

	/**
	 * 输出表头信息
	 * 
	 * @param writer
	 * @param tableModel
	 * @param exportModel
	 */
	private void printPageHeader(JspWriter writer, XTableModel tableModel)
			throws Exception {

		String headTemplate = XTableUtil.getInstance().getTableHeadTemplate()
				.toString();
		// 替换上下文路径
		if (StringUtil.IndexOfIgnoreCase(headTemplate, "#{contextPath}") != -1) {
			headTemplate = StringUtil.replaceAllStr(headTemplate,
					"#{contextPath}", tableModel.getContextPath());
		}
		headTemplate = StringUtil.replaceAllStr(headTemplate,
				"#{toolbarStyle}", tableModel.getToolBarStyle());
		
		// 增加工具条按钮等
		if (headTemplate.indexOf("#{pageExtToolbar}") != -1) {
			String pageExtBar = tableModel.getTagBodyDefine().get("pageExt") == null ? ""
					: (String) tableModel.getTagBodyDefine().get("pageExt");
			headTemplate = StringUtil.replaceStr(headTemplate,
					"#{pageExtToolbar}", pageExtBar);
		}
		HashMap tableConstantNames = XTableUtil.getInstance()
				.getTableConstantNames();
		Iterator iter = tableConstantNames.keySet().iterator();
		String constantName;
		while (iter.hasNext()) {
			constantName = (String) iter.next();
			headTemplate = StringUtil.replaceAllStr(headTemplate, "#{"
					+ constantName + "}", (String) tableConstantNames
					.get(constantName));
		}
		headTemplate = executePaginationMarco(tableModel, headTemplate,
				tableModel.getMarcoSplitSign());

		writer.println(headTemplate);
	}

	/**
	 * 执行分页中的自定义宏标记
	 * 
	 * @param paginationStr
	 * @param splitSign
	 * @return
	 */
	private String executePaginationMarco(XTableModel tableModel,
			String paginationStr, String splitSign) {
		List marcos = XTableUtil.parseMarco(paginationStr, splitSign,
				XTableUtil.getInstance().getMarcExtLanguage());
		if (marcos != null && !marcos.isEmpty()) {
			MarcoModel marcoModel;
			String result;
			MarcoFacade marcoExt;
			for (Iterator iter = marcos.iterator(); iter.hasNext();) {
				marcoModel = (MarcoModel) iter.next();
				marcoExt = (MarcoFacade) XTableUtil.getInstance()
						.getMarcExtLanguage().get(marcoModel.getMarcoName());
				result = marcoExt.process(null, tableModel, null, 0, marcoModel
						.getParam(), marcoModel.getTemplate());

				paginationStr = StringUtil.replaceStr(paginationStr, marcoModel
						.getMarcoStr(), result);
			}
		}
		return paginationStr;
	}

	/**
	 * 输出表体信息
	 * 
	 * @param writer
	 * @param tableModel
	 */
	private void printTableRows(JspWriter writer, XTableModel tableModel)
			throws Exception {
		List rowDatas = tableModel.getRowsData();
		if (rowDatas == null || rowDatas.size() == 0)
			return;
		String bodyStr = (String) tableModel.getTagBodyDefine().get("tbody");
		bodyStr = bodyStr.replaceFirst("<tbody>", "");
		bodyStr = bodyStr.replaceFirst("</tbody>", "");
		List bodyMarcos = XTableUtil.parseMarco(bodyStr, tableModel
				.getMarcoSplitSign(), XTableUtil.getInstance()
				.getMarcExtLanguage());

		HashMap propertiesMap = tableModel.getPropertiesMap();
		Iterator propertyIter;
		int paramIndex;
		String paramName;
		MarcoModel marcoModel;
		MarcoFacade marcoExt;
		String result;
		List rowData;
		String tmpStr;
		String lineStyle;
		StringBuffer trClass;
		for (int i = 0; i < rowDatas.size(); i++) {
			trClass = new StringBuffer();
			trClass.append("<tr ");
			//奇偶行样式
			if (i % 2 == 1)
				lineStyle = tableModel.getOddLine();
			else
				lineStyle = tableModel.getEvenLine();
			tmpStr = bodyStr;
			rowData = (List) rowDatas.get(i);
			if (bodyMarcos != null && !bodyMarcos.isEmpty()) {
				for (Iterator iter = bodyMarcos.iterator(); iter.hasNext();) {
					marcoModel = (MarcoModel) iter.next();
					marcoExt = (MarcoFacade) XTableUtil.getInstance()
							.getMarcExtLanguage()
							.get(marcoModel.getMarcoName());
					result = marcoExt.process(null, tableModel, rowData, i,
							marcoModel.getParam(), marcoModel.getTemplate());
					if (result != null)
						while (tmpStr.indexOf(marcoModel.getMarcoStr()) != -1)
							tmpStr = StringUtil.replaceStr(tmpStr, marcoModel
									.getMarcoStr(), result);
				}
			}
			propertyIter = propertiesMap.keySet().iterator();
			while (propertyIter.hasNext()) {
				paramName = (String) propertyIter.next();
				paramIndex = ((Integer) propertiesMap.get(paramName))
						.intValue();
				tmpStr = StringUtil.replaceAllStr(tmpStr, "#[" + paramName
						+ "]", rowData.get(paramIndex) == null ? "" : rowData
						.get(paramIndex).toString());

			}
			if (StringUtil.isNotNullAndBlank(tableModel.getOnclickEvent()))
				trClass.append(" onclick='").append(
						tableModel.getOnclickEvent()).append("'");
			if (StringUtil.isNotNullAndBlank(lineStyle))
				trClass.append(" class=").append(lineStyle);
			if (StringUtil.isNotNullAndBlank(tableModel.getOnMouseOut()))
				trClass.append(" onMouseOut='").append(
						tableModel.getOnMouseOut()).append("'");
			if (StringUtil.isNotNullAndBlank(tableModel.getOnMouseOver()))
				trClass.append(" onMouseOver='").append(
						tableModel.getOnMouseOver()).append("'");
			tmpStr = StringUtil.replaceStr(tmpStr, "<tr", trClass.toString());
			writer.println(tmpStr);
		}
	}
}
