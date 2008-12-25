/**
 * 
 */
package org.sagacity.framework.orm;

/**
 * @Project:wisdom-framework
 * @Description:
 * @author chenrf
 * @version $Id: ModelConverterSupport.java,v 1.1 2008/06/05 11:09:19 nb_chenrenfei Exp $
 */
public abstract class ModelConverterSupport {
	/**
	 * 创建一个VO
	 * @param entity
	 * @return
	 */
	public abstract Object createValueObject(Object entity);
	
	/**
	 * 将VO转为PO
	 * @param vo
	 * @return
	 */
	public abstract Object createEntityObject(Object vo);
	
	public void updateEntity(Object entity,Object vo)
	{
		
	}
}
