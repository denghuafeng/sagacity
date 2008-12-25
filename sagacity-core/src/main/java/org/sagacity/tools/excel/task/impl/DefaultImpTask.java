/**
 * 
 */
package org.sagacity.tools.excel.task.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.NumberUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.model.ExcelModel;
import org.sagacity.tools.excel.model.Result;
import org.sagacity.tools.excel.model.TaskModel;
import org.sagacity.tools.excel.task.ITask;
import org.sagacity.tools.excel.utils.DBUtil;
import org.sagacity.tools.excel.utils.ExcelReader;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DefaultImpTask.java,Revision:v1.0,Date:Jul 30, 2008 10:25:26 AM $
 */
public class DefaultImpTask implements ITask {
	private HashMap pstHash = new HashMap();

	/**
	 * 定义日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abchina.tools.excel.task.ITask#process()
	 */
	public Result process(TaskModel taskModel) {
		logger.info("开始处理任务:" + taskModel.getTaskName());
		Result result = new Result();
		result.setResultFlag("true");
		if (taskModel.getExcelModels() == null
				|| taskModel.getExcelModels().isEmpty()) {
			logger.info("任务没有设置Excel文件!");
			result.setMessage("Excel文件没有设置!");
			return result;
		}

		// 读取出文件中的数据
		List excelData = new ArrayList();
		for (Iterator iter = taskModel.getExcelModels().iterator(); iter
				.hasNext();) {
			excelData.addAll(ExcelReader.read((ExcelModel) iter.next(),
					taskModel.getCols()));
		}

		// 判断数据是否为空
		if (excelData == null || excelData.isEmpty()) {
			logger.info("Excel数据记录为空,任务终止执行!");
			return result;
		}

		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		try {
			// 执行before sql
			if (taskModel.getBeforeSql() != null
					&& !taskModel.getBeforeSql().isEmpty()) {
				logger.info("开始任务执行前的清理工作!");
				for (int i = 0; i < taskModel.getBeforeSql().size(); i++)
					DBUtil.executeSql(taskModel.getBeforeSql().get(i)
							.toString());
			}

			// 是否执行数据清空
			if (taskModel.isClear()) {
				logger.info("任务执行前清空表内容!");
				DBUtil.executeSql("truncate table " + taskModel.getTableName());
			}
			conn.setAutoCommit(false);
			String executSql = taskModel.getExecutSql();
			if (StringUtil.isNullOrBlank(executSql)) {
				StringBuffer params = new StringBuffer();
				for (int i = 0; i < taskModel.getCols().size(); i++) {
					if (i != 0)
						params.append(",");
					params.append("?");
				}
				executSql = "insert " + taskModel.getTableName() + "("
						+ taskModel.getFields() + ") values ("
						+ params.toString() + ")";
			} else {
				executSql = StringUtil.replaceStr(executSql, "#tableFields#",
						taskModel.getFields());
			}
			logger.info("开始执行导入任务主SQL=" + executSql);
			pst = conn.prepareStatement(executSql);
			List cols = taskModel.getCols();
			ColumnModel colModel;
			int index = 0;
			List rowData;
			int paramIndex;
			logger.info("=============开始导入数据!===================");
			// 行
			for (int i = 0; i < excelData.size(); i++) {
				index++;
				rowData = (List) excelData.get(i);
				paramIndex = 0;
				// 设置每列
				for (int j = 0; j < cols.size(); j++) {
					colModel = (ColumnModel) cols.get(j);

					// 子表
					if (colModel.isForSub()) {
						if (StringUtil.isNotNullAndBlank(colModel.getSubSql()))
							insertSubTable(colModel, rowData.get(j), rowData,
									cols);
					} else if (StringUtil.isNotNullAndBlank(colModel
							.getFieldName())) {
						paramIndex++;
						// 判断是否是DBDictConvert转换过来的数组，如果是则取出第一个
						if (rowData.get(j) != null
								&& rowData.get(j) instanceof List)
							setParam(((List) rowData.get(j)).get(0), colModel,
									pst, paramIndex);
						else
							setParam(rowData.get(j), colModel, pst, paramIndex);
					}
				}

				pst.addBatch();
				// 每到一定数量的记录做一次提交
				if ((index % taskModel.getBatchSize()) == 0
						|| index == excelData.size()) {
					pst.executeBatch();

					// 子表数据提交
					if (pstHash.size() != 0) {
						Collection psts = pstHash.values();
						Object obj;
						PreparedStatement subPst;
						for (Iterator iter = psts.iterator(); iter.hasNext();) {
							obj = iter.next();
							if (obj != null) {
								subPst = (PreparedStatement) obj;
								subPst.executeBatch();
								// subPst.getConnection().commit();
							}
						}
					}
					System.err
							.println("###############execute batch ####################");
					conn.commit();
				}
			}

			conn.setAutoCommit(true);
			logger.info("============共导入数据:" + index + "条!===============");

			// 执行after sql
			if (taskModel.getAfterSql() != null
					&& !taskModel.getAfterSql().isEmpty()) {
				logger.info("开始任务执行后的清理工作!");
				for (int i = 0; i < taskModel.getAfterSql().size(); i++)
					DBUtil
							.executeSql(taskModel.getAfterSql().get(i)
									.toString());
			}
			logger.info("任务" + taskModel.getTaskName() + "导入已经完成!");
		} catch (SQLException e) {
			e.printStackTrace();
			result.setResultFlag("false");
			logger.error(e.getMessage());
		} finally {
			try {
				if (pstHash.size() != 0) {
					Collection psts = pstHash.values();
					Object obj;
					for (Iterator iter = psts.iterator(); iter.hasNext();) {
						obj = iter.next();
						if (obj != null) {
							((PreparedStatement) obj).close();
						}
					}
				}
				if (pst != null)
					pst.close();
				DBUtil.destory();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("任务导入数据操作异常", e.fillInStackTrace());
			}
		}
		return result;
	}

