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
ResultSet rs = c.createStatement().executeQuery("SELECT userid,username,registrationid FROM usertbl");%>
<table border="1">
<tr><th>userid</th><th>name</th><th>registrationId</th></tr>
<%
while(rs.next()) {
	String userid = rs.getString("userid");
	String name = rs.getString("username");
	String registrationId = rs.getString("registrationId");
%>
<tr><td><%= userid %></td><td><%= name %></td><td><%= registrationId %></td></tr>
<%
}
rs.close();
%>
</table>

</body>
</html>