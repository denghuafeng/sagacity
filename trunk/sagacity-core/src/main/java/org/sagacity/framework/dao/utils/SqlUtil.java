/**
 * 
 */
package org.sagacity.framework.dao.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.sagacity.framework.Constants;
import org.sagacity.framework.utils.AryUtil;
import org.sagacity.framework.utils.StringUtil;

/**
 * @project sagacity-core
 * @description:$ <p>
 *                提供BaseDAOSupport的相关sql数据库部分公共处理方法
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:SQLUtil.java,Revision:v1.0,Date:2008-12-2 下午03:47:21 $
 */
public class SqlUtil {
	/**
	 * 合成数据库in 查询的条件
	 * 
	 * @param conditions
	 *            :数据库in条件的数据集合，可以是POJO List或Object[]
	 * @param colIndex
	 *            :二维数组对应列编号
	 * @param property
	 *            :POJO property
	 * @param isChar
	 *            :in 是否要加单引号
	 * @return:example:1,2,3或'1','2','3'
	 * @throws Exception
	 */
	public static String combineQueryInStr(Object conditions, Integer colIndex,
			String property, boolean isChar) throws Exception {
		StringBuffer conditons = new StringBuffer("");
		String flag = "";
		if (isChar)
			flag = "'";
		int dimen = AryUtil.judgeObjectDimen(conditions);
		switch (dimen) {
		case 0:
			break;
		// 一维数组
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
				ary = AryUtil.twoDimenlistToArray((Collection) conditions);
			else if (conditions instanceof Object[][])
				ary = (Object[][]) conditions;
			else
				ary = AryUtil.twoDimenlistToArray(((Map) conditions).values());

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

	/**
	 * 处理字段信息,提取出sql查询字段，针对sybase分页，如: select t1.id,t2.name,sum() total
	 * result:id,name,total
	 * 
	 * @param columns
	 * @return
	 */
	public static String processColumn(String columns) {
		String[] cols;
		if (columns.indexOf(",") == -1)
			cols = new String[] { columns };
		else
			cols = columns.split(",");
		StringBuffer result = new StringBuffer();
		int index = 0;
		for (int i = 0; i < cols.length; i++) {
			if (StringUtil.isNotNullAndBlank(cols[i].trim())) {
				if (index != 0)
					result.append(",");
				if (cols[i].trim().indexOf("(") == 0)
					result.append(cols[i].substring(StringUtil.getMarkEndIndex(
							"(", ")", cols[i], 0) + 1));
				else if (cols[i].indexOf(".") != -1)
					result.append(cols[i].substring(cols[i].indexOf(".") + 1));
				else
					result.append(cols[i]);

				index++;
			}
		}
		return result.toString();
	}

	/**
	 * 设置参数条件的值
	 * 
	 * @param pst
	 * @param params
	 * @param fromIndex
	 * @param endIndex
	 * @throws SQLException
	 */
	public static void setParamsValue(PreparedStatement pst, Object[] params,
			int fromIndex) throws SQLException {
		if (params != null && params.length > 0) {
			Object param;
			int paramIndex;
			for (int i = 0; i < params.length; i++) {
				param = params[i];
				paramIndex = fromIndex + 1 + i;
				if (param instanceof java.lang.String)
					pst.setString(paramIndex, (String) param);
				else if (param instanceof java.lang.Long)
					pst.setLong(paramIndex, ((Long) param).longValue());
				else if (param instanceof java.lang.Integer)
					pst.setInt(paramIndex, ((Integer) param).intValue());
				else if (param instanceof java.util.Date)
					pst.setDate(paramIndex, new java.sql.Date(
							((java.util.Date) param).getTime()));
				else if (param instanceof java.sql.Date)
					pst.setDate(paramIndex, (java.sql.Date) param);
				else if (param instanceof java.lang.Double)
					pst.setDouble(paramIndex, ((Double) param).doubleValue());
				else if (param instanceof java.lang.Float)
					pst.setFloat(paramIndex, ((Float) param).floatValue());
			}
		}
	}

	/**
	 * 获取查询sql语句中参数的个数
	 * 
	 * @param queryStr
	 * @param skipField
	 * @return
	 */
	public static int getParamsCount(String queryStr, boolean skipField) {
		int paramCnt = 0;
		if (StringUtil.isNullOrBlank(queryStr))
			return paramCnt;

		String tmpStr = queryStr;
		tmpStr = tmpStr.replaceAll("\\n", " ");
		tmpStr = tmpStr.replaceAll("\\t", " ");
		tmpStr = tmpStr.replaceAll("\\r", " ");
		if (skipField) {
			/**
			 * 从第一个from 之后计算,仅通过?方式判断存在缺陷,待优化,正确的做法排除'',"" 和注释部分中的?号
			 */
			tmpStr = tmpStr.substring(StringUtil.IndexOfIgnoreCase(tmpStr,
					" from "));
		}
		// 去除掉空格符
		tmpStr = tmpStr.replaceAll(" ", "");
		String sign = "?";
		if (tmpStr.indexOf("?") == -1)
			sign = ":";
		int index = 0;
		while ((index = tmpStr.indexOf(sign, index + 1)) != -1) {
			paramCnt++;
		}
		return paramCnt;
	}

	/**
	 * 判断条件为null,过滤sql的组合查询条件,如果可能where 后没有任何条件， 建议在where后增加 1=1，程序不对此特征做处理
	 * example: queryStr= select t1.* from xx_table t1 where #[t1.status=?]
	 * #[and t1.auditTime=?]
	 * 
	 * @param queryStr
	 * @param params
	 */
	public static QueryParam filterNullConditions(String queryStr,
			String[] paramsName, Object[] paramsValue) {
		QueryParam sqlParam = new QueryParam();
		// 判断是否需要过滤处理
		if (queryStr != null
				&& queryStr.indexOf(Constants.SQL_PSEUDO_START_MARK) != -1) {
			int beginMarkIndex = queryStr
					.lastIndexOf(Constants.SQL_PSEUDO_START_MARK);
			int endMarkIndex = StringUtil.getMarkEndIndex(
					Constants.SQL_PSEUDO_START_MARK,
					Constants.SQL_PSEUDO_END_MARK, queryStr, beginMarkIndex
							+ Constants.SQL_PSEUDO_START_MARK.length());
			String preSql = queryStr.substring(0, beginMarkIndex);
			String markContentSql = queryStr.substring(beginMarkIndex
					+ Constants.SQL_PSEUDO_START_MARK.length(), endMarkIndex);

			int preParamCnt = getParamsCount(preSql, false);
			// 获取#[]中的参数数量
			int paramCnt = getParamsCount(markContentSql, false);
			// #[]中无参数，剔除该部分sql语句
			if (paramCnt == 0)
				queryStr = queryStr.substring(0, beginMarkIndex)
						+ " "
						+ queryStr.substring(endMarkIndex
								+ Constants.SQL_PSEUDO_END_MARK.length());
			// 从最后一个#[]中的参数开始处理
			for (int i = preParamCnt; i < preParamCnt + paramCnt; i++) {
				// 参数值为null，剔除#[]部分内容，同时将参数从数组中剔除
				if (paramsValue[i] == null) {
					// sql中剔除最后部分的#[]内容
					queryStr = queryStr.substring(0, beginMarkIndex)
							+ " "
							+ queryStr.substring(endMarkIndex
									+ Constants.SQL_PSEUDO_END_MARK.length());
					// 重新构造参数条件数组
					paramsName = (String[]) subArray(paramsName, preParamCnt,
							paramCnt);
					paramsValue = subArray(paramsValue, preParamCnt, paramCnt);
					break;
				}

				// 最后一个参数,表示没有为null的参数,去除"#[","]"符号
				if (i == preParamCnt + paramCnt - 1) {
					String endSql = queryStr.substring(endMarkIndex + 1);
					queryStr = queryStr.substring(0, beginMarkIndex) + " ";

					// 去除like ?部分
					if (StringUtil.matchs(markContentSql.toLowerCase(),
							"\\s+like\\s+\\?")) {
						queryStr += markContentSql.replace("?",
								"\'"
										+ (paramsValue[i].toString().indexOf(
												"%") != -1 ? paramsValue[i]
												: "%" + paramsValue[i] + "%")
										+ "\'");
						paramsName = (String[]) subArray(paramsName, i, 1);
						paramsValue = subArray(paramsValue, i, 1);
					} else if (paramsName != null
							&& paramsName.length != 0
							&& StringUtil.matchs(markContentSql.toLowerCase(),
									"\\s+like\\s+\\:" + paramsName[i])) {
						queryStr += markContentSql.replace(":" + paramsName[i],
								"\'"
										+ (paramsValue[i].toString().indexOf(
												"%") != -1 ? paramsValue[i]
												: "%" + paramsValue[i] + "%")
										+ "\'");
						paramsName = (String[]) subArray(paramsName, i, 1);
						paramsValue = subArray(paramsValue, i, 1);
						// // 去除in (?)部分
					} else if (StringUtil.matchs(markContentSql.toLowerCase(),
							"\\s+in\\s+\\(\\s*\\?\\s*\\)")) {
						queryStr += markContentSql.replace("?", paramsValue[i]
								.toString());
						paramsName = (String[]) subArray(paramsName, i, 1);
						paramsValue = subArray(paramsValue, i, 1);
					} else if (paramsName != null
							&& paramsName.length != 0
							&& StringUtil.matchs(markContentSql.toLowerCase(),
									"\\s+in\\s+\\(\\s*\\:" + paramsName[i]
											+ "\\s*\\)")) {
						queryStr += markContentSql.replace(":" + paramsName[i],
								paramsValue[i].toString());
						paramsName = (String[]) subArray(paramsName, i, 1);
						paramsValue = subArray(paramsValue, i, 1);
					}

					else
						queryStr += markContentSql;
					queryStr += " " + endSql;
				}
			}
			// 递归检查
			return filterNullConditions(queryStr, paramsName, paramsValue);
		}
		// 没有#[]则将结果以QueryParam 对象返回
		sqlParam.setQueryStr(queryStr);
		sqlParam.setParamsName(paramsName);
		sqlParam.setParamsValue(paramsValue);
		return sqlParam;

	}

	/**
	 * 判断查询语句是query命名还是直接就是查询sql
	 * 
	 * @param queryStr
	 * @return
	 */
	public static boolean isNamedQuery(String queryStr) {
		if (StringUtil.IndexOfIgnoreCase(queryStr.trim(), "from", 0) != -1
				&& (queryStr.trim().indexOf(" ") != -1
						|| queryStr.trim().indexOf("\\t") != -1
						|| queryStr.trim().indexOf("\\n") != -1 || queryStr
						.trim().indexOf("\\r") != -1))
			return false;
		return true;

	}

	/**
	 * 替换换行、回车、tab符号
	 * 
	 * @param queryStr
	 * @return
	 */
	public static String clearMistyChars(String queryStr) {
		queryStr = queryStr.replaceAll("\\r", " ");
		queryStr = queryStr.replaceAll("\\t", " ");
		queryStr = queryStr.replaceAll("\\n", " ");
		return queryStr;
	}

	/**
	 * 处理查询语句，如果是NamedQuery则将具体对应的sqlStr提取出来返回
	 * 
	 * @param queryStr
	 * @param session
	 * @return
	 */
	public static String processQuery(String queryStr, Session session) {
		String realQueryStr;
		if (isNamedQuery(queryStr)) {
			Query query = session.getNamedQuery(queryStr);
			realQueryStr = query.getQueryString();
			query = null;
			// session.clear();
		} else
			realQueryStr = queryStr;
		realQueryStr = clearMistyChars(realQueryStr);
		return realQueryStr;
	}

	/**
	 * 剔除对象数组中的部分数据,简单采用List remove方式实现
	 * 
	 * @param targetAry
	 * @param begin
	 * @param length
	 * @return
	 */
	public static Object[] subArray(Object[] targetAry, int begin, int length) {
		if (targetAry == null || targetAry.length == 0)
			return null;
		if (begin + length > targetAry.length)
			return targetAry;
		List sourceList = new ArrayList();
		for (int i = 0; i < targetAry.length; i++)
			sourceList.add(targetAry[i]);

		for (int i = begin + length - 1; i >= begin; i--)
			sourceList.remove(i);

		return sourceList.toArray();
	}

	public static class QueryParam {
		private String queryStr;
		private String[] paramsName;
		private Object[] paramsValue;

		public String getQueryStr() {
			return queryStr;
		}

		public void setQueryStr(String queryStr) {
			this.queryStr = queryStr;
		}

		public String[] getParamsName() {
			return paramsName;
		}

		public void setParamsName(String[] paramsName) {
			this.paramsName = paramsName;
		}

		public Object[] getParamsValue() {
			return paramsValue;
		}

		public void setParamsValue(Object[] paramsValue) {
			this.paramsValue = paramsValue;
		}

	}

	public static void main(String[] args) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select *  ");
		queryStr.append("from OA_CAR_REGIST t ");
		queryStr.append("where 1=1 ");
		queryStr.append("      #[and t.REGIST_DATE>=? and t.REGIST_DATE<= ? ]");
		queryStr.append("      #[and t.CAR_MODE like ? ] ");
		queryStr.append("      #[and t.IS_ACTIVE=?]");

		Object[] paramsValue = new Object[] { null, null, null, "1" };
		QueryParam sqlParam = SqlUtil.filterNullConditions(queryStr.toString(),
				null, paramsValue);
		System.err.println("tmp=" + sqlParam.getQueryStr());
		for (int i = 0; i < sqlParam.getParamsValue().length; i++)
			System.err.println(sqlParam.getParamsValue()[i]);
		/*
		 * System.err.println(SqlUtil.isNamedQuery(" from table")); Object[]
		 * tmpAry = new Object[] { "1", "2", "3", "4", "5" }; tmpAry =
		 * SqlUtil.subArray(tmpAry, 2, 3); for (int i = 0; i < tmpAry.length;
		 * i++) System.err.println("tmpAry[i]=" + tmpAry[i]);
		 */

	}
}
