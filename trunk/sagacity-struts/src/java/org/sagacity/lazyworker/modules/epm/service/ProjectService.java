/**
 * 
 */
package org.sagacity.lazyworker.modules.epm.service;

import org.sagacity.framework.web.view.PaginationModel;
import org.sagacity.lazyworker.exception.ServiceException;

/**
 * @author chenrenfei
 * 
 */
public interface ProjectService {
	/**
	 * 项目分页查询
	 * 
	 * @param pageModel
	 * @return
	 * @throws ServiceException
	 */
	public PaginationModel queryProjects(PaginationModel pageModel)
			throws ServiceException;
	
	/**
	 * 
	 * @throws ServiceException
	 */
	public void createProject() throws ServiceException;
}
