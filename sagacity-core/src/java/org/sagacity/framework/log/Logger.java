package org.sagacity.framework.log;

import org.apache.commons.logging.Log;

/**
 * Log实现，主要通过jakarta的Log实现 使用适配器模式将jakarta的Log转换成跟具体实现无关的基础Log
 * 
 * @author <a href="mailto:zhongxuchen@shec.com.cn">$Author: zhongxuchen $</a>
 * @version $Revision: 1.6 $ $Date: 2004/04/16 01:41:24 $
 */

public class Logger implements org.sagacity.framework.log.Log {
	protected Log jakartaLog = null;

	public Logger(org.apache.commons.logging.Log log) {
		jakartaLog = log;
	}

	// ----------------------------------------------------- Logging Properties

	/**
	 * <p>
	 * Is debug logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than debug.
	 * </p>
	 */
	public boolean isDebugEnabled() {
		return jakartaLog.isDebugEnabled();
	}

	/**
	 * <p>
	 * Is error logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than error.
	 * </p>
	 */
	public boolean isErrorEnabled() {
		return jakartaLog.isErrorEnabled();
	}

	/**
	 * <p>
	 * Is fatal logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than fatal.
	 * </p>
	 */
	public boolean isFatalEnabled() {
		return jakartaLog.isFatalEnabled();
	}

	/**
	 * <p>
	 * Is info logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than info.
	 * </p>
	 */
	public boolean isInfoEnabled() {
		return jakartaLog.isInfoEnabled();
	}

	/**
	 * <p>
	 * Is trace logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than trace.
	 * </p>
	 */
	public boolean isTraceEnabled() {
		return jakartaLog.isTraceEnabled();
	}

	/**
	 * <p>
	 * Is warning logging currently enabled?
	 * </p>
	 * 
	 * <p>
	 * Call this method to prevent having to perform expensive operations (for
	 * example, <code>String</code> concatination) when the log level is more
	 * than warning.
	 * </p>
	 */
	public boolean isWarnEnabled() {
		return jakartaLog.isWarnEnabled();
	}

	// -------------------------------------------------------- Logging Methods

	/**
	 * <p>
	 * Log a message with trace log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void trace(Object message) {
		jakartaLog.trace(message);
	}

	/**
	 * <p>
	 * Log an error with trace log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void trace(Object message, Throwable t) {
		jakartaLog.trace(message, t);
	}

	/**
	 * <p>
	 * Log a message with debug log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void debug(Object message) {
		jakartaLog.debug(message);
	}

	/**
	 * <p>
	 * Log an error with debug log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void debug(Object message, Throwable t) {
		jakartaLog.debug(message, t);
	}

	/**
	 * <p>
	 * Log a message with info log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void info(Object message) {
		jakartaLog.info(message);
	}

	/**
	 * <p>
	 * Log an error with info log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void info(Object message, Throwable t) {
		jakartaLog.info(message, t);
	}

	/**
	 * <p>
	 * Log a message with warn log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void warn(Object message) {
		jakartaLog.warn(message);
	}

	/**
	 * <p>
	 * Log an error with warn log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void warn(Object message, Throwable t) {
		jakartaLog.warn(message, t);
	}

	/**
	 * <p>
	 * Log a message with error log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void error(Object message) {
		jakartaLog.error(message);
	}

	/**
	 * <p>
	 * Log an error with error log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void error(Object message, Throwable t) {
		jakartaLog.error(message, t);
	}

	/**
	 * <p>
	 * Log a message with fatal log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void fatal(Object message) {
		jakartaLog.fatal(message);
	}

	/**
	 * <p>
	 * Log an error with fatal log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 * @param t
	 *            log this cause
	 */
	public void fatal(Object message, Throwable t) {
		jakartaLog.fatal(message, t);
	}

	/**
	 * Prints this throwable and its backtrace to the standard error stream.
	 */
	public void printStackTrace(Throwable t) {
		if (t != null) {
			t.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ntabStr
	 *            int
	 * @return String
	 */
	public String space(int ntabStr) {
		StringBuffer result = new StringBuffer();
		while (ntabStr > 0) {
			result.append(ntabStr);
		}
		return result.toString();
	}

}