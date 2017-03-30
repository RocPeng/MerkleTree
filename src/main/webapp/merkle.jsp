<%-- 用户删除页面, 主要是删除一项的逻辑运算 --%>
<%@page import="util.FileUtil"%>
<%@page import="dao.IFileDao"%>
<%@page import="dao.FileDao"%>
<%@page import="dao.DaoManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String fileName = new String(request.getParameter("fileName"));// 获取ID参数
	StringBuilder sb=new StringBuilder(fileName);
	if(sb.lastIndexOf(".")>0){
		sb.delete(sb.lastIndexOf("."), sb.length());
	}
	String cotent=FileUtil.readFileToStr(FileUtil.filesPath+"merkle/"+sb.toString());
%>
<%=cotent  %>