/**
 * 
 */
package org.sagacity.tools.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.tools.excel.convert.IConvert;
import org.sagacity.tools.excel.model.ColumnModel;
import org.sagacity.tools.excel.model.ExcelModel;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExcelReader.java,Revision:v1.0,Date:Jul 29, 2008 2:27:18 PM $
 */
public class ExcelReader {
	private final static Log logger = LogFactory.getFactory().getLog(
			ExcelReader.class);
	/**
	 * 返回excel数据
	 * 
	 * @param excelModel
	 * @param colList
	 */
	public static List read(ExcelModel excelModel, List colList) {
		logger.info("开始读取Excel文件:" + excelModel.getExcelFile());
		List data = new ArrayList();
		File excelFile = new File(excelModel.getExcelFile());
		if (excelFile.exists()) {
			try {
				FileInputStream fi = new FileInputStream(excelFile);
				Workbook wb = Workbook.getWorkbook(fi);
				Sheet sheet = wb.getSheet(0);
			
				// 开始行
				int beginRow = excelModel.getBeginRow() - 1;

				// 结束行
				int endRow = excelModel.getEndRow() == -1 ? sheet.getRows()
						: excelModel.getEndRow();
				Cell[] row = null;
				ColumnModel colModel = null;
				DateCell dateCell;
				NumberCell numCell;

				int emptyCnt;
				int defaultValueCnt;
				int rowCnt = 0;
				Object content = null;
				IConvert convert=null;
				
				//读取excel数据内容
				while (endRow > beginRow) {
					row = sheet.getRow(beginRow);
					emptyCnt = 0;
					rowCnt++;
					defaultValueCnt = 0;
					List rowData = new ArrayList();
					for (int i = 0; i < colList.size(); i++) {
						colModel = (ColumnModel) colList.get(i);
						if (colModel.getColIndex() == -1) {
							rowData.add(colModel.getDefaultValue());
							defaultValueCnt++;
						} else {
							// 判断值是否为空
							if (row.length + 1 <= (short) colModel
									.getColIndex() - 1
									|| StringUtil
											.isNullOrBlank(row[(short) colModel
													.getColIndex() - 1]
													.getContents())) {
								emptyCnt++;
								if (colModel.getEmptyNull().equalsIgnoreCase(
										"true"))
									content = null;
								else
									content = "";
							} else {
								if (row[(short) colModel.getColIndex() - 1]
										.getType() == jxl.CellType.DATE) {
									dateCell = (DateCell) row[(short) colModel
											.getColIndex() - 1];
									content = dateCell.getDate();
								} else if (row[(short) colModel.getColIndex() - 1]
										.getType() == jxl.CellType.NUMBER) {
									numCell = (NumberCell) row[(short) colModel
											.getColIndex() - 1];
									content = new BigDecimal(numCell.getValue());
								} 
								else {
									content = row[(short) colModel
											.getColIndex() - 1].getContents();
								}
							}
							rowData.add(content);
						}
					}
					
					// 如果整行全是空,而且行为空就终止，则结束excel读取
					if (emptyCnt == colList.size() - defaultValueCnt
							&& excelModel.isRowEmptyEnd())
						break;
					
					//数据转换
					for (int i = 0; i < colList.size(); i++) {
						colModel = (ColumnModel) colList.get(i);
						if (StringUtil.isNotNullAndBlank(colModel
								.getConvert())) {
							convert = ConvertUtil
									.getConvert(colModel.getConvert());
							rowData.set(i, convert.convert(rowData.get(i),rowData,colModel));
						}
					}
					
					data.add(rowData);
					beginRow++;
				}
				logger.info("共读取Excel文件:" + excelModel.getExcelFile() + "共"
						+ rowCnt + "行");
				fi.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				DBUtil.destory();
			}
		}
		return data;
	}
}
