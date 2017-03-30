<%@page import="util.FileUtil"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Merkle Tree</title>
</head>
<body>
	<h2>Merkle Tree</h2>
	<h3>使用一个账户名登录,该账户名将作为服务器文件的根目录使用</h3>
	<%
		String userPath = "";%>
	<form action="dologin.jsp" name="userForm" method="post">
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" name="userPath"></td>
			<tr />
			<tr>
				<td colspan="2"><input type="submit" name="登陆"></td>
			<tr />
		</table>
	</form>
</body>
</html>