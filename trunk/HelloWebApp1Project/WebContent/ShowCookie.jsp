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
	Cookie[] cookies = request.getCookies();
	for(Cookie cookie : cookies) {
		out.println("cookie name : " + cookie.getName() + ", value : " + cookie.getValue() + "<BR>");
		if (cookie.getName().equalsIgnoreCase("param1")) {
			
		}
	}
%>
</body>
</html>