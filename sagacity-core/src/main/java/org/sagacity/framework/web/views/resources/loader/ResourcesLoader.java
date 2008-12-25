/**
 * 
 */
package org.sagacity.framework.web.views.resources.loader;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.sagacity.framework.Constants;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;
import org.sagacity.framework.utils.FileUtil;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.resources.ResourceWrapper;
import org.sagacity.framework.web.views.resources.model.ResourceFile;

/**
 * 
 *@project sagacity-core
 *@description:$<p>用来解压框架所提供的组件相关的资源文件,包括:css、js、images,并将其放置 于web应用资源目录下</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourcesLoader.java,Revision:v1.0,Date:2008-11-10 上午12:50:26 $
 */
public class ResourcesLoader {
	protected final static Log logger = LogFactory.getFactory().getLog(ResourcesLoader.class);
	/**
	 * 当前组件版本号，做全局定义目的在于写入组件版本属性文件中(暂时采用非重写方式)
	 */
	private static String versionNo;

	/**
	 * 组件配置文件,根据版本号(替换${version}宏)决定采用相应版本的文件
	 */
	private final static String COMPONENT_CONFIG_FILE = "/components/sagacity-component${version}.xml";

	/**
	 * 组件版本文件
	 */
	private final static String COMPONENT_VERSION_FILE = "/resources/components/sagacity.components.version.properties";

	/**
	 * 解压处理组件的资源文件到应用的相关路径下
	 * 
	 * @param webCtxPath
	 */
	public static void load(final String webCtxPath)
			throws LoadResourcesException {
		// 当前组件版本号
		versionNo = Constants.getKeyValue(Constants.COMPONENT_RES_VERSION);
		// 版本对应的配置文件
		String componentFile = StringUtil.replaceStr(COMPONENT_CONFIG_FILE,
				"${version}", versionNo);
		List components = ResourceWrapper.getComponentFileConfig(componentFile);
		if (components != null && !components.isEmpty()) {
			ResourceFile resModel = null;
			for (Iterator iter = components.iterator(); iter.hasNext();) {
				resModel = (ResourceFile) iter.next();
				loadResource(resModel, webCtxPath);
			}
		}
	}

	/**
	 * 验证版本决定是否解压文件
	 * 
	 * @return
	 */
	/*
	 * private boolean validateVersion() { return true; }
	 */

	/**
	 * 解压资源到web应用指定的路径下 目前不采取比较版本文件方式，采用非覆盖解压模式,对于已经存在的文件不做修改
	 * 
	 * @param pResources
	 * @param pDestPath
	 * @throws Exception
	 */
	private static void loadResource(ResourceFile resModel, String webHome)
			throws LoadResourcesException {
		String exportDir = webHome
				+ (resModel.getUnzipPath().indexOf(File.separator) != 0 ? File.separator
						: "") + resModel.getUnzipPath();
		try {
			InputStream resources = ResourcesLoader.class
					.getResourceAsStream(resModel.getResource());
			ZipInputStream zis = new ZipInputStream(resources);
			FileUtil.unzip(zis, exportDir, false);
		} catch (Exception e) {
			final String MSG = "解压缩资源文件:" + resModel.getResource() + " 到 "
					+ resModel.getUnzipPath() + "失败!";
			logger.error(MSG);
			throw new LoadResourcesException(MSG, e);
		}
	}
}
