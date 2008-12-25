/**
 * 
 */
package org.sagacity.framework.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * @project sagacity-core
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseSpringTest.java,Revision:v1.0,Date:May 26, 2008 11:01:36 PM $
 */
public class BaseSpringTest extends
		AbstractTransactionalDataSourceSpringContextTests {

	public String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		this.setDefaultRollback(false);
		return new String[] { "classpath*:/**/applicationContext-test.xml"};
	}

	
}
