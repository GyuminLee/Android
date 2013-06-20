<?xml version="1.0" encoding="EUC-KR" ?>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="org.apache.commons.dbcp.PoolingDriver"%>
<%@page import="org.apache.commons.dbcp.PoolableConnectionFactory"%>
<%@page import="org.apache.commons.dbcp.DriverManagerConnectionFactory"%>
<%@page import="org.apache.commons.pool.impl.GenericObjectPool"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>Insert title here</title>
</head>
<body>
	<%
		Connection conn = null;
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/webdb_pool");
		conn.close();
	%>
</body>
</html>