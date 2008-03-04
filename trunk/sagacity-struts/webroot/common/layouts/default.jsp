<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title><decorator:title default="上海牡丹香精香料信息管理系统" />
		</title>
		<link type="text/css"
			href="<c:out value="${contextPath}"/>/resources/styles/zxStyle.css"
			rel="stylesheet">
		<link type="text/css"
			href="<c:out value="${contextPath}"/>/resources/styles/base_style.css"
			rel="stylesheet">
		<link rel="stylesheet" type="text/css"
			href="<c:out value="${contextPath}"/>/resources/styles/dhtmlXGrid.css">
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/datepicker.js"></script>
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/messages.js"></script>
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/outlookbar.js"></script>
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/clientinterface.js"></script>
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/table_js.js"></script>
		<script language="javascript"
			src="<c:out value="${contextPath}"/>/resources/scripts/zxFoundation.js"></script>
		<script type='text/javascript'
			src="<c:out value="${contextPath}"/>/resources/scripts/dhtmlXGrid/dhtmlXCommon.js"></script>
		<script type='text/javascript'
			src="<c:out value="${contextPath}"/>/resources/scripts/dhtmlXGrid/dhtmlXGrid.js"></script>
		<script type='text/javascript'
			src="<c:out value="${contextPath}"/>/resources/scripts/dhtmlXGrid/dhtmlXGridCell.js"></script>
	</head>
	<body>
		<html:html locale="true" />
		<div id="screen">
			<table id="wsd_title">
				<tr>
					<td class="wsd_titlefont">
						您当前的位置
					</td>
					<td class="wsd_2rightarrow">
						&nbsp;
					</td>
					<td class="wsd_titlefont2">
						<decorator:getProperty property="page.moduleName" />
					</td>
					<td class="wsd_1rightarrow">
						&nbsp;
					</td>
					<td class="wsd_titlefont1">
						<decorator:getProperty property="page.navigation" />
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<logic:messagesPresent>
				<bean:message key="errors.header" />
				<ul>
					<html:messages id="error">
						<li>
							<bean:write name="error" />
						</li>
					</html:messages>
				</ul>
			</logic:messagesPresent>

			<logic:messagesPresent message="true">
				<html:messages id="messages" message="true" header="messages.header"
					footer="messages.footer">
						messageAdd("<bean:write name="messages" />");
					</html:messages>
			</logic:messagesPresent>
			<decorator:body />
		</div>
	</body>
</html>

