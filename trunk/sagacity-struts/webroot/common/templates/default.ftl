<#macro page hasBody>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><@s.text name="application.title"/></title>
	<#global contextPath = Request.contextPath/>
	<#global image_path = "${contextPath}/resources/images"/>
	<#include "${contextPath}/common/meta.ftl">
	</head>
	<#if hasBody=="true"><body></#if>
    <#nested> 
    <#if hasBody=="true"></body></#if>
</html>
</#macro>