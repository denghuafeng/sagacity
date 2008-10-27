/**
 * 
 */
package org.sagacity.framework.cache.ehcache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.abchina.framework.log.Log;
import com.abchina.framework.log.LogFactory;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:MethodCacheInterceptor.java,Revision:v1.0,Date:Jul 21, 2008
 *          8:29:46 PM $
 */
public class MethodCacheInterceptor implements MethodInterceptor,
		InitializingBean {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public MethodCacheInterceptor() {
		super();
	}

	/**
	 * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值， 否则，返回数据库查询结果，并将查询结果放入cache
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Object result;
		logger.debug("Find object from cache is " + cache.getName());
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Element element = cache.get(cacheKey);
		long startTime = System.currentTimeMillis();
		if (element == null) {
			logger
					.debug("Hold up method , Get method result and create cache........!");
			result = invocation.proceed();
			element = new Element(cacheKey, (Serializable) result);
			cache.put(element);
			long endTime = System.currentTimeMillis();
			logger.info(targetName + "." + methodName + " 方法被首次调用并被缓存。耗时"
					+ (endTime - startTime) + "毫秒" + " cacheKey:"
					+ element.getKey());
		} else {
			long endTime = System.currentTimeMillis();
			logger.info(targetName + "." + methodName + " 结果从缓存中直接调用。耗时"
					+ (endTime - startTime) + "毫秒" + " cacheKey:"
					+ element.getKey());
		}
		return element.getValue();
	}

	/**
	 * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 cache key包括 包名+类名+方法名+参数
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * implement InitializingBean，检查cache是否为空
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,
				"Need a cache. Please use setCache(Cache) create it.");
	}

}
