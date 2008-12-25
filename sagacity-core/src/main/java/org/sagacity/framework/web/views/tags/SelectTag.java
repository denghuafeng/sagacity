/**
 * 
 */
package org.sagacity.framework.web.views.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;

/**
 * 
 * @project abchina
 * @description:$
 *          <p>
 *          下拉选择列表组件，提供display和value的简单组合如：display为pojo中的两个属性的组合
 *          example:display="{orgName}/[orgNo]"也可以是数组中的一个或多个值的组合 使用方式:<abc:select
 *          datasource="orgInfoList" value="{orgNo}"
 *          display="{orgName}/[{orgNo}]" onchange=""/> 对象数组形式：<abc:select
 *          datasource="orgInfoList" value="{item[0].orgNo}"
 *          display="{item[1].orgName}/[{item[0].orgNo}]"> 数组形式：<abc:select
 *          datasource="orgInfoList" value="{item[0]}"
 *          display="{item[1]}/[{item[0]}]">
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:SelectTag.java,Revision:v1.0,Date:May 20, 2008 2:46:26 PM $
 */
public class SelectTag extends BaseTagSupport {
	
	//普通select,不要随便改变其定义，其用来定位资源的引用
	private final String COMPONENT_ID="select";
	
	// 是否多选
	private String multiple = "false";

	// firstName用于页面自定义首个选项
	private String firstName = null;

	// firstValue用于页面自定义首个选项对应的值
	private String firstValue = null;

	// 显示的属性
	private String display;

	/**
	 * 是否用层的方式构造select,默认为div,用层的方式解决 html传统的select遮盖住页面tree,date控件的问题,但用div的方式
	 * select 只提供单选功能
	 */
	private String mode = "div";

	/**
	 * select选中的值的数组
	 */
	private Object[] selectedAry;

