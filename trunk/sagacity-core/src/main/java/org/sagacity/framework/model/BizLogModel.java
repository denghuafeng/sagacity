/**
 * 
 */
package org.sagacity.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class BizLogModel implements Serializable {
	/**
	 * 事务编号
	 */
	private Long transId;

	/**
	 * 应用代码
	 */
	private String appCode;

	/**
	 * 模块编号
	 */
	private String moduleNo;

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 功能编号
	 */
	private String funcNo;

	/**
	 * 功能名称
	 */
	private String funcName;

	/**
	 * 事务涉及对象:一般指相关业务的单据编号等 如:新增采购定单，那事务对象就是采购定单的编号 如果是多个事务对象，用逗号分割
	 */
	private String referenceObj;

	/**
	 * 操作人
	 */
	private String operateBy;

	/**
	 * 操作日期
	 */
	private Date operateDate;

	/**
	 * 业务描述
	 */
	private String bizDecs;

	/**
	 * 业务终端的IP
	 */
	private String terminalIP;

	public BizLogModel(String moduleNo, String funcName, String funcNo,
			String referenceObj, String bizDecs, String terminalIP,
			String operateBy) {
		this.moduleNo = moduleNo;
		this.funcName = funcName;
		this.funcNo = funcNo;
		this.terminalIP = terminalIP;
		this.referenceObj = referenceObj;
		this.bizDecs = bizDecs;
		this.operateBy = operateBy;
	}

	/**
	 * @return the terminalIP
	 */
	public String getTerminalIP() {
		return terminalIP;
	}

	/**
	 * @param terminalIP
	 *            the terminalIP to set
	 */
	public void setTerminalIP(String terminalIP) {
		this.terminalIP = terminalIP;
	}

	/**
	 * @return the transId
	 */
	public Long getTransId() {
		return transId;
	}

	/**
	 * @param transId
	 *            the transId to set
	 */
	public void setTransId(Long transId) {
		this.transId = transId;
	}

	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode
	 *            the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return the moduleNo
	 */
	public String getModuleNo() {
		return moduleNo;
	}

	/**
	 * @param moduleNo
	 *            the moduleNo to set
	 */
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the funcNo
	 */
	public String getFuncNo() {
		return funcNo;
	}

	/**
	 * @param funcNo
	 *            the funcNo to set
	 */
	public void setFuncNo(String funcNo) {
		this.funcNo = funcNo;
	}

	/**
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}

	/**
	 * @param funcName
	 *            the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	/**
	 * @return the operateBy
	 */
	public String getOperateBy() {
		return operateBy;
	}

	/**
	 * @param operateBy
	 *            the operateBy to set
	 */
	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	/**
	 * @return the operateDate
	 */
	public Date getOperateDate() {
		return operateDate;
	}

	/**
	 * @param operateDate
	 *            the operateDate to set
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * @return the bizDecs
	 */
	public String getBizDecs() {
		return bizDecs;
	}

	/**
	 * @param bizDecs
	 *            the bizDecs to set
	 */
	public void setBizDecs(String bizDecs) {
		this.bizDecs = bizDecs;
	}

	/**
	 * @return the referenceObj
	 */
	public String getReferenceObj() {
		return referenceObj;
	}

	/**
	 * @param referenceObj
	 *            the referenceObj to set
	 */
	public void setReferenceObj(String referenceObj) {
		this.referenceObj = referenceObj;
	}
}
