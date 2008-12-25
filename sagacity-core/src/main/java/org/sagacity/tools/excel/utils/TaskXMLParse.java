/**
 * 
 */
package org.sagacity.tools.excel.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.Constants;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.model.ConvertModel;
import org.sagacity.tools.excel.model.ExcelModel;
import org.sagacity.tools.excel.model.TaskModel;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TaskXMLParse.java,Revision:v1.0,Date:2008-12-9 下午08:04:37 $
 */
public class TaskXMLParse {
	/**
	 * 定义logger
	 */
	private final static Log logger = LogFactory.getFactory().getLog(
			TaskXMLParse.class);

	/**
	 * 返回配置信息
	 * 
	 * @param filePath
	 * @return
	 */
	public static List parseConfig(File fileName) {
		logger.info("解析任务配置文件:" + fileName);
		List tasks = new ArrayList();
		try {
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader.read(fileName);

			Element root = doc.getRootElement();
			// 数据库设置
			logger.info("数据库配置解析!");
			Element dbconfig = root.element("database");
			System.setProperty(Constants.DB_URL, dbconfig
					.elementTextTrim("url"));
			System.setProperty(Constants.DB_DRIVER, dbconfig
					.elementTextTrim("driver"));
			System.setProperty(Constants.DB_USERNAME, dbconfig
					.elementTextTrim("username"));
			System.setProperty(Constants.DB_PASSWORD, dbconfig
					.elementTextTrim("password"));

			// 解析convert
			parseConvert(root.element("converts"));

			// excel导入任务解析
			Element impTasks = root.element("impTasks");
			Element taskElement;
			Iterator impIter = impTasks.elementIterator("task");

			// 解析导入任务
			if (impIter != null) {
				while (impIter.hasNext()) {
					taskElement = (Element) impIter.next();
					if (taskElement.attributeValue("activeFlag")
							.equalsIgnoreCase("true")) {
						TaskModel task = new TaskModel(Constants.TASK_TYPE_IMP);
						task.setTaskName(taskElement.attributeValue("name"));
						// 表名
						if (taskElement.attribute("table") != null)
							task.setTableName(taskElement
									.attributeValue("table"));

						// 任务处理类
						if (taskElement.attribute("processClass") != null) {
							String processClass = taskElement
									.attributeValue("processClass");
							if (!processClass.equalsIgnoreCase("default")
									&& StringUtil
											.isNotNullAndBlank(processClass))
								task.setProcessClass(processClass);
						}

						// 判断是否在执行前要清空表数据
						if (StringUtil.isNotNullAndBlank(task.getTableName())
								&& taskElement.attribute("preClear") != null) {
							task
									.setClear(new Boolean(taskElement
											.attributeValue("preClear"))
											.booleanValue());
						} else
							task.setClear(false);

						// 判断是否设置了批处理记录数量
						if (taskElement.attribute("batchSize") != null) {
							task.setBatchSize(Integer.parseInt(taskElement
									.attributeValue("batchSize")));
						}

						// excel文件信息
						Iterator excelIter = taskElement.element("excels")
								.elementIterator("excel");
						if (excelIter != null) {
							List excelFiles = new ArrayList();
							Element excelElement;
							while (excelIter.hasNext()) {
								excelElement = (Element) excelIter.next();
								ExcelModel excelModel = new ExcelModel();

								// 自动终止
								if (excelElement.attribute("autoEnd") != null) {
									excelModel.setRowEmptyEnd(new Boolean(
											excelElement
													.attributeValue("autoEnd"))
											.booleanValue());
								}
								excelModel.setExcelFile(excelElement
										.getTextTrim());

								// 设置开始行
								if (excelElement.attribute("beginRow") != null) {
									excelModel
											.setBeginRow(Integer
													.parseInt(excelElement
															.attributeValue("beginRow")));
								} else
									excelModel.setBeginRow(2);

								// 结束行
								if (excelElement.attribute("endRow") != null) {
									if (StringUtil.isNullOrBlank(excelElement
											.attributeValue("endRow")))
										excelModel.setEndRow(-1);
									else
										excelModel
												.setEndRow(Integer
														.parseInt(excelElement
																.attributeValue("endRow")));
								} else
									excelModel.setEndRow(-1);
								excelFiles.add(excelModel);
							}
							task.setExcelModels(excelFiles);
						}
						// 字段跟excel col对应设置
						Iterator colIter = taskElement.element("cols")
								.elementIterator("col");
						List colList = new ArrayList();
						int index = 0;
						StringBuffer fields = new StringBuffer();
						Element colElement;
						String subCols;
						Element subColElement;
						String shareType;
						while (colIter.hasNext()) {
							colElement = (Element) colIter.next();
							ColumnModel colModel = new ColumnModel();
							if (colElement.attribute("tableField") != null)
								colModel.setFieldName(colElement
										.attributeValue("tableField"));

							// 数据类型，如果为空或""则默认为String
							if (colElement.attribute("type") != null)
								colModel.setDataType(colElement
										.attributeValue("type"));
							else
								colModel.setDataType("String");

							// 转换器
							if (colElement.attribute("convert") != null)
								colModel.setConvert(colElement
										.attributeValue("convert"));
							// 空为null
							if (colElement.attribute("emptyNull") != null)
								colModel.setEmptyNull(colElement
										.attributeValue("emptyNull"));
							else
								colModel.setEmptyNull("true");

							// 字段对应的excel列
							if (colElement.attribute("excelCol") != null)
								colModel.setColIndex(Integer
										.parseInt(colElement
												.attributeValue("excelCol")));
							else
								colModel.setColIndex(-1);

							// 格式化样式
							if (colElement.attribute("format") != null)
								colModel.setFormat(colElement
										.attributeValue("format"));
							// 默认值
							if (colElement.attribute("default") != null) {
								colModel.setDefaultValue(colElement
										.attributeValue("default"));
							}

							// 字段是否针对字表
							if (colElement.attribute("forSub") != null) {
								shareType = colElement.attributeValue("forSub");
								colModel.setForSub(true);

								// 有字表的处理
								if (colElement.elements() != null
										&& !colElement.elements().isEmpty()) {
									subColElement = colElement
											.element("subsql");
									colModel.setSubSql(subColElement
											.getTextTrim());

									// 子表的sequence
									if (subColElement.attribute("sequence") != null) {
										colModel
												.setSequence(ConvertUtil
														.getConvert(subColElement
																.attributeValue("sequence")));
										if (subColElement.attribute("type") != null) {
											colModel.setSeqType(subColElement
													.attributeValue("type"));
										}
									}
									// 子表的列
									if (subColElement.attribute("cols") != null) {
										subCols = subColElement
												.attributeValue("cols");
										String[] subs = subCols.split(",");
										int[] subcolIndex = new int[subs.length];
										for (int i = 0; i < subcolIndex.length; i++) {
											subcolIndex[i] = Integer
													.parseInt(subs[i]);
										}
										colModel.setSubCol(subcolIndex);
									}
								}
							} else {
								if (StringUtil.isNotNullAndBlank(colModel
										.getFieldName())) {
									if (index != 0)
										fields.append(",");
									fields.append(colModel.getFieldName());
									index++;
								}
							}
							colList.add(colModel);
						}
						task.setCols(colList);
						task.setFields(fields.toString());
						// 执行sql
						task.setExecutSql(taskElement.elementTextTrim("sql"));

						// 解析任务前置处理
						if (taskElement.element("before") != null) {
							Iterator beforeIter = taskElement.element("before")
									.elementIterator("value");
							if (beforeIter != null) {
								List beforeSqlList = new ArrayList();
								Element beforeElement;
								while (beforeIter.hasNext()) {
									beforeElement = (Element) beforeIter.next();
									if (beforeElement.attributeValue(
											"activeFlag").equalsIgnoreCase(
											"true"))
										beforeSqlList.add(beforeElement
												.getTextTrim());
								}
								task.setBeforeSql(beforeSqlList);
							}
						}

						// 解析任务后置处理
						if (taskElement.element("after") != null) {
							Iterator afterIter = taskElement.element("after")
									.elementIterator("value");
							if (afterIter != null) {
								List afterSqlList = new ArrayList();
								Element afterElement;
								while (afterIter.hasNext()) {
									afterElement = (Element) afterIter.next();
									if (afterElement.attributeValue(
											"activeFlag").equalsIgnoreCase(
											"true"))
										afterSqlList.add(afterElement
												.getTextTrim());
								}
								task.setAfterSql(afterSqlList);
							}
						}
						tasks.add(task);
					}
				}

			} else
				logger.info("Excel导入任务为空!");
			logger.info("任务配置文件解析成功!");
		} catch (DocumentException de) {
			de.printStackTrace();
			logger.error("解析任务配置文件失败!请检查配置文件的正确性!");
			logger.error(de.getMessage());
		}
		return tasks;
	}

	/**
	 * 
	 * @param element
	 */
	private static void parseConvert(Element element) {
		if (element == null)
			return;
		Iterator iter = element.elementIterator("convert");
		Element convertElement;
		Element paramElement;
		while (iter.hasNext()) {
			convertElement = (Element) iter.next();
			ConvertModel convertModel = new ConvertModel();
			convertModel.setName(convertElement.attributeValue("name"));
			convertModel
					.setProcessClass(convertElement.attributeValue("class"));
			Iterator params = convertElement.elementIterator("param");
			List paramList = new ArrayList();
			if (params != null) {
				while (params.hasNext()) {
					paramElement = (Element) params.next();
					paramList.add(paramElement.getTextTrim());
				}
			}
			convertModel.setParams(paramList);
			ConvertUtil.put(convertModel);
		}
	}
}
