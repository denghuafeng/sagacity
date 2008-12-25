package org.sagacity.framework.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.sagacity.exoteric.model.FileModel;

/**
 * @deprecated 此类中的部分功能将在BeanUtil中提供
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:AryUtil.java,Revision:v1.0,Date:2008-10-22 上午10:57:00 $
 */
public class AryUtil {
	public AryUtil() {
	}

	/**
	 * 此转换只适用于一维和二维数组
	 * 
	 * @param arySource
	 *            Object
	 * @return List
	 */
	public static List AryToDeepList(Object arySource) {
		if (null == arySource) {
			System.err.println("the Ary Source is Null");
			return null;
		}

		List resultList = new ArrayList();
		if (arySource instanceof Object[][]) {
			Object[][] aryObject = (Object[][]) arySource;
			if (null != aryObject && 0 < aryObject.length) {
				int rowLength = aryObject[0].length;
				for (int i = 0; i < aryObject.length; i++) {
					List tmpList = new ArrayList();
					for (int j = 0; j < rowLength; j++) {
						tmpList.add(aryObject[i][j]);
					}
					resultList.add(tmpList);
				}
			}
		} else {
			if (arySource instanceof Object[]) {
				Object[] aryObject = (Object[]) arySource;
				if (null != aryObject && 0 < aryObject.length) {
					for (int i = 0; i < aryObject.length; i++)
						resultList.add(aryObject[i]);
				}
			} else {
				System.err
						.println("error define the Array! please sure the array is one or two dimension!");
			}
		}
		return resultList;
	}

	/**
	 * 此转换只适用于一维和二维数组
	 * 
	 * @param arySource
	 *            Object
	 * @return List
	 */
	public static List AryToList(Object arySource) {
		if (null == arySource) {
			System.err.println("the Ary Source is Null");
			return null;
		}
		List resultList = new ArrayList();

		if (arySource instanceof Object[]) {
			Object[] aryObject = (Object[]) arySource;
			if (null != aryObject && 0 < aryObject.length) {
				for (int i = 0; i < aryObject.length; i++)
					resultList.add(aryObject[i]);
			}
		} else {
			System.err
					.println("error define the Array! please sure the array is one or two dimension!");
		}

		return resultList;
	}

	/**
	 * 二维数组排序,写的比较早，建议使用Collections实现排序
	 * 
	 * @param sourceList
	 *            ArrayList
	 */
	public static void sortTwoDimenAryList(ArrayList sourceList,
			String isDescend, int sortRow) {
		boolean descend = false;
		if (sourceList != null && sourceList.size() > 0
				&& (sourceList.get(0) instanceof ArrayList)
				&& ((ArrayList) sourceList.get(0)).size() > sortRow) {
			if (isDescend.equalsIgnoreCase("true"))
				descend = true;

			Object tmpList;
			Object rowObj;
			Object tmpObj;
			for (int i = 0; i < sourceList.size(); i++) {
				rowObj = ((ArrayList) sourceList.get(i)).get(sortRow);
				for (int j = i + 1; j < sourceList.size(); j++) {
					tmpObj = ((ArrayList) sourceList.get(j)).get(sortRow);

					// 小于
					if ((rowObj.toString()).compareTo(tmpObj.toString()) <= 0) {
						// 降序
						if (descend) {
							tmpList = sourceList.get(i);
							sourceList.set(i, sourceList.get(j));
							sourceList.set(j, tmpList);
						}
					}// 大于等于
					else {
						// 升序
						if (!descend) {
							tmpList = sourceList.get(j);
							sourceList.set(j, sourceList.get(i));
							sourceList.set(i, tmpList);
						}
					}
				}
			}
		} else {
			System.err.println("对二维数组排序条件不符合");
		}
	}

	public static List subList(List sourceList, int fromIndex, int toIndex) {
		List result = new ArrayList();
		return result;
	}

	public static Collection subList(Collection sourceCollection,
			int fromIndex, int toIndex) {
		Collection result = new ArrayList();
		return result;
	}

