/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

/**
 * @project abchina
 * @description:$
 *          <p>
 *          页面输出checkBox或radio的标签
 *          </p>$ 用法说明: <abc:choose property="" datasource="" mode="checkbox"
 *          scope="request" filter="checked" displayProperties="" lineCnt="4">
 *          <tr>
 * @loop(
 *          <td><input type='checkbox'
 * @checked(#{value}) value='#{value}'></td>) </tr>
 *                    </abc:choose>
 * 
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:CheckBoxTag.java,Revision:v1.0,Date:Jun 30, 2008 2:59:37 PM $
 */
public class ChooseTag extends BaseTagSupport {
	/**
	 * 模式：分checkbox和radio两种
	 */
	private String mode = "checkbox";

	/**
	 * 显示的数据属性
	 */
	private String displayProperties;

	/**
	 * 过滤，如果filter="checked"，表示只显示被选中的的记录
	 */
	private String filter;

	/**
	 * 每行记录循环次数
	 */
	private String lineCnt="4";

	/**
	 * select选中的值的数组
	 */
	private Object[] selectedAry;

	/** @todo Constructor ---------------------------------------------------- */
	/** @todo Method Definition ---------------------------------------------- */
	public int doStartTag() throws JspException {
		try {
			// 获取选项
			Object dataSource = this.getDataResource();

			if (dataSource == null) {
				System.out.println("this choose Datasource is Null");
				return this.SKIP_BODY;
			}

			// 选中的数值
			Object selectAry = this.getPropertyValue();
			if (selectAry != null) {
				if (selectAry instanceof Object[])
					selectedAry = (Object[]) selectAry;
				else
					selectedAry = new Object[] { selectAry };
			}
			List optionList = TagUtil.getInstance().reflactList(dataSource,
					this.displayProperties, true, 1);
			this.displayProperties = "choose_seqNo,"
					+ this.displayProperties.trim();
			HashMap paramHash = TagUtil.getInstance().parseParams(this.displayProperties);

			printHtmlToPage(optionList, paramHash);
			return this.SKIP_BODY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	public int doAfterBody() {
		return this.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		release();
		return this.EVAL_PAGE;
	}

	/**
	 * @todo release Memery Source
	 */
	public void release() {
		this.releaseSource();
		this.selectedAry = null;
		this.mode = "checkbox";
		this.displayProperties = null;
		this.filter=null;
		this.lineCnt="4";

	}

	/** @todo Support Function ----------------------------------------------- */

	/**
	 * out print to jsp page 向页面输出html select
	 * 
	 * @param writer
	 *            JspWriter
	 * @throws JspException
	 */
	private void printHtmlToPage(List dataList, HashMap propertiesMap)
			throws JspException {
		try {
			JspWriter writer = pageContext.getOut();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the displayProperties
	 */
	public String getDisplayProperties() {
		return displayProperties;
	}

	/**
	 * @param displayProperties
	 *            the displayProperties to set
	 */
	public void setDisplayProperties(String displayProperties) {
		this.displayProperties = displayProperties;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the lineCnt
	 */
	public String getLineCnt() {
		return lineCnt;
	}

	/**
	 * @param lineCnt
	 *            the lineCnt to set
	 */
	public void setLineCnt(String lineCnt) {
		this.lineCnt = lineCnt;
	}
}
