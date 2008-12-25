/**
 * 
 */
package org.sagacity.framework.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;
import org.sagacity.framework.dao.exception.CreateSequenceException;
import org.sagacity.framework.dao.handler.CriteriaCallbackHandler;
import org.sagacity.framework.dao.handler.RowCallbackHandler;
import org.sagacity.framework.dao.model.TableSequence;
import org.sagacity.framework.dao.model.sp.StoreProcParam;
import org.sagacity.framework.dao.model.sp.StoreProcParamNotSupportException;
import org.sagacity.framework.dao.utils.SqlUtil;
import org.sagacity.framework.dao.utils.SqlUtil.QueryParam;
import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.model.PaginationModel;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          基于hibernate 以及其它orm 框架体系的底层核心操作方法
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseDAOSupport.java,Revision:v1.0,Date:2008-10-22 下午04:21:51 $
 */
public class BaseDAOSupport extends HibernateDaoSupport {

	/**
	 * 常量，提供给获取表Sequence,like
	 * this.getDateSequence(fiApHead.class,this.DATE_FORMAT_LONG, 10)
	 * 批次取出财务应付单当前日期10条sequence
	 */
	protected final BigDecimal ONE_BIGDECIMAL = new BigDecimal(1);

	/**
	 * 长日期格式
	 */
	protected final String DATE_FORMAT_LONG = "yyyyMMdd";

	protected final String DATE_FORMAT_SHORT = "yyMMdd";

	protected final String DATE_FORMAT_YEAR = "yyyy";

	protected final String DATE_FORMAT_SHORTYEAR = "yy";

	protected final String DATE_FORMAT_YEARMONTH = "yyyyMM";

	/**
	 * 是否输出DAO日志的标志
	 */
	private static String isOutDAOLogFlag = "";

	/**
	 * 定义全局日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	/**
	 * 获取当前会话的数据库类型
	 * 
	 * @return 数据库的类型
	 */
	protected String getCurrentSessionDBDialect() {
		String dialect = ((SessionImpl) this.getSession()).getFactory()
				.getDialect().getClass().getName();
		logger.debug("current session dataBase dialect:" + dialect);
		return dialect;
	}

