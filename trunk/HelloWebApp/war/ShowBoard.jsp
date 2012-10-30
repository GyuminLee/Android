<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.google.appengine.api.rdbms.AppEngineDriver" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Connection c = null;
DriverManager.registerDriver(new AppEngineDriver());
c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boardb/boarddb");
ResultSet rs = c.createStatement().executeQuery("SELECT title,content FROM boardtbl");%>
<table border="1">
<tr><th>title</th><th>content</th></tr>
<%
while(rs.next()) {
	String title = rs.getString("title");
	String content = rs.getString("content");
%>
<tr><td><%= title %></td><td><%= content %></td></tr>
<%
}
rs.close();
%>
</body>
</html>