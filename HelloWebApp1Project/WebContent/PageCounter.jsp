<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<%! private int count = 0; %>
<%! private int sqr(int count) {
		return count * count;
	}
%>
<body>
<h1>page count : <%= ++count %></h1>
<% application.setAttribute("count", count); %>
<h2>sqr : <%= sqr(count) %></h2>
</body>
</html>