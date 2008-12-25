/**
 * 
 */
package org.sagacity.tools.excel.model;

import java.io.Serializable;
import java.util.List;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          excel导入数据库任务模型
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:TaskModel.java,Revision:v1.0,Date:Jul 29, 2008 11:07:49 AM $
 */
public class TaskModel implements Serializable {
	public TaskModel(String taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * 任务名称
	 */
	private String taskName;
	
	/**
	 * 任务类别:exp,imp两种
	 */
	private String taskType;

	/**
	 * 模板文件
	 */
	private String templateFile;

	/**
	 * 处理类
	 */
	private String processClass="com.abchina.tools.excel.task.impl.DefaultImpTask";

	/**
	 * 输出文件
	 */
	private String outFile;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 是否清空表
	 */
	private boolean isClear = false;

	/**
	 * 批次记录数量，默认100
	 */
	private int batchSize = 100;

	/**
	 * sql语句
	 */
	private String executSql;

	/**
	 * excel文件信息
	 */
	private List excelModels;

	/**
	 * 列
	 */
	private List cols;

	/**
	 * 数据表字段
	 */
	private String fields;
	
	/**
	 * 任务执行前执行的sql
	 */
	private List beforeSql;

	/**
	 * 任务执行完执行的sql
	 */
	private List afterSql;

	

	/**
	 * @return the excelModels
	 */
	public List getExcelModels() {
		return excelModels;
	}

	/**
	 * @param excelModels the excelModels to set
	 */
	public void setExcelModels(List excelModels) {
		this.excelModels = excelModels;
	}

	/**
	 * @return the cols
	 */
	public List getCols() {
		return cols;
	}

	/**
	 * @param cols
	 *            the cols to set
	 */
	public void setCols(List cols) {
		this.cols = cols;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the isClear
	 */
	public boolean isClear() {
		return isClear;
	}

	/**
	 * @param isClear
	 *            the isClear to set
	 */
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize
	 *            the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the executSql
	 */
	public String getExecutSql() {
		return executSql;
	}

	/**
	 * @param executSql
	 *            the executSql to set
	 */
	public void setExecutSql(String executSql) {
		this.executSql = executSql;
	}

	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the templateFile
	 */
	public String getTemplateFile() {
		return templateFile;
	}

	/**
	 * @param templateFile
	 *            the templateFile to set
	 */
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * @return the processClass
	 */
	public String getProcessClass() {
		return processClass;
	}

	/**
	 * @param processClass
	 *            the processClass to set
	 */
	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}

	/**
	 * @return the outFile
	 */
	public String getOutFile() {
		return outFile;
	}

	/**
	 * @param outFile
	 *            the outFile to set
	 */
	public void setOutFile(String outFile) {
		this.outFile = outFile;
	}

	/**
	 * @return the fields
	 */
	public String getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	

	/**
	 * @return the afterSql
	 */
	public List getAfterSql() {
		return afterSql;
	}

	/**
	 * @param afterSql the afterSql to set
	 */
	public void setAfterSql(List afterSql) {
		this.afterSql = afterSql;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the beforeSql
	 */
	public List getBeforeSql() {
		return beforeSql;
	}

	/**
	 * @param beforeSql the beforeSql to set
	 */
	public void setBeforeSql(List beforeSql) {
		this.beforeSql = beforeSql;
	}
	
	
}
