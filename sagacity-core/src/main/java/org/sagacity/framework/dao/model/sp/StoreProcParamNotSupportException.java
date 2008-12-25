package org.sagacity.framework.dao.model.sp;

import org.sagacity.framework.exception.BaseException;

/**
 * 
 *@project abchina 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:StoreProcParamNotSupportException.java,Revision:v1.0,Date:Jun 6, 2008 1:12:46 PM $
 */
public class StoreProcParamNotSupportException extends BaseException {
	/**
	 * 
	 */
	public StoreProcParamNotSupportException()
	{
		super("Make Sure Your Params Correctlly! All of Params must be StoreProcParamType type!");
	}

}