	/**
	 * 插入子表
	 * 
	 * @param colModel
	 * @param colValue
	 * @param rowData
	 */
	private void insertSubTable(ColumnModel colModel, Object colValue,
			List rowData, List colModels) {
		PreparedStatement pst = null;
		try {
			if (pstHash.get(colModel.getSubSql()) == null) {
				logger.info("执行子表插入:" + colModel.getSubSql());
				pst = DBUtil.getConnection().prepareStatement(
						colModel.getSubSql());
				pstHash.put(colModel.getSubSql(), pst);
			} else
				pst = (PreparedStatement) pstHash.get(colModel.getSubSql());
			List subTableValues;
			if (!(colValue instanceof List)) {
				subTableValues = new ArrayList();
				subTableValues.add(colValue);
			} else
				subTableValues = (List) colValue;
			int index;
			ColumnModel otherCol;
			int seqBegin = 0;
			if (colModel.getSequence() != null)
				seqBegin = 1;
			for (int i = 0; i < subTableValues.size(); i++) {
				if (colModel.getSequence() != null) {
					ColumnModel seqModel = new ColumnModel();
					seqModel.setDataType(colModel.getSeqType());
					seqModel.setEmptyNull("true");
					setParam(colModel.getSequence().convert(null, null,
							seqModel), seqModel, pst, 1);
				}
				for (int j = 0; j < colModel.getSubCol().length; j++) {
					index = colModel.getSubCol()[j];
					otherCol = (ColumnModel) colModels.get(index - 1);
					setParam(rowData.get(index - 1), otherCol, pst, j + 1
							+ seqBegin);
				}
				setParam(subTableValues.get(i), colModel, pst, colModel
						.getSubCol().length
						+ 1 + seqBegin);
				pst.addBatch();
			}
			// pst.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入子表数据操作异常", e.fillInStackTrace());
		}
	}

