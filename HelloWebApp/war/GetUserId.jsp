<?xml version="1.0" encoding="UTF-8"?>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.rdbms.AppEngineDriver" %>
<rss>
	<channel>
<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
if (user != null) {
	Connection c = null;
	ResultSet rs = null;
	try {
		DriverManager.registerDriver(new AppEngineDriver());
		c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
		String countsql = "SELECT id FROM usertbl WHERE userid = '" + user.getEmail() + "'";
		rs = c.createStatement().executeQuery(countsql);
		if (rs.next()) {
			request.getSession().setAttribute("userid", rs.getInt("id"));
%>
		<result>Success</result>
		<resultid><%= rs.getInt("id") %></resultid>
<%			
		} else {
%>
		<result>NotAdded</result>
<%
			
		}
%>
<%
	} catch (Exception e) {
%>
		<result>Fail</result>
<%	
	} finally {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
%>
<%
} else {
%>
		<result>NotLogin</result>
<%
}
%>
	</channel>
</rss>