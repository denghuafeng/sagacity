/**
 * 
 */
package org.sagacity.framework.service.transations;

import java.math.BigDecimal;

import org.sagacity.framework.Constants;
import org.sagacity.framework.dao.hibernate.BaseDAOSupport;
import org.sagacity.framework.dao.model.TransLogModel;
import org.sagacity.framework.exception.BaseException;
import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.filter.WebToolkitFilter;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

/**
 * @project sagacity-core
 * @description:$ <p>
 *                记录应用业务操作的日志，主要记录
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:TransactionLogDAO.java,Revision:v1.0,Date:Jul 2, 2008 9:21:12 AM
 *          $
 */
public class TransLogger extends BaseDAOSupport {

	/**
	 * 记录事务的日志
	 * 
	 * @param bizType
	 *            业务类别
	 * @param bizObj
	 *            业务单据号
	 * @param content
	 *            业务内容
	 * @throws BaseException
	 */
	public void logger(String bizType, String bizObj, String content)
			throws BaseException {
		logger(bizType, bizObj, content, 1);
	}

	/**
	 * 记录事务的日志
	 * 
	 * @param bizType
	 *            业务类别
	 * @param bizObj
	 *            业务单据号
	 * @param content
	 *            业务内容
	 * @param resultFlag
	 *            操作结果标志
	 * @throws BaseException
	 */
	public void logger(String bizType, String bizObj, String content,
			Integer resultFlag) throws BaseException {
		TransLogModel transLogModel = new TransLogModel();
		BigDecimal seq = this.getBigDecimalSequence(TransLogModel.class);
		transLogModel.setTransId(seq.longValue());
		transLogModel.setAppCode(Constants.getKeyValue(Constants.APP_CODE));
		transLogModel.setOperator(this.getCurrentUserId());
		transLogModel.setTerminalIp(WebToolkitFilter.getClientIP());
		transLogModel.setTransTime(DateUtil.getNowTime());
		if (StringUtil.isNotNullAndBlank(transLogModel.getTerminalIp()))
			transLogModel.setIsClient(1);
		else
			transLogModel.setIsClient(0);
		transLogModel.setContent(content);
		transLogModel.setRefObject(bizObj);
		transLogModel.setTransType(bizType);
		if (resultFlag == null)
			transLogModel.setResultFlag(1);
		else
			transLogModel.setResultFlag(resultFlag);
		this.create(transLogModel);
	}

	/**
	 * 获取当前用户id
	 * 
	 * @return
	 */
	private String getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			User user = (User) auth.getPrincipal();
			return user.getUsername();
		}
		return null;
	}
}
