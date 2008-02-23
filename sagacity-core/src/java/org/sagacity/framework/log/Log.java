/**
 * 
 */
package org.sagacity.framework.log;

/**
 * @author Administrator
 * 
 */
public interface Log {
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
	public boolean isDebugEnabled();

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
	public boolean isErrorEnabled();

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
	public boolean isFatalEnabled();

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
	public boolean isInfoEnabled();

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
	public boolean isTraceEnabled();

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
	public boolean isWarnEnabled();

	// -------------------------------------------------------- Logging Methods

	/**
	 * <p>
	 * Log a message with trace log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void trace(Object message);

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
	public void trace(Object message, Throwable t);

	/**
	 * <p>
	 * Log a message with debug log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void debug(Object message);

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
	public void debug(Object message, Throwable t);

	/**
	 * <p>
	 * Log a message with info log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void info(Object message);

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
	public void info(Object message, Throwable t);

	/**
	 * <p>
	 * Log a message with warn log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void warn(Object message);

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
	public void warn(Object message, Throwable t);

	/**
	 * <p>
	 * Log a message with error log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void error(Object message);

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
	public void error(Object message, Throwable t);

	/**
	 * <p>
	 * Log a message with fatal log level.
	 * </p>
	 * 
	 * @param message
	 *            log this message
	 */
	public void fatal(Object message);

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
	public void fatal(Object message, Throwable t);

	/**
	 * Prints this throwable and its backtrace to the standard error stream.
	 */
	public void printStackTrace(Throwable t);

	/**
	 * 
	 * @param ntabStr
	 *            int
	 * @return String
	 */
	public String space(int ntabStr);
}
