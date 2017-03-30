<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String userPath = request.getParameter("userPath");
	
	session.setAttribute("userPath", userPath);
	response.sendRedirect("filelist.jsp");
%>