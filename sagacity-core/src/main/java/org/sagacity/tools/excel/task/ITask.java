/**
 * 
 */
package org.sagacity.tools.excel.task;

import org.sagacity.tools.excel.model.Result;
import org.sagacity.tools.excel.model.TaskModel;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ITask.java,Revision:v1.0,Date:Jul 29, 2008 3:14:30 PM $
 */
public interface ITask {
	public Result process(TaskModel taskModel); 
}
