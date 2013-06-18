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
	String startStr = request.getParameter("start");
	int start = Integer.parseInt(startStr);
	String endStr = request.getParameter("end");
	int end = Integer.parseInt(endStr);
	int total = 0;
	for (int i = start; i <= end; i++) {
		total += i;
	}
	pageContext.setAttribute("total", total);
%>
${param.start}부터 ${param.end}까지의 합 : ${total}<BR>

count : ${count}<BR>

<%@ include file="Today.jsp" %>

</body>
</html>