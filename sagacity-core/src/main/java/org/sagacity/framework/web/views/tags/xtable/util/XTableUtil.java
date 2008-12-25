/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.util;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sagacity.framework.utils.AryUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.tags.TagUtil;
import org.sagacity.framework.web.views.tags.xtable.marco.MarcoFacade;
import org.sagacity.framework.web.views.tags.xtable.model.Cell;
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;
import org.sagacity.framework.web.views.tags.xtable.model.GridCellModel;
import org.sagacity.framework.web.views.tags.xtable.model.MarcoModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          xtable 工具类
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:XTableUtil.java,Revision:v1.0,Date:May 14, 2008 3:45:09 PM $
 */
public class XTableUtil implements Serializable {
	/**
	 * 宏扩展语言
	 */
	private static HashMap marcoExtLanguage = null;

	/**
	 * 导出下载文件
	 */
	private static HashMap exportTypes = null;

	/**
	 * 表格常用名称
	 */
	private static HashMap tableConstantNames = null;

	/**
	 * 事件名称，目前针对struts1.2为准用method
	 */
	private static String EVENT_PARAM = "method";

	/**
	 * 实例
	 */
	private static XTableUtil me;

	/**
	 * 自定义扩展标记宏文件,文件路径和名称不能修改
	 */
	private final static String xtableConfigFile = "/components/xtable/xtable.xml";
	
	/**
	 * 表头模板
	 */
	private final static String xtableHeadTemplate="/components/xtable/header.txt";
	
	/**
	 * 分页部分模板
	 */
	private final static String xtablePageTemplate="/components/xtable/pagination.txt";

	/**
	 * 分页模板
	 */
	private static StringBuffer paginationTemplate;

	/**
	 * 表头模板
	 */
	private static StringBuffer tableHeadTemplate;

	/**
	 * 
	 * @return
	 */
	public static XTableUtil getInstance() {
		if (me == null) {
			me = new XTableUtil();
		}
		return me;
	}

