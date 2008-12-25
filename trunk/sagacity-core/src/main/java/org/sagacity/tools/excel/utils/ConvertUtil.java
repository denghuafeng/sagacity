/**
 * 
 */
package org.sagacity.tools.excel.utils;

import java.util.HashMap;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ConvertModel;

/**
 * @project abchina
 * @description:$ <p>
 *                请在此说明此文件的功能
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ConvertUtil.java,Revision:v1.0,Date:Jul 31, 2008 9:20:46 PM $
 */
public class ConvertUtil {
	private final static Log logger = LogFactory.getFactory().getLog(
			ConvertUtil.class);
	private static HashMap convertMap = new HashMap();

	/**
	 * 
	 * @param convertModel
	 */
	public static void put(ConvertModel convertModel) {
		try {
			IConvert convert = (IConvert) Class.forName(
					convertModel.getProcessClass()).newInstance();
			convert.setParams(convertModel.getParams());
			convertMap.put(convertModel.getName(), convert);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static IConvert getConvert(String key) {
		return (IConvert) convertMap.get(key);
	}
}
