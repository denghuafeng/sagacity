/**
 * 
 */
package org.sagacity.framework.dao.model;

import java.io.Serializable;
import java.util.List;

/**
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TableMeta.java,Revision:v1.0,Date:2009-2-24 下午05:21:44 $
 */
public class TableMeta implements Serializable {
	private String tableName;
	
	private String tableAlias;
	
	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	private String tableRemark;
	
	private String schema;
	
	
	/**
	A 
	Alias 
	L 
	Logical file 
	M 
	Materialized query table 
	P 
	Physical file 
	T 
	Table 
	V 
	View 
	**/
	private String tableType;
	
	private String owner;
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	private List colMetas;
	
	

	public List getColMetas() {
		return colMetas;
	}

	public void setColMetas(List colMetas) {
		this.colMetas = colMetas;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableRemark() {
		return tableRemark;
	}

	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
