package org.sagacity.framework.web.views.tags;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.sagacity.framework.utils.AryUtil;
import org.sagacity.framework.utils.StringUtil;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:TagUtil.java,Revision:v1.0,Date:2008-12-11 下午02:44:36 $
 */
public class TagUtil {
	private static TagUtil me = null;

	public static TagUtil getInstance() {
		if (me == null)
			me = new TagUtil();
		return me;
	}

	/** Quote metacharacters in HTML. */
	public String quote(String x) {
		if (x == null)
			return null;
		else {
			// Enhanced performance by using a faster String replace method
			x = replace(x, "&", "&amp;");
			x = replace(x, "\"", "&quot;");
			x = replace(x, "<", "&lt;");
			x = replace(x, ">", "&gt;");
			return x;
		}
	}

	/**
	 * Efficient string replace function. Replaces instances of the substring
	 * find with replace in the string subject. karl@xk72.com
	 * 
	 * @param subject
	 *            The string to search for and replace in.
	 * @param find
	 *            The substring to search for.
	 * @param replace
	 *            The string to replace instances of the string find with.
	 */
	public String replace(String subject, String find, String replace) {
		StringBuffer buf = new StringBuffer();
		int l = find.length();
		int s = 0;
		int i = subject.indexOf(find);
		while (i != -1) {
			buf.append(subject.substring(s, i));
			buf.append(replace);
			s = i + l;
			i = subject.indexOf(find, s);
		}
		buf.append(subject.substring(s));
		return buf.toString();
	}

	/**
	 * 
	 * @param bodyContent
	 *            String
	 * @return String
	 * @author zhongxuchen
	 */
	public String clearHtmlMark(String bodyContent) {
		if (bodyContent == null)
			return "";
		// bodyContent = bodyContent.replaceAll("\\\'", "");
		int markBegin = bodyContent.indexOf("<!--", 1);
		int markEnd = bodyContent.indexOf("-->", 1);
		while (markBegin != -1 && markEnd != -1) {
			bodyContent = bodyContent.substring(0, markBegin)
					+ bodyContent.substring(markEnd + 1);
			markBegin = bodyContent.indexOf("\"", 1);
			markEnd = bodyContent.indexOf("\"", 2);
		}
		bodyContent = bodyContent.replaceAll("\\n", " ");
		bodyContent = bodyContent.replaceAll("\\r", " ");
		bodyContent = bodyContent.replaceAll("\\t", " ");
		return bodyContent;
	}

	/**
	 * 
	 * @param bodyContent
	 *            String
	 * @return String
	 * @author zhongxuchen
	 */
	public String clearMistyStr(String bodyContent) {
		bodyContent = bodyContent.replaceAll("\\\"", "");
		int quoteBegin = bodyContent.indexOf("\"", 1);
		int quoteEnd = bodyContent.indexOf("\"", 2);
		while (quoteBegin != -1 && quoteEnd != -1) {
			bodyContent = bodyContent.substring(0, quoteBegin)
					+ bodyContent.substring(quoteEnd + 1);
			quoteBegin = bodyContent.indexOf("\"", 1);
			quoteEnd = bodyContent.indexOf("\"", 2);
		}
		return bodyContent;
	}

	/**
	 * 
	 * @param bodyContent
	 *            String
	 * @return String
	 * @author zhongxuchen
	 */
	public String clearMistyChar(String bodyContent) {
		bodyContent = bodyContent.replaceAll("\\\'", "");
		int sigleQuoteStar = bodyContent.indexOf("\'", 1);
		int sigleQuoteEnd = bodyContent.indexOf("\'", 2);
		while (sigleQuoteStar != -1 && sigleQuoteEnd != -1) {
			bodyContent = bodyContent.substring(0, sigleQuoteStar)
					+ bodyContent.substring(sigleQuoteEnd + 1);
			sigleQuoteStar = bodyContent.indexOf("\'", 1);
			sigleQuoteEnd = bodyContent.indexOf("\'", 2);
		}
		return bodyContent;
	}


