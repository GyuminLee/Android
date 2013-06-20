<?xml version="1.0" encoding="EUC-KR" ?>
<%@page import="java.io.IOException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.example.web.DBConstant"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>Insert title here</title>
</head>
<body>
<%
	String str = request.getParameter("id");
	int id = Integer.parseInt(str);
	Connection conn = DBConstant.getConnection();
	PreparedStatement statement = conn.prepareStatement(DBConstant.BoardTable.BOARD_DETAIL);
	statement.setInt(DBConstant.BoardTable.FIELD_ID, id);
	ResultSet rs = statement.executeQuery();
	if (rs.next()) {
		pageContext.setAttribute("RESULT", rs);
	} else {
		throw new IOException("Data Empty");
	}
%>
<table>
<tr><td>Author</td><td><c:out value="${RESULT.author}" /></td></tr>
<tr><td>Title</td><td><c:out value="${RESULT.title}" /></td></tr>
<tr><td colspan="2">Content</td></tr>
<tr><td colspan="2"><c:out value="${RESULT.content}" /></td></tr>
</table>

<%
	if (request.getRemoteUser().equals(rs.getString("id"))) {
%>
	<a href="${RESULT.id}/updateForm.jsp">����</a> <a href="${RESULT.id}/boarddelete">����</a>
<%
	}
%>



</body>
</html>