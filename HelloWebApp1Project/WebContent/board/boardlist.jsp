<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.example.web.DBConstant"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.example.web.BoardList" %>
<%@page import="com.example.web.BoardItem" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.google.gson.Gson" %>
<%@ page language="java" contentType="application/json; charset=EUC-KR"
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
	BoardList list = new BoardList();
	list.start = start;
	list.items = new ArrayList<BoardItem>();
	while(rs.next()) {
		BoardItem item = new BoardItem();
		item.id = rs.getInt("id");
		item.link = "http://localhost:8880/HelloWebApp1Project/board/boardshow/" + rs.getInt("id");
		item.title = rs.getString("title");
		item.author = rs.getString("author");
		list.items.add(item);
	}
	rs.last();
	list.count = rs.getRow();
	Gson gson = new Gson();
	String json = gson.toJson(list);
%>
<%= json %>
