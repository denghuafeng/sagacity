/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.excelutils.ExcelUtils;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>读取Excel工具,主要以excelUtils开源项目为基础</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ExcelUtil.java,Revision:v1.0,Date:2008-12-14 下午10:02:07 $
 */
public class ExcelUtil {
	/**
	 * 读取excel文件
	 * 
	 * @param excelBytes
	 * @param beginRow
	 * @param endRow
	 * @param beginCol
	 * @param endCol
	 * @return
	 */
	public static List read(Object excelData, Integer beginRow, Integer endRow,
			Integer beginCol, Integer endCol) throws Exception {
		if (excelData == null)
			return null;
		InputStream excelInputStream = null;
		List result = null;

		if (excelData instanceof InputStream)
			excelInputStream = (InputStream) excelData;
		else if (excelData instanceof byte[])
			excelInputStream = new ByteArrayInputStream((byte[]) excelData);
		else if (excelData instanceof File)
			excelInputStream = new FileInputStream((File) excelData);
		else if (excelData instanceof String) {
			File excelFile = new File((String) excelData);
			if (!excelFile.exists())
				return null;
			else
				excelInputStream = new FileInputStream(excelFile);
		}
		if (excelInputStream == null)
			return null;

		Workbook wb = Workbook.getWorkbook(excelInputStream);
		Sheet sheet = wb.getSheet(0);
		// excel数据为空
		if (sheet.getRows() <= 1 || sheet.getColumns() < 1)
			return null;
		result = new ArrayList();
		// 开始行
		int realBeginRow = (beginRow == null) ? 1 : beginRow.intValue() - 1;

		// 结束行
		int realEndRow = (endRow == null) ? sheet.getRows() : endRow.intValue();
		// 开始列
		int realBeginCol = (beginCol == null) ? 0 : beginCol.intValue() - 1;

		// 结束列
		int realEndCol = (endCol == null) ? sheet.getColumns() : endCol
				.intValue();
		Cell[] row = null;
		DateCell dateCell;
		NumberCell numCell;
		Object content = null;

		int emptyCnt = 0;
		// 读取excel数据内容
		while (realEndRow > realBeginRow) {
			row = sheet.getRow(realBeginRow);
			List rowData = new ArrayList();
			emptyCnt = 0;
			for (int i = realBeginCol; i < realEndCol; i++) {
				if (i > row.length - 1)
					content = null;
				else {
					if (row[i].getType() == jxl.CellType.DATE) {
						dateCell = (DateCell) row[i];
						content = dateCell.getDate();
					} else if (row[i].getType() == jxl.CellType.NUMBER) {
						numCell = (NumberCell) row[i];
						content = new BigDecimal(numCell.getValue());
					} else {
						content = row[i].getContents();
					}
				}

				rowData.add(content);
				if (content == null || content.toString().equals(""))
					emptyCnt++;
			}
			if (emptyCnt == realEndCol - realBeginCol)
				break;
			result.add(rowData);
			realBeginRow++;
		}
		excelInputStream.close();

		return result;
	}

	/**
	 * 写excel文件
	 * 
	 * @param keys
	 * @param excelDatas
	 * @param templateFile
	 * @param resultFile
	 */
	public static void writer(String[] keys, Object[] excelDatas,
			String templateFile, String resultFile) {
		for (int i = 0; i < keys.length; i++) {
			ExcelUtils.addValue(keys[i], excelDatas[i]);
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(resultFile));
			ExcelUtils.export(templateFile, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写流到excel文件
	 * 
	 * @param keys
	 * @param excelDatas
	 * @param is
	 * @param resultFile
	 */
	public static void writer(String[] keys, Object[] excelDatas,
			InputStream is, String resultFile) {
		// Map contect=new HashMap();
		for (int i = 0; i < keys.length; i++) {
			ExcelUtils.addValue(keys[i], excelDatas[i]);
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(resultFile));
			ExcelUtils.export(is, ExcelUtils.getContext(), out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写excel文件
	 * 
	 * @param excelData
	 * @param out
	 */
	public static void writer(String[] keys, Object[] excelDatas,
			String templateFile, OutputStream out) {
		for (int i = 0; i < keys.length; i++) {
			ExcelUtils.addValue(keys[i], excelDatas[i]);
		}
		try {
			ExcelUtils.export(templateFile, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写excel文件到页面下载
	 * 
	 * @param keys
	 * @param excelDatas
	 * @param templateFile
	 * @param request
	 * @param response
	 */
	public static void writer(String[] keys, Object[] excelDatas,
			Object templateFile, HttpServletRequest request,
			HttpServletResponse response) {
		for (int i = 0; i < keys.length; i++) {
			ExcelUtils.addValue(keys[i], excelDatas[i]);
		}
		response.reset();
		response.setContentType("application/x-download");
		ServletContext context = request.getSession().getServletContext();
		String downFile = Long.toString(System.currentTimeMillis()).concat(
				".xls");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(downFile, "UTF-8"));

			OutputStream out = response.getOutputStream();
			if (templateFile instanceof InputStream)
				ExcelUtils.export((InputStream) templateFile, ExcelUtils
						.getContext(), out);
			else if (templateFile instanceof String) {
				String path = context.getRealPath((String) templateFile);
				ExcelUtils.export(path, out);
			} else if (templateFile instanceof File) {
				FileInputStream fileIs = new FileInputStream(
						(File) templateFile);
				ExcelUtils.export(fileIs, ExcelUtils.getContext(), out);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String[][] datas = { { "001", "张三" }, { "002", "李四" }, { "003", "王五" } };
		String config = "D:/workspace/nantian/abchina/docs/t1.xls";
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(
					"D:/workspace/nantian/abchina/docs/result.xls");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ExcelUtil.writer(new String[] { "datas" }, new Object[] { datas },
				config, fo);
		List data = ExcelUtil.read(new File("D:/test.xls"), new Integer(3),
				null, new Integer(1), new Integer(7));
		for (int i = 0; i < data.size(); i++) {
			System.err.println(((List) data.get(i)).get(6));
		}
	}
}
