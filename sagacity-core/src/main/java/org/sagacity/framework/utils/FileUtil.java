/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 
 *@project sagacity-core
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:FileUtil.java,Revision:v1.0,Date:2008-11-7 下午01:53:21 $
 */
public class FileUtil {
	private String message;

	/**
	 * 压缩文件
	 * 
	 * @param fileNames
	 * @param targetFile
	 * @param filter
	 */
	public static void zip(Object[] fileNames, String targetFile, String filter) {
		try {
			FileOutputStream fileOut = new FileOutputStream(targetFile);
			ZipOutputStream zipOut = new ZipOutputStream(fileOut);
			File entryFile = null;
			for (int i = 0; i < fileNames.length; i++) {
				if (fileNames[i] instanceof File)
					entryFile = (File) fileNames[i];
				else
					entryFile = new File((String) fileNames[i]);
				zip(zipOut, entryFile, entryFile.getName());
			}
			zipOut.flush();
			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	public static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream fileIn = new FileInputStream(f);

			if (fileIn.available() != 0) {
				byte[] buffer = new byte[fileIn.available()];
				while (fileIn.read(buffer) != -1) {
					out.write(buffer);
				}
			}
			out.closeEntry();
			fileIn.close();
		}
	}

	/**
	 * 解压文件:支持文件名称、文件、流三种类型
	 * 
	 * @param zipSrc
	 * @param outputDirectory
	 * @param override
	 *            :true 同名文件覆盖,false 不覆盖保留原文件
	 * @throws IOException
	 */
	public static synchronized void unzip(Object zipSrc,
			String outputDirectory, boolean override) throws IOException {
		if (zipSrc == null || outputDirectory == null)
			throw new IllegalArgumentException("输入参数类型不能为空!");
		File outFile = new File(outputDirectory);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}

		ZipInputStream zis = null;

		if (zipSrc instanceof String)
			zis = new ZipInputStream(new FileInputStream((String) zipSrc));
		else if (zipSrc instanceof File)
			zis = new ZipInputStream(new FileInputStream((File) zipSrc));
		else if (zipSrc instanceof ZipInputStream)
			zis = (ZipInputStream) zipSrc;
		else if (zipSrc instanceof InputStream)
			zis = new ZipInputStream((InputStream) zipSrc);
		else
			throw new IllegalArgumentException("输入参数类型不正确!");
		ZipEntry zipEntry = null;
		try {
			while ((zipEntry = zis.getNextEntry()) != null) {
				if (zipEntry.isDirectory()) {
					// mkdir directory
					String dirName = zipEntry.getName();
					dirName = dirName.substring(0, dirName.length() - 1);

					File dirFile = new File(outFile.getPath() + File.separator
							+ dirName);
					if (dirFile.exists() == false) {
						dirFile.mkdirs();
					}

				} else {
					// unzip file
					File dirFile = new File(outFile.getPath() + File.separator
							+ zipEntry.getName());
					// 文件存在
					if (dirFile.exists() == true) {
						// 重写
						if (override)
							dirFile.delete();
						else
							continue;
					}
					FileOutputStream out = new FileOutputStream(dirFile);
					try {
						int c;
						byte[] by = new byte[1024];
						while ((c = zis.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					} finally {
						out.close();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zis != null)
				zis.close();
		}
	}

	/**
	 * 将文件转到OutputStream
	 * 
	 * @param out
	 * @param fileName
	 */
	public static void putFileInOutStream(OutputStream out, Object fileName)
			throws Exception {
		if (fileName == null || out == null)
			throw new IllegalArgumentException("参数不能为空");
		File outFile = null;
		if (fileName instanceof String)
			outFile = new File((String) fileName);
		else if (fileName instanceof File)
			outFile = (File) fileName;
		else
			throw new IllegalArgumentException(
					"fileName参数类型错误,只提供String and File两个类型!");

		if (outFile.exists()) {
			FileInputStream fileIn = new FileInputStream(outFile);
			byte[] buffer = new byte[fileIn.available()];
			while (fileIn.read(buffer) != -1) {
				out.write(buffer);
			}
			fileIn.close();
			out.flush();
		}
	}

	/**
	 * 将流保存为文�?
	 * 
	 * @param is
	 * @param fileName
	 */
	public static void putInputStreamToFile(InputStream is, String fileName)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		byte[] buffer = new byte[is.available()];
		int length;
		while ((length = is.read(buffer)) != -1) {
			fos.write(buffer, 0, length);
		}
		fos.flush();
		fos.close();
	}

	/**
	 * 将文件保存为�?
	 * 
	 * @param is
	 * @param fileName
	 */
	public static InputStream putFileToInputStream(String fileName)
			throws Exception {
		return new FileInputStream(new File(fileName));
	}

	/**
	 * 将流保存为文�?
	 * 
	 * @param is
	 * @param fileName
	 */
	public static void putByteArrayToFile(byte[] bytes, String fileName)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

	/**
	 * 递归将指定文件夹下面的文件（直到�?低层文件夹）放入数组�?
	 * 
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
			nameList.add(parentFile.getPath());
		}
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.err.println("创建目录操作出错");
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路�? 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			System.err.println("创建目录操作出错");
			e.printStackTrace();
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			System.err.println("创建文件操作出错");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
			}
		} catch (Exception e) {
			System.err.println("删除文件操作失败!");
		}
		return bea;
	}

	/**
	 * 删除文件�?
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路�?
	 * @return
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内�?
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			message = ("删除文件夹操作出�?");
		}
	}

	/**
	 * 删除指定文件夹下�?有文�?
	 * 
	 * @param path
	 *            文件夹完整绝对路�?
	 * @return
	 * @return
	 */
	public boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文�?
				delFolder(path + "/" + tempList[i]);// 再删除空文件�?
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在�?
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文�?
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节�? 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("复制单个文件操作出错");
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目�?
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件�?
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件�?
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("复制整个文件夹内容操作出�?");
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 文件改名
	 * 
	 * @param fileName
	 * @param distFile
	 * @return:1 修改成功,0:修改失败,-1:文件不存�?
	 */
	public static int rename(String fileName, String distFile) {
		synchronized (fileName) {
			File oldFile = new File(fileName);
			if (oldFile.exists()) {
				try {
					oldFile.renameTo(new File(distFile));
					return 1;
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			} else
				return -1;
		}
	}

	/**
	 * 获取文件的摘要，一般应用于检查文件是否被修改过（如在网络传输过程中，下载后取其摘要进行对比）
	 * @param fileName
	 * @param digestType
	 *            :like MD5
	 * @return
	 */
	public static String getFileMessageDigest(String fileName, String digestType) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance(digestType);
			FileInputStream fin = new FileInputStream(fileName);
			DigestInputStream din = new DigestInputStream(fin, md);// 构造输入流
			int b;
			while ((b = din.read()) != -1)
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

	public static void main(String[] args) throws Exception {

		/*
		 * FileUtil.zip(new String[] { "D:/test/1", "D:/test/2" }, "D:/abc.zip",
		 * "");
		 */
		FileUtil.unzip("D:/workspace/calendar4.1.zip", "D:/xtable", true);
		// FileUtil.unzip("F:/struts-2.1.2-lib.zip", "D:/abc");
	}
}
