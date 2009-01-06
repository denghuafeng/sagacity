/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.filter.resolver;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.sagacity.framework.web.views.tags.xtable.model.Cell;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;

/**
 *@project sagacity-core
 *@description:$<p>excel文件下载</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:XlsViewResolver.java,Revision:v1.0,Date:2008-12-29 下午02:16:45 $
 */
public class XlsViewResolver implements ViewResolver {
	private HSSFWorkbook xlsFile = null;

	private HSSFSheet xlsSheet = null;

	private HSSFCellStyle titleStyle = null;

	private HSSFCellStyle valueStyle = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sagacity.framework.web.views.tags.xtable.filter.resolver.ViewResolver
	 * #resolveView(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * java.lang.Object)
	 */
	public void resolveView(ServletRequest request, ServletResponse response,
			XTableModel exportModel) throws Exception {
		// TODO Auto-generated method stub
		xlsFile = new HSSFWorkbook();
		xlsSheet = xlsFile.createSheet();
		xlsFile.setSheetName(0, exportModel.getExportFile() == null ? "sheet1"
				: exportModel.getExportFile());
		titleStyle = xlsFile.createCellStyle();
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		titleStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setBorderTop((short) 1);
		titleStyle.setBorderBottom((short) 1);
		titleStyle.setBorderLeft((short) 1);
		titleStyle.setBorderRight((short) 1);
		valueStyle = xlsFile.createCellStyle();
		valueStyle.setBorderTop((short) 1);
		valueStyle.setBorderBottom((short) 1);
		valueStyle.setBorderLeft((short) 1);
		valueStyle.setBorderRight((short) 1);
		List title = (List) exportModel.getHeaderList().get(0);
		this.setTitle(title);
		this.addRows(exportModel.getRowsData(), title.size());
		xlsFile.write(response.getOutputStream());
	}

	/**
	 * 
	 * @param titles
	 */
	private void setTitle(List titles) {
		HSSFRow title = xlsSheet.createRow(0);
		Cell colCell;
		for (int var = 0; var < titles.size(); var++) {
			colCell = (Cell) titles.get(var);
			HSSFCell cell = title.createCell((short) var);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(colCell.getCellContent() == null ? "" : colCell
					.getCellContent());
		}
	}

	/**
	 * 
	 * @param titles
	 */
	private void setTitle(String[] titles) {
		HSSFRow title = xlsSheet.createRow(0);
		for (int var = 0; var < titles.length; var++) {
			HSSFCell cell = title.createCell((short) var);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(titles[var]);
		}
	}

	/**
	 * 
	 * @param rowsData
	 * @param width
	 *            :表头的宽度
	 */
	public void addRows(List rowsData, int width) {
		List row;
		for (int i = 0; i < rowsData.size(); i++) {
			HSSFRow datas = xlsSheet.createRow(i + 1);
			row = (List) rowsData.get(i);
			if (width > row.size())
				width = row.size();
			for (int j = 0; j < width; j++) {
				HSSFCell cell = datas.createCell((short) j);
				cell.setCellStyle(valueStyle);
				if ((row.get(j) instanceof Integer)
						|| (row.get(j) instanceof Double)
						|| (row.get(j) instanceof Float)
						|| (row.get(j) instanceof Integer || (row.get(j) instanceof java.math.BigDecimal))) {
					cell.setCellValue(Double.valueOf(row.get(j).toString())
							.doubleValue());
				} else {
					cell.setCellValue(row.get(j) == null ? "" : row.get(j)
							.toString());
				}
			}
		}
	}

}
