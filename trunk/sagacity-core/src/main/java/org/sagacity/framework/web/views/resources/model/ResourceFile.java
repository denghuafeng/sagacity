/**
 * 
 */
package org.sagacity.framework.web.views.resources.model;

import java.io.Serializable;

/**
 *@project sagacity-core
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourceFile.java,Revision:v1.0,Date:2008-11-11 下午04:12:20 $
 */
public class ResourceFile implements Serializable {
	private String name;
	private String id;
	private String description;
	private String url;
	private String resource;
	private String unzipPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getUnzipPath() {
		return unzipPath;
	}

	public void setUnzipPath(String unzipPath) {
		this.unzipPath = unzipPath;
	}
}
