<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page
	import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page
	import="org.acegisecurity.userdetails.UsernameNotFoundException"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>

		<title>上海牡丹香精香料有限公司信息化管理系统</title>
		<link
			href="<s:property value="#contextPath"/>/resources/styles/base_style.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript">
	function initWindow() { 
		loginForm.j_username.focus();

	}
	
    function login(target) 
    {    	
    	if(target.j_username.value=="" || target.j_password.value=="")
    	{
    		alert("用户名和密码不能为空!")
    		if(target.j_username.value=="")
    			target.j_username.focus();
    		else
    		   	target.j_password.focus();
    		return false;
    	}
  		else
  			target.submit();
	}
	
	function resetLogin() { 
		loginForm.j_username.value = "";
		loginForm.j_password.value = "";
		return false;
	}
	
	function keyFilter(obj)
	{
		if(event.keyCode==13)
			return login(loginForm);
		else 
			event.returnValue = true;
	}
</script>
	</head>
	<body class="wsd_login_body" onload="initWindow();">

		<s:if test="#param.login_error!=null">
			<div class="error">
				<s:if test="#param.login_error=='user_psw_error'">
					<font color="red">无效用户名或密码，请重试！</font>
					<s:elseif test="#param.login_error == 'user_not_found_error'">
						<font color="red">该用户名不存在，请重试!</font>
					</s:elseif>
					<s:elseif test="#param.login_error == 'too_many_user_error'">
						<font color="red">多处使用同一用户名登陆,请等候！</font>
					</s:elseif>
					<s:else>
						<font color="red">登陆时意外错误,请稍后再试！</font>
					</s:else>
				</s:if>
			</div>
		</s:if>

		<form name="loginForm"
			action="<s:url value='j_acegi_security_check'/>" method="post">
			<%--<input type="checkbox" name="_acegi_security_remember_me" style="display:none;" checked="true">
	--%>
			<table cellpadding="0" cellspacing="0" border="0"
				class="wsd_login_psn">
				<tr>
					<td>
						<img src="<s:property value="#image_path"/>/login/login_01.gif">
					</td>
				</tr>
				<tr>
					<td width="671" height="123"
						background="<s:property value="#image_path"/>/login/login_02.gif">
						<table cellpadding="0" cellspacing="0" border="0" width="100%"
							height="123">
							<tr>
								<td width="275">
									&nbsp;
								</td>
								<td>
									<table cellpadding="0" cellspacing="0" border="0" width="100%"
										height="117">
										<tr>
											<td height="35" class="wsd_login_userntex">
												用户名:&nbsp;
												<input type="text" name="j_username" class="wsd_logininput"
													tabindex=1 onkeyDown="keyFilter(this);">
											</td>
										</tr>
										<tr>
											<td height="37" class="wsd_login_userntex">
												密&nbsp;&nbsp;&nbsp;码:&nbsp;
												<input type="password" name="j_password"
													class="wsd_logininput" tabindex=2
													onkeyDown="keyFilter(this);">
											</td>
										</tr>
										<tr>

											<td height="45">
												&nbsp;&nbsp;
												<input type="button" class="wsd_login_but"
													value="登&nbsp;&nbsp;录" onclick="return login(loginForm)"
													tabindex=3>
												&nbsp;&nbsp;
												<input name="重置" type="reset" class="wsd_login_but"
													value="重&nbsp;&nbsp;置" onclick="return resetLogin()"
													tabindex=4>
											</td>
										</tr>
									</table>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<img src="<s:property value="#image_path"/>/login/login_03.gif">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
