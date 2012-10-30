<?xml version="1.0" encoding="UTF-8"?>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.google.appengine.api.rdbms.AppEngineDriver" %>
<rss>
	<channel>
<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
if (user != null) {
	int id = -1;
	if (request.getParameter("id") != null) {
		id = Integer.parseInt(request.getParameter("id"));
	}
	Connection c = null;
	ResultSet boardrs = null;
	ResultSet rs = null;
	ResultSet countrs = null;
	try {
		DriverManager.registerDriver(new AppEngineDriver());
		c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
		String boardsql = "SELECT boardtbl.id bid, usertbl.id uid, username, " + 
						  "usertbl.imagekey ukey, boardtbl.title title, boardtbl.content content, " +
						  "boardtbl.imagekey bkey " + 
						  "FROM usertbl INNER JOIN boardtbl ON usertbl.id = boardtbl.userid " +
						  "WHERE boardtbl.id = " + id;
		boardrs = c.createStatement().executeQuery(boardsql);
		String downloadurl = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
			downloadurl += ":" + request.getServerPort();
		}
		downloadurl += "/download?key=";
		if (boardrs.next()) {
			int count = 0;
			String countsql = "SELECT COUNT(id) count FROM boardtbl WHERE id = " + id;
			countrs = c.createStatement().executeQuery(countsql);
			if (countrs.next()) {
				count = countrs.getInt("count");
			}
%>
		<result>Success</result>
		<id><%= boardrs.getInt("bid") %></id>
		<userid><%= boardrs.getInt("uid") %></userid>
		<username><%= URLEncoder.encode(boardrs.getString("username"), "UTF-8") %></username>
		<userimageurl><%= downloadurl + boardrs.getString("ukey") %></userimageurl>
		<title><%= URLEncoder.encode(boardrs.getString("title"), "UTF-8") %></title>
		<content><%= URLEncoder.encode(boardrs.getString("content"), "UTF-8") %></content>
		<boardimageurl><%= downloadurl + boardrs.getString("bkey") %></boardimageurl>
		<count><%= count %></count>
<%
			String sql = "SELECT replytbl.id rid, usertbl.id uid, username, " +
						 "usertbl.imagekey ukey, boardid, replytbl.content content " +
						 "FROM usertbl INNER JOIN replytbl ON usertbl.id = replytbl.userid " +
						 "WHERE replytbl.boardid = " + id;
			rs = c.createStatement().executeQuery(sql);
			
			while (rs.next()) {
%>
		<item>
			<id><%= rs.getInt("rid") %></id>
			<userid><%= rs.getInt("uid") %></userid>
			<boardid><%= rs.getInt("boardid") %></boardid>
			<username><%= URLEncoder.encode(rs.getString("username"), "UTF-8") %></username>
			<userimageurl><%= downloadurl+rs.getString("ukey") %></userimageurl>
			<content><%= URLEncoder.encode(rs.getString("content"), "UTF-8") %></content>
		</item>
<%				
			}
%>
<%			
		} else {
%>
		<result>Fail</result>
<%			
		}
	} catch (Exception e) {
%>
		<result>Fail</result>
<%	
	} finally {
		if (boardrs != null) {
			try {
				boardrs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (countrs != null) {
			try {
				countrs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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