	/** @todo Constructor ---------------------------------------------------- */
	/** @todo Method Definition ---------------------------------------------- */
	public int doStartTag() throws JspException {
		//判断是否表格在做导出操作，是则跳过输出select减少无用处理
		if (this.isExportData())
		{
			this.release();
			return SKIP_BODY;
		}
		
		try {
			// 获取选项
			Object dataSource = this.getDataResource();

			if (dataSource == null) {
				System.err.println("the select Data is Null");
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
		
			HashMap paramHash = TagUtil.getInstance().parseParams(this.display + this.value);

			List optionList = TagUtil.getInstance().reflactList(dataSource, TagUtil.getInstance()
					.parseParamHash(paramHash), false, 1);
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
		this.multiple = "false";
		this.firstName = null;
		this.firstValue = null;
		this.display = null;
		this.mode = "div";
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
			
			//输入组件需要的js,css等资源
			super.renderResources(this.COMPONENT_ID, writer);
			
			String[][] purityData = processData(dataList, propertiesMap);
			printSltBegin(writer, purityData);
			if (purityData.length > 0) {
				// 选项显示内容
				String optionText = null;
				// 选项值
				String optionValue = null;
				String width = this.getWidth();
				String objId = StringUtil.isNullOrBlank(this.getStyleId()) ? this
						.getProperty()
						: this.getStyleId();
				objId = objId.trim();

				for (int i = 0; i < purityData.length; i++) {
					optionValue = purityData[i][0];
					optionText = purityData[i][1];
					if (this.mode.equalsIgnoreCase("div")) {
						writer.println("<tr style=\"cursor:pointer;\">");
						writer
								.println("<td onmouseover=\"this.style.backgroundColor='#3277DE';\" onmouseout=\"this.style.backgroundColor='';\" style=\"width:"
										+ width + "px;height:20px;\"");
						if (this.align != null)
							writer.println(" align=\"" + this.align + "\"");
						writer.println(" onclick=\"dropDownSlt('" + objId
								+ "','" + optionValue + "','" + optionText
								+ "')\">");
						writer.println(optionText + "</td>");
						writer.println("</tr>");
					} else {
						writer.println("<option value=\"" + optionValue + "\""
								+ isSelected(optionValue) + ">" + optionText
								+ "</option>");
					}
				}
			}
			// 输出select组件的尾
			printSltEnd(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将数据处理成select最终显示需要的数据
	 * 
	 * @param originalList
	 * @param propertiesMap
	 * @return
	 */
	public String[][] processData(List originalList, HashMap propertiesMap) {
		int extSize = 0;
		if (this.firstName != null)
			extSize = 1;
		String[][] result = new String[(originalList == null ? 0 : originalList
				.size())
				+ extSize][2];
		if (extSize == 1) {
			result[0][0] = this.firstValue;
			result[0][1] = this.firstName;
		}
		if (originalList == null || originalList.isEmpty())
			return result;
		List item;
		for (int i = 0; i < originalList.size(); i++) {
			item = (List) originalList.get(i);	
			result[i + extSize][0] = TagUtil.getInstance().replacePlaceHolder(item,
					this.value, propertiesMap).trim();
			result[i + extSize][1] = TagUtil.getInstance().replacePlaceHolder(item,
					this.display, propertiesMap).trim();
		}
		return result;
	}

	/**
	 * 设置和输出select的样式
	 * 
	 * @throws IOException
	 */
	private void printSltBegin(JspWriter writer, String[][] purityData)
			throws IOException {
		String sltId = StringUtil.isNotNullAndBlank(this.styleId) ? this.styleId
				: this.property;
		sltId = sltId.trim();
		if (!mode.equalsIgnoreCase("div")) {
			writer.println("<select name=\"" + this.property + "\"");
			writer.println(" id=\"" + sltId + "\"");
			if (this.style != null)
				writer.println(" style=\"" + this.style + "\"");
			if (this.onblur != null)
				writer.println(" onblur=\"" + this.onblur + "\"");
			if (this.onclick != null)
				writer.println(" onclick=\"" + this.onclick + "\"");
			if (this.onchange != null)
				writer.println(" onchange=\"" + this.onchange + "\"");
			if (this.onfocus != null)
				writer.println(" onfocus=\"" + this.onfocus + "\"");
			if (this.align != null)
				writer.println(" align=\"" + this.align + "\"");
			if (this.onmouseover != null)
				writer.println(" onmouseover=\"" + this.onmouseover + "\"");
			if (this.multiple.equalsIgnoreCase("true"))
				writer.println(" multiple");
			if (this.tabindex != null)
				writer.println(" tabindex=\"" + this.tabindex + "\"");
			if (this.size != null)
				writer.println(" size=\"" + this.size + "\"");

			writer.println(">");
		} else {
			int height = purityData.length * 20;
			if (height > 200)
				height = 200;
			String selectedValue = "";
			String displayName = "";
			if (this.selectedAry != null) {
				selectedValue = this.selectedAry[0].toString();
				displayName = getDisplayName(purityData, selectedValue);
			} else {
				if (purityData.length > 0) {
					selectedValue = purityData[0][0];
					displayName = purityData[0][1];
				}
			}

			// 输出隐藏的值
			writer.println("<input type=\"hidden\"");
			if (this.onchange != null)
				writer.println(" onpropertychange=\"" + this.onchange + "\"");
			writer.println(" id=\"" + sltId + "\"");
			writer.println(" name=\"" + this.property + "\"");
			writer.println(" value=\"" + selectedValue + "\">");

			// 输出名称
			writer.println("<input readonly=\"readonly\" type=\"text\"");
			writer.println(" id=\"Name_" + sltId + "\"");
			writer.println(" name=\"Name_" + this.property + "\"");
			if (this.align != null)
				writer.println(" align=\"" + this.align + "\"");
			writer
					.println(" value=\""
							+ displayName
							+ "\" style=\"width:"
							+ this.getWidth()
							+ "px;background-image:url("
							+ ((HttpServletRequest) this.pageContext
									.getRequest()).getContextPath()
							+ "/resources/components/select/images/select_dropDown.gif);background-position:right top;background-repeat:no-repeat;\" onclick=\"onSltDropDown(this,'OptionList_"
							+ sltId + "');\" />");

			writer
					.println("<div onblur=\"if(this.value==1) return;this.style.display='none';\" name=\"hiddenDiv\"");
			writer.println(" id=\"OptionList_" + sltId + "\"");

			writer
					.println(" style=\"overflow:auto;height:0;position:absolute;z-index:1000;border:1px solid;vertical-align: top;background-color: #ffffff;display:none;width:"
							+ this.getWidth()
							+ "px;height: "
							+ height
							+ "px;\" onmouseout=\"this.value=0;this.style.display='none'\" onmouseover=\"this.value=1;this.style.display=''\">");
			writer
					.println("<table class=\"\" frame=\"void\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		}
	}

	/**
	 * 输出select组件的结尾
	 * 
	 * @param writer
	 * @throws IOException
	 */
	private void printSltEnd(JspWriter writer) throws IOException {
		if (!mode.equalsIgnoreCase("div"))
			writer.write("</select>");
		else
			writer.write("</table></div>");
	}

	/**
	 * judge the choose is selected or not 判断选项值是否被选中
	 * 
	 * @param selectValue
	 *            String
	 * @return boolean
	 */
	private String isSelected(String selectValue) {
		if (this.autobind.equalsIgnoreCase("false"))
			return "";
		if (null != selectedAry && 0 < selectedAry.length) {
			for (int i = 0; i < selectedAry.length; i++) {
				if(selectedAry[i]!=null && selectValue.equals(selectedAry[i].toString()))
					return " selected";
			}
		}
		return "";
	}

	/**
	 * 根据选中的值获取对应的显示名称
	 * 
	 * @param purityData
	 * @param selectedValue
	 * @return
	 */
	public String getDisplayName(Object[][] purityData, String selectedValue) {
		for (int i = 0; i < purityData.length; i++) {
			if (purityData[i][0].toString().equalsIgnoreCase(selectedValue))
				return purityData[i][1].toString();
		}
		return "";
	}

	/** @todo Accessor Definition -------------------------------------------- */
	/**
	 * @return the multiple
	 */
	public String getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the firstValue
	 */
	public String getFirstValue() {
		return firstValue;
	}

	/**
	 * @param firstValue
	 *            the firstValue to set
	 */
	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
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
	 * 根据size计算width
	 * 
	 * @return
	 */
	public String getWidth() {
		if (StringUtil.isNullOrBlank(this.size))
			return "130";
		else
			return "" + Integer.parseInt(this.size) * 8;
	}
}