	/**
	 * 
	 * @param obj
	 * @param properties
	 * @param hasSequence
	 * @param startSeqNo
	 * @return
	 * @throws Exception
	 */
	public List reflactList(Object obj, String properties, boolean hasSequence,
			int startSeqNo) throws Exception {
		if (obj == null || properties == null)
			return null;
		String[] methods = null;
		if (properties.indexOf(",") != -1)
			methods = properties.split(",");
		else
			methods = new String[] { properties };
		return reflactList(obj, methods, hasSequence, startSeqNo);
	}

	/**
	 * 将数据对象通过反射器获取其属性信息并封装成List结果集返回
	 * 
	 * @param obj
	 * @param methods
	 *            [item[0].style,item[1].value]
	 * @param hasSequence
	 *            是否增加sequence
	 * @param startSeqNo
	 *            sequenceNo起始编号,一般是0和1
	 * @return
	 * @throws Exception
	 */
	public List reflactList(Object obj, String[] methods, boolean hasSequence,
			int startSeqNo) throws Exception {
		if (obj == null || methods == null || methods.length < 1)
			return null;
		List results = new ArrayList();

		// 数据源的类型：一般为List,HashMap,Set,Object[]
		int dataType = 0;

		// 是否进行类型判断标志
		boolean hasJudge = false;

		// 统一数据源为List
		List source = null;

		if (obj instanceof Object[])
			source = AryUtil.AryToList(obj);
		else if (obj instanceof List)
			source = (List) obj;
		else if (obj instanceof Set)
			source = AryUtil.AryToList(((Set) obj).toArray());
		Object item;
		String method;
		BeanUtils beanUtils = new BeanUtils();

		// 数据的长度
		int maxLength = Integer.toString(source.size()).length();
		int seqNo = startSeqNo;

		// 循环反射处理数据集合
		for (Iterator iter = source.iterator(); iter.hasNext();) {
			item = iter.next();

			// 判断集合中每行的数据类型
			if (!hasJudge) {
				if (item instanceof Object[])
					dataType = 1;
				else if (item instanceof List || item instanceof HashMap
						|| item instanceof Vector)
					dataType = 2;
				else
					dataType = 3;
			}
			hasJudge = true;
			List row = new ArrayList();

			/**
			 * 左补零补齐seqNo长度到数据列表记录数的位宽，如列表记录为20条，是两位数字，则seqNO补齐到 2位数字
			 */
			if (hasSequence)
				row.add(StringUtil.addLeftZero2Len(Integer.toString(seqNo),
						maxLength));
			for (int i = 0; i < methods.length; i++) {
				method = methods[i];
				Object colData = null;
				switch (dataType) {
				// 数组
				case 1: {
					colData = getPropertyMethod(method) != null ? beanUtils
							.getProperty(
									((Object[]) item)[getPropertyIndex(method)],
									getPropertyMethod(method))
							: ((Object[]) item)[getPropertyIndex(method)];
					break;
				}

					// 集合
				case 2: {
					colData = getPropertyMethod(method) != null ? beanUtils
							.getProperty(((List) item)
									.get(getPropertyIndex(method)),
									getPropertyMethod(method)) : ((List) item)
							.get(getPropertyIndex(method));
					break;
				}

				// POJO
				case 3: {
					colData = beanUtils.getProperty(item, method);
					break;
				}
				}
				row.add(colData);
			}
			results.add(row);
			seqNo++;
		}
		return results;
	}

	/**
	 * 提取属性中的数组序号
	 * 
	 * @author chenrenfei
	 * @param property
	 * @return
	 */
	private int getPropertyIndex(String property) {
		return Integer.parseInt(property.substring(property.indexOf("[") + 1,
				property.indexOf("]")).trim());
	}

	/**
	 * example: 1,property=item[0].name return=name 2,property=OrgInfoVO.orgNo
	 * return=OrgInfoVO.orgNo
	 * 
	 * @author chenrenfei
	 * @param property
	 * @return
	 */
	private String getPropertyMethod(String property) {
		// 非数组
		if (property.indexOf("[") == -1)
			return property.trim();
		// 数组且有字段属性
		if (property.indexOf(".") != -1)
			return property.substring(property.indexOf(".") + 1).trim();
		else
			return null;
	}

