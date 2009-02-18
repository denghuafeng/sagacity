/**
 * 
 */
package org.sagacity.tools.excel.model;

import java.io.Serializable;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExcelModel.java,Revision:v1.0,Date:Jul 29, 2008 2:30:14 PM $
 */
public class ExcelModel implements Serializable {
	/**
	 * excel文件
	 */
	private String excelFile;
	
	/**
	 * excel文件sheet
	 */
	private String sheet;
	
	/**
	 * 开始行
	 */
	private int beginRow = 0;

	/**
	 * 截止行
	 */
	private int endRow = -1;
	
	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	/**
	 * 行的第一个为空则终止
	 */
	private boolean rowEmptyEnd=true;

	/**
	 * @return the excelFile
	 */
	public String getExcelFile() {
		return excelFile;
	}

	/**
	 * @param excelFile the excelFile to set
	 */
	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}

	/**
	 * @return the beginRow
	 */
	public int getBeginRow() {
		return beginRow;
	}

	/**
	 * @param beginRow the beginRow to set
	 */
	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return the rowEmptyEnd
	 */
	public boolean isRowEmptyEnd() {
		return rowEmptyEnd;
	}

	/**
	 * @param rowEmptyEnd the rowEmptyEnd to set
	 */
	public void setRowEmptyEnd(boolean rowEmptyEnd) {
		this.rowEmptyEnd = rowEmptyEnd;
	}


	
}