	// helper methods for create/update/delete
	protected void create(final Object entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * 修改数据库表的实体
	 * 
	 * @param entity
	 */
	protected void update(final Object entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 删除数据库表的实体
	 * 
	 * @param entity
	 */
	protected void delete(final Object entity) {
		getHibernateTemplate().delete(entity);
	}

	// helper methods for query
	protected Object loadById(final Class entity, final Serializable id) {
		return getHibernateTemplate().get(entity, id);
	}

	protected List findAll(final Class entity) {
		return getHibernateTemplate().find("from " + entity.getName());
	}

	/**
	 * 支持hql以及namedQuery
	 * 
	 * @param query
	 *            :可以是hql语句也可以是query对应的一个命名
	 * @return
	 */
	protected List findByhql(final String hqlOrNamedQuery, final Object params) {
		Object[] parameters = convertParams(params);
		if (hqlOrNamedQuery != null) {
			String queryStr = SqlUtil.processQuery(hqlOrNamedQuery,
					getHibernateTemplate().getSessionFactory()
							.getCurrentSession());
			QueryParam queryParam = SqlUtil.filterNullConditions(queryStr,
					null, parameters);
			return getHibernateTemplate().find(queryParam.getQueryStr(),
					queryParam.getParamsValue());
		} else
			return null;
	}

	/**
	 * hql查询
	 * 
	 * @param query
	 * @return
	 */
	protected List findByhql(final String hql) {
		if (hql != null) {
			return getHibernateTemplate().find(
					SqlUtil.processQuery(hql, getHibernateTemplate()
							.getSessionFactory().getCurrentSession()));
		} else
			return null;
	}

	/**
	 * Criteria 查询
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	protected List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		List result = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.list();
					}
				}, true);
		logger.debug("findAllByCriteria:"
				+ (result == null ? "结果为空" : "记录数为:" + result.size()));
		return result;
	}

	/**
	 * Criteria分页查询记录总笔数
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	protected int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				}, true);
		return count.intValue();
	}

	/**
	 * sql 查询并返回结果
	 * 
	 * @param queryStr
	 * @param preparHandler
	 * @param paramsObj
	 * @param rowCallHandler
	 * @return
	 * @throws Exception
	 */
	protected List findByJdbcQuery(String sqlOrNamedSql,
			PreparedStatementSetter preparHandler, Object paramsObj,
			RowCallbackHandler rowCallHandler) throws Exception {
		String queryStr = SqlUtil.processQuery(sqlOrNamedSql,
				getHibernateTemplate().getSessionFactory().getCurrentSession());
		logger.debug("findByJdbcQuery=" + queryStr);
		Object[] params = convertParams(paramsObj);
		// 根据条件是否为null剔除无效查询
		QueryParam queryParam = SqlUtil.filterNullConditions(queryStr, null,
				params);
		params = queryParam.getParamsValue();
		List items = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = this.getSession().connection().prepareStatement(
					queryParam.getQueryStr());
			if (preparHandler != null)
				preparHandler.setValues(pst);
			else if (params != null && params.length > 0) {
				SqlUtil.setParamsValue(pst, params, 0);
			}

			rs = pst.executeQuery();
			// 取得字段列数,在没有rowCallbackHandler用数组返回
			int rowCnt = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				if (rowCallHandler != null)
					rowCallHandler.processRow(rs);
				else {
					if (items == null)
						items = new ArrayList();
					List rowData = new ArrayList();
					for (int i = 0; i < rowCnt; i++) {
						rowData.add(rs.getObject(i + 1));
					}
					items.add(rowData);
				}
			}
			if (rowCallHandler != null)
				items = rowCallHandler.getResult();
		} catch (Exception se) {
			se.printStackTrace();
			throw new Exception(se);
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
		logger.debug("findByJdbcQuery:"
				+ (items == null ? "结果为空" : "记录数为:" + items.size()));
		return items;
	}

	/**
	 * 无返回结果的SQL执行
	 * 
	 * @param executeSql
	 * @param preparHandler
	 * @throws Exception
	 */
	protected void executeJdbcSql(String sqlOrNamedSql,
			PreparedStatementSetter preparHandler, Object paramsObj)
			throws Exception {
		String executeSql = SqlUtil.processQuery(sqlOrNamedSql,
				getHibernateTemplate().getSessionFactory().getCurrentSession());
		logger.debug("executeJdbcSql=" + executeSql);
		Object[] params = convertParams(paramsObj);

		// 根据条件是否为null剔除无效查询
		QueryParam queryParam = SqlUtil.filterNullConditions(executeSql, null,
				params);
		params = queryParam.getParamsValue();
		PreparedStatement pst = null;
		try {
			pst = this.getSession().connection().prepareStatement(
					queryParam.getQueryStr());
			if (preparHandler != null)
				preparHandler.setValues(pst);
			else if (params != null && params.length > 0)
				SqlUtil.setParamsValue(pst, (Object[]) params, 0);
			pst.execute();
		} catch (Exception se) {
			se.printStackTrace();
			logger.debug("executeJdbcSql执行失败!");
			throw new Exception(se);
		} finally {
			if (pst != null)
				pst.close();
		}
		logger.debug("executeJdbcSql执行成功!");
	}

	/**
	 * 根据对象类获取序号
	 * 
	 * @param entityClass
	 * @return
	 * @throws CreateSequenceException
	 */
	protected BigDecimal getBigDecimalSequence(Class entityClass)
			throws CreateSequenceException {
		return getBigDecimalSequence(entityClass, 1);
	}

	/**
	 * @todo example 获取以日期开头的序列号:seqName:orgData size:10 now seqence:10000
	 *       return: 10001 now seqence:10010
	 * @param seqName
	 * @param size
	 * @return
	 * @throws CreateSequenceException
	 */
	protected BigDecimal getBigDecimalSequence(Class entityClass, int size)
			throws CreateSequenceException {
		if (entityClass == null || size < 1)
			throw new CreateSequenceException("Input Parameters has Null!");
		else
			return getSequence(entityClass.getName(), null, null, null, size,
					false);

	}

	/**
	 * 根据日期获取SEQNO
	 * 
	 * @param entityClass
	 * @param nowDate
	 * @param dateStyle
	 * @param length
	 * @return
	 * @throws CreateSequenceException
	 */
	protected BigDecimal getDateSequence(Class entityClass, Date nowDate,
			String dateStyle, int length) throws CreateSequenceException {
		return getDateSequence(entityClass, nowDate, dateStyle, length, 1);
	}

	/**
	 * @todo 获取以日期开头的序列号example:nowSeq=20051118001 ,
	 *       condition:dateValue=20051118;length=3,size=4 update seq:20051118005
	 *       return:20051118002
	 * @param entityClass
	 * @param nowDate
	 * @param dateStyle
	 * @param length
	 * @param size
	 * @return
	 * @throws CreateSequenceException
	 */
	protected BigDecimal getDateSequence(Class entityClass, Date nowDate,
			String dateStyle, int length, int size)
			throws CreateSequenceException {
		if (entityClass == null || length < 1 || size < 1)
			throw new CreateSequenceException("Input Parameters has Null!");
		else
			return getSequence(entityClass.getName(), nowDate, dateStyle,
					new Integer(length), size, true);
	}

	/**
	 * hql分页查询
	 * 
	 * @param hql
	 * @param params
	 *            :条件参数值
	 * @param paginationModel
	 *            :分页模型
	 * @return：返回含数据集合以及总页等信息的分页数据模型
	 */
	protected PaginationModel findPageByhql(final String hql,
			final Object paramsObj, final PaginationModel paginationModel) {
		Object[] params = convertParams(paramsObj);
		return findPageByhql(hql, null, params, paginationModel);
	}

	/**
	 * @todo 分页查询
	 * @param hql
	 * @param paramNames
	 *            ：参数名称
	 * @param params
	 *            :参数值
	 * @param paginationModel
	 *            :分页模型,含页号
	 * @return
	 */
	protected PaginationModel findPageByhql(final String hqlOrNamedQuery,
			final String[] paramNamed, final Object[] paramsValue,
			final PaginationModel paginationModel) {
		// 根据条件是否为null剔除无效查询
		QueryParam queryParam = SqlUtil.filterNullConditions(SqlUtil
				.processQuery(hqlOrNamedQuery, getHibernateTemplate()
						.getSessionFactory().getCurrentSession()), paramNamed,
				paramsValue);
		final String queryStr = queryParam.getQueryStr();
		logger.debug("分页查询hql语句queryStr=" + queryStr);
		final String[] paramsName = queryParam.getParamsName();
		final Object[] params = queryParam.getParamsValue();

		PaginationModel pageModel = null;
		final int recordCount;
		final int realStartPage;
		if (paginationModel.getPageNo() != -1) {
			recordCount = getRecordCount(queryStr, paramsName, params);
			// 总记录数为零则不需要再查询
			if (recordCount == 0)
				return new PaginationModel(null, 0, paginationModel
						.getPageSize(), 1);
			realStartPage = (paginationModel.getPageNo() * paginationModel
					.getPageSize()) >= (recordCount + paginationModel
					.getPageSize()) ? 1 : paginationModel.getPageNo();
		} else {
			recordCount = 0;
			realStartPage = 1;
		}
		List items = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query queryObject = session.createQuery(queryStr);
						if (paramsName != null && paramsName.length > 0
								&& params != null
								&& paramsName.length == params.length) {
							for (int i = 0; i < paramsName.length; i++)
								queryObject.setParameter(paramsName[i],
										params[i]);
						} else {
							if (params != null && params.length > 0) {
								for (int i = 0; i < params.length; i++)
									queryObject.setParameter(i, params[i]);
							}
						}
						// 分页显示
						if (paginationModel.getPageNo() != -1) {
							queryObject.setFirstResult((realStartPage - 1)
									* paginationModel.getPageSize());
							queryObject.setMaxResults(paginationModel
									.getPageSize());
						}
						return queryObject.list();
					}
				}, true);

		pageModel = new PaginationModel(items,
				(paginationModel.getPageNo() != -1) ? recordCount : items
						.size(),
				(paginationModel.getPageNo() != -1) ? (realStartPage - 1)
						* paginationModel.getPageSize() + 1 : 1);
		pageModel.setPageSize(paginationModel.getPageSize());
		pageModel.setShowAllLimitPage(paginationModel.getShowAllLimitPage());
		if (paginationModel.getPageNo() != -1)
			pageModel.setPageNo(realStartPage);
		else
			pageModel.setPageNo(-1);
		return pageModel;
	}

	/**
	 * 条件查询分页
	 * 
	 * @param detachedCriteria
	 * @param paginationModel
	 * @return
	 */
	protected PaginationModel findPageByCriteria(
			final DetachedCriteria detachedCriteria,
			final PaginationModel paginationModel) {
		return (PaginationModel) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						int recordCount = 0;
						int realStartPage = 1;
						if (paginationModel.getPageNo() != -1) {
							recordCount = ((Integer) criteria.setProjection(
									Projections.rowCount()).uniqueResult())
									.intValue();
							// 总记录数为零则不需要再查询
							if (recordCount == 0)
								return new PaginationModel(null, 0,
										paginationModel.getPageSize(), 1);
							realStartPage = (paginationModel.getPageNo() * paginationModel
									.getPageSize()) >= (recordCount + paginationModel
									.getPageSize()) ? 1 : paginationModel
									.getPageNo();
						}

						criteria.setProjection(null);
						List items;
						if (paginationModel.getPageNo() != -1) {
							items = criteria.setFirstResult(
									(realStartPage - 1)
											* paginationModel.getPageSize())
									.setMaxResults(
											paginationModel.getPageSize())
									.list();
						} else
							items = criteria.list();

						PaginationModel ps = new PaginationModel(
								items,
								(paginationModel.getPageNo() != -1) ? recordCount
										: items.size(),
								paginationModel.getPageSize(),
								(paginationModel.getPageNo() != -1) ? (realStartPage - 1)
										* paginationModel.getPageSize() + 1
										: 1);
						ps.setShowAllLimitPage(paginationModel
								.getShowAllLimitPage());
						if (paginationModel.getPageNo() != -1)
							ps.setPageNo(realStartPage);
						else
							ps.setPageNo(-1);

						return ps;
					}
				}, true);
	}

	/**
	 * 本方法提供各类型数据库基于原始sql的分页功能，目前只实现了oracle,sybase,mysql,db2,
	 * sqlserver2005的分页sql处理,除此之外的数据库目前用Sick(欠妥的)方式提供临时实现
	 * 
	 * @param queryStr
	 * @param rowCallbackHandler
	 * @param paramsObj
	 * @param paginationModel
	 * @return
	 * @throws Exception
	 */
	protected PaginationModel findPageByJdbc(String sqlOrNamedSql,
			final RowCallbackHandler rowCallbackHandler,
			final Object paramsObj, final PaginationModel paginationModel)
			throws Exception {
		Object[] params = convertParams(paramsObj);
		QueryParam queryParam = SqlUtil
				.filterNullConditions(SqlUtil.processQuery(sqlOrNamedSql,
						getHibernateTemplate().getSessionFactory()
								.getCurrentSession()), null, params);
		String queryStr = queryParam.getQueryStr();
		params = queryParam.getParamsValue();
		logger.debug("findPageByJdbc:分页查询sql为:" + queryStr);
		String dbDialect = getCurrentSessionDBDialect();
		PaginationModel result = null;
		if (StringUtil.IndexOfIgnoreCase(dbDialect, "oracle") != -1)
			result = findPageCommonJdbc(queryStr, rowCallbackHandler, params,
					paginationModel, 1);
		else if (StringUtil.IndexOfIgnoreCase(dbDialect, "db2") != -1)
			result = findPageCommonJdbc(queryStr, rowCallbackHandler, params,
					paginationModel, 2);
		else if (StringUtil.IndexOfIgnoreCase(dbDialect, "sqlserver") != -1)
			result = findPageCommonJdbc(queryStr, rowCallbackHandler, params,
					paginationModel, 3);
		else if (StringUtil.IndexOfIgnoreCase(dbDialect, "mysql") != -1)
			result = findPageCommonJdbc(queryStr, rowCallbackHandler, params,
					paginationModel, 4);
		else if (StringUtil.IndexOfIgnoreCase(dbDialect, "informix") != -1)
			result = findPageCommonJdbc(queryStr, rowCallbackHandler, params,
					paginationModel, 5);
		else if (StringUtil.IndexOfIgnoreCase(dbDialect, "sybase") != -1)
			result = findPageBySybaseJdbc(queryStr, rowCallbackHandler, params,
					paginationModel);
		else
			result = findPageByJdbcSick(queryStr, rowCallbackHandler, params,
					paginationModel);
		logger.debug("findPageByJdbc"
				+ (result == null ? "结果为空" : "记录条数:" + result.getRecordCount()
						+ "共" + result.getTotalPage() + "页!"));
		return result;
	}

	/**
	 * oracle,mysql,db2,sqlserver2005分页查询
	 * 
	 * @param queryStr
	 * @param rowCallbackHandler
	 * @param params
	 * @param paginationModel
	 * @return
	 * @throws Exception
	 */
	private PaginationModel findPageCommonJdbc(String queryStr,
			final RowCallbackHandler rowCallbackHandler, final Object[] params,
			final PaginationModel paginationModel, int dbType) throws Exception {
		PaginationModel pageModel = null;
		int recordCount = 0;
		int realStartPage = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer paginationSQL = new StringBuffer();
		try {
			// 获取记录总笔数
			if (paginationModel.getPageNo() != -1) {
				recordCount = getJdbcRecordCount(queryStr, params);
				// 总记录数为零则不需要再查询
				if (recordCount == 0)
					return new PaginationModel(null, 0, paginationModel
							.getPageSize(), 1);
				realStartPage = (paginationModel.getPageNo() * paginationModel
						.getPageSize()) >= (recordCount + paginationModel
						.getPageSize()) ? 1 : paginationModel.getPageNo();
			}

			// 设置非分页时的查询参数
			if (paginationModel.getPageNo() != -1) {
				switch (dbType) {
				// oracle
				case 1:
					paginationSQL
							.append(" SELECT * FROM (SELECT SAG_Paginationtable.* ,ROWNUM page_row_id FROM ( ");
					paginationSQL.append(queryStr);
					paginationSQL
							.append("  ) SAG_Paginationtable where ROWNUM <=? ");
					paginationSQL.append(" ) WHERE  page_row_id >? ");
					break;
				// db2
				case 2:
					paginationSQL.append("SELECT * FROM ");
					paginationSQL
							.append("(SELECT rownumber() over() as page_row_id,SAG_Paginationtable.* ");
					paginationSQL.append(" FROM ( ");
					paginationSQL.append(queryStr);
					paginationSQL.append(" ) SAG_Paginationtable ) ");
					paginationSQL
							.append("where page_row_id <=? and page_row_id >?");
					break;
				// sql server2005
				case 3:
					paginationSQL.append("SELECT * FROM ");
					paginationSQL
							.append("(SELECT ROW_NUMBER() OVER() as page_row_id,SAG_Paginationtable.* ");
					paginationSQL.append(" FROM ( ");
					paginationSQL.append(queryStr);
					paginationSQL.append(" ) SAG_Paginationtable ) ");
					paginationSQL
							.append("where page_row_id <=? and page_row_id >?");
					break;
				// mysql
				case 4:
					paginationSQL.append(queryStr);
					paginationSQL.append(" limit ?,? ");
					break;
				// informix
				case 5:
					paginationSQL
							.append("SELECT SKIP ? FIRST ?  SAG_Paginationtable.* FROM (");
					paginationSQL.append(queryStr);
					paginationSQL.append(" ) SAG_Paginationtable");
					break;
				}
			} else
				paginationSQL.append(queryStr);

			pst = this.getSession().connection().prepareStatement(
					paginationSQL.toString());
			// informix数据库参数从2开始
			int paramIndex = 0;
			// 设置分页时的查询参数
			if (paginationModel.getPageNo() != -1) {
				switch (dbType) {
				case 4:
					pst.setInt(params.length + 1, (realStartPage - 1)
							* paginationModel.getPageSize());
					pst.setInt(params.length + 2, realStartPage
							* paginationModel.getPageSize());
					break;
				// informix 数据库
				case 5:
					pst.setInt(1, (realStartPage - 1)
							* paginationModel.getPageSize());
					pst.setInt(2, paginationModel.getPageSize());
					paramIndex = 2;
					break;
				default:
					pst.setInt(params.length + 1, realStartPage
							* paginationModel.getPageSize());
					pst.setInt(params.length + 2, (realStartPage - 1)
							* paginationModel.getPageSize());
					break;
				}
			}

			// 设置查询参数
			if (params != null && params.length > 0)
				SqlUtil.setParamsValue(pst, (Object[]) params, paramIndex);

			rs = pst.executeQuery();
			List items = processResultRow(rs, rowCallbackHandler);
			pageModel = new PaginationModel(items,
					(paginationModel.getPageNo() != -1) ? recordCount
							: rowCallbackHandler.getResult().size(),
					(paginationModel.getPageNo() != -1) ? (realStartPage - 1)
							* paginationModel.getPageSize() + 1 : 1);
			pageModel.setPageSize(paginationModel.getPageSize());
			pageModel
					.setShowAllLimitPage(paginationModel.getShowAllLimitPage());
			if (paginationModel.getPageNo() != -1)
				pageModel.setPageNo(realStartPage);
			else
				pageModel.setPageNo(-1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
		return pageModel;
	}

	/**
	 * sybase jdbc 方式分页查询，sybase12.5及以下版本因为不能使用top嵌套 只提供采用临时表方式实现分页
	 * 
	 * @param queryStr
	 * @param rowCallbackHandler
	 * @param params
	 * @param paginationModel
	 * @param isSickCount
	 * @return
	 * @throws Exception
	 */
	private PaginationModel findPageBySybaseJdbc(String queryStr,
			final RowCallbackHandler rowCallbackHandler, final Object[] params,
			final PaginationModel paginationModel) throws Exception {
		PaginationModel pageModel = null;
		int recordCount = 0;
		int realStartPage = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer paginationSQL = new StringBuffer();
		boolean hasCreateTmp = false;
		String tmpTable = "#SAG_TMP_" + System.currentTimeMillis();
		try {
			// 需要做分页查询
			if (paginationModel.getPageNo() != -1) {
				recordCount = getJdbcRecordCount(queryStr, params);
				// 总记录数为零则不需要再查询
				if (recordCount == 0)
					return new PaginationModel(null, 0, paginationModel
							.getPageSize(), 1);
				realStartPage = (paginationModel.getPageNo() * paginationModel
						.getPageSize()) >= (recordCount + paginationModel
						.getPageSize()) ? 1 : paginationModel.getPageNo();
			}

			// 组合分页sql
			if (paginationModel.getPageNo() != -1) {
				String columns = queryStr.substring(StringUtil
						.IndexOfIgnoreCase(queryStr, "select ") + 6, StringUtil
						.getIgnoreCaseMarkEndIndex("select ", " from",
								queryStr, 0));
				// 判断是否有distinct,有则从字段中剔除
				if (StringUtil.IndexOfIgnoreCase(columns.trim(), "distinct ") == 0) {
					columns = columns.trim().substring(8);
				}

				String rowIdStr = "select rowid=identity(12),"
						+ columns
						+ " into  "
						+ tmpTable
						+ " "
						+ queryStr.substring(StringUtil
								.getIgnoreCaseMarkEndIndex("select ", " from",
										queryStr, 0));
				logger.info("分页插入SQL=" + rowIdStr);
				// 将记录放入临时表
				this.executeJdbcSql(rowIdStr, null, params);
				hasCreateTmp = true;
				// 组合从临时表获取数据的sql
				paginationSQL.append("select " + SqlUtil.processColumn(columns)
						+ " from " + tmpTable);
				paginationSQL.append(" where rowid>? and rowid<=?");
				logger.info("分页查询临时表SQL=" + paginationSQL.toString());
			} else
				paginationSQL.append(queryStr);

			pst = this.getSession().connection().prepareStatement(
					paginationSQL.toString());
			// 设置非分页时的查询参数
			if (paginationModel.getPageNo() == -1) {
				if (params != null && params.length > 0) {
					SqlUtil.setParamsValue(pst, (Object[]) params, 0);
				}
			} else {
				pst.setInt(1, (realStartPage - 1)
						* paginationModel.getPageSize());
				pst.setInt(2, realStartPage * paginationModel.getPageSize());
			}
			rs = pst.executeQuery();
			List items = processResultRow(rs, rowCallbackHandler);
			pageModel = new PaginationModel(items,
					(paginationModel.getPageNo() != -1) ? recordCount
							: rowCallbackHandler.getResult().size(),
					(paginationModel.getPageNo() != -1) ? (realStartPage - 1)
							* paginationModel.getPageSize() + 1 : 1);
			pageModel.setPageSize(paginationModel.getPageSize());
			pageModel
					.setShowAllLimitPage(paginationModel.getShowAllLimitPage());
			if (paginationModel.getPageNo() != -1)
				pageModel.setPageNo(realStartPage);
			else
				pageModel.setPageNo(-1);

			// 在使用分页的条件下删除临时表记录
			if (paginationModel.getPageNo() != -1 && hasCreateTmp) {
				this.executeJdbcSql("drop table " + tmpTable, null, null);
				hasCreateTmp = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (hasCreateTmp)
				this.executeJdbcSql("drop table " + tmpTable, null, null);
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
		return pageModel;
	}

	/**
	 * 本方法一般针对SqlServer分页查询,采用先取出所有结果集,再通过页编号过滤取出当前页数据
	 * 
	 * @deprecated 2008.08.26 修正了findPageByJdbc,此方法停止使用
	 * @param queryStr
	 * @param rowCallbackHandler
	 * @param params
	 * @param paginationModel
	 * @return
	 * @throws Exception
	 */
	private PaginationModel findPageByJdbcSick(String queryStr,
			final RowCallbackHandler rowCallbackHandler, final Object[] params,
			final PaginationModel paginationModel) throws Exception {
		PaginationModel resultPageModel = null;
		int recordCount = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int realStartPage = 0;
		try {
			// 获取记录总笔数
			if (paginationModel.getPageNo() != -1) {
				realStartPage = (paginationModel.getPageNo() * paginationModel
						.getPageSize()) >= (recordCount + paginationModel
						.getPageSize()) ? 1 : paginationModel.getPageNo();
			} else {
				recordCount = 0;
				realStartPage = -1;
			}

			pst = this.getSession().connection().prepareStatement(queryStr);
			if (params != null && params.length > 0) {
				SqlUtil.setParamsValue(pst, (Object[]) params, 0);
			}
			rs = pst.executeQuery();
			List items = null;
			int rowCnt = rs.getMetaData().getColumnCount();
			int index = 1;
			while (rs.next()) {
				if (index >= (realStartPage - 1)
						* paginationModel.getPageSize() + 1
						&& index <= realStartPage
								* paginationModel.getPageSize()) {
					if (rowCallbackHandler != null)
						rowCallbackHandler.processRow(rs);
					else {
						if (items == null)
							items = new ArrayList();
						List rowData = new ArrayList();
						for (int i = 0; i < rowCnt; i++) {
							rowData.add(rs.getObject(i + 1));
						}
						items.add(rowData);
					}
				}
				index++;
			}
			recordCount = index;
			if (rowCallbackHandler != null)
				items = rowCallbackHandler.getResult();
			resultPageModel = new PaginationModel(items, (paginationModel
					.getPageNo() != -1) ? recordCount : rowCallbackHandler
					.getResult().size(),
					(paginationModel.getPageNo() != -1) ? (realStartPage - 1)
							* paginationModel.getPageSize() + 1 : 1);
			resultPageModel.setPageSize(paginationModel.getPageSize());
			resultPageModel.setShowAllLimitPage(paginationModel
					.getShowAllLimitPage());

			resultPageModel.setPageNo(realStartPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
		return resultPageModel;

	}

	/**
	 * 判断对象某列的值在数据库中不存在重复
	 * 
	 * @param names
	 *            在POJO里相对应的属性名,列组合时以逗号分割<br>
	 *            如"name,loginid,password"
	 */
	protected boolean isNotUnique(Object entity, String names,
			CriteriaCallbackHandler criteriaCallback) {
		Assert.hasText(names);
		Criteria criteria = getSession().createCriteria(entity.getClass())
				.setProjection(Projections.rowCount());
		if (criteriaCallback != null)
			criteriaCallback.addRestrictions(criteria);
		String[] nameList = names.split(",");
		try {
			for (int i = 0; i < nameList.length; i++) {
				criteria.add(Restrictions.eq(nameList[i], PropertyUtils
						.getProperty(entity, nameList[i])));
			}

			String keyName = getSessionFactory().getClassMetadata(
					entity.getClass()).getIdentifierPropertyName();
			if (keyName != null
					&& StringUtil.IndexOfIgnoreCase(names, keyName) == -1) {
				Object id = PropertyUtils.getProperty(entity, keyName);
				// 如果是update,排除自身
				if (id != null)
					criteria
							.add(Restrictions.not(Restrictions.eq(keyName, id)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return (((Integer) criteria.uniqueResult())).intValue() > 0;
	}

	/**
	 * 获取表的列字段类型信息
	 * 
	 * @param tableName
	 * @return
	 */
	protected HashMap getTableColumnMeta(String tableName) {
		final HashMap columnMeta = new HashMap();
		try {
			String mediaSql = "select * from " + tableName + " where 1=0";

			this.findByJdbcQuery(mediaSql, null, null,
					new RowCallbackHandler() {
						public void processRow(ResultSet rs)
								throws SQLException {
							String columnName;
							int columnType;
							ResultSetMetaData md = rs.getMetaData();

							for (int i = 1; i < md.getColumnCount() + 1; i++) {
								columnName = md.getColumnName(i);
								columnType = md.getColumnType(i);
								columnMeta.put(columnName, new Integer(
										columnType));
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return columnMeta;
	}

	/**
	 * 快速删除表中的数据
	 * 
	 * @param tableName
	 */
	protected void truncate(String tableName) {
		String truncateSql = "truncate table " + tableName;
		try {
			String dialect = this.getCurrentSessionDBDialect();
			if (StringUtil.IndexOfIgnoreCase(dialect, "Sybase") != -1) {
				if (this.getSession().connection().getAutoCommit() == false)
					this.getSession().connection().setAutoCommit(true);
			}
			this.executeJdbcSql(truncateSql, null, null);
			if (StringUtil.IndexOfIgnoreCase(dialect, "Sybase") != -1)
				this.getSession().connection().setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 此来自宁波农行绩效考核一期，暂没有修改
	 * 
	 * @param procedureName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected List storeProcedure(String procedureName, List params)
			throws Exception {
		Connection conn = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().connection();
		StringBuffer sql = new StringBuffer();
		StringBuffer debug = new StringBuffer();
		sql.append("{?=call ");
		debug.append("{?=call ");
		sql.append(procedureName);
		debug.append(procedureName);
		if (params != null) {
			sql.append("(");
			debug.append("(");
			for (int i = 0; i < params.size(); i++) {
				StoreProcParam param = (StoreProcParam) params.get(i);
				if (i == (params.size() - 1)) {
					sql.append("?");
					if (param.getParamValue() instanceof String) {
						debug.append("'" + param.getParamValue() + "'");
					} else {
						debug.append(param.getParamValue());
					}
				} else {
					sql.append("?,");
					if (param.getParamValue() instanceof String) {
						debug.append("'" + param.getParamValue() + "',");
					} else {
						debug.append(param.getParamValue() + ",");
					}
				}
			}
			sql.append(")");
			debug.append(")");
		}

		sql.append("}");
		debug.append("}");

		CallableStatement call = conn.prepareCall(sql.toString());
		logger.info("StoreProcedure Grammar Is :" + debug.toString());

		call.registerOutParameter(1, Types.INTEGER);
		if (params != null) {
			for (int i = 2; i <= params.size() + 1; i++) {
				if (!(params.get(i - 2) instanceof StoreProcParam)) {
					throw new StoreProcParamNotSupportException();
				}
				StoreProcParam param = (StoreProcParam) params.get(i - 2);
				if (param.getType().equals(StoreProcParam.IN)) {
					if (param.getParamValue() == null) {
						call.setNull(i, param.getSqlType());
					} else {
						call.setObject(i, param.getParamValue());
					}
				} else {
					call.registerOutParameter(i, param.getSqlType());
				}
			}
		}
		List result = new ArrayList();

		ResultSet rs = null;
		try {
			/*
			 * update by chenrenfei 2007-10-11 解决存储过程返回多个结果集问题，取最后一个结果集
			 */
			Object obj = call.executeQuery();

			if (obj instanceof Object[]) {
				ResultSet[] rss = (ResultSet[]) obj;
				rs = rss[rss.length - 1];
			} else
				rs = (ResultSet) obj;
		} catch (Exception e) {
			logger.info("StoreProc is no ResultSet Return!");
			e.printStackTrace();
			logger.error("存储过程调用错误信息:" + e.getMessage());
			return new ArrayList();
		}

		if (rs != null) {
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				List row = new ArrayList();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					row.add(rs.getObject(i + 1));
				}
				result.add(row.toArray());
			}
			rs.close();
		}

		SQLWarning warn = call.getWarnings();

		while (warn != null) {
			if (warn.getErrorCode() == 0 && warn.getSQLState() == null) {
				logger.error(warn.getMessage());
			}
			warn = warn.getNextWarning();
		}

		int rtnFlag = call.getInt(1);
		if (rtnFlag != 0) {
			return new ArrayList();
		}
		call.close();
		return result;
	}

	/**
	 * @todo 获取以日期开头的序列号:example:nowSeq=20051118001 ,
	 *       condition:dateValue=20051118;length=3,size=4 update seq:20051118005
	 *       return:20051118002
	 * @param seqName
	 * @param nowDate
	 * @param dateStyle
	 * @param length
	 * @param size
	 * @param hasDate
	 * @return
	 * @throws CreateSequenceException
	 */
	private BigDecimal getSequence(String seqName, Date nowDate,
			String dateStyle, Integer length, int size, boolean hasDate)
			throws CreateSequenceException {
		if (seqName == null || size < 1)
			throw new CreateSequenceException("Input Parameters has Null!");
		logger.debug("获取" + seqName + "的连续" + size + "个SEQUENCE!");
		try {
			synchronized (seqName) {
				TableSequence tableSeq = null;
				Object obj = this.getHibernateTemplate().get(
						TableSequence.class, seqName);
				Long seqValue = new Long(0);

				// 非第一次创建表序号,取出当前的SEQNO
				if (obj != null) {
					tableSeq = (TableSequence) obj;
					seqValue = tableSeq.getCurrentKeyValue();
				} else {
					// 第一次SEQNO=0
					tableSeq = new TableSequence();
					tableSeq.setSequenceName(seqName);
				}

				Long dateValue = null;
				// 有日期
				if (hasDate) {
					// 如果日期值为空，则取系统当前日期
					Date seqDate = (nowDate == null) ? DateUtil.getNowTime()
							: nowDate;

					dateValue = Long.valueOf(DateUtil.formatDate(seqDate,
							dateStyle));
					// 当前日期大于现有表里的日期，用当前日期替换seq 日期,seqNo从零开始
					if (tableSeq.getDateValue() == null
							|| dateValue.longValue() > tableSeq.getDateValue()
									.longValue()) {
						tableSeq.setDateValue(dateValue);
						seqValue = new Long(0);
					}
				}

				// SEQNO 设置为当前SEQNO+增加量
				tableSeq.setCurrentKeyValue(new Long(seqValue.longValue()
						+ size));
				if (obj != null)
					this.getHibernateTemplate().update(tableSeq);
				else
					this.getHibernateTemplate().save(tableSeq);

				// 返回起始seqNO
				BigDecimal startSeqNo = new BigDecimal(seqValue.longValue() + 1);
				return startSeqNo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateSequenceException("创建表:" + seqName
					+ "的Sequence未成功!", e);
		}
	}

	/**
	 * 数据查询参数对象类型转换
	 * 
	 * @param paramsObj
	 * @return
	 */
	private Object[] convertParams(Object paramsObj) {
		if (paramsObj == null)
			return null;
		if (paramsObj instanceof Object[])
			return (Object[]) paramsObj;
		else if (paramsObj instanceof Collection)
			return ((Collection) paramsObj).toArray();
		else if (paramsObj instanceof Map)
			return ((Map) paramsObj).values().toArray();
		else
			return new Object[] { paramsObj };

	}

	/**
	 * hql语句分页获取其总记录数
	 * 
	 * @param hql
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	private int getRecordCount(String hqlStr, String[] paramNames,
			Object[] paramValues) {
		int recordCnt = 0;
		int sql_index = 0;
		String hql = SqlUtil.clearMistyChars(hqlStr);
		if (StringUtil.IndexOfIgnoreCase(hql.trim(), "from") != 0)
			sql_index = StringUtil.getIgnoreCaseMarkEndIndex("select ",
					" from", hql, 0);
		String countStr = "select count(*) "
				+ (sql_index != -1 ? hql.substring(sql_index) : hql);
		// 去除order by,需要再去除group by 有待完善
		int orderByIndex = StringUtil
				.lastIndexOfIgnoreCase(countStr, " order ");
		int lastBracketIndex = countStr.lastIndexOf(")");
		if (orderByIndex > lastBracketIndex)
			countStr = countStr.substring(0, orderByIndex);
		Query queryObject = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(countStr);

		if (paramNames != null && paramNames.length > 0 && paramValues != null
				&& paramNames.length == paramValues.length) {
			for (int i = 0; i < paramNames.length; i++)
				queryObject.setParameter(paramNames[i], paramValues[i]);
		} else {
			if (paramValues != null && paramValues.length > 0) {
				int index = 0;
				for (int i = 0; i < paramValues.length; i++) {
					// 排除空指针
					if (paramValues[i] != null) {
						queryObject.setParameter(index, paramValues[i]);
						index++;
					}
				}
			}
		}

		List tmp = queryObject.list();
		if (tmp != null && !tmp.isEmpty()) {
			if (tmp.get(0) instanceof Long)
				recordCnt = ((Long) tmp.get(0)).intValue();
			else if (tmp.get(0) instanceof Integer)
				recordCnt = ((Integer) tmp.get(0)).intValue();
		}
		return recordCnt;
	}

	/**
	 * 获取分页查询中总记录数
	 * 
	 * @param queryStr
	 * @param conn
	 * @param params
	 * @return
	 */
	private int getJdbcRecordCount(String queryStr, Object params) {
		// 剔除回车、换行、tab符号
		String tmpStr = SqlUtil.clearMistyChars(queryStr);
		int recordCount = 0;
		// 剔除order
		int orderByIndex = StringUtil.lastIndexOfIgnoreCase(tmpStr, " order ");
		int lastBracketIndex = tmpStr.lastIndexOf(")");
		if (orderByIndex > lastBracketIndex)
			tmpStr = tmpStr.substring(0, orderByIndex);

		StringBuffer countQueryStr = new StringBuffer();

		int sql_index = StringUtil.getIgnoreCaseMarkEndIndex("select ",
				" from", tmpStr, 0);
		countQueryStr.append("select count(*) " + tmpStr.substring(sql_index));

		int paramCnt = SqlUtil.getParamsCount(countQueryStr.toString(), true);

		logger.debug("countQueryStr=" + countQueryStr.toString());

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = this.getSession().connection().prepareStatement(
					countQueryStr.toString());
			if (params != null) {
				if (params instanceof PreparedStatementSetter) {
					((PreparedStatementSetter) params).setValues(pst);
				} else if (params instanceof Object[]) {
					Object[] realParams = (Object[]) params;
					//将from前的参数剔除
					SqlUtil.setParamsValue(pst, SqlUtil.subArray(realParams, 0,
							realParams.length - paramCnt), 0);
				}
			}

			rs = pst.executeQuery();
			while (rs.next())
				recordCount = rs.getInt(1);
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recordCount;
	}

	/**
	 * 处理sql查询时的结果集，当没有反调处理时返回resultSet中的数组
	 * 
	 * @param rs
	 * @param rowCallbackHandler
	 * @return
	 * @throws SQLException
	 */
	private List processResultRow(ResultSet rs,
			RowCallbackHandler rowCallbackHandler) throws SQLException {
		List items = null;
		// 取得字段列数,在没有rowCallbackHandler用数组返回
		int rowCnt = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			if (rowCallbackHandler != null)
				rowCallbackHandler.processRow(rs);
			else {
				if (items == null)
					items = new ArrayList();
				List rowData = new ArrayList();
				for (int i = 0; i < rowCnt; i++)
					rowData.add(rs.getObject(i + 1));
				items.add(rowData);
			}
		}
		if (rowCallbackHandler != null)
			items = rowCallbackHandler.getResult();
		return items;
	}

	/**
	 * 转换sql参数
	 * 
	 * @param params
	 * @return
	 */
	protected Object convertSqlParams(Object params) {
		return convertSqlParams(params, "");
	}

	/**
	 * 转换sql参数
	 * 
	 * @param params
	 * @param contrasts
	 * @return
	 */
	protected Object convertSqlParams(Object params, Object contrasts) {
		if (params == null)
			return null;
		boolean isArray = true;
		// 转换参数
		Object[] contrastSigns = convertParams(contrasts);
		Object[] paramObjs;
		if (params instanceof Object[])
			paramObjs = (Object[]) params;
		else if (params instanceof Collection)
			paramObjs = ((Collection) params).toArray();
		else if (params instanceof Map)
			paramObjs = ((Map) params).values().toArray();
		else {
			isArray = false;
			paramObjs = new Object[] { params };
		}
		for (int i = 0; i < paramObjs.length; i++) {
			if (paramObjs[i] != null) {
				for (int j = 0; j < contrastSigns.length; j++) {
					if (contrastSigns[i].equals("")) {
						if (((String) params).trim().equals("")) {
							paramObjs[i] = null;
							break;
						}
					} else if (contrastSigns[i].equals(paramObjs[i])) {
						paramObjs[i] = null;
						break;
					}
				}
			}
		}
		if (!isArray)
			return paramObjs[0];
		else
			return paramObjs;
	}

	public static void main(String[] args) {
		String queryStr = " select * from HR_ORGAN_INFO where 1=1 and CREATE_DATE>=? and CREATE_DATE<=?  ";

		BaseDAOSupport test = new BaseDAOSupport();
		test.getJdbcRecordCount(queryStr, new Object[] { "", "" });
	}
}