	/**
	 * 
	 * 通过正则表达式解析标签参数中对应集合中的参数信息
	 * 
	 * @author chenrenfei
	 * @param params
	 * @return
	 */
	public HashMap parseParams(String params) throws Exception {
		String regexp = "\\#\\[\\w+(\\[\\d+\\][\\.\\w+]*\\s*)?\\]";

		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(params);
		String param;
		HashMap paramHash = null;
		while (m.find()) {
			param = m.group().replaceFirst("\\#\\[", "")
					.replaceFirst("\\]", "");
			if (paramHash == null) {
				paramHash = new HashMap();
				paramHash.put(param, new Integer(0));
			} else if (paramHash.get(param) == null)
				paramHash.put(param, new Integer(paramHash.size()));
		}
		return paramHash;
	}

	/**
	 * 将hashmap中的参数转为数组，便于list类型的标签(select,table)根据参数反射出展示数据
	 * 
	 * @param params
	 * @return
	 */
	public String[] parseParamHash(HashMap params) {
		String[] methods = null;
		String paramName;
		if (params != null && !params.isEmpty()) {
			methods = new String[params.size()];
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				paramName = iter.next().toString();
				methods[((Integer) params.get(paramName)).intValue()] = paramName;
			}
		}
		return methods;
	}

	/**
	 * 切割properties在hashMap中根据各个参数放入其顺序如: orgName,orgDesc,isActive则hashMap为，
	 * hashMap.put("orgName",0) hashMap.put("orgDesc",1);
	 * hashMap.put("isActive",2);
	 * 
	 * @param properties
	 * @return
	 */
	public HashMap splitProperties(String properties) {
		HashMap result = new HashMap();
		String[] paramAry = properties.trim().split(",");
		for (int i = 0; i < paramAry.length; i++) {
			result.put(paramAry[i], new Integer(i));
		}
		return result;
	}

	/**
	 * 替换display和value中的属性占位符：如display="#[orgName]/[#[orgNo]]" 替换后："机构1/[0001]"
	 * 
	 * @param item
	 * @param placeHolderSource
	 * @param paramsHash
	 * @return
	 */
	public String replacePlaceHolder(List item, String placeHolderSource,
			HashMap paramsHash) {
		int index = 0;
		String param;
		for (Iterator iter = paramsHash.keySet().iterator(); iter.hasNext();) {
			param = (String) iter.next();
			if (placeHolderSource.indexOf("#[" + param + "]") != -1) {
				index = ((Integer) paramsHash.get(param)).intValue();
				placeHolderSource = StringUtil.replaceAllStr(placeHolderSource,
						"#[" + param + "]", item.get(index).toString());
			}
		}
		return placeHolderSource;
	}

	/**
	 * 基于struts1.x 标签的数据反射查找
	 * 
	 * @param pageContext
	 * @param name
	 * @param property
	 * @param scope
	 * @return
	 */
	public Object lookup(PageContext pageContext, String name, String property,
			String scope) throws JspException {
		return org.apache.struts.taglib.TagUtils.getInstance().lookup(
				pageContext, name, property, scope);
	}

	/**
	 * 基于struts1.x 标签的数据反射查找
	 * 
	 * @param pageContext
	 * @param property
	 * @param scope
	 * @return
	 * @throws JspException
	 */
	public Object lookup(PageContext pageContext, String property, String scope)
			throws JspException {
		return org.apache.struts.taglib.TagUtils.getInstance().lookup(
				pageContext, property, scope);
	}

	/**
	 * 基于OGNL 的valueStack模式的数据对象查询，获取堆栈值
	 * 
	 * @param pageContext
	 * @param key
	 * @return
	 */
	public Object findValue(PageContext pageContext, String key) {
		ValueStack valueStack = org.apache.struts2.views.jsp.TagUtils
				.getStack(pageContext);
		return valueStack.findValue(key);
	}
	
	

	public static void main(String[] args) throws Exception {
		String temp = "abc,tmp,name";
		System.err.println(TagUtil.getInstance().splitProperties(temp));
	}

}
