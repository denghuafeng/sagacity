package org.sagacity.exoteric.security.resourcedetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataRetrievalFailureException;

import org.sagacity.exoteric.Constants;

/**
 * Caches <code>Resource</code> objects using a Spring IoC defined <a
 * HREF="http://ehcache.sourceforge.net">EHCACHE</a>.
 * 
 * @author cac
 */
public class ResourceCache {

	// ~ Static fields/initializers
	// =============================================

	private static final Log logger = LogFactory.getLog(ResourceCache.class);

	// ~ Instance fields
	// ========================================================

	private Cache cache;

	// ~ Methods
	// ================================================================

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}

	public ResourceDetails getAuthorityFromCache(String resString) {
		Element element = null;

		try {
			element = cache.get(resString);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Cache hit: " + (element != null) + "; resString: "
					+ resString);
		}

		if (element == null) {
			return null;
		} else {
			return (ResourceDetails) element.getValue();
		}
	}

	public void putAuthorityInCache(ResourceDetails resourceDetails) {
		Element element = new Element(resourceDetails.getResString(),
				resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.cache.put(element);
	}

	public void removeAuthorityFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		this.cache.remove(resString);
	}

	/**
	 * 移除所有资源对应的角色信息
	 * 
	 */
	public void removeAllResAuthority() {
		this.cache.removeAll();
	}

	public List getUrlResStrings() {
		return getResourcesByType(Constants.RESOURCE_URL);
	}

	public List getFunctions() {
		return getResourcesByType(Constants.RESOURCE_FUNCTION);
	}

	public List getComponents() {
		return getResourcesByType(Constants.RESOURCE_COMPONENT);
	}

	private List getResourcesByType(String type) {
		List resources;
		List resclist = new ArrayList();
		try {
			resources = this.cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage());
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage());
		}
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String resString = (String) iter.next();

			ResourceDetails rd = getAuthorityFromCache(resString);
			if (rd != null)
				if (rd.getResType().equals(type))
					resclist.add(resString);
		}
		return resclist;
	}

}
