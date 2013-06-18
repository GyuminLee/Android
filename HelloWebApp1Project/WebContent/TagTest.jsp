<%@page import="com.example.web.LineDraw"%>
<%@page import="sun.java2d.loops.DrawLine"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tool" uri="/taglibs/tools.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h3>Line Tag</h3>
<mytag:line2 color="red" size="20"/>

<mytag:box2>Test</mytag:box2>

<mytag:max2 num2="10" num1="20"/>
maximum : ${maximum}
<br>
<mytag:loop2 var="num" start="1" end="5">
${num} : ${num * num}<BR>
</mytag:loop2>
<br>
<tool:line2 size="20" color="red"/>
</body>
</html>