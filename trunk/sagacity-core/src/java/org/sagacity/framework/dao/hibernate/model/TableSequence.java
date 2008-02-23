/**
 * 
 */
package org.sagacity.framework.dao.hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */

@Entity
@Table(name="SYS_TABLE_SEQUENCE")
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

	@Id
	@Column(name="SEQUENCE_NAME")
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	@Column(name="CURRENT_KEY_VALUE")
	public Long getCurrentKeyValue() {
		return this.currentKeyValue;
	}

	public void setCurrentKeyValue(Long currentKeyValue) {
		this.currentKeyValue = currentKeyValue;
	}
	
	@Column(name="DATE_VALUE")
	public Long getDateValue() {
		return this.dateValue;
	}

	public void setDateValue(Long dateValue) {
		this.dateValue = dateValue;
	}
}
