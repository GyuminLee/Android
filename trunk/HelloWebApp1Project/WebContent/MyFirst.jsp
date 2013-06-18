<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tool" uri="/taglibs/tools.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%! private int mCount = 0;  %>
<h1> Hello JSP!!!</h1>
<util:line color="blue" size="20" style="bold"/>
<util:box>My First Custom Action</util:box>
<util:max num2="${param.NUM1}" num1="${param.NUM2}"/>
Maximum : ${maximum}<BR>
param1 : ${param.NUM1}, param2 : ${param.NUM2}<BR>
1 ~ 5 :
<util:loop end="5" var="num" start="1">
${num} : ${num * num} <BR>
</util:loop>
<tool:starLine color="blue" size="20"/>
</body>
<tool:box>My First Custom Action</tool:box>
<%@ include file="Today.jsp" %>
</html>