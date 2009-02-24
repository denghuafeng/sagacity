package org.sagacity.framework.dao.model;

/**
 * 
 *@project sagacity-core
 *@description:$<p>数据库表字段属性</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TableColumnMeta.java,Revision:v1.0,Date:2009-2-24 下午03:27:29 $
 */
public class TableColumnMeta implements java.io.Serializable {
	/**
	 * 字段名称
	 */
	private String colName;
	
	/**
	 * 字段类型
	 */
	private int dataType;
	
	/**
	 * 类别名称
	 */
	private String typeName;
	
	/**
	 * 是否为null
	 */
	private boolean nullable;
	
	/**
	 * 字段注释
	 */
	private String colRemark;
	
	
	private int decimalDigits;
	
	/**
	 *小数位
	 */
	private int numPrecRadix;
	
	/**
	 * 字段长度
	 */
	private int colSize;
	
	/**
	 * 字段默认值
	 */
	private String colDefault;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getColRemark() {
		return colRemark;
	}

	public void setColRemark(String colRemark) {
		this.colRemark = colRemark;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNumPrecRadix() {
		return numPrecRadix;
	}

	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

	public int getColSize() {
		return colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	public String getColDefault() {
		return colDefault;
	}

	public void setColDefault(String colDefault) {
		this.colDefault = colDefault;
	}
}