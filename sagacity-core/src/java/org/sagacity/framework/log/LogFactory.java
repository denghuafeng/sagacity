/**
 * 
 */
package org.sagacity.framework.log;

/**
 * @author Administrator
 * 
 */
public class LogFactory {
	private static LogFactory staticLogFactory;
	protected static org.apache.commons.logging.LogFactory jakartaLogFactory;

	private LogFactory() {
	}

	/**
	 * 返回Log工厂的实例 使用单件模式(Sington)实例化，只保持一个实例
	 * 
	 * @return LogFactory
	 */
	public synchronized static LogFactory getFactory() {

		if (staticLogFactory == null) {
			jakartaLogFactory = org.apache.commons.logging.LogFactory
					.getFactory();
			staticLogFactory = new LogFactory();

		}
		return staticLogFactory;
	}

	/**
	 * 获取Log接口
	 * 
	 * @param Class
	 *            需要Log的类
	 * @return Log
	 */
	public Log getLog(Class classC) {
		return new Logger(jakartaLogFactory.getLog(classC));
	}

	/**
	 * 获取Log接口
	 * 
	 * @param className
	 *            String 类的名称
	 * @return Log
	 */
	public Log getLog(String className) {
		return new Logger(jakartaLogFactory.getLog(className));
	}
}
