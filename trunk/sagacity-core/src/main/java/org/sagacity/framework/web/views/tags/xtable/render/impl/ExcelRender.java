/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtable.render.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.sagacity.framework.web.views.tags.xtable.model.Cell;
import org.sagacity.framework.web.views.tags.xtable.model.ExportModel;
import org.sagacity.framework.web.views.tags.xtable.model.XTableModel;
import org.sagacity.framework.web.views.tags.xtable.render.Render;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          excel的导出最终将支持merg功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:ExcelExport.java,Revision:v1.0,Date:May 24, 2008 9:56:03 AM $
 */
public class ExcelRender implements Render {
	private ByteArrayOutputStream data = new ByteArrayOutputStream();

	private HSSFWorkbook xlsFile = null;

	private HSSFSheet xlsSheet = null;

	private HSSFCellStyle titleStyle = null;

	private HSSFCellStyle valueStyle = null;


	/*
	 * (non-Javadoc)
	 * @see org.sagacity.framework.web.views.tags.xtable.render.Render#render(javax.servlet.jsp.PageContext, javax.servlet.jsp.tagext.BodyContent, javax.servlet.jsp.JspWriter, org.sagacity.framework.web.views.tags.xtable.model.XTableModel)
	 */
	public void render(PageContext pageContext, BodyContent bodyContent,
			JspWriter writer, XTableModel tableModel) {
		try {
			ExportModel exportModel = (ExportModel) tableModel.getExportTypes()
					.get("excel");
			xlsFile = new HSSFWorkbook();
			xlsSheet = xlsFile.createSheet();
			xlsFile.setSheetName(0,
					exportModel.getExportFile() == null ? "sheet1"
							: exportModel.getExportFile(),
					HSSFWorkbook.ENCODING_UTF_16);
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
			List title = (List) tableModel.getHeaderList().get(0);
			this.setTitle(title);
			this.addRows(tableModel.getRowsData(), title.size());

			this.download(pageContext, bodyContent,
					exportModel.getExportFile(), exportModel.getExtName());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
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
			cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(titles[var]);
		}
	}

	/**
	 * 
	 * @param rowsData
	 * @param width:表头的宽度
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
				cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
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

	/**
	 * 
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public void download(PageContext pageContext, BodyContent bodyContent,
			String fileName, String extName) {
		try {
			HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			response.setContentType("application/x-download");// 设置为下载application/x-download
			String downFile = (fileName == null ? Long.toString(System
					.currentTimeMillis()) : fileName)
					+ "." + extName;
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(downFile, "UTF-8"));
			OutputStream out = response.getOutputStream();

			xlsFile.write(out);
			out.flush();
			out.close();
			pageContext.getResponse().flushBuffer();
			bodyContent.clear();
			bodyContent = pageContext.pushBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
