<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>Insert title here</title>
</head>
<body>
<c:set var="mytag" value='"mytagvalue"'></c:set>
TagValue : ${mytag} <br />

<c:out value="<H1>Hi</H1>" />
<c:out value="<H1>Hi</H1>" escapeXml="false"/>

<fmt:formatNumber value="1000000" groupingUsed="true" /><BR />
<fmt:formatNumber value="2.5425" pattern="#.##" /><BR />
<fmt:formatNumber value="2.5" pattern="#.00" /><BR />
<fmt:setLocale value="ko_kr" />
<fmt:bundle basename="Intro" >
message : <fmt:message key="TITLE" /><BR />
</fmt:bundle>
<fmt:setLocale value="en_us" />
<fmt:bundle basename="Intro" >
message : <fmt:message key="TITLE" /><BR />
</fmt:bundle>
</body>
</html>