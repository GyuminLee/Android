<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.io.IOException" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("euc-kr"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	PrintWriter writer = null;
	String result = "Fail";
	String filePath = null;
	try {
		filePath = application.getRealPath("/WEB-INF/" + "mydata.txt");
		writer = new PrintWriter(filePath);
		writer.println("이름 : " + request.getParameter("name"));
		writer.println("비밀번호 : " + request.getParameter("password"));
		result = "Success";
	} catch (IOException e) {
		
	} finally {
		try {
			writer.close();
		} catch (Exception e) {
			
		}
	}
%>
결과 : <%= result %><br>
file Path : <%= filePath %>
</body>
</html>