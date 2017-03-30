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
	<%
		request.setCharacterEncoding("UTF-8");
		String userPath =session.getAttribute("userPath").toString();
		FileUtil.setUserPath(userPath);
		List<FileBean> files = new ArrayList();
		files = FileUtil.getFiles(FileUtil.filesPath);
		List<FileBean> filesParams = new ArrayList();
		filesParams = FileUtil.getFilesParams(FileUtil.filesPath);
	%>
	<form
		action="${pageContext.request.contextPath}/uploadHandleServlet"
		enctype="multipart/form-data" method="post">
		上传用户：<input type="text" name="username" value="<%=userPath%>"><br />
		上传文件1：<input type="file" name="file1"><br /> 上传文件2：<input
			type="file" name="file2"><br /> <input type="submit"
			value="提交">
	</form>
	<a href="index.jsp">返回首页</a>&nbsp;&nbsp;
	<hr>
	<p>普通视图模式</p>
	<table align="center" width="80%" border="0.5">
		<!-- 表格样式 -->
		<tr>
			<td>文件名</td>
			<td>文件大小</td>
			<td>SHA-1</td>
		</tr>
		<%
			// 如果存在记录
			if (files.size() > 0) {
				// 循环遍历输出列表数据
				for (FileBean file : files) {
		%>
		<tr>
			<td><%=file.getFileName()%></td>
			<td><%=file.getFileSize()%></td>
			<td><%=file.getFileHash()%></td>
			<td>
				<!-- 操作 --> 
				<a href="merkle.jsp?fileName=<%=file.getFileName().get(0)%>">merkle</a>&nbsp;&nbsp;
				<a href=<%=request.getContextPath() +"/downLoadServlet?fileName="+file.getFileName().get(0)%>>下载</a>&nbsp;&nbsp;
				<a href="delete.jsp?fileName=<%=file.getFileName().get(0)%>">删除</a>
			</td>
		</tr>
		<%
			}
			} else { // 如果不存在记录
		%>
		<tr>
			<td colspan="5">当前文件夹下为空！</td>
		</tr>
		<%
			}
		%>
	</table>
	<hr>
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
				<!-- 操作 --> <a >下载</a>&nbsp;&nbsp;
				<a ">删除</a>
			</td>
		</tr>
		<%
			}
			} else { // 如果不存在记录
		%>
		<tr>
			<td colspan="5">当前文件夹下为空！</td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>