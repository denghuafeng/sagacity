/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.sagacity.framework.Constants;
import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.resources.ResourceWrapper;
import org.sagacity.framework.web.views.resources.model.ResourceLink;
import org.sagacity.framework.log.Log;
import org.sagacity.framework.log.LogFactory;

/**
 * @project abchina
 * @description:$ <p>
 *                为标签提供基础一些方法，如绑定数据值获取，list型标签的数据源获取以及一些标签常用的 属性映射
 *                </p>
 *                $
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:BaseTagSupport.java,Revision:v1.0,Date:May 21, 2008 9:59:17 AM $
 */
public class BaseTagSupport extends BodyTagSupport {
	protected final Log logger = LogFactory.getFactory().getLog(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279676995742701715L;

	protected String name = Constants.BEAN_KEY;
	protected String datasource = null;
	protected String property = null;
	protected String scope = "request";
	protected String readonly;
	protected String disabled;
	protected String tabindex;
	protected String onmouseover;
	protected String onfocus;
	protected String onclick;
	protected String onblur;
	protected String onchange;
	protected String size;
	protected String defaultValue;
	protected String maxlength;
	protected String onkeypress;
	protected String onkeydown;
	protected String style;
	protected String autobind = "true";
	protected String value;
	protected String styleId;
	protected String align;
	protected String valign;
	protected String onmouseout;
	protected String onkeyup;

	protected static final String IS_EXPORT_PARAM = "xtable_is_export";

	/**
	 * 导出文件判断结果
	 */
	public static final String IS_EXPORT_PARAM_RESULT = "true";
	/**
	 * 数据容器
	 */
	protected String dataContainer = "pojo";

	/**
	 * 列表是否导出数据
	 * 
	 * @return
	 */
	protected boolean isExportData() {
		String isExport = null;
		try {
			isExport = this.pageContext.getRequest().getParameter(
					this.IS_EXPORT_PARAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isExport == null)
			return false;
		if (isExport.equalsIgnoreCase(IS_EXPORT_PARAM_RESULT))
			return true;
		else
			return false;
	}

	/**
	 * 获取页面标签的数据源和数据集合
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Object getDataResource() throws JspException {
		try {
			Object data = null;
			// struts2 or webwork
			if (Constants.getKeyValue(Constants.MVC_TYPE).equalsIgnoreCase(
					"struts2"))
				data = TagUtil.getInstance().findValue(pageContext,
						this.datasource);
			else
				data = TagUtil.getInstance().lookup(pageContext, this.name,
						this.datasource, this.scope);
			if (data != null) {
				return data;
			}
		} catch (Exception e) {
			logger.error("JspTag Error:datasource is empty from Struts Form!",
					e.getCause());
		}
		try {
			/*
			 * 从scope中获取数据对象,如果为空则判断datasource是否为参数对象中的方法
			 * 如:scope为request,datasource为pageData.items
			 * pageData在request中的attribute， items为attribute对应的属性
			 */
			try {
				Object data = TagUtil.getInstance().lookup(pageContext,
						this.datasource, this.scope);
				if (data != null) {
					dataContainer = "attribute";
					return data;
				}
			} catch (Exception e) {
				logger.error("JspTag Error:it's empty in scope!");
			}
			if (this.datasource.indexOf(".") != -1) {
				dataContainer = "attribute";
				return new BeanUtils().getProperty(TagUtil.getInstance()
						.lookup(
								pageContext,
								this.datasource.substring(0, this.datasource
										.indexOf(".")), this.scope),
						this.datasource
								.substring(this.datasource.indexOf(".") + 1));
			}
		} catch (Exception e) {
			logger.error("JspTag Error:it's empty in scope!");
		}

		return null;
	}

	/**
	 * 获取页面标签的值，主要针对text,select,日期,文件等组件
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Object getPropertyValue() throws JspException {
		// 默认从form的属性中获取对象,如果为空则直接从scope中获取对象
		try {
			Object data = null;
			if (Constants.getKeyValue(Constants.MVC_TYPE).equalsIgnoreCase(
					"struts2"))
				data = TagUtil.getInstance().findValue(pageContext,
						this.property);
			else
				TagUtil.getInstance().lookup(pageContext, this.name,
						this.property, this.scope);
			if (data != null)
				return data;
		} catch (Exception e) {
			logger.error("JspTag Error:Form Data is Empty");
		}

		// 从scope中获取参数的值
		try {
			return TagUtil.getInstance().lookup(pageContext, this.property,
					this.scope);
		} catch (Exception e) {
			logger.error("JspTag Error!", e.getCause());
		}

		return null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the datasource
	 */
	public String getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource
	 *            the datasource to set
	 */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @param property
	 *            the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the tabindex
	 */
	public String getTabindex() {
		return tabindex;
	}

	/**
	 * @param tabindex
	 *            the tabindex to set
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * @return the onmouseover
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * @param onmouseover
	 *            the onmouseover to set
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * @return the onfocus
	 */
	public String getOnfocus() {
		return onfocus;
	}

	/**
	 * @param onfocus
	 *            the onfocus to set
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * @return the onclick
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick
	 *            the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return the onblur
	 */
	public String getOnblur() {
		return onblur;
	}

	/**
	 * @param onblur
	 *            the onblur to set
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * @return the onchange
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange
	 *            the onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the maxlength
	 */
	public String getMaxlength() {
		return maxlength;
	}

