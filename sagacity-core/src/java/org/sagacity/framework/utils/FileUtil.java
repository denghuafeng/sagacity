/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Administrator
 * 
 */
public class FileUtil {
	/**
	 * 取得文件的MD5校验码
	 * @param fileName
	 * @return
	 */
	public static String getFileMD5Digest(String fileName) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			FileInputStream fin = new FileInputStream(fileName);
			DigestInputStream din = new DigestInputStream(fin, md);// 构造输入流

			int index;
			while ((index = din.read()) != -1)
				;
			din.close();
			fin.close();
			byte[] re = md.digest();// 获得消息摘要

			for (int i = 0; i < re.length; i++) {
				result += Integer
						.toHexString((0x000000ff & re[i]) | 0xffffff00)
						.substring(6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 压缩单个文件
	 * 
	 * @param fileName
	 * @param targetFile
	 */
	public static void zipFile(String fileName, String targetFile, boolean isDel) {
		try {
			FileOutputStream fileOut = new FileOutputStream(targetFile);
			ZipOutputStream outputStream = new ZipOutputStream(fileOut);
			File file = new File(fileName);
			FileInputStream fileIn = new FileInputStream(file);
			outputStream.putNextEntry(new ZipEntry(file.getName()));
			byte[] buffer = new byte[fileIn.available()];
			while (fileIn.read(buffer) != -1) {
				outputStream.write(buffer);
			}
			outputStream.closeEntry();
			fileIn.close();
			outputStream.close();
			if (isDel) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 压缩文件路径
	 * @param filePath
	 * @param targetFile
	 * @param filter
	 */
	public static void zipPath(String filePath, String targetFile, String filter) {
		ArrayList fileNames = new ArrayList(); // 存放文件名,并非含有路径的名字
		ArrayList files = new ArrayList(); // 存放文件对象

		try {
			FileOutputStream fileOut = new FileOutputStream(targetFile);
			ZipOutputStream outputStream = new ZipOutputStream(fileOut);

			File rootFile = new File(filePath);
			listFile(rootFile, fileNames, files);
			for (int loop = 0; loop < files.size(); loop++) {
				FileInputStream fileIn = new FileInputStream((File) files
						.get(loop));
				outputStream.putNextEntry(new ZipEntry((String) fileNames
						.get(loop)));
				byte[] buffer = new byte[fileIn.available()];
				while (fileIn.read(buffer) != -1) {
					outputStream.write(buffer);
				}

				outputStream.closeEntry();
				fileIn.close();
			}

			outputStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	/**
	 * 递归将指定文件夹下面的文件（直到最低层文件夹）放入数组中
	 * @param parentFile
	 * @param nameList
	 * @param fileList
	 */
	private static void listFile(File parentFile, List nameList, List fileList) {
		if (parentFile.isDirectory()) {
			File[] files = parentFile.listFiles();
			for (int loop = 0; loop < files.length; loop++) {
				listFile(files[loop], nameList, fileList);
			}
		} else {
			fileList.add(parentFile);
			nameList.add(parentFile.getName());
		}
	}
}
