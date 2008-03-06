<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title><s:text name="application.title"/>
		</title>
	</head>
	<body>
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
			<s:actionerror/>
			<s:actionmessage/>
			<s:fielderror/>
			<decorator:body />
		</div>
	</body>
</html>

