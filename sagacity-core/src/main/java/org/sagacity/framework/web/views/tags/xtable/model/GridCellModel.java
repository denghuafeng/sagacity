/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

import java.io.Serializable;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:CellModel.java,Revision:v1.0,Date:May 29, 2008 11:14:32 AM $
 */
public class GridCellModel implements Serializable {
	// /<col title="" value="" align="" type="" format="" expression="">
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 对齐方式
	 */
	private String align = "center";
	
	/**
	 * 类型
	 */
	private String type;

	
	/**
	 * 表达式
	 */
	private String expression;
	
	/**
	 * 宽度
	 */
	private String width;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align
	 *            the align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}
}
