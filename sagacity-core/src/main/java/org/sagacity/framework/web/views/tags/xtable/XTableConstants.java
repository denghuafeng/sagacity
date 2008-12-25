/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable;

import java.io.Serializable;

/**
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:XTableConstants.java,Revision:v1.0,Date:May 31, 2008 1:42:00 PM $
 */
public class XTableConstants implements Serializable {
	/**
	 * 占位符
	 */
	public static final String PLACE_HOLDER="placeHolder";
	
	/**
	 * 是否导出文件
	 */
	public static final String IS_EXPORT_PARAM="xtable_is_export";
	
	/**
	 * 导出文件判断结果
	 */
	public static final String IS_EXPORT_PARAM_RESULT="true";
	
	/**
	 * xtable 模式定义
	 */
	public static final class XTABLE_MODEL{
		/**
		 * 不定宽模式
		 */
		public static final String XTABLE_MODEL_UNLIMITWIDTH="unlimitwidth";
		
		/**
		 * 简单模式
		 */
		public static final String XTABLE_MODEL_SIMPLE="simple";
		
		/**
		 * 默认模式
		 */
		public static final String XTABLE_MODEL_DEFAULT="default";
		
		/**
		 * 高级模式
		 */
		public static final String XTABLE_MODEL_ADVANCED="advanced";
		
	}
}
