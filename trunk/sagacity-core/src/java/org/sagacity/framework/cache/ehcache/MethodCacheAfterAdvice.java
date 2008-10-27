/**
 * 
 */
package org.sagacity.framework.cache.ehcache;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;

import org.springframework.aop.AfterReturningAdvice;
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
 * @version $id:MethodCacheAfterAdvice.java,Revision:v1.0,Date:Jul 21, 2008
 *          8:48:46 PM $
 */
public class MethodCacheAfterAdvice implements AfterReturningAdvice,
		InitializingBean {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());
	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public MethodCacheAfterAdvice() {
		super();
	}

	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		String className = arg3.getClass().getName();
		List list = cache.getKeys();
		for (int i = 0; i < list.size(); i++) {
			String cacheKey = String.valueOf(list.get(i));
			if (cacheKey.startsWith(className)) {
				cache.remove(cacheKey);
				logger.debug("remove cache " + cacheKey);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,
				"Need a cache. Please use setCache(Cache) create it.");
	}

}
