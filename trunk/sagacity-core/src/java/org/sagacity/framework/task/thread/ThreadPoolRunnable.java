/**
 * 
 */
package org.sagacity.framework.task.thread;

/**
 * @Project Name:sagacity
 * @Desciption:
 * @author Administrator
 * @version $Id:ThreadPoolRunnable.java, Revision v1.0, Oct 7, 2007 9:43:05 AM $
 */
public interface ThreadPoolRunnable {
	    public Object[] getInitData();
	    
	    public void runIt(Object thData[]);
}
