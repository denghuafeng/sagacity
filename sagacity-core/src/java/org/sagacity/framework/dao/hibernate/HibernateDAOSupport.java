/**
 * 
 */
package org.sagacity.framework.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.sagacity.framework.dao.hibernate.model.TableSequence;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.DateUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.view.PaginationModel;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          提供在spring和hibernate集成所需要的hibernate常用操作
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:HibernateDAOSupport.java,Revision:v1.0,Date:Oct 18, 2007 8:57:45
 *          AM $
 */
public class HibernateDAOSupport<T> extends HibernateDaoSupport{
	/**
	 * 常量
	 */
	protected final BigDecimal ONEBIGDECIMAL = new BigDecimal(1);

	protected final String DATE_FORMAT_LONG = "yyyyMMdd";

	protected final String DATE_FORMAT_SHORT = "yyMMdd";

	protected final String DATE_FORMAT_YEAR = "yyyy";

	protected final String DATE_FORMAT_SHORTYEAR = "yy";

	protected final String DATE_FORMAT_YEARMONTH = "yyyyMM";

	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 定义全局日志
	 */
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	// Pagination 实现
	public PaginationModel findPageByCriteria(
			final DetachedCriteria detachedCriteria) {
		return findPageByCriteria(detachedCriteria, PaginationModel.PAGE_SIZE,
				0);
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @param startIndex
	 * @return
	 */
	public PaginationModel findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int pageNo) {
		return findPageByCriteria(detachedCriteria, PaginationModel.PAGE_SIZE,
				pageNo);
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PaginationModel findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int pageSize,
			final int pageNo) {
		return (PaginationModel) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						int totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult())
								.intValue();
						criteria.setProjection(null);
						int realStartPage = (pageNo * pageSize) > (totalCount + pageSize) ? 1
								: pageNo;
						List items = criteria.setFirstResult(
								(realStartPage - 1) * pageSize).setMaxResults(
								pageSize).list();
						PaginationModel ps = new PaginationModel(items,
								totalCount, pageSize, realStartPage * pageSize
										+ 1);
						ps.setPageNo(realStartPage);
						ps.setPageSize(pageSize);
						return ps;
					}
				}, true);
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		}, true);
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
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
	 * 
	 * @param queryStr
	 * @param preparHandler
	 * @param rowCallHandler
	 * @throws Exception
	 */
	protected void findByJdbcQuery(String queryStr,
			PreparedStatementSetter preparHandler,
			RowCallbackHandler rowCallHandler) throws Exception {
		Session sess = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			sess = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			pst = sess.connection().prepareStatement(queryStr);
			if (preparHandler != null)
				preparHandler.setValues(pst);

			if (rowCallHandler != null) {
				rs = pst.executeQuery();
				while (rs.next()) {
					rowCallHandler.processRow(rs);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se);
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
	}

	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @param rowCallHandler
	 * @throws Exception
	 */
	protected void findByJdbcParamsQuery(String queryStr, Object[] params,
			RowCallbackHandler rowCallHandler) throws Exception {
		Session sess = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			sess = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			pst = sess.connection().prepareStatement(queryStr);

			if (params != null && params.length > 0) {
				Object param;
				// /String className;
				int index = 1;
				for (int i = 0; i < params.length; i++) {
					param = params[i];
					if (param != null) {
						// /className=param.getClass().getName();
						if (param instanceof java.lang.String)
							pst.setString(index, (String) param);
						else if (param instanceof java.lang.Long)
							pst.setLong(index, (Long) param);
						else if (param instanceof java.lang.Integer)
							pst.setInt(index, (Integer) param);
						else if (param instanceof java.util.Date)
							pst.setDate(index, new java.sql.Date(
									((java.util.Date) param).getTime()));
						else if (param instanceof java.sql.Date)
							pst.setDate(index, (java.sql.Date) param);
						else if (param instanceof java.lang.Double)
							pst.setDouble(index, (Double) param);
						else if (param instanceof java.lang.Float)
							pst.setFloat(index, (Float) param);
						index++;
					}
				}
			}

			// 处理数据集
			if (rowCallHandler != null) {
				rs = pst.executeQuery();
				while (rs.next()) {
					rowCallHandler.processRow(rs);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se);
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
	}

	/**
	 * 执行Sql语句
	 * 
	 * @param executeSql
	 * @param preparHandler
	 * @throws Exception
	 */
	protected void executeJdbcSql(String executeSql,
			PreparedStatementSetter preparHandler) throws Exception {
		Session sess = null;
		PreparedStatement pst = null;
		try {
			sess = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			pst = sess.connection().prepareStatement(executeSql);
			if (preparHandler != null)
				preparHandler.setValues(pst);
			pst.execute();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se);
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	// helper methods for create/update/delete
	protected void create(final Object entity) {
		getHibernateTemplate().save(entity);
	}

	protected void update(final Object entity) {
		getHibernateTemplate().update(entity);
	}

	protected void delete(final Object entity) {
		getHibernateTemplate().delete(entity);
	}

	// helper methods for query
	protected Object loadById(final Class entity, final Serializable id) {
		return getHibernateTemplate().get(entity, id);
	}

	protected Object getEntity(final Serializable id) {
		return getHibernateTemplate().get(this.entityClass, id);
	}

	protected List findAll(final Class entity) {
		return getHibernateTemplate().find("from " + entity.getName());
	}

	/**
	 * 
	 * @return
	 */
	protected List findAll() {
		return getHibernateTemplate()
				.find("from " + this.entityClass.getName());
	}

	protected List findByNamedQuery(final String namedQuery) {
		return getHibernateTemplate().findByNamedQuery(namedQuery);
	}

	protected List findByNamedQuery(final String query, final Object parameter) {
		return getHibernateTemplate().findByNamedQuery(query, parameter);
	}

	protected List findByNamedQuery(final String query,
			final Object[] parameters) {
		return getHibernateTemplate().findByNamedQuery(query, parameters);
	}

	protected List find(final String query) {
		return getHibernateTemplate().find(query);
	}

	protected BigDecimal getBigDecimalSequence(String seqName)
			throws CreateSequenceException {
		return getBigDecimalSequence(seqName, 1);
	}

	/**
	 * @todo example 获取以日期开头的序列号:seqName:orgData size:10 now seqence:10000
	 *       return: 10001 now seqence:10010
	 * @param seqName
	 * @param size
	 * @return
	 */
	protected BigDecimal getBigDecimalSequence(String seqName, int size)
			throws CreateSequenceException {
		if (seqName == null || size < 1)
			throw new CreateSequenceException("Input Parameters has Null!");
		try {
			synchronized (seqName) {
				BigDecimal backSequence = new BigDecimal("1");
				TableSequence tableSeq;
				Object obj = null;
				obj = this.getHibernateTemplate().get(TableSequence.class,
						seqName);

				if (obj != null) {
					tableSeq = (TableSequence) obj;
					backSequence = new BigDecimal(tableSeq.getCurrentKeyValue()
							.toString());
					tableSeq
							.setCurrentKeyValue(Long.valueOf(backSequence.add(
									new BigDecimal(Integer.toString(size)))
									.toString()));

					this.getHibernateTemplate().update(tableSeq);
					backSequence = backSequence.add(this.ONEBIGDECIMAL);
				} else {
					tableSeq = new TableSequence();
					tableSeq.setSequenceName(seqName);
					tableSeq.setCurrentKeyValue(new Long(size));

					this.getHibernateTemplate().save(tableSeq);
				}
				return backSequence;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateSequenceException("create table:" + seqName
					+ " Sequence failure!��Sequenc sese", e);
		}
	}

	/**
	 * 
	 * @param seqName
	 * @param nowDate
	 * @param length
	 * @return
	 * @throws Exception
	 */
	protected BigDecimal getDateSequence(String seqName, Date nowDate,
			String dateStyle, int length) throws CreateSequenceException {
		return getDateSequence(seqName, nowDate, dateStyle, length, 1);
	}

	/**
	 * @todo 获取以日期开头的序列号��ȡ�����ڿ�ͷ�����к�:example:nowSeq=20051118001 ,
	 *       condition:dateValue=20051118;length=3,size=4 update seq:20051118005
	 *       return:20051118002
	 * @param seqName
	 * @param nowDate
	 * @param size
	 * @param length
	 * @return
	 */
	protected BigDecimal getDateSequence(String seqName, Date nowDate,
			String dateStyle, int length, int size)
			throws CreateSequenceException {
		if (seqName == null || length < 1 || size < 1)
			throw new CreateSequenceException("Input Parameters has Null!");
		try {
			if (nowDate == null)
				nowDate = DateUtil.getNowTime();
			Long dateValue = new Long(0);

			dateValue = Long.valueOf(DateUtil.formatDate(nowDate, dateStyle));
			synchronized (seqName) {
				BigDecimal backSequence = new BigDecimal("1");
				TableSequence tableSeq = null;
				Object obj = null;
				obj = this.getHibernateTemplate().get(TableSequence.class,
						seqName);

				// 非第一次创建表序号��������
				if (obj != null) {
					tableSeq = (TableSequence) obj;
					// 当前日期大于现有表里的日期，将当前日期替换seq 日期
					if (dateValue.longValue() > tableSeq.getDateValue()
							.longValue()) {
						tableSeq.setDateValue(dateValue);
						tableSeq.setCurrentKeyValue(new Long(size));
						this.getHibernateTemplate().update(tableSeq);
					} else {
						dateValue = tableSeq.getDateValue();
						backSequence = new BigDecimal(tableSeq
								.getCurrentKeyValue().toString());
						tableSeq.setCurrentKeyValue(Long.valueOf(backSequence
								.add(new BigDecimal(Integer.toString(size)))
								.toString()));
						this.getHibernateTemplate().update(tableSeq);
						backSequence = backSequence.add(this.ONEBIGDECIMAL);
					}
				} else// the first create seqence
				{
					tableSeq = new TableSequence();
					tableSeq.setCurrentKeyValue(new Long(size));
					tableSeq.setDateValue(dateValue);
					tableSeq.setSequenceName(seqName);
					this.getHibernateTemplate().save(tableSeq);
				}
				return new BigDecimal(dateValue.toString()
						+ StringUtil.addLeftZero2Len(backSequence.toString(),
								length));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateSequenceException("创建表��:" + seqName
					+ "的Sequence未成功!", e);
		}
	}

	/**
	 * 
	 * @param seqName
	 * @return TsysTableSequence
	 */
	protected TableSequence getTableSequence(String seqName) {
		Object obj = this.getHibernateTemplate().get(TableSequence.class,
				seqName);
		if (obj != null)
			return (TableSequence) obj;
		else
			return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param param
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	protected PaginationModel findPageByQuery(final String hql,
			final Object param, final int pageSize, final int pageNo) {
		PaginationModel result = null;
		int recordCount = getRecordCount(hql, null, new Object[] { param })
				.intValue();
		final int realStartPage;
		realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
				: pageNo;
		List items = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query queryObject = session.createQuery(hql);
						if (param != null) {
							queryObject.setParameter(0, param);
						}
						queryObject.setFirstResult((realStartPage - 1)
								* pageSize);
						queryObject.setMaxResults(pageSize);
						return queryObject.list();
					}
				}, true);
		result = new PaginationModel(items, recordCount, (realStartPage - 1)
				* pageSize + 1);
		result.setPageSize(pageSize);
		result.setPageNo(realStartPage);
		return result;
	}

	/**
	 * 
	 * @param hql
	 * @param params
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	protected PaginationModel findPageByQuery(final String hql,
			final Object[] params, final int pageSize, final int pageNo) {
		PaginationModel result = null;
		int recordCount = getRecordCount(hql, null, params).intValue();
		final int realStartPage;
		realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
				: pageNo;
		List items = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query queryObject = session.createQuery(hql);

						if (params != null && params.length > 0) {
							for (int i = 0; i < params.length; i++)
								queryObject.setParameter(i, params[i]);

						}
						queryObject.setFirstResult((realStartPage - 1)
								* pageSize);
						queryObject.setMaxResults(pageSize);
						return queryObject.list();
					}
				}, true);
		result = new PaginationModel(items, recordCount, (realStartPage - 1)
				* pageSize + 1);
		result.setPageSize(pageSize);
		result.setPageNo(realStartPage);
		return result;
	}

	/**
	 * 
	 * @param hql
	 * @param paramName
	 * @param param
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	protected PaginationModel findPageByNamedQuery(final String hql,
			final String paramName, final Object param, final int pageSize,
			final int pageNo) {
		PaginationModel result = null;
		int recordCount = getRecordCount(hql, new String[] { paramName },
				new Object[] { param }).intValue();
		final int realStartPage;
		realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
				: pageNo;
		List items = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query queryObject = session.createQuery(hql);

						if (paramName != null && param != null) {
							queryObject.setParameter(paramName, param);
						}
						queryObject.setFirstResult((realStartPage - 1)
								* pageSize);
						queryObject.setMaxResults(pageSize);
						return queryObject.list();
					}
				}, true);
		result = new PaginationModel(items, recordCount, (realStartPage - 1)
				* pageSize + 1);
		result.setPageSize(pageSize);
		result.setPageNo(realStartPage);
		return result;
	}

	/**
	 * @todo 分页查询
	 * @author zhongxuchen
	 * @param hql
	 * @param paramNames
	 * @param params
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	protected PaginationModel findPageByNamedQuery(final String hql,
			final String[] paramNames, final Object[] params,
			final int pageSize, final int pageNo) {
		PaginationModel result = null;
		int recordCount = getRecordCount(hql, paramNames, params).intValue();
		final int realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
				: pageNo;
		List items = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query queryObject = session.createQuery(hql);

						if (paramNames != null && paramNames.length > 0
								&& params != null
								&& paramNames.length == params.length) {
							for (int i = 0; i < paramNames.length; i++)
								queryObject.setParameter(paramNames[i],
										params[i]);
						}
						queryObject.setFirstResult((realStartPage - 1)
								* pageSize);
						queryObject.setMaxResults(pageSize);
						return queryObject.list();
					}
				}, true);
		result = new PaginationModel(items, recordCount, (realStartPage - 1)
				* pageSize + 1);
		result.setPageSize(pageSize);
		result.setPageNo(realStartPage);
		return result;
	}

	/**
	 * 获取当前会话的数据库
	 * 
	 * @return
	 */
	protected String getCurrentSessionDBDialect() {
		String dialect = ((SessionImpl) this.getSession()).getFactory()
				.getDialect().getClass().getName();
		logger.info("current session dataBase dialect:" + dialect);
		return dialect;
	}

	/**
	 * 根据当前的数据库信息组合相应的分页查询语句
	 * 
	 * @param queryStr
	 * @return
	 */
	private String getPaginationSql(String queryStr) {
		String dialect = getCurrentSessionDBDialect();
		StringBuffer paginationSQL = new StringBuffer();

		if (StringUtil.IndexOfIgnoreCase(dialect, "Sybase") != -1) {

		} else if (StringUtil.IndexOfIgnoreCase(dialect, "SqlServer") != -1) {

		} else if (StringUtil.IndexOfIgnoreCase(dialect, "MySql") != -1) {

		} else if (StringUtil.IndexOfIgnoreCase(dialect, "Oracle") != -1) {
			paginationSQL
					.append(" SELECT * FROM (SELECT Paginationtable.* ,ROWNUM num FROM ( ");
			paginationSQL.append(queryStr);
			paginationSQL.append("  ) Paginationtable where ROWNUM <=? ");
			paginationSQL.append(" ) WHERE  num >? ");
		}
		return paginationSQL.toString();
	}

	// JdbcTemplate
	protected PaginationModel findPageByOracleJdbc(String queryStr,
			final PreparedStatementSetter pstSetter, final int pageSize,
			final int pageNo, final RowCallbackHandler rowCallbackHandler)
			throws Exception {
		PaginationModel pageModel = null;
		int recordCount = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Session sess = null;
		int realStartPage = 0;
		StringBuffer paginationSQL;
		try {
			sess = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			// 获取记录总笔数��ȡ��¼�ܱ���
			recordCount = getJdbcRecordCount(queryStr, sess.connection(),
					pstSetter);
			if (recordCount != 0) {
				realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
						: pageNo;

				// 构造oracle数据库的分页语句���
				paginationSQL = new StringBuffer();
				paginationSQL
						.append(" SELECT * FROM (SELECT Paginationtable.* ,ROWNUM num FROM ( ");
				paginationSQL.append(queryStr);
				paginationSQL.append("  ) Paginationtable where ROWNUM <=? ");
				paginationSQL.append(" ) WHERE  num >? ");

				pst = sess.connection().prepareStatement(
						paginationSQL.toString());
				if (pstSetter != null)
					pstSetter.setValues(pst);
				int params = getParamsCount(queryStr);
				pst.setInt(params + 1, realStartPage * pageSize);
				pst.setInt(params + 2, (realStartPage - 1) * pageSize);
				rs = pst.executeQuery();
				while (rs.next()) {
					rowCallbackHandler.processRow(rs);
				}
			}
			pageModel = new PaginationModel(null, recordCount,
					(realStartPage - 1) * pageSize + 1);
			pageModel.setPageNo(realStartPage);
			pageModel.setPageSize(pageSize);
		} catch (SQLException e) {
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
	 * 本方法一般针对SqlServer分页查询,采用先取出所有结果集,再通过页编号过滤取出当前页数据
	 * 
	 * @param queryStr
	 * @param pstSetter
	 * @param pageSize
	 * @param pageNo
	 * @param rowCallbackHandler
	 * @return
	 * @throws Exception
	 */
	protected PaginationModel findPageByJdbc(String queryStr,
			final PaginationModel pageModel,
			final PreparedStatementSetter pstSetter,
			final RowCallbackHandler rowCallbackHandler) throws Exception {
		pageModel.setItems(null);
		int recordCount = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Session sess = null;
		int realStartPage = 0;
		try {
			sess = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			// 获取记录总笔数
			recordCount = getJdbcRecordCount(queryStr, sess.connection(),
					pstSetter);

			if (recordCount != 0) {
				// 页
				realStartPage = (pageModel.getPageNo() * pageModel
						.getPageSize()) > (recordCount + pageModel
						.getPageSize()) ? 1 : pageModel.getPageNo();
				pst = sess.connection().prepareStatement(queryStr);
				if (pstSetter != null)
					pstSetter.setValues(pst);
				rs = pst.executeQuery();
				int rowCnt = rs.getMetaData().getColumnCount();
				int index = 1;
				List items = null;
				if (rowCallbackHandler == null)
					items = new ArrayList();
				while (rs.next()) {
					if (index >= (realStartPage - 1) * pageModel.getPageSize()
							+ 1
							&& index <= realStartPage * pageModel.getPageSize()) {
						if (rowCallbackHandler != null)
							rowCallbackHandler.processRow(rs);
						else {
							List rowData = new ArrayList();
							for (int i = 0; i < rowCnt; i++) {
								rowData.add(rs.getObject(i));
							}
							items.add(rowData);
						}
					}
					index++;
				}
			}
			pageModel.setRecordCount(recordCount);
			pageModel.setStartIndex((realStartPage - 1)
					* pageModel.getPageSize() + 1);
			pageModel.setPageNo(realStartPage);
			pageModel.setPageSize(pageModel.getPageSize());
		} catch (SQLException e) {
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
	 * 本方法一般针对SqlServer分页查询,采用先取出所有结果集,再通过页编号过滤取出当前页数据
	 * 
	 * @param queryStr
	 * @param pstSetter
	 * @param pageSize
	 * @param pageNo
	 * @param rowCallbackHandler
	 * @return
	 * @throws Exception
	 */
	protected PaginationModel findPageParamsByJdbc(String queryStr,
			final Object[] params, final int pageSize, final int pageNo,
			final RowCallbackHandler rowCallbackHandler) throws Exception {
		PaginationModel pageModel = null;
		int recordCount = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Session sess = null;
		int realStartPage = 0;
		try {
			sess = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();

			// 获取记录总笔数
			recordCount = getJdbcRecordParamsCount(queryStr, sess.connection(),
					params);

			if (recordCount != 0) {
				// 开始页
				realStartPage = (pageNo * pageSize) > (recordCount + pageSize) ? 1
						: pageNo;
				pst = sess.connection().prepareStatement(queryStr);
				if (params != null && params.length > 0) {
					Object param;
					int paramIndex = 1;
					for (int i = 0; i < params.length; i++) {
						param = params[i];
						if (param != null) {
							if (param instanceof java.lang.String)
								pst.setString(paramIndex, (String) param);
							else if (param instanceof java.lang.Long)
								pst.setLong(paramIndex, (Long) param);
							else if (param instanceof java.lang.Integer)
								pst.setInt(paramIndex, (Integer) param);
							else if (param instanceof java.util.Date)
								pst.setDate(paramIndex, new java.sql.Date(
										((java.util.Date) param).getTime()));
							else if (param instanceof java.sql.Date)
								pst.setDate(paramIndex, (java.sql.Date) param);
							else if (param instanceof java.lang.Double)
								pst.setDouble(paramIndex, (Double) param);
							else if (param instanceof java.lang.Float)
								pst.setFloat(paramIndex, (Float) param);
							paramIndex++;
						}
					}
				}
				rs = pst.executeQuery();
				int index = 1;
				while (rs.next()) {
					if (index >= (realStartPage - 1) * pageSize + 1
							&& index <= realStartPage * pageSize) {
						rowCallbackHandler.processRow(rs);
					}
					index++;
				}
			}
			pageModel = new PaginationModel(null, recordCount,
					(realStartPage - 1) * pageSize + 1);
			pageModel.setPageNo(realStartPage);
			pageModel.setPageSize(pageSize);
		} catch (SQLException e) {
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
	 * 判断对象某列的值在数据库中不存在重复
	 * 
	 * @param names
	 *            在POJO里相对应的属性名,列组合时以逗号分割<br>
	 *            如"name,loginid,password"
	 */
	protected boolean isNotUnique(Object entity, String names) {
		Assert.hasText(names);
		Criteria criteria = getSession().createCriteria(entity.getClass())
				.setProjection(Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
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
			logger.error(e.getMessage());
			return false;
		}
		return ((Integer) criteria.uniqueResult()) > 0;
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

			this.findByJdbcQuery(mediaSql, null, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					String columnName;
					int columnType;
					ResultSetMetaData md = rs.getMetaData();

					for (int i = 1; i < md.getColumnCount() + 1; i++) {
						columnName = md.getColumnName(i);
						columnType = md.getColumnType(i);
						columnMeta.put(columnName, new Integer(columnType));
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
	public void truncate(String tableName) {
		String truncateSql = "truncate table " + tableName;
		try {
			String dialect = this.getCurrentSessionDBDialect();
			if (StringUtil.IndexOfIgnoreCase(dialect, "Sybase") != -1) {
				if (this.getSession().connection().getAutoCommit() == false)
					this.getSession().connection().setAutoCommit(true);
			}
			this.executeJdbcSql(truncateSql, null);
			if (StringUtil.IndexOfIgnoreCase(dialect, "Sybase") != -1)
				this.getSession().connection().setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * hql语句分页获取其总记录数
	 * 
	 * @param hql
	 * @return Integer
	 */
	private Integer getRecordCount(String hql, String[] paramNames,
			Object[] paramValues) {
		Integer amount = new Integer(0);
		int sql_index = StringUtil.IndexOfIgnoreCase(hql, " from");
		String countStr = "select count(*) " + hql.substring(sql_index);
		// 去除order by
		int orderByIndex = StringUtil.IndexOfIgnoreCase(countStr, " order ");
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
					if (paramValues[i] != null) {
						queryObject.setParameter(index, paramValues[i]);
						index++;
					}
				}
			}
		}

		List tmp = queryObject.list();
		if (tmp != null && !tmp.isEmpty()) {
			amount = (Integer) tmp.get(0);
		}
		return amount;
	}

	/**
	 * 获取分页查询中总记录数
	 * 
	 * @param queryStr
	 * @param conn
	 * @param pstSetter
	 * @return
	 */
	private int getJdbcRecordCount(String queryStr, Connection conn,
			PreparedStatementSetter pstSetter) {
		int recordCount = 0;
		int sql_index = StringUtil.IndexOfIgnoreCase(queryStr, " from");
		String countStr = "select count(*) " + queryStr.substring(sql_index);
		int orderByIndex = StringUtil.IndexOfIgnoreCase(countStr, " order ");
		int lastBracketIndex = countStr.lastIndexOf(")");
		if (orderByIndex > lastBracketIndex)
			countStr = countStr.substring(0, orderByIndex);

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(countStr);
			if (pstSetter != null)
				pstSetter.setValues(pst);
			rs = pst.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt(1);
			}
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
	 * 
	 * 根据参数获取分页查询的总记录数
	 * 
	 * @param queryStr
	 * @param conn
	 * @param pstSetter
	 * @return
	 */
	private int getJdbcRecordParamsCount(String queryStr, Connection conn,
			Object[] params) {
		int recordCount = 0;
		int sql_index = StringUtil.IndexOfIgnoreCase(queryStr, " from");
		String countStr = "select count(*) " + queryStr.substring(sql_index);
		int orderByIndex = StringUtil.IndexOfIgnoreCase(countStr, " order ");
		int lastBracketIndex = countStr.lastIndexOf(")");
		if (orderByIndex > lastBracketIndex)
			countStr = countStr.substring(0, orderByIndex);

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(countStr);
			if (params != null && params.length > 0) {
				Object param;
				int index = 1;
				for (int i = 0; i < params.length; i++) {
					param = params[i];
					if (param != null) {
						if (param instanceof java.lang.String)
							pst.setString(index, (String) param);
						else if (param instanceof java.lang.Long)
							pst.setLong(index, (Long) param);
						else if (param instanceof java.lang.Integer)
							pst.setInt(index, (Integer) param);
						else if (param instanceof java.util.Date)
							pst.setDate(index, new java.sql.Date(
									((java.util.Date) param).getTime()));
						else if (param instanceof java.sql.Date)
							pst.setDate(index, (java.sql.Date) param);
						else if (param instanceof java.lang.Double)
							pst.setDouble(index, (Double) param);
						else if (param instanceof java.lang.Float)
							pst.setFloat(index, (Float) param);
						index++;
					}
				}
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt(1);
			}
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
	 * 获取查询sql语句中参数的个数
	 * 
	 * @param queryStr
	 * @return
	 */
	private int getParamsCount(String queryStr) {
		int paramCnt = 0;
		if (StringUtil.isNullOrBlank(queryStr))
			return paramCnt;
		int index = 0;
		while ((index = queryStr.indexOf("?", index + 1)) != -1) {
			paramCnt++;
		}
		return paramCnt;
	}

	public static void main(String[] args) {
		String queryStr = "select tmail.mail_no,   tmail.topic,   tmail.content,   tmail.mail_type,   tmail.status,   tmail.grade,   tmail.create_by,   tmail.CREATE_DATE,   tmail.IS_AUTO_BACK,   tmail.DISPATCH_DATE,   tmail.is_active,   tmail.disable_date,   tmail.is_broadcast,   tmail.isread,   emp.name sentName from ( select t3.mail_no,        t3.topic,        t3.content,        t3.mail_type,        t3.status,        t3.grade,        t3.create_by,        t3.CREATE_DATE,        t3.IS_AUTO_BACK,        t3.is_active,        t3.DISPATCH_DATE,        t3.is_broadcast,        t3.disable_date,        case when t5.read_no is null then '0' else '1' end isread  from (     select t0.* from biz_mail_box t0, (select t.mail_no from biz_mail_address t where t.accept_by=?    union all   select t1.mail_no from biz_mail_address t1 where t1.accept_company=?     and t1.accept_dept is null and t1.accept_by is null   union all   select t2.mail_no from biz_mail_address t2 where t2.accept_dept=?   and t2.accept_company=? and t2.accept_by is null ) address  where t0.is_active=1 and t0.status>=1 and t0.DISPATCH_DATE<=getDate()  and t0.mail_no=address.mail_no ) t3 left join (select t4.mail_no,t4.read_no from biz_mail_readrecord t4  where t4.read_by=? and t4.process_type<>0) t5 on t3.mail_no=t5.mail_no ) tmail,v_sys_employee emp where tmail.create_by=emp.emp_no and (((tmail.disable_date is null or tmail.disable_date>getDate()) and tmail.isread=0) or tmail.isread=1) order by tmail.mail_no desc";
		int recordCount = 0;
		int sql_index = StringUtil.IndexOfIgnoreCase(queryStr, " from");
		String countStr = "select count(*) " + queryStr.substring(sql_index);
		int orderByIndex = StringUtil.IndexOfIgnoreCase(countStr, " order ");
		int lastBracketIndex = countStr.lastIndexOf(")");
		if (orderByIndex > lastBracketIndex)
			countStr = countStr.substring(0, orderByIndex);

		System.out.println("countStr:" + countStr);

	}

}