	/**
	 * 获取对象在数组中的位置
	 * 
	 * @param obj
	 *            Object
	 * @param source
	 *            Object[]
	 * @return int
	 */
	public static int indexOfAry(Object obj, Object[] source) {
		int index = -1;
		if (null != source && 0 < source.length) {
			for (int i = 0; i < source.length; i++) {
				if (obj.equals(source[i])) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	private static Object subAry(Object sourceAry, int fromIndex, int toIndex) {
		Object subAry = null;
		if (sourceAry instanceof Collection) {
			ArrayList result = new ArrayList();
		}
		return subAry;
	}

	/**
	 * @todo 二维list转换为数组对象
	 * @param source
	 * @return
	 */
	public static Object[][] twoDimenlistToArray(Collection source) {
		if (source == null || source.isEmpty())
			return null;
		Object[][] result = new Object[source.size()][];
		int index = 0;
		Object obj;
		for (Iterator iter = source.iterator(); iter.hasNext();) {
			obj = iter.next();
			if (obj instanceof Collection)
				result[index] = ((Collection) obj).toArray();
			else if (obj instanceof Object[])
				result[index] = (Object[]) obj;
			else if (obj instanceof Map)
				result[index] = ((Map) obj).values().toArray();
		}
		return result;
	}

	/**
	 * 判断list的维度
	 * 
	 * @param obj
	 * @return
	 */
	public static int judgeObjectDimen(Object obj) {
		int result = 0;
		if (obj == null)
			return result;

		if (obj instanceof Collection || obj instanceof Object[]
				|| obj instanceof Map) {
			result = 1;
			if (obj instanceof Collection) {
				Collection tmp = (Collection) obj;
				if (tmp.isEmpty())
					return result;
				if (((List) obj).get(0) instanceof List)
					result = 2;
			} else if (obj instanceof Object[]) {
				Object[] tmp = (Object[]) obj;
				if (tmp.length == 0)
					return result;
				if (tmp[0] instanceof Object[])
					result = 2;
			} else if (obj instanceof Map) {
				Map tmp = (Map) obj;
				if (tmp.isEmpty())
					return result;
				Object setItem = tmp.values().iterator().next();
				if (setItem instanceof Object[]
						|| setItem instanceof Collection
						|| setItem instanceof Map)
					result = 2;
			}
		}
		return result;
	}

	/**
	 * 合成数据库in 查询的条件
	 * @param conditions:数据库in条件的数据集合，可以是POJO List或Object[]
	 * @param colIndex:二维数组对应列编号
	 * @param property:POJO property
	 * @param isChar:in 是否要加单引号
	 * @return:example:1,2,3 �?'1','2','3'
	 * @throws Exception
	 */
	public static String wrapperDbInStr(Object conditions, Integer colIndex,
			String property, boolean isChar) throws Exception {
		StringBuffer conditons = new StringBuffer("");
		String flag = "";
		if (isChar)
			flag = "'";
		int dimen = judgeObjectDimen(conditions);
		switch (dimen) {
		case 0:
			break;
		// �?维数�?
		case 1: {
			Object[] ary;
			if (conditions instanceof Collection)
				ary = ((Collection) conditions).toArray();
			else if (conditions instanceof Object[])
				ary = (Object[]) conditions;
			else
				ary = ((Map) conditions).values().toArray();

			for (int i = 0; i < ary.length; i++) {
				if (i != 0)
					conditons.append(",");
				conditons.append(flag);
				if (property == null)
					conditons.append(ary[i]);
				else
					conditons.append(BeanUtils.getProperty(ary[i], property));

				conditons.append(flag);
			}
			break;
		}
			// 二维数据
		case 2: {
			Object[][] ary;
			if (conditions instanceof Collection)
				ary = twoDimenlistToArray((Collection) conditions);
			else if (conditions instanceof Object[][])
				ary = (Object[][]) conditions;
			else
				ary = twoDimenlistToArray(((Map) conditions).values());

			for (int i = 0; i < ary.length; i++) {
				if (i != 0)
					conditons.append(",");
				conditons.append(flag);
				if (property == null)
					conditons.append(ary[i][colIndex.intValue()]);
				else
					conditons.append(BeanUtils.getProperty(ary[i][colIndex
							.intValue()], property));
				conditons.append(flag);
			}
			break;
		}

		}
		return conditons.toString();
	}

	public static void main(String[] args) throws Exception {
		List tmpList = new ArrayList();
		FileModel fileModel1 = new FileModel();
		fileModel1.setFileName("test1");
		tmpList.add(fileModel1);

		FileModel fileModel2 = new FileModel();
		fileModel2.setFileName("test2");
		tmpList.add(fileModel2);
		System.err.println(AryUtil.wrapperDbInStr(tmpList, null, "fileName",
				false));
		
		Object[][] tmp = new Object[][] { { "1", "2" }, { "11", "22" },
				{ "12", "23" } };
		System.err.println(AryUtil.wrapperDbInStr(tmp, new Integer(1), null,
				true));

		List tmpA = new ArrayList();
		List tmp1 = new ArrayList();
		tmp1.add("1");
		tmpA.add(tmp1);
		System.err.println("###" + AryUtil.twoDimenlistToArray(tmpA)[0][0]);
	}
}
