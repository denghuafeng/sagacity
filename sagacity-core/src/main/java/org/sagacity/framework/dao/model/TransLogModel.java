/**
 * 
 */
package org.sagacity.framework.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 *@project sagacity-core 
 *@description:$<p>交易事务对象模型</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TransLogModel.java,Revision:v1.0,Date:2009-1-12 上午09:55:47 $
 */
public class TransLogModel implements Serializable {

	// Fields

	private Long transId;
	private String appCode;
	private String transType;
	private String refObject;
	private String content;
	private String operator;
	private Date transTime;
	private String terminalIp;
	private Integer resultFlag;
	private Byte isClient;

	// Constructors

	/** default constructor */
	public TransLogModel() {
	}

	/** minimal constructor */
	public TransLogModel(Long transId, String appCode, String transType,
			String content, Date transTime, Integer resultFlag, Byte isClient) {
		this.transId = transId;
		this.appCode = appCode;
		this.transType = transType;
		this.content = content;
		this.transTime = transTime;
		this.resultFlag = resultFlag;
		this.isClient = isClient;
	}

	/** full constructor */
	public TransLogModel(Long transId, String appCode, String transType,
			String refObject, String content, String operator, Date transTime,
			String terminalIp, Integer resultFlag, Byte isClient) {
		this.transId = transId;
		this.appCode = appCode;
		this.transType = transType;
		this.refObject = refObject;
		this.content = content;
		this.operator = operator;
		this.transTime = transTime;
		this.terminalIp = terminalIp;
		this.resultFlag = resultFlag;
		this.isClient = isClient;
	}

	// Property accessors

	public Long getTransId() {
		return this.transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getRefObject() {
		return this.refObject;
	}

	public void setRefObject(String refObject) {
		this.refObject = refObject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTransTime() {
		return this.transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public String getTerminalIp() {
		return this.terminalIp;
	}

	public void setTerminalIp(String terminalIp) {
		this.terminalIp = terminalIp;
	}

	public Integer getResultFlag() {
		return this.resultFlag;
	}

	public void setResultFlag(Integer resultFlag) {
		this.resultFlag = resultFlag;
	}

	public Byte getIsClient() {
		return this.isClient;
	}

	public void setIsClient(Byte isClient) {
		this.isClient = isClient;
	}


}
