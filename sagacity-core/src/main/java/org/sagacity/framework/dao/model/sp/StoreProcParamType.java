/**
 * 
 */
package org.sagacity.framework.dao.model.sp;


/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:StoreProcParamType.java,Revision:v1.0,Date:Jun 6, 2008 1:10:57
 *          PM $
 */
public class StoreProcParamType {
	public String value;

	public StoreProcParamType(String type) {
		this.value = type;
	}

	public boolean equals(Object obj) {
		String value = ((StoreProcParamType) obj).value;
		return this.value.equals(value);
	}
}
