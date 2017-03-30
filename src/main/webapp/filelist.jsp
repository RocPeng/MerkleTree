<%@page import="model.FileBean"%>
<%@page import="dao.DaoManager"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="util.FileUtil"%>
<%@page import="java.awt.Window"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件列表页面</title>
</head>
<body>
	<h1>文件列表</h1>
	<a href="adduser.jsp">上传文件</a>&nbsp;&nbsp;
	<a href="index.jsp">返回首页</a>&nbsp;&nbsp;
	<hr>
	<%
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getParameter("userPath");
		FileUtil.setUserPath(userPath);
		List<FileBean> filesParams = new ArrayList();
		filesParams = FileUtil.getFilesParams(FileUtil.filesPath);
	%>
	<table align="center" width="80%" border="0.5">
		<!-- 表格样式 -->
		<tr>
			<td>文件名</td>
			<td>文件大小</td>
			<td>SHA-1</td>
		</tr>
		<%
			// 如果存在记录
			if (filesParams.size() > 0) {
				// 循环遍历输出列表数据
				for (FileBean file : filesParams) {
		%>
		<tr>
			<td><%=file.getFileName()%></td>
			<td><%=file.getFileSize()%></td>
			<td><%=file.getFileHash()%></td>
			<td>
				<!-- 操作 --> <a href="modify.jsp?port=<%=file.getFileName()%>">修改</a>&nbsp;&nbsp;
				<a href="delete.jsp?port=<%=file.getFileName()%>">删除</a>
			</td>
		</tr>
		<%
			}
			} else { // 如果不存在记录
		%>
		<tr>
			<td colspan="5">数据库中不存在相应的记录！</td>
		</tr>
		<%
			}
		%>
	</table>
	<hr>
</body>
</html>