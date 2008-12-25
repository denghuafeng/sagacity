/**
 * 
 */
package org.sagacity.framework.dao.model.j4;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
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
	
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	public Long getCurrentKeyValue() {
		return this.currentKeyValue;
	}

	public void setCurrentKeyValue(Long currentKeyValue) {
		this.currentKeyValue = currentKeyValue;
	}
	
	public Long getDateValue() {
		return this.dateValue;
	}

	public void setDateValue(Long dateValue) {
		this.dateValue = dateValue;
	}
}
