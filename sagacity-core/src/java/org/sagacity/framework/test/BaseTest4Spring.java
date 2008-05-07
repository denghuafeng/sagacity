/**
 * 
 */
package org.sagacity.framework.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * 
 * @project sagacity-core
 * @description:$
 *          <p>
 *          针对spring 的单元测试底层基类
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseTest4Spring.java,Revision:v1.0,Date:May 4, 2008 9:57:38 AM $
 */
public class BaseTest4Spring extends
		AbstractTransactionalDataSourceSpringContextTests {
	public String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { "classpath*:/**/applicationContext-*.xml",
				"classpath*:/**/dao/applicationContext-*.xml",
				"classpath*:/**/*bean.xml",
				"classpath*:/**/*beans.xml",
				"classpath*:META-INF/applicationContext-*.xml" };
	}

}