	/**
	 * 
	 * @param colData
	 * @param colModel
	 * @param pst
	 * @param index
	 */
	private void setParam(Object colData, ColumnModel colModel,
			PreparedStatement pst, int index) throws SQLException {
		String dataType = colModel.getDataType();
		try {
			// 数据为空
			if (colData == null) {
				// 空白为Null
				if (colModel.getEmptyNull().equalsIgnoreCase("true")) {
					if (dataType.equalsIgnoreCase("String"))
						pst.setNull(index, java.sql.Types.VARCHAR);
					else if (dataType.equalsIgnoreCase("Char"))
						pst.setNull(index, java.sql.Types.CHAR);
					else if (dataType.equalsIgnoreCase("Date"))
						pst.setNull(index, java.sql.Types.DATE);
					else if (dataType.equalsIgnoreCase("Double"))
						pst.setNull(index, java.sql.Types.DOUBLE);
					else if (dataType.equalsIgnoreCase("Float"))
						pst.setNull(index, java.sql.Types.FLOAT);
					else if (dataType.equalsIgnoreCase("byte"))
						pst.setNull(index, java.sql.Types.BIT);
					else if (dataType.equalsIgnoreCase("Decimal"))
						pst.setNull(index, java.sql.Types.DECIMAL);
					else if (dataType.equalsIgnoreCase("INTEGER"))
						pst.setNull(index, java.sql.Types.INTEGER);
					else if (dataType.equalsIgnoreCase("Long"))
						pst.setNull(index, java.sql.Types.DECIMAL);
					else if (dataType.equalsIgnoreCase("TIMESTAMP"))
						pst.setNull(index, java.sql.Types.TIMESTAMP);
				} // 空白不为Null设置默认值
				else {
					if (dataType.equalsIgnoreCase("String"))
						pst.setString(index, "");
					else if (dataType.equalsIgnoreCase("Integer"))
						pst.setInt(index, 0);
					else if (dataType.equalsIgnoreCase("Long"))
						pst.setLong(index, 0);
					else if (dataType.equalsIgnoreCase("Decimal"))
						pst.setBigDecimal(index, new BigDecimal(0));
					else if (dataType.equalsIgnoreCase("Float"))
						pst.setFloat(index, 0);
					else if (dataType.equalsIgnoreCase("Double"))
						pst.setDouble(index, 0);
					else if (dataType.equalsIgnoreCase("Date"))
						pst.setDate(index, DateUtil.getSqlDate());
				}
			} else if (StringUtil.isNullOrBlank(colData.toString().trim())
					&& colModel.getEmptyNull().equalsIgnoreCase("true"))
				pst.setObject(index, null);
			else {
				if (dataType.equalsIgnoreCase("String")) {
					if (colData instanceof Date) {
						pst.setString(index, new String(DateUtil.formatDate(
								(Date) colData, colModel.getFormat()).getBytes(
								"ISO-8859-1"), "GBK"));
					} else if (colData instanceof BigDecimal
							&& StringUtil.isNotNullAndBlank(colModel
									.getFormat())) {
						pst.setString(index, new String(NumberUtil.format(
								(BigDecimal) colData, colModel.getFormat())
								.getBytes("ISO-8859-1"), "GBK"));
					} else
						pst.setString(index, new String(colData.toString()
								.getBytes(), "cp936"));
				} else if (dataType.equalsIgnoreCase("Date"))
					pst.setDate(index, DateUtil.getSqlDate(DateUtil
							.parseString(colData.toString())));
				else if (dataType.equalsIgnoreCase("Double")) {
					if (colData instanceof BigDecimal)
						pst.setDouble(index, ((BigDecimal) colData)
								.doubleValue());
					else
						pst.setDouble(index, Double.parseDouble(colData
								.toString()));
				} else if (dataType.equalsIgnoreCase("Float")) {
					if (colData instanceof BigDecimal)
						pst
								.setFloat(index, ((BigDecimal) colData)
										.floatValue());
					else
						pst.setFloat(index, Float
								.parseFloat(colData.toString()));
				} else if (dataType.equalsIgnoreCase("byte"))
					pst.setBytes(index, colData.toString().getBytes());
				else if (dataType.equalsIgnoreCase("Decimal"))
					pst
							.setBigDecimal(index, new BigDecimal(colData
									.toString()));
				else if (dataType.equalsIgnoreCase("TIMESTAMP"))
					pst.setTimestamp(index, Timestamp.valueOf(colData
							.toString()));
				else if (dataType.equalsIgnoreCase("INTEGER")) {
					if (colData instanceof BigDecimal)
						pst.setInt(index, ((BigDecimal) colData).intValue());
					else
						pst.setInt(index, Integer.parseInt(colData.toString()));
				} else if (dataType.equalsIgnoreCase("Long")) {
					if (colData instanceof BigDecimal)
						pst.setLong(index, ((BigDecimal) colData).longValue());
					else
						pst.setLong(index, Long.parseLong(colData.toString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置子表的插入值操作失败!" + e.getMessage());
		}

	}
}