	/**
	 * 获取扩展标记函数
	 * 
	 * @return
	 */
	public HashMap getMarcExtLanguage() {
		if (marcoExtLanguage == null) {
			try {
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(this.getClass()
						.getResourceAsStream(xtableConfigFile));

				Element root = doc.getRootElement();
				Element marcs = root.element("marcs");
				Iterator marcIter = marcs.elementIterator("marc");
				marcoExtLanguage = new HashMap();
				Element marc = null;

				while (marcIter.hasNext()) {
					marc = (Element) marcIter.next();
					marcoExtLanguage.put("@" + marc.attributeValue("name"),
							Class.forName(marc.attributeValue("class"))
									.newInstance());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return marcoExtLanguage;
	}

	/**
	 * 表格常用名称
	 * 
	 * @return
	 */
	public HashMap getTableConstantNames() {
		if (tableConstantNames == null) {
			try {
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(this.getClass()
						.getResourceAsStream(xtableConfigFile));
				Element root = doc.getRootElement();
				Element properties = root.element("properties");
				Iterator iters = properties.elementIterator("property");
				Element property = null;
				while (iters.hasNext()) {
					property = (Element) iters.next();
					if (tableConstantNames == null)
						tableConstantNames = new HashMap();
					tableConstantNames.put(property.attributeValue("name"),
							property.attributeValue("value"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tableConstantNames;
	}

	/**
	 * 获取导出方式和相关处理函数等信息
	 * 
	 * @return
	 */
	public HashMap getExportTypes() {
		if (exportTypes == null) {
			try {
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(this.getClass()
						.getResourceAsStream(xtableConfigFile));
				Element root = doc.getRootElement();
				Element exportTypesElement = root.element("exportTypes");
				Iterator exports = exportTypesElement.elementIterator("export");
				Element export = null;
				while (exports.hasNext()) {
					export = (Element) exports.next();
					if (export.attributeValue("active")
							.equalsIgnoreCase("true")) {
						if (exportTypes == null)
							exportTypes = new HashMap();
						ExportModel exportModel = new ExportModel();
						exportModel.setType(export.attributeValue("type"));
						exportModel.setParam(export.attributeValue("param"));
						exportModel
								.setExtName(export.attributeValue("extName"));
						exportModel.setExportRender(export
								.attributeValue("exportRender"));
						exportModel.setTitle(export.attributeValue("title"));
						exportTypes.put(exportModel.getType(), exportModel);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return exportTypes;
	}

	/**
	 * /components/xtable/header.txt(html,htm)为固定的表头样式存放文件路径，如果
	 * 要修改表头样式则在项目的classes/components/xtable/下面放入header.txt文件
	 * 
	 * @return
	 */
	public StringBuffer getTableHeadTemplate() {
		if (tableHeadTemplate == null) {
			InputStream inputStream = this.getClass().getResourceAsStream(xtableHeadTemplate);
			tableHeadTemplate = StringUtil.inputStream2Buffer(inputStream);
		}

		return tableHeadTemplate;
	}

	/**
	 * /components/xtable/pagination.txt(html,htm)为固定的分页样式存放文件路径，如果
	 * 要修改分页样式则在项目的classes/components/xtable/下面放入pagination.txt文件
	 * 
	 * @return
	 */
	public StringBuffer getPaginationTemplate() {
		if (paginationTemplate == null) {
			InputStream is = this.getClass().getResourceAsStream(xtablePageTemplate);
			paginationTemplate = StringUtil.inputStream2Buffer(is);
		}
		return paginationTemplate;
	}

	/**
	 * 组合xtable导出excel等数据时的url,url需要包含前次发起请求所需要的参数信息
	 * 
	 * @param exportUrl
	 * @param request
	 * @param filter:要排斥的参数
	 * @return
	 */
	public String assembExportUrl(String exportUrl, HttpServletRequest request,
			String filter) {
		String exportAction;
		if (exportUrl == null)
			exportAction = request.getRequestURI();
		else
			exportAction = exportUrl;
		int i = 0;
		String param;
		if (exportAction.startsWith("/"))
			exportAction = request.getContextPath() + exportAction;
		else
			exportAction = request.getContextPath() + "/" + exportAction;
		boolean hasMethod = false;
		try {
			if (request.getParameter(this.EVENT_PARAM) != null) {
				hasMethod = true;
				if (exportAction.indexOf("?") == -1)
					exportAction += "?";
				else
					exportAction += "&";
				exportAction += this.EVENT_PARAM + "="
						+ request.getParameter(this.EVENT_PARAM);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Enumeration em = request.getParameterNames(); em.hasMoreElements();) {
			param = (String) em.nextElement();
			if (!param.equalsIgnoreCase(this.EVENT_PARAM) || !hasMethod) {
				if (!param.equalsIgnoreCase(filter)) {
					if (i == 0) {
						if (exportAction.indexOf("?") == -1)
							exportAction += "?";
						else
							exportAction += "&";
						exportAction += param
								+ "="
								+ URLEncoder
										.encode(request.getParameter(param));
					} else
						exportAction += "&"
								+ param
								+ "="
								+ URLEncoder
										.encode(request.getParameter(param));
					i++;
				}
			}
		}

		// exportAction+="&jsessionid="+request.getSession().getId();
		return exportAction;
	}

	/**
	 * 解析tag的体内容，得到相应的样式和指令(执行顺序:1)
	 * 
	 * @todo parse tagBody and get the information
	 * @param tagBody
	 *            String
	 */
	public static HashMap parseTagBody(String tagBody) {
		if (StringUtil.isNotNullAndBlank(tagBody.trim())) {
			HashMap bodyContent = new HashMap();
			try {
				// 统一tagbody中的标记
				tagBody = unifyTagBody(tagBody);
				String pageExt = null;
				if (tagBody.indexOf("<pageExt>") != -1) {
					pageExt = tagBody.substring(
							tagBody.indexOf("<pageExt>") + 9, tagBody
									.indexOf("</pageExt>"));

					/*
					 * 执行表头中的宏指令，暂时不提供实现,后期实现提供表头交叉等复杂功能
					 */
					// executeHeadMarc();
					bodyContent.put("pageExt", pageExt);
				}

				if (tagBody.indexOf("<table") != -1) {
					String table = tagBody.substring(tagBody.indexOf("<table"),
							tagBody.indexOf("<thead"));
					if (!StringUtil.isNullOrBlank(pageExt))
						table = StringUtil.replaceStr(table, pageExt, "");
					bodyContent.put("table", table);
				}

				// 存在表头表体定义则提取内容用于导出excel等文件,excel
				if (tagBody.indexOf("<thead") != -1) {
					String header = tagBody.substring(tagBody
							.indexOf("<thead>") + 7, tagBody
							.indexOf("</thead>"));

					/*
					 * 执行表头中的宏指令，暂时不提供实现,后期实现提供表头交叉等复杂功能
					 */
					// executeHeadMarc();
					bodyContent.put("thead", header);

				}
				if (tagBody.indexOf("<tbody") != -1) {
					bodyContent.put("tbody", tagBody
							.substring(tagBody.indexOf("<tbody"), tagBody
									.indexOf("</tbody>") + 8));
				}
				if (tagBody.indexOf("<tfoot") != -1) {
					bodyContent.put("tfoot", tagBody
							.substring(tagBody.indexOf("<tfoot"), tagBody
									.indexOf("</tfoot>") + 8));
				}
				return bodyContent;
			} catch (Exception e) {
				System.err.println(e.getMessage()
						+ "  Input True BodyContent Please!");
			}
		}
		return null;
	}

	/**
	 * 处理标签体，将标签体中的标记统一成小写的格式
	 * 
	 * @param tagBody
	 * @return
	 */
	private static String unifyTagBody(String tagBody) {
		// clear the chars that be used to mark
		tagBody = TagUtil.getInstance().clearHtmlMark(tagBody);
		// unify cell's style like "<td>cellValue</td>"
		tagBody = tagBody.replaceAll("<TABLE", "<table");
		tagBody = tagBody.replaceAll("</TABLE", "</table");
		tagBody = tagBody.replaceAll("<THEAD", "<thead");
		tagBody = tagBody.replaceAll("</THEAD", "</thead");
		tagBody = tagBody.replaceAll("<TBODY", "<tbody");
		tagBody = tagBody.replaceAll("</TBODY", "</tbody");
		tagBody = tagBody.replaceAll("<TFOOT", "<tfoot");
		tagBody = tagBody.replaceAll("</TFOOT", "</tfoot");
		tagBody = tagBody.replaceAll("<TH", "<th");
		tagBody = tagBody.replaceAll("</TH", "</th");
		tagBody = tagBody.replaceAll("<TR", "<tr");
		tagBody = tagBody.replaceAll("</TR", "</tr");
		tagBody = tagBody.replaceAll("<TD", "<td");
		tagBody = tagBody.replaceAll("</TD", "</td");
		return tagBody.trim();
	}

	/**
	 * 
	 * @param tagHeader
	 * @return
	 */
	public static List parseHead(String tagHeader) {
		String cellSign = "th";
		if (tagHeader.indexOf("<td") != -1)
			cellSign = "td";
		String[] trAry = tagHeader.replaceAll("<thead>", "").replaceAll(
				"</thead>", "").split("<tr");
		List resultList = new ArrayList();
		int styleEnd;
		String tdStyle;
		String tdTxt;
		String colspanRegexp = "\\s+colspan\\s+\\=(\"|\')?\\d*(\"|\')?";
		String rowspanRegexp = "\\s+rowspan\\s+\\=(\"|\')?\\d*(\"|\')?";
		Pattern p = null;
		Matcher m = null;
		String colspan;
		String rowspan;
		// 解析tr中td的内容
		for (int i = 0; i < trAry.length; i++) {
			if (!trAry[i].trim().equals("")) {
				if (trAry[i].indexOf("<" + cellSign) != -1) {
					List row = new ArrayList();
					String[] tds = trAry[i].split("<" + cellSign);
					for (int j = 1; j < tds.length; j++) {
						if (StringUtil.isNotNullAndBlank(tds[j])) {
							styleEnd = tds[j].indexOf(">");
							tdStyle = tds[j].substring(0, styleEnd);
							tdTxt = tds[j].substring(styleEnd + 1,
									tds[j].indexOf("</" + cellSign + ">"))
									.trim();
							Cell cell = new Cell();
							if (tdStyle.toLowerCase().indexOf(" colspan") != -1) {
								p = Pattern.compile(colspanRegexp);
								m = p.matcher(tdStyle.toLowerCase());
								while (m.find()) {
									colspan = m.group().replaceFirst(
											" colspan", "").replaceFirst("=",
											"").replaceAll("\"", "")
											.replaceAll("\'", "");
									if (StringUtil.isNotNullAndBlank(colspan))
										cell.setColspan(Integer
												.parseInt(colspan));
								}
							}
							if (tdStyle.toLowerCase().indexOf(" rowspan") != -1) {
								p = Pattern.compile(rowspanRegexp);
								m = p.matcher(tdStyle.toLowerCase());
								while (m.find()) {
									rowspan = m.group().replaceFirst(
											" rowspan", "").replaceFirst("=",
											"").replaceAll("\"", "")
											.replaceAll("\'", "");
									if (StringUtil.isNotNullAndBlank(rowspan))
										cell.setRowspan(Integer
												.parseInt(rowspan));
								}
							}
							cell.setStyle(tdStyle.trim());
							cell.setCellContent(tdTxt);
							cell.setValue(tdTxt);
							row.add(cell);
						}
					}
					resultList.add(row);
				}
			}
		}
		return resultList;
	}

	/**
	 * 输出表头
	 * 
	 * @param writer
	 * @param tableModel
	 */
	public static String executeTableHeadMarco(XTableModel tableModel,
			HashMap marcoMap) {
		String tableHead = (String) tableModel.getTagBodyDefine().get("thead");
		List headMarco = parseMarco(tableHead, tableModel.getMarcoSplitSign(),
				marcoMap);

		if (headMarco != null && !headMarco.isEmpty()) {
			MarcoModel marcoModel;
			MarcoFacade marcoExt;
			String result;
			for (Iterator iter = headMarco.iterator(); iter.hasNext();) {
				marcoModel = (MarcoModel) iter.next();
				marcoExt = (MarcoFacade) marcoMap
						.get(marcoModel.getMarcoName());
				result = marcoExt.process(null, tableModel, (List) tableModel
						.getHeaderList().get(
								tableModel.getHeaderList().size() - 1), 0,
						marcoModel.getParam(), marcoModel.getTemplate());
				while (tableHead.indexOf(marcoModel.getMarcoStr()) != -1)
					tableHead = tableHead.replaceFirst(
							marcoModel.getMarcoStr(), result);
			}

			// head中是否有参数
			if (tableModel.getHeaderList() != null) {
				List rowData = (List) tableModel.getHeaderList().get(
						tableModel.getHeaderList().size() - 1);
				HashMap propertiesMap = tableModel.getPropertiesMap();
				Iterator propertyIter = propertiesMap.keySet().iterator();
				String paramName;
				int paramIndex;
				while (propertyIter.hasNext()) {
					paramName = (String) propertyIter.next();
					paramIndex = ((Integer) propertiesMap.get(paramName))
							.intValue();
					tableHead = tableHead.replaceAll("\\#\\[" + paramName
							+ "\\]", rowData.get(paramIndex) == null ? ""
							: rowData.get(paramIndex).toString());
				}
			}
		}
		return tableHead;
	}

	/**
	 * 
	 * @param tagHeader
	 * @return
	 */
	public static List parseTr(String trStr) {
		String[] trAry = trStr.split("<tr");
		List resultList = new ArrayList();
		int styleEnd;
		String tdStyle;
		String tdTxt;
		String colspanRegexp = "\\s+colspan\\s+\\=(\"|\')?\\d*(\"|\')?";
		String rowspanRegexp = "\\s+rowspan\\s+\\=(\"|\')?\\d*(\"|\')?";
		Pattern p = null;
		Matcher m = null;
		String colspan;
		String rowspan;
		for (int i = 0; i < trAry.length; i++) {
			if (!trAry[i].trim().equals("")) {
				if (trAry[i].indexOf("<td") != -1) {
					List row = new ArrayList();
					String[] tds = trAry[i].split("<td");
					for (int j = 1; j < tds.length; j++) {
						if (StringUtil.isNotNullAndBlank(tds[j])) {
							styleEnd = tds[j].indexOf(">");
							tdStyle = tds[j].substring(0, styleEnd);
							tdTxt = tds[j].substring(styleEnd + 1,
									tds[j].indexOf("</td>")).trim();
							Cell cell = new Cell();
							if (tdStyle.toLowerCase().indexOf(" colspan") != -1) {
								p = Pattern.compile(colspanRegexp);
								m = p.matcher(tdStyle.toLowerCase());
								while (m.find()) {
									colspan = m.group().replaceFirst(
											" colspan", "").replaceFirst("=",
											"").replaceAll("\"", "")
											.replaceAll("\'", "");
									if (StringUtil.isNotNullAndBlank(colspan))
										cell.setColspan(Integer
												.parseInt(colspan));
								}
							}
							if (tdStyle.toLowerCase().indexOf(" rowspan") != -1) {
								p = Pattern.compile(rowspanRegexp);
								m = p.matcher(tdStyle.toLowerCase());
								while (m.find()) {
									rowspan = m.group().replaceFirst(
											" rowspan", "").replaceFirst("=",
											"").replaceAll("\"", "")
											.replaceAll("\'", "");
									if (StringUtil.isNotNullAndBlank(rowspan))
										cell.setRowspan(Integer
												.parseInt(rowspan));
								}
							}
							cell.setStyle(tdStyle.trim());
							cell.setCellContent(tdTxt);
							cell.setValue(tdTxt);
							if (StringUtil.isNullOrBlank(tdTxt.trim()))
								cell.setPropertiesIndex(j - 1);
							row.add(cell);
						}
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 解析出标签体中的自定义宏扩展标记
	 * 
	 * @param marcoContainer
	 * @param splitSign
	 * @return
	 */
	public static List parseMarco(String marcoContainer, String splitSign,
			HashMap marcoMap) {
		if (StringUtil.isNullOrBlank(marcoContainer) || marcoMap == null)
			return null;
		List result = new ArrayList();
		try {
			Iterator iter = marcoMap.keySet().iterator();
			String marcoName;
			HashMap unionMap = new HashMap();
			while (iter.hasNext()) {
				marcoName = iter.next().toString();
				String container = marcoContainer;
				int startIndex = container.indexOf(marcoName + "(");
				String mapStr;
				while (startIndex != -1) {
					int lastIndex = StringUtil.getMarkEndIndex("(", ")",
							container, startIndex + marcoName.length());
					if (lastIndex != -1) {
						mapStr = container.substring(startIndex, lastIndex + 1);
						container = StringUtil
								.replaceStr(container, mapStr, "");

						// 判断重复的宏使用
						if (unionMap.get(mapStr) == null) {
							MarcoModel marcoModel = new MarcoModel();
							marcoModel.setMarcoStr(mapStr);
							marcoModel.setMarcoName(mapStr.substring(0, mapStr
									.indexOf("(")));
							marcoModel.setParam(mapStr.substring(mapStr
									.indexOf("(") + 1, mapStr
									.indexOf(splitSign)));
							
							marcoModel.setTemplate(mapStr.substring(mapStr
									.indexOf(splitSign)
									+ splitSign.length(), StringUtil.getMarkEndIndex("(", ")", mapStr, 0)));
							
							marcoModel.setTemplate(mapStr.substring(mapStr
									.indexOf(splitSign)
									+ splitSign.length(), mapStr
									.lastIndexOf(")")));
							result.add(marcoModel);
							unionMap.put(mapStr, "");
						}
						startIndex = container.indexOf(marcoName + "(");
					} else
						startIndex = container.indexOf(marcoName + "(",
								startIndex + marcoName.length() + 1);
				}
			}
		} catch (Exception e) {
			System.err.println("解析分页模板中的宏内容定义错误,请检查宏扩展标记的分隔符号等定义是否正确!");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 解析gridTag body内容
	 * 
	 * @param gridBody
	 * @return
	 */
	public static HashMap parseGridBody(String gridBody) {
		gridBody = TagUtil.getInstance().clearHtmlMark(gridBody.trim());
		String gridBodylowStr = gridBody.toLowerCase();
		HashMap hashMap = new HashMap();
		int tableIndex = gridBodylowStr.indexOf("<table");

		String tableStyle = gridBody.substring(tableIndex, StringUtil
				.getMarkEndIndex("<", ">", gridBodylowStr, tableIndex + 1) + 1);
		hashMap.put("table", tableStyle);

		String tableOtherStyle;
		// 表的特殊属性，如fieldset等
		if (gridBodylowStr.indexOf("<#tableother>") != -1
				&& gridBodylowStr.indexOf("</#tableother>") != -1) {
			tableOtherStyle = gridBodylowStr.substring(gridBodylowStr
					.indexOf("<#tableother>") + 13, gridBodylowStr
					.indexOf("</#tableother>"));
			hashMap.put("tableother", tableOtherStyle);
		}

		// grid的全局自定义扩展javascript 等脚本
		String headScript;
		if (gridBodylowStr.indexOf("<#headscript>") != -1
				&& gridBodylowStr.indexOf("</#headscript>") != -1) {
			headScript = gridBody.substring(gridBodylowStr
					.indexOf("<#headscript>") + 13, gridBodylowStr
					.indexOf("</#headscript>"));
			hashMap.put("headscript", headScript);
		}

		String tailscript;
		if (gridBodylowStr.indexOf("<#tailscript>") != -1
				&& gridBodylowStr.indexOf("</#tailscript>") != -1) {
			tailscript = gridBody.substring(gridBodylowStr
					.indexOf("<#tailscript>") + 13, gridBodylowStr
					.indexOf("</#tailscript>"));
			hashMap.put("tailscript", tailscript);
		}

		String cols = null;
		String beforeCol = null;
		String afterCol = null;
		String rowscript = null;
		// 行样式等信息定义解析
		if (gridBodylowStr.indexOf("<cols>") != -1
				&& gridBodylowStr.indexOf("</cols>") != -1) {
			cols = gridBody.substring(gridBodylowStr.indexOf("<cols>") + 6,
					gridBodylowStr.indexOf("</cols>"));
			// 行自定义javascript等脚本扩展，比如设置行的特殊显示样式
			if (cols.indexOf("<#rowscript>") != -1
					&& cols.indexOf("</#rowscript>") != -1) {
				rowscript = cols.substring(cols.indexOf("<#rowscript>") + 12,
						cols.indexOf("</#rowscript>"));
				hashMap.put("rowscript", rowscript);
				// 是否嵌套标签头中定义的属性
				if (cols.indexOf("<#embed>") != -1) {
					beforeCol = cols.substring(0, cols.indexOf("<#embed>"));
					afterCol = cols.substring(cols.indexOf("<#embed>") + 8,
							cols.indexOf("<#rowscript>"));
				} else
					hashMap.put("cols", cols.substring(0, cols
							.indexOf("<#rowscript>")));
			} else {
				if (cols.indexOf("<#embed>") != -1) {
					beforeCol = cols.substring(0, cols.indexOf("<#embed>"));
					afterCol = cols.substring(cols.indexOf("<#embed>") + 8);
				} else
					hashMap.put("cols", cols);
			}
			hashMap.put("beforeCols", beforeCol);
			hashMap.put("afterCols", afterCol);
		}
		return hashMap;
	}

	/**
	 * 解析grid标签体中col列定义 <col title="测试" value="01" align="right"
	 * format="yyyy-MM-dd" expression="dd">
	 * 
	 * @param colDefineStr
	 * @return
	 */
	public static List parseGridBodyCols(String colDefineStr) {
		List result = null;

		String regexp = "\\<col\\s+[a-zA-Z]+\\=(\'|\")(.*)(\'|\")\\>";
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(colDefineStr);
		String mapStr;
		int paramIndex;
		while (m.find()) {
			if (result == null)
				result = new ArrayList();
			mapStr = m.group();
			GridCellModel gridCellModel = new GridCellModel();
			paramIndex = mapStr.indexOf(" title=\"");
			if (paramIndex != -1)
				gridCellModel.setTitle(mapStr.substring(paramIndex + 8,
						mapStr.indexOf("\"", paramIndex + 8)).trim());

			paramIndex = mapStr.indexOf(" value=\"");
			if (paramIndex != -1)
				gridCellModel.setValue(mapStr.substring(paramIndex + 8,
						mapStr.indexOf("\"", paramIndex + 8)).trim());

			paramIndex = mapStr.indexOf(" align=\"");
			if (paramIndex != -1)
				gridCellModel.setAlign(mapStr.substring(paramIndex + 8,
						mapStr.indexOf("\"", paramIndex + 8)).trim());

			paramIndex = mapStr.indexOf(" width=\"");
			if (paramIndex != -1)
				gridCellModel.setWidth(mapStr.substring(paramIndex + 8,
						mapStr.indexOf("\"", paramIndex + 8)).trim());

			paramIndex = mapStr.indexOf(" type=\"");
			if (paramIndex != -1)
				gridCellModel.setType(mapStr.substring(paramIndex + 7,
						mapStr.indexOf("\"", paramIndex + 7)).trim());

			paramIndex = mapStr.indexOf(" expression=\"");
			if (paramIndex != -1)
				gridCellModel.setExpression(mapStr.substring(paramIndex + 13,
						mapStr.indexOf("\"", paramIndex + 13)).trim());
			result.add(gridCellModel);
		}
		return result;
	}

	/**
	 * 替换占位符号如:source="abc#{orgNo}[#{orgName}]"，将从数组中获取值替换
	 * id="abc#{orgNO}#{orgCode}" abc001FA
	 * 
	 * @param source
	 * @param propertiesMap
	 * @param rowData
	 * @return
	 */
	public static String replaceHoler(String source, HashMap propertiesMap,
			List rowData) {
		if (propertiesMap == null || propertiesMap.isEmpty())
			return source;
		Iterator iter = propertiesMap.keySet().iterator();
		String property;
		while (iter.hasNext()) {
			property = iter.next().toString();
			if (source.indexOf("#[" + property + "]") != -1) {
				source = source.replaceAll("\\#\\[" + property + "\\]",
						rowData.get(((Integer) propertiesMap.get(property))
								.intValue()) == null ? "" : rowData.get(
								((Integer) propertiesMap.get(property))
										.intValue()).toString());
			}
		}
		return source;
	}

	/**
	 * 
	 * @return
	 */
	public static String processDisplayProperties(String displayProperties,
			Object items) {
		if (displayProperties != null)
			return displayProperties;
		StringBuffer result = new StringBuffer();
		List rowsData = new ArrayList();
		if (items instanceof Object[])
			rowsData = AryUtil.AryToDeepList(items);
		else if (items instanceof List)
			rowsData = (List) items;
		else if (items instanceof Set)
			rowsData = AryUtil.AryToDeepList(((Set) items).toArray());
		int rowCount = 0;
		if (rowsData.get(0) instanceof List)
			rowCount = ((List) rowsData.get(0)).size();
		else if (rowsData.get(0) instanceof Object[])
			rowCount = ((Object[]) rowsData.get(0)).length;
		else if (rowsData.get(0) instanceof Set)
			rowCount = ((Set) rowsData.get(0)).size();
		for (int i = 0; i < rowCount; i++) {
			if (i == 0)
				result.append("item[" + i + "]");
			else
				result.append(",item[" + i + "]");
		}
		return result.toString();
	}

	/**
	 * 获取宏@xxx(param,template)中的param值，目前param只提供一个数据对象如#{status}，
	 * 不提供数据对象格式的组合如:#{status}/#{value}
	 * 
	 * @param propertiesMap
	 * @param param
	 * @param rowDataList
	 * @param rowIndex
	 * @return
	 */
	public static HashMap parseMarcoObject(HashMap propertiesMap, Object param,
			List rowDataList, int rowIndex) {
		HashMap result = new HashMap();
		Iterator propertyIter = propertiesMap.keySet().iterator();
		String paramName = "";
		int paramIndex = -1;
		Object data;
		while (propertyIter.hasNext()) {
			paramName = (String) propertyIter.next();
			paramIndex = ((Integer) propertiesMap.get(paramName)).intValue();
			if (param.equals("#[" + paramName + "]"))
				break;
		}
		if (paramIndex == -1)
			data = "";
		else
			data = rowDataList.get(paramIndex);
		result.put("paramValue", data);
		result.put("paramName", paramName);
		return result;
	}

	public static void main(String[] args) {
		StringBuffer queryStr = new StringBuffer();

		queryStr.append("select t.CREATE_DATE,t.COMMENT  ");
		queryStr
				.append(",(select t1.STAFF_NAME from STAFF_INFO t1 where t1.STAFF_ID=t.CREATE_BY) CREATE_BY_NAME  ");
		queryStr
				.append(",(select tt.ORGAN_NAME from ORGAN_INFO tt where tt.ORGAN_ID=(select t2.ORGAN_ID ");
		queryStr
				.append("from STAFF_INFO t2 where t2.STAFF_ID=t.CREATE_BY)) CREATE_ORGAN_NAME    ");
		queryStr
				.append(",(select t3.STAFF_NAME from STAFF_INFO t3 where t3.STAFF_ID=t.PROCESS_BY ) PROCESS_BY_NAME ");
		queryStr
				.append(",t.PROCESS_DATE,t.PROCESS_ATTRIBUTE,t.STATUS from   BGM_MAP_APPLY t where 1=1     ");
		queryStr.append(" and t.CREATE_DATE >='20080101'       ");
		queryStr.append(" and t.CREATE_DATE <='20080805'");

		String tmp = queryStr.toString().replaceAll("\\t", " ");
		System.err.println(StringUtil.getIgnoreCaseMarkEndIndex("select ",
				" from", tmp, 69));
		System.err.println(tmp.indexOf("from (select t1.*"));
		String columns = tmp.substring(StringUtil.IndexOfIgnoreCase(tmp,
				"select ") + 6, StringUtil.getIgnoreCaseMarkEndIndex("select ",
				" from", tmp, 0));
		String rowIdStr = "select rowid=identity(12),"
				+ columns
				+ " into  "
				+ "#tmp "
				+ " "
				+ tmp.substring(StringUtil.getIgnoreCaseMarkEndIndex("select ",
						" from", tmp, 0));

		System.err.println(rowIdStr.replaceAll("\\t", " "));
		String[] cols;
		if (columns.indexOf(",") == -1)
			cols = new String[] { columns };
		else
			cols = columns.split(",");
		StringBuffer result = new StringBuffer();
		int index = 0;
		for (int i = 0; i < cols.length; i++) {
			if (StringUtil.isNotNullAndBlank(cols[i].trim())) {
				if (index != 0)
					result.append(",");
				System.err.println("cols[i]=" + cols[i]);
				if (cols[i].trim().indexOf("(") == 0) {
					System.err.println("###"
							+ cols[i].substring(StringUtil.getMarkEndIndex("(",
									")", cols[i], 0) + 1));
					result.append(cols[i].substring(StringUtil.getMarkEndIndex(
							"(", ")", cols[i], 0) + 1));
				} else if (cols[i].indexOf(".") != -1)
					result.append(cols[i].substring(cols[i].indexOf(".") + 1));
				else
					result.append(cols[i]);

				index++;
			}
		}
		System.err.println(result.toString());
		System.err.println(tmp.indexOf("from BGM_ORGAN_INFO_ADD"));
	}
}
