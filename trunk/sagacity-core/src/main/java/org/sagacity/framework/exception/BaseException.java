/**
 * 
 */
package org.sagacity.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.sagacity.framework.utils.StringUtil;

/**
 * 
 * @project sagacity-core
 * @description:$ <p>
 *                定义框架的基础异常，以后所有异常都必须继承该异常
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseException.java,Revision:v1.0,Date:Oct 19, 2007 10:07:08 AM $
 */
public class BaseException extends Exception {
	protected Throwable rootCause = null;

	/**
	 * 异常的key
	 */
	private String errorKey;

	/**
	 * 异常的提示信息
	 */
	private String errorMessage = null;

	/**
	 * 错误信息中的参数
	 */
	protected String[] errorArgs = null;

	/**
	 * 异常数组
	 */
	private List exceptions = new ArrayList();

	public BaseException() {
		super();
	}

	public BaseException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public BaseException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BaseException(String errorMessage, Exception e) {
		super(e);
		this.errorMessage = errorMessage;
	}

	public BaseException(String errorKey, String errorArg) {
		this.errorKey = errorKey;
		if (StringUtil.isNotNullAndBlank(errorArg))
			this.errorArgs = new String[] { errorArg };
	}

	public BaseException(String errorKey, String[] errorArgs) {
		this.errorKey = errorKey;
		this.errorArgs = errorArgs;
	}

	public BaseException(String errorKey, String errorArg, Exception e) {
		super(e);
		this.errorKey = errorKey;
		if (StringUtil.isNotNullAndBlank(errorArg))
			this.errorArgs = new String[] { errorArg };
	}

	public BaseException(String errorKey, String[] errorArgs, Exception e) {
		super(e);
		this.errorKey = errorKey;
		this.errorArgs = errorArgs;
	}

	public void addException(BaseException e) {
		exceptions.add(e);
	}

	public List getExceptions() {
		return this.exceptions;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getRootCause() != null) {
			this.getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public String getErrorKey() {
		return this.errorKey;
	}

	public String[] getErrorArgs() {
		return this.errorArgs;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
