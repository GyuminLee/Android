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
	int start = 0;
	int count = 10;
	int total = 0;
	if (request.getParameter("start")!=null) {
		start = Integer.parseInt(request.getParameter("start"));
	}
	if (request.getParameter("count")!=null) {
		count = Integer.parseInt(request.getParameter("count"));
	}
	Connection c = null;
	ResultSet rs = null;
	ResultSet countrs = null;
	try {
		DriverManager.registerDriver(new AppEngineDriver());
		c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
		String countsql = "SELECT COUNT(id) count FROM boardtbl";
		countrs = c.createStatement().executeQuery(countsql);
		if (countrs.next()) {
			total = countrs.getInt("count");
		}
		if (start + count > total) {
			count = total - start;
		}
		String sql = "SELECT boardtbl.id bid, usertbl.id uid, username, " +
					 "usertbl.imagekey ukey, boardtbl.title title, boardtbl.content content, " +
					 "boardtbl.imagekey bkey, COUNT(replytbl.id) replycount " +
					 "FROM usertbl INNER JOIN boardtbl ON usertbl.id = boardtbl.userid " +
					 "LEFT JOIN replytbl ON boardtbl.id = replytbl.boardid " +
					 "GROUP BY boardtbl.id " + 
					 "ORDER BY boardtbl.id DESC " +
					 "LIMIT " + count + " OFFSET " + start;
		rs = c.createStatement().executeQuery(sql);
		String downloadurl = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
			downloadurl += ":" + request.getServerPort();
		}
		downloadurl += "/download?key=";
%>
		<result>Success</result>
		<start><%= start %></start>
		<count><%= count %></count>
		<total><%= total %></total>
<%		
		while (rs.next()) {
%>
		<item>
			<id><%= rs.getInt("bid") %></id>
			<userid><%= rs.getInt("uid") %></userid>
			<username><%= URLEncoder.encode(rs.getString("username"), "UTF-8") %></username>
			<userimageurl><%= downloadurl+rs.getString("ukey") %></userimageurl>
			<title><%= URLEncoder.encode(rs.getString("title"), "UTF-8") %></title>
			<content><%= URLEncoder.encode(rs.getString("content"), "UTF-8") %></content>
			<imageurl><%= downloadurl+rs.getString("bkey") %></imageurl>
			<replaycount><%= rs.getInt("replycount") %></replaycount>
		</item>
<%			
		}
	} catch (Exception e) {
%>
		<result>Fail</result>
<%	
	} finally {
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