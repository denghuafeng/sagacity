/**
 * 
 */
package org.sagacity.framework.exception;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          表达式计算的异常
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExpressionException.java,Revision:v1.0,Date:Aug 26, 2008
 *          10:37:48 AM $
 */
public class ExpressionException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 931442182800671386L;

	public ExpressionException(String msg) {
		super(msg);
	}
	
	public ExpressionException(String msg,Exception e) {
		super(msg,e);
	}
}
