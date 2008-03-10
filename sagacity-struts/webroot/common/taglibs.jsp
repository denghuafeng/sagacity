<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<s:set name="contextPath" value="<%=request.getContextPath()%>" />
<s:set name="basePath"
	value="<%=(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/")%>" />
<s:set name="image_path" value="<%=request.getContextPath()+"/resources/images"%>" />

