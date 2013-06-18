<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	String filePath = application.getRealPath("/WEB-INF/mydata.txt");
	BufferedReader reader = new BufferedReader(new FileReader(filePath));
	String line = null;
	while((line = reader.readLine()) != null) {
		String[] token = line.split("\\:");
		if (token[0].startsWith("이름")) {
			request.setAttribute("name", token[1]);
		} else if (token[0].startsWith("비밀번호")) {
			request.setAttribute("password", token[1]);
		}
	}
	RequestDispatcher dispatcher = request.getRequestDispatcher("Show.jsp");
	dispatcher.forward(request, response);
	reader.close();
%>
</body>
</html>