	/**
	 * @param maxlength
	 *            the maxlength to set
	 */
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * @return the onkeypress
	 */
	public String getOnkeypress() {
		return onkeypress;
	}

	/**
	 * @param onkeypress
	 *            the onkeypress to set
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * @return the onkeydown
	 */
	public String getOnkeydown() {
		return onkeydown;
	}

	/**
	 * @param onkeydown
	 *            the onkeydown to set
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the autobind
	 */
	public String getAutobind() {
		return autobind;
	}

	/**
	 * @param autobind
	 *            the autobind to set
	 */
	public void setAutobind(String autobind) {
		this.autobind = autobind;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the styleId
	 */
	public String getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId
	 *            the styleId to set
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @todo release Memery Source
	 */
	protected void releaseSource() {
		this.datasource = null;
		this.property = null;
		this.scope = "request";
		this.tabindex = null;
		this.onmouseover = null;
		this.onmouseout = null;
		this.onfocus = null;
		this.onclick = null;
		this.onblur = null;
		this.onchange = null;
		this.size = null;
		this.defaultValue = null;
		this.maxlength = null;
		this.onkeypress = null;
		this.onkeyup = null;
		this.onkeydown = null;
		this.style = null;
		this.autobind = "true";
		this.value = null;
		this.styleId = null;
		this.dataContainer = "pojo";
		this.align = null;
		this.readonly = null;
		this.disabled = null;
	}

	protected String getTagParamsContent() {
		StringBuffer content = new StringBuffer();
		if (this.styleId != null)
			content.append(" id=\"" + this.styleId + "\"");
		if (this.style != null)
			content.append(" style=\"" + this.style + "\"");
		if (this.onblur != null)
			content.append(" onblur=\"" + this.onblur + "\"");
		if (this.onclick != null)
			content.append(" onclick=\"" + this.onclick + "\"");
		if (this.onchange != null)
			content.append(" onchange=\"" + this.onchange + "\"");
		if (this.onfocus != null)
			content.append(" onfocus=\"" + this.onfocus + "\"");
		if (this.align != null)
			content.append(" align=\"" + this.align + "\"");
		if (this.valign != null)
			content.append(" valign=\"" + this.valign + "\"");
		if (this.onmouseover != null)
			content.append(" onmouseover=\"" + this.onmouseover + "\"");
		if (this.onmouseout != null)
			content.append(" onmouseout=\"" + this.onmouseout + "\"");
		if (this.onkeydown != null)
			content.append(" onkeydown=\"" + this.onkeydown + "\"");
		if (this.onkeyup != null)
			content.append(" onkeyup=\"" + this.onkeyup + "\"");
		if (this.onkeypress != null)
			content.append(" onkeypress=\"" + this.onkeypress + "\"");
		if (this.tabindex != null)
			content.append(" tabindex=\"" + this.tabindex + "\"");
		if (this.size != null)
			content.append(" size=\"" + this.size + "\"");
		if (this.maxlength != null)
			content.append(" maxlength=\"" + this.maxlength + "\"");
		if (this.readonly != null)
			content.append(" readonly=\"" + this.readonly + "\"");
		if (this.disabled != null)
			content.append(" disabled=\"" + this.disabled + "\"");
		return content.toString();
	}

	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align
	 *            the align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * @return the valign
	 */
	public String getValign() {
		return valign;
	}

	/**
	 * @param valign
	 *            the valign to set
	 */
	public void setValign(String valign) {
		this.valign = valign;
	}

	/**
	 * @return the onmouseout
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * @param onmouseout
	 *            the onmouseout to set
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * @return the onkeyup
	 */
	public String getOnkeyup() {
		return onkeyup;
	}

	/**
	 * @param onkeyup
	 *            the onkeyup to set
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * @return the readonly
	 */
	public String getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly
	 *            the readonly to set
	 */
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the disabled
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * 渲染输出资源对应的css,js等
	 * 
	 * @param componentId
	 */
	public void renderResources(final String componentId, JspWriter writer)
			throws IOException {	
		String ctxPath = pageContext.getServletContext().getContextPath();
		List linksHtml = new ArrayList();
		List tmp = ResourceWrapper.getComponentLinks(componentId);
		List base = ResourceWrapper
				.getComponentLinks(Constants.COMPONENT_RESOURCE_BASE);
		if (base != null && !base.isEmpty())
			linksHtml.addAll(base);
		if (tmp != null && !tmp.isEmpty())
			linksHtml.addAll(tmp);
		
		if (linksHtml.isEmpty())
			return;
		HashMap resourceMap;
		Object obj = this.pageContext
				.getAttribute(Constants.SAGACITY_COMPONENT_RESOURCES);
		if (obj != null)
			resourceMap = (HashMap) obj;
		else {
			resourceMap = new HashMap();
		}
		ResourceLink link;
		String linkHtml;
		for (Iterator iter = linksHtml.iterator(); iter.hasNext();) {
			link = (ResourceLink) iter.next();
			linkHtml = StringUtil.replaceStr(link.getLinkHtml(),
					Constants.CONTEXT_PATH_MACO, ctxPath);
			// 页面没有输出过
			if (resourceMap.get(linkHtml) == null) {
				if (writer != null) {
					writer.println(linkHtml);
				} else
					this.pageContext.getOut().println(linkHtml);
				resourceMap.put(linkHtml, linkHtml);
			}
		}
		this.pageContext.setAttribute(Constants.SAGACITY_COMPONENT_RESOURCES,
				resourceMap);
	}
}
