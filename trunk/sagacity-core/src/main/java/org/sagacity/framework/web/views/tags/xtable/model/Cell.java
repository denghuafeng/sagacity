/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.model;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          表格单元信息
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:Cell.java,Revision:v1.0,Date:May 8, 2008 5:07:36 PM $
 */
public class Cell {
	/**
	 * 显示对应properties中的顺序号
	 */
	private int propertiesIndex;

	/**
	 * 样式
	 */
	private String style;

	/**
	 * cell数值
	 */
	private Object value;

	/**
	 * 横向合并量
	 */
	private int colspan = 0;

	/**
	 * 纵向合并量
	 */
	private int rowspan = 0;

	/**
	 * 单元格中的内容
	 */
	private String cellContent;

	/**
	 * 是否合并
	 */
	private boolean isMerg = false;

	/**
	 * @return the isMerg
	 */
	public boolean isMerg() {
		return isMerg;
	}

	/**
	 * @param isMerg
	 *            the isMerg to set
	 */
	public void setMerg(boolean isMerg) {
		this.isMerg = isMerg;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the colspan
	 */
	public int getColspan() {
		return colspan;
	}

	/**
	 * @param colspan
	 *            the colspan to set
	 */
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	/**
	 * @return the rowspan
	 */
	public int getRowspan() {
		return rowspan;
	}

	/**
	 * @param rowspan
	 *            the rowspan to set
	 */
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	/**
	 * @return the cellContent
	 */
	public String getCellContent() {
		return cellContent;
	}

	/**
	 * @param cellContent
	 *            the cellContent to set
	 */
	public void setCellContent(String cellContent) {
		this.cellContent = cellContent;
	}

	/**
	 * @return the propertiesIndex
	 */
	public int getPropertiesIndex() {
		return propertiesIndex;
	}

	/**
	 * @param propertiesIndex
	 *            the propertiesIndex to set
	 */
	public void setPropertiesIndex(int propertiesIndex) {
		this.propertiesIndex = propertiesIndex;
	}
}
