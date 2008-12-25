/**
 * 
 */
package org.sagacity.framework.dao.model;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
//@Entity
//@Table(name="SYS_TABLE_SEQUENCE")
public class TableSequence implements Serializable {
	// Fields
	/**
	 * 表的序号名称
	 */
	private String sequenceName;
	
	/**
	 * 序号的当前值
	 */
	private Long currentKeyValue;
	
	/**
	 * 当前日期
	 */
	private Long dateValue;

	// Constructors

	/** default constructor */
	public TableSequence() {
	}

	/** minimal constructor */
	public TableSequence(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/** full constructor */
	public TableSequence(String sequenceName, Long currentKeyValue,
			Long dateValue) {
		this.sequenceName = sequenceName;
		this.currentKeyValue = currentKeyValue;
		this.dateValue = dateValue;
	}
	
	//@Id   
	
	//@Column(name="SEQUENCE_NAME", unique=true, nullable=false, length=60)
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	//@Column(name="CURRENT_KEY_VALUE", nullable=false, precision=20, scale=0)
	public Long getCurrentKeyValue() {
		return this.currentKeyValue;
	}

	public void setCurrentKeyValue(Long currentKeyValue) {
		this.currentKeyValue = currentKeyValue;
	}
	
	//@Column(name="DATE_VALUE", precision=8, scale=0)
	public Long getDateValue() {
		return this.dateValue;
	}

	public void setDateValue(Long dateValue) {
		this.dateValue = dateValue;
	}
}
