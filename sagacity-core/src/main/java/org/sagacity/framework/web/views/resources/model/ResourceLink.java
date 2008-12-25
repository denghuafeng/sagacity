/**
 * 
 */
package org.sagacity.framework.web.views.resources.model;

/**
 *@project sagacity-core 
 *@description:$<p>请在此说明此文件的功能</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourceLink.java,Revision:v1.0,Date:2008-11-11 下午04:14:08 $
 */
public class ResourceLink {
	private String id;
	private String name;
	private String description;
	
	//like <script src=""></script>
	private String linkHtml;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkHtml() {
		return linkHtml;
	}

	public void setLinkHtml(String linkHtml) {
		this.linkHtml = linkHtml;
	}
	
	
}
