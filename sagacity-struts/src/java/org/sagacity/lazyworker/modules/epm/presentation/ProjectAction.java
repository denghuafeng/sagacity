/**
 * 
 */
package org.sagacity.lazyworker.modules.epm.presentation;

import org.sagacity.framework.web.plugin.struts2.BaseActionSupport;
import org.sagacity.lazyworker.modules.epm.service.ProjectService;

/**
 * 
 * @author chenrenfei
 * 
 */
public class ProjectAction extends BaseActionSupport {

	private ProjectService projectService;

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public String save() throws Exception {
		return UPDATE;
	}
}
