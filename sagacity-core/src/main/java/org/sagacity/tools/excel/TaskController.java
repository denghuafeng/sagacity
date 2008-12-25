/**
 * 
 */
package org.sagacity.tools.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.tools.excel.model.Result;
import org.sagacity.tools.excel.model.TaskModel;
import org.sagacity.tools.excel.task.ITask;
import org.sagacity.tools.excel.utils.TaskXMLParse;

/**
 * @project abchina
 * @description:$ <p>
 *                excel任务控制调度主程序
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:DBExcel.java,Revision:v1.0,Date:Jul 29, 2008 11:07:07 AM $
 */
public class TaskController {
	/**
	 * 日志参数定义文件
	 */
	private final static String logFile = "/com/abchina/tools/excel/dbexcellog.properties";

	private final Log logger;

	/**
	 * 是否执行的标志
	 */
	private boolean isRunFlag = true;

	/**
	 * 任务列表
	 */
	private List tasks;

	public TaskController() {
		this.loadEnv();
		logger = LogFactory.getFactory().getLog(getClass());
		logger
				.info("=======================         系统提示           ==================================");
		logger
				.info("Copyright @2008 AGRICULTURAL BANK OF CHINA, All Rights Reserved 中国农业银行宁波市分行");
		logger
				.info("======================= 项目实施方上海南天(陈仁飞倾情提供) =============================");
		logger
				.info("-----------------------------------------------------------------------------------");
	}

	/**
	 * 解吸任务文件
	 * 
	 * @param taskFile
	 */
	public final void parse(String taskFile) {
		if (taskFile == null) {
			logger.error("taskFile 为空,任务调用方式为:java abchina.jar excelTask.xml");
			isRunFlag = false;
			return;
		}
		// 去掉空格
		taskFile = taskFile.trim();
		if (taskFile.toLowerCase().indexOf(".xml") != taskFile.length() - 4) {
			logger.error("请用xml格式定义任务,任务调用方式为:java abchina.jar ***.xml");
			isRunFlag = false;
			return;
		}
		File tasksFile = new File(taskFile);
		if (!tasksFile.exists()) {
			logger.info("任务文件:" + taskFile + "不存在,请正确定义相关文件");
			isRunFlag = false;
			return;
		}

		// 解析任务
		tasks = TaskXMLParse.parseConfig(tasksFile);
	}

	/**
	 * 任务调度
	 */
	public final void taskInvoke() {
		if (isRunFlag == false)
			return;
		try {
			TaskModel taskModel;
			Result result;
			long beginTime;
			long endTime;
			ITask task;
			for (Iterator iter = tasks.iterator(); iter.hasNext();) {
				beginTime = System.currentTimeMillis();
				taskModel = (TaskModel) iter.next();
				logger.info("===========开始执行任务:" + taskModel.getTaskName()
						+ "==========");
				logger.info("开始执行时间:" + beginTime);
				task = (ITask) Class.forName(taskModel.getProcessClass())
						.newInstance();
				result = task.process(taskModel);
				endTime = System.currentTimeMillis();

				logger.info("执行结果:" + result.getResultFlag() + "  提示:"
						+ result.getMessage());
				logger.info("执行结束时间:" + endTime + " 累计执行秒:"
						+ (new Float(endTime - beginTime)).floatValue() / 1000);
				logger.info("===========结束任务:" + taskModel.getTaskName()
						+ "==========");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("任务调度失败!", e.fillInStackTrace());
		}
	}

	/**
	 * 加载环境
	 */
	private void loadEnv() {
		try {
			Properties props = new Properties();
			InputStream stream = Thread.currentThread().getClass()
					.getResourceAsStream(logFile);
			props.load(stream);
			PropertyConfigurator.configure(props);
			System.out.println("log4j properties is loaded");

			/**
			 * 动态加载当前目录下的jar,减少配置 URLClassLoader classLoader = null; List jars =
			 * new ArrayList(); File file = new File("."); if
			 * (file.isDirectory()) { File[] files = file.listFiles(); for (int
			 * i = 0; i < files.length; i++) { if
			 * (files[i].getName().indexOf(".jar") != -1) { jars.add(new
			 * JarFile(files[i])); } } }
			 */
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 获得任务定义文件
		String taskFile;
		// taskFile=args[0];
		long startTime = System.currentTimeMillis();
		taskFile = "D:/workspace/nantian/abchina/docs/excel数据/taskDef.xml";

		// 实例化dbExcel
		TaskController dbExcel = new TaskController();

		// 解析任务
		dbExcel.parse(taskFile);

		// 任务调度执行
		dbExcel.taskInvoke();
		System.out.println("整个任务调度执行时间总计:"
				+ (new Float(System.currentTimeMillis() - startTime))
						.floatValue() / 1000);
	}
}
