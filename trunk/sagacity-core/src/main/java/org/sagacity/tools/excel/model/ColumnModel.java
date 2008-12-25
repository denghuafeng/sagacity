/**
 * 
 */
package org.sagacity.tools.excel.model;

import java.io.Serializable;

import org.sagacity.tools.excel.convert.IConvert;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ColumnModel.java,Revision:v1.0,Date:Jul 30, 2008 1:24:22 AM $
 */
public class ColumnModel implements Serializable {
	/**
	 * 字段名称
	 */
	private String fieldName;

	/**
	 * excel列序号
	 */
	private int colIndex;

	/**
	 * 数据类型
	 */
	private String dataType;
	
	/**
	 * 空为null
	 */
	private String emptyNull="true";
	
	/**
	 * 样式
	 */
	private String format="";
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 转换器
	 */
	private String convert;
	
	/**
	 * 是否为子表
	 */
	private boolean forSub=false;
	
	/**
	 * 子表执行sql
	 */
	private String subSql;
	
	/**
	 * 子表对应的excel子段
	 */
	private int[] subCol;
	
	/**
	 * sequence 转换器
	 */
	private IConvert sequence;
	
	/**
	 * seq 数据类型
	 */
	private String seqType="Long";
	
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the colIndex
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex
	 *            the colIndex to set
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the emptyNull
	 */
	public String getEmptyNull() {
		return emptyNull;
	}

	/**
	 * @param emptyNull the emptyNull to set
	 */
	public void setEmptyNull(String emptyNull) {
		this.emptyNull = emptyNull;
	}

	/**
	 * @return the convert
	 */
	public String getConvert() {
		return convert;
	}

	/**
	 * @param convert the convert to set
	 */
	public void setConvert(String convert) {
		this.convert = convert;
	}

	/**
	 * @return the forSub
	 */
	public boolean isForSub() {
		return forSub;
	}

	/**
	 * @param forSub the forSub to set
	 */
	public void setForSub(boolean forSub) {
		this.forSub = forSub;
	}

	/**
	 * @return the subSql
	 */
	public String getSubSql() {
		return subSql;
	}

	/**
	 * @param subSql the subSql to set
	 */
	public void setSubSql(String subSql) {
		this.subSql = subSql;
	}

	/**
	 * @return the subCol
	 */
	public int[] getSubCol() {
		return subCol;
	}

	/**
	 * @param subCol the subCol to set
	 */
	public void setSubCol(int[] subCol) {
		this.subCol = subCol;
	}

	/**
	 * @return the sequence
	 */
	public IConvert getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(IConvert sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the seqType
	 */
	public String getSeqType() {
		return seqType;
	}

	/**
	 * @param seqType the seqType to set
	 */
	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}
}
