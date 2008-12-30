/**
 * 
 */
package org.sagacity.framework.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @project regs
 * @description:$
 *          <p>
 *          用来匹配url
 *          </p>$
 * @author chenrenfei $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:UrlMatcher.java,Revision:v1.0,Date:Jun 12, 2008 9:41:07 PM $
 */
public class UrlMatcher {
	public static boolean matches(String url, String urlPattern) {
		StringBuffer buff = new StringBuffer();
		buff.append('^');
		buff.append(urlPattern.replaceAll("\\.", "\\\\.").replaceAll("\\?",
				"\\\\?").replaceAll("\\*", ".*"));
		buff.append("$");
		Pattern pattern = Pattern.compile(buff.toString());
		Matcher matcher = pattern.matcher(url);
		boolean found = matcher.find();
		return found && matcher.group().equals(url);
	}
	
	public static void main(String[] args)
	{
		String url="http://localhost:8080/regs/irregs/irregEventAction.do?method=showEditDerg&matId=378";
		System.err.println(UrlMatcher.matches(url, "http://localhost:8080/regs/irregs/irregEventAction.do?method=showEditDerg*"));
	}
}
