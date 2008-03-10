<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>错误提示页面</title>
		<%@ include file="/common/meta.jsp"%>
		<style type="text/css">
			A:visited{TEXT-DECORATION: none}
			A:active{TEXT-DECORATION: none}
			A:hover{TEXT-DECORATION: underline overline}
			A:link{text-decoration: none;}
			.t{LINE-HEIGHT: 1.4}
			BODY{FONT-SIZE: 9pt;
			SCROLLBAR-HIGHLIGHT-COLOR: buttonface;
			SCROLLBAR-SHADOW-COLOR: buttonface;
			SCROLLBAR-3DLIGHT-COLOR: buttonhighlight;
			SCROLLBAR-TRACK-COLOR: #eeeeee;
			SCROLLBAR-DARKSHADOW-COLOR: buttonshadow}
			TD,DIV,form,OPTION,P,TD,BR{FONT-SIZE: 9pt} 
			INPUT{BORDER-TOP-WIDTH: 1px; PADDING-RIGHT: 1px; PADDING-LEFT: 1px; BORDER-LEFT-WIDTH: 1px; FONT-SIZE: 9pt; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 1px; BORDER-BOTTOM-COLOR: #cccccc; PADDING-BOTTOM: 1px; BORDER-TOP-COLOR: #cccccc; PADDING-TOP: 1px; HEIGHT: 18px; BORDER-RIGHT-WIDTH: 1px; BORDER-RIGHT-COLOR: #cccccc}
			textarea, select {border-width: 1; border-color: #000000; background-color: #efefef; font-size: 9pt; font-style: bold;}
			TABLE {BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 1px; }
			TD {BORDER-RIGHT: 0px; BORDER-TOP: 0px; color: #333333; }
			.select {FONT-SIZE: 9pt; WIDTH: 150px}
			.jumpto {FONT-SIZE: 9pt; WIDTH: 150px}
			</style>
	</head>
	<content tag="moduleName">
	系统提示
	</content>
	<content tag="navigation">
	业务操作错误提示
	</content>
	<body bgcolor=#ffffff alink=#333333 vlink=#333333 link=#333333
		topmargin=0 leftmargin=0>
		<p>
			<script language="JavaScript">
				function HighlightAll(theField) {
				  var tempval=eval("document."+theField)
				  tempval.focus()
				  tempval.select()
				  therange=tempval.createTextRange()
				  therange.execCommand("Copy")
				}
				function DoTitle(addTitle) {
				  var revisedTitle;
				  var currentTitle = document.submitok.postTitle.value;
				  revisedTitle = currentTitle+addTitle;
				  document.submitok.postTitle.value=revisedTitle;
				  document.submitok.postTitle.focus();
				  return;
				}						
		</script>
		<table cellpadding=0 cellspacing=0 border=0 width=95% bgcolor=#000000
			align=center>
			<tr>
				<td>
					<table cellpadding=6 cellspacing=1 border=0 width=100%>
						<tr>
							<td bgcolor=#ffffff align=center>
								<font color=#333333><b> 对不起，您的操作没有成功，请查看以下原因: </b> </font>
							</td>
						</tr>
						<tr>
							<td bgcolor=#ffffff align=center>
								错误:<s:actionerror/>
							</td>
						</tr>
						<tr>
							<td bgcolor=#C4D0DF>
								<font color=#333333> 如果浏览器没有自动返回，请点击下面的链接！
									<ul>
										<li>
											<a href="<%=request.getRequestURI()%>">返回</a>
										</li>
									</ul> <%--<meta http-equiv="refresh"
										content="3;url=<%=request.getRequestURI()%>"> --%>
								</font>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<br>
	</body>
</html>
