/**
 * 
 */
package org.sagacity.tools.excel;

import java.io.Serializable;

/**
 * EXCEL导入到数据库或数据库导出到文件的常量定义
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Constants.java,Revision:v1.0,Date:2008-11-12 下午04:38:47 $
 */
public class Constants implements Serializable {
	public final static String TASK_TYPE_IMP="import";
	public final static String TASK_TYPE_EXP="export";
	public final static String DB_URL="DB_URL";
	public final static String DB_DRIVER="DB_DRIVER";
	public final static String DB_USERNAME="DB_USERNAME";
	public final static String DB_PASSWORD="DB_PASSWORD";
}
