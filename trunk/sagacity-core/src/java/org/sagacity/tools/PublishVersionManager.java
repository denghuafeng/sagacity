/**
 * 
 */
package org.sagacity.tools;

/**
 *@project sagacity-core 
 *@description:$<p>提供应用程序部署时的版本更新：
 *针对的问题：在长期的项目实施过程中应用程序会经常发布版本，但由于对应用的发布没有很好的版本信息控制，在
 *应用部署后无法有效的判定本次应用的具体版本信息，此类功能提供给Ant或Maven在应打包时对系统版本文件进行
 *修改，一般要求在系统层面上要求有一about "xxx":关于本应用的url菜单链接，本功能主要针对此类的版本信息
 *文件进行修改，增加时间戳以及必要的信息，以方面确认该应用包的版本。
 *
 *</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:PublishVersionManager.java,Revision:v1.0,Date:Mar 7, 2008 12:54:18 PM $
 */
public class PublishVersionManager {
	/**
	 * 
	 */
	private String configPropertyFile;
	
	/**
	 * 版本文件
	 */
	private String versionFile;
	
	/**
	 * 
	 */
	public void process()
	{
		
	}
}
