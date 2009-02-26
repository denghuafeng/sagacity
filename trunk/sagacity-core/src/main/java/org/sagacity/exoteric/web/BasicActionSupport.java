/**
 * 
 */
package org.sagacity.exoteric.web;

import org.sagacity.exoteric.Constants;
import org.sagacity.exoteric.service.UserInfoService;
import org.sagacity.framework.exception.BaseException;
import org.sagacity.framework.web.views.struts2.BaseActionSupport;

/**
 * 基于struts2 的通用Action基类,提供登陆用户信息的获取，用户只需要实现
 * userInfoService中的getCurrentUserInfo方法
 * @author Administrator
 *
 */
public class BasicActionSupport extends BaseActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2676883065445490926L;
	/**
	 * 用户信息业务
	 */
	private UserInfoService userInfoService;

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	/**
	 * 获取当前用户信息
	 * @return
	 * @throws BaseException
	 */
	protected Object getCurrentUser() throws BaseException {
		Object obj = this.request.getSession().getAttribute(
				Constants.USER_INFO_SESSION_ID);
		if (obj != null)
			return obj;
		else {
			Object userInfo = userInfoService.getCurrentUserInfo();
			this.request.getSession().setAttribute(Constants.USER_INFO_SESSION_ID,
					userInfo);
			return userInfo;
		}
	}
}
