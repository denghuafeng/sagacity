/**
 * 
 */
package org.sagacity.exoteric.security.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *@project sagacity-core
 *@description:$<p>用户登陆账号</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:LogonAcctVO.java,Revision:v1.0,Date:2008-12-10 下午04:37:18 $
 */
public class LogonAcctVO implements Serializable {
	/**
	 * 登陆名
	 */
	private String logonName;

	/**
	 * 新验证名
	 */
	private String newLogonName;
	
	/**
	 * 工号,用户唯一编码
	 */
	private String userNo;

	/**
	 * 密码
	 */
	private transient String password;

	/**
	 * 新密码
	 */
	private transient String newPassword;

	/**
	 * 确认密码
	 */
	private transient String confirmPassword;

	/**
	 * 创建人
	 */
	private String createBy;
	
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 修改日期
	 */
	private Date updateDate;

	/**
	 * 是否有效
	 */
	private Long isActive;
	
	/**
	 * @return the logonName
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * @param logonName the logonName to set
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * @return the newLogonName
	 */
	public String getNewLogonName() {
		return newLogonName;
	}

	/**
	 * @param newLogonName the newLogonName to set
	 */
	public void setNewLogonName(String newLogonName) {
		this.newLogonName = newLogonName;
	}

	/**
	 * @return the userNo
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * @param userNo the userNo to set
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the isActive
	 */
	public Long getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	
}
