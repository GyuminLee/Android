<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<%! 
	String initParam;
	int count;

	public void jspInit() {
		count = 10;
	}
%>

<%
	out.println("init Param : " + pageContext.getServletConfig().getInitParameter("logfile") + ", count : " + count++ + "<BR>");
	out.println("context init param : " + application.getInitParameter("debug") + "<BR>");
	out.println("server : " + application.getServerInfo());
	out.println("version : " + application.getMajorVersion());
	application.log("LifeCylce.jsp called");
%>
<%! public void jspDestroy() {
	}
%>
<body>

</body>
</html>