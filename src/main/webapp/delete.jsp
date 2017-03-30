<%-- 用户删除页面, 主要是删除一项的逻辑运算 --%>
<%@page import="dao.IFileDao"%>
<%@page import="dao.FileDao"%>
<%@page import="dao.DaoManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String fileName = new String(request.getParameter("fileName"));// 获取ID参数
	IFileDao fileDao = DaoManager.getFileDao();// 创建Dao
	fileDao.deleteFile(fileName);
	System.out.println("jsp deleteFile:"+fileName);
	response.sendRedirect("filelist.jsp");// 服务器跳转到列表页面
%>