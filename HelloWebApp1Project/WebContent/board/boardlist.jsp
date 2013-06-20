<?xml version="1.0" encoding="EUC-KR" ?>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.example.web.DBConstant"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	String str = request.getParameter("count");
	int count = 30;
	if (str != null && !str.equals("")) {
		count = Integer.parseInt(str);
	}
	str = request.getParameter("start");
	int start = 0;
	if (str != null && !str.equals("")) {
		start = Integer.parseInt(str);
	}
	Connection conn = DBConstant.getConnection();
	PreparedStatement statement = conn
			.prepareStatement(DBConstant.BoardTable.BOARD_SELECT);
	statement.setInt(DBConstant.BoardTable.FIELD_COUNT, count);
	statement.setInt(DBConstant.BoardTable.FIELD_START, start);
	ResultSet rs = statement.executeQuery();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>Insert title here</title>
</head>
<body>
	<TABLE border="1">
		<TR>
			<th>id</th>
			<th>Title</th>
			<th>Author</th>
		</TR>
		<%
			while (rs.next()) {
		%>
		<tr>
			<td><a href="<%=rs.getInt(DBConstant.BoardTable.ID)%>/boardshow.jsp"><%=rs.getInt(DBConstant.BoardTable.ID)%></a></td>
			<td><%=rs.getString(DBConstant.BoardTable.TITLE)%></td>
			<td><%=rs.getString(DBConstant.BoardTable.AUTHOR)%></td>
		</tr>
		<%
			}
		%>
	</TABLE>

</body>
</